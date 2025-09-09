package org.joda.time.format;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes5.dex */
public class ISOPeriodFormat {
    private static PeriodFormatter cAlternate;
    private static PeriodFormatter cAlternateExtended;
    private static PeriodFormatter cAlternateExtendedWihWeeks;
    private static PeriodFormatter cAlternateWithWeeks;
    private static PeriodFormatter cStandard;

    public static PeriodFormatter alternate() {
        if (cAlternate == null) {
            cAlternate = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().minimumPrintedDigits(2).appendMonths().appendDays().appendSeparatorIfFieldsAfter(ExifInterface.GPS_DIRECTION_TRUE).appendHours().appendMinutes().appendSecondsWithOptionalMillis().toFormatter();
        }
        return cAlternate;
    }

    public static PeriodFormatter alternateExtended() {
        if (cAlternateExtended == null) {
            cAlternateExtended = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().appendSeparator(Constants.ACCEPT_TIME_SEPARATOR_SERVER).minimumPrintedDigits(2).appendMonths().appendSeparator(Constants.ACCEPT_TIME_SEPARATOR_SERVER).appendDays().appendSeparatorIfFieldsAfter(ExifInterface.GPS_DIRECTION_TRUE).appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSecondsWithOptionalMillis().toFormatter();
        }
        return cAlternateExtended;
    }

    public static PeriodFormatter alternateExtendedWithWeeks() {
        if (cAlternateExtendedWihWeeks == null) {
            cAlternateExtendedWihWeeks = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().appendSeparator(Constants.ACCEPT_TIME_SEPARATOR_SERVER).minimumPrintedDigits(2).appendPrefix(ExifInterface.LONGITUDE_WEST).appendWeeks().appendSeparator(Constants.ACCEPT_TIME_SEPARATOR_SERVER).appendDays().appendSeparatorIfFieldsAfter(ExifInterface.GPS_DIRECTION_TRUE).appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSecondsWithOptionalMillis().toFormatter();
        }
        return cAlternateExtendedWihWeeks;
    }

    public static PeriodFormatter alternateWithWeeks() {
        if (cAlternateWithWeeks == null) {
            cAlternateWithWeeks = new PeriodFormatterBuilder().appendLiteral("P").printZeroAlways().minimumPrintedDigits(4).appendYears().minimumPrintedDigits(2).appendPrefix(ExifInterface.LONGITUDE_WEST).appendWeeks().appendDays().appendSeparatorIfFieldsAfter(ExifInterface.GPS_DIRECTION_TRUE).appendHours().appendMinutes().appendSecondsWithOptionalMillis().toFormatter();
        }
        return cAlternateWithWeeks;
    }

    public static PeriodFormatter standard() {
        if (cStandard == null) {
            cStandard = new PeriodFormatterBuilder().appendLiteral("P").appendYears().appendSuffix("Y").appendMonths().appendSuffix("M").appendWeeks().appendSuffix(ExifInterface.LONGITUDE_WEST).appendDays().appendSuffix("D").appendSeparatorIfFieldsAfter(ExifInterface.GPS_DIRECTION_TRUE).appendHours().appendSuffix("H").appendMinutes().appendSuffix("M").appendSecondsWithOptionalMillis().appendSuffix(ExifInterface.LATITUDE_SOUTH).toFormatter();
        }
        return cStandard;
    }
}
