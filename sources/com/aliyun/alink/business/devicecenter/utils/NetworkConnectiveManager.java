package com.aliyun.alink.business.devicecenter.utils;

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
import com.aliyun.alink.business.devicecenter.log.ALog;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class NetworkConnectiveManager {

    /* renamed from: a, reason: collision with root package name */
    public Context f10643a;

    /* renamed from: b, reason: collision with root package name */
    public BroadcastReceiver f10644b;

    /* renamed from: c, reason: collision with root package name */
    public CopyOnWriteArrayList<INetworkChangeListener> f10645c;

    /* renamed from: d, reason: collision with root package name */
    public ConnectivityManager.NetworkCallback f10646d;

    /* renamed from: e, reason: collision with root package name */
    public AtomicBoolean f10647e;

    public interface INetworkChangeListener {
        void onNetworkStateChange(NetworkInfo networkInfo, Network network);
    }

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final NetworkConnectiveManager f10650a = new NetworkConnectiveManager();
    }

    public static NetworkConnectiveManager getInstance() {
        return SingletonHolder.f10650a;
    }

    public final void b() {
        ALog.d("NetworkConnectiveManager", "clearListener() called");
        CopyOnWriteArrayList<INetworkChangeListener> copyOnWriteArrayList = this.f10645c;
        if (copyOnWriteArrayList != null) {
            copyOnWriteArrayList.clear();
        }
    }

    public final void c() {
        ALog.d("NetworkConnectiveManager", "deinitRN() called");
        try {
            ConnectivityManager connectivityManagerD = d();
            if (connectivityManagerD != null) {
                connectivityManagerD.unregisterNetworkCallback(this.f10646d);
            }
        } catch (Exception e2) {
            ALog.d("NetworkConnectiveManager", "deinitRN exception=" + e2);
        }
    }

    public final ConnectivityManager d() {
        Context context = this.f10643a;
        if (context != null) {
            return (ConnectivityManager) context.getSystemService("connectivity");
        }
        return null;
    }

    public void deinitNetworkConnectiveManager() {
        ALog.d("NetworkConnectiveManager", "deinitNetworkConnectiveManager() called");
        h();
        c();
        b();
        this.f10647e.set(false);
    }

    public void dispatch(NetworkInfo networkInfo, Network network) {
        ALog.d("NetworkConnectiveManager", "dispatch() called with: networkInfo = [" + networkInfo + "], network = [" + network + "]");
        CopyOnWriteArrayList<INetworkChangeListener> copyOnWriteArrayList = this.f10645c;
        if (copyOnWriteArrayList == null) {
            ALog.w("NetworkConnectiveManager", "dispatch network state change listener is empty.");
            return;
        }
        int size = copyOnWriteArrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            INetworkChangeListener iNetworkChangeListener = this.f10645c.get(i2);
            if (iNetworkChangeListener != null) {
                iNetworkChangeListener.onNetworkStateChange(networkInfo, network);
            }
        }
    }

    public final void e() {
        ALog.d("NetworkConnectiveManager", "initBR() called");
        this.f10644b = new BroadcastReceiver() { // from class: com.aliyun.alink.business.devicecenter.utils.NetworkConnectiveManager.2
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
                    NetworkConnectiveManager.this.dispatch(networkInfo, null);
                }
            }
        };
    }

    public final void f() {
        ALog.d("NetworkConnectiveManager", "initRN() called version=" + Build.VERSION.SDK_INT);
        ConnectivityManager connectivityManagerD = d();
        if (connectivityManagerD != null) {
            connectivityManagerD.requestNetwork(new NetworkRequest.Builder().build(), this.f10646d);
        }
    }

    public final void g() {
        if (this.f10643a == null) {
            ALog.w("NetworkConnectiveManager", "registerReceiver failed, context=null.");
            return;
        }
        ALog.d("NetworkConnectiveManager", "registerReceiver() called.");
        e();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.f10643a.registerReceiver(this.f10644b, intentFilter);
    }

    public final void h() {
        Context context;
        try {
            BroadcastReceiver broadcastReceiver = this.f10644b;
            if (broadcastReceiver == null || (context = this.f10643a) == null) {
                return;
            }
            context.unregisterReceiver(broadcastReceiver);
            this.f10644b = null;
        } catch (Exception e2) {
            ALog.w("NetworkConnectiveManager", "unregisterAPBroadcast exception=" + e2);
        }
    }

    public void initNetworkConnectiveManager(Context context) {
        ALog.d("NetworkConnectiveManager", "initNetworkConnectiveManager() called with: context = [" + context + "]");
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null.");
        }
        if (this.f10647e.get()) {
            ALog.d("NetworkConnectiveManager", "initNetworkConnectiveManager has inited.");
        }
        this.f10647e.set(true);
        this.f10643a = context.getApplicationContext();
        if (!a()) {
            g();
            return;
        }
        try {
            f();
        } catch (Exception e2) {
            ALog.w("NetworkConnectiveManager", "initNetworkConnectiveManager exception = " + e2);
            c();
            g();
        }
    }

    public void registerConnectiveListener(INetworkChangeListener iNetworkChangeListener) {
        CopyOnWriteArrayList<INetworkChangeListener> copyOnWriteArrayList;
        ALog.d("NetworkConnectiveManager", "registerConnectiveListener() called with: listener = [" + iNetworkChangeListener + "]");
        if (iNetworkChangeListener == null || (copyOnWriteArrayList = this.f10645c) == null || copyOnWriteArrayList.contains(iNetworkChangeListener)) {
            return;
        }
        this.f10645c.add(iNetworkChangeListener);
    }

    public void unregisterConnectiveListener(INetworkChangeListener iNetworkChangeListener) {
        CopyOnWriteArrayList<INetworkChangeListener> copyOnWriteArrayList;
        ALog.d("NetworkConnectiveManager", "unregisterConnectiveListener() called with: listener = [" + iNetworkChangeListener + "]");
        if (iNetworkChangeListener == null || (copyOnWriteArrayList = this.f10645c) == null) {
            return;
        }
        copyOnWriteArrayList.remove(iNetworkChangeListener);
    }

    public NetworkConnectiveManager() {
        this.f10643a = null;
        this.f10644b = null;
        this.f10645c = null;
        this.f10647e = new AtomicBoolean(false);
        this.f10645c = new CopyOnWriteArrayList<>();
        if (!a()) {
            ALog.d("NetworkConnectiveManager", "use broadcast receiver.");
        } else {
            ALog.d("NetworkConnectiveManager", "use request network.");
            this.f10646d = new ConnectivityManager.NetworkCallback() { // from class: com.aliyun.alink.business.devicecenter.utils.NetworkConnectiveManager.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    ALog.i("NetworkConnectiveManager", "onAvailable");
                    ConnectivityManager connectivityManagerD = NetworkConnectiveManager.this.d();
                    if (connectivityManagerD != null) {
                        NetworkConnectiveManager.this.dispatch(connectivityManagerD.getNetworkInfo(network), network);
                    }
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    super.onLost(network);
                    ALog.i("NetworkConnectiveManager", "onLost");
                    ConnectivityManager connectivityManagerD = NetworkConnectiveManager.this.d();
                    if (connectivityManagerD != null) {
                        NetworkConnectiveManager.this.dispatch(connectivityManagerD.getNetworkInfo(network), network);
                    }
                }
            };
        }
    }

    public final boolean a() {
        return Build.VERSION.SDK_INT != 23;
    }
}
