package org.joda.time.format;

import java.util.Collection;
import org.joda.time.DateTimeFieldType;

/* loaded from: classes5.dex */
public class ISODateTimeFormat {

    static final class Constants {
        private static final DateTimeFormatter ye = yearElement();
        private static final DateTimeFormatter mye = monthElement();
        private static final DateTimeFormatter dme = dayOfMonthElement();
        private static final DateTimeFormatter we = weekyearElement();
        private static final DateTimeFormatter wwe = weekElement();
        private static final DateTimeFormatter dwe = dayOfWeekElement();
        private static final DateTimeFormatter dye = dayOfYearElement();
        private static final DateTimeFormatter hde = hourElement();
        private static final DateTimeFormatter mhe = minuteElement();
        private static final DateTimeFormatter sme = secondElement();
        private static final DateTimeFormatter fse = fractionElement();
        private static final DateTimeFormatter ze = offsetElement();
        private static final DateTimeFormatter lte = literalTElement();
        private static final DateTimeFormatter ym = yearMonth();
        private static final DateTimeFormatter ymd = yearMonthDay();
        private static final DateTimeFormatter ww = weekyearWeek();
        private static final DateTimeFormatter wwd = weekyearWeekDay();
        private static final DateTimeFormatter hm = hourMinute();
        private static final DateTimeFormatter hms = hourMinuteSecond();
        private static final DateTimeFormatter hmsl = hourMinuteSecondMillis();
        private static final DateTimeFormatter hmsf = hourMinuteSecondFraction();
        private static final DateTimeFormatter dh = dateHour();
        private static final DateTimeFormatter dhm = dateHourMinute();
        private static final DateTimeFormatter dhms = dateHourMinuteSecond();
        private static final DateTimeFormatter dhmsl = dateHourMinuteSecondMillis();
        private static final DateTimeFormatter dhmsf = dateHourMinuteSecondFraction();

        /* renamed from: t, reason: collision with root package name */
        private static final DateTimeFormatter f26736t = time();
        private static final DateTimeFormatter tx = timeNoMillis();
        private static final DateTimeFormatter tt = tTime();
        private static final DateTimeFormatter ttx = tTimeNoMillis();
        private static final DateTimeFormatter dt = dateTime();
        private static final DateTimeFormatter dtx = dateTimeNoMillis();
        private static final DateTimeFormatter wdt = weekDateTime();
        private static final DateTimeFormatter wdtx = weekDateTimeNoMillis();
        private static final DateTimeFormatter od = ordinalDate();
        private static final DateTimeFormatter odt = ordinalDateTime();
        private static final DateTimeFormatter odtx = ordinalDateTimeNoMillis();
        private static final DateTimeFormatter bd = basicDate();
        private static final DateTimeFormatter bt = basicTime();
        private static final DateTimeFormatter btx = basicTimeNoMillis();
        private static final DateTimeFormatter btt = basicTTime();
        private static final DateTimeFormatter bttx = basicTTimeNoMillis();
        private static final DateTimeFormatter bdt = basicDateTime();
        private static final DateTimeFormatter bdtx = basicDateTimeNoMillis();
        private static final DateTimeFormatter bod = basicOrdinalDate();
        private static final DateTimeFormatter bodt = basicOrdinalDateTime();
        private static final DateTimeFormatter bodtx = basicOrdinalDateTimeNoMillis();
        private static final DateTimeFormatter bwd = basicWeekDate();
        private static final DateTimeFormatter bwdt = basicWeekDateTime();
        private static final DateTimeFormatter bwdtx = basicWeekDateTimeNoMillis();
        private static final DateTimeFormatter dpe = dateElementParser();
        private static final DateTimeFormatter tpe = timeElementParser();
        private static final DateTimeFormatter dp = dateParser();
        private static final DateTimeFormatter ldp = localDateParser();
        private static final DateTimeFormatter tp = timeParser();
        private static final DateTimeFormatter ltp = localTimeParser();
        private static final DateTimeFormatter dtp = dateTimeParser();
        private static final DateTimeFormatter dotp = dateOptionalTimeParser();
        private static final DateTimeFormatter ldotp = localDateOptionalTimeParser();

