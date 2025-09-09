package anet.channel.heartbeat;

import anet.channel.Session;
import anet.channel.thread.ThreadPoolExecutorFactory;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class c implements IHeartbeat, Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Session f6804a = null;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f6805b = false;

    /* renamed from: c, reason: collision with root package name */
    private volatile long f6806c = System.currentTimeMillis();

    @Override // anet.channel.heartbeat.IHeartbeat
    public void reSchedule() {
        this.f6806c = System.currentTimeMillis() + 45000;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f6805b) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis < this.f6806c - 1000) {
            ThreadPoolExecutorFactory.submitScheduledTask(this, this.f6806c - jCurrentTimeMillis, TimeUnit.MILLISECONDS);
        } else {
            this.f6804a.close(false);
        }
    }

    @Override // anet.channel.heartbeat.IHeartbeat
    public void start(Session session) {
        if (session == null) {
            throw new NullPointerException("session is null");
        }
        this.f6804a = session;
        this.f6806c = System.currentTimeMillis() + 45000;
        ThreadPoolExecutorFactory.submitScheduledTask(this, 45000L, TimeUnit.MILLISECONDS);
    }

    @Override // anet.channel.heartbeat.IHeartbeat
    public void stop() {
        this.f6805b = true;
    }
}
