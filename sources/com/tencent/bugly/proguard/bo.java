package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class bo extends m {
    static ArrayList<bn> A;
    static Map<String, String> B;
    static Map<String, String> C;

    /* renamed from: v, reason: collision with root package name */
    static Map<String, String> f20976v;

    /* renamed from: w, reason: collision with root package name */
    static bm f20977w;

    /* renamed from: x, reason: collision with root package name */
    static bl f20978x;

    /* renamed from: y, reason: collision with root package name */
    static ArrayList<bl> f20979y;

    /* renamed from: z, reason: collision with root package name */
    static ArrayList<bl> f20980z;

    /* renamed from: a, reason: collision with root package name */
    public String f20981a = "";

    /* renamed from: b, reason: collision with root package name */
    public long f20982b = 0;

    /* renamed from: c, reason: collision with root package name */
    public String f20983c = "";

    /* renamed from: d, reason: collision with root package name */
    public String f20984d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f20985e = "";

    /* renamed from: f, reason: collision with root package name */
    public String f20986f = "";

    /* renamed from: g, reason: collision with root package name */
    public String f20987g = "";

    /* renamed from: h, reason: collision with root package name */
    public Map<String, String> f20988h = null;

    /* renamed from: i, reason: collision with root package name */
    public String f20989i = "";

    /* renamed from: j, reason: collision with root package name */
    public bm f20990j = null;

    /* renamed from: k, reason: collision with root package name */
    public int f20991k = 0;

    /* renamed from: l, reason: collision with root package name */
    public String f20992l = "";

    /* renamed from: m, reason: collision with root package name */
    public String f20993m = "";

    /* renamed from: n, reason: collision with root package name */
    public bl f20994n = null;

    /* renamed from: o, reason: collision with root package name */
    public ArrayList<bl> f20995o = null;

    /* renamed from: p, reason: collision with root package name */
    public ArrayList<bl> f20996p = null;

    /* renamed from: q, reason: collision with root package name */
    public ArrayList<bn> f20997q = null;

    /* renamed from: r, reason: collision with root package name */
    public Map<String, String> f20998r = null;

    /* renamed from: s, reason: collision with root package name */
    public Map<String, String> f20999s = null;

    /* renamed from: t, reason: collision with root package name */
    public String f21000t = "";

    /* renamed from: u, reason: collision with root package name */
    public boolean f21001u = true;

    static {
        HashMap map = new HashMap();
        f20976v = map;
        map.put("", "");
        f20977w = new bm();
        f20978x = new bl();
        f20979y = new ArrayList<>();
        f20979y.add(new bl());
        f20980z = new ArrayList<>();
        f20980z.add(new bl());
        A = new ArrayList<>();
        A.add(new bn());
        HashMap map2 = new HashMap();
        B = map2;
        map2.put("", "");
        HashMap map3 = new HashMap();
        C = map3;
        map3.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f20981a, 0);
        lVar.a(this.f20982b, 1);
        lVar.a(this.f20983c, 2);
        String str = this.f20984d;
        if (str != null) {
            lVar.a(str, 3);
        }
        String str2 = this.f20985e;
        if (str2 != null) {
            lVar.a(str2, 4);
        }
        String str3 = this.f20986f;
        if (str3 != null) {
            lVar.a(str3, 5);
        }
        String str4 = this.f20987g;
        if (str4 != null) {
            lVar.a(str4, 6);
        }
        Map<String, String> map = this.f20988h;
        if (map != null) {
            lVar.a((Map) map, 7);
        }
        String str5 = this.f20989i;
        if (str5 != null) {
            lVar.a(str5, 8);
        }
        bm bmVar = this.f20990j;
        if (bmVar != null) {
            lVar.a((m) bmVar, 9);
        }
        lVar.a(this.f20991k, 10);
        String str6 = this.f20992l;
        if (str6 != null) {
            lVar.a(str6, 11);
        }
        String str7 = this.f20993m;
        if (str7 != null) {
            lVar.a(str7, 12);
        }
        bl blVar = this.f20994n;
        if (blVar != null) {
            lVar.a((m) blVar, 13);
        }
        ArrayList<bl> arrayList = this.f20995o;
        if (arrayList != null) {
            lVar.a((Collection) arrayList, 14);
        }
        ArrayList<bl> arrayList2 = this.f20996p;
        if (arrayList2 != null) {
            lVar.a((Collection) arrayList2, 15);
        }
        ArrayList<bn> arrayList3 = this.f20997q;
        if (arrayList3 != null) {
            lVar.a((Collection) arrayList3, 16);
        }
        Map<String, String> map2 = this.f20998r;
        if (map2 != null) {
            lVar.a((Map) map2, 17);
        }
        Map<String, String> map3 = this.f20999s;
        if (map3 != null) {
            lVar.a((Map) map3, 18);
        }
        String str8 = this.f21000t;
        if (str8 != null) {
            lVar.a(str8, 19);
        }
        lVar.a(this.f21001u, 20);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f20981a = kVar.b(0, true);
        this.f20982b = kVar.a(this.f20982b, 1, true);
        this.f20983c = kVar.b(2, true);
        this.f20984d = kVar.b(3, false);
        this.f20985e = kVar.b(4, false);
        this.f20986f = kVar.b(5, false);
        this.f20987g = kVar.b(6, false);
        this.f20988h = (Map) kVar.a((k) f20976v, 7, false);
        this.f20989i = kVar.b(8, false);
        this.f20990j = (bm) kVar.a((m) f20977w, 9, false);
        this.f20991k = kVar.a(this.f20991k, 10, false);
        this.f20992l = kVar.b(11, false);
        this.f20993m = kVar.b(12, false);
        this.f20994n = (bl) kVar.a((m) f20978x, 13, false);
        this.f20995o = (ArrayList) kVar.a((k) f20979y, 14, false);
        this.f20996p = (ArrayList) kVar.a((k) f20980z, 15, false);
        this.f20997q = (ArrayList) kVar.a((k) A, 16, false);
        this.f20998r = (Map) kVar.a((k) B, 17, false);
        this.f20999s = (Map) kVar.a((k) C, 18, false);
        this.f21000t = kVar.b(19, false);
        this.f21001u = kVar.a(20, false);
    }
}
