package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.api.ICADisconnectListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAConnectParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProviderListener;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalConnectParams;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.alcs.pal.ica.ICAAlcsNative;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class d implements IPalConnect {

    /* renamed from: d, reason: collision with root package name */
    private static final String f10801d = "[AlcsLPBS]ICAAlcsConnect";

    /* renamed from: a, reason: collision with root package name */
    protected PalConnectParams f10802a;

    /* renamed from: b, reason: collision with root package name */
    protected c f10803b;

    /* renamed from: c, reason: collision with root package name */
    protected PalDeviceInfo f10804c;

    public d(c cVar, PalDeviceInfo palDeviceInfo) {
        this.f10803b = cVar;
        this.f10804c = palDeviceInfo;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean asyncSendRequest(PalReqMessage palReqMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10801d, "asyncSendRequest reqMessageInfo:" + palReqMessage + " callback:" + palMsgListener);
        if (palReqMessage == null) {
            ALog.d(f10801d, "asyncSendRequest error:");
            return false;
        }
        ICAReqMessage iCAReqMessageA = m.a(palReqMessage);
        String str = iCAReqMessageA.topic;
        if (str != null && str.contains("/thing/model/down_raw")) {
            iCAReqMessageA.code = 3;
        }
        return ICAAlcsNative.sendRequest(iCAReqMessageA, new k(palMsgListener));
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public int getConnectType(PalDeviceInfo palDeviceInfo) {
        return 1;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public String getPluginId() {
        return "iot_ica";
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean isDeviceConnected(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zIsDeviceOnline = ICAAlcsNative.isDeviceOnline(new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId));
        ALog.d(f10801d, "isDeviceConnected deviceInfo:" + zIsDeviceOnline);
        return zIsDeviceOnline;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void onCloudChannelCreate(IThingCloudChannel iThingCloudChannel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10801d, "onCloudChannelCreate cloudChannel:" + iThingCloudChannel);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean regDeviceStateListener(final PalDeviceInfo palDeviceInfo, final PalDeviceStateListener palDeviceStateListener) {
        ICAAlcsNative.setDeviceDisconnectListener(new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId), new ICADisconnectListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.d.2
            @Override // com.aliyun.alink.linksdk.alcs.api.ICADisconnectListener
            public void onDisConnect(ICADeviceInfo iCADeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                StringBuilder sb = new StringBuilder();
                sb.append("DeviceStatechange icaDeviceInfo:");
                sb.append(iCADeviceInfo == null ? TmpConstant.GROUP_ROLE_UNKNOWN : iCADeviceInfo.toString());
                sb.append(" STATE_DISCONNECTED");
                sb.append(" listener:");
                sb.append(palDeviceStateListener);
                ALog.d(d.f10801d, sb.toString());
                PalDeviceStateListener palDeviceStateListener2 = palDeviceStateListener;
                if (palDeviceStateListener2 != null || iCADeviceInfo == null) {
                    palDeviceStateListener2.onDeviceStateChange(palDeviceInfo, 0);
                }
            }
        });
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void startConnect(final PalConnectParams palConnectParams, final PalConnectListener palConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palConnectListener == null) {
            ALog.e(f10801d, "startConnect listener null");
            return;
        }
        if (palConnectParams == null) {
            ALog.e(f10801d, "startConnect params null");
            palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
            return;
        }
        ICADiscoveryDeviceInfo iCADiscoveryDeviceInfoA = this.f10803b.a(palConnectParams.getDevId());
        this.f10802a = palConnectParams;
        if (iCADiscoveryDeviceInfoA == null) {
            ALog.e(f10801d, "startConnect discoveryDeviceInfo null params:" + palConnectParams.toString());
            palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
            return;
        }
        final String str = iCADiscoveryDeviceInfoA.addr;
        final int i2 = iCADiscoveryDeviceInfoA.port;
        final String str2 = iCADiscoveryDeviceInfoA.pal;
        if (palConnectParams.authInfo == null) {
            ALog.d(f10801d, "authInfo null");
            if (this.f10803b.getPalAuthRegister().getProvider() == null) {
                palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
                return;
            } else {
                this.f10803b.getPalAuthRegister().getProvider().queryAuthInfo(palConnectParams.deviceInfo, null, iCADiscoveryDeviceInfoA, new IAuthProviderListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.d.1
                    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProviderListener
                    public void onComplete(PalDeviceInfo palDeviceInfo, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        if (obj == null) {
                            palConnectListener.onLoad(1, null, palConnectParams.deviceInfo);
                            return;
                        }
                        PalDeviceInfo palDeviceInfo2 = palConnectParams.deviceInfo;
                        ICAConnectParams iCAConnectParams = new ICAConnectParams(new ICADeviceInfo(palDeviceInfo2.productModel, palDeviceInfo2.deviceId), str2, (ICAAuthParams) obj);
                        h hVar = new h(palConnectListener);
                        ICAAuthParams iCAAuthParams = iCAConnectParams.authInfo;
                        if (iCAAuthParams == null || TextUtils.isEmpty(iCAAuthParams.accessKey) || TextUtils.isEmpty(iCAConnectParams.authInfo.accessToken)) {
                            ALog.d(d.f10801d, "startConnect params empty");
                            hVar.onLoad(503, "invalid params", iCAConnectParams.deviceInfo);
                            return;
                        }
                        ALog.d(d.f10801d, "startConnect params:" + palConnectParams + " listener:" + palConnectListener);
                        ICAAlcsNative.connectDevice(str, i2, iCAConnectParams, hVar);
                    }
                });
                return;
            }
        }
        PalDeviceInfo palDeviceInfo = palConnectParams.deviceInfo;
        ICAConnectParams iCAConnectParams = new ICAConnectParams(new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId), iCADiscoveryDeviceInfoA.pal, (ICAAuthParams) palConnectParams.authInfo);
        h hVar = new h(palConnectListener);
        ICAAuthParams iCAAuthParams = iCAConnectParams.authInfo;
        if (iCAAuthParams == null || TextUtils.isEmpty(iCAAuthParams.accessKey) || TextUtils.isEmpty(iCAConnectParams.authInfo.accessToken)) {
            ALog.d(f10801d, "startConnect params empty");
            hVar.onLoad(503, "invalid params", iCAConnectParams.deviceInfo);
            return;
        }
        ALog.d(f10801d, "startConnect params:" + palConnectParams + " listener:" + palConnectListener);
        ICAAlcsNative.connectDevice(str, i2, iCAConnectParams, hVar);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void stopConnect(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10801d, "stopConnect deviceInfo:" + palDeviceInfo);
        ICAAlcsNative.disConnectDevice(m.a(palDeviceInfo));
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean subscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener, PalMsgListener palMsgListener2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10801d, "subscribe  subMessage:" + palSubMessage + " PalMsgListener:" + palMsgListener + " eventListener:" + palMsgListener2);
        return ICAAlcsNative.subcribe(m.a(palSubMessage), new k(palMsgListener), new k(palMsgListener2));
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unregDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) {
        ICAAlcsNative.removeDeviceDisconnectListener(new ICADeviceInfo(palDeviceInfo.productModel, palDeviceInfo.deviceId));
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unsubscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10801d, "unsubcribe reqMessageInfo:" + palSubMessage + " callback:" + palMsgListener);
        return ICAAlcsNative.unsubcribe(m.a(palSubMessage), new k(palMsgListener));
    }
}
