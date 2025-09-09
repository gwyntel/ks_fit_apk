package com.yc.utesdk.utils.open;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class CalendarUtils {
    private static final String gang = "-";
    public static String[] weeks = null;
    public static final String yyyy = "yyyy";
    public static final String yyyyMM = "yyyyMM";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddHH = "yyyyMMddHH";
    public static final String yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyy_MM = "yyyy-MM";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_HH = "yyyy-MM-dd HH";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static String calendarChangeFormat(String str, boolean z2) {
        StringBuilder sb;
        String strSubstring;
        if (str == null || str.length() < 8) {
            return "";
        }
        if (z2) {
            sb = new StringBuilder();
            sb.append(str.substring(0, 4));
            sb.append("-");
            sb.append(str.substring(4, 6));
            sb.append("-");
            strSubstring = str.substring(6, 8);
        } else {
            sb = new StringBuilder();
            sb.append(str.substring(6, 8));
            sb.append("-");
            sb.append(str.substring(4, 6));
            sb.append("-");
            strSubstring = str.substring(0, 4);
        }
        sb.append(strSubstring);
        return sb.toString();
    }

    public static String conversionDateFormat(String str) {
        if (str == null || str.length() != 10) {
            return null;
        }
        return str.substring(0, 4) + str.substring(5, 7) + str.substring(8, 10);
    }

    public static String convertDateForm(String str) {
        new Date();
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMdd, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyy_MM_dd, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 8) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm10(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 14) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm11(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 14) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm12(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyy_MM_dd, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 14) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm13(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm", locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 16) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm14(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 19) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm15(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 19) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm2(String str) {
        new Date();
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMdd, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyy_MM_dd, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 10) {
                return simpleDateFormat.format(simpleDateFormat2.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm3(String str) {
        new Date();
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmm, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyy_MM_dd_HH_mm, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 12) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm4(String str) {
        new Date();
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 19) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm5(String str) {
        new Date();
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 14) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm6(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyyMMdd, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 19) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm7(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 19) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm8(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyyMMdd, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 14) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateForm9(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm", locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 14) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static String convertDateFormUniversal(String str, String str2, String str3) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2, locale);
        try {
            return new SimpleDateFormat(str3, locale).format(simpleDateFormat.parse(str));
        } catch (ParseException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static long dateToStamp(String str) throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, Locale.US);
        if (!TextUtils.isEmpty(str) && str.length() != 14) {
            str = str.length() > 14 ? str.substring(0, 14) : utendo(str, 14);
        }
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
            date = null;
        }
        if (date != null) {
            return date.getTime();
        }
        return 0L;
    }

    public static int dayCntOfMonth(int i2, int i3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, i2);
        calendar.set(2, i3 - 1);
        return calendar.getActualMaximum(5);
    }

    public static String displayCalendarTime(int i2) {
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        String strValueOf = String.valueOf(i3);
        String strValueOf2 = String.valueOf(i4);
        if (i3 < 10) {
            strValueOf = "0" + strValueOf;
        }
        if (i4 < 10) {
            strValueOf2 = "0" + strValueOf2;
        }
        return DateFormat.getDateTimeInstance(2, 3, Locale.getDefault()).format(new SimpleDateFormat(yyyyMMddHHmm, Locale.US).parse(getCalendar(0) + strValueOf + "" + strValueOf2, new ParsePosition(0)));
    }

    public static String displayCurrentDateTime() {
        return DateFormat.getDateTimeInstance(2, 3, Locale.getDefault()).format(new Date(System.currentTimeMillis()));
    }

    public static String displayDateFormat(int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, i2);
        return DateFormat.getDateInstance(2, Locale.getDefault()).format(calendar.getTime());
    }

    public static String displayDateTimeSecond(String str) {
        return DateFormat.getDateTimeInstance(2, 2, Locale.getDefault()).format(strToDateTime(str));
    }

    public static String displayDateTimeSecond2(String str) {
        return DateFormat.getDateTimeInstance(2, 3, Locale.getDefault()).format(strToDateTime2(str));
    }

    public static String displayDateWeekFormat(String str) {
        return DateFormat.getDateInstance(0, Locale.getDefault()).format(strToDate(str));
    }

    public static boolean filterDate(String str, String str2) {
        return !TextUtils.isEmpty(str) && str.length() == 8 && str.compareTo("2147483647") < 0 && Integer.valueOf(str).intValue() >= Integer.valueOf(getCalendar(-365)).intValue() && Integer.valueOf(str).intValue() <= getToday() && Integer.valueOf(str).intValue() >= Integer.valueOf(str2).intValue();
    }

    public static String get15DaysAgoTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, -15);
        return new SimpleDateFormat(yyyyMMddHHmm, Locale.US).format(calendar.getTime());
    }

    public static String getCalendar() {
        return new SimpleDateFormat(yyyyMMdd, Locale.US).format(new Date());
    }

    public static String getCalendar2(int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, i2);
        return new SimpleDateFormat(yyyy_MM_dd, Locale.US).format(calendar.getTime());
    }

    public static String getCalendar3(int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, i2);
        return new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss, Locale.US).format(calendar.getTime());
    }

    public static String getCalendarAndTime() {
        return new SimpleDateFormat(yyyyMMddHHmmss, Locale.US).format(new Date());
    }

    public static String getCalendarAndTime2() {
        return new SimpleDateFormat(yyyyMMddHHmm, Locale.US).format(new Date());
    }

    public static String getCalendarTime(String str, int i2) {
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        String strValueOf = String.valueOf(i3);
        String strValueOf2 = String.valueOf(i4);
        if (i3 < 10) {
            strValueOf = "0" + strValueOf;
        }
        if (i4 < 10) {
            strValueOf2 = "0" + strValueOf2;
        }
        return str + strValueOf + strValueOf2;
    }

    public static String getDateStr(String str, int i2) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMdd, Locale.US);
        Date date = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return new SimpleDateFormat(yyyyMMdd, Locale.US).format(new Date(date.getTime() + (i2 * 86400000)));
    }

    public static int getDayDistance(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(str));
        long timeInMillis = calendar.getTimeInMillis();
        calendar.setTime(strToDate(str2));
        return (int) ((timeInMillis - calendar.getTimeInMillis()) / 86400000);
    }

    public static int getDayOfWeek(String str) {
        Calendar calendar = Calendar.getInstance();
        Date dateStrToDate = strToDate(str);
        if (dateStrToDate != null) {
            calendar.setTime(dateStrToDate);
        }
        return calendar.get(7);
    }

    public static int getGapCount(String str, String str2) throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMdd, Locale.US);
        Date date2 = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e2) {
            e = e2;
            date = null;
        }
        try {
            date2 = simpleDateFormat.parse(str2);
        } catch (ParseException e3) {
            e = e3;
            e.printStackTrace();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);
            calendar2.set(11, 0);
            calendar2.set(12, 0);
            calendar2.set(13, 0);
            calendar2.set(14, 0);
            return (int) ((calendar2.getTime().getTime() - calendar.getTime().getTime()) / 86400000);
        }
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(date);
        calendar3.set(11, 0);
        calendar3.set(12, 0);
        calendar3.set(13, 0);
        calendar3.set(14, 0);
        Calendar calendar22 = Calendar.getInstance();
        calendar22.setTime(date2);
        calendar22.set(11, 0);
        calendar22.set(12, 0);
        calendar22.set(13, 0);
        calendar22.set(14, 0);
        return (int) ((calendar22.getTime().getTime() - calendar3.getTime().getTime()) / 86400000);
    }

    public static int getMinute(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(str2, Locale.US).parse(str));
            return (calendar.get(11) * 60) + calendar.get(12);
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String getMonth(int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, i2);
        return new SimpleDateFormat(yyyyMMdd, Locale.US).format(calendar.getTime());
    }

    public static String getMonthAgo(String str, int i2) throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMdd, Locale.US);
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e2) {
            e2.printStackTrace();
            date = null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, i2);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int getPhoneCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(11) * 60) + calendar.get(12);
    }

    public static int getSecond(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(str2, Locale.US).parse(str));
            return (calendar.get(11) * 3600) + (calendar.get(12) * 60) + calendar.get(13);
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static int getSeconds(int i2) {
        return ((int) (System.currentTimeMillis() / 1000)) - (i2 * 3600);
    }

    public static String getTimeAdd(String str, int i2) {
        long jUtendo = utendo(str) + (i2 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, Locale.US);
        Date date = new Date();
        date.setTime(jUtendo);
        return simpleDateFormat.format(date);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getTimeDiff(java.lang.String r3, java.lang.String r4) throws java.text.ParseException {
        /*
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.US
            java.lang.String r2 = "yyyyMMddHHmmss"
            r0.<init>(r2, r1)
            r1 = 0
            java.util.Date r3 = r0.parse(r3)     // Catch: java.text.ParseException -> L15
            java.util.Date r1 = r0.parse(r4)     // Catch: java.text.ParseException -> L13
            goto L1a
        L13:
            r4 = move-exception
            goto L17
        L15:
            r4 = move-exception
            r3 = r1
        L17:
            r4.printStackTrace()
        L1a:
            long r0 = r1.getTime()
            long r3 = r3.getTime()
            long r0 = r0 - r3
            r3 = 0
            int r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r2 >= 0) goto L2a
            r0 = r3
        L2a:
            r3 = 1000(0x3e8, double:4.94E-321)
            long r0 = r0 / r3
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.open.CalendarUtils.getTimeDiff(java.lang.String, java.lang.String):long");
    }

    public static int getTimeExpend(String str, String str2) {
        return (int) ((utendo(str2) - utendo(str)) / 1000);
    }

    public static long getTimeStamp() {
        return new Date().getTime();
    }

    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, 0);
        return Integer.valueOf(new SimpleDateFormat(yyyyMMdd, Locale.US).format(calendar.getTime())).intValue();
    }

    public static int getWeekOfMonth(String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(str));
        return calendar.get(4);
    }

    public static String getYear() {
        return new SimpleDateFormat(yyyy, Locale.US).format(new Date());
    }

    public static String getYearMonth(int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, i2);
        return new SimpleDateFormat(yyyyMM, Locale.US).format(calendar.getTime());
    }

    public static boolean is24HourFormat(Context context) {
        String string = Settings.System.getString(context.getContentResolver(), "time_12_24");
        return string == null ? android.text.format.DateFormat.is24HourFormat(context) : !string.equals(AgooConstants.ACK_PACK_NULL);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int isDateOneBigger(java.lang.String r6, java.lang.String r7) throws java.text.ParseException {
        /*
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.US
            java.lang.String r2 = "yyyyMMdd"
            r0.<init>(r2, r1)
            r1 = 0
            java.util.Date r6 = r0.parse(r6)     // Catch: java.text.ParseException -> L15
            java.util.Date r1 = r0.parse(r7)     // Catch: java.text.ParseException -> L13
            goto L1a
        L13:
            r7 = move-exception
            goto L17
        L15:
            r7 = move-exception
            r6 = r1
        L17:
            r7.printStackTrace()
        L1a:
            long r2 = r6.getTime()
            long r4 = r1.getTime()
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            r0 = 1
            if (r7 <= 0) goto L28
            goto L43
        L28:
            long r2 = r6.getTime()
            long r4 = r1.getTime()
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 >= 0) goto L36
            r0 = -1
            goto L43
        L36:
            long r6 = r6.getTime()
            long r1 = r1.getTime()
            int r6 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r6 != 0) goto L43
            r0 = 0
        L43:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.open.CalendarUtils.isDateOneBigger(java.lang.String, java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isWeekSame(java.lang.String r6, java.lang.String r7) throws java.text.ParseException {
        /*
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.util.Locale r1 = java.util.Locale.US
            java.lang.String r2 = "yyyyMMdd"
            r0.<init>(r2, r1)
            r1 = 0
            java.util.Date r6 = r0.parse(r6)     // Catch: java.lang.Exception -> L15
            java.util.Date r1 = r0.parse(r7)     // Catch: java.lang.Exception -> L13
            goto L1a
        L13:
            r7 = move-exception
            goto L17
        L15:
            r7 = move-exception
            r6 = r1
        L17:
            r7.printStackTrace()
        L1a:
            java.util.Calendar r7 = java.util.Calendar.getInstance()
            java.util.Calendar r0 = java.util.Calendar.getInstance()
            r2 = 1
            r7.setFirstDayOfWeek(r2)
            r0.setFirstDayOfWeek(r2)
            r7.setTime(r6)
            r0.setTime(r1)
            int r6 = r7.get(r2)
            int r1 = r0.get(r2)
            int r6 = r6 - r1
            r1 = 3
            if (r6 != 0) goto L46
            int r6 = r7.get(r1)
            int r7 = r0.get(r1)
            if (r6 != r7) goto L70
            return r2
        L46:
            r3 = 11
            r4 = 2
            if (r6 != r2) goto L5c
            int r5 = r0.get(r4)
            if (r5 != r3) goto L5c
            int r6 = r7.get(r1)
            int r7 = r0.get(r1)
            if (r6 != r7) goto L70
            return r2
        L5c:
            r5 = -1
            if (r6 != r5) goto L70
            int r6 = r7.get(r4)
            if (r6 != r3) goto L70
            int r6 = r7.get(r1)
            int r7 = r0.get(r1)
            if (r6 != r7) goto L70
            return r2
        L70:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.open.CalendarUtils.isWeekSame(java.lang.String, java.lang.String):boolean");
    }

    public static String judgeSleepTime(String str) throws ParseException {
        StringBuilder sb;
        String strSubstring;
        int iIntValue = Integer.valueOf(str.substring(8, 10)).intValue();
        if (iIntValue < 0 || iIntValue >= 18) {
            sb = new StringBuilder();
            strSubstring = str.substring(0, 8);
        } else {
            sb = new StringBuilder();
            strSubstring = getDateStr(str.substring(0, 8), -1);
        }
        sb.append(strSubstring);
        sb.append("1800");
        return sb.toString();
    }

    public static String minToTime(int i2) {
        String strValueOf;
        String strValueOf2;
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        if (i3 >= 10) {
            strValueOf = String.valueOf(i3);
        } else {
            strValueOf = "0" + i3;
        }
        if (i4 >= 10) {
            strValueOf2 = String.valueOf(i4);
        } else {
            strValueOf2 = "0" + i4;
        }
        return strValueOf + ":" + strValueOf2;
    }

    public static String secondToSpeed(int i2) {
        int i3;
        int i4;
        if (i2 != 0) {
            i4 = i2 / 60;
            i3 = i2 % 60;
        } else {
            i3 = 0;
            i4 = 0;
        }
        try {
            return String.format("%1$02d'%2$02d''", Integer.valueOf(i4), Integer.valueOf(i3));
        } catch (Exception unused) {
            return "" + i4 + "'" + i3 + "''";
        }
    }

    public static String strMinuteToDateSecTime(String str) {
        Locale locale = Locale.US;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss, locale);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss, locale);
        try {
            if (!TextUtils.isEmpty(str) && str.length() == 14) {
                return simpleDateFormat2.format(simpleDateFormat.parse(str));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        return "";
    }

    public static Date strToDate(String str) {
        return new SimpleDateFormat(yyyyMMdd, Locale.US).parse(str, new ParsePosition(0));
    }

    public static Date strToDateTime(String str) {
        return new SimpleDateFormat(yyyyMMddHHmmss, Locale.US).parse(str, new ParsePosition(0));
    }

    public static Date strToDateTime2(String str) {
        return new SimpleDateFormat(yyyy_MM_dd_HH_mm, Locale.US).parse(str, new ParsePosition(0));
    }

    public static int timeToMin(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        return (Integer.valueOf(str.substring(0, str.indexOf(":"))).intValue() * 60) + Integer.valueOf(str.substring(str.indexOf(":") + 1, str.length())).intValue();
    }

    public static String timesStampToDate(int i2) {
        return new SimpleDateFormat(yyyyMMddHHmmss, Locale.US).format(new Date(i2 * 1000));
    }

    public static String timesStampToDateGMT8(int i2) {
        return new SimpleDateFormat(yyyyMMddHHmmss, Locale.US).format(new Date((i2 + utendo()) * 1000));
    }

    public static String utendo(String str, int i2) {
        while (str.length() < i2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str);
            stringBuffer.append("0");
            str = stringBuffer.toString();
        }
        return str;
    }

    public static int dayCntOfMonth(String str) {
        return dayCntOfMonth(Integer.valueOf(str.substring(0, 4)).intValue(), Integer.valueOf(str.substring(4, 6)).intValue());
    }

    public static String displayCalendarTime(String str, int i2) {
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        String strValueOf = String.valueOf(i3);
        String strValueOf2 = String.valueOf(i4);
        if (i3 < 10) {
            strValueOf = "0" + strValueOf;
        }
        if (i4 < 10) {
            strValueOf2 = "0" + strValueOf2;
        }
        return DateFormat.getDateTimeInstance(2, 3, Locale.getDefault()).format(new SimpleDateFormat(yyyyMMddHHmm, Locale.US).parse(str + strValueOf + "" + strValueOf2, new ParsePosition(0)));
    }

    public static String displayDateFormat(String str) {
        return DateFormat.getDateInstance(2, Locale.getDefault()).format(strToDate(str));
    }

    public static String getCalendar(int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, i2);
        return new SimpleDateFormat(yyyyMMdd, Locale.US).format(calendar.getTime());
    }

    public static String timesStampToDate(long j2) {
        return new SimpleDateFormat(yyyyMMddHHmmss, Locale.US).format(new Date(j2));
    }

    public static long utendo(String str) {
        try {
            return new SimpleDateFormat(yyyyMMddHHmmss, Locale.US).parse(str).getTime();
        } catch (ParseException unused) {
            return 0L;
        }
    }

    public static int utendo() {
        return 28800 - (Calendar.getInstance().getTimeZone().getOffset(System.currentTimeMillis()) / 1000);
    }
}
