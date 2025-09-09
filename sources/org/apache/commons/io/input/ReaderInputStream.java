package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/* loaded from: classes5.dex */
public class ReaderInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final CharsetEncoder encoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private boolean endOfInput;
    private CoderResult lastCoderResult;
    private final Reader reader;

    public ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder) {
        this(reader, charsetEncoder, 1024);
    }

    private void fillBuffer() throws IOException {
        CoderResult coderResult;
        if (!this.endOfInput && ((coderResult = this.lastCoderResult) == null || coderResult.isUnderflow())) {
            this.encoderIn.compact();
            int iPosition = this.encoderIn.position();
            int i2 = this.reader.read(this.encoderIn.array(), iPosition, this.encoderIn.remaining());
            if (i2 == -1) {
                this.endOfInput = true;
            } else {
                this.encoderIn.position(iPosition + i2);
            }
            this.encoderIn.flip();
        }
        this.encoderOut.compact();
        this.lastCoderResult = this.encoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
        this.encoderOut.flip();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.reader.close();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        if (bArr == null) {
            throw new NullPointerException("Byte array must not be null");
        }
        if (i3 < 0 || i2 < 0 || i2 + i3 > bArr.length) {
            throw new IndexOutOfBoundsException("Array Size=" + bArr.length + ", offset=" + i2 + ", length=" + i3);
        }
        int i4 = 0;
        if (i3 == 0) {
            return 0;
        }
        while (i3 > 0) {
            if (!this.encoderOut.hasRemaining()) {
                fillBuffer();
                if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                    break;
                }
            } else {
                int iMin = Math.min(this.encoderOut.remaining(), i3);
                this.encoderOut.get(bArr, i2, iMin);
                i2 += iMin;
                i3 -= iMin;
                i4 += iMin;
            }
        }
        if (i4 == 0 && this.endOfInput) {
            return -1;
        }
        return i4;
    }

    public ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder, int i2) {
        this.reader = reader;
        this.encoder = charsetEncoder;
        CharBuffer charBufferAllocate = CharBuffer.allocate(i2);
        this.encoderIn = charBufferAllocate;
        charBufferAllocate.flip();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(128);
        this.encoderOut = byteBufferAllocate;
        byteBufferAllocate.flip();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ReaderInputStream(Reader reader, Charset charset, int i2) {
        CharsetEncoder charsetEncoderNewEncoder = charset.newEncoder();
        CodingErrorAction codingErrorAction = CodingErrorAction.REPLACE;
        this(reader, charsetEncoderNewEncoder.onMalformedInput(codingErrorAction).onUnmappableCharacter(codingErrorAction), i2);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        while (!this.encoderOut.hasRemaining()) {
            fillBuffer();
            if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                return -1;
            }
        }
        return this.encoderOut.get() & 255;
    }

    public ReaderInputStream(Reader reader, Charset charset) {
        this(reader, charset, 1024);
    }

    public ReaderInputStream(Reader reader, String str, int i2) {
        this(reader, Charset.forName(str), i2);
    }

    public ReaderInputStream(Reader reader, String str) {
        this(reader, str, 1024);
    }

    @Deprecated
    public ReaderInputStream(Reader reader) {
        this(reader, Charset.defaultCharset());
    }
}
