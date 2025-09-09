package com.vivo.push.d;

/* loaded from: classes4.dex */
final class g extends z {
    g(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    protected final void a(com.vivo.push.o oVar) {
        com.vivo.push.util.p.d("OnClearCacheTask", "delete push info " + this.f23178a.getPackageName());
        com.vivo.push.util.y.b(this.f23178a).a();
    }
}
