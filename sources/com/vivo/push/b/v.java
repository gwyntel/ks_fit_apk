package com.vivo.push.b;

/* loaded from: classes4.dex */
public abstract class v extends s {

    /* renamed from: a, reason: collision with root package name */
    private String f23068a;

    /* renamed from: b, reason: collision with root package name */
    private long f23069b;

    public v(int i2) {
        super(i2);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("OnVerifyCallBackCommand.EXTRA_SECURITY_CONTENT", this.f23068a);
        aVar.a("notify_id", this.f23069b);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    protected void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23068a = aVar.a("OnVerifyCallBackCommand.EXTRA_SECURITY_CONTENT");
        this.f23069b = aVar.b("notify_id", -1L);
    }

    public final long f() {
        return this.f23069b;
    }

    public final String i() {
        return this.f23068a;
    }
}
