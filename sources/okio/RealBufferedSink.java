package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* loaded from: classes5.dex */
final class RealBufferedSink implements BufferedSink {

    /* renamed from: a, reason: collision with root package name */
    boolean f26489a;
    public final Buffer buffer = new Buffer();
    public final Sink sink;

    RealBufferedSink(Sink sink) {
        if (sink == null) {
            throw new NullPointerException("sink == null");
        }
        this.sink = sink;
    }

    @Override // okio.BufferedSink
    public Buffer buffer() {
        return this.buffer;
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.f26489a) {
            return;
        }
        try {
            Buffer buffer = this.buffer;
            long j2 = buffer.f26470b;
            if (j2 > 0) {
                this.sink.write(buffer, j2);
            }
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            this.sink.close();
        } catch (Throwable th2) {
            if (th == null) {
                th = th2;
            }
        }
        this.f26489a = true;
        if (th != null) {
            Util.sneakyRethrow(th);
        }
    }

    @Override // okio.BufferedSink
    public BufferedSink emit() throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        long size = this.buffer.size();
        if (size > 0) {
            this.sink.write(this.buffer, size);
        }
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink emitCompleteSegments() throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        long jCompleteSegmentByteCount = this.buffer.completeSegmentByteCount();
        if (jCompleteSegmentByteCount > 0) {
            this.sink.write(this.buffer, jCompleteSegmentByteCount);
        }
        return this;
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        Buffer buffer = this.buffer;
        long j2 = buffer.f26470b;
        if (j2 > 0) {
            this.sink.write(buffer, j2);
        }
        this.sink.flush();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.f26489a;
    }

    @Override // okio.BufferedSink
    public OutputStream outputStream() {
        return new OutputStream() { // from class: okio.RealBufferedSink.1
            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws Throwable {
                RealBufferedSink.this.close();
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public void flush() throws IOException {
                RealBufferedSink realBufferedSink = RealBufferedSink.this;
                if (realBufferedSink.f26489a) {
                    return;
                }
                realBufferedSink.flush();
            }

            public String toString() {
                return RealBufferedSink.this + ".outputStream()";
            }

            @Override // java.io.OutputStream
            public void write(int i2) throws IOException {
                RealBufferedSink realBufferedSink = RealBufferedSink.this;
                if (realBufferedSink.f26489a) {
                    throw new IOException("closed");
                }
                realBufferedSink.buffer.writeByte((int) ((byte) i2));
                RealBufferedSink.this.emitCompleteSegments();
            }

            @Override // java.io.OutputStream
            public void write(byte[] bArr, int i2, int i3) throws IOException {
                RealBufferedSink realBufferedSink = RealBufferedSink.this;
                if (!realBufferedSink.f26489a) {
                    realBufferedSink.buffer.write(bArr, i2, i3);
                    RealBufferedSink.this.emitCompleteSegments();
                    return;
                }
                throw new IOException("closed");
            }
        };
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "buffer(" + this.sink + ")";
    }

    @Override // okio.Sink
    public void write(Buffer buffer, long j2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(buffer, j2);
        emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j2 = 0;
        while (true) {
            long j3 = source.read(this.buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (j3 == -1) {
                return j2;
            }
            j2 += j3;
            emitCompleteSegments();
        }
    }

    @Override // okio.BufferedSink
    public BufferedSink writeByte(int i2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeByte(i2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeDecimalLong(long j2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeDecimalLong(j2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeHexadecimalUnsignedLong(long j2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeHexadecimalUnsignedLong(j2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeInt(int i2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeInt(i2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeIntLe(int i2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeIntLe(i2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeLong(long j2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeLong(j2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeLongLe(long j2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeLongLe(j2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeShort(int i2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeShort(i2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeShortLe(int i2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeShortLe(i2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeString(String str, Charset charset) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeString(str, charset);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8(String str) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeUtf8(str);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8CodePoint(int i2) throws IOException {
        if (this.f26489a) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeUtf8CodePoint(i2);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink write(ByteString byteString) throws IOException {
        if (!this.f26489a) {
            this.buffer.write(byteString);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink writeString(String str, int i2, int i3, Charset charset) throws IOException {
        if (!this.f26489a) {
            this.buffer.writeString(str, i2, i3, charset);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8(String str, int i2, int i3) throws IOException {
        if (!this.f26489a) {
            this.buffer.writeUtf8(str, i2, i3);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink write(byte[] bArr) throws IOException {
        if (!this.f26489a) {
            this.buffer.write(bArr);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink write(byte[] bArr, int i2, int i3) throws IOException {
        if (!this.f26489a) {
            this.buffer.write(bArr, i2, i3);
            return emitCompleteSegments();
        }
        throw new IllegalStateException("closed");
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) throws IOException {
        if (!this.f26489a) {
            int iWrite = this.buffer.write(byteBuffer);
            emitCompleteSegments();
            return iWrite;
        }
        throw new IllegalStateException("closed");
    }

    @Override // okio.BufferedSink
    public BufferedSink write(Source source, long j2) throws IOException {
        while (j2 > 0) {
            long j3 = source.read(this.buffer, j2);
            if (j3 != -1) {
                j2 -= j3;
                emitCompleteSegments();
            } else {
                throw new EOFException();
            }
        }
        return this;
    }
}
