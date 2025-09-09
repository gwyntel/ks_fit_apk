package com.google.zxing.oned.rss.expanded.decoders;

/* loaded from: classes3.dex */
final class DecodedChar extends DecodedObject {
    private final char value;

    DecodedChar(int i2, char c2) {
        super(i2);
        this.value = c2;
    }

    char b() {
        return this.value;
    }

    boolean c() {
        return this.value == '$';
    }
}
