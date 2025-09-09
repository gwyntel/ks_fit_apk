package com.luck.picture.lib.utils;

import anetwork.channel.util.RequestConstant;

/* loaded from: classes4.dex */
public class ValueOf {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T to(Object obj, T t2) {
        return obj == 0 ? t2 : obj;
    }

    public static boolean toBoolean(Object obj) {
        return toBoolean(obj, false);
    }

    public static double toDouble(Object obj) {
        return toDouble(obj, 0);
    }

    public static float toFloat(Object obj, long j2) {
        if (obj == null) {
            return j2;
        }
        try {
            return Float.parseFloat(obj.toString().trim());
        } catch (Exception unused) {
            return j2;
        }
    }

    public static int toInt(Object obj, int i2) {
        if (obj == null) {
            return i2;
        }
        try {
            String strTrim = obj.toString().trim();
            return strTrim.contains(".") ? Integer.parseInt(strTrim.substring(0, strTrim.lastIndexOf("."))) : Integer.parseInt(strTrim);
        } catch (Exception unused) {
            return i2;
        }
    }

    public static long toLong(Object obj, long j2) {
        if (obj == null) {
            return j2;
        }
        try {
            String strTrim = obj.toString().trim();
            return strTrim.contains(".") ? Long.parseLong(strTrim.substring(0, strTrim.lastIndexOf("."))) : Long.parseLong(strTrim);
        } catch (Exception unused) {
            return j2;
        }
    }

    public static String toString(Object obj) {
        try {
            return obj.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static boolean toBoolean(Object obj, boolean z2) {
        if (obj == null) {
            return false;
        }
        try {
            return !RequestConstant.FALSE.equals(obj.toString().trim().trim());
        } catch (Exception unused) {
            return z2;
        }
    }

    public static double toDouble(Object obj, int i2) {
        if (obj == null) {
            return i2;
        }
        try {
            return Double.parseDouble(obj.toString().trim());
        } catch (Exception unused) {
            return i2;
        }
    }

    public static float toFloat(Object obj) {
        return toFloat(obj, 0L);
    }

    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    public static long toLong(Object obj) {
        return toLong(obj, 0L);
    }
}
