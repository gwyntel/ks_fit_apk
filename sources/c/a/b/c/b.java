package c.a.b.c;

import c.a.b.d;
import c.a.b.e;
import c.a.b.h.k;
import c.a.d.h;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.business.devicecenter.config.ble.BreezeConstants;
import com.google.common.base.Ascii;
import java.lang.reflect.Array;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;

/* loaded from: classes2.dex */
public class b implements d {

    /* renamed from: a, reason: collision with root package name */
    public static final byte[] f8038a = {99, 124, 119, 123, -14, 107, 111, -59, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 1, 103, 43, -2, -41, -85, 118, -54, -126, -55, 125, -6, 89, 71, -16, -83, -44, -94, -81, -100, -92, 114, -64, -73, -3, -109, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, 63, -9, -52, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, -91, -27, BreezeConstants.BREEZE_PROVISION_VERSION, 113, -40, Constants.CMD_TYPE.CMD_STATUS_REPORT, Ascii.NAK, 4, -57, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, -61, Ascii.CAN, -106, 5, -102, 7, 18, Byte.MIN_VALUE, -30, -21, 39, -78, 117, 9, -125, 44, Ascii.SUB, Ascii.ESC, 110, 90, -96, 82, 59, -42, -77, 41, -29, Constants.CMD_TYPE.CMD_OTA, -124, 83, -47, 0, -19, 32, -4, -79, 91, 106, -53, -66, 57, 74, 76, 88, -49, -48, -17, -86, -5, 67, 77, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, -123, 69, -7, 2, Byte.MAX_VALUE, 80, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, -97, -88, 81, -93, 64, -113, -110, -99, 56, -11, -68, -74, -38, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 16, -1, -13, -46, -51, 12, 19, -20, 95, -105, 68, Ascii.ETB, -60, -89, 126, 61, 100, 93, Ascii.EM, 115, 96, -127, 79, -36, Constants.CMD_TYPE.CMD_REQUEST_OTA, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, -112, -120, 70, -18, -72, Ascii.DC4, -34, 94, 11, -37, -32, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, 58, 10, 73, 6, Constants.CMD_TYPE.CMD_OTA_RES, 92, -62, -45, -84, 98, -111, -107, -28, 121, -25, -56, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 109, -115, -43, 78, -87, 108, 86, -12, -22, 101, 122, -82, 8, -70, 120, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, 46, Ascii.FS, -90, -76, -58, -24, -35, 116, Ascii.US, 75, -67, -117, -118, 112, 62, -75, 102, 72, 3, -10, 14, 97, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 87, -71, -122, -63, Ascii.GS, -98, -31, -8, -104, 17, 105, -39, -114, -108, -101, Ascii.RS, -121, -23, -50, 85, 40, -33, -116, -95, -119, 13, -65, -26, 66, 104, 65, -103, 45, 15, -80, 84, -69, Ascii.SYN};

    /* renamed from: b, reason: collision with root package name */
    public static final byte[] f8039b = {82, 9, 106, -43, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, -91, 56, -65, 64, -93, -98, -127, -13, -41, -5, 124, -29, 57, -126, -101, Constants.CMD_TYPE.CMD_OTA, -1, -121, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, -114, 67, 68, -60, -34, -23, -53, 84, 123, -108, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, -90, -62, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, 61, -18, 76, -107, 11, 66, -6, -61, 78, 8, 46, -95, 102, 40, -39, Constants.CMD_TYPE.CMD_OTA_RES, -78, 118, 91, -94, 73, 109, -117, -47, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, 114, -8, -10, 100, -122, 104, -104, Ascii.SYN, -44, -92, 92, -52, 93, 101, -74, -110, 108, 112, 72, 80, -3, -19, -71, -38, 94, Ascii.NAK, 70, 87, -89, -115, -99, -124, -112, -40, -85, 0, -116, -68, -45, 10, -9, -28, 88, 5, -72, -77, 69, 6, -48, 44, Ascii.RS, -113, -54, 63, 15, 2, -63, -81, -67, 3, 1, 19, -118, 107, 58, -111, 17, 65, 79, 103, -36, -22, -105, -14, -49, -50, -16, -76, -26, 115, -106, -84, 116, Constants.CMD_TYPE.CMD_REQUEST_OTA, -25, -83, Constants.CMD_TYPE.CMD_SIGNATURE_RES, -123, -30, -7, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -24, Ascii.FS, 117, -33, 110, 71, BreezeConstants.BREEZE_PROVISION_VERSION, Ascii.SUB, 113, Ascii.GS, 41, -59, -119, 111, -73, 98, 14, -86, Ascii.CAN, -66, Ascii.ESC, -4, 86, 62, 75, -58, -46, 121, 32, -102, -37, -64, -2, 120, -51, 90, -12, Ascii.US, -35, -88, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, -120, 7, -57, Constants.CMD_TYPE.CMD_STATUS_REPORT, -79, 18, 16, 89, 39, Byte.MIN_VALUE, -20, 95, 96, 81, Byte.MAX_VALUE, -87, Ascii.EM, -75, 74, 13, 45, -27, 122, -97, -109, -55, -100, -17, -96, -32, 59, 77, -82, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, -11, -80, -56, -21, -69, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, -125, 83, -103, 97, Ascii.ETB, 43, 4, 126, -70, 119, -42, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, -31, 105, Ascii.DC4, 99, 85, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 12, 125};

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f8040c = {1, 2, 4, 8, 16, 32, 64, 128, 27, 54, 108, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, 125, 250, 239, 197, 145};

