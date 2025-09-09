package com.taobao.accs.data;

import anet.channel.appmonitor.AppMonitor;
import com.taobao.accs.common.Constants;
import com.taobao.accs.ut.monitor.AssembleMonitor;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f20136a;

    c(a aVar) {
        this.f20136a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f20136a) {
            try {
                if (this.f20136a.f20132f == 0) {
                    ALog.e("AssembleMessage", "timeout", Constants.KEY_DATA_ID, this.f20136a.f20128b);
                    this.f20136a.f20132f = 1;
                    this.f20136a.f20134h.clear();
                    AppMonitor.getInstance().commitStat(new AssembleMonitor(this.f20136a.f20128b, String.valueOf(this.f20136a.f20132f)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
