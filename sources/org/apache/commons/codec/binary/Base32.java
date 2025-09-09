package org.apache.commons.codec.binary;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;

/* loaded from: classes5.dex */
public class Base32 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 5;
    private static final int BYTES_PER_ENCODED_BLOCK = 8;
    private static final int BYTES_PER_UNENCODED_BLOCK = 5;
    private static final byte[] CHUNK_SEPARATOR = {13, 10};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 63, -1, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM};
    private static final byte[] ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK};
    private static final byte[] HEX_DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 63, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32};
    private static final byte[] HEX_ENCODE_TABLE = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86};
    private static final int MASK_5BITS = 31;
    private long bitWorkArea;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    public Base32() {
        this(false);
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    void c(byte[] bArr, int i2, int i3) {
        byte b2;
        if (this.f26565e) {
        }
        if (i3 < 0) {
            this.f26565e = true;
        }
        int i4 = 0;
        int i5 = i2;
        while (true) {
            if (i4 >= i3) {
                break;
            }
            int i6 = i5 + 1;
            byte b3 = bArr[i5];
            if (b3 == 61) {
                this.f26565e = true;
                break;
            }
            e(this.decodeSize);
            if (b3 >= 0) {
                byte[] bArr2 = this.decodeTable;
                if (b3 < bArr2.length && (b2 = bArr2[b3]) >= 0) {
                    int i7 = (this.f26567g + 1) % 8;
                    this.f26567g = i7;
                    this.bitWorkArea = (this.bitWorkArea << 5) + b2;
                    if (i7 == 0) {
                        byte[] bArr3 = this.f26563c;
                        int i8 = this.f26564d;
                        int i9 = i8 + 1;
                        this.f26564d = i9;
                        bArr3[i8] = (byte) ((r10 >> 32) & 255);
                        int i10 = i8 + 2;
                        this.f26564d = i10;
                        bArr3[i9] = (byte) ((r10 >> 24) & 255);
                        int i11 = i8 + 3;
                        this.f26564d = i11;
                        bArr3[i10] = (byte) ((r10 >> 16) & 255);
                        int i12 = i8 + 4;
                        this.f26564d = i12;
                        bArr3[i11] = (byte) ((r10 >> 8) & 255);
                        this.f26564d = i8 + 5;
                        bArr3[i12] = (byte) (r10 & 255);
                    }
                }
            }
            i4++;
            i5 = i6;
        }
        if (!this.f26565e || this.f26567g < 2) {
            return;
        }
        e(this.decodeSize);
        switch (this.f26567g) {
            case 2:
                byte[] bArr4 = this.f26563c;
                int i13 = this.f26564d;
                this.f26564d = i13 + 1;
                bArr4[i13] = (byte) ((this.bitWorkArea >> 2) & 255);
                break;
            case 3:
                byte[] bArr5 = this.f26563c;
                int i14 = this.f26564d;
                this.f26564d = i14 + 1;
                bArr5[i14] = (byte) ((this.bitWorkArea >> 7) & 255);
                break;
            case 4:
                this.bitWorkArea = this.bitWorkArea >> 4;
                byte[] bArr6 = this.f26563c;
                int i15 = this.f26564d;
                int i16 = i15 + 1;
                this.f26564d = i16;
                bArr6[i15] = (byte) ((r1 >> 12) & 255);
                this.f26564d = i15 + 2;
                bArr6[i16] = (byte) (r7 & 255);
                break;
            case 5:
                this.bitWorkArea = this.bitWorkArea >> 1;
                byte[] bArr7 = this.f26563c;
                int i17 = this.f26564d;
                int i18 = i17 + 1;
                this.f26564d = i18;
                bArr7[i17] = (byte) ((r3 >> 17) & 255);
                int i19 = i17 + 2;
                this.f26564d = i19;
                bArr7[i18] = (byte) ((r3 >> 9) & 255);
                this.f26564d = i17 + 3;
                bArr7[i19] = (byte) (r1 & 255);
                break;
            case 6:
                this.bitWorkArea = this.bitWorkArea >> 6;
                byte[] bArr8 = this.f26563c;
                int i20 = this.f26564d;
                int i21 = i20 + 1;
                this.f26564d = i21;
                bArr8[i20] = (byte) ((r1 >> 22) & 255);
                int i22 = i20 + 2;
                this.f26564d = i22;
                bArr8[i21] = (byte) ((r1 >> 14) & 255);
                this.f26564d = i20 + 3;
                bArr8[i22] = (byte) (r3 & 255);
                break;
            case 7:
                this.bitWorkArea = this.bitWorkArea >> 3;
                byte[] bArr9 = this.f26563c;
                int i23 = this.f26564d;
                int i24 = i23 + 1;
                this.f26564d = i24;
                bArr9[i23] = (byte) ((r1 >> 27) & 255);
                int i25 = i23 + 2;
                this.f26564d = i25;
                bArr9[i24] = (byte) ((r1 >> 19) & 255);
                int i26 = i23 + 3;
                this.f26564d = i26;
                bArr9[i25] = (byte) ((r1 >> 11) & 255);
                this.f26564d = i23 + 4;
                bArr9[i26] = (byte) (r7 & 255);
                break;
        }
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    void d(byte[] bArr, int i2, int i3) {
        boolean z2;
        if (this.f26565e) {
            return;
        }
        boolean z3 = false;
        int i4 = 1;
        if (i3 >= 0) {
            int i5 = i2;
            int i6 = 0;
            while (i6 < i3) {
                e(this.encodeSize);
                int i7 = (this.f26567g + i4) % 5;
                this.f26567g = i7;
                int i8 = i5 + 1;
                int i9 = bArr[i5];
                if (i9 < 0) {
                    i9 += 256;
                }
                long j2 = (this.bitWorkArea << 8) + i9;
                this.bitWorkArea = j2;
                if (i7 == 0) {
                    byte[] bArr2 = this.f26563c;
                    int i10 = this.f26564d;
                    int i11 = i10 + 1;
                    this.f26564d = i11;
                    byte[] bArr3 = this.encodeTable;
                    bArr2[i10] = bArr3[((int) (j2 >> 35)) & 31];
                    int i12 = i10 + 2;
                    this.f26564d = i12;
                    bArr2[i11] = bArr3[((int) (j2 >> 30)) & 31];
                    int i13 = i10 + 3;
                    this.f26564d = i13;
                    bArr2[i12] = bArr3[((int) (j2 >> 25)) & 31];
                    int i14 = i10 + 4;
                    this.f26564d = i14;
                    bArr2[i13] = bArr3[((int) (j2 >> 20)) & 31];
                    int i15 = i10 + 5;
                    this.f26564d = i15;
                    bArr2[i14] = bArr3[((int) (j2 >> 15)) & 31];
                    int i16 = i10 + 6;
                    this.f26564d = i16;
                    bArr2[i15] = bArr3[((int) (j2 >> 10)) & 31];
                    int i17 = i10 + 7;
                    this.f26564d = i17;
                    bArr2[i16] = bArr3[((int) (j2 >> 5)) & 31];
                    int i18 = i10 + 8;
                    this.f26564d = i18;
                    bArr2[i17] = bArr3[((int) j2) & 31];
                    int i19 = this.f26566f + 8;
                    this.f26566f = i19;
                    int i20 = this.f26562b;
                    if (i20 <= 0 || i20 > i19) {
                        z2 = false;
                    } else {
                        byte[] bArr4 = this.lineSeparator;
                        z2 = false;
                        System.arraycopy(bArr4, 0, bArr2, i18, bArr4.length);
                        this.f26564d += this.lineSeparator.length;
                        this.f26566f = 0;
                    }
                } else {
                    z2 = z3;
                }
                i6++;
                z3 = z2;
                i5 = i8;
                i4 = 1;
            }
            return;
        }
        this.f26565e = true;
        if (this.f26567g == 0 && this.f26562b == 0) {
            return;
        }
        e(this.encodeSize);
        int i21 = this.f26564d;
        int i22 = this.f26567g;
        if (i22 == 1) {
            byte[] bArr5 = this.f26563c;
            int i23 = i21 + 1;
            this.f26564d = i23;
            byte[] bArr6 = this.encodeTable;
            long j3 = this.bitWorkArea;
            bArr5[i21] = bArr6[((int) (j3 >> 3)) & 31];
            int i24 = i21 + 2;
            this.f26564d = i24;
            bArr5[i23] = bArr6[((int) (j3 << 2)) & 31];
            int i25 = i21 + 3;
            this.f26564d = i25;
            bArr5[i24] = 61;
            int i26 = i21 + 4;
            this.f26564d = i26;
            bArr5[i25] = 61;
            int i27 = i21 + 5;
            this.f26564d = i27;
            bArr5[i26] = 61;
            int i28 = i21 + 6;
            this.f26564d = i28;
            bArr5[i27] = 61;
            int i29 = i21 + 7;
            this.f26564d = i29;
            bArr5[i28] = 61;
            this.f26564d = i21 + 8;
            bArr5[i29] = 61;
        } else if (i22 == 2) {
            byte[] bArr7 = this.f26563c;
            int i30 = i21 + 1;
            this.f26564d = i30;
            byte[] bArr8 = this.encodeTable;
            long j4 = this.bitWorkArea;
            bArr7[i21] = bArr8[((int) (j4 >> 11)) & 31];
            int i31 = i21 + 2;
            this.f26564d = i31;
            bArr7[i30] = bArr8[((int) (j4 >> 6)) & 31];
            int i32 = i21 + 3;
            this.f26564d = i32;
            bArr7[i31] = bArr8[((int) (j4 >> 1)) & 31];
            int i33 = i21 + 4;
            this.f26564d = i33;
            bArr7[i32] = bArr8[((int) (j4 << 4)) & 31];
            int i34 = i21 + 5;
            this.f26564d = i34;
            bArr7[i33] = 61;
            int i35 = i21 + 6;
            this.f26564d = i35;
            bArr7[i34] = 61;
            int i36 = i21 + 7;
            this.f26564d = i36;
            bArr7[i35] = 61;
            this.f26564d = i21 + 8;
            bArr7[i36] = 61;
        } else if (i22 == 3) {
            byte[] bArr9 = this.f26563c;
            int i37 = i21 + 1;
            this.f26564d = i37;
            byte[] bArr10 = this.encodeTable;
            long j5 = this.bitWorkArea;
            bArr9[i21] = bArr10[((int) (j5 >> 19)) & 31];
            int i38 = i21 + 2;
            this.f26564d = i38;
            bArr9[i37] = bArr10[((int) (j5 >> 14)) & 31];
            int i39 = i21 + 3;
            this.f26564d = i39;
            bArr9[i38] = bArr10[((int) (j5 >> 9)) & 31];
            int i40 = i21 + 4;
            this.f26564d = i40;
            bArr9[i39] = bArr10[((int) (j5 >> 4)) & 31];
            int i41 = i21 + 5;
            this.f26564d = i41;
            bArr9[i40] = bArr10[((int) (j5 << 1)) & 31];
            int i42 = i21 + 6;
            this.f26564d = i42;
            bArr9[i41] = 61;
            int i43 = i21 + 7;
            this.f26564d = i43;
            bArr9[i42] = 61;
            this.f26564d = i21 + 8;
            bArr9[i43] = 61;
        } else if (i22 == 4) {
            byte[] bArr11 = this.f26563c;
            int i44 = i21 + 1;
            this.f26564d = i44;
            byte[] bArr12 = this.encodeTable;
            long j6 = this.bitWorkArea;
            bArr11[i21] = bArr12[((int) (j6 >> 27)) & 31];
            int i45 = i21 + 2;
            this.f26564d = i45;
            bArr11[i44] = bArr12[((int) (j6 >> 22)) & 31];
            int i46 = i21 + 3;
            this.f26564d = i46;
            bArr11[i45] = bArr12[((int) (j6 >> 17)) & 31];
            int i47 = i21 + 4;
            this.f26564d = i47;
            bArr11[i46] = bArr12[((int) (j6 >> 12)) & 31];
            int i48 = i21 + 5;
            this.f26564d = i48;
            bArr11[i47] = bArr12[((int) (j6 >> 7)) & 31];
            int i49 = i21 + 6;
            this.f26564d = i49;
            bArr11[i48] = bArr12[((int) (j6 >> 2)) & 31];
            int i50 = i21 + 7;
            this.f26564d = i50;
            bArr11[i49] = bArr12[((int) (j6 << 3)) & 31];
            this.f26564d = i21 + 8;
            bArr11[i50] = 61;
        }
        int i51 = this.f26566f;
        int i52 = this.f26564d;
        int i53 = i51 + (i52 - i21);
        this.f26566f = i53;
        if (this.f26562b <= 0 || i53 <= 0) {
            return;
        }
        byte[] bArr13 = this.lineSeparator;
        System.arraycopy(bArr13, 0, this.f26563c, i52, bArr13.length);
        this.f26564d += this.lineSeparator.length;
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    public boolean isInAlphabet(byte b2) {
        if (b2 >= 0) {
            byte[] bArr = this.decodeTable;
            if (b2 < bArr.length && bArr[b2] != -1) {
                return true;
            }
        }
        return false;
    }

    public Base32(boolean z2) {
        this(0, null, z2);
    }

    public Base32(int i2) {
        this(i2, CHUNK_SEPARATOR);
    }

    public Base32(int i2, byte[] bArr) {
        this(i2, bArr, false);
    }

    public Base32(int i2, byte[] bArr, boolean z2) {
        super(5, 8, i2, bArr == null ? 0 : bArr.length);
        if (z2) {
            this.encodeTable = HEX_ENCODE_TABLE;
            this.decodeTable = HEX_DECODE_TABLE;
        } else {
            this.encodeTable = ENCODE_TABLE;
            this.decodeTable = DECODE_TABLE;
        }
        if (i2 <= 0) {
            this.encodeSize = 8;
            this.lineSeparator = null;
        } else if (bArr != null) {
            if (!b(bArr)) {
                this.encodeSize = bArr.length + 8;
                byte[] bArr2 = new byte[bArr.length];
                this.lineSeparator = bArr2;
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            } else {
                throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + StringUtils.newStringUtf8(bArr) + "]");
            }
        } else {
            throw new IllegalArgumentException("lineLength " + i2 + " > 0, but lineSeparator is null");
        }
        this.decodeSize = this.encodeSize - 1;
    }
}
