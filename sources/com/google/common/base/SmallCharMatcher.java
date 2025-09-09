package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.CharMatcher;
import java.util.BitSet;

@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class SmallCharMatcher extends CharMatcher.NamedFastMatcher {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5d;
    private final boolean containsZero;
    private final long filter;
    private final char[] table;

    private SmallCharMatcher(char[] cArr, long j2, boolean z2, String str) {
        super(str);
        this.table = cArr;
        this.filter = j2;
        this.containsZero = z2;
    }

    private boolean checkFilter(int i2) {
        return 1 == ((this.filter >> i2) & 1);
    }

    static int d(int i2) {
        if (i2 == 1) {
            return 2;
        }
        int iHighestOneBit = Integer.highestOneBit(i2 - 1) << 1;
        while (iHighestOneBit * 0.5d < i2) {
            iHighestOneBit <<= 1;
        }
        return iHighestOneBit;
    }

    static CharMatcher e(BitSet bitSet, String str) {
        int i2;
        int iCardinality = bitSet.cardinality();
        boolean z2 = bitSet.get(0);
        int iD = d(iCardinality);
        char[] cArr = new char[iD];
        int i3 = iD - 1;
        int iNextSetBit = bitSet.nextSetBit(0);
        long j2 = 0;
        while (iNextSetBit != -1) {
            long j3 = (1 << iNextSetBit) | j2;
            int iF = f(iNextSetBit);
            while (true) {
                i2 = iF & i3;
                if (cArr[i2] == 0) {
                    break;
                }
                iF = i2 + 1;
            }
            cArr[i2] = (char) iNextSetBit;
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
            j2 = j3;
        }
        return new SmallCharMatcher(cArr, j2, z2, str);
    }

    static int f(int i2) {
        return Integer.rotateLeft(i2 * C1, 15) * C2;
    }

    @Override // com.google.common.base.CharMatcher
    void c(BitSet bitSet) {
        if (this.containsZero) {
            bitSet.set(0);
        }
        for (char c2 : this.table) {
            if (c2 != 0) {
                bitSet.set(c2);
            }
        }
    }

    @Override // com.google.common.base.CharMatcher
    public boolean matches(char c2) {
        if (c2 == 0) {
            return this.containsZero;
        }
        if (!checkFilter(c2)) {
            return false;
        }
        int length = this.table.length - 1;
        int iF = f(c2) & length;
        int i2 = iF;
        do {
            char c3 = this.table[i2];
            if (c3 == 0) {
                return false;
            }
            if (c3 == c2) {
                return true;
            }
            i2 = (i2 + 1) & length;
        } while (i2 != iF);
        return false;
    }
}
