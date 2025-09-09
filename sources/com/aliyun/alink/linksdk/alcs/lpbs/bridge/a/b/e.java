package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b;

import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.alcs.api.ICAConnectListener;
import com.aliyun.alink.linksdk.alcs.api.ICAMsgListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthServerParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAConnectParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.ica.ICASetupRequestPayload;
import com.aliyun.alink.linksdk.alcs.lpbs.utils.RandomUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.utils.TopicUtils;
import com.aliyun.alink.linksdk.alcs.pal.ica.ICAAlcsNative;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class e implements d {

    /* renamed from: d, reason: collision with root package name */
    private ICADiscoveryDeviceInfo f10795d;

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.d
    public void a(ICADiscoveryDeviceInfo iCADiscoveryDeviceInfo, ICAConnectListener iCAConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f10795d = iCADiscoveryDeviceInfo;
        ICAAuthParams iCAAuthParamsA = b.a("Xtau@iot", "Yx3DdsyetbSezlvc", "0");
        ICADeviceInfo iCADeviceInfo = iCADiscoveryDeviceInfo.deviceInfo;
        ICAAlcsNative.connectDevice(iCADiscoveryDeviceInfo.addr, iCADiscoveryDeviceInfo.port, new ICAConnectParams(new ICADeviceInfo(iCADeviceInfo.productKey, iCADeviceInfo.deviceName), iCADiscoveryDeviceInfo.pal, iCAAuthParamsA), iCAConnectListener);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.d
    public void a() {
        ICAAlcsNative.disConnectDevice(this.f10795d.deviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.d
    public boolean a(ICAAuthServerParams iCAAuthServerParams, ICAMsgListener iCAMsgListener) {
        ICAReqMessage iCAReqMessage = new ICAReqMessage();
        ICASetupRequestPayload iCASetupRequestPayload = new ICASetupRequestPayload();
        ICASetupRequestPayload.ICASetupData iCASetupData = new ICASetupRequestPayload.ICASetupData();
        ICASetupRequestPayload.ICAProvisionAuthData iCAProvisionAuthData = new ICASetupRequestPayload.ICAProvisionAuthData();
        iCAProvisionAuthData.authCode = iCAAuthServerParams.authCode;
        iCAProvisionAuthData.authSecret = iCAAuthServerParams.authSecret;
        ICADeviceInfo iCADeviceInfo = this.f10795d.deviceInfo;
        iCAProvisionAuthData.productKey = iCADeviceInfo.productKey;
        iCAProvisionAuthData.deviceName = iCADeviceInfo.deviceName;
        iCASetupData.configValue.add(iCAProvisionAuthData);
        iCASetupData.configType = "ServerAuthInfo";
        iCASetupRequestPayload.params = iCASetupData;
        iCASetupRequestPayload.id = String.valueOf(RandomUtils.getNextInt());
        iCASetupRequestPayload.method = "core.service.setup";
        iCASetupRequestPayload.version = "1.0";
        String jSONString = JSON.toJSONString(iCASetupRequestPayload);
        ICADeviceInfo iCADeviceInfo2 = this.f10795d.deviceInfo;
        iCAReqMessage.deviceInfo = iCADeviceInfo2;
        iCAReqMessage.topic = TopicUtils.formatTopicByMethod(iCADeviceInfo2.productKey, iCADeviceInfo2.deviceName, TopicUtils.PATH_PRE_DEV, "core.service.setup");
        iCAReqMessage.payload = jSONString.getBytes();
        iCAReqMessage.type = 0;
        iCAReqMessage.code = 3;
        return ICAAlcsNative.sendRequest(iCAReqMessage, iCAMsgListener);
    }
}
