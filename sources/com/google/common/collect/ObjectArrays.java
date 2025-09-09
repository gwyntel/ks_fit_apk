package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ObjectArrays {
    private ObjectArrays() {
    }

    static Object a(Object obj, int i2) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException("at index " + i2);
    }

    static Object[] b(Object... objArr) {
        c(objArr, objArr.length);
        return objArr;
    }

    static Object[] c(Object[] objArr, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            a(objArr[i3], i3);
        }
        return objArr;
    }

    @GwtIncompatible
    public static <T> T[] concat(T[] tArr, T[] tArr2, Class<T> cls) {
        T[] tArr3 = (T[]) newArray(cls, tArr.length + tArr2.length);
        System.arraycopy(tArr, 0, tArr3, 0, tArr.length);
        System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        return tArr3;
    }

    static Object[] d(Collection collection) {
        return fillArray(collection, new Object[collection.size()]);
    }

    static Object[] e(Collection collection, Object[] objArr) {
        int size = collection.size();
        if (objArr.length < size) {
            objArr = newArray(objArr, size);
        }
        fillArray(collection, objArr);
        if (objArr.length > size) {
            objArr[size] = null;
        }
        return objArr;
    }

    static Object[] f(Object[] objArr, int i2, int i3, Object[] objArr2) {
        Preconditions.checkPositionIndexes(i2, i2 + i3, objArr.length);
        if (objArr2.length < i3) {
            objArr2 = newArray(objArr2, i3);
        } else if (objArr2.length > i3) {
            objArr2[i3] = null;
        }
        System.arraycopy(objArr, i2, objArr2, 0, i3);
        return objArr2;
    }

    @CanIgnoreReturnValue
    private static Object[] fillArray(Iterable<?> iterable, Object[] objArr) {
        Iterator<?> it = iterable.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            objArr[i2] = it.next();
            i2++;
        }
        return objArr;
    }

    @GwtIncompatible
    public static <T> T[] newArray(Class<T> cls, int i2) {
        return (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i2));
    }

    public static <T> T[] newArray(T[] tArr, int i2) {
        return (T[]) Platform.c(tArr, i2);
    }

    public static <T> T[] concat(@ParametricNullness T t2, T[] tArr) {
        T[] tArr2 = (T[]) newArray(tArr, tArr.length + 1);
        tArr2[0] = t2;
        System.arraycopy(tArr, 0, tArr2, 1, tArr.length);
        return tArr2;
    }

    public static <T> T[] concat(T[] tArr, @ParametricNullness T t2) {
        T[] tArr2 = (T[]) Arrays.copyOf(tArr, tArr.length + 1);
        tArr2[tArr.length] = t2;
        return tArr2;
    }
}
