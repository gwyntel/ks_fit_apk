package com.tencent.bugly.proguard;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class bt extends m implements Cloneable {

    /* renamed from: m, reason: collision with root package name */
    static bs f21042m = new bs();

    /* renamed from: n, reason: collision with root package name */
    static Map<String, String> f21043n = null;

    /* renamed from: o, reason: collision with root package name */
    static final /* synthetic */ boolean f21044o = true;

    /* renamed from: a, reason: collision with root package name */
    public boolean f21045a = true;

    /* renamed from: b, reason: collision with root package name */
    public boolean f21046b = true;

    /* renamed from: c, reason: collision with root package name */
    public boolean f21047c = true;

    /* renamed from: d, reason: collision with root package name */
    public String f21048d = "";

    /* renamed from: e, reason: collision with root package name */
    public String f21049e = "";

    /* renamed from: f, reason: collision with root package name */
    public bs f21050f = null;

    /* renamed from: g, reason: collision with root package name */
    public Map<String, String> f21051g = null;

    /* renamed from: h, reason: collision with root package name */
    public long f21052h = 0;

    /* renamed from: i, reason: collision with root package name */
    public String f21053i = "";

    /* renamed from: j, reason: collision with root package name */
    public String f21054j = "";

    /* renamed from: k, reason: collision with root package name */
    public int f21055k = 0;

    /* renamed from: l, reason: collision with root package name */
    public int f21056l = 0;

    static {
        HashMap map = new HashMap();
        f21043n = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f21045a, 0);
        lVar.a(this.f21046b, 1);
        lVar.a(this.f21047c, 2);
        String str = this.f21048d;
        if (str != null) {
            lVar.a(str, 3);
        }
        String str2 = this.f21049e;
        if (str2 != null) {
            lVar.a(str2, 4);
        }
        bs bsVar = this.f21050f;
        if (bsVar != null) {
            lVar.a((m) bsVar, 5);
        }
        Map<String, String> map = this.f21051g;
        if (map != null) {
            lVar.a((Map) map, 6);
        }
        lVar.a(this.f21052h, 7);
        String str3 = this.f21053i;
        if (str3 != null) {
            lVar.a(str3, 8);
        }
        String str4 = this.f21054j;
        if (str4 != null) {
            lVar.a(str4, 9);
        }
        lVar.a(this.f21055k, 10);
        lVar.a(this.f21056l, 11);
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f21044o) {
                return null;
            }
            throw new AssertionError();
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        bt btVar = (bt) obj;
        return n.a(this.f21045a, btVar.f21045a) && n.a(this.f21046b, btVar.f21046b) && n.a(this.f21047c, btVar.f21047c) && n.a(this.f21048d, btVar.f21048d) && n.a(this.f21049e, btVar.f21049e) && n.a(this.f21050f, btVar.f21050f) && n.a(this.f21051g, btVar.f21051g) && n.a(this.f21052h, btVar.f21052h) && n.a(this.f21053i, btVar.f21053i) && n.a(this.f21054j, btVar.f21054j) && n.a(this.f21055k, btVar.f21055k) && n.a(this.f21056l, btVar.f21056l);
    }

    public final int hashCode() throws Exception {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f21045a = kVar.a(0, true);
        this.f21046b = kVar.a(1, true);
        this.f21047c = kVar.a(2, true);
        this.f21048d = kVar.b(3, false);
        this.f21049e = kVar.b(4, false);
        this.f21050f = (bs) kVar.a((m) f21042m, 5, false);
        this.f21051g = (Map) kVar.a((k) f21043n, 6, false);
        this.f21052h = kVar.a(this.f21052h, 7, false);
        this.f21053i = kVar.b(8, false);
        this.f21054j = kVar.b(9, false);
        this.f21055k = kVar.a(this.f21055k, 10, false);
        this.f21056l = kVar.a(this.f21056l, 11, false);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.f21045a, "enable");
        iVar.a(this.f21046b, "enableUserInfo");
        iVar.a(this.f21047c, "enableQuery");
        iVar.a(this.f21048d, "url");
        iVar.a(this.f21049e, "expUrl");
        iVar.a((m) this.f21050f, AlinkConstants.KEY_SECURITY);
        iVar.a((Map) this.f21051g, "valueMap");
        iVar.a(this.f21052h, "strategylastUpdateTime");
        iVar.a(this.f21053i, "httpsUrl");
        iVar.a(this.f21054j, "httpsExpUrl");
        iVar.a(this.f21055k, "eventRecordCount");
        iVar.a(this.f21056l, "eventTimeInterval");
    }
}
