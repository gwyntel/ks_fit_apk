package androidx.media3.exoplayer.rtsp;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.StreamKey;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.rtsp.RtpDataChannel;
import androidx.media3.exoplayer.rtsp.RtpDataLoadable;
import androidx.media3.exoplayer.rtsp.RtspClient;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.exoplayer.rtsp.RtspMessageChannel;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.source.SampleQueue;
import androidx.media3.exoplayer.source.SampleStream;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.Loader;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.SeekMap;
import androidx.media3.extractor.TrackOutput;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.net.SocketFactory;

/* loaded from: classes.dex */
final class RtspMediaPeriod implements MediaPeriod {
    private static final int PORT_BINDING_MAX_RETRY_COUNT = 3;
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final Handler handler = Util.createHandlerForCurrentLooper();
    private final InternalListener internalListener;
    private boolean isUsingRtpTcp;
    private final Listener listener;
    private boolean loadingFinished;
    private boolean notifyDiscontinuity;
    private long pendingSeekPositionUs;
    private long pendingSeekPositionUsForTcpRetry;

    @Nullable
    private RtspMediaSource.RtspPlaybackException playbackException;
    private int portBindingRetryCount;

    @Nullable
    private IOException preparationError;
    private boolean prepared;
    private boolean released;
    private long requestedSeekPositionUs;
    private final RtpDataChannel.Factory rtpDataChannelFactory;
    private final RtspClient rtspClient;
    private final List<RtspLoaderWrapper> rtspLoaderWrappers;
    private final List<RtpLoadInfo> selectedLoadInfos;
    private ImmutableList<TrackGroup> trackGroups;
    private boolean trackSelected;

    /* JADX INFO: Access modifiers changed from: private */
    final class ExtractorOutputImpl implements ExtractorOutput {
        private final TrackOutput trackOutput;

        @Override // androidx.media3.extractor.ExtractorOutput
        public void endTracks() {
            Handler handler = RtspMediaPeriod.this.handler;
            final RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
            handler.post(new Runnable() { // from class: androidx.media3.exoplayer.rtsp.e
                @Override // java.lang.Runnable
                public final void run() {
                    RtspMediaPeriod.w(rtspMediaPeriod);
                }
            });
        }

        @Override // androidx.media3.extractor.ExtractorOutput
        public void seekMap(SeekMap seekMap) {
        }

        @Override // androidx.media3.extractor.ExtractorOutput
        public TrackOutput track(int i2, int i3) {
            return this.trackOutput;
        }

