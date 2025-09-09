package com.taobao.accs.net;

import anet.channel.session.TnetSpdySession;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;

/* loaded from: classes4.dex */
class r implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f20252a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ byte[] f20253b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ TnetSpdySession f20254c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ k f20255d;

    r(k kVar, int i2, byte[] bArr, TnetSpdySession tnetSpdySession) {
        this.f20255d = kVar;
        this.f20252a = i2;
        this.f20253b = bArr;
        this.f20254c = tnetSpdySession;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f20252a != 200) {
            ALog.e(this.f20255d.d(), "drop frame len:" + this.f20253b.length + " frameType" + this.f20252a, new Object[0]);
            return;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.f20255d.f20195e.a(this.f20253b, this.f20254c.getHost());
            com.taobao.accs.ut.a.d dVarG = this.f20255d.f20195e.g();
            if (dVarG != null) {
                dVarG.f20311c = String.valueOf(jCurrentTimeMillis);
                dVarG.f20315g = this.f20255d.f20193c == 0 ? "service" : "inapp";
                dVarG.a();
            }
        } catch (Throwable th) {
            ALog.e(this.f20255d.d(), "onDataReceive ", th, new Object[0]);
            UTMini.getInstance().commitEvent(66001, "DATA_RECEIVE", UtilityImpl.a(th));
        }
    }
}
