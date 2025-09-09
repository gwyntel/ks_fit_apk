package androidx.media3.decoder;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.decoder.DecoderException;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.decoder.DecoderOutputBuffer;
import java.util.ArrayDeque;

@UnstableApi
/* loaded from: classes.dex */
public abstract class SimpleDecoder<I extends DecoderInputBuffer, O extends DecoderOutputBuffer, E extends DecoderException> implements Decoder<I, O, E> {
    private int availableInputBufferCount;
    private final I[] availableInputBuffers;
    private int availableOutputBufferCount;
    private final O[] availableOutputBuffers;
    private final Thread decodeThread;

    @Nullable
    private I dequeuedInputBuffer;

    @Nullable
    private E exception;
    private boolean flushed;
    private final Object lock = new Object();
    private long outputStartTimeUs = C.TIME_UNSET;
    private final ArrayDeque<I> queuedInputBuffers = new ArrayDeque<>();
    private final ArrayDeque<O> queuedOutputBuffers = new ArrayDeque<>();
    private boolean released;
    private int skippedOutputBufferCount;

    /* JADX WARN: Multi-variable type inference failed */
    protected SimpleDecoder(DecoderInputBuffer[] decoderInputBufferArr, DecoderOutputBuffer[] decoderOutputBufferArr) {
        this.availableInputBuffers = decoderInputBufferArr;
        this.availableInputBufferCount = decoderInputBufferArr.length;
        for (int i2 = 0; i2 < this.availableInputBufferCount; i2++) {
            ((I[]) this.availableInputBuffers)[i2] = b();
        }
        this.availableOutputBuffers = decoderOutputBufferArr;
        this.availableOutputBufferCount = decoderOutputBufferArr.length;
        for (int i3 = 0; i3 < this.availableOutputBufferCount; i3++) {
            ((O[]) this.availableOutputBuffers)[i3] = c();
        }
        Thread thread = new Thread("ExoPlayer:SimpleDecoder") { // from class: androidx.media3.decoder.SimpleDecoder.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SimpleDecoder.this.run();
            }
        };
        this.decodeThread = thread;
        thread.start();
    }

    private boolean canDecodeBuffer() {
        return !this.queuedInputBuffers.isEmpty() && this.availableOutputBufferCount > 0;
    }

    private boolean decode() throws InterruptedException {
        E e2;
        synchronized (this.lock) {
            while (!this.released && !canDecodeBuffer()) {
                try {
                    this.lock.wait();
                } finally {
                }
            }
            if (this.released) {
                return false;
            }
            I iRemoveFirst = this.queuedInputBuffers.removeFirst();
            O[] oArr = this.availableOutputBuffers;
            int i2 = this.availableOutputBufferCount - 1;
            this.availableOutputBufferCount = i2;
            O o2 = oArr[i2];
            boolean z2 = this.flushed;
            this.flushed = false;
            if (iRemoveFirst.isEndOfStream()) {
                o2.addFlag(4);
            } else {
                o2.timeUs = iRemoveFirst.timeUs;
                if (iRemoveFirst.isFirstSample()) {
                    o2.addFlag(C.BUFFER_FLAG_FIRST_SAMPLE);
                }
                if (!f(iRemoveFirst.timeUs)) {
                    o2.shouldBeSkipped = true;
                }
                try {
                    e2 = (E) e(iRemoveFirst, o2, z2);
                } catch (OutOfMemoryError e3) {
                    e2 = (E) d(e3);
                } catch (RuntimeException e4) {
                    e2 = (E) d(e4);
                }
                if (e2 != null) {
                    synchronized (this.lock) {
                        this.exception = e2;
                    }
                    return false;
                }
            }
            synchronized (this.lock) {
                try {
                    if (this.flushed) {
                        o2.release();
                    } else if (o2.shouldBeSkipped) {
                        this.skippedOutputBufferCount++;
                        o2.release();
                    } else {
                        o2.skippedOutputBufferCount = this.skippedOutputBufferCount;
                        this.skippedOutputBufferCount = 0;
                        this.queuedOutputBuffers.addLast(o2);
                    }
                    releaseInputBufferInternal(iRemoveFirst);
                } finally {
                }
            }
            return true;
        }
    }

    private void maybeNotifyDecodeLoop() {
        if (canDecodeBuffer()) {
            this.lock.notify();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: E extends androidx.media3.decoder.DecoderException */
    private void maybeThrowException() throws E, DecoderException {
        E e2 = this.exception;
        if (e2 != null) {
            throw e2;
        }
    }

    private void releaseInputBufferInternal(I i2) {
        i2.clear();
        I[] iArr = this.availableInputBuffers;
        int i3 = this.availableInputBufferCount;
        this.availableInputBufferCount = i3 + 1;
        iArr[i3] = i2;
    }

    private void releaseOutputBufferInternal(O o2) {
        o2.clear();
        O[] oArr = this.availableOutputBuffers;
        int i2 = this.availableOutputBufferCount;
        this.availableOutputBufferCount = i2 + 1;
        oArr[i2] = o2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void run() {
        do {
            try {
            } catch (InterruptedException e2) {
                throw new IllegalStateException(e2);
            }
        } while (decode());
    }

    protected abstract DecoderInputBuffer b();

    protected abstract DecoderOutputBuffer c();

    protected abstract DecoderException d(Throwable th);

    protected abstract DecoderException e(DecoderInputBuffer decoderInputBuffer, DecoderOutputBuffer decoderOutputBuffer, boolean z2);

    protected final boolean f(long j2) {
        boolean z2;
        synchronized (this.lock) {
            long j3 = this.outputStartTimeUs;
            z2 = j3 == C.TIME_UNSET || j2 >= j3;
        }
        return z2;
    }

    @Override // androidx.media3.decoder.Decoder
    public final void flush() {
        synchronized (this.lock) {
            try {
                this.flushed = true;
                this.skippedOutputBufferCount = 0;
                I i2 = this.dequeuedInputBuffer;
                if (i2 != null) {
                    releaseInputBufferInternal(i2);
                    this.dequeuedInputBuffer = null;
                }
                while (!this.queuedInputBuffers.isEmpty()) {
                    releaseInputBufferInternal(this.queuedInputBuffers.removeFirst());
                }
                while (!this.queuedOutputBuffers.isEmpty()) {
                    this.queuedOutputBuffers.removeFirst().release();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void g(DecoderOutputBuffer decoderOutputBuffer) {
        synchronized (this.lock) {
            releaseOutputBufferInternal(decoderOutputBuffer);
            maybeNotifyDecodeLoop();
        }
    }

    protected final void h(int i2) {
        Assertions.checkState(this.availableInputBufferCount == this.availableInputBuffers.length);
        for (I i3 : this.availableInputBuffers) {
            i3.ensureSpaceForWrite(i2);
        }
    }

    @Override // androidx.media3.decoder.Decoder
    @CallSuper
    public void release() throws InterruptedException {
        synchronized (this.lock) {
            this.released = true;
            this.lock.notify();
        }
        try {
            this.decodeThread.join();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    @Override // androidx.media3.decoder.Decoder
    public final void setOutputStartTimeUs(long j2) {
        synchronized (this.lock) {
            try {
                Assertions.checkState(this.availableInputBufferCount == this.availableInputBuffers.length || this.flushed);
                this.outputStartTimeUs = j2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.media3.decoder.Decoder
    @Nullable
    public final I dequeueInputBuffer() throws DecoderException {
        I i2;
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkState(this.dequeuedInputBuffer == null);
            int i3 = this.availableInputBufferCount;
            if (i3 == 0) {
                i2 = null;
            } else {
                I[] iArr = this.availableInputBuffers;
                int i4 = i3 - 1;
                this.availableInputBufferCount = i4;
                i2 = iArr[i4];
            }
            this.dequeuedInputBuffer = i2;
        }
        return i2;
    }

    @Override // androidx.media3.decoder.Decoder
    @Nullable
    public final O dequeueOutputBuffer() throws DecoderException {
        synchronized (this.lock) {
            try {
                maybeThrowException();
                if (this.queuedOutputBuffers.isEmpty()) {
                    return null;
                }
                return this.queuedOutputBuffers.removeFirst();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.media3.decoder.Decoder
    public final void queueInputBuffer(I i2) throws DecoderException {
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkArgument(i2 == this.dequeuedInputBuffer);
            this.queuedInputBuffers.addLast(i2);
            maybeNotifyDecodeLoop();
            this.dequeuedInputBuffer = null;
        }
    }
}
