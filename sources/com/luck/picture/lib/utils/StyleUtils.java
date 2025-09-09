package com.luck.picture.lib.utils;

import android.content.Context;
import android.graphics.ColorFilter;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class StyleUtils {
    private static final int INVALID = 0;

    public static boolean checkArrayValidity(int[] iArr) {
        return iArr != null && iArr.length > 0;
    }

    public static boolean checkSizeValidity(int i2) {
        return i2 > 0;
    }

    public static boolean checkStyleValidity(int i2) {
        return i2 != 0;
    }

    public static boolean checkTextValidity(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static ColorFilter getColorFilter(Context context, int i2) {
        return BlendModeColorFilterCompat.createBlendModeColorFilterCompat(ContextCompat.getColor(context, i2), BlendModeCompat.SRC_ATOP);
    }

    public static int getFormatCount(String str) {
        int i2 = 0;
        while (Pattern.compile("%[^%]*\\d").matcher(str).find()) {
            i2++;
        }
        return i2;
    }
}
