package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/* loaded from: classes5.dex */
public class CharSequenceInputStream extends InputStream {
    private static final int BUFFER_SIZE = 2048;
    private static final int NO_MARK = -1;
    private final ByteBuffer bbuf;
    private final CharBuffer cbuf;
    private final CharsetEncoder encoder;
    private int mark_bbuf;
    private int mark_cbuf;

    public CharSequenceInputStream(CharSequence charSequence, Charset charset, int i2) {
        CharsetEncoder charsetEncoderNewEncoder = charset.newEncoder();
        CodingErrorAction codingErrorAction = CodingErrorAction.REPLACE;
        CharsetEncoder charsetEncoderOnUnmappableCharacter = charsetEncoderNewEncoder.onMalformedInput(codingErrorAction).onUnmappableCharacter(codingErrorAction);
        this.encoder = charsetEncoderOnUnmappableCharacter;
        float fMaxBytesPerChar = charsetEncoderOnUnmappableCharacter.maxBytesPerChar();
        if (i2 < fMaxBytesPerChar) {
            throw new IllegalArgumentException("Buffer size " + i2 + " is less than maxBytesPerChar " + fMaxBytesPerChar);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i2);
        this.bbuf = byteBufferAllocate;
        byteBufferAllocate.flip();
        this.cbuf = CharBuffer.wrap(charSequence);
        this.mark_cbuf = -1;
        this.mark_bbuf = -1;
    }

    private void fillBuffer() throws CharacterCodingException {
        this.bbuf.compact();
        CoderResult coderResultEncode = this.encoder.encode(this.cbuf, this.bbuf, true);
        if (coderResultEncode.isError()) {
            coderResultEncode.throwException();
        }
        this.bbuf.flip();
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.bbuf.remaining() + this.cbuf.remaining();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // java.io.InputStream
    public synchronized void mark(int i2) {
        this.mark_cbuf = this.cbuf.position();
        this.mark_bbuf = this.bbuf.position();
        this.cbuf.mark();
        this.bbuf.mark();
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (bArr == null) {
            throw new NullPointerException("Byte array is null");
        }
        if (i3 < 0 || i2 + i3 > bArr.length) {
            throw new IndexOutOfBoundsException("Array Size=" + bArr.length + ", offset=" + i2 + ", length=" + i3);
        }
        int i4 = 0;
        if (i3 == 0) {
            return 0;
        }
        if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
            return -1;
        }
        while (i3 > 0) {
            if (!this.bbuf.hasRemaining()) {
                fillBuffer();
                if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                    break;
                }
            } else {
                int iMin = Math.min(this.bbuf.remaining(), i3);
                this.bbuf.get(bArr, i2, iMin);
                i2 += iMin;
                i3 -= iMin;
                i4 += iMin;
            }
        }
        if (i4 != 0 || this.cbuf.hasRemaining()) {
            return i4;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public synchronized void reset() throws IOException {
        try {
            if (this.mark_cbuf != -1) {
                if (this.cbuf.position() != 0) {
                    this.encoder.reset();
                    this.cbuf.rewind();
                    this.bbuf.rewind();
                    this.bbuf.limit(0);
                    while (this.cbuf.position() < this.mark_cbuf) {
                        this.bbuf.rewind();
                        this.bbuf.limit(0);
                        fillBuffer();
                    }
                }
                if (this.cbuf.position() != this.mark_cbuf) {
                    throw new IllegalStateException("Unexpected CharBuffer postion: actual=" + this.cbuf.position() + " expected=" + this.mark_cbuf);
                }
                this.bbuf.position(this.mark_bbuf);
                this.mark_cbuf = -1;
                this.mark_bbuf = -1;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        long j3 = 0;
        while (j2 > 0 && available() > 0) {
            read();
            j2--;
            j3++;
        }
        return j3;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        while (!this.bbuf.hasRemaining()) {
            fillBuffer();
            if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                return -1;
            }
        }
        return this.bbuf.get() & 255;
    }

    public CharSequenceInputStream(CharSequence charSequence, String str, int i2) {
        this(charSequence, Charset.forName(str), i2);
    }

    public CharSequenceInputStream(CharSequence charSequence, Charset charset) {
        this(charSequence, charset, 2048);
    }

    public CharSequenceInputStream(CharSequence charSequence, String str) {
        this(charSequence, str, 2048);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }
}
