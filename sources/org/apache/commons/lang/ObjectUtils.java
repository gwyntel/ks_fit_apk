package org.apache.commons.lang;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang.exception.CloneFailedException;
import org.apache.commons.lang.reflect.MethodUtils;

/* loaded from: classes5.dex */
public class ObjectUtils {
    public static final Null NULL = new Null();

    public static class Null implements Serializable {
        private static final long serialVersionUID = 7092611880189329093L;

        Null() {
        }

        private Object readResolve() {
            return ObjectUtils.NULL;
        }
    }

    public static StringBuffer appendIdentityToString(StringBuffer stringBuffer, Object obj) {
        if (obj == null) {
            return null;
        }
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        stringBuffer.append(obj.getClass().getName());
        stringBuffer.append('@');
        stringBuffer.append(Integer.toHexString(System.identityHashCode(obj)));
        return stringBuffer;
    }

    public static Object clone(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        if (!(obj instanceof Cloneable)) {
            return null;
        }
        if (!obj.getClass().isArray()) {
            try {
                return MethodUtils.invokeMethod(obj, "clone", (Object[]) null);
            } catch (IllegalAccessException e2) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Cannot clone Cloneable type ");
                stringBuffer.append(obj.getClass().getName());
                throw new CloneFailedException(stringBuffer.toString(), e2);
            } catch (NoSuchMethodException e3) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("Cloneable type ");
                stringBuffer2.append(obj.getClass().getName());
                stringBuffer2.append(" has no clone method");
                throw new CloneFailedException(stringBuffer2.toString(), e3);
            } catch (InvocationTargetException e4) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Exception cloning Cloneable type ");
                stringBuffer3.append(obj.getClass().getName());
                throw new CloneFailedException(stringBuffer3.toString(), e4.getTargetException());
            }
        }
        Class<?> componentType = obj.getClass().getComponentType();
        if (!componentType.isPrimitive()) {
            return ((Object[]) obj).clone();
        }
        int length = Array.getLength(obj);
        Object objNewInstance = Array.newInstance(componentType, length);
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return objNewInstance;
            }
            Array.set(objNewInstance, i2, Array.get(obj, i2));
            length = i2;
        }
    }

    public static Object cloneIfPossible(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        Object objClone = clone(obj);
        return objClone == null ? obj : objClone;
    }

    public static int compare(Comparable comparable, Comparable comparable2) {
        return compare(comparable, comparable2, false);
    }

    public static Object defaultIfNull(Object obj, Object obj2) {
        return obj != null ? obj : obj2;
    }

    public static boolean equals(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return obj.equals(obj2);
    }

    public static int hashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public static String identityToString(Object obj) {
        if (obj == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        identityToString(stringBuffer, obj);
        return stringBuffer.toString();
    }

    public static Object max(Comparable comparable, Comparable comparable2) {
        return compare(comparable, comparable2, false) >= 0 ? comparable : comparable2;
    }

    public static Object min(Comparable comparable, Comparable comparable2) {
        return compare(comparable, comparable2, true) <= 0 ? comparable : comparable2;
    }

    public static boolean notEqual(Object obj, Object obj2) {
        return !equals(obj, obj2);
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static int compare(Comparable comparable, Comparable comparable2, boolean z2) {
        if (comparable == comparable2) {
            return 0;
        }
        return comparable == null ? z2 ? 1 : -1 : comparable2 == null ? z2 ? -1 : 1 : comparable.compareTo(comparable2);
    }

    public static String toString(Object obj, String str) {
        return obj == null ? str : obj.toString();
    }

    public static void identityToString(StringBuffer stringBuffer, Object obj) {
        if (obj != null) {
            stringBuffer.append(obj.getClass().getName());
            stringBuffer.append('@');
            stringBuffer.append(Integer.toHexString(System.identityHashCode(obj)));
            return;
        }
        throw new NullPointerException("Cannot get the toString of a null identity");
    }
}
