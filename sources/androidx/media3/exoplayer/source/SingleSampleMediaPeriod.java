package androidx.media3.exoplayer.source;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSourceUtil;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.StatsDataSource;
import androidx.media3.datasource.TransferListener;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.source.MediaSourceEventListener;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import androidx.media3.exoplayer.upstream.Loader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
final class SingleSampleMediaPeriod implements MediaPeriod, Loader.Callback<SourceLoadable> {
    private static final int INITIAL_SAMPLE_SIZE = 1024;
    private static final String TAG = "SingleSampleMediaPeriod";

    /* renamed from: b, reason: collision with root package name */
    final Format f5407b;

    /* renamed from: c, reason: collision with root package name */
    final boolean f5408c;

    /* renamed from: d, reason: collision with root package name */
    boolean f5409d;
    private final DataSource.Factory dataSourceFactory;
    private final DataSpec dataSpec;
    private final long durationUs;

    /* renamed from: e, reason: collision with root package name */
    byte[] f5410e;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;

    /* renamed from: f, reason: collision with root package name */
    int f5411f;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final TrackGroupArray tracks;

    @Nullable
    private final TransferListener transferListener;
    private final ArrayList<SampleStreamImpl> sampleStreams = new ArrayList<>();

    /* renamed from: a, reason: collision with root package name */
    final Loader f5406a = new Loader(TAG);

    private final class SampleStreamImpl implements SampleStream {
        private static final int STREAM_STATE_END_OF_STREAM = 2;
        private static final int STREAM_STATE_SEND_FORMAT = 0;
        private static final int STREAM_STATE_SEND_SAMPLE = 1;
        private boolean notifiedDownstreamFormat;
        private int streamState;

        private SampleStreamImpl() {
        }

