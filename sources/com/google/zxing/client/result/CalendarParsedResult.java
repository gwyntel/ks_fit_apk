package com.google.zxing.client.result;

import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class CalendarParsedResult extends ParsedResult {
    private final String[] attendees;
    private final String description;
    private final long end;
    private final boolean endAllDay;
    private final double latitude;
    private final String location;
    private final double longitude;
    private final String organizer;
    private final long start;
    private final boolean startAllDay;
    private final String summary;
    private static final Pattern RFC2445_DURATION = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
    private static final long[] RFC2445_DURATION_FIELD_UNITS = {604800000, 86400000, 3600000, 60000, 1000};
    private static final Pattern DATE_TIME = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");

    public CalendarParsedResult(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr, String str7, double d2, double d3) {
        super(ParsedResultType.CALENDAR);
        this.summary = str;
        try {
            long date = parseDate(str2);
            this.start = date;
            if (str3 == null) {
                long durationMS = parseDurationMS(str4);
                this.end = durationMS < 0 ? -1L : date + durationMS;
            } else {
                try {
                    this.end = parseDate(str3);
                } catch (ParseException e2) {
                    throw new IllegalArgumentException(e2.toString());
                }
            }
            boolean z2 = false;
            this.startAllDay = str2.length() == 8;
            if (str3 != null && str3.length() == 8) {
                z2 = true;
            }
            this.endAllDay = z2;
            this.location = str5;
            this.organizer = str6;
            this.attendees = strArr;
            this.description = str7;
            this.latitude = d2;
            this.longitude = d3;
        } catch (ParseException e3) {
            throw new IllegalArgumentException(e3.toString());
        }
    }

    private static String format(boolean z2, long j2) {
        if (j2 < 0) {
            return null;
        }
        return (z2 ? DateFormat.getDateInstance(2) : DateFormat.getDateTimeInstance(2, 2)).format(Long.valueOf(j2));
    }

    private static long parseDate(String str) throws ParseException {
        if (!DATE_TIME.matcher(str).matches()) {
            throw new ParseException(str, 0);
        }
        if (str.length() == 8) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CalendarUtils.yyyyMMdd, Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.parse(str).getTime();
        }
        if (str.length() != 16 || str.charAt(15) != 'Z') {
            return parseDateTimeString(str);
        }
        long dateTimeString = parseDateTimeString(str.substring(0, 15));
        long j2 = dateTimeString + r5.get(15);
        new GregorianCalendar().setTime(new Date(j2));
        return j2 + r5.get(16);
    }

    private static long parseDateTimeString(String str) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH).parse(str).getTime();
    }

    private static long parseDurationMS(CharSequence charSequence) {
        if (charSequence == null) {
            return -1L;
        }
        Matcher matcher = RFC2445_DURATION.matcher(charSequence);
        if (!matcher.matches()) {
            return -1L;
        }
        long j2 = 0;
        int i2 = 0;
        while (true) {
            long[] jArr = RFC2445_DURATION_FIELD_UNITS;
            if (i2 >= jArr.length) {
                return j2;
            }
            int i3 = i2 + 1;
            if (matcher.group(i3) != null) {
                j2 += jArr[i2] * Integer.parseInt(r5);
            }
            i2 = i3;
        }
    }

    public String[] getAttendees() {
        return this.attendees;
    }

    public String getDescription() {
        return this.description;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(100);
        ParsedResult.maybeAppend(this.summary, sb);
        ParsedResult.maybeAppend(format(this.startAllDay, this.start), sb);
        ParsedResult.maybeAppend(format(this.endAllDay, this.end), sb);
        ParsedResult.maybeAppend(this.location, sb);
        ParsedResult.maybeAppend(this.organizer, sb);
        ParsedResult.maybeAppend(this.attendees, sb);
        ParsedResult.maybeAppend(this.description, sb);
        return sb.toString();
    }

    @Deprecated
    public Date getEnd() {
        if (this.end < 0) {
            return null;
        }
        return new Date(this.end);
    }

    public long getEndTimestamp() {
        return this.end;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getLocation() {
        return this.location;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getOrganizer() {
        return this.organizer;
    }

    @Deprecated
    public Date getStart() {
        return new Date(this.start);
    }

    public long getStartTimestamp() {
        return this.start;
    }

    public String getSummary() {
        return this.summary;
    }

    public boolean isEndAllDay() {
        return this.endAllDay;
    }

    public boolean isStartAllDay() {
        return this.startAllDay;
    }
}
