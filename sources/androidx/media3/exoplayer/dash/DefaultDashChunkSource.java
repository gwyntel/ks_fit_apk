package androidx.media3.exoplayer.dash;

import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.UriUtil;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.HttpDataSource;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.dash.DashChunkSource;
import androidx.media3.exoplayer.dash.PlayerEmsgHandler;
import androidx.media3.exoplayer.dash.manifest.AdaptationSet;
import androidx.media3.exoplayer.dash.manifest.BaseUrl;
import androidx.media3.exoplayer.dash.manifest.DashManifest;
import androidx.media3.exoplayer.dash.manifest.RangedUri;
import androidx.media3.exoplayer.dash.manifest.Representation;
import androidx.media3.exoplayer.source.BehindLiveWindowException;
import androidx.media3.exoplayer.source.chunk.BaseMediaChunkIterator;
import androidx.media3.exoplayer.source.chunk.BundledChunkExtractor;
import androidx.media3.exoplayer.source.chunk.Chunk;
import androidx.media3.exoplayer.source.chunk.ChunkExtractor;
import androidx.media3.exoplayer.source.chunk.ContainerMediaChunk;
import androidx.media3.exoplayer.source.chunk.InitializationChunk;
import androidx.media3.exoplayer.source.chunk.MediaChunk;
import androidx.media3.exoplayer.source.chunk.SingleSampleMediaChunk;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import androidx.media3.exoplayer.upstream.CmcdData;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import androidx.media3.exoplayer.upstream.LoaderErrorThrower;
import androidx.media3.extractor.ChunkIndex;
import androidx.media3.extractor.text.SubtitleParser;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@UnstableApi
/* loaded from: classes.dex */
public class DefaultDashChunkSource implements DashChunkSource {

    /* renamed from: a, reason: collision with root package name */
    protected final RepresentationHolder[] f5166a;
    private final int[] adaptationSetIndices;
    private final BaseUrlExclusionList baseUrlExclusionList;

    @Nullable
    private final CmcdConfiguration cmcdConfiguration;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;

    @Nullable
    private IOException fatalError;
    private long lastChunkRequestRealtimeMs = C.TIME_UNSET;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;

    @Nullable
    private final PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler;
    private ExoTrackSelection trackSelection;
    private final int trackType;

    public static final class Factory implements DashChunkSource.Factory {
        private final ChunkExtractor.Factory chunkExtractorFactory;
        private final DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(DataSource.Factory factory) {
            this(factory, 1);
        }

        @Override // androidx.media3.exoplayer.dash.DashChunkSource.Factory
        public DashChunkSource createDashChunkSource(LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i2, int[] iArr, ExoTrackSelection exoTrackSelection, int i3, long j2, boolean z2, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler, @Nullable TransferListener transferListener, PlayerId playerId, @Nullable CmcdConfiguration cmcdConfiguration) {
            DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSourceCreateDataSource.addTransferListener(transferListener);
            }
            return new DefaultDashChunkSource(this.chunkExtractorFactory, loaderErrorThrower, dashManifest, baseUrlExclusionList, i2, iArr, exoTrackSelection, i3, dataSourceCreateDataSource, j2, this.maxSegmentsPerLoad, z2, list, playerTrackEmsgHandler, playerId, cmcdConfiguration);
        }

        @Override // androidx.media3.exoplayer.dash.DashChunkSource.Factory
        public Format getOutputTextFormat(Format format) {
            return this.chunkExtractorFactory.getOutputTextFormat(format);
        }

        public Factory(DataSource.Factory factory, int i2) {
            this(BundledChunkExtractor.FACTORY, factory, i2);
        }

        @Override // androidx.media3.exoplayer.dash.DashChunkSource.Factory
        @CanIgnoreReturnValue
        public Factory experimentalParseSubtitlesDuringExtraction(boolean z2) {
            this.chunkExtractorFactory.experimentalParseSubtitlesDuringExtraction(z2);
            return this;
        }

        @Override // androidx.media3.exoplayer.dash.DashChunkSource.Factory
        @CanIgnoreReturnValue
        public Factory setSubtitleParserFactory(SubtitleParser.Factory factory) {
            this.chunkExtractorFactory.setSubtitleParserFactory(factory);
            return this;
        }

