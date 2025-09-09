package androidx.media3.exoplayer.hls;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UriUtil;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.hls.playlist.HlsMediaPlaylist;
import androidx.media3.exoplayer.hls.playlist.HlsPlaylistTracker;
import androidx.media3.exoplayer.source.BehindLiveWindowException;
import androidx.media3.exoplayer.source.chunk.BaseMediaChunkIterator;
import androidx.media3.exoplayer.source.chunk.Chunk;
import androidx.media3.exoplayer.source.chunk.DataChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunkIterator;
import androidx.media3.exoplayer.trackselection.BaseTrackSelection;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import androidx.media3.exoplayer.upstream.CmcdData;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.primitives.Ints;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
class HlsChunkSource {
    public static final int CHUNK_PUBLICATION_STATE_PRELOAD = 0;
    public static final int CHUNK_PUBLICATION_STATE_PUBLISHED = 1;
    public static final int CHUNK_PUBLICATION_STATE_REMOVED = 2;
    private static final int KEY_CACHE_SIZE = 4;

    @Nullable
    private final CmcdConfiguration cmcdConfiguration;
    private final DataSource encryptionDataSource;

    @Nullable
    private Uri expectedPlaylistUrl;
    private final HlsExtractorFactory extractorFactory;

    @Nullable
    private IOException fatalError;
    private boolean independentSegments;
    private boolean isPrimaryTimestampSource;
    private final DataSource mediaDataSource;

    @Nullable
    private final List<Format> muxedCaptionFormats;
    private final PlayerId playerId;
    private final Format[] playlistFormats;
    private final HlsPlaylistTracker playlistTracker;
    private final Uri[] playlistUrls;
    private boolean seenExpectedPlaylistError;
    private final long timestampAdjusterInitializationTimeoutMs;
    private final TimestampAdjusterProvider timestampAdjusterProvider;
    private final TrackGroup trackGroup;
    private ExoTrackSelection trackSelection;
    private long lastChunkRequestRealtimeMs = C.TIME_UNSET;
    private final FullSegmentEncryptionKeyCache keyCache = new FullSegmentEncryptionKeyCache(4);
    private byte[] scratchSpace = Util.EMPTY_BYTE_ARRAY;
    private long liveEdgeInPeriodTimeUs = C.TIME_UNSET;

    private static final class EncryptionKeyChunk extends DataChunk {
        private byte[] result;

        public EncryptionKeyChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i2, @Nullable Object obj, byte[] bArr) {
            super(dataSource, dataSpec, 3, format, i2, obj, bArr);
        }

        @Override // androidx.media3.exoplayer.source.chunk.DataChunk
        protected void a(byte[] bArr, int i2) {
            this.result = Arrays.copyOf(bArr, i2);
        }

