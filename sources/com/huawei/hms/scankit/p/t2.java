package com.huawei.hms.scankit.p;

import android.text.TextUtils;
import com.huawei.hms.ml.scan.HmsScan;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class t2 extends t6 {

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f17809b = Pattern.compile("(?:MATMSG:TO:|mailto:|SMTP:)([\\s\\S]+)", 2);

    /* renamed from: c, reason: collision with root package name */
    private static final Pattern f17810c = Pattern.compile("mailto:([\\s\\S]+)\\?subject=([\\s\\S]+)&body=([\\s\\S]+)", 2);

    /* renamed from: d, reason: collision with root package name */
    private static final Pattern f17811d = Pattern.compile("MATMSG:TO:([\\s\\S]+);SUB:([\\s\\S]+);BODY:([\\s\\S]+)", 2);

    /* renamed from: e, reason: collision with root package name */
    private static final Pattern f17812e = Pattern.compile("SMTP:([\\s\\S]+):([\\s\\S]+):([\\s\\S]+)", 2);

    static String c(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    @Override // com.huawei.hms.scankit.p.t6
    public HmsScan b(s6 s6Var) {
        Matcher matcher;
        Matcher matcher2;
        Matcher matcher3;
        String strGroup;
        String str;
        String strGroup2;
        String strA = t6.a(s6Var);
        if (TextUtils.isEmpty(strA) || !f17809b.matcher(strA).matches()) {
            return null;
        }
        try {
            matcher = f17810c.matcher(strA);
            matcher2 = f17811d.matcher(strA);
            matcher3 = f17812e.matcher(strA);
        } catch (RuntimeException | Exception unused) {
        }
        if (matcher.matches()) {
            String strGroup3 = matcher.group(1);
            strGroup = matcher.group(2);
            strGroup2 = matcher.group(3);
            str = strGroup3;
        } else {
            if (!matcher2.matches()) {
                if (matcher3.matches()) {
                    String strGroup4 = matcher3.group(1);
                    strGroup = matcher3.group(2);
                    str = strGroup4;
                    strGroup2 = matcher3.group(3);
                }
                return null;
            }
            String strGroup5 = matcher2.group(1);
            String strGroup6 = matcher2.group(2);
            String strGroup7 = matcher2.group(3);
            str = strGroup5;
            strGroup = strGroup6;
            strGroup2 = strGroup7;
        }
        return new HmsScan(s6Var.k(), t6.a(s6Var.c()), str, HmsScan.EMAIL_CONTENT_FORM, s6Var.i(), t6.a(s6Var.j()), null, new z6(new HmsScan.EmailContent(str, c(strGroup), c(strGroup2), HmsScan.EmailContent.OTHER_USE_TYPE)));
    }
}
