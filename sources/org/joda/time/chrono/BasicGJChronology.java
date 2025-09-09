package org.joda.time.chrono;

import org.joda.time.Chronology;

/* loaded from: classes5.dex */
abstract class BasicGJChronology extends BasicChronology {
    private static final long FEB_29 = 5097600000L;
    private static final long serialVersionUID = 538276888268L;
    private static final int[] MIN_DAYS_PER_MONTH_ARRAY = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] MAX_DAYS_PER_MONTH_ARRAY = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final long[] MIN_TOTAL_MILLIS_BY_MONTH_ARRAY = new long[12];
    private static final long[] MAX_TOTAL_MILLIS_BY_MONTH_ARRAY = new long[12];

    static {
        long j2 = 0;
        int i2 = 0;
        long j3 = 0;
        while (i2 < 11) {
            j2 += MIN_DAYS_PER_MONTH_ARRAY[i2] * 86400000;
            int i3 = i2 + 1;
            MIN_TOTAL_MILLIS_BY_MONTH_ARRAY[i3] = j2;
            j3 += MAX_DAYS_PER_MONTH_ARRAY[i2] * 86400000;
            MAX_TOTAL_MILLIS_BY_MONTH_ARRAY[i3] = j3;
            i2 = i3;
        }
    }

    BasicGJChronology(Chronology chronology, Object obj, int i2) {
        super(chronology, obj, i2);
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInMonthMax(int i2) {
        return MAX_DAYS_PER_MONTH_ARRAY[i2 - 1];
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInMonthMaxForSet(long j2, int i2) {
        if (i2 > 28 || i2 < 1) {
            return getDaysInMonthMax(j2);
        }
        return 28;
    }

    @Override // org.joda.time.chrono.BasicChronology
    int getDaysInYearMonth(int i2, int i3) {
        return isLeapYear(i2) ? MAX_DAYS_PER_MONTH_ARRAY[i3 - 1] : MIN_DAYS_PER_MONTH_ARRAY[i3 - 1];
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0033, code lost:
    
        if (r13 < 5062500) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0046, code lost:
    
        if (r13 < 12825000) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005e, code lost:
    
        if (r13 < 20587500) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006d, code lost:
    
        if (r13 < 28265625) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0083, code lost:
    
        if (r13 < 4978125) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008f, code lost:
    
        if (r13 < 12740625) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00a0, code lost:
    
        if (r13 < 20503125) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ac, code lost:
    
        if (r13 < 28181250) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:?, code lost:
    
        return 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:?, code lost:
    
        return 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:?, code lost:
    
        return 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:?, code lost:
    
        return 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:?, code lost:
    
        return 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:?, code lost:
    
        return 9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:?, code lost:
    
        return 11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:?, code lost:
    
        return 12;
     */
    @Override // org.joda.time.chrono.BasicChronology
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int getMonthOfYear(long r13, int r15) {
        /*
            r12 = this;
            long r0 = r12.getYearMillis(r15)
            long r13 = r13 - r0
            r0 = 10
            long r13 = r13 >> r0
            int r13 = (int) r13
            boolean r14 = r12.isLeapYear(r15)
            r15 = 12
            r1 = 11
            r2 = 9
            r3 = 8
            r4 = 7
            r5 = 6
            r6 = 5
            r7 = 4
            r8 = 3
            r9 = 2
            r10 = 1
            r11 = 2615625(0x27e949, float:3.665271E-39)
            if (r14 == 0) goto L73
            r14 = 15356250(0xea515a, float:2.151869E-38)
            if (r13 >= r14) goto L4e
            r14 = 7678125(0x7528ad, float:1.0759345E-38)
            if (r13 >= r14) goto L3b
            if (r13 >= r11) goto L30
        L2d:
            r0 = r10
            goto Laf
        L30:
            r14 = 5062500(0x4d3f64, float:7.094073E-39)
            if (r13 >= r14) goto L38
        L35:
            r0 = r9
            goto Laf
        L38:
            r0 = r8
            goto Laf
        L3b:
            r14 = 10209375(0x9bc85f, float:1.4306382E-38)
            if (r13 >= r14) goto L43
        L40:
            r0 = r7
            goto Laf
        L43:
            r14 = 12825000(0xc3b1a8, float:1.7971653E-38)
            if (r13 >= r14) goto L4b
        L48:
            r0 = r6
            goto Laf
        L4b:
            r0 = r5
            goto Laf
        L4e:
            r14 = 23118750(0x160c39e, float:4.128265E-38)
            if (r13 >= r14) goto L64
            r14 = 17971875(0x1123aa3, float:2.6858035E-38)
            if (r13 >= r14) goto L5b
        L58:
            r0 = r4
            goto Laf
        L5b:
            r14 = 20587500(0x13a23ec, float:3.4188577E-38)
            if (r13 >= r14) goto L62
        L60:
            r0 = r3
            goto Laf
        L62:
            r0 = r2
            goto Laf
        L64:
            r14 = 25734375(0x188ace7, float:5.020661E-38)
            if (r13 >= r14) goto L6a
            goto Laf
        L6a:
            r14 = 28265625(0x1af4c99, float:6.439476E-38)
            if (r13 >= r14) goto L71
        L6f:
            r0 = r1
            goto Laf
        L71:
            r0 = r15
            goto Laf
        L73:
            r14 = 15271875(0xe907c3, float:2.1400455E-38)
            if (r13 >= r14) goto L92
            r14 = 7593750(0x73df16, float:1.064111E-38)
            if (r13 >= r14) goto L86
            if (r13 >= r11) goto L80
            goto L2d
        L80:
            r14 = 4978125(0x4bf5cd, float:6.975839E-39)
            if (r13 >= r14) goto L38
            goto L35
        L86:
            r14 = 10125000(0x9a7ec8, float:1.4188147E-38)
            if (r13 >= r14) goto L8c
            goto L40
        L8c:
            r14 = 12740625(0xc26811, float:1.7853418E-38)
            if (r13 >= r14) goto L4b
            goto L48
        L92:
            r14 = 23034375(0x15f7a07, float:4.1046182E-38)
            if (r13 >= r14) goto La3
            r14 = 17887500(0x110f10c, float:2.6621566E-38)
            if (r13 >= r14) goto L9d
            goto L58
        L9d:
            r14 = 20503125(0x138da55, float:3.3952108E-38)
            if (r13 >= r14) goto L62
            goto L60
        La3:
            r14 = 25650000(0x1876350, float:4.9733674E-38)
            if (r13 >= r14) goto La9
            goto Laf
        La9:
            r14 = 28181250(0x1ae0302, float:6.392182E-38)
            if (r13 >= r14) goto L71
            goto L6f
        Laf:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.chrono.BasicGJChronology.getMonthOfYear(long, int):int");
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getTotalMillisByYearMonth(int i2, int i3) {
        return isLeapYear(i2) ? MAX_TOTAL_MILLIS_BY_MONTH_ARRAY[i3 - 1] : MIN_TOTAL_MILLIS_BY_MONTH_ARRAY[i3 - 1];
    }

    @Override // org.joda.time.chrono.BasicChronology
    long getYearDifference(long j2, long j3) {
        int year = getYear(j2);
        int year2 = getYear(j3);
        long yearMillis = j2 - getYearMillis(year);
        long yearMillis2 = j3 - getYearMillis(year2);
        if (yearMillis2 >= FEB_29) {
            if (isLeapYear(year2)) {
                if (!isLeapYear(year)) {
                    yearMillis2 -= 86400000;
                }
            } else if (yearMillis >= FEB_29 && isLeapYear(year)) {
                yearMillis -= 86400000;
            }
        }
        int i2 = year - year2;
        if (yearMillis < yearMillis2) {
            i2--;
        }
        return i2;
    }

    @Override // org.joda.time.chrono.BasicChronology
    long setYear(long j2, int i2) {
        int year = getYear(j2);
        int dayOfYear = getDayOfYear(j2, year);
        int millisOfDay = getMillisOfDay(j2);
        if (dayOfYear > 59) {
            if (isLeapYear(year)) {
                if (!isLeapYear(i2)) {
                    dayOfYear--;
                }
            } else if (isLeapYear(i2)) {
                dayOfYear++;
            }
        }
        return getYearMonthDayMillis(i2, 1, dayOfYear) + millisOfDay;
    }
}
