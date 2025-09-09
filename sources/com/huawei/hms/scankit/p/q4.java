package com.huawei.hms.scankit.p;

import android.text.TextUtils;
import com.huawei.hms.ml.scan.HmsScan;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class q4 extends t6 {

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f17692b = Pattern.compile("market://[\\s\\S]*", 2);

    @Override // com.huawei.hms.scankit.p.t6
    public HmsScan b(s6 s6Var) {
        String strA = t6.a(s6Var);
        if (TextUtils.isEmpty(strA) || !f17692b.matcher(strA).matches()) {
            return null;
        }
        return new HmsScan(s6Var.k(), t6.a(s6Var.c()), strA, HmsScan.URL_FORM, s6Var.i(), t6.a(s6Var.j()), null, new z6(new HmsScan.LinkUrl("", "")));
    }
}
