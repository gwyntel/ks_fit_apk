package com.aliyun.alink.business.devicecenter.provision.core;

import android.net.wifi.p2p.WifiP2pManager;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;

/* loaded from: classes2.dex */
public class W implements WifiP2pManager.ActionListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ String f10549a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Z f10550b;

    public W(Z z2, String str) {
        this.f10550b = z2;
        this.f10549a = str;
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public void onFailure(int i2) {
        ALog.w(Z.f10553a, "changeDeviceName(" + this.f10549a + "),onFailure(), reason = " + i2);
        if (i2 == 0 || i2 == 1) {
            this.f10550b.i();
        }
    }

    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
    public void onSuccess() {
        ALog.d(Z.f10553a, "changeDeviceName() succ,name = " + this.f10549a + ",prepareName=" + this.f10550b.f10555c + ", configName=" + this.f10550b.f10556d + ", originName=" + this.f10550b.f10554b);
        if (TextUtils.isEmpty(this.f10549a)) {
            return;
        }
        if (!(this.f10550b.f10560h.get() && this.f10549a.equals(this.f10550b.f10556d)) && (this.f10550b.f10560h.get() || !this.f10549a.equals(this.f10550b.f10554b))) {
            return;
        }
        ALog.d(Z.f10553a, "change name succ,expose. ,isProvision=" + this.f10550b.f10560h);
        if (this.f10550b.f10560h.get()) {
            DCUserTrack.addTrackData(AlinkConstants.KEY_BROADCAST_P2P, String.valueOf(System.currentTimeMillis()));
        }
        this.f10550b.a();
    }
}
