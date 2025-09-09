package com.tencent.bugly.proguard;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class br extends m {

    /* renamed from: i, reason: collision with root package name */
    static byte[] f21030i = {0};

    /* renamed from: j, reason: collision with root package name */
    static Map<String, String> f21031j;

    /* renamed from: a, reason: collision with root package name */
    public byte f21032a = 0;

    /* renamed from: b, reason: collision with root package name */
    public int f21033b = 0;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f21034c = null;

    /* renamed from: d, reason: collision with root package name */
    public String f21035d = "";

    /* renamed from: e, reason: collision with root package name */
    public long f21036e = 0;

    /* renamed from: f, reason: collision with root package name */
    public String f21037f = "";

    /* renamed from: g, reason: collision with root package name */
    public String f21038g = "";

    /* renamed from: h, reason: collision with root package name */
    public Map<String, String> f21039h = null;

    static {
        HashMap map = new HashMap();
        f21031j = map;
        map.put("", "");
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(l lVar) throws UnsupportedEncodingException {
        lVar.a(this.f21032a, 0);
        lVar.a(this.f21033b, 1);
        byte[] bArr = this.f21034c;
        if (bArr != null) {
            lVar.a(bArr, 2);
        }
        String str = this.f21035d;
        if (str != null) {
            lVar.a(str, 3);
        }
        lVar.a(this.f21036e, 4);
        String str2 = this.f21037f;
        if (str2 != null) {
            lVar.a(str2, 5);
        }
        String str3 = this.f21038g;
        if (str3 != null) {
            lVar.a(str3, 6);
        }
        Map<String, String> map = this.f21039h;
        if (map != null) {
            lVar.a((Map) map, 7);
        }
    }

    @Override // com.tencent.bugly.proguard.m
    public final void a(k kVar) {
        this.f21032a = kVar.a(this.f21032a, 0, true);
        this.f21033b = kVar.a(this.f21033b, 1, true);
        this.f21034c = kVar.c(2, false);
        this.f21035d = kVar.b(3, false);
        this.f21036e = kVar.a(this.f21036e, 4, false);
        this.f21037f = kVar.b(5, false);
        this.f21038g = kVar.b(6, false);
        this.f21039h = (Map) kVar.a((k) f21031j, 7, false);
    }
}
