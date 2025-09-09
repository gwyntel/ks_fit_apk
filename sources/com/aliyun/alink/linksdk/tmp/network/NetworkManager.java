package com.aliyun.alink.linksdk.tmp.network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.aliyun.alink.linksdk.tmp.network.NoNetworkException;
import com.aliyun.alink.linksdk.tools.ALog;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class NetworkManager {
    public static final int NET_WORK_ID_ERROR = -1;
    public static final String TAG = "[Tmp]NetworkManager";
    public static final int UNCONNECTED = -9999;
    private static NetworkManager networkManager;
    private Context applicationContext;
    private boolean isConnected = true;
    private boolean mLastIsConnected = false;
    private HashSet<INetworkListener> listenerSet = new HashSet<>();
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.aliyun.alink.linksdk.tmp.network.NetworkManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            INetworkListener iNetworkListener;
            if (ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                NetworkManager networkManager2 = NetworkManager.this;
                networkManager2.mLastIsConnected = networkManager2.isConnected;
                NetworkManager.this.isConnected = NetworkManager.isNetworkAvailable(context);
                ALog.d(NetworkManager.TAG, "CONNECTIVITY_ACTION mLastIsConnected:" + NetworkManager.this.mLastIsConnected + " isConnected:" + NetworkManager.this.isConnected);
                if (NetworkManager.this.listenerSet == null || NetworkManager.this.listenerSet.isEmpty()) {
                    return;
                }
                Iterator it = NetworkManager.this.listenerSet.iterator();
                if (!it.hasNext() || (iNetworkListener = (INetworkListener) it.next()) == null) {
                    return;
                }
                iNetworkListener.onNetworkChanged(NetworkManager.this.isConnected, NetworkManager.this.mLastIsConnected);
            }
        }
    };

    public interface INetworkListener {
        void onNetworkChanged(boolean z2, boolean z3);
    }

    private NetworkManager() {
    }

    public static int getActiveNetworkId(Context context) {
        WifiInfo connectionInfo;
        if (context == null || (connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo()) == null) {
            return -1;
        }
        return connectionInfo.getNetworkId();
    }

    public static int getNetworkType(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Context applicationContext;
        if ((context instanceof Activity) && (applicationContext = context.getApplicationContext()) != null) {
            context = applicationContext;
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            return (activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable()) ? activeNetworkInfo.getType() : UNCONNECTED;
        } catch (Exception e2) {
            ALog.e(TAG, "getNetworkType e:" + e2.toString());
            return UNCONNECTED;
        } catch (Throwable th) {
            ALog.e(TAG, "getNetworkType t:" + th.toString());
            return UNCONNECTED;
        }
    }

    public static NetworkManager instance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    public static boolean isNetworkAvailable(Context context) {
        return -9999 != getNetworkType(context);
    }

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public void init(Context context) {
        init(context, null);
    }

    public boolean isNetworkConnected() {
        return this.isConnected;
    }

    public void registerStateChangedListener(INetworkListener iNetworkListener) {
        this.listenerSet.add(iNetworkListener);
        Log.i(TAG, "registerStateChangedListener, size:" + this.listenerSet.size());
    }

    public void release() {
        this.applicationContext.unregisterReceiver(this.mBroadcastReceiver);
    }

    public void unregisterStateChangedListener(INetworkListener iNetworkListener) {
        this.listenerSet.remove(iNetworkListener);
        Log.i(TAG, "unregisterStateChangedListener, size:" + this.listenerSet.size());
    }

    public void init(Context context, NoNetworkException.NoNetworkHanler noNetworkHanler) {
        Context applicationContext;
        this.applicationContext = context;
        if ((context instanceof Activity) && (applicationContext = context.getApplicationContext()) != null) {
            this.applicationContext = applicationContext;
        }
        this.applicationContext.registerReceiver(this.mBroadcastReceiver, new IntentFilter(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION));
        NoNetworkException.setNoNetworkHanler(noNetworkHanler);
        boolean zIsNetworkAvailable = isNetworkAvailable(context);
        this.isConnected = zIsNetworkAvailable;
        this.mLastIsConnected = zIsNetworkAvailable;
    }
}
