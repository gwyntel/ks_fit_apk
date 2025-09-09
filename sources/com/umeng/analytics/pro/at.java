package com.umeng.analytics.pro;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes4.dex */
public class at {

    /* renamed from: a, reason: collision with root package name */
    private static String f21342a = "";

    /* renamed from: b, reason: collision with root package name */
    private static String f21343b = "";

    /* renamed from: c, reason: collision with root package name */
    private static final String f21344c = "hw_sc.build.platform.version";

    /* renamed from: d, reason: collision with root package name */
    private static final String f21345d = "ro.build.version.emui";

    /* renamed from: e, reason: collision with root package name */
    private static final String f21346e = "ro.build.version.magic";

    /* renamed from: f, reason: collision with root package name */
    private static final String f21347f = "ro.miui.ui.version.name";

    /* renamed from: g, reason: collision with root package name */
    private static final String f21348g = "ro.build.version.opporom";

    /* renamed from: h, reason: collision with root package name */
    private static final String f21349h = "ro.vivo.os.name";

    /* renamed from: i, reason: collision with root package name */
    private static final String f21350i = "ro.vivo.os.version";

    /* renamed from: j, reason: collision with root package name */
    private static final String f21351j = "ro.build.version.oplusrom";

    /* renamed from: k, reason: collision with root package name */
    private static final String f21352k = "ro.rom.version";

    private static boolean a() {
        try {
            Class<?> cls = Class.forName("com.huawei.system.BuildEx");
            return !TextUtils.isEmpty((String) cls.getMethod("getOsBrand", null).invoke(cls, null));
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(f21342a)) {
            e(str);
        }
        return f21343b;
    }

    public static String c(String str) {
        return TextUtils.isEmpty(str) ? "" : str.replaceAll(" ", "").toUpperCase();
    }

    private static String d(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(cls, str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void e(java.lang.String r4) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.at.e(java.lang.String):void");
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(f21342a)) {
            e(str);
        }
        return f21342a;
    }
}
