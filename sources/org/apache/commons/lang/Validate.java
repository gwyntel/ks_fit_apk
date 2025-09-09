package org.apache.commons.lang;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes5.dex */
public class Validate {
    public static void allElementsOfType(Collection collection, Class cls, String str) {
        notNull(collection);
        notNull(cls);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!cls.isInstance(it.next())) {
                throw new IllegalArgumentException(str);
            }
        }
    }

    public static void isTrue(boolean z2, String str, Object obj) {
        if (z2) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(obj);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static void noNullElements(Object[] objArr, String str) {
        notNull(objArr);
        for (Object obj : objArr) {
            if (obj == null) {
                throw new IllegalArgumentException(str);
            }
        }
    }

    public static void notEmpty(Object[] objArr, String str) {
        if (objArr == null || objArr.length == 0) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void notNull(Object obj) {
        notNull(obj, "The validated object is null");
    }

    public static void isTrue(boolean z2, String str, long j2) {
        if (z2) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(j2);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static void notNull(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void isTrue(boolean z2, String str, double d2) {
        if (z2) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(d2);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public static void notEmpty(Object[] objArr) {
        notEmpty(objArr, "The validated array is empty");
    }

    public static void isTrue(boolean z2, String str) {
        if (!z2) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void notEmpty(Collection collection, String str) {
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void isTrue(boolean z2) {
        if (!z2) {
            throw new IllegalArgumentException("The validated expression is false");
        }
    }

    public static void noNullElements(Object[] objArr) {
        notNull(objArr);
        for (int i2 = 0; i2 < objArr.length; i2++) {
            if (objArr[i2] == null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("The validated array contains null element at index: ");
                stringBuffer.append(i2);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
    }

    public static void allElementsOfType(Collection collection, Class cls) {
        notNull(collection);
        notNull(cls);
        Iterator it = collection.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (!cls.isInstance(it.next())) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("The validated collection contains an element not of type ");
                stringBuffer.append(cls.getName());
                stringBuffer.append(" at index: ");
                stringBuffer.append(i2);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            i2++;
        }
    }

    public static void notEmpty(Collection collection) {
        notEmpty(collection, "The validated collection is empty");
    }

    public static void notEmpty(Map map, String str) {
        if (map == null || map.size() == 0) {
            throw new IllegalArgumentException(str);
        }
    }

    public static void noNullElements(Collection collection, String str) {
        notNull(collection);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                throw new IllegalArgumentException(str);
            }
        }
    }

    public static void notEmpty(Map map) {
        notEmpty(map, "The validated map is empty");
    }

    public static void notEmpty(String str, String str2) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException(str2);
        }
    }

    public static void notEmpty(String str) {
        notEmpty(str, "The validated string is empty");
    }

    public static void noNullElements(Collection collection) {
        notNull(collection);
        Iterator it = collection.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next() == null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("The validated collection contains null element at index: ");
                stringBuffer.append(i2);
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            i2++;
        }
    }
}
