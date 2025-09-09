package org.apache.commons.lang.time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;

/* loaded from: classes5.dex */
public class DateUtils {
    public static final int MILLIS_IN_DAY = 86400000;
    public static final int MILLIS_IN_HOUR = 3600000;
    public static final int MILLIS_IN_MINUTE = 60000;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final long MILLIS_PER_DAY = 86400000;
    public static final long MILLIS_PER_HOUR = 3600000;
    public static final long MILLIS_PER_MINUTE = 60000;
    public static final long MILLIS_PER_SECOND = 1000;
    private static final int MODIFY_CEILING = 2;
    private static final int MODIFY_ROUND = 1;
    private static final int MODIFY_TRUNCATE = 0;
    public static final int RANGE_MONTH_MONDAY = 6;
    public static final int RANGE_MONTH_SUNDAY = 5;
    public static final int RANGE_WEEK_CENTER = 4;
    public static final int RANGE_WEEK_MONDAY = 2;
    public static final int RANGE_WEEK_RELATIVE = 3;
    public static final int RANGE_WEEK_SUNDAY = 1;
    public static final int SEMI_MONTH = 1001;
    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
    private static final int[][] fields = {new int[]{14}, new int[]{13}, new int[]{12}, new int[]{11, 10}, new int[]{5, 5, 9}, new int[]{2, 1001}, new int[]{1}, new int[]{0}};

    static class DateIterator implements Iterator {
        private final Calendar endFinal;
        private final Calendar spot;

        DateIterator(Calendar calendar, Calendar calendar2) {
            this.endFinal = calendar2;
            this.spot = calendar;
            calendar.add(5, -1);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.spot.before(this.endFinal);
        }

        @Override // java.util.Iterator
        public Object next() {
            if (this.spot.equals(this.endFinal)) {
                throw new NoSuchElementException();
            }
            this.spot.add(5, 1);
            return this.spot.clone();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static Date add(Date date, int i2, int i3) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(i2, i3);
        return calendar.getTime();
    }

    public static Date addDays(Date date, int i2) {
        return add(date, 5, i2);
    }

    public static Date addHours(Date date, int i2) {
        return add(date, 11, i2);
    }

    public static Date addMilliseconds(Date date, int i2) {
        return add(date, 14, i2);
    }

    public static Date addMinutes(Date date, int i2) {
        return add(date, 12, i2);
    }

    public static Date addMonths(Date date, int i2) {
        return add(date, 2, i2);
    }

    public static Date addSeconds(Date date, int i2) {
        return add(date, 13, i2);
    }

    public static Date addWeeks(Date date, int i2) {
        return add(date, 3, i2);
    }

    public static Date addYears(Date date, int i2) {
        return add(date, 1, i2);
    }

    public static Date ceiling(Date date, int i2) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        modify(calendar, i2, 2);
        return calendar.getTime();
    }

