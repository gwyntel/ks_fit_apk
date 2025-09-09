package me.zhanghai.android.materialprogressbar.internal;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.util.Property;

@TargetApi(21)
/* loaded from: classes5.dex */
class ObjectAnimatorCompatLollipop {
    private ObjectAnimatorCompatLollipop() {
    }

    public static ObjectAnimator ofArgb(Object obj, String str, int... iArr) {
        return ObjectAnimator.ofArgb(obj, str, iArr);
    }

    public static ObjectAnimator ofFloat(Object obj, String str, String str2, Path path) {
        return ObjectAnimator.ofFloat(obj, str, str2, path);
    }

    public static ObjectAnimator ofInt(Object obj, String str, String str2, Path path) {
        return ObjectAnimator.ofInt(obj, str, str2, path);
    }

    public static <T> ObjectAnimator ofArgb(T t2, Property<T, Integer> property, int... iArr) {
        return ObjectAnimator.ofArgb(t2, property, iArr);
    }

    public static <T> ObjectAnimator ofFloat(T t2, Property<T, Float> property, Property<T, Float> property2, Path path) {
        return ObjectAnimator.ofFloat(t2, property, property2, path);
    }

    public static <T> ObjectAnimator ofInt(T t2, Property<T, Integer> property, Property<T, Integer> property2, Path path) {
        return ObjectAnimator.ofInt(t2, property, property2, path);
    }
}
