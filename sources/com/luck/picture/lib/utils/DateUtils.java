package com.luck.picture.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import com.luck.picture.lib.R;
import com.xiaomi.mipush.sdk.Constants;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class DateUtils {

    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat SF = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat SDF = new SimpleDateFormat(CalendarUtils.yyyy_MM);

    @SuppressLint({"SimpleDateFormat"})
    private static final SimpleDateFormat SDF_YEAR = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss);

    public static String cdTime(long j2, long j3) {
        long j4 = j3 - j2;
        if (j4 > 1000) {
            return (j4 / 1000) + "秒";
        }
        return j4 + "毫秒";
    }

    public static int dateDiffer(long j2) {
        try {
            return (int) Math.abs(getCurrentTimeMillis() - j2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    public static String formatDurationTime(long j2) {
        String str = j2 < 0 ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : "";
        long jAbs = Math.abs(j2) / 1000;
        long j3 = jAbs % 60;
        long j4 = (jAbs / 60) % 60;
        long j5 = jAbs / 3600;
        return j5 > 0 ? String.format(Locale.getDefault(), "%s%d:%02d:%02d", str, Long.valueOf(j5), Long.valueOf(j4), Long.valueOf(j3)) : String.format(Locale.getDefault(), "%s%02d:%02d", str, Long.valueOf(j4), Long.valueOf(j3));
    }

    public static String getCreateFileName(String str) {
        return str + SF.format(Long.valueOf(System.currentTimeMillis()));
    }

    public static long getCurrentTimeMillis() {
        String string = ValueOf.toString(Long.valueOf(System.currentTimeMillis()));
        if (string.length() > 10) {
            string = string.substring(0, 10);
        }
        return ValueOf.toLong(string);
    }

    public static String getDataFormat(Context context, long j2) {
        if (String.valueOf(j2).length() <= 10) {
            j2 *= 1000;
        }
        return isThisWeek(j2) ? context.getString(R.string.ps_current_week) : isThisMonth(j2) ? context.getString(R.string.ps_current_month) : SDF.format(Long.valueOf(j2));
    }

    public static String getYearDataFormat(long j2) {
        if (String.valueOf(j2).length() <= 10) {
            j2 *= 1000;
        }
        return SDF_YEAR.format(Long.valueOf(j2));
    }

    public static boolean isThisMonth(long j2) {
        Date date = new Date(j2);
        SimpleDateFormat simpleDateFormat = SDF;
        return simpleDateFormat.format(date).equals(simpleDateFormat.format(new Date()));
    }

    private static boolean isThisWeek(long j2) {
        Calendar calendar = Calendar.getInstance();
        int i2 = calendar.get(3);
        calendar.setTime(new Date(j2));
        return calendar.get(3) == i2;
    }

    public static long millisecondToSecond(long j2) {
        return (j2 / 1000) * 1000;
    }

    public static String getCreateFileName() {
        return SF.format(Long.valueOf(System.currentTimeMillis()));
    }
}
