package com.google.zxing.pdf417.decoder;

import com.google.zxing.ResultPoint;

/* loaded from: classes3.dex */
final class DetectionResultRowIndicatorColumn extends DetectionResultColumn {
    private final boolean isLeft;

    DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean z2) {
        super(boundingBox);
        this.isLeft = z2;
    }

    private void adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        BoundingBox boundingBoxA = a();
        ResultPoint resultPointH = this.isLeft ? boundingBoxA.h() : boundingBoxA.i();
        ResultPoint resultPointB = this.isLeft ? boundingBoxA.b() : boundingBoxA.c();
        int iE = e((int) resultPointB.getY());
        Codeword[] codewordArrD = d();
        int iC = -1;
        int i2 = 0;
        int iMax = 1;
        for (int iE2 = e((int) resultPointH.getY()); iE2 < iE; iE2++) {
            Codeword codeword = codewordArrD[iE2];
            if (codeword != null) {
                codeword.j();
                int iC2 = codeword.c() - iC;
                if (iC2 == 0) {
                    i2++;
                } else {
                    if (iC2 == 1) {
                        iMax = Math.max(iMax, i2);
                        iC = codeword.c();
                    } else if (codeword.c() >= barcodeMetadata.c()) {
                        codewordArrD[iE2] = null;
                    } else {
                        iC = codeword.c();
                    }
                    i2 = 1;
                }
            }
        }
    }

    private void removeIncorrectCodewords(Codeword[] codewordArr, BarcodeMetadata barcodeMetadata) {
        for (int i2 = 0; i2 < codewordArr.length; i2++) {
            Codeword codeword = codewordArr[i2];
            if (codeword != null) {
                int iE = codeword.e() % 30;
                int iC = codeword.c();
                if (iC > barcodeMetadata.c()) {
                    codewordArr[i2] = null;
                } else {
                    if (!this.isLeft) {
                        iC += 2;
                    }
                    int i3 = iC % 3;
                    if (i3 != 0) {
                        if (i3 != 1) {
                            if (i3 == 2 && iE + 1 != barcodeMetadata.a()) {
                                codewordArr[i2] = null;
                            }
                        } else if (iE / 3 != barcodeMetadata.b() || iE % 3 != barcodeMetadata.d()) {
                            codewordArr[i2] = null;
                        }
                    } else if ((iE * 3) + 1 != barcodeMetadata.e()) {
                        codewordArr[i2] = null;
                    }
                }
            }
        }
    }

    private void setRowNumbers() {
        for (Codeword codeword : d()) {
            if (codeword != null) {
                codeword.j();
            }
        }
    }

    void g(BarcodeMetadata barcodeMetadata) {
        Codeword[] codewordArrD = d();
        setRowNumbers();
        removeIncorrectCodewords(codewordArrD, barcodeMetadata);
        BoundingBox boundingBoxA = a();
        ResultPoint resultPointH = this.isLeft ? boundingBoxA.h() : boundingBoxA.i();
        ResultPoint resultPointB = this.isLeft ? boundingBoxA.b() : boundingBoxA.c();
        int iE = e((int) resultPointH.getY());
        int iE2 = e((int) resultPointB.getY());
        int iC = -1;
        int i2 = 0;
        int iMax = 1;
        while (iE < iE2) {
            Codeword codeword = codewordArrD[iE];
            if (codeword != null) {
                int iC2 = codeword.c() - iC;
                if (iC2 == 0) {
                    i2++;
                } else {
                    if (iC2 == 1) {
                        iMax = Math.max(iMax, i2);
                        iC = codeword.c();
                    } else if (iC2 < 0 || codeword.c() >= barcodeMetadata.c() || iC2 > iE) {
                        codewordArrD[iE] = null;
                    } else {
                        if (iMax > 2) {
                            iC2 *= iMax - 2;
                        }
                        boolean z2 = iC2 >= iE;
                        for (int i3 = 1; i3 <= iC2 && !z2; i3++) {
                            z2 = codewordArrD[iE - i3] != null;
                        }
                        if (z2) {
                            codewordArrD[iE] = null;
                        } else {
                            iC = codeword.c();
                        }
                    }
                    i2 = 1;
                }
            }
            iE++;
        }
    }

    BarcodeMetadata h() {
        Codeword[] codewordArrD = d();
        BarcodeValue barcodeValue = new BarcodeValue();
        BarcodeValue barcodeValue2 = new BarcodeValue();
        BarcodeValue barcodeValue3 = new BarcodeValue();
        BarcodeValue barcodeValue4 = new BarcodeValue();
        for (Codeword codeword : codewordArrD) {
            if (codeword != null) {
                codeword.j();
                int iE = codeword.e() % 30;
                int iC = codeword.c();
                if (!this.isLeft) {
                    iC += 2;
                }
                int i2 = iC % 3;
                if (i2 == 0) {
                    barcodeValue2.c((iE * 3) + 1);
                } else if (i2 == 1) {
                    barcodeValue4.c(iE / 3);
                    barcodeValue3.c(iE % 3);
                } else if (i2 == 2) {
                    barcodeValue.c(iE + 1);
                }
            }
        }
        if (barcodeValue.b().length == 0 || barcodeValue2.b().length == 0 || barcodeValue3.b().length == 0 || barcodeValue4.b().length == 0 || barcodeValue.b()[0] <= 0 || barcodeValue2.b()[0] + barcodeValue3.b()[0] < 3 || barcodeValue2.b()[0] + barcodeValue3.b()[0] > 90) {
            return null;
        }
        BarcodeMetadata barcodeMetadata = new BarcodeMetadata(barcodeValue.b()[0], barcodeValue2.b()[0], barcodeValue3.b()[0], barcodeValue4.b()[0]);
        removeIncorrectCodewords(codewordArrD, barcodeMetadata);
        return barcodeMetadata;
    }

    int[] i() {
        int iC;
        BarcodeMetadata barcodeMetadataH = h();
        if (barcodeMetadataH == null) {
            return null;
        }
        adjustIncompleteIndicatorColumnRowNumbers(barcodeMetadataH);
        int iC2 = barcodeMetadataH.c();
        int[] iArr = new int[iC2];
        for (Codeword codeword : d()) {
            if (codeword != null && (iC = codeword.c()) < iC2) {
                iArr[iC] = iArr[iC] + 1;
            }
        }
        return iArr;
    }

    boolean j() {
        return this.isLeft;
    }

    @Override // com.google.zxing.pdf417.decoder.DetectionResultColumn
    public String toString() {
        return "IsLeft: " + this.isLeft + '\n' + super.toString();
    }
}
