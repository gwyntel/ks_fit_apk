package com.github.barteksc.pdfviewer.util;

/* loaded from: classes3.dex */
public class MathUtils {
    private static final double BIG_ENOUGH_CEIL = 16384.999999999996d;
    private static final double BIG_ENOUGH_FLOOR = 16384.0d;
    private static final int BIG_ENOUGH_INT = 16384;

    private MathUtils() {
    }

    public static int ceil(float f2) {
        return ((int) (f2 + BIG_ENOUGH_CEIL)) - 16384;
    }

    public static int floor(float f2) {
        return ((int) (f2 + BIG_ENOUGH_FLOOR)) - 16384;
    }

    public static float limit(float f2, float f3, float f4) {
        return f2 <= f3 ? f3 : f2 >= f4 ? f4 : f2;
    }

    public static float max(float f2, float f3) {
        return f2 > f3 ? f3 : f2;
    }

    public static float min(float f2, float f3) {
        return f2 < f3 ? f3 : f2;
    }

    public static int limit(int i2, int i3, int i4) {
        return i2 <= i3 ? i3 : i2 >= i4 ? i4 : i2;
    }

    public static int max(int i2, int i3) {
        return i2 > i3 ? i3 : i2;
    }

    public static int min(int i2, int i3) {
        return i2 < i3 ? i3 : i2;
    }
}
