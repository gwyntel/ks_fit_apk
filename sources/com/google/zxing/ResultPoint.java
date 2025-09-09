package com.google.zxing;

import com.google.zxing.common.detector.MathUtils;

/* loaded from: classes3.dex */
public class ResultPoint {

    /* renamed from: x, reason: collision with root package name */
    private final float f15397x;

    /* renamed from: y, reason: collision with root package name */
    private final float f15398y;

    public ResultPoint(float f2, float f3) {
        this.f15397x = f2;
        this.f15398y = f3;
    }

    private static float crossProductZ(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        float f2 = resultPoint2.f15397x;
        float f3 = resultPoint2.f15398y;
        return ((resultPoint3.f15397x - f2) * (resultPoint.f15398y - f3)) - ((resultPoint3.f15398y - f3) * (resultPoint.f15397x - f2));
    }

    public static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.f15397x, resultPoint.f15398y, resultPoint2.f15397x, resultPoint2.f15398y);
    }

    public static void orderBestPatterns(ResultPoint[] resultPointArr) {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        float fDistance = distance(resultPointArr[0], resultPointArr[1]);
        float fDistance2 = distance(resultPointArr[1], resultPointArr[2]);
        float fDistance3 = distance(resultPointArr[0], resultPointArr[2]);
        if (fDistance2 >= fDistance && fDistance2 >= fDistance3) {
            resultPoint = resultPointArr[0];
            resultPoint2 = resultPointArr[1];
            resultPoint3 = resultPointArr[2];
        } else if (fDistance3 < fDistance2 || fDistance3 < fDistance) {
            resultPoint = resultPointArr[2];
            resultPoint2 = resultPointArr[0];
            resultPoint3 = resultPointArr[1];
        } else {
            resultPoint = resultPointArr[1];
            resultPoint2 = resultPointArr[0];
            resultPoint3 = resultPointArr[2];
        }
        if (crossProductZ(resultPoint2, resultPoint, resultPoint3) < 0.0f) {
            ResultPoint resultPoint4 = resultPoint3;
            resultPoint3 = resultPoint2;
            resultPoint2 = resultPoint4;
        }
        resultPointArr[0] = resultPoint2;
        resultPointArr[1] = resultPoint;
        resultPointArr[2] = resultPoint3;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ResultPoint) {
            ResultPoint resultPoint = (ResultPoint) obj;
            if (this.f15397x == resultPoint.f15397x && this.f15398y == resultPoint.f15398y) {
                return true;
            }
        }
        return false;
    }

    public final float getX() {
        return this.f15397x;
    }

    public final float getY() {
        return this.f15398y;
    }

    public final int hashCode() {
        return (Float.floatToIntBits(this.f15397x) * 31) + Float.floatToIntBits(this.f15398y);
    }

    public final String toString() {
        return "(" + this.f15397x + ',' + this.f15398y + ')';
    }
}
