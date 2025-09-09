package com.yc.utesdk.command;

import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/* loaded from: classes4.dex */
public class MultiSportsModesUtils {
    private static MultiSportsModesUtils instance;

    public static MultiSportsModesUtils getInstance() {
        if (instance == null) {
            instance = new MultiSportsModesUtils();
        }
        return instance;
    }

    public int StrToInt(String str) {
        try {
            long jLongValue = Long.valueOf(str).longValue();
            if (jLongValue <= 2147483647L && jLongValue >= -2147483648L) {
                return Integer.valueOf(str).intValue();
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public float bleAveragePace(int i2, byte[] bArr) {
        return (((bArr[i2] & 255) * 60) + (bArr[i2 + 1] & 255)) / 60.0f;
    }

    public float bleAveragePaceReal(byte[] bArr) {
        return (((bArr[5] & 255) * 60) + (bArr[6] & 255)) / 60.0f;
    }

    public int bleAverageRate(byte[] bArr) {
        return bArr[12] & 255;
    }

    public int bleMaxRate(byte[] bArr) {
        return bArr[13] & 255;
    }

    public int bleMinRate(byte[] bArr) {
        return bArr[14] & 255;
    }

    public int blePace(int i2, byte[] bArr) {
        return ((bArr[i2] & 255) * 60) + (bArr[i2 + 1] & 255);
    }

    public int bleRateReal(byte[] bArr) {
        return bArr[2] & 255;
    }

    public int bleSportsCalories(byte[] bArr) {
        return (bArr[9] & 255) | ((bArr[8] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int bleSportsCaloriesReal(byte[] bArr) {
        return (bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int bleSportsCount(byte[] bArr) {
        return (bArr[7] & 255) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int bleSportsCountReal(byte[] bArr) {
        return (bArr[11] & 255) | ((bArr[10] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public float bleSportsDistance(byte[] bArr) {
        return ((bArr[10] & 255) + ((bArr[11] & 255) / 100.0f)) * 1000.0f;
    }

    public float bleSportsDistanceReal(byte[] bArr) {
        return ((bArr[12] & 255) + ((bArr[13] & 255) / 100.0f)) * 1000.0f;
    }

    public int bleStepCount(byte[] bArr) {
        return (bArr[5] & 255) | ((bArr[3] << 16) & 16711680) | ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int bleStepCountReal(byte[] bArr) {
        return (bArr[9] & 255) | ((bArr[7] << 16) & 16711680) | ((bArr[8] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int bleTimeInterval(byte[] bArr) {
        int i2 = bArr[17] & 255;
        return i2 >= 220 ? i2 - 220 : i2 * 10;
    }

    public int currentSportsModes(byte[] bArr) {
        return bArr[3] & 255;
    }

    public int currentSportsModesReal(byte[] bArr) {
        return bArr[1];
    }

    public String endDateTime(byte[] bArr, String str) {
        if (DeviceMode.isHasFunction_5(262144)) {
            return str.length() == 40 ? CalendarUtils.timesStampToDateGMT8(Integer.parseInt(str.substring(26, 34), 16)) : "";
        }
        int i2 = bArr[19] & 255;
        int i3 = bArr[18] & 255;
        int i4 = bArr[17] & 255;
        int i5 = bArr[16] & 255;
        int i6 = bArr[15] & 255;
        int i7 = (bArr[14] & 255) | ((bArr[13] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        String strValueOf3 = String.valueOf(i4);
        String strValueOf4 = String.valueOf(i5);
        String strValueOf5 = String.valueOf(i6);
        String strValueOf6 = String.valueOf(i7);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        if (i4 < 10) {
            strValueOf3 = "0" + i4;
        }
        if (i5 < 10) {
            strValueOf4 = "0" + i5;
        }
        if (i6 < 10) {
            strValueOf5 = "0" + i6;
        }
        return strValueOf6 + strValueOf5 + strValueOf4 + strValueOf3 + strValueOf2 + strValueOf;
    }

    public String formatSimpleData(float f2) {
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)).format(f2);
    }

    public int getHeartRateDuration(int i2, byte[] bArr) {
        return (bArr[i2 + 2] & 255) | ((bArr[i2] << 16) & 16711680) | ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int getSwimAvgStrokeFrequency(byte[] bArr) {
        return bArr[13] & 255;
    }

    public int getSwimAvgSwolf(byte[] bArr) {
        return (bArr[19] & 255) | ((bArr[18] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int getSwimBestSwolf(byte[] bArr) {
        return (bArr[17] & 255) | ((bArr[16] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int getSwimLaps(byte[] bArr) {
        return (bArr[21] & 255) | ((bArr[20] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public String getSwimStartTime(byte[] bArr) {
        int i2 = bArr[9] & 255;
        int i3 = bArr[8] & 255;
        int i4 = bArr[7] & 255;
        int i5 = bArr[6] & 255;
        int i6 = bArr[5] & 255;
        int i7 = (bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        String strValueOf3 = String.valueOf(i4);
        String strValueOf4 = String.valueOf(i5);
        String strValueOf5 = String.valueOf(i6);
        String strValueOf6 = String.valueOf(i7);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        if (i4 < 10) {
            strValueOf3 = "0" + i4;
        }
        if (i5 < 10) {
            strValueOf4 = "0" + i5;
        }
        if (i6 < 10) {
            strValueOf5 = "0" + i6;
        }
        return strValueOf6 + strValueOf5 + strValueOf4 + strValueOf3 + strValueOf2 + strValueOf;
    }

    public int getSwimStrokeFrequency(byte[] bArr) {
        return bArr[12] & 255;
    }

    public int getSwimStrokeTimes(byte[] bArr) {
        return (bArr[11] & 255) | ((bArr[10] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int getSwimSwolf(byte[] bArr) {
        return (bArr[15] & 255) | ((bArr[14] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int getSwimType(byte[] bArr) {
        return bArr[22] & 255;
    }

    public String listToString(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                sb.append(strArr[i2]);
            }
        }
        return sb.toString();
    }

    public int sportsModesRateCount(byte[] bArr) {
        return ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[5] & 255);
    }

    public int sportsModesSerialNumber(byte[] bArr) {
        return ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[2] & 255);
    }

    public String startCalendar(byte[] bArr) {
        int i2 = bArr[9] & 255;
        int i3 = bArr[8] & 255;
        int i4 = (bArr[7] & 255) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
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

    public String startDateTime(byte[] bArr) {
        int i2 = bArr[12] & 255;
        int i3 = bArr[11] & 255;
        int i4 = bArr[10] & 255;
        String strValueOf = String.valueOf(i2);
        String strValueOf2 = String.valueOf(i3);
        String strValueOf3 = String.valueOf(i4);
        if (i2 < 10) {
            strValueOf = "0" + i2;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        if (i4 < 10) {
            strValueOf3 = "0" + i4;
        }
        return startCalendar(bArr) + strValueOf3 + strValueOf2 + strValueOf;
    }

    public int syncSportsModesDataCount(byte[] bArr) {
        return (bArr[9] & 255) | ((bArr[8] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public int validExerciseTime(byte[] bArr, String str) {
        if (!DeviceMode.isHasFunction_5(262144)) {
            return CalendarUtils.getTimeExpend(startDateTime(bArr), endDateTime(bArr, str));
        }
        if (str.length() == 40) {
            return Integer.parseInt(str.substring(34, 40), 16);
        }
        return 0;
    }
}
