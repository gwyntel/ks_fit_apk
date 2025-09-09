package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;

/* loaded from: classes3.dex */
public final class PDF417ScanningDecoder {
    private static final int CODEWORD_SKEW_SIZE = 2;
    private static final int MAX_EC_CODEWORDS = 512;
    private static final int MAX_ERRORS = 3;
    private static final ErrorCorrection errorCorrection = new ErrorCorrection();

    private PDF417ScanningDecoder() {
    }

    private static BoundingBox adjustBoundingBox(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn) throws NotFoundException {
        int[] iArrI;
        if (detectionResultRowIndicatorColumn == null || (iArrI = detectionResultRowIndicatorColumn.i()) == null) {
            return null;
        }
        int max = getMax(iArrI);
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArrI) {
            i3 += max - i4;
            if (i4 > 0) {
                break;
            }
        }
        Codeword[] codewordArrD = detectionResultRowIndicatorColumn.d();
        for (int i5 = 0; i3 > 0 && codewordArrD[i5] == null; i5++) {
            i3--;
        }
        for (int length = iArrI.length - 1; length >= 0; length--) {
            int i6 = iArrI[length];
            i2 += max - i6;
            if (i6 > 0) {
                break;
            }
        }
        for (int length2 = codewordArrD.length - 1; i2 > 0 && codewordArrD[length2] == null; length2--) {
            i2--;
        }
        return detectionResultRowIndicatorColumn.a().a(i3, i2, detectionResultRowIndicatorColumn.j());
    }

    private static void adjustCodewordCount(DetectionResult detectionResult, BarcodeValue[][] barcodeValueArr) throws NotFoundException {
        BarcodeValue barcodeValue = barcodeValueArr[0][1];
        int[] iArrB = barcodeValue.b();
        int iA = (detectionResult.a() * detectionResult.c()) - getNumberOfECCodeWords(detectionResult.b());
        if (iArrB.length != 0) {
            if (iArrB[0] != iA) {
                barcodeValue.c(iA);
            }
        } else {
            if (iA <= 0 || iA > 928) {
                throw NotFoundException.getNotFoundInstance();
            }
            barcodeValue.c(iA);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0022, code lost:
    
        r0 = -r0;
        r8 = !r8;
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0022, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0022, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0022, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int adjustCodewordStartColumn(com.google.zxing.common.BitMatrix r5, int r6, int r7, boolean r8, int r9, int r10) {
        /*
            if (r8 == 0) goto L4
            r0 = -1
            goto L5
        L4:
            r0 = 1
        L5:
            r1 = 0
            r2 = r9
        L7:
            r3 = 2
            if (r1 >= r3) goto L28
        La:
            if (r8 == 0) goto Lf
            if (r2 < r6) goto L22
            goto L11
        Lf:
            if (r2 >= r7) goto L22
        L11:
            boolean r4 = r5.get(r2, r10)
            if (r8 != r4) goto L22
            int r4 = r9 - r2
            int r4 = java.lang.Math.abs(r4)
            if (r4 <= r3) goto L20
            return r9
        L20:
            int r2 = r2 + r0
            goto La
        L22:
            int r0 = -r0
            r8 = r8 ^ 1
            int r1 = r1 + 1
            goto L7
        L28:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.adjustCodewordStartColumn(com.google.zxing.common.BitMatrix, int, int, boolean, int, int):int");
    }

    private static boolean checkCodewordSkew(int i2, int i3, int i4) {
        return i3 + (-2) <= i2 && i2 <= i4 + 2;
    }

    private static int correctErrors(int[] iArr, int[] iArr2, int i2) throws ChecksumException {
        if ((iArr2 == null || iArr2.length <= (i2 / 2) + 3) && i2 >= 0 && i2 <= 512) {
            return errorCorrection.decode(iArr, i2, iArr2);
        }
        throw ChecksumException.getChecksumInstance();
    }

    private static BarcodeValue[][] createBarcodeMatrix(DetectionResult detectionResult) {
        int iC;
        BarcodeValue[][] barcodeValueArr = (BarcodeValue[][]) Array.newInstance((Class<?>) BarcodeValue.class, detectionResult.c(), detectionResult.a() + 2);
        for (BarcodeValue[] barcodeValueArr2 : barcodeValueArr) {
            int i2 = 0;
            while (true) {
                if (i2 < barcodeValueArr2.length) {
                    barcodeValueArr2[i2] = new BarcodeValue();
                    i2++;
                }
            }
        }
        int i3 = 0;
        for (DetectionResultColumn detectionResultColumn : detectionResult.f()) {
            if (detectionResultColumn != null) {
                for (Codeword codeword : detectionResultColumn.d()) {
                    if (codeword != null && (iC = codeword.c()) >= 0 && iC < barcodeValueArr.length) {
                        barcodeValueArr[iC][i3].c(codeword.e());
                    }
                }
            }
            i3++;
        }
        return barcodeValueArr;
    }

    private static DecoderResult createDecoderResult(DetectionResult detectionResult) throws NotFoundException, ChecksumException, FormatException {
        BarcodeValue[][] barcodeValueArrCreateBarcodeMatrix = createBarcodeMatrix(detectionResult);
        adjustCodewordCount(detectionResult, barcodeValueArrCreateBarcodeMatrix);
        ArrayList arrayList = new ArrayList();
        int[] iArr = new int[detectionResult.c() * detectionResult.a()];
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i2 = 0; i2 < detectionResult.c(); i2++) {
            int i3 = 0;
            while (i3 < detectionResult.a()) {
                int i4 = i3 + 1;
                int[] iArrB = barcodeValueArrCreateBarcodeMatrix[i2][i4].b();
                int iA = (detectionResult.a() * i2) + i3;
                if (iArrB.length == 0) {
                    arrayList.add(Integer.valueOf(iA));
                } else if (iArrB.length == 1) {
                    iArr[iA] = iArrB[0];
                } else {
                    arrayList3.add(Integer.valueOf(iA));
                    arrayList2.add(iArrB);
                }
                i3 = i4;
            }
        }
        int size = arrayList2.size();
        int[][] iArr2 = new int[size][];
        for (int i5 = 0; i5 < size; i5++) {
            iArr2[i5] = (int[]) arrayList2.get(i5);
        }
        return createDecoderResultFromAmbiguousValues(detectionResult.b(), iArr, PDF417Common.toIntArray(arrayList), PDF417Common.toIntArray(arrayList3), iArr2);
    }

    private static DecoderResult createDecoderResultFromAmbiguousValues(int i2, int[] iArr, int[] iArr2, int[] iArr3, int[][] iArr4) throws ChecksumException, FormatException {
        int length = iArr3.length;
        int[] iArr5 = new int[length];
        int i3 = 100;
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                throw ChecksumException.getChecksumInstance();
            }
            for (int i5 = 0; i5 < length; i5++) {
                iArr[iArr3[i5]] = iArr4[i5][iArr5[i5]];
            }
            try {
                return decodeCodewords(iArr, i2, iArr2);
            } catch (ChecksumException unused) {
                if (length == 0) {
                    throw ChecksumException.getChecksumInstance();
                }
                int i6 = 0;
                while (true) {
                    if (i6 >= length) {
                        break;
                    }
                    int i7 = iArr5[i6];
                    if (i7 < iArr4[i6].length - 1) {
                        iArr5[i6] = i7 + 1;
                        break;
                    }
                    iArr5[i6] = 0;
                    if (i6 == length - 1) {
                        throw ChecksumException.getChecksumInstance();
                    }
                    i6++;
                }
                i3 = i4;
            }
        }
    }

    public static DecoderResult decode(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i2, int i3) throws NotFoundException, ChecksumException, FormatException {
        int i4;
        int i5;
        int i6;
        int i7;
        DetectionResultRowIndicatorColumn rowIndicatorColumn = null;
        DetectionResultRowIndicatorColumn rowIndicatorColumn2 = null;
        DetectionResult detectionResultMerge = null;
        BoundingBox boundingBox = new BoundingBox(bitMatrix, resultPoint, resultPoint2, resultPoint3, resultPoint4);
        for (int i8 = 0; i8 < 2; i8++) {
            if (resultPoint != null) {
                rowIndicatorColumn = getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint, true, i2, i3);
            }
            if (resultPoint3 != null) {
                rowIndicatorColumn2 = getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint3, false, i2, i3);
            }
            detectionResultMerge = merge(rowIndicatorColumn, rowIndicatorColumn2);
            if (detectionResultMerge == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (i8 != 0 || detectionResultMerge.d() == null || (detectionResultMerge.d().g() >= boundingBox.g() && detectionResultMerge.d().e() <= boundingBox.e())) {
                detectionResultMerge.g(boundingBox);
                break;
            }
            boundingBox = detectionResultMerge.d();
        }
        int iA = detectionResultMerge.a() + 1;
        detectionResultMerge.h(0, rowIndicatorColumn);
        detectionResultMerge.h(iA, rowIndicatorColumn2);
        boolean z2 = rowIndicatorColumn != null;
        int iMin = i2;
        int iMax = i3;
        for (int i9 = 1; i9 <= iA; i9++) {
            int i10 = z2 ? i9 : iA - i9;
            if (detectionResultMerge.e(i10) == null) {
                DetectionResultColumn detectionResultRowIndicatorColumn = (i10 == 0 || i10 == iA) ? new DetectionResultRowIndicatorColumn(boundingBox, i10 == 0) : new DetectionResultColumn(boundingBox);
                detectionResultMerge.h(i10, detectionResultRowIndicatorColumn);
                int i11 = -1;
                int iG = boundingBox.g();
                int i12 = -1;
                while (iG <= boundingBox.e()) {
                    int startColumn = getStartColumn(detectionResultMerge, i10, iG, z2);
                    if (startColumn >= 0 && startColumn <= boundingBox.d()) {
                        i7 = startColumn;
                    } else if (i12 != i11) {
                        i7 = i12;
                    } else {
                        i4 = i12;
                        i5 = iG;
                        i6 = i11;
                        i12 = i4;
                        iG = i5 + 1;
                        i11 = i6;
                    }
                    i4 = i12;
                    int i13 = iG;
                    i6 = i11;
                    Codeword codewordDetectCodeword = detectCodeword(bitMatrix, boundingBox.f(), boundingBox.d(), z2, i7, i13, iMin, iMax);
                    i5 = i13;
                    if (codewordDetectCodeword != null) {
                        detectionResultRowIndicatorColumn.f(i5, codewordDetectCodeword);
                        iMin = Math.min(iMin, codewordDetectCodeword.f());
                        iMax = Math.max(iMax, codewordDetectCodeword.f());
                        i12 = i7;
                    } else {
                        i12 = i4;
                    }
                    iG = i5 + 1;
                    i11 = i6;
                }
            }
        }
        return createDecoderResult(detectionResultMerge);
    }

    private static DecoderResult decodeCodewords(int[] iArr, int i2, int[] iArr2) throws ChecksumException, FormatException {
        if (iArr.length == 0) {
            throw FormatException.getFormatInstance();
        }
        int i3 = 1 << (i2 + 1);
        int iCorrectErrors = correctErrors(iArr, iArr2, i3);
        verifyCodewordCount(iArr, i3);
        DecoderResult decoderResultA = DecodedBitStreamParser.a(iArr, String.valueOf(i2));
        decoderResultA.setErrorsCorrected(Integer.valueOf(iCorrectErrors));
        decoderResultA.setErasures(Integer.valueOf(iArr2.length));
        return decoderResultA;
    }

    private static Codeword detectCodeword(BitMatrix bitMatrix, int i2, int i3, boolean z2, int i4, int i5, int i6, int i7) {
        int i8;
        int iA;
        int codeword;
        int iAdjustCodewordStartColumn = adjustCodewordStartColumn(bitMatrix, i2, i3, z2, i4, i5);
        int[] moduleBitCount = getModuleBitCount(bitMatrix, i2, i3, z2, iAdjustCodewordStartColumn, i5);
        if (moduleBitCount == null) {
            return null;
        }
        int iSum = MathUtils.sum(moduleBitCount);
        if (z2) {
            i8 = iAdjustCodewordStartColumn + iSum;
        } else {
            for (int i9 = 0; i9 < moduleBitCount.length / 2; i9++) {
                int i10 = moduleBitCount[i9];
                moduleBitCount[i9] = moduleBitCount[(moduleBitCount.length - 1) - i9];
                moduleBitCount[(moduleBitCount.length - 1) - i9] = i10;
            }
            iAdjustCodewordStartColumn -= iSum;
            i8 = iAdjustCodewordStartColumn;
        }
        if (checkCodewordSkew(iSum, i6, i7) && (codeword = PDF417Common.getCodeword((iA = PDF417CodewordDecoder.a(moduleBitCount)))) != -1) {
            return new Codeword(iAdjustCodewordStartColumn, i8, getCodewordBucketNumber(iA), codeword);
        }
        return null;
    }

    private static BarcodeMetadata getBarcodeMetadata(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) {
        BarcodeMetadata barcodeMetadataH;
        BarcodeMetadata barcodeMetadataH2;
        if (detectionResultRowIndicatorColumn == null || (barcodeMetadataH = detectionResultRowIndicatorColumn.h()) == null) {
            if (detectionResultRowIndicatorColumn2 == null) {
                return null;
            }
            return detectionResultRowIndicatorColumn2.h();
        }
        if (detectionResultRowIndicatorColumn2 == null || (barcodeMetadataH2 = detectionResultRowIndicatorColumn2.h()) == null || barcodeMetadataH.a() == barcodeMetadataH2.a() || barcodeMetadataH.b() == barcodeMetadataH2.b() || barcodeMetadataH.c() == barcodeMetadataH2.c()) {
            return barcodeMetadataH;
        }
        return null;
    }

    private static int[] getBitCountForCodeword(int i2) {
        int[] iArr = new int[8];
        int i3 = 0;
        int i4 = 7;
        while (true) {
            int i5 = i2 & 1;
            if (i5 != i3) {
                i4--;
                if (i4 < 0) {
                    return iArr;
                }
                i3 = i5;
            }
            iArr[i4] = iArr[i4] + 1;
            i2 >>= 1;
        }
    }

    private static int getCodewordBucketNumber(int i2) {
        return getCodewordBucketNumber(getBitCountForCodeword(i2));
    }

    private static int getMax(int[] iArr) {
        int iMax = -1;
        for (int i2 : iArr) {
            iMax = Math.max(iMax, i2);
        }
        return iMax;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int[] getModuleBitCount(com.google.zxing.common.BitMatrix r7, int r8, int r9, boolean r10, int r11, int r12) {
        /*
            r0 = 8
            int[] r1 = new int[r0]
            r2 = 1
            if (r10 == 0) goto L9
            r3 = r2
            goto La
        L9:
            r3 = -1
        La:
            r4 = 0
            r5 = r10
        Lc:
            if (r10 == 0) goto L11
            if (r11 >= r9) goto L27
            goto L13
        L11:
            if (r11 < r8) goto L27
        L13:
            if (r4 >= r0) goto L27
            boolean r6 = r7.get(r11, r12)
            if (r6 != r5) goto L22
            r6 = r1[r4]
            int r6 = r6 + r2
            r1[r4] = r6
            int r11 = r11 + r3
            goto Lc
        L22:
            int r4 = r4 + 1
            r5 = r5 ^ 1
            goto Lc
        L27:
            if (r4 == r0) goto L34
            if (r10 == 0) goto L2c
            r8 = r9
        L2c:
            if (r11 != r8) goto L32
            r7 = 7
            if (r4 != r7) goto L32
            goto L34
        L32:
            r7 = 0
            return r7
        L34:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.getModuleBitCount(com.google.zxing.common.BitMatrix, int, int, boolean, int, int):int[]");
    }

    private static int getNumberOfECCodeWords(int i2) {
        return 2 << i2;
    }

    private static DetectionResultRowIndicatorColumn getRowIndicatorColumn(BitMatrix bitMatrix, BoundingBox boundingBox, ResultPoint resultPoint, boolean z2, int i2, int i3) {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn = new DetectionResultRowIndicatorColumn(boundingBox, z2);
        int i4 = 0;
        while (i4 < 2) {
            int i5 = i4 == 0 ? 1 : -1;
            int x2 = (int) resultPoint.getX();
            for (int y2 = (int) resultPoint.getY(); y2 <= boundingBox.e() && y2 >= boundingBox.g(); y2 += i5) {
                Codeword codewordDetectCodeword = detectCodeword(bitMatrix, 0, bitMatrix.getWidth(), z2, x2, y2, i2, i3);
                if (codewordDetectCodeword != null) {
                    detectionResultRowIndicatorColumn.f(y2, codewordDetectCodeword);
                    x2 = z2 ? codewordDetectCodeword.d() : codewordDetectCodeword.b();
                }
            }
            i4++;
        }
        return detectionResultRowIndicatorColumn;
    }

    private static int getStartColumn(DetectionResult detectionResult, int i2, int i3, boolean z2) {
        int i4 = z2 ? 1 : -1;
        int i5 = i2 - i4;
        Codeword codewordB = isValidBarcodeColumn(detectionResult, i5) ? detectionResult.e(i5).b(i3) : null;
        if (codewordB != null) {
            return z2 ? codewordB.b() : codewordB.d();
        }
        Codeword codewordC = detectionResult.e(i2).c(i3);
        if (codewordC != null) {
            return z2 ? codewordC.d() : codewordC.b();
        }
        if (isValidBarcodeColumn(detectionResult, i5)) {
            codewordC = detectionResult.e(i5).c(i3);
        }
        if (codewordC != null) {
            return z2 ? codewordC.b() : codewordC.d();
        }
        int i6 = 0;
        while (true) {
            i2 -= i4;
            if (!isValidBarcodeColumn(detectionResult, i2)) {
                BoundingBox boundingBoxD = detectionResult.d();
                return z2 ? boundingBoxD.f() : boundingBoxD.d();
            }
            for (Codeword codeword : detectionResult.e(i2).d()) {
                if (codeword != null) {
                    return (z2 ? codeword.b() : codeword.d()) + (i4 * i6 * (codeword.b() - codeword.d()));
                }
            }
            i6++;
        }
    }

    private static boolean isValidBarcodeColumn(DetectionResult detectionResult, int i2) {
        return i2 >= 0 && i2 <= detectionResult.a() + 1;
    }

    private static DetectionResult merge(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) throws NotFoundException {
        BarcodeMetadata barcodeMetadata;
        if ((detectionResultRowIndicatorColumn == null && detectionResultRowIndicatorColumn2 == null) || (barcodeMetadata = getBarcodeMetadata(detectionResultRowIndicatorColumn, detectionResultRowIndicatorColumn2)) == null) {
            return null;
        }
        return new DetectionResult(barcodeMetadata, BoundingBox.j(adjustBoundingBox(detectionResultRowIndicatorColumn), adjustBoundingBox(detectionResultRowIndicatorColumn2)));
    }

    public static String toString(BarcodeValue[][] barcodeValueArr) {
        Formatter formatter = new Formatter();
        for (int i2 = 0; i2 < barcodeValueArr.length; i2++) {
            try {
                formatter.format("Row %2d: ", Integer.valueOf(i2));
                int i3 = 0;
                while (true) {
                    BarcodeValue[] barcodeValueArr2 = barcodeValueArr[i2];
                    if (i3 < barcodeValueArr2.length) {
                        BarcodeValue barcodeValue = barcodeValueArr2[i3];
                        if (barcodeValue.b().length == 0) {
                            formatter.format("        ", null);
                        } else {
                            formatter.format("%4d(%2d)", Integer.valueOf(barcodeValue.b()[0]), barcodeValue.a(barcodeValue.b()[0]));
                        }
                        i3++;
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

    private static void verifyCodewordCount(int[] iArr, int i2) throws FormatException {
        if (iArr.length < 4) {
            throw FormatException.getFormatInstance();
        }
        int i3 = iArr[0];
        if (i3 > iArr.length) {
            throw FormatException.getFormatInstance();
        }
        if (i3 == 0) {
            if (i2 >= iArr.length) {
                throw FormatException.getFormatInstance();
            }
            iArr[0] = iArr.length - i2;
        }
    }

    private static int getCodewordBucketNumber(int[] iArr) {
        return ((((iArr[0] - iArr[2]) + iArr[4]) - iArr[6]) + 9) % 9;
    }
}
