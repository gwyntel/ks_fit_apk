package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class bu extends m {

    /* renamed from: i, reason: collision with root package name */
    static Map<String, String> f21057i;

    /* renamed from: a, reason: collision with root package name */
    public long f21058a = 0;

    /* renamed from: b, reason: collision with root package name */
    public byte f21059b = 0;

    /* renamed from: c, reason: collision with root package name */
    public String f21060c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f21061d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f21062e = "";

    /* renamed from: f, reason: collision with root package name */
    public Map<String, String> f21063f = null;

    /* renamed from: g, reason: collision with root package name */
    public String f21064g = "";

    /* renamed from: h, reason: collision with root package name */
    public boolean f21065h = true;

    static {
        HashMap map = new HashMap();
        f21057i = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f21058a, 0);
        lVar.a(this.f21059b, 1);
        String str = this.f21060c;
        if (str != null) {
            lVar.a(str, 2);
        }
        String str2 = this.f21061d;
        if (str2 != null) {
            lVar.a(str2, 3);
        }
        String str3 = this.f21062e;
        if (str3 != null) {
            lVar.a(str3, 4);
        }
        Map<String, String> map = this.f21063f;
        if (map != null) {
            lVar.a((Map) map, 5);
        }
        String str4 = this.f21064g;
        if (str4 != null) {
            lVar.a(str4, 6);
        }
        lVar.a(this.f21065h, 7);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f21058a = kVar.a(this.f21058a, 0, true);
        this.f21059b = kVar.a(this.f21059b, 1, true);
        this.f21060c = kVar.b(2, false);
        this.f21061d = kVar.b(3, false);
        this.f21062e = kVar.b(4, false);
        this.f21063f = (Map) kVar.a((k) f21057i, 5, false);
        this.f21064g = kVar.b(6, false);
        this.f21065h = kVar.a(7, false);
    }
}
