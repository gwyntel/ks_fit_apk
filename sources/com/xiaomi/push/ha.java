package com.xiaomi.push;

import java.util.Date;

/* loaded from: classes4.dex */
class ha implements he {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ gz f23860a;

    ha(gz gzVar) {
        this.f23860a = gzVar;
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, int i2, Exception exc) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f23860a.f513a.format(new Date()) + " Connection closed (" + this.f23860a.f510a.hashCode() + ")");
    }

    @Override // com.xiaomi.push.he
    public void b(hb hbVar) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f23860a.f513a.format(new Date()) + " Connection reconnected (" + this.f23860a.f510a.hashCode() + ")");
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, Exception exc) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f23860a.f513a.format(new Date()) + " Reconnection failed due to an exception (" + this.f23860a.f510a.hashCode() + ")");
        exc.printStackTrace();
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar) {
        com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + this.f23860a.f513a.format(new Date()) + " Connection started (" + this.f23860a.f510a.hashCode() + ")");
    }
}
