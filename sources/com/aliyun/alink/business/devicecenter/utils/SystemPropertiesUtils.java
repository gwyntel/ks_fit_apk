package com.aliyun.alink.business.devicecenter.utils;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class SystemPropertiesUtils {

    /* renamed from: a, reason: collision with root package name */
    public static Class<?> f10652a;

    /* renamed from: b, reason: collision with root package name */
    public static Method f10653b;

    /* renamed from: c, reason: collision with root package name */
    public static Method f10654c;

    public static void a() throws ClassNotFoundException {
        try {
            if (f10652a == null) {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                f10652a = cls;
                f10653b = cls.getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class);
                f10654c = f10652a.getDeclaredMethod("getInt", String.class, Integer.TYPE);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static String get(String str) throws ClassNotFoundException {
        a();
        try {
            return (String) f10653b.invoke(f10652a, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static int getInt(String str, int i2) throws ClassNotFoundException {
        a();
        try {
            return ((Integer) f10654c.invoke(f10652a, str, Integer.valueOf(i2))).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return i2;
        }
    }
}
