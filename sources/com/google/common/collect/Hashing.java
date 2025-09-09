package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Hashing {
    private static final long C1 = -862048943;
    private static final long C2 = 461845907;
    private static final int MAX_TABLE_SIZE = 1073741824;

    private Hashing() {
    }

    static int a(int i2, double d2) {
        int iMax = Math.max(i2, 2);
        int iHighestOneBit = Integer.highestOneBit(iMax);
        if (iMax <= ((int) (d2 * iHighestOneBit))) {
            return iHighestOneBit;
        }
        int i3 = iHighestOneBit << 1;
        if (i3 > 0) {
            return i3;
        }
        return 1073741824;
    }

    static boolean b(int i2, int i3, double d2) {
        return ((double) i2) > d2 * ((double) i3) && i3 < 1073741824;
    }

    static int c(int i2) {
        return (int) (Integer.rotateLeft((int) (i2 * C1), 15) * C2);
    }

    static int d(Object obj) {
        return c(obj == null ? 0 : obj.hashCode());
    }
}
