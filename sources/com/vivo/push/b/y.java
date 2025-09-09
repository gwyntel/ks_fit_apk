package com.vivo.push.b;

/* loaded from: classes4.dex */
public final class y extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private String f23073a;

    public y(String str) {
        super(2008);
        this.f23073a = str;
    }

    @Override // com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        aVar.a("package_name", this.f23073a);
    }

    @Override // com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        this.f23073a = aVar.a("package_name");
    }

    @Override // com.vivo.push.o
    public final String toString() {
        return "StopServiceCommand";
    }

    public y() {
        super(2008);
    }
}
