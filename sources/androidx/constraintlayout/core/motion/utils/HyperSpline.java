package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class HyperSpline {

    /* renamed from: a, reason: collision with root package name */
    int f2673a;

    /* renamed from: b, reason: collision with root package name */
    Cubic[][] f2674b;

    /* renamed from: c, reason: collision with root package name */
    int f2675c;

    /* renamed from: d, reason: collision with root package name */
    double[] f2676d;

    /* renamed from: e, reason: collision with root package name */
    double f2677e;

    /* renamed from: f, reason: collision with root package name */
    double[][] f2678f;

    public static class Cubic {

        /* renamed from: a, reason: collision with root package name */
        double f2679a;

        /* renamed from: b, reason: collision with root package name */
        double f2680b;

        /* renamed from: c, reason: collision with root package name */
        double f2681c;

        /* renamed from: d, reason: collision with root package name */
        double f2682d;

        public Cubic(double d2, double d3, double d4, double d5) {
            this.f2679a = d2;
            this.f2680b = d3;
            this.f2681c = d4;
            this.f2682d = d5;
        }

        public double eval(double d2) {
            return (((((this.f2682d * d2) + this.f2681c) * d2) + this.f2680b) * d2) + this.f2679a;
        }

        public double vel(double d2) {
            return (((this.f2682d * 3.0d * d2) + (this.f2681c * 2.0d)) * d2) + this.f2680b;
        }
    }

    public HyperSpline(double[][] dArr) {
        setup(dArr);
    }

    static Cubic[] a(int i2, double[] dArr) {
        double[] dArr2 = new double[i2];
        double[] dArr3 = new double[i2];
        double[] dArr4 = new double[i2];
        int i3 = i2 - 1;
        int i4 = 0;
        dArr2[0] = 0.5d;
        int i5 = 1;
        for (int i6 = 1; i6 < i3; i6++) {
            dArr2[i6] = 1.0d / (4.0d - dArr2[i6 - 1]);
        }
        int i7 = i2 - 2;
        dArr2[i3] = 1.0d / (2.0d - dArr2[i7]);
        dArr3[0] = (dArr[1] - dArr[0]) * 3.0d * dArr2[0];
        while (i5 < i3) {
            int i8 = i5 + 1;
            int i9 = i5 - 1;
            dArr3[i5] = (((dArr[i8] - dArr[i9]) * 3.0d) - dArr3[i9]) * dArr2[i5];
            i5 = i8;
        }
        double d2 = (((dArr[i3] - dArr[i7]) * 3.0d) - dArr3[i7]) * dArr2[i3];
        dArr3[i3] = d2;
        dArr4[i3] = d2;
        while (i7 >= 0) {
            dArr4[i7] = dArr3[i7] - (dArr2[i7] * dArr4[i7 + 1]);
            i7--;
        }
        Cubic[] cubicArr = new Cubic[i3];
        while (i4 < i3) {
            double d3 = dArr[i4];
            double d4 = dArr4[i4];
            int i10 = i4 + 1;
            double d5 = dArr[i10];
            double d6 = dArr4[i10];
            cubicArr[i4] = new Cubic((float) d3, d4, (((d5 - d3) * 3.0d) - (d4 * 2.0d)) - d6, ((d3 - d5) * 2.0d) + d4 + d6);
            i4 = i10;
        }
        return cubicArr;
    }

    public double approxLength(Cubic[] cubicArr) {
        int i2;
        int length = cubicArr.length;
        double[] dArr = new double[cubicArr.length];
        double d2 = 0.0d;
        double d3 = 0.0d;
        double dSqrt = 0.0d;
        while (true) {
            i2 = 0;
            if (d3 >= 1.0d) {
                break;
            }
            double d4 = 0.0d;
            while (i2 < cubicArr.length) {
                double d5 = dArr[i2];
                double dEval = cubicArr[i2].eval(d3);
                dArr[i2] = dEval;
                double d6 = d5 - dEval;
                d4 += d6 * d6;
                i2++;
            }
            if (d3 > 0.0d) {
                dSqrt += Math.sqrt(d4);
            }
            d3 += 0.1d;
        }
        while (i2 < cubicArr.length) {
            double d7 = dArr[i2];
            double dEval2 = cubicArr[i2].eval(1.0d);
            dArr[i2] = dEval2;
            double d8 = d7 - dEval2;
            d2 += d8 * d8;
            i2++;
        }
        return dSqrt + Math.sqrt(d2);
    }

    public void getPos(double d2, double[] dArr) {
        double d3 = d2 * this.f2677e;
        int i2 = 0;
        while (true) {
            double[] dArr2 = this.f2676d;
            if (i2 >= dArr2.length - 1) {
                break;
            }
            double d4 = dArr2[i2];
            if (d4 >= d3) {
                break;
            }
            d3 -= d4;
            i2++;
        }
        for (int i3 = 0; i3 < dArr.length; i3++) {
            dArr[i3] = this.f2674b[i3][i2].eval(d3 / this.f2676d[i2]);
        }
    }

    public void getVelocity(double d2, double[] dArr) {
        double d3 = d2 * this.f2677e;
        int i2 = 0;
        while (true) {
            double[] dArr2 = this.f2676d;
            if (i2 >= dArr2.length - 1) {
                break;
            }
            double d4 = dArr2[i2];
            if (d4 >= d3) {
                break;
            }
            d3 -= d4;
            i2++;
        }
        for (int i3 = 0; i3 < dArr.length; i3++) {
            dArr[i3] = this.f2674b[i3][i2].vel(d3 / this.f2676d[i2]);
        }
    }

    public void setup(double[][] dArr) {
        int i2;
        int length = dArr[0].length;
        this.f2675c = length;
        int length2 = dArr.length;
        this.f2673a = length2;
        this.f2678f = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, length2);
        this.f2674b = new Cubic[this.f2675c][];
        for (int i3 = 0; i3 < this.f2675c; i3++) {
            for (int i4 = 0; i4 < this.f2673a; i4++) {
                this.f2678f[i3][i4] = dArr[i4][i3];
            }
        }
        int i5 = 0;
        while (true) {
            i2 = this.f2675c;
            if (i5 >= i2) {
                break;
            }
            Cubic[][] cubicArr = this.f2674b;
            double[] dArr2 = this.f2678f[i5];
            cubicArr[i5] = a(dArr2.length, dArr2);
            i5++;
        }
        this.f2676d = new double[this.f2673a - 1];
        this.f2677e = 0.0d;
        Cubic[] cubicArr2 = new Cubic[i2];
        for (int i6 = 0; i6 < this.f2676d.length; i6++) {
            for (int i7 = 0; i7 < this.f2675c; i7++) {
                cubicArr2[i7] = this.f2674b[i7][i6];
            }
            double d2 = this.f2677e;
            double[] dArr3 = this.f2676d;
            double dApproxLength = approxLength(cubicArr2);
            dArr3[i6] = dApproxLength;
            this.f2677e = d2 + dApproxLength;
        }
    }

    public HyperSpline() {
    }

    public void getPos(double d2, float[] fArr) {
        double d3 = d2 * this.f2677e;
        int i2 = 0;
        while (true) {
            double[] dArr = this.f2676d;
            if (i2 >= dArr.length - 1) {
                break;
            }
            double d4 = dArr[i2];
            if (d4 >= d3) {
                break;
            }
            d3 -= d4;
            i2++;
        }
        for (int i3 = 0; i3 < fArr.length; i3++) {
            fArr[i3] = (float) this.f2674b[i3][i2].eval(d3 / this.f2676d[i2]);
        }
    }

    public double getPos(double d2, int i2) {
        double[] dArr;
        double d3 = d2 * this.f2677e;
        int i3 = 0;
        while (true) {
            dArr = this.f2676d;
            if (i3 >= dArr.length - 1) {
                break;
            }
            double d4 = dArr[i3];
            if (d4 >= d3) {
                break;
            }
            d3 -= d4;
            i3++;
        }
        return this.f2674b[i2][i3].eval(d3 / dArr[i3]);
    }
}
