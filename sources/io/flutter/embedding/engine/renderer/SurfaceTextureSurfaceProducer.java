package io.flutter.embedding.engine.renderer;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.renderer.FlutterRenderer;
import io.flutter.view.TextureRegistry;

/* loaded from: classes4.dex */
final class SurfaceTextureSurfaceProducer implements TextureRegistry.SurfaceProducer, TextureRegistry.GLTextureConsumer {

    @NonNull
    private final FlutterJNI flutterJNI;

    @NonNull
    private final Handler handler;
    private final long id;
    private boolean released;
    private int requestBufferWidth;
    private int requestedBufferHeight;

    @Nullable
    private Surface surface;

    @NonNull
    private final TextureRegistry.SurfaceTextureEntry texture;

    SurfaceTextureSurfaceProducer(long j2, @NonNull Handler handler, @NonNull FlutterJNI flutterJNI, @NonNull TextureRegistry.SurfaceTextureEntry surfaceTextureEntry) {
        this.id = j2;
        this.handler = handler;
        this.flutterJNI = flutterJNI;
        this.texture = surfaceTextureEntry;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.released) {
                return;
            }
            release();
            this.handler.post(new FlutterRenderer.TextureFinalizerRunnable(this.id, this.flutterJNI));
        } finally {
            super.finalize();
        }
    }

    @Override // io.flutter.view.TextureRegistry.SurfaceProducer
    public int getHeight() {
        return this.requestedBufferHeight;
    }

    @Override // io.flutter.view.TextureRegistry.SurfaceProducer
    public Surface getSurface() {
        if (this.surface == null) {
            this.surface = new Surface(this.texture.surfaceTexture());
        }
        return this.surface;
    }

    @Override // io.flutter.view.TextureRegistry.GLTextureConsumer
    @NonNull
    public SurfaceTexture getSurfaceTexture() {
        return this.texture.surfaceTexture();
    }

    @Override // io.flutter.view.TextureRegistry.SurfaceProducer
    public int getWidth() {
        return this.requestBufferWidth;
    }

    @Override // io.flutter.view.TextureRegistry.TextureEntry
    public long id() {
        return this.id;
    }

    @Override // io.flutter.view.TextureRegistry.TextureEntry
    public void release() {
        this.texture.release();
        this.released = true;
    }

    @Override // io.flutter.view.TextureRegistry.SurfaceProducer
    public void scheduleFrame() {
        this.flutterJNI.markTextureFrameAvailable(this.id);
    }

    @Override // io.flutter.view.TextureRegistry.SurfaceProducer
    public void setSize(int i2, int i3) {
        this.requestBufferWidth = i2;
        this.requestedBufferHeight = i3;
        getSurfaceTexture().setDefaultBufferSize(i2, i3);
    }
}
