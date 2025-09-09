package androidx.transition;

import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
class GhostViewUtils {
    private GhostViewUtils() {
    }

    static GhostView a(View view, ViewGroup viewGroup, Matrix matrix) {
        return Build.VERSION.SDK_INT == 28 ? GhostViewPlatform.a(view, viewGroup, matrix) : GhostViewPort.a(view, viewGroup, matrix);
    }

    static void b(View view) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT == 28) {
            GhostViewPlatform.b(view);
        } else {
            GhostViewPort.e(view);
        }
    }
}
