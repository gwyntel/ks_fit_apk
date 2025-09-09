package com.taobao.accs.net;

import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.entity.ConnType;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class q implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ k f20251a;

    q(k kVar) {
        this.f20251a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f20251a.f20197g) {
            try {
                Session session = SessionCenter.getInstance(this.f20251a.f20199i.getAppKey()).get(this.f20251a.b((String) null), ConnType.TypeLevel.SPDY, 0L);
                if (session != null) {
                    ALog.e(this.f20251a.d(), "try session ping", new Object[0]);
                    int pingTimeout = this.f20251a.f20199i.getPingTimeout();
                    if (pingTimeout > 0) {
                        session.ping(true, pingTimeout);
                    } else {
                        session.ping(true);
                    }
                }
            } catch (Exception e2) {
                ALog.e(this.f20251a.d(), "ping error", e2, new Object[0]);
            }
        }
    }
}
