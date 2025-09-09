package com.aliyun.iot.link.ui.component.wheelview;

import android.content.res.Resources;
import android.util.TypedValue;

/* loaded from: classes3.dex */
public class DimensionUtil {
    public static float dip2px(float f2) {
        return TypedValue.applyDimension(1, f2, Resources.getSystem().getDisplayMetrics());
    }

    public static float sp2px(float f2) {
        return TypedValue.applyDimension(2, f2, Resources.getSystem().getDisplayMetrics());
    }
}
