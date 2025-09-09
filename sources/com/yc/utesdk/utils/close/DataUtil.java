package com.yc.utesdk.utils.close;

import androidx.core.view.MotionEventCompat;

/* loaded from: classes4.dex */
public class DataUtil {
    private static DataUtil instance;

    public static DataUtil getInstance() {
        if (instance == null) {
            instance = new DataUtil();
        }
        return instance;
    }

    public String startMenstrualCalendar(byte[] bArr) {
        int i2 = bArr[6] & 255;
        int i3 = bArr[5] & 255;
        int i4 = (bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        String strValueOf3 = String.valueOf(i4);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        return strValueOf3 + strValueOf2 + strValueOf;
    }
}
