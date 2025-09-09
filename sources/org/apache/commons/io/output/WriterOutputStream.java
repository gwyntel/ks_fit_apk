package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/* loaded from: classes5.dex */
public class WriterOutputStream extends OutputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final CharsetDecoder decoder;
    private final ByteBuffer decoderIn;
    private final CharBuffer decoderOut;
    private final boolean writeImmediately;
    private final Writer writer;

    public WriterOutputStream(Writer writer, CharsetDecoder charsetDecoder) {
        this(writer, charsetDecoder, 1024, false);
    }

    private static void checkIbmJdkWithBrokenUTF16(Charset charset) {
        if ("UTF-16".equals(charset.name())) {
            byte[] bytes = "vés".getBytes(charset);
            CharsetDecoder charsetDecoderNewDecoder = charset.newDecoder();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(16);
            CharBuffer charBufferAllocate = CharBuffer.allocate(3);
            int length = bytes.length;
            int i2 = 0;
            while (i2 < length) {
                byteBufferAllocate.put(bytes[i2]);
                byteBufferAllocate.flip();
                try {
                    charsetDecoderNewDecoder.decode(byteBufferAllocate, charBufferAllocate, i2 == length + (-1));
                    byteBufferAllocate.compact();
                    i2++;
                } catch (IllegalArgumentException unused) {
                    throw new UnsupportedOperationException("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
                }
            }
            charBufferAllocate.rewind();
            if (!"vés".equals(charBufferAllocate.toString())) {
                throw new UnsupportedOperationException("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
            }
        }
    }

    private void flushOutput() throws IOException {
        if (this.decoderOut.position() > 0) {
            this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
            this.decoderOut.rewind();
        }
    }

    private void processInput(boolean z2) throws IOException {
        CoderResult coderResultDecode;
        this.decoderIn.flip();
        while (true) {
            coderResultDecode = this.decoder.decode(this.decoderIn, this.decoderOut, z2);
            if (!coderResultDecode.isOverflow()) {
                break;
            } else {
                flushOutput();
            }
        }
        if (!coderResultDecode.isUnderflow()) {
            throw new IOException("Unexpected coder result");
        }
        this.decoderIn.compact();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        processInput(true);
        flushOutput();
        this.writer.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        flushOutput();
        this.writer.flush();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        while (i3 > 0) {
            int iMin = Math.min(i3, this.decoderIn.remaining());
            this.decoderIn.put(bArr, i2, iMin);
            processInput(false);
            i3 -= iMin;
            i2 += iMin;
        }
        if (this.writeImmediately) {
            flushOutput();
        }
    }

    public WriterOutputStream(Writer writer, CharsetDecoder charsetDecoder, int i2, boolean z2) {
        this.decoderIn = ByteBuffer.allocate(128);
        checkIbmJdkWithBrokenUTF16(charsetDecoder.charset());
        this.writer = writer;
        this.decoder = charsetDecoder;
        this.writeImmediately = z2;
        this.decoderOut = CharBuffer.allocate(i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        write(new byte[]{(byte) i2}, 0, 1);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public WriterOutputStream(Writer writer, Charset charset, int i2, boolean z2) {
        CharsetDecoder charsetDecoderNewDecoder = charset.newDecoder();
        CodingErrorAction codingErrorAction = CodingErrorAction.REPLACE;
        this(writer, charsetDecoderNewDecoder.onMalformedInput(codingErrorAction).onUnmappableCharacter(codingErrorAction).replaceWith("?"), i2, z2);
    }

    public WriterOutputStream(Writer writer, Charset charset) {
        this(writer, charset, 1024, false);
    }

    public WriterOutputStream(Writer writer, String str, int i2, boolean z2) {
        this(writer, Charset.forName(str), i2, z2);
    }

    public WriterOutputStream(Writer writer, String str) {
        this(writer, str, 1024, false);
    }

    @Deprecated
    public WriterOutputStream(Writer writer) {
        this(writer, Charset.defaultCharset(), 1024, false);
    }
}
