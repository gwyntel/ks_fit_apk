package com.google.zxing.oned.rss;

/* loaded from: classes3.dex */
final class Pair extends DataCharacter {
    private int count;
    private final FinderPattern finderPattern;

    Pair(int i2, int i3, FinderPattern finderPattern) {
        super(i2, i3);
        this.finderPattern = finderPattern;
    }

    int a() {
        return this.count;
    }

    FinderPattern b() {
        return this.finderPattern;
    }

    void c() {
        this.count++;
    }
}
