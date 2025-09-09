package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

/* loaded from: classes.dex */
public class Oscillator {
    public static final int BOUNCE = 6;
    public static final int COS_WAVE = 5;
    public static final int CUSTOM = 7;
    public static final int REVERSE_SAW_WAVE = 4;
    public static final int SAW_WAVE = 3;
    public static final int SIN_WAVE = 0;
    public static final int SQUARE_WAVE = 1;
    public static String TAG = "Oscillator";
    public static final int TRIANGLE_WAVE = 2;

    /* renamed from: c, reason: collision with root package name */
    double[] f2720c;

    /* renamed from: d, reason: collision with root package name */
    String f2721d;

    /* renamed from: e, reason: collision with root package name */
    MonotonicCurveFit f2722e;

    /* renamed from: f, reason: collision with root package name */
    int f2723f;

    /* renamed from: a, reason: collision with root package name */
    float[] f2718a = new float[0];

    /* renamed from: b, reason: collision with root package name */
    double[] f2719b = new double[0];

    /* renamed from: g, reason: collision with root package name */
    double f2724g = 6.283185307179586d;
    private boolean mNormalized = false;

    double a(double d2) {
        if (d2 <= 0.0d) {
            d2 = 1.0E-5d;
        } else if (d2 >= 1.0d) {
            d2 = 0.999999d;
        }
        int iBinarySearch = Arrays.binarySearch(this.f2719b, d2);
        if (iBinarySearch > 0 || iBinarySearch == 0) {
            return 0.0d;
        }
        int i2 = -iBinarySearch;
        int i3 = i2 - 1;
        float[] fArr = this.f2718a;
        float f2 = fArr[i3];
        int i4 = i2 - 2;
        float f3 = fArr[i4];
        double[] dArr = this.f2719b;
        double d3 = dArr[i3];
        double d4 = dArr[i4];
        double d5 = (f2 - f3) / (d3 - d4);
        return (d2 * d5) + (f3 - (d5 * d4));
    }

    public void addPoint(double d2, float f2) {
        int length = this.f2718a.length + 1;
        int iBinarySearch = Arrays.binarySearch(this.f2719b, d2);
        if (iBinarySearch < 0) {
            iBinarySearch = (-iBinarySearch) - 1;
        }
        this.f2719b = Arrays.copyOf(this.f2719b, length);
        this.f2718a = Arrays.copyOf(this.f2718a, length);
        this.f2720c = new double[length];
        double[] dArr = this.f2719b;
        System.arraycopy(dArr, iBinarySearch, dArr, iBinarySearch + 1, (length - iBinarySearch) - 1);
        this.f2719b[iBinarySearch] = d2;
        this.f2718a[iBinarySearch] = f2;
        this.mNormalized = false;
    }

    double b(double d2) {
        if (d2 < 0.0d) {
            d2 = 0.0d;
        } else if (d2 > 1.0d) {
            d2 = 1.0d;
        }
        int iBinarySearch = Arrays.binarySearch(this.f2719b, d2);
        if (iBinarySearch > 0) {
            return 1.0d;
        }
        if (iBinarySearch == 0) {
            return 0.0d;
        }
        int i2 = -iBinarySearch;
        int i3 = i2 - 1;
        float[] fArr = this.f2718a;
        float f2 = fArr[i3];
        int i4 = i2 - 2;
        float f3 = fArr[i4];
        double[] dArr = this.f2719b;
        double d3 = dArr[i3];
        double d4 = dArr[i4];
        double d5 = (f2 - f3) / (d3 - d4);
        return this.f2720c[i4] + ((f3 - (d5 * d4)) * (d2 - d4)) + ((d5 * ((d2 * d2) - (d4 * d4))) / 2.0d);
    }

    public double getSlope(double d2, double d3, double d4) {
        double dB = d3 + b(d2);
        double dA = a(d2) + d4;
        switch (this.f2723f) {
            case 1:
                return 0.0d;
            case 2:
                return dA * 4.0d * Math.signum((((dB * 4.0d) + 3.0d) % 4.0d) - 2.0d);
            case 3:
                return dA * 2.0d;
            case 4:
                return (-dA) * 2.0d;
            case 5:
                double d5 = this.f2724g;
                return (-d5) * dA * Math.sin(d5 * dB);
            case 6:
                return dA * 4.0d * ((((dB * 4.0d) + 2.0d) % 4.0d) - 2.0d);
            case 7:
                return this.f2722e.getSlope(dB % 1.0d, 0);
            default:
                double d6 = this.f2724g;
                return dA * d6 * Math.cos(d6 * dB);
        }
    }

    public double getValue(double d2, double d3) {
        double dAbs;
        double dB = b(d2) + d3;
        switch (this.f2723f) {
            case 1:
                return Math.signum(0.5d - (dB % 1.0d));
            case 2:
                dAbs = Math.abs((((dB * 4.0d) + 1.0d) % 4.0d) - 2.0d);
                break;
            case 3:
                return (((dB * 2.0d) + 1.0d) % 2.0d) - 1.0d;
            case 4:
                dAbs = ((dB * 2.0d) + 1.0d) % 2.0d;
                break;
            case 5:
                return Math.cos(this.f2724g * (d3 + dB));
            case 6:
                double dAbs2 = 1.0d - Math.abs(((dB * 4.0d) % 4.0d) - 2.0d);
                dAbs = dAbs2 * dAbs2;
                break;
            case 7:
                return this.f2722e.getPos(dB % 1.0d, 0);
            default:
                return Math.sin(this.f2724g * dB);
        }
        return 1.0d - dAbs;
    }

    public void normalize() {
        double d2 = 0.0d;
        int i2 = 0;
        while (true) {
            if (i2 >= this.f2718a.length) {
                break;
            }
            d2 += r7[i2];
            i2++;
        }
        double d3 = 0.0d;
        int i3 = 1;
        while (true) {
            float[] fArr = this.f2718a;
            if (i3 >= fArr.length) {
                break;
            }
            int i4 = i3 - 1;
            float f2 = (fArr[i4] + fArr[i3]) / 2.0f;
            double[] dArr = this.f2719b;
            d3 += (dArr[i3] - dArr[i4]) * f2;
            i3++;
        }
        int i5 = 0;
        while (true) {
            float[] fArr2 = this.f2718a;
            if (i5 >= fArr2.length) {
                break;
            }
            fArr2[i5] = (float) (fArr2[i5] * (d2 / d3));
            i5++;
        }
        this.f2720c[0] = 0.0d;
        int i6 = 1;
        while (true) {
            float[] fArr3 = this.f2718a;
            if (i6 >= fArr3.length) {
                this.mNormalized = true;
                return;
            }
            int i7 = i6 - 1;
            float f3 = (fArr3[i7] + fArr3[i6]) / 2.0f;
            double[] dArr2 = this.f2719b;
            double d4 = dArr2[i6] - dArr2[i7];
            double[] dArr3 = this.f2720c;
            dArr3[i6] = dArr3[i7] + (d4 * f3);
            i6++;
        }
    }

    public void setType(int i2, String str) {
        this.f2723f = i2;
        this.f2721d = str;
        if (str != null) {
            this.f2722e = MonotonicCurveFit.buildWave(str);
        }
    }

    public String toString() {
        return "pos =" + Arrays.toString(this.f2719b) + " period=" + Arrays.toString(this.f2718a);
    }
}
