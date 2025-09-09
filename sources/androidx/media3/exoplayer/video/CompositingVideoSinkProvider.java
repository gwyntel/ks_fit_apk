package androidx.media3.exoplayer.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.util.Pair;
import android.view.Surface;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.media3.common.C;
import androidx.media3.common.ColorInfo;
import androidx.media3.common.DebugViewProvider;
import androidx.media3.common.Effect;
import androidx.media3.common.Format;
import androidx.media3.common.FrameInfo;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.PreviewingVideoGraph;
import androidx.media3.common.SurfaceInfo;
import androidx.media3.common.VideoFrameProcessingException;
import androidx.media3.common.VideoFrameProcessor;
import androidx.media3.common.VideoGraph;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.HandlerWrapper;
import androidx.media3.common.util.Size;
import androidx.media3.common.util.TimestampIterator;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.video.CompositingVideoSinkProvider;
import androidx.media3.exoplayer.video.VideoFrameRenderControl;
import androidx.media3.exoplayer.video.VideoSink;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@UnstableApi
/* loaded from: classes2.dex */
public final class CompositingVideoSinkProvider implements VideoSinkProvider, VideoGraph.Listener {
    private static final Executor NO_OP_EXECUTOR = new Executor() { // from class: androidx.media3.exoplayer.video.b
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            CompositingVideoSinkProvider.lambda$static$0(runnable);
        }
    };
    private static final int STATE_CREATED = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_RELEASED = 2;
    private long bufferTimestampAdjustmentUs;
    private final Clock clock;
    private final Context context;

    @Nullable
    private Pair<Surface, Size> currentSurfaceAndSize;
    private HandlerWrapper handler;
    private final CopyOnWriteArraySet<Listener> listeners;
    private Format outputFormat;
    private int pendingFlushCount;
    private final PreviewingVideoGraph.Factory previewingVideoGraphFactory;
    private int state;
    private VideoFrameMetadataListener videoFrameMetadataListener;
    private final VideoFrameReleaseControl videoFrameReleaseControl;
    private final VideoFrameRenderControl videoFrameRenderControl;
    private PreviewingVideoGraph videoGraph;
    private final VideoSinkImpl videoSinkImpl;

    public static final class Builder {
        private boolean built;
        private Clock clock = Clock.DEFAULT;
        private final Context context;
        private PreviewingVideoGraph.Factory previewingVideoGraphFactory;
        private VideoFrameProcessor.Factory videoFrameProcessorFactory;
        private final VideoFrameReleaseControl videoFrameReleaseControl;

        public Builder(Context context, VideoFrameReleaseControl videoFrameReleaseControl) {
            this.context = context.getApplicationContext();
            this.videoFrameReleaseControl = videoFrameReleaseControl;
        }

        public CompositingVideoSinkProvider build() {
            Assertions.checkState(!this.built);
            if (this.previewingVideoGraphFactory == null) {
                if (this.videoFrameProcessorFactory == null) {
                    this.videoFrameProcessorFactory = new ReflectiveDefaultVideoFrameProcessorFactory();
                }
                this.previewingVideoGraphFactory = new ReflectivePreviewingSingleInputVideoGraphFactory(this.videoFrameProcessorFactory);
            }
            CompositingVideoSinkProvider compositingVideoSinkProvider = new CompositingVideoSinkProvider(this);
            this.built = true;
            return compositingVideoSinkProvider;
        }

        @CanIgnoreReturnValue
        public Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setPreviewingVideoGraphFactory(PreviewingVideoGraph.Factory factory) {
            this.previewingVideoGraphFactory = factory;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setVideoFrameProcessorFactory(VideoFrameProcessor.Factory factory) {
            this.videoFrameProcessorFactory = factory;
            return this;
        }
    }

    private final class FrameRendererImpl implements VideoFrameRenderControl.FrameRenderer {
        private FrameRendererImpl() {
        }

        @Override // androidx.media3.exoplayer.video.VideoFrameRenderControl.FrameRenderer
        public void dropFrame() {
            Iterator it = CompositingVideoSinkProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((Listener) it.next()).onFrameDropped(CompositingVideoSinkProvider.this);
            }
            ((PreviewingVideoGraph) Assertions.checkStateNotNull(CompositingVideoSinkProvider.this.videoGraph)).renderOutputFrame(-2L);
        }

        @Override // androidx.media3.exoplayer.video.VideoFrameRenderControl.FrameRenderer
        public void onVideoSizeChanged(VideoSize videoSize) {
            CompositingVideoSinkProvider.this.outputFormat = new Format.Builder().setWidth(videoSize.width).setHeight(videoSize.height).setSampleMimeType(MimeTypes.VIDEO_RAW).build();
            Iterator it = CompositingVideoSinkProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((Listener) it.next()).onVideoSizeChanged(CompositingVideoSinkProvider.this, videoSize);
            }
        }

        @Override // androidx.media3.exoplayer.video.VideoFrameRenderControl.FrameRenderer
        public void renderFrame(long j2, long j3, long j4, boolean z2) {
            if (z2 && CompositingVideoSinkProvider.this.currentSurfaceAndSize != null) {
                Iterator it = CompositingVideoSinkProvider.this.listeners.iterator();
                while (it.hasNext()) {
                    ((Listener) it.next()).onFirstFrameRendered(CompositingVideoSinkProvider.this);
                }
            }
            if (CompositingVideoSinkProvider.this.videoFrameMetadataListener != null) {
                CompositingVideoSinkProvider.this.videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j3, CompositingVideoSinkProvider.this.clock.nanoTime(), CompositingVideoSinkProvider.this.outputFormat == null ? new Format.Builder().build() : CompositingVideoSinkProvider.this.outputFormat, null);
            }
            ((PreviewingVideoGraph) Assertions.checkStateNotNull(CompositingVideoSinkProvider.this.videoGraph)).renderOutputFrame(j2);
        }
    }

    public interface Listener {
        void onError(CompositingVideoSinkProvider compositingVideoSinkProvider, VideoFrameProcessingException videoFrameProcessingException);

        void onFirstFrameRendered(CompositingVideoSinkProvider compositingVideoSinkProvider);

        void onFrameDropped(CompositingVideoSinkProvider compositingVideoSinkProvider);

        void onVideoSizeChanged(CompositingVideoSinkProvider compositingVideoSinkProvider, VideoSize videoSize);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ReflectiveDefaultVideoFrameProcessorFactory implements VideoFrameProcessor.Factory {
        private static final Supplier<VideoFrameProcessor.Factory> VIDEO_FRAME_PROCESSOR_FACTORY_SUPPLIER = Suppliers.memoize(new Supplier() { // from class: androidx.media3.exoplayer.video.d
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return CompositingVideoSinkProvider.ReflectiveDefaultVideoFrameProcessorFactory.lambda$static$0();
            }
        });

        private ReflectiveDefaultVideoFrameProcessorFactory() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ VideoFrameProcessor.Factory lambda$static$0() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
            try {
                Class<?> cls = Class.forName("androidx.media3.effect.DefaultVideoFrameProcessor$Factory$Builder");
                return (VideoFrameProcessor.Factory) Assertions.checkNotNull(cls.getMethod("build", null).invoke(cls.getConstructor(null).newInstance(null), null));
            } catch (Exception e2) {
                throw new IllegalStateException(e2);
            }
        }

        @Override // androidx.media3.common.VideoFrameProcessor.Factory
        public VideoFrameProcessor create(Context context, DebugViewProvider debugViewProvider, ColorInfo colorInfo, boolean z2, Executor executor, VideoFrameProcessor.Listener listener) throws VideoFrameProcessingException {
            return VIDEO_FRAME_PROCESSOR_FACTORY_SUPPLIER.get().create(context, debugViewProvider, colorInfo, z2, executor, listener);
        }
    }

    private static final class ReflectivePreviewingSingleInputVideoGraphFactory implements PreviewingVideoGraph.Factory {
        private final VideoFrameProcessor.Factory videoFrameProcessorFactory;

        public ReflectivePreviewingSingleInputVideoGraphFactory(VideoFrameProcessor.Factory factory) {
            this.videoFrameProcessorFactory = factory;
        }

        @Override // androidx.media3.common.PreviewingVideoGraph.Factory
        public PreviewingVideoGraph create(Context context, ColorInfo colorInfo, DebugViewProvider debugViewProvider, VideoGraph.Listener listener, Executor executor, List<Effect> list, long j2) throws VideoFrameProcessingException {
            try {
                try {
                    return ((PreviewingVideoGraph.Factory) Class.forName("androidx.media3.effect.PreviewingSingleInputVideoGraph$Factory").getConstructor(VideoFrameProcessor.Factory.class).newInstance(this.videoFrameProcessorFactory)).create(context, colorInfo, debugViewProvider, listener, executor, list, j2);
                } catch (Exception e2) {
                    e = e2;
                    throw VideoFrameProcessingException.from(e);
                }
            } catch (Exception e3) {
                e = e3;
            }
        }
    }

    private static final class ScaleAndRotateAccessor {
        private static Method buildScaleAndRotateTransformationMethod;
        private static Constructor<?> scaleAndRotateTransformationBuilderConstructor;
        private static Method setRotationMethod;

        private ScaleAndRotateAccessor() {
        }

        public static Effect createRotationEffect(float f2) throws IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
            try {
                prepare();
                Object objNewInstance = scaleAndRotateTransformationBuilderConstructor.newInstance(null);
                setRotationMethod.invoke(objNewInstance, Float.valueOf(f2));
                return (Effect) Assertions.checkNotNull(buildScaleAndRotateTransformationMethod.invoke(objNewInstance, null));
            } catch (Exception e2) {
                throw new IllegalStateException(e2);
            }
        }

        @EnsuresNonNull({"scaleAndRotateTransformationBuilderConstructor", "setRotationMethod", "buildScaleAndRotateTransformationMethod"})
        private static void prepare() throws NoSuchMethodException, ClassNotFoundException {
            if (scaleAndRotateTransformationBuilderConstructor == null || setRotationMethod == null || buildScaleAndRotateTransformationMethod == null) {
                Class<?> cls = Class.forName("androidx.media3.effect.ScaleAndRotateTransformation$Builder");
                scaleAndRotateTransformationBuilderConstructor = cls.getConstructor(null);
                setRotationMethod = cls.getMethod("setRotationDegrees", Float.TYPE);
                buildScaleAndRotateTransformationMethod = cls.getMethod("build", null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class VideoSinkImpl implements VideoSink, Listener {
        private final Context context;
        private boolean hasRegisteredFirstInputStream;
        private long inputBufferTimestampAdjustmentUs;

        @Nullable
        private Format inputFormat;
        private long inputStreamOffsetUs;
        private int inputType;
        private long pendingInputStreamBufferPresentationTimeUs;
        private boolean pendingInputStreamOffsetChange;

        @Nullable
        private Effect rotationEffect;
        private VideoFrameProcessor videoFrameProcessor;
        private final int videoFrameProcessorMaxPendingFrameCount;
        private final ArrayList<Effect> videoEffects = new ArrayList<>();
        private long finalBufferPresentationTimeUs = C.TIME_UNSET;
        private long lastBufferPresentationTimeUs = C.TIME_UNSET;
        private VideoSink.Listener listener = VideoSink.Listener.NO_OP;
        private Executor listenerExecutor = CompositingVideoSinkProvider.NO_OP_EXECUTOR;

        public VideoSinkImpl(Context context) {
            this.context = context;
            this.videoFrameProcessorMaxPendingFrameCount = Util.getMaxPendingFramesCountForMediaCodecDecoders(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$3(VideoSink.Listener listener, VideoFrameProcessingException videoFrameProcessingException) {
            listener.onError(this, new VideoSink.VideoSinkException(videoFrameProcessingException, (Format) Assertions.checkStateNotNull(this.inputFormat)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFirstFrameRendered$0(VideoSink.Listener listener) {
            listener.onFirstFrameRendered(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFrameDropped$1(VideoSink.Listener listener) {
            listener.onFrameDropped((VideoSink) Assertions.checkStateNotNull(this));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVideoSizeChanged$2(VideoSink.Listener listener, VideoSize videoSize) {
            listener.onVideoSizeChanged(this, videoSize);
        }

        private void maybeRegisterInputStream() {
            if (this.inputFormat == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            Effect effect = this.rotationEffect;
            if (effect != null) {
                arrayList.add(effect);
            }
            arrayList.addAll(this.videoEffects);
            Format format = (Format) Assertions.checkNotNull(this.inputFormat);
            ((VideoFrameProcessor) Assertions.checkStateNotNull(this.videoFrameProcessor)).registerInputStream(this.inputType, arrayList, new FrameInfo.Builder(CompositingVideoSinkProvider.getAdjustedInputColorInfo(format.colorInfo), format.width, format.height).setPixelWidthHeightRatio(format.pixelWidthHeightRatio).build());
            this.finalBufferPresentationTimeUs = C.TIME_UNSET;
        }

        private boolean maybeRegisterPendingInputStream() {
            long j2 = this.pendingInputStreamBufferPresentationTimeUs;
            if (j2 == C.TIME_UNSET) {
                return true;
            }
            if (!CompositingVideoSinkProvider.this.hasReleasedFrame(j2)) {
                return false;
            }
            maybeRegisterInputStream();
            this.pendingInputStreamBufferPresentationTimeUs = C.TIME_UNSET;
            return true;
        }

        private void maybeSetStreamOffsetChange(long j2) {
            if (this.pendingInputStreamOffsetChange) {
                CompositingVideoSinkProvider.this.onStreamOffsetChange(this.inputBufferTimestampAdjustmentUs, j2, this.inputStreamOffsetUs);
                this.pendingInputStreamOffsetChange = false;
            }
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void clearOutputSurfaceInfo() {
            CompositingVideoSinkProvider.this.clearOutputSurfaceInfo();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void enableMayRenderStartOfStream() {
            CompositingVideoSinkProvider.this.videoFrameReleaseControl.allowReleaseFirstFrameBeforeStarted();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void flush(boolean z2) {
            if (isInitialized()) {
                this.videoFrameProcessor.flush();
            }
            this.hasRegisteredFirstInputStream = false;
            this.finalBufferPresentationTimeUs = C.TIME_UNSET;
            this.lastBufferPresentationTimeUs = C.TIME_UNSET;
            CompositingVideoSinkProvider.this.flush();
            if (z2) {
                CompositingVideoSinkProvider.this.videoFrameReleaseControl.reset();
            }
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public Surface getInputSurface() {
            Assertions.checkState(isInitialized());
            return ((VideoFrameProcessor) Assertions.checkStateNotNull(this.videoFrameProcessor)).getInputSurface();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void initialize(Format format) throws VideoSink.VideoSinkException {
            Assertions.checkState(!isInitialized());
            this.videoFrameProcessor = CompositingVideoSinkProvider.this.initialize(format);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean isEnded() {
            if (isInitialized()) {
                long j2 = this.finalBufferPresentationTimeUs;
                if (j2 != C.TIME_UNSET && CompositingVideoSinkProvider.this.hasReleasedFrame(j2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean isFrameDropAllowedOnInput() {
            return Util.isFrameDropAllowedOnSurfaceInput(this.context);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        @EnsuresNonNullIf(expression = {"videoFrameProcessor"}, result = true)
        public boolean isInitialized() {
            return this.videoFrameProcessor != null;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean isReady() {
            return isInitialized() && CompositingVideoSinkProvider.this.isReady();
        }

        @Override // androidx.media3.exoplayer.video.CompositingVideoSinkProvider.Listener
        public void onError(CompositingVideoSinkProvider compositingVideoSinkProvider, final VideoFrameProcessingException videoFrameProcessingException) {
            final VideoSink.Listener listener = this.listener;
            this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.g
                @Override // java.lang.Runnable
                public final void run() {
                    this.f5552a.lambda$onError$3(listener, videoFrameProcessingException);
                }
            });
        }

        @Override // androidx.media3.exoplayer.video.CompositingVideoSinkProvider.Listener
        public void onFirstFrameRendered(CompositingVideoSinkProvider compositingVideoSinkProvider) {
            final VideoSink.Listener listener = this.listener;
            this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f5555a.lambda$onFirstFrameRendered$0(listener);
                }
            });
        }

        @Override // androidx.media3.exoplayer.video.CompositingVideoSinkProvider.Listener
        public void onFrameDropped(CompositingVideoSinkProvider compositingVideoSinkProvider) {
            final VideoSink.Listener listener = this.listener;
            this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f5550a.lambda$onFrameDropped$1(listener);
                }
            });
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void onRendererDisabled() {
            CompositingVideoSinkProvider.this.videoFrameReleaseControl.onDisabled();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void onRendererEnabled(boolean z2) {
            CompositingVideoSinkProvider.this.videoFrameReleaseControl.onEnabled(z2);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void onRendererStarted() {
            CompositingVideoSinkProvider.this.videoFrameReleaseControl.onStarted();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void onRendererStopped() {
            CompositingVideoSinkProvider.this.videoFrameReleaseControl.onStopped();
        }

        @Override // androidx.media3.exoplayer.video.CompositingVideoSinkProvider.Listener
        public void onVideoSizeChanged(CompositingVideoSinkProvider compositingVideoSinkProvider, final VideoSize videoSize) {
            final VideoSink.Listener listener = this.listener;
            this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f5547a.lambda$onVideoSizeChanged$2(listener, videoSize);
                }
            });
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean queueBitmap(Bitmap bitmap, TimestampIterator timestampIterator) {
            Assertions.checkState(isInitialized());
            if (!maybeRegisterPendingInputStream() || !((VideoFrameProcessor) Assertions.checkStateNotNull(this.videoFrameProcessor)).queueInputBitmap(bitmap, timestampIterator)) {
                return false;
            }
            TimestampIterator timestampIteratorCopyOf = timestampIterator.copyOf();
            long next = timestampIteratorCopyOf.next();
            long lastTimestampUs = timestampIteratorCopyOf.getLastTimestampUs() - this.inputBufferTimestampAdjustmentUs;
            Assertions.checkState(lastTimestampUs != C.TIME_UNSET);
            maybeSetStreamOffsetChange(next);
            this.lastBufferPresentationTimeUs = lastTimestampUs;
            this.finalBufferPresentationTimeUs = lastTimestampUs;
            return true;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public long registerInputFrame(long j2, boolean z2) {
            Assertions.checkState(isInitialized());
            Assertions.checkState(this.videoFrameProcessorMaxPendingFrameCount != -1);
            long j3 = this.pendingInputStreamBufferPresentationTimeUs;
            if (j3 != C.TIME_UNSET) {
                if (!CompositingVideoSinkProvider.this.hasReleasedFrame(j3)) {
                    return C.TIME_UNSET;
                }
                maybeRegisterInputStream();
                this.pendingInputStreamBufferPresentationTimeUs = C.TIME_UNSET;
            }
            if (((VideoFrameProcessor) Assertions.checkStateNotNull(this.videoFrameProcessor)).getPendingInputFrameCount() >= this.videoFrameProcessorMaxPendingFrameCount || !((VideoFrameProcessor) Assertions.checkStateNotNull(this.videoFrameProcessor)).registerInputFrame()) {
                return C.TIME_UNSET;
            }
            long j4 = j2 - this.inputBufferTimestampAdjustmentUs;
            maybeSetStreamOffsetChange(j4);
            this.lastBufferPresentationTimeUs = j4;
            if (z2) {
                this.finalBufferPresentationTimeUs = j4;
            }
            return j2 * 1000;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void registerInputStream(int i2, Format format) {
            int i3;
            Format format2;
            Assertions.checkState(isInitialized());
            if (i2 != 1 && i2 != 2) {
                throw new UnsupportedOperationException("Unsupported input type " + i2);
            }
            CompositingVideoSinkProvider.this.videoFrameReleaseControl.setFrameRate(format.frameRate);
            if (i2 != 1 || Util.SDK_INT >= 21 || (i3 = format.rotationDegrees) == -1 || i3 == 0) {
                this.rotationEffect = null;
            } else if (this.rotationEffect == null || (format2 = this.inputFormat) == null || format2.rotationDegrees != i3) {
                this.rotationEffect = ScaleAndRotateAccessor.createRotationEffect(i3);
            }
            this.inputType = i2;
            this.inputFormat = format;
            if (this.hasRegisteredFirstInputStream) {
                Assertions.checkState(this.lastBufferPresentationTimeUs != C.TIME_UNSET);
                this.pendingInputStreamBufferPresentationTimeUs = this.lastBufferPresentationTimeUs;
            } else {
                maybeRegisterInputStream();
                this.hasRegisteredFirstInputStream = true;
                this.pendingInputStreamBufferPresentationTimeUs = C.TIME_UNSET;
            }
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void release() {
            CompositingVideoSinkProvider.this.release();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void render(long j2, long j3) throws VideoSink.VideoSinkException {
            try {
                CompositingVideoSinkProvider.this.render(j2, j3);
            } catch (ExoPlaybackException e2) {
                Format formatBuild = this.inputFormat;
                if (formatBuild == null) {
                    formatBuild = new Format.Builder().build();
                }
                throw new VideoSink.VideoSinkException(e2, formatBuild);
            }
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setListener(VideoSink.Listener listener, Executor executor) {
            this.listener = listener;
            this.listenerExecutor = executor;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setOutputSurfaceInfo(Surface surface, Size size) {
            CompositingVideoSinkProvider.this.setOutputSurfaceInfo(surface, size);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setPendingVideoEffects(List<Effect> list) {
            this.videoEffects.clear();
            this.videoEffects.addAll(list);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setPlaybackSpeed(@FloatRange(from = 0.0d, fromInclusive = false) float f2) {
            CompositingVideoSinkProvider.this.setPlaybackSpeed(f2);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setStreamOffsetAndAdjustmentUs(long j2, long j3) {
            this.pendingInputStreamOffsetChange |= (this.inputStreamOffsetUs == j2 && this.inputBufferTimestampAdjustmentUs == j3) ? false : true;
            this.inputStreamOffsetUs = j2;
            this.inputBufferTimestampAdjustmentUs = j3;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setVideoEffects(List<Effect> list) {
            if (this.videoEffects.equals(list)) {
                return;
            }
            setPendingVideoEffects(list);
            maybeRegisterInputStream();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
            CompositingVideoSinkProvider.this.setVideoFrameMetadataListener(videoFrameMetadataListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flush() {
        if (isInitialized()) {
            this.pendingFlushCount++;
            this.videoFrameRenderControl.flush();
            ((HandlerWrapper) Assertions.checkStateNotNull(this.handler)).post(new Runnable() { // from class: androidx.media3.exoplayer.video.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f5546a.flushInternal();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flushInternal() {
        int i2 = this.pendingFlushCount - 1;
        this.pendingFlushCount = i2;
        if (i2 > 0) {
            return;
        }
        if (i2 < 0) {
            throw new IllegalStateException(String.valueOf(this.pendingFlushCount));
        }
        this.videoFrameRenderControl.flush();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ColorInfo getAdjustedInputColorInfo(@Nullable ColorInfo colorInfo) {
        return (colorInfo == null || !colorInfo.isDataSpaceValid()) ? ColorInfo.SDR_BT709_LIMITED : colorInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasReleasedFrame(long j2) {
        return this.pendingFlushCount == 0 && this.videoFrameRenderControl.hasReleasedFrame(j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VideoFrameProcessor initialize(Format format) throws VideoSink.VideoSinkException {
        Assertions.checkState(this.state == 0);
        ColorInfo adjustedInputColorInfo = getAdjustedInputColorInfo(format.colorInfo);
        if (adjustedInputColorInfo.colorTransfer == 7 && Util.SDK_INT < 34) {
            adjustedInputColorInfo = adjustedInputColorInfo.buildUpon().setColorTransfer(6).build();
        }
        ColorInfo colorInfo = adjustedInputColorInfo;
        final HandlerWrapper handlerWrapperCreateHandler = this.clock.createHandler((Looper) Assertions.checkStateNotNull(Looper.myLooper()), null);
        this.handler = handlerWrapperCreateHandler;
        try {
            PreviewingVideoGraph.Factory factory = this.previewingVideoGraphFactory;
            Context context = this.context;
            DebugViewProvider debugViewProvider = DebugViewProvider.NONE;
            Objects.requireNonNull(handlerWrapperCreateHandler);
            this.videoGraph = factory.create(context, colorInfo, debugViewProvider, this, new Executor() { // from class: androidx.media3.exoplayer.video.a
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    handlerWrapperCreateHandler.post(runnable);
                }
            }, ImmutableList.of(), 0L);
            Pair<Surface, Size> pair = this.currentSurfaceAndSize;
            if (pair != null) {
                Surface surface = (Surface) pair.first;
                Size size = (Size) pair.second;
                maybeSetOutputSurfaceInfo(surface, size.getWidth(), size.getHeight());
            }
            this.videoGraph.registerInput(0);
            this.state = 1;
            return this.videoGraph.getProcessor(0);
        } catch (VideoFrameProcessingException e2) {
            throw new VideoSink.VideoSinkException(e2, format);
        }
    }

    private boolean isInitialized() {
        return this.state == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isReady() {
        return this.pendingFlushCount == 0 && this.videoFrameRenderControl.isReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$0(Runnable runnable) {
    }

    private void maybeSetOutputSurfaceInfo(@Nullable Surface surface, int i2, int i3) {
        if (this.videoGraph != null) {
            this.videoGraph.setOutputSurfaceInfo(surface != null ? new SurfaceInfo(surface, i2, i3) : null);
            this.videoFrameReleaseControl.setOutputSurface(surface);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStreamOffsetChange(long j2, long j3, long j4) {
        this.bufferTimestampAdjustmentUs = j2;
        this.videoFrameRenderControl.onStreamOffsetChange(j3, j4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlaybackSpeed(float f2) {
        this.videoFrameRenderControl.setPlaybackSpeed(f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        this.videoFrameMetadataListener = videoFrameMetadataListener;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void clearOutputSurfaceInfo() {
        Size size = Size.UNKNOWN;
        maybeSetOutputSurfaceInfo(null, size.getWidth(), size.getHeight());
        this.currentSurfaceAndSize = null;
    }

    @Nullable
    public Surface getOutputSurface() {
        Pair<Surface, Size> pair = this.currentSurfaceAndSize;
        if (pair != null) {
            return (Surface) pair.first;
        }
        return null;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public VideoSink getSink() {
        return this.videoSinkImpl;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public VideoFrameReleaseControl getVideoFrameReleaseControl() {
        return this.videoFrameReleaseControl;
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onEnded(long j2) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onError(VideoFrameProcessingException videoFrameProcessingException) {
        Iterator<Listener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onError(this, videoFrameProcessingException);
        }
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onOutputFrameAvailableForRendering(long j2) {
        if (this.pendingFlushCount > 0) {
            return;
        }
        this.videoFrameRenderControl.onOutputFrameAvailableForRendering(j2 - this.bufferTimestampAdjustmentUs);
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onOutputSizeChanged(int i2, int i3) {
        this.videoFrameRenderControl.onOutputSizeChanged(i2, i3);
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void release() {
        if (this.state == 2) {
            return;
        }
        HandlerWrapper handlerWrapper = this.handler;
        if (handlerWrapper != null) {
            handlerWrapper.removeCallbacksAndMessages(null);
        }
        PreviewingVideoGraph previewingVideoGraph = this.videoGraph;
        if (previewingVideoGraph != null) {
            previewingVideoGraph.release();
        }
        this.currentSurfaceAndSize = null;
        this.state = 2;
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public void render(long j2, long j3) throws ExoPlaybackException {
        if (this.pendingFlushCount == 0) {
            this.videoFrameRenderControl.render(j2, j3);
        }
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setOutputSurfaceInfo(Surface surface, Size size) {
        Pair<Surface, Size> pair = this.currentSurfaceAndSize;
        if (pair != null && ((Surface) pair.first).equals(surface) && ((Size) this.currentSurfaceAndSize.second).equals(size)) {
            return;
        }
        this.currentSurfaceAndSize = Pair.create(surface, size);
        maybeSetOutputSurfaceInfo(surface, size.getWidth(), size.getHeight());
    }

    private CompositingVideoSinkProvider(Builder builder) {
        Context context = builder.context;
        this.context = context;
        VideoSinkImpl videoSinkImpl = new VideoSinkImpl(context);
        this.videoSinkImpl = videoSinkImpl;
        Clock clock = builder.clock;
        this.clock = clock;
        VideoFrameReleaseControl videoFrameReleaseControl = builder.videoFrameReleaseControl;
        this.videoFrameReleaseControl = videoFrameReleaseControl;
        videoFrameReleaseControl.setClock(clock);
        this.videoFrameRenderControl = new VideoFrameRenderControl(new FrameRendererImpl(), videoFrameReleaseControl);
        this.previewingVideoGraphFactory = (PreviewingVideoGraph.Factory) Assertions.checkStateNotNull(builder.previewingVideoGraphFactory);
        this.listeners = new CopyOnWriteArraySet<>();
        this.state = 0;
        addListener(videoSinkImpl);
    }
}
