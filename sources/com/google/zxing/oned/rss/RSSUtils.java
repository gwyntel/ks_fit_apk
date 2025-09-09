package com.google.zxing.oned.rss;

/* loaded from: classes3.dex */
public final class RSSUtils {
    private RSSUtils() {
    }

    private static int combins(int i2, int i3) {
        int i4 = i2 - i3;
        if (i4 > i3) {
            i4 = i3;
            i3 = i4;
        }
        int i5 = 1;
        int i6 = 1;
        while (i2 > i3) {
            i5 *= i2;
            if (i6 <= i4) {
                i5 /= i6;
                i6++;
            }
            i2--;
        }
        while (i6 <= i4) {
            i5 /= i6;
            i6++;
        }
        return i5;
    }

    public static int getRSSvalue(int[] iArr, int i2, boolean z2) {
        int[] iArr2 = iArr;
        int i3 = 0;
        for (int i4 : iArr2) {
            i3 += i4;
        }
        int length = iArr2.length;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            int i8 = length - 1;
            if (i5 >= i8) {
                return i6;
            }
            int i9 = 1 << i5;
            i7 |= i9;
            int i10 = 1;
            while (i10 < iArr2[i5]) {
                int i11 = i3 - i10;
                int i12 = length - i5;
                int i13 = i12 - 2;
                int iCombins = combins(i11 - 1, i13);
                if (z2 && i7 == 0) {
                    int i14 = i12 - 1;
                    if (i11 - i14 >= i14) {
                        iCombins -= combins(i11 - i12, i13);
                    }
                }
                if (i12 - 1 > 1) {
                    int iCombins2 = 0;
                    for (int i15 = i11 - i13; i15 > i2; i15--) {
                        iCombins2 += combins((i11 - i15) - 1, i12 - 3);
                    }
                    iCombins -= iCombins2 * (i8 - i5);
                } else if (i11 > i2) {
                    iCombins--;
                }
                i6 += iCombins;
                i10++;
                i7 &= ~i9;
                iArr2 = iArr;
            }
            i3 -= i10;
            i5++;
            iArr2 = iArr;
        }
    }
}
