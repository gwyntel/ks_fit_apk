package com.linkkit.tools.utils;

import com.linkkit.tools.a;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class ReflectUtils {
    private static final String TAG = "ReflectUtils";

    public static boolean hasClass(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException e2) {
            a.c(TAG, "hasClss=" + e2);
            return false;
        } catch (Exception e3) {
            a.c(TAG, "hasClssEx=" + e3);
            return false;
        }
    }

    public static Object invokeStaticMethod(String str, String str2) {
        try {
            return Class.forName(str).getMethod(str2, null).invoke(null, null);
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return null;
        } catch (Exception e6) {
            e6.printStackTrace();
            return null;
        }
    }

    public static void setFieldValue(Class<?> cls, String str, String str2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(cls, str2);
        } catch (Exception e2) {
            a.a(TAG, "setFieldValue key=" + str + ", value=" + str2 + ",e=" + e2);
        }
    }
}
