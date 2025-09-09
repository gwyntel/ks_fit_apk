package com.yalantis.ucrop.util;

import android.graphics.RectF;

/* loaded from: classes4.dex */
public class RectUtils {
    public static float[] getCenterFromRect(RectF rectF) {
        return new float[]{rectF.centerX(), rectF.centerY()};
    }

    public static float[] getCornersFromRect(RectF rectF) {
        float f2 = rectF.left;
        float f3 = rectF.top;
        float f4 = rectF.right;
        float f5 = rectF.bottom;
        return new float[]{f2, f3, f4, f3, f4, f5, f2, f5};
    }

    public static float[] getRectSidesFromCorners(float[] fArr) {
        return new float[]{(float) Math.sqrt(Math.pow(fArr[0] - fArr[2], 2.0d) + Math.pow(fArr[1] - fArr[3], 2.0d)), (float) Math.sqrt(Math.pow(fArr[2] - fArr[4], 2.0d) + Math.pow(fArr[3] - fArr[5], 2.0d))};
    }

    public static RectF trapToRect(float[] fArr) {
        RectF rectF = new RectF(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        for (int i2 = 1; i2 < fArr.length; i2 += 2) {
            float fRound = Math.round(fArr[i2 - 1] * 10.0f) / 10.0f;
            float fRound2 = Math.round(fArr[i2] * 10.0f) / 10.0f;
            float f2 = rectF.left;
            if (fRound < f2) {
                f2 = fRound;
            }
            rectF.left = f2;
            float f3 = rectF.top;
            if (fRound2 < f3) {
                f3 = fRound2;
            }
            rectF.top = f3;
            float f4 = rectF.right;
            if (fRound <= f4) {
                fRound = f4;
            }
            rectF.right = fRound;
            float f5 = rectF.bottom;
            if (fRound2 <= f5) {
                fRound2 = f5;
            }
            rectF.bottom = fRound2;
        }
        rectF.sort();
        return rectF;
    }
}
