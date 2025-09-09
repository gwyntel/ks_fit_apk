package com.vivo.push.b;

/* loaded from: classes4.dex */
public final class u extends v {

    /* renamed from: a, reason: collision with root package name */
    private long f23066a;

    /* renamed from: b, reason: collision with root package name */
    private int f23067b;

    public u() {
        super(20);
        this.f23066a = -1L;
    }

    @Override // com.vivo.push.b.v, com.vivo.push.b.s, com.vivo.push.o
    protected final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("undo_msg_v1", this.f23066a);
        aVar.a("undo_msg_type_v1", this.f23067b);
    }

    public final long d() {
        return this.f23066a;
    }

    public final String e() {
        long j2 = this.f23066a;
        if (j2 != -1) {
            return String.valueOf(j2);
        }
        return null;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnUndoMsgCommand";
    }

    @Override // com.vivo.push.b.v, com.vivo.push.b.s, com.vivo.push.o
    protected final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f23066a = aVar.b("undo_msg_v1", this.f23066a);
        this.f23067b = aVar.b("undo_msg_type_v1", 0);
    }
}
