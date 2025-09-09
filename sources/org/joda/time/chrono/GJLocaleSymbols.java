package org.joda.time.chrono;

import java.lang.ref.WeakReference;
import java.text.DateFormatSymbols;
import java.util.Comparator;
import java.util.Locale;
import java.util.TreeMap;
import java.util.WeakHashMap;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.IllegalFieldValueException;

/* loaded from: classes5.dex */
class GJLocaleSymbols {
    private static final int FAST_CACHE_SIZE = 64;
    private final String[] iDaysOfWeek;
    private final String[] iEras;
    private final String[] iHalfday;
    private final WeakReference<Locale> iLocale;
    private final int iMaxDayOfWeekLength;
    private final int iMaxEraLength;
    private final int iMaxHalfdayLength;
    private final int iMaxMonthLength;
    private final int iMaxShortDayOfWeekLength;
    private final int iMaxShortMonthLength;
    private final String[] iMonths;
    private final TreeMap<String, Integer> iParseDaysOfWeek;
    private final TreeMap<String, Integer> iParseEras;
    private final TreeMap<String, Integer> iParseMonths;
    private final String[] iShortDaysOfWeek;
    private final String[] iShortMonths;
    private static final GJLocaleSymbols[] cFastCache = new GJLocaleSymbols[64];
    private static WeakHashMap<Locale, GJLocaleSymbols> cCache = new WeakHashMap<>();

    private GJLocaleSymbols(Locale locale) {
        this.iLocale = new WeakReference<>(locale);
        DateFormatSymbols dateFormatSymbols = DateTimeUtils.getDateFormatSymbols(locale);
        this.iEras = dateFormatSymbols.getEras();
        this.iDaysOfWeek = realignDaysOfWeek(dateFormatSymbols.getWeekdays());
        this.iShortDaysOfWeek = realignDaysOfWeek(dateFormatSymbols.getShortWeekdays());
        this.iMonths = realignMonths(dateFormatSymbols.getMonths());
        this.iShortMonths = realignMonths(dateFormatSymbols.getShortMonths());
        this.iHalfday = dateFormatSymbols.getAmPmStrings();
        Integer[] numArr = new Integer[13];
        for (int i2 = 0; i2 < 13; i2++) {
            numArr[i2] = Integer.valueOf(i2);
        }
        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        TreeMap<String, Integer> treeMap = new TreeMap<>((Comparator<? super String>) comparator);
        this.iParseEras = treeMap;
        addSymbols(treeMap, this.iEras, numArr);
        if ("en".equals(locale.getLanguage())) {
            treeMap.put("BCE", numArr[0]);
            treeMap.put("CE", numArr[1]);
        }
        TreeMap<String, Integer> treeMap2 = new TreeMap<>((Comparator<? super String>) comparator);
        this.iParseDaysOfWeek = treeMap2;
        addSymbols(treeMap2, this.iDaysOfWeek, numArr);
        addSymbols(treeMap2, this.iShortDaysOfWeek, numArr);
        addNumerals(treeMap2, 1, 7, numArr);
        TreeMap<String, Integer> treeMap3 = new TreeMap<>((Comparator<? super String>) comparator);
        this.iParseMonths = treeMap3;
        addSymbols(treeMap3, this.iMonths, numArr);
        addSymbols(treeMap3, this.iShortMonths, numArr);
        addNumerals(treeMap3, 1, 12, numArr);
        this.iMaxEraLength = maxLength(this.iEras);
        this.iMaxDayOfWeekLength = maxLength(this.iDaysOfWeek);
        this.iMaxShortDayOfWeekLength = maxLength(this.iShortDaysOfWeek);
        this.iMaxMonthLength = maxLength(this.iMonths);
        this.iMaxShortMonthLength = maxLength(this.iShortMonths);
        this.iMaxHalfdayLength = maxLength(this.iHalfday);
    }

    private static void addNumerals(TreeMap<String, Integer> treeMap, int i2, int i3, Integer[] numArr) {
        while (i2 <= i3) {
            treeMap.put(String.valueOf(i2).intern(), numArr[i2]);
            i2++;
        }
    }