        private void maybeNotifyDownstreamFormat() {
            if (this.notifiedDownstreamFormat) {
                return;
            }
            SingleSampleMediaPeriod.this.eventDispatcher.downstreamFormatChanged(MimeTypes.getTrackType(SingleSampleMediaPeriod.this.f5407b.sampleMimeType), SingleSampleMediaPeriod.this.f5407b, 0, null, 0L);
            this.notifiedDownstreamFormat = true;
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public boolean isReady() {
            return SingleSampleMediaPeriod.this.f5409d;
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public void maybeThrowError() throws IOException {
            SingleSampleMediaPeriod singleSampleMediaPeriod = SingleSampleMediaPeriod.this;
            if (singleSampleMediaPeriod.f5408c) {
                return;
            }
            singleSampleMediaPeriod.f5406a.maybeThrowError();
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            maybeNotifyDownstreamFormat();
            SingleSampleMediaPeriod singleSampleMediaPeriod = SingleSampleMediaPeriod.this;
            boolean z2 = singleSampleMediaPeriod.f5409d;
            if (z2 && singleSampleMediaPeriod.f5410e == null) {
                this.streamState = 2;
            }
            int i3 = this.streamState;
            if (i3 == 2) {
                decoderInputBuffer.addFlag(4);
                return -4;
            }
            if ((i2 & 2) != 0 || i3 == 0) {
                formatHolder.format = singleSampleMediaPeriod.f5407b;
                this.streamState = 1;
                return -5;
            }
            if (!z2) {
                return -3;
            }
            Assertions.checkNotNull(singleSampleMediaPeriod.f5410e);
            decoderInputBuffer.addFlag(1);
            decoderInputBuffer.timeUs = 0L;
            if ((i2 & 4) == 0) {
                decoderInputBuffer.ensureSpaceForWrite(SingleSampleMediaPeriod.this.f5411f);
                ByteBuffer byteBuffer = decoderInputBuffer.data;
                SingleSampleMediaPeriod singleSampleMediaPeriod2 = SingleSampleMediaPeriod.this;
                byteBuffer.put(singleSampleMediaPeriod2.f5410e, 0, singleSampleMediaPeriod2.f5411f);
            }
            if ((i2 & 1) == 0) {
                this.streamState = 2;
            }
            return -4;
        }

        public void reset() {
            if (this.streamState == 2) {
                this.streamState = 1;
            }
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int skipData(long j2) {
            maybeNotifyDownstreamFormat();
            if (j2 <= 0 || this.streamState == 2) {
                return 0;
            }
            this.streamState = 2;
            return 1;
        }
    }

    static final class SourceLoadable implements Loader.Loadable {
        private final StatsDataSource dataSource;
        public final DataSpec dataSpec;
        public final long loadTaskId = LoadEventInfo.getNewId();

        @Nullable
        private byte[] sampleData;

        public SourceLoadable(DataSpec dataSpec, DataSource dataSource) {
            this.dataSpec = dataSpec;
            this.dataSource = new StatsDataSource(dataSource);
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
        public void cancelLoad() {
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
        public void load() throws IOException {
            this.dataSource.resetBytesRead();
            try {
                this.dataSource.open(this.dataSpec);
                int i2 = 0;
                while (i2 != -1) {
                    int bytesRead = (int) this.dataSource.getBytesRead();
                    byte[] bArr = this.sampleData;
                    if (bArr == null) {
                        this.sampleData = new byte[1024];
                    } else if (bytesRead == bArr.length) {
                        this.sampleData = Arrays.copyOf(bArr, bArr.length * 2);
                    }
                    StatsDataSource statsDataSource = this.dataSource;
                    byte[] bArr2 = this.sampleData;
                    i2 = statsDataSource.read(bArr2, bytesRead, bArr2.length - bytesRead);
                }
                DataSourceUtil.closeQuietly(this.dataSource);
            } catch (Throwable th) {
                DataSourceUtil.closeQuietly(this.dataSource);
                throw th;
            }
        }
    }

    public SingleSampleMediaPeriod(DataSpec dataSpec, DataSource.Factory factory, @Nullable TransferListener transferListener, Format format, long j2, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher, boolean z2) {
        this.dataSpec = dataSpec;
        this.dataSourceFactory = factory;
        this.transferListener = transferListener;
        this.f5407b = format;
        this.durationUs = j2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher;
        this.f5408c = z2;
        this.tracks = new TrackGroupArray(new TrackGroup(format));
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean continueLoading(LoadingInfo loadingInfo) {
        if (this.f5409d || this.f5406a.isLoading() || this.f5406a.hasFatalError()) {
            return false;
        }
        DataSource dataSourceCreateDataSource = this.dataSourceFactory.createDataSource();
        TransferListener transferListener = this.transferListener;
        if (transferListener != null) {
            dataSourceCreateDataSource.addTransferListener(transferListener);
        }
        SourceLoadable sourceLoadable = new SourceLoadable(this.dataSpec, dataSourceCreateDataSource);
        this.eventDispatcher.loadStarted(new LoadEventInfo(sourceLoadable.loadTaskId, this.dataSpec, this.f5406a.startLoading(sourceLoadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(1))), 1, -1, this.f5407b, 0, null, 0L, this.durationUs);
        return true;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.f5409d ? Long.MIN_VALUE : 0L;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return (this.f5409d || this.f5406a.isLoading()) ? Long.MIN_VALUE : 0L;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public /* synthetic */ List getStreamKeys(List list) {
        return o.a(this, list);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.tracks;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean isLoading() {
        return this.f5406a.isLoading();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void maybeThrowPrepareError() {
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        callback.onPrepared(this);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
    }

    public void release() {
        this.f5406a.release();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long seekToUs(long j2) {
        for (int i2 = 0; i2 < this.sampleStreams.size(); i2++) {
            this.sampleStreams.get(i2).reset();
        }
        return j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            SampleStream sampleStream = sampleStreamArr[i2];
            if (sampleStream != null && (exoTrackSelectionArr[i2] == null || !zArr[i2])) {
                this.sampleStreams.remove(sampleStream);
                sampleStreamArr[i2] = null;
            }
            if (sampleStreamArr[i2] == null && exoTrackSelectionArr[i2] != null) {
                SampleStreamImpl sampleStreamImpl = new SampleStreamImpl();
                this.sampleStreams.add(sampleStreamImpl);
                sampleStreamArr[i2] = sampleStreamImpl;
                zArr2[i2] = true;
            }
        }
        return j2;
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Callback
    public void onLoadCanceled(SourceLoadable sourceLoadable, long j2, long j3, boolean z2) {
        StatsDataSource statsDataSource = sourceLoadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(sourceLoadable.loadTaskId, sourceLoadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(sourceLoadable.loadTaskId);
        this.eventDispatcher.loadCanceled(loadEventInfo, 1, -1, null, 0, null, 0L, this.durationUs);
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Callback
    public void onLoadCompleted(SourceLoadable sourceLoadable, long j2, long j3) {
        this.f5411f = (int) sourceLoadable.dataSource.getBytesRead();
        this.f5410e = (byte[]) Assertions.checkNotNull(sourceLoadable.sampleData);
        this.f5409d = true;
        StatsDataSource statsDataSource = sourceLoadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(sourceLoadable.loadTaskId, sourceLoadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, this.f5411f);
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(sourceLoadable.loadTaskId);
        this.eventDispatcher.loadCompleted(loadEventInfo, 1, -1, this.f5407b, 0, null, 0L, this.durationUs);
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Callback
    public Loader.LoadErrorAction onLoadError(SourceLoadable sourceLoadable, long j2, long j3, IOException iOException, int i2) {
        Loader.LoadErrorAction loadErrorActionCreateRetryAction;
        StatsDataSource statsDataSource = sourceLoadable.dataSource;
        LoadEventInfo loadEventInfo = new LoadEventInfo(sourceLoadable.loadTaskId, sourceLoadable.dataSpec, statsDataSource.getLastOpenedUri(), statsDataSource.getLastResponseHeaders(), j2, j3, statsDataSource.getBytesRead());
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(1, -1, this.f5407b, 0, null, 0L, Util.usToMs(this.durationUs)), iOException, i2));
        boolean z2 = retryDelayMsFor == C.TIME_UNSET || i2 >= this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(1);
        if (this.f5408c && z2) {
            Log.w(TAG, "Loading failed, treating as end-of-stream.", iOException);
            this.f5409d = true;
            loadErrorActionCreateRetryAction = Loader.DONT_RETRY;
        } else {
            loadErrorActionCreateRetryAction = retryDelayMsFor != C.TIME_UNSET ? Loader.createRetryAction(false, retryDelayMsFor) : Loader.DONT_RETRY_FATAL;
        }
        Loader.LoadErrorAction loadErrorAction = loadErrorActionCreateRetryAction;
        boolean z3 = !loadErrorAction.isRetry();
        this.eventDispatcher.loadError(loadEventInfo, 1, -1, this.f5407b, 0, null, 0L, this.durationUs, iOException, z3);
        if (z3) {
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(sourceLoadable.loadTaskId);
        }
        return loadErrorAction;
    }
}
