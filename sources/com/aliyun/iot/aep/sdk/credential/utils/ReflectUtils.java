package com.aliyun.iot.aep.sdk.credential.utils;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class ReflectUtils {

    /* renamed from: a, reason: collision with root package name */
    private static AtomicBoolean f11747a = new AtomicBoolean(false);

    /* renamed from: b, reason: collision with root package name */
    private static AtomicBoolean f11748b = new AtomicBoolean(false);

    public static boolean hasClass(String str) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException e2) {
            ALog.e("ReflectUtils", "hasClss=" + e2);
            return false;
        } catch (Exception e3) {
            ALog.e("ReflectUtils", "hasClssEx=" + e3);
            return false;
        }
    }

    public static boolean hasOADep() {
        if (f11747a.compareAndSet(false, true)) {
            f11748b.set(hasClass("com.aliyun.iot.aep.sdk.login.LoginBusiness"));
        }
        return f11748b.get();
    }
}
