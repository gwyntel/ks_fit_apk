package anet.channel.session;

import anet.channel.statist.SessionStatistic;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TnetSpdySession f6920a;

    h(TnetSpdySession tnetSpdySession) {
        this.f6920a = tnetSpdySession;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f6920a.f6899y) {
            TnetSpdySession tnetSpdySession = this.f6920a;
            ALog.e("awcn.TnetSpdySession", "send msg time out!", tnetSpdySession.f6632p, "pingUnRcv:", Boolean.valueOf(tnetSpdySession.f6899y));
            try {
                this.f6920a.handleCallbacks(2048, null);
                SessionStatistic sessionStatistic = this.f6920a.f6633q;
                if (sessionStatistic != null) {
                    sessionStatistic.closeReason = "ping time out";
                }
                ConnEvent connEvent = new ConnEvent();
                connEvent.isSuccess = false;
                connEvent.isAccs = this.f6920a.I;
                StrategyCenter.getInstance().notifyConnEvent(this.f6920a.f6620d, this.f6920a.f6627k, connEvent);
                this.f6920a.close(true);
            } catch (Exception unused) {
            }
        }
    }
}
