package com.umeng.message.proguard;

import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.common.base.Ascii;

/* loaded from: classes4.dex */
public final class as extends at {

    /* renamed from: g, reason: collision with root package name */
    private static final byte[] f22786g = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 43, Constants.CMD_TYPE.CMD_OTA};

    /* renamed from: h, reason: collision with root package name */
    private static final byte[] f22787h = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, Constants.CMD_TYPE.CMD_SIGNATURE_RES, Constants.CMD_TYPE.CMD_NOTIFY_STATUS, Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, 56, 57, 58, 59, Constants.CMD_TYPE.CMD_BUSINESS_DOWNSTREAM, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, Ascii.DC4, Ascii.NAK, Ascii.SYN, Ascii.ETB, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, 63, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, Constants.CMD_TYPE.CMD_REQUEST_OTA, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, Constants.CMD_TYPE.CMD_OTA_RES, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 39, 40, 41, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RESEX, 43, 44, 45, 46, Constants.CMD_TYPE.CMD_OTA, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES};

    /* renamed from: l, reason: collision with root package name */
    private int f22791l;

    /* renamed from: k, reason: collision with root package name */
    private final int f22790k = 4;

    /* renamed from: j, reason: collision with root package name */
    private final int f22789j = 4 - 1;

    /* renamed from: i, reason: collision with root package name */
    private final byte[] f22788i = f22786g;

    private static byte[] f(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        as asVar = new as();
        long jC = super.c(bArr);
        if (jC <= 2147483647L) {
            return super.d(bArr);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + jC + ") than the specified maximum size of 2147483647");
    }

    @Override // com.umeng.message.proguard.at
    final void a(byte[] bArr, int i2, int i3) {
        if (this.f22795d) {
            return;
        }
        if (i3 >= 0) {
            int i4 = 0;
            while (i4 < i3) {
                a(this.f22790k);
                int i5 = (this.f22797f + 1) % 3;
                this.f22797f = i5;
                int i6 = i2 + 1;
                int i7 = bArr[i2];
                if (i7 < 0) {
                    i7 += 256;
                }
                int i8 = (this.f22791l << 8) + i7;
                this.f22791l = i8;
                if (i5 == 0) {
                    byte[] bArr2 = this.f22793b;
                    int i9 = this.f22794c;
                    int i10 = i9 + 1;
                    this.f22794c = i10;
                    byte[] bArr3 = this.f22788i;
                    bArr2[i9] = bArr3[(i8 >> 18) & 63];
                    int i11 = i9 + 2;
                    this.f22794c = i11;
                    bArr2[i10] = bArr3[(i8 >> 12) & 63];
                    int i12 = i9 + 3;
                    this.f22794c = i12;
                    bArr2[i11] = bArr3[(i8 >> 6) & 63];
                    this.f22794c = i9 + 4;
                    bArr2[i12] = bArr3[i8 & 63];
                    this.f22796e += 4;
                }
                i4++;
                i2 = i6;
            }
            return;
        }
        this.f22795d = true;
        if (this.f22797f == 0) {
            return;
        }
        a(this.f22790k);
        int i13 = this.f22794c;
        int i14 = this.f22797f;
        if (i14 == 1) {
            byte[] bArr4 = this.f22793b;
            int i15 = i13 + 1;
            this.f22794c = i15;
            byte[] bArr5 = this.f22788i;
            int i16 = this.f22791l;
            bArr4[i13] = bArr5[(i16 >> 2) & 63];
            int i17 = i13 + 2;
            this.f22794c = i17;
            bArr4[i15] = bArr5[(i16 << 4) & 63];
            if (bArr5 == f22786g) {
                int i18 = i13 + 3;
                this.f22794c = i18;
                bArr4[i17] = 61;
                this.f22794c = i13 + 4;
                bArr4[i18] = 61;
            }
        } else if (i14 == 2) {
            byte[] bArr6 = this.f22793b;
            int i19 = i13 + 1;
            this.f22794c = i19;
            byte[] bArr7 = this.f22788i;
            int i20 = this.f22791l;
            bArr6[i13] = bArr7[(i20 >> 10) & 63];
            int i21 = i13 + 2;
            this.f22794c = i21;
            bArr6[i19] = bArr7[(i20 >> 4) & 63];
            int i22 = i13 + 3;
            this.f22794c = i22;
            bArr6[i21] = bArr7[(i20 << 2) & 63];
            if (bArr7 == f22786g) {
                this.f22794c = i13 + 4;
                bArr6[i22] = 61;
            }
        }
        this.f22796e += this.f22794c - i13;
    }

    @Override // com.umeng.message.proguard.at
    public final /* bridge */ /* synthetic */ byte[] b(String str) {
        return super.b(str);
    }

    @Override // com.umeng.message.proguard.at
    public final /* bridge */ /* synthetic */ long c(byte[] bArr) {
        return super.c(bArr);
    }

    @Override // com.umeng.message.proguard.at
    public final /* bridge */ /* synthetic */ byte[] d(byte[] bArr) {
        return super.d(bArr);
    }

    @Override // com.umeng.message.proguard.at
    public final /* bridge */ /* synthetic */ byte[] e(byte[] bArr) {
        return super.e(bArr);
    }

    @Override // com.umeng.message.proguard.at
    final void b(byte[] bArr, int i2, int i3) {
        byte b2;
        if (this.f22795d) {
            return;
        }
        if (i3 < 0) {
            this.f22795d = true;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= i3) {
                break;
            }
            a(this.f22789j);
            int i5 = i2 + 1;
            byte b3 = bArr[i2];
            if (b3 == 61) {
                this.f22795d = true;
                break;
            }
            if (b3 >= 0) {
                byte[] bArr2 = f22787h;
                if (b3 < bArr2.length && (b2 = bArr2[b3]) >= 0) {
                    int i6 = (this.f22797f + 1) % 4;
                    this.f22797f = i6;
                    int i7 = (this.f22791l << 6) + b2;
                    this.f22791l = i7;
                    if (i6 == 0) {
                        byte[] bArr3 = this.f22793b;
                        int i8 = this.f22794c;
                        int i9 = i8 + 1;
                        this.f22794c = i9;
                        bArr3[i8] = (byte) ((i7 >> 16) & 255);
                        int i10 = i8 + 2;
                        this.f22794c = i10;
                        bArr3[i9] = (byte) ((i7 >> 8) & 255);
                        this.f22794c = i8 + 3;
                        bArr3[i10] = (byte) (i7 & 255);
                    }
                }
            }
            i4++;
            i2 = i5;
        }
        if (!this.f22795d || this.f22797f == 0) {
            return;
        }
        a(this.f22789j);
        int i11 = this.f22797f;
        if (i11 == 2) {
            int i12 = this.f22791l >> 4;
            this.f22791l = i12;
            byte[] bArr4 = this.f22793b;
            int i13 = this.f22794c;
            this.f22794c = i13 + 1;
            bArr4[i13] = (byte) (i12 & 255);
            return;
        }
        if (i11 != 3) {
            return;
        }
        int i14 = this.f22791l;
        int i15 = i14 >> 2;
        this.f22791l = i15;
        byte[] bArr5 = this.f22793b;
        int i16 = this.f22794c;
        int i17 = i16 + 1;
        this.f22794c = i17;
        bArr5[i16] = (byte) ((i14 >> 10) & 255);
        this.f22794c = i16 + 2;
        bArr5[i17] = (byte) (i15 & 255);
    }

    public static byte[] b(byte[] bArr) {
        return f(bArr);
    }

    public static String a(byte[] bArr) {
        return new String(f(bArr));
    }

    public static byte[] a(String str) {
        return super.b(str);
    }
}