        private static DateTimeFormatter basicDate() {
            DateTimeFormatter dateTimeFormatter = bd;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.monthOfYear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfMonth(), 2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicDateTime() {
            DateTimeFormatter dateTimeFormatter = bdt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(basicDate()).append(basicTTime()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicDateTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = bdtx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(basicDate()).append(basicTTimeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicOrdinalDate() {
            DateTimeFormatter dateTimeFormatter = bod;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.dayOfYear(), 3).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicOrdinalDateTime() {
            DateTimeFormatter dateTimeFormatter = bodt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(basicOrdinalDate()).append(basicTTime()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicOrdinalDateTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = bodtx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(basicOrdinalDate()).append(basicTTimeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicTTime() {
            DateTimeFormatter dateTimeFormatter = btt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(literalTElement()).append(basicTime()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicTTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = bttx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(literalTElement()).append(basicTimeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicTime() {
            DateTimeFormatter dateTimeFormatter = bt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendLiteral('.').appendFractionOfSecond(3, 9).appendTimeZoneOffset("Z", false, 2, 2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = btx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendTimeZoneOffset("Z", false, 2, 2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicWeekDate() {
            DateTimeFormatter dateTimeFormatter = bwd;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendWeekyear(4, 4).appendLiteral('W').appendFixedDecimal(DateTimeFieldType.weekOfWeekyear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfWeek(), 1).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicWeekDateTime() {
            DateTimeFormatter dateTimeFormatter = bwdt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(basicWeekDate()).append(basicTTime()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter basicWeekDateTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = bwdtx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(basicWeekDate()).append(basicTTimeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateElementParser() {
            DateTimeFormatter dateTimeFormatter = dpe;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(yearElement()).appendOptional(new DateTimeFormatterBuilder().append(monthElement()).appendOptional(dayOfMonthElement().getParser()).toParser()).toParser(), new DateTimeFormatterBuilder().append(weekyearElement()).append(weekElement()).appendOptional(dayOfWeekElement().getParser()).toParser(), new DateTimeFormatterBuilder().append(yearElement()).append(dayOfYearElement()).toParser()}).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateHour() {
            DateTimeFormatter dateTimeFormatter = dh;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(ISODateTimeFormat.hour()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateHourMinute() {
            DateTimeFormatter dateTimeFormatter = dhm;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinute()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateHourMinuteSecond() {
            DateTimeFormatter dateTimeFormatter = dhms;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinuteSecond()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateHourMinuteSecondFraction() {
            DateTimeFormatter dateTimeFormatter = dhmsf;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinuteSecondFraction()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateHourMinuteSecondMillis() {
            DateTimeFormatter dateTimeFormatter = dhmsl;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(literalTElement()).append(hourMinuteSecondMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateOptionalTimeParser() {
            DateTimeFormatter dateTimeFormatter = dotp;
            if (dateTimeFormatter != null) {
                return dateTimeFormatter;
            }
            return new DateTimeFormatterBuilder().append(dateElementParser()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').appendOptional(timeElementParser().getParser()).appendOptional(offsetElement().getParser()).toParser()).toFormatter();
        }

        private static DateTimeFormatter dateParser() {
            DateTimeFormatter dateTimeFormatter = dp;
            if (dateTimeFormatter != null) {
                return dateTimeFormatter;
            }
            return new DateTimeFormatterBuilder().append(dateElementParser()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').append(offsetElement()).toParser()).toFormatter();
        }

        private static DateTimeFormatter dateTime() {
            DateTimeFormatter dateTimeFormatter = dt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(tTime()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = dtx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(tTimeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dateTimeParser() {
            DateTimeFormatter dateTimeFormatter = dtp;
            if (dateTimeFormatter != null) {
                return dateTimeFormatter;
            }
            return new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().appendLiteral('T').append(timeElementParser()).appendOptional(offsetElement().getParser()).toParser(), dateOptionalTimeParser().getParser()}).toFormatter();
        }

        private static DateTimeFormatter dayOfMonthElement() {
            DateTimeFormatter dateTimeFormatter = dme;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfMonth(2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dayOfWeekElement() {
            DateTimeFormatter dateTimeFormatter = dwe;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfWeek(1).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter dayOfYearElement() {
            DateTimeFormatter dateTimeFormatter = dye;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfYear(3).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter fractionElement() {
            DateTimeFormatter dateTimeFormatter = fse;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral('.').appendFractionOfSecond(3, 9).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter hourElement() {
            DateTimeFormatter dateTimeFormatter = hde;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendHourOfDay(2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter hourMinute() {
            DateTimeFormatter dateTimeFormatter = hm;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter hourMinuteSecond() {
            DateTimeFormatter dateTimeFormatter = hms;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).append(secondElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter hourMinuteSecondFraction() {
            DateTimeFormatter dateTimeFormatter = hmsf;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).append(secondElement()).append(fractionElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter hourMinuteSecondMillis() {
            DateTimeFormatter dateTimeFormatter = hmsl;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(hourElement()).append(minuteElement()).append(secondElement()).appendLiteral('.').appendFractionOfSecond(3, 3).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter literalTElement() {
            DateTimeFormatter dateTimeFormatter = lte;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral('T').toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter localDateOptionalTimeParser() {
            DateTimeFormatter dateTimeFormatter = ldotp;
            if (dateTimeFormatter != null) {
                return dateTimeFormatter;
            }
            return new DateTimeFormatterBuilder().append(dateElementParser()).appendOptional(new DateTimeFormatterBuilder().appendLiteral('T').append(timeElementParser()).toParser()).toFormatter().withZoneUTC();
        }

        private static DateTimeFormatter localDateParser() {
            DateTimeFormatter dateTimeFormatter = ldp;
            return dateTimeFormatter == null ? dateElementParser().withZoneUTC() : dateTimeFormatter;
        }

        private static DateTimeFormatter localTimeParser() {
            DateTimeFormatter dateTimeFormatter = ltp;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendOptional(literalTElement().getParser()).append(timeElementParser()).toFormatter().withZoneUTC() : dateTimeFormatter;
        }

        private static DateTimeFormatter minuteElement() {
            DateTimeFormatter dateTimeFormatter = mhe;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral(':').appendMinuteOfHour(2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter monthElement() {
            DateTimeFormatter dateTimeFormatter = mye;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral('-').appendMonthOfYear(2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter offsetElement() {
            DateTimeFormatter dateTimeFormatter = ze;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendTimeZoneOffset("Z", true, 2, 4).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter ordinalDate() {
            DateTimeFormatter dateTimeFormatter = od;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(yearElement()).append(dayOfYearElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter ordinalDateTime() {
            DateTimeFormatter dateTimeFormatter = odt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ordinalDate()).append(tTime()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter ordinalDateTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = odtx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ordinalDate()).append(tTimeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter secondElement() {
            DateTimeFormatter dateTimeFormatter = sme;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral(':').appendSecondOfMinute(2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter tTime() {
            DateTimeFormatter dateTimeFormatter = tt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(literalTElement()).append(time()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter tTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = ttx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(literalTElement()).append(timeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter time() {
            DateTimeFormatter dateTimeFormatter = f26736t;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(hourMinuteSecondFraction()).append(offsetElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter timeElementParser() {
            DateTimeFormatter dateTimeFormatter = tpe;
            if (dateTimeFormatter != null) {
                return dateTimeFormatter;
            }
            DateTimeParser parser = new DateTimeFormatterBuilder().append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().appendLiteral('.').toParser(), new DateTimeFormatterBuilder().appendLiteral(',').toParser()}).toParser();
            return new DateTimeFormatterBuilder().append(hourElement()).append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(minuteElement()).append((DateTimePrinter) null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(secondElement()).appendOptional(new DateTimeFormatterBuilder().append(parser).appendFractionOfSecond(1, 9).toParser()).toParser(), new DateTimeFormatterBuilder().append(parser).appendFractionOfMinute(1, 9).toParser(), null}).toParser(), new DateTimeFormatterBuilder().append(parser).appendFractionOfHour(1, 9).toParser(), null}).toFormatter();
        }

        private static DateTimeFormatter timeNoMillis() {
            DateTimeFormatter dateTimeFormatter = tx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(hourMinuteSecond()).append(offsetElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter timeParser() {
            DateTimeFormatter dateTimeFormatter = tp;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendOptional(literalTElement().getParser()).append(timeElementParser()).appendOptional(offsetElement().getParser()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter weekDateTime() {
            DateTimeFormatter dateTimeFormatter = wdt;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(tTime()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter weekDateTimeNoMillis() {
            DateTimeFormatter dateTimeFormatter = wdtx;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(tTimeNoMillis()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter weekElement() {
            DateTimeFormatter dateTimeFormatter = wwe;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendLiteral("-W").appendWeekOfWeekyear(2).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter weekyearElement() {
            DateTimeFormatter dateTimeFormatter = we;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendWeekyear(4, 9).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter weekyearWeek() {
            DateTimeFormatter dateTimeFormatter = ww;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(weekyearElement()).append(weekElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter weekyearWeekDay() {
            DateTimeFormatter dateTimeFormatter = wwd;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(weekyearElement()).append(weekElement()).append(dayOfWeekElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter yearElement() {
            DateTimeFormatter dateTimeFormatter = ye;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().appendYear(4, 9).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter yearMonth() {
            DateTimeFormatter dateTimeFormatter = ym;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(yearElement()).append(monthElement()).toFormatter() : dateTimeFormatter;
        }

        private static DateTimeFormatter yearMonthDay() {
            DateTimeFormatter dateTimeFormatter = ymd;
            return dateTimeFormatter == null ? new DateTimeFormatterBuilder().append(yearElement()).append(monthElement()).append(dayOfMonthElement()).toFormatter() : dateTimeFormatter;
        }
    }

    private static void appendSeparator(DateTimeFormatterBuilder dateTimeFormatterBuilder, boolean z2) {
        if (z2) {
            dateTimeFormatterBuilder.appendLiteral('-');
        }
    }

    public static DateTimeFormatter basicDate() {
        return Constants.bd;
    }

    public static DateTimeFormatter basicDateTime() {
        return Constants.bdt;
    }

    public static DateTimeFormatter basicDateTimeNoMillis() {
        return Constants.bdtx;
    }

    public static DateTimeFormatter basicOrdinalDate() {
        return Constants.bod;
    }

    public static DateTimeFormatter basicOrdinalDateTime() {
        return Constants.bodt;
    }

    public static DateTimeFormatter basicOrdinalDateTimeNoMillis() {
        return Constants.bodtx;
    }

    public static DateTimeFormatter basicTTime() {
        return Constants.btt;
    }

    public static DateTimeFormatter basicTTimeNoMillis() {
        return Constants.bttx;
    }

    public static DateTimeFormatter basicTime() {
        return Constants.bt;
    }

    public static DateTimeFormatter basicTimeNoMillis() {
        return Constants.btx;
    }

    public static DateTimeFormatter basicWeekDate() {
        return Constants.bwd;
    }

    public static DateTimeFormatter basicWeekDateTime() {
        return Constants.bwdt;
    }

    public static DateTimeFormatter basicWeekDateTimeNoMillis() {
        return Constants.bwdtx;
    }

    private static void checkNotStrictISO(Collection<DateTimeFieldType> collection, boolean z2) {
        if (z2) {
            throw new IllegalArgumentException("No valid ISO8601 format for fields: " + collection);
        }
    }

    public static DateTimeFormatter date() {
        return yearMonthDay();
    }

    private static boolean dateByMonth(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z2, boolean z3) {
        if (collection.remove(DateTimeFieldType.year())) {
            dateTimeFormatterBuilder.append(Constants.ye);
            if (collection.remove(DateTimeFieldType.monthOfYear())) {
                if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
                    dateTimeFormatterBuilder.appendLiteral('-');
                    dateTimeFormatterBuilder.appendMonthOfYear(2);
                    return true;
                }
                appendSeparator(dateTimeFormatterBuilder, z2);
                dateTimeFormatterBuilder.appendMonthOfYear(2);
                appendSeparator(dateTimeFormatterBuilder, z2);
                dateTimeFormatterBuilder.appendDayOfMonth(2);
            } else {
                if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
                    return true;
                }
                checkNotStrictISO(collection, z3);
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendDayOfMonth(2);
            }
        } else if (collection.remove(DateTimeFieldType.monthOfYear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendMonthOfYear(2);
            if (!collection.remove(DateTimeFieldType.dayOfMonth())) {
                return true;
            }
            appendSeparator(dateTimeFormatterBuilder, z2);
            dateTimeFormatterBuilder.appendDayOfMonth(2);
        } else if (collection.remove(DateTimeFieldType.dayOfMonth())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfMonth(2);
        }
        return false;
    }

    private static boolean dateByOrdinal(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z2, boolean z3) {
        if (collection.remove(DateTimeFieldType.year())) {
            dateTimeFormatterBuilder.append(Constants.ye);
            if (!collection.remove(DateTimeFieldType.dayOfYear())) {
                return true;
            }
            appendSeparator(dateTimeFormatterBuilder, z2);
            dateTimeFormatterBuilder.appendDayOfYear(3);
        } else if (collection.remove(DateTimeFieldType.dayOfYear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfYear(3);
        }
        return false;
    }

    private static boolean dateByWeek(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z2, boolean z3) {
        if (collection.remove(DateTimeFieldType.weekyear())) {
            dateTimeFormatterBuilder.append(Constants.we);
            if (collection.remove(DateTimeFieldType.weekOfWeekyear())) {
                appendSeparator(dateTimeFormatterBuilder, z2);
                dateTimeFormatterBuilder.appendLiteral('W');
                dateTimeFormatterBuilder.appendWeekOfWeekyear(2);
                if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                    return true;
                }
                appendSeparator(dateTimeFormatterBuilder, z2);
                dateTimeFormatterBuilder.appendDayOfWeek(1);
            } else {
                if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                    return true;
                }
                checkNotStrictISO(collection, z3);
                appendSeparator(dateTimeFormatterBuilder, z2);
                dateTimeFormatterBuilder.appendLiteral('W');
                dateTimeFormatterBuilder.appendLiteral('-');
                dateTimeFormatterBuilder.appendDayOfWeek(1);
            }
        } else if (collection.remove(DateTimeFieldType.weekOfWeekyear())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('W');
            dateTimeFormatterBuilder.appendWeekOfWeekyear(2);
            if (!collection.remove(DateTimeFieldType.dayOfWeek())) {
                return true;
            }
            appendSeparator(dateTimeFormatterBuilder, z2);
            dateTimeFormatterBuilder.appendDayOfWeek(1);
        } else if (collection.remove(DateTimeFieldType.dayOfWeek())) {
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendLiteral('W');
            dateTimeFormatterBuilder.appendLiteral('-');
            dateTimeFormatterBuilder.appendDayOfWeek(1);
        }
        return false;
    }

    public static DateTimeFormatter dateElementParser() {
        return Constants.dpe;
    }

    public static DateTimeFormatter dateHour() {
        return Constants.dh;
    }

    public static DateTimeFormatter dateHourMinute() {
        return Constants.dhm;
    }

    public static DateTimeFormatter dateHourMinuteSecond() {
        return Constants.dhms;
    }

    public static DateTimeFormatter dateHourMinuteSecondFraction() {
        return Constants.dhmsf;
    }

    public static DateTimeFormatter dateHourMinuteSecondMillis() {
        return Constants.dhmsl;
    }

    public static DateTimeFormatter dateOptionalTimeParser() {
        return Constants.dotp;
    }

    public static DateTimeFormatter dateParser() {
        return Constants.dp;
    }

    public static DateTimeFormatter dateTime() {
        return Constants.dt;
    }

    public static DateTimeFormatter dateTimeNoMillis() {
        return Constants.dtx;
    }

    public static DateTimeFormatter dateTimeParser() {
        return Constants.dtp;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.joda.time.format.DateTimeFormatter forFields(java.util.Collection<org.joda.time.DateTimeFieldType> r8, boolean r9, boolean r10) {
        /*
            if (r8 == 0) goto Lbf
            int r0 = r8.size()
            if (r0 == 0) goto Lbf
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>(r8)
            int r1 = r0.size()
            org.joda.time.format.DateTimeFormatterBuilder r7 = new org.joda.time.format.DateTimeFormatterBuilder
            r7.<init>()
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.monthOfYear()
            boolean r2 = r0.contains(r2)
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L28
            boolean r2 = dateByMonth(r7, r0, r9, r10)
        L26:
            r5 = r2
            goto L8a
        L28:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.dayOfYear()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L37
            boolean r2 = dateByOrdinal(r7, r0, r9, r10)
            goto L26
        L37:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.weekOfWeekyear()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L46
            boolean r2 = dateByWeek(r7, r0, r9, r10)
            goto L26
        L46:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.dayOfMonth()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L55
            boolean r2 = dateByMonth(r7, r0, r9, r10)
            goto L26
        L55:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.dayOfWeek()
            boolean r2 = r0.contains(r2)
            if (r2 == 0) goto L64
            boolean r2 = dateByWeek(r7, r0, r9, r10)
            goto L26
        L64:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.year()
            boolean r2 = r0.remove(r2)
            if (r2 == 0) goto L77
            org.joda.time.format.DateTimeFormatter r2 = org.joda.time.format.ISODateTimeFormat.Constants.a()
            r7.append(r2)
        L75:
            r5 = r4
            goto L8a
        L77:
            org.joda.time.DateTimeFieldType r2 = org.joda.time.DateTimeFieldType.weekyear()
            boolean r2 = r0.remove(r2)
            if (r2 == 0) goto L89
            org.joda.time.format.DateTimeFormatter r2 = org.joda.time.format.ISODateTimeFormat.Constants.b()
            r7.append(r2)
            goto L75
        L89:
            r5 = r3
        L8a:
            int r2 = r0.size()
            if (r2 >= r1) goto L92
            r6 = r4
            goto L93
        L92:
            r6 = r3
        L93:
            r1 = r7
            r2 = r0
            r3 = r9
            r4 = r10
            time(r1, r2, r3, r4, r5, r6)
            boolean r9 = r7.canBuildFormatter()
            if (r9 == 0) goto La8
            r8.retainAll(r0)     // Catch: java.lang.UnsupportedOperationException -> La3
        La3:
            org.joda.time.format.DateTimeFormatter r8 = r7.toFormatter()
            return r8
        La8:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "No valid format for fields: "
            r10.append(r0)
            r10.append(r8)
            java.lang.String r8 = r10.toString()
            r9.<init>(r8)
            throw r9
        Lbf:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.String r9 = "The fields must not be null or empty"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.ISODateTimeFormat.forFields(java.util.Collection, boolean, boolean):org.joda.time.format.DateTimeFormatter");
    }

    public static DateTimeFormatter hour() {
        return Constants.hde;
    }

    public static DateTimeFormatter hourMinute() {
        return Constants.hm;
    }

    public static DateTimeFormatter hourMinuteSecond() {
        return Constants.hms;
    }

    public static DateTimeFormatter hourMinuteSecondFraction() {
        return Constants.hmsf;
    }

    public static DateTimeFormatter hourMinuteSecondMillis() {
        return Constants.hmsl;
    }

    public static DateTimeFormatter localDateOptionalTimeParser() {
        return Constants.ldotp;
    }

    public static DateTimeFormatter localDateParser() {
        return Constants.ldp;
    }

    public static DateTimeFormatter localTimeParser() {
        return Constants.ltp;
    }

    public static DateTimeFormatter ordinalDate() {
        return Constants.od;
    }

    public static DateTimeFormatter ordinalDateTime() {
        return Constants.odt;
    }

    public static DateTimeFormatter ordinalDateTimeNoMillis() {
        return Constants.odtx;
    }

    public static DateTimeFormatter tTime() {
        return Constants.tt;
    }

    public static DateTimeFormatter tTimeNoMillis() {
        return Constants.ttx;
    }

    private static void time(DateTimeFormatterBuilder dateTimeFormatterBuilder, Collection<DateTimeFieldType> collection, boolean z2, boolean z3, boolean z4, boolean z5) {
        boolean zRemove = collection.remove(DateTimeFieldType.hourOfDay());
        boolean zRemove2 = collection.remove(DateTimeFieldType.minuteOfHour());
        boolean zRemove3 = collection.remove(DateTimeFieldType.secondOfMinute());
        boolean zRemove4 = collection.remove(DateTimeFieldType.millisOfSecond());
        if (zRemove || zRemove2 || zRemove3 || zRemove4) {
            if (zRemove || zRemove2 || zRemove3 || zRemove4) {
                if (z3 && z4) {
                    throw new IllegalArgumentException("No valid ISO8601 format for fields because Date was reduced precision: " + collection);
                }
                if (z5) {
                    dateTimeFormatterBuilder.appendLiteral('T');
                }
            }
            if ((!zRemove || !zRemove2 || !zRemove3) && (!zRemove || zRemove3 || zRemove4)) {
                if (z3 && z5) {
                    throw new IllegalArgumentException("No valid ISO8601 format for fields because Time was truncated: " + collection);
                }
                if ((zRemove || ((!zRemove2 || !zRemove3) && ((!zRemove2 || zRemove4) && !zRemove3))) && z3) {
                    throw new IllegalArgumentException("No valid ISO8601 format for fields: " + collection);
                }
            }
            if (zRemove) {
                dateTimeFormatterBuilder.appendHourOfDay(2);
            } else if (zRemove2 || zRemove3 || zRemove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (z2 && zRemove && zRemove2) {
                dateTimeFormatterBuilder.appendLiteral(':');
            }
            if (zRemove2) {
                dateTimeFormatterBuilder.appendMinuteOfHour(2);
            } else if (zRemove3 || zRemove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (z2 && zRemove2 && zRemove3) {
                dateTimeFormatterBuilder.appendLiteral(':');
            }
            if (zRemove3) {
                dateTimeFormatterBuilder.appendSecondOfMinute(2);
            } else if (zRemove4) {
                dateTimeFormatterBuilder.appendLiteral('-');
            }
            if (zRemove4) {
                dateTimeFormatterBuilder.appendLiteral('.');
                dateTimeFormatterBuilder.appendMillisOfSecond(3);
            }
        }
    }

    public static DateTimeFormatter timeElementParser() {
        return Constants.tpe;
    }

    public static DateTimeFormatter timeNoMillis() {
        return Constants.tx;
    }

    public static DateTimeFormatter timeParser() {
        return Constants.tp;
    }

    public static DateTimeFormatter weekDate() {
        return Constants.wwd;
    }

    public static DateTimeFormatter weekDateTime() {
        return Constants.wdt;
    }

    public static DateTimeFormatter weekDateTimeNoMillis() {
        return Constants.wdtx;
    }

    public static DateTimeFormatter weekyear() {
        return Constants.we;
    }

    public static DateTimeFormatter weekyearWeek() {
        return Constants.ww;
    }

    public static DateTimeFormatter weekyearWeekDay() {
        return Constants.wwd;
    }

    public static DateTimeFormatter year() {
        return Constants.ye;
    }

    public static DateTimeFormatter yearMonth() {
        return Constants.ym;
    }

    public static DateTimeFormatter yearMonthDay() {
        return Constants.ymd;
    }

    public static DateTimeFormatter time() {
        return Constants.f26736t;
    }
}
