package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.BlendModeUtils;

/* loaded from: classes.dex */
public class BlendModeColorFilterCompat {

    @RequiresApi(29)
    static class Api29Impl {
        private Api29Impl() {
        }

        @DoNotInline
        static ColorFilter a(int i2, Object obj) {
            return new BlendModeColorFilter(i2, (BlendMode) obj);
        }
    }

    private BlendModeColorFilterCompat() {
    }

    @Nullable
    public static ColorFilter createBlendModeColorFilterCompat(int i2, @NonNull BlendModeCompat blendModeCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            Object objA = BlendModeUtils.Api29Impl.a(blendModeCompat);
            if (objA != null) {
                return Api29Impl.a(i2, objA);
            }
            return null;
        }
        PorterDuff.Mode modeA = BlendModeUtils.a(blendModeCompat);
        if (modeA != null) {
            return new PorterDuffColorFilter(i2, modeA);
        }
        return null;
    }
}
