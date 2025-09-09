package org.apache.commons.lang.math;

/* loaded from: classes5.dex */
public class IEEE754rUtils {
    public static double max(double[] dArr) {
        if (dArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (dArr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double dMax = dArr[0];
        for (int i2 = 1; i2 < dArr.length; i2++) {
            dMax = max(dArr[i2], dMax);
        }
        return dMax;
    }

    public static double min(double[] dArr) {
        if (dArr == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (dArr.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double dMin = dArr[0];
        for (int i2 = 1; i2 < dArr.length; i2++) {
            dMin = min(dArr[i2], dMin);
        }
        return dMin;
    }

    public static float max(float[] fArr) {
        if (fArr != null) {
            if (fArr.length != 0) {
                float fMax = fArr[0];
                for (int i2 = 1; i2 < fArr.length; i2++) {
                    fMax = max(fArr[i2], fMax);
                }
                return fMax;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static float min(float[] fArr) {
        if (fArr != null) {
            if (fArr.length != 0) {
                float fMin = fArr[0];
                for (int i2 = 1; i2 < fArr.length; i2++) {
                    fMin = min(fArr[i2], fMin);
                }
                return fMin;
            }
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static double max(double d2, double d3, double d4) {
        return max(max(d2, d3), d4);
    }

    public static double min(double d2, double d3, double d4) {
        return min(min(d2, d3), d4);
    }

    public static double max(double d2, double d3) {
        return Double.isNaN(d2) ? d3 : Double.isNaN(d3) ? d2 : Math.max(d2, d3);
    }

    public static double min(double d2, double d3) {
        return Double.isNaN(d2) ? d3 : Double.isNaN(d3) ? d2 : Math.min(d2, d3);
    }

    public static float max(float f2, float f3, float f4) {
        return max(max(f2, f3), f4);
    }

    public static float min(float f2, float f3, float f4) {
        return min(min(f2, f3), f4);
    }

    public static float max(float f2, float f3) {
        return Float.isNaN(f2) ? f3 : Float.isNaN(f3) ? f2 : Math.max(f2, f3);
    }

    public static float min(float f2, float f3) {
        return Float.isNaN(f2) ? f3 : Float.isNaN(f3) ? f2 : Math.min(f2, f3);
    }
}
