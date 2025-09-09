package com.aliyun.alink.business.devicecenter.provision.other;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.channel.coap.response.CoapResponsePayload;
import com.aliyun.alink.business.devicecenter.diagnose.SoftApDiagnose;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.other.softap.SoftAPConfigStrategy;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler;

/* loaded from: classes2.dex */
public class b implements IAlcsCoAPReqHandler {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ SoftAPConfigStrategy f10605a;

    public b(SoftAPConfigStrategy softAPConfigStrategy) {
        this.f10605a = softAPConfigStrategy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPReqHandler
    public void onReqComplete(AlcsCoAPContext alcsCoAPContext, int i2, AlcsCoAPResponse alcsCoAPResponse) {
        CoAPClient.getInstance().printResponse(alcsCoAPContext, alcsCoAPResponse);
        if (alcsCoAPResponse == null || TextUtils.isEmpty(alcsCoAPResponse.getPayloadString())) {
            return;
        }
        ALog.llog((byte) 3, SoftAPConfigStrategy.TAG, "getDeviceStatus responseString=" + alcsCoAPResponse.getPayloadString());
        try {
            CoapResponsePayload coapResponsePayload = (CoapResponsePayload) JSON.parseObject(alcsCoAPResponse.getPayloadString(), new a(this).getType(), new Feature[0]);
            if (coapResponsePayload == null || coapResponsePayload.data == 0) {
                return;
            }
            SoftApDiagnose.getInstance().stopDiagnose();
            SoftApDiagnose softApDiagnose = SoftApDiagnose.getInstance();
            T t2 = coapResponsePayload.data;
            softApDiagnose.startDiagnose(((DeviceInfo) t2).productKey, ((DeviceInfo) t2).deviceName, 30);
            this.f10605a.notifySupportProvisionService((DeviceInfo) coapResponsePayload.data);
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(SoftAPConfigStrategy.TAG, "getDeviceErrorCode device.errcode.get parsePayloadException= " + e2);
        }
    }
}
