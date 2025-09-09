package com.google.zxing.oned.rss.expanded.decoders;

/* loaded from: classes3.dex */
abstract class DecodedObject {
    private final int newPosition;

    DecodedObject(int i2) {
        this.newPosition = i2;
    }

    final int a() {
        return this.newPosition;
    }
}