    private static void addSymbols(TreeMap<String, Integer> treeMap, String[] strArr, Integer[] numArr) {
        int length = strArr.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            String str = strArr[length];
            if (str != null) {
                treeMap.put(str, numArr[length]);
            }
        }
    }

    public static GJLocaleSymbols forLocale(Locale locale) {
        GJLocaleSymbols gJLocaleSymbols;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        int iIdentityHashCode = System.identityHashCode(locale) & 63;
        GJLocaleSymbols[] gJLocaleSymbolsArr = cFastCache;
        GJLocaleSymbols gJLocaleSymbols2 = gJLocaleSymbolsArr[iIdentityHashCode];
        if (gJLocaleSymbols2 != null && gJLocaleSymbols2.iLocale.get() == locale) {
            return gJLocaleSymbols2;
        }
        synchronized (cCache) {
            try {
                gJLocaleSymbols = cCache.get(locale);
                if (gJLocaleSymbols == null) {
                    gJLocaleSymbols = new GJLocaleSymbols(locale);
                    cCache.put(locale, gJLocaleSymbols);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        gJLocaleSymbolsArr[iIdentityHashCode] = gJLocaleSymbols;
        return gJLocaleSymbols;
    }

    private static int maxLength(String[] strArr) {
        int length;
        int length2 = strArr.length;
        int i2 = 0;
        while (true) {
            length2--;
            if (length2 < 0) {
                return i2;
            }
            String str = strArr[length2];
            if (str != null && (length = str.length()) > i2) {
                i2 = length;
            }
        }
    }

    private static String[] realignDaysOfWeek(String[] strArr) {
        String[] strArr2 = new String[8];
        int i2 = 1;
        while (i2 < 8) {
            strArr2[i2] = strArr[i2 < 7 ? i2 + 1 : 1];
            i2++;
        }
        return strArr2;
    }

    private static String[] realignMonths(String[] strArr) {
        String[] strArr2 = new String[13];
        for (int i2 = 1; i2 < 13; i2++) {
            strArr2[i2] = strArr[i2 - 1];
        }
        return strArr2;
    }

    public int dayOfWeekTextToValue(String str) {
        Integer num = this.iParseDaysOfWeek.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.dayOfWeek(), str);
    }

    public String dayOfWeekValueToShortText(int i2) {
        return this.iShortDaysOfWeek[i2];
    }

    public String dayOfWeekValueToText(int i2) {
        return this.iDaysOfWeek[i2];
    }

    public int eraTextToValue(String str) {
        Integer num = this.iParseEras.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.era(), str);
    }

    public String eraValueToText(int i2) {
        return this.iEras[i2];
    }

    public int getDayOfWeekMaxShortTextLength() {
        return this.iMaxShortDayOfWeekLength;
    }

    public int getDayOfWeekMaxTextLength() {
        return this.iMaxDayOfWeekLength;
    }

    public int getEraMaxTextLength() {
        return this.iMaxEraLength;
    }

    public int getHalfdayMaxTextLength() {
        return this.iMaxHalfdayLength;
    }

    public int getMonthMaxShortTextLength() {
        return this.iMaxShortMonthLength;
    }

    public int getMonthMaxTextLength() {
        return this.iMaxMonthLength;
    }

    public int halfdayTextToValue(String str) {
        String[] strArr = this.iHalfday;
        int length = strArr.length;
        do {
            length--;
            if (length < 0) {
                throw new IllegalFieldValueException(DateTimeFieldType.halfdayOfDay(), str);
            }
        } while (!strArr[length].equalsIgnoreCase(str));
        return length;
    }

    public String halfdayValueToText(int i2) {
        return this.iHalfday[i2];
    }

    public int monthOfYearTextToValue(String str) {
        Integer num = this.iParseMonths.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalFieldValueException(DateTimeFieldType.monthOfYear(), str);
    }

    public String monthOfYearValueToShortText(int i2) {
        return this.iShortMonths[i2];
    }

    public String monthOfYearValueToText(int i2) {
        return this.iMonths[i2];
    }
}
