package com.vivo.push.b;

/* loaded from: classes4.dex */
public final class j extends s {

    /* renamed from: a, reason: collision with root package name */
    private int f23046a;

    /* renamed from: b, reason: collision with root package name */
    private int f23047b;

    public j() {
        super(12);
        this.f23046a = -1;
        this.f23047b = -1;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("OnChangePushStatus.EXTRA_REQ_SERVICE_STATUS", this.f23046a);
        aVar.a("OnChangePushStatus.EXTRA_REQ_RECEIVER_STATUS", this.f23047b);
    }

    public final int d() {
        return this.f23046a;
    }

    public final int e() {
        return this.f23047b;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnChangePushStatusCommand";
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23046a = aVar.b("OnChangePushStatus.EXTRA_REQ_SERVICE_STATUS", this.f23046a);
        this.f23047b = aVar.b("OnChangePushStatus.EXTRA_REQ_RECEIVER_STATUS", this.f23047b);
    }
}