    /* renamed from: d, reason: collision with root package name */
    public int f8041d;

    /* renamed from: e, reason: collision with root package name */
    public int[][] f8042e = null;

    /* renamed from: f, reason: collision with root package name */
    public int f8043f;

    /* renamed from: g, reason: collision with root package name */
    public int f8044g;

    /* renamed from: h, reason: collision with root package name */
    public int f8045h;

    /* renamed from: i, reason: collision with root package name */
    public int f8046i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f8047j;

    public static int b(int i2) {
        int i3 = (1061109567 & i2) << 2;
        int i4 = i2 & (-1061109568);
        int i5 = i4 ^ (i4 >>> 1);
        return (i5 >>> 5) ^ (i3 ^ (i5 >>> 2));
    }

    public static int c(int i2) {
        int iA = a(i2, 8) ^ i2;
        int iA2 = i2 ^ a(iA);
        int iB = iA ^ b(iA2);
        return iA2 ^ (iB ^ a(iB, 16));
    }

    public static int d(int i2) {
        int iA = a(i2, 8);
        int i3 = i2 ^ iA;
        return a(i3) ^ (iA ^ a(i3, 16));
    }

    public static int e(int i2) {
        byte[] bArr = f8038a;
        return (bArr[(i2 >> 24) & 255] << Ascii.CAN) | (bArr[i2 & 255] & 255) | ((bArr[(i2 >> 8) & 255] & 255) << 8) | ((bArr[(i2 >> 16) & 255] & 255) << 16);
    }

    @Override // c.a.b.d
    public int a() {
        return 16;
    }

    @Override // c.a.b.d
    public void reset() {
    }

    public static int a(int i2) {
        return (((i2 & (-2139062144)) >>> 7) * 27) ^ ((2139062143 & i2) << 1);
    }

    public final void b(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        this.f8043f = i3;
        int i4 = ((bArr[i2 + 1] & 255) << 8) | i3;
        this.f8043f = i4;
        int i5 = i4 | ((bArr[i2 + 2] & 255) << 16);
        this.f8043f = i5;
        this.f8043f = i5 | (bArr[i2 + 3] << Ascii.CAN);
        int i6 = bArr[i2 + 4] & 255;
        this.f8044g = i6;
        int i7 = ((bArr[i2 + 5] & 255) << 8) | i6;
        this.f8044g = i7;
        int i8 = i7 | ((bArr[i2 + 6] & 255) << 16);
        this.f8044g = i8;
        this.f8044g = i8 | (bArr[i2 + 7] << Ascii.CAN);
        int i9 = bArr[i2 + 8] & 255;
        this.f8045h = i9;
        int i10 = ((bArr[i2 + 9] & 255) << 8) | i9;
        this.f8045h = i10;
        int i11 = i10 | ((bArr[i2 + 10] & 255) << 16);
        this.f8045h = i11;
        this.f8045h = i11 | (bArr[i2 + 11] << Ascii.CAN);
        int i12 = bArr[i2 + 12] & 255;
        this.f8046i = i12;
        int i13 = ((bArr[i2 + 13] & 255) << 8) | i12;
        this.f8046i = i13;
        int i14 = i13 | ((bArr[i2 + 14] & 255) << 16);
        this.f8046i = i14;
        this.f8046i = (bArr[i2 + 15] << Ascii.CAN) | i14;
    }

