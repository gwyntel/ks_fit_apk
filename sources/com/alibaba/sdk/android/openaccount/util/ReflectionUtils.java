package com.alibaba.sdk.android.openaccount.util;

import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ReflectionUtils {
    private static final Map<String, Class<?>> PRIMITIVE_CLASSES;
    private static final String TAG;

    static {
        HashMap map = new HashMap();
        PRIMITIVE_CLASSES = map;
        TAG = ReflectionUtils.class.getSimpleName();
        map.put("short", Short.TYPE);
        map.put("int", Integer.TYPE);
        map.put("long", Long.TYPE);
        map.put(TmpConstant.TYPE_VALUE_DOUBLE, Double.TYPE);
        map.put("float", Float.TYPE);
        map.put("char", Character.TYPE);
    }

    public static Field getField(Class<?> cls, String str) throws NoSuchFieldException, SecurityException {
        if (cls == null) {
            return null;
        }
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Exception unused) {
            return getField(cls.getSuperclass(), str);
        }
    }

    public static Object getFieldValue(Class<?> cls, String str, Object obj) throws NoSuchFieldException, SecurityException {
        Field field = getField(cls, str);
        if (field != null) {
            try {
                return field.get(obj);
            } catch (Exception e2) {
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to get field value ", e2);
                return null;
            }
        }
        AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to find field named " + str + " from class " + cls.getName());
        return null;
    }

    public static Method getMethod(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException, SecurityException {
        if (cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Exception unused) {
            return getMethod(cls.getSuperclass(), str, new Class[0]);
        }
    }

    public static Object invoke(Class<?> cls, String str, String[] strArr, Object obj, Object[] objArr) {
        Method method;
        if (strArr != null) {
            try {
                method = strArr.length == 0 ? cls.getMethod(str, null) : cls.getMethod(str, toClasses(strArr));
            } catch (Exception e2) {
                AliSDKLogger.e(TAG, "Fail to invoke the " + cls.getName() + "." + str + ", the error is " + e2.getMessage());
                throw new RuntimeException(e2);
            }
        }
        return method.invoke(obj, objArr);
    }

    public static Class<?> loadClassQuietly(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static <T> T newInstance(Class<T> cls, Class<?>[] clsArr, Object[] objArr) {
        if (clsArr != null) {
            try {
                if (clsArr.length != 0) {
                    return cls.getConstructor(clsArr).newInstance(objArr);
                }
            } catch (Exception e2) {
                AliSDKLogger.e(TAG, "Fail to create the instance of type " + cls.getName() + ", the error is " + e2.getMessage());
                throw new RuntimeException(e2);
            }
        }
        return cls.newInstance();
    }

    public static void set(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field field = obj.getClass().getField(str);
            field.setAccessible(true);
            field.set(obj, obj2);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
    }

    public static Class<?>[] toClasses(String[] strArr) throws Exception {
        if (strArr == null) {
            return null;
        }
        Class<?>[] clsArr = new Class[strArr.length];
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = strArr[i2];
            if (str.length() < 7) {
                clsArr[i2] = PRIMITIVE_CLASSES.get(str);
            }
            if (clsArr[i2] == null) {
                clsArr[i2] = Class.forName(str);
            }
        }
        return clsArr;
    }

    public static Object newInstance(String str, String[] strArr, Object[] objArr) {
        try {
            return newInstance(Class.forName(str), toClasses(strArr), objArr);
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            AliSDKLogger.e(TAG, "Fail to create the instance of type " + str + ", the error is " + e3.getMessage());
            throw new RuntimeException(e3);
        }
    }

    public static Object invoke(String str, String str2, String[] strArr, Object obj, Object[] objArr) {
        try {
            return invoke(Class.forName(str), str2, strArr, obj, objArr);
        } catch (Exception e2) {
            AliSDKLogger.e(TAG, "Fail to invoke the " + str + "." + str2 + ", the error is " + e2.getMessage());
            throw new RuntimeException(e2);
        }
    }
}
