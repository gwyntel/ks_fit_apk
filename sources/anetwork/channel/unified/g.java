package anetwork.channel.unified;

import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.HttpUrl;

/* loaded from: classes2.dex */
class g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ SessionCenter f7270a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ HttpUrl f7271b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ RequestStatistic f7272c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ HttpUrl f7273d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ boolean f7274e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ e f7275f;

    g(e eVar, SessionCenter sessionCenter, HttpUrl httpUrl, RequestStatistic requestStatistic, HttpUrl httpUrl2, boolean z2) {
        this.f7275f = eVar;
        this.f7270a = sessionCenter;
        this.f7271b = httpUrl;
        this.f7272c = requestStatistic;
        this.f7273d = httpUrl2;
        this.f7274e = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Session session = this.f7270a.get(this.f7271b, anet.channel.entity.c.f6777a, 3000L);
        this.f7272c.connWaitTime = System.currentTimeMillis() - jCurrentTimeMillis;
        this.f7272c.spdyRequestSend = session != null;
        Session sessionA = this.f7275f.a(session, this.f7270a, this.f7273d, this.f7274e);
        e eVar = this.f7275f;
        eVar.a(sessionA, eVar.f7253a.f7286a.a());
    }
}
