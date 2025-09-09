package org.apache.commons.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;

/* loaded from: classes5.dex */
public class MethodUtils {
    public static Method getAccessibleMethod(Class cls, String str, Class cls2) {
        return getAccessibleMethod(cls, str, new Class[]{cls2});
    }

    private static Method getAccessibleMethodFromInterfaceNest(Class cls, String str, Class[] clsArr) throws NoSuchMethodException, SecurityException {
        Method accessibleMethodFromInterfaceNest = null;
        while (cls != null) {
            Class<?>[] interfaces = cls.getInterfaces();
            for (int i2 = 0; i2 < interfaces.length; i2++) {
                if (Modifier.isPublic(interfaces[i2].getModifiers())) {
                    try {
                        accessibleMethodFromInterfaceNest = interfaces[i2].getDeclaredMethod(str, clsArr);
                    } catch (NoSuchMethodException unused) {
                    }
                    if (accessibleMethodFromInterfaceNest == null && (accessibleMethodFromInterfaceNest = getAccessibleMethodFromInterfaceNest(interfaces[i2], str, clsArr)) == null) {
                    }
                }
            }
            cls = cls.getSuperclass();
        }
        return accessibleMethodFromInterfaceNest;
    }

    private static Method getAccessibleMethodFromSuperclass(Class cls, String str, Class[] clsArr) {
        for (Class superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            if (Modifier.isPublic(superclass.getModifiers())) {
                try {
                    return superclass.getMethod(str, clsArr);
                } catch (NoSuchMethodException unused) {
                    return null;
                }
            }
        }
        return null;
    }

    public static Method getMatchingAccessibleMethod(Class cls, String str, Class[] clsArr) throws NoSuchMethodException, SecurityException {
        Method accessibleMethod;
        try {
            Method method = cls.getMethod(str, clsArr);
            MemberUtils.f(method);
            return method;
        } catch (NoSuchMethodException unused) {
            Method[] methods = cls.getMethods();
            int length = methods.length;
            Method method2 = null;
            for (int i2 = 0; i2 < length; i2++) {
                if (methods[i2].getName().equals(str) && ClassUtils.isAssignable(clsArr, (Class[]) methods[i2].getParameterTypes(), true) && (accessibleMethod = getAccessibleMethod(methods[i2])) != null && (method2 == null || MemberUtils.b(accessibleMethod.getParameterTypes(), method2.getParameterTypes(), clsArr) < 0)) {
                    method2 = accessibleMethod;
                }
            }
            if (method2 != null) {
                MemberUtils.f(method2);
            }
            return method2;
        }
    }

    public static Object invokeExactMethod(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeExactMethod(obj, str, new Object[]{obj2});
    }

    public static Object invokeExactStaticMethod(Class cls, String str, Object[] objArr, Class[] clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Method accessibleMethod = getAccessibleMethod(cls, str, clsArr);
        if (accessibleMethod != null) {
            return accessibleMethod.invoke(null, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on class: ");
        stringBuffer.append(cls.getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static Object invokeMethod(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeMethod(obj, str, new Object[]{obj2});
    }

    public static Object invokeStaticMethod(Class cls, String str, Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeStaticMethod(cls, str, new Object[]{obj});
    }

    public static Method getAccessibleMethod(Class cls, String str, Class[] clsArr) {
        try {
            return getAccessibleMethod(cls.getMethod(str, clsArr));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Object invokeExactMethod(Object obj, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i2 = 0; i2 < length; i2++) {
            clsArr[i2] = objArr[i2].getClass();
        }
        return invokeExactMethod(obj, str, objArr, clsArr);
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i2 = 0; i2 < length; i2++) {
            clsArr[i2] = objArr[i2].getClass();
        }
        return invokeMethod(obj, str, objArr, clsArr);
    }

    public static Object invokeStaticMethod(Class cls, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i2 = 0; i2 < length; i2++) {
            clsArr[i2] = objArr[i2].getClass();
        }
        return invokeStaticMethod(cls, str, objArr, clsArr);
    }

    public static Method getAccessibleMethod(Method method) throws NoSuchMethodException, SecurityException {
        if (!MemberUtils.c(method)) {
            return null;
        }
        Class<?> declaringClass = method.getDeclaringClass();
        if (Modifier.isPublic(declaringClass.getModifiers())) {
            return method;
        }
        String name = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method accessibleMethodFromInterfaceNest = getAccessibleMethodFromInterfaceNest(declaringClass, name, parameterTypes);
        return accessibleMethodFromInterfaceNest == null ? getAccessibleMethodFromSuperclass(declaringClass, name, parameterTypes) : accessibleMethodFromInterfaceNest;
    }

    public static Object invokeExactStaticMethod(Class cls, String str, Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return invokeExactStaticMethod(cls, str, new Object[]{obj});
    }

    public static Object invokeExactMethod(Object obj, String str, Object[] objArr, Class[] clsArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Method accessibleMethod = getAccessibleMethod(obj.getClass(), str, clsArr);
        if (accessibleMethod != null) {
            return accessibleMethod.invoke(obj, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on object: ");
        stringBuffer.append(obj.getClass().getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static Object invokeExactStaticMethod(Class cls, String str, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        int length = objArr.length;
        Class[] clsArr = new Class[length];
        for (int i2 = 0; i2 < length; i2++) {
            clsArr[i2] = objArr[i2].getClass();
        }
        return invokeExactStaticMethod(cls, str, objArr, clsArr);
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr, Class[] clsArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        Method matchingAccessibleMethod = getMatchingAccessibleMethod(obj.getClass(), str, clsArr);
        if (matchingAccessibleMethod != null) {
            return matchingAccessibleMethod.invoke(obj, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on object: ");
        stringBuffer.append(obj.getClass().getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }

    public static Object invokeStaticMethod(Class cls, String str, Object[] objArr, Class[] clsArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, InvocationTargetException {
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (objArr == null) {
            objArr = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        Method matchingAccessibleMethod = getMatchingAccessibleMethod(cls, str, clsArr);
        if (matchingAccessibleMethod != null) {
            return matchingAccessibleMethod.invoke(null, objArr);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("No such accessible method: ");
        stringBuffer.append(str);
        stringBuffer.append("() on class: ");
        stringBuffer.append(cls.getName());
        throw new NoSuchMethodException(stringBuffer.toString());
    }
}
