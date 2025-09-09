package androidx.core.view.animation;

import android.graphics.Path;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class PathInterpolatorCompat {

    @RequiresApi(21)
    static class Api21Impl {
        private Api21Impl() {
        }

        @DoNotInline
        static Interpolator a(float f2, float f3) {
            return new PathInterpolator(f2, f3);
        }

        @DoNotInline
        static Interpolator b(float f2, float f3, float f4, float f5) {
            return new PathInterpolator(f2, f3, f4, f5);
        }

        @DoNotInline
        static Interpolator c(Path path) {
            return new PathInterpolator(path);
        }
    }

    private PathInterpolatorCompat() {
    }

    @NonNull
    public static Interpolator create(@NonNull Path path) {
        return Api21Impl.c(path);
    }

    @NonNull
    public static Interpolator create(float f2, float f3) {
        return Api21Impl.a(f2, f3);
    }

    @NonNull
    public static Interpolator create(float f2, float f3, float f4, float f5) {
        return Api21Impl.b(f2, f3, f4, f5);
    }
}
