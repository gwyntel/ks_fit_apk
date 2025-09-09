package com.google.zxing.pdf417.decoder;

/* loaded from: classes3.dex */
final class Codeword {
    private static final int BARCODE_ROW_UNKNOWN = -1;
    private final int bucket;
    private final int endX;
    private int rowNumber = -1;
    private final int startX;
    private final int value;

    Codeword(int i2, int i3, int i4, int i5) {
        this.startX = i2;
        this.endX = i3;
        this.bucket = i4;
        this.value = i5;
    }

    int a() {
        return this.bucket;
    }

    int b() {
        return this.endX;
    }

    int c() {
        return this.rowNumber;
    }

    int d() {
        return this.startX;
    }

    int e() {
        return this.value;
    }

    int f() {
        return this.endX - this.startX;
    }

    boolean g() {
        return h(this.rowNumber);
    }

    boolean h(int i2) {
        return i2 != -1 && this.bucket == (i2 % 3) * 3;
    }

    void i(int i2) {
        this.rowNumber = i2;
    }

    void j() {
        this.rowNumber = ((this.value / 30) * 3) + (this.bucket / 3);
    }

    public String toString() {
        return this.rowNumber + "|" + this.value;
    }
}
