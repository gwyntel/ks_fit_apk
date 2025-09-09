package androidx.heifwriter;

import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.heifwriter.HeifEncoder;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class HeifWriter implements AutoCloseable {
    private static final boolean DEBUG = false;
    public static final int INPUT_MODE_BITMAP = 2;
    public static final int INPUT_MODE_BUFFER = 0;
    public static final int INPUT_MODE_SURFACE = 1;
    private static final int MUXER_DATA_FLAG = 16;
    private static final String TAG = "HeifWriter";

    /* renamed from: a, reason: collision with root package name */
    int f4539a;

    /* renamed from: b, reason: collision with root package name */
    final int f4540b;

    /* renamed from: c, reason: collision with root package name */
    final int f4541c;

    /* renamed from: d, reason: collision with root package name */
    final int f4542d;

    /* renamed from: f, reason: collision with root package name */
    MediaMuxer f4544f;

    /* renamed from: h, reason: collision with root package name */
    int[] f4546h;

    /* renamed from: i, reason: collision with root package name */
    int f4547i;
    private final Handler mHandler;
    private final HandlerThread mHandlerThread;
    private HeifEncoder mHeifEncoder;
    private final int mInputMode;
    private boolean mStarted;

    /* renamed from: e, reason: collision with root package name */
    final ResultWaiter f4543e = new ResultWaiter();

    /* renamed from: g, reason: collision with root package name */
    final AtomicBoolean f4545g = new AtomicBoolean(false);
    private final List<Pair<Integer, ByteBuffer>> mExifList = new ArrayList();

    public static final class Builder {
        private final FileDescriptor mFd;
        private boolean mGridEnabled;
        private Handler mHandler;
        private final int mHeight;
        private final int mInputMode;
        private int mMaxImages;
        private final String mPath;
        private int mPrimaryIndex;
        private int mQuality;
        private int mRotation;
        private final int mWidth;

        public Builder(@NonNull String str, int i2, int i3, int i4) {
            this(str, null, i2, i3, i4);
        }

        public HeifWriter build() throws IOException {
            return new HeifWriter(this.mPath, this.mFd, this.mWidth, this.mHeight, this.mRotation, this.mGridEnabled, this.mQuality, this.mMaxImages, this.mPrimaryIndex, this.mInputMode, this.mHandler);
        }

        public Builder setGridEnabled(boolean z2) {
            this.mGridEnabled = z2;
            return this;
        }

        public Builder setHandler(@Nullable Handler handler) {
            this.mHandler = handler;
            return this;
        }

        public Builder setMaxImages(int i2) {
            if (i2 > 0) {
                this.mMaxImages = i2;
                return this;
            }
            throw new IllegalArgumentException("Invalid maxImage: " + i2);
        }

        public Builder setPrimaryIndex(int i2) {
            if (i2 >= 0) {
                this.mPrimaryIndex = i2;
                return this;
            }
            throw new IllegalArgumentException("Invalid primaryIndex: " + i2);
        }

        public Builder setQuality(int i2) {
            if (i2 >= 0 && i2 <= 100) {
                this.mQuality = i2;
                return this;
            }
            throw new IllegalArgumentException("Invalid quality: " + i2);
        }

        public Builder setRotation(int i2) {
            if (i2 == 0 || i2 == 90 || i2 == 180 || i2 == 270) {
                this.mRotation = i2;
                return this;
            }
            throw new IllegalArgumentException("Invalid rotation angle: " + i2);
        }

        public Builder(@NonNull FileDescriptor fileDescriptor, int i2, int i3, int i4) {
            this(null, fileDescriptor, i2, i3, i4);
        }

        private Builder(String str, FileDescriptor fileDescriptor, int i2, int i3, int i4) {
            this.mGridEnabled = true;
            this.mQuality = 100;
            this.mMaxImages = 1;
            this.mPrimaryIndex = 0;
            this.mRotation = 0;
            if (i2 > 0 && i3 > 0) {
                this.mPath = str;
                this.mFd = fileDescriptor;
                this.mWidth = i2;
                this.mHeight = i3;
                this.mInputMode = i4;
                return;
            }
            throw new IllegalArgumentException("Invalid image size: " + i2 + "x" + i3);
        }
    }

    class HeifCallback extends HeifEncoder.Callback {
        private boolean mEncoderStopped;

        HeifCallback() {
        }

        private void stopAndNotify(@Nullable Exception exc) {
            if (this.mEncoderStopped) {
                return;
            }
            this.mEncoderStopped = true;
            HeifWriter.this.f4543e.a(exc);
        }

        @Override // androidx.heifwriter.HeifEncoder.Callback
        public void onComplete(@NonNull HeifEncoder heifEncoder) {
            stopAndNotify(null);
        }

        @Override // androidx.heifwriter.HeifEncoder.Callback
        public void onDrainOutputBuffer(@NonNull HeifEncoder heifEncoder, @NonNull ByteBuffer byteBuffer) {
            if (this.mEncoderStopped) {
                return;
            }
            HeifWriter heifWriter = HeifWriter.this;
            if (heifWriter.f4546h == null) {
                stopAndNotify(new IllegalStateException("Output buffer received before format info"));
                return;
            }
            if (heifWriter.f4547i < heifWriter.f4541c * heifWriter.f4539a) {
                MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                bufferInfo.set(byteBuffer.position(), byteBuffer.remaining(), 0L, 0);
                HeifWriter heifWriter2 = HeifWriter.this;
                heifWriter2.f4544f.writeSampleData(heifWriter2.f4546h[heifWriter2.f4547i / heifWriter2.f4539a], byteBuffer, bufferInfo);
            }
            HeifWriter heifWriter3 = HeifWriter.this;
            int i2 = heifWriter3.f4547i + 1;
            heifWriter3.f4547i = i2;
            if (i2 == heifWriter3.f4541c * heifWriter3.f4539a) {
                stopAndNotify(null);
            }
        }

        @Override // androidx.heifwriter.HeifEncoder.Callback
        public void onError(@NonNull HeifEncoder heifEncoder, @NonNull MediaCodec.CodecException codecException) {
            stopAndNotify(codecException);
        }

        @Override // androidx.heifwriter.HeifEncoder.Callback
        public void onOutputFormatChanged(@NonNull HeifEncoder heifEncoder, @NonNull MediaFormat mediaFormat) {
            if (this.mEncoderStopped) {
                return;
            }
            if (HeifWriter.this.f4546h != null) {
                stopAndNotify(new IllegalStateException("Output format changed after muxer started"));
                return;
            }
            try {
                HeifWriter.this.f4539a = mediaFormat.getInteger("grid-rows") * mediaFormat.getInteger("grid-cols");
            } catch (ClassCastException | NullPointerException unused) {
                HeifWriter.this.f4539a = 1;
            }
            HeifWriter heifWriter = HeifWriter.this;
            heifWriter.f4546h = new int[heifWriter.f4541c];
            if (heifWriter.f4540b > 0) {
                Log.d(HeifWriter.TAG, "setting rotation: " + HeifWriter.this.f4540b);
                HeifWriter heifWriter2 = HeifWriter.this;
                heifWriter2.f4544f.setOrientationHint(heifWriter2.f4540b);
            }
            int i2 = 0;
            while (true) {
                HeifWriter heifWriter3 = HeifWriter.this;
                if (i2 >= heifWriter3.f4546h.length) {
                    heifWriter3.f4544f.start();
                    HeifWriter.this.f4545g.set(true);
                    HeifWriter.this.f();
                    return;
                } else {
                    mediaFormat.setInteger("is-default", i2 == heifWriter3.f4542d ? 1 : 0);
                    HeifWriter heifWriter4 = HeifWriter.this;
                    heifWriter4.f4546h[i2] = heifWriter4.f4544f.addTrack(mediaFormat);
                    i2++;
                }
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputMode {
    }

    static class ResultWaiter {
        private boolean mDone;
        private Exception mException;

        ResultWaiter() {
        }

        synchronized void a(Exception exc) {
            if (!this.mDone) {
                this.mDone = true;
                this.mException = exc;
                notifyAll();
            }
        }

        synchronized void b(long j2) {
            if (j2 < 0) {
                throw new IllegalArgumentException("timeoutMs is negative");
            }
            if (j2 == 0) {
                while (!this.mDone) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                    }
                }
            } else {
                long jCurrentTimeMillis = System.currentTimeMillis();
                while (!this.mDone && j2 > 0) {
                    try {
                        wait(j2);
                    } catch (InterruptedException unused2) {
                    }
                    j2 -= System.currentTimeMillis() - jCurrentTimeMillis;
                }
            }
            if (!this.mDone) {
                this.mDone = true;
                this.mException = new TimeoutException("timed out waiting for result");
            }
            Exception exc = this.mException;
            if (exc != null) {
                throw exc;
            }
        }
    }

    HeifWriter(String str, FileDescriptor fileDescriptor, int i2, int i3, int i4, boolean z2, int i5, int i6, int i7, int i8, Handler handler) {
        if (i7 >= i6) {
            throw new IllegalArgumentException("Invalid maxImages (" + i6 + ") or primaryIndex (" + i7 + ")");
        }
        MediaFormat.createVideoFormat("image/vnd.android.heic", i2, i3);
        this.f4539a = 1;
        this.f4540b = i4;
        this.mInputMode = i8;
        this.f4541c = i6;
        this.f4542d = i7;
        Looper looper = handler != null ? handler.getLooper() : null;
        if (looper == null) {
            HandlerThread handlerThread = new HandlerThread("HeifEncoderThread", -2);
            this.mHandlerThread = handlerThread;
            handlerThread.start();
            looper = handlerThread.getLooper();
        } else {
            this.mHandlerThread = null;
        }
        Handler handler2 = new Handler(looper);
        this.mHandler = handler2;
        this.f4544f = str != null ? new MediaMuxer(str, 3) : b.a(fileDescriptor, 3);
        this.mHeifEncoder = new HeifEncoder(i2, i3, z2, i5, i8, handler2, new HeifCallback());
    }

    private void checkMode(int i2) {
        if (this.mInputMode == i2) {
            return;
        }
        throw new IllegalStateException("Not valid in input mode " + this.mInputMode);
    }

    private void checkStarted(boolean z2) {
        if (this.mStarted != z2) {
            throw new IllegalStateException("Already started");
        }
    }

    private void checkStartedAndMode(int i2) {
        checkStarted(true);
        checkMode(i2);
    }

    public void addBitmap(@NonNull Bitmap bitmap) {
        checkStartedAndMode(2);
        synchronized (this) {
            try {
                HeifEncoder heifEncoder = this.mHeifEncoder;
                if (heifEncoder != null) {
                    heifEncoder.addBitmap(bitmap);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void addExifData(int i2, @NonNull byte[] bArr, int i3, int i4) {
        checkStarted(true);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i4);
        byteBufferAllocateDirect.put(bArr, i3, i4);
        byteBufferAllocateDirect.flip();
        synchronized (this.mExifList) {
            this.mExifList.add(new Pair<>(Integer.valueOf(i2), byteBufferAllocateDirect));
        }
        f();
    }

    public void addYuvBuffer(int i2, @NonNull byte[] bArr) {
        checkStartedAndMode(0);
        synchronized (this) {
            try {
                HeifEncoder heifEncoder = this.mHeifEncoder;
                if (heifEncoder != null) {
                    heifEncoder.addYuvBuffer(i2, bArr);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: androidx.heifwriter.HeifWriter.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    HeifWriter.this.e();
                } catch (Exception unused) {
                }
            }
        });
    }

    void e() {
        MediaMuxer mediaMuxer = this.f4544f;
        if (mediaMuxer != null) {
            mediaMuxer.stop();
            this.f4544f.release();
            this.f4544f = null;
        }
        HeifEncoder heifEncoder = this.mHeifEncoder;
        if (heifEncoder != null) {
            heifEncoder.close();
            synchronized (this) {
                this.mHeifEncoder = null;
            }
        }
    }

    void f() {
        Pair<Integer, ByteBuffer> pairRemove;
        if (!this.f4545g.get()) {
            return;
        }
        while (true) {
            synchronized (this.mExifList) {
                try {
                    if (this.mExifList.isEmpty()) {
                        return;
                    } else {
                        pairRemove = this.mExifList.remove(0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            bufferInfo.set(((ByteBuffer) pairRemove.second).position(), ((ByteBuffer) pairRemove.second).remaining(), 0L, 16);
            this.f4544f.writeSampleData(this.f4546h[((Integer) pairRemove.first).intValue()], (ByteBuffer) pairRemove.second, bufferInfo);
        }
    }

    @NonNull
    public Surface getInputSurface() {
        checkStarted(false);
        checkMode(1);
        return this.mHeifEncoder.getInputSurface();
    }

    public void setInputEndOfStreamTimestamp(long j2) {
        checkStartedAndMode(1);
        synchronized (this) {
            try {
                HeifEncoder heifEncoder = this.mHeifEncoder;
                if (heifEncoder != null) {
                    heifEncoder.setEndOfInputStreamTimestamp(j2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void start() {
        checkStarted(false);
        this.mStarted = true;
        this.mHeifEncoder.start();
    }

    public void stop(long j2) throws Exception {
        checkStarted(true);
        synchronized (this) {
            try {
                HeifEncoder heifEncoder = this.mHeifEncoder;
                if (heifEncoder != null) {
                    heifEncoder.stopAsync();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.f4543e.b(j2);
        f();
        e();
    }
}
