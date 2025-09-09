package com.alibaba.ailabs.tg.utils;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.collection.SimpleArrayMap;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes2.dex */
public class ObjectUtils {
    private ObjectUtils() {
    }

    public static String checkNoNull(Object obj) {
        return obj == null ? "" : obj instanceof String ? (String) obj : obj.toString();
    }

    public static <V extends Comparable> int compare(V v2, V v3) {
        if (v2 == null) {
            return v3 == null ? 0 : -1;
        }
        if (v3 == null) {
            return 1;
        }
        return v2.compareTo(v3);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isEquals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static Integer[] transformIntArray(int[] iArr) {
        Integer[] numArr = new Integer[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            numArr[i2] = Integer.valueOf(iArr[i2]);
        }
        return numArr;
    }

    public static Long[] transformLongArray(long[] jArr) {
        Long[] lArr = new Long[jArr.length];
        for (int i2 = 0; i2 < jArr.length; i2++) {
            lArr[i2] = Long.valueOf(jArr[i2]);
        }
        return lArr;
    }

    public static <V> boolean isEmpty(V[] vArr) {
        return vArr == null || vArr.length == 0;
    }

    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static int[] transformIntArray(Integer[] numArr) {
        int[] iArr = new int[numArr.length];
        for (int i2 = 0; i2 < numArr.length; i2++) {
            iArr[i2] = numArr[i2].intValue();
        }
        return iArr;
    }

    public static long[] transformLongArray(Long[] lArr) {
        long[] jArr = new long[lArr.length];
        for (int i2 = 0; i2 < lArr.length; i2++) {
            jArr[i2] = lArr[i2].longValue();
        }
        return jArr;
    }

    public static boolean isEmpty(SimpleArrayMap simpleArrayMap) {
        return simpleArrayMap == null || simpleArrayMap.isEmpty();
    }

    public static boolean isEmpty(SparseArray sparseArray) {
        return sparseArray == null || sparseArray.size() == 0;
    }

    public static boolean isEmpty(SparseBooleanArray sparseBooleanArray) {
        return sparseBooleanArray == null || sparseBooleanArray.size() == 0;
    }

    public static boolean isEmpty(SparseIntArray sparseIntArray) {
        return sparseIntArray == null || sparseIntArray.size() == 0;
    }
}
