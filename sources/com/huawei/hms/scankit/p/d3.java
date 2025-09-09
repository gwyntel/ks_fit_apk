package com.huawei.hms.scankit.p;

import com.huawei.hms.ml.scan.HmsScan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
final class d3 {

    /* renamed from: a, reason: collision with root package name */
    private static final Pattern f17104a = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})(\\d{2})Z");

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f17105b = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})(\\d{2})");

    /* renamed from: c, reason: collision with root package name */
    private static final Pattern f17106c = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})");

    /* renamed from: d, reason: collision with root package name */
    private static final Pattern f17107d = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})\\d{6}Z");

    static void a(String str, HmsScan.EventTime eventTime) {
        Matcher matcher = f17104a.matcher(str);
        Matcher matcher2 = f17105b.matcher(str);
        Matcher matcher3 = f17106c.matcher(str);
        Matcher matcher4 = f17107d.matcher(str);
        try {
            if (matcher.matches()) {
                a(eventTime, Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
                b(eventTime, Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
                eventTime.isUTCTime = true;
                eventTime.originalValue = str;
            } else if (matcher2.matches()) {
                a(eventTime, Integer.parseInt(matcher2.group(1)), Integer.parseInt(matcher2.group(2)), Integer.parseInt(matcher2.group(3)));
                b(eventTime, Integer.parseInt(matcher2.group(4)), Integer.parseInt(matcher2.group(5)), Integer.parseInt(matcher2.group(6)));
                eventTime.originalValue = str;
            } else if (matcher3.matches()) {
                a(eventTime, Integer.parseInt(matcher3.group(1)), Integer.parseInt(matcher3.group(2)), Integer.parseInt(matcher3.group(3)));
                eventTime.originalValue = str;
            } else if (matcher4.matches()) {
                a(eventTime, Integer.parseInt(matcher4.group(1)), Integer.parseInt(matcher4.group(2)), Integer.parseInt(matcher4.group(3)));
            }
        } catch (NullPointerException unused) {
            o4.b("exception", "NullPointerException");
        }
    }

    private static void b(HmsScan.EventTime eventTime, int i2, int i3, int i4) {
        eventTime.hours = i2;
        eventTime.minutes = i3;
        eventTime.seconds = i4;
    }

    private static void a(HmsScan.EventTime eventTime, int i2, int i3, int i4) {
        eventTime.year = i2;
        eventTime.month = i3;
        eventTime.day = i4;
    }
}
