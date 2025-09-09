package com.aliyun.alink.linksdk.tmp.utils;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class LogCat {
    public static void d(String str, String str2, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(str, str2);
    }

    public static void e(String str, String str2, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(str, str2, th == null ? null : th.toString());
    }

    public static void i(String str, String str2, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.i(str, str2);
    }

    public static void v(String str, String str2, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        i(str, str2, th);
    }

    public static void w(String str, String str2, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.w(str, str2);
    }

    public static void d(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(str, str2);
    }

    public static void e(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.e(str, str2);
    }

    public static void i(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.i(str, str2);
    }

    public static void v(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        i(str, str2);
    }

    public static void w(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.w(str, str2);
    }
}
