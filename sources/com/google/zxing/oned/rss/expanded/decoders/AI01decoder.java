package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

/* loaded from: classes3.dex */
abstract class AI01decoder extends AbstractExpandedDecoder {
    AI01decoder(BitArray bitArray) {
        super(bitArray);
    }

    private static void appendCheckDigit(StringBuilder sb, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < 13; i4++) {
            int iCharAt = sb.charAt(i4 + i2) - '0';
            if ((i4 & 1) == 0) {
                iCharAt *= 3;
            }
            i3 += iCharAt;
        }
        int i5 = 10 - (i3 % 10);
        sb.append(i5 != 10 ? i5 : 0);
    }

    final void c(StringBuilder sb, int i2) {
        sb.append("(01)");
        int length = sb.length();
        sb.append('9');
        d(sb, i2, length);
    }

    final void d(StringBuilder sb, int i2, int i3) {
        for (int i4 = 0; i4 < 4; i4++) {
            int iC = a().c((i4 * 10) + i2, 10);
            if (iC / 100 == 0) {
                sb.append('0');
            }
            if (iC / 10 == 0) {
                sb.append('0');
            }
            sb.append(iC);
        }
        appendCheckDigit(sb, i3);
    }
}
