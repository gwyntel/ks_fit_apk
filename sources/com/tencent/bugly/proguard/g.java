package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class g extends m {

    /* renamed from: k, reason: collision with root package name */
    static byte[] f21087k = null;

    /* renamed from: l, reason: collision with root package name */
    static Map<String, String> f21088l = null;

    /* renamed from: m, reason: collision with root package name */
    static final /* synthetic */ boolean f21089m = true;

    /* renamed from: g, reason: collision with root package name */
    public byte[] f21096g;

    /* renamed from: i, reason: collision with root package name */
    public Map<String, String> f21098i;

    /* renamed from: j, reason: collision with root package name */
    public Map<String, String> f21099j;

    /* renamed from: a, reason: collision with root package name */
    public short f21090a = 0;

    /* renamed from: b, reason: collision with root package name */
    public byte f21091b = 0;

    /* renamed from: c, reason: collision with root package name */
    public int f21092c = 0;

    /* renamed from: d, reason: collision with root package name */
    public int f21093d = 0;

    /* renamed from: e, reason: collision with root package name */
    public String f21094e = null;

    /* renamed from: f, reason: collision with root package name */
    public String f21095f = null;

    /* renamed from: h, reason: collision with root package name */
    public int f21097h = 0;

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f21090a, 1);
        lVar.a(this.f21091b, 2);
        lVar.a(this.f21092c, 3);
        lVar.a(this.f21093d, 4);
        lVar.a(this.f21094e, 5);
        lVar.a(this.f21095f, 6);
        lVar.a(this.f21096g, 7);
        lVar.a(this.f21097h, 8);
        lVar.a((Map) this.f21098i, 9);
        lVar.a((Map) this.f21099j, 10);
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            if (f21089m) {
                return null;
            }
            throw new AssertionError();
        }
    }

    public final boolean equals(Object obj) {
        g gVar = (g) obj;
        return n.a(1, (int) gVar.f21090a) && n.a(1, (int) gVar.f21091b) && n.a(1, gVar.f21092c) && n.a(1, gVar.f21093d) && n.a((Object) 1, (Object) gVar.f21094e) && n.a((Object) 1, (Object) gVar.f21095f) && n.a((Object) 1, (Object) gVar.f21096g) && n.a(1, gVar.f21097h) && n.a((Object) 1, (Object) gVar.f21098i) && n.a((Object) 1, (Object) gVar.f21099j);
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        try {
            this.f21090a = kVar.a(this.f21090a, 1, true);
            this.f21091b = kVar.a(this.f21091b, 2, true);
            this.f21092c = kVar.a(this.f21092c, 3, true);
            this.f21093d = kVar.a(this.f21093d, 4, true);
            this.f21094e = kVar.b(5, true);
            this.f21095f = kVar.b(6, true);
            if (f21087k == null) {
                f21087k = new byte[]{0};
            }
            this.f21096g = kVar.c(7, true);
            this.f21097h = kVar.a(this.f21097h, 8, true);
            if (f21088l == null) {
                HashMap map = new HashMap();
                f21088l = map;
                map.put("", "");
            }
            this.f21098i = (Map) kVar.a((k) f21088l, 9, true);
            if (f21088l == null) {
                HashMap map2 = new HashMap();
                f21088l = map2;
                map2.put("", "");
            }
            this.f21099j = (Map) kVar.a((k) f21088l, 10, true);
        } catch (Exception e2) {
            e2.printStackTrace();
            System.out.println("RequestPacket decode error " + f.a(this.f21096g));
            throw new RuntimeException(e2);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.f21090a, "iVersion");
        iVar.a(this.f21091b, "cPacketType");
        iVar.a(this.f21092c, "iMessageType");
        iVar.a(this.f21093d, "iRequestId");
        iVar.a(this.f21094e, "sServantName");
        iVar.a(this.f21095f, "sFuncName");
        iVar.a(this.f21096g, "sBuffer");
        iVar.a(this.f21097h, "iTimeout");
        iVar.a((Map) this.f21098i, com.umeng.analytics.pro.f.X);
        iVar.a((Map) this.f21099j, "status");
    }
}
