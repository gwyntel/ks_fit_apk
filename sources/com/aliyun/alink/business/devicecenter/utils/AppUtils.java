package com.aliyun.alink.business.devicecenter.utils;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class AppUtils {
    public static Context getContext() throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(cls, null);
            return (Context) objInvoke.getClass().getMethod("getApplication", null).invoke(objInvoke, null);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
