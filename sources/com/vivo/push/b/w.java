package com.vivo.push.b;

/* loaded from: classes4.dex */
public final class w extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private int f23070a;

    public w() {
        super(2011);
        this.f23070a = 0;
    }

    @Override // com.vivo.push.o
    public final boolean c() {
        return true;
    }

    public final int d() {
        return this.f23070a;
    }

    @Override // com.vivo.push.o
    public final String toString() {
        return "PushModeCommand";
    }

    @Override // com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        aVar.a("com.bbk.push.ikey.MODE_TYPE", this.f23070a);
    }

    @Override // com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        this.f23070a = aVar.b("com.bbk.push.ikey.MODE_TYPE", 0);
    }
}
