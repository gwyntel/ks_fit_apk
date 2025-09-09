package com.afollestad.materialdialogs.util;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.ArrayRes;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

/* loaded from: classes2.dex */
public class DialogUtils {

    /* renamed from: com.afollestad.materialdialogs.util.DialogUtils$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f8258a;

        static {
            int[] iArr = new int[GravityEnum.values().length];
            f8258a = iArr;
            try {
                iArr[GravityEnum.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f8258a[GravityEnum.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @ColorInt
    public static int adjustAlpha(@ColorInt int i2, float f2) {
        return Color.argb(Math.round(Color.alpha(i2) * f2), Color.red(i2), Color.green(i2), Color.blue(i2));
    }

    public static ColorStateList getActionTextColorStateList(Context context, @ColorRes int i2) throws Resources.NotFoundException {
        TypedValue typedValue = new TypedValue();
        context.getResources().getValue(i2, typedValue, true);
        int i3 = typedValue.type;
        return (i3 < 28 || i3 > 31) ? context.getColorStateList(i2) : getActionTextStateList(context, typedValue.data);
    }

    public static ColorStateList getActionTextStateList(Context context, int i2) {
        int iResolveColor = resolveColor(context, R.attr.textColorPrimary);
        if (i2 == 0) {
            i2 = iResolveColor;
        }
        return new ColorStateList(new int[][]{new int[]{-16842910}, new int[0]}, new int[]{adjustAlpha(i2, 0.4f), i2});
    }

    @ColorInt
    public static int getColor(Context context, @ColorRes int i2) {
        return ContextCompat.getColor(context, i2);
    }

    public static int[] getColorArray(@NonNull Context context, @ArrayRes int i2) throws Resources.NotFoundException {
        if (i2 == 0) {
            return null;
        }
        TypedArray typedArrayObtainTypedArray = context.getResources().obtainTypedArray(i2);
        int[] iArr = new int[typedArrayObtainTypedArray.length()];
        for (int i3 = 0; i3 < typedArrayObtainTypedArray.length(); i3++) {
            iArr[i3] = typedArrayObtainTypedArray.getColor(i3, 0);
        }
        typedArrayObtainTypedArray.recycle();
        return iArr;
    }

    @ColorInt
    public static int getDisabledColor(Context context) {
        return adjustAlpha(isColorDark(resolveColor(context, R.attr.textColorPrimary)) ? ViewCompat.MEASURED_STATE_MASK : -1, 0.3f);
    }

    private static int gravityEnumToAttrInt(GravityEnum gravityEnum) {
        int i2 = AnonymousClass2.f8258a[gravityEnum.ordinal()];
        int i3 = 1;
        if (i2 != 1) {
            i3 = 2;
            if (i2 != 2) {
                return 0;
            }
        }
        return i3;
    }

    public static void hideKeyboard(@NonNull DialogInterface dialogInterface, @NonNull MaterialDialog.Builder builder) {
        InputMethodManager inputMethodManager;
        MaterialDialog materialDialog = (MaterialDialog) dialogInterface;
        if (materialDialog.getInputEditText() == null || (inputMethodManager = (InputMethodManager) builder.getContext().getSystemService("input_method")) == null) {
            return;
        }
        View currentFocus = materialDialog.getCurrentFocus();
        IBinder windowToken = currentFocus != null ? currentFocus.getWindowToken() : materialDialog.getView() != null ? materialDialog.getView().getWindowToken() : null;
        if (windowToken != null) {
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    public static boolean isColorDark(@ColorInt int i2) {
        return 1.0d - ((((((double) Color.red(i2)) * 0.299d) + (((double) Color.green(i2)) * 0.587d)) + (((double) Color.blue(i2)) * 0.114d)) / 255.0d) >= 0.5d;
    }

    public static <T> boolean isIn(@NonNull T t2, @Nullable T[] tArr) {
        if (tArr != null && tArr.length != 0) {
            for (T t3 : tArr) {
                if (t3.equals(t2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ColorStateList resolveActionTextColorStateList(Context context, @AttrRes int i2, ColorStateList colorStateList) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(0);
            if (typedValuePeekValue == null) {
                return colorStateList;
            }
            int i3 = typedValuePeekValue.type;
            if (i3 >= 28 && i3 <= 31) {
                return getActionTextStateList(context, typedValuePeekValue.data);
            }
            ColorStateList colorStateList2 = typedArrayObtainStyledAttributes.getColorStateList(0);
            return colorStateList2 != null ? colorStateList2 : colorStateList;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static boolean resolveBoolean(Context context, @AttrRes int i2, boolean z2) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            return typedArrayObtainStyledAttributes.getBoolean(0, z2);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @ColorInt
    public static int resolveColor(Context context, @AttrRes int i2) {
        return resolveColor(context, i2, 0);
    }

    public static int resolveDimension(Context context, @AttrRes int i2) {
        return resolveDimension(context, i2, -1);
    }

    public static Drawable resolveDrawable(Context context, @AttrRes int i2) {
        return resolveDrawable(context, i2, null);
    }

    public static GravityEnum resolveGravityEnum(Context context, @AttrRes int i2, GravityEnum gravityEnum) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            int i3 = typedArrayObtainStyledAttributes.getInt(0, gravityEnumToAttrInt(gravityEnum));
            return i3 != 1 ? i3 != 2 ? GravityEnum.START : GravityEnum.END : GravityEnum.CENTER;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static String resolveString(Context context, @AttrRes int i2) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i2, typedValue, true);
        return (String) typedValue.string;
    }

    public static void setBackgroundCompat(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

    public static void showKeyboard(@NonNull DialogInterface dialogInterface, @NonNull final MaterialDialog.Builder builder) {
        final MaterialDialog materialDialog = (MaterialDialog) dialogInterface;
        if (materialDialog.getInputEditText() == null) {
            return;
        }
        materialDialog.getInputEditText().post(new Runnable() { // from class: com.afollestad.materialdialogs.util.DialogUtils.1
            @Override // java.lang.Runnable
            public void run() {
                materialDialog.getInputEditText().requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) builder.getContext().getSystemService("input_method");
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(materialDialog.getInputEditText(), 1);
                }
            }
        });
    }

    @ColorInt
    public static int resolveColor(Context context, @AttrRes int i2, int i3) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            return typedArrayObtainStyledAttributes.getColor(0, i3);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private static int resolveDimension(Context context, @AttrRes int i2, int i3) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            return typedArrayObtainStyledAttributes.getDimensionPixelSize(0, i3);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private static Drawable resolveDrawable(Context context, @AttrRes int i2, Drawable drawable) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{i2});
        try {
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(0);
            if (drawable2 != null || drawable == null) {
                drawable = drawable2;
            }
            return drawable;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static boolean resolveBoolean(Context context, @AttrRes int i2) {
        return resolveBoolean(context, i2, false);
    }
}
