package org.apache.commons.lang.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.SystemUtils;

/* loaded from: classes5.dex */
abstract class MemberUtils {
    private static final int ACCESS_TEST = 7;
    private static final Method IS_SYNTHETIC;
    private static final Class[] ORDERED_PRIMITIVE_TYPES;

    /* renamed from: a, reason: collision with root package name */
    static /* synthetic */ Class f26635a;

    static {
        Method method;
        if (SystemUtils.isJavaVersionAtLeast(1.5f)) {
            try {
                Class clsA = f26635a;
                if (clsA == null) {
                    clsA = a("java.lang.reflect.Member");
                    f26635a = clsA;
                }
                method = clsA.getMethod("isSynthetic", ArrayUtils.EMPTY_CLASS_ARRAY);
            } catch (Exception unused) {
            }
        } else {
            method = null;
        }
        IS_SYNTHETIC = method;
        ORDERED_PRIMITIVE_TYPES = new Class[]{Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE};
    }

    static /* synthetic */ Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    static int b(Class[] clsArr, Class[] clsArr2, Class[] clsArr3) {
        float totalTransformationCost = getTotalTransformationCost(clsArr3, clsArr);
        float totalTransformationCost2 = getTotalTransformationCost(clsArr3, clsArr2);
        if (totalTransformationCost < totalTransformationCost2) {
            return -1;
        }
        return totalTransformationCost2 < totalTransformationCost ? 1 : 0;
    }

    static boolean c(Member member) {
        return (member == null || !Modifier.isPublic(member.getModifiers()) || e(member)) ? false : true;
    }

    static boolean d(int i2) {
        return (i2 & 7) == 0;
    }

    static boolean e(Member member) {
        Method method = IS_SYNTHETIC;
        if (method == null) {
            return false;
        }
        try {
            return ((Boolean) method.invoke(member, null)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static void f(AccessibleObject accessibleObject) throws SecurityException {
        if (accessibleObject == 0 || accessibleObject.isAccessible()) {
            return;
        }
        Member member = (Member) accessibleObject;
        if (Modifier.isPublic(member.getModifiers()) && d(member.getDeclaringClass().getModifiers())) {
            try {
                accessibleObject.setAccessible(true);
            } catch (SecurityException unused) {
            }
        }
    }

    private static float getObjectTransformationCost(Class cls, Class cls2) {
        if (cls2.isPrimitive()) {
            return getPrimitivePromotionCost(cls, cls2);
        }
        float f2 = 0.0f;
        while (true) {
            if (cls != null && !cls2.equals(cls)) {
                if (cls2.isInterface() && ClassUtils.isAssignable(cls, cls2)) {
                    f2 += 0.25f;
                    break;
                }
                f2 += 1.0f;
                cls = cls.getSuperclass();
            } else {
                break;
            }
        }
        return cls == null ? f2 + 1.5f : f2;
    }

    private static float getPrimitivePromotionCost(Class cls, Class cls2) {
        float f2;
        if (cls.isPrimitive()) {
            f2 = 0.0f;
        } else {
            cls = ClassUtils.wrapperToPrimitive(cls);
            f2 = 0.1f;
        }
        int i2 = 0;
        while (cls != cls2) {
            Class[] clsArr = ORDERED_PRIMITIVE_TYPES;
            if (i2 >= clsArr.length) {
                break;
            }
            if (cls == clsArr[i2]) {
                f2 += 0.1f;
                if (i2 < clsArr.length - 1) {
                    cls = clsArr[i2 + 1];
                }
            }
            i2++;
        }
        return f2;
    }

    private static float getTotalTransformationCost(Class[] clsArr, Class[] clsArr2) {
        float objectTransformationCost = 0.0f;
        for (int i2 = 0; i2 < clsArr.length; i2++) {
            objectTransformationCost += getObjectTransformationCost(clsArr[i2], clsArr2[i2]);
        }
        return objectTransformationCost;
    }
}
