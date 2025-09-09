package com.github.barteksc.pdfviewer.util;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class ArrayUtils {
    private ArrayUtils() {
    }

    public static String arrayToString(int[] iArr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i2 = 0; i2 < iArr.length; i2++) {
            sb.append(iArr[i2]);
            if (i2 != iArr.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static int[] calculateIndexesInDuplicateArray(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        if (iArr.length == 0) {
            return iArr2;
        }
        int i2 = 0;
        iArr2[0] = 0;
        for (int i3 = 1; i3 < iArr.length; i3++) {
            if (iArr[i3] != iArr[i3 - 1]) {
                i2++;
            }
            iArr2[i3] = i2;
        }
        return iArr2;
    }

    public static int[] deleteDuplicatedPages(int[] iArr) {
        ArrayList arrayList = new ArrayList();
        int length = iArr.length;
        int i2 = -1;
        int i3 = 0;
        while (i3 < length) {
            int i4 = iArr[i3];
            Integer numValueOf = Integer.valueOf(i4);
            if (i2 != i4) {
                arrayList.add(numValueOf);
            }
            i3++;
            i2 = i4;
        }
        int[] iArr2 = new int[arrayList.size()];
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            iArr2[i5] = ((Integer) arrayList.get(i5)).intValue();
        }
        return iArr2;
    }
}
