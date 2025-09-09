package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.ColorUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ThemeUtils {
    private static final String TAG = "ThemeUtils";
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();

    /* renamed from: a, reason: collision with root package name */
    static final int[] f2331a = {-16842910};

    /* renamed from: b, reason: collision with root package name */
    static final int[] f2332b = {R.attr.state_focused};

    /* renamed from: c, reason: collision with root package name */
    static final int[] f2333c = {R.attr.state_activated};

    /* renamed from: d, reason: collision with root package name */
    static final int[] f2334d = {R.attr.state_pressed};

    /* renamed from: e, reason: collision with root package name */
    static final int[] f2335e = {R.attr.state_checked};

    /* renamed from: f, reason: collision with root package name */
    static final int[] f2336f = {R.attr.state_selected};

    /* renamed from: g, reason: collision with root package name */
    static final int[] f2337g = {-16842919, -16842908};

    /* renamed from: h, reason: collision with root package name */
    static final int[] f2338h = new int[0];
    private static final int[] TEMP_ARRAY = new int[1];

    private ThemeUtils() {
    }

    static int a(Context context, int i2, float f2) {
        return ColorUtils.setAlphaComponent(getThemeAttrColor(context, i2), Math.round(Color.alpha(r0) * f2));
    }

    public static void checkAppCompatTheme(@NonNull View view, @NonNull Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme);
        try {
            if (!typedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)) {
                Log.e(TAG, "View " + view.getClass() + " is an AppCompat widget that can only be used with a Theme.AppCompat theme (or descendant).");
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @NonNull
    public static ColorStateList createDisabledStateList(int i2, int i3) {
        return new ColorStateList(new int[][]{f2331a, f2338h}, new int[]{i3, i2});
    }

    public static int getDisabledThemeAttrColor(@NonNull Context context, int i2) {
        ColorStateList themeAttrColorStateList = getThemeAttrColorStateList(context, i2);
        if (themeAttrColorStateList != null && themeAttrColorStateList.isStateful()) {
            return themeAttrColorStateList.getColorForState(f2331a, themeAttrColorStateList.getDefaultColor());
        }
        TypedValue typedValue = getTypedValue();
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        return a(context, i2, typedValue.getFloat());
    }

    public static int getThemeAttrColor(@NonNull Context context, int i2) {
        int[] iArr = TEMP_ARRAY;
        iArr[0] = i2;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, iArr);
        try {
            return tintTypedArrayObtainStyledAttributes.getColor(0, 0);
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    @Nullable
    public static ColorStateList getThemeAttrColorStateList(@NonNull Context context, int i2) {
        int[] iArr = TEMP_ARRAY;
        iArr[0] = i2;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, iArr);
        try {
            return tintTypedArrayObtainStyledAttributes.getColorStateList(0);
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    private static TypedValue getTypedValue() {
        ThreadLocal<TypedValue> threadLocal = TL_TYPED_VALUE;
        TypedValue typedValue = threadLocal.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        threadLocal.set(typedValue2);
        return typedValue2;
    }
}
