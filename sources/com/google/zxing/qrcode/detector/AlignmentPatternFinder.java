package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
final class AlignmentPatternFinder {
    private final int height;
    private final BitMatrix image;
    private final float moduleSize;
    private final ResultPointCallback resultPointCallback;
    private final int startX;
    private final int startY;
    private final int width;
    private final List<AlignmentPattern> possibleCenters = new ArrayList(5);
    private final int[] crossCheckStateCount = new int[3];

    AlignmentPatternFinder(BitMatrix bitMatrix, int i2, int i3, int i4, int i5, float f2, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.startX = i2;
        this.startY = i3;
        this.width = i4;
        this.height = i5;
        this.moduleSize = f2;
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] iArr, int i2) {
        return (i2 - iArr[2]) - (iArr[1] / 2.0f);
    }

    private float crossCheckVertical(int i2, int i3, int i4, int i5) {
        BitMatrix bitMatrix = this.image;
        int height = bitMatrix.getHeight();
        int[] iArr = this.crossCheckStateCount;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        int i6 = i2;
        while (i6 >= 0 && bitMatrix.get(i3, i6)) {
            int i7 = iArr[1];
            if (i7 > i4) {
                break;
            }
            iArr[1] = i7 + 1;
            i6--;
        }
        if (i6 >= 0 && iArr[1] <= i4) {
            while (i6 >= 0 && !bitMatrix.get(i3, i6)) {
                int i8 = iArr[0];
                if (i8 > i4) {
                    break;
                }
                iArr[0] = i8 + 1;
                i6--;
            }
            if (iArr[0] > i4) {
                return Float.NaN;
            }
            int i9 = i2 + 1;
            while (i9 < height && bitMatrix.get(i3, i9)) {
                int i10 = iArr[1];
                if (i10 > i4) {
                    break;
                }
                iArr[1] = i10 + 1;
                i9++;
            }
            if (i9 != height && iArr[1] <= i4) {
                while (i9 < height && !bitMatrix.get(i3, i9)) {
                    int i11 = iArr[2];
                    if (i11 > i4) {
                        break;
                    }
                    iArr[2] = i11 + 1;
                    i9++;
                }
                int i12 = iArr[2];
                if (i12 <= i4 && Math.abs(((iArr[0] + iArr[1]) + i12) - i5) * 5 < i5 * 2 && foundPatternCross(iArr)) {
                    return centerFromEnd(iArr, i9);
                }
            }
        }
        return Float.NaN;
    }

    private boolean foundPatternCross(int[] iArr) {
        float f2 = this.moduleSize;
        float f3 = f2 / 2.0f;
        for (int i2 = 0; i2 < 3; i2++) {
            if (Math.abs(f2 - iArr[i2]) >= f3) {
                return false;
            }
        }
        return true;
    }

    private AlignmentPattern handlePossibleCenter(int[] iArr, int i2, int i3) {
        int i4 = iArr[0] + iArr[1] + iArr[2];
        float fCenterFromEnd = centerFromEnd(iArr, i3);
        float fCrossCheckVertical = crossCheckVertical(i2, (int) fCenterFromEnd, iArr[1] * 2, i4);
        if (Float.isNaN(fCrossCheckVertical)) {
            return null;
        }
        float f2 = ((iArr[0] + iArr[1]) + iArr[2]) / 3.0f;
        for (AlignmentPattern alignmentPattern : this.possibleCenters) {
            if (alignmentPattern.a(f2, fCrossCheckVertical, fCenterFromEnd)) {
                return alignmentPattern.b(fCrossCheckVertical, fCenterFromEnd, f2);
            }
        }
        AlignmentPattern alignmentPattern2 = new AlignmentPattern(fCenterFromEnd, fCrossCheckVertical, f2);
        this.possibleCenters.add(alignmentPattern2);
        ResultPointCallback resultPointCallback = this.resultPointCallback;
        if (resultPointCallback == null) {
            return null;
        }
        resultPointCallback.foundPossibleResultPoint(alignmentPattern2);
        return null;
    }

    AlignmentPattern a() throws NotFoundException {
        AlignmentPattern alignmentPatternHandlePossibleCenter;
        AlignmentPattern alignmentPatternHandlePossibleCenter2;
        int i2 = this.startX;
        int i3 = this.height;
        int i4 = this.width + i2;
        int i5 = this.startY + (i3 / 2);
        int[] iArr = new int[3];
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = ((i6 & 1) == 0 ? (i6 + 1) / 2 : -((i6 + 1) / 2)) + i5;
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            int i8 = i2;
            while (i8 < i4 && !this.image.get(i8, i7)) {
                i8++;
            }
            int i9 = 0;
            while (i8 < i4) {
                if (!this.image.get(i8, i7)) {
                    if (i9 == 1) {
                        i9++;
                    }
                    iArr[i9] = iArr[i9] + 1;
                } else if (i9 == 1) {
                    iArr[1] = iArr[1] + 1;
                } else if (i9 != 2) {
                    i9++;
                    iArr[i9] = iArr[i9] + 1;
                } else {
                    if (foundPatternCross(iArr) && (alignmentPatternHandlePossibleCenter2 = handlePossibleCenter(iArr, i7, i8)) != null) {
                        return alignmentPatternHandlePossibleCenter2;
                    }
                    iArr[0] = iArr[2];
                    iArr[1] = 1;
                    iArr[2] = 0;
                    i9 = 1;
                }
                i8++;
            }
            if (foundPatternCross(iArr) && (alignmentPatternHandlePossibleCenter = handlePossibleCenter(iArr, i7, i4)) != null) {
                return alignmentPatternHandlePossibleCenter;
            }
        }
        if (this.possibleCenters.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return this.possibleCenters.get(0);
    }
}
