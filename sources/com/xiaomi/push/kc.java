package com.xiaomi.push;

import com.xiaomi.push.kf;

/* loaded from: classes4.dex */
public class kc {

    /* renamed from: a, reason: collision with root package name */
    private final kj f24367a;

    /* renamed from: a, reason: collision with other field name */
    private final ks f912a;

    public kc() {
        this(new kf.a());
    }

    public void a(jy jyVar, byte[] bArr) {
        try {
            this.f912a.a(bArr);
            jyVar.a(this.f24367a);
        } finally {
            this.f24367a.k();
        }
    }

    public kc(kl klVar) {
        ks ksVar = new ks();
        this.f912a = ksVar;
        this.f24367a = klVar.a(ksVar);
    }
}
