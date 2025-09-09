package com.huawei.hms.hatool;

import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class f implements g {

    /* renamed from: a, reason: collision with root package name */
    private byte[] f16352a;

    /* renamed from: b, reason: collision with root package name */
    private String f16353b;

    /* renamed from: c, reason: collision with root package name */
    private String f16354c;

    /* renamed from: d, reason: collision with root package name */
    private String f16355d;

    /* renamed from: e, reason: collision with root package name */
    private String f16356e;

    /* renamed from: f, reason: collision with root package name */
    private List<b1> f16357f;

    public f(byte[] bArr, String str, String str2, String str3, String str4, List<b1> list) {
        this.f16352a = (byte[]) bArr.clone();
        this.f16353b = str;
        this.f16354c = str2;
        this.f16356e = str3;
        this.f16355d = str4;
        this.f16357f = list;
    }

    private n0 a(Map<String, String> map) {
        return w.a(this.f16353b, this.f16352a, map);
    }

    private void b() {
        b0.c().a(new d1(this.f16357f, this.f16354c, this.f16355d, this.f16356e));
    }

    @Override // java.lang.Runnable
    public void run() {
        v.c("hmsSdk", "send data running");
        int iB = a(a()).b();
        if (iB != 200) {
            b();
            return;
        }
        v.b("hmsSdk", "events PostRequest sendevent TYPE : %s, TAG : %s, resultCode: %d ,reqID:" + this.f16355d, this.f16356e, this.f16354c, Integer.valueOf(iB));
    }

    private Map<String, String> a() {
        return k.b(this.f16354c, this.f16356e, this.f16355d);
    }
}
