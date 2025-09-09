package androidx.media3.exoplayer.smoothstreaming;

import android.net.Uri;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.UriUtil;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.smoothstreaming.SsChunkSource;
import androidx.media3.exoplayer.smoothstreaming.manifest.SsManifest;
import androidx.media3.exoplayer.source.BehindLiveWindowException;
import androidx.media3.exoplayer.source.chunk.BaseMediaChunkIterator;
import androidx.media3.exoplayer.source.chunk.BundledChunkExtractor;
import androidx.media3.exoplayer.source.chunk.Chunk;
import androidx.media3.exoplayer.source.chunk.ChunkExtractor;
import androidx.media3.exoplayer.source.chunk.ChunkHolder;
import androidx.media3.exoplayer.source.chunk.ContainerMediaChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunkIterator;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.trackselection.TrackSelectionUtil;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import androidx.media3.exoplayer.upstream.CmcdData;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import androidx.media3.exoplayer.upstream.LoaderErrorThrower;
import androidx.media3.extractor.mp4.FragmentedMp4Extractor;
import androidx.media3.extractor.mp4.Track;
import androidx.media3.extractor.mp4.TrackEncryptionBox;
import androidx.media3.extractor.text.DefaultSubtitleParserFactory;
import androidx.media3.extractor.text.SubtitleParser;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public class DefaultSsChunkSource implements SsChunkSource {
    private final ChunkExtractor[] chunkExtractors;

    @Nullable
    private final CmcdConfiguration cmcdConfiguration;
    private int currentManifestChunkOffset;
    private final DataSource dataSource;

    @Nullable
    private IOException fatalError;
    private long lastChunkRequestRealtimeMs = C.TIME_UNSET;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int streamElementIndex;
    private ExoTrackSelection trackSelection;

    public static final class Factory implements SsChunkSource.Factory {
        private final DataSource.Factory dataSourceFactory;
        private boolean parseSubtitlesDuringExtraction;
        private SubtitleParser.Factory subtitleParserFactory = new DefaultSubtitleParserFactory();

        public Factory(DataSource.Factory factory) {
            this.dataSourceFactory = factory;
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.SsChunkSource.Factory
        public SsChunkSource createChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i2, ExoTrackSelection exoTrackSelection, @Nullable TransferListener transferListener, @Nullable CmcdConfiguration cmcdConfiguration) {
            DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSourceCreateDataSource.addTransferListener(transferListener);
            }
            return new DefaultSsChunkSource(loaderErrorThrower, ssManifest, i2, exoTrackSelection, dataSourceCreateDataSource, cmcdConfiguration, this.subtitleParserFactory, this.parseSubtitlesDuringExtraction);
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.SsChunkSource.Factory
        public Format getOutputTextFormat(Format format) {
            String str;
            if (!this.parseSubtitlesDuringExtraction || !this.subtitleParserFactory.supportsFormat(format)) {
                return format;
            }
            Format.Builder cueReplacementBehavior = format.buildUpon().setSampleMimeType(MimeTypes.APPLICATION_MEDIA3_CUES).setCueReplacementBehavior(this.subtitleParserFactory.getCueReplacementBehavior(format));
            StringBuilder sb = new StringBuilder();
            sb.append(format.sampleMimeType);
            if (format.codecs != null) {
                str = " " + format.codecs;
            } else {
                str = "";
            }
            sb.append(str);
            return cueReplacementBehavior.setCodecs(sb.toString()).setSubsampleOffsetUs(Long.MAX_VALUE).build();
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.SsChunkSource.Factory
        @CanIgnoreReturnValue
        public Factory experimentalParseSubtitlesDuringExtraction(boolean z2) {
            this.parseSubtitlesDuringExtraction = z2;
            return this;
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.SsChunkSource.Factory
        @CanIgnoreReturnValue
        public Factory setSubtitleParserFactory(SubtitleParser.Factory factory) {
            this.subtitleParserFactory = factory;
            return this;
        }
    }

    private static final class StreamElementIterator extends BaseMediaChunkIterator {
        private final SsManifest.StreamElement streamElement;
        private final int trackIndex;

        public StreamElementIterator(SsManifest.StreamElement streamElement, int i2, int i3) {
            super(i3, streamElement.chunkCount - 1);
            this.streamElement = streamElement;
            this.trackIndex = i2;
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            return getChunkStartTimeUs() + this.streamElement.getChunkDurationUs((int) b());
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            a();
            return this.streamElement.getStartTimeUs((int) b());
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            a();
            return new DataSpec(this.streamElement.buildRequestUri(this.trackIndex, (int) b()));
        }
    }

    public DefaultSsChunkSource(LoaderErrorThrower loaderErrorThrower, SsManifest ssManifest, int i2, ExoTrackSelection exoTrackSelection, DataSource dataSource, @Nullable CmcdConfiguration cmcdConfiguration, SubtitleParser.Factory factory, boolean z2) {
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = ssManifest;
        this.streamElementIndex = i2;
        this.trackSelection = exoTrackSelection;
        this.dataSource = dataSource;
        this.cmcdConfiguration = cmcdConfiguration;
        SsManifest.StreamElement streamElement = ssManifest.streamElements[i2];
        this.chunkExtractors = new ChunkExtractor[exoTrackSelection.length()];
        for (int i3 = 0; i3 < this.chunkExtractors.length; i3++) {
            int indexInTrackGroup = exoTrackSelection.getIndexInTrackGroup(i3);
            Format format = streamElement.formats[indexInTrackGroup];
            TrackEncryptionBox[] trackEncryptionBoxArr = format.drmInitData != null ? ((SsManifest.ProtectionElement) Assertions.checkNotNull(ssManifest.protectionElement)).trackEncryptionBoxes : null;
            int i4 = streamElement.type;
            this.chunkExtractors[i3] = new BundledChunkExtractor(new FragmentedMp4Extractor(factory, !z2 ? 35 : 3, null, new Track(indexInTrackGroup, i4, streamElement.timescale, C.TIME_UNSET, ssManifest.durationUs, format, 0, trackEncryptionBoxArr, i4 == 2 ? 4 : 0, null, null), ImmutableList.of(), null), streamElement.type, format);
        }
    }

    private static MediaChunk newMediaChunk(Format format, DataSource dataSource, Uri uri, int i2, long j2, long j3, long j4, int i3, @Nullable Object obj, ChunkExtractor chunkExtractor, @Nullable CmcdData.Factory factory) {
        DataSpec dataSpecBuild = new DataSpec.Builder().setUri(uri).build();
        if (factory != null) {
            dataSpecBuild = factory.createCmcdData().addToDataSpec(dataSpecBuild);
        }
        return new ContainerMediaChunk(dataSource, dataSpecBuild, format, i3, obj, j2, j3, j4, C.TIME_UNSET, i2, 1, j2, chunkExtractor);
    }

    private long resolveTimeToLiveEdgeUs(long j2) {
        SsManifest ssManifest = this.manifest;
        if (!ssManifest.isLive) {
            return C.TIME_UNSET;
        }
        SsManifest.StreamElement streamElement = ssManifest.streamElements[this.streamElementIndex];
        int i2 = streamElement.chunkCount - 1;
        return (streamElement.getStartTimeUs(i2) + streamElement.getChunkDurationUs(i2)) - j2;
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int chunkIndex = streamElement.getChunkIndex(j2);
        long startTimeUs = streamElement.getStartTimeUs(chunkIndex);
        return seekParameters.resolveSeekPositionUs(j2, startTimeUs, (startTimeUs >= j2 || chunkIndex >= streamElement.chunkCount + (-1)) ? startTimeUs : streamElement.getStartTimeUs(chunkIndex + 1));
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public final void getNextChunk(LoadingInfo loadingInfo, long j2, List<? extends MediaChunk> list, ChunkHolder chunkHolder) {
        int nextChunkIndex;
        CmcdData.Factory objectType;
        if (this.fatalError != null) {
            return;
        }
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        if (streamElement.chunkCount == 0) {
            chunkHolder.endOfStream = !r5.isLive;
            return;
        }
        if (list.isEmpty()) {
            nextChunkIndex = streamElement.getChunkIndex(j2);
        } else {
            nextChunkIndex = (int) (list.get(list.size() - 1).getNextChunkIndex() - this.currentManifestChunkOffset);
            if (nextChunkIndex < 0) {
                this.fatalError = new BehindLiveWindowException();
                return;
            }
        }
        if (nextChunkIndex >= streamElement.chunkCount) {
            chunkHolder.endOfStream = !this.manifest.isLive;
            return;
        }
        long j3 = loadingInfo.playbackPositionUs;
        long j4 = j2 - j3;
        long jResolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(j3);
        int length = this.trackSelection.length();
        MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[length];
        for (int i2 = 0; i2 < length; i2++) {
            mediaChunkIteratorArr[i2] = new StreamElementIterator(streamElement, this.trackSelection.getIndexInTrackGroup(i2), nextChunkIndex);
        }
        this.trackSelection.updateSelectedTrack(j3, j4, jResolveTimeToLiveEdgeUs, list, mediaChunkIteratorArr);
        long startTimeUs = streamElement.getStartTimeUs(nextChunkIndex);
        long chunkDurationUs = startTimeUs + streamElement.getChunkDurationUs(nextChunkIndex);
        long j5 = list.isEmpty() ? j2 : -9223372036854775807L;
        int i3 = nextChunkIndex + this.currentManifestChunkOffset;
        int selectedIndex = this.trackSelection.getSelectedIndex();
        ChunkExtractor chunkExtractor = this.chunkExtractors[selectedIndex];
        int indexInTrackGroup = this.trackSelection.getIndexInTrackGroup(selectedIndex);
        Uri uriBuildRequestUri = streamElement.buildRequestUri(indexInTrackGroup, nextChunkIndex);
        if (this.cmcdConfiguration != null) {
            objectType = new CmcdData.Factory(this.cmcdConfiguration, this.trackSelection, Math.max(0L, j4), loadingInfo.playbackSpeed, "s", this.manifest.isLive, loadingInfo.rebufferedSince(this.lastChunkRequestRealtimeMs), list.isEmpty()).setChunkDurationUs(chunkDurationUs - startTimeUs).setObjectType(CmcdData.Factory.getObjectType(this.trackSelection));
            int i4 = nextChunkIndex + 1;
            if (i4 < streamElement.chunkCount) {
                objectType.setNextObjectRequest(UriUtil.getRelativePath(uriBuildRequestUri, streamElement.buildRequestUri(indexInTrackGroup, i4)));
            }
        } else {
            objectType = null;
        }
        CmcdData.Factory factory = objectType;
        this.lastChunkRequestRealtimeMs = SystemClock.elapsedRealtime();
        chunkHolder.chunk = newMediaChunk(this.trackSelection.getSelectedFormat(), this.dataSource, uriBuildRequestUri, i3, startTimeUs, chunkDurationUs, j5, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), chunkExtractor, factory);
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public int getPreferredQueueSize(long j2, List<? extends MediaChunk> list) {
        return (this.fatalError != null || this.trackSelection.length() < 2) ? list.size() : this.trackSelection.evaluateQueueSize(j2, list);
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException != null) {
            throw iOException;
        }
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public void onChunkLoadCompleted(Chunk chunk) {
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean z2, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(TrackSelectionUtil.createFallbackOptions(this.trackSelection), loadErrorInfo);
        if (z2 && fallbackSelectionFor != null && fallbackSelectionFor.type == 2) {
            ExoTrackSelection exoTrackSelection = this.trackSelection;
            if (exoTrackSelection.excludeTrack(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs)) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public void release() {
        for (ChunkExtractor chunkExtractor : this.chunkExtractors) {
            chunkExtractor.release();
        }
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long j2, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(j2, chunk, list);
    }

    @Override // androidx.media3.exoplayer.smoothstreaming.SsChunkSource
    public void updateManifest(SsManifest ssManifest) {
        SsManifest.StreamElement[] streamElementArr = this.manifest.streamElements;
        int i2 = this.streamElementIndex;
        SsManifest.StreamElement streamElement = streamElementArr[i2];
        int i3 = streamElement.chunkCount;
        SsManifest.StreamElement streamElement2 = ssManifest.streamElements[i2];
        if (i3 == 0 || streamElement2.chunkCount == 0) {
            this.currentManifestChunkOffset += i3;
        } else {
            int i4 = i3 - 1;
            long startTimeUs = streamElement.getStartTimeUs(i4) + streamElement.getChunkDurationUs(i4);
            long startTimeUs2 = streamElement2.getStartTimeUs(0);
            if (startTimeUs <= startTimeUs2) {
                this.currentManifestChunkOffset += i3;
            } else {
                this.currentManifestChunkOffset += streamElement.getChunkIndex(startTimeUs2);
            }
        }
        this.manifest = ssManifest;
    }

    @Override // androidx.media3.exoplayer.smoothstreaming.SsChunkSource
    public void updateTrackSelection(ExoTrackSelection exoTrackSelection) {
        this.trackSelection = exoTrackSelection;
    }
}
