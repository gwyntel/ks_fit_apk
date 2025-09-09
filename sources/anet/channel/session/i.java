package anet.channel.session;

import anet.channel.IAuth;
import anet.channel.heartbeat.IHeartbeat;
import anet.channel.statist.SessionStatistic;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
class i implements IAuth.AuthCallback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TnetSpdySession f6921a;

    i(TnetSpdySession tnetSpdySession) {
        this.f6921a = tnetSpdySession;
    }

    @Override // anet.channel.IAuth.AuthCallback
    public void onAuthFail(int i2, String str) {
        this.f6921a.notifyStatus(5, null);
        SessionStatistic sessionStatistic = this.f6921a.f6633q;
        if (sessionStatistic != null) {
            sessionStatistic.closeReason = "Accs_Auth_Fail:" + i2;
            this.f6921a.f6633q.errorCode = (long) i2;
        }
        this.f6921a.close();
    }

    @Override // anet.channel.IAuth.AuthCallback
    public void onAuthSuccess() {
        this.f6921a.notifyStatus(4, null);
        this.f6921a.f6900z = System.currentTimeMillis();
        TnetSpdySession tnetSpdySession = this.f6921a;
        IHeartbeat iHeartbeat = tnetSpdySession.D;
        if (iHeartbeat != null) {
            iHeartbeat.start(tnetSpdySession);
        }
        TnetSpdySession tnetSpdySession2 = this.f6921a;
        SessionStatistic sessionStatistic = tnetSpdySession2.f6633q;
        sessionStatistic.ret = 1;
        ALog.d("awcn.TnetSpdySession", "spdyOnStreamResponse", tnetSpdySession2.f6632p, "authTime", Long.valueOf(sessionStatistic.authTime));
        TnetSpdySession tnetSpdySession3 = this.f6921a;
        if (tnetSpdySession3.A > 0) {
            tnetSpdySession3.f6633q.authTime = System.currentTimeMillis() - this.f6921a.A;
        }
    }
}
