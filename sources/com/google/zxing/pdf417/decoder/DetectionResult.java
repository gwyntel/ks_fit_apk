package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.util.Formatter;

/* loaded from: classes3.dex */
final class DetectionResult {
    private static final int ADJUST_ROW_NUMBER_SKIP = 2;
    private final int barcodeColumnCount;
    private final BarcodeMetadata barcodeMetadata;
    private BoundingBox boundingBox;
    private final DetectionResultColumn[] detectionResultColumns;

    DetectionResult(BarcodeMetadata barcodeMetadata, BoundingBox boundingBox) {
        this.barcodeMetadata = barcodeMetadata;
        int iA = barcodeMetadata.a();
        this.barcodeColumnCount = iA;
        this.boundingBox = boundingBox;
        this.detectionResultColumns = new DetectionResultColumn[iA + 2];
    }

    private void adjustIndicatorColumnRowNumbers(DetectionResultColumn detectionResultColumn) {
        if (detectionResultColumn != null) {
            ((DetectionResultRowIndicatorColumn) detectionResultColumn).g(this.barcodeMetadata);
        }
    }

    private static boolean adjustRowNumber(Codeword codeword, Codeword codeword2) {
        if (codeword2 == null || !codeword2.g() || codeword2.a() != codeword.a()) {
            return false;
        }
        codeword.i(codeword2.c());
        return true;
    }

    private static int adjustRowNumberIfValid(int i2, int i3, Codeword codeword) {
        if (codeword == null || codeword.g()) {
            return i3;
        }
        if (!codeword.h(i2)) {
            return i3 + 1;
        }
        codeword.i(i2);
        return 0;
    }

    private int adjustRowNumbers() {
        int iAdjustRowNumbersByRow = adjustRowNumbersByRow();
        if (iAdjustRowNumbersByRow == 0) {
            return 0;
        }
        for (int i2 = 1; i2 < this.barcodeColumnCount + 1; i2++) {
            Codeword[] codewordArrD = this.detectionResultColumns[i2].d();
            for (int i3 = 0; i3 < codewordArrD.length; i3++) {
                Codeword codeword = codewordArrD[i3];
                if (codeword != null && !codeword.g()) {
                    adjustRowNumbers(i2, i3, codewordArrD);
                }
            }
        }
        return iAdjustRowNumbersByRow;
    }

    private int adjustRowNumbersByRow() {
        adjustRowNumbersFromBothRI();
        return adjustRowNumbersFromLRI() + adjustRowNumbersFromRRI();
    }

    private void adjustRowNumbersFromBothRI() {
        DetectionResultColumn[] detectionResultColumnArr = this.detectionResultColumns;
        DetectionResultColumn detectionResultColumn = detectionResultColumnArr[0];
        if (detectionResultColumn == null || detectionResultColumnArr[this.barcodeColumnCount + 1] == null) {
            return;
        }
        Codeword[] codewordArrD = detectionResultColumn.d();
        Codeword[] codewordArrD2 = this.detectionResultColumns[this.barcodeColumnCount + 1].d();
        for (int i2 = 0; i2 < codewordArrD.length; i2++) {
            Codeword codeword = codewordArrD[i2];
            if (codeword != null && codewordArrD2[i2] != null && codeword.c() == codewordArrD2[i2].c()) {
                for (int i3 = 1; i3 <= this.barcodeColumnCount; i3++) {
                    Codeword codeword2 = this.detectionResultColumns[i3].d()[i2];
                    if (codeword2 != null) {
                        codeword2.i(codewordArrD[i2].c());
                        if (!codeword2.g()) {
                            this.detectionResultColumns[i3].d()[i2] = null;
                        }
                    }
                }
            }
        }
    }

    private int adjustRowNumbersFromLRI() {
        DetectionResultColumn detectionResultColumn = this.detectionResultColumns[0];
        if (detectionResultColumn == null) {
            return 0;
        }
        Codeword[] codewordArrD = detectionResultColumn.d();
        int i2 = 0;
        for (int i3 = 0; i3 < codewordArrD.length; i3++) {
            Codeword codeword = codewordArrD[i3];
            if (codeword != null) {
                int iC = codeword.c();
                int iAdjustRowNumberIfValid = 0;
                for (int i4 = 1; i4 < this.barcodeColumnCount + 1 && iAdjustRowNumberIfValid < 2; i4++) {
                    Codeword codeword2 = this.detectionResultColumns[i4].d()[i3];
                    if (codeword2 != null) {
                        iAdjustRowNumberIfValid = adjustRowNumberIfValid(iC, iAdjustRowNumberIfValid, codeword2);
                        if (!codeword2.g()) {
                            i2++;
                        }
                    }
                }
            }
        }
        return i2;
    }

