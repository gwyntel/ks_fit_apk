package com.heytap.mcssdk.base;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;
import java.math.BigInteger;
import org.apache.commons.codec.binary.StringUtils;

/* loaded from: classes3.dex */
public class Base64 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    private static final int MASK_6BITS = 63;
    private int bitWorkArea;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    /* renamed from: h, reason: collision with root package name */
    static final byte[] f15434h = {13, 10};
    private static final byte[] STANDARD_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 43, Constants.CMD_TYPE.CMD_OTA};
    private static final byte[] URL_SAFE_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 45, 95};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 58, 59, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, 63, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 39, 40, 41, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, 43, 44, 45, 46, Constants.CMD_TYPE.CMD_OTA, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES};

    public Base64() {
        this(0);
    }

    public static byte[] decodeBase64(String str) {
        return new Base64().decode(str);
    }

    public static BigInteger decodeInteger(byte[] bArr) {
        return new BigInteger(1, decodeBase64(bArr));
    }

    public static byte[] encodeBase64(byte[] bArr) {
        return encodeBase64(bArr, false);
    }

    public static byte[] encodeBase64Chunked(byte[] bArr) {
        return encodeBase64(bArr, true);
    }

    public static String encodeBase64String(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] bArr) {
        return encodeBase64(bArr, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false, true));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger != null) {
            return encodeBase64(j(bigInteger), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    public static boolean isArrayByteBase64(byte[] bArr) {
        return isBase64(bArr);
    }

    public static boolean isBase64(byte b2) {
        if (b2 != 61) {
            if (b2 >= 0) {
                byte[] bArr = DECODE_TABLE;
                if (b2 >= bArr.length || bArr[b2] == -1) {
                }
            }
            return false;
        }
        return true;
    }

    static byte[] j(BigInteger bigInteger) {
        int iBitLength = ((bigInteger.bitLength() + 7) >> 3) << 3;
        byte[] byteArray = bigInteger.toByteArray();
        int i2 = 1;
        if (bigInteger.bitLength() % 8 != 0 && (bigInteger.bitLength() / 8) + 1 == iBitLength / 8) {
            return byteArray;
        }
        int length = byteArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            length--;
        } else {
            i2 = 0;
        }
        int i3 = iBitLength / 8;
        int i4 = i3 - length;
        byte[] bArr = new byte[i3];
        System.arraycopy(byteArray, i2, bArr, i4, length);
        return bArr;
    }

    @Override // com.heytap.mcssdk.base.BaseNCodec
    void c(byte[] bArr, int i2, int i3) {
        byte b2;
        if (this.f15439e) {
            return;
        }
        if (i3 < 0) {
            this.f15439e = true;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= i3) {
                break;
            }
            e(this.decodeSize);
            int i5 = i2 + 1;
            byte b3 = bArr[i2];
            if (b3 == 61) {
                this.f15439e = true;
                break;
            }
            if (b3 >= 0) {
                byte[] bArr2 = DECODE_TABLE;
                if (b3 < bArr2.length && (b2 = bArr2[b3]) >= 0) {
                    int i6 = (this.f15441g + 1) % 4;
                    this.f15441g = i6;
                    int i7 = (this.bitWorkArea << 6) + b2;
                    this.bitWorkArea = i7;
                    if (i6 == 0) {
                        byte[] bArr3 = this.f15437c;
                        int i8 = this.f15438d;
                        int i9 = i8 + 1;
                        this.f15438d = i9;
                        bArr3[i8] = (byte) ((i7 >> 16) & 255);
                        int i10 = i8 + 2;
                        this.f15438d = i10;
                        bArr3[i9] = (byte) ((i7 >> 8) & 255);
                        this.f15438d = i8 + 3;
                        bArr3[i10] = (byte) (i7 & 255);
                    }
                }
            }
            i4++;
            i2 = i5;
        }
        if (!this.f15439e || this.f15441g == 0) {
            return;
        }
        e(this.decodeSize);
        int i11 = this.f15441g;
        if (i11 == 2) {
            int i12 = this.bitWorkArea >> 4;
            this.bitWorkArea = i12;
            byte[] bArr4 = this.f15437c;
            int i13 = this.f15438d;
            this.f15438d = i13 + 1;
            bArr4[i13] = (byte) (i12 & 255);
            return;
        }
        if (i11 != 3) {
            return;
        }
        int i14 = this.bitWorkArea;
        int i15 = i14 >> 2;
        this.bitWorkArea = i15;
        byte[] bArr5 = this.f15437c;
        int i16 = this.f15438d;
        int i17 = i16 + 1;
        this.f15438d = i17;
        bArr5[i16] = (byte) ((i14 >> 10) & 255);
        this.f15438d = i16 + 2;
        bArr5[i17] = (byte) (i15 & 255);
    }

    @Override // com.heytap.mcssdk.base.BaseNCodec
    void d(byte[] bArr, int i2, int i3) {
        if (this.f15439e) {
            return;
        }
        if (i3 >= 0) {
            int i4 = 0;
            while (i4 < i3) {
                e(this.encodeSize);
                int i5 = (this.f15441g + 1) % 3;
                this.f15441g = i5;
                int i6 = i2 + 1;
                int i7 = bArr[i2];
                if (i7 < 0) {
                    i7 += 256;
                }
                int i8 = (this.bitWorkArea << 8) + i7;
                this.bitWorkArea = i8;
                if (i5 == 0) {
                    byte[] bArr2 = this.f15437c;
                    int i9 = this.f15438d;
                    int i10 = i9 + 1;
                    this.f15438d = i10;
                    byte[] bArr3 = this.encodeTable;
                    bArr2[i9] = bArr3[(i8 >> 18) & 63];
                    int i11 = i9 + 2;
                    this.f15438d = i11;
                    bArr2[i10] = bArr3[(i8 >> 12) & 63];
                    int i12 = i9 + 3;
                    this.f15438d = i12;
                    bArr2[i11] = bArr3[(i8 >> 6) & 63];
                    int i13 = i9 + 4;
                    this.f15438d = i13;
                    bArr2[i12] = bArr3[i8 & 63];
                    int i14 = this.f15440f + 4;
                    this.f15440f = i14;
                    int i15 = this.f15436b;
                    if (i15 > 0 && i15 <= i14) {
                        byte[] bArr4 = this.lineSeparator;
                        System.arraycopy(bArr4, 0, bArr2, i13, bArr4.length);
                        this.f15438d += this.lineSeparator.length;
                        this.f15440f = 0;
                    }
                }
                i4++;
                i2 = i6;
            }
            return;
        }
        this.f15439e = true;
        if (this.f15441g == 0 && this.f15436b == 0) {
            return;
        }
        e(this.encodeSize);
        int i16 = this.f15438d;
        int i17 = this.f15441g;
        if (i17 == 1) {
            byte[] bArr5 = this.f15437c;
            int i18 = i16 + 1;
            this.f15438d = i18;
            byte[] bArr6 = this.encodeTable;
            int i19 = this.bitWorkArea;
            bArr5[i16] = bArr6[(i19 >> 2) & 63];
            int i20 = i16 + 2;
            this.f15438d = i20;
            bArr5[i18] = bArr6[(i19 << 4) & 63];
            if (bArr6 == STANDARD_ENCODE_TABLE) {
                int i21 = i16 + 3;
                this.f15438d = i21;
                bArr5[i20] = 61;
                this.f15438d = i16 + 4;
                bArr5[i21] = 61;
            }
        } else if (i17 == 2) {
            byte[] bArr7 = this.f15437c;
            int i22 = i16 + 1;
            this.f15438d = i22;
            byte[] bArr8 = this.encodeTable;
            int i23 = this.bitWorkArea;
            bArr7[i16] = bArr8[(i23 >> 10) & 63];
            int i24 = i16 + 2;
            this.f15438d = i24;
            bArr7[i22] = bArr8[(i23 >> 4) & 63];
            int i25 = i16 + 3;
            this.f15438d = i25;
            bArr7[i24] = bArr8[(i23 << 2) & 63];
            if (bArr8 == STANDARD_ENCODE_TABLE) {
                this.f15438d = i16 + 4;
                bArr7[i25] = 61;
            }
        }
        int i26 = this.f15440f;
        int i27 = this.f15438d;
        int i28 = i26 + (i27 - i16);
        this.f15440f = i28;
        if (this.f15436b <= 0 || i28 <= 0) {
            return;
        }
        byte[] bArr9 = this.lineSeparator;
        System.arraycopy(bArr9, 0, this.f15437c, i27, bArr9.length);
        this.f15438d += this.lineSeparator.length;
    }

    @Override // com.heytap.mcssdk.base.BaseNCodec
    protected boolean g(byte b2) {
        if (b2 >= 0) {
            byte[] bArr = this.decodeTable;
            if (b2 < bArr.length && bArr[b2] != -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isUrlSafe() {
        return this.encodeTable == URL_SAFE_ENCODE_TABLE;
    }

    public Base64(boolean z2) {
        this(76, f15434h, z2);
    }

    public static byte[] decodeBase64(byte[] bArr) {
        return new Base64().decode(bArr);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z2) {
        return encodeBase64(bArr, z2, false);
    }

    public static boolean isBase64(String str) {
        return isBase64(StringUtils.getBytesUtf8(str));
    }

    public Base64(int i2) {
        this(i2, f15434h);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z2, boolean z3) {
        return encodeBase64(bArr, z2, z3, Integer.MAX_VALUE);
    }

    public static boolean isBase64(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (!isBase64(bArr[i2]) && !BaseNCodec.h(bArr[i2])) {
                return false;
            }
        }
        return true;
    }

    public Base64(int i2, byte[] bArr) {
        this(i2, bArr, false);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z2, boolean z3, int i2) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        Base64 base64 = z2 ? new Base64(z3) : new Base64(0, f15434h, z3);
        long encodedLength = base64.getEncodedLength(bArr);
        if (encodedLength <= i2) {
            return base64.encode(bArr);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + encodedLength + ") than the specified maximum size of " + i2);
    }

    public Base64(int i2, byte[] bArr, boolean z2) {
        super(3, 4, i2, bArr == null ? 0 : bArr.length);
        this.decodeTable = DECODE_TABLE;
        if (bArr != null) {
            if (b(bArr)) {
                throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + StringUtils.newStringUtf8(bArr) + "]");
            }
            if (i2 > 0) {
                this.encodeSize = bArr.length + 4;
                byte[] bArr2 = new byte[bArr.length];
                this.lineSeparator = bArr2;
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            } else {
                this.encodeSize = 4;
                this.lineSeparator = null;
            }
        } else {
            this.encodeSize = 4;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = z2 ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    }
}
