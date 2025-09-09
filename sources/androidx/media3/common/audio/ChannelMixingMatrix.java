package androidx.media3.common.audio;

import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public final class ChannelMixingMatrix {
    private final float[] coefficients;
    private final int inputChannelCount;
    private final boolean isDiagonal;
    private final boolean isIdentity;
    private final boolean isZero;
    private final int outputChannelCount;

    public ChannelMixingMatrix(int i2, int i3, float[] fArr) {
        boolean z2 = false;
        Assertions.checkArgument(i2 > 0, "Input channel count must be positive.");
        Assertions.checkArgument(i3 > 0, "Output channel count must be positive.");
        Assertions.checkArgument(fArr.length == i2 * i3, "Coefficient array length is invalid.");
        this.inputChannelCount = i2;
        this.outputChannelCount = i3;
        this.coefficients = checkCoefficientsValid(fArr);
        int i4 = 0;
        boolean z3 = true;
        boolean z4 = true;
        boolean z5 = true;
        while (i4 < i2) {
            int i5 = 0;
            while (i5 < i3) {
                float mixingCoefficient = getMixingCoefficient(i4, i5);
                boolean z6 = i4 == i5;
                if (mixingCoefficient != 1.0f && z6) {
                    z5 = false;
                }
                if (mixingCoefficient != 0.0f) {
                    z3 = false;
                    if (!z6) {
                        z4 = false;
                    }
                }
                i5++;
            }
            i4++;
        }
        this.isZero = z3;
        boolean z7 = isSquare() && z4;
        this.isDiagonal = z7;
        if (z7 && z5) {
            z2 = true;
        }
        this.isIdentity = z2;
    }

    private static float[] checkCoefficientsValid(float[] fArr) {
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (fArr[i2] < 0.0f) {
                throw new IllegalArgumentException("Coefficient at index " + i2 + " is negative.");
            }
        }
        return fArr;
    }

    public static ChannelMixingMatrix create(int i2, int i3) {
        return new ChannelMixingMatrix(i2, i3, createMixingCoefficients(i2, i3));
    }

    private static float[] createMixingCoefficients(int i2, int i3) {
        if (i2 == i3) {
            return initializeIdentityMatrix(i3);
        }
        if (i2 == 1 && i3 == 2) {
            return new float[]{1.0f, 1.0f};
        }
        if (i2 == 2 && i3 == 1) {
            return new float[]{0.5f, 0.5f};
        }
        throw new UnsupportedOperationException("Default channel mixing coefficients for " + i2 + "->" + i3 + " are not yet implemented.");
    }

    private static float[] initializeIdentityMatrix(int i2) {
        float[] fArr = new float[i2 * i2];
        for (int i3 = 0; i3 < i2; i3++) {
            fArr[(i2 * i3) + i3] = 1.0f;
        }
        return fArr;
    }

    public int getInputChannelCount() {
        return this.inputChannelCount;
    }

    public float getMixingCoefficient(int i2, int i3) {
        return this.coefficients[(i2 * this.outputChannelCount) + i3];
    }

    public int getOutputChannelCount() {
        return this.outputChannelCount;
    }

    public boolean isDiagonal() {
        return this.isDiagonal;
    }

    public boolean isIdentity() {
        return this.isIdentity;
    }

    public boolean isSquare() {
        return this.inputChannelCount == this.outputChannelCount;
    }

    public boolean isZero() {
        return this.isZero;
    }

    public ChannelMixingMatrix scaleBy(float f2) {
        float[] fArr = new float[this.coefficients.length];
        int i2 = 0;
        while (true) {
            float[] fArr2 = this.coefficients;
            if (i2 >= fArr2.length) {
                return new ChannelMixingMatrix(this.inputChannelCount, this.outputChannelCount, fArr);
            }
            fArr[i2] = fArr2[i2] * f2;
            i2++;
        }
    }
}
