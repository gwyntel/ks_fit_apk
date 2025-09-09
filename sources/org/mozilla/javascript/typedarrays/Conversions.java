package org.mozilla.javascript.typedarrays;

import androidx.core.view.InputDeviceCompat;
import org.mozilla.javascript.ScriptRuntime;

/* loaded from: classes5.dex */
public class Conversions {
    public static final int EIGHT_BIT = 256;
    public static final int SIXTEEN_BIT = 65536;
    public static final long THIRTYTWO_BIT = 4294967296L;

    public static int toInt16(Object obj) {
        int iIntValue = (obj instanceof Integer ? ((Integer) obj).intValue() : ScriptRuntime.toInt32(obj)) % 65536;
        return iIntValue >= 32768 ? iIntValue - 65536 : iIntValue;
    }

    public static int toInt32(Object obj) {
        long number = ((long) ScriptRuntime.toNumber(obj)) % THIRTYTWO_BIT;
        if (number >= 2147483648L) {
            number -= THIRTYTWO_BIT;
        }
        return (int) number;
    }

    public static int toInt8(Object obj) {
        int iIntValue = (obj instanceof Integer ? ((Integer) obj).intValue() : ScriptRuntime.toInt32(obj)) % 256;
        return iIntValue >= 128 ? iIntValue + InputDeviceCompat.SOURCE_ANY : iIntValue;
    }

    public static int toUint16(Object obj) {
        return (obj instanceof Integer ? ((Integer) obj).intValue() : ScriptRuntime.toInt32(obj)) % 65536;
    }

    public static long toUint32(Object obj) {
        return ((long) ScriptRuntime.toNumber(obj)) % THIRTYTWO_BIT;
    }

    public static int toUint8(Object obj) {
        return (obj instanceof Integer ? ((Integer) obj).intValue() : ScriptRuntime.toInt32(obj)) % 256;
    }

    public static int toUint8Clamp(Object obj) {
        double number = ScriptRuntime.toNumber(obj);
        if (number <= 0.0d) {
            return 0;
        }
        if (number >= 255.0d) {
            return 255;
        }
        double dFloor = Math.floor(number);
        double d2 = 0.5d + dFloor;
        if (d2 < number) {
            return (int) (dFloor + 1.0d);
        }
        if (number < d2) {
            return (int) dFloor;
        }
        int i2 = (int) dFloor;
        return i2 % 2 != 0 ? i2 + 1 : i2;
    }
}
