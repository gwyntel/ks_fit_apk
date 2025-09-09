package androidx.media3.exoplayer.hls;

import android.net.Uri;
import android.os.Handler;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.DataReader;
import androidx.media3.common.DrmInitData;
import androidx.media3.common.Format;
import androidx.media3.common.Metadata;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.HttpDataSource;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.drm.DrmSessionEventListener;
import androidx.media3.exoplayer.drm.DrmSessionManager;
import androidx.media3.exoplayer.hls.HlsChunkSource;
import androidx.media3.exoplayer.source.LoadEventInfo;
import androidx.media3.exoplayer.source.MediaLoadData;
import androidx.media3.exoplayer.source.MediaSourceEventListener;
import androidx.media3.exoplayer.source.SampleQueue;
import androidx.media3.exoplayer.source.SampleStream;
import androidx.media3.exoplayer.source.SequenceableLoader;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.source.chunk.Chunk;
import androidx.media3.exoplayer.trackselection.TrackSelectionUtil;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import androidx.media3.exoplayer.upstream.Loader;
import androidx.media3.extractor.DiscardingTrackOutput;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.metadata.emsg.EventMessage;
import androidx.media3.extractor.metadata.emsg.EventMessageDecoder;
import androidx.media3.extractor.metadata.id3.PrivFrame;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes.dex */
final class HlsSampleStreamWrapper implements Loader.Callback<Chunk>, Loader.ReleaseCallback, SequenceableLoader, ExtractorOutput, SampleQueue.UpstreamFormatChangedListener {
    private static final Set<Integer> MAPPABLE_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(1, 2, 5)));
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_FATAL = -2;
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_NON_FATAL = -3;
    public static final int SAMPLE_QUEUE_INDEX_PENDING = -1;
    private static final String TAG = "HlsSampleStreamWrapper";
    private final Allocator allocator;
    private final Callback callback;
    private final HlsChunkSource chunkSource;

    @Nullable
    private Format downstreamTrackFormat;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;

    @Nullable
    private DrmInitData drmInitData;
    private final DrmSessionManager drmSessionManager;
    private TrackOutput emsgUnwrappingTrackOutput;
    private int enabledTrackGroupCount;
    private final Handler handler;
    private boolean haveAudioVideoSampleQueues;
    private final ArrayList<HlsSampleStream> hlsSampleStreams;
    private long lastSeekPositionUs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;

    @Nullable
    private Chunk loadingChunk;
    private boolean loadingFinished;
    private final Runnable maybeFinishPrepareRunnable;
    private final ArrayList<HlsMediaChunk> mediaChunks;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
    private final int metadataType;

    @Nullable
    private final Format muxedAudioFormat;
    private final Runnable onTracksEndedRunnable;
    private Set<TrackGroup> optionalTrackGroups;
    private final Map<String, DrmInitData> overridingDrmInitData;
    private long pendingResetPositionUs;
    private boolean pendingResetUpstreamFormats;
    private boolean prepared;
    private int primarySampleQueueIndex;
    private int primarySampleQueueType;
    private int primaryTrackGroupIndex;
    private final List<HlsMediaChunk> readOnlyMediaChunks;
    private boolean released;
    private long sampleOffsetUs;
    private SparseIntArray sampleQueueIndicesByType;
    private boolean[] sampleQueueIsAudioVideoFlags;
    private Set<Integer> sampleQueueMappingDoneByType;
    private HlsSampleQueue[] sampleQueues;
    private boolean sampleQueuesBuilt;
    private boolean[] sampleQueuesEnabledStates;
    private boolean seenFirstTrackSelection;

    @Nullable
    private HlsMediaChunk sourceChunk;
    private int[] trackGroupToSampleQueueIndex;
    private TrackGroupArray trackGroups;
    private final int trackType;
    private boolean tracksEnded;
    private final String uid;
    private Format upstreamTrackFormat;
    private final Loader loader = new Loader("Loader:HlsSampleStreamWrapper");
    private final HlsChunkSource.HlsChunkHolder nextChunkHolder = new HlsChunkSource.HlsChunkHolder();
    private int[] sampleQueueTrackIds = new int[0];

    public interface Callback extends SequenceableLoader.Callback<HlsSampleStreamWrapper> {
        void onPlaylistRefreshRequired(Uri uri);

        void onPrepared();
    }

    private static class EmsgUnwrappingTrackOutput implements TrackOutput {
        private byte[] buffer;
        private int bufferPosition;
        private final TrackOutput delegate;
        private final Format delegateFormat;
        private final EventMessageDecoder emsgDecoder = new EventMessageDecoder();
        private Format format;
        private static final Format ID3_FORMAT = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_ID3).build();
        private static final Format EMSG_FORMAT = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_EMSG).build();

        public EmsgUnwrappingTrackOutput(TrackOutput trackOutput, int i2) {
            this.delegate = trackOutput;
            if (i2 == 1) {
                this.delegateFormat = ID3_FORMAT;
            } else {
                if (i2 != 3) {
                    throw new IllegalArgumentException("Unknown metadataType: " + i2);
                }
                this.delegateFormat = EMSG_FORMAT;
            }
            this.buffer = new byte[0];
            this.bufferPosition = 0;
        }

        private boolean emsgContainsExpectedWrappedFormat(EventMessage eventMessage) {
            Format wrappedMetadataFormat = eventMessage.getWrappedMetadataFormat();
            return wrappedMetadataFormat != null && Util.areEqual(this.delegateFormat.sampleMimeType, wrappedMetadataFormat.sampleMimeType);
        }

        private void ensureBufferCapacity(int i2) {
            byte[] bArr = this.buffer;
            if (bArr.length < i2) {
                this.buffer = Arrays.copyOf(bArr, i2 + (i2 / 2));
            }
        }

        private ParsableByteArray getSampleAndTrimBuffer(int i2, int i3) {
            int i4 = this.bufferPosition - i3;
            ParsableByteArray parsableByteArray = new ParsableByteArray(Arrays.copyOfRange(this.buffer, i4 - i2, i4));
            byte[] bArr = this.buffer;
            System.arraycopy(bArr, i4, bArr, 0, i3);
            this.bufferPosition = i3;
            return parsableByteArray;
        }

        @Override // androidx.media3.extractor.TrackOutput
        public void format(Format format) {
            this.format = format;
            this.delegate.format(this.delegateFormat);
        }

        @Override // androidx.media3.extractor.TrackOutput
        public /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) {
            return androidx.media3.extractor.g.a(this, dataReader, i2, z2);
        }

        @Override // androidx.media3.extractor.TrackOutput
        public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
            Assertions.checkNotNull(this.format);
            ParsableByteArray sampleAndTrimBuffer = getSampleAndTrimBuffer(i3, i4);
            if (!Util.areEqual(this.format.sampleMimeType, this.delegateFormat.sampleMimeType)) {
                if (!MimeTypes.APPLICATION_EMSG.equals(this.format.sampleMimeType)) {
                    Log.w(HlsSampleStreamWrapper.TAG, "Ignoring sample for unsupported format: " + this.format.sampleMimeType);
                    return;
                }
                EventMessage eventMessageDecode = this.emsgDecoder.decode(sampleAndTrimBuffer);
                if (!emsgContainsExpectedWrappedFormat(eventMessageDecode)) {
                    Log.w(HlsSampleStreamWrapper.TAG, String.format("Ignoring EMSG. Expected it to contain wrapped %s but actual wrapped format: %s", this.delegateFormat.sampleMimeType, eventMessageDecode.getWrappedMetadataFormat()));
                    return;
                }
                sampleAndTrimBuffer = new ParsableByteArray((byte[]) Assertions.checkNotNull(eventMessageDecode.getWrappedMetadataBytes()));
            }
            int iBytesLeft = sampleAndTrimBuffer.bytesLeft();
            this.delegate.sampleData(sampleAndTrimBuffer, iBytesLeft);
            this.delegate.sampleMetadata(j2, i2, iBytesLeft, 0, cryptoData);
        }

        @Override // androidx.media3.extractor.TrackOutput
        public /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
            androidx.media3.extractor.g.b(this, parsableByteArray, i2);
        }

        @Override // androidx.media3.extractor.TrackOutput
        public int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
            ensureBufferCapacity(this.bufferPosition + i2);
            int i4 = dataReader.read(this.buffer, this.bufferPosition, i2);
            if (i4 != -1) {
                this.bufferPosition += i4;
                return i4;
            }
            if (z2) {
                return -1;
            }
            throw new EOFException();
        }

        @Override // androidx.media3.extractor.TrackOutput
        public void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
            ensureBufferCapacity(this.bufferPosition + i2);
            parsableByteArray.readBytes(this.buffer, this.bufferPosition, i2);
            this.bufferPosition += i2;
        }
    }

    private static final class HlsSampleQueue extends SampleQueue {

        @Nullable
        private DrmInitData drmInitData;
        private final Map<String, DrmInitData> overridingDrmInitData;

        @Nullable
        private Metadata getAdjustedMetadata(@Nullable Metadata metadata) {
            if (metadata == null) {
                return null;
            }
            int length = metadata.length();
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    i3 = -1;
                    break;
                }
                Metadata.Entry entry = metadata.get(i3);
                if ((entry instanceof PrivFrame) && HlsMediaChunk.PRIV_TIMESTAMP_FRAME_OWNER.equals(((PrivFrame) entry).owner)) {
                    break;
                }
                i3++;
            }
            if (i3 == -1) {
                return metadata;
            }
            if (length == 1) {
                return null;
            }
            Metadata.Entry[] entryArr = new Metadata.Entry[length - 1];
            while (i2 < length) {
                if (i2 != i3) {
                    entryArr[i2 < i3 ? i2 : i2 - 1] = metadata.get(i2);
                }
                i2++;
            }
            return new Metadata(entryArr);
        }

        @Override // androidx.media3.exoplayer.source.SampleQueue
        public Format getAdjustedUpstreamFormat(Format format) {
            DrmInitData drmInitData;
            DrmInitData drmInitData2 = this.drmInitData;
            if (drmInitData2 == null) {
                drmInitData2 = format.drmInitData;
            }
            if (drmInitData2 != null && (drmInitData = this.overridingDrmInitData.get(drmInitData2.schemeType)) != null) {
                drmInitData2 = drmInitData;
            }
            Metadata adjustedMetadata = getAdjustedMetadata(format.metadata);
            if (drmInitData2 != format.drmInitData || adjustedMetadata != format.metadata) {
                format = format.buildUpon().setDrmInitData(drmInitData2).setMetadata(adjustedMetadata).build();
            }
            return super.getAdjustedUpstreamFormat(format);
        }

        @Override // androidx.media3.exoplayer.source.SampleQueue, androidx.media3.extractor.TrackOutput
        public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
            super.sampleMetadata(j2, i2, i3, i4, cryptoData);
        }

        public void setDrmInitData(@Nullable DrmInitData drmInitData) {
            this.drmInitData = drmInitData;
            b();
        }

        public void setSourceChunk(HlsMediaChunk hlsMediaChunk) {
            sourceId(hlsMediaChunk.uid);
        }

        private HlsSampleQueue(Allocator allocator, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, Map<String, DrmInitData> map) {
            super(allocator, drmSessionManager, eventDispatcher);
            this.overridingDrmInitData = map;
        }
    }

    public HlsSampleStreamWrapper(String str, int i2, Callback callback, HlsChunkSource hlsChunkSource, Map<String, DrmInitData> map, Allocator allocator, long j2, @Nullable Format format, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, int i3) {
        this.uid = str;
        this.trackType = i2;
        this.callback = callback;
        this.chunkSource = hlsChunkSource;
        this.overridingDrmInitData = map;
        this.allocator = allocator;
        this.muxedAudioFormat = format;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = eventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.mediaSourceEventDispatcher = eventDispatcher2;
        this.metadataType = i3;
        Set<Integer> set = MAPPABLE_TYPES;
        this.sampleQueueMappingDoneByType = new HashSet(set.size());
        this.sampleQueueIndicesByType = new SparseIntArray(set.size());
        this.sampleQueues = new HlsSampleQueue[0];
        this.sampleQueueIsAudioVideoFlags = new boolean[0];
        this.sampleQueuesEnabledStates = new boolean[0];
        ArrayList<HlsMediaChunk> arrayList = new ArrayList<>();
        this.mediaChunks = arrayList;
        this.readOnlyMediaChunks = Collections.unmodifiableList(arrayList);
        this.hlsSampleStreams = new ArrayList<>();
        this.maybeFinishPrepareRunnable = new Runnable() { // from class: androidx.media3.exoplayer.hls.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f5259a.maybeFinishPrepare();
            }
        };
        this.onTracksEndedRunnable = new Runnable() { // from class: androidx.media3.exoplayer.hls.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f5260a.onTracksEnded();
            }
        };
        this.handler = Util.createHandlerForCurrentLooper();
        this.lastSeekPositionUs = j2;
        this.pendingResetPositionUs = j2;
    }

    @EnsuresNonNull({"trackGroups", "optionalTrackGroups"})
    private void assertIsPrepared() {
        Assertions.checkState(this.prepared);
        Assertions.checkNotNull(this.trackGroups);
        Assertions.checkNotNull(this.optionalTrackGroups);
    }

    @EnsuresNonNull({"trackGroups", "optionalTrackGroups", "trackGroupToSampleQueueIndex"})
    private void buildTracksFromSampleStreams() {
        Format format;
        int length = this.sampleQueues.length;
        int i2 = -2;
        int i3 = -1;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                break;
            }
            String str = ((Format) Assertions.checkStateNotNull(this.sampleQueues[i4].getUpstreamFormat())).sampleMimeType;
            int i5 = MimeTypes.isVideo(str) ? 2 : MimeTypes.isAudio(str) ? 1 : MimeTypes.isText(str) ? 3 : -2;
            if (getTrackTypeScore(i5) > getTrackTypeScore(i2)) {
                i3 = i4;
                i2 = i5;
            } else if (i5 == i2 && i3 != -1) {
                i3 = -1;
            }
            i4++;
        }
        TrackGroup trackGroup = this.chunkSource.getTrackGroup();
        int i6 = trackGroup.length;
        this.primaryTrackGroupIndex = -1;
        this.trackGroupToSampleQueueIndex = new int[length];
        for (int i7 = 0; i7 < length; i7++) {
            this.trackGroupToSampleQueueIndex[i7] = i7;
        }
        TrackGroup[] trackGroupArr = new TrackGroup[length];
        int i8 = 0;
        while (i8 < length) {
            Format format2 = (Format) Assertions.checkStateNotNull(this.sampleQueues[i8].getUpstreamFormat());
            if (i8 == i3) {
                Format[] formatArr = new Format[i6];
                for (int i9 = 0; i9 < i6; i9++) {
                    Format format3 = trackGroup.getFormat(i9);
                    if (i2 == 1 && (format = this.muxedAudioFormat) != null) {
                        format3 = format3.withManifestFormatInfo(format);
                    }
                    formatArr[i9] = i6 == 1 ? format2.withManifestFormatInfo(format3) : deriveFormat(format3, format2, true);
                }
                trackGroupArr[i8] = new TrackGroup(this.uid, formatArr);
                this.primaryTrackGroupIndex = i8;
            } else {
                Format format4 = (i2 == 2 && MimeTypes.isAudio(format2.sampleMimeType)) ? this.muxedAudioFormat : null;
                StringBuilder sb = new StringBuilder();
                sb.append(this.uid);
                sb.append(":muxed:");
                sb.append(i8 < i3 ? i8 : i8 - 1);
                trackGroupArr[i8] = new TrackGroup(sb.toString(), deriveFormat(format4, format2, false));
            }
            i8++;
        }
        this.trackGroups = createTrackGroupArrayWithDrmInfo(trackGroupArr);
        Assertions.checkState(this.optionalTrackGroups == null);
        this.optionalTrackGroups = Collections.emptySet();
    }

    private boolean canDiscardUpstreamMediaChunksFromIndex(int i2) {
        for (int i3 = i2; i3 < this.mediaChunks.size(); i3++) {
            if (this.mediaChunks.get(i3).shouldSpliceIn) {
                return false;
            }
        }
        HlsMediaChunk hlsMediaChunk = this.mediaChunks.get(i2);
        for (int i4 = 0; i4 < this.sampleQueues.length; i4++) {
            if (this.sampleQueues[i4].getReadIndex() > hlsMediaChunk.getFirstSampleIndex(i4)) {
                return false;
            }
        }
        return true;
    }

    private static DiscardingTrackOutput createDiscardingTrackOutput(int i2, int i3) {
        Log.w(TAG, "Unmapped track with id " + i2 + " of type " + i3);
        return new DiscardingTrackOutput();
    }

    private SampleQueue createSampleQueue(int i2, int i3) {
        int length = this.sampleQueues.length;
        boolean z2 = true;
        if (i3 != 1 && i3 != 2) {
            z2 = false;
        }
        HlsSampleQueue hlsSampleQueue = new HlsSampleQueue(this.allocator, this.drmSessionManager, this.drmEventDispatcher, this.overridingDrmInitData);
        hlsSampleQueue.setStartTimeUs(this.lastSeekPositionUs);
        if (z2) {
            hlsSampleQueue.setDrmInitData(this.drmInitData);
        }
        hlsSampleQueue.setSampleOffsetUs(this.sampleOffsetUs);
        HlsMediaChunk hlsMediaChunk = this.sourceChunk;
        if (hlsMediaChunk != null) {
            hlsSampleQueue.setSourceChunk(hlsMediaChunk);
        }
        hlsSampleQueue.setUpstreamFormatChangeListener(this);
        int i4 = length + 1;
        int[] iArrCopyOf = Arrays.copyOf(this.sampleQueueTrackIds, i4);
        this.sampleQueueTrackIds = iArrCopyOf;
        iArrCopyOf[length] = i2;
        this.sampleQueues = (HlsSampleQueue[]) Util.nullSafeArrayAppend(this.sampleQueues, hlsSampleQueue);
        boolean[] zArrCopyOf = Arrays.copyOf(this.sampleQueueIsAudioVideoFlags, i4);
        this.sampleQueueIsAudioVideoFlags = zArrCopyOf;
        zArrCopyOf[length] = z2;
        this.haveAudioVideoSampleQueues |= z2;
        this.sampleQueueMappingDoneByType.add(Integer.valueOf(i3));
        this.sampleQueueIndicesByType.append(i3, length);
        if (getTrackTypeScore(i3) > getTrackTypeScore(this.primarySampleQueueType)) {
            this.primarySampleQueueIndex = length;
            this.primarySampleQueueType = i3;
        }
        this.sampleQueuesEnabledStates = Arrays.copyOf(this.sampleQueuesEnabledStates, i4);
        return hlsSampleQueue;
    }

    private TrackGroupArray createTrackGroupArrayWithDrmInfo(TrackGroup[] trackGroupArr) {
        for (int i2 = 0; i2 < trackGroupArr.length; i2++) {
            TrackGroup trackGroup = trackGroupArr[i2];
            Format[] formatArr = new Format[trackGroup.length];
            for (int i3 = 0; i3 < trackGroup.length; i3++) {
                Format format = trackGroup.getFormat(i3);
                formatArr[i3] = format.copyWithCryptoType(this.drmSessionManager.getCryptoType(format));
            }
            trackGroupArr[i2] = new TrackGroup(trackGroup.id, formatArr);
        }
        return new TrackGroupArray(trackGroupArr);
    }

    private static Format deriveFormat(@Nullable Format format, Format format2, boolean z2) {
        String codecsCorrespondingToMimeType;
        String mediaMimeType;
        if (format == null) {
            return format2;
        }
        int trackType = MimeTypes.getTrackType(format2.sampleMimeType);
        if (Util.getCodecCountOfType(format.codecs, trackType) == 1) {
            codecsCorrespondingToMimeType = Util.getCodecsOfType(format.codecs, trackType);
            mediaMimeType = MimeTypes.getMediaMimeType(codecsCorrespondingToMimeType);
        } else {
            codecsCorrespondingToMimeType = MimeTypes.getCodecsCorrespondingToMimeType(format.codecs, format2.sampleMimeType);
            mediaMimeType = format2.sampleMimeType;
        }
        Format.Builder codecs = format2.buildUpon().setId(format.id).setLabel(format.label).setLabels(format.labels).setLanguage(format.language).setSelectionFlags(format.selectionFlags).setRoleFlags(format.roleFlags).setAverageBitrate(z2 ? format.averageBitrate : -1).setPeakBitrate(z2 ? format.peakBitrate : -1).setCodecs(codecsCorrespondingToMimeType);
        if (trackType == 2) {
            codecs.setWidth(format.width).setHeight(format.height).setFrameRate(format.frameRate);
        }
        if (mediaMimeType != null) {
            codecs.setSampleMimeType(mediaMimeType);
        }
        int i2 = format.channelCount;
        if (i2 != -1 && trackType == 1) {
            codecs.setChannelCount(i2);
        }
        Metadata metadataCopyWithAppendedEntriesFrom = format.metadata;
        if (metadataCopyWithAppendedEntriesFrom != null) {
            Metadata metadata = format2.metadata;
            if (metadata != null) {
                metadataCopyWithAppendedEntriesFrom = metadata.copyWithAppendedEntriesFrom(metadataCopyWithAppendedEntriesFrom);
            }
            codecs.setMetadata(metadataCopyWithAppendedEntriesFrom);
        }
        return codecs.build();
    }

    private void discardUpstream(int i2) {
        Assertions.checkState(!this.loader.isLoading());
        while (true) {
            if (i2 >= this.mediaChunks.size()) {
                i2 = -1;
                break;
            } else if (canDiscardUpstreamMediaChunksFromIndex(i2)) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 == -1) {
            return;
        }
        long j2 = getLastMediaChunk().endTimeUs;
        HlsMediaChunk hlsMediaChunkDiscardUpstreamMediaChunksFromIndex = discardUpstreamMediaChunksFromIndex(i2);
        if (this.mediaChunks.isEmpty()) {
            this.pendingResetPositionUs = this.lastSeekPositionUs;
        } else {
            ((HlsMediaChunk) Iterables.getLast(this.mediaChunks)).invalidateExtractor();
        }
        this.loadingFinished = false;
        this.mediaSourceEventDispatcher.upstreamDiscarded(this.primarySampleQueueType, hlsMediaChunkDiscardUpstreamMediaChunksFromIndex.startTimeUs, j2);
    }

    private HlsMediaChunk discardUpstreamMediaChunksFromIndex(int i2) {
        HlsMediaChunk hlsMediaChunk = this.mediaChunks.get(i2);
        ArrayList<HlsMediaChunk> arrayList = this.mediaChunks;
        Util.removeRange(arrayList, i2, arrayList.size());
        for (int i3 = 0; i3 < this.sampleQueues.length; i3++) {
            this.sampleQueues[i3].discardUpstreamSamples(hlsMediaChunk.getFirstSampleIndex(i3));
        }
        return hlsMediaChunk;
    }

    private boolean finishedReadingChunk(HlsMediaChunk hlsMediaChunk) {
        int i2 = hlsMediaChunk.uid;
        int length = this.sampleQueues.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (this.sampleQueuesEnabledStates[i3] && this.sampleQueues[i3].peekSourceId() == i2) {
                return false;
            }
        }
        return true;
    }

    private static boolean formatsMatch(Format format, Format format2) {
        String str = format.sampleMimeType;
        String str2 = format2.sampleMimeType;
        int trackType = MimeTypes.getTrackType(str);
        if (trackType != 3) {
            return trackType == MimeTypes.getTrackType(str2);
        }
        if (Util.areEqual(str, str2)) {
            return !(MimeTypes.APPLICATION_CEA608.equals(str) || MimeTypes.APPLICATION_CEA708.equals(str)) || format.accessibilityChannel == format2.accessibilityChannel;
        }
        return false;
    }

    private HlsMediaChunk getLastMediaChunk() {
        return this.mediaChunks.get(r0.size() - 1);
    }

    @Nullable
    private TrackOutput getMappedTrackOutput(int i2, int i3) {
        Assertions.checkArgument(MAPPABLE_TYPES.contains(Integer.valueOf(i3)));
        int i4 = this.sampleQueueIndicesByType.get(i3, -1);
        if (i4 == -1) {
            return null;
        }
        if (this.sampleQueueMappingDoneByType.add(Integer.valueOf(i3))) {
            this.sampleQueueTrackIds[i4] = i2;
        }
        return this.sampleQueueTrackIds[i4] == i2 ? this.sampleQueues[i4] : createDiscardingTrackOutput(i2, i3);
    }

    private static int getTrackTypeScore(int i2) {
        if (i2 == 1) {
            return 2;
        }
        if (i2 != 2) {
            return i2 != 3 ? 0 : 1;
        }
        return 3;
    }

    private void initMediaChunkLoad(HlsMediaChunk hlsMediaChunk) {
        this.sourceChunk = hlsMediaChunk;
        this.upstreamTrackFormat = hlsMediaChunk.trackFormat;
        this.pendingResetPositionUs = C.TIME_UNSET;
        this.mediaChunks.add(hlsMediaChunk);
        ImmutableList.Builder builder = ImmutableList.builder();
        for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
            builder.add((ImmutableList.Builder) Integer.valueOf(hlsSampleQueue.getWriteIndex()));
        }
        hlsMediaChunk.init(this, builder.build());
        for (HlsSampleQueue hlsSampleQueue2 : this.sampleQueues) {
            hlsSampleQueue2.setSourceChunk(hlsMediaChunk);
            if (hlsMediaChunk.shouldSpliceIn) {
                hlsSampleQueue2.splice();
            }
        }
    }

    private static boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof HlsMediaChunk;
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C.TIME_UNSET;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPlaylistUpdated$0(HlsMediaChunk hlsMediaChunk) {
        this.callback.onPlaylistRefreshRequired(hlsMediaChunk.playlistUrl);
    }

    @EnsuresNonNull({"trackGroupToSampleQueueIndex"})
    @RequiresNonNull({"trackGroups"})
    private void mapSampleQueuesToMatchTrackGroups() {
        int i2 = this.trackGroups.length;
        int[] iArr = new int[i2];
        this.trackGroupToSampleQueueIndex = iArr;
        Arrays.fill(iArr, -1);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = 0;
            while (true) {
                HlsSampleQueue[] hlsSampleQueueArr = this.sampleQueues;
                if (i4 >= hlsSampleQueueArr.length) {
                    break;
                }
                if (formatsMatch((Format) Assertions.checkStateNotNull(hlsSampleQueueArr[i4].getUpstreamFormat()), this.trackGroups.get(i3).getFormat(0))) {
                    this.trackGroupToSampleQueueIndex[i3] = i4;
                    break;
                }
                i4++;
            }
        }
        Iterator<HlsSampleStream> it = this.hlsSampleStreams.iterator();
        while (it.hasNext()) {
            it.next().bindSampleQueue();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeFinishPrepare() {
        if (!this.released && this.trackGroupToSampleQueueIndex == null && this.sampleQueuesBuilt) {
            for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
                if (hlsSampleQueue.getUpstreamFormat() == null) {
                    return;
                }
            }
            if (this.trackGroups != null) {
                mapSampleQueuesToMatchTrackGroups();
                return;
            }
            buildTracksFromSampleStreams();
            setIsPrepared();
            this.callback.onPrepared();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTracksEnded() {
        this.sampleQueuesBuilt = true;
        maybeFinishPrepare();
    }

    private void resetSampleQueues() {
        for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
            hlsSampleQueue.reset(this.pendingResetUpstreamFormats);
        }
        this.pendingResetUpstreamFormats = false;
    }

    private boolean seekInsideBufferUs(long j2, @Nullable HlsMediaChunk hlsMediaChunk) {
        int length = this.sampleQueues.length;
        for (int i2 = 0; i2 < length; i2++) {
            HlsSampleQueue hlsSampleQueue = this.sampleQueues[i2];
            if (!(hlsMediaChunk != null ? hlsSampleQueue.seekTo(hlsMediaChunk.getFirstSampleIndex(i2)) : hlsSampleQueue.seekTo(j2, false)) && (this.sampleQueueIsAudioVideoFlags[i2] || !this.haveAudioVideoSampleQueues)) {
                return false;
            }
        }
        return true;
    }

    @RequiresNonNull({"trackGroups", "optionalTrackGroups"})
    private void setIsPrepared() {
        this.prepared = true;
    }

    private void updateSampleStreams(SampleStream[] sampleStreamArr) {
        this.hlsSampleStreams.clear();
        for (SampleStream sampleStream : sampleStreamArr) {
            if (sampleStream != null) {
                this.hlsSampleStreams.add((HlsSampleStream) sampleStream);
            }
        }
    }

    public int bindSampleQueueToSampleStream(int i2) {
        assertIsPrepared();
        Assertions.checkNotNull(this.trackGroupToSampleQueueIndex);
        int i3 = this.trackGroupToSampleQueueIndex[i2];
        if (i3 == -1) {
            return this.optionalTrackGroups.contains(this.trackGroups.get(i2)) ? -3 : -2;
        }
        boolean[] zArr = this.sampleQueuesEnabledStates;
        if (zArr[i3]) {
            return -2;
        }
        zArr[i3] = true;
        return i3;
    }

    @Override // androidx.media3.exoplayer.source.SequenceableLoader
    public boolean continueLoading(LoadingInfo loadingInfo) {
        List<HlsMediaChunk> listEmptyList;
        long jMax;
        if (this.loadingFinished || this.loader.isLoading() || this.loader.hasFatalError()) {
            return false;
        }
        if (isPendingReset()) {
            listEmptyList = Collections.emptyList();
            jMax = this.pendingResetPositionUs;
            for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
                hlsSampleQueue.setStartTimeUs(this.pendingResetPositionUs);
            }
        } else {
            listEmptyList = this.readOnlyMediaChunks;
            HlsMediaChunk lastMediaChunk = getLastMediaChunk();
            jMax = lastMediaChunk.isLoadCompleted() ? lastMediaChunk.endTimeUs : Math.max(this.lastSeekPositionUs, lastMediaChunk.startTimeUs);
        }
        List<HlsMediaChunk> list = listEmptyList;
        long j2 = jMax;
        this.nextChunkHolder.clear();
        this.chunkSource.getNextChunk(loadingInfo, j2, list, this.prepared || !list.isEmpty(), this.nextChunkHolder);
        HlsChunkSource.HlsChunkHolder hlsChunkHolder = this.nextChunkHolder;
        boolean z2 = hlsChunkHolder.endOfStream;
        Chunk chunk = hlsChunkHolder.chunk;
        Uri uri = hlsChunkHolder.playlistUrl;
        if (z2) {
            this.pendingResetPositionUs = C.TIME_UNSET;
            this.loadingFinished = true;
            return true;
        }
        if (chunk == null) {
            if (uri != null) {
                this.callback.onPlaylistRefreshRequired(uri);
            }
            return false;
        }
        if (isMediaChunk(chunk)) {
            initMediaChunkLoad((HlsMediaChunk) chunk);
        }
        this.loadingChunk = chunk;
        this.mediaSourceEventDispatcher.loadStarted(new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, this.loader.startLoading(chunk, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(chunk.type))), chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
        return true;
    }

    public void continuePreparing() {
        if (this.prepared) {
            return;
        }
        continueLoading(new LoadingInfo.Builder().setPlaybackPositionUs(this.lastSeekPositionUs).build());
    }

    public void discardBuffer(long j2, boolean z2) {
        if (!this.sampleQueuesBuilt || isPendingReset()) {
            return;
        }
        int length = this.sampleQueues.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.sampleQueues[i2].discardTo(j2, z2, this.sampleQueuesEnabledStates[i2]);
        }
    }

    @Override // androidx.media3.extractor.ExtractorOutput
    public void endTracks() {
        this.tracksEnded = true;
        this.handler.post(this.onTracksEndedRunnable);
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return this.chunkSource.getAdjustedSeekPositionUs(j2, seekParameters);
    }

    /*  JADX ERROR: NullPointerException in pass: LoopRegionVisitor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.use(jadx.core.dex.instructions.args.RegisterArg)" because "ssaVar" is null
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:493)
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:496)
        */
    @Override // androidx.media3.exoplayer.source.SequenceableLoader
    public long getBufferedPositionUs() {
        /*
            r7 = this;
            boolean r0 = r7.loadingFinished
            if (r0 == 0) goto L7
            r0 = -9223372036854775808
            return r0
        L7:
            boolean r0 = r7.isPendingReset()
            if (r0 == 0) goto L10
            long r0 = r7.pendingResetPositionUs
            return r0
        L10:
            long r0 = r7.lastSeekPositionUs
            androidx.media3.exoplayer.hls.HlsMediaChunk r2 = r7.getLastMediaChunk()
            boolean r3 = r2.isLoadCompleted()
            if (r3 == 0) goto L1d
            goto L36
        L1d:
            java.util.ArrayList<androidx.media3.exoplayer.hls.HlsMediaChunk> r2 = r7.mediaChunks
            int r2 = r2.size()
            r3 = 1
            if (r2 <= r3) goto L35
            java.util.ArrayList<androidx.media3.exoplayer.hls.HlsMediaChunk> r2 = r7.mediaChunks
            int r3 = r2.size()
            int r3 = r3 + (-2)
            java.lang.Object r2 = r2.get(r3)
            androidx.media3.exoplayer.hls.HlsMediaChunk r2 = (androidx.media3.exoplayer.hls.HlsMediaChunk) r2
            goto L36
        L35:
            r2 = 0
        L36:
            if (r2 == 0) goto L3e
            long r2 = r2.endTimeUs
            long r0 = java.lang.Math.max(r0, r2)
        L3e:
            boolean r2 = r7.sampleQueuesBuilt
            if (r2 == 0) goto L55
            androidx.media3.exoplayer.hls.HlsSampleStreamWrapper$HlsSampleQueue[] r2 = r7.sampleQueues
            int r3 = r2.length
            r4 = 0
        L46:
            if (r4 >= r3) goto L55
            r5 = r2[r4]
            long r5 = r5.getLargestQueuedTimestampUs()
            long r0 = java.lang.Math.max(r0, r5)
            int r4 = r4 + 1
            goto L46
        L55:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.hls.HlsSampleStreamWrapper.getBufferedPositionUs():long");
    }

    @Override // androidx.media3.exoplayer.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        return getLastMediaChunk().endTimeUs;
    }

    public int getPrimaryTrackGroupIndex() {
        return this.primaryTrackGroupIndex;
    }

    public TrackGroupArray getTrackGroups() {
        assertIsPrepared();
        return this.trackGroups;
    }

    @Override // androidx.media3.exoplayer.source.SequenceableLoader
    public boolean isLoading() {
        return this.loader.isLoading();
    }

    public boolean isReady(int i2) {
        return !isPendingReset() && this.sampleQueues[i2].isReady(this.loadingFinished);
    }

    public boolean isVideoSampleStream() {
        return this.primarySampleQueueType == 2;
    }

    public void maybeThrowError(int i2) throws IOException {
        maybeThrowError();
        this.sampleQueues[i2].maybeThrowError();
    }

    public void maybeThrowPrepareError() throws IOException {
        maybeThrowError();
        if (this.loadingFinished && !this.prepared) {
            throw ParserException.createForMalformedContainer("Loading finished before preparation is complete.", null);
        }
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.ReleaseCallback
    public void onLoaderReleased() {
        for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
            hlsSampleQueue.release();
        }
    }

    public void onNewExtractor() {
        this.sampleQueueMappingDoneByType.clear();
    }

    public boolean onPlaylistError(Uri uri, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean z2) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor;
        if (!this.chunkSource.obtainsChunksForPlaylist(uri)) {
            return true;
        }
        long j2 = (z2 || (fallbackSelectionFor = this.loadErrorHandlingPolicy.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.chunkSource.getTrackSelection()), loadErrorInfo)) == null || fallbackSelectionFor.type != 2) ? -9223372036854775807L : fallbackSelectionFor.exclusionDurationMs;
        return this.chunkSource.onPlaylistError(uri, j2) && j2 != C.TIME_UNSET;
    }

    public void onPlaylistUpdated() {
        if (this.mediaChunks.isEmpty()) {
            return;
        }
        final HlsMediaChunk hlsMediaChunk = (HlsMediaChunk) Iterables.getLast(this.mediaChunks);
        int chunkPublicationState = this.chunkSource.getChunkPublicationState(hlsMediaChunk);
        if (chunkPublicationState == 1) {
            hlsMediaChunk.publish();
            return;
        }
        if (chunkPublicationState == 0) {
            this.handler.post(new Runnable() { // from class: androidx.media3.exoplayer.hls.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f5257a.lambda$onPlaylistUpdated$0(hlsMediaChunk);
                }
            });
        } else if (chunkPublicationState == 2 && !this.loadingFinished && this.loader.isLoading()) {
            this.loader.cancelLoading();
        }
    }

    @Override // androidx.media3.exoplayer.source.SampleQueue.UpstreamFormatChangedListener
    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void prepareWithMultivariantPlaylistInfo(TrackGroup[] trackGroupArr, int i2, int... iArr) {
        this.trackGroups = createTrackGroupArrayWithDrmInfo(trackGroupArr);
        this.optionalTrackGroups = new HashSet();
        for (int i3 : iArr) {
            this.optionalTrackGroups.add(this.trackGroups.get(i3));
        }
        this.primaryTrackGroupIndex = i2;
        Handler handler = this.handler;
        final Callback callback = this.callback;
        Objects.requireNonNull(callback);
        handler.post(new Runnable() { // from class: androidx.media3.exoplayer.hls.f
            @Override // java.lang.Runnable
            public final void run() {
                callback.onPrepared();
            }
        });
        setIsPrepared();
    }

    public int readData(int i2, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i3) {
        if (isPendingReset()) {
            return -3;
        }
        int i4 = 0;
        if (!this.mediaChunks.isEmpty()) {
            int i5 = 0;
            while (i5 < this.mediaChunks.size() - 1 && finishedReadingChunk(this.mediaChunks.get(i5))) {
                i5++;
            }
            Util.removeRange(this.mediaChunks, 0, i5);
            HlsMediaChunk hlsMediaChunk = this.mediaChunks.get(0);
            Format format = hlsMediaChunk.trackFormat;
            if (!format.equals(this.downstreamTrackFormat)) {
                this.mediaSourceEventDispatcher.downstreamFormatChanged(this.trackType, format, hlsMediaChunk.trackSelectionReason, hlsMediaChunk.trackSelectionData, hlsMediaChunk.startTimeUs);
            }
            this.downstreamTrackFormat = format;
        }
        if (!this.mediaChunks.isEmpty() && !this.mediaChunks.get(0).isPublished()) {
            return -3;
        }
        int i6 = this.sampleQueues[i2].read(formatHolder, decoderInputBuffer, i3, this.loadingFinished);
        if (i6 == -5) {
            Format formatWithManifestFormatInfo = (Format) Assertions.checkNotNull(formatHolder.format);
            if (i2 == this.primarySampleQueueIndex) {
                int iCheckedCast = Ints.checkedCast(this.sampleQueues[i2].peekSourceId());
                while (i4 < this.mediaChunks.size() && this.mediaChunks.get(i4).uid != iCheckedCast) {
                    i4++;
                }
                formatWithManifestFormatInfo = formatWithManifestFormatInfo.withManifestFormatInfo(i4 < this.mediaChunks.size() ? this.mediaChunks.get(i4).trackFormat : (Format) Assertions.checkNotNull(this.upstreamTrackFormat));
            }
            formatHolder.format = formatWithManifestFormatInfo;
        }
        return i6;
    }

    @Override // androidx.media3.exoplayer.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
        if (this.loader.hasFatalError() || isPendingReset()) {
            return;
        }
        if (this.loader.isLoading()) {
            Assertions.checkNotNull(this.loadingChunk);
            if (this.chunkSource.shouldCancelLoad(j2, this.loadingChunk, this.readOnlyMediaChunks)) {
                this.loader.cancelLoading();
                return;
            }
            return;
        }
        int size = this.readOnlyMediaChunks.size();
        while (size > 0 && this.chunkSource.getChunkPublicationState(this.readOnlyMediaChunks.get(size - 1)) == 2) {
            size--;
        }
        if (size < this.readOnlyMediaChunks.size()) {
            discardUpstream(size);
        }
        int preferredQueueSize = this.chunkSource.getPreferredQueueSize(j2, this.readOnlyMediaChunks);
        if (preferredQueueSize < this.mediaChunks.size()) {
            discardUpstream(preferredQueueSize);
        }
    }

    public void release() {
        if (this.prepared) {
            for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
                hlsSampleQueue.preRelease();
            }
        }
        this.chunkSource.reset();
        this.loader.release(this);
        this.handler.removeCallbacksAndMessages(null);
        this.released = true;
        this.hlsSampleStreams.clear();
    }

    @Override // androidx.media3.extractor.ExtractorOutput
    public void seekMap(SeekMap seekMap) {
    }

    public boolean seekToUs(long j2, boolean z2) {
        HlsMediaChunk hlsMediaChunk;
        this.lastSeekPositionUs = j2;
        if (isPendingReset()) {
            this.pendingResetPositionUs = j2;
            return true;
        }
        if (this.chunkSource.hasIndependentSegments()) {
            for (int i2 = 0; i2 < this.mediaChunks.size(); i2++) {
                hlsMediaChunk = this.mediaChunks.get(i2);
                if (hlsMediaChunk.startTimeUs == j2) {
                    break;
                }
            }
            hlsMediaChunk = null;
        } else {
            hlsMediaChunk = null;
        }
        if (this.sampleQueuesBuilt && !z2 && seekInsideBufferUs(j2, hlsMediaChunk)) {
            return false;
        }
        this.pendingResetPositionUs = j2;
        this.loadingFinished = false;
        this.mediaChunks.clear();
        if (this.loader.isLoading()) {
            if (this.sampleQueuesBuilt) {
                for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
                    hlsSampleQueue.discardToEnd();
                }
            }
            this.loader.cancelLoading();
        } else {
            this.loader.clearFatalError();
            resetSampleQueues();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0126  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean selectTracks(androidx.media3.exoplayer.trackselection.ExoTrackSelection[] r20, boolean[] r21, androidx.media3.exoplayer.source.SampleStream[] r22, boolean[] r23, long r24, boolean r26) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.hls.HlsSampleStreamWrapper.selectTracks(androidx.media3.exoplayer.trackselection.ExoTrackSelection[], boolean[], androidx.media3.exoplayer.source.SampleStream[], boolean[], long, boolean):boolean");
    }

    public void setDrmInitData(@Nullable DrmInitData drmInitData) {
        if (Util.areEqual(this.drmInitData, drmInitData)) {
            return;
        }
        this.drmInitData = drmInitData;
        int i2 = 0;
        while (true) {
            HlsSampleQueue[] hlsSampleQueueArr = this.sampleQueues;
            if (i2 >= hlsSampleQueueArr.length) {
                return;
            }
            if (this.sampleQueueIsAudioVideoFlags[i2]) {
                hlsSampleQueueArr[i2].setDrmInitData(drmInitData);
            }
            i2++;
        }
    }

    public void setIsPrimaryTimestampSource(boolean z2) {
        this.chunkSource.setIsPrimaryTimestampSource(z2);
    }

    public void setSampleOffsetUs(long j2) {
        if (this.sampleOffsetUs != j2) {
            this.sampleOffsetUs = j2;
            for (HlsSampleQueue hlsSampleQueue : this.sampleQueues) {
                hlsSampleQueue.setSampleOffsetUs(j2);
            }
        }
    }

    public int skipData(int i2, long j2) {
        if (isPendingReset()) {
            return 0;
        }
        HlsSampleQueue hlsSampleQueue = this.sampleQueues[i2];
        int skipCount = hlsSampleQueue.getSkipCount(j2, this.loadingFinished);
        HlsMediaChunk hlsMediaChunk = (HlsMediaChunk) Iterables.getLast(this.mediaChunks, null);
        if (hlsMediaChunk != null && !hlsMediaChunk.isPublished()) {
            skipCount = Math.min(skipCount, hlsMediaChunk.getFirstSampleIndex(i2) - hlsSampleQueue.getReadIndex());
        }
        hlsSampleQueue.skip(skipCount);
        return skipCount;
    }

    @Override // androidx.media3.extractor.ExtractorOutput
    public TrackOutput track(int i2, int i3) {
        TrackOutput trackOutputCreateSampleQueue;
        if (!MAPPABLE_TYPES.contains(Integer.valueOf(i3))) {
            int i4 = 0;
            while (true) {
                TrackOutput[] trackOutputArr = this.sampleQueues;
                if (i4 >= trackOutputArr.length) {
                    trackOutputCreateSampleQueue = null;
                    break;
                }
                if (this.sampleQueueTrackIds[i4] == i2) {
                    trackOutputCreateSampleQueue = trackOutputArr[i4];
                    break;
                }
                i4++;
            }
        } else {
            trackOutputCreateSampleQueue = getMappedTrackOutput(i2, i3);
        }
        if (trackOutputCreateSampleQueue == null) {
            if (this.tracksEnded) {
                return createDiscardingTrackOutput(i2, i3);
            }
            trackOutputCreateSampleQueue = createSampleQueue(i2, i3);
        }
        if (i3 != 5) {
            return trackOutputCreateSampleQueue;
        }
        if (this.emsgUnwrappingTrackOutput == null) {
            this.emsgUnwrappingTrackOutput = new EmsgUnwrappingTrackOutput(trackOutputCreateSampleQueue, this.metadataType);
        }
        return this.emsgUnwrappingTrackOutput;
    }

    public void unbindSampleQueue(int i2) {
        assertIsPrepared();
        Assertions.checkNotNull(this.trackGroupToSampleQueueIndex);
        int i3 = this.trackGroupToSampleQueueIndex[i2];
        Assertions.checkState(this.sampleQueuesEnabledStates[i3]);
        this.sampleQueuesEnabledStates[i3] = false;
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Callback
    public void onLoadCanceled(Chunk chunk, long j2, long j3, boolean z2) {
        this.loadingChunk = null;
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j2, j3, chunk.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(chunk.loadTaskId);
        this.mediaSourceEventDispatcher.loadCanceled(loadEventInfo, chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
        if (z2) {
            return;
        }
        if (isPendingReset() || this.enabledTrackGroupCount == 0) {
            resetSampleQueues();
        }
        if (this.enabledTrackGroupCount > 0) {
            this.callback.onContinueLoadingRequested(this);
        }
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Callback
    public void onLoadCompleted(Chunk chunk, long j2, long j3) {
        this.loadingChunk = null;
        this.chunkSource.onChunkLoadCompleted(chunk);
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j2, j3, chunk.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(chunk.loadTaskId);
        this.mediaSourceEventDispatcher.loadCompleted(loadEventInfo, chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs);
        if (this.prepared) {
            this.callback.onContinueLoadingRequested(this);
        } else {
            continueLoading(new LoadingInfo.Builder().setPlaybackPositionUs(this.lastSeekPositionUs).build());
        }
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Callback
    public Loader.LoadErrorAction onLoadError(Chunk chunk, long j2, long j3, IOException iOException, int i2) {
        Loader.LoadErrorAction loadErrorActionCreateRetryAction;
        int i3;
        boolean zIsMediaChunk = isMediaChunk(chunk);
        if (zIsMediaChunk && !((HlsMediaChunk) chunk).isPublished() && (iOException instanceof HttpDataSource.InvalidResponseCodeException) && ((i3 = ((HttpDataSource.InvalidResponseCodeException) iOException).responseCode) == 410 || i3 == 404)) {
            return Loader.RETRY;
        }
        long jBytesLoaded = chunk.bytesLoaded();
        LoadEventInfo loadEventInfo = new LoadEventInfo(chunk.loadTaskId, chunk.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), j2, j3, jBytesLoaded);
        LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo = new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, Util.usToMs(chunk.startTimeUs), Util.usToMs(chunk.endTimeUs)), iOException, i2);
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = this.loadErrorHandlingPolicy.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.chunkSource.getTrackSelection()), loadErrorInfo);
        boolean zMaybeExcludeTrack = (fallbackSelectionFor == null || fallbackSelectionFor.type != 2) ? false : this.chunkSource.maybeExcludeTrack(chunk, fallbackSelectionFor.exclusionDurationMs);
        if (zMaybeExcludeTrack) {
            if (zIsMediaChunk && jBytesLoaded == 0) {
                ArrayList<HlsMediaChunk> arrayList = this.mediaChunks;
                Assertions.checkState(arrayList.remove(arrayList.size() - 1) == chunk);
                if (this.mediaChunks.isEmpty()) {
                    this.pendingResetPositionUs = this.lastSeekPositionUs;
                } else {
                    ((HlsMediaChunk) Iterables.getLast(this.mediaChunks)).invalidateExtractor();
                }
            }
            loadErrorActionCreateRetryAction = Loader.DONT_RETRY;
        } else {
            long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(loadErrorInfo);
            loadErrorActionCreateRetryAction = retryDelayMsFor != C.TIME_UNSET ? Loader.createRetryAction(false, retryDelayMsFor) : Loader.DONT_RETRY_FATAL;
        }
        Loader.LoadErrorAction loadErrorAction = loadErrorActionCreateRetryAction;
        boolean z2 = !loadErrorAction.isRetry();
        this.mediaSourceEventDispatcher.loadError(loadEventInfo, chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, iOException, z2);
        if (z2) {
            this.loadingChunk = null;
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(chunk.loadTaskId);
        }
        if (zMaybeExcludeTrack) {
            if (this.prepared) {
                this.callback.onContinueLoadingRequested(this);
            } else {
                continueLoading(new LoadingInfo.Builder().setPlaybackPositionUs(this.lastSeekPositionUs).build());
            }
        }
        return loadErrorAction;
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError();
        this.chunkSource.maybeThrowError();
    }
}
