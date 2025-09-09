package com.aliyun.iot.aep.sdk.framework.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Parcelable;
import com.aliyun.iot.aep.sdk.log.ALog;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class NetworkConnectiveManager {

    /* renamed from: a, reason: collision with root package name */
    private Context f11802a;

    /* renamed from: b, reason: collision with root package name */
    private BroadcastReceiver f11803b;

    /* renamed from: c, reason: collision with root package name */
    private Set<INetworkChangeListener> f11804c;

    /* renamed from: d, reason: collision with root package name */
    private ConnectivityManager.NetworkCallback f11805d;

    /* renamed from: e, reason: collision with root package name */
    private AtomicBoolean f11806e;

    public interface INetworkChangeListener {
        void onNetworkStateChange(NetworkInfo networkInfo, Network network);
    }

    static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final NetworkConnectiveManager f11809a = new NetworkConnectiveManager();
    }

    private void b() {
        ALog.d("NetworkConnectiveManager", "initBR() called");
        this.f11803b = new BroadcastReceiver() { // from class: com.aliyun.iot.aep.sdk.framework.network.NetworkConnectiveManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (context == null || intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                ALog.d("NetworkConnectiveManager", "connectBroadCastRecv, onReceive()" + action);
                if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                    Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
                    if (!(parcelableExtra instanceof NetworkInfo)) {
                        ALog.e("NetworkConnectiveManager", "parcelableExtra network info is null.");
                        return;
                    }
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    ALog.i("NetworkConnectiveManager", "type:" + networkInfo.getType() + ", name:" + networkInfo.getTypeName() + ", subType=" + networkInfo.getSubtype() + ", subTypeName=" + networkInfo.getSubtypeName() + ", is connected=" + networkInfo.isConnected() + ", isAvailable=" + networkInfo.isAvailable());
                    NetworkConnectiveManager.this.a(networkInfo, null);
                }
            }
        };
    }

    private void c() {
        ALog.d("NetworkConnectiveManager", "initRN() called version=" + Build.VERSION.SDK_INT);
        ConnectivityManager connectivityManagerE = e();
        if (connectivityManagerE != null) {
            connectivityManagerE.requestNetwork(new NetworkRequest.Builder().build(), this.f11805d);
        }
    }

    private void d() {
        ALog.d("NetworkConnectiveManager", "deinitRN() called");
        try {
            ConnectivityManager connectivityManagerE = e();
            if (connectivityManagerE != null) {
                connectivityManagerE.unregisterNetworkCallback(this.f11805d);
            }
        } catch (Exception e2) {
            ALog.d("NetworkConnectiveManager", "deinitRN exception=" + e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ConnectivityManager e() {
        Context context = this.f11802a;
        if (context != null) {
            return (ConnectivityManager) context.getSystemService("connectivity");
        }
        return null;
    }

    private void f() {
        if (this.f11802a == null) {
            ALog.w("NetworkConnectiveManager", "registerReceiver failed, context=null.");
            return;
        }
        ALog.d("NetworkConnectiveManager", "registerReceiver() called.");
        b();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.f11802a.registerReceiver(this.f11803b, intentFilter);
    }

    private void g() {
        Context context;
        try {
            BroadcastReceiver broadcastReceiver = this.f11803b;
            if (broadcastReceiver == null || (context = this.f11802a) == null) {
                return;
            }
            context.unregisterReceiver(broadcastReceiver);
            this.f11803b = null;
        } catch (Exception e2) {
            ALog.w("NetworkConnectiveManager", "unregisterAPBroadcast exception=" + e2);
        }
    }

    public static NetworkConnectiveManager getInstance() {
        return a.f11809a;
    }

    private void h() {
        ALog.d("NetworkConnectiveManager", "clearListener() called");
        Set<INetworkChangeListener> set = this.f11804c;
        if (set != null) {
            set.clear();
        }
    }

    public void deinitNetworkConnectiveManager() {
        ALog.d("NetworkConnectiveManager", "deinitNetworkConnectiveManager() called");
        g();
        d();
        h();
        this.f11806e.set(false);
    }

    public void initNetworkConnectiveManager(Context context) {
        ALog.d("NetworkConnectiveManager", "initNetworkConnectiveManager() called with: context = [" + context + "]");
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        if (this.f11806e.get()) {
            ALog.d("NetworkConnectiveManager", "initNetworkConnectiveManager has inited.");
        }
        this.f11806e.set(true);
        this.f11802a = context.getApplicationContext();
        if (!a()) {
            f();
            return;
        }
        try {
            c();
        } catch (Exception e2) {
            ALog.w("NetworkConnectiveManager", "initNetworkConnectiveManager exception = " + e2);
            d();
            f();
        }
    }

    public void registerConnectiveListener(INetworkChangeListener iNetworkChangeListener) {
        ALog.d("NetworkConnectiveManager", "registerConnectiveListener() called with: listener = [" + iNetworkChangeListener + "]");
        Set<INetworkChangeListener> set = this.f11804c;
        if (set != null) {
            set.add(iNetworkChangeListener);
        }
    }

    public void unregisterConnectiveListener(INetworkChangeListener iNetworkChangeListener) {
        ALog.d("NetworkConnectiveManager", "unregisterConnectiveListener() called with: listener = [" + iNetworkChangeListener + "]");
        Set<INetworkChangeListener> set = this.f11804c;
        if (set != null) {
            set.remove(iNetworkChangeListener);
        }
    }

    private NetworkConnectiveManager() {
        this.f11802a = null;
        this.f11803b = null;
        this.f11804c = null;
        this.f11806e = new AtomicBoolean(false);
        this.f11804c = Collections.synchronizedSet(new HashSet());
        if (!a()) {
            ALog.d("NetworkConnectiveManager", "use broadcast receiver.");
        } else {
            ALog.d("NetworkConnectiveManager", "use request network.");
            this.f11805d = new ConnectivityManager.NetworkCallback() { // from class: com.aliyun.iot.aep.sdk.framework.network.NetworkConnectiveManager.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    ALog.i("NetworkConnectiveManager", "onAvailable");
                    ConnectivityManager connectivityManagerE = NetworkConnectiveManager.this.e();
                    if (connectivityManagerE != null) {
                        NetworkConnectiveManager.this.a(connectivityManagerE.getNetworkInfo(network), network);
                    }
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    super.onLost(network);
                    ALog.i("NetworkConnectiveManager", "onLost");
                    ConnectivityManager connectivityManagerE = NetworkConnectiveManager.this.e();
                    if (connectivityManagerE != null) {
                        NetworkConnectiveManager.this.a(connectivityManagerE.getNetworkInfo(network), network);
                    }
                }
            };
        }
    }

    private boolean a() {
        return Build.VERSION.SDK_INT != 23;
    }

    protected void a(NetworkInfo networkInfo, Network network) {
        ALog.d("NetworkConnectiveManager", "dispatch() called with: networkInfo = [" + networkInfo + "], network = [" + network + "]");
        Set<INetworkChangeListener> set = this.f11804c;
        if (set == null) {
            ALog.w("NetworkConnectiveManager", "dispatch network state change listener is empty.");
            return;
        }
        synchronized (set) {
            try {
                for (INetworkChangeListener iNetworkChangeListener : this.f11804c) {
                    if (iNetworkChangeListener != null) {
                        ALog.d("NetworkConnectiveManager", "dispatch network state change -> " + iNetworkChangeListener);
                        iNetworkChangeListener.onNetworkStateChange(networkInfo, network);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
