package androidx.core.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class TypedValueCompat {
    private static final float INCHES_PER_MM = 0.03937008f;
    private static final float INCHES_PER_PT = 0.013888889f;

    @RequiresApi(34)
    private static class Api34Impl {
        private Api34Impl() {
        }

        @DoNotInline
        public static float deriveDimension(int i2, float f2, DisplayMetrics displayMetrics) {
            return TypedValue.deriveDimension(i2, f2, displayMetrics);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ComplexDimensionUnit {
    }

    private TypedValueCompat() {
    }

    public static float deriveDimension(int i2, float f2, @NonNull DisplayMetrics displayMetrics) {
        float f3;
        float f4;
        if (Build.VERSION.SDK_INT >= 34) {
            return Api34Impl.deriveDimension(i2, f2, displayMetrics);
        }
        if (i2 == 0) {
            return f2;
        }
        if (i2 == 1) {
            float f5 = displayMetrics.density;
            if (f5 == 0.0f) {
                return 0.0f;
            }
            return f2 / f5;
        }
        if (i2 == 2) {
            float f6 = displayMetrics.scaledDensity;
            if (f6 == 0.0f) {
                return 0.0f;
            }
            return f2 / f6;
        }
        if (i2 == 3) {
            float f7 = displayMetrics.xdpi;
            if (f7 == 0.0f) {
                return 0.0f;
            }
            f3 = f2 / f7;
            f4 = INCHES_PER_PT;
        } else {
            if (i2 == 4) {
                float f8 = displayMetrics.xdpi;
                if (f8 == 0.0f) {
                    return 0.0f;
                }
                return f2 / f8;
            }
            if (i2 != 5) {
                throw new IllegalArgumentException("Invalid unitToConvertTo " + i2);
            }
            float f9 = displayMetrics.xdpi;
            if (f9 == 0.0f) {
                return 0.0f;
            }
            f3 = f2 / f9;
            f4 = INCHES_PER_MM;
        }
        return f3 / f4;
    }

    public static float dpToPx(float f2, @NonNull DisplayMetrics displayMetrics) {
        return TypedValue.applyDimension(1, f2, displayMetrics);
    }

    @SuppressLint({"WrongConstant"})
    public static int getUnitFromComplexDimension(int i2) {
        return i2 & 15;
    }

    public static float pxToDp(float f2, @NonNull DisplayMetrics displayMetrics) {
        return deriveDimension(1, f2, displayMetrics);
    }

    public static float pxToSp(float f2, @NonNull DisplayMetrics displayMetrics) {
        return deriveDimension(2, f2, displayMetrics);
    }

    public static float spToPx(float f2, @NonNull DisplayMetrics displayMetrics) {
        return TypedValue.applyDimension(2, f2, displayMetrics);
    }
}
