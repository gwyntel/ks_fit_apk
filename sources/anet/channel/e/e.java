package anet.channel.e;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.session.TnetSpdySession;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import java.util.List;

/* loaded from: classes2.dex */
final class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ List f6764a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ NetworkStatusHelper.NetworkStatus f6765b;

    e(List list, NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f6764a = list;
        this.f6765b = networkStatus;
    }

    @Override // java.lang.Runnable
    public void run() {
        IConnStrategy iConnStrategy = (IConnStrategy) this.f6764a.get(0);
        TnetSpdySession tnetSpdySession = new TnetSpdySession(GlobalAppRuntimeInfo.getContext(), new anet.channel.entity.a("https://" + a.f6752b, "Http3Detect" + a.f6758h.getAndIncrement(), a.b(iConnStrategy)));
        tnetSpdySession.registerEventcb(257, new f(this, iConnStrategy));
        tnetSpdySession.f6633q.isCommitted = true;
        tnetSpdySession.connect();
    }
}