    public static int a(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    public final int[][] a(byte[] bArr, boolean z2) {
        int length = bArr.length;
        if (length >= 16 && length <= 32 && (length & 7) == 0) {
            int i2 = length >> 2;
            this.f8041d = i2 + 6;
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i2 + 7, 4);
            if (i2 == 4) {
                int iC = h.c(bArr, 0);
                iArr[0][0] = iC;
                int iC2 = h.c(bArr, 4);
                iArr[0][1] = iC2;
                int iC3 = h.c(bArr, 8);
                iArr[0][2] = iC3;
                int iC4 = h.c(bArr, 12);
                iArr[0][3] = iC4;
                for (int i3 = 1; i3 <= 10; i3++) {
                    iC ^= e(a(iC4, 8)) ^ f8040c[i3 - 1];
                    int[] iArr2 = iArr[i3];
                    iArr2[0] = iC;
                    iC2 ^= iC;
                    iArr2[1] = iC2;
                    iC3 ^= iC2;
                    iArr2[2] = iC3;
                    iC4 ^= iC3;
                    iArr2[3] = iC4;
                }
            } else if (i2 == 6) {
                int iC5 = h.c(bArr, 0);
                iArr[0][0] = iC5;
                int iC6 = h.c(bArr, 4);
                iArr[0][1] = iC6;
                int iC7 = h.c(bArr, 8);
                iArr[0][2] = iC7;
                int iC8 = h.c(bArr, 12);
                iArr[0][3] = iC8;
                int iC9 = h.c(bArr, 16);
                iArr[1][0] = iC9;
                int iC10 = h.c(bArr, 20);
                iArr[1][1] = iC10;
                int iE = iC5 ^ (e(a(iC10, 8)) ^ 1);
                int[] iArr3 = iArr[1];
                iArr3[2] = iE;
                int i4 = iC6 ^ iE;
                iArr3[3] = i4;
                int i5 = iC7 ^ i4;
                int[] iArr4 = iArr[2];
                iArr4[0] = i5;
                int i6 = iC8 ^ i5;
                iArr4[1] = i6;
                int i7 = iC9 ^ i6;
                iArr4[2] = i7;
                int i8 = iC10 ^ i7;
                iArr4[3] = i8;
                int i9 = 2;
                for (int i10 = 3; i10 < 12; i10 += 3) {
                    int iE2 = iE ^ (e(a(i8, 8)) ^ i9);
                    int[] iArr5 = iArr[i10];
                    iArr5[0] = iE2;
                    int i11 = i4 ^ iE2;
                    iArr5[1] = i11;
                    int i12 = i5 ^ i11;
                    iArr5[2] = i12;
                    int i13 = i6 ^ i12;
                    iArr5[3] = i13;
                    int i14 = i7 ^ i13;
                    int i15 = i10 + 1;
                    int[] iArr6 = iArr[i15];
                    iArr6[0] = i14;
                    int i16 = i8 ^ i14;
                    iArr6[1] = i16;
                    int iE3 = e(a(i16, 8)) ^ (i9 << 1);
                    i9 <<= 2;
                    iE = iE2 ^ iE3;
                    int[] iArr7 = iArr[i15];
                    iArr7[2] = iE;
                    i4 = i11 ^ iE;
                    iArr7[3] = i4;
                    i5 = i12 ^ i4;
                    int[] iArr8 = iArr[i10 + 2];
                    iArr8[0] = i5;
                    i6 = i13 ^ i5;
                    iArr8[1] = i6;
                    i7 = i14 ^ i6;
                    iArr8[2] = i7;
                    i8 = i16 ^ i7;
                    iArr8[3] = i8;
                }
                int iE4 = (e(a(i8, 8)) ^ i9) ^ iE;
                int[] iArr9 = iArr[12];
                iArr9[0] = iE4;
                int i17 = iE4 ^ i4;
                iArr9[1] = i17;
                int i18 = i17 ^ i5;
                iArr9[2] = i18;
                iArr9[3] = i18 ^ i6;
            } else if (i2 == 8) {
                int iC11 = h.c(bArr, 0);
                iArr[0][0] = iC11;
                int iC12 = h.c(bArr, 4);
                iArr[0][1] = iC12;
                int iC13 = h.c(bArr, 8);
                iArr[0][2] = iC13;
                int iC14 = h.c(bArr, 12);
                iArr[0][3] = iC14;
                int iC15 = h.c(bArr, 16);
                iArr[1][0] = iC15;
                int iC16 = h.c(bArr, 20);
                iArr[1][1] = iC16;
                int iC17 = h.c(bArr, 24);
                iArr[1][2] = iC17;
                int iC18 = h.c(bArr, 28);
                iArr[1][3] = iC18;
                int i19 = 1;
                for (int i20 = 2; i20 < 14; i20 += 2) {
                    int iE5 = e(a(iC18, 8)) ^ i19;
                    i19 <<= 1;
                    iC11 ^= iE5;
                    int[] iArr10 = iArr[i20];
                    iArr10[0] = iC11;
                    iC12 ^= iC11;
                    iArr10[1] = iC12;
                    iC13 ^= iC12;
                    iArr10[2] = iC13;
                    iC14 ^= iC13;
                    iArr10[3] = iC14;
                    iC15 ^= e(iC14);
                    int[] iArr11 = iArr[i20 + 1];
                    iArr11[0] = iC15;
                    iC16 ^= iC15;
                    iArr11[1] = iC16;
                    iC17 ^= iC16;
                    iArr11[2] = iC17;
                    iC18 ^= iC17;
                    iArr11[3] = iC18;
                }
                int iE6 = (e(a(iC18, 8)) ^ i19) ^ iC11;
                int[] iArr12 = iArr[14];
                iArr12[0] = iE6;
                int i21 = iE6 ^ iC12;
                iArr12[1] = i21;
                int i22 = i21 ^ iC13;
                iArr12[2] = i22;
                iArr12[3] = i22 ^ iC14;
            } else {
                throw new IllegalStateException("Should never get here");
            }
            if (!z2) {
                for (int i23 = 1; i23 < this.f8041d; i23++) {
                    for (int i24 = 0; i24 < 4; i24++) {
                        int[] iArr13 = iArr[i23];
                        iArr13[i24] = c(iArr13[i24]);
                    }
                }
            }
            return iArr;
        }
        throw new IllegalArgumentException("Key length not 128/192/256 bits.");
    }

