package anet.channel;

import anet.channel.entity.EventCb;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
class g implements EventCb {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Session f6793a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ SessionRequest f6794b;

    g(SessionRequest sessionRequest, Session session) {
        this.f6794b = sessionRequest;
        this.f6793a = session;
    }

    @Override // anet.channel.entity.EventCb
    public void onEvent(Session session, int i2, anet.channel.entity.b bVar) {
        ALog.d("awcn.SessionRequest", "Receive session event", null, "eventType", Integer.valueOf(i2));
        ConnEvent connEvent = new ConnEvent();
        if (i2 == 512) {
            connEvent.isSuccess = true;
        }
        SessionInfo sessionInfo = this.f6794b.f6657c;
        if (sessionInfo != null) {
            connEvent.isAccs = sessionInfo.isAccs;
        }
        StrategyCenter.getInstance().notifyConnEvent(this.f6793a.getRealHost(), this.f6793a.getConnStrategy(), connEvent);
    }
}
