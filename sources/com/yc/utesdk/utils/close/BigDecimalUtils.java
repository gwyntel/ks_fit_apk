package com.yc.utesdk.utils.close;

import java.math.BigDecimal;

/* loaded from: classes4.dex */
public class BigDecimalUtils {
    private static BigDecimalUtils instance;

    public static BigDecimalUtils getInstance() {
        if (instance == null) {
            instance = new BigDecimalUtils();
        }
        return instance;
    }

    public float add(float f2, float f3) {
        return new BigDecimal(String.valueOf(f2)).add(new BigDecimal(String.valueOf(f3))).floatValue();
    }

    public float divide(float f2, float f3) {
        if (f3 == 0.0f) {
            return 0.0f;
        }
        return new BigDecimal(String.valueOf(f2)).divide(new BigDecimal(String.valueOf(f3)), 6, 4).floatValue();
    }

    public int getDecimalPointBehind(float f2) {
        String strValueOf = String.valueOf(f2);
        return Integer.valueOf(strValueOf.substring(strValueOf.indexOf(".") + 1)).intValue();
    }

    public int getDecimalPointFront(float f2) {
        String strValueOf = String.valueOf(f2);
        return Integer.valueOf(strValueOf.substring(0, strValueOf.indexOf("."))).intValue();
    }

    public float multiply(float f2, float f3) {
        return new BigDecimal(String.valueOf(f2)).multiply(new BigDecimal(String.valueOf(f3))).floatValue();
    }

    public float subtract(float f2, float f3) {
        return new BigDecimal(String.valueOf(f2)).subtract(new BigDecimal(String.valueOf(f3))).floatValue();
    }
}
