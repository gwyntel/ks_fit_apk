package com.huawei.hms.scankit.p;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.text.TextUtils;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanBase;
import com.huawei.hms.ml.scan.HmsScanResult;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import com.huawei.hms.scankit.p.w3;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class r6 {

    /* renamed from: a, reason: collision with root package name */
    private static volatile r6 f17741a;

    public static r6 a() {
        if (f17741a == null) {
            synchronized (r6.class) {
                try {
                    if (f17741a == null) {
                        f17741a = new r6();
                    }
                } finally {
                }
            }
        }
        return f17741a;
    }

    public static s6 c() {
        return new s6("", null, new u6[]{new u6(-2.0f, -2.0f), new u6(-2.0f, -2.0f), new u6(-2.0f, -2.0f), new u6(-2.0f, -2.0f)}, BarcodeFormat.NONE);
    }

    public HmsScan[] b(Bitmap bitmap, int i2, boolean z2, w3 w3Var) {
        w3.c cVarA;
        if (w3Var != null) {
            w3Var.a("single");
            cVarA = w3Var.a(z2, bitmap.getHeight() * bitmap.getWidth());
            if (bitmap.getHeight() < 30 || bitmap.getWidth() < 30) {
                cVarA.a(-1005);
            }
        } else {
            cVarA = null;
        }
        s6[] s6VarArrB = m1.b(bitmap, new x6(i2, z2));
        o4.d("Scankit", "start totalParseResult");
        HmsScan[] hmsScanArrA = y6.a(s6VarArrB);
        if (w3Var != null) {
            w3Var.a(hmsScanArrA, cVarA);
        }
        return hmsScanArrA;
    }

    public HmsScanResult a(byte[] bArr, int i2, int i3, int i4, boolean z2, boolean z3, w3 w3Var) {
        w3.c cVarA;
        if (w3Var != null) {
            w3Var.a("single");
            cVarA = w3Var.a(z2, i3 * i2);
            if (i3 < 30 || i2 < 30) {
                cVarA.a(-1005);
            }
        } else {
            cVarA = null;
        }
        w3.c cVar = cVarA;
        o4.d("Scankit", "start decodeSingleCode");
        s6[] s6VarArrC = m1.c(bArr, new x6(i2, i3, i4, true, z2));
        o4.d("Scankit", "start totalParseResult");
        HmsScan[] hmsScanArrA = y6.a(s6VarArrC);
        o4.d("Scankit", "end totalParseResult");
        if (w3Var != null) {
            w3Var.a(hmsScanArrA, cVar);
        }
        if (!z3) {
            o4.d("Scankit", "start hmsResultTrans");
            hmsScanArrA = w7.a(hmsScanArrA);
            o4.d("Scankit", "end hmsResultTrans");
        }
        int i5 = i4 == 0 ? 8191 : i4;
        if (r3.f17717d) {
            return new HmsScanResult(4099, hmsScanArrA);
        }
        if (r3.f17718e) {
            return new HmsScanResult(4100, hmsScanArrA);
        }
        if (r3.f17721h && hmsScanArrA.length == 0) {
            int i6 = HmsScanBase.QRCODE_SCAN_TYPE;
            if ((i5 & i6) == i6) {
                return new HmsScanResult(4097, hmsScanArrA);
            }
        }
        if (hmsScanArrA.length == 0) {
            return new HmsScanResult(4096, hmsScanArrA);
        }
        if (hmsScanArrA.length > 0 && !TextUtils.isEmpty(hmsScanArrA[0].getOriginalValue())) {
            return new HmsScanResult(0, hmsScanArrA);
        }
        if (hmsScanArrA.length > 0 && hmsScanArrA[0].getZoomValue() > 1.0d) {
            return new HmsScanResult(4098, hmsScanArrA);
        }
        return new HmsScanResult(4096, new HmsScan[0]);
    }

    public static HmsScan b() {
        return new HmsScan("", HmsScanBase.FORMAT_UNKNOWN, "", HmsScan.PURE_TEXT_FORM, null, new Point[]{new Point(-2, -2), new Point(-2, -2), new Point(-2, -2), new Point(-2, -2)}, null, null).setZoomValue(1.0d);
    }

    public HmsScan[] a(Bitmap bitmap, int i2, boolean z2, w3 w3Var) {
        w3.c cVarA;
        o4.d("Scankit", "start decodeWithBitmapWorkMulti");
        if (w3Var != null) {
            w3Var.a("multi");
            cVarA = w3Var.a(z2, bitmap.getHeight() * bitmap.getWidth());
            if (bitmap.getHeight() < 30 || bitmap.getWidth() < 30) {
                cVarA.a(-1005);
            }
        } else {
            cVarA = null;
        }
        o4.d("Scankit", "end decodeWithBitmapWorkMulti");
        s6[] s6VarArrA = m1.a(bitmap, new x6(i2, z2));
        o4.d("Scankit", "start totalParseResult");
        HmsScan[] hmsScanArrA = y6.a(s6VarArrA);
        o4.d("Scankit", "end totalParseResult");
        if (w3Var != null) {
            w3Var.a(hmsScanArrA, cVarA);
        }
        return hmsScanArrA;
    }

    public HmsScan[] a(ByteBuffer byteBuffer, int i2, int i3, int i4, boolean z2, w3 w3Var) {
        w3.c cVarA;
        if (w3Var != null) {
            w3Var.a("multi");
            int i5 = i3 * i2;
            cVarA = w3Var.a(z2, i5);
            if (i2 >= 30 && i3 >= 30) {
                if (byteBuffer.array().length < i5) {
                    cVarA.a(-1008);
                }
            } else {
                cVarA.a(-1007);
            }
        } else {
            cVarA = null;
        }
        HmsScan[] hmsScanArrA = y6.a(m1.a(byteBuffer, new x6(i2, i3, i4, true, z2)));
        if (w3Var != null) {
            w3Var.a(hmsScanArrA, cVarA);
        }
        return hmsScanArrA;
    }
}
