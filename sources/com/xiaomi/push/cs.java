package com.xiaomi.push;

import com.xiaomi.push.ah;
import com.xiaomi.push.cr;

/* loaded from: classes4.dex */
class cs extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cr f23552a;

    cs(cr crVar) {
        this.f23552a = crVar;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "100957";
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f23552a.f241a) {
            try {
                if (this.f23552a.f241a.size() > 0) {
                    if (this.f23552a.f241a.size() > 1) {
                        cr crVar = this.f23552a;
                        crVar.a(crVar.f241a);
                    } else {
                        cr crVar2 = this.f23552a;
                        crVar2.b((cr.a) crVar2.f241a.get(0));
                    }
                    this.f23552a.f241a.clear();
                    System.gc();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
