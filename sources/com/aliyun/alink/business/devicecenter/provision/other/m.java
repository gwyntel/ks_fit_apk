package com.aliyun.alink.business.devicecenter.provision.other;

import android.net.NetworkInfo;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftApState;
import com.aliyun.alink.business.devicecenter.utils.PermissionCheckerUtils;
import com.aliyun.alink.business.devicecenter.utils.WiFiUtils;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AtomicInteger f10616a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10617b;

    public m(SoftAPConfigStrategy softAPConfigStrategy, AtomicInteger atomicInteger) {
        this.f10617b = softAPConfigStrategy;
        this.f10616a = atomicInteger;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f10617b.provisionHasStopped.get() || this.f10617b.mConfigParams == null) {
            return;
        }
        this.f10616a.incrementAndGet();
        if (this.f10617b.hasNotifiedUser2ConnectDevAp.get() && PermissionCheckerUtils.hasLocationAccess(this.f10617b.mContext) && this.f10617b.softApState.ordinal() < SoftApState.CONNECTED_DEV_AP.ordinal() && (this.f10617b.mCurrentWiFiState == NetworkInfo.State.CONNECTED || WiFiUtils.isWiFiConnected(this.f10617b.mContext))) {
            String wifiSsid = AlinkHelper.getWifiSsid(this.f10617b.mContext);
            if (!TextUtils.isEmpty(wifiSsid)) {
                if (this.f10617b.isDeviceApConnected("\"" + wifiSsid + "\"")) {
                    this.f10617b.mDeviceApSsid = wifiSsid;
                    ALog.i(SoftAPConfigStrategy.TAG, "call onWiFiConnected in startPatch, maybe get ssid failed when wifi connected.");
                    this.f10617b.onWiFiConnected();
                    return;
                }
            }
        }
        if (this.f10616a.get() == 1 && !this.f10617b.android10plus() && this.f10617b.needReconnectSoftAp() && !TextUtils.isEmpty(this.f10617b.mDeviceApBssid)) {
            this.f10617b.stopDiscover();
            SoftAPConfigStrategy softAPConfigStrategy = this.f10617b;
            softAPConfigStrategy.connectDeviceAp(softAPConfigStrategy.mDeviceApSsid, this.f10617b.mDeviceApBssid, null);
            return;
        }
        if (this.f10616a.get() == 2 && !this.f10617b.android10plus() && this.f10617b.needReconnectSoftAp()) {
            this.f10617b.needReconnectSoftApAB.set(false);
            this.f10617b.stopDiscover();
            SoftAPConfigStrategy softAPConfigStrategy2 = this.f10617b;
            softAPConfigStrategy2.notifyConnectApByUser(softAPConfigStrategy2.mConfigParams.productKey, null);
            return;
        }
        if (!this.f10617b.needRecoverWifi() || this.f10617b.recvSwitchAPAckTime.get() <= 0 || this.f10617b.recvSwitchAPAckTime.get() + 10000 > System.currentTimeMillis()) {
            return;
        }
        if (WiFiUtils.isNetworkAvaiable(this.f10617b.mContext) && this.f10617b.isConnectedWiFiValid()) {
            return;
        }
        this.f10617b.notifyUser2RecoverWiFi(true);
    }
}