        private ExtractorOutputImpl(TrackOutput trackOutput) {
            this.trackOutput = trackOutput;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class InternalListener implements Loader.Callback<RtpDataLoadable>, SampleQueue.UpstreamFormatChangedListener, RtspClient.SessionInfoListener, RtspClient.PlaybackEventListener {
        private InternalListener() {
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCanceled(RtpDataLoadable rtpDataLoadable, long j2, long j3, boolean z2) {
        }

        @Override // androidx.media3.exoplayer.rtsp.RtspClient.PlaybackEventListener
        public void onPlaybackError(RtspMediaSource.RtspPlaybackException rtspPlaybackException) throws NumberFormatException {
            if (!(rtspPlaybackException instanceof RtspMediaSource.RtspUdpUnsupportedTransportException) || RtspMediaPeriod.this.isUsingRtpTcp) {
                RtspMediaPeriod.this.playbackException = rtspPlaybackException;
            } else {
                RtspMediaPeriod.this.retryWithRtpTcp();
            }
        }

        @Override // androidx.media3.exoplayer.rtsp.RtspClient.PlaybackEventListener
        public void onPlaybackStarted(long j2, ImmutableList<RtspTrackTiming> immutableList) throws NumberFormatException {
            ArrayList arrayList = new ArrayList(immutableList.size());
            for (int i2 = 0; i2 < immutableList.size(); i2++) {
                arrayList.add((String) Assertions.checkNotNull(immutableList.get(i2).uri.getPath()));
            }
            for (int i3 = 0; i3 < RtspMediaPeriod.this.selectedLoadInfos.size(); i3++) {
                if (!arrayList.contains(((RtpLoadInfo) RtspMediaPeriod.this.selectedLoadInfos.get(i3)).getTrackUri().getPath())) {
                    RtspMediaPeriod.this.listener.onSeekingUnsupported();
                    if (RtspMediaPeriod.this.isSeekPending()) {
                        RtspMediaPeriod.this.notifyDiscontinuity = true;
                        RtspMediaPeriod.this.pendingSeekPositionUs = C.TIME_UNSET;
                        RtspMediaPeriod.this.requestedSeekPositionUs = C.TIME_UNSET;
                        RtspMediaPeriod.this.pendingSeekPositionUsForTcpRetry = C.TIME_UNSET;
                    }
                }
            }
            for (int i4 = 0; i4 < immutableList.size(); i4++) {
                RtspTrackTiming rtspTrackTiming = immutableList.get(i4);
                RtpDataLoadable loadableByTrackUri = RtspMediaPeriod.this.getLoadableByTrackUri(rtspTrackTiming.uri);
                if (loadableByTrackUri != null) {
                    loadableByTrackUri.setTimestamp(rtspTrackTiming.rtpTimestamp);
                    loadableByTrackUri.setSequenceNumber(rtspTrackTiming.sequenceNumber);
                    if (RtspMediaPeriod.this.isSeekPending() && RtspMediaPeriod.this.pendingSeekPositionUs == RtspMediaPeriod.this.requestedSeekPositionUs) {
                        loadableByTrackUri.seekToUs(j2, rtspTrackTiming.rtpTimestamp);
                    }
                }
            }
            if (!RtspMediaPeriod.this.isSeekPending()) {
                if (RtspMediaPeriod.this.pendingSeekPositionUsForTcpRetry == C.TIME_UNSET || !RtspMediaPeriod.this.isUsingRtpTcp) {
                    return;
                }
                RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
                rtspMediaPeriod.seekToUs(rtspMediaPeriod.pendingSeekPositionUsForTcpRetry);
                RtspMediaPeriod.this.pendingSeekPositionUsForTcpRetry = C.TIME_UNSET;
                return;
            }
            if (RtspMediaPeriod.this.pendingSeekPositionUs == RtspMediaPeriod.this.requestedSeekPositionUs) {
                RtspMediaPeriod.this.pendingSeekPositionUs = C.TIME_UNSET;
                RtspMediaPeriod.this.requestedSeekPositionUs = C.TIME_UNSET;
            } else {
                RtspMediaPeriod.this.pendingSeekPositionUs = C.TIME_UNSET;
                RtspMediaPeriod rtspMediaPeriod2 = RtspMediaPeriod.this;
                rtspMediaPeriod2.seekToUs(rtspMediaPeriod2.requestedSeekPositionUs);
            }
        }

        @Override // androidx.media3.exoplayer.rtsp.RtspClient.PlaybackEventListener
        public void onRtspSetupCompleted() throws NumberFormatException {
            RtspMediaPeriod.this.rtspClient.startPlayback(RtspMediaPeriod.this.pendingSeekPositionUs != C.TIME_UNSET ? Util.usToMs(RtspMediaPeriod.this.pendingSeekPositionUs) : RtspMediaPeriod.this.pendingSeekPositionUsForTcpRetry != C.TIME_UNSET ? Util.usToMs(RtspMediaPeriod.this.pendingSeekPositionUsForTcpRetry) : 0L);
        }

        @Override // androidx.media3.exoplayer.rtsp.RtspClient.SessionInfoListener
        public void onSessionTimelineRequestFailed(String str, @Nullable Throwable th) {
            RtspMediaPeriod.this.preparationError = th == null ? new IOException(str) : new IOException(str, th);
        }

        @Override // androidx.media3.exoplayer.rtsp.RtspClient.SessionInfoListener
        public void onSessionTimelineUpdated(RtspSessionTiming rtspSessionTiming, ImmutableList<RtspMediaTrack> immutableList) {
            for (int i2 = 0; i2 < immutableList.size(); i2++) {
                RtspMediaTrack rtspMediaTrack = immutableList.get(i2);
                RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
                RtspLoaderWrapper rtspLoaderWrapper = rtspMediaPeriod.new RtspLoaderWrapper(rtspMediaTrack, i2, rtspMediaPeriod.rtpDataChannelFactory);
                RtspMediaPeriod.this.rtspLoaderWrappers.add(rtspLoaderWrapper);
                rtspLoaderWrapper.startLoading();
            }
            RtspMediaPeriod.this.listener.onSourceInfoRefreshed(rtspSessionTiming);
        }

        @Override // androidx.media3.exoplayer.source.SampleQueue.UpstreamFormatChangedListener
        public void onUpstreamFormatChanged(Format format) {
            Handler handler = RtspMediaPeriod.this.handler;
            final RtspMediaPeriod rtspMediaPeriod = RtspMediaPeriod.this;
            handler.post(new Runnable() { // from class: androidx.media3.exoplayer.rtsp.f
                @Override // java.lang.Runnable
                public final void run() {
                    RtspMediaPeriod.w(rtspMediaPeriod);
                }
            });
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCompleted(RtpDataLoadable rtpDataLoadable, long j2, long j3) throws NumberFormatException {
            if (RtspMediaPeriod.this.getBufferedPositionUs() == 0) {
                if (RtspMediaPeriod.this.isUsingRtpTcp) {
                    return;
                }
                RtspMediaPeriod.this.retryWithRtpTcp();
                return;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= RtspMediaPeriod.this.rtspLoaderWrappers.size()) {
                    break;
                }
                RtspLoaderWrapper rtspLoaderWrapper = (RtspLoaderWrapper) RtspMediaPeriod.this.rtspLoaderWrappers.get(i2);
                if (rtspLoaderWrapper.loadInfo.loadable == rtpDataLoadable) {
                    rtspLoaderWrapper.cancelLoad();
                    break;
                }
                i2++;
            }
            RtspMediaPeriod.this.rtspClient.signalPlaybackEnded();
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(RtpDataLoadable rtpDataLoadable, long j2, long j3, IOException iOException, int i2) {
            if (!RtspMediaPeriod.this.prepared) {
                RtspMediaPeriod.this.preparationError = iOException;
            } else if (!(iOException.getCause() instanceof BindException)) {
                RtspMediaPeriod.this.playbackException = new RtspMediaSource.RtspPlaybackException(rtpDataLoadable.rtspMediaTrack.uri.toString(), iOException);
            } else if (RtspMediaPeriod.c(RtspMediaPeriod.this) < 3) {
                return Loader.RETRY;
            }
            return Loader.DONT_RETRY;
        }
    }

    interface Listener {
        void onSeekingUnsupported();

        void onSourceInfoRefreshed(RtspSessionTiming rtspSessionTiming);
    }

    final class RtpLoadInfo {
        private final RtpDataLoadable loadable;
        public final RtspMediaTrack mediaTrack;

        @Nullable
        private String transport;

        public RtpLoadInfo(RtspMediaTrack rtspMediaTrack, int i2, TrackOutput trackOutput, RtpDataChannel.Factory factory) {
            this.mediaTrack = rtspMediaTrack;
            this.loadable = new RtpDataLoadable(i2, rtspMediaTrack, new RtpDataLoadable.EventListener() { // from class: androidx.media3.exoplayer.rtsp.h
                @Override // androidx.media3.exoplayer.rtsp.RtpDataLoadable.EventListener
                public final void onTransportReady(String str, RtpDataChannel rtpDataChannel) throws NumberFormatException {
                    this.f5377a.lambda$new$0(str, rtpDataChannel);
                }
            }, new ExtractorOutputImpl(trackOutput), factory);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(String str, RtpDataChannel rtpDataChannel) throws NumberFormatException {
            this.transport = str;
            RtspMessageChannel.InterleavedBinaryDataListener interleavedBinaryDataListener = rtpDataChannel.getInterleavedBinaryDataListener();
            if (interleavedBinaryDataListener != null) {
                RtspMediaPeriod.this.rtspClient.registerInterleavedDataChannel(rtpDataChannel.getLocalPort(), interleavedBinaryDataListener);
                RtspMediaPeriod.this.isUsingRtpTcp = true;
            }
            RtspMediaPeriod.this.maybeSetupTracks();
        }

        public Uri getTrackUri() {
            return this.loadable.rtspMediaTrack.uri;
        }

        public String getTransport() {
            Assertions.checkStateNotNull(this.transport);
            return this.transport;
        }

        public boolean isTransportReady() {
            return this.transport != null;
        }
    }

    private final class RtspLoaderWrapper {
        private boolean canceled;
        public final RtpLoadInfo loadInfo;
        private final Loader loader;
        private boolean released;
        private final SampleQueue sampleQueue;

        public RtspLoaderWrapper(RtspMediaTrack rtspMediaTrack, int i2, RtpDataChannel.Factory factory) {
            this.loader = new Loader("ExoPlayer:RtspMediaPeriod:RtspLoaderWrapper " + i2);
            SampleQueue sampleQueueCreateWithoutDrm = SampleQueue.createWithoutDrm(RtspMediaPeriod.this.allocator);
            this.sampleQueue = sampleQueueCreateWithoutDrm;
            this.loadInfo = RtspMediaPeriod.this.new RtpLoadInfo(rtspMediaTrack, i2, sampleQueueCreateWithoutDrm, factory);
            sampleQueueCreateWithoutDrm.setUpstreamFormatChangeListener(RtspMediaPeriod.this.internalListener);
        }

        public void cancelLoad() {
            if (this.canceled) {
                return;
            }
            this.loadInfo.loadable.cancelLoad();
            this.canceled = true;
            RtspMediaPeriod.this.updateLoadingFinished();
        }

        public long getBufferedPositionUs() {
            return this.sampleQueue.getLargestQueuedTimestampUs();
        }

        public boolean isSampleQueueReady() {
            return this.sampleQueue.isReady(this.canceled);
        }

        public int read(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            return this.sampleQueue.read(formatHolder, decoderInputBuffer, i2, this.canceled);
        }

        public void release() {
            if (this.released) {
                return;
            }
            this.loader.release();
            this.sampleQueue.release();
            this.released = true;
        }

        public void resumeLoad() {
            Assertions.checkState(this.canceled);
            this.canceled = false;
            RtspMediaPeriod.this.updateLoadingFinished();
            startLoading();
        }

        public void seekTo(long j2) {
            if (this.canceled) {
                return;
            }
            this.loadInfo.loadable.resetForSeek();
            this.sampleQueue.reset();
            this.sampleQueue.setStartTimeUs(j2);
        }

        public int skipData(long j2) {
            int skipCount = this.sampleQueue.getSkipCount(j2, this.canceled);
            this.sampleQueue.skip(skipCount);
            return skipCount;
        }

        public void startLoading() {
            this.loader.startLoading(this.loadInfo.loadable, RtspMediaPeriod.this.internalListener, 0);
        }
    }

    private final class SampleStreamImpl implements SampleStream {
        private final int track;

        public SampleStreamImpl(int i2) {
            this.track = i2;
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public boolean isReady() {
            return RtspMediaPeriod.this.C(this.track);
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public void maybeThrowError() throws RtspMediaSource.RtspPlaybackException {
            if (RtspMediaPeriod.this.playbackException != null) {
                throw RtspMediaPeriod.this.playbackException;
            }
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i2) {
            return RtspMediaPeriod.this.D(this.track, formatHolder, decoderInputBuffer, i2);
        }

        @Override // androidx.media3.exoplayer.source.SampleStream
        public int skipData(long j2) {
            return RtspMediaPeriod.this.E(this.track, j2);
        }
    }

    public RtspMediaPeriod(Allocator allocator, RtpDataChannel.Factory factory, Uri uri, Listener listener, String str, SocketFactory socketFactory, boolean z2) {
        this.allocator = allocator;
        this.rtpDataChannelFactory = factory;
        this.listener = listener;
        InternalListener internalListener = new InternalListener();
        this.internalListener = internalListener;
        this.rtspClient = new RtspClient(internalListener, internalListener, str, uri, socketFactory, z2);
        this.rtspLoaderWrappers = new ArrayList();
        this.selectedLoadInfos = new ArrayList();
        this.pendingSeekPositionUs = C.TIME_UNSET;
        this.requestedSeekPositionUs = C.TIME_UNSET;
        this.pendingSeekPositionUsForTcpRetry = C.TIME_UNSET;
    }

    private static ImmutableList<TrackGroup> buildTrackGroups(ImmutableList<RtspLoaderWrapper> immutableList) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i2 = 0; i2 < immutableList.size(); i2++) {
            builder.add((ImmutableList.Builder) new TrackGroup(Integer.toString(i2), (Format) Assertions.checkNotNull(immutableList.get(i2).sampleQueue.getUpstreamFormat())));
        }
        return builder.build();
    }

    static /* synthetic */ int c(RtspMediaPeriod rtspMediaPeriod) {
        int i2 = rtspMediaPeriod.portBindingRetryCount;
        rtspMediaPeriod.portBindingRetryCount = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public RtpDataLoadable getLoadableByTrackUri(Uri uri) {
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            if (!this.rtspLoaderWrappers.get(i2).canceled) {
                RtpLoadInfo rtpLoadInfo = this.rtspLoaderWrappers.get(i2).loadInfo;
                if (rtpLoadInfo.getTrackUri().equals(uri)) {
                    return rtpLoadInfo.loadable;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSeekPending() {
        return this.pendingSeekPositionUs != C.TIME_UNSET;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeFinishPrepare() {
        if (this.released || this.prepared) {
            return;
        }
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            if (this.rtspLoaderWrappers.get(i2).sampleQueue.getUpstreamFormat() == null) {
                return;
            }
        }
        this.prepared = true;
        this.trackGroups = buildTrackGroups(ImmutableList.copyOf((Collection) this.rtspLoaderWrappers));
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeSetupTracks() throws NumberFormatException {
        boolean zIsTransportReady = true;
        for (int i2 = 0; i2 < this.selectedLoadInfos.size(); i2++) {
            zIsTransportReady &= this.selectedLoadInfos.get(i2).isTransportReady();
        }
        if (zIsTransportReady && this.trackSelected) {
            this.rtspClient.setupSelectedTracks(this.selectedLoadInfos);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public void retryWithRtpTcp() throws NumberFormatException {
        this.isUsingRtpTcp = true;
        this.rtspClient.retryWithRtpTcp();
        RtpDataChannel.Factory factoryCreateFallbackDataChannelFactory = this.rtpDataChannelFactory.createFallbackDataChannelFactory();
        if (factoryCreateFallbackDataChannelFactory == null) {
            this.playbackException = new RtspMediaSource.RtspPlaybackException("No fallback data channel factory for TCP retry");
            return;
        }
        ArrayList arrayList = new ArrayList(this.rtspLoaderWrappers.size());
        ArrayList arrayList2 = new ArrayList(this.selectedLoadInfos.size());
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i2);
            if (rtspLoaderWrapper.canceled) {
                arrayList.add(rtspLoaderWrapper);
            } else {
                RtspLoaderWrapper rtspLoaderWrapper2 = new RtspLoaderWrapper(rtspLoaderWrapper.loadInfo.mediaTrack, i2, factoryCreateFallbackDataChannelFactory);
                arrayList.add(rtspLoaderWrapper2);
                rtspLoaderWrapper2.startLoading();
                if (this.selectedLoadInfos.contains(rtspLoaderWrapper.loadInfo)) {
                    arrayList2.add(rtspLoaderWrapper2.loadInfo);
                }
            }
        }
        ImmutableList immutableListCopyOf = ImmutableList.copyOf((Collection) this.rtspLoaderWrappers);
        this.rtspLoaderWrappers.clear();
        this.rtspLoaderWrappers.addAll(arrayList);
        this.selectedLoadInfos.clear();
        this.selectedLoadInfos.addAll(arrayList2);
        for (int i3 = 0; i3 < immutableListCopyOf.size(); i3++) {
            ((RtspLoaderWrapper) immutableListCopyOf.get(i3)).cancelLoad();
        }
    }

    private boolean seekInsideBufferUs(long j2) {
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            if (!this.rtspLoaderWrappers.get(i2).sampleQueue.seekTo(j2, false)) {
                return false;
            }
        }
        return true;
    }

    private boolean suppressRead() {
        return this.notifyDiscontinuity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLoadingFinished() {
        this.loadingFinished = true;
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            this.loadingFinished &= this.rtspLoaderWrappers.get(i2).canceled;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void w(RtspMediaPeriod rtspMediaPeriod) {
        rtspMediaPeriod.maybeFinishPrepare();
    }

    boolean C(int i2) {
        return !suppressRead() && this.rtspLoaderWrappers.get(i2).isSampleQueueReady();
    }

    int D(int i2, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, int i3) {
        if (suppressRead()) {
            return -3;
        }
        return this.rtspLoaderWrappers.get(i2).read(formatHolder, decoderInputBuffer, i3);
    }

    int E(int i2, long j2) {
        if (suppressRead()) {
            return -3;
        }
        return this.rtspLoaderWrappers.get(i2).skipData(j2);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean continueLoading(LoadingInfo loadingInfo) {
        return isLoading();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
        if (isSeekPending()) {
            return;
        }
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i2);
            if (!rtspLoaderWrapper.canceled) {
                rtspLoaderWrapper.sampleQueue.discardTo(j2, z2, true);
            }
        }
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getBufferedPositionUs() {
        if (this.loadingFinished || this.rtspLoaderWrappers.isEmpty()) {
            return Long.MIN_VALUE;
        }
        long j2 = this.requestedSeekPositionUs;
        if (j2 != C.TIME_UNSET) {
            return j2;
        }
        boolean z2 = true;
        long jMin = Long.MAX_VALUE;
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i2);
            if (!rtspLoaderWrapper.canceled) {
                jMin = Math.min(jMin, rtspLoaderWrapper.getBufferedPositionUs());
                z2 = false;
            }
        }
        if (z2 || jMin == Long.MIN_VALUE) {
            return 0L;
        }
        return jMin;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return getBufferedPositionUs();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public /* bridge */ /* synthetic */ List getStreamKeys(List list) {
        return getStreamKeys((List<ExoTrackSelection>) list);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        Assertions.checkState(this.prepared);
        return new TrackGroupArray((TrackGroup[]) ((ImmutableList) Assertions.checkNotNull(this.trackGroups)).toArray(new TrackGroup[0]));
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean isLoading() {
        return !this.loadingFinished && (this.rtspClient.getState() == 2 || this.rtspClient.getState() == 1);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        IOException iOException = this.preparationError;
        if (iOException != null) {
            throw iOException;
        }
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) throws NumberFormatException, IOException {
        this.callback = callback;
        try {
            this.rtspClient.start();
        } catch (IOException e2) {
            this.preparationError = e2;
            Util.closeQuietly(this.rtspClient);
        }
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long readDiscontinuity() {
        if (!this.notifyDiscontinuity) {
            return C.TIME_UNSET;
        }
        this.notifyDiscontinuity = false;
        return 0L;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
    }

    public void release() {
        for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
            this.rtspLoaderWrappers.get(i2).release();
        }
        Util.closeQuietly(this.rtspClient);
        this.released = true;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long seekToUs(long j2) throws NumberFormatException {
        if (getBufferedPositionUs() == 0 && !this.isUsingRtpTcp) {
            this.pendingSeekPositionUsForTcpRetry = j2;
            return j2;
        }
        discardBuffer(j2, false);
        this.requestedSeekPositionUs = j2;
        if (isSeekPending()) {
            int state = this.rtspClient.getState();
            if (state == 1) {
                return j2;
            }
            if (state != 2) {
                throw new IllegalStateException();
            }
            this.pendingSeekPositionUs = j2;
            this.rtspClient.seekToUs(j2);
            return j2;
        }
        if (seekInsideBufferUs(j2)) {
            return j2;
        }
        this.pendingSeekPositionUs = j2;
        if (this.loadingFinished) {
            for (int i2 = 0; i2 < this.rtspLoaderWrappers.size(); i2++) {
                this.rtspLoaderWrappers.get(i2).resumeLoad();
            }
            if (this.isUsingRtpTcp) {
                this.rtspClient.startPlayback(Util.usToMs(j2));
            } else {
                this.rtspClient.seekToUs(j2);
            }
        } else {
            this.rtspClient.seekToUs(j2);
        }
        for (int i3 = 0; i3 < this.rtspLoaderWrappers.size(); i3++) {
            this.rtspLoaderWrappers.get(i3).seekTo(j2);
        }
        return j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) throws NumberFormatException {
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            if (sampleStreamArr[i2] != null && (exoTrackSelectionArr[i2] == null || !zArr[i2])) {
                sampleStreamArr[i2] = null;
            }
        }
        this.selectedLoadInfos.clear();
        for (int i3 = 0; i3 < exoTrackSelectionArr.length; i3++) {
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i3];
            if (exoTrackSelection != null) {
                TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
                int iIndexOf = ((ImmutableList) Assertions.checkNotNull(this.trackGroups)).indexOf(trackGroup);
                this.selectedLoadInfos.add(((RtspLoaderWrapper) Assertions.checkNotNull(this.rtspLoaderWrappers.get(iIndexOf))).loadInfo);
                if (this.trackGroups.contains(trackGroup) && sampleStreamArr[i3] == null) {
                    sampleStreamArr[i3] = new SampleStreamImpl(iIndexOf);
                    zArr2[i3] = true;
                }
            }
        }
        for (int i4 = 0; i4 < this.rtspLoaderWrappers.size(); i4++) {
            RtspLoaderWrapper rtspLoaderWrapper = this.rtspLoaderWrappers.get(i4);
            if (!this.selectedLoadInfos.contains(rtspLoaderWrapper.loadInfo)) {
                rtspLoaderWrapper.cancelLoad();
            }
        }
        this.trackSelected = true;
        if (j2 != 0) {
            this.requestedSeekPositionUs = j2;
            this.pendingSeekPositionUs = j2;
            this.pendingSeekPositionUsForTcpRetry = j2;
        }
        maybeSetupTracks();
        return j2;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public ImmutableList<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        return ImmutableList.of();
    }
}
