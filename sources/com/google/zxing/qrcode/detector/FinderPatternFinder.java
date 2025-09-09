package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    private static final class CenterComparator implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private CenterComparator(float f2) {
            this.average = f2;
        }

        @Override // java.util.Comparator
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            int iCompare = Integer.compare(finderPattern2.c(), finderPattern.c());
            return iCompare == 0 ? Float.compare(Math.abs(finderPattern.getEstimatedModuleSize() - this.average), Math.abs(finderPattern2.getEstimatedModuleSize() - this.average)) : iCompare;
        }
    }

    private static final class FurthestFromAverageComparator implements Serializable, Comparator<FinderPattern> {
        private final float average;

        private FurthestFromAverageComparator(float f2) {
            this.average = f2;
        }

        @Override // java.util.Comparator
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            return Float.compare(Math.abs(finderPattern2.getEstimatedModuleSize() - this.average), Math.abs(finderPattern.getEstimatedModuleSize() - this.average));
        }
    }

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    protected static boolean c(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = iArr[i3];
            if (i4 == 0) {
                return false;
            }
            i2 += i4;
        }
        if (i2 < 7) {
            return false;
        }
        float f2 = i2 / 7.0f;
        float f3 = f2 / 2.0f;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs(f2 - ((float) iArr[1])) < f3 && Math.abs((f2 * 3.0f) - ((float) iArr[2])) < 3.0f * f3 && Math.abs(f2 - ((float) iArr[3])) < f3 && Math.abs(f2 - ((float) iArr[4])) < f3;
    }

    private static float centerFromEnd(int[] iArr, int i2) {
        return ((i2 - iArr[4]) - iArr[3]) - (iArr[2] / 2.0f);
    }

    private boolean crossCheckDiagonal(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i7 = 0;
        while (i2 >= i7 && i3 >= i7 && this.image.get(i3 - i7, i2 - i7)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i7++;
        }
        if (crossCheckStateCount[2] == 0) {
            return false;
        }
        while (i2 >= i7 && i3 >= i7 && !this.image.get(i3 - i7, i2 - i7)) {
            crossCheckStateCount[1] = crossCheckStateCount[1] + 1;
            i7++;
        }
        if (crossCheckStateCount[1] == 0) {
            return false;
        }
        while (i2 >= i7 && i3 >= i7 && this.image.get(i3 - i7, i2 - i7)) {
            crossCheckStateCount[0] = crossCheckStateCount[0] + 1;
            i7++;
        }
        if (crossCheckStateCount[0] == 0) {
            return false;
        }
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i8 = 1;
        while (true) {
            int i9 = i2 + i8;
            if (i9 >= height || (i6 = i3 + i8) >= width || !this.image.get(i6, i9)) {
                break;
            }
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i8++;
        }
        while (true) {
            int i10 = i2 + i8;
            if (i10 >= height || (i5 = i3 + i8) >= width || this.image.get(i5, i10)) {
                break;
            }
            crossCheckStateCount[3] = crossCheckStateCount[3] + 1;
            i8++;
        }
        if (crossCheckStateCount[3] == 0) {
            return false;
        }
        while (true) {
            int i11 = i2 + i8;
            if (i11 >= height || (i4 = i3 + i8) >= width || !this.image.get(i4, i11)) {
                break;
            }
            crossCheckStateCount[4] = crossCheckStateCount[4] + 1;
            i8++;
        }
        if (crossCheckStateCount[4] == 0) {
            return false;
        }
        return d(crossCheckStateCount);
    }

    private float crossCheckHorizontal(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        BitMatrix bitMatrix = this.image;
        int width = bitMatrix.getWidth();
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i9 = i2;
        while (i9 >= 0 && bitMatrix.get(i9, i3)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i9--;
        }
        if (i9 < 0) {
            return Float.NaN;
        }
        while (i9 >= 0 && !bitMatrix.get(i9, i3)) {
            int i10 = crossCheckStateCount[1];
            if (i10 > i4) {
                break;
            }
            crossCheckStateCount[1] = i10 + 1;
            i9--;
        }
        if (i9 >= 0 && crossCheckStateCount[1] <= i4) {
            while (i9 >= 0 && bitMatrix.get(i9, i3) && (i8 = crossCheckStateCount[0]) <= i4) {
                crossCheckStateCount[0] = i8 + 1;
                i9--;
            }
            if (crossCheckStateCount[0] > i4) {
                return Float.NaN;
            }
            int i11 = i2 + 1;
            while (i11 < width && bitMatrix.get(i11, i3)) {
                crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
                i11++;
            }
            if (i11 == width) {
                return Float.NaN;
            }
            while (i11 < width && !bitMatrix.get(i11, i3) && (i7 = crossCheckStateCount[3]) < i4) {
                crossCheckStateCount[3] = i7 + 1;
                i11++;
            }
            if (i11 != width && crossCheckStateCount[3] < i4) {
                while (i11 < width && bitMatrix.get(i11, i3) && (i6 = crossCheckStateCount[4]) < i4) {
                    crossCheckStateCount[4] = i6 + 1;
                    i11++;
                }
                int i12 = crossCheckStateCount[4];
                if (i12 < i4 && Math.abs(((((crossCheckStateCount[0] + crossCheckStateCount[1]) + crossCheckStateCount[2]) + crossCheckStateCount[3]) + i12) - i5) * 5 < i5 && c(crossCheckStateCount)) {
                    return centerFromEnd(crossCheckStateCount, i11);
                }
            }
        }
        return Float.NaN;
    }

    private float crossCheckVertical(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        BitMatrix bitMatrix = this.image;
        int height = bitMatrix.getHeight();
        int[] crossCheckStateCount = getCrossCheckStateCount();
        int i9 = i2;
        while (i9 >= 0 && bitMatrix.get(i3, i9)) {
            crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
            i9--;
        }
        if (i9 < 0) {
            return Float.NaN;
        }
        while (i9 >= 0 && !bitMatrix.get(i3, i9)) {
            int i10 = crossCheckStateCount[1];
            if (i10 > i4) {
                break;
            }
            crossCheckStateCount[1] = i10 + 1;
            i9--;
        }
        if (i9 >= 0 && crossCheckStateCount[1] <= i4) {
            while (i9 >= 0 && bitMatrix.get(i3, i9) && (i8 = crossCheckStateCount[0]) <= i4) {
                crossCheckStateCount[0] = i8 + 1;
                i9--;
            }
            if (crossCheckStateCount[0] > i4) {
                return Float.NaN;
            }
            int i11 = i2 + 1;
            while (i11 < height && bitMatrix.get(i3, i11)) {
                crossCheckStateCount[2] = crossCheckStateCount[2] + 1;
                i11++;
            }
            if (i11 == height) {
                return Float.NaN;
            }
            while (i11 < height && !bitMatrix.get(i3, i11) && (i7 = crossCheckStateCount[3]) < i4) {
                crossCheckStateCount[3] = i7 + 1;
                i11++;
            }
            if (i11 != height && crossCheckStateCount[3] < i4) {
                while (i11 < height && bitMatrix.get(i3, i11) && (i6 = crossCheckStateCount[4]) < i4) {
                    crossCheckStateCount[4] = i6 + 1;
                    i11++;
                }
                int i12 = crossCheckStateCount[4];
                if (i12 < i4 && Math.abs(((((crossCheckStateCount[0] + crossCheckStateCount[1]) + crossCheckStateCount[2]) + crossCheckStateCount[3]) + i12) - i5) * 5 < i5 * 2 && c(crossCheckStateCount)) {
                    return centerFromEnd(crossCheckStateCount, i11);
                }
            }
        }
        return Float.NaN;
    }

    protected static boolean d(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = iArr[i3];
            if (i4 == 0) {
                return false;
            }
            i2 += i4;
        }
        if (i2 < 7) {
            return false;
        }
        float f2 = i2 / 7.0f;
        float f3 = f2 / 1.333f;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs(f2 - ((float) iArr[1])) < f3 && Math.abs((f2 * 3.0f) - ((float) iArr[2])) < 3.0f * f3 && Math.abs(f2 - ((float) iArr[3])) < f3 && Math.abs(f2 - ((float) iArr[4])) < f3;
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        FinderPattern finderPattern = null;
        for (FinderPattern finderPattern2 : this.possibleCenters) {
            if (finderPattern2.c() >= 2) {
                if (finderPattern != null) {
                    this.hasSkipped = true;
                    return ((int) (Math.abs(finderPattern.getX() - finderPattern2.getX()) - Math.abs(finderPattern.getY() - finderPattern2.getY()))) / 2;
                }
                finderPattern = finderPattern2;
            }
        }
        return 0;
    }

    private int[] getCrossCheckStateCount() {
        a(this.crossCheckStateCount);
        return this.crossCheckStateCount;
    }

    private boolean haveMultiplyConfirmedCenters() {
        int size = this.possibleCenters.size();
        float fAbs = 0.0f;
        int i2 = 0;
        float estimatedModuleSize = 0.0f;
        for (FinderPattern finderPattern : this.possibleCenters) {
            if (finderPattern.c() >= 2) {
                i2++;
                estimatedModuleSize += finderPattern.getEstimatedModuleSize();
            }
        }
        if (i2 < 3) {
            return false;
        }
        float f2 = estimatedModuleSize / size;
        Iterator<FinderPattern> it = this.possibleCenters.iterator();
        while (it.hasNext()) {
            fAbs += Math.abs(it.next().getEstimatedModuleSize() - f2);
        }
        return fAbs <= estimatedModuleSize * 0.05f;
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        int size = this.possibleCenters.size();
        if (size < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        float estimatedModuleSize = 0.0f;
        if (size > 3) {
            Iterator<FinderPattern> it = this.possibleCenters.iterator();
            float f2 = 0.0f;
            float f3 = 0.0f;
            while (it.hasNext()) {
                float estimatedModuleSize2 = it.next().getEstimatedModuleSize();
                f2 += estimatedModuleSize2;
                f3 += estimatedModuleSize2 * estimatedModuleSize2;
            }
            float f4 = f2 / size;
            float fSqrt = (float) Math.sqrt((f3 / r3) - (f4 * f4));
            Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(f4));
            float fMax = Math.max(0.2f * f4, fSqrt);
            int i2 = 0;
            while (i2 < this.possibleCenters.size() && this.possibleCenters.size() > 3) {
                if (Math.abs(this.possibleCenters.get(i2).getEstimatedModuleSize() - f4) > fMax) {
                    this.possibleCenters.remove(i2);
                    i2--;
                }
                i2++;
            }
        }
        if (this.possibleCenters.size() > 3) {
            Iterator<FinderPattern> it2 = this.possibleCenters.iterator();
            while (it2.hasNext()) {
                estimatedModuleSize += it2.next().getEstimatedModuleSize();
            }
            Collections.sort(this.possibleCenters, new CenterComparator(estimatedModuleSize / this.possibleCenters.size()));
            List<FinderPattern> list = this.possibleCenters;
            list.subList(3, list.size()).clear();
        }
        return new FinderPattern[]{this.possibleCenters.get(0), this.possibleCenters.get(1), this.possibleCenters.get(2)};
    }

    protected final void a(int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = 0;
        }
    }

    final FinderPatternInfo b(Map map) throws NotFoundException {
        boolean z2 = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i2 = (height * 3) / 388;
        if (i2 < 3 || z2) {
            i2 = 3;
        }
        int[] iArr = new int[5];
        int i3 = i2 - 1;
        boolean zHaveMultiplyConfirmedCenters = false;
        while (i3 < height && !zHaveMultiplyConfirmedCenters) {
            a(iArr);
            int i4 = 0;
            int i5 = 0;
            while (i4 < width) {
                if (this.image.get(i4, i3)) {
                    if ((i5 & 1) == 1) {
                        i5++;
                    }
                    iArr[i5] = iArr[i5] + 1;
                } else if ((i5 & 1) != 0) {
                    iArr[i5] = iArr[i5] + 1;
                } else if (i5 != 4) {
                    i5++;
                    iArr[i5] = iArr[i5] + 1;
                } else if (c(iArr) && g(iArr, i3, i4)) {
                    if (this.hasSkipped) {
                        zHaveMultiplyConfirmedCenters = haveMultiplyConfirmedCenters();
                    } else {
                        int iFindRowSkip = findRowSkip();
                        int i6 = iArr[2];
                        if (iFindRowSkip > i6) {
                            i3 += (iFindRowSkip - i6) - 2;
                            i4 = width - 1;
                        }
                    }
                    a(iArr);
                    i2 = 2;
                    i5 = 0;
                } else {
                    h(iArr);
                    i5 = 3;
                }
                i4++;
            }
            if (c(iArr) && g(iArr, i3, width)) {
                i2 = iArr[0];
                if (this.hasSkipped) {
                    zHaveMultiplyConfirmedCenters = haveMultiplyConfirmedCenters();
                }
            }
            i3 += i2;
        }
        FinderPattern[] finderPatternArrSelectBestPatterns = selectBestPatterns();
        ResultPoint.orderBestPatterns(finderPatternArrSelectBestPatterns);
        return new FinderPatternInfo(finderPatternArrSelectBestPatterns);
    }

    protected final BitMatrix e() {
        return this.image;
    }

    protected final List f() {
        return this.possibleCenters;
    }

    protected final boolean g(int[] iArr, int i2, int i3) {
        int i4 = 0;
        int i5 = iArr[0] + iArr[1] + iArr[2] + iArr[3] + iArr[4];
        int iCenterFromEnd = (int) centerFromEnd(iArr, i3);
        float fCrossCheckVertical = crossCheckVertical(i2, iCenterFromEnd, iArr[2], i5);
        if (!Float.isNaN(fCrossCheckVertical)) {
            int i6 = (int) fCrossCheckVertical;
            float fCrossCheckHorizontal = crossCheckHorizontal(iCenterFromEnd, i6, iArr[2], i5);
            if (!Float.isNaN(fCrossCheckHorizontal) && crossCheckDiagonal(i6, (int) fCrossCheckHorizontal)) {
                float f2 = i5 / 7.0f;
                while (true) {
                    if (i4 < this.possibleCenters.size()) {
                        FinderPattern finderPattern = this.possibleCenters.get(i4);
                        if (finderPattern.a(f2, fCrossCheckVertical, fCrossCheckHorizontal)) {
                            this.possibleCenters.set(i4, finderPattern.b(fCrossCheckVertical, fCrossCheckHorizontal, f2));
                            break;
                        }
                        i4++;
                    } else {
                        FinderPattern finderPattern2 = new FinderPattern(fCrossCheckHorizontal, fCrossCheckVertical, f2);
                        this.possibleCenters.add(finderPattern2);
                        ResultPointCallback resultPointCallback = this.resultPointCallback;
                        if (resultPointCallback != null) {
                            resultPointCallback.foundPossibleResultPoint(finderPattern2);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected final void h(int[] iArr) {
        iArr[0] = iArr[2];
        iArr[1] = iArr[3];
        iArr[2] = iArr[4];
        iArr[3] = 1;
        iArr[4] = 0;
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback;
    }
}
