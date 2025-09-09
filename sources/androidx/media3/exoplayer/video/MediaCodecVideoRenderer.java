package androidx.media3.exoplayer.video;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.Display;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media3.common.C;
import androidx.media3.common.Effect;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.MediaFormatUtil;
import androidx.media3.common.util.Size;
import androidx.media3.common.util.TraceUtil;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.exoplayer.DecoderCounters;
import androidx.media3.exoplayer.DecoderReuseEvaluation;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.FormatHolder;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import androidx.media3.exoplayer.mediacodec.MediaCodecAdapter;
import androidx.media3.exoplayer.mediacodec.MediaCodecDecoderException;
import androidx.media3.exoplayer.mediacodec.MediaCodecInfo;
import androidx.media3.exoplayer.mediacodec.MediaCodecRenderer;
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector;
import androidx.media3.exoplayer.mediacodec.MediaCodecUtil;
import androidx.media3.exoplayer.v2;
import androidx.media3.exoplayer.video.CompositingVideoSinkProvider;
import androidx.media3.exoplayer.video.VideoFrameReleaseControl;
import androidx.media3.exoplayer.video.VideoRendererEventListener;
import androidx.media3.exoplayer.video.VideoSink;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.MoreExecutors;
import com.huawei.hms.push.constant.RemoteMessageConst;
import io.flutter.plugin.platform.PlatformPlugin;
import java.nio.ByteBuffer;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.joda.time.DateTimeConstants;

