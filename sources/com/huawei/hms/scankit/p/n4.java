package com.huawei.hms.scankit.p;

import com.huawei.hms.ml.scan.HmsScan;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class n4 extends t6 {

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f17577b = Pattern.compile("geo:([\\s\\-0-9.]+),([\\s\\-0-9.]+)(?:[,?].*)?", 2);

    @Override // com.huawei.hms.scankit.p.t6
    public HmsScan b(s6 s6Var) {
        String strA = t6.a(s6Var);
        Matcher matcher = f17577b.matcher(strA);
        if (!matcher.matches()) {
            return null;
        }
        try {
            return new HmsScan(s6Var.k(), t6.a(s6Var.c()), strA, HmsScan.LOCATION_COORDINATE_FORM, s6Var.i(), t6.a(s6Var.j()), null, new z6(new HmsScan.LocationCoordinate(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2)))));
        } catch (NumberFormatException unused) {
            return null;
        }
    }
}
