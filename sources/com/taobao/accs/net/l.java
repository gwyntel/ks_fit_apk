package com.taobao.accs.net;

import com.taobao.accs.ACCSManager;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.p;
import java.util.UUID;

/* loaded from: classes4.dex */
class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ k f20241a;

    l(k kVar) {
        this.f20241a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ALog.d(this.f20241a.d(), "sendAccsHeartbeatMessage", new Object[0]);
        try {
            ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest(null, null, new p.a().a("dataType", "pingreq").a("timeInterval", Long.valueOf(this.f20241a.f20230p)).a().toString().getBytes("utf-8"), UUID.randomUUID().toString());
            accsRequest.setTarget("accs-iot");
            accsRequest.setTargetServiceName("sal");
            k kVar = this.f20241a;
            this.f20241a.a(Message.buildRequest(kVar.f20194d, kVar.b((String) null), this.f20241a.d(), this.f20241a.f20199i.getStoreId(), this.f20241a.f20194d.getPackageName(), Constants.TARGET_SERVICE, accsRequest, true), true);
        } catch (Exception e2) {
            ALog.e(this.f20241a.d(), "send accs heartbeat message", e2, new Object[0]);
        }
    }
}
