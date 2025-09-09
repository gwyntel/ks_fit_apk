package com.taobao.accs.data;

import android.content.Intent;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f20166a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ MsgDistributeService f20167b;

    l(MsgDistributeService msgDistributeService, Intent intent) {
        this.f20167b = msgDistributeService;
        this.f20166a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        ALog.i("MsgDistributeService", "onStartCommand send message", new Object[0]);
        ACCSManager.AccsRequest accsRequest = (ACCSManager.AccsRequest) this.f20166a.getSerializableExtra(Constants.KEY_SEND_REQDATA);
        String stringExtra = this.f20166a.getStringExtra("packageName");
        String stringExtra2 = this.f20166a.getStringExtra("appKey");
        String stringExtra3 = this.f20166a.getStringExtra(Constants.KEY_CONFIG_TAG);
        if (TextUtils.isEmpty(stringExtra3)) {
            stringExtra3 = stringExtra2;
        }
        ACCSManager.getAccsInstance(this.f20167b.getApplicationContext(), stringExtra2, stringExtra3).a(this.f20167b.getApplicationContext(), accsRequest, stringExtra, false);
    }
}
