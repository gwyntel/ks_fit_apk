package com.aliyun.alink.business.devicecenter.provision.core;

import android.net.wifi.p2p.WifiP2pManager;
import com.aliyun.alink.business.devicecenter.log.ALog;

/* loaded from: classes2.dex */
public class Y implements WifiP2pManager.ActionListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Z f10552a;

    public Y(Z z2) {
        this.f10552a = z2;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public void onFailure(int i2) {
        ALog.w(Z.f10553a, "stopExposeData(),discoverPeers fail");
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public void onSuccess() {
        ALog.d(Z.f10553a, "stopExposeData(),discoverPeers succ");
    }
}
