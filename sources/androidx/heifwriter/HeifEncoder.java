package androidx.heifwriter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.Image;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class HeifEncoder implements AutoCloseable, SurfaceTexture.OnFrameAvailableListener {
    private static final boolean DEBUG = false;
    private static final int GRID_HEIGHT = 512;
    private static final int GRID_WIDTH = 512;
    private static final int INPUT_BUFFER_POOL_SIZE = 2;
    public static final int INPUT_MODE_BITMAP = 2;
    public static final int INPUT_MODE_BUFFER = 0;
    public static final int INPUT_MODE_SURFACE = 1;
    private static final double MAX_COMPRESS_RATIO = 0.25d;
    private static final String TAG = "HeifEncoder";

    /* renamed from: a, reason: collision with root package name */
    MediaCodec f4514a;

    /* renamed from: b, reason: collision with root package name */
    final Callback f4515b;

    /* renamed from: c, reason: collision with root package name */
    final Handler f4516c;

    /* renamed from: d, reason: collision with root package name */
    final int f4517d;

    /* renamed from: e, reason: collision with root package name */
    final int f4518e;

    /* renamed from: f, reason: collision with root package name */
    final int f4519f;

    /* renamed from: g, reason: collision with root package name */
    final int f4520g;

    /* renamed from: h, reason: collision with root package name */
    final int f4521h;

    /* renamed from: i, reason: collision with root package name */
    final int f4522i;

    /* renamed from: j, reason: collision with root package name */
    final boolean f4523j;

    /* renamed from: k, reason: collision with root package name */
    boolean f4524k;

    /* renamed from: m, reason: collision with root package name */
    SurfaceEOSTracker f4526m;
    private ByteBuffer mCurrentBuffer;
    private final Rect mDstRect;
    private EglWindowSurface mEncoderEglSurface;
    private Surface mEncoderSurface;
    private final HandlerThread mHandlerThread;
    private int mInputIndex;
    private final int mInputMode;
    private Surface mInputSurface;
    private SurfaceTexture mInputTexture;
    private final int mNumTiles;
    private EglRectBlt mRectBlt;
    private final Rect mSrcRect;
    private int mTextureId;
    private final ArrayList<ByteBuffer> mEmptyBuffers = new ArrayList<>();
    private final ArrayList<ByteBuffer> mFilledBuffers = new ArrayList<>();

    /* renamed from: l, reason: collision with root package name */
    final ArrayList f4525l = new ArrayList();
    private final float[] mTmpMatrix = new float[16];

    public static abstract class Callback {
        public abstract void onComplete(@NonNull HeifEncoder heifEncoder);

        public abstract void onDrainOutputBuffer(@NonNull HeifEncoder heifEncoder, @NonNull ByteBuffer byteBuffer);

        public abstract void onError(@NonNull HeifEncoder heifEncoder, @NonNull MediaCodec.CodecException codecException);

        public abstract void onOutputFormatChanged(@NonNull HeifEncoder heifEncoder, @NonNull MediaFormat mediaFormat);
    }

    class EncoderCallback extends MediaCodec.Callback {
        private boolean mOutputEOS;

        EncoderCallback() {
        }

        private void stopAndNotify(@Nullable MediaCodec.CodecException codecException) {
            HeifEncoder.this.f();
            if (codecException == null) {
                HeifEncoder heifEncoder = HeifEncoder.this;
                heifEncoder.f4515b.onComplete(heifEncoder);
            } else {
                HeifEncoder heifEncoder2 = HeifEncoder.this;
                heifEncoder2.f4515b.onError(heifEncoder2, codecException);
            }
        }

        @Override // android.media.MediaCodec.Callback
        public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
            if (mediaCodec != HeifEncoder.this.f4514a) {
                return;
            }
            Log.e(HeifEncoder.TAG, "onError: " + codecException);
            stopAndNotify(codecException);
        }

        @Override // android.media.MediaCodec.Callback
        public void onInputBufferAvailable(MediaCodec mediaCodec, int i2) throws MediaCodec.CryptoException {
            HeifEncoder heifEncoder = HeifEncoder.this;
            if (mediaCodec != heifEncoder.f4514a || heifEncoder.f4524k) {
                return;
            }
            heifEncoder.f4525l.add(Integer.valueOf(i2));
            HeifEncoder.this.e();
        }

        @Override // android.media.MediaCodec.Callback
        public void onOutputBufferAvailable(MediaCodec mediaCodec, int i2, MediaCodec.BufferInfo bufferInfo) {
            if (mediaCodec != HeifEncoder.this.f4514a || this.mOutputEOS) {
                return;
            }
            if (bufferInfo.size > 0 && (bufferInfo.flags & 2) == 0) {
                ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i2);
                outputBuffer.position(bufferInfo.offset);
                outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                SurfaceEOSTracker surfaceEOSTracker = HeifEncoder.this.f4526m;
                if (surfaceEOSTracker != null) {
                    surfaceEOSTracker.c(bufferInfo.presentationTimeUs);
                }
                HeifEncoder heifEncoder = HeifEncoder.this;
                heifEncoder.f4515b.onDrainOutputBuffer(heifEncoder, outputBuffer);
            }
            this.mOutputEOS = ((bufferInfo.flags & 4) != 0) | this.mOutputEOS;
            mediaCodec.releaseOutputBuffer(i2, false);
            if (this.mOutputEOS) {
                stopAndNotify(null);
            }
        }

        @Override // android.media.MediaCodec.Callback
        public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
            if (mediaCodec != HeifEncoder.this.f4514a) {
                return;
            }
            if (!"image/vnd.android.heic".equals(mediaFormat.getString("mime"))) {
                mediaFormat.setString("mime", "image/vnd.android.heic");
                mediaFormat.setInteger(ViewHierarchyConstants.DIMENSION_WIDTH_KEY, HeifEncoder.this.f4517d);
                mediaFormat.setInteger(ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, HeifEncoder.this.f4518e);
                HeifEncoder heifEncoder = HeifEncoder.this;
                if (heifEncoder.f4523j) {
                    mediaFormat.setInteger("tile-width", heifEncoder.f4519f);
                    mediaFormat.setInteger("tile-height", HeifEncoder.this.f4520g);
                    mediaFormat.setInteger("grid-rows", HeifEncoder.this.f4521h);
                    mediaFormat.setInteger("grid-cols", HeifEncoder.this.f4522i);
                }
            }
            HeifEncoder heifEncoder2 = HeifEncoder.this;
            heifEncoder2.f4515b.onOutputFormatChanged(heifEncoder2, mediaFormat);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputMode {
    }

    private class SurfaceEOSTracker {
        private static final boolean DEBUG_EOS = false;

        /* renamed from: a, reason: collision with root package name */
        final boolean f4530a;

        /* renamed from: b, reason: collision with root package name */
        long f4531b = -1;

        /* renamed from: c, reason: collision with root package name */
        long f4532c = -1;

        /* renamed from: d, reason: collision with root package name */
        long f4533d = -1;

        /* renamed from: e, reason: collision with root package name */
        long f4534e = -1;

        /* renamed from: f, reason: collision with root package name */
        long f4535f = -1;

        /* renamed from: g, reason: collision with root package name */
        boolean f4536g;

        SurfaceEOSTracker(boolean z2) {
            this.f4530a = z2;
        }

        private void doSignalEOSLocked() {
            HeifEncoder.this.f4516c.post(new Runnable() { // from class: androidx.heifwriter.HeifEncoder.SurfaceEOSTracker.1
                @Override // java.lang.Runnable
                public void run() {
                    MediaCodec mediaCodec = HeifEncoder.this.f4514a;
                    if (mediaCodec != null) {
                        mediaCodec.signalEndOfInputStream();
                    }
                }
            });
            this.f4536g = true;
        }

        private void updateEOSLocked() {
            if (this.f4536g) {
                return;
            }
            if (this.f4533d < 0) {
                long j2 = this.f4531b;
                if (j2 >= 0 && this.f4532c >= j2) {
                    long j3 = this.f4534e;
                    if (j3 < 0) {
                        doSignalEOSLocked();
                        return;
                    }
                    this.f4533d = j3;
                }
            }
            long j4 = this.f4533d;
            if (j4 < 0 || j4 > this.f4535f) {
                return;
            }
            doSignalEOSLocked();
        }

        synchronized void a(long j2) {
            try {
                if (this.f4530a) {
                    if (this.f4531b < 0) {
                        this.f4531b = j2;
                    }
                } else if (this.f4533d < 0) {
                    this.f4533d = j2 / 1000;
                }
                updateEOSLocked();
            } catch (Throwable th) {
                throw th;
            }
        }

        synchronized boolean b(long j2, long j3) {
            boolean z2;
            try {
                long j4 = this.f4531b;
                z2 = j4 < 0 || j2 <= j4;
                if (z2) {
                    this.f4534e = j3;
                }
                this.f4532c = j2;
                updateEOSLocked();
            } catch (Throwable th) {
                throw th;
            }
            return z2;
        }

        synchronized void c(long j2) {
            this.f4535f = j2;
            updateEOSLocked();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0234  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HeifEncoder(int r20, int r21, boolean r22, int r23, int r24, @androidx.annotation.Nullable android.os.Handler r25, @androidx.annotation.NonNull androidx.heifwriter.HeifEncoder.Callback r26) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 621
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.heifwriter.HeifEncoder.<init>(int, int, boolean, int, int, android.os.Handler, androidx.heifwriter.HeifEncoder$Callback):void");
    }

    private ByteBuffer acquireEmptyBuffer() {
        ByteBuffer byteBufferRemove;
        synchronized (this.mEmptyBuffers) {
            while (!this.f4524k && this.mEmptyBuffers.isEmpty()) {
                try {
                    this.mEmptyBuffers.wait();
                } catch (InterruptedException unused) {
                }
            }
            byteBufferRemove = this.f4524k ? null : this.mEmptyBuffers.remove(0);
        }
        return byteBufferRemove;
    }

    private void addYuvBufferInternal(@Nullable byte[] bArr) {
        ByteBuffer byteBufferAcquireEmptyBuffer = acquireEmptyBuffer();
        if (byteBufferAcquireEmptyBuffer == null) {
            return;
        }
        byteBufferAcquireEmptyBuffer.clear();
        if (bArr != null) {
            byteBufferAcquireEmptyBuffer.put(bArr);
        }
        byteBufferAcquireEmptyBuffer.flip();
        synchronized (this.mFilledBuffers) {
            this.mFilledBuffers.add(byteBufferAcquireEmptyBuffer);
        }
        this.f4516c.post(new Runnable() { // from class: androidx.heifwriter.HeifEncoder.1
            @Override // java.lang.Runnable
            public void run() throws MediaCodec.CryptoException {
                HeifEncoder.this.e();
            }
        });
    }

    private long computePresentationTime(int i2) {
        return ((i2 * 1000000) / this.mNumTiles) + 132;
    }

    private static void copyOneTileYUV(ByteBuffer byteBuffer, Image image, int i2, int i3, Rect rect, Rect rect2) {
        int i4;
        int i5;
        if (rect.width() != rect2.width() || rect.height() != rect2.height()) {
            throw new IllegalArgumentException("src and dst rect size are different!");
        }
        if (i2 % 2 == 0 && i3 % 2 == 0) {
            int i6 = 2;
            if (rect.left % 2 == 0 && rect.top % 2 == 0 && rect.right % 2 == 0 && rect.bottom % 2 == 0 && rect2.left % 2 == 0 && rect2.top % 2 == 0 && rect2.right % 2 == 0 && rect2.bottom % 2 == 0) {
                Image.Plane[] planes = image.getPlanes();
                int i7 = 0;
                while (i7 < planes.length) {
                    ByteBuffer buffer = planes[i7].getBuffer();
                    int pixelStride = planes[i7].getPixelStride();
                    int iMin = Math.min(rect.width(), i2 - rect.left);
                    int iMin2 = Math.min(rect.height(), i3 - rect.top);
                    if (i7 > 0) {
                        i5 = ((i2 * i3) * (i7 + 3)) / 4;
                        i4 = i6;
                    } else {
                        i4 = 1;
                        i5 = 0;
                    }
                    for (int i8 = 0; i8 < iMin2 / i4; i8++) {
                        byteBuffer.position(((((rect.top / i4) + i8) * i2) / i4) + i5 + (rect.left / i4));
                        buffer.position((((rect2.top / i4) + i8) * planes[i7].getRowStride()) + ((rect2.left * pixelStride) / i4));
                        int i9 = 0;
                        while (true) {
                            int i10 = iMin / i4;
                            if (i9 < i10) {
                                buffer.put(byteBuffer.get());
                                if (pixelStride > 1 && i9 != i10 - 1) {
                                    buffer.position((buffer.position() + pixelStride) - 1);
                                }
                                i9++;
                            }
                        }
                    }
                    i7++;
                    i6 = 2;
                }
                return;
            }
        }
        throw new IllegalArgumentException("src or dst are not aligned!");
    }

    private void copyTilesGL() {
        GLES20.glViewport(0, 0, this.f4519f, this.f4520g);
        for (int i2 = 0; i2 < this.f4521h; i2++) {
            for (int i3 = 0; i3 < this.f4522i; i3++) {
                int i4 = this.f4519f;
                int i5 = i3 * i4;
                int i6 = this.f4520g;
                int i7 = i2 * i6;
                this.mSrcRect.set(i5, i7, i4 + i5, i6 + i7);
                this.mRectBlt.copyRect(this.mTextureId, Texture2dProgram.V_FLIP_MATRIX, this.mSrcRect);
                EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
                int i8 = this.mInputIndex;
                this.mInputIndex = i8 + 1;
                eglWindowSurface.setPresentationTime(computePresentationTime(i8) * 1000);
                this.mEncoderEglSurface.swapBuffers();
            }
        }
    }

    private ByteBuffer getCurrentBuffer() {
        if (!this.f4524k && this.mCurrentBuffer == null) {
            synchronized (this.mFilledBuffers) {
                this.mCurrentBuffer = this.mFilledBuffers.isEmpty() ? null : this.mFilledBuffers.remove(0);
            }
        }
        if (this.f4524k) {
            return null;
        }
        return this.mCurrentBuffer;
    }

    private void returnEmptyBufferAndNotify(boolean z2) {
        synchronized (this.mEmptyBuffers) {
            this.f4524k = z2 | this.f4524k;
            this.mEmptyBuffers.add(this.mCurrentBuffer);
            this.mEmptyBuffers.notifyAll();
        }
        this.mCurrentBuffer = null;
    }

    public void addBitmap(@NonNull Bitmap bitmap) {
        if (this.mInputMode != 2) {
            throw new IllegalStateException("addBitmap is only allowed in bitmap input mode");
        }
        if (this.f4526m.b(computePresentationTime(this.mInputIndex) * 1000, computePresentationTime((this.mInputIndex + this.mNumTiles) - 1))) {
            synchronized (this) {
                try {
                    EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
                    if (eglWindowSurface == null) {
                        return;
                    }
                    eglWindowSurface.makeCurrent();
                    this.mRectBlt.loadTexture(this.mTextureId, bitmap);
                    copyTilesGL();
                    this.mEncoderEglSurface.makeUnCurrent();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void addYuvBuffer(int i2, @NonNull byte[] bArr) {
        if (this.mInputMode != 0) {
            throw new IllegalStateException("addYuvBuffer is only allowed in buffer input mode");
        }
        if (bArr == null || bArr.length != ((this.f4517d * this.f4518e) * 3) / 2) {
            throw new IllegalArgumentException("invalid data");
        }
        addYuvBufferInternal(bArr);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mEmptyBuffers) {
            this.f4524k = true;
            this.mEmptyBuffers.notifyAll();
        }
        this.f4516c.postAtFrontOfQueue(new Runnable() { // from class: androidx.heifwriter.HeifEncoder.2
            @Override // java.lang.Runnable
            public void run() {
                HeifEncoder.this.f();
            }
        });
    }

    void e() throws MediaCodec.CryptoException {
        while (true) {
            ByteBuffer currentBuffer = getCurrentBuffer();
            if (currentBuffer == null || this.f4525l.isEmpty()) {
                return;
            }
            int iIntValue = ((Integer) this.f4525l.remove(0)).intValue();
            boolean z2 = this.mInputIndex % this.mNumTiles == 0 && currentBuffer.remaining() == 0;
            if (!z2) {
                Image inputImage = this.f4514a.getInputImage(iIntValue);
                int i2 = this.f4519f;
                int i3 = this.mInputIndex;
                int i4 = this.f4522i;
                int i5 = (i3 % i4) * i2;
                int i6 = this.f4520g;
                int i7 = ((i3 / i4) % this.f4521h) * i6;
                this.mSrcRect.set(i5, i7, i2 + i5, i6 + i7);
                copyOneTileYUV(currentBuffer, inputImage, this.f4517d, this.f4518e, this.mSrcRect, this.mDstRect);
            }
            MediaCodec mediaCodec = this.f4514a;
            int iCapacity = z2 ? 0 : mediaCodec.getInputBuffer(iIntValue).capacity();
            int i8 = this.mInputIndex;
            this.mInputIndex = i8 + 1;
            mediaCodec.queueInputBuffer(iIntValue, 0, iCapacity, computePresentationTime(i8), z2 ? 4 : 0);
            if (z2 || this.mInputIndex % this.mNumTiles == 0) {
                returnEmptyBufferAndNotify(z2);
            }
        }
    }

    void f() {
        MediaCodec mediaCodec = this.f4514a;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.f4514a.release();
            this.f4514a = null;
        }
        synchronized (this.mEmptyBuffers) {
            this.f4524k = true;
            this.mEmptyBuffers.notifyAll();
        }
        synchronized (this) {
            try {
                EglRectBlt eglRectBlt = this.mRectBlt;
                if (eglRectBlt != null) {
                    eglRectBlt.release(false);
                    this.mRectBlt = null;
                }
                EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
                if (eglWindowSurface != null) {
                    eglWindowSurface.release();
                    this.mEncoderEglSurface = null;
                }
                SurfaceTexture surfaceTexture = this.mInputTexture;
                if (surfaceTexture != null) {
                    surfaceTexture.release();
                    this.mInputTexture = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @NonNull
    public Surface getInputSurface() {
        if (this.mInputMode == 1) {
            return this.mInputSurface;
        }
        throw new IllegalStateException("getInputSurface is only allowed in surface input mode");
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            try {
                EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
                if (eglWindowSurface == null) {
                    return;
                }
                eglWindowSurface.makeCurrent();
                surfaceTexture.updateTexImage();
                surfaceTexture.getTransformMatrix(this.mTmpMatrix);
                if (this.f4526m.b(surfaceTexture.getTimestamp(), computePresentationTime((this.mInputIndex + this.mNumTiles) - 1))) {
                    copyTilesGL();
                }
                surfaceTexture.releaseTexImage();
                this.mEncoderEglSurface.makeUnCurrent();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setEndOfInputStreamTimestamp(long j2) {
        if (this.mInputMode != 1) {
            throw new IllegalStateException("setEndOfInputStreamTimestamp is only allowed in surface input mode");
        }
        SurfaceEOSTracker surfaceEOSTracker = this.f4526m;
        if (surfaceEOSTracker != null) {
            surfaceEOSTracker.a(j2);
        }
    }

    public void start() {
        this.f4514a.start();
    }

    public void stopAsync() {
        int i2 = this.mInputMode;
        if (i2 == 2) {
            this.f4526m.a(0L);
        } else if (i2 == 0) {
            addYuvBufferInternal(null);
        }
    }
}
