package com.taobao.accs.data;

import android.content.Intent;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;

/* loaded from: classes4.dex */
class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f20160a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20161b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ Intent f20162c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ g f20163d;

    i(g gVar, String str, String str2, Intent intent) {
        this.f20163d = gVar;
        this.f20160a = str;
        this.f20161b = str2;
        this.f20162c = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (g.f20156a == null || !g.f20156a.contains(this.f20160a)) {
            return;
        }
        ALog.e("MsgDistribute", "routing msg time out, try election", Constants.KEY_DATA_ID, this.f20160a, Constants.KEY_SERVICE_ID, this.f20161b);
        g.f20156a.remove(this.f20160a);
        com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "", "timeout", "pkg:" + this.f20162c.getPackage());
    }
}