@UnstableApi
/* loaded from: classes2.dex */
public class MediaCodecVideoRenderer extends MediaCodecRenderer implements VideoFrameReleaseControl.FrameTimingEvaluator {
    private static final int HEVC_MAX_INPUT_SIZE_THRESHOLD = 2097152;
    private static final float INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR = 1.5f;
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final long MIN_EARLY_US_LATE_THRESHOLD = -30000;
    private static final long MIN_EARLY_US_VERY_LATE_THRESHOLD = -500000;
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = {1920, 1600, DateTimeConstants.MINUTES_PER_DAY, PlatformPlugin.DEFAULT_SYSTEM_UI, 960, 854, 640, 540, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static final long TUNNELING_EOS_PRESENTATION_TIME_US = Long.MAX_VALUE;
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;

    /* renamed from: b, reason: collision with root package name */
    OnFrameRenderedListenerV23 f5541b;
    private int buffersInCodecCount;
    private boolean codecHandlesHdr10PlusOutOfBandMetadata;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private VideoSize decodedVideoSize;
    private final boolean deviceNeedsNoPostProcessWorkaround;

    @Nullable
    private Surface displaySurface;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;

    @Nullable
    private VideoFrameMetadataListener frameMetadataListener;
    private boolean hasSetVideoSink;
    private boolean haveReportedFirstFrameRenderedForCurrentSurface;
    private long lastFrameReleaseTimeNs;
    private final int maxDroppedFramesToNotify;
    private Size outputResolution;
    private final boolean ownsVideoSink;

    @Nullable
    private PlaceholderSurface placeholderSurface;
    private int rendererPriority;

    @Nullable
    private VideoSize reportedVideoSize;
    private int scalingMode;
    private long totalVideoFrameProcessingOffsetUs;
    private boolean tunneling;
    private int tunnelingAudioSessionId;
    private List<Effect> videoEffects;
    private int videoFrameProcessingOffsetCount;
    private final VideoFrameReleaseControl videoFrameReleaseControl;
    private final VideoFrameReleaseControl.FrameReleaseInfo videoFrameReleaseInfo;
    private VideoSink videoSink;

    @Nullable
    private final VideoSinkProvider videoSinkProvider;

    @RequiresApi(26)
    private static final class Api26 {
        private Api26() {
        }

        @DoNotInline
        public static boolean doesDisplaySupportDolbyVision(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            Display display = displayManager != null ? displayManager.getDisplay(0) : null;
            if (display == null || !display.isHdr()) {
                return false;
            }
            for (int i2 : display.getHdrCapabilities().getSupportedHdrTypes()) {
                if (i2 == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    protected static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int i2, int i3, int i4) {
            this.width = i2;
            this.height = i3;
            this.inputSize = i4;
        }
    }

    @RequiresApi(23)
    private final class OnFrameRenderedListenerV23 implements MediaCodecAdapter.OnFrameRenderedListener, Handler.Callback {
        private static final int HANDLE_FRAME_RENDERED = 0;
        private final Handler handler;

        public OnFrameRenderedListenerV23(MediaCodecAdapter mediaCodecAdapter) {
            Handler handlerCreateHandlerForCurrentLooper = Util.createHandlerForCurrentLooper(this);
            this.handler = handlerCreateHandlerForCurrentLooper;
            mediaCodecAdapter.setOnFrameRenderedListener(this, handlerCreateHandlerForCurrentLooper);
        }

        private void handleFrameRendered(long j2) {
            MediaCodecVideoRenderer mediaCodecVideoRenderer = MediaCodecVideoRenderer.this;
            if (this != mediaCodecVideoRenderer.f5541b || mediaCodecVideoRenderer.C() == null) {
                return;
            }
            if (j2 == Long.MAX_VALUE) {
                MediaCodecVideoRenderer.this.onProcessedTunneledEndOfStream();
                return;
            }
            try {
                MediaCodecVideoRenderer.this.D0(j2);
            } catch (ExoPlaybackException e2) {
                MediaCodecVideoRenderer.this.i0(e2);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            handleFrameRendered(Util.toLong(message.arg1, message.arg2));
            return true;
        }

        @Override // androidx.media3.exoplayer.mediacodec.MediaCodecAdapter.OnFrameRenderedListener
        public void onFrameRendered(MediaCodecAdapter mediaCodecAdapter, long j2, long j3) {
            if (Util.SDK_INT >= 30) {
                handleFrameRendered(j2);
            } else {
                this.handler.sendMessageAtFrontOfQueue(Message.obtain(this.handler, 0, (int) (j2 >> 32), (int) j2));
            }
        }
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0L);
    }

    protected static int A0(MediaCodecInfo mediaCodecInfo, Format format) {
        if (format.maxInputSize == -1) {
            return getCodecMaxInputSize(mediaCodecInfo, format);
        }
        int size = format.initializationData.size();
        int length = 0;
        for (int i2 = 0; i2 < size; i2++) {
            length += format.initializationData.get(i2).length;
        }
        return format.maxInputSize + length;
    }

    private static boolean codecAppliesRotation() {
        return Util.SDK_INT >= 21;
    }

    @RequiresApi(21)
    private static void configureTunnelingV21(MediaFormat mediaFormat, int i2) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", i2);
    }

    private static boolean deviceNeedsNoPostProcessWorkaround() {
        return "NVIDIA".equals(Util.MANUFACTURER);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0110  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean evaluateDeviceNeedsSetOutputSurfaceWorkaround() {
        /*
            Method dump skipped, instructions count: 3180
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.video.MediaCodecVideoRenderer.evaluateDeviceNeedsSetOutputSurfaceWorkaround():boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getCodecMaxInputSize(androidx.media3.exoplayer.mediacodec.MediaCodecInfo r10, androidx.media3.common.Format r11) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.video.MediaCodecVideoRenderer.getCodecMaxInputSize(androidx.media3.exoplayer.mediacodec.MediaCodecInfo, androidx.media3.common.Format):int");
    }

    @Nullable
    private static Point getCodecMaxSize(MediaCodecInfo mediaCodecInfo, Format format) {
        int i2 = format.height;
        int i3 = format.width;
        boolean z2 = i2 > i3;
        int i4 = z2 ? i2 : i3;
        if (z2) {
            i2 = i3;
        }
        float f2 = i2 / i4;
        for (int i5 : STANDARD_LONG_EDGE_VIDEO_PX) {
            int i6 = (int) (i5 * f2);
            if (i5 <= i4 || i6 <= i2) {
                break;
            }
            if (Util.SDK_INT >= 21) {
                int i7 = z2 ? i6 : i5;
                if (!z2) {
                    i5 = i6;
                }
                Point pointAlignVideoSizeV21 = mediaCodecInfo.alignVideoSizeV21(i7, i5);
                float f3 = format.frameRate;
                if (pointAlignVideoSizeV21 != null && mediaCodecInfo.isVideoSizeAndRateSupportedV21(pointAlignVideoSizeV21.x, pointAlignVideoSizeV21.y, f3)) {
                    return pointAlignVideoSizeV21;
                }
            } else {
                try {
                    int iCeilDivide = Util.ceilDivide(i5, 16) * 16;
                    int iCeilDivide2 = Util.ceilDivide(i6, 16) * 16;
                    if (iCeilDivide * iCeilDivide2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        int i8 = z2 ? iCeilDivide2 : iCeilDivide;
                        if (!z2) {
                            iCeilDivide = iCeilDivide2;
                        }
                        return new Point(i8, iCeilDivide);
                    }
                } catch (MediaCodecUtil.DecoderQueryException unused) {
                }
            }
        }
        return null;
    }

    private static List<MediaCodecInfo> getDecoderInfos(Context context, MediaCodecSelector mediaCodecSelector, Format format, boolean z2, boolean z3) throws MediaCodecUtil.DecoderQueryException {
        String str = format.sampleMimeType;
        if (str == null) {
            return ImmutableList.of();
        }
        if (Util.SDK_INT >= 26 && MimeTypes.VIDEO_DOLBY_VISION.equals(str) && !Api26.doesDisplaySupportDolbyVision(context)) {
            List<MediaCodecInfo> alternativeDecoderInfos = MediaCodecUtil.getAlternativeDecoderInfos(mediaCodecSelector, format, z2, z3);
            if (!alternativeDecoderInfos.isEmpty()) {
                return alternativeDecoderInfos;
            }
        }
        return MediaCodecUtil.getDecoderInfosSoftMatch(mediaCodecSelector, format, z2, z3);
    }

    private static int getMaxSampleSize(int i2, int i3) {
        return (i2 * 3) / (i3 * 2);
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long jElapsedRealtime = c().elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, jElapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = jElapsedRealtime;
        }
    }

    private void maybeNotifyRenderedFirstFrame() {
        if (!this.videoFrameReleaseControl.onFrameReleasedIsFirstFrame() || this.displaySurface == null) {
            return;
        }
        notifyRenderedFirstFrame();
    }

    private void maybeNotifyVideoFrameProcessingOffset() {
        int i2 = this.videoFrameProcessingOffsetCount;
        if (i2 != 0) {
            this.eventDispatcher.reportVideoFrameProcessingOffset(this.totalVideoFrameProcessingOffsetUs, i2);
            this.totalVideoFrameProcessingOffsetUs = 0L;
            this.videoFrameProcessingOffsetCount = 0;
        }
    }

    private void maybeNotifyVideoSizeChanged(VideoSize videoSize) {
        if (videoSize.equals(VideoSize.UNKNOWN) || videoSize.equals(this.reportedVideoSize)) {
            return;
        }
        this.reportedVideoSize = videoSize;
        this.eventDispatcher.videoSizeChanged(videoSize);
    }

    private boolean maybeReleaseFrame(MediaCodecAdapter mediaCodecAdapter, int i2, long j2, Format format) throws InterruptedException {
        long releaseTimeNs = this.videoFrameReleaseInfo.getReleaseTimeNs();
        long earlyUs = this.videoFrameReleaseInfo.getEarlyUs();
        if (Util.SDK_INT >= 21) {
            if (L0() && releaseTimeNs == this.lastFrameReleaseTimeNs) {
                M0(mediaCodecAdapter, i2, j2);
            } else {
                notifyFrameMetadataListener(j2, releaseTimeNs, format);
                G0(mediaCodecAdapter, i2, j2, releaseTimeNs);
            }
            O0(earlyUs);
            this.lastFrameReleaseTimeNs = releaseTimeNs;
            return true;
        }
        if (earlyUs >= 30000) {
            return false;
        }
        if (earlyUs > 11000) {
            try {
                Thread.sleep((earlyUs - 10000) / 1000);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        notifyFrameMetadataListener(j2, releaseTimeNs, format);
        F0(mediaCodecAdapter, i2, j2);
        O0(earlyUs);
        return true;
    }

    private void maybeRenotifyRenderedFirstFrame() {
        Surface surface = this.displaySurface;
        if (surface == null || !this.haveReportedFirstFrameRenderedForCurrentSurface) {
            return;
        }
        this.eventDispatcher.renderedFirstFrame(surface);
    }

    private void maybeRenotifyVideoSizeChanged() {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null) {
            this.eventDispatcher.videoSizeChanged(videoSize);
        }
    }

    private void maybeSetKeyAllowFrameDrop(MediaFormat mediaFormat) {
        VideoSink videoSink = this.videoSink;
        if (videoSink == null || videoSink.isFrameDropAllowedOnInput()) {
            return;
        }
        mediaFormat.setInteger("allow-frame-drop", 0);
    }

    private void maybeSetupTunnelingForFirstFrame() {
        int i2;
        MediaCodecAdapter mediaCodecAdapterC;
        if (!this.tunneling || (i2 = Util.SDK_INT) < 23 || (mediaCodecAdapterC = C()) == null) {
            return;
        }
        this.f5541b = new OnFrameRenderedListenerV23(mediaCodecAdapterC);
        if (i2 >= 33) {
            Bundle bundle = new Bundle();
            bundle.putInt("tunnel-peek", 1);
            mediaCodecAdapterC.setParameters(bundle);
        }
    }

    private void notifyFrameMetadataListener(long j2, long j3, Format format) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j2, j3, format, H());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresNonNull({"displaySurface"})
    public void notifyRenderedFirstFrame() {
        this.eventDispatcher.renderedFirstFrame(this.displaySurface);
        this.haveReportedFirstFrameRenderedForCurrentSurface = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProcessedTunneledEndOfStream() {
        h0();
    }

    private void releasePlaceholderSurface() {
        Surface surface = this.displaySurface;
        PlaceholderSurface placeholderSurface = this.placeholderSurface;
        if (surface == placeholderSurface) {
            this.displaySurface = null;
        }
        if (placeholderSurface != null) {
            placeholderSurface.release();
            this.placeholderSurface = null;
        }
    }

    private void renderOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i2, long j2, long j3) {
        if (Util.SDK_INT >= 21) {
            G0(mediaCodecAdapter, i2, j2, j3);
        } else {
            F0(mediaCodecAdapter, i2, j2);
        }
    }

    @RequiresApi(29)
    private static void setHdr10PlusInfoV29(MediaCodecAdapter mediaCodecAdapter, byte[] bArr) {
        Bundle bundle = new Bundle();
        bundle.putByteArray("hdr10-plus-info", bArr);
        mediaCodecAdapter.setParameters(bundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.video.MediaCodecVideoRenderer] */
    /* JADX WARN: Type inference failed for: r6v10, types: [android.view.Surface] */
    private void setOutput(@Nullable Object obj) throws ExoPlaybackException {
        PlaceholderSurface placeholderSurfaceNewInstance = obj instanceof Surface ? (Surface) obj : null;
        if (placeholderSurfaceNewInstance == null) {
            PlaceholderSurface placeholderSurface = this.placeholderSurface;
            if (placeholderSurface != null) {
                placeholderSurfaceNewInstance = placeholderSurface;
            } else {
                MediaCodecInfo mediaCodecInfoE = E();
                if (mediaCodecInfoE != null && shouldUsePlaceholderSurface(mediaCodecInfoE)) {
                    placeholderSurfaceNewInstance = PlaceholderSurface.newInstance(this.context, mediaCodecInfoE.secure);
                    this.placeholderSurface = placeholderSurfaceNewInstance;
                }
            }
        }
        if (this.displaySurface == placeholderSurfaceNewInstance) {
            if (placeholderSurfaceNewInstance == null || placeholderSurfaceNewInstance == this.placeholderSurface) {
                return;
            }
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
            return;
        }
        this.displaySurface = placeholderSurfaceNewInstance;
        if (this.videoSink == null) {
            this.videoFrameReleaseControl.setOutputSurface(placeholderSurfaceNewInstance);
        }
        this.haveReportedFirstFrameRenderedForCurrentSurface = false;
        int state = getState();
        MediaCodecAdapter mediaCodecAdapterC = C();
        if (mediaCodecAdapterC != null && this.videoSink == null) {
            if (Util.SDK_INT < 23 || placeholderSurfaceNewInstance == null || this.codecNeedsSetOutputSurfaceWorkaround) {
                d0();
                S();
            } else {
                H0(mediaCodecAdapterC, placeholderSurfaceNewInstance);
            }
        }
        if (placeholderSurfaceNewInstance == null || placeholderSurfaceNewInstance == this.placeholderSurface) {
            this.reportedVideoSize = null;
            VideoSink videoSink = this.videoSink;
            if (videoSink != null) {
                videoSink.clearOutputSurfaceInfo();
            }
        } else {
            maybeRenotifyVideoSizeChanged();
            if (state == 2) {
                this.videoFrameReleaseControl.join(true);
            }
        }
        maybeSetupTunnelingForFirstFrame();
    }

    private boolean shouldUsePlaceholderSurface(MediaCodecInfo mediaCodecInfo) {
        return Util.SDK_INT >= 23 && !this.tunneling && !w0(mediaCodecInfo.name) && (!mediaCodecInfo.secure || PlaceholderSurface.isSecureSupported(this.context));
    }

    private void updateCodecImportance() {
        MediaCodecAdapter mediaCodecAdapterC = C();
        if (mediaCodecAdapterC != null && Util.SDK_INT >= 35) {
            Bundle bundle = new Bundle();
            bundle.putInt("importance", Math.max(0, -this.rendererPriority));
            mediaCodecAdapterC.setParameters(bundle);
        }
    }

    protected MediaFormat B0(Format format, String str, CodecMaxValues codecMaxValues, float f2, boolean z2, int i2) {
        Pair<Integer, Integer> codecProfileAndLevel;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", str);
        mediaFormat.setInteger(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, format.width);
        mediaFormat.setInteger(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            MediaFormatUtil.maybeSetInteger(mediaFormat, "profile", ((Integer) codecProfileAndLevel.first).intValue());
        }
        mediaFormat.setInteger("max-width", codecMaxValues.width);
        mediaFormat.setInteger("max-height", codecMaxValues.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.inputSize);
        int i3 = Util.SDK_INT;
        if (i3 >= 23) {
            mediaFormat.setInteger(RemoteMessageConst.Notification.PRIORITY, 0);
            if (f2 != -1.0f) {
                mediaFormat.setFloat("operating-rate", f2);
            }
        }
        if (z2) {
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (i2 != 0) {
            configureTunnelingV21(mediaFormat, i2);
        }
        if (i3 >= 35) {
            mediaFormat.setInteger("importance", Math.max(0, -this.rendererPriority));
        }
        return mediaFormat;
    }

    protected boolean C0(long j2, boolean z2) {
        int iW = w(j2);
        if (iW == 0) {
            return false;
        }
        if (z2) {
            DecoderCounters decoderCounters = this.f5299a;
            decoderCounters.skippedInputBufferCount += iW;
            decoderCounters.skippedOutputBufferCount += this.buffersInCodecCount;
        } else {
            this.f5299a.droppedToKeyframeCount++;
            N0(iW, this.buffersInCodecCount);
        }
        A();
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.flush(false);
        }
        return true;
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected int D(DecoderInputBuffer decoderInputBuffer) {
        return (Util.SDK_INT < 34 || !this.tunneling || decoderInputBuffer.timeUs >= g()) ? 0 : 32;
    }

    protected void D0(long j2) {
        o0(j2);
        maybeNotifyVideoSizeChanged(this.decodedVideoSize);
        this.f5299a.renderedOutputBufferCount++;
        maybeNotifyRenderedFirstFrame();
        onProcessedOutputBuffer(j2);
    }

    protected void E0() {
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected boolean F() {
        return this.tunneling && Util.SDK_INT < 23;
    }

    protected void F0(MediaCodecAdapter mediaCodecAdapter, int i2, long j2) {
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, true);
        TraceUtil.endSection();
        this.f5299a.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        if (this.videoSink == null) {
            maybeNotifyVideoSizeChanged(this.decodedVideoSize);
            maybeNotifyRenderedFirstFrame();
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected float G(float f2, Format format, Format[] formatArr) {
        float fMax = -1.0f;
        for (Format format2 : formatArr) {
            float f3 = format2.frameRate;
            if (f3 != -1.0f) {
                fMax = Math.max(fMax, f3);
            }
        }
        if (fMax == -1.0f) {
            return -1.0f;
        }
        return fMax * f2;
    }

    protected void G0(MediaCodecAdapter mediaCodecAdapter, int i2, long j2, long j3) {
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, j3);
        TraceUtil.endSection();
        this.f5299a.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        if (this.videoSink == null) {
            maybeNotifyVideoSizeChanged(this.decodedVideoSize);
            maybeNotifyRenderedFirstFrame();
        }
    }

    protected void H0(MediaCodecAdapter mediaCodecAdapter, Surface surface) {
        mediaCodecAdapter.setOutputSurface(surface);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected List I(MediaCodecSelector mediaCodecSelector, Format format, boolean z2) {
        return MediaCodecUtil.getDecoderInfosSortedByFormatSupport(getDecoderInfos(this.context, mediaCodecSelector, format, z2, this.tunneling), format);
    }

    protected boolean I0(long j2, long j3, boolean z2) {
        return j2 < MIN_EARLY_US_VERY_LATE_THRESHOLD && !z2;
    }

    protected boolean J0(long j2, long j3, boolean z2) {
        return j2 < MIN_EARLY_US_LATE_THRESHOLD && !z2;
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected MediaCodecAdapter.Configuration K(MediaCodecInfo mediaCodecInfo, Format format, MediaCrypto mediaCrypto, float f2) {
        PlaceholderSurface placeholderSurface = this.placeholderSurface;
        if (placeholderSurface != null && placeholderSurface.secure != mediaCodecInfo.secure) {
            releasePlaceholderSurface();
        }
        String str = mediaCodecInfo.codecMimeType;
        CodecMaxValues codecMaxValuesZ0 = z0(mediaCodecInfo, format, i());
        this.codecMaxValues = codecMaxValuesZ0;
        MediaFormat mediaFormatB0 = B0(format, str, codecMaxValuesZ0, f2, this.deviceNeedsNoPostProcessWorkaround, this.tunneling ? this.tunnelingAudioSessionId : 0);
        if (this.displaySurface == null) {
            if (!shouldUsePlaceholderSurface(mediaCodecInfo)) {
                throw new IllegalStateException();
            }
            if (this.placeholderSurface == null) {
                this.placeholderSurface = PlaceholderSurface.newInstance(this.context, mediaCodecInfo.secure);
            }
            this.displaySurface = this.placeholderSurface;
        }
        maybeSetKeyAllowFrameDrop(mediaFormatB0);
        VideoSink videoSink = this.videoSink;
        return MediaCodecAdapter.Configuration.createForVideoDecoding(mediaCodecInfo, mediaFormatB0, format, videoSink != null ? videoSink.getInputSurface() : this.displaySurface, mediaCrypto);
    }

    protected boolean K0(long j2, long j3) {
        return j2 < MIN_EARLY_US_LATE_THRESHOLD && j3 > SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US;
    }

    protected boolean L0() {
        return true;
    }

    protected void M0(MediaCodecAdapter mediaCodecAdapter, int i2, long j2) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, false);
        TraceUtil.endSection();
        this.f5299a.skippedOutputBufferCount++;
    }

    protected void N0(int i2, int i3) {
        DecoderCounters decoderCounters = this.f5299a;
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

    protected void O0(long j2) {
        this.f5299a.addVideoFrameProcessingOffset(j2);
        this.totalVideoFrameProcessingOffsetUs += j2;
        this.videoFrameProcessingOffsetCount++;
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void P(DecoderInputBuffer decoderInputBuffer) {
        if (this.codecHandlesHdr10PlusOutOfBandMetadata) {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.supplementalData);
            if (byteBuffer.remaining() >= 7) {
                byte b2 = byteBuffer.get();
                short s2 = byteBuffer.getShort();
                short s3 = byteBuffer.getShort();
                byte b3 = byteBuffer.get();
                byte b4 = byteBuffer.get();
                byteBuffer.position(0);
                if (b2 == -75 && s2 == 60 && s3 == 1 && b3 == 4) {
                    if (b4 == 0 || b4 == 1) {
                        byte[] bArr = new byte[byteBuffer.remaining()];
                        byteBuffer.get(bArr);
                        byteBuffer.position(0);
                        setHdr10PlusInfoV29((MediaCodecAdapter) Assertions.checkNotNull(C()), bArr);
                    }
                }
            }
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void T(Exception exc) {
        Log.e(TAG, "Video codec error", exc);
        this.eventDispatcher.videoCodecError(exc);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void U(String str, MediaCodecAdapter.Configuration configuration, long j2, long j3) {
        this.eventDispatcher.decoderInitialized(str, j2, j3);
        this.codecNeedsSetOutputSurfaceWorkaround = w0(str);
        this.codecHandlesHdr10PlusOutOfBandMetadata = ((MediaCodecInfo) Assertions.checkNotNull(E())).isHdr10PlusOutOfBandMetadataSupported();
        maybeSetupTunnelingForFirstFrame();
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void V(String str) {
        this.eventDispatcher.decoderReleased(str);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected DecoderReuseEvaluation W(FormatHolder formatHolder) {
        DecoderReuseEvaluation decoderReuseEvaluationW = super.W(formatHolder);
        this.eventDispatcher.inputFormatChanged((Format) Assertions.checkNotNull(formatHolder.format), decoderReuseEvaluationW);
        return decoderReuseEvaluationW;
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void X(Format format, MediaFormat mediaFormat) {
        int integer;
        int i2;
        MediaCodecAdapter mediaCodecAdapterC = C();
        if (mediaCodecAdapterC != null) {
            mediaCodecAdapterC.setVideoScalingMode(this.scalingMode);
        }
        int i3 = 0;
        if (this.tunneling) {
            i2 = format.width;
            integer = format.height;
        } else {
            Assertions.checkNotNull(mediaFormat);
            boolean z2 = mediaFormat.containsKey(KEY_CROP_RIGHT) && mediaFormat.containsKey(KEY_CROP_LEFT) && mediaFormat.containsKey(KEY_CROP_BOTTOM) && mediaFormat.containsKey(KEY_CROP_TOP);
            int integer2 = z2 ? (mediaFormat.getInteger(KEY_CROP_RIGHT) - mediaFormat.getInteger(KEY_CROP_LEFT)) + 1 : mediaFormat.getInteger(ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
            integer = z2 ? (mediaFormat.getInteger(KEY_CROP_BOTTOM) - mediaFormat.getInteger(KEY_CROP_TOP)) + 1 : mediaFormat.getInteger(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
            i2 = integer2;
        }
        float f2 = format.pixelWidthHeightRatio;
        if (codecAppliesRotation()) {
            int i4 = format.rotationDegrees;
            if (i4 == 90 || i4 == 270) {
                f2 = 1.0f / f2;
                int i5 = integer;
                integer = i2;
                i2 = i5;
            }
        } else if (this.videoSink == null) {
            i3 = format.rotationDegrees;
        }
        this.decodedVideoSize = new VideoSize(i2, integer, i3, f2);
        if (this.videoSink == null) {
            this.videoFrameReleaseControl.setFrameRate(format.frameRate);
        } else {
            E0();
            this.videoSink.registerInputStream(1, format.buildUpon().setWidth(i2).setHeight(integer).setRotationDegrees(i3).setPixelWidthHeightRatio(f2).build());
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void Z() {
        super.Z();
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.setStreamOffsetAndAdjustmentUs(L(), y0());
        } else {
            this.videoFrameReleaseControl.onProcessedStreamChange();
        }
        maybeSetupTunnelingForFirstFrame();
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void a0(DecoderInputBuffer decoderInputBuffer) {
        boolean z2 = this.tunneling;
        if (!z2) {
            this.buffersInCodecCount++;
        }
        if (Util.SDK_INT >= 23 || !z2) {
            return;
        }
        D0(decoderInputBuffer.timeUs);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void b0(Format format) throws ExoPlaybackException {
        VideoSink videoSink = this.videoSink;
        if (videoSink == null || videoSink.isInitialized()) {
            return;
        }
        try {
            this.videoSink.initialize(format);
        } catch (VideoSink.VideoSinkException e2) {
            throw a(e2, format, 7000);
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected boolean c0(long j2, long j3, MediaCodecAdapter mediaCodecAdapter, ByteBuffer byteBuffer, int i2, int i3, int i4, long j4, boolean z2, boolean z3, Format format) throws ExoPlaybackException {
        Assertions.checkNotNull(mediaCodecAdapter);
        long jL = j4 - L();
        int frameReleaseAction = this.videoFrameReleaseControl.getFrameReleaseAction(j4, j2, j3, M(), z3, this.videoFrameReleaseInfo);
        if (frameReleaseAction == 4) {
            return false;
        }
        if (z2 && !z3) {
            M0(mediaCodecAdapter, i2, jL);
            return true;
        }
        if (this.displaySurface == this.placeholderSurface && this.videoSink == null) {
            if (this.videoFrameReleaseInfo.getEarlyUs() >= 30000) {
                return false;
            }
            M0(mediaCodecAdapter, i2, jL);
            O0(this.videoFrameReleaseInfo.getEarlyUs());
            return true;
        }
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            try {
                videoSink.render(j2, j3);
                long jRegisterInputFrame = this.videoSink.registerInputFrame(j4 + y0(), z3);
                if (jRegisterInputFrame == C.TIME_UNSET) {
                    return false;
                }
                renderOutputBuffer(mediaCodecAdapter, i2, jL, jRegisterInputFrame);
                return true;
            } catch (VideoSink.VideoSinkException e2) {
                throw a(e2, e2.format, PlaybackException.ERROR_CODE_VIDEO_FRAME_PROCESSING_FAILED);
            }
        }
        if (frameReleaseAction == 0) {
            long jNanoTime = c().nanoTime();
            notifyFrameMetadataListener(jL, jNanoTime, format);
            renderOutputBuffer(mediaCodecAdapter, i2, jL, jNanoTime);
            O0(this.videoFrameReleaseInfo.getEarlyUs());
            return true;
        }
        if (frameReleaseAction == 1) {
            return maybeReleaseFrame((MediaCodecAdapter) Assertions.checkStateNotNull(mediaCodecAdapter), i2, jL, format);
        }
        if (frameReleaseAction == 2) {
            x0(mediaCodecAdapter, i2, jL);
            O0(this.videoFrameReleaseInfo.getEarlyUs());
            return true;
        }
        if (frameReleaseAction != 3) {
            if (frameReleaseAction == 5) {
                return false;
            }
            throw new IllegalStateException(String.valueOf(frameReleaseAction));
        }
        M0(mediaCodecAdapter, i2, jL);
        O0(this.videoFrameReleaseInfo.getEarlyUs());
        return true;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.Renderer
    public void enableMayRenderStartOfStream() {
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.enableMayRenderStartOfStream();
        } else {
            this.videoFrameReleaseControl.allowReleaseFirstFrameBeforeStarted();
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void f0() {
        super.f0();
        this.buffersInCodecCount = 0;
    }

    @Override // androidx.media3.exoplayer.Renderer, androidx.media3.exoplayer.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.PlayerMessage.Target
    public void handleMessage(int i2, @Nullable Object obj) throws ExoPlaybackException {
        if (i2 == 1) {
            setOutput(obj);
            return;
        }
        if (i2 == 7) {
            VideoFrameMetadataListener videoFrameMetadataListener = (VideoFrameMetadataListener) Assertions.checkNotNull(obj);
            this.frameMetadataListener = videoFrameMetadataListener;
            VideoSink videoSink = this.videoSink;
            if (videoSink != null) {
                videoSink.setVideoFrameMetadataListener(videoFrameMetadataListener);
                return;
            }
            return;
        }
        if (i2 == 10) {
            int iIntValue = ((Integer) Assertions.checkNotNull(obj)).intValue();
            if (this.tunnelingAudioSessionId != iIntValue) {
                this.tunnelingAudioSessionId = iIntValue;
                if (this.tunneling) {
                    d0();
                    return;
                }
                return;
            }
            return;
        }
        if (i2 == 16) {
            this.rendererPriority = ((Integer) Assertions.checkNotNull(obj)).intValue();
            updateCodecImportance();
            return;
        }
        if (i2 == 4) {
            this.scalingMode = ((Integer) Assertions.checkNotNull(obj)).intValue();
            MediaCodecAdapter mediaCodecAdapterC = C();
            if (mediaCodecAdapterC != null) {
                mediaCodecAdapterC.setVideoScalingMode(this.scalingMode);
                return;
            }
            return;
        }
        if (i2 == 5) {
            this.videoFrameReleaseControl.setChangeFrameRateStrategy(((Integer) Assertions.checkNotNull(obj)).intValue());
            return;
        }
        if (i2 == 13) {
            setVideoEffects((List) Assertions.checkNotNull(obj));
            return;
        }
        if (i2 != 14) {
            super.handleMessage(i2, obj);
            return;
        }
        Size size = (Size) Assertions.checkNotNull(obj);
        if (size.getWidth() == 0 || size.getHeight() == 0) {
            return;
        }
        this.outputResolution = size;
        VideoSink videoSink2 = this.videoSink;
        if (videoSink2 != null) {
            videoSink2.setOutputSurfaceInfo((Surface) Assertions.checkStateNotNull(this.displaySurface), size);
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.Renderer
    public boolean isEnded() {
        VideoSink videoSink;
        return super.isEnded() && ((videoSink = this.videoSink) == null || videoSink.isEnded());
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.Renderer
    public boolean isReady() {
        PlaceholderSurface placeholderSurface;
        VideoSink videoSink;
        boolean z2 = super.isReady() && ((videoSink = this.videoSink) == null || videoSink.isReady());
        if (z2 && (((placeholderSurface = this.placeholderSurface) != null && this.displaySurface == placeholderSurface) || C() == null || this.tunneling)) {
            return true;
        }
        return this.videoFrameReleaseControl.isReady(z2);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected boolean j0(MediaCodecInfo mediaCodecInfo) {
        return this.displaySurface != null || shouldUsePlaceholderSurface(mediaCodecInfo);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer
    protected void k() {
        this.reportedVideoSize = null;
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.onRendererDisabled();
        } else {
            this.videoFrameReleaseControl.onDisabled();
        }
        maybeSetupTunnelingForFirstFrame();
        this.haveReportedFirstFrameRenderedForCurrentSurface = false;
        this.f5541b = null;
        try {
            super.k();
        } finally {
            this.eventDispatcher.disabled(this.f5299a);
            this.eventDispatcher.videoSizeChanged(VideoSize.UNKNOWN);
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer
    protected void l(boolean z2, boolean z3) {
        super.l(z2, z3);
        boolean z4 = d().tunneling;
        Assertions.checkState((z4 && this.tunnelingAudioSessionId == 0) ? false : true);
        if (this.tunneling != z4) {
            this.tunneling = z4;
            d0();
        }
        this.eventDispatcher.enabled(this.f5299a);
        if (!this.hasSetVideoSink) {
            if ((this.videoEffects != null || !this.ownsVideoSink) && this.videoSink == null) {
                VideoSinkProvider videoSinkProviderBuild = this.videoSinkProvider;
                if (videoSinkProviderBuild == null) {
                    videoSinkProviderBuild = new CompositingVideoSinkProvider.Builder(this.context, this.videoFrameReleaseControl).setClock(c()).build();
                }
                this.videoSink = videoSinkProviderBuild.getSink();
            }
            this.hasSetVideoSink = true;
        }
        VideoSink videoSink = this.videoSink;
        if (videoSink == null) {
            this.videoFrameReleaseControl.setClock(c());
            this.videoFrameReleaseControl.onEnabled(z3);
            return;
        }
        videoSink.setListener(new VideoSink.Listener() { // from class: androidx.media3.exoplayer.video.MediaCodecVideoRenderer.1
            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onError(VideoSink videoSink2, VideoSink.VideoSinkException videoSinkException) {
                MediaCodecVideoRenderer mediaCodecVideoRenderer = MediaCodecVideoRenderer.this;
                mediaCodecVideoRenderer.i0(mediaCodecVideoRenderer.a(videoSinkException, videoSinkException.format, PlaybackException.ERROR_CODE_VIDEO_FRAME_PROCESSING_FAILED));
            }

            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onFirstFrameRendered(VideoSink videoSink2) {
                Assertions.checkStateNotNull(MediaCodecVideoRenderer.this.displaySurface);
                MediaCodecVideoRenderer.this.notifyRenderedFirstFrame();
            }

            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onFrameDropped(VideoSink videoSink2) {
                MediaCodecVideoRenderer.this.N0(0, 1);
            }

            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onVideoSizeChanged(VideoSink videoSink2, VideoSize videoSize) {
            }
        }, MoreExecutors.directExecutor());
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            this.videoSink.setVideoFrameMetadataListener(videoFrameMetadataListener);
        }
        if (this.displaySurface != null && !this.outputResolution.equals(Size.UNKNOWN)) {
            this.videoSink.setOutputSurfaceInfo(this.displaySurface, this.outputResolution);
        }
        this.videoSink.setPlaybackSpeed(N());
        List<Effect> list = this.videoEffects;
        if (list != null) {
            this.videoSink.setVideoEffects(list);
        }
        this.videoSink.onRendererEnabled(z3);
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void m() {
        super.m();
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected int m0(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        boolean z2;
        int i2 = 0;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return v2.c(0);
        }
        boolean z3 = format.drmInitData != null;
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(this.context, mediaCodecSelector, format, z3, false);
        if (z3 && decoderInfos.isEmpty()) {
            decoderInfos = getDecoderInfos(this.context, mediaCodecSelector, format, false, false);
        }
        if (decoderInfos.isEmpty()) {
            return v2.c(1);
        }
        if (!MediaCodecRenderer.n0(format)) {
            return v2.c(2);
        }
        MediaCodecInfo mediaCodecInfo = decoderInfos.get(0);
        boolean zIsFormatSupported = mediaCodecInfo.isFormatSupported(format);
        if (zIsFormatSupported) {
            z2 = true;
        } else {
            for (int i3 = 1; i3 < decoderInfos.size(); i3++) {
                MediaCodecInfo mediaCodecInfo2 = decoderInfos.get(i3);
                if (mediaCodecInfo2.isFormatSupported(format)) {
                    z2 = false;
                    zIsFormatSupported = true;
                    mediaCodecInfo = mediaCodecInfo2;
                    break;
                }
            }
            z2 = true;
        }
        int i4 = zIsFormatSupported ? 4 : 3;
        int i5 = mediaCodecInfo.isSeamlessAdaptationSupported(format) ? 16 : 8;
        int i6 = mediaCodecInfo.hardwareAccelerated ? 64 : 0;
        int i7 = z2 ? 128 : 0;
        if (Util.SDK_INT >= 26 && MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && !Api26.doesDisplaySupportDolbyVision(this.context)) {
            i7 = 256;
        }
        if (zIsFormatSupported) {
            List<MediaCodecInfo> decoderInfos2 = getDecoderInfos(this.context, mediaCodecSelector, format, z3, true);
            if (!decoderInfos2.isEmpty()) {
                MediaCodecInfo mediaCodecInfo3 = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(decoderInfos2, format).get(0);
                if (mediaCodecInfo3.isFormatSupported(format) && mediaCodecInfo3.isSeamlessAdaptationSupported(format)) {
                    i2 = 32;
                }
            }
        }
        return v2.f(i4, i5, i2, i6, i7);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer
    protected void n(long j2, boolean z2) {
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.flush(true);
            this.videoSink.setStreamOffsetAndAdjustmentUs(L(), y0());
        }
        super.n(j2, z2);
        if (this.videoSink == null) {
            this.videoFrameReleaseControl.reset();
        }
        if (z2) {
            this.videoFrameReleaseControl.join(false);
        }
        maybeSetupTunnelingForFirstFrame();
        this.consecutiveDroppedFrameCount = 0;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer
    protected void o() {
        super.o();
        VideoSink videoSink = this.videoSink;
        if (videoSink == null || !this.ownsVideoSink) {
            return;
        }
        videoSink.release();
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected void onProcessedOutputBuffer(long j2) {
        super.onProcessedOutputBuffer(j2);
        if (this.tunneling) {
            return;
        }
        this.buffersInCodecCount--;
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer
    protected void q() {
        try {
            super.q();
        } finally {
            this.hasSetVideoSink = false;
            if (this.placeholderSurface != null) {
                releasePlaceholderSurface();
            }
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer
    protected void r() {
        super.r();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = c().elapsedRealtime();
        this.totalVideoFrameProcessingOffsetUs = 0L;
        this.videoFrameProcessingOffsetCount = 0;
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.onRendererStarted();
        } else {
            this.videoFrameReleaseControl.onStarted();
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.Renderer
    @CallSuper
    public void render(long j2, long j3) throws ExoPlaybackException {
        super.render(j2, j3);
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            try {
                videoSink.render(j2, j3);
            } catch (VideoSink.VideoSinkException e2) {
                throw a(e2, e2.format, PlaybackException.ERROR_CODE_VIDEO_FRAME_PROCESSING_FAILED);
            }
        }
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer
    protected void s() {
        maybeNotifyDroppedFrames();
        maybeNotifyVideoFrameProcessingOffset();
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.onRendererStopped();
        } else {
            this.videoFrameReleaseControl.onStopped();
        }
        super.s();
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer, androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.Renderer
    public void setPlaybackSpeed(float f2, float f3) throws ExoPlaybackException {
        super.setPlaybackSpeed(f2, f3);
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.setPlaybackSpeed(f2);
        } else {
            this.videoFrameReleaseControl.setPlaybackSpeed(f2);
        }
    }

    public void setVideoEffects(List<Effect> list) {
        this.videoEffects = list;
        VideoSink videoSink = this.videoSink;
        if (videoSink != null) {
            videoSink.setVideoEffects(list);
        }
    }

    @Override // androidx.media3.exoplayer.video.VideoFrameReleaseControl.FrameTimingEvaluator
    public boolean shouldDropFrame(long j2, long j3, boolean z2) {
        return J0(j2, j3, z2);
    }

    @Override // androidx.media3.exoplayer.video.VideoFrameReleaseControl.FrameTimingEvaluator
    public boolean shouldForceReleaseFrame(long j2, long j3) {
        return K0(j2, j3);
    }

    @Override // androidx.media3.exoplayer.video.VideoFrameReleaseControl.FrameTimingEvaluator
    public boolean shouldIgnoreFrame(long j2, long j3, long j4, boolean z2, boolean z3) throws ExoPlaybackException {
        return I0(j2, j4, z2) && C0(j3, z3);
    }

    protected boolean w0(String str) {
        if (str.startsWith("OMX.google")) {
            return false;
        }
        synchronized (MediaCodecVideoRenderer.class) {
            try {
                if (!evaluatedDeviceNeedsSetOutputSurfaceWorkaround) {
                    deviceNeedsSetOutputSurfaceWorkaround = evaluateDeviceNeedsSetOutputSurfaceWorkaround();
                    evaluatedDeviceNeedsSetOutputSurfaceWorkaround = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return deviceNeedsSetOutputSurfaceWorkaround;
    }

    protected void x0(MediaCodecAdapter mediaCodecAdapter, int i2, long j2) {
        TraceUtil.beginSection("dropVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, false);
        TraceUtil.endSection();
        N0(0, 1);
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected DecoderReuseEvaluation y(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        DecoderReuseEvaluation decoderReuseEvaluationCanReuseCodec = mediaCodecInfo.canReuseCodec(format, format2);
        int i2 = decoderReuseEvaluationCanReuseCodec.discardReasons;
        CodecMaxValues codecMaxValues = (CodecMaxValues) Assertions.checkNotNull(this.codecMaxValues);
        if (format2.width > codecMaxValues.width || format2.height > codecMaxValues.height) {
            i2 |= 256;
        }
        if (A0(mediaCodecInfo, format2) > codecMaxValues.inputSize) {
            i2 |= 64;
        }
        int i3 = i2;
        return new DecoderReuseEvaluation(mediaCodecInfo.name, format, format2, i3 != 0 ? 0 : decoderReuseEvaluationCanReuseCodec.result, i3);
    }

    protected long y0() {
        return 0L;
    }

    @Override // androidx.media3.exoplayer.mediacodec.MediaCodecRenderer
    protected MediaCodecDecoderException z(Throwable th, MediaCodecInfo mediaCodecInfo) {
        return new MediaCodecVideoDecoderException(th, mediaCodecInfo, this.displaySurface);
    }

    protected CodecMaxValues z0(MediaCodecInfo mediaCodecInfo, Format format, Format[] formatArr) {
        int codecMaxInputSize;
        int iMax = format.width;
        int iMax2 = format.height;
        int iA0 = A0(mediaCodecInfo, format);
        if (formatArr.length == 1) {
            if (iA0 != -1 && (codecMaxInputSize = getCodecMaxInputSize(mediaCodecInfo, format)) != -1) {
                iA0 = Math.min((int) (iA0 * INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR), codecMaxInputSize);
            }
            return new CodecMaxValues(iMax, iMax2, iA0);
        }
        int length = formatArr.length;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            Format formatBuild = formatArr[i2];
            if (format.colorInfo != null && formatBuild.colorInfo == null) {
                formatBuild = formatBuild.buildUpon().setColorInfo(format.colorInfo).build();
            }
            if (mediaCodecInfo.canReuseCodec(format, formatBuild).result != 0) {
                int i3 = formatBuild.width;
                z2 |= i3 == -1 || formatBuild.height == -1;
                iMax = Math.max(iMax, i3);
                iMax2 = Math.max(iMax2, formatBuild.height);
                iA0 = Math.max(iA0, A0(mediaCodecInfo, formatBuild));
            }
        }
        if (z2) {
            Log.w(TAG, "Resolutions unknown. Codec max resolution: " + iMax + "x" + iMax2);
            Point codecMaxSize = getCodecMaxSize(mediaCodecInfo, format);
            if (codecMaxSize != null) {
                iMax = Math.max(iMax, codecMaxSize.x);
                iMax2 = Math.max(iMax2, codecMaxSize.y);
                iA0 = Math.max(iA0, getCodecMaxInputSize(mediaCodecInfo, format.buildUpon().setWidth(iMax).setHeight(iMax2).build()));
                Log.w(TAG, "Codec max resolution adjusted to: " + iMax + "x" + iMax2);
            }
        }
        return new CodecMaxValues(iMax, iMax2, iA0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j2) {
        this(context, mediaCodecSelector, j2, null, null, 0);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2) {
        this(context, androidx.media3.exoplayer.mediacodec.h.a(context), mediaCodecSelector, j2, false, handler, videoRendererEventListener, i2, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j2, boolean z2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2) {
        this(context, androidx.media3.exoplayer.mediacodec.h.a(context), mediaCodecSelector, j2, z2, handler, videoRendererEventListener, i2, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j2, boolean z2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2) {
        this(context, factory, mediaCodecSelector, j2, z2, handler, videoRendererEventListener, i2, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j2, boolean z2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2, float f2) {
        this(context, factory, mediaCodecSelector, j2, z2, handler, videoRendererEventListener, i2, f2, null);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j2, boolean z2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2, float f2, @Nullable VideoSinkProvider videoSinkProvider) {
        super(2, factory, mediaCodecSelector, z2, f2);
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.maxDroppedFramesToNotify = i2;
        this.videoSinkProvider = videoSinkProvider;
        this.eventDispatcher = new VideoRendererEventListener.EventDispatcher(handler, videoRendererEventListener);
        this.ownsVideoSink = videoSinkProvider == null;
        if (videoSinkProvider == null) {
            this.videoFrameReleaseControl = new VideoFrameReleaseControl(applicationContext, this, j2);
        } else {
            this.videoFrameReleaseControl = videoSinkProvider.getVideoFrameReleaseControl();
        }
        this.videoFrameReleaseInfo = new VideoFrameReleaseControl.FrameReleaseInfo();
        this.deviceNeedsNoPostProcessWorkaround = deviceNeedsNoPostProcessWorkaround();
        this.outputResolution = Size.UNKNOWN;
        this.scalingMode = 1;
        this.decodedVideoSize = VideoSize.UNKNOWN;
        this.tunnelingAudioSessionId = 0;
        this.reportedVideoSize = null;
        this.rendererPriority = -1000;
    }
}
