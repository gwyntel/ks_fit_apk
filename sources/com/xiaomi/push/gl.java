package com.xiaomi.push;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
public class gl implements he {

    /* renamed from: a, reason: collision with root package name */
    private int f23832a;

    /* renamed from: a, reason: collision with other field name */
    hb f473a;

    /* renamed from: a, reason: collision with other field name */
    XMPushService f474a;

    /* renamed from: a, reason: collision with other field name */
    private Exception f475a;

    /* renamed from: e, reason: collision with root package name */
    private long f23836e;

    /* renamed from: f, reason: collision with root package name */
    private long f23837f;

    /* renamed from: a, reason: collision with other field name */
    private long f472a = 0;

    /* renamed from: b, reason: collision with root package name */
    private long f23833b = 0;

    /* renamed from: c, reason: collision with root package name */
    private long f23834c = 0;

    /* renamed from: d, reason: collision with root package name */
    private long f23835d = 0;

    /* renamed from: a, reason: collision with other field name */
    private String f476a = "";

    gl(XMPushService xMPushService) {
        this.f23836e = 0L;
        this.f23837f = 0L;
        this.f474a = xMPushService;
        b();
        int iMyUid = Process.myUid();
        try {
            this.f23837f = TrafficStats.getUidRxBytes(iMyUid);
            this.f23836e = TrafficStats.getUidTxBytes(iMyUid);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Failed to obtain traffic data during initialization: " + e2);
            this.f23837f = -1L;
            this.f23836e = -1L;
        }
    }

    private void b() {
        this.f23833b = 0L;
        this.f23835d = 0L;
        this.f472a = 0L;
        this.f23834c = 0L;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (bg.b(this.f474a)) {
            this.f472a = jElapsedRealtime;
        }
        if (this.f474a.m716c()) {
            this.f23834c = jElapsedRealtime;
        }
    }

    private synchronized void c() {
        com.xiaomi.channel.commonutils.logger.b.c("stat connpt = " + this.f476a + " netDuration = " + this.f23833b + " ChannelDuration = " + this.f23835d + " channelConnectedTime = " + this.f23834c);
        gf gfVar = new gf();
        gfVar.f451a = (byte) 0;
        gfVar.a(ge.CHANNEL_ONLINE_RATE.a());
        gfVar.a(this.f476a);
        gfVar.d((int) (System.currentTimeMillis() / 1000));
        gfVar.b((int) (this.f23833b / 1000));
        gfVar.c((int) (this.f23835d / 1000));
        gm.m436a().a(gfVar);
        b();
    }

    Exception a() {
        return this.f475a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m435a() {
        try {
            XMPushService xMPushService = this.f474a;
            if (xMPushService == null) {
                return;
            }
            String strM204a = bg.m204a((Context) xMPushService);
            boolean zC = bg.c(this.f474a);
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            long j2 = this.f472a;
            if (j2 > 0) {
                this.f23833b += jElapsedRealtime - j2;
                this.f472a = 0L;
            }
            long j3 = this.f23834c;
            if (j3 != 0) {
                this.f23835d += jElapsedRealtime - j3;
                this.f23834c = 0L;
            }
            if (zC) {
                if ((!TextUtils.equals(this.f476a, strM204a) && this.f23833b > 30000) || this.f23833b > 5400000) {
                    c();
                }
                this.f476a = strM204a;
                if (this.f472a == 0) {
                    this.f472a = jElapsedRealtime;
                }
                if (this.f474a.m716c()) {
                    this.f23834c = jElapsedRealtime;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.xiaomi.push.he
    public void b(hb hbVar) {
        m435a();
        this.f23834c = SystemClock.elapsedRealtime();
        go.a(0, ge.CONN_SUCCESS.a(), hbVar.mo468a(), hbVar.a());
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar) {
        this.f23832a = 0;
        this.f475a = null;
        this.f473a = hbVar;
        this.f476a = bg.m204a((Context) this.f474a);
        go.a(0, ge.CONN_SUCCESS.a());
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, int i2, Exception exc) {
        long uidRxBytes;
        long uidTxBytes;
        if (this.f23832a == 0 && this.f475a == null) {
            this.f23832a = i2;
            this.f475a = exc;
            go.b(hbVar.mo468a(), exc);
        }
        if (i2 == 22 && this.f23834c != 0) {
            long jM466a = hbVar.m466a() - this.f23834c;
            if (jM466a < 0) {
                jM466a = 0;
            }
            this.f23835d += jM466a + (hh.b() / 2);
            this.f23834c = 0L;
        }
        m435a();
        int iMyUid = Process.myUid();
        try {
            uidRxBytes = TrafficStats.getUidRxBytes(iMyUid);
            uidTxBytes = TrafficStats.getUidTxBytes(iMyUid);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Failed to obtain traffic data: " + e2);
            uidRxBytes = -1;
            uidTxBytes = -1L;
        }
        com.xiaomi.channel.commonutils.logger.b.c("Stats rx=" + (uidRxBytes - this.f23837f) + ", tx=" + (uidTxBytes - this.f23836e));
        this.f23837f = uidRxBytes;
        this.f23836e = uidTxBytes;
    }

    @Override // com.xiaomi.push.he
    public void a(hb hbVar, Exception exc) {
        go.a(0, ge.CHANNEL_CON_FAIL.a(), 1, hbVar.mo468a(), bg.c(this.f474a) ? 1 : 0);
        m435a();
    }
}
