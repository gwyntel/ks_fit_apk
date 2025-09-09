package com.meizu.cloud.pushsdk.e.d;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    private static final Pattern f19412a = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f19413b = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");

    /* renamed from: c, reason: collision with root package name */
    private final String f19414c;

    /* renamed from: d, reason: collision with root package name */
    private final String f19415d;

    /* renamed from: e, reason: collision with root package name */
    private final String f19416e;

    /* renamed from: f, reason: collision with root package name */
    private final String f19417f;

    private g(String str, String str2, String str3, String str4) {
        this.f19414c = str;
        this.f19415d = str2;
        this.f19416e = str3;
        this.f19417f = str4;
    }

    public static g a(String str) {
        Matcher matcher = f19412a.matcher(str);
        if (!matcher.lookingAt()) {
            return null;
        }
        String strGroup = matcher.group(1);
        Locale locale = Locale.US;
        String lowerCase = strGroup.toLowerCase(locale);
        String lowerCase2 = matcher.group(2).toLowerCase(locale);
        Matcher matcher2 = f19413b.matcher(str);
        String str2 = null;
        for (int iEnd = matcher.end(); iEnd < str.length(); iEnd = matcher2.end()) {
            matcher2.region(iEnd, str.length());
            if (!matcher2.lookingAt()) {
                return null;
            }
            if ("charset".equalsIgnoreCase(matcher2.group(1))) {
                String strGroup2 = matcher2.group(2) != null ? matcher2.group(2) : matcher2.group(3);
                if (str2 != null && !strGroup2.equalsIgnoreCase(str2)) {
                    throw new IllegalArgumentException("Multiple different charsets: " + str);
                }
                str2 = strGroup2;
            }
        }
        return new g(str, lowerCase, lowerCase2, str2);
    }

    public String b() {
        return this.f19415d;
    }

    public boolean equals(Object obj) {
        return (obj instanceof g) && ((g) obj).f19414c.equals(this.f19414c);
    }

    public int hashCode() {
        return this.f19414c.hashCode();
    }

    public String toString() {
        return this.f19414c;
    }

    public Charset a() {
        String str = this.f19417f;
        if (str != null) {
            return Charset.forName(str);
        }
        return null;
    }
}
