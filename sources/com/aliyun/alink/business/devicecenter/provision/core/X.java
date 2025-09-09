package com.aliyun.alink.business.devicecenter.provision.core;

import android.net.wifi.p2p.WifiP2pManager;
import com.aliyun.alink.business.devicecenter.log.ALog;

/* loaded from: classes2.dex */
public class X implements WifiP2pManager.ActionListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Z f10551a;

    public X(Z z2) {
        this.f10551a = z2;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public void onFailure(int i2) {
        ALog.w(Z.f10553a, "exposeData(),discoverPeers fail,reason=" + i2);
        if (i2 == 0 || i2 == 1) {
            this.f10551a.i();
        }
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public void onSuccess() {
        ALog.d(Z.f10553a, "exposeData(),discoverPeers succ");
    }
}