        @Nullable
        public byte[] getResult() {
            return this.result;
        }
    }

    public static final class HlsChunkHolder {

        @Nullable
        public Chunk chunk;
        public boolean endOfStream;

        @Nullable
        public Uri playlistUrl;

        public HlsChunkHolder() {
            clear();
        }

        public void clear() {
            this.chunk = null;
            this.endOfStream = false;
            this.playlistUrl = null;
        }
    }

    @VisibleForTesting
    static final class HlsMediaPlaylistSegmentIterator extends BaseMediaChunkIterator {
        private final String playlistBaseUri;
        private final List<HlsMediaPlaylist.SegmentBase> segmentBases;
        private final long startOfPlaylistInPeriodUs;

        public HlsMediaPlaylistSegmentIterator(String str, long j2, List<HlsMediaPlaylist.SegmentBase> list) {
            super(0L, list.size() - 1);
            this.playlistBaseUri = str;
            this.startOfPlaylistInPeriodUs = j2;
            this.segmentBases = list;
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            a();
            HlsMediaPlaylist.SegmentBase segmentBase = this.segmentBases.get((int) b());
            return this.startOfPlaylistInPeriodUs + segmentBase.relativeStartTimeUs + segmentBase.durationUs;
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            a();
            return this.startOfPlaylistInPeriodUs + this.segmentBases.get((int) b()).relativeStartTimeUs;
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            a();
            HlsMediaPlaylist.SegmentBase segmentBase = this.segmentBases.get((int) b());
            return new DataSpec(UriUtil.resolveToUri(this.playlistBaseUri, segmentBase.url), segmentBase.byteRangeOffset, segmentBase.byteRangeLength);
        }
    }

    private static final class InitializationTrackSelection extends BaseTrackSelection {
        private int selectedIndex;

        public InitializationTrackSelection(TrackGroup trackGroup, int[] iArr) {
            super(trackGroup, iArr);
            this.selectedIndex = indexOf(trackGroup.getFormat(iArr[0]));
        }

        @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
        public int getSelectedIndex() {
            return this.selectedIndex;
        }

        @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
        @Nullable
        public Object getSelectionData() {
            return null;
        }

        @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
        public int getSelectionReason() {
            return 0;
        }

        @Override // androidx.media3.exoplayer.trackselection.ExoTrackSelection
        public void updateSelectedTrack(long j2, long j3, long j4, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIteratorArr) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (isTrackExcluded(this.selectedIndex, jElapsedRealtime)) {
                for (int i2 = this.f5504b - 1; i2 >= 0; i2--) {
                    if (!isTrackExcluded(i2, jElapsedRealtime)) {
                        this.selectedIndex = i2;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }
    }

    static final class SegmentBaseHolder {
        public final boolean isPreload;
        public final long mediaSequence;
        public final int partIndex;
        public final HlsMediaPlaylist.SegmentBase segmentBase;

        public SegmentBaseHolder(HlsMediaPlaylist.SegmentBase segmentBase, long j2, int i2) {
            this.segmentBase = segmentBase;
            this.mediaSequence = j2;
            this.partIndex = i2;
            this.isPreload = (segmentBase instanceof HlsMediaPlaylist.Part) && ((HlsMediaPlaylist.Part) segmentBase).isPreload;
        }
    }

    public HlsChunkSource(HlsExtractorFactory hlsExtractorFactory, HlsPlaylistTracker hlsPlaylistTracker, Uri[] uriArr, Format[] formatArr, HlsDataSourceFactory hlsDataSourceFactory, @Nullable TransferListener transferListener, TimestampAdjusterProvider timestampAdjusterProvider, long j2, @Nullable List<Format> list, PlayerId playerId, @Nullable CmcdConfiguration cmcdConfiguration) {
        this.extractorFactory = hlsExtractorFactory;
        this.playlistTracker = hlsPlaylistTracker;
        this.playlistUrls = uriArr;
        this.playlistFormats = formatArr;
        this.timestampAdjusterProvider = timestampAdjusterProvider;
        this.timestampAdjusterInitializationTimeoutMs = j2;
        this.muxedCaptionFormats = list;
        this.playerId = playerId;
        this.cmcdConfiguration = cmcdConfiguration;
        DataSource dataSourceCreateDataSource = hlsDataSourceFactory.createDataSource(1);
        this.mediaDataSource = dataSourceCreateDataSource;
        if (transferListener != null) {
            dataSourceCreateDataSource.addTransferListener(transferListener);
        }
        this.encryptionDataSource = hlsDataSourceFactory.createDataSource(3);
        this.trackGroup = new TrackGroup(formatArr);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < uriArr.length; i2++) {
            if ((formatArr[i2].roleFlags & 16384) == 0) {
                arrayList.add(Integer.valueOf(i2));
            }
        }
        this.trackSelection = new InitializationTrackSelection(this.trackGroup, Ints.toArray(arrayList));
    }

    static List a(HlsMediaPlaylist hlsMediaPlaylist, long j2, int i2) {
        int i3 = (int) (j2 - hlsMediaPlaylist.mediaSequence);
        if (i3 < 0 || hlsMediaPlaylist.segments.size() < i3) {
            return ImmutableList.of();
        }
        ArrayList arrayList = new ArrayList();
        if (i3 < hlsMediaPlaylist.segments.size()) {
            if (i2 != -1) {
                HlsMediaPlaylist.Segment segment = hlsMediaPlaylist.segments.get(i3);
                if (i2 == 0) {
                    arrayList.add(segment);
                } else if (i2 < segment.parts.size()) {
                    List<HlsMediaPlaylist.Part> list = segment.parts;
                    arrayList.addAll(list.subList(i2, list.size()));
                }
                i3++;
            }
            List<HlsMediaPlaylist.Segment> list2 = hlsMediaPlaylist.segments;
            arrayList.addAll(list2.subList(i3, list2.size()));
            i2 = 0;
        }
        if (hlsMediaPlaylist.partTargetDurationUs != C.TIME_UNSET) {
            int i4 = i2 != -1 ? i2 : 0;
            if (i4 < hlsMediaPlaylist.trailingParts.size()) {
                List<HlsMediaPlaylist.Part> list3 = hlsMediaPlaylist.trailingParts;
                arrayList.addAll(list3.subList(i4, list3.size()));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    private void deactivatePlaylistForSelectedTrack() {
        this.playlistTracker.deactivatePlaylistForPlayback(this.playlistUrls[this.trackSelection.getSelectedIndexInTrackGroup()]);
    }

    @Nullable
    private static Uri getFullEncryptionKeyUri(HlsMediaPlaylist hlsMediaPlaylist, @Nullable HlsMediaPlaylist.SegmentBase segmentBase) {
        String str;
        if (segmentBase == null || (str = segmentBase.fullSegmentEncryptionKeyUri) == null) {
            return null;
        }
        return UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, str);
    }

    private boolean getIsMuxedAudioAndVideo() {
        Format format = this.trackGroup.getFormat(this.trackSelection.getSelectedIndex());
        return (MimeTypes.getAudioMediaMimeType(format.codecs) == null || MimeTypes.getVideoMediaMimeType(format.codecs) == null) ? false : true;
    }

    private Pair<Long, Integer> getNextMediaSequenceAndPartIndex(@Nullable HlsMediaChunk hlsMediaChunk, boolean z2, HlsMediaPlaylist hlsMediaPlaylist, long j2, long j3) {
        if (hlsMediaChunk != null && !z2) {
            if (!hlsMediaChunk.isLoadCompleted()) {
                return new Pair<>(Long.valueOf(hlsMediaChunk.chunkIndex), Integer.valueOf(hlsMediaChunk.partIndex));
            }
            Long lValueOf = Long.valueOf(hlsMediaChunk.partIndex == -1 ? hlsMediaChunk.getNextChunkIndex() : hlsMediaChunk.chunkIndex);
            int i2 = hlsMediaChunk.partIndex;
            return new Pair<>(lValueOf, Integer.valueOf(i2 != -1 ? i2 + 1 : -1));
        }
        long j4 = hlsMediaPlaylist.durationUs + j2;
        if (hlsMediaChunk != null && !this.independentSegments) {
            j3 = hlsMediaChunk.startTimeUs;
        }
        if (!hlsMediaPlaylist.hasEndTag && j3 >= j4) {
            return new Pair<>(Long.valueOf(hlsMediaPlaylist.mediaSequence + hlsMediaPlaylist.segments.size()), -1);
        }
        long j5 = j3 - j2;
        int i3 = 0;
        int iBinarySearchFloor = Util.binarySearchFloor((List<? extends Comparable<? super Long>>) hlsMediaPlaylist.segments, Long.valueOf(j5), true, !this.playlistTracker.isLive() || hlsMediaChunk == null);
        long j6 = iBinarySearchFloor + hlsMediaPlaylist.mediaSequence;
        if (iBinarySearchFloor >= 0) {
            HlsMediaPlaylist.Segment segment = hlsMediaPlaylist.segments.get(iBinarySearchFloor);
            List<HlsMediaPlaylist.Part> list = j5 < segment.relativeStartTimeUs + segment.durationUs ? segment.parts : hlsMediaPlaylist.trailingParts;
            while (true) {
                if (i3 >= list.size()) {
                    break;
                }
                HlsMediaPlaylist.Part part = list.get(i3);
                if (j5 >= part.relativeStartTimeUs + part.durationUs) {
                    i3++;
                } else if (part.isIndependent) {
                    j6 += list == hlsMediaPlaylist.trailingParts ? 1L : 0L;
                    i = i3;
                }
            }
        }
        return new Pair<>(Long.valueOf(j6), Integer.valueOf(i));
    }

    @Nullable
    private static SegmentBaseHolder getNextSegmentHolder(HlsMediaPlaylist hlsMediaPlaylist, long j2, int i2) {
        int i3 = (int) (j2 - hlsMediaPlaylist.mediaSequence);
        if (i3 == hlsMediaPlaylist.segments.size()) {
            if (i2 == -1) {
                i2 = 0;
            }
            if (i2 < hlsMediaPlaylist.trailingParts.size()) {
                return new SegmentBaseHolder(hlsMediaPlaylist.trailingParts.get(i2), j2, i2);
            }
            return null;
        }
        HlsMediaPlaylist.Segment segment = hlsMediaPlaylist.segments.get(i3);
        if (i2 == -1) {
            return new SegmentBaseHolder(segment, j2, -1);
        }
        if (i2 < segment.parts.size()) {
            return new SegmentBaseHolder(segment.parts.get(i2), j2, i2);
        }
        int i4 = i3 + 1;
        if (i4 < hlsMediaPlaylist.segments.size()) {
            return new SegmentBaseHolder(hlsMediaPlaylist.segments.get(i4), j2 + 1, -1);
        }
        if (hlsMediaPlaylist.trailingParts.isEmpty()) {
            return null;
        }
        return new SegmentBaseHolder(hlsMediaPlaylist.trailingParts.get(0), j2 + 1, 0);
    }

    @Nullable
    private Chunk maybeCreateEncryptionChunkFor(@Nullable Uri uri, int i2, boolean z2, @Nullable CmcdData.Factory factory) {
        if (uri == null) {
            return null;
        }
        byte[] bArrRemove = this.keyCache.remove(uri);
        if (bArrRemove != null) {
            this.keyCache.put(uri, bArrRemove);
            return null;
        }
        DataSpec dataSpecBuild = new DataSpec.Builder().setUri(uri).setFlags(1).build();
        if (factory != null) {
            if (z2) {
                factory.setObjectType("i");
            }
            dataSpecBuild = factory.createCmcdData().addToDataSpec(dataSpecBuild);
        }
        return new EncryptionKeyChunk(this.encryptionDataSource, dataSpecBuild, this.playlistFormats[i2], this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.scratchSpace);
    }

    private long resolveTimeToLiveEdgeUs(long j2) {
        long j3 = this.liveEdgeInPeriodTimeUs;
        return j3 != C.TIME_UNSET ? j3 - j2 : C.TIME_UNSET;
    }

    private void updateLiveEdgeTimeUs(HlsMediaPlaylist hlsMediaPlaylist) {
        this.liveEdgeInPeriodTimeUs = hlsMediaPlaylist.hasEndTag ? C.TIME_UNSET : hlsMediaPlaylist.getEndTimeUs() - this.playlistTracker.getInitialStartTimeUs();
    }

    public MediaChunkIterator[] createMediaChunkIterators(@Nullable HlsMediaChunk hlsMediaChunk, long j2) {
        int i2;
        int iIndexOf = hlsMediaChunk == null ? -1 : this.trackGroup.indexOf(hlsMediaChunk.trackFormat);
        int length = this.trackSelection.length();
        MediaChunkIterator[] mediaChunkIteratorArr = new MediaChunkIterator[length];
        boolean z2 = false;
        int i3 = 0;
        while (i3 < length) {
            int indexInTrackGroup = this.trackSelection.getIndexInTrackGroup(i3);
            Uri uri = this.playlistUrls[indexInTrackGroup];
            if (this.playlistTracker.isSnapshotValid(uri)) {
                HlsMediaPlaylist playlistSnapshot = this.playlistTracker.getPlaylistSnapshot(uri, z2);
                Assertions.checkNotNull(playlistSnapshot);
                long initialStartTimeUs = playlistSnapshot.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
                i2 = i3;
                Pair<Long, Integer> nextMediaSequenceAndPartIndex = getNextMediaSequenceAndPartIndex(hlsMediaChunk, indexInTrackGroup != iIndexOf ? true : z2, playlistSnapshot, initialStartTimeUs, j2);
                mediaChunkIteratorArr[i2] = new HlsMediaPlaylistSegmentIterator(playlistSnapshot.baseUri, initialStartTimeUs, a(playlistSnapshot, ((Long) nextMediaSequenceAndPartIndex.first).longValue(), ((Integer) nextMediaSequenceAndPartIndex.second).intValue()));
            } else {
                mediaChunkIteratorArr[i3] = MediaChunkIterator.EMPTY;
                i2 = i3;
            }
            i3 = i2 + 1;
            z2 = false;
        }
        return mediaChunkIteratorArr;
    }

    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        int selectedIndex = this.trackSelection.getSelectedIndex();
        Uri[] uriArr = this.playlistUrls;
        HlsMediaPlaylist playlistSnapshot = (selectedIndex >= uriArr.length || selectedIndex == -1) ? null : this.playlistTracker.getPlaylistSnapshot(uriArr[this.trackSelection.getSelectedIndexInTrackGroup()], true);
        if (playlistSnapshot == null || playlistSnapshot.segments.isEmpty() || !playlistSnapshot.hasIndependentSegments) {
            return j2;
        }
        long initialStartTimeUs = playlistSnapshot.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
        long j3 = j2 - initialStartTimeUs;
        int iBinarySearchFloor = Util.binarySearchFloor((List<? extends Comparable<? super Long>>) playlistSnapshot.segments, Long.valueOf(j3), true, true);
        long j4 = playlistSnapshot.segments.get(iBinarySearchFloor).relativeStartTimeUs;
        return seekParameters.resolveSeekPositionUs(j3, j4, iBinarySearchFloor != playlistSnapshot.segments.size() - 1 ? playlistSnapshot.segments.get(iBinarySearchFloor + 1).relativeStartTimeUs : j4) + initialStartTimeUs;
    }

    public int getChunkPublicationState(HlsMediaChunk hlsMediaChunk) {
        if (hlsMediaChunk.partIndex == -1) {
            return 1;
        }
        HlsMediaPlaylist hlsMediaPlaylist = (HlsMediaPlaylist) Assertions.checkNotNull(this.playlistTracker.getPlaylistSnapshot(this.playlistUrls[this.trackGroup.indexOf(hlsMediaChunk.trackFormat)], false));
        int i2 = (int) (hlsMediaChunk.chunkIndex - hlsMediaPlaylist.mediaSequence);
        if (i2 < 0) {
            return 1;
        }
        List<HlsMediaPlaylist.Part> list = i2 < hlsMediaPlaylist.segments.size() ? hlsMediaPlaylist.segments.get(i2).parts : hlsMediaPlaylist.trailingParts;
        if (hlsMediaChunk.partIndex >= list.size()) {
            return 2;
        }
        HlsMediaPlaylist.Part part = list.get(hlsMediaChunk.partIndex);
        if (part.isPreload) {
            return 0;
        }
        return Util.areEqual(Uri.parse(UriUtil.resolve(hlsMediaPlaylist.baseUri, part.url)), hlsMediaChunk.dataSpec.uri) ? 1 : 2;
    }

    public void getNextChunk(LoadingInfo loadingInfo, long j2, List<HlsMediaChunk> list, boolean z2, HlsChunkHolder hlsChunkHolder) {
        HlsMediaPlaylist hlsMediaPlaylist;
        int i2;
        long initialStartTimeUs;
        Uri uri;
        CmcdData.Factory objectType;
        HlsMediaChunk hlsMediaChunk = list.isEmpty() ? null : (HlsMediaChunk) Iterables.getLast(list);
        int iIndexOf = hlsMediaChunk == null ? -1 : this.trackGroup.indexOf(hlsMediaChunk.trackFormat);
        long j3 = loadingInfo.playbackPositionUs;
        long jMax = j2 - j3;
        long jResolveTimeToLiveEdgeUs = resolveTimeToLiveEdgeUs(j3);
        if (hlsMediaChunk != null && !this.independentSegments) {
            long durationUs = hlsMediaChunk.getDurationUs();
            jMax = Math.max(0L, jMax - durationUs);
            if (jResolveTimeToLiveEdgeUs != C.TIME_UNSET) {
                jResolveTimeToLiveEdgeUs = Math.max(0L, jResolveTimeToLiveEdgeUs - durationUs);
            }
        }
        long j4 = jResolveTimeToLiveEdgeUs;
        long j5 = jMax;
        this.trackSelection.updateSelectedTrack(j3, j5, j4, list, createMediaChunkIterators(hlsMediaChunk, j2));
        int selectedIndexInTrackGroup = this.trackSelection.getSelectedIndexInTrackGroup();
        boolean z3 = iIndexOf != selectedIndexInTrackGroup;
        Uri uri2 = this.playlistUrls[selectedIndexInTrackGroup];
        if (!this.playlistTracker.isSnapshotValid(uri2)) {
            hlsChunkHolder.playlistUrl = uri2;
            this.seenExpectedPlaylistError &= uri2.equals(this.expectedPlaylistUrl);
            this.expectedPlaylistUrl = uri2;
            return;
        }
        HlsMediaPlaylist playlistSnapshot = this.playlistTracker.getPlaylistSnapshot(uri2, true);
        Assertions.checkNotNull(playlistSnapshot);
        this.independentSegments = playlistSnapshot.hasIndependentSegments;
        updateLiveEdgeTimeUs(playlistSnapshot);
        long initialStartTimeUs2 = playlistSnapshot.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
        int i3 = iIndexOf;
        Pair<Long, Integer> nextMediaSequenceAndPartIndex = getNextMediaSequenceAndPartIndex(hlsMediaChunk, z3, playlistSnapshot, initialStartTimeUs2, j2);
        long jLongValue = ((Long) nextMediaSequenceAndPartIndex.first).longValue();
        int iIntValue = ((Integer) nextMediaSequenceAndPartIndex.second).intValue();
        if (jLongValue >= playlistSnapshot.mediaSequence || hlsMediaChunk == null || !z3) {
            hlsMediaPlaylist = playlistSnapshot;
            i2 = selectedIndexInTrackGroup;
            initialStartTimeUs = initialStartTimeUs2;
            uri = uri2;
        } else {
            Uri uri3 = this.playlistUrls[i3];
            HlsMediaPlaylist playlistSnapshot2 = this.playlistTracker.getPlaylistSnapshot(uri3, true);
            Assertions.checkNotNull(playlistSnapshot2);
            initialStartTimeUs = playlistSnapshot2.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
            Pair<Long, Integer> nextMediaSequenceAndPartIndex2 = getNextMediaSequenceAndPartIndex(hlsMediaChunk, false, playlistSnapshot2, initialStartTimeUs, j2);
            jLongValue = ((Long) nextMediaSequenceAndPartIndex2.first).longValue();
            iIntValue = ((Integer) nextMediaSequenceAndPartIndex2.second).intValue();
            i2 = i3;
            uri = uri3;
            hlsMediaPlaylist = playlistSnapshot2;
        }
        if (i2 != i3 && i3 != -1) {
            this.playlistTracker.deactivatePlaylistForPlayback(this.playlistUrls[i3]);
        }
        if (jLongValue < hlsMediaPlaylist.mediaSequence) {
            this.fatalError = new BehindLiveWindowException();
            return;
        }
        SegmentBaseHolder nextSegmentHolder = getNextSegmentHolder(hlsMediaPlaylist, jLongValue, iIntValue);
        if (nextSegmentHolder == null) {
            if (!hlsMediaPlaylist.hasEndTag) {
                hlsChunkHolder.playlistUrl = uri;
                this.seenExpectedPlaylistError &= uri.equals(this.expectedPlaylistUrl);
                this.expectedPlaylistUrl = uri;
                return;
            } else {
                if (z2 || hlsMediaPlaylist.segments.isEmpty()) {
                    hlsChunkHolder.endOfStream = true;
                    return;
                }
                nextSegmentHolder = new SegmentBaseHolder((HlsMediaPlaylist.SegmentBase) Iterables.getLast(hlsMediaPlaylist.segments), (hlsMediaPlaylist.mediaSequence + hlsMediaPlaylist.segments.size()) - 1, -1);
            }
        }
        this.seenExpectedPlaylistError = false;
        this.expectedPlaylistUrl = null;
        if (this.cmcdConfiguration != null) {
            objectType = new CmcdData.Factory(this.cmcdConfiguration, this.trackSelection, Math.max(0L, j5), loadingInfo.playbackSpeed, "h", !hlsMediaPlaylist.hasEndTag, loadingInfo.rebufferedSince(this.lastChunkRequestRealtimeMs), list.isEmpty()).setObjectType(getIsMuxedAudioAndVideo() ? "av" : CmcdData.Factory.getObjectType(this.trackSelection));
            int i4 = nextSegmentHolder.partIndex;
            SegmentBaseHolder nextSegmentHolder2 = getNextSegmentHolder(hlsMediaPlaylist, i4 == -1 ? nextSegmentHolder.mediaSequence + 1 : nextSegmentHolder.mediaSequence, i4 == -1 ? -1 : i4 + 1);
            if (nextSegmentHolder2 != null) {
                objectType.setNextObjectRequest(UriUtil.getRelativePath(UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, nextSegmentHolder.segmentBase.url), UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, nextSegmentHolder2.segmentBase.url)));
                String string = nextSegmentHolder2.segmentBase.byteRangeOffset + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
                if (nextSegmentHolder2.segmentBase.byteRangeLength != -1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(string);
                    HlsMediaPlaylist.SegmentBase segmentBase = nextSegmentHolder2.segmentBase;
                    sb.append(segmentBase.byteRangeOffset + segmentBase.byteRangeLength);
                    string = sb.toString();
                }
                objectType.setNextRangeRequest(string);
            }
        } else {
            objectType = null;
        }
        this.lastChunkRequestRealtimeMs = SystemClock.elapsedRealtime();
        Uri fullEncryptionKeyUri = getFullEncryptionKeyUri(hlsMediaPlaylist, nextSegmentHolder.segmentBase.initializationSegment);
        Chunk chunkMaybeCreateEncryptionChunkFor = maybeCreateEncryptionChunkFor(fullEncryptionKeyUri, i2, true, objectType);
        hlsChunkHolder.chunk = chunkMaybeCreateEncryptionChunkFor;
        if (chunkMaybeCreateEncryptionChunkFor != null) {
            return;
        }
        Uri fullEncryptionKeyUri2 = getFullEncryptionKeyUri(hlsMediaPlaylist, nextSegmentHolder.segmentBase);
        Chunk chunkMaybeCreateEncryptionChunkFor2 = maybeCreateEncryptionChunkFor(fullEncryptionKeyUri2, i2, false, objectType);
        hlsChunkHolder.chunk = chunkMaybeCreateEncryptionChunkFor2;
        if (chunkMaybeCreateEncryptionChunkFor2 != null) {
            return;
        }
        boolean zShouldSpliceIn = HlsMediaChunk.shouldSpliceIn(hlsMediaChunk, uri, hlsMediaPlaylist, nextSegmentHolder, initialStartTimeUs);
        if (zShouldSpliceIn && nextSegmentHolder.isPreload) {
            return;
        }
        hlsChunkHolder.chunk = HlsMediaChunk.createInstance(this.extractorFactory, this.mediaDataSource, this.playlistFormats[i2], initialStartTimeUs, hlsMediaPlaylist, nextSegmentHolder, uri, this.muxedCaptionFormats, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.isPrimaryTimestampSource, this.timestampAdjusterProvider, this.timestampAdjusterInitializationTimeoutMs, hlsMediaChunk, this.keyCache.get(fullEncryptionKeyUri2), this.keyCache.get(fullEncryptionKeyUri), zShouldSpliceIn, this.playerId, objectType);
    }

    public int getPreferredQueueSize(long j2, List<? extends MediaChunk> list) {
        return (this.fatalError != null || this.trackSelection.length() < 2) ? list.size() : this.trackSelection.evaluateQueueSize(j2, list);
    }

    public TrackGroup getTrackGroup() {
        return this.trackGroup;
    }

    public ExoTrackSelection getTrackSelection() {
        return this.trackSelection;
    }

    public boolean hasIndependentSegments() {
        return this.independentSegments;
    }

    public boolean maybeExcludeTrack(Chunk chunk, long j2) {
        ExoTrackSelection exoTrackSelection = this.trackSelection;
        return exoTrackSelection.excludeTrack(exoTrackSelection.indexOf(this.trackGroup.indexOf(chunk.trackFormat)), j2);
    }

    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException != null) {
            throw iOException;
        }
        Uri uri = this.expectedPlaylistUrl;
        if (uri == null || !this.seenExpectedPlaylistError) {
            return;
        }
        this.playlistTracker.maybeThrowPlaylistRefreshError(uri);
    }

    public boolean obtainsChunksForPlaylist(Uri uri) {
        return Util.contains(this.playlistUrls, uri);
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof EncryptionKeyChunk) {
            EncryptionKeyChunk encryptionKeyChunk = (EncryptionKeyChunk) chunk;
            this.scratchSpace = encryptionKeyChunk.getDataHolder();
            this.keyCache.put(encryptionKeyChunk.dataSpec.uri, (byte[]) Assertions.checkNotNull(encryptionKeyChunk.getResult()));
        }
    }

    public boolean onPlaylistError(Uri uri, long j2) {
        int iIndexOf;
        int i2 = 0;
        while (true) {
            Uri[] uriArr = this.playlistUrls;
            if (i2 >= uriArr.length) {
                i2 = -1;
                break;
            }
            if (uriArr[i2].equals(uri)) {
                break;
            }
            i2++;
        }
        if (i2 == -1 || (iIndexOf = this.trackSelection.indexOf(i2)) == -1) {
            return true;
        }
        this.seenExpectedPlaylistError |= uri.equals(this.expectedPlaylistUrl);
        return j2 == C.TIME_UNSET || (this.trackSelection.excludeTrack(iIndexOf, j2) && this.playlistTracker.excludeMediaPlaylist(uri, j2));
    }

    public void reset() {
        deactivatePlaylistForSelectedTrack();
        this.fatalError = null;
    }

    public void setIsPrimaryTimestampSource(boolean z2) {
        this.isPrimaryTimestampSource = z2;
    }

    public void setTrackSelection(ExoTrackSelection exoTrackSelection) {
        deactivatePlaylistForSelectedTrack();
        this.trackSelection = exoTrackSelection;
    }

    public boolean shouldCancelLoad(long j2, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(j2, chunk, list);
    }
}