    private int adjustRowNumbersFromRRI() {
        DetectionResultColumn[] detectionResultColumnArr = this.detectionResultColumns;
        int i2 = this.barcodeColumnCount;
        if (detectionResultColumnArr[i2 + 1] == null) {
            return 0;
        }
        Codeword[] codewordArrD = detectionResultColumnArr[i2 + 1].d();
        int i3 = 0;
        for (int i4 = 0; i4 < codewordArrD.length; i4++) {
            Codeword codeword = codewordArrD[i4];
            if (codeword != null) {
                int iC = codeword.c();
                int iAdjustRowNumberIfValid = 0;
                for (int i5 = this.barcodeColumnCount + 1; i5 > 0 && iAdjustRowNumberIfValid < 2; i5--) {
                    Codeword codeword2 = this.detectionResultColumns[i5].d()[i4];
                    if (codeword2 != null) {
                        iAdjustRowNumberIfValid = adjustRowNumberIfValid(iC, iAdjustRowNumberIfValid, codeword2);
                        if (!codeword2.g()) {
                            i3++;
                        }
                    }
                }
            }
        }
        return i3;
    }

    int a() {
        return this.barcodeColumnCount;
    }

    int b() {
        return this.barcodeMetadata.b();
    }

    int c() {
        return this.barcodeMetadata.c();
    }

    BoundingBox d() {
        return this.boundingBox;
    }

    DetectionResultColumn e(int i2) {
        return this.detectionResultColumns[i2];
    }

    DetectionResultColumn[] f() {
        adjustIndicatorColumnRowNumbers(this.detectionResultColumns[0]);
        adjustIndicatorColumnRowNumbers(this.detectionResultColumns[this.barcodeColumnCount + 1]);
        int i2 = PDF417Common.MAX_CODEWORDS_IN_BARCODE;
        while (true) {
            int iAdjustRowNumbers = adjustRowNumbers();
            if (iAdjustRowNumbers <= 0 || iAdjustRowNumbers >= i2) {
                break;
            }
            i2 = iAdjustRowNumbers;
        }
        return this.detectionResultColumns;
    }

    void g(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    void h(int i2, DetectionResultColumn detectionResultColumn) {
        this.detectionResultColumns[i2] = detectionResultColumn;
    }

    public String toString() {
        DetectionResultColumn[] detectionResultColumnArr = this.detectionResultColumns;
        DetectionResultColumn detectionResultColumn = detectionResultColumnArr[0];
        if (detectionResultColumn == null) {
            detectionResultColumn = detectionResultColumnArr[this.barcodeColumnCount + 1];
        }
        Formatter formatter = new Formatter();
        for (int i2 = 0; i2 < detectionResultColumn.d().length; i2++) {
            try {
                formatter.format("CW %3d:", Integer.valueOf(i2));
                for (int i3 = 0; i3 < this.barcodeColumnCount + 2; i3++) {
                    DetectionResultColumn detectionResultColumn2 = this.detectionResultColumns[i3];
                    if (detectionResultColumn2 == null) {
                        formatter.format("    |   ", new Object[0]);
                    } else {
                        Codeword codeword = detectionResultColumn2.d()[i2];
                        if (codeword == null) {
                            formatter.format("    |   ", new Object[0]);
                        } else {
                            formatter.format(" %3d|%3d", Integer.valueOf(codeword.c()), Integer.valueOf(codeword.e()));
                        }
                    }
                }
                formatter.format("%n", new Object[0]);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        formatter.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    private void adjustRowNumbers(int i2, int i3, Codeword[] codewordArr) {
        Codeword codeword = codewordArr[i3];
        Codeword[] codewordArrD = this.detectionResultColumns[i2 - 1].d();
        DetectionResultColumn detectionResultColumn = this.detectionResultColumns[i2 + 1];
        Codeword[] codewordArrD2 = detectionResultColumn != null ? detectionResultColumn.d() : codewordArrD;
        Codeword[] codewordArr2 = new Codeword[14];
        codewordArr2[2] = codewordArrD[i3];
        codewordArr2[3] = codewordArrD2[i3];
        if (i3 > 0) {
            int i4 = i3 - 1;
            codewordArr2[0] = codewordArr[i4];
            codewordArr2[4] = codewordArrD[i4];
            codewordArr2[5] = codewordArrD2[i4];
        }
        if (i3 > 1) {
            int i5 = i3 - 2;
            codewordArr2[8] = codewordArr[i5];
            codewordArr2[10] = codewordArrD[i5];
            codewordArr2[11] = codewordArrD2[i5];
        }
        if (i3 < codewordArr.length - 1) {
            int i6 = i3 + 1;
            codewordArr2[1] = codewordArr[i6];
            codewordArr2[6] = codewordArrD[i6];
            codewordArr2[7] = codewordArrD2[i6];
        }
        if (i3 < codewordArr.length - 2) {
            int i7 = i3 + 2;
            codewordArr2[9] = codewordArr[i7];
            codewordArr2[12] = codewordArrD[i7];
            codewordArr2[13] = codewordArrD2[i7];
        }
        for (int i8 = 0; i8 < 14 && !adjustRowNumber(codeword, codewordArr2[i8]); i8++) {
        }
    }
}