        public Factory(ChunkExtractor.Factory factory, DataSource.Factory factory2, int i2) {
            this.chunkExtractorFactory = factory;
            this.dataSourceFactory = factory2;
            this.maxSegmentsPerLoad = i2;
        }
    }

    protected static final class RepresentationHolder {

        /* renamed from: a, reason: collision with root package name */
        final ChunkExtractor f5167a;
        private final long periodDurationUs;
        public final Representation representation;

        @Nullable
        public final DashSegmentIndex segmentIndex;
        private final long segmentNumShift;
        public final BaseUrl selectedBaseUrl;

        RepresentationHolder(long j2, Representation representation, BaseUrl baseUrl, ChunkExtractor chunkExtractor, long j3, DashSegmentIndex dashSegmentIndex) {
            this.periodDurationUs = j2;
            this.representation = representation;
            this.selectedBaseUrl = baseUrl;
            this.segmentNumShift = j3;
            this.f5167a = chunkExtractor;
            this.segmentIndex = dashSegmentIndex;
        }

        RepresentationHolder b(long j2, Representation representation) throws BehindLiveWindowException {
            long segmentNum;
            DashSegmentIndex index = this.representation.getIndex();
            DashSegmentIndex index2 = representation.getIndex();
            if (index == null) {
                return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.f5167a, this.segmentNumShift, index);
            }
            if (!index.isExplicit()) {
                return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.f5167a, this.segmentNumShift, index2);
            }
            long segmentCount = index.getSegmentCount(j2);
            if (segmentCount == 0) {
                return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.f5167a, this.segmentNumShift, index2);
            }
            Assertions.checkStateNotNull(index2);
            long firstSegmentNum = index.getFirstSegmentNum();
            long timeUs = index.getTimeUs(firstSegmentNum);
            long segmentNum2 = segmentCount + firstSegmentNum;
            long j3 = segmentNum2 - 1;
            long timeUs2 = index.getTimeUs(j3) + index.getDurationUs(j3, j2);
            long firstSegmentNum2 = index2.getFirstSegmentNum();
            long timeUs3 = index2.getTimeUs(firstSegmentNum2);
            long j4 = this.segmentNumShift;
            if (timeUs2 == timeUs3) {
                segmentNum = j4 + (segmentNum2 - firstSegmentNum2);
            } else {
                if (timeUs2 < timeUs3) {
                    throw new BehindLiveWindowException();
                }
                if (timeUs3 < timeUs) {
                    segmentNum = j4 - (index2.getSegmentNum(timeUs, j2) - firstSegmentNum);
                } else {
                    segmentNum2 = index.getSegmentNum(timeUs3, j2);
                    segmentNum = j4 + (segmentNum2 - firstSegmentNum2);
                }
            }
            return new RepresentationHolder(j2, representation, this.selectedBaseUrl, this.f5167a, segmentNum, index2);
        }

        RepresentationHolder c(DashSegmentIndex dashSegmentIndex) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, this.selectedBaseUrl, this.f5167a, this.segmentNumShift, dashSegmentIndex);
        }

        RepresentationHolder d(BaseUrl baseUrl) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, baseUrl, this.f5167a, this.segmentNumShift, this.segmentIndex);
        }

        public long getFirstAvailableSegmentNum(long j2) {
            return ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getFirstAvailableSegmentNum(this.periodDurationUs, j2) + this.segmentNumShift;
        }

        public long getFirstSegmentNum() {
            return ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getFirstSegmentNum() + this.segmentNumShift;
        }

        public long getLastAvailableSegmentNum(long j2) {
            return (getFirstAvailableSegmentNum(j2) + ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getAvailableSegmentCount(this.periodDurationUs, j2)) - 1;
        }

        public long getSegmentCount() {
            return ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentEndTimeUs(long j2) {
            return getSegmentStartTimeUs(j2) + ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getDurationUs(j2 - this.segmentNumShift, this.periodDurationUs);
        }

        public long getSegmentNum(long j2) {
            return ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getSegmentNum(j2, this.periodDurationUs) + this.segmentNumShift;
        }

        public long getSegmentStartTimeUs(long j2) {
            return ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getTimeUs(j2 - this.segmentNumShift);
        }

        public RangedUri getSegmentUrl(long j2) {
            return ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).getSegmentUrl(j2 - this.segmentNumShift);
        }

        public boolean isSegmentAvailableAtFullNetworkSpeed(long j2, long j3) {
            return ((DashSegmentIndex) Assertions.checkStateNotNull(this.segmentIndex)).isExplicit() || j3 == C.TIME_UNSET || getSegmentEndTimeUs(j2) <= j3;
        }
    }

    protected static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final long nowPeriodTimeUs;
        private final RepresentationHolder representationHolder;

        public RepresentationSegmentIterator(RepresentationHolder representationHolder, long j2, long j3, long j4) {
            super(j2, j3);
            this.representationHolder = representationHolder;
            this.nowPeriodTimeUs = j4;
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public long getChunkEndTimeUs() {
            a();
            return this.representationHolder.getSegmentEndTimeUs(b());
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public long getChunkStartTimeUs() {
            a();
            return this.representationHolder.getSegmentStartTimeUs(b());
        }

        @Override // androidx.media3.exoplayer.source.chunk.MediaChunkIterator
        public DataSpec getDataSpec() {
            a();
            long jB = b();
            RangedUri segmentUrl = this.representationHolder.getSegmentUrl(jB);
            int i2 = this.representationHolder.isSegmentAvailableAtFullNetworkSpeed(jB, this.nowPeriodTimeUs) ? 0 : 8;
            RepresentationHolder representationHolder = this.representationHolder;
            return DashUtil.buildDataSpec(representationHolder.representation, representationHolder.selectedBaseUrl.url, segmentUrl, i2, ImmutableMap.of());
        }
    }

    public DefaultDashChunkSource(ChunkExtractor.Factory factory, LoaderErrorThrower loaderErrorThrower, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i2, int[] iArr, ExoTrackSelection exoTrackSelection, int i3, DataSource dataSource, long j2, int i4, boolean z2, List<Format> list, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler, PlayerId playerId, @Nullable CmcdConfiguration cmcdConfiguration) {
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.manifest = dashManifest;
        this.baseUrlExclusionList = baseUrlExclusionList;
        this.adaptationSetIndices = iArr;
        this.trackSelection = exoTrackSelection;
        this.trackType = i3;
        this.dataSource = dataSource;
        this.periodIndex = i2;
        this.elapsedRealtimeOffsetMs = j2;
        this.maxSegmentsPerLoad = i4;
        this.playerTrackEmsgHandler = playerTrackEmsgHandler;
        this.cmcdConfiguration = cmcdConfiguration;
        long periodDurationUs = dashManifest.getPeriodDurationUs(i2);
        ArrayList<Representation> representations = getRepresentations();
        this.f5166a = new RepresentationHolder[exoTrackSelection.length()];
        int i5 = 0;
        while (i5 < this.f5166a.length) {
            Representation representation = representations.get(exoTrackSelection.getIndexInTrackGroup(i5));
            BaseUrl baseUrlSelectBaseUrl = baseUrlExclusionList.selectBaseUrl(representation.baseUrls);
            int i6 = i5;
            this.f5166a[i6] = new RepresentationHolder(periodDurationUs, representation, baseUrlSelectBaseUrl == null ? representation.baseUrls.get(0) : baseUrlSelectBaseUrl, factory.createProgressiveMediaExtractor(i3, representation.format, z2, list, playerTrackEmsgHandler, playerId), 0L, representation.getIndex());
            i5 = i6 + 1;
        }
    }

    private LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection exoTrackSelection, List<BaseUrl> list) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int length = exoTrackSelection.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (exoTrackSelection.isTrackExcluded(i3, jElapsedRealtime)) {
                i2++;
            }
        }
        int priorityCount = BaseUrlExclusionList.getPriorityCount(list);
        return new LoadErrorHandlingPolicy.FallbackOptions(priorityCount, priorityCount - this.baseUrlExclusionList.getPriorityCountAfterExclusion(list), length, i2);
    }

    private long getAvailableLiveDurationUs(long j2, long j3) {
        if (!this.manifest.dynamic || this.f5166a[0].getSegmentCount() == 0) {
            return C.TIME_UNSET;
        }
        return Math.max(0L, Math.min(getNowPeriodTimeUs(j2), this.f5166a[0].getSegmentEndTimeUs(this.f5166a[0].getLastAvailableSegmentNum(j2))) - j3);
    }

    @Nullable
    private Pair<String, String> getNextObjectAndRangeRequest(long j2, RangedUri rangedUri, RepresentationHolder representationHolder) {
        long j3 = j2 + 1;
        if (j3 >= representationHolder.getSegmentCount()) {
            return null;
        }
        RangedUri segmentUrl = representationHolder.getSegmentUrl(j3);
        String relativePath = UriUtil.getRelativePath(rangedUri.resolveUri(representationHolder.selectedBaseUrl.url), segmentUrl.resolveUri(representationHolder.selectedBaseUrl.url));
        String str = segmentUrl.start + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        if (segmentUrl.length != -1) {
            str = str + (segmentUrl.start + segmentUrl.length);
        }
        return new Pair<>(relativePath, str);
    }

    private long getNowPeriodTimeUs(long j2) {
        DashManifest dashManifest = this.manifest;
        long j3 = dashManifest.availabilityStartTimeMs;
        return j3 == C.TIME_UNSET ? C.TIME_UNSET : j2 - Util.msToUs(j3 + dashManifest.getPeriod(this.periodIndex).startMs);
    }

    @RequiresNonNull({"manifest", "adaptationSetIndices"})
    private ArrayList<Representation> getRepresentations() {
        List<AdaptationSet> list = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> arrayList = new ArrayList<>();
        for (int i2 : this.adaptationSetIndices) {
            arrayList.addAll(list.get(i2).representations);
        }
        return arrayList;
    }

    private long getSegmentNum(RepresentationHolder representationHolder, @Nullable MediaChunk mediaChunk, long j2, long j3, long j4) {
        return mediaChunk != null ? mediaChunk.getNextChunkIndex() : Util.constrainValue(representationHolder.getSegmentNum(j2), j3, j4);
    }

    private RepresentationHolder updateSelectedBaseUrl(int i2) {
        RepresentationHolder representationHolder = this.f5166a[i2];
        BaseUrl baseUrlSelectBaseUrl = this.baseUrlExclusionList.selectBaseUrl(representationHolder.representation.baseUrls);
        if (baseUrlSelectBaseUrl == null || baseUrlSelectBaseUrl.equals(representationHolder.selectedBaseUrl)) {
            return representationHolder;
        }
        RepresentationHolder representationHolderD = representationHolder.d(baseUrlSelectBaseUrl);
        this.f5166a[i2] = representationHolderD;
        return representationHolderD;
    }

    protected Chunk a(RepresentationHolder representationHolder, DataSource dataSource, Format format, int i2, Object obj, RangedUri rangedUri, RangedUri rangedUri2, CmcdData.Factory factory) {
        Representation representation = representationHolder.representation;
        if (rangedUri != null) {
            RangedUri rangedUriAttemptMerge = rangedUri.attemptMerge(rangedUri2, representationHolder.selectedBaseUrl.url);
            if (rangedUriAttemptMerge != null) {
                rangedUri = rangedUriAttemptMerge;
            }
        } else {
            rangedUri = (RangedUri) Assertions.checkNotNull(rangedUri2);
        }
        DataSpec dataSpecBuildDataSpec = DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, rangedUri, 0, ImmutableMap.of());
        if (factory != null) {
            dataSpecBuildDataSpec = factory.setObjectType("i").createCmcdData().addToDataSpec(dataSpecBuildDataSpec);
        }
        return new InitializationChunk(dataSource, dataSpecBuildDataSpec, format, i2, obj, representationHolder.f5167a);
    }

    protected Chunk b(RepresentationHolder representationHolder, DataSource dataSource, int i2, Format format, int i3, Object obj, long j2, int i4, long j3, long j4, CmcdData.Factory factory) {
        DataSpec dataSpecAddToDataSpec;
        Representation representation = representationHolder.representation;
        long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(j2);
        RangedUri segmentUrl = representationHolder.getSegmentUrl(j2);
        if (representationHolder.f5167a == null) {
            long segmentEndTimeUs = representationHolder.getSegmentEndTimeUs(j2);
            DataSpec dataSpecBuildDataSpec = DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, segmentUrl, representationHolder.isSegmentAvailableAtFullNetworkSpeed(j2, j4) ? 0 : 8, ImmutableMap.of());
            if (factory != null) {
                factory.setChunkDurationUs(segmentEndTimeUs - segmentStartTimeUs).setObjectType(CmcdData.Factory.getObjectType(this.trackSelection));
                Pair<String, String> nextObjectAndRangeRequest = getNextObjectAndRangeRequest(j2, segmentUrl, representationHolder);
                if (nextObjectAndRangeRequest != null) {
                    factory.setNextObjectRequest((String) nextObjectAndRangeRequest.first).setNextRangeRequest((String) nextObjectAndRangeRequest.second);
                }
                dataSpecAddToDataSpec = factory.createCmcdData().addToDataSpec(dataSpecBuildDataSpec);
            } else {
                dataSpecAddToDataSpec = dataSpecBuildDataSpec;
            }
            return new SingleSampleMediaChunk(dataSource, dataSpecAddToDataSpec, format, i3, obj, segmentStartTimeUs, segmentEndTimeUs, j2, i2, format);
        }
        int i5 = 1;
        int i6 = 1;
        while (i5 < i4) {
            RangedUri rangedUriAttemptMerge = segmentUrl.attemptMerge(representationHolder.getSegmentUrl(i5 + j2), representationHolder.selectedBaseUrl.url);
            if (rangedUriAttemptMerge == null) {
                break;
            }
            i6++;
            i5++;
            segmentUrl = rangedUriAttemptMerge;
        }
        long j5 = (i6 + j2) - 1;
        long segmentEndTimeUs2 = representationHolder.getSegmentEndTimeUs(j5);
        long j6 = representationHolder.periodDurationUs;
        long j7 = C.TIME_UNSET;
        if (j6 != C.TIME_UNSET && j6 <= segmentEndTimeUs2) {
            j7 = j6;
        }
        DataSpec dataSpecBuildDataSpec2 = DashUtil.buildDataSpec(representation, representationHolder.selectedBaseUrl.url, segmentUrl, representationHolder.isSegmentAvailableAtFullNetworkSpeed(j5, j4) ? 0 : 8, ImmutableMap.of());
        if (factory != null) {
            factory.setChunkDurationUs(segmentEndTimeUs2 - segmentStartTimeUs).setObjectType(CmcdData.Factory.getObjectType(this.trackSelection));
            Pair<String, String> nextObjectAndRangeRequest2 = getNextObjectAndRangeRequest(j2, segmentUrl, representationHolder);
            if (nextObjectAndRangeRequest2 != null) {
                factory.setNextObjectRequest((String) nextObjectAndRangeRequest2.first).setNextRangeRequest((String) nextObjectAndRangeRequest2.second);
            }
            dataSpecBuildDataSpec2 = factory.createCmcdData().addToDataSpec(dataSpecBuildDataSpec2);
        }
        DataSpec dataSpec = dataSpecBuildDataSpec2;
        long j8 = -representation.presentationTimeOffsetUs;
        if (MimeTypes.isImage(format.sampleMimeType)) {
            j8 += segmentStartTimeUs;
        }
        return new ContainerMediaChunk(dataSource, dataSpec, format, i3, obj, segmentStartTimeUs, segmentEndTimeUs2, j3, j7, j2, i6, j8, representationHolder.f5167a);
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        for (RepresentationHolder representationHolder : this.f5166a) {
            if (representationHolder.segmentIndex != null) {
                long segmentCount = representationHolder.getSegmentCount();
                if (segmentCount != 0) {
                    long segmentNum = representationHolder.getSegmentNum(j2);
                    long segmentStartTimeUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                    return seekParameters.resolveSeekPositionUs(j2, segmentStartTimeUs, (segmentStartTimeUs >= j2 || (segmentCount != -1 && segmentNum >= (representationHolder.getFirstSegmentNum() + segmentCount) - 1)) ? segmentStartTimeUs : representationHolder.getSegmentStartTimeUs(segmentNum + 1));
                }
            }
        }
        return j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01a0  */
    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void getNextChunk(androidx.media3.exoplayer.LoadingInfo r44, long r45, java.util.List<? extends androidx.media3.exoplayer.source.chunk.MediaChunk> r47, androidx.media3.exoplayer.source.chunk.ChunkHolder r48) {
        /*
            Method dump skipped, instructions count: 584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.DefaultDashChunkSource.getNextChunk(androidx.media3.exoplayer.LoadingInfo, long, java.util.List, androidx.media3.exoplayer.source.chunk.ChunkHolder):void");
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
        ChunkIndex chunkIndex;
        if (chunk instanceof InitializationChunk) {
            int iIndexOf = this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.f5166a[iIndexOf];
            if (representationHolder.segmentIndex == null && (chunkIndex = ((ChunkExtractor) Assertions.checkStateNotNull(representationHolder.f5167a)).getChunkIndex()) != null) {
                this.f5166a[iIndexOf] = representationHolder.c(new DashWrappingSegmentIndex(chunkIndex, representationHolder.representation.presentationTimeOffsetUs));
            }
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null) {
            playerTrackEmsgHandler.onChunkLoadCompleted(chunk);
        }
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public boolean onChunkLoadError(Chunk chunk, boolean z2, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        LoadErrorHandlingPolicy.FallbackSelection fallbackSelectionFor;
        if (!z2) {
            return false;
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler != null && playerTrackEmsgHandler.onChunkLoadError(chunk)) {
            return true;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk)) {
            IOException iOException = loadErrorInfo.exception;
            if ((iOException instanceof HttpDataSource.InvalidResponseCodeException) && ((HttpDataSource.InvalidResponseCodeException) iOException).responseCode == 404) {
                RepresentationHolder representationHolder = this.f5166a[this.trackSelection.indexOf(chunk.trackFormat)];
                long segmentCount = representationHolder.getSegmentCount();
                if (segmentCount != -1 && segmentCount != 0) {
                    if (((MediaChunk) chunk).getNextChunkIndex() > (representationHolder.getFirstSegmentNum() + segmentCount) - 1) {
                        this.missingLastSegment = true;
                        return true;
                    }
                }
            }
        }
        RepresentationHolder representationHolder2 = this.f5166a[this.trackSelection.indexOf(chunk.trackFormat)];
        BaseUrl baseUrlSelectBaseUrl = this.baseUrlExclusionList.selectBaseUrl(representationHolder2.representation.baseUrls);
        if (baseUrlSelectBaseUrl != null && !representationHolder2.selectedBaseUrl.equals(baseUrlSelectBaseUrl)) {
            return true;
        }
        LoadErrorHandlingPolicy.FallbackOptions fallbackOptionsCreateFallbackOptions = createFallbackOptions(this.trackSelection, representationHolder2.representation.baseUrls);
        if ((!fallbackOptionsCreateFallbackOptions.isFallbackAvailable(2) && !fallbackOptionsCreateFallbackOptions.isFallbackAvailable(1)) || (fallbackSelectionFor = loadErrorHandlingPolicy.getFallbackSelectionFor(fallbackOptionsCreateFallbackOptions, loadErrorInfo)) == null || !fallbackOptionsCreateFallbackOptions.isFallbackAvailable(fallbackSelectionFor.type)) {
            return false;
        }
        int i2 = fallbackSelectionFor.type;
        if (i2 == 2) {
            ExoTrackSelection exoTrackSelection = this.trackSelection;
            return exoTrackSelection.excludeTrack(exoTrackSelection.indexOf(chunk.trackFormat), fallbackSelectionFor.exclusionDurationMs);
        }
        if (i2 != 1) {
            return false;
        }
        this.baseUrlExclusionList.exclude(representationHolder2.selectedBaseUrl, fallbackSelectionFor.exclusionDurationMs);
        return true;
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public void release() {
        for (RepresentationHolder representationHolder : this.f5166a) {
            ChunkExtractor chunkExtractor = representationHolder.f5167a;
            if (chunkExtractor != null) {
                chunkExtractor.release();
            }
        }
    }

    @Override // androidx.media3.exoplayer.source.chunk.ChunkSource
    public boolean shouldCancelLoad(long j2, Chunk chunk, List<? extends MediaChunk> list) {
        if (this.fatalError != null) {
            return false;
        }
        return this.trackSelection.shouldCancelChunkLoad(j2, chunk, list);
    }

    @Override // androidx.media3.exoplayer.dash.DashChunkSource
    public void updateManifest(DashManifest dashManifest, int i2) {
        try {
            this.manifest = dashManifest;
            this.periodIndex = i2;
            long periodDurationUs = dashManifest.getPeriodDurationUs(i2);
            ArrayList<Representation> representations = getRepresentations();
            for (int i3 = 0; i3 < this.f5166a.length; i3++) {
                Representation representation = representations.get(this.trackSelection.getIndexInTrackGroup(i3));
                RepresentationHolder[] representationHolderArr = this.f5166a;
                representationHolderArr[i3] = representationHolderArr[i3].b(periodDurationUs, representation);
            }
        } catch (BehindLiveWindowException e2) {
            this.fatalError = e2;
        }
    }

    @Override // androidx.media3.exoplayer.dash.DashChunkSource
    public void updateTrackSelection(ExoTrackSelection exoTrackSelection) {
        this.trackSelection = exoTrackSelection;
    }
}
