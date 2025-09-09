package com.huawei.hms.hatool;

import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.UUID;

/* loaded from: classes4.dex */
public class i1 {

    /* renamed from: a, reason: collision with root package name */
    private String f16389a;

    /* renamed from: b, reason: collision with root package name */
    private String f16390b;

    /* renamed from: c, reason: collision with root package name */
    private String f16391c;

    /* renamed from: d, reason: collision with root package name */
    private String f16392d;

    /* renamed from: e, reason: collision with root package name */
    private long f16393e;

    public i1(String str, String str2, String str3, String str4, long j2) {
        this.f16389a = str;
        this.f16390b = str2;
        this.f16391c = str3;
        this.f16392d = str4;
        this.f16393e = j2;
    }

    public void a() {
        v.c("StreamEventHandler", "Begin to handle stream events...");
        b1 b1Var = new b1();
        b1Var.b(this.f16391c);
        b1Var.d(this.f16390b);
        b1Var.a(this.f16392d);
        b1Var.c(String.valueOf(this.f16393e));
        if ("oper".equals(this.f16390b) && z.i(this.f16389a, "oper")) {
            p0 p0VarA = y.a().a(this.f16389a, this.f16393e);
            String strA = p0VarA.a();
            Boolean boolValueOf = Boolean.valueOf(p0VarA.b());
            b1Var.f(strA);
            b1Var.e(String.valueOf(boolValueOf));
        }
        String strReplace = UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
        ArrayList arrayList = new ArrayList();
        arrayList.add(b1Var);
        new l0(this.f16389a, this.f16390b, q0.g(), arrayList, strReplace).a();
    }
}
