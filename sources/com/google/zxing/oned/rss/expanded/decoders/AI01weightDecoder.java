package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

/* loaded from: classes3.dex */
abstract class AI01weightDecoder extends AI01decoder {
    AI01weightDecoder(BitArray bitArray) {
        super(bitArray);
    }

    protected abstract void e(StringBuilder sb, int i2);

    protected abstract int f(int i2);

    final void g(StringBuilder sb, int i2, int i3) {
        int iC = a().c(i2, i3);
        e(sb, iC);
        int iF = f(iC);
        int i4 = 100000;
        for (int i5 = 0; i5 < 5; i5++) {
            if (iF / i4 == 0) {
                sb.append('0');
            }
            i4 /= 10;
        }
        sb.append(iF);
    }
}
