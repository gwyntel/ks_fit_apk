package com.xiaomi.push;

import com.xiaomi.push.ah;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
class ch extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cf f23534a;

    ch(cf cfVar) {
        this.f23534a = cfVar;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "10054";
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.channel.commonutils.logger.b.c("exec== DbSizeControlJob");
        cr.a(this.f23534a.f228a).a(new ck(this.f23534a.c(), new WeakReference(this.f23534a.f228a)));
        this.f23534a.b("check_time");
    }
}
