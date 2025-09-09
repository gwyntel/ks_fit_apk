package com.huawei.hms.scankit.p;

import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public class i5 {

    /* renamed from: a, reason: collision with root package name */
    private static final int[][] f17392a = {new int[]{1, 1, 1, 1, 1, 1, 1, 0}, new int[]{1, 0, 0, 0, 0, 0, 1, 0}, new int[]{1, 0, 1, 1, 1, 0, 1, 0}, new int[]{1, 0, 1, 1, 1, 0, 1, 0}, new int[]{1, 0, 1, 1, 1, 0, 1, 0}, new int[]{1, 0, 0, 0, 0, 0, 1, 0}, new int[]{1, 1, 1, 1, 1, 1, 1, 0}, new int[]{0, 0, 0, 0, 0, 0, 0, 0}};

    /* renamed from: b, reason: collision with root package name */
    private static final int[][] f17393b = {new int[]{1, 1, 1, 1, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1}, new int[]{1, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1}};

    public static s a(s sVar, s sVar2, int i2, double[][] dArr) {
        int i3;
        if (i2 == 21) {
            int i4 = 0;
            for (int i5 = 0; i5 < i2; i5++) {
                for (int i6 = 0; i6 < i2; i6++) {
                    if (i6 >= 8 || i5 >= 8) {
                        int i7 = i2 - 8;
                        if (i6 < i7 || i5 >= 8) {
                            if (i6 >= 8 || i5 < i7) {
                                double[] dArrA = a((float) dArr[0][i4], (float) dArr[1][i4]);
                                if (sVar2.b(Math.round((float) dArrA[0]), Math.round((float) dArrA[1]))) {
                                    sVar.c(i6, i5);
                                }
                                i4++;
                            } else if (f17392a[i6][(i2 - 1) - i5] == 1) {
                                sVar.c(i6, i5);
                            }
                        } else if (f17392a[i5][(i2 - 1) - i6] == 1) {
                            sVar.c(i6, i5);
                        }
                    } else if (f17392a[i5][i6] == 1) {
                        sVar.c(i6, i5);
                    }
                }
            }
        } else {
            int i8 = 0;
            for (int i9 = 0; i9 < i2; i9++) {
                for (int i10 = 0; i10 < i2; i10++) {
                    if (i10 >= 8 || i9 >= 8) {
                        int i11 = i2 - 8;
                        if (i10 < i11 || i9 >= 8) {
                            if (i10 >= 8 || i9 < i11) {
                                int i12 = i2 - 9;
                                if (i10 < i12 || i10 >= i2 - 4 || i9 < i12 || i9 >= i3) {
                                    double[] dArrA2 = a((float) dArr[0][i8], (float) dArr[1][i8]);
                                    if (sVar2.b(Math.round((float) dArrA2[0]), Math.round((float) dArrA2[1]))) {
                                        sVar.c(i10, i9);
                                    }
                                    i8++;
                                } else {
                                    int i13 = -i12;
                                    if (f17393b[i13 + i9][i13 + i10] == 1) {
                                        sVar.c(i10, i9);
                                    }
                                }
                            } else if (f17392a[i10][(i2 - 1) - i9] == 1) {
                                sVar.c(i10, i9);
                            }
                        } else if (f17392a[i9][(i2 - 1) - i10] == 1) {
                            sVar.c(i10, i9);
                        }
                    } else if (f17392a[i9][i10] == 1) {
                        sVar.c(i10, i9);
                    }
                }
            }
        }
        return sVar;
    }

    public static double[][] a(double d2, double[] dArr, String str) {
        int iA = a(str);
        String[] strArrSplit = str.split(com.alipay.sdk.m.u.i.f9802b);
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 2, iA);
        int i2 = 0;
        for (int i3 = 0; i3 < strArrSplit.length; i3++) {
            double[] dArrA = a(strArrSplit[i3].split(","));
            for (int i4 = 0; i4 < dArrA.length; i4++) {
                int i5 = i3 / 2;
                dArr2[0][i2] = d2 - (dArr[i5] * Math.cos(dArrA[i4]));
                dArr2[1][i2] = d2 - (dArr[i5] * Math.sin(dArrA[i4]));
                i2++;
            }
        }
        return dArr2;
    }

    private static double[] a(float f2, float f3) {
        double[] dArr = {0.0d, 0.0d};
        dArr[0] = f2;
        dArr[1] = f3;
        return dArr;
    }

    public static StringBuffer a(double[] dArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (double d2 : dArr) {
            stringBuffer.append(d2);
            stringBuffer.append(",");
        }
        stringBuffer.append(com.alipay.sdk.m.u.i.f9802b);
        return stringBuffer;
    }

    private static double[] a(String[] strArr) {
        int length = strArr.length;
        double[] dArr = new double[length];
        for (int i2 = 0; i2 < length; i2++) {
            dArr[i2] = Double.valueOf(strArr[i2]).doubleValue();
        }
        return dArr;
    }

    private static int a(String str) {
        int length = 0;
        for (String str2 : str.split(com.alipay.sdk.m.u.i.f9802b)) {
            length += str2.split(",").length;
        }
        return length;
    }
}
