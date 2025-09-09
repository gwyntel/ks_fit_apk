package com.huawei.hms.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.media3.extractor.text.ttml.TtmlNode;

/* loaded from: classes4.dex */
public abstract class ResourceLoaderUtil {

    /* renamed from: a, reason: collision with root package name */
    private static Context f18229a;

    /* renamed from: b, reason: collision with root package name */
    private static String f18230b;

    public static int getAnimId(String str) {
        return f18229a.getResources().getIdentifier(str, "anim", f18230b);
    }

    public static int getColorId(String str) {
        return f18229a.getResources().getIdentifier(str, "color", f18230b);
    }

    public static int getDimenId(String str) {
        return f18229a.getResources().getIdentifier(str, "dimen", f18230b);
    }

    public static Drawable getDrawable(String str) {
        return f18229a.getResources().getDrawable(getDrawableId(str));
    }

    public static int getDrawableId(String str) {
        return f18229a.getResources().getIdentifier(str, "drawable", f18230b);
    }

    public static int getIdId(String str) {
        return f18229a.getResources().getIdentifier(str, "id", f18230b);
    }

    public static int getLayoutId(String str) {
        return f18229a.getResources().getIdentifier(str, TtmlNode.TAG_LAYOUT, f18230b);
    }

    public static String getString(String str) {
        return f18229a.getResources().getString(getStringId(str));
    }

    public static int getStringId(String str) {
        return f18229a.getResources().getIdentifier(str, "string", f18230b);
    }

    public static int getStyleId(String str) {
        return f18229a.getResources().getIdentifier(str, "style", f18230b);
    }

    public static Context getmContext() {
        return f18229a;
    }

    public static void setmContext(Context context) {
        f18229a = context;
        f18230b = context.getPackageName();
    }

    public static String getString(String str, Object... objArr) {
        return f18229a.getResources().getString(getStringId(str), objArr);
    }
}
