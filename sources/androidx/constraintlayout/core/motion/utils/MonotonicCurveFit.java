package androidx.constraintlayout.core.motion.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes.dex */
public class MonotonicCurveFit extends CurveFit {
    private static final String TAG = "MonotonicCurveFit";

    /* renamed from: a, reason: collision with root package name */
    double[] f2717a;
    private boolean mExtrapolate = true;
    private double[] mT;
    private double[][] mTangent;
    private double[][] mY;

    public MonotonicCurveFit(double[] dArr, double[][] dArr2) {
        int length = dArr.length;
        int length2 = dArr2[0].length;
        this.f2717a = new double[length2];
        int i2 = length - 1;
        Class cls = Double.TYPE;
        double[][] dArr3 = (double[][]) Array.newInstance((Class<?>) cls, i2, length2);
        double[][] dArr4 = (double[][]) Array.newInstance((Class<?>) cls, length, length2);
        for (int i3 = 0; i3 < length2; i3++) {
            int i4 = 0;
            while (i4 < i2) {
                int i5 = i4 + 1;
                double d2 = dArr[i5] - dArr[i4];
                double[] dArr5 = dArr3[i4];
                double d3 = (dArr2[i5][i3] - dArr2[i4][i3]) / d2;
                dArr5[i3] = d3;
                if (i4 == 0) {
                    dArr4[i4][i3] = d3;
                } else {
                    dArr4[i4][i3] = (dArr3[i4 - 1][i3] + d3) * 0.5d;
                }
                i4 = i5;
            }
            dArr4[i2][i3] = dArr3[length - 2][i3];
        }
        for (int i6 = 0; i6 < i2; i6++) {
            for (int i7 = 0; i7 < length2; i7++) {
                double d4 = dArr3[i6][i7];
                if (d4 == 0.0d) {
                    dArr4[i6][i7] = 0.0d;
                    dArr4[i6 + 1][i7] = 0.0d;
                } else {
                    double d5 = dArr4[i6][i7] / d4;
                    int i8 = i6 + 1;
                    double d6 = dArr4[i8][i7] / d4;
                    double dHypot = Math.hypot(d5, d6);
                    if (dHypot > 9.0d) {
                        double d7 = 3.0d / dHypot;
                        double[] dArr6 = dArr4[i6];
                        double[] dArr7 = dArr3[i6];
                        dArr6[i7] = d5 * d7 * dArr7[i7];
                        dArr4[i8][i7] = d7 * d6 * dArr7[i7];
                    }
                }
            }
        }
        this.mT = dArr;
        this.mY = dArr2;
        this.mTangent = dArr4;
    }

    public static MonotonicCurveFit buildWave(String str) {
        double[] dArr = new double[str.length() / 2];
        int iIndexOf = str.indexOf(40) + 1;
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        int i2 = 0;
        while (iIndexOf2 != -1) {
            dArr[i2] = Double.parseDouble(str.substring(iIndexOf, iIndexOf2).trim());
            iIndexOf = iIndexOf2 + 1;
            iIndexOf2 = str.indexOf(44, iIndexOf);
            i2++;
        }
        dArr[i2] = Double.parseDouble(str.substring(iIndexOf, str.indexOf(41, iIndexOf)).trim());
        return buildWave(Arrays.copyOf(dArr, i2 + 1));
    }

    private static double diff(double d2, double d3, double d4, double d5, double d6, double d7) {
        double d8 = d3 * d3;
        double d9 = d3 * 6.0d;
        double d10 = 3.0d * d2;
        return ((((((((((-6.0d) * d8) * d5) + (d9 * d5)) + ((6.0d * d8) * d4)) - (d9 * d4)) + ((d10 * d7) * d8)) + ((d10 * d6) * d8)) - (((2.0d * d2) * d7) * d3)) - (((4.0d * d2) * d6) * d3)) + (d2 * d6);
    }

