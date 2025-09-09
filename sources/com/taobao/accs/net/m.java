package com.taobao.accs.net;

import android.content.Intent;
import anet.channel.ISessionListener;
import com.taobao.accs.base.AccsConnectStateListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import java.util.Iterator;

/* loaded from: classes4.dex */
class m implements ISessionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ k f20242a;

    m(k kVar) {
        this.f20242a = kVar;
    }

    @Override // anet.channel.ISessionListener
    public void onConnectionChanged(Intent intent) {
        if (intent != null) {
            boolean booleanExtra = intent.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
            String stringExtra = intent.getStringExtra("host");
            ALog.e(this.f20242a.d(), "onConnectionChanged", "currentHost", "https://" + this.f20242a.f20199i.getInappHost(), "changeHost", stringExtra, "state", Boolean.valueOf(booleanExtra));
            if (("https://" + this.f20242a.f20199i.getInappHost()).equals(stringExtra)) {
                g.a(GlobalClientInfo.getContext()).a();
                int intExtra = intent.getIntExtra("errorCode", -1);
                String stringExtra2 = intent.getStringExtra(Constants.KEY_ERROR_DETAIL);
                boolean booleanExtra2 = intent.getBooleanExtra(Constants.KEY_TYPE_INAPP, false);
                boolean booleanExtra3 = intent.getBooleanExtra(Constants.KEY_CENTER_HOST, false);
                TaoBaseService.ConnectInfo connectInfo = booleanExtra ? new TaoBaseService.ConnectInfo(stringExtra, booleanExtra2, booleanExtra3) : new TaoBaseService.ConnectInfo(stringExtra, booleanExtra2, booleanExtra3, intExtra, stringExtra2);
                connectInfo.connected = booleanExtra;
                Iterator<AccsConnectStateListener> it = this.f20242a.n().iterator();
                while (it.hasNext()) {
                    this.f20242a.f20232r.post(new n(this, connectInfo, it.next()));
                }
            }
        }
    }
}
