package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import kotlin.text.Typography;

/* loaded from: classes5.dex */
final class RealBufferedSource implements BufferedSource {

    /* renamed from: a, reason: collision with root package name */
    boolean f26491a;
    public final Buffer buffer = new Buffer();
    public final Source source;

    RealBufferedSource(Source source) {
        if (source == null) {
            throw new NullPointerException("source == null");
        }
        this.source = source;
    }

    @Override // okio.BufferedSource, okio.BufferedSink
    public Buffer buffer() {
        return this.buffer;
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.f26491a) {
            return;
        }
        this.f26491a = true;
        this.source.close();
        this.buffer.clear();
    }

    @Override // okio.BufferedSource
    public boolean exhausted() throws IOException {
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        return this.buffer.exhausted() && this.source.read(this.buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1;
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2) throws IOException {
        return indexOf(b2, 0L, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString) throws IOException {
        return indexOfElement(byteString, 0L);
    }

    @Override // okio.BufferedSource
    public InputStream inputStream() {
        return new InputStream() { // from class: okio.RealBufferedSource.1
            @Override // java.io.InputStream
            public int available() throws IOException {
                RealBufferedSource realBufferedSource = RealBufferedSource.this;
                if (realBufferedSource.f26491a) {
                    throw new IOException("closed");
                }
                return (int) Math.min(realBufferedSource.buffer.f26470b, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                RealBufferedSource.this.close();
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                RealBufferedSource realBufferedSource = RealBufferedSource.this;
                if (realBufferedSource.f26491a) {
                    throw new IOException("closed");
                }
                Buffer buffer = realBufferedSource.buffer;
                if (buffer.f26470b == 0 && realBufferedSource.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                return RealBufferedSource.this.buffer.readByte() & 255;
            }

            public String toString() {
                return RealBufferedSource.this + ".inputStream()";
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i2, int i3) throws IOException {
                if (!RealBufferedSource.this.f26491a) {
                    Util.checkOffsetAndCount(bArr.length, i2, i3);
                    RealBufferedSource realBufferedSource = RealBufferedSource.this;
                    Buffer buffer = realBufferedSource.buffer;
                    if (buffer.f26470b == 0 && realBufferedSource.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                        return -1;
                    }
                    return RealBufferedSource.this.buffer.read(bArr, i2, i3);
                }
                throw new IOException("closed");
            }
        };
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.f26491a;
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j2, ByteString byteString) throws IOException {
        return rangeEquals(j2, byteString, 0, byteString.size());
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j2) throws IOException {
        if (buffer == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        Buffer buffer2 = this.buffer;
        if (buffer2.f26470b == 0 && this.source.read(buffer2, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1L;
        }
        return this.buffer.read(buffer, Math.min(j2, this.buffer.f26470b));
    }

    @Override // okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        long j2 = 0;
        while (this.source.read(this.buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1) {
            long jCompleteSegmentByteCount = this.buffer.completeSegmentByteCount();
            if (jCompleteSegmentByteCount > 0) {
                j2 += jCompleteSegmentByteCount;
                sink.write(this.buffer, jCompleteSegmentByteCount);
            }
        }
        if (this.buffer.size() <= 0) {
            return j2;
        }
        long size = j2 + this.buffer.size();
        Buffer buffer = this.buffer;
        sink.write(buffer, buffer.size());
        return size;
    }

    @Override // okio.BufferedSource
    public byte readByte() throws IOException {
        require(1L);
        return this.buffer.readByte();
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteArray();
    }

    @Override // okio.BufferedSource
    public ByteString readByteString() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readByteString();
    }

    @Override // okio.BufferedSource
    public long readDecimalLong() throws IOException {
        byte b2;
        require(1L);
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (!request(i3)) {
                break;
            }
            b2 = this.buffer.getByte(i2);
            if ((b2 < 48 || b2 > 57) && !(i2 == 0 && b2 == 45)) {
                break;
            }
            i2 = i3;
        }
        if (i2 == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", Byte.valueOf(b2)));
        }
        return this.buffer.readDecimalLong();
    }

    @Override // okio.BufferedSource
    public void readFully(byte[] bArr) throws IOException {
        try {
            require(bArr.length);
            this.buffer.readFully(bArr);
        } catch (EOFException e2) {
            int i2 = 0;
            while (true) {
                Buffer buffer = this.buffer;
                long j2 = buffer.f26470b;
                if (j2 <= 0) {
                    throw e2;
                }
                int i3 = buffer.read(bArr, i2, (int) j2);
                if (i3 == -1) {
                    throw new AssertionError();
                }
                i2 += i3;
            }
        }
    }

    @Override // okio.BufferedSource
    public long readHexadecimalUnsignedLong() throws IOException {
        byte b2;
        require(1L);
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (!request(i3)) {
                break;
            }
            b2 = this.buffer.getByte(i2);
            if ((b2 < 48 || b2 > 57) && ((b2 < 97 || b2 > 102) && (b2 < 65 || b2 > 70))) {
                break;
            }
            i2 = i3;
        }
        if (i2 == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", Byte.valueOf(b2)));
        }
        return this.buffer.readHexadecimalUnsignedLong();
    }

    @Override // okio.BufferedSource
    public int readInt() throws IOException {
        require(4L);
        return this.buffer.readInt();
    }

    @Override // okio.BufferedSource
    public int readIntLe() throws IOException {
        require(4L);
        return this.buffer.readIntLe();
    }

    @Override // okio.BufferedSource
    public long readLong() throws IOException {
        require(8L);
        return this.buffer.readLong();
    }

    @Override // okio.BufferedSource
    public long readLongLe() throws IOException {
        require(8L);
        return this.buffer.readLongLe();
    }

    @Override // okio.BufferedSource
    public short readShort() throws IOException {
        require(2L);
        return this.buffer.readShort();
    }

    @Override // okio.BufferedSource
    public short readShortLe() throws IOException {
        require(2L);
        return this.buffer.readShortLe();
    }

    @Override // okio.BufferedSource
    public String readString(Charset charset) throws IOException {
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        this.buffer.writeAll(this.source);
        return this.buffer.readString(charset);
    }

    @Override // okio.BufferedSource
    public String readUtf8() throws IOException {
        this.buffer.writeAll(this.source);
        return this.buffer.readUtf8();
    }

    @Override // okio.BufferedSource
    public int readUtf8CodePoint() throws IOException {
        require(1L);
        byte b2 = this.buffer.getByte(0L);
        if ((b2 & 224) == 192) {
            require(2L);
        } else if ((b2 & 240) == 224) {
            require(3L);
        } else if ((b2 & 248) == 240) {
            require(4L);
        }
        return this.buffer.readUtf8CodePoint();
    }

    @Override // okio.BufferedSource
    @Nullable
    public String readUtf8Line() throws IOException {
        long jIndexOf = indexOf((byte) 10);
        if (jIndexOf != -1) {
            return this.buffer.b(jIndexOf);
        }
        long j2 = this.buffer.f26470b;
        if (j2 != 0) {
            return readUtf8(j2);
        }
        return null;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict() throws IOException {
        return readUtf8LineStrict(Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public boolean request(long j2) throws IOException {
        Buffer buffer;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        do {
            buffer = this.buffer;
            if (buffer.f26470b >= j2) {
                return true;
            }
        } while (this.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1);
        return false;
    }

    @Override // okio.BufferedSource
    public void require(long j2) throws IOException {
        if (!request(j2)) {
            throw new EOFException();
        }
    }

    @Override // okio.BufferedSource
    public int select(Options options) throws IOException {
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        do {
            int iC = this.buffer.c(options, true);
            if (iC == -1) {
                return -1;
            }
            if (iC != -2) {
                this.buffer.skip(options.f26479a[iC].size());
                return iC;
            }
        } while (this.source.read(this.buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1);
        return -1;
    }

    @Override // okio.BufferedSource
    public void skip(long j2) throws IOException {
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        while (j2 > 0) {
            Buffer buffer = this.buffer;
            if (buffer.f26470b == 0 && this.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j2, this.buffer.size());
            this.buffer.skip(jMin);
            j2 -= jMin;
        }
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.source.timeout();
    }

    public String toString() {
        return "buffer(" + this.source + ")";
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2, long j2) throws IOException {
        return indexOf(b2, j2, Long.MAX_VALUE);
    }

    @Override // okio.BufferedSource
    public long indexOfElement(ByteString byteString, long j2) throws IOException {
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long jIndexOfElement = this.buffer.indexOfElement(byteString, j2);
            if (jIndexOfElement != -1) {
                return jIndexOfElement;
            }
            Buffer buffer = this.buffer;
            long j3 = buffer.f26470b;
            if (this.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1L;
            }
            j2 = Math.max(j2, j3);
        }
    }

    @Override // okio.BufferedSource
    public boolean rangeEquals(long j2, ByteString byteString, int i2, int i3) throws IOException {
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        if (j2 < 0 || i2 < 0 || i3 < 0 || byteString.size() - i2 < i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            long j3 = i4 + j2;
            if (!request(1 + j3) || this.buffer.getByte(j3) != byteString.getByte(i2 + i4)) {
                return false;
            }
        }
        return true;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict(long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("limit < 0: " + j2);
        }
        long j3 = j2 == Long.MAX_VALUE ? Long.MAX_VALUE : j2 + 1;
        long jIndexOf = indexOf((byte) 10, 0L, j3);
        if (jIndexOf != -1) {
            return this.buffer.b(jIndexOf);
        }
        if (j3 < Long.MAX_VALUE && request(j3) && this.buffer.getByte(j3 - 1) == 13 && request(1 + j3) && this.buffer.getByte(j3) == 10) {
            return this.buffer.b(j3);
        }
        Buffer buffer = new Buffer();
        Buffer buffer2 = this.buffer;
        buffer2.copyTo(buffer, 0L, Math.min(32L, buffer2.size()));
        throw new EOFException("\\n not found: limit=" + Math.min(this.buffer.size(), j2) + " content=" + buffer.readByteString().hex() + Typography.ellipsis);
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b2, long j2, long j3) throws IOException {
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        if (j2 < 0 || j3 < j2) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", Long.valueOf(j2), Long.valueOf(j3)));
        }
        while (j2 < j3) {
            long jIndexOf = this.buffer.indexOf(b2, j2, j3);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            Buffer buffer = this.buffer;
            long j4 = buffer.f26470b;
            if (j4 >= j3 || this.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                break;
            }
            j2 = Math.max(j2, j4);
        }
        return -1L;
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray(long j2) throws IOException {
        require(j2);
        return this.buffer.readByteArray(j2);
    }

    @Override // okio.BufferedSource
    public ByteString readByteString(long j2) throws IOException {
        require(j2);
        return this.buffer.readByteString(j2);
    }

    @Override // okio.BufferedSource
    public String readUtf8(long j2) throws IOException {
        require(j2);
        return this.buffer.readUtf8(j2);
    }

    @Override // okio.BufferedSource
    public String readString(long j2, Charset charset) throws IOException {
        require(j2);
        if (charset != null) {
            return this.buffer.readString(j2, charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    @Override // okio.BufferedSource
    public void readFully(Buffer buffer, long j2) throws IOException {
        try {
            require(j2);
            this.buffer.readFully(buffer, j2);
        } catch (EOFException e2) {
            buffer.writeAll(this.buffer);
            throw e2;
        }
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // okio.BufferedSource
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        long j2 = i3;
        Util.checkOffsetAndCount(bArr.length, i2, j2);
        Buffer buffer = this.buffer;
        if (buffer.f26470b == 0 && this.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.buffer.read(bArr, i2, (int) Math.min(j2, this.buffer.f26470b));
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString) throws IOException {
        return indexOf(byteString, 0L);
    }

    @Override // okio.BufferedSource
    public long indexOf(ByteString byteString, long j2) throws IOException {
        if (this.f26491a) {
            throw new IllegalStateException("closed");
        }
        while (true) {
            long jIndexOf = this.buffer.indexOf(byteString, j2);
            if (jIndexOf != -1) {
                return jIndexOf;
            }
            Buffer buffer = this.buffer;
            long j3 = buffer.f26470b;
            if (this.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1L;
            }
            j2 = Math.max(j2, (j3 - byteString.size()) + 1);
        }
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        Buffer buffer = this.buffer;
        if (buffer.f26470b == 0 && this.source.read(buffer, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.buffer.read(byteBuffer);
    }
}
