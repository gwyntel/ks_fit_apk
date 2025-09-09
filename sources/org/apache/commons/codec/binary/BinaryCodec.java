package org.apache.commons.codec.binary;

import com.alibaba.ailabs.iot.aisbase.Constants;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/* loaded from: classes5.dex */
public class BinaryCodec implements BinaryDecoder, BinaryEncoder {
    private static final int BIT_0 = 1;
    private static final int BIT_1 = 2;
    private static final int BIT_2 = 4;
    private static final int BIT_3 = 8;
    private static final int BIT_4 = 16;
    private static final int BIT_5 = 32;
    private static final int BIT_6 = 64;
    private static final int BIT_7 = 128;
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int[] BITS = {1, 2, 4, 8, 16, 32, 64, 128};

    public static byte[] fromAscii(char[] cArr) {
        if (cArr == null || cArr.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        int length = cArr.length >> 3;
        byte[] bArr = new byte[length];
        int length2 = cArr.length - 1;
        int i2 = 0;
        while (i2 < length) {
            int i3 = 0;
            while (true) {
                int[] iArr = BITS;
                if (i3 < iArr.length) {
                    if (cArr[length2 - i3] == '1') {
                        bArr[i2] = (byte) (iArr[i3] | bArr[i2]);
                    }
                    i3++;
                }
            }
            i2++;
            length2 -= 8;
        }
        return bArr;
    }

    private static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static byte[] toAsciiBytes(byte[] bArr) {
        if (isEmpty(bArr)) {
            return EMPTY_BYTE_ARRAY;
        }
        int length = bArr.length << 3;
        byte[] bArr2 = new byte[length];
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < bArr.length) {
            int i4 = 0;
            while (true) {
                int[] iArr = BITS;
                if (i4 < iArr.length) {
                    if ((iArr[i4] & bArr[i3]) == 0) {
                        bArr2[i2 - i4] = Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM;
                    } else {
                        bArr2[i2 - i4] = Constants.CMD_TYPE.CMD_STATUS_REPORT;
                    }
                    i4++;
                }
            }
            i3++;
            i2 -= 8;
        }
        return bArr2;
    }

    public static char[] toAsciiChars(byte[] bArr) {
        if (isEmpty(bArr)) {
            return EMPTY_CHAR_ARRAY;
        }
        int length = bArr.length << 3;
        char[] cArr = new char[length];
        int i2 = length - 1;
        int i3 = 0;
        while (i3 < bArr.length) {
            int i4 = 0;
            while (true) {
                int[] iArr = BITS;
                if (i4 < iArr.length) {
                    if ((iArr[i4] & bArr[i3]) == 0) {
                        cArr[i2 - i4] = '0';
                    } else {
                        cArr[i2 - i4] = '1';
                    }
                    i4++;
                }
            }
            i3++;
            i2 -= 8;
        }
        return cArr;
    }

    public static String toAsciiString(byte[] bArr) {
        return new String(toAsciiChars(bArr));
    }

    @Override // org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return EMPTY_BYTE_ARRAY;
        }
        if (obj instanceof byte[]) {
            return fromAscii((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return fromAscii((char[]) obj);
        }
        if (obj instanceof String) {
            return fromAscii(((String) obj).toCharArray());
        }
        throw new DecoderException("argument not a byte array");
    }

    @Override // org.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        return toAsciiBytes(bArr);
    }

    public byte[] toByteArray(String str) {
        return str == null ? EMPTY_BYTE_ARRAY : fromAscii(str.toCharArray());
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return toAsciiChars((byte[]) obj);
        }
        throw new EncoderException("argument not a byte array");
    }

    public static byte[] fromAscii(byte[] bArr) {
        if (isEmpty(bArr)) {
            return EMPTY_BYTE_ARRAY;
        }
        int length = bArr.length >> 3;
        byte[] bArr2 = new byte[length];
        int length2 = bArr.length - 1;
        int i2 = 0;
        while (i2 < length) {
            int i3 = 0;
            while (true) {
                int[] iArr = BITS;
                if (i3 < iArr.length) {
                    if (bArr[length2 - i3] == 49) {
                        bArr2[i2] = (byte) (iArr[i3] | bArr2[i2]);
                    }
                    i3++;
                }
            }
            i2++;
            length2 -= 8;
        }
        return bArr2;
    }

    @Override // org.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) {
        return fromAscii(bArr);
    }
}
