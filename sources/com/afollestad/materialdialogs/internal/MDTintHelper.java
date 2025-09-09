package com.afollestad.materialdialogs.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import com.afollestad.materialdialogs.R;
import com.afollestad.materialdialogs.util.DialogUtils;
import java.lang.reflect.Field;

@SuppressLint({"PrivateResource"})
/* loaded from: classes2.dex */
public class MDTintHelper {
    private static ColorStateList createEditTextColorStateList(@NonNull Context context, @ColorInt int i2) {
        return new ColorStateList(new int[][]{new int[]{-16842910}, new int[]{-16842919, -16842908}, new int[0]}, new int[]{DialogUtils.resolveColor(context, R.attr.colorControlNormal), DialogUtils.resolveColor(context, R.attr.colorControlNormal), i2});
    }

    private static void setCursorTint(@NonNull EditText editText, @ColorInt int i2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField.setAccessible(true);
            int i3 = declaredField.getInt(editText);
            Field declaredField2 = TextView.class.getDeclaredField("mEditor");
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get(editText);
            Field declaredField3 = obj.getClass().getDeclaredField("mCursorDrawable");
            declaredField3.setAccessible(true);
            Drawable[] drawableArr = {ContextCompat.getDrawable(editText.getContext(), i3), ContextCompat.getDrawable(editText.getContext(), i3)};
            Drawable drawable = drawableArr[0];
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            drawable.setColorFilter(i2, mode);
            drawableArr[1].setColorFilter(i2, mode);
            declaredField3.set(obj, drawableArr);
        } catch (NoSuchFieldException e2) {
            Log.d("MDTintHelper", "Device issue with cursor tinting: " + e2.getMessage());
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public static void setTint(@NonNull RadioButton radioButton, @NonNull ColorStateList colorStateList) {
        radioButton.setButtonTintList(colorStateList);
    }

    public static void setTint(@NonNull RadioButton radioButton, @ColorInt int i2) {
        int disabledColor = DialogUtils.getDisabledColor(radioButton.getContext());
        setTint(radioButton, new ColorStateList(new int[][]{new int[]{android.R.attr.state_enabled, -16842912}, new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}, new int[]{-16842910, -16842912}, new int[]{-16842910, android.R.attr.state_checked}}, new int[]{DialogUtils.resolveColor(radioButton.getContext(), R.attr.colorControlNormal), i2, disabledColor, disabledColor}));
    }

    public static void setTint(@NonNull CheckBox checkBox, @NonNull ColorStateList colorStateList) {
        checkBox.setButtonTintList(colorStateList);
    }

    public static void setTint(@NonNull CheckBox checkBox, @ColorInt int i2) {
        int disabledColor = DialogUtils.getDisabledColor(checkBox.getContext());
        setTint(checkBox, new ColorStateList(new int[][]{new int[]{android.R.attr.state_enabled, -16842912}, new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}, new int[]{-16842910, -16842912}, new int[]{-16842910, android.R.attr.state_checked}}, new int[]{DialogUtils.resolveColor(checkBox.getContext(), R.attr.colorControlNormal), i2, disabledColor, disabledColor}));
    }

    public static void setTint(@NonNull SeekBar seekBar, @ColorInt int i2) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i2);
        seekBar.setThumbTintList(colorStateListValueOf);
        seekBar.setProgressTintList(colorStateListValueOf);
    }

    public static void setTint(@NonNull ProgressBar progressBar, @ColorInt int i2) {
        setTint(progressBar, i2, false);
    }

    private static void setTint(@NonNull ProgressBar progressBar, @ColorInt int i2, boolean z2) {
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i2);
        progressBar.setProgressTintList(colorStateListValueOf);
        progressBar.setSecondaryProgressTintList(colorStateListValueOf);
        if (z2) {
            return;
        }
        progressBar.setIndeterminateTintList(colorStateListValueOf);
    }

    public static void setTint(@NonNull EditText editText, @ColorInt int i2) {
        ColorStateList colorStateListCreateEditTextColorStateList = createEditTextColorStateList(editText.getContext(), i2);
        if (editText instanceof AppCompatEditText) {
            ((AppCompatEditText) editText).setSupportBackgroundTintList(colorStateListCreateEditTextColorStateList);
        } else {
            editText.setBackgroundTintList(colorStateListCreateEditTextColorStateList);
        }
        setCursorTint(editText, i2);
    }
}
