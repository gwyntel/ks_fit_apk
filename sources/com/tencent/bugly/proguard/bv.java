package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class bv extends m implements Cloneable {

    /* renamed from: f, reason: collision with root package name */
    static ArrayList<bu> f21066f;

    /* renamed from: g, reason: collision with root package name */
    static Map<String, String> f21067g;

    /* renamed from: a, reason: collision with root package name */
    public byte f21068a = 0;

    /* renamed from: b, reason: collision with root package name */
    public String f21069b = "";

    /* renamed from: c, reason: collision with root package name */
    public String f21070c = "";

    /* renamed from: d, reason: collision with root package name */
    public ArrayList<bu> f21071d = null;

    /* renamed from: e, reason: collision with root package name */
    public Map<String, String> f21072e = null;

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f21068a, 0);
        String str = this.f21069b;
        if (str != null) {
            lVar.a(str, 1);
        }
        String str2 = this.f21070c;
        if (str2 != null) {
            lVar.a(str2, 2);
        }
        ArrayList<bu> arrayList = this.f21071d;
        if (arrayList != null) {
            lVar.a((Collection) arrayList, 3);
        }
        Map<String, String> map = this.f21072e;
        if (map != null) {
            lVar.a((Map) map, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f21068a = kVar.a(this.f21068a, 0, true);
        this.f21069b = kVar.b(1, false);
        this.f21070c = kVar.b(2, false);
        if (f21066f == null) {
            f21066f = new ArrayList<>();
            f21066f.add(new bu());
        }
        this.f21071d = (ArrayList) kVar.a((k) f21066f, 3, false);
        if (f21067g == null) {
            HashMap map = new HashMap();
            f21067g = map;
            map.put("", "");
        }
        this.f21072e = (Map) kVar.a((k) f21067g, 4, false);
    }
}
