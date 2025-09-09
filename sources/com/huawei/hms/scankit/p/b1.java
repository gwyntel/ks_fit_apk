package com.huawei.hms.scankit.p;

import android.view.animation.Interpolator;
import java.math.BigDecimal;

/* loaded from: classes4.dex */
public class b1 implements Interpolator {

    /* renamed from: e, reason: collision with root package name */
    private static final BigDecimal f16990e;

    /* renamed from: f, reason: collision with root package name */
    private static final BigDecimal f16991f;

    /* renamed from: g, reason: collision with root package name */
    private static final float f16992g;

    /* renamed from: a, reason: collision with root package name */
    private float f16993a;

    /* renamed from: b, reason: collision with root package name */
    private float f16994b;

    /* renamed from: c, reason: collision with root package name */
    private float f16995c;

    /* renamed from: d, reason: collision with root package name */
    private float f16996d;

    static {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(1.0f));
        f16990e = bigDecimal;
        BigDecimal bigDecimal2 = new BigDecimal(Long.toString(4000L));
        f16991f = bigDecimal2;
        f16992g = bigDecimal.divide(bigDecimal2, 20, 4).floatValue();
    }

    public b1(float f2, float f3, float f4, float f5) {
        this.f16993a = f2;
        this.f16994b = f3;
        this.f16995c = f4;
        this.f16996d = f5;
    }

    private long a(float f2) {
        long j2 = 0;
        long j3 = 4000;
        while (j2 <= j3) {
            long j4 = (j2 + j3) >>> 1;
            float fB = b(f16992g * j4);
            if (fB < f2) {
                j2 = j4 + 1;
            } else {
                if (fB <= f2) {
                    return j4;
                }
                j3 = j4 - 1;
            }
        }
        return j2;
    }

    private float b(float f2) {
        float f3 = 1.0f - f2;
        float f4 = 3.0f * f3;
        return (f3 * f4 * f2 * this.f16993a) + (f4 * f2 * f2 * this.f16995c) + (f2 * f2 * f2);
    }

    private float c(float f2) {
        float f3 = 1.0f - f2;
        float f4 = 3.0f * f3;
        return (f3 * f4 * f2 * this.f16994b) + (f4 * f2 * f2 * this.f16996d) + (f2 * f2 * f2);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f2) {
        return c(f16992g * a(f2));
    }

    public String toString() {
        return a();
    }

    private String a() {
        return "CubicBezierInterpolator  mControlPoint1x = " + this.f16993a + ", mControlPoint1y = " + this.f16994b + ", mControlPoint2x = " + this.f16995c + ", mControlPoint2y = " + this.f16996d;
    }
}
