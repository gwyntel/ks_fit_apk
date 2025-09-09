package com.facebook.appevents.ml;

/* loaded from: classes3.dex */
final class Operator {
    Operator() {
    }

    static float[] add(float[] fArr, float[] fArr2, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < i2 * i3; i5++) {
            for (int i6 = 0; i6 < i4; i6++) {
                int i7 = (i5 * i4) + i6;
                fArr[i7] = fArr[i7] + fArr2[i6];
            }
        }
        return fArr;
    }

    static float[] concatenate(float[] fArr, float[] fArr2) {
        float[] fArr3 = new float[fArr.length + fArr2.length];
        System.arraycopy(fArr, 0, fArr3, 0, fArr.length);
        System.arraycopy(fArr2, 0, fArr3, fArr.length, fArr2.length);
        return fArr3;
    }

    static float[] conv1D(float[] fArr, float[] fArr2, int i2, int i3, int i4, int i5, int i6) {
        int i7 = (i3 - i5) + 1;
        float[] fArr3 = new float[i2 * i7 * i6];
        for (int i8 = 0; i8 < i2; i8++) {
            for (int i9 = 0; i9 < i6; i9++) {
                for (int i10 = 0; i10 < i7; i10++) {
                    float f2 = 0.0f;
                    for (int i11 = 0; i11 < i5; i11++) {
                        for (int i12 = 0; i12 < i4; i12++) {
                            f2 += fArr[(i3 * i4 * i8) + ((i11 + i10) * i4) + i12] * fArr2[(((i11 * i4) + i12) * i6) + i9];
                        }
                    }
                    fArr3[(i6 * i7 * i8) + (i10 * i6) + i9] = f2;
                }
            }
        }
        return fArr3;
    }

    static float[] dense(float[] fArr, float[] fArr2, float[] fArr3, int i2, int i3, int i4) {
        float[] fArrMul = mul(fArr, fArr2, i2, i3, i4);
        for (int i5 = 0; i5 < i2; i5++) {
            for (int i6 = 0; i6 < i4; i6++) {
                int i7 = (i5 * i4) + i6;
                fArrMul[i7] = fArrMul[i7] + fArr3[i6];
            }
        }
        return fArrMul;
    }

    static float[] embedding(int[] iArr, float[] fArr, int i2, int i3, int i4) {
        float[] fArr2 = new float[i2 * i3 * i4];
        for (int i5 = 0; i5 < i2; i5++) {
            for (int i6 = 0; i6 < i3; i6++) {
                int i7 = iArr[(i5 * i3) + i6];
                for (int i8 = 0; i8 < i4; i8++) {
                    fArr2[(i4 * i3 * i5) + (i4 * i6) + i8] = fArr[(i7 * i4) + i8];
                }
            }
        }
        return fArr2;
    }

    static float[] maxPool1D(float[] fArr, int i2, int i3, int i4) {
        int i5 = (i2 - i4) + 1;
        float[] fArr2 = new float[i5 * i3];
        for (int i6 = 0; i6 < i3; i6++) {
            for (int i7 = 0; i7 < i5; i7++) {
                for (int i8 = i7; i8 < i7 + i4; i8++) {
                    int i9 = (i7 * i3) + i6;
                    fArr2[i9] = Math.max(fArr2[i9], fArr[(i8 * i3) + i6]);
                }
            }
        }
        return fArr2;
    }

    static float[] mul(float[] fArr, float[] fArr2, int i2, int i3, int i4) {
        float[] fArr3 = new float[i2 * i4];
        for (int i5 = 0; i5 < i2; i5++) {
            for (int i6 = 0; i6 < i4; i6++) {
                int i7 = (i5 * i4) + i6;
                fArr3[i7] = 0.0f;
                for (int i8 = 0; i8 < i3; i8++) {
                    fArr3[i7] = fArr3[i7] + (fArr[(i5 * i3) + i8] * fArr2[(i8 * i4) + i6]);
                }
            }
        }
        return fArr3;
    }

    static void relu(float[] fArr, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (fArr[i3] < 0.0f) {
                fArr[i3] = 0.0f;
            }
        }
    }

    static void softmax(float[] fArr, int i2) {
        float f2 = Float.MIN_VALUE;
        for (int i3 = 0; i3 < i2; i3++) {
            float f3 = fArr[i3];
            if (f3 > f2) {
                f2 = f3;
            }
        }
        for (int i4 = 0; i4 < i2; i4++) {
            fArr[i4] = (float) Math.exp(fArr[i4] - f2);
        }
        float f4 = 0.0f;
        for (int i5 = 0; i5 < i2; i5++) {
            f4 += fArr[i5];
        }
        for (int i6 = 0; i6 < i2; i6++) {
            fArr[i6] = fArr[i6] / f4;
        }
    }

    static float[] transpose2D(float[] fArr, int i2, int i3) {
        float[] fArr2 = new float[i2 * i3];
        for (int i4 = 0; i4 < i2; i4++) {
            for (int i5 = 0; i5 < i3; i5++) {
                fArr2[(i5 * i2) + i4] = fArr[(i4 * i3) + i5];
            }
        }
        return fArr2;
    }

    static float[] transpose3D(float[] fArr, int i2, int i3, int i4) {
        float[] fArr2 = new float[i2 * i3 * i4];
        for (int i5 = 0; i5 < i2; i5++) {
            for (int i6 = 0; i6 < i3; i6++) {
                for (int i7 = 0; i7 < i4; i7++) {
                    fArr2[(i7 * i2 * i3) + (i6 * i2) + i5] = fArr[(i5 * i3 * i4) + (i6 * i4) + i7];
                }
            }
        }
        return fArr2;
    }
}
