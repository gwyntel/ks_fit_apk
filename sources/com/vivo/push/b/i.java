package com.vivo.push.b;

/* loaded from: classes4.dex */
public final class i extends s {

    /* renamed from: a, reason: collision with root package name */
    private String f23043a;

    /* renamed from: b, reason: collision with root package name */
    private String f23044b;

    /* renamed from: c, reason: collision with root package name */
    private String f23045c;

    public i(int i2) {
        super(i2);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("app_id", this.f23043a);
        aVar.a("client_id", this.f23044b);
        aVar.a("client_token", this.f23045c);
    }

    public final String d() {
        return this.f23043a;
    }

    public final String e() {
        return this.f23045c;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnBindCommand";
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23043a = aVar.a("app_id");
        this.f23044b = aVar.a("client_id");
        this.f23045c = aVar.a("client_token");
    }
}
