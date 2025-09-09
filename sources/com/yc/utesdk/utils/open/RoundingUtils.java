package com.yc.utesdk.utils.open;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/* loaded from: classes4.dex */
public class RoundingUtils {
    public static final int ROUNDING_0 = 0;
    public static final int ROUNDING_1 = 1;
    public static final int ROUNDING_2 = 2;
    public static final int ROUNDING_3 = 3;
    private static RoundingUtils instance;

    public static RoundingUtils getInstance() {
        if (instance == null) {
            instance = new RoundingUtils();
        }
        return instance;
    }

    public int celsiusToFahrenheit(int i2) {
        return (int) (((i2 * 9.0f) / 5.0f) + 32.0f);
    }

    public float celsiusToFahrenheitF(float f2) {
        return roundingToFloat(1, ((f2 * 9.0f) / 5.0f) + 32.0f);
    }

    public int fahrenheitToCelsius(int i2) {
        return roundingToInt((i2 - 32) * 0.5555556f);
    }

    public float fahrenheitToCelsiusF(float f2) {
        return roundingToFloat(1, (f2 - 32.0f) * 0.5555556f);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public float roundingToFloat(int i2, double d2) {
        float f2 = 5.0E-6f;
        String str = "%.0f";
        switch (i2) {
            case 0:
                break;
            case 1:
                str = "%.1f";
                break;
            case 2:
                str = "%.2f";
                break;
            case 3:
                str = "%.3f";
                break;
            case 4:
                str = "%.4f";
                f2 = 0.0f;
                break;
            case 5:
                str = "%.5f";
                f2 = 0.0f;
                break;
            case 6:
                str = "%.6f";
                f2 = 0.0f;
                break;
            case 7:
                str = "%.7f";
                f2 = 0.0f;
                break;
            case 8:
                str = "%.8f";
                f2 = 0.0f;
                break;
            case 9:
                str = "%.9f";
                f2 = 0.0f;
                break;
            case 10:
                str = "%.10f";
                f2 = 0.0f;
                break;
            default:
                f2 = 0.0f;
                break;
        }
        return Float.valueOf(String.format(Locale.US, str, Double.valueOf(d2 + f2))).floatValue();
    }

    public float roundingToFloatDisplay(int i2, double d2) {
        return Float.valueOf(new BigDecimal(d2).setScale(i2, RoundingMode.DOWN).toString()).floatValue();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public String roundingToFloatString(int i2, double d2) {
        float f2 = 5.0E-6f;
        String str = "%.0f";
        switch (i2) {
            case 0:
                break;
            case 1:
                str = "%.1f";
                break;
            case 2:
                str = "%.2f";
                break;
            case 3:
                str = "%.3f";
                break;
            case 4:
                str = "%.4f";
                f2 = 0.0f;
                break;
            case 5:
                str = "%.5f";
                f2 = 0.0f;
                break;
            case 6:
                str = "%.6f";
                f2 = 0.0f;
                break;
            case 7:
                str = "%.7f";
                f2 = 0.0f;
                break;
            case 8:
                str = "%.8f";
                f2 = 0.0f;
                break;
            case 9:
                str = "%.9f";
                f2 = 0.0f;
                break;
            case 10:
                str = "%.10f";
                f2 = 0.0f;
                break;
            default:
                f2 = 0.0f;
                break;
        }
        return String.format(Locale.US, str, Double.valueOf(d2 + f2));
    }

    public int roundingToInt(double d2) {
        return Integer.parseInt(String.format(Locale.US, "%.0f", Double.valueOf(d2 + 5.0E-6f)));
    }

    public String roundingToIntString(double d2) {
        return String.format(Locale.US, "%.0f", Double.valueOf(d2 + 5.0E-6f));
    }
}
