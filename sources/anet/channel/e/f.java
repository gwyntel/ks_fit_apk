package anet.channel.e;

import anet.channel.Session;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.entity.EventCb;
import anet.channel.statist.Http3DetectStat;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;

/* loaded from: classes2.dex */
class f implements EventCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ IConnStrategy f6766a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f6767b;

    f(e eVar, IConnStrategy iConnStrategy) {
        this.f6767b = eVar;
        this.f6766a = iConnStrategy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v2 */
    @Override // anet.channel.entity.EventCb
    public void onEvent(Session session, int i2, anet.channel.entity.b bVar) {
        ?? r02 = i2 != 1 ? 0 : 1;
        a.f6751a.a(NetworkStatusHelper.getUniqueId(this.f6767b.f6765b), r02);
        session.close(false);
        Http3DetectStat http3DetectStat = new Http3DetectStat(a.f6752b, this.f6766a);
        http3DetectStat.ret = r02;
        AppMonitor.getInstance().commitStat(http3DetectStat);
    }
}
