package androidx.media3.exoplayer.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.CircularIntArray;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.mediacodec.MediaCodecAdapter;
import com.kingsmith.miot.KsProperty;
import java.util.ArrayDeque;

@RequiresApi(23)
/* loaded from: classes.dex */
final class AsynchronousMediaCodecCallback extends MediaCodec.Callback {
    private final HandlerThread callbackThread;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private MediaFormat currentFormat;
    private Handler handler;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private IllegalStateException internalException;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private MediaCodec.CryptoException mediaCodecCryptoException;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private MediaCodec.CodecException mediaCodecException;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private MediaCodecAdapter.OnBufferAvailableListener onBufferAvailableListener;

    @GuardedBy(KsProperty.Lock)
    private long pendingFlushCount;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private MediaFormat pendingOutputFormat;

    @GuardedBy(KsProperty.Lock)
    private boolean shutDown;
    private final Object lock = new Object();

    @GuardedBy(KsProperty.Lock)
    private final CircularIntArray availableInputBuffers = new CircularIntArray();

    @GuardedBy(KsProperty.Lock)
    private final CircularIntArray availableOutputBuffers = new CircularIntArray();

    @GuardedBy(KsProperty.Lock)
    private final ArrayDeque<MediaCodec.BufferInfo> bufferInfos = new ArrayDeque<>();

    @GuardedBy(KsProperty.Lock)
    private final ArrayDeque<MediaFormat> formats = new ArrayDeque<>();

    AsynchronousMediaCodecCallback(HandlerThread handlerThread) {
        this.callbackThread = handlerThread;
    }

    @GuardedBy(KsProperty.Lock)
    private void addOutputFormat(MediaFormat mediaFormat) {
        this.availableOutputBuffers.addLast(-2);
        this.formats.add(mediaFormat);
    }

    @GuardedBy(KsProperty.Lock)
    private void flushInternal() {
        if (!this.formats.isEmpty()) {
            this.pendingOutputFormat = this.formats.getLast();
        }
        this.availableInputBuffers.clear();
        this.availableOutputBuffers.clear();
        this.bufferInfos.clear();
        this.formats.clear();
    }

    @GuardedBy(KsProperty.Lock)
    private boolean isFlushingOrShutdown() {
        return this.pendingFlushCount > 0 || this.shutDown;
    }

    @GuardedBy(KsProperty.Lock)
    private void maybeThrowException() {
        maybeThrowInternalException();
        maybeThrowMediaCodecException();
        maybeThrowMediaCodecCryptoException();
    }

    @GuardedBy(KsProperty.Lock)
    private void maybeThrowInternalException() {
        IllegalStateException illegalStateException = this.internalException;
        if (illegalStateException == null) {
            return;
        }
        this.internalException = null;
        throw illegalStateException;
    }

    @GuardedBy(KsProperty.Lock)
    private void maybeThrowMediaCodecCryptoException() {
        MediaCodec.CryptoException cryptoException = this.mediaCodecCryptoException;
        if (cryptoException == null) {
            return;
        }
        this.mediaCodecCryptoException = null;
        throw cryptoException;
    }

