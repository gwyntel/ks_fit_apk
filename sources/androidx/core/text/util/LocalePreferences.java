package androidx.core.text.util;

import android.icu.number.NumberFormatter;
import android.icu.number.UnlocalizedNumberFormatter;
import android.icu.text.DateFormat;
import android.icu.text.DateTimePatternGenerator;
import android.icu.util.Calendar;
import android.icu.util.MeasureUnit;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Locale;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class LocalePreferences {
    private static final String TAG = "LocalePreferences";
    private static final String[] WEATHER_FAHRENHEIT_COUNTRIES = {"BS", "BZ", "KY", "PR", "PW", "US"};

    /* renamed from: androidx.core.text.util.LocalePreferences$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3711a;

        static {
            int[] iArr = new int[DateFormat.HourCycle.values().length];
            f3711a = iArr;
            try {
                iArr[DateFormat.HourCycle.HOUR_CYCLE_11.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3711a[DateFormat.HourCycle.HOUR_CYCLE_12.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3711a[DateFormat.HourCycle.HOUR_CYCLE_23.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f3711a[DateFormat.HourCycle.HOUR_CYCLE_24.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @RequiresApi(24)
    private static class Api24Impl {
        private Api24Impl() {
        }

        @DoNotInline
        static String a(@NonNull Locale locale) {
            return Calendar.getInstance(locale).getType();
        }

        @DoNotInline
        static Locale b() {
            return Locale.getDefault(Locale.Category.FORMAT);
        }
    }

    @RequiresApi(33)
    private static class Api33Impl {
        private Api33Impl() {
        }

        @DoNotInline
        static String a(@NonNull Locale locale) {
            return getHourCycleType(DateTimePatternGenerator.getInstance(locale).getDefaultHourCycle());
        }

        @DoNotInline
        static String b(@NonNull Locale locale) {
            String identifier = ((UnlocalizedNumberFormatter) ((UnlocalizedNumberFormatter) NumberFormatter.with().usage("weather")).unit(MeasureUnit.CELSIUS)).locale(locale).format(1L).getOutputUnit().getIdentifier();
            return identifier.startsWith(TemperatureUnit.FAHRENHEIT) ? TemperatureUnit.FAHRENHEIT : identifier;
        }

        private static String getHourCycleType(DateFormat.HourCycle hourCycle) {
            int i2 = AnonymousClass1.f3711a[hourCycle.ordinal()];
            return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? "" : HourCycle.H24 : HourCycle.H23 : HourCycle.H12 : HourCycle.H11;
        }
    }

    public static class CalendarType {
        public static final String CHINESE = "chinese";
        public static final String DANGI = "dangi";
        public static final String DEFAULT = "";
        public static final String GREGORIAN = "gregorian";
        public static final String HEBREW = "hebrew";
        public static final String INDIAN = "indian";
        public static final String ISLAMIC = "islamic";
        public static final String ISLAMIC_CIVIL = "islamic-civil";
        public static final String ISLAMIC_RGSA = "islamic-rgsa";
        public static final String ISLAMIC_TBLA = "islamic-tbla";
        public static final String ISLAMIC_UMALQURA = "islamic-umalqura";
        public static final String PERSIAN = "persian";
        private static final String U_EXTENSION_TAG = "ca";

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface CalendarTypes {
        }

        private CalendarType() {
        }
    }

    public static class FirstDayOfWeek {
        public static final String DEFAULT = "";
        public static final String FRIDAY = "fri";
        public static final String MONDAY = "mon";
        public static final String SATURDAY = "sat";
        public static final String SUNDAY = "sun";
        public static final String THURSDAY = "thu";
        public static final String TUESDAY = "tue";
        private static final String U_EXTENSION_TAG = "fw";
        public static final String WEDNESDAY = "wed";

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface Days {
        }

        private FirstDayOfWeek() {
        }
    }

    public static class HourCycle {
        public static final String DEFAULT = "";
        public static final String H11 = "h11";
        public static final String H12 = "h12";
        public static final String H23 = "h23";
        public static final String H24 = "h24";
        private static final String U_EXTENSION_TAG = "hc";

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface HourCycleTypes {
        }

        private HourCycle() {
        }
    }

    public static class TemperatureUnit {
        public static final String CELSIUS = "celsius";
        public static final String DEFAULT = "";
        public static final String FAHRENHEIT = "fahrenhe";
        public static final String KELVIN = "kelvin";
        private static final String U_EXTENSION_TAG = "mu";

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public @interface TemperatureUnits {
        }

        private TemperatureUnit() {
        }
    }

    private LocalePreferences() {
    }

    private static String getBaseFirstDayOfWeek(@NonNull Locale locale) {
        return getStringOfFirstDayOfWeek(java.util.Calendar.getInstance(locale).getFirstDayOfWeek());
    }

    private static String getBaseHourCycle(@NonNull Locale locale) {
        return android.text.format.DateFormat.getBestDateTimePattern(locale, "jm").contains("H") ? HourCycle.H23 : HourCycle.H12;
    }

    @NonNull
    public static String getCalendarType() {
        return getCalendarType(true);
    }

    private static Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    @NonNull
    public static String getFirstDayOfWeek() {
        return getFirstDayOfWeek(true);
    }

    @NonNull
    public static String getHourCycle() {
        return getHourCycle(true);
    }

    private static String getStringOfFirstDayOfWeek(int i2) {
        return (i2 < 1 || i2 > 7) ? "" : new String[]{FirstDayOfWeek.SUNDAY, FirstDayOfWeek.MONDAY, FirstDayOfWeek.TUESDAY, FirstDayOfWeek.WEDNESDAY, FirstDayOfWeek.THURSDAY, FirstDayOfWeek.FRIDAY, FirstDayOfWeek.SATURDAY}[i2 - 1];
    }

    private static String getTemperatureHardCoded(Locale locale) {
        return Arrays.binarySearch(WEATHER_FAHRENHEIT_COUNTRIES, locale.getCountry()) >= 0 ? TemperatureUnit.FAHRENHEIT : TemperatureUnit.CELSIUS;
    }

    @NonNull
    public static String getTemperatureUnit() {
        return getTemperatureUnit(true);
    }

    private static String getUnicodeLocaleType(String str, String str2, Locale locale, boolean z2) {
        String unicodeLocaleType = locale.getUnicodeLocaleType(str);
        if (unicodeLocaleType != null) {
            return unicodeLocaleType;
        }
        if (z2) {
            return null;
        }
        return str2;
    }

    @NonNull
    public static String getCalendarType(@NonNull Locale locale) {
        return getCalendarType(locale, true);
    }

    @NonNull
    public static String getFirstDayOfWeek(@NonNull Locale locale) {
        return getFirstDayOfWeek(locale, true);
    }

    @NonNull
    public static String getHourCycle(@NonNull Locale locale) {
        return getHourCycle(locale, true);
    }

    @NonNull
    public static String getTemperatureUnit(@NonNull Locale locale) {
        return getTemperatureUnit(locale, true);
    }

    @NonNull
    public static String getCalendarType(boolean z2) {
        Locale defaultLocale;
        if (Build.VERSION.SDK_INT >= 24) {
            defaultLocale = Api24Impl.b();
        } else {
            defaultLocale = getDefaultLocale();
        }
        return getCalendarType(defaultLocale, z2);
    }

    @NonNull
    public static String getFirstDayOfWeek(boolean z2) {
        Locale defaultLocale;
        if (Build.VERSION.SDK_INT >= 24) {
            defaultLocale = Api24Impl.b();
        } else {
            defaultLocale = getDefaultLocale();
        }
        return getFirstDayOfWeek(defaultLocale, z2);
    }

    @NonNull
    public static String getHourCycle(boolean z2) {
        Locale defaultLocale;
        if (Build.VERSION.SDK_INT >= 24) {
            defaultLocale = Api24Impl.b();
        } else {
            defaultLocale = getDefaultLocale();
        }
        return getHourCycle(defaultLocale, z2);
    }

    @NonNull
    public static String getTemperatureUnit(boolean z2) {
        Locale defaultLocale;
        if (Build.VERSION.SDK_INT >= 24) {
            defaultLocale = Api24Impl.b();
        } else {
            defaultLocale = getDefaultLocale();
        }
        return getTemperatureUnit(defaultLocale, z2);
    }

    @NonNull
    public static String getCalendarType(@NonNull Locale locale, boolean z2) {
        String unicodeLocaleType = getUnicodeLocaleType("ca", "", locale, z2);
        if (unicodeLocaleType != null) {
            return unicodeLocaleType;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.a(locale);
        }
        return z2 ? CalendarType.GREGORIAN : "";
    }

    @NonNull
    public static String getFirstDayOfWeek(@NonNull Locale locale, boolean z2) {
        String unicodeLocaleType = getUnicodeLocaleType("fw", "", locale, z2);
        return unicodeLocaleType != null ? unicodeLocaleType : getBaseFirstDayOfWeek(locale);
    }

    @NonNull
    public static String getHourCycle(@NonNull Locale locale, boolean z2) {
        String unicodeLocaleType = getUnicodeLocaleType("hc", "", locale, z2);
        if (unicodeLocaleType != null) {
            return unicodeLocaleType;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            return Api33Impl.a(locale);
        }
        return getBaseHourCycle(locale);
    }

    @NonNull
    public static String getTemperatureUnit(@NonNull Locale locale, boolean z2) {
        String unicodeLocaleType = getUnicodeLocaleType("mu", "", locale, z2);
        if (unicodeLocaleType != null) {
            return unicodeLocaleType;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            return Api33Impl.b(locale);
        }
        return getTemperatureHardCoded(locale);
    }
}
