package com.vivo.push.b;

/* loaded from: classes4.dex */
public class s extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private String f23062a;

    /* renamed from: b, reason: collision with root package name */
    private int f23063b;

    public s(int i2) {
        super(i2);
        this.f23062a = null;
        this.f23063b = 0;
    }

    @Override // com.vivo.push.o
    protected void c(com.vivo.push.a aVar) {
        aVar.a("req_id", this.f23062a);
        aVar.a("status_msg_code", this.f23063b);
    }

    @Override // com.vivo.push.o
    protected void d(com.vivo.push.a aVar) {
        this.f23062a = aVar.a("req_id");
        this.f23063b = aVar.b("status_msg_code", this.f23063b);
    }

    public final String g() {
        return this.f23062a;
    }

    public final int h() {
        return this.f23063b;
    }

    @Override // com.vivo.push.o
    public String toString() {
        return "OnReceiveCommand";
    }
}
