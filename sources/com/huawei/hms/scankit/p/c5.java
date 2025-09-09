package com.huawei.hms.scankit.p;

import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class c5 implements l8 {

    static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f17071a;

        static {
            int[] iArr = new int[BarcodeFormat.values().length];
            f17071a = iArr;
            try {
                iArr[BarcodeFormat.EAN_8.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f17071a[BarcodeFormat.UPC_E.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f17071a[BarcodeFormat.EAN_13.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f17071a[BarcodeFormat.UPC_A.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f17071a[BarcodeFormat.QR_CODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f17071a[BarcodeFormat.CODE_39.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f17071a[BarcodeFormat.CODE_93.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f17071a[BarcodeFormat.CODE_128.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f17071a[BarcodeFormat.ITF.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f17071a[BarcodeFormat.PDF_417.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f17071a[BarcodeFormat.CODABAR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f17071a[BarcodeFormat.DATA_MATRIX.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f17071a[BarcodeFormat.AZTEC.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    @Override // com.huawei.hms.scankit.p.l8
    public s a(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<u2, ?> map) throws WriterException {
        l8 r2Var;
        switch (a.f17071a[barcodeFormat.ordinal()]) {
            case 1:
                r2Var = new r2();
                break;
            case 2:
                r2Var = new t7();
                break;
            case 3:
                r2Var = new p2();
                break;
            case 4:
                r2Var = new m7();
                break;
            case 5:
                r2Var = new k6();
                break;
            case 6:
                r2Var = new u0();
                break;
            case 7:
                r2Var = new w0();
                break;
            case 8:
                r2Var = new s0();
                break;
            case 9:
                r2Var = new k4();
                break;
            case 10:
                r2Var = new u5();
                break;
            case 11:
                r2Var = new q0();
                break;
            case 12:
                r2Var = new j1();
                break;
            case 13:
                r2Var = new i();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + barcodeFormat);
        }
        return r2Var.a(str, barcodeFormat, i2, i3, map);
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x020a A[Catch: Exception -> 0x01c9, IllegalArgumentException -> 0x01cc, OutOfMemoryError -> 0x01cf, TryCatch #4 {OutOfMemoryError -> 0x01cf, blocks: (B:105:0x0127, B:108:0x012d, B:110:0x0131, B:112:0x014f, B:115:0x0156, B:119:0x01a9, B:121:0x01ad, B:123:0x01b1, B:125:0x01b5, B:127:0x01b9, B:138:0x01e3, B:134:0x01d2, B:136:0x01d8, B:137:0x01de, B:139:0x01e6, B:140:0x01e9, B:145:0x020a, B:148:0x021e, B:152:0x0230, B:154:0x0236, B:156:0x0241, B:155:0x023c, B:157:0x0244, B:158:0x0247, B:166:0x0279, B:168:0x0281, B:169:0x0288), top: B:186:0x0127 }] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x021d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x021e A[Catch: Exception -> 0x01c9, IllegalArgumentException -> 0x01cc, OutOfMemoryError -> 0x01cf, TryCatch #4 {OutOfMemoryError -> 0x01cf, blocks: (B:105:0x0127, B:108:0x012d, B:110:0x0131, B:112:0x014f, B:115:0x0156, B:119:0x01a9, B:121:0x01ad, B:123:0x01b1, B:125:0x01b5, B:127:0x01b9, B:138:0x01e3, B:134:0x01d2, B:136:0x01d8, B:137:0x01de, B:139:0x01e6, B:140:0x01e9, B:145:0x020a, B:148:0x021e, B:152:0x0230, B:154:0x0236, B:156:0x0241, B:155:0x023c, B:157:0x0244, B:158:0x0247, B:166:0x0279, B:168:0x0281, B:169:0x0288), top: B:186:0x0127 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0296  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap a(java.lang.String r27, int r28, int r29, int r30, com.huawei.hms.ml.scan.HmsBuildBitmapOption r31) throws com.huawei.hms.hmsscankit.WriterException {
        /*
            Method dump skipped, instructions count: 760
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.c5.a(java.lang.String, int, int, int, com.huawei.hms.ml.scan.HmsBuildBitmapOption):android.graphics.Bitmap");
    }
}
