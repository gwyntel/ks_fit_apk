package com.aliyun.alink.linksdk.tmp.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.IDiscoveryFilter;
import com.aliyun.alink.linksdk.tmp.data.discovery.DiscoveryConfig;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class BluetoothStateMgr {
    private static final String TAG = "[Tmp]BluetoothStateMgr";
    private static BluetoothBroadcast mBluetoothBroadcast;
    private static Context mContext;

    public static class BluetoothBroadcast extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(BluetoothStateMgr.TAG, "onReceive context:" + context + " intent:" + intent);
            if (intent == null) {
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
            if (intExtra == 10) {
                NetConnected.notifyAllDeviceOffline();
                DeviceManager.getInstance().clearBasicDataList();
            } else {
                if (intExtra != 12) {
                    return;
                }
                DiscoveryConfig discoveryConfig = new DiscoveryConfig();
                DiscoveryConfig.DiscoveryParams discoveryParams = new DiscoveryConfig.DiscoveryParams();
                discoveryConfig.discoveryParams = discoveryParams;
                discoveryParams.discoveryStrategy = DiscoveryConfig.DiscoveryStrategy.LOW_LATENCY;
                DeviceManager.getInstance().discoverDevices((Object) null, false, 40000L, discoveryConfig, (IDiscoveryFilter) null, (IDevListener) null);
            }
        }
    }

    public static void init(Context context) {
        if (mBluetoothBroadcast != null || context == null) {
            return;
        }
        mContext = context;
        mBluetoothBroadcast = new BluetoothBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        mContext.registerReceiver(mBluetoothBroadcast, intentFilter);
    }

    public static void uninit() {
        Context context = mContext;
        if (context != null) {
            context.unregisterReceiver(mBluetoothBroadcast);
        }
    }
}
