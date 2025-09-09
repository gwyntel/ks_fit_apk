package com.xiaomi.push;

import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class cg extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cf f23533a;

    cg(cf cfVar) {
        this.f23533a = cfVar;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "10052";
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.channel.commonutils.logger.b.c("exec== mUploadJob");
        if (this.f23533a.f231a != null) {
            this.f23533a.f231a.a(this.f23533a.f228a);
            this.f23533a.b("upload_time");
        }
    }
}
