package com.google.zxing.aztec.detector;

import aisble.data.MutableData;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public final class Detector {
    private static final int[] EXPECTED_CORNER_BITS = {3808, 476, 2107, 1799};
    private boolean compact;
    private final BitMatrix image;
    private int nbCenterLayers;
    private int nbDataBlocks;
    private int nbLayers;
    private int shift;

    static final class Point {

        /* renamed from: x, reason: collision with root package name */
        private final int f15400x;

        /* renamed from: y, reason: collision with root package name */
        private final int f15401y;

        Point(int i2, int i3) {
            this.f15400x = i2;
            this.f15401y = i3;
        }

        int a() {
            return this.f15400x;
        }

        int b() {
            return this.f15401y;
        }

        ResultPoint c() {
            return new ResultPoint(a(), b());
        }

        public String toString() {
            return "<" + this.f15400x + ' ' + this.f15401y + Typography.greater;
        }
    }

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private static float distance(Point point, Point point2) {
        return MathUtils.distance(point.a(), point.b(), point2.a(), point2.b());
    }

    private static ResultPoint[] expandSquare(ResultPoint[] resultPointArr, int i2, int i3) {
        float f2 = i3 / (i2 * 2.0f);
        float x2 = resultPointArr[0].getX() - resultPointArr[2].getX();
        float y2 = resultPointArr[0].getY() - resultPointArr[2].getY();
        float x3 = (resultPointArr[0].getX() + resultPointArr[2].getX()) / 2.0f;
        float y3 = (resultPointArr[0].getY() + resultPointArr[2].getY()) / 2.0f;
        float f3 = x2 * f2;
        float f4 = y2 * f2;
        ResultPoint resultPoint = new ResultPoint(x3 + f3, y3 + f4);
        ResultPoint resultPoint2 = new ResultPoint(x3 - f3, y3 - f4);
        float x4 = resultPointArr[1].getX() - resultPointArr[3].getX();
        float y4 = resultPointArr[1].getY() - resultPointArr[3].getY();
        float x5 = (resultPointArr[1].getX() + resultPointArr[3].getX()) / 2.0f;
        float y5 = (resultPointArr[1].getY() + resultPointArr[3].getY()) / 2.0f;
        float f5 = x4 * f2;
        float f6 = f2 * y4;
        return new ResultPoint[]{resultPoint, new ResultPoint(x5 + f5, y5 + f6), resultPoint2, new ResultPoint(x5 - f5, y5 - f6)};
    }

    private void extractParameters(ResultPoint[] resultPointArr) throws NotFoundException {
        long j2;
        long j3;
        if (!isValid(resultPointArr[0]) || !isValid(resultPointArr[1]) || !isValid(resultPointArr[2]) || !isValid(resultPointArr[3])) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i2 = this.nbCenterLayers * 2;
        int[] iArr = {sampleLine(resultPointArr[0], resultPointArr[1], i2), sampleLine(resultPointArr[1], resultPointArr[2], i2), sampleLine(resultPointArr[2], resultPointArr[3], i2), sampleLine(resultPointArr[3], resultPointArr[0], i2)};
        this.shift = getRotation(iArr, i2);
        long j4 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = iArr[(this.shift + i3) % 4];
            if (this.compact) {
                j2 = j4 << 7;
                j3 = (i4 >> 1) & 127;
            } else {
                j2 = j4 << 10;
                j3 = ((i4 >> 2) & 992) + ((i4 >> 1) & 31);
            }
            j4 = j2 + j3;
        }
        int correctedParameterData = getCorrectedParameterData(j4, this.compact);
        if (this.compact) {
            this.nbLayers = (correctedParameterData >> 6) + 1;
            this.nbDataBlocks = (correctedParameterData & 63) + 1;
        } else {
            this.nbLayers = (correctedParameterData >> 11) + 1;
            this.nbDataBlocks = (correctedParameterData & MutableData.SFLOAT_NAN) + 1;
        }
    }

    private ResultPoint[] getBullsEyeCorners(Point point) throws NotFoundException {
        this.nbCenterLayers = 1;
        Point point2 = point;
        Point point3 = point2;
        Point point4 = point3;
        Point point5 = point4;
        boolean z2 = true;
        while (this.nbCenterLayers < 9) {
            Point firstDifferent = getFirstDifferent(point2, z2, 1, -1);
            Point firstDifferent2 = getFirstDifferent(point3, z2, 1, 1);
            Point firstDifferent3 = getFirstDifferent(point4, z2, -1, 1);
            Point firstDifferent4 = getFirstDifferent(point5, z2, -1, -1);
            if (this.nbCenterLayers > 2) {
                double dDistance = (distance(firstDifferent4, firstDifferent) * this.nbCenterLayers) / (distance(point5, point2) * (this.nbCenterLayers + 2));
                if (dDistance < 0.75d || dDistance > 1.25d || !isWhiteOrBlackRectangle(firstDifferent, firstDifferent2, firstDifferent3, firstDifferent4)) {
                    break;
                }
            }
            z2 = !z2;
            this.nbCenterLayers++;
            point5 = firstDifferent4;
            point2 = firstDifferent;
            point3 = firstDifferent2;
            point4 = firstDifferent3;
        }
        int i2 = this.nbCenterLayers;
        if (i2 != 5 && i2 != 7) {
            throw NotFoundException.getNotFoundInstance();
        }
        this.compact = i2 == 5;
        ResultPoint[] resultPointArr = {new ResultPoint(point2.a() + 0.5f, point2.b() - 0.5f), new ResultPoint(point3.a() + 0.5f, point3.b() + 0.5f), new ResultPoint(point4.a() - 0.5f, point4.b() + 0.5f), new ResultPoint(point5.a() - 0.5f, point5.b() - 0.5f)};
        int i3 = this.nbCenterLayers;
        return expandSquare(resultPointArr, (i3 * 2) - 3, i3 * 2);
    }

    private int getColor(Point point, Point point2) {
        float fDistance = distance(point, point2);
        float fA = (point2.a() - point.a()) / fDistance;
        float fB = (point2.b() - point.b()) / fDistance;
        float fA2 = point.a();
        float fB2 = point.b();
        boolean z2 = this.image.get(point.a(), point.b());
        int iCeil = (int) Math.ceil(fDistance);
        int i2 = 0;
        for (int i3 = 0; i3 < iCeil; i3++) {
            fA2 += fA;
            fB2 += fB;
            if (this.image.get(MathUtils.round(fA2), MathUtils.round(fB2)) != z2) {
                i2++;
            }
        }
        float f2 = i2 / fDistance;
        if (f2 <= 0.1f || f2 >= 0.9f) {
            return (f2 <= 0.1f) == z2 ? 1 : -1;
        }
        return 0;
    }

    private static int getCorrectedParameterData(long j2, boolean z2) throws NotFoundException {
        int i2;
        int i3;
        if (z2) {
            i2 = 7;
            i3 = 2;
        } else {
            i2 = 10;
            i3 = 4;
        }
        int i4 = i2 - i3;
        int[] iArr = new int[i2];
        for (int i5 = i2 - 1; i5 >= 0; i5--) {
            iArr[i5] = ((int) j2) & 15;
            j2 >>= 4;
        }
        try {
            new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(iArr, i4);
            int i6 = 0;
            for (int i7 = 0; i7 < i3; i7++) {
                i6 = (i6 << 4) + iArr[i7];
            }
            return i6;
        } catch (ReedSolomonException unused) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private int getDimension() {
        if (this.compact) {
            return (this.nbLayers * 4) + 11;
        }
        int i2 = this.nbLayers;
        return i2 <= 4 ? (i2 * 4) + 15 : (i2 * 4) + ((((i2 - 4) / 8) + 1) * 2) + 15;
    }

    private Point getFirstDifferent(Point point, boolean z2, int i2, int i3) {
        int iA = point.a() + i2;
        int iB = point.b();
        while (true) {
            iB += i3;
            if (!isValid(iA, iB) || this.image.get(iA, iB) != z2) {
                break;
            }
            iA += i2;
        }
        int i4 = iA - i2;
        int i5 = iB - i3;
        while (isValid(i4, i5) && this.image.get(i4, i5) == z2) {
            i4 += i2;
        }
        int i6 = i4 - i2;
        while (isValid(i6, i5) && this.image.get(i6, i5) == z2) {
            i5 += i3;
        }
        return new Point(i6, i5 - i3);
    }

    private Point getMatrixCenter() {
        ResultPoint resultPointC;
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPointC2;
        ResultPoint resultPointC3;
        ResultPoint resultPointC4;
        ResultPoint resultPointC5;
        try {
            ResultPoint[] resultPointArrDetect = new WhiteRectangleDetector(this.image).detect();
            resultPoint2 = resultPointArrDetect[0];
            resultPoint3 = resultPointArrDetect[1];
            resultPoint = resultPointArrDetect[2];
            resultPointC = resultPointArrDetect[3];
        } catch (NotFoundException unused) {
            int width = this.image.getWidth() / 2;
            int height = this.image.getHeight() / 2;
            int i2 = width + 7;
            int i3 = height - 7;
            ResultPoint resultPointC6 = getFirstDifferent(new Point(i2, i3), false, 1, -1).c();
            int i4 = height + 7;
            ResultPoint resultPointC7 = getFirstDifferent(new Point(i2, i4), false, 1, 1).c();
            int i5 = width - 7;
            ResultPoint resultPointC8 = getFirstDifferent(new Point(i5, i4), false, -1, 1).c();
            resultPointC = getFirstDifferent(new Point(i5, i3), false, -1, -1).c();
            resultPoint = resultPointC8;
            resultPoint2 = resultPointC6;
            resultPoint3 = resultPointC7;
        }
        int iRound = MathUtils.round((((resultPoint2.getX() + resultPointC.getX()) + resultPoint3.getX()) + resultPoint.getX()) / 4.0f);
        int iRound2 = MathUtils.round((((resultPoint2.getY() + resultPointC.getY()) + resultPoint3.getY()) + resultPoint.getY()) / 4.0f);
        try {
            ResultPoint[] resultPointArrDetect2 = new WhiteRectangleDetector(this.image, 15, iRound, iRound2).detect();
            resultPointC2 = resultPointArrDetect2[0];
            resultPointC3 = resultPointArrDetect2[1];
            resultPointC4 = resultPointArrDetect2[2];
            resultPointC5 = resultPointArrDetect2[3];
        } catch (NotFoundException unused2) {
            int i6 = iRound + 7;
            int i7 = iRound2 - 7;
            resultPointC2 = getFirstDifferent(new Point(i6, i7), false, 1, -1).c();
            int i8 = iRound2 + 7;
            resultPointC3 = getFirstDifferent(new Point(i6, i8), false, 1, 1).c();
            int i9 = iRound - 7;
            resultPointC4 = getFirstDifferent(new Point(i9, i8), false, -1, 1).c();
            resultPointC5 = getFirstDifferent(new Point(i9, i7), false, -1, -1).c();
        }
        return new Point(MathUtils.round((((resultPointC2.getX() + resultPointC5.getX()) + resultPointC3.getX()) + resultPointC4.getX()) / 4.0f), MathUtils.round((((resultPointC2.getY() + resultPointC5.getY()) + resultPointC3.getY()) + resultPointC4.getY()) / 4.0f));
    }

    private ResultPoint[] getMatrixCornerPoints(ResultPoint[] resultPointArr) {
        return expandSquare(resultPointArr, this.nbCenterLayers * 2, getDimension());
    }

    private static int getRotation(int[] iArr, int i2) throws NotFoundException {
        int i3 = 0;
        for (int i4 : iArr) {
            i3 = (i3 << 3) + ((i4 >> (i2 - 2)) << 1) + (i4 & 1);
        }
        int i5 = ((i3 & 1) << 11) + (i3 >> 1);
        for (int i6 = 0; i6 < 4; i6++) {
            if (Integer.bitCount(EXPECTED_CORNER_BITS[i6] ^ i5) <= 2) {
                return i6;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private boolean isValid(int i2, int i3) {
        return i2 >= 0 && i2 < this.image.getWidth() && i3 > 0 && i3 < this.image.getHeight();
    }

    private boolean isWhiteOrBlackRectangle(Point point, Point point2, Point point3, Point point4) {
        Point point5 = new Point(point.a() - 3, point.b() + 3);
        Point point6 = new Point(point2.a() - 3, point2.b() - 3);
        Point point7 = new Point(point3.a() + 3, point3.b() - 3);
        Point point8 = new Point(point4.a() + 3, point4.b() + 3);
        int color = getColor(point8, point5);
        return color != 0 && getColor(point5, point6) == color && getColor(point6, point7) == color && getColor(point7, point8) == color;
    }

    private BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        GridSampler gridSampler = GridSampler.getInstance();
        int dimension = getDimension();
        float f2 = dimension / 2.0f;
        int i2 = this.nbCenterLayers;
        float f3 = f2 - i2;
        float f4 = f2 + i2;
        return gridSampler.sampleGrid(bitMatrix, dimension, dimension, f3, f3, f4, f3, f4, f4, f3, f4, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint4.getX(), resultPoint4.getY());
    }

    private int sampleLine(ResultPoint resultPoint, ResultPoint resultPoint2, int i2) {
        float fDistance = distance(resultPoint, resultPoint2);
        float f2 = fDistance / i2;
        float x2 = resultPoint.getX();
        float y2 = resultPoint.getY();
        float x3 = ((resultPoint2.getX() - resultPoint.getX()) * f2) / fDistance;
        float y3 = (f2 * (resultPoint2.getY() - resultPoint.getY())) / fDistance;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            float f3 = i4;
            if (this.image.get(MathUtils.round((f3 * x3) + x2), MathUtils.round((f3 * y3) + y2))) {
                i3 |= 1 << ((i2 - i4) - 1);
            }
        }
        return i3;
    }

    public AztecDetectorResult detect() throws NotFoundException {
        return detect(false);
    }

    private static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private boolean isValid(ResultPoint resultPoint) {
        return isValid(MathUtils.round(resultPoint.getX()), MathUtils.round(resultPoint.getY()));
    }

    public AztecDetectorResult detect(boolean z2) throws NotFoundException {
        ResultPoint[] bullsEyeCorners = getBullsEyeCorners(getMatrixCenter());
        if (z2) {
            ResultPoint resultPoint = bullsEyeCorners[0];
            bullsEyeCorners[0] = bullsEyeCorners[2];
            bullsEyeCorners[2] = resultPoint;
        }
        extractParameters(bullsEyeCorners);
        BitMatrix bitMatrix = this.image;
        int i2 = this.shift;
        return new AztecDetectorResult(sampleGrid(bitMatrix, bullsEyeCorners[i2 % 4], bullsEyeCorners[(i2 + 1) % 4], bullsEyeCorners[(i2 + 2) % 4], bullsEyeCorners[(i2 + 3) % 4]), getMatrixCornerPoints(bullsEyeCorners), this.compact, this.nbDataBlocks, this.nbLayers);
    }
}
