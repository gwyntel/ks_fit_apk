package com.taobao.accs.utl;

import android.app.Application;
import com.taobao.accs.utl.l;
import java.util.Iterator;

/* loaded from: classes4.dex */
class n implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ l f20410a;

    n(l lVar) {
        this.f20410a = lVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Iterator it = l.f20399d.iterator();
        while (it.hasNext()) {
            l.b bVar = (l.b) it.next();
            if (bVar != null) {
                Application unused = l.f20400e;
                bVar.b();
            }
        }
    }
}
