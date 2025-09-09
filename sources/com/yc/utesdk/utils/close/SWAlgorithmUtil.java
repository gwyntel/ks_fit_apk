package com.yc.utesdk.utils.close;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.utils.open.RoundingUtils;

/* loaded from: classes4.dex */
public class SWAlgorithmUtil {
    private static SWAlgorithmUtil instance;

    public static SWAlgorithmUtil getInstance() {
        if (instance == null) {
            instance = new SWAlgorithmUtil();
        }
        return instance;
    }

    public int getBleCurrentHour(byte[] bArr) {
        return bArr[6] & 255;
    }

    public float getBleCurrentHourCalories(byte[] bArr) {
        return RoundingUtils.getInstance().roundingToFloat(1, (((bArr[11] << 16) & 16711680) | ((bArr[13] & 255) | ((bArr[12] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK))) / 1000.0f);
    }

    public float getBleCurrentHourDistance(byte[] bArr) {
        return RoundingUtils.getInstance().roundingToFloat(2, (((bArr[9] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[10] & 255)) / 1000.0f);
    }

    public int getBleCurrentHourStep(byte[] bArr) {
        return ((bArr[7] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[8] & 255);
    }

    public String getBleDate(byte[] bArr) {
        int i2 = bArr[5] & 255;
        int i3 = bArr[4] & 255;
        int i4 = (bArr[3] & 255) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        return String.valueOf(i4) + strValueOf2 + strValueOf;
    }
}
