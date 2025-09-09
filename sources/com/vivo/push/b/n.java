package com.vivo.push.b;

/* loaded from: classes4.dex */
public final class n extends s {

    /* renamed from: a, reason: collision with root package name */
    private String f23051a;

    /* renamed from: b, reason: collision with root package name */
    private int f23052b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f23053c;

    public n() {
        super(7);
        this.f23052b = 0;
        this.f23053c = false;
    }

    public final void a(int i2) {
        this.f23052b = i2;
    }

    public final void b(String str) {
        this.f23051a = str;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("content", this.f23051a);
        aVar.a("log_level", this.f23052b);
        aVar.a("is_server_log", this.f23053c);
    }

    public final String d() {
        return this.f23051a;
    }

    public final int e() {
        return this.f23052b;
    }

    public final boolean f() {
        return this.f23053c;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnLogCommand";
    }

    public final void a(boolean z2) {
        this.f23053c = z2;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23051a = aVar.a("content");
        this.f23052b = aVar.b("log_level", 0);
        this.f23053c = aVar.e("is_server_log");
    }
}