    @GuardedBy(KsProperty.Lock)
    private void maybeThrowMediaCodecException() {
        MediaCodec.CodecException codecException = this.mediaCodecException;
        if (codecException == null) {
            return;
        }
        this.mediaCodecException = null;
        throw codecException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFlushCompleted() {
        synchronized (this.lock) {
            try {
                if (this.shutDown) {
                    return;
                }
                long j2 = this.pendingFlushCount - 1;
                this.pendingFlushCount = j2;
                if (j2 > 0) {
                    return;
                }
                if (j2 < 0) {
                    setInternalException(new IllegalStateException());
                } else {
                    flushInternal();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void setInternalException(IllegalStateException illegalStateException) {
        synchronized (this.lock) {
            this.internalException = illegalStateException;
        }
    }

    public int dequeueInputBufferIndex() {
        synchronized (this.lock) {
            try {
                maybeThrowException();
                int iPopFirst = -1;
                if (isFlushingOrShutdown()) {
                    return -1;
                }
                if (!this.availableInputBuffers.isEmpty()) {
                    iPopFirst = this.availableInputBuffers.popFirst();
                }
                return iPopFirst;
            } finally {
            }
        }
    }

    public int dequeueOutputBufferIndex(MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.lock) {
            try {
                maybeThrowException();
                if (isFlushingOrShutdown()) {
                    return -1;
                }
                if (this.availableOutputBuffers.isEmpty()) {
                    return -1;
                }
                int iPopFirst = this.availableOutputBuffers.popFirst();
                if (iPopFirst >= 0) {
                    Assertions.checkStateNotNull(this.currentFormat);
                    MediaCodec.BufferInfo bufferInfoRemove = this.bufferInfos.remove();
                    bufferInfo.set(bufferInfoRemove.offset, bufferInfoRemove.size, bufferInfoRemove.presentationTimeUs, bufferInfoRemove.flags);
                } else if (iPopFirst == -2) {
                    this.currentFormat = this.formats.remove();
                }
                return iPopFirst;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void flush() {
        synchronized (this.lock) {
            this.pendingFlushCount++;
            ((Handler) Util.castNonNull(this.handler)).post(new Runnable() { // from class: androidx.media3.exoplayer.mediacodec.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f5305a.onFlushCompleted();
                }
            });
        }
    }

    public MediaFormat getOutputFormat() {
        MediaFormat mediaFormat;
        synchronized (this.lock) {
            try {
                mediaFormat = this.currentFormat;
                if (mediaFormat == null) {
                    throw new IllegalStateException();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return mediaFormat;
    }

    public void initialize(MediaCodec mediaCodec) {
        Assertions.checkState(this.handler == null);
        this.callbackThread.start();
        Handler handler = new Handler(this.callbackThread.getLooper());
        mediaCodec.setCallback(this, handler);
        this.handler = handler;
    }

    @Override // android.media.MediaCodec.Callback
    public void onCryptoError(MediaCodec mediaCodec, MediaCodec.CryptoException cryptoException) {
        synchronized (this.lock) {
            this.mediaCodecCryptoException = cryptoException;
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
        synchronized (this.lock) {
            this.mediaCodecException = codecException;
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onInputBufferAvailable(MediaCodec mediaCodec, int i2) {
        synchronized (this.lock) {
            try {
                this.availableInputBuffers.addLast(i2);
                MediaCodecAdapter.OnBufferAvailableListener onBufferAvailableListener = this.onBufferAvailableListener;
                if (onBufferAvailableListener != null) {
                    onBufferAvailableListener.onInputBufferAvailable();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputBufferAvailable(MediaCodec mediaCodec, int i2, MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.lock) {
            try {
                MediaFormat mediaFormat = this.pendingOutputFormat;
                if (mediaFormat != null) {
                    addOutputFormat(mediaFormat);
                    this.pendingOutputFormat = null;
                }
                this.availableOutputBuffers.addLast(i2);
                this.bufferInfos.add(bufferInfo);
                MediaCodecAdapter.OnBufferAvailableListener onBufferAvailableListener = this.onBufferAvailableListener;
                if (onBufferAvailableListener != null) {
                    onBufferAvailableListener.onOutputBufferAvailable();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        synchronized (this.lock) {
            addOutputFormat(mediaFormat);
            this.pendingOutputFormat = null;
        }
    }

    public void setOnBufferAvailableListener(MediaCodecAdapter.OnBufferAvailableListener onBufferAvailableListener) {
        synchronized (this.lock) {
            this.onBufferAvailableListener = onBufferAvailableListener;
        }
    }

    public void shutdown() {
        synchronized (this.lock) {
            this.shutDown = true;
            this.callbackThread.quit();
            flushInternal();
        }
    }
}
