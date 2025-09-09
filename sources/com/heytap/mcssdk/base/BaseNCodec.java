package com.heytap.mcssdk.base;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

/* loaded from: classes3.dex */
public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int MIME_CHUNK_SIZE = 76;
    public static final int PEM_CHUNK_SIZE = 64;

    /* renamed from: a, reason: collision with root package name */
    protected final byte f15435a = 61;

    /* renamed from: b, reason: collision with root package name */
    protected final int f15436b;

    /* renamed from: c, reason: collision with root package name */
    protected byte[] f15437c;
    private final int chunkSeparatorLength;

    /* renamed from: d, reason: collision with root package name */
    protected int f15438d;

    /* renamed from: e, reason: collision with root package name */
    protected boolean f15439e;
    private final int encodedBlockSize;

    /* renamed from: f, reason: collision with root package name */
    protected int f15440f;

    /* renamed from: g, reason: collision with root package name */
    protected int f15441g;
    private int readPos;
    private final int unencodedBlockSize;

    protected BaseNCodec(int i2, int i3, int i4, int i5) {
        this.unencodedBlockSize = i2;
        this.encodedBlockSize = i3;
        this.f15436b = (i4 <= 0 || i5 <= 0) ? 0 : (i4 / i3) * i3;
        this.chunkSeparatorLength = i5;
    }

    protected static boolean h(byte b2) {
        return b2 == 9 || b2 == 10 || b2 == 13 || b2 == 32;
    }

    private void reset() {
        this.f15437c = null;
        this.f15438d = 0;
        this.readPos = 0;
        this.f15440f = 0;
        this.f15441g = 0;
        this.f15439e = false;
    }

    private void resizeBuffer() {
        byte[] bArr = this.f15437c;
        if (bArr == null) {
            this.f15437c = new byte[f()];
            this.f15438d = 0;
            this.readPos = 0;
        } else {
            byte[] bArr2 = new byte[bArr.length * 2];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.f15437c = bArr2;
        }
    }

    int a() {
        if (this.f15437c != null) {
            return this.f15438d - this.readPos;
        }
        return 0;
    }

    protected boolean b(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b2 : bArr) {
            if (61 == b2 || g(b2)) {
                return true;
            }
        }
        return false;
    }

    abstract void c(byte[] bArr, int i2, int i3);

    abstract void d(byte[] bArr, int i2, int i3);

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

    protected void e(int i2) {
        byte[] bArr = this.f15437c;
        if (bArr == null || bArr.length < this.f15438d + i2) {
            resizeBuffer();
        }
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    public String encodeAsString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    public String encodeToString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    protected int f() {
        return 8192;
    }

    protected abstract boolean g(byte b2);

    public long getEncodedLength(byte[] bArr) {
        int length = bArr.length;
        int i2 = this.unencodedBlockSize;
        long j2 = (((length + i2) - 1) / i2) * this.encodedBlockSize;
        int i3 = this.f15436b;
        return i3 > 0 ? j2 + ((((i3 + j2) - 1) / i3) * this.chunkSeparatorLength) : j2;
    }

    int i(byte[] bArr, int i2, int i3) {
        if (this.f15437c == null) {
            return this.f15439e ? -1 : 0;
        }
        int iMin = Math.min(a(), i3);
        System.arraycopy(this.f15437c, this.readPos, bArr, i2, iMin);
        int i4 = this.readPos + iMin;
        this.readPos = i4;
        if (i4 >= this.f15438d) {
            this.f15437c = null;
        }
        return iMin;
    }

    public boolean isInAlphabet(byte[] bArr, boolean z2) {
        byte b2;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (!g(bArr[i2]) && (!z2 || ((b2 = bArr[i2]) != 61 && !h(b2)))) {
                return false;
            }
        }
        return true;
    }

    @Override // org.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        reset();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        d(bArr, 0, bArr.length);
        d(bArr, 0, -1);
        int i2 = this.f15438d - this.readPos;
        byte[] bArr2 = new byte[i2];
        i(bArr2, 0, i2);
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
        c(bArr, 0, bArr.length);
        c(bArr, 0, -1);
        int i2 = this.f15438d;
        byte[] bArr2 = new byte[i2];
        i(bArr2, 0, i2);
        return bArr2;
    }
}
