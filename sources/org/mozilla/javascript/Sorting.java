package org.mozilla.javascript;

import java.util.Comparator;

/* loaded from: classes5.dex */
public final class Sorting {
    private static final int SMALLSORT = 16;

    public static void hybridSort(Object[] objArr, Comparator<Object> comparator) {
        hybridSort(objArr, 0, objArr.length - 1, comparator, log2(objArr.length) * 2);
    }

    public static void insertionSort(Object[] objArr, Comparator<Object> comparator) {
        insertionSort(objArr, 0, objArr.length - 1, comparator);
    }

    private static int log2(int i2) {
        return (int) (Math.log10(i2) / Math.log10(2.0d));
    }

    public static int median(Object[] objArr, int i2, int i3, Comparator<Object> comparator) {
        int i4 = ((i3 - i2) / 2) + i2;
        int i5 = comparator.compare(objArr[i2], objArr[i4]) > 0 ? i4 : i2;
        if (comparator.compare(objArr[i5], objArr[i3]) > 0) {
            i5 = i3;
        }
        return i5 == i2 ? comparator.compare(objArr[i4], objArr[i3]) < 0 ? i4 : i3 : i5 == i4 ? comparator.compare(objArr[i2], objArr[i3]) < 0 ? i2 : i3 : comparator.compare(objArr[i2], objArr[i4]) < 0 ? i2 : i4;
    }

    private static int partition(Object[] objArr, int i2, int i3, Comparator<Object> comparator) {
        int iMedian = median(objArr, i2, i3, comparator);
        Object obj = objArr[iMedian];
        objArr[iMedian] = objArr[i2];
        objArr[i2] = obj;
        int i4 = i3 + 1;
        int i5 = i2;
        while (true) {
            i5++;
            if (comparator.compare(objArr[i5], obj) >= 0 || i5 == i3) {
                do {
                    i4--;
                    if (comparator.compare(objArr[i4], obj) < 0) {
                        break;
                    }
                } while (i4 != i2);
                if (i5 >= i4) {
                    swap(objArr, i2, i4);
                    return i4;
                }
                swap(objArr, i5, i4);
            }
        }
    }

    private static void swap(Object[] objArr, int i2, int i3) {
        Object obj = objArr[i2];
        objArr[i2] = objArr[i3];
        objArr[i3] = obj;
    }

    private static void hybridSort(Object[] objArr, int i2, int i3, Comparator<Object> comparator, int i4) {
        if (i2 < i3) {
            if (i4 == 0 || i3 - i2 <= 16) {
                insertionSort(objArr, i2, i3, comparator);
                return;
            }
            int iPartition = partition(objArr, i2, i3, comparator);
            int i5 = i4 - 1;
            hybridSort(objArr, i2, iPartition, comparator, i5);
            hybridSort(objArr, iPartition + 1, i3, comparator, i5);
        }
    }

    public static void insertionSort(Object[] objArr, int i2, int i3, Comparator<Object> comparator) {
        for (int i4 = i2; i4 <= i3; i4++) {
            Object obj = objArr[i4];
            int i5 = i4 - 1;
            while (i5 >= i2 && comparator.compare(objArr[i5], obj) > 0) {
                objArr[i5 + 1] = objArr[i5];
                i5--;
            }
            objArr[i5 + 1] = obj;
        }
    }
}