    private static long getFragment(Date date, int i2, int i3) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFragment(calendar, i2, i3);
    }

    public static long getFragmentInDays(Date date, int i2) {
        return getFragment(date, i2, 6);
    }

    public static long getFragmentInHours(Date date, int i2) {
        return getFragment(date, i2, 11);
    }

    public static long getFragmentInMilliseconds(Date date, int i2) {
        return getFragment(date, i2, 14);
    }

    public static long getFragmentInMinutes(Date date, int i2) {
        return getFragment(date, i2, 12);
    }

    public static long getFragmentInSeconds(Date date, int i2) {
        return getFragment(date, i2, 13);
    }

    private static long getMillisPerUnit(int i2) {
        if (i2 == 5 || i2 == 6) {
            return 86400000L;
        }
        switch (i2) {
            case 11:
                return 3600000L;
            case 12:
                return 60000L;
            case 13:
                return 1000L;
            case 14:
                return 1L;
            default:
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("The unit ");
                stringBuffer.append(i2);
                stringBuffer.append(" cannot be represented is milleseconds");
                throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    private static int indexOfSignChars(String str, int i2) {
        int iIndexOf = StringUtils.indexOf(str, '+', i2);
        return iIndexOf < 0 ? StringUtils.indexOf(str, '-', i2) : iIndexOf;
    }

    public static boolean isSameDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return isSameDay(calendar, calendar2);
    }

    public static boolean isSameInstant(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date.getTime() == date2.getTime();
    }

    public static boolean isSameLocalTime(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(14) == calendar2.get(14) && calendar.get(13) == calendar2.get(13) && calendar.get(12) == calendar2.get(12) && calendar.get(10) == calendar2.get(10) && calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0) && calendar.getClass() == calendar2.getClass();
    }

    public static Iterator iterator(Date date, int i2) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return iterator(calendar, i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c1, code lost:
    
        if (r17 == 9) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00c3, code lost:
    
        if (r17 == 1001) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00c5, code lost:
    
        r7 = '\f';
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00ca, code lost:
    
        if (r10[0] != 5) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00cc, code lost:
    
        r4 = r16.get(5);
        r6 = r4 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d2, code lost:
    
        if (r6 < 15) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d4, code lost:
    
        r4 = r4 - 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d7, code lost:
    
        r4 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00d9, code lost:
    
        if (r4 <= 7) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00db, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00dd, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00de, code lost:
    
        r11 = r4;
        r7 = '\f';
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00e1, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00e6, code lost:
    
        if (r10[0] != 11) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00e8, code lost:
    
        r4 = r16.get(11);
        r7 = '\f';
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00ee, code lost:
    
        if (r4 < 12) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00f0, code lost:
    
        r4 = r4 - 12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00f3, code lost:
    
        if (r4 < 6) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00f5, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00f7, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00f8, code lost:
    
        r11 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00fa, code lost:
    
        r4 = false;
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00fc, code lost:
    
        if (r4 != false) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00fe, code lost:
    
        r4 = r16.getActualMinimum(r9[r5][0]);
        r8 = r16.getActualMaximum(r9[r5][0]);
        r11 = r16.get(r9[r5][0]) - r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x011b, code lost:
    
        if (r11 <= ((r8 - r4) / 2)) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x011d, code lost:
    
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x011f, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0120, code lost:
    
        r6 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0121, code lost:
    
        if (r11 == 0) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0123, code lost:
    
        r4 = r9[r5][0];
        r16.set(r4, r16.get(r4) - r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0132, code lost:
    
        r5 = r5 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void modify(java.util.Calendar r16, int r17, int r18) {
        /*
            Method dump skipped, instructions count: 347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.time.DateUtils.modify(java.util.Calendar, int, int):void");
    }

    public static Date parseDate(String str, String[] strArr) throws ParseException {
        return parseDateWithLeniency(str, strArr, true);
    }

    public static Date parseDateStrictly(String str, String[] strArr) throws ParseException {
        return parseDateWithLeniency(str, strArr, false);
    }

    private static Date parseDateWithLeniency(String str, String[] strArr, boolean z2) throws ParseException {
        String strReformatTimezone;
        if (str == null || strArr == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.setLenient(z2);
        ParsePosition parsePosition = new ParsePosition(0);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String strSubstring = strArr[i2];
            if (strSubstring.endsWith("ZZ")) {
                strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
            }
            simpleDateFormat.applyPattern(strSubstring);
            parsePosition.setIndex(0);
            if (strArr[i2].endsWith("ZZ")) {
                int iIndexOfSignChars = indexOfSignChars(str, 0);
                strReformatTimezone = str;
                while (iIndexOfSignChars >= 0) {
                    strReformatTimezone = reformatTimezone(strReformatTimezone, iIndexOfSignChars);
                    iIndexOfSignChars = indexOfSignChars(strReformatTimezone, iIndexOfSignChars + 1);
                }
            } else {
                strReformatTimezone = str;
            }
            Date date = simpleDateFormat.parse(strReformatTimezone, parsePosition);
            if (date != null && parsePosition.getIndex() == strReformatTimezone.length()) {
                return date;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unable to parse the date: ");
        stringBuffer.append(str);
        throw new ParseException(stringBuffer.toString(), -1);
    }

    private static String reformatTimezone(String str, int i2) {
        int i3;
        if (i2 < 0 || (i3 = i2 + 5) >= str.length() || !Character.isDigit(str.charAt(i2 + 1)) || !Character.isDigit(str.charAt(i2 + 2))) {
            return str;
        }
        int i4 = i2 + 3;
        if (str.charAt(i4) != ':') {
            return str;
        }
        int i5 = i2 + 4;
        if (!Character.isDigit(str.charAt(i5)) || !Character.isDigit(str.charAt(i3))) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, i4));
        stringBuffer.append(str.substring(i5));
        return stringBuffer.toString();
    }

    public static Date round(Date date, int i2) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        modify(calendar, i2, 1);
        return calendar.getTime();
    }

    private static Date set(Date date, int i2, int i3) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.setTime(date);
        calendar.set(i2, i3);
        return calendar.getTime();
    }

    public static Date setDays(Date date, int i2) {
        return set(date, 5, i2);
    }

    public static Date setHours(Date date, int i2) {
        return set(date, 11, i2);
    }

    public static Date setMilliseconds(Date date, int i2) {
        return set(date, 14, i2);
    }

    public static Date setMinutes(Date date, int i2) {
        return set(date, 12, i2);
    }

    public static Date setMonths(Date date, int i2) {
        return set(date, 2, i2);
    }

    public static Date setSeconds(Date date, int i2) {
        return set(date, 13, i2);
    }

    public static Date setYears(Date date, int i2) {
        return set(date, 1, i2);
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date truncate(Date date, int i2) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        modify(calendar, i2, 0);
        return calendar.getTime();
    }

    public static int truncatedCompareTo(Calendar calendar, Calendar calendar2, int i2) {
        return truncate(calendar, i2).getTime().compareTo(truncate(calendar2, i2).getTime());
    }

    public static boolean truncatedEquals(Calendar calendar, Calendar calendar2, int i2) {
        return truncatedCompareTo(calendar, calendar2, i2) == 0;
    }

    public static long getFragmentInDays(Calendar calendar, int i2) {
        return getFragment(calendar, i2, 6);
    }

    public static long getFragmentInHours(Calendar calendar, int i2) {
        return getFragment(calendar, i2, 11);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int i2) {
        return getFragment(calendar, i2, 14);
    }

    public static long getFragmentInMinutes(Calendar calendar, int i2) {
        return getFragment(calendar, i2, 12);
    }

    public static long getFragmentInSeconds(Calendar calendar, int i2) {
        return getFragment(calendar, i2, 13);
    }

    public static boolean truncatedEquals(Date date, Date date2, int i2) {
        return truncatedCompareTo(date, date2, i2) == 0;
    }

    public static boolean isSameInstant(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.getTime().getTime() == calendar2.getTime().getTime();
    }

    public static int truncatedCompareTo(Date date, Date date2, int i2) {
        return truncate(date, i2).compareTo(truncate(date2, i2));
    }

    private static long getFragment(Calendar calendar, int i2, int i3) {
        long j2;
        if (calendar != null) {
            long millisPerUnit = getMillisPerUnit(i3);
            if (i2 != 1) {
                j2 = i2 != 2 ? 0L : (calendar.get(5) * 86400000) / millisPerUnit;
            } else {
                j2 = (calendar.get(6) * 86400000) / millisPerUnit;
            }
            if (i2 == 1 || i2 == 2 || i2 == 5 || i2 == 6) {
                j2 += (calendar.get(11) * 3600000) / millisPerUnit;
            } else {
                switch (i2) {
                    case 11:
                        break;
                    case 12:
                        j2 += (calendar.get(13) * 1000) / millisPerUnit;
                    case 13:
                        return j2 + (calendar.get(14) / millisPerUnit);
                    case 14:
                        return j2;
                    default:
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("The fragment ");
                        stringBuffer.append(i2);
                        stringBuffer.append(" is not supported");
                        throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
            j2 += (calendar.get(12) * 60000) / millisPerUnit;
            j2 += (calendar.get(13) * 1000) / millisPerUnit;
            return j2 + (calendar.get(14) / millisPerUnit);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007c A[LOOP:0: B:30:0x0076->B:32:0x007c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0086 A[LOOP:1: B:33:0x0080->B:35:0x0086, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Iterator iterator(java.util.Calendar r8, int r9) {
        /*
            if (r8 == 0) goto L90
            r0 = -1
            r1 = 5
            r2 = 2
            r3 = 1
            r4 = 7
            switch(r9) {
                case 1: goto L42;
                case 2: goto L42;
                case 3: goto L42;
                case 4: goto L42;
                case 5: goto L26;
                case 6: goto L26;
                default: goto La;
            }
        La:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.String r1 = "The range style "
            r0.append(r1)
            r0.append(r9)
            java.lang.String r9 = " is not valid."
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            r8.<init>(r9)
            throw r8
        L26:
            java.util.Calendar r8 = truncate(r8, r2)
            java.lang.Object r5 = r8.clone()
            java.util.Calendar r5 = (java.util.Calendar) r5
            r5.add(r2, r3)
            r5.add(r1, r0)
            r6 = 6
            if (r9 != r6) goto L3d
            r6 = r5
            r5 = r8
        L3b:
            r8 = r3
            goto L66
        L3d:
            r2 = r3
            r6 = r5
            r5 = r8
        L40:
            r8 = r4
            goto L66
        L42:
            java.util.Calendar r5 = truncate(r8, r1)
            java.util.Calendar r6 = truncate(r8, r1)
            if (r9 == r2) goto L3b
            r2 = 3
            if (r9 == r2) goto L60
            r7 = 4
            if (r9 == r7) goto L54
            r2 = r3
            goto L40
        L54:
            int r9 = r8.get(r4)
            int r9 = r9 - r2
            int r8 = r8.get(r4)
            int r8 = r8 + r2
            r2 = r9
            goto L66
        L60:
            int r2 = r8.get(r4)
            int r8 = r2 + (-1)
        L66:
            if (r2 >= r3) goto L6a
            int r2 = r2 + 7
        L6a:
            if (r2 <= r4) goto L6e
            int r2 = r2 + (-7)
        L6e:
            if (r8 >= r3) goto L72
            int r8 = r8 + 7
        L72:
            if (r8 <= r4) goto L76
            int r8 = r8 + (-7)
        L76:
            int r9 = r5.get(r4)
            if (r9 == r2) goto L80
            r5.add(r1, r0)
            goto L76
        L80:
            int r9 = r6.get(r4)
            if (r9 == r8) goto L8a
            r6.add(r1, r3)
            goto L80
        L8a:
            org.apache.commons.lang.time.DateUtils$DateIterator r8 = new org.apache.commons.lang.time.DateUtils$DateIterator
            r8.<init>(r5, r6)
            return r8
        L90:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "The date must not be null"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.time.DateUtils.iterator(java.util.Calendar, int):java.util.Iterator");
    }

    public static Calendar ceiling(Calendar calendar, int i2) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            modify(calendar2, i2, 2);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar round(Calendar calendar, int i2) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            modify(calendar2, i2, 1);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar truncate(Calendar calendar, int i2) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            modify(calendar2, i2, 0);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isSameDay(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }

    public static Date ceiling(Object obj, int i2) {
        if (obj != null) {
            if (obj instanceof Date) {
                return ceiling((Date) obj, i2);
            }
            if (obj instanceof Calendar) {
                return ceiling((Calendar) obj, i2).getTime();
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not find ceiling of for type: ");
            stringBuffer.append(obj.getClass());
            throw new ClassCastException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date round(Object obj, int i2) {
        if (obj != null) {
            if (obj instanceof Date) {
                return round((Date) obj, i2);
            }
            if (obj instanceof Calendar) {
                return round((Calendar) obj, i2).getTime();
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not round ");
            stringBuffer.append(obj);
            throw new ClassCastException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date truncate(Object obj, int i2) {
        if (obj != null) {
            if (obj instanceof Date) {
                return truncate((Date) obj, i2);
            }
            if (obj instanceof Calendar) {
                return truncate((Calendar) obj, i2).getTime();
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not truncate ");
            stringBuffer.append(obj);
            throw new ClassCastException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Iterator iterator(Object obj, int i2) {
        if (obj != null) {
            if (obj instanceof Date) {
                return iterator((Date) obj, i2);
            }
            if (obj instanceof Calendar) {
                return iterator((Calendar) obj, i2);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not iterate based on ");
            stringBuffer.append(obj);
            throw new ClassCastException(stringBuffer.toString());
        }
        throw new IllegalArgumentException("The date must not be null");
    }
}
