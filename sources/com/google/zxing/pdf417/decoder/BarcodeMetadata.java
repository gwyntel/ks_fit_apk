package com.google.zxing.pdf417.decoder;

/* loaded from: classes3.dex */
final class BarcodeMetadata {
    private final int columnCount;
    private final int errorCorrectionLevel;
    private final int rowCount;
    private final int rowCountLowerPart;
    private final int rowCountUpperPart;

    BarcodeMetadata(int i2, int i3, int i4, int i5) {
        this.columnCount = i2;
        this.errorCorrectionLevel = i5;
        this.rowCountUpperPart = i3;
        this.rowCountLowerPart = i4;
        this.rowCount = i3 + i4;
    }

    int a() {
        return this.columnCount;
    }

    int b() {
        return this.errorCorrectionLevel;
    }

    int c() {
        return this.rowCount;
    }

    int d() {
        return this.rowCountLowerPart;
    }

    int e() {
        return this.rowCountUpperPart;
    }
}