    private static double interpolate(double d2, double d3, double d4, double d5, double d6, double d7) {
        double d8 = d3 * d3;
        double d9 = d8 * d3;
        double d10 = 3.0d * d8;
        double d11 = ((((((-2.0d) * d9) * d5) + (d10 * d5)) + ((d9 * 2.0d) * d4)) - (d10 * d4)) + d4;
        double d12 = d2 * d7;
        double d13 = d2 * d6;
        return ((((d11 + (d12 * d9)) + (d9 * d13)) - (d12 * d8)) - (((d2 * 2.0d) * d6) * d8)) + (d13 * d3);
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double d2, double[] dArr) {
        double[] dArr2 = this.mT;
        int length = dArr2.length;
        int i2 = 0;
        int length2 = this.mY[0].length;
        if (this.mExtrapolate) {
            double d3 = dArr2[0];
            if (d2 <= d3) {
                getSlope(d3, this.f2717a);
                for (int i3 = 0; i3 < length2; i3++) {
                    dArr[i3] = this.mY[0][i3] + ((d2 - this.mT[0]) * this.f2717a[i3]);
                }
                return;
            }
            int i4 = length - 1;
            double d4 = dArr2[i4];
            if (d2 >= d4) {
                getSlope(d4, this.f2717a);
                while (i2 < length2) {
                    dArr[i2] = this.mY[i4][i2] + ((d2 - this.mT[i4]) * this.f2717a[i2]);
                    i2++;
                }
                return;
            }
        } else {
            if (d2 <= dArr2[0]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    dArr[i5] = this.mY[0][i5];
                }
                return;
            }
            int i6 = length - 1;
            if (d2 >= dArr2[i6]) {
                while (i2 < length2) {
                    dArr[i2] = this.mY[i6][i2];
                    i2++;
                }
                return;
            }
        }
        int i7 = 0;
        while (i7 < length - 1) {
            if (d2 == this.mT[i7]) {
                for (int i8 = 0; i8 < length2; i8++) {
                    dArr[i8] = this.mY[i7][i8];
                }
            }
            double[] dArr3 = this.mT;
            int i9 = i7 + 1;
            double d5 = dArr3[i9];
            if (d2 < d5) {
                double d6 = dArr3[i7];
                double d7 = d5 - d6;
                double d8 = (d2 - d6) / d7;
                while (i2 < length2) {
                    double[][] dArr4 = this.mY;
                    double d9 = dArr4[i7][i2];
                    double d10 = dArr4[i9][i2];
                    double[][] dArr5 = this.mTangent;
                    dArr[i2] = interpolate(d7, d8, d9, d10, dArr5[i7][i2], dArr5[i9][i2]);
                    i2++;
                }
                return;
            }
            i7 = i9;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getSlope(double d2, double[] dArr) {
        double[] dArr2 = this.mT;
        int length = dArr2.length;
        int length2 = this.mY[0].length;
        double d3 = dArr2[0];
        if (d2 > d3) {
            d3 = dArr2[length - 1];
            if (d2 < d3) {
                d3 = d2;
            }
        }
        int i2 = 0;
        while (i2 < length - 1) {
            double[] dArr3 = this.mT;
            int i3 = i2 + 1;
            double d4 = dArr3[i3];
            if (d3 <= d4) {
                double d5 = dArr3[i2];
                double d6 = d4 - d5;
                double d7 = (d3 - d5) / d6;
                for (int i4 = 0; i4 < length2; i4++) {
                    double[][] dArr4 = this.mY;
                    double d8 = dArr4[i2][i4];
                    double d9 = dArr4[i3][i4];
                    double[][] dArr5 = this.mTangent;
                    dArr[i4] = diff(d6, d7, d8, d9, dArr5[i2][i4], dArr5[i3][i4]) / d6;
                }
                return;
            }
            i2 = i3;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.mT;
    }

    private static MonotonicCurveFit buildWave(double[] dArr) {
        int length = (dArr.length * 3) - 2;
        int length2 = dArr.length - 1;
        double d2 = 1.0d / length2;
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, length, 1);
        double[] dArr3 = new double[length];
        for (int i2 = 0; i2 < dArr.length; i2++) {
            double d3 = dArr[i2];
            int i3 = i2 + length2;
            dArr2[i3][0] = d3;
            double d4 = i2 * d2;
            dArr3[i3] = d4;
            if (i2 > 0) {
                int i4 = (length2 * 2) + i2;
                dArr2[i4][0] = d3 + 1.0d;
                dArr3[i4] = d4 + 1.0d;
                int i5 = i2 - 1;
                dArr2[i5][0] = (d3 - 1.0d) - d2;
                dArr3[i5] = (d4 - 1.0d) - d2;
            }
        }
        return new MonotonicCurveFit(dArr3, dArr2);
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getSlope(double d2, int i2) {
        double[] dArr = this.mT;
        int length = dArr.length;
        int i3 = 0;
        double d3 = dArr[0];
        if (d2 >= d3) {
            d3 = dArr[length - 1];
            if (d2 < d3) {
                d3 = d2;
            }
        }
        while (i3 < length - 1) {
            double[] dArr2 = this.mT;
            int i4 = i3 + 1;
            double d4 = dArr2[i4];
            if (d3 <= d4) {
                double d5 = dArr2[i3];
                double d6 = d4 - d5;
                double[][] dArr3 = this.mY;
                double d7 = dArr3[i3][i2];
                double d8 = dArr3[i4][i2];
                double[][] dArr4 = this.mTangent;
                return diff(d6, (d3 - d5) / d6, d7, d8, dArr4[i3][i2], dArr4[i4][i2]) / d6;
            }
            i3 = i4;
        }
        return 0.0d;
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public void getPos(double d2, float[] fArr) {
        double[] dArr = this.mT;
        int length = dArr.length;
        int i2 = 0;
        int length2 = this.mY[0].length;
        if (this.mExtrapolate) {
            double d3 = dArr[0];
            if (d2 <= d3) {
                getSlope(d3, this.f2717a);
                for (int i3 = 0; i3 < length2; i3++) {
                    fArr[i3] = (float) (this.mY[0][i3] + ((d2 - this.mT[0]) * this.f2717a[i3]));
                }
                return;
            }
            int i4 = length - 1;
            double d4 = dArr[i4];
            if (d2 >= d4) {
                getSlope(d4, this.f2717a);
                while (i2 < length2) {
                    fArr[i2] = (float) (this.mY[i4][i2] + ((d2 - this.mT[i4]) * this.f2717a[i2]));
                    i2++;
                }
                return;
            }
        } else {
            if (d2 <= dArr[0]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    fArr[i5] = (float) this.mY[0][i5];
                }
                return;
            }
            int i6 = length - 1;
            if (d2 >= dArr[i6]) {
                while (i2 < length2) {
                    fArr[i2] = (float) this.mY[i6][i2];
                    i2++;
                }
                return;
            }
        }
        int i7 = 0;
        while (i7 < length - 1) {
            if (d2 == this.mT[i7]) {
                for (int i8 = 0; i8 < length2; i8++) {
                    fArr[i8] = (float) this.mY[i7][i8];
                }
            }
            double[] dArr2 = this.mT;
            int i9 = i7 + 1;
            double d5 = dArr2[i9];
            if (d2 < d5) {
                double d6 = dArr2[i7];
                double d7 = d5 - d6;
                double d8 = (d2 - d6) / d7;
                while (i2 < length2) {
                    double[][] dArr3 = this.mY;
                    double d9 = dArr3[i7][i2];
                    double d10 = dArr3[i9][i2];
                    double[][] dArr4 = this.mTangent;
                    fArr[i2] = (float) interpolate(d7, d8, d9, d10, dArr4[i7][i2], dArr4[i9][i2]);
                    i2++;
                }
                return;
            }
            i7 = i9;
        }
    }

    @Override // androidx.constraintlayout.core.motion.utils.CurveFit
    public double getPos(double d2, int i2) {
        double d3;
        double d4;
        double slope;
        double[] dArr = this.mT;
        int length = dArr.length;
        int i3 = 0;
        if (this.mExtrapolate) {
            double d5 = dArr[0];
            if (d2 <= d5) {
                d3 = this.mY[0][i2];
                d4 = d2 - d5;
                slope = getSlope(d5, i2);
            } else {
                int i4 = length - 1;
                double d6 = dArr[i4];
                if (d2 >= d6) {
                    d3 = this.mY[i4][i2];
                    d4 = d2 - d6;
                    slope = getSlope(d6, i2);
                }
            }
            return d3 + (d4 * slope);
        }
        if (d2 <= dArr[0]) {
            return this.mY[0][i2];
        }
        int i5 = length - 1;
        if (d2 >= dArr[i5]) {
            return this.mY[i5][i2];
        }
        while (i3 < length - 1) {
            double[] dArr2 = this.mT;
            double d7 = dArr2[i3];
            if (d2 == d7) {
                return this.mY[i3][i2];
            }
            int i6 = i3 + 1;
            double d8 = dArr2[i6];
            if (d2 < d8) {
                double d9 = d8 - d7;
                double d10 = (d2 - d7) / d9;
                double[][] dArr3 = this.mY;
                double d11 = dArr3[i3][i2];
                double d12 = dArr3[i6][i2];
                double[][] dArr4 = this.mTangent;
                return interpolate(d9, d10, d11, d12, dArr4[i3][i2], dArr4[i6][i2]);
            }
            i3 = i6;
        }
        return 0.0d;
    }
}
