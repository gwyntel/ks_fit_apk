package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import java.util.List;

/* loaded from: classes3.dex */
final class BitArrayBuilder {
    private BitArrayBuilder() {
    }

    static BitArray a(List list) {
        int size = list.size() << 1;
        int i2 = size - 1;
        if (((ExpandedPair) list.get(list.size() - 1)).c() == null) {
            i2 = size - 2;
        }
        BitArray bitArray = new BitArray(i2 * 12);
        int i3 = 0;
        int value = ((ExpandedPair) list.get(0)).c().getValue();
        for (int i4 = 11; i4 >= 0; i4--) {
            if (((1 << i4) & value) != 0) {
                bitArray.set(i3);
            }
            i3++;
        }
        for (int i5 = 1; i5 < list.size(); i5++) {
            ExpandedPair expandedPair = (ExpandedPair) list.get(i5);
            int value2 = expandedPair.b().getValue();
            for (int i6 = 11; i6 >= 0; i6--) {
                if (((1 << i6) & value2) != 0) {
                    bitArray.set(i3);
                }
                i3++;
            }
            if (expandedPair.c() != null) {
                int value3 = expandedPair.c().getValue();
                for (int i7 = 11; i7 >= 0; i7--) {
                    if (((1 << i7) & value3) != 0) {
                        bitArray.set(i3);
                    }
                    i3++;
                }
            }
        }
        return bitArray;
    }
}
