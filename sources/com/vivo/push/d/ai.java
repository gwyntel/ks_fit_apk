package com.vivo.push.d;

import android.content.pm.PackageManager;

/* loaded from: classes4.dex */
final class ai extends com.vivo.push.l {
    ai(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    protected final void a(com.vivo.push.o oVar) throws PackageManager.NameNotFoundException {
        com.vivo.push.b.c cVar = (com.vivo.push.b.c) oVar;
        com.vivo.push.model.b bVarA = com.vivo.push.util.t.a(this.f23178a);
        if (bVarA == null) {
            com.vivo.push.e.a().a(cVar.h(), 1005, new Object[0]);
            return;
        }
        String strA = bVarA.a();
        if (bVarA.c()) {
            com.vivo.push.e.a().a(cVar.h(), 1004, new Object[0]);
            oVar = new com.vivo.push.b.e();
        } else {
            int iA = com.vivo.push.util.s.a(cVar);
            if (iA != 0) {
                com.vivo.push.e.a().a(cVar.h(), iA, new Object[0]);
                return;
            }
        }
        com.vivo.push.a.a.a(this.f23178a, strA, oVar);
    }
}
