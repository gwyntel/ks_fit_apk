package com.linkkit.tools.utils;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class SystemPropertiesUtils {
    private static final String TAG = "SystemUtil";
    private static Class<?> mClassType;
    private static Method mGetIntMethod;
    private static Method mGetMethod;

    public static String get(String str) throws ClassNotFoundException {
        init();
        try {
            return (String) mGetMethod.invoke(mClassType, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static int getInt(String str, int i2) throws ClassNotFoundException {
        init();
        try {
            return ((Integer) mGetIntMethod.invoke(mClassType, str, Integer.valueOf(i2))).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return i2;
        }
    }

    private static void init() throws ClassNotFoundException {
        try {
            if (mClassType == null) {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                mClassType = cls;
                mGetMethod = cls.getDeclaredMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class);
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, Integer.TYPE);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
