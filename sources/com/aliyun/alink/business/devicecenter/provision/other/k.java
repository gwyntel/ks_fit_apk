package com.aliyun.alink.business.devicecenter.provision.other;

import android.net.NetworkInfo;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.WiFiConnectiveUtils;

/* loaded from: classes2.dex */
public class k implements WiFiConnectiveUtils.IWiFiConnectivityCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10614a;

    public k(SoftAPConfigStrategy softAPConfigStrategy) {
        this.f10614a = softAPConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.utils.WiFiConnectiveUtils.IWiFiConnectivityCallback
    public void onWiFiStateChange(NetworkInfo networkInfo) {
        ALog.d(SoftAPConfigStrategy.TAG, "onWiFiStateChange() called with: networkInfo = [" + networkInfo + "]");
        try {
            if (this.f10614a.provisionHasStopped.get()) {
                ALog.d(SoftAPConfigStrategy.TAG, "provision stopped, ignore.");
            } else {
                this.f10614a.handleWiFiStateChange(networkInfo);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(SoftAPConfigStrategy.TAG, "handleWiFiStateChange exception=" + e2);
        }
    }
}
