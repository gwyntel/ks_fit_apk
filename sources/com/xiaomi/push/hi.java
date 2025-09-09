package com.xiaomi.push;

import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.push.service.XMPushService;
import java.io.IOException;
import java.net.Socket;

/* loaded from: classes4.dex */
public abstract class hi extends hb {

    /* renamed from: a, reason: collision with root package name */
    protected Exception f23876a;

    /* renamed from: a, reason: collision with other field name */
    protected Socket f536a;

    /* renamed from: b, reason: collision with root package name */
    protected XMPushService f23877b;

    /* renamed from: c, reason: collision with root package name */
    private int f23878c;

    /* renamed from: c, reason: collision with other field name */
    String f537c;

    /* renamed from: d, reason: collision with root package name */
    private String f23879d;

    /* renamed from: e, reason: collision with root package name */
    protected volatile long f23880e;

    /* renamed from: f, reason: collision with root package name */
    protected volatile long f23881f;

    /* renamed from: g, reason: collision with root package name */
    protected volatile long f23882g;

    /* renamed from: h, reason: collision with root package name */
    private long f23883h;

    public hi(XMPushService xMPushService, hc hcVar) {
        super(xMPushService, hcVar);
        this.f23876a = null;
        this.f537c = null;
        this.f23880e = 0L;
        this.f23881f = 0L;
        this.f23882g = 0L;
        this.f23883h = 0L;
        this.f23877b = xMPushService;
    }

    public Context a() {
        return this.f23877b;
    }

    /* renamed from: a */
    protected abstract void mo460a(boolean z2);

    @Override // com.xiaomi.push.hb
    public void b(boolean z2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long jCurrentTimeMillis = System.currentTimeMillis();
        mo460a(z2);
        com.xiaomi.push.service.p.a(this.f23877b).m789c();
        if (z2) {
            return;
        }
        this.f23877b.a(new hj(this, 13, jElapsedRealtime, jCurrentTimeMillis), 10000L);
    }

    public String c() {
        return ((hb) this).f522a;
    }

    public synchronized void e() {
        try {
            if (!m473c() && !m472b()) {
                a(0, 0, (Exception) null);
                a(((hb) this).f519a);
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("WARNING: current xmpp has connected");
        } catch (IOException e2) {
            throw new hm(e2);
        }
    }

    public void f() {
        this.f23880e = SystemClock.elapsedRealtime();
    }

    public void g() {
        this.f23881f = SystemClock.elapsedRealtime();
    }

    @Override // com.xiaomi.push.hb
    /* renamed from: a */
    public String mo468a() {
        return this.f23879d;
    }

    public void c(int i2, Exception exc) {
        bg.b();
        this.f23877b.a(new hk(this, 2, i2, exc));
    }

    protected synchronized void a(int i2, Exception exc) {
        if (b() == 2) {
            return;
        }
        a(2, i2, exc);
        ((hb) this).f522a = "";
        try {
            this.f536a.close();
        } catch (Throwable unused) {
        }
        this.f23880e = 0L;
        this.f23881f = 0L;
    }

    @Override // com.xiaomi.push.hb
    public void b(int i2, Exception exc) {
        a(i2, exc);
        if ((exc != null || i2 == 18) && this.f23882g != 0) {
            a(exc);
        }
    }

    protected void a(Exception exc) {
        if (SystemClock.elapsedRealtime() - this.f23882g < 300000) {
            if (bg.b(this.f23877b)) {
                int i2 = this.f23878c + 1;
                this.f23878c = i2;
                if (i2 >= 2) {
                    String strMo468a = mo468a();
                    com.xiaomi.channel.commonutils.logger.b.m91a("max short conn time reached, sink down current host:" + strMo468a);
                    a(strMo468a, 0L, exc);
                    this.f23878c = 0;
                    return;
                }
                return;
            }
            return;
        }
        this.f23878c = 0;
    }

    protected void a(String str, long j2, Exception exc) {
        cz czVarA = dd.a().a(hc.a(), false);
        if (czVarA != null) {
            czVarA.b(str, j2, 0L, exc);
            dd.a().m277c();
        }
    }

    @Override // com.xiaomi.push.hb
    public void a(gq[] gqVarArr) throws hm {
        throw new hm("Don't support send Blob");
    }

    private void a(hc hcVar) throws Throwable {
        a(hcVar.c(), hcVar.m474a());
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x01ef A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01ef A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0286 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x035e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r32, int r33) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 873
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hi.a(java.lang.String, int):void");
    }

    /* renamed from: a, reason: collision with other method in class */
    protected synchronized void mo480a() {
    }

    /* renamed from: a, reason: collision with other method in class */
    public Socket m479a() {
        return new Socket();
    }

    cz a(String str) {
        cz czVarA = dd.a().a(str, false);
        if (!czVarA.b()) {
            ie.a(new hl(this, str));
        }
        return czVarA;
    }
}
