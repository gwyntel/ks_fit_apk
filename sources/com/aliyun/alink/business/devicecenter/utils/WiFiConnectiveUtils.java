package com.aliyun.alink.business.devicecenter.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Parcelable;
import com.aliyun.alink.business.devicecenter.log.ALog;

/* loaded from: classes2.dex */
public class WiFiConnectiveUtils {

    /* renamed from: a, reason: collision with root package name */
    public Context f10664a;

    /* renamed from: b, reason: collision with root package name */
    public BroadcastReceiver f10665b = null;

    /* renamed from: c, reason: collision with root package name */
    public IWiFiConnectivityCallback f10666c = null;

    public interface IWiFiConnectivityCallback {
        void onWiFiStateChange(NetworkInfo networkInfo);
    }

    public WiFiConnectiveUtils(Context context) {
        this.f10664a = context;
    }

    public final void b() {
        if (this.f10664a == null) {
            ALog.w("WiFiConnectiveUtils", "registerReceiver failed, context=null.");
            return;
        }
        c();
        ALog.d("WiFiConnectiveUtils", "registerReceiver() called.");
        a();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.f10664a.registerReceiver(this.f10665b, intentFilter);
    }

    public final void c() {
        Context context;
        try {
            BroadcastReceiver broadcastReceiver = this.f10665b;
            if (broadcastReceiver == null || (context = this.f10664a) == null) {
                return;
            }
            context.unregisterReceiver(broadcastReceiver);
            this.f10665b = null;
        } catch (Exception e2) {
            ALog.w("WiFiConnectiveUtils", "unregisterAPBroadcast exception=" + e2);
        }
    }

    public void startListener(IWiFiConnectivityCallback iWiFiConnectivityCallback) {
        ALog.d("WiFiConnectiveUtils", "addListener listener=" + iWiFiConnectivityCallback + ", registerReceiver called.");
        this.f10666c = iWiFiConnectivityCallback;
        b();
    }

    public void stopListener() {
        ALog.d("WiFiConnectiveUtils", "stopListener unregisterReceiver");
        this.f10666c = null;
        c();
    }

    public final void a() {
        this.f10665b = new BroadcastReceiver() { // from class: com.aliyun.alink.business.devicecenter.utils.WiFiConnectiveUtils.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (context == null || intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                ALog.d("WiFiConnectiveUtils", "connectBroadCastRecv, onReceive()" + action + ", listener=" + WiFiConnectiveUtils.this.f10666c);
                if (!"android.net.wifi.STATE_CHANGE".equals(action)) {
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                        int intExtra = intent.getIntExtra("wifi_state", -1);
                        ALog.i("WiFiConnectiveUtils", "wifiState:" + intExtra);
                        if (intExtra == 1 || intExtra == 0 || intExtra == 4) {
                            ALog.e("WiFiConnectiveUtils", "wifiState is disabled or disabling, this will cause provision fail.");
                            return;
                        }
                        return;
                    }
                    return;
                }
                Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
                if (!(parcelableExtra instanceof NetworkInfo)) {
                    ALog.e("WiFiConnectiveUtils", "parcelableExtra network info is null.");
                    return;
                }
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                if (networkInfo.getType() == 1) {
                    if (WiFiConnectiveUtils.this.f10666c != null) {
                        WiFiConnectiveUtils.this.f10666c.onWiFiStateChange(networkInfo);
                    }
                } else {
                    ALog.w("WiFiConnectiveUtils", "ignore, networkInfo.getType()=" + networkInfo.getType());
                }
            }
        };
    }
}