    public final void b(int[][] iArr) {
        int i2 = this.f8043f;
        int[] iArr2 = iArr[0];
        int i3 = i2 ^ iArr2[0];
        int i4 = this.f8044g ^ iArr2[1];
        int i5 = this.f8045h ^ iArr2[2];
        int iD = iArr2[3] ^ this.f8046i;
        int i6 = 1;
        while (i6 < this.f8041d - 1) {
            byte[] bArr = f8038a;
            int iD2 = d((((bArr[i3 & 255] & 255) ^ ((bArr[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i5 >> 16) & 255] & 255) << 16)) ^ (bArr[(iD >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][0];
            int iD3 = d((((bArr[i4 & 255] & 255) ^ ((bArr[(i5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iD >> 16) & 255] & 255) << 16)) ^ (bArr[(i3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][1];
            int iD4 = d((((bArr[i5 & 255] & 255) ^ ((bArr[(iD >> 8) & 255] & 255) << 8)) ^ ((bArr[(i3 >> 16) & 255] & 255) << 16)) ^ (bArr[(i4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][2];
            int iD5 = d(((((bArr[(i3 >> 8) & 255] & 255) << 8) ^ (bArr[iD & 255] & 255)) ^ ((bArr[(i4 >> 16) & 255] & 255) << 16)) ^ (bArr[(i5 >> 24) & 255] << Ascii.CAN));
            int i7 = i6 + 1;
            int i8 = iD5 ^ iArr[i6][3];
            int iD6 = d((((bArr[iD2 & 255] & 255) ^ ((bArr[(iD3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iD4 >> 16) & 255] & 255) << 16)) ^ (bArr[(i8 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][0];
            int iD7 = d((((bArr[iD3 & 255] & 255) ^ ((bArr[(iD4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i8 >> 16) & 255] & 255) << 16)) ^ (bArr[(iD2 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][1];
            int iD8 = d((((bArr[iD4 & 255] & 255) ^ ((bArr[(i8 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iD2 >> 16) & 255] & 255) << 16)) ^ (bArr[(iD3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][2];
            i6 += 2;
            iD = iArr[i7][3] ^ d((((bArr[i8 & 255] & 255) ^ ((bArr[(iD2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iD3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iD4 >> 24) & 255] << Ascii.CAN));
            i3 = iD6;
            i4 = iD7;
            i5 = iD8;
        }
        byte[] bArr2 = f8038a;
        int iD9 = d((((bArr2[i3 & 255] & 255) ^ ((bArr2[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(i5 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iD >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][0];
        int iD10 = d((((bArr2[i4 & 255] & 255) ^ ((bArr2[(i5 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iD >> 16) & 255] & 255) << 16)) ^ (bArr2[(i3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][1];
        int iD11 = d((((bArr2[i5 & 255] & 255) ^ ((bArr2[(iD >> 8) & 255] & 255) << 8)) ^ ((bArr2[(i3 >> 16) & 255] & 255) << 16)) ^ (bArr2[(i4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][2];
        int iD12 = d(((((bArr2[(i3 >> 8) & 255] & 255) << 8) ^ (bArr2[iD & 255] & 255)) ^ ((bArr2[(i4 >> 16) & 255] & 255) << 16)) ^ (bArr2[(i5 >> 24) & 255] << Ascii.CAN)) ^ iArr[i6][3];
        int i9 = (((bArr2[iD9 & 255] & 255) ^ ((bArr2[(iD10 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iD11 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iD12 >> 24) & 255] << Ascii.CAN);
        int[] iArr3 = iArr[i6 + 1];
        this.f8043f = iArr3[0] ^ i9;
        this.f8044g = ((((bArr2[iD10 & 255] & 255) ^ ((bArr2[(iD11 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iD12 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iD9 >> 24) & 255] << Ascii.CAN)) ^ iArr3[1];
        this.f8045h = ((((bArr2[iD11 & 255] & 255) ^ ((bArr2[(iD12 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iD9 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iD10 >> 24) & 255] << Ascii.CAN)) ^ iArr3[2];
        this.f8046i = ((((bArr2[iD12 & 255] & 255) ^ ((bArr2[(iD9 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iD10 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iD11 >> 24) & 255] << Ascii.CAN)) ^ iArr3[3];
    }

    @Override // c.a.b.d
    public void a(boolean z2, e eVar) {
        if (eVar instanceof k) {
            this.f8042e = a(((k) eVar).a(), z2);
            this.f8047j = z2;
        } else {
            throw new IllegalArgumentException("invalid parameter passed to AES init - " + eVar.getClass().getName());
        }
    }

    @Override // c.a.b.d
    public int a(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.f8042e != null) {
            if (i2 + 16 <= bArr.length) {
                if (i3 + 16 <= bArr2.length) {
                    if (this.f8047j) {
                        b(bArr, i2);
                        b(this.f8042e);
                        a(bArr2, i3);
                        return 16;
                    }
                    b(bArr, i2);
                    a(this.f8042e);
                    a(bArr2, i3);
                    return 16;
                }
                throw new OutputLengthException("output buffer too short");
            }
            throw new DataLengthException("input buffer too short");
        }
        throw new IllegalStateException("AES engine not initialised");
    }

    public final void a(byte[] bArr, int i2) {
        int i3 = this.f8043f;
        bArr[i2] = (byte) i3;
        bArr[i2 + 1] = (byte) (i3 >> 8);
        bArr[i2 + 2] = (byte) (i3 >> 16);
        bArr[i2 + 3] = (byte) (i3 >> 24);
        int i4 = this.f8044g;
        bArr[i2 + 4] = (byte) i4;
        bArr[i2 + 5] = (byte) (i4 >> 8);
        bArr[i2 + 6] = (byte) (i4 >> 16);
        bArr[i2 + 7] = (byte) (i4 >> 24);
        int i5 = this.f8045h;
        bArr[i2 + 8] = (byte) i5;
        bArr[i2 + 9] = (byte) (i5 >> 8);
        bArr[i2 + 10] = (byte) (i5 >> 16);
        bArr[i2 + 11] = (byte) (i5 >> 24);
        int i6 = this.f8046i;
        bArr[i2 + 12] = (byte) i6;
        bArr[i2 + 13] = (byte) (i6 >> 8);
        bArr[i2 + 14] = (byte) (i6 >> 16);
        bArr[i2 + 15] = (byte) (i6 >> 24);
    }

    public final void a(int[][] iArr) {
        int i2 = this.f8043f;
        int i3 = this.f8041d;
        int[] iArr2 = iArr[i3];
        int i4 = i2 ^ iArr2[0];
        int i5 = this.f8044g ^ iArr2[1];
        int i6 = this.f8045h ^ iArr2[2];
        int i7 = i3 - 1;
        int iC = iArr2[3] ^ this.f8046i;
        while (i7 > 1) {
            byte[] bArr = f8039b;
            int iC2 = c((((bArr[i4 & 255] & 255) ^ ((bArr[(iC >> 8) & 255] & 255) << 8)) ^ ((bArr[(i6 >> 16) & 255] & 255) << 16)) ^ (bArr[(i5 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][0];
            int iC3 = c((((bArr[i5 & 255] & 255) ^ ((bArr[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iC >> 16) & 255] & 255) << 16)) ^ (bArr[(i6 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][1];
            int iC4 = c((((bArr[i6 & 255] & 255) ^ ((bArr[(i5 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i4 >> 16) & 255] & 255) << 16)) ^ (bArr[(iC >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][2];
            int iC5 = c((bArr[(i4 >> 24) & 255] << Ascii.CAN) ^ (((bArr[iC & 255] & 255) ^ ((bArr[(i6 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i5 >> 16) & 255] & 255) << 16)));
            int i8 = i7 - 1;
            int i9 = iC5 ^ iArr[i7][3];
            int iC6 = c((((bArr[iC2 & 255] & 255) ^ ((bArr[(i9 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iC4 >> 16) & 255] & 255) << 16)) ^ (bArr[(iC3 >> 24) & 255] << Ascii.CAN)) ^ iArr[i8][0];
            int iC7 = c((((bArr[iC3 & 255] & 255) ^ ((bArr[(iC2 >> 8) & 255] & 255) << 8)) ^ ((bArr[(i9 >> 16) & 255] & 255) << 16)) ^ (bArr[(iC4 >> 24) & 255] << Ascii.CAN)) ^ iArr[i8][1];
            int iC8 = c((((bArr[iC4 & 255] & 255) ^ ((bArr[(iC3 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iC2 >> 16) & 255] & 255) << 16)) ^ (bArr[(i9 >> 24) & 255] << Ascii.CAN)) ^ iArr[i8][2];
            i7 -= 2;
            iC = iArr[i8][3] ^ c((((bArr[i9 & 255] & 255) ^ ((bArr[(iC4 >> 8) & 255] & 255) << 8)) ^ ((bArr[(iC3 >> 16) & 255] & 255) << 16)) ^ (bArr[(iC2 >> 24) & 255] << Ascii.CAN));
            i4 = iC6;
            i5 = iC7;
            i6 = iC8;
        }
        byte[] bArr2 = f8039b;
        int iC9 = c((((bArr2[i4 & 255] & 255) ^ ((bArr2[(iC >> 8) & 255] & 255) << 8)) ^ ((bArr2[(i6 >> 16) & 255] & 255) << 16)) ^ (bArr2[(i5 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][0];
        int iC10 = c((((bArr2[i5 & 255] & 255) ^ ((bArr2[(i4 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iC >> 16) & 255] & 255) << 16)) ^ (bArr2[(i6 >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][1];
        int iC11 = c((((bArr2[i6 & 255] & 255) ^ ((bArr2[(i5 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(i4 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iC >> 24) & 255] << Ascii.CAN)) ^ iArr[i7][2];
        int iC12 = c((bArr2[(i4 >> 24) & 255] << Ascii.CAN) ^ (((bArr2[iC & 255] & 255) ^ ((bArr2[(i6 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(i5 >> 16) & 255] & 255) << 16))) ^ iArr[i7][3];
        int i10 = (((bArr2[iC9 & 255] & 255) ^ ((bArr2[(iC12 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iC11 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iC10 >> 24) & 255] << Ascii.CAN);
        int[] iArr3 = iArr[0];
        this.f8043f = i10 ^ iArr3[0];
        this.f8044g = ((((bArr2[iC10 & 255] & 255) ^ ((bArr2[(iC9 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iC12 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iC11 >> 24) & 255] << Ascii.CAN)) ^ iArr3[1];
        this.f8045h = ((((bArr2[iC11 & 255] & 255) ^ ((bArr2[(iC10 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iC9 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iC12 >> 24) & 255] << Ascii.CAN)) ^ iArr3[2];
        this.f8046i = ((((bArr2[iC12 & 255] & 255) ^ ((bArr2[(iC11 >> 8) & 255] & 255) << 8)) ^ ((bArr2[(iC10 >> 16) & 255] & 255) << 16)) ^ (bArr2[(iC9 >> 24) & 255] << Ascii.CAN)) ^ iArr3[3];
    }
}
