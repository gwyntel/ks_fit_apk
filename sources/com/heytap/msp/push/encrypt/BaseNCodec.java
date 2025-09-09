package com.heytap.msp.push.encrypt;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

/* loaded from: classes3.dex */
public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    public static final int MIME_CHUNK_SIZE = 76;
    protected static final byte PAD_DEFAULT = 61;
    public static final int PEM_CHUNK_SIZE = 64;
    protected final byte PAD = 61;
    protected byte[] buffer;
    private final int chunkSeparatorLength;
    protected int currentLinePos;
    private final int encodedBlockSize;
    protected boolean eof;
    protected final int lineLength;
    protected int modulus;
    protected int pos;
    private int readPos;
    private final int unencodedBlockSize;

    protected BaseNCodec(int i2, int i3, int i4, int i5) {
        this.unencodedBlockSize = i2;
        this.encodedBlockSize = i3;
        this.lineLength = (i4 <= 0 || i5 <= 0) ? 0 : (i4 / i3) * i3;
        this.chunkSeparatorLength = i5;
    }

    protected static boolean isWhiteSpace(byte b2) {
        return b2 == 9 || b2 == 10 || b2 == 13 || b2 == 32;
    }

    private void reset() {
        this.buffer = null;
        this.pos = 0;
        this.readPos = 0;
        this.currentLinePos = 0;
        this.modulus = 0;
        this.eof = false;
    }

    private void resizeBuffer() {
        byte[] bArr = this.buffer;
        if (bArr == null) {
            this.buffer = new byte[getDefaultBufferSize()];
            this.pos = 0;
            this.readPos = 0;
        } else {
            byte[] bArr2 = new byte[bArr.length * 2];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.buffer = bArr2;
        }
    }

    int available() {
        if (this.buffer != null) {
            return this.pos - this.readPos;
        }
        return 0;
    }

    protected boolean containsAlphabetOrPad(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b2 : bArr) {
            if (61 == b2 || isInAlphabet(b2)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    abstract void decode(byte[] bArr, int i2, int i3);

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    abstract void encode(byte[] bArr, int i2, int i3);

    public String encodeAsString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    public String encodeToString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    protected void ensureBufferSize(int i2) {
        byte[] bArr = this.buffer;
        if (bArr == null || bArr.length < this.pos + i2) {
            resizeBuffer();
        }
    }

    protected int getDefaultBufferSize() {
        return 8192;
    }

    public long getEncodedLength(byte[] bArr) {
        int length = bArr.length;
        int i2 = this.unencodedBlockSize;
        long j2 = (((length + i2) - 1) / i2) * this.encodedBlockSize;
        int i3 = this.lineLength;
        return i3 > 0 ? j2 + ((((i3 + j2) - 1) / i3) * this.chunkSeparatorLength) : j2;
    }

    boolean hasData() {
        return this.buffer != null;
    }

    protected abstract boolean isInAlphabet(byte b2);

    public boolean isInAlphabet(byte[] bArr, boolean z2) {
        byte b2;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (!isInAlphabet(bArr[i2]) && (!z2 || ((b2 = bArr[i2]) != 61 && !isWhiteSpace(b2)))) {
                return false;
            }
        }
        return true;
    }

    int readResults(byte[] bArr, int i2, int i3) {
        if (this.buffer == null) {
            return this.eof ? -1 : 0;
        }
        int iMin = Math.min(available(), i3);
        System.arraycopy(this.buffer, this.readPos, bArr, i2, iMin);
        int i4 = this.readPos + iMin;
        this.readPos = i4;
        if (i4 >= this.pos) {
            this.buffer = null;
        }
        return iMin;
    }

    @Override // org.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        reset();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        encode(bArr, 0, bArr.length);
        encode(bArr, 0, -1);
        int i2 = this.pos - this.readPos;
        byte[] bArr2 = new byte[i2];
        readResults(bArr2, 0, i2);
        return bArr2;
    }

    public boolean isInAlphabet(String str) {
        return isInAlphabet(StringUtils.getBytesUtf8(str), true);
    }

    public byte[] decode(String str) {
        return decode(StringUtils.getBytesUtf8(str));
    }

    @Override // org.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) {
        reset();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        decode(bArr, 0, bArr.length);
        decode(bArr, 0, -1);
        int i2 = this.pos;
        byte[] bArr2 = new byte[i2];
        readResults(bArr2, 0, i2);
        return bArr2;
    }
}
