package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes3.dex */
final class AI01393xDecoder extends AI01decoder {
    private static final int FIRST_THREE_DIGITS_SIZE = 10;
    private static final int HEADER_SIZE = 8;
    private static final int LAST_DIGIT_SIZE = 2;

    AI01393xDecoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException, FormatException {
        if (b().getSize() < 48) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb = new StringBuilder();
        c(sb, 8);
        int iC = a().c(48, 2);
        sb.append("(393");
        sb.append(iC);
        sb.append(')');
        int iC2 = a().c(50, 10);
        if (iC2 / 100 == 0) {
            sb.append('0');
        }
        if (iC2 / 10 == 0) {
            sb.append('0');
        }
        sb.append(iC2);
        sb.append(a().b(60, null).b());
        return sb.toString();
    }
}
