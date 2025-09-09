package anet.channel.session;

import anet.channel.RequestCb;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;

/* loaded from: classes2.dex */
class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Request f6915a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ RequestCb f6916b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ RequestStatistic f6917c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ d f6918d;

    f(d dVar, Request request, RequestCb requestCb, RequestStatistic requestStatistic) {
        this.f6918d = dVar;
        this.f6915a = request;
        this.f6916b = requestCb;
        this.f6917c = requestStatistic;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f6915a.f6850a.sendBeforeTime = System.currentTimeMillis() - this.f6915a.f6850a.reqStart;
        b.a(this.f6915a, new g(this));
    }
}
