package androidx.media3.exoplayer.video;

import android.os.SystemClock;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.TimedValueQueue;
import androidx.media3.common.util.TraceUtil;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.CryptoConfig;
import androidx.media3.decoder.Decoder;
import androidx.media3.decoder.DecoderException;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.decoder.VideoDecoderOutputBuffer;
import androidx.media3.exoplayer.BaseRenderer;
import androidx.media3.exoplayer.DecoderCounters;
import androidx.media3.exoplayer.DecoderReuseEvaluation;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import androidx.media3.exoplayer.drm.DrmSession;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.video.VideoRendererEventListener;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class DecoderVideoRenderer extends BaseRenderer {
    private static final int REINITIALIZATION_STATE_NONE = 0;
    private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 2;
    private static final String TAG = "DecoderVideoRenderer";

    /* renamed from: a, reason: collision with root package name */
    protected DecoderCounters f5540a;
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private int consecutiveDroppedFrameCount;

    @Nullable
    private Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder;

    @Nullable
    private DrmSession decoderDrmSession;
    private boolean decoderReceivedBuffers;
    private int decoderReinitializationState;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;
    private int firstFrameState;
    private final DecoderInputBuffer flagsOnlyBuffer;
    private final TimedValueQueue<Format> formatQueue;

    @Nullable
    private VideoFrameMetadataListener frameMetadataListener;
    private long initialPositionUs;

    @Nullable
    private DecoderInputBuffer inputBuffer;

    @Nullable
    private Format inputFormat;
    private boolean inputStreamEnded;
    private long joiningDeadlineMs;
    private long lastRenderTimeUs;
    private final int maxDroppedFramesToNotify;

    @Nullable
    private Object output;

    @Nullable
    private VideoDecoderOutputBuffer outputBuffer;

    @Nullable
    private VideoDecoderOutputBufferRenderer outputBufferRenderer;

    @Nullable
    private Format outputFormat;
    private int outputMode;
    private boolean outputStreamEnded;
    private long outputStreamOffsetUs;

    @Nullable
    private Surface outputSurface;

    @Nullable
    private VideoSize reportedVideoSize;

    @Nullable
    private DrmSession sourceDrmSession;
    private boolean waitingForFirstSampleInFormat;

    private boolean drainOutputBuffer(long j2, long j3) throws ExoPlaybackException, DecoderException {
        if (this.outputBuffer == null) {
            VideoDecoderOutputBuffer videoDecoderOutputBuffer = (VideoDecoderOutputBuffer) ((Decoder) Assertions.checkNotNull(this.decoder)).dequeueOutputBuffer();
            this.outputBuffer = videoDecoderOutputBuffer;
            if (videoDecoderOutputBuffer == null) {
                return false;
            }
            DecoderCounters decoderCounters = this.f5540a;
            int i2 = decoderCounters.skippedOutputBufferCount;
            int i3 = videoDecoderOutputBuffer.skippedOutputBufferCount;
            decoderCounters.skippedOutputBufferCount = i2 + i3;
            this.buffersInCodecCount -= i3;
        }
        if (!this.outputBuffer.isEndOfStream()) {
            boolean zProcessOutputBuffer = processOutputBuffer(j2, j3);
            if (zProcessOutputBuffer) {
                onProcessedOutputBuffer(((VideoDecoderOutputBuffer) Assertions.checkNotNull(this.outputBuffer)).timeUs);
                this.outputBuffer = null;
            }
            return zProcessOutputBuffer;
        }
        if (this.decoderReinitializationState == 2) {
            releaseDecoder();
            maybeInitDecoder();
        } else {
            this.outputBuffer.release();
            this.outputBuffer = null;
            this.outputStreamEnded = true;
        }
        return false;
    }

    private boolean feedInputBuffer() throws ExoPlaybackException, DecoderException {
        Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.decoder;
        if (decoder == null || this.decoderReinitializationState == 2 || this.inputStreamEnded) {
            return false;
        }
        if (this.inputBuffer == null) {
            DecoderInputBuffer decoderInputBufferDequeueInputBuffer = decoder.dequeueInputBuffer();
            this.inputBuffer = decoderInputBufferDequeueInputBuffer;
            if (decoderInputBufferDequeueInputBuffer == null) {
                return false;
            }
        }
        DecoderInputBuffer decoderInputBuffer = (DecoderInputBuffer) Assertions.checkNotNull(this.inputBuffer);
        if (this.decoderReinitializationState == 1) {
            decoderInputBuffer.setFlags(4);
            ((Decoder) Assertions.checkNotNull(this.decoder)).queueInputBuffer(decoderInputBuffer);
            this.inputBuffer = null;
            this.decoderReinitializationState = 2;
            return false;
        }
        FormatHolder formatHolderE = e();
        int iV = v(formatHolderE, decoderInputBuffer, 0);
        if (iV == -5) {
            onInputFormatChanged(formatHolderE);
            return true;
        }
        if (iV != -4) {
            if (iV == -3) {
                return false;
            }
            throw new IllegalStateException();
        }
        if (decoderInputBuffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            ((Decoder) Assertions.checkNotNull(this.decoder)).queueInputBuffer(decoderInputBuffer);
            this.inputBuffer = null;
            return false;
        }
        if (this.waitingForFirstSampleInFormat) {
            this.formatQueue.add(decoderInputBuffer.timeUs, (Format) Assertions.checkNotNull(this.inputFormat));
            this.waitingForFirstSampleInFormat = false;
        }
        decoderInputBuffer.flip();
        decoderInputBuffer.format = this.inputFormat;
        B(decoderInputBuffer);
        ((Decoder) Assertions.checkNotNull(this.decoder)).queueInputBuffer(decoderInputBuffer);
        this.buffersInCodecCount++;
        this.decoderReceivedBuffers = true;
        this.f5540a.queuedInputBufferCount++;
        this.inputBuffer = null;
        return true;
    }

    private boolean hasOutput() {
        return this.outputMode != -1;
    }

    private static boolean isBufferLate(long j2) {
        return j2 < -30000;
    }

    private static boolean isBufferVeryLate(long j2) {
        return j2 < -500000;
    }

    private void lowerFirstFrameState(int i2) {
        this.firstFrameState = Math.min(this.firstFrameState, i2);
    }

    private void maybeInitDecoder() throws ExoPlaybackException {
        CryptoConfig cryptoConfig;
        if (this.decoder != null) {
            return;
        }
        setDecoderDrmSession(this.sourceDrmSession);
        DrmSession drmSession = this.decoderDrmSession;
        if (drmSession != null) {
            cryptoConfig = drmSession.getCryptoConfig();
            if (cryptoConfig == null && this.decoderDrmSession.getError() == null) {
                return;
            }
        } else {
            cryptoConfig = null;
        }
        try {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            Decoder decoderY = y((Format) Assertions.checkNotNull(this.inputFormat), cryptoConfig);
            this.decoder = decoderY;
            decoderY.setOutputStartTimeUs(g());
            E(this.outputMode);
            long jElapsedRealtime2 = SystemClock.elapsedRealtime();
            this.eventDispatcher.decoderInitialized(((Decoder) Assertions.checkNotNull(this.decoder)).getName(), jElapsedRealtime2, jElapsedRealtime2 - jElapsedRealtime);
            this.f5540a.decoderInitCount++;
        } catch (DecoderException e2) {
            Log.e(TAG, "Video codec error", e2);
            this.eventDispatcher.videoCodecError(e2);
            throw a(e2, this.inputFormat, 4001);
        } catch (OutOfMemoryError e3) {
            throw a(e3, this.inputFormat, 4001);
        }
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, jElapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = jElapsedRealtime;
        }
    }

    private void maybeNotifyRenderedFirstFrame() {
        if (this.firstFrameState != 3) {
            this.firstFrameState = 3;
            Object obj = this.output;
            if (obj != null) {
                this.eventDispatcher.renderedFirstFrame(obj);
            }
        }
    }

    private void maybeNotifyVideoSizeChanged(int i2, int i3) {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null && videoSize.width == i2 && videoSize.height == i3) {
            return;
        }
        VideoSize videoSize2 = new VideoSize(i2, i3);
        this.reportedVideoSize = videoSize2;
        this.eventDispatcher.videoSizeChanged(videoSize2);
    }

    private void maybeRenotifyRenderedFirstFrame() {
        Object obj;
        if (this.firstFrameState != 3 || (obj = this.output) == null) {
            return;
        }
        this.eventDispatcher.renderedFirstFrame(obj);
    }

    private void maybeRenotifyVideoSizeChanged() {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null) {
            this.eventDispatcher.videoSizeChanged(videoSize);
        }
    }

    private void onOutputChanged() {
        maybeRenotifyVideoSizeChanged();
        lowerFirstFrameState(1);
        if (getState() == 2) {
            setJoiningDeadlineMs();
        }
    }

    private void onOutputRemoved() {
        this.reportedVideoSize = null;
        lowerFirstFrameState(1);
    }

    private void onOutputReset() {
        maybeRenotifyVideoSizeChanged();
        maybeRenotifyRenderedFirstFrame();
    }

    private boolean processOutputBuffer(long j2, long j3) throws ExoPlaybackException, DecoderException {
        if (this.initialPositionUs == C.TIME_UNSET) {
            this.initialPositionUs = j2;
        }
        VideoDecoderOutputBuffer videoDecoderOutputBuffer = (VideoDecoderOutputBuffer) Assertions.checkNotNull(this.outputBuffer);
        long j4 = videoDecoderOutputBuffer.timeUs;
        long j5 = j4 - j2;
        if (!hasOutput()) {
            if (!isBufferLate(j5)) {
                return false;
            }
            I(videoDecoderOutputBuffer);
            return true;
        }
        Format formatPollFloor = this.formatQueue.pollFloor(j4);
        if (formatPollFloor != null) {
            this.outputFormat = formatPollFloor;
        } else if (this.outputFormat == null) {
            this.outputFormat = this.formatQueue.pollFirst();
        }
        long j6 = j4 - this.outputStreamOffsetUs;
        if (shouldForceRender(j5)) {
            C(videoDecoderOutputBuffer, j6, (Format) Assertions.checkNotNull(this.outputFormat));
            return true;
        }
        if (getState() != 2 || j2 == this.initialPositionUs || (F(j5, j3) && A(j2))) {
            return false;
        }
        if (G(j5, j3)) {
            z(videoDecoderOutputBuffer);
            return true;
        }
        if (j5 < 30000) {
            C(videoDecoderOutputBuffer, j6, (Format) Assertions.checkNotNull(this.outputFormat));
            return true;
        }
        return false;
    }

    private void setDecoderDrmSession(@Nullable DrmSession drmSession) {
        androidx.media3.exoplayer.drm.i.b(this.decoderDrmSession, drmSession);
        this.decoderDrmSession = drmSession;
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C.TIME_UNSET;
    }

    private void setSourceDrmSession(@Nullable DrmSession drmSession) {
        androidx.media3.exoplayer.drm.i.b(this.sourceDrmSession, drmSession);
        this.sourceDrmSession = drmSession;
    }

    private boolean shouldForceRender(long j2) {
        boolean z2 = getState() == 2;
        int i2 = this.firstFrameState;
        if (i2 == 0) {
            return z2;
        }
        if (i2 == 1) {
            return true;
        }
        if (i2 == 3) {
            return z2 && H(j2, Util.msToUs(SystemClock.elapsedRealtime()) - this.lastRenderTimeUs);
        }
        throw new IllegalStateException();
    }

    protected boolean A(long j2) throws ExoPlaybackException {
        int iW = w(j2);
        if (iW == 0) {
            return false;
        }
        this.f5540a.droppedToKeyframeCount++;
        J(iW, this.buffersInCodecCount);
        flushDecoder();
        return true;
    }

    protected void B(DecoderInputBuffer decoderInputBuffer) {
    }

    protected void C(VideoDecoderOutputBuffer videoDecoderOutputBuffer, long j2, Format format) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j2, c().nanoTime(), format, null);
        }
        this.lastRenderTimeUs = Util.msToUs(SystemClock.elapsedRealtime());
        int i2 = videoDecoderOutputBuffer.mode;
        boolean z2 = i2 == 1 && this.outputSurface != null;
        boolean z3 = i2 == 0 && this.outputBufferRenderer != null;
        if (!z3 && !z2) {
            z(videoDecoderOutputBuffer);
            return;
        }
        maybeNotifyVideoSizeChanged(videoDecoderOutputBuffer.width, videoDecoderOutputBuffer.height);
        if (z3) {
            ((VideoDecoderOutputBufferRenderer) Assertions.checkNotNull(this.outputBufferRenderer)).setOutputBuffer(videoDecoderOutputBuffer);
        } else {
            D(videoDecoderOutputBuffer, (Surface) Assertions.checkNotNull(this.outputSurface));
        }
        this.consecutiveDroppedFrameCount = 0;
        this.f5540a.renderedOutputBufferCount++;
        maybeNotifyRenderedFirstFrame();
    }

    protected abstract void D(VideoDecoderOutputBuffer videoDecoderOutputBuffer, Surface surface);

    protected abstract void E(int i2);

    protected boolean F(long j2, long j3) {
        return isBufferVeryLate(j2);
    }

    protected boolean G(long j2, long j3) {
        return isBufferLate(j2);
    }

    protected boolean H(long j2, long j3) {
        return isBufferLate(j2) && j3 > SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US;
    }

    protected void I(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        this.f5540a.skippedOutputBufferCount++;
        videoDecoderOutputBuffer.release();
    }

    protected void J(int i2, int i3) {
        DecoderCounters decoderCounters = this.f5540a;
        decoderCounters.droppedInputBufferCount += i2;
        int i4 = i2 + i3;
        decoderCounters.droppedBufferCount += i4;
        this.droppedFrames += i4;
        int i5 = this.consecutiveDroppedFrameCount + i4;
        this.consecutiveDroppedFrameCount = i5;
        decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(i5, decoderCounters.maxConsecutiveDroppedBufferCount);
        int i6 = this.maxDroppedFramesToNotify;
        if (i6 <= 0 || this.droppedFrames < i6) {
            return;
        }
        maybeNotifyDroppedFrames();
    }

    @Override // androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.Renderer
    public void enableMayRenderStartOfStream() {
        if (this.firstFrameState == 0) {
            this.firstFrameState = 1;
        }
    }

    protected void flushDecoder() throws ExoPlaybackException {
        this.buffersInCodecCount = 0;
        if (this.decoderReinitializationState != 0) {
            releaseDecoder();
            maybeInitDecoder();
            return;
        }
        this.inputBuffer = null;
        VideoDecoderOutputBuffer videoDecoderOutputBuffer = this.outputBuffer;
        if (videoDecoderOutputBuffer != null) {
            videoDecoderOutputBuffer.release();
            this.outputBuffer = null;
        }
        Decoder decoder = (Decoder) Assertions.checkNotNull(this.decoder);
        decoder.flush();
        decoder.setOutputStartTimeUs(g());
        this.decoderReceivedBuffers = false;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.PlayerMessage.Target
    public void handleMessage(int i2, @Nullable Object obj) throws ExoPlaybackException {
        if (i2 == 1) {
            setOutput(obj);
        } else if (i2 == 7) {
            this.frameMetadataListener = (VideoFrameMetadataListener) obj;
        } else {
            super.handleMessage(i2, obj);
        }
    }

    @Override // androidx.media3.exoplayer.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // androidx.media3.exoplayer.Renderer
    public boolean isReady() {
        if (this.inputFormat != null && ((j() || this.outputBuffer != null) && (this.firstFrameState == 3 || !hasOutput()))) {
            this.joiningDeadlineMs = C.TIME_UNSET;
            return true;
        }
        if (this.joiningDeadlineMs == C.TIME_UNSET) {
            return false;
        }
        if (SystemClock.elapsedRealtime() < this.joiningDeadlineMs) {
            return true;
        }
        this.joiningDeadlineMs = C.TIME_UNSET;
        return false;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void k() {
        this.inputFormat = null;
        this.reportedVideoSize = null;
        lowerFirstFrameState(0);
        try {
            setSourceDrmSession(null);
            releaseDecoder();
        } finally {
            this.eventDispatcher.disabled(this.f5540a);
        }
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void l(boolean z2, boolean z3) {
        DecoderCounters decoderCounters = new DecoderCounters();
        this.f5540a = decoderCounters;
        this.eventDispatcher.enabled(decoderCounters);
        this.firstFrameState = z3 ? 1 : 0;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void n(long j2, boolean z2) throws ExoPlaybackException {
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        lowerFirstFrameState(1);
        this.initialPositionUs = C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        if (this.decoder != null) {
            flushDecoder();
        }
        if (z2) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C.TIME_UNSET;
        }
        this.formatQueue.clear();
    }

    protected void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        this.waitingForFirstSampleInFormat = true;
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        setSourceDrmSession(formatHolder.drmSession);
        Format format2 = this.inputFormat;
        this.inputFormat = format;
        Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.decoder;
        if (decoder == null) {
            maybeInitDecoder();
            this.eventDispatcher.inputFormatChanged((Format) Assertions.checkNotNull(this.inputFormat), null);
            return;
        }
        DecoderReuseEvaluation decoderReuseEvaluation = this.sourceDrmSession != this.decoderDrmSession ? new DecoderReuseEvaluation(decoder.getName(), (Format) Assertions.checkNotNull(format2), format, 0, 128) : x(decoder.getName(), (Format) Assertions.checkNotNull(format2), format);
        if (decoderReuseEvaluation.result == 0) {
            if (this.decoderReceivedBuffers) {
                this.decoderReinitializationState = 1;
            } else {
                releaseDecoder();
                maybeInitDecoder();
            }
        }
        this.eventDispatcher.inputFormatChanged((Format) Assertions.checkNotNull(this.inputFormat), decoderReuseEvaluation);
    }

    protected void onProcessedOutputBuffer(long j2) {
        this.buffersInCodecCount--;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void r() {
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderTimeUs = Util.msToUs(SystemClock.elapsedRealtime());
    }

    protected void releaseDecoder() {
        this.inputBuffer = null;
        this.outputBuffer = null;
        this.decoderReinitializationState = 0;
        this.decoderReceivedBuffers = false;
        this.buffersInCodecCount = 0;
        Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.decoder;
        if (decoder != null) {
            this.f5540a.decoderReleaseCount++;
            decoder.release();
            this.eventDispatcher.decoderReleased(this.decoder.getName());
            this.decoder = null;
        }
        setDecoderDrmSession(null);
    }

    @Override // androidx.media3.exoplayer.Renderer
    public void render(long j2, long j3) throws ExoPlaybackException {
        if (this.outputStreamEnded) {
            return;
        }
        if (this.inputFormat == null) {
            FormatHolder formatHolderE = e();
            this.flagsOnlyBuffer.clear();
            int iV = v(formatHolderE, this.flagsOnlyBuffer, 2);
            if (iV != -5) {
                if (iV == -4) {
                    Assertions.checkState(this.flagsOnlyBuffer.isEndOfStream());
                    this.inputStreamEnded = true;
                    this.outputStreamEnded = true;
                    return;
                }
                return;
            }
            onInputFormatChanged(formatHolderE);
        }
        maybeInitDecoder();
        if (this.decoder != null) {
            try {
                TraceUtil.beginSection("drainAndFeed");
                while (drainOutputBuffer(j2, j3)) {
                }
                while (feedInputBuffer()) {
                }
                TraceUtil.endSection();
                this.f5540a.ensureUpdated();
            } catch (DecoderException e2) {
                Log.e(TAG, "Video codec error", e2);
                this.eventDispatcher.videoCodecError(e2);
                throw a(e2, this.inputFormat, PlaybackException.ERROR_CODE_DECODING_FAILED);
            }
        }
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void s() {
        this.joiningDeadlineMs = C.TIME_UNSET;
        maybeNotifyDroppedFrames();
    }

    protected final void setOutput(Object obj) {
        if (obj instanceof Surface) {
            this.outputSurface = (Surface) obj;
            this.outputBufferRenderer = null;
            this.outputMode = 1;
        } else if (obj instanceof VideoDecoderOutputBufferRenderer) {
            this.outputSurface = null;
            this.outputBufferRenderer = (VideoDecoderOutputBufferRenderer) obj;
            this.outputMode = 0;
        } else {
            this.outputSurface = null;
            this.outputBufferRenderer = null;
            this.outputMode = -1;
            obj = null;
        }
        if (this.output == obj) {
            if (obj != null) {
                onOutputReset();
                return;
            }
            return;
        }
        this.output = obj;
        if (obj == null) {
            onOutputRemoved();
            return;
        }
        if (this.decoder != null) {
            E(this.outputMode);
        }
        onOutputChanged();
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void t(Format[] formatArr, long j2, long j3, MediaSource.MediaPeriodId mediaPeriodId) {
        this.outputStreamOffsetUs = j3;
        super.t(formatArr, j2, j3, mediaPeriodId);
    }

    protected DecoderReuseEvaluation x(String str, Format format, Format format2) {
        return new DecoderReuseEvaluation(str, format, format2, 0, 1);
    }

    protected abstract Decoder y(Format format, CryptoConfig cryptoConfig);

    protected void z(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        J(0, 1);
        videoDecoderOutputBuffer.release();
    }
}
