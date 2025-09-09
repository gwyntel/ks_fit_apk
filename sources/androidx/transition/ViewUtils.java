package androidx.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.Property;
import android.view.View;
import androidx.core.view.ViewCompat;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
class ViewUtils {
    private static final ViewUtilsBase IMPL;
    private static final String TAG = "ViewUtils";

    /* renamed from: a, reason: collision with root package name */
    static final Property f6354a;

    /* renamed from: b, reason: collision with root package name */
    static final Property f6355b;

    static {
        if (Build.VERSION.SDK_INT >= 29) {
            IMPL = new ViewUtilsApi29();
        } else {
            IMPL = new ViewUtilsApi23();
        }
        f6354a = new Property<View, Float>(Float.class, "translationAlpha") { // from class: androidx.transition.ViewUtils.1
            @Override // android.util.Property
            public Float get(View view) {
                return Float.valueOf(ViewUtils.c(view));
            }

            @Override // android.util.Property
            public void set(View view, Float f2) {
                ViewUtils.h(view, f2.floatValue());
            }
        };
        f6355b = new Property<View, Rect>(Rect.class, "clipBounds") { // from class: androidx.transition.ViewUtils.2
            @Override // android.util.Property
            public Rect get(View view) {
                return ViewCompat.getClipBounds(view);
            }

            @Override // android.util.Property
            public void set(View view, Rect rect) {
                ViewCompat.setClipBounds(view, rect);
            }
        };
    }

    private ViewUtils() {
    }

    static void a(View view) {
        IMPL.clearNonTransitionAlpha(view);
    }

    static ViewOverlayImpl b(View view) {
        return new ViewOverlayApi18(view);
    }

    static float c(View view) {
        return IMPL.getTransitionAlpha(view);
    }

    static WindowIdImpl d(View view) {
        return new WindowIdApi18(view);
    }

    static void e(View view) {
        IMPL.saveNonTransitionAlpha(view);
    }

    static void f(View view, Matrix matrix) {
        IMPL.setAnimationMatrix(view, matrix);
    }

    static void g(View view, int i2, int i3, int i4, int i5) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        IMPL.setLeftTopRightBottom(view, i2, i3, i4, i5);
    }

    static void h(View view, float f2) {
        IMPL.setTransitionAlpha(view, f2);
    }

    static void i(View view, int i2) {
        IMPL.setTransitionVisibility(view, i2);
    }

    static void j(View view, Matrix matrix) {
        IMPL.transformMatrixToGlobal(view, matrix);
    }

    static void k(View view, Matrix matrix) {
        IMPL.transformMatrixToLocal(view, matrix);
    }
}
