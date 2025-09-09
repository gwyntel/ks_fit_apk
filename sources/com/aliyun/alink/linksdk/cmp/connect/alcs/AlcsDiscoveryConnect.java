package com.aliyun.alink.linksdk.cmp.connect.alcs;

import android.content.Context;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig;
import com.aliyun.alink.linksdk.cmp.core.base.AMultiportConnect;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.base.DiscoveryConfig;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectInitListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IDiscoveryListener;
import com.aliyun.alink.linksdk.cmp.manager.discovery.DiscoveryMessage;
import com.aliyun.alink.linksdk.cmp.manager.discovery.DiscoveryRequest;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.linksdk.alcs.AlcsCmpSDK;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class AlcsDiscoveryConnect extends AMultiportConnect implements IConnectDiscovery {
    public static final String CONNECT_ID = "LINK_ALCS_MULTIPORT_DISCOVERY";
    private static final String TAG = "AlcsDiscoveryConnect";

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery
    public void discoveryCertainDevice(final ARequest aRequest, final IDiscoveryListener iDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "discoveryCertainDevice()");
        if (aRequest == null || !(aRequest instanceof DiscoveryRequest)) {
            return;
        }
        DiscoveryRequest discoveryRequest = (DiscoveryRequest) aRequest;
        AlcsCmpSDK.discoveryCertainDevice(new PalDeviceInfo(discoveryRequest.productKey, discoveryRequest.deviceName), new AlcsCmpSDK.IDiscoveryCertainDeviceListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsDiscoveryConnect.2
            @Override // com.aliyun.linksdk.alcs.AlcsCmpSDK.IDiscoveryCertainDeviceListener
            public void onFail(PalDeviceInfo palDeviceInfo) {
                if (iDiscoveryListener == null) {
                    return;
                }
                AError aError = new AError();
                aError.setCode(4201);
                aError.setSubCode(2);
                aError.setSubMsg("rsp error");
                iDiscoveryListener.onFailure(aError);
            }

            @Override // com.aliyun.linksdk.alcs.AlcsCmpSDK.IDiscoveryCertainDeviceListener
            public void onSuccess(PalDeviceInfo palDeviceInfo) {
                if (iDiscoveryListener == null) {
                    return;
                }
                DiscoveryMessage discoveryMessage = new DiscoveryMessage();
                discoveryMessage.discoveryRequest = aRequest;
                iDiscoveryListener.onDiscovery(discoveryMessage);
            }

            @Override // com.aliyun.linksdk.alcs.AlcsCmpSDK.IDiscoveryCertainDeviceListener
            public void onTimeout(PalDeviceInfo palDeviceInfo) {
                if (iDiscoveryListener == null) {
                    return;
                }
                AError aError = new AError();
                aError.setCode(4201);
                aError.setSubCode(1);
                aError.setSubMsg("timeout");
                iDiscoveryListener.onFailure(aError);
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void init(Context context, AConnectConfig aConnectConfig, IConnectInitListener iConnectInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init()");
        this.connectId = CONNECT_ID;
        updateConnectState(ConnectState.CONNECTED);
        if (iConnectInitListener != null) {
            iConnectInitListener.onSuccess();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onDestroy()");
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void send(ARequest aRequest, IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "send()");
        if (iConnectSendListener != null) {
            iConnectSendListener.onFailure(aRequest, CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void setNotifyListener(IConnectNotifyListener iConnectNotifyListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setNotifyListener()");
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery
    public void startDiscovery(IDiscoveryListener iDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        startDiscovery(5000, iDiscoveryListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery
    public void startNotifyMonitor(final IDiscoveryListener iDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startNotifyMonitor()");
        AlcsCmpSDK.startNotifyMonitor(new AlcsCmpSDK.IDiscoveryDevicesListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsDiscoveryConnect.3
            @Override // com.aliyun.linksdk.alcs.AlcsCmpSDK.IDiscoveryDevicesListener
            public void onFound(PalDiscoveryDeviceInfo palDiscoveryDeviceInfo) {
                if (iDiscoveryListener == null || palDiscoveryDeviceInfo == null) {
                    return;
                }
                DiscoveryMessage discoveryMessage = new DiscoveryMessage();
                PalDeviceInfo palDeviceInfo = palDiscoveryDeviceInfo.deviceInfo;
                discoveryMessage.productKey = palDeviceInfo.productModel;
                discoveryMessage.deviceName = palDeviceInfo.deviceId;
                discoveryMessage.modelType = palDiscoveryDeviceInfo.modelType;
                iDiscoveryListener.onDiscovery(discoveryMessage);
            }
        });
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery
    public void stopDiscovery() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopDiscovery()");
        AlcsCmpSDK.stopDiscoveryDevices();
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery
    public void stopNotifyMonitor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopNotifyMonitor()");
        AlcsCmpSDK.stopNotifyMonitor();
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void subscribe(ARequest aRequest, IConnectSubscribeListener iConnectSubscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribe()");
        if (iConnectSubscribeListener != null) {
            iConnectSubscribeListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void unsubscribe(ARequest aRequest, IConnectUnscribeListener iConnectUnscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unsubscribe()");
        if (iConnectUnscribeListener != null) {
            iConnectUnscribeListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery
    public void startDiscovery(int i2, IDiscoveryListener iDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        startDiscovery(i2, null, iDiscoveryListener);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectDiscovery
    public void startDiscovery(int i2, DiscoveryConfig discoveryConfig, final IDiscoveryListener iDiscoveryListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startDiscovery()");
        try {
            AlcsCmpSDK.startDiscoverDevices(i2, (discoveryConfig == null || !(discoveryConfig instanceof AlcsDiscoveryConfig)) ? null : ((AlcsDiscoveryConfig) discoveryConfig).transform(), new AlcsCmpSDK.IDiscoveryDevicesListener() { // from class: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsDiscoveryConnect.1
                @Override // com.aliyun.linksdk.alcs.AlcsCmpSDK.IDiscoveryDevicesListener
                public void onFound(PalDiscoveryDeviceInfo palDiscoveryDeviceInfo) {
                    if (iDiscoveryListener == null || palDiscoveryDeviceInfo == null) {
                        return;
                    }
                    DiscoveryMessage discoveryMessage = new DiscoveryMessage();
                    PalDeviceInfo palDeviceInfo = palDiscoveryDeviceInfo.deviceInfo;
                    discoveryMessage.productKey = palDeviceInfo.productModel;
                    discoveryMessage.deviceName = palDeviceInfo.deviceId;
                    discoveryMessage.modelType = palDiscoveryDeviceInfo.modelType;
                    discoveryMessage.setIp(palDeviceInfo.ip);
                    discoveryMessage.mac = palDiscoveryDeviceInfo.deviceInfo.mac;
                    discoveryMessage.extraData = palDiscoveryDeviceInfo.extraData;
                    iDiscoveryListener.onDiscovery(discoveryMessage);
                }
            });
        } catch (Throwable th) {
            ALog.e(TAG, "startDiscovery error t:" + th.toString());
        }
    }
}
