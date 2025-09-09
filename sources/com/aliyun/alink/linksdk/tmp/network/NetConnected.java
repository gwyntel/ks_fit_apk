package com.aliyun.alink.linksdk.tmp.network;

import android.content.Context;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.IDiscoveryFilter;
import com.aliyun.alink.linksdk.tmp.data.discovery.DiscoveryConfig;
import com.aliyun.alink.linksdk.tmp.device.d.a;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.network.NetworkManager;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class NetConnected {
    private static final String TAG = "[Tmp]NetConnected";
    protected static boolean isListening = false;
    protected static int mNetworkId = -1;

    public static void init(Context context) {
        NetworkManager.instance().init(context);
        mNetworkId = NetworkManager.getActiveNetworkId(context);
    }

    public static void notifyAllDeviceOffline() {
        List<DeviceBasicData> allDeviceDataList = DeviceManager.getInstance().getAllDeviceDataList();
        if (allDeviceDataList == null || allDeviceDataList.isEmpty()) {
            ALog.d(TAG, "notifyAllDeviceOffline devicelist empty");
            return;
        }
        Iterator<DeviceBasicData> it = allDeviceDataList.iterator();
        while (it.hasNext()) {
            a.a().a(it.next(), TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_OFFLINE);
        }
    }

    protected static void setNetWorkId(int i2) {
        mNetworkId = i2;
    }

    public static void startNetChangeListen() {
        ALog.d(TAG, "startNetChangeListen isListening:" + isListening);
        if (isListening) {
            return;
        }
        isListening = true;
        NetworkManager.instance().registerStateChangedListener(new NetworkManager.INetworkListener() { // from class: com.aliyun.alink.linksdk.tmp.network.NetConnected.1
            @Override // com.aliyun.alink.linksdk.tmp.network.NetworkManager.INetworkListener
            public void onNetworkChanged(boolean z2, boolean z3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                int networkType = NetworkManager.getNetworkType(NetworkManager.instance().getApplicationContext());
                int activeNetworkId = NetworkManager.getActiveNetworkId(NetworkManager.instance().getApplicationContext());
                ALog.d(NetConnected.TAG, "onNetworkChanged isConnected:" + z2 + " lastIsConnected:" + z3 + " netType:" + networkType + " curNetworkId:" + activeNetworkId + " mNetworkId:" + NetConnected.mNetworkId);
                if (!z2) {
                    NetConnected.setNetWorkId(-1);
                    NetConnected.notifyAllDeviceOffline();
                    DeviceManager.getInstance().clearBasicDataList();
                    return;
                }
                if (activeNetworkId == NetConnected.mNetworkId) {
                    ALog.w(NetConnected.TAG, "same network id return");
                    return;
                }
                NetConnected.setNetWorkId(activeNetworkId);
                DiscoveryConfig discoveryConfig = new DiscoveryConfig();
                DiscoveryConfig.DiscoveryParams discoveryParams = new DiscoveryConfig.DiscoveryParams();
                discoveryConfig.discoveryParams = discoveryParams;
                discoveryParams.discoveryStrategy = DiscoveryConfig.DiscoveryStrategy.LOW_LATENCY;
                if (networkType == 1) {
                    NetConnected.notifyAllDeviceOffline();
                    DeviceManager.getInstance().clearBasicDataList();
                    DeviceManager.getInstance().discoverDevices((Object) null, false, 40000L, discoveryConfig, (IDiscoveryFilter) null, (IDevListener) null);
                } else if (networkType == 0) {
                    NetConnected.notifyAllDeviceOffline();
                    DeviceManager.getInstance().clearBasicDataList();
                } else {
                    NetConnected.notifyAllDeviceOffline();
                    DeviceManager.getInstance().clearBasicDataList();
                    DeviceManager.getInstance().discoverDevices(null, 50000L, null);
                }
            }
        });
    }
}
