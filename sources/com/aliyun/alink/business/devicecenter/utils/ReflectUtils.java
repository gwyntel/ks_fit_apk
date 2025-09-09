package com.aliyun.alink.business.devicecenter.utils;

import android.app.Application;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.iot.aep.sdk.credential.IotCredentialManager.IoTCredentialManageImpl;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ReflectUtils {
    public static Class<?>[] a(Object... objArr) {
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            if (obj == null) {
                throw new IllegalArgumentException("objects has null value.");
            }
            clsArr[i2] = obj.getClass();
        }
        return clsArr;
    }

    public static Object callStaticMethod(String str, String str2, Object... objArr) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        return Class.forName(str).getMethod(str2, a(objArr)).invoke(null, objArr);
    }

    public static Object getIdentifyId() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("ReflectUtils", "getIdentifyId() called");
        try {
            String str = IoTCredentialManageImpl.AUTH_IOT_TOKEN_STATUS_CHANGE_BROADCAST;
            Object objInvoke = IoTCredentialManageImpl.class.getDeclaredMethod("getInstance", Application.class).invoke(null, ContextHolder.getInstance().getAppContext());
            return objInvoke.getClass().getDeclaredMethod("getIoTIdentity", null).invoke(objInvoke, null);
        } catch (ClassNotFoundException e2) {
            ALog.d("ReflectUtils", "ClassNotFoundException e=" + e2);
            return null;
        } catch (IllegalAccessException e3) {
            ALog.d("ReflectUtils", "IllegalAccessException e=" + e3);
            return null;
        } catch (NoSuchMethodException e4) {
            ALog.d("ReflectUtils", "NoSuchMethodException e=" + e4);
            return null;
        } catch (InvocationTargetException e5) {
            ALog.d("ReflectUtils", "InvocationTargetException e=" + e5);
            return null;
        } catch (Exception e6) {
            ALog.d("ReflectUtils", "Exception e=" + e6);
            return null;
        }
    }

    public static boolean hasClass(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException e2) {
            ALog.e("ReflectUtils", "hasClss=" + e2);
            return false;
        } catch (Exception e3) {
            ALog.e("ReflectUtils", "hasClssEx=" + e3);
            return false;
        } catch (NoClassDefFoundError e4) {
            ALog.e("ReflectUtils", "hasClssNCDFE=" + e4);
            return false;
        } catch (Throwable th) {
            ALog.e("ReflectUtils", "hasClssT=" + th);
            return false;
        }
    }

    public static Object newInstance(Class<?> cls, Object... objArr) {
        Class<?>[] clsArrA = a(objArr);
        try {
            Constructor<?> constructorA = a(cls, clsArrA);
            if (constructorA != null) {
                return constructorA.newInstance(objArr);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("newInstance not found match construct, className: ");
            sb.append(cls.getName());
            ALog.w("ReflectUtils", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            for (Class<?> cls2 : clsArrA) {
                sb2.append(cls2.getName());
                sb2.append("'");
            }
            sb2.deleteCharAt(sb2.length() - 1);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("args: ");
            sb3.append(sb2.toString());
            ALog.w("ReflectUtils", sb3.toString());
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            ALog.w("ReflectUtils", "newInstance IllegalAccessException=" + e2);
            return null;
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            ALog.w("ReflectUtils", "newInstance InstantiationException=" + e3);
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            ALog.w("ReflectUtils", "newInstance InvocationTargetException=" + e4);
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            ALog.w("ReflectUtils", "newInstance exception=" + th);
            return null;
        }
    }

    public static void setFieldValue(Class<?> cls, String str, String str2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(cls, str2);
        } catch (Exception e2) {
            ALog.d("ReflectUtils", "setFieldValue key=" + str + ", value=" + str2 + ",e=" + e2);
        }
    }

    public static Constructor<?> a(Class<?> cls, Class<?>[] clsArr) throws SecurityException {
        Constructor<?>[] constructors = cls.getConstructors();
        int length = constructors.length;
        Constructor<?> constructor = null;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            Constructor<?> constructor2 = constructors[i2];
            Class<?>[] parameterTypes = constructor2.getParameterTypes();
            if (parameterTypes.length == clsArr.length) {
                int i3 = 0;
                while (true) {
                    if (i3 >= clsArr.length) {
                        constructor = constructor2;
                        break;
                    }
                    if (!parameterTypes[i3].isAssignableFrom(clsArr[i3])) {
                        constructor = null;
                        break;
                    }
                    i3++;
                }
                if (constructor != null) {
                    ALog.i("ReflectUtils", "found constructor for classType: " + cls.getName());
                    break;
                }
            }
            i2++;
        }
        return constructor;
    }
}
