package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;

/* loaded from: classes3.dex */
final class DecodedNumeric extends DecodedObject {
    private final int firstDigit;
    private final int secondDigit;

    DecodedNumeric(int i2, int i3, int i4) throws FormatException {
        super(i2);
        if (i3 < 0 || i3 > 10 || i4 < 0 || i4 > 10) {
            throw FormatException.getFormatInstance();
        }
        this.firstDigit = i3;
        this.secondDigit = i4;
    }

    int b() {
        return this.firstDigit;
    }

    int c() {
        return this.secondDigit;
    }

    boolean d() {
        return this.firstDigit == 10;
    }

    boolean e() {
        return this.secondDigit == 10;
    }
}
