package com.aliyun.alink.linksdk.tools.log;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class TLogHelper {

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f11462a = true;

    /* renamed from: b, reason: collision with root package name */
    private static Class f11463b;

    /* renamed from: c, reason: collision with root package name */
    private static Method f11464c;

    /* renamed from: d, reason: collision with root package name */
    private static Method f11465d;

    /* renamed from: e, reason: collision with root package name */
    private static Method f11466e;

    /* renamed from: f, reason: collision with root package name */
    private static Method f11467f;

    /* renamed from: g, reason: collision with root package name */
    private static Method f11468g;

    static {
        try {
            Class<?> cls = Class.forName("com.taobao.tao.log.TLog");
            f11463b = cls;
            f11464c = cls.getDeclaredMethod("logv", String.class, String.class, String.class);
            f11465d = f11463b.getDeclaredMethod("logd", String.class, String.class, String.class);
            f11466e = f11463b.getDeclaredMethod("logi", String.class, String.class, String.class);
            f11467f = f11463b.getDeclaredMethod("logw", String.class, String.class, String.class);
            f11468g = f11463b.getDeclaredMethod("loge", String.class, String.class, String.class);
        } catch (Exception e2) {
            Log.e("TLogHelper", "printToTLog reflect e:" + e2.toString());
        }
    }

    public static boolean isToTlogOn() {
        return f11462a;
    }

    public static void printToTLog(int i2, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method;
        if (isToTlogOn()) {
            if (i2 <= 2) {
                Method method2 = f11464c;
                if (method2 == null) {
                    return;
                }
                try {
                    method2.invoke(f11463b, "", str, str2);
                    return;
                } catch (Exception e2) {
                    Log.e("TLogHelper", "printToTLog logvMethodOfTLog e:" + e2.toString());
                    return;
                }
            }
            if (i2 == 3) {
                Method method3 = f11465d;
                if (method3 == null) {
                    return;
                }
                try {
                    method3.invoke(f11463b, "", str, str2);
                    return;
                } catch (Exception e3) {
                    Log.e("TLogHelper", "printToTLog logvMethodOfTLog e:" + e3.toString());
                    return;
                }
            }
            if (i2 == 4) {
                Method method4 = f11466e;
                if (method4 == null) {
                    return;
                }
                try {
                    method4.invoke(f11463b, "", str, str2);
                    return;
                } catch (Exception e4) {
                    Log.e("TLogHelper", "printToTLog logvMethodOfTLog e:" + e4.toString());
                    return;
                }
            }
            if (i2 == 5) {
                Method method5 = f11467f;
                if (method5 == null) {
                    return;
                }
                try {
                    method5.invoke(f11463b, "", str, str2);
                    return;
                } catch (Exception e5) {
                    Log.e("TLogHelper", "printToTLog logvMethodOfTLog e:" + e5.toString());
                    return;
                }
            }
            if (i2 < 6 || (method = f11468g) == null) {
                return;
            }
            try {
                method.invoke(f11463b, "", str, str2);
            } catch (Exception e6) {
                Log.e("TLogHelper", "printToTLog logvMethodOfTLog e:" + e6.toString());
            }
        }
    }

    public static void setToTlogOn(boolean z2) {
        Log.d("TLogHelper", "setToTlogOn on:" + z2);
        f11462a = z2;
    }
}
