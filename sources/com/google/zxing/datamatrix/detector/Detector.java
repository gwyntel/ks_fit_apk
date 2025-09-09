package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/* loaded from: classes3.dex */
public final class Detector {
    private final BitMatrix image;
    private final WhiteRectangleDetector rectangleDetector;

    private static final class ResultPointsAndTransitions {
        private final ResultPoint from;
        private final ResultPoint to;
        private final int transitions;

        ResultPoint a() {
            return this.from;
        }

        ResultPoint b() {
            return this.to;
        }

        int c() {
            return this.transitions;
        }

        public String toString() {
            return this.from + "/" + this.to + IOUtils.DIR_SEPARATOR_UNIX + this.transitions;
        }

        private ResultPointsAndTransitions(ResultPoint resultPoint, ResultPoint resultPoint2, int i2) {
            this.from = resultPoint;
            this.to = resultPoint2;
            this.transitions = i2;
        }
    }

    private static final class ResultPointsAndTransitionsComparator implements Serializable, Comparator<ResultPointsAndTransitions> {
        private ResultPointsAndTransitionsComparator() {
        }

        @Override // java.util.Comparator
        public int compare(ResultPointsAndTransitions resultPointsAndTransitions, ResultPointsAndTransitions resultPointsAndTransitions2) {
            return resultPointsAndTransitions.c() - resultPointsAndTransitions2.c();
        }
    }

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.image = bitMatrix;
        this.rectangleDetector = new WhiteRectangleDetector(bitMatrix);
    }

    private ResultPoint correctTopRight(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i2) {
        float f2 = i2;
        float fDistance = distance(resultPoint, resultPoint2) / f2;
        float fDistance2 = distance(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint3.getX()) / fDistance2) * fDistance), resultPoint4.getY() + (fDistance * ((resultPoint4.getY() - resultPoint3.getY()) / fDistance2)));
        float fDistance3 = distance(resultPoint, resultPoint3) / f2;
        float fDistance4 = distance(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint2.getX()) / fDistance4) * fDistance3), resultPoint4.getY() + (fDistance3 * ((resultPoint4.getY() - resultPoint2.getY()) / fDistance4)));
        if (isValid(resultPoint5)) {
            return (isValid(resultPoint6) && Math.abs(transitionsBetween(resultPoint3, resultPoint5).c() - transitionsBetween(resultPoint2, resultPoint5).c()) > Math.abs(transitionsBetween(resultPoint3, resultPoint6).c() - transitionsBetween(resultPoint2, resultPoint6).c())) ? resultPoint6 : resultPoint5;
        }
        if (isValid(resultPoint6)) {
            return resultPoint6;
        }
        return null;
    }

    private ResultPoint correctTopRightRectangular(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i2, int i3) {
        float fDistance = distance(resultPoint, resultPoint2) / i2;
        float fDistance2 = distance(resultPoint3, resultPoint4);
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint3.getX()) / fDistance2) * fDistance), resultPoint4.getY() + (fDistance * ((resultPoint4.getY() - resultPoint3.getY()) / fDistance2)));
        float fDistance3 = distance(resultPoint, resultPoint3) / i3;
        float fDistance4 = distance(resultPoint2, resultPoint4);
        ResultPoint resultPoint6 = new ResultPoint(resultPoint4.getX() + (((resultPoint4.getX() - resultPoint2.getX()) / fDistance4) * fDistance3), resultPoint4.getY() + (fDistance3 * ((resultPoint4.getY() - resultPoint2.getY()) / fDistance4)));
        if (isValid(resultPoint5)) {
            return (isValid(resultPoint6) && Math.abs(i2 - transitionsBetween(resultPoint3, resultPoint5).c()) + Math.abs(i3 - transitionsBetween(resultPoint2, resultPoint5).c()) > Math.abs(i2 - transitionsBetween(resultPoint3, resultPoint6).c()) + Math.abs(i3 - transitionsBetween(resultPoint2, resultPoint6).c())) ? resultPoint6 : resultPoint5;
        }
        if (isValid(resultPoint6)) {
            return resultPoint6;
        }
        return null;
    }

    private static int distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2));
    }

    private static void increment(Map<ResultPoint, Integer> map, ResultPoint resultPoint) {
        Integer num = map.get(resultPoint);
        map.put(resultPoint, Integer.valueOf(num != null ? 1 + num.intValue() : 1));
    }

    private boolean isValid(ResultPoint resultPoint) {
        return resultPoint.getX() >= 0.0f && resultPoint.getX() < ((float) this.image.getWidth()) && resultPoint.getY() > 0.0f && resultPoint.getY() < ((float) this.image.getHeight());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i2, int i3) throws NotFoundException {
        float f2 = i2 - 0.5f;
        float f3 = i3 - 0.5f;
        return GridSampler.getInstance().sampleGrid(bitMatrix, i2, i3, 0.5f, 0.5f, f2, 0.5f, f2, f3, 0.5f, f3, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private ResultPointsAndTransitions transitionsBetween(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int x2 = (int) resultPoint.getX();
        int y2 = (int) resultPoint.getY();
        int x3 = (int) resultPoint2.getX();
        int y3 = (int) resultPoint2.getY();
        int i2 = 0;
        boolean z2 = Math.abs(y3 - y2) > Math.abs(x3 - x2);
        if (z2) {
            y2 = x2;
            x2 = y2;
            y3 = x3;
            x3 = y3;
        }
        int iAbs = Math.abs(x3 - x2);
        int iAbs2 = Math.abs(y3 - y2);
        int i3 = (-iAbs) / 2;
        int i4 = y2 < y3 ? 1 : -1;
        int i5 = x2 >= x3 ? -1 : 1;
        boolean z3 = this.image.get(z2 ? y2 : x2, z2 ? x2 : y2);
        while (x2 != x3) {
            boolean z4 = this.image.get(z2 ? y2 : x2, z2 ? x2 : y2);
            if (z4 != z3) {
                i2++;
                z3 = z4;
            }
            i3 += iAbs2;
            if (i3 > 0) {
                if (y2 == y3) {
                    break;
                }
                y2 += i4;
                i3 -= iAbs;
            }
            x2 += i5;
        }
        return new ResultPointsAndTransitions(resultPoint, resultPoint2, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v3, types: [com.google.zxing.ResultPoint] */
    /* JADX WARN: Type inference failed for: r16v3, types: [com.google.zxing.ResultPoint] */
    /* JADX WARN: Type inference failed for: r22v0, types: [com.google.zxing.ResultPoint] */
    /* JADX WARN: Type inference failed for: r23v0, types: [com.google.zxing.datamatrix.detector.Detector] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.google.zxing.ResultPoint[]] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.google.zxing.ResultPoint[]] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.google.zxing.ResultPoint] */
    public DetectorResult detect() throws NotFoundException {
        ResultPoint resultPoint;
        BitMatrix bitMatrixSampleGrid;
        ResultPoint[] resultPointArrDetect = this.rectangleDetector.detect();
        ResultPoint resultPoint2 = resultPointArrDetect[0];
        ResultPoint resultPoint3 = resultPointArrDetect[1];
        ResultPoint resultPoint4 = resultPointArrDetect[2];
        ResultPoint resultPoint5 = resultPointArrDetect[3];
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(transitionsBetween(resultPoint2, resultPoint3));
        arrayList.add(transitionsBetween(resultPoint2, resultPoint4));
        arrayList.add(transitionsBetween(resultPoint3, resultPoint5));
        arrayList.add(transitionsBetween(resultPoint4, resultPoint5));
        AnonymousClass1 anonymousClass1 = null;
        Collections.sort(arrayList, new ResultPointsAndTransitionsComparator());
        ResultPointsAndTransitions resultPointsAndTransitions = (ResultPointsAndTransitions) arrayList.get(0);
        ResultPointsAndTransitions resultPointsAndTransitions2 = (ResultPointsAndTransitions) arrayList.get(1);
        HashMap map = new HashMap();
        increment(map, resultPointsAndTransitions.a());
        increment(map, resultPointsAndTransitions.b());
        increment(map, resultPointsAndTransitions2.a());
        increment(map, resultPointsAndTransitions2.b());
        Object obj = null;
        Object obj2 = null;
        for (Map.Entry entry : map.entrySet()) {
            ?? r16 = (ResultPoint) entry.getKey();
            if (((Integer) entry.getValue()).intValue() == 2) {
                obj = r16;
            } else if (anonymousClass1 == null) {
                anonymousClass1 = r16;
            } else {
                obj2 = r16;
            }
        }
        if (anonymousClass1 == null || obj == null || obj2 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        ?? r4 = {anonymousClass1, obj, obj2};
        ResultPoint.orderBestPatterns(r4);
        ?? r14 = r4[0];
        ?? r22 = r4[1];
        ?? r6 = r4[2];
        ResultPoint resultPoint6 = !map.containsKey(resultPoint2) ? resultPoint2 : !map.containsKey(resultPoint3) ? resultPoint3 : !map.containsKey(resultPoint4) ? resultPoint4 : resultPoint5;
        int iC = transitionsBetween(r6, resultPoint6).c();
        int iC2 = transitionsBetween(r14, resultPoint6).c();
        if ((iC & 1) == 1) {
            iC++;
        }
        int i2 = iC + 2;
        if ((iC2 & 1) == 1) {
            iC2++;
        }
        int i3 = iC2 + 2;
        if (i2 * 4 >= i3 * 7 || i3 * 4 >= i2 * 7) {
            resultPoint = r6;
            ResultPoint resultPointCorrectTopRightRectangular = correctTopRightRectangular(r22, r14, r6, resultPoint6, i2, i3);
            if (resultPointCorrectTopRightRectangular != null) {
                resultPoint6 = resultPointCorrectTopRightRectangular;
            }
            int iC3 = transitionsBetween(resultPoint, resultPoint6).c();
            int iC4 = transitionsBetween(r14, resultPoint6).c();
            if ((iC3 & 1) == 1) {
                iC3++;
            }
            int i4 = iC3;
            if ((iC4 & 1) == 1) {
                iC4++;
            }
            bitMatrixSampleGrid = sampleGrid(this.image, resultPoint, r22, r14, resultPoint6, i4, iC4);
        } else {
            ResultPoint resultPointCorrectTopRight = correctTopRight(r22, r14, r6, resultPoint6, Math.min(i3, i2));
            if (resultPointCorrectTopRight != null) {
                resultPoint6 = resultPointCorrectTopRight;
            }
            int iMax = Math.max(transitionsBetween(r6, resultPoint6).c(), transitionsBetween(r14, resultPoint6).c());
            int i5 = iMax + 1;
            int i6 = (i5 & 1) == 1 ? iMax + 2 : i5;
            bitMatrixSampleGrid = sampleGrid(this.image, r6, r22, r14, resultPoint6, i6, i6);
            resultPoint = r6;
        }
        return new DetectorResult(bitMatrixSampleGrid, new ResultPoint[]{resultPoint, r22, r14, resultPoint6});
    }
}
