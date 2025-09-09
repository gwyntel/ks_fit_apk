package com.aliyun.alink.linksdk.tmp.utils;

import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class SystemUtil {
    private static final String TAG = "SystemUtil";
    private static Class<?> mClassType;
    private static Method mGetIntMethod;
    private static Method mGetMethod;

    public static String get(String str) {
        init();
        String str2 = null;
        try {
            String str3 = (String) mGetMethod.invoke(mClassType, str);
            try {
                LogCat.d(TAG, "get key:" + str + " value:" + str3);
                return str3;
            } catch (Exception e2) {
                e = e2;
                str2 = str3;
                e.printStackTrace();
                return str2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static int getInt(String str, int i2) {
        init();
        try {
            Integer num = (Integer) mGetIntMethod.invoke(mClassType, str, Integer.valueOf(i2));
            i2 = num.intValue();
            LogCat.d(TAG, "getInt key:" + str + " value:" + num);
            return i2;
        } catch (Exception e2) {
            e2.printStackTrace();
            return i2;
        }
    }

    private static void init() {
        try {
            if (mClassType == null) {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                mClassType = cls;
                mGetMethod = cls.getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class);
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, Integer.TYPE);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
