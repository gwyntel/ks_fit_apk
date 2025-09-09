package com.huawei.hms.scankit.p;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.hmsscankit.DetailRect;
import com.huawei.hms.hmsscankit.api.IRemoteFrameDecoderDelegate;
import com.huawei.hms.scankit.util.OpencvJNI;

/* loaded from: classes4.dex */
public class h4 extends IRemoteFrameDecoderDelegate.Stub {

    /* renamed from: d, reason: collision with root package name */
    private static volatile h4 f17324d = new h4();

    /* renamed from: a, reason: collision with root package name */
    Point f17325a;

    /* renamed from: b, reason: collision with root package name */
    int f17326b = 0;

    /* renamed from: c, reason: collision with root package name */
    Rect f17327c;

    private h4() {
    }

    public static h4 a() {
        return f17324d;
    }

    public synchronized Rect b(int i2, int i3) {
        try {
            Rect rect = new Rect(a(i2, i3));
            Point point = new Point(i2, i3);
            Point point2 = this.f17325a;
            if (point2 == null) {
                return null;
            }
            int i4 = point2.x;
            int i5 = point2.y;
            if (i4 < i5) {
                int i6 = rect.left;
                int i7 = point.y;
                rect.left = (i6 * i7) / i4;
                rect.right = (rect.right * i7) / i4;
                int i8 = rect.top;
                int i9 = point.x;
                rect.top = (i8 * i9) / i5;
                rect.bottom = (rect.bottom * i9) / i5;
            } else {
                int i10 = rect.top;
                int i11 = point.y;
                rect.top = (i10 * i11) / i5;
                rect.bottom = (rect.bottom * i11) / i5;
                int i12 = rect.left;
                int i13 = point.x;
                rect.left = (i12 * i13) / i4;
                rect.right = (rect.right * i13) / i4;
            }
            return rect;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.huawei.hms.hmsscankit.api.IRemoteFrameDecoderDelegate
    public s6[] decode(byte[] bArr, int i2, int i3, int i4, int i5, IObjectWrapper iObjectWrapper) throws RemoteException {
        boolean z2;
        boolean z3;
        boolean z4;
        if (!r3.A) {
            OpencvJNI.init();
        }
        if (iObjectWrapper == null || !(ObjectWrapper.unwrap(iObjectWrapper) instanceof Bundle)) {
            z2 = false;
            z3 = false;
            z4 = true;
        } else {
            Bundle bundle = (Bundle) ObjectWrapper.unwrap(iObjectWrapper);
            this.f17325a = (Point) bundle.getParcelable("Screen");
            this.f17327c = (Rect) bundle.getParcelable("Rect");
            z2 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper)).getBoolean(DetailRect.USE_APK, false);
            z3 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper)).getBoolean(DetailRect.SUPPORT_ROLLBACK, false);
            z4 = ((Bundle) ObjectWrapper.unwrap(iObjectWrapper)).getBoolean(DetailRect.PARSE_RESULT, true);
        }
        r3.f17719f = z4;
        if (z3 && !r3.f17714a && z2) {
            return new s6[]{r6.c()};
        }
        if (this.f17327c == null) {
            this.f17327c = new Rect(-1, -1, -1, -1);
        }
        if (this.f17325a == null) {
            this.f17325a = new Point(1080, 1920);
        }
        e6 e6VarA = a(bArr, i2, i3, i4);
        byte[] bArrB = e6VarA.b();
        x6 x6Var = new x6(e6VarA.c(), e6VarA.a(), i5);
        int i6 = this.f17326b;
        this.f17326b = 1 + i6;
        return m1.c(bArrB, x6Var.a(i6));
    }

    private e6 a(byte[] bArr, int i2, int i3, int i4) {
        if (i4 == 0) {
            byte[] bArr2 = new byte[bArr.length];
            for (int i5 = 0; i5 < i3; i5++) {
                for (int i6 = 0; i6 < i2; i6++) {
                    bArr2[(((i6 * i3) + i3) - i5) - 1] = bArr[(i5 * i2) + i6];
                }
            }
            return a(bArr2, i3, i2);
        }
        if (i4 == 2) {
            byte[] bArr3 = new byte[bArr.length];
            for (int i7 = 0; i7 < i3; i7++) {
                for (int i8 = 0; i8 < i2; i8++) {
                    bArr3[(((i2 - 1) - i8) * i3) + i7] = bArr[(i7 * i2) + i8];
                }
            }
            return a(bArr3, i3, i2);
        }
        if (i4 != 3) {
            return a(bArr, i2, i3);
        }
        byte[] bArr4 = new byte[bArr.length];
        for (int i9 = 0; i9 < i3; i9++) {
            for (int i10 = 0; i10 < i2; i10++) {
                bArr4[(((((i3 - 1) - i9) * i2) + i2) - 1) - i10] = bArr[(i9 * i2) + i10];
            }
        }
        return a(bArr4, i2, i3);
    }

    public synchronized Rect a(int i2, int i3) {
        int iMin;
        int i4;
        int i5;
        iMin = Math.min(i2, i3);
        i4 = (i2 - iMin) / 2;
        i5 = (i3 - iMin) / 2;
        return new Rect(i4, i5, i4 + iMin, iMin + i5);
    }

    public e6 a(byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        if (b(i2, i3) == null) {
            return null;
        }
        int iMin = (int) (Math.min(i2, i3) * 0.8d);
        int i12 = (i2 - iMin) / 2;
        int i13 = (i3 - iMin) / 2;
        Rect rect = this.f17327c;
        if (rect == null) {
            i4 = i12;
            i5 = iMin;
        } else {
            if (rect.left == -1 && rect.right == -1 && rect.top == -1 && rect.bottom == -1) {
                iMin = (int) (i2 * 0.85d);
                i12 = (i2 - iMin) / 2;
                double d2 = i3;
                i11 = (int) (0.8d * d2);
                i13 = (int) (d2 * 0.1d);
            } else {
                iMin = (int) (Math.min(i2, i3) * 0.9d);
                i12 = (i2 - iMin) / 2;
                Rect rect2 = this.f17327c;
                Rect rect3 = new Rect(rect2.left, rect2.top, rect2.right, rect2.bottom);
                int iMax = Math.max(i2, i3);
                Point point = this.f17325a;
                if (point != null) {
                    i10 = point.x;
                    i9 = point.y;
                } else {
                    i9 = iMax;
                    i10 = i9;
                }
                float fMax = iMax / Math.max(i10, i9);
                Rect rect4 = this.f17327c;
                int i14 = (int) (rect4.top * fMax);
                rect3.top = i14;
                int i15 = (int) (rect4.bottom * fMax);
                rect3.bottom = i15;
                float f2 = i3 / 14.0f;
                if (i14 > f2) {
                    i14 -= (int) f2;
                }
                i13 = i14 < 0 ? 0 : i14;
                i11 = i15 - i13;
                if (i13 + i11 > i3) {
                    i13 = (i3 - iMin) / 2;
                    i4 = i12;
                    i5 = iMin;
                }
            }
            int i16 = i12;
            i5 = iMin;
            iMin = i11;
            i4 = i16;
        }
        o4.a("ScanSize", "top:" + i13 + "scanSizeHeight" + iMin + "mHeight:" + i3);
        if (i3 < i13 + iMin) {
            i6 = i3;
            i7 = 0;
        } else {
            i6 = iMin;
            i7 = i13;
        }
        if (i2 < i4 + i5) {
            i8 = i2;
            i4 = 0;
        } else {
            i8 = i5;
        }
        return new e6(bArr, i2, i3, i4, i7, i8, i6, false);
    }
}
