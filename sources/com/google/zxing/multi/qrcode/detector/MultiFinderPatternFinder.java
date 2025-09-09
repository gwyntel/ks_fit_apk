package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class MultiFinderPatternFinder extends FinderPatternFinder {
    private static final float DIFF_MODSIZE_CUTOFF = 0.5f;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05f;
    private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0f;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0f;

    private static final class ModuleSizeComparator implements Serializable, Comparator<FinderPattern> {
        private ModuleSizeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            double estimatedModuleSize = finderPattern2.getEstimatedModuleSize() - finderPattern.getEstimatedModuleSize();
            if (estimatedModuleSize < 0.0d) {
                return -1;
            }
            return estimatedModuleSize > 0.0d ? 1 : 0;
        }
    }

    MultiFinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        super(bitMatrix, resultPointCallback);
    }

    private FinderPattern[][] selectMutipleBestPatterns() throws NotFoundException {
        int i2;
        int i3;
        char c2 = 0;
        int i4 = 1;
        List listF = f();
        int size = listF.size();
        if (size < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (size == 3) {
            return new FinderPattern[][]{new FinderPattern[]{(FinderPattern) listF.get(0), (FinderPattern) listF.get(1), (FinderPattern) listF.get(2)}};
        }
        Collections.sort(listF, new ModuleSizeComparator());
        ArrayList arrayList = new ArrayList();
        int i5 = 0;
        while (i5 < size - 2) {
            FinderPattern finderPattern = (FinderPattern) listF.get(i5);
            if (finderPattern != null) {
                int i6 = i5 + 1;
                while (i6 < size - 1) {
                    FinderPattern finderPattern2 = (FinderPattern) listF.get(i6);
                    if (finderPattern2 != null) {
                        float estimatedModuleSize = (finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) / Math.min(finderPattern.getEstimatedModuleSize(), finderPattern2.getEstimatedModuleSize());
                        float fAbs = Math.abs(finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize());
                        float f2 = 0.5f;
                        float f3 = DIFF_MODSIZE_CUTOFF_PERCENT;
                        if (fAbs > 0.5f && estimatedModuleSize >= DIFF_MODSIZE_CUTOFF_PERCENT) {
                            break;
                        }
                        int i7 = i6 + 1;
                        while (i7 < size) {
                            FinderPattern finderPattern3 = (FinderPattern) listF.get(i7);
                            if (finderPattern3 != null) {
                                float estimatedModuleSize2 = (finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) / Math.min(finderPattern2.getEstimatedModuleSize(), finderPattern3.getEstimatedModuleSize());
                                if (Math.abs(finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) > f2 && estimatedModuleSize2 >= f3) {
                                    i2 = 1;
                                    break;
                                }
                                FinderPattern[] finderPatternArr = new FinderPattern[3];
                                finderPatternArr[c2] = finderPattern;
                                finderPatternArr[1] = finderPattern2;
                                finderPatternArr[2] = finderPattern3;
                                ResultPoint.orderBestPatterns(finderPatternArr);
                                FinderPatternInfo finderPatternInfo = new FinderPatternInfo(finderPatternArr);
                                float fDistance = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getBottomLeft());
                                float fDistance2 = ResultPoint.distance(finderPatternInfo.getTopRight(), finderPatternInfo.getBottomLeft());
                                float fDistance3 = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getTopRight());
                                float estimatedModuleSize3 = (fDistance + fDistance3) / (finderPattern.getEstimatedModuleSize() * 2.0f);
                                if (estimatedModuleSize3 <= MAX_MODULE_COUNT_PER_EDGE && estimatedModuleSize3 >= MIN_MODULE_COUNT_PER_EDGE && Math.abs((fDistance - fDistance3) / Math.min(fDistance, fDistance3)) < 0.1f) {
                                    float fSqrt = (float) Math.sqrt((fDistance * fDistance) + (fDistance3 * fDistance3));
                                    if (Math.abs((fDistance2 - fSqrt) / Math.min(fDistance2, fSqrt)) < 0.1f) {
                                        arrayList.add(finderPatternArr);
                                    }
                                }
                                i3 = 1;
                            } else {
                                i3 = i4;
                            }
                            i7 += i3;
                            i4 = i3;
                            c2 = 0;
                            f2 = 0.5f;
                            f3 = DIFF_MODSIZE_CUTOFF_PERCENT;
                        }
                        i2 = i4;
                    } else {
                        i2 = i4;
                    }
                    i6 += i2;
                    i4 = i2;
                    c2 = 0;
                }
            }
            int i8 = i4;
            i5 += i8;
            i4 = i8;
            c2 = 0;
        }
        if (arrayList.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (FinderPattern[][]) arrayList.toArray(new FinderPattern[arrayList.size()][]);
    }

    public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> map) throws NotFoundException {
        boolean z2 = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        BitMatrix bitMatrixE = e();
        int height = bitMatrixE.getHeight();
        int width = bitMatrixE.getWidth();
        int i2 = (height * 3) / 388;
        if (i2 < 3 || z2) {
            i2 = 3;
        }
        int[] iArr = new int[5];
        for (int i3 = i2 - 1; i3 < height; i3 += i2) {
            a(iArr);
            int i4 = 0;
            for (int i5 = 0; i5 < width; i5++) {
                if (bitMatrixE.get(i5, i3)) {
                    if ((i4 & 1) == 1) {
                        i4++;
                    }
                    iArr[i4] = iArr[i4] + 1;
                } else if ((i4 & 1) != 0) {
                    iArr[i4] = iArr[i4] + 1;
                } else if (i4 != 4) {
                    i4++;
                    iArr[i4] = iArr[i4] + 1;
                } else if (FinderPatternFinder.c(iArr) && g(iArr, i3, i5)) {
                    a(iArr);
                    i4 = 0;
                } else {
                    h(iArr);
                    i4 = 3;
                }
            }
            if (FinderPatternFinder.c(iArr)) {
                g(iArr, i3, width);
            }
        }
        FinderPattern[][] finderPatternArrSelectMutipleBestPatterns = selectMutipleBestPatterns();
        ArrayList arrayList = new ArrayList();
        for (FinderPattern[] finderPatternArr : finderPatternArrSelectMutipleBestPatterns) {
            ResultPoint.orderBestPatterns(finderPatternArr);
            arrayList.add(new FinderPatternInfo(finderPatternArr));
        }
        return arrayList.isEmpty() ? EMPTY_RESULT_ARRAY : (FinderPatternInfo[]) arrayList.toArray(new FinderPatternInfo[arrayList.size()]);
    }
}
