package anet.channel.heartbeat;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.Session;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import com.umeng.analytics.pro.bc;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
class b implements IHeartbeat, Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Session f6800a;

    /* renamed from: b, reason: collision with root package name */
    private volatile long f6801b = 0;

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f6802c = false;

    /* renamed from: d, reason: collision with root package name */
    private long f6803d = 0;

    b() {
    }

    private void a(long j2) {
        try {
            this.f6801b = System.currentTimeMillis() + j2;
            ThreadPoolExecutorFactory.submitScheduledTask(this, j2 + 50, TimeUnit.MILLISECONDS);
        } catch (Exception e2) {
            ALog.e("awcn.DefaultHeartbeatImpl", "Submit heartbeat task failed.", this.f6800a.f6632p, e2, new Object[0]);
        }
    }

    @Override // anet.channel.heartbeat.IHeartbeat
    public void reSchedule() {
        this.f6801b = System.currentTimeMillis() + this.f6803d;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f6802c) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis < this.f6801b - 1000) {
            a(this.f6801b - jCurrentTimeMillis);
            return;
        }
        if (GlobalAppRuntimeInfo.isAppBackground()) {
            Session session = this.f6800a;
            ALog.e("awcn.DefaultHeartbeatImpl", "close session in background", session.f6632p, "session", session);
            this.f6800a.close(false);
        } else {
            if (ALog.isPrintLog(1)) {
                Session session2 = this.f6800a;
                ALog.d("awcn.DefaultHeartbeatImpl", "heartbeat", session2.f6632p, "session", session2);
            }
            this.f6800a.ping(true);
            a(this.f6803d);
        }
    }

    @Override // anet.channel.heartbeat.IHeartbeat
    public void start(Session session) {
        if (session == null) {
            throw new NullPointerException("session is null");
        }
        this.f6800a = session;
        long heartbeat = session.getConnStrategy().getHeartbeat();
        this.f6803d = heartbeat;
        if (heartbeat <= 0) {
            this.f6803d = 45000L;
        }
        ALog.i("awcn.DefaultHeartbeatImpl", "heartbeat start", session.f6632p, "session", session, bc.ba, Long.valueOf(this.f6803d));
        a(this.f6803d);
    }

    @Override // anet.channel.heartbeat.IHeartbeat
    public void stop() {
        Session session = this.f6800a;
        if (session == null) {
            return;
        }
        ALog.i("awcn.DefaultHeartbeatImpl", "heartbeat stop", session.f6632p, "session", session);
        this.f6802c = true;
    }
}
