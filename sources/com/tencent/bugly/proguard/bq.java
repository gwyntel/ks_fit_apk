package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class bq extends m {

    /* renamed from: y, reason: collision with root package name */
    static byte[] f21004y = {0};

    /* renamed from: z, reason: collision with root package name */
    static Map<String, String> f21005z;

    /* renamed from: a, reason: collision with root package name */
    public int f21006a = 0;

    /* renamed from: b, reason: collision with root package name */
    public String f21007b = "";

    /* renamed from: c, reason: collision with root package name */
    public String f21008c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f21009d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f21010e = "";

    /* renamed from: f, reason: collision with root package name */
    public String f21011f = "";

    /* renamed from: g, reason: collision with root package name */
    public int f21012g = 0;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f21013h = null;

    /* renamed from: i, reason: collision with root package name */
    public String f21014i = "";

    /* renamed from: j, reason: collision with root package name */
    public String f21015j = "";

    /* renamed from: k, reason: collision with root package name */
    public Map<String, String> f21016k = null;

    /* renamed from: l, reason: collision with root package name */
    public String f21017l = "";

    /* renamed from: m, reason: collision with root package name */
    public long f21018m = 0;

    /* renamed from: n, reason: collision with root package name */
    public String f21019n = "";

    /* renamed from: o, reason: collision with root package name */
    public String f21020o = "";

    /* renamed from: p, reason: collision with root package name */
    public String f21021p = "";

    /* renamed from: q, reason: collision with root package name */
    public long f21022q = 0;

    /* renamed from: r, reason: collision with root package name */
    public String f21023r = "";

    /* renamed from: s, reason: collision with root package name */
    public String f21024s = "";

    /* renamed from: t, reason: collision with root package name */
    public String f21025t = "";

    /* renamed from: u, reason: collision with root package name */
    public String f21026u = "";

    /* renamed from: v, reason: collision with root package name */
    public String f21027v = "";

    /* renamed from: w, reason: collision with root package name */
    public String f21028w = "";

    /* renamed from: x, reason: collision with root package name */
    public String f21029x = "";

    static {
        HashMap map = new HashMap();
        f21005z = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f21006a, 0);
        lVar.a(this.f21007b, 1);
        lVar.a(this.f21008c, 2);
        lVar.a(this.f21009d, 3);
        String str = this.f21010e;
        if (str != null) {
            lVar.a(str, 4);
        }
        lVar.a(this.f21011f, 5);
        lVar.a(this.f21012g, 6);
        lVar.a(this.f21013h, 7);
        String str2 = this.f21014i;
        if (str2 != null) {
            lVar.a(str2, 8);
        }
        String str3 = this.f21015j;
        if (str3 != null) {
            lVar.a(str3, 9);
        }
        Map<String, String> map = this.f21016k;
        if (map != null) {
            lVar.a((Map) map, 10);
        }
        String str4 = this.f21017l;
        if (str4 != null) {
            lVar.a(str4, 11);
        }
        lVar.a(this.f21018m, 12);
        String str5 = this.f21019n;
        if (str5 != null) {
            lVar.a(str5, 13);
        }
        String str6 = this.f21020o;
        if (str6 != null) {
            lVar.a(str6, 14);
        }
        String str7 = this.f21021p;
        if (str7 != null) {
            lVar.a(str7, 15);
        }
        lVar.a(this.f21022q, 16);
        String str8 = this.f21023r;
        if (str8 != null) {
            lVar.a(str8, 17);
        }
        String str9 = this.f21024s;
        if (str9 != null) {
            lVar.a(str9, 18);
        }
        String str10 = this.f21025t;
        if (str10 != null) {
            lVar.a(str10, 19);
        }
        String str11 = this.f21026u;
        if (str11 != null) {
            lVar.a(str11, 20);
        }
        String str12 = this.f21027v;
        if (str12 != null) {
            lVar.a(str12, 21);
        }
        String str13 = this.f21028w;
        if (str13 != null) {
            lVar.a(str13, 22);
        }
        String str14 = this.f21029x;
        if (str14 != null) {
            lVar.a(str14, 23);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f21006a = kVar.a(this.f21006a, 0, true);
        this.f21007b = kVar.b(1, true);
        this.f21008c = kVar.b(2, true);
        this.f21009d = kVar.b(3, true);
        this.f21010e = kVar.b(4, false);
        this.f21011f = kVar.b(5, true);
        this.f21012g = kVar.a(this.f21012g, 6, true);
        this.f21013h = kVar.c(7, true);
        this.f21014i = kVar.b(8, false);
        this.f21015j = kVar.b(9, false);
        this.f21016k = (Map) kVar.a((k) f21005z, 10, false);
        this.f21017l = kVar.b(11, false);
        this.f21018m = kVar.a(this.f21018m, 12, false);
        this.f21019n = kVar.b(13, false);
        this.f21020o = kVar.b(14, false);
        this.f21021p = kVar.b(15, false);
        this.f21022q = kVar.a(this.f21022q, 16, false);
        this.f21023r = kVar.b(17, false);
        this.f21024s = kVar.b(18, false);
        this.f21025t = kVar.b(19, false);
        this.f21026u = kVar.b(20, false);
        this.f21027v = kVar.b(21, false);
        this.f21028w = kVar.b(22, false);
        this.f21029x = kVar.b(23, false);
    }
}
