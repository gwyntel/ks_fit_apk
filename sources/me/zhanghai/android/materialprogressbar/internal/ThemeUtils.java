package me.zhanghai.android.materialprogressbar.internal;

import android.content.Context;
import android.content.res.TypedArray;

/* loaded from: classes5.dex */
public class ThemeUtils {
    private ThemeUtils() {
    }

    public static int getColorFromAttrRes(int i2, int i3, Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i2});
        try {
            return typedArrayObtainStyledAttributes.getColor(0, i3);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public static float getFloatFromAttrRes(int i2, float f2, Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i2});
        try {
            return typedArrayObtainStyledAttributes.getFloat(0, f2);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }
}
