package org.mozilla.javascript;

import com.luck.picture.lib.config.FileSizeUnit;
import com.yc.utesdk.ble.close.KeyType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/* loaded from: classes5.dex */
final class NativeDate extends IdScriptableObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ConstructorId_UTC = -1;
    private static final int ConstructorId_now = -3;
    private static final int ConstructorId_parse = -2;
    private static final Object DATE_TAG = "Date";
    private static final double HalfTimeDomain = 8.64E15d;
    private static final double HoursPerDay = 24.0d;
    private static final int Id_constructor = 1;
    private static final int Id_getDate = 17;
    private static final int Id_getDay = 19;
    private static final int Id_getFullYear = 13;
    private static final int Id_getHours = 21;
    private static final int Id_getMilliseconds = 27;
    private static final int Id_getMinutes = 23;
    private static final int Id_getMonth = 15;
    private static final int Id_getSeconds = 25;
    private static final int Id_getTime = 11;
    private static final int Id_getTimezoneOffset = 29;
    private static final int Id_getUTCDate = 18;
    private static final int Id_getUTCDay = 20;
    private static final int Id_getUTCFullYear = 14;
    private static final int Id_getUTCHours = 22;
    private static final int Id_getUTCMilliseconds = 28;
    private static final int Id_getUTCMinutes = 24;
    private static final int Id_getUTCMonth = 16;
    private static final int Id_getUTCSeconds = 26;
    private static final int Id_getYear = 12;
    private static final int Id_setDate = 39;
    private static final int Id_setFullYear = 43;
    private static final int Id_setHours = 37;
    private static final int Id_setMilliseconds = 31;
    private static final int Id_setMinutes = 35;
    private static final int Id_setMonth = 41;
    private static final int Id_setSeconds = 33;
    private static final int Id_setTime = 30;
    private static final int Id_setUTCDate = 40;
    private static final int Id_setUTCFullYear = 44;
    private static final int Id_setUTCHours = 38;
    private static final int Id_setUTCMilliseconds = 32;
    private static final int Id_setUTCMinutes = 36;
    private static final int Id_setUTCMonth = 42;
    private static final int Id_setUTCSeconds = 34;
    private static final int Id_setYear = 45;
    private static final int Id_toDateString = 4;
    private static final int Id_toGMTString = 8;
    private static final int Id_toISOString = 46;
    private static final int Id_toJSON = 47;
    private static final int Id_toLocaleDateString = 7;
    private static final int Id_toLocaleString = 5;
    private static final int Id_toLocaleTimeString = 6;
    private static final int Id_toSource = 9;
    private static final int Id_toString = 2;
    private static final int Id_toTimeString = 3;
    private static final int Id_toUTCString = 8;
    private static final int Id_valueOf = 10;
    private static double LocalTZA = 0.0d;
    private static final int MAXARGS = 7;
    private static final int MAX_PROTOTYPE_ID = 47;
    private static final double MinutesPerDay = 1440.0d;
    private static final double MinutesPerHour = 60.0d;
    private static final double SecondsPerDay = 86400.0d;
    private static final double SecondsPerHour = 3600.0d;
    private static final double SecondsPerMinute = 60.0d;
    private static final String js_NaN_date_str = "Invalid Date";
    private static DateFormat localeDateFormatter = null;
    private static DateFormat localeDateTimeFormatter = null;
    private static DateFormat localeTimeFormatter = null;
    private static final double msPerDay = 8.64E7d;
    private static final double msPerHour = 3600000.0d;
    private static final double msPerMinute = 60000.0d;
    private static final double msPerSecond = 1000.0d;
    static final long serialVersionUID = -8307438915861678966L;
    private static TimeZone thisTimeZone;
    private static DateFormat timeZoneFormatter;
    private double date;

    private NativeDate() {
        if (thisTimeZone == null) {
            thisTimeZone = TimeZone.getDefault();
            LocalTZA = r0.getRawOffset();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int DateFromTime(double r3) {
        /*
            int r0 = YearFromTime(r3)
            double r3 = Day(r3)
            double r1 = (double) r0
            double r1 = DayFromYear(r1)
            double r3 = r3 - r1
            int r3 = (int) r3
            int r4 = r3 + (-59)
            if (r4 >= 0) goto L1d
            r0 = -28
            if (r4 >= r0) goto L1a
            int r3 = r3 + 1
            goto L1c
        L1a:
            int r3 = r3 + (-30)
        L1c:
            return r3
        L1d:
            boolean r0 = IsLeapYear(r0)
            if (r0 == 0) goto L2a
            if (r4 != 0) goto L28
            r3 = 29
            return r3
        L28:
            int r4 = r3 + (-60)
        L2a:
            int r3 = r4 / 30
            float r3 = (float) r3
            int r3 = java.lang.Math.round(r3)
            r0 = 30
            r1 = 31
            switch(r3) {
                case 0: goto L62;
                case 1: goto L5a;
                case 2: goto L57;
                case 3: goto L54;
                case 4: goto L51;
                case 5: goto L4e;
                case 6: goto L4b;
                case 7: goto L48;
                case 8: goto L43;
                case 9: goto L40;
                case 10: goto L3d;
                default: goto L38;
            }
        L38:
            java.lang.RuntimeException r3 = org.mozilla.javascript.Kit.codeBug()
            throw r3
        L3d:
            int r4 = r4 + (-274)
            return r4
        L40:
            r1 = 275(0x113, float:3.85E-43)
            goto L5b
        L43:
            r3 = 245(0xf5, float:3.43E-43)
        L45:
            r0 = r1
            r1 = r3
            goto L5b
        L48:
            r1 = 214(0xd6, float:3.0E-43)
            goto L5b
        L4b:
            r3 = 184(0xb8, float:2.58E-43)
            goto L45
        L4e:
            r3 = 153(0x99, float:2.14E-43)
            goto L45
        L51:
            r1 = 122(0x7a, float:1.71E-43)
            goto L5b
        L54:
            r3 = 92
            goto L45
        L57:
            r1 = 61
            goto L5b
        L5a:
            r0 = r1
        L5b:
            int r4 = r4 - r1
            if (r4 >= 0) goto L5f
            int r4 = r4 + r0
        L5f:
            int r4 = r4 + 1
            return r4
        L62:
            int r4 = r4 + 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeDate.DateFromTime(double):int");
    }

    private static double Day(double d2) {
        return Math.floor(d2 / msPerDay);
    }

    private static double DayFromMonth(int i2, int i3) {
        int i4;
        int i5;
        int i6 = i2 * 30;
        if (i2 >= 7) {
            i5 = i2 / 2;
        } else {
            if (i2 < 2) {
                i4 = i6 + i2;
                if (i2 >= 2 && IsLeapYear(i3)) {
                    i4++;
                }
                return i4;
            }
            i5 = (i2 - 1) / 2;
        }
        i4 = i6 + (i5 - 1);
        if (i2 >= 2) {
            i4++;
        }
        return i4;
    }

    private static double DayFromYear(double d2) {
        return ((((d2 - 1970.0d) * 365.0d) + Math.floor((d2 - 1969.0d) / 4.0d)) - Math.floor((d2 - 1901.0d) / 100.0d)) + Math.floor((d2 - 1601.0d) / 400.0d);
    }

    private static double DaylightSavingTA(double d2) {
        if (d2 < 0.0d) {
            d2 = MakeDate(MakeDay(EquivalentYear(YearFromTime(d2)), MonthFromTime(d2), DateFromTime(d2)), TimeWithinDay(d2));
        }
        if (thisTimeZone.inDaylightTime(new Date((long) d2))) {
            return msPerHour;
        }
        return 0.0d;
    }

    private static int DaysInMonth(int i2, int i3) {
        return i3 == 2 ? IsLeapYear(i2) ? 29 : 28 : i3 >= 8 ? 31 - (i3 & 1) : (i3 & 1) + 30;
    }

    private static double DaysInYear(double d2) {
        return (Double.isInfinite(d2) || Double.isNaN(d2)) ? ScriptRuntime.NaN : IsLeapYear((int) d2) ? 366.0d : 365.0d;
    }

    private static int EquivalentYear(int i2) {
        int iDayFromYear = (((int) DayFromYear(i2)) + 4) % 7;
        if (iDayFromYear < 0) {
            iDayFromYear += 7;
        }
        if (IsLeapYear(i2)) {
            switch (iDayFromYear) {
                case 0:
                    return 1984;
                case 1:
                    return 1996;
                case 2:
                    return 1980;
                case 3:
                    return 1992;
                case 4:
                    return 1976;
                case 5:
                    return 1988;
                case 6:
                    return 1972;
            }
        }
        switch (iDayFromYear) {
            case 0:
                return 1978;
            case 1:
                return 1973;
            case 2:
                return 1985;
            case 3:
                return 1986;
            case 4:
                return 1981;
            case 5:
                return 1971;
            case 6:
                return 1977;
        }
        throw Kit.codeBug();
    }

    private static int HourFromTime(double d2) {
        double dFloor = Math.floor(d2 / msPerHour) % HoursPerDay;
        if (dFloor < 0.0d) {
            dFloor += HoursPerDay;
        }
        return (int) dFloor;
    }

    private static boolean IsLeapYear(int i2) {
        return i2 % 4 == 0 && (i2 % 100 != 0 || i2 % 400 == 0);
    }

    private static double LocalTime(double d2) {
        return LocalTZA + d2 + DaylightSavingTA(d2);
    }

    private static double MakeDate(double d2, double d3) {
        return (d2 * msPerDay) + d3;
    }

    private static double MakeDay(double d2, double d3, double d4) {
        double dFloor = d2 + Math.floor(d3 / 12.0d);
        double d5 = d3 % 12.0d;
        if (d5 < 0.0d) {
            d5 += 12.0d;
        }
        return ((Math.floor(TimeFromYear(dFloor) / msPerDay) + DayFromMonth((int) d5, (int) dFloor)) + d4) - 1.0d;
    }

    private static double MakeTime(double d2, double d3, double d4, double d5) {
        return (((((d2 * 60.0d) + d3) * 60.0d) + d4) * msPerSecond) + d5;
    }

    private static int MinFromTime(double d2) {
        double dFloor = Math.floor(d2 / msPerMinute) % 60.0d;
        if (dFloor < 0.0d) {
            dFloor += 60.0d;
        }
        return (int) dFloor;
    }

    private static int MonthFromTime(double d2) {
        int i2;
        int iYearFromTime = YearFromTime(d2);
        int iDay = (int) (Day(d2) - DayFromYear(iYearFromTime));
        int i3 = iDay - 59;
        if (i3 < 0) {
            return i3 < -28 ? 0 : 1;
        }
        if (IsLeapYear(iYearFromTime)) {
            if (i3 == 0) {
                return 1;
            }
            i3 = iDay - 60;
        }
        int i4 = i3 / 30;
        switch (i4) {
            case 0:
                return 2;
            case 1:
                i2 = 31;
                break;
            case 2:
                i2 = 61;
                break;
            case 3:
                i2 = 92;
                break;
            case 4:
                i2 = 122;
                break;
            case 5:
                i2 = 153;
                break;
            case 6:
                i2 = 184;
                break;
            case 7:
                i2 = 214;
                break;
            case 8:
                i2 = 245;
                break;
            case 9:
                i2 = KeyType.ABORT_DIAL_BIN;
                break;
            case 10:
                return 11;
            default:
                throw Kit.codeBug();
        }
        return i3 >= i2 ? i4 + 2 : i4 + 1;
    }

    private static int SecFromTime(double d2) {
        double dFloor = Math.floor(d2 / msPerSecond) % 60.0d;
        if (dFloor < 0.0d) {
            dFloor += 60.0d;
        }
        return (int) dFloor;
    }

    private static double TimeClip(double d2) {
        return (d2 != d2 || d2 == Double.POSITIVE_INFINITY || d2 == Double.NEGATIVE_INFINITY || Math.abs(d2) > HalfTimeDomain) ? ScriptRuntime.NaN : d2 > 0.0d ? Math.floor(d2 + 0.0d) : Math.ceil(d2 + 0.0d);
    }

    private static double TimeFromYear(double d2) {
        return DayFromYear(d2) * msPerDay;
    }

    private static double TimeWithinDay(double d2) {
        double d3 = d2 % msPerDay;
        return d3 < 0.0d ? d3 + msPerDay : d3;
    }

    private static int WeekDay(double d2) {
        double dDay = (Day(d2) + 4.0d) % 7.0d;
        if (dDay < 0.0d) {
            dDay += 7.0d;
        }
        return (int) dDay;
    }

    private static int YearFromTime(double d2) {
        if (Double.isInfinite(d2) || Double.isNaN(d2)) {
            return 0;
        }
        double dFloor = Math.floor(d2 / 3.1556952E10d) + 1970.0d;
        double dTimeFromYear = TimeFromYear(dFloor);
        if (dTimeFromYear > d2) {
            dFloor -= 1.0d;
        } else if (dTimeFromYear + (DaysInYear(dFloor) * msPerDay) <= d2) {
            dFloor += 1.0d;
        }
        return (int) dFloor;
    }

    private static void append0PaddedUint(StringBuilder sb, int i2, int i3) throws RuntimeException {
        int i4;
        if (i2 < 0) {
            Kit.codeBug();
        }
        int i5 = i3 - 1;
        if (i2 >= 10) {
            i4 = FileSizeUnit.ACCURATE_GB;
            if (i2 < 1000000000) {
                i4 = 1;
                while (true) {
                    int i6 = i4 * 10;
                    if (i2 < i6) {
                        break;
                    }
                    i5--;
                    i4 = i6;
                }
            } else {
                i5 = i3 - 10;
            }
        } else {
            i4 = 1;
        }
        while (i5 > 0) {
            sb.append('0');
            i5--;
        }
        while (i4 != 1) {
            sb.append((char) ((i2 / i4) + 48));
            i2 %= i4;
            i4 /= 10;
        }
        sb.append((char) (i2 + 48));
    }

    private static void appendMonthName(StringBuilder sb, int i2) {
        int i3 = i2 * 3;
        for (int i4 = 0; i4 != 3; i4++) {
            sb.append("JanFebMarAprMayJunJulAugSepOctNovDec".charAt(i3 + i4));
        }
    }

    private static void appendWeekDayName(StringBuilder sb, int i2) {
        int i3 = i2 * 3;
        for (int i4 = 0; i4 != 3; i4++) {
            sb.append("SunMonTueWedThuFriSat".charAt(i3 + i4));
        }
    }

    private static String date_format(double d2, int i2) throws RuntimeException {
        StringBuilder sb = new StringBuilder(60);
        double dLocalTime = LocalTime(d2);
        if (i2 != 3) {
            appendWeekDayName(sb, WeekDay(dLocalTime));
            sb.append(' ');
            appendMonthName(sb, MonthFromTime(dLocalTime));
            sb.append(' ');
            append0PaddedUint(sb, DateFromTime(dLocalTime), 2);
            sb.append(' ');
            int iYearFromTime = YearFromTime(dLocalTime);
            if (iYearFromTime < 0) {
                sb.append('-');
                iYearFromTime = -iYearFromTime;
            }
            append0PaddedUint(sb, iYearFromTime, 4);
            if (i2 != 4) {
                sb.append(' ');
            }
        }
        if (i2 != 4) {
            append0PaddedUint(sb, HourFromTime(dLocalTime), 2);
            sb.append(':');
            append0PaddedUint(sb, MinFromTime(dLocalTime), 2);
            sb.append(':');
            append0PaddedUint(sb, SecFromTime(dLocalTime), 2);
            int iFloor = (int) Math.floor((LocalTZA + DaylightSavingTA(d2)) / msPerMinute);
            int i3 = ((iFloor / 60) * 100) + (iFloor % 60);
            if (i3 > 0) {
                sb.append(" GMT+");
            } else {
                sb.append(" GMT-");
                i3 = -i3;
            }
            append0PaddedUint(sb, i3, 4);
            if (timeZoneFormatter == null) {
                timeZoneFormatter = new SimpleDateFormat("zzz");
            }
            if (d2 < 0.0d) {
                d2 = MakeDate(MakeDay(EquivalentYear(YearFromTime(dLocalTime)), MonthFromTime(d2), DateFromTime(d2)), TimeWithinDay(d2));
            }
            sb.append(" (");
            Date date = new Date((long) d2);
            synchronized (timeZoneFormatter) {
                sb.append(timeZoneFormatter.format(date));
            }
            sb.append(')');
        }
        return sb.toString();
    }

    private static double date_msecFromArgs(Object[] objArr) {
        double[] dArr = new double[7];
        for (int i2 = 0; i2 < 7; i2++) {
            if (i2 < objArr.length) {
                double number = ScriptRuntime.toNumber(objArr[i2]);
                if (number != number || Double.isInfinite(number)) {
                    return ScriptRuntime.NaN;
                }
                dArr[i2] = ScriptRuntime.toInteger(objArr[i2]);
            } else if (i2 == 2) {
                dArr[i2] = 1.0d;
            } else {
                dArr[i2] = 0.0d;
            }
        }
        double d2 = dArr[0];
        if (d2 >= 0.0d && d2 <= 99.0d) {
            dArr[0] = d2 + 1900.0d;
        }
        return date_msecFromDate(dArr[0], dArr[1], dArr[2], dArr[3], dArr[4], dArr[5], dArr[6]);
    }

    private static double date_msecFromDate(double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return MakeDate(MakeDay(d2, d3, d4), MakeTime(d5, d6, d7, d8));
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01a2 A[PHI: r12 r16
      0x01a2: PHI (r12v4 int) = (r12v1 int), (r12v1 int), (r12v1 int), (r12v1 int), (r12v1 int), (r12v3 int), (r12v1 int), (r12v1 int) binds: [B:159:0x0198, B:175:0x01cb, B:174:0x01c8, B:173:0x01c5, B:170:0x01bb, B:164:0x01a1, B:154:0x018d, B:152:0x0189] A[DONT_GENERATE, DONT_INLINE]
      0x01a2: PHI (r16v9 double) = 
      (r16v1 double)
      (r16v5 double)
      (r16v6 double)
      (r16v7 double)
      (r16v1 double)
      (r16v1 double)
      (r16v1 double)
      (r16v1 double)
     binds: [B:159:0x0198, B:175:0x01cb, B:174:0x01c8, B:173:0x01c5, B:170:0x01bb, B:164:0x01a1, B:154:0x018d, B:152:0x0189] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0155 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static double date_parseString(java.lang.String r34) throws java.lang.RuntimeException {
        /*
            Method dump skipped, instructions count: 608
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeDate.date_parseString(java.lang.String):double");
    }

    static void init(Scriptable scriptable, boolean z2) {
        NativeDate nativeDate = new NativeDate();
        nativeDate.date = ScriptRuntime.NaN;
        nativeDate.exportAsJSClass(47, scriptable, z2);
    }

    private static double internalUTC(double d2) {
        double d3 = LocalTZA;
        return (d2 - d3) - DaylightSavingTA(d2 - d3);
    }

    private static Object jsConstructor(Object[] objArr) {
        NativeDate nativeDate = new NativeDate();
        if (objArr.length == 0) {
            nativeDate.date = now();
            return nativeDate;
        }
        if (objArr.length == 1) {
            Object defaultValue = objArr[0];
            if (defaultValue instanceof Scriptable) {
                defaultValue = ((Scriptable) defaultValue).getDefaultValue(null);
            }
            nativeDate.date = TimeClip(defaultValue instanceof CharSequence ? date_parseString(defaultValue.toString()) : ScriptRuntime.toNumber(defaultValue));
            return nativeDate;
        }
        double dDate_msecFromArgs = date_msecFromArgs(objArr);
        if (!Double.isNaN(dDate_msecFromArgs) && !Double.isInfinite(dDate_msecFromArgs)) {
            dDate_msecFromArgs = TimeClip(internalUTC(dDate_msecFromArgs));
        }
        nativeDate.date = dDate_msecFromArgs;
        return nativeDate;
    }

    private static double jsStaticFunction_UTC(Object[] objArr) {
        return TimeClip(date_msecFromArgs(objArr));
    }

    private static String js_toISOString(double d2) throws RuntimeException {
        StringBuilder sb = new StringBuilder(27);
        int iYearFromTime = YearFromTime(d2);
        if (iYearFromTime < 0) {
            sb.append('-');
            append0PaddedUint(sb, -iYearFromTime, 6);
        } else if (iYearFromTime > 9999) {
            append0PaddedUint(sb, iYearFromTime, 6);
        } else {
            append0PaddedUint(sb, iYearFromTime, 4);
        }
        sb.append('-');
        append0PaddedUint(sb, MonthFromTime(d2) + 1, 2);
        sb.append('-');
        append0PaddedUint(sb, DateFromTime(d2), 2);
        sb.append('T');
        append0PaddedUint(sb, HourFromTime(d2), 2);
        sb.append(':');
        append0PaddedUint(sb, MinFromTime(d2), 2);
        sb.append(':');
        append0PaddedUint(sb, SecFromTime(d2), 2);
        sb.append('.');
        append0PaddedUint(sb, msFromTime(d2), 3);
        sb.append('Z');
        return sb.toString();
    }

    private static String js_toUTCString(double d2) throws RuntimeException {
        StringBuilder sb = new StringBuilder(60);
        appendWeekDayName(sb, WeekDay(d2));
        sb.append(", ");
        append0PaddedUint(sb, DateFromTime(d2), 2);
        sb.append(' ');
        appendMonthName(sb, MonthFromTime(d2));
        sb.append(' ');
        int iYearFromTime = YearFromTime(d2);
        if (iYearFromTime < 0) {
            sb.append('-');
            iYearFromTime = -iYearFromTime;
        }
        append0PaddedUint(sb, iYearFromTime, 4);
        sb.append(' ');
        append0PaddedUint(sb, HourFromTime(d2), 2);
        sb.append(':');
        append0PaddedUint(sb, MinFromTime(d2), 2);
        sb.append(':');
        append0PaddedUint(sb, SecFromTime(d2), 2);
        sb.append(" GMT");
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static double makeDate(double r19, java.lang.Object[] r21, int r22) {
        /*
            r0 = r21
            int r1 = r0.length
            if (r1 != 0) goto L8
            double r0 = org.mozilla.javascript.ScriptRuntime.NaN
            return r0
        L8:
            r1 = 2
            r2 = 3
            r3 = 0
            r4 = 1
            switch(r22) {
                case 39: goto L20;
                case 40: goto L1e;
                case 41: goto L1b;
                case 42: goto L19;
                case 43: goto L16;
                case 44: goto L14;
                default: goto Lf;
            }
        Lf:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L14:
            r5 = r3
            goto L17
        L16:
            r5 = r4
        L17:
            r6 = r2
            goto L22
        L19:
            r5 = r3
            goto L1c
        L1b:
            r5 = r4
        L1c:
            r6 = r1
            goto L22
        L1e:
            r5 = r3
            goto L21
        L20:
            r5 = r4
        L21:
            r6 = r4
        L22:
            int r7 = r0.length
            if (r7 >= r6) goto L27
            int r7 = r0.length
            goto L28
        L27:
            r7 = r6
        L28:
            double[] r8 = new double[r2]
            r9 = r3
            r10 = r9
        L2c:
            if (r9 >= r7) goto L4a
            r11 = r0[r9]
            double r11 = org.mozilla.javascript.ScriptRuntime.toNumber(r11)
            int r13 = (r11 > r11 ? 1 : (r11 == r11 ? 0 : -1))
            if (r13 != 0) goto L46
            boolean r13 = java.lang.Double.isInfinite(r11)
            if (r13 == 0) goto L3f
            goto L46
        L3f:
            double r11 = org.mozilla.javascript.ScriptRuntime.toInteger(r11)
            r8[r9] = r11
            goto L47
        L46:
            r10 = r4
        L47:
            int r9 = r9 + 1
            goto L2c
        L4a:
            if (r10 == 0) goto L4f
            double r0 = org.mozilla.javascript.ScriptRuntime.NaN
            return r0
        L4f:
            int r0 = (r19 > r19 ? 1 : (r19 == r19 ? 0 : -1))
            if (r0 == 0) goto L5b
            if (r6 >= r2) goto L58
            double r0 = org.mozilla.javascript.ScriptRuntime.NaN
            return r0
        L58:
            r9 = 0
            goto L64
        L5b:
            if (r5 == 0) goto L62
            double r9 = LocalTime(r19)
            goto L64
        L62:
            r9 = r19
        L64:
            if (r6 < r2) goto L6d
            if (r7 <= 0) goto L6d
            r2 = r8[r3]
            r13 = r2
            r3 = r4
            goto L73
        L6d:
            int r0 = YearFromTime(r9)
            double r11 = (double) r0
            r13 = r11
        L73:
            if (r6 < r1) goto L7e
            if (r3 >= r7) goto L7e
            int r0 = r3 + 1
            r1 = r8[r3]
            r3 = r0
            r15 = r1
            goto L84
        L7e:
            int r0 = MonthFromTime(r9)
            double r0 = (double) r0
            r15 = r0
        L84:
            if (r6 < r4) goto L8d
            if (r3 >= r7) goto L8d
            r0 = r8[r3]
        L8a:
            r17 = r0
            goto L93
        L8d:
            int r0 = DateFromTime(r9)
            double r0 = (double) r0
            goto L8a
        L93:
            double r0 = MakeDay(r13, r15, r17)
            double r2 = TimeWithinDay(r9)
            double r0 = MakeDate(r0, r2)
            if (r5 == 0) goto La5
            double r0 = internalUTC(r0)
        La5:
            double r0 = TimeClip(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeDate.makeDate(double, java.lang.Object[], int):double");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static double makeTime(double r22, java.lang.Object[] r24, int r25) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeDate.makeTime(double, java.lang.Object[], int):double");
    }

    private static int msFromTime(double d2) {
        double d3 = d2 % msPerSecond;
        if (d3 < 0.0d) {
            d3 += msPerSecond;
        }
        return (int) d3;
    }

    private static double now() {
        return System.currentTimeMillis();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0135 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00d7 A[PHI: r2 r13
      0x00d7: PHI (r2v36 char) = (r2v32 char), (r2v37 char) binds: [B:70:0x00eb, B:61:0x00d4] A[DONT_GENERATE, DONT_INLINE]
      0x00d7: PHI (r13v18 char) = (r13v15 char), (r13v19 char) binds: [B:70:0x00eb, B:61:0x00d4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00d9 A[PHI: r2 r13
      0x00d9: PHI (r2v33 char) = (r2v32 char), (r2v32 char), (r2v37 char), (r2v37 char) binds: [B:69:0x00e9, B:70:0x00eb, B:60:0x00d2, B:61:0x00d4] A[DONT_GENERATE, DONT_INLINE]
      0x00d9: PHI (r13v16 char) = (r13v15 char), (r13v15 char), (r13v19 char), (r13v19 char) binds: [B:69:0x00e9, B:70:0x00eb, B:60:0x00d2, B:61:0x00d4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00fb A[PHI: r13
      0x00fb: PHI (r13v13 char) = (r13v12 char), (r13v14 char) binds: [B:81:0x0101, B:76:0x00f8] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static double parseISOString(java.lang.String r35) {
        /*
            Method dump skipped, instructions count: 474
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeDate.parseISOString(java.lang.String):double");
    }

    private static String toLocale_helper(double d2, int i2) {
        DateFormat dateFormat;
        String str;
        if (i2 == 5) {
            if (localeDateTimeFormatter == null) {
                localeDateTimeFormatter = DateFormat.getDateTimeInstance(1, 1);
            }
            dateFormat = localeDateTimeFormatter;
        } else if (i2 == 6) {
            if (localeTimeFormatter == null) {
                localeTimeFormatter = DateFormat.getTimeInstance(1);
            }
            dateFormat = localeTimeFormatter;
        } else {
            if (i2 != 7) {
                throw new AssertionError();
            }
            if (localeDateFormatter == null) {
                localeDateFormatter = DateFormat.getDateInstance(1);
            }
            dateFormat = localeDateFormatter;
        }
        synchronized (dateFormat) {
            str = dateFormat.format(new Date((long) d2));
        }
        return str;
    }

    @Override // org.mozilla.javascript.IdScriptableObject, org.mozilla.javascript.IdFunctionCall
    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        double dTimeClip;
        if (!idFunctionObject.hasTag(DATE_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int iMethodId = idFunctionObject.methodId();
        if (iMethodId == -3) {
            return ScriptRuntime.wrapNumber(now());
        }
        if (iMethodId == -2) {
            return ScriptRuntime.wrapNumber(date_parseString(ScriptRuntime.toString(objArr, 0)));
        }
        if (iMethodId == -1) {
            return ScriptRuntime.wrapNumber(jsStaticFunction_UTC(objArr));
        }
        if (iMethodId == 1) {
            return scriptable2 != null ? date_format(now(), 2) : jsConstructor(objArr);
        }
        if (iMethodId == 47) {
            Scriptable object = ScriptRuntime.toObject(context, scriptable, scriptable2);
            Object primitive = ScriptRuntime.toPrimitive(object, ScriptRuntime.NumberClass);
            if (primitive instanceof Number) {
                double dDoubleValue = ((Number) primitive).doubleValue();
                if (dDoubleValue != dDoubleValue || Double.isInfinite(dDoubleValue)) {
                    return null;
                }
            }
            Object property = ScriptableObject.getProperty(object, "toISOString");
            if (property == Scriptable.NOT_FOUND) {
                throw ScriptRuntime.typeError2("msg.function.not.found.in", "toISOString", ScriptRuntime.toString(object));
            }
            if (!(property instanceof Callable)) {
                throw ScriptRuntime.typeError3("msg.isnt.function.in", "toISOString", ScriptRuntime.toString(object), ScriptRuntime.toString(property));
            }
            Object objCall = ((Callable) property).call(context, scriptable, object, ScriptRuntime.emptyArgs);
            if (ScriptRuntime.isPrimitive(objCall)) {
                return objCall;
            }
            throw ScriptRuntime.typeError1("msg.toisostring.must.return.primitive", ScriptRuntime.toString(objCall));
        }
        if (!(scriptable2 instanceof NativeDate)) {
            throw IdScriptableObject.incompatibleCallError(idFunctionObject);
        }
        NativeDate nativeDate = (NativeDate) scriptable2;
        double dYearFromTime = nativeDate.date;
        switch (iMethodId) {
            case 2:
            case 3:
            case 4:
                return dYearFromTime == dYearFromTime ? date_format(dYearFromTime, iMethodId) : js_NaN_date_str;
            case 5:
            case 6:
            case 7:
                return dYearFromTime == dYearFromTime ? toLocale_helper(dYearFromTime, iMethodId) : js_NaN_date_str;
            case 8:
                return dYearFromTime == dYearFromTime ? js_toUTCString(dYearFromTime) : js_NaN_date_str;
            case 9:
                return "(new Date(" + ScriptRuntime.toString(dYearFromTime) + "))";
            case 10:
            case 11:
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 12:
            case 13:
            case 14:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId != 14) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = YearFromTime(dYearFromTime);
                    if (iMethodId == 12 && (!context.hasFeature(1) || (1900.0d <= dYearFromTime && dYearFromTime < 2000.0d))) {
                        dYearFromTime -= 1900.0d;
                    }
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 15:
            case 16:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId == 15) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = MonthFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 17:
            case 18:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId == 17) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = DateFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 19:
            case 20:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId == 19) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = WeekDay(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 21:
            case 22:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId == 21) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = HourFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 23:
            case 24:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId == 23) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = MinFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 25:
            case 26:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId == 25) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = SecFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 27:
            case 28:
                if (dYearFromTime == dYearFromTime) {
                    if (iMethodId == 27) {
                        dYearFromTime = LocalTime(dYearFromTime);
                    }
                    dYearFromTime = msFromTime(dYearFromTime);
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 29:
                if (dYearFromTime == dYearFromTime) {
                    dYearFromTime = (dYearFromTime - LocalTime(dYearFromTime)) / msPerMinute;
                }
                return ScriptRuntime.wrapNumber(dYearFromTime);
            case 30:
                double dTimeClip2 = TimeClip(ScriptRuntime.toNumber(objArr, 0));
                nativeDate.date = dTimeClip2;
                return ScriptRuntime.wrapNumber(dTimeClip2);
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
                double dMakeTime = makeTime(dYearFromTime, objArr, iMethodId);
                nativeDate.date = dMakeTime;
                return ScriptRuntime.wrapNumber(dMakeTime);
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                double dMakeDate = makeDate(dYearFromTime, objArr, iMethodId);
                nativeDate.date = dMakeDate;
                return ScriptRuntime.wrapNumber(dMakeDate);
            case 45:
                double number = ScriptRuntime.toNumber(objArr, 0);
                if (number != number || Double.isInfinite(number)) {
                    dTimeClip = ScriptRuntime.NaN;
                } else {
                    double dLocalTime = dYearFromTime != dYearFromTime ? 0.0d : LocalTime(dYearFromTime);
                    if (number >= 0.0d && number <= 99.0d) {
                        number += 1900.0d;
                    }
                    dTimeClip = TimeClip(internalUTC(MakeDate(MakeDay(number, MonthFromTime(dLocalTime), DateFromTime(dLocalTime)), TimeWithinDay(dLocalTime))));
                }
                nativeDate.date = dTimeClip;
                return ScriptRuntime.wrapNumber(dTimeClip);
            case 46:
                if (dYearFromTime == dYearFromTime) {
                    return js_toISOString(dYearFromTime);
                }
                throw ScriptRuntime.constructError("RangeError", ScriptRuntime.getMessage0("msg.invalid.date"));
            default:
                throw new IllegalArgumentException(String.valueOf(iMethodId));
        }
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void fillConstructorProperties(IdFunctionObject idFunctionObject) {
        Object obj = DATE_TAG;
        addIdFunctionProperty(idFunctionObject, obj, -3, "now", 0);
        addIdFunctionProperty(idFunctionObject, obj, -2, "parse", 1);
        addIdFunctionProperty(idFunctionObject, obj, -1, "UTC", 7);
        super.fillConstructorProperties(idFunctionObject);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0217 A[FALL_THROUGH] */
    @Override // org.mozilla.javascript.IdScriptableObject
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int findPrototypeId(java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 590
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.NativeDate.findPrototypeId(java.lang.String):int");
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public String getClassName() {
        return "Date";
    }

    @Override // org.mozilla.javascript.ScriptableObject, org.mozilla.javascript.Scriptable
    public Object getDefaultValue(Class<?> cls) {
        if (cls == null) {
            cls = ScriptRuntime.StringClass;
        }
        return super.getDefaultValue(cls);
    }

    double getJSTimeValue() {
        return this.date;
    }

    @Override // org.mozilla.javascript.IdScriptableObject
    protected void initPrototypeId(int i2) {
        String str;
        String str2;
        int i3 = 4;
        int i4 = 0;
        switch (i2) {
            case 1:
                i3 = 7;
                str = "constructor";
                i4 = i3;
                str2 = str;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 2:
                str2 = "toString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 3:
                str2 = "toTimeString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 4:
                str2 = "toDateString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 5:
                str2 = "toLocaleString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 6:
                str2 = "toLocaleTimeString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 7:
                str2 = "toLocaleDateString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 8:
                str2 = "toUTCString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 9:
                str2 = "toSource";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 10:
                str2 = "valueOf";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 11:
                str2 = "getTime";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 12:
                str2 = "getYear";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 13:
                str2 = "getFullYear";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 14:
                str2 = "getUTCFullYear";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 15:
                str2 = "getMonth";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 16:
                str2 = "getUTCMonth";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 17:
                str2 = "getDate";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 18:
                str2 = "getUTCDate";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 19:
                str2 = "getDay";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 20:
                str2 = "getUTCDay";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 21:
                str2 = "getHours";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 22:
                str2 = "getUTCHours";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 23:
                str2 = "getMinutes";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 24:
                str2 = "getUTCMinutes";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 25:
                str2 = "getSeconds";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 26:
                str2 = "getUTCSeconds";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 27:
                str2 = "getMilliseconds";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 28:
                str2 = "getUTCMilliseconds";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 29:
                str2 = "getTimezoneOffset";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 30:
                str2 = "setTime";
                i4 = 1;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 31:
                str2 = "setMilliseconds";
                i4 = 1;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 32:
                str2 = "setUTCMilliseconds";
                i4 = 1;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 33:
                str2 = "setSeconds";
                i4 = 2;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 34:
                str2 = "setUTCSeconds";
                i4 = 2;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 35:
                str2 = "setMinutes";
                i4 = 3;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 36:
                str2 = "setUTCMinutes";
                i4 = 3;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 37:
                str = "setHours";
                i4 = i3;
                str2 = str;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 38:
                str = "setUTCHours";
                i4 = i3;
                str2 = str;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 39:
                str2 = "setDate";
                i4 = 1;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 40:
                str2 = "setUTCDate";
                i4 = 1;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 41:
                str2 = "setMonth";
                i4 = 2;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 42:
                str2 = "setUTCMonth";
                i4 = 2;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 43:
                str2 = "setFullYear";
                i4 = 3;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 44:
                str2 = "setUTCFullYear";
                i4 = 3;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 45:
                str2 = "setYear";
                i4 = 1;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 46:
                str2 = "toISOString";
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            case 47:
                str2 = "toJSON";
                i4 = 1;
                initPrototypeMethod(DATE_TAG, i2, str2, i4);
                return;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }
}
