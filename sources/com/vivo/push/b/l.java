package com.vivo.push.b;

/* loaded from: classes4.dex */
public final class l extends s {

    /* renamed from: a, reason: collision with root package name */
    private int f23048a;

    /* renamed from: b, reason: collision with root package name */
    private int f23049b;

    public l() {
        super(2016);
        this.f23048a = -1;
        this.f23049b = -1;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("key_dispatch_environment", this.f23048a);
        aVar.a("key_dispatch_area", this.f23049b);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23048a = aVar.b("key_dispatch_environment", 1);
        this.f23049b = aVar.b("key_dispatch_area", 1);
    }

    public final int e() {
        return this.f23049b;
    }

    public final int d() {
        return this.f23048a;
    }
}
