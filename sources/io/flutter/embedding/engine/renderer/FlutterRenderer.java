package io.flutter.embedding.engine.renderer;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.view.TextureRegistry;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public class FlutterRenderer implements TextureRegistry {
    private static final String TAG = "FlutterRenderer";

    @VisibleForTesting
    public static boolean debugForceSurfaceProducerGlTextures = false;

    @NonNull
    private final FlutterJNI flutterJNI;

    @NonNull
    private final FlutterUiDisplayListener flutterUiDisplayListener;

    @Nullable
    private Surface surface;

    @NonNull
    private final AtomicLong nextTextureId = new AtomicLong(0);
    private boolean isDisplayingFlutterUi = false;
    private final Handler handler = new Handler();

    @NonNull
    private final Set<WeakReference<TextureRegistry.OnTrimMemoryListener>> onTrimMemoryListeners = new HashSet();

    public enum DisplayFeatureState {
        UNKNOWN(0),
        POSTURE_FLAT(1),
        POSTURE_HALF_OPENED(2);

        public final int encodedValue;

        DisplayFeatureState(int i2) {
            this.encodedValue = i2;
        }
    }

    public enum DisplayFeatureType {
        UNKNOWN(0),
        FOLD(1),
        HINGE(2),
        CUTOUT(3);

        public final int encodedValue;

        DisplayFeatureType(int i2) {
            this.encodedValue = i2;
        }
    }

    @Keep
    @TargetApi(29)
    final class ImageReaderSurfaceProducer implements TextureRegistry.SurfaceProducer, TextureRegistry.ImageConsumer, TextureRegistry.OnTrimMemoryListener {
        private static final boolean CLEANUP_ON_MEMORY_PRESSURE = true;
        private static final int MAX_IMAGES = 5;
        private static final String TAG = "ImageReaderSurfaceProducer";
        private static final boolean VERBOSE_LOGS = false;
        private final long id;
        private boolean released;
        private boolean ignoringFence = false;
        private boolean trimOnMemoryPressure = true;
        private int requestedWidth = 1;
        private int requestedHeight = 1;
        private boolean createNewReader = true;
        private long lastDequeueTime = 0;
        private long lastQueueTime = 0;
        private long lastScheduleTime = 0;
        private int numTrims = 0;
        private Object lock = new Object();
        private final ArrayDeque<PerImageReader> imageReaderQueue = new ArrayDeque<>();
        private final HashMap<ImageReader, PerImageReader> perImageReaders = new HashMap<>();
        private PerImage lastDequeuedImage = null;
        private PerImageReader lastReaderDequeuedFrom = null;

        private class PerImage {
            public final Image image;
            public final long queuedTime;

            public PerImage(Image image, long j2) {
                this.image = image;
                this.queuedTime = j2;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class PerImageReader {
            private final ImageReader.OnImageAvailableListener onImageAvailableListener;
            public final ImageReader reader;
            private final ArrayDeque<PerImage> imageQueue = new ArrayDeque<>();
            private boolean closed = false;

            public PerImageReader(ImageReader imageReader) {
                ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() { // from class: io.flutter.embedding.engine.renderer.i
                    @Override // android.media.ImageReader.OnImageAvailableListener
                    public final void onImageAvailable(ImageReader imageReader2) {
                        this.f25076a.lambda$new$0(imageReader2);
                    }
                };
                this.onImageAvailableListener = onImageAvailableListener;
                this.reader = imageReader;
                imageReader.setOnImageAvailableListener(onImageAvailableListener, new Handler(Looper.getMainLooper()));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$new$0(ImageReader imageReader) {
                Image imageAcquireLatestImage;
                try {
                    imageAcquireLatestImage = imageReader.acquireLatestImage();
                } catch (IllegalStateException e2) {
                    Log.e(ImageReaderSurfaceProducer.TAG, "onImageAvailable acquireLatestImage failed: " + e2);
                    imageAcquireLatestImage = null;
                }
                if (imageAcquireLatestImage == null) {
                    return;
                }
                if (ImageReaderSurfaceProducer.this.released || this.closed) {
                    imageAcquireLatestImage.close();
                } else {
                    ImageReaderSurfaceProducer.this.onImage(imageReader, imageAcquireLatestImage);
                }
            }

            boolean canPrune() {
                return this.imageQueue.size() == 0 && ImageReaderSurfaceProducer.this.lastReaderDequeuedFrom != this;
            }

            void close() {
                this.closed = true;
                this.reader.close();
                this.imageQueue.clear();
            }

            PerImage dequeueImage() {
                if (this.imageQueue.size() == 0) {
                    return null;
                }
                return this.imageQueue.removeFirst();
            }

            PerImage queueImage(Image image) {
                if (this.closed) {
                    return null;
                }
                PerImage perImage = ImageReaderSurfaceProducer.this.new PerImage(image, System.nanoTime());
                this.imageQueue.add(perImage);
                while (this.imageQueue.size() > 2) {
                    this.imageQueue.removeFirst().image.close();
                }
                return perImage;
            }
        }

        ImageReaderSurfaceProducer(long j2) {
            this.id = j2;
        }

        private void cleanup() {
            synchronized (this.lock) {
                try {
                    for (PerImageReader perImageReader : this.perImageReaders.values()) {
                        if (this.lastReaderDequeuedFrom == perImageReader) {
                            this.lastReaderDequeuedFrom = null;
                        }
                        perImageReader.close();
                    }
                    this.perImageReaders.clear();
                    PerImage perImage = this.lastDequeuedImage;
                    if (perImage != null) {
                        perImage.image.close();
                        this.lastDequeuedImage = null;
                    }
                    PerImageReader perImageReader2 = this.lastReaderDequeuedFrom;
                    if (perImageReader2 != null) {
                        perImageReader2.close();
                        this.lastReaderDequeuedFrom = null;
                    }
                    this.imageReaderQueue.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private ImageReader createImageReader() {
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 33) {
                return createImageReader33();
            }
            if (i2 >= 29) {
                return createImageReader29();
            }
            throw new UnsupportedOperationException("ImageReaderPlatformViewRenderTarget requires API version 29+");
        }

        @TargetApi(29)
        private ImageReader createImageReader29() {
            return ImageReader.newInstance(this.requestedWidth, this.requestedHeight, 34, 5, 256L);
        }

        @TargetApi(33)
        private ImageReader createImageReader33() {
            h.a();
            ImageReader.Builder builderA = g.a(this.requestedWidth, this.requestedHeight);
            builderA.setMaxImages(5);
            builderA.setImageFormat(34);
            builderA.setUsage(256L);
            return builderA.build();
        }

        private PerImageReader getActiveReader() {
            synchronized (this.lock) {
                try {
                    if (!this.createNewReader) {
                        return this.imageReaderQueue.peekLast();
                    }
                    this.createNewReader = false;
                    return getOrCreatePerImageReader(createImageReader());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private void maybeWaitOnFence(Image image) {
            if (image == null || this.ignoringFence) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 33) {
                waitOnFence(image);
            } else {
                this.ignoringFence = true;
                Log.w(TAG, "ImageTextureEntry can't wait on the fence on Android < 33");
            }
        }

        private void releaseInternal() {
            cleanup();
            this.released = true;
        }

        @TargetApi(33)
        private void waitOnFence(Image image) {
            try {
                image.getFence().awaitForever();
            } catch (IOException unused) {
            }
        }

        @Override // io.flutter.view.TextureRegistry.ImageConsumer
        @TargetApi(29)
        public Image acquireLatestImage() {
            PerImage perImageDequeueImage = dequeueImage();
            if (perImageDequeueImage == null) {
                return null;
            }
            maybeWaitOnFence(perImageDequeueImage.image);
            return perImageDequeueImage.image;
        }

        double deltaMillis(long j2) {
            return j2 / 1000000.0d;
        }

        PerImage dequeueImage() {
            PerImage perImage;
            synchronized (this.lock) {
                try {
                    Iterator<PerImageReader> it = this.imageReaderQueue.iterator();
                    perImage = null;
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        PerImageReader next = it.next();
                        PerImage perImageDequeueImage = next.dequeueImage();
                        if (perImageDequeueImage == null) {
                            perImage = perImageDequeueImage;
                        } else {
                            PerImage perImage2 = this.lastDequeuedImage;
                            if (perImage2 != null) {
                                perImage2.image.close();
                                this.lastDequeuedImage = null;
                            }
                            this.lastDequeuedImage = perImageDequeueImage;
                            this.lastReaderDequeuedFrom = next;
                            perImage = perImageDequeueImage;
                        }
                    }
                    pruneImageReaderQueue();
                } catch (Throwable th) {
                    throw th;
                }
            }
            return perImage;
        }

        @VisibleForTesting
        public void disableFenceForTest() {
            this.ignoringFence = true;
        }

        protected void finalize() throws Throwable {
            try {
                if (this.released) {
                    return;
                }
                releaseInternal();
                FlutterRenderer.this.handler.post(new TextureFinalizerRunnable(this.id, FlutterRenderer.this.flutterJNI));
            } finally {
                super.finalize();
            }
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceProducer
        public int getHeight() {
            return this.requestedHeight;
        }

        PerImageReader getOrCreatePerImageReader(ImageReader imageReader) {
            PerImageReader perImageReader = this.perImageReaders.get(imageReader);
            if (perImageReader != null) {
                return perImageReader;
            }
            PerImageReader perImageReader2 = new PerImageReader(imageReader);
            this.perImageReaders.put(imageReader, perImageReader2);
            this.imageReaderQueue.add(perImageReader2);
            return perImageReader2;
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceProducer
        public Surface getSurface() {
            return getActiveReader().reader.getSurface();
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceProducer
        public int getWidth() {
            return this.requestedWidth;
        }

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        public long id() {
            return this.id;
        }

        @VisibleForTesting
        public int numImageReaders() {
            int size;
            synchronized (this.lock) {
                size = this.imageReaderQueue.size();
            }
            return size;
        }

        @VisibleForTesting
        public int numImages() {
            int size;
            synchronized (this.lock) {
                try {
                    Iterator<PerImageReader> it = this.imageReaderQueue.iterator();
                    size = 0;
                    while (it.hasNext()) {
                        size += it.next().imageQueue.size();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return size;
        }

        @VisibleForTesting
        public int numTrims() {
            int i2;
            synchronized (this.lock) {
                i2 = this.numTrims;
            }
            return i2;
        }

        void onImage(ImageReader imageReader, Image image) {
            PerImage perImageQueueImage;
            synchronized (this.lock) {
                perImageQueueImage = getOrCreatePerImageReader(imageReader).queueImage(image);
            }
            if (perImageQueueImage == null) {
                return;
            }
            FlutterRenderer.this.scheduleEngineFrame();
        }

        @Override // io.flutter.view.TextureRegistry.OnTrimMemoryListener
        public void onTrimMemory(int i2) {
            if (this.trimOnMemoryPressure && i2 >= 40) {
                synchronized (this.lock) {
                    this.numTrims++;
                }
                cleanup();
                this.createNewReader = true;
            }
        }

        void pruneImageReaderQueue() {
            while (this.imageReaderQueue.size() > 1) {
                PerImageReader perImageReaderPeekFirst = this.imageReaderQueue.peekFirst();
                if (!perImageReaderPeekFirst.canPrune()) {
                    return;
                }
                this.imageReaderQueue.removeFirst();
                this.perImageReaders.remove(perImageReaderPeekFirst.reader);
                perImageReaderPeekFirst.close();
            }
        }

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        public void release() {
            if (this.released) {
                return;
            }
            releaseInternal();
            FlutterRenderer.this.unregisterTexture(this.id);
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceProducer
        public void scheduleFrame() {
            FlutterRenderer.this.scheduleEngineFrame();
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceProducer
        public void setSize(int i2, int i3) {
            int iMax = Math.max(1, i2);
            int iMax2 = Math.max(1, i3);
            if (this.requestedWidth == iMax && this.requestedHeight == iMax2) {
                return;
            }
            this.createNewReader = true;
            this.requestedHeight = iMax2;
            this.requestedWidth = iMax;
        }
    }

    @Keep
    final class ImageTextureRegistryEntry implements TextureRegistry.ImageTextureEntry, TextureRegistry.ImageConsumer {
        private static final String TAG = "ImageTextureRegistryEntry";
        private final long id;
        private boolean ignoringFence = false;
        private Image image;
        private boolean released;

        ImageTextureRegistryEntry(long j2) {
            this.id = j2;
        }

        @TargetApi(29)
        private void maybeWaitOnFence(Image image) {
            if (image == null || this.ignoringFence) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 33) {
                waitOnFence(image);
            } else {
                this.ignoringFence = true;
                Log.w(TAG, "ImageTextureEntry can't wait on the fence on Android < 33");
            }
        }

        @TargetApi(33)
        private void waitOnFence(Image image) {
            try {
                image.getFence().awaitForever();
            } catch (IOException unused) {
            }
        }

        @Override // io.flutter.view.TextureRegistry.ImageConsumer
        @TargetApi(29)
        public Image acquireLatestImage() {
            Image image;
            synchronized (this) {
                image = this.image;
                this.image = null;
            }
            maybeWaitOnFence(image);
            return image;
        }

        protected void finalize() throws Throwable {
            try {
                if (this.released) {
                    super.finalize();
                    return;
                }
                Image image = this.image;
                if (image != null) {
                    image.close();
                    this.image = null;
                }
                this.released = true;
                FlutterRenderer.this.handler.post(new TextureFinalizerRunnable(this.id, FlutterRenderer.this.flutterJNI));
                super.finalize();
            } catch (Throwable th) {
                super.finalize();
                throw th;
            }
        }

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        public long id() {
            return this.id;
        }

        @Override // io.flutter.view.TextureRegistry.ImageTextureEntry
        public void pushImage(Image image) {
            Image image2;
            if (this.released) {
                return;
            }
            synchronized (this) {
                image2 = this.image;
                this.image = image;
            }
            if (image2 != null) {
                Log.e(TAG, "Dropping PlatformView Frame");
                image2.close();
            }
            if (image != null) {
                FlutterRenderer.this.scheduleEngineFrame();
            }
        }

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        public void release() {
            if (this.released) {
                return;
            }
            this.released = true;
            Image image = this.image;
            if (image != null) {
                image.close();
                this.image = null;
            }
            FlutterRenderer.this.unregisterTexture(this.id);
        }
    }

    final class SurfaceTextureRegistryEntry implements TextureRegistry.SurfaceTextureEntry, TextureRegistry.OnTrimMemoryListener {

        @Nullable
        private TextureRegistry.OnFrameConsumedListener frameConsumedListener;
        private final long id;
        private boolean released;

        @NonNull
        private final SurfaceTextureWrapper textureWrapper;

        @Nullable
        private TextureRegistry.OnTrimMemoryListener trimMemoryListener;

        SurfaceTextureRegistryEntry(long j2, @NonNull SurfaceTexture surfaceTexture) {
            this.id = j2;
            this.textureWrapper = new SurfaceTextureWrapper(surfaceTexture, new Runnable() { // from class: io.flutter.embedding.engine.renderer.j
                @Override // java.lang.Runnable
                public final void run() {
                    this.f25077a.lambda$new$0();
                }
            });
            surfaceTexture().setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: io.flutter.embedding.engine.renderer.k
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public final void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                    this.f25078a.lambda$new$1(surfaceTexture2);
                }
            }, new Handler());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            TextureRegistry.OnFrameConsumedListener onFrameConsumedListener = this.frameConsumedListener;
            if (onFrameConsumedListener != null) {
                onFrameConsumedListener.onFrameConsumed();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(SurfaceTexture surfaceTexture) {
            if (this.released || !FlutterRenderer.this.flutterJNI.isAttached()) {
                return;
            }
            this.textureWrapper.markDirty();
            FlutterRenderer.this.scheduleEngineFrame();
        }

        private void removeListener() {
            FlutterRenderer.this.removeOnTrimMemoryListener(this);
        }

        protected void finalize() throws Throwable {
            try {
                if (this.released) {
                    return;
                }
                FlutterRenderer.this.handler.post(new TextureFinalizerRunnable(this.id, FlutterRenderer.this.flutterJNI));
            } finally {
                super.finalize();
            }
        }

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        public long id() {
            return this.id;
        }

        @Override // io.flutter.view.TextureRegistry.OnTrimMemoryListener
        public void onTrimMemory(int i2) {
            TextureRegistry.OnTrimMemoryListener onTrimMemoryListener = this.trimMemoryListener;
            if (onTrimMemoryListener != null) {
                onTrimMemoryListener.onTrimMemory(i2);
            }
        }

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        public void release() {
            if (this.released) {
                return;
            }
            Log.v(FlutterRenderer.TAG, "Releasing a SurfaceTexture (" + this.id + ").");
            this.textureWrapper.release();
            FlutterRenderer.this.unregisterTexture(this.id);
            removeListener();
            this.released = true;
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceTextureEntry
        public void setOnFrameConsumedListener(@Nullable TextureRegistry.OnFrameConsumedListener onFrameConsumedListener) {
            this.frameConsumedListener = onFrameConsumedListener;
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceTextureEntry
        public void setOnTrimMemoryListener(@Nullable TextureRegistry.OnTrimMemoryListener onTrimMemoryListener) {
            this.trimMemoryListener = onTrimMemoryListener;
        }

        @Override // io.flutter.view.TextureRegistry.SurfaceTextureEntry
        @NonNull
        public SurfaceTexture surfaceTexture() {
            return this.textureWrapper.surfaceTexture();
        }

        @NonNull
        public SurfaceTextureWrapper textureWrapper() {
            return this.textureWrapper;
        }
    }

    static final class TextureFinalizerRunnable implements Runnable {
        private final FlutterJNI flutterJNI;
        private final long id;

        TextureFinalizerRunnable(long j2, @NonNull FlutterJNI flutterJNI) {
            this.id = j2;
            this.flutterJNI = flutterJNI;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.flutterJNI.isAttached()) {
                Log.v(FlutterRenderer.TAG, "Releasing a Texture (" + this.id + ").");
                this.flutterJNI.unregisterTexture(this.id);
            }
        }
    }

    public static final class ViewportMetrics {
        public static final int unsetValue = -1;
        public float devicePixelRatio = 1.0f;
        public int width = 0;
        public int height = 0;
        public int viewPaddingTop = 0;
        public int viewPaddingRight = 0;
        public int viewPaddingBottom = 0;
        public int viewPaddingLeft = 0;
        public int viewInsetTop = 0;
        public int viewInsetRight = 0;
        public int viewInsetBottom = 0;
        public int viewInsetLeft = 0;
        public int systemGestureInsetTop = 0;
        public int systemGestureInsetRight = 0;
        public int systemGestureInsetBottom = 0;
        public int systemGestureInsetLeft = 0;
        public int physicalTouchSlop = -1;
        public List<DisplayFeature> displayFeatures = new ArrayList();

        boolean validate() {
            return this.width > 0 && this.height > 0 && this.devicePixelRatio > 0.0f;
        }
    }

    public FlutterRenderer(@NonNull FlutterJNI flutterJNI) {
        FlutterUiDisplayListener flutterUiDisplayListener = new FlutterUiDisplayListener() { // from class: io.flutter.embedding.engine.renderer.FlutterRenderer.1
            @Override // io.flutter.embedding.engine.renderer.FlutterUiDisplayListener
            public void onFlutterUiDisplayed() {
                FlutterRenderer.this.isDisplayingFlutterUi = true;
            }

            @Override // io.flutter.embedding.engine.renderer.FlutterUiDisplayListener
            public void onFlutterUiNoLongerDisplayed() {
                FlutterRenderer.this.isDisplayingFlutterUi = false;
            }
        };
        this.flutterUiDisplayListener = flutterUiDisplayListener;
        this.flutterJNI = flutterJNI;
        flutterJNI.addIsDisplayingFlutterUiListener(flutterUiDisplayListener);
    }

    private void clearDeadListeners() {
        Iterator<WeakReference<TextureRegistry.OnTrimMemoryListener>> it = this.onTrimMemoryListeners.iterator();
        while (it.hasNext()) {
            if (it.next().get() == null) {
                it.remove();
            }
        }
    }

    private void markTextureFrameAvailable(long j2) {
        this.flutterJNI.markTextureFrameAvailable(j2);
    }

    private void registerImageTexture(long j2, @NonNull TextureRegistry.ImageConsumer imageConsumer) {
        this.flutterJNI.registerImageTexture(j2, imageConsumer);
    }

    private void registerTexture(long j2, @NonNull SurfaceTextureWrapper surfaceTextureWrapper) {
        this.flutterJNI.registerTexture(j2, surfaceTextureWrapper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleEngineFrame() {
        this.flutterJNI.scheduleFrame();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterTexture(long j2) {
        this.flutterJNI.unregisterTexture(j2);
    }

    public void addIsDisplayingFlutterUiListener(@NonNull FlutterUiDisplayListener flutterUiDisplayListener) {
        this.flutterJNI.addIsDisplayingFlutterUiListener(flutterUiDisplayListener);
        if (this.isDisplayingFlutterUi) {
            flutterUiDisplayListener.onFlutterUiDisplayed();
        }
    }

    @VisibleForTesting
    void addOnTrimMemoryListener(@NonNull TextureRegistry.OnTrimMemoryListener onTrimMemoryListener) {
        clearDeadListeners();
        this.onTrimMemoryListeners.add(new WeakReference<>(onTrimMemoryListener));
    }

    @Override // io.flutter.view.TextureRegistry
    @NonNull
    public TextureRegistry.ImageTextureEntry createImageTexture() {
        ImageTextureRegistryEntry imageTextureRegistryEntry = new ImageTextureRegistryEntry(this.nextTextureId.getAndIncrement());
        Log.v(TAG, "New ImageTextureEntry ID: " + imageTextureRegistryEntry.id());
        registerImageTexture(imageTextureRegistryEntry.id(), imageTextureRegistryEntry);
        return imageTextureRegistryEntry;
    }

    @Override // io.flutter.view.TextureRegistry
    @NonNull
    public TextureRegistry.SurfaceProducer createSurfaceProducer() {
        if (debugForceSurfaceProducerGlTextures || Build.VERSION.SDK_INT < 29) {
            TextureRegistry.SurfaceTextureEntry surfaceTextureEntryCreateSurfaceTexture = createSurfaceTexture();
            SurfaceTextureSurfaceProducer surfaceTextureSurfaceProducer = new SurfaceTextureSurfaceProducer(surfaceTextureEntryCreateSurfaceTexture.id(), this.handler, this.flutterJNI, surfaceTextureEntryCreateSurfaceTexture);
            Log.v(TAG, "New SurfaceTextureSurfaceProducer ID: " + surfaceTextureEntryCreateSurfaceTexture.id());
            return surfaceTextureSurfaceProducer;
        }
        long andIncrement = this.nextTextureId.getAndIncrement();
        ImageReaderSurfaceProducer imageReaderSurfaceProducer = new ImageReaderSurfaceProducer(andIncrement);
        registerImageTexture(andIncrement, imageReaderSurfaceProducer);
        addOnTrimMemoryListener(imageReaderSurfaceProducer);
        Log.v(TAG, "New ImageReaderSurfaceProducer ID: " + andIncrement);
        return imageReaderSurfaceProducer;
    }

    @Override // io.flutter.view.TextureRegistry
    @NonNull
    public TextureRegistry.SurfaceTextureEntry createSurfaceTexture() {
        Log.v(TAG, "Creating a SurfaceTexture.");
        return registerSurfaceTexture(new SurfaceTexture(0));
    }

    public void dispatchPointerDataPacket(@NonNull ByteBuffer byteBuffer, int i2) {
        this.flutterJNI.dispatchPointerDataPacket(byteBuffer, i2);
    }

    public void dispatchSemanticsAction(int i2, int i3, @Nullable ByteBuffer byteBuffer, int i4) {
        this.flutterJNI.dispatchSemanticsAction(i2, i3, byteBuffer, i4);
    }

    public Bitmap getBitmap() {
        return this.flutterJNI.getBitmap();
    }

    public boolean isDisplayingFlutterUi() {
        return this.isDisplayingFlutterUi;
    }

    public boolean isSoftwareRenderingEnabled() {
        return this.flutterJNI.getIsSoftwareRenderingEnabled();
    }

    @Override // io.flutter.view.TextureRegistry
    public void onTrimMemory(int i2) {
        Iterator<WeakReference<TextureRegistry.OnTrimMemoryListener>> it = this.onTrimMemoryListeners.iterator();
        while (it.hasNext()) {
            TextureRegistry.OnTrimMemoryListener onTrimMemoryListener = it.next().get();
            if (onTrimMemoryListener != null) {
                onTrimMemoryListener.onTrimMemory(i2);
            } else {
                it.remove();
            }
        }
    }

    @Override // io.flutter.view.TextureRegistry
    @NonNull
    public TextureRegistry.SurfaceTextureEntry registerSurfaceTexture(@NonNull SurfaceTexture surfaceTexture) {
        return registerSurfaceTexture(this.nextTextureId.getAndIncrement(), surfaceTexture);
    }

    public void removeIsDisplayingFlutterUiListener(@NonNull FlutterUiDisplayListener flutterUiDisplayListener) {
        this.flutterJNI.removeIsDisplayingFlutterUiListener(flutterUiDisplayListener);
    }

    @VisibleForTesting
    void removeOnTrimMemoryListener(@NonNull TextureRegistry.OnTrimMemoryListener onTrimMemoryListener) {
        for (WeakReference<TextureRegistry.OnTrimMemoryListener> weakReference : this.onTrimMemoryListeners) {
            if (weakReference.get() == onTrimMemoryListener) {
                this.onTrimMemoryListeners.remove(weakReference);
                return;
            }
        }
    }

    public void setAccessibilityFeatures(int i2) {
        this.flutterJNI.setAccessibilityFeatures(i2);
    }

    public void setSemanticsEnabled(boolean z2) {
        this.flutterJNI.setSemanticsEnabled(z2);
    }

    public void setViewportMetrics(@NonNull ViewportMetrics viewportMetrics) {
        if (viewportMetrics.validate()) {
            Log.v(TAG, "Setting viewport metrics\nSize: " + viewportMetrics.width + " x " + viewportMetrics.height + "\nPadding - L: " + viewportMetrics.viewPaddingLeft + ", T: " + viewportMetrics.viewPaddingTop + ", R: " + viewportMetrics.viewPaddingRight + ", B: " + viewportMetrics.viewPaddingBottom + "\nInsets - L: " + viewportMetrics.viewInsetLeft + ", T: " + viewportMetrics.viewInsetTop + ", R: " + viewportMetrics.viewInsetRight + ", B: " + viewportMetrics.viewInsetBottom + "\nSystem Gesture Insets - L: " + viewportMetrics.systemGestureInsetLeft + ", T: " + viewportMetrics.systemGestureInsetTop + ", R: " + viewportMetrics.systemGestureInsetRight + ", B: " + viewportMetrics.systemGestureInsetRight + "\nDisplay Features: " + viewportMetrics.displayFeatures.size());
            int[] iArr = new int[viewportMetrics.displayFeatures.size() * 4];
            int[] iArr2 = new int[viewportMetrics.displayFeatures.size()];
            int[] iArr3 = new int[viewportMetrics.displayFeatures.size()];
            for (int i2 = 0; i2 < viewportMetrics.displayFeatures.size(); i2++) {
                DisplayFeature displayFeature = viewportMetrics.displayFeatures.get(i2);
                int i3 = i2 * 4;
                Rect rect = displayFeature.bounds;
                iArr[i3] = rect.left;
                iArr[i3 + 1] = rect.top;
                iArr[i3 + 2] = rect.right;
                iArr[i3 + 3] = rect.bottom;
                iArr2[i2] = displayFeature.type.encodedValue;
                iArr3[i2] = displayFeature.state.encodedValue;
            }
            this.flutterJNI.setViewportMetrics(viewportMetrics.devicePixelRatio, viewportMetrics.width, viewportMetrics.height, viewportMetrics.viewPaddingTop, viewportMetrics.viewPaddingRight, viewportMetrics.viewPaddingBottom, viewportMetrics.viewPaddingLeft, viewportMetrics.viewInsetTop, viewportMetrics.viewInsetRight, viewportMetrics.viewInsetBottom, viewportMetrics.viewInsetLeft, viewportMetrics.systemGestureInsetTop, viewportMetrics.systemGestureInsetRight, viewportMetrics.systemGestureInsetBottom, viewportMetrics.systemGestureInsetLeft, viewportMetrics.physicalTouchSlop, iArr, iArr2, iArr3);
        }
    }

    public void startRenderingToSurface(@NonNull Surface surface, boolean z2) {
        if (!z2) {
            stopRenderingToSurface();
        }
        this.surface = surface;
        if (z2) {
            this.flutterJNI.onSurfaceWindowChanged(surface);
        } else {
            this.flutterJNI.onSurfaceCreated(surface);
        }
    }

    public void stopRenderingToSurface() {
        if (this.surface != null) {
            this.flutterJNI.onSurfaceDestroyed();
            if (this.isDisplayingFlutterUi) {
                this.flutterUiDisplayListener.onFlutterUiNoLongerDisplayed();
            }
            this.isDisplayingFlutterUi = false;
            this.surface = null;
        }
    }

    public void surfaceChanged(int i2, int i3) {
        this.flutterJNI.onSurfaceChanged(i2, i3);
    }

    public void swapSurface(@NonNull Surface surface) {
        this.surface = surface;
        this.flutterJNI.onSurfaceWindowChanged(surface);
    }

    @NonNull
    private TextureRegistry.SurfaceTextureEntry registerSurfaceTexture(long j2, @NonNull SurfaceTexture surfaceTexture) {
        surfaceTexture.detachFromGLContext();
        SurfaceTextureRegistryEntry surfaceTextureRegistryEntry = new SurfaceTextureRegistryEntry(j2, surfaceTexture);
        Log.v(TAG, "New SurfaceTexture ID: " + surfaceTextureRegistryEntry.id());
        registerTexture(surfaceTextureRegistryEntry.id(), surfaceTextureRegistryEntry.textureWrapper());
        addOnTrimMemoryListener(surfaceTextureRegistryEntry);
        return surfaceTextureRegistryEntry;
    }

    public static final class DisplayFeature {
        public final Rect bounds;
        public final DisplayFeatureState state;
        public final DisplayFeatureType type;

        public DisplayFeature(Rect rect, DisplayFeatureType displayFeatureType, DisplayFeatureState displayFeatureState) {
            this.bounds = rect;
            this.type = displayFeatureType;
            this.state = displayFeatureState;
        }

        public DisplayFeature(Rect rect, DisplayFeatureType displayFeatureType) {
            this.bounds = rect;
            this.type = displayFeatureType;
            this.state = DisplayFeatureState.UNKNOWN;
        }
    }
}
