package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.cmp.connect.channel.MqttRrpcRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcHandle;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener;
import com.aliyun.alink.linksdk.tmp.device.payload.cloud.ResponsePayload;
import com.aliyun.alink.linksdk.tmp.device.payload.cloud.UpdatePrefixRequestPayload;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class m implements IConnectRrpcListener {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11179a = "[Tmp]UpdatePrefixHandler";

    /* renamed from: b, reason: collision with root package name */
    protected WeakReference<com.aliyun.alink.linksdk.tmp.device.a> f11180b;

    public m(com.aliyun.alink.linksdk.tmp.device.a aVar) {
        this.f11180b = new WeakReference<>(aVar);
    }

    public void a() {
        ALog.d(f11179a, "unSubTopic");
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener
    public void onReceived(ARequest aRequest, IConnectRrpcHandle iConnectRrpcHandle) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11179a, "onReceive aRequest:" + aRequest + " iConnectRrpcHandle:" + iConnectRrpcHandle);
        if (aRequest == null || !(aRequest instanceof MqttRrpcRequest)) {
            ALog.e(f11179a, "onNotify aMessage  error");
            return;
        }
        UpdatePrefixRequestPayload updatePrefixRequestPayload = (UpdatePrefixRequestPayload) GsonUtils.fromJson(String.valueOf(((MqttRrpcRequest) aRequest).payloadObj), new TypeToken<UpdatePrefixRequestPayload>() { // from class: com.aliyun.alink.linksdk.tmp.connect.entity.cmp.m.1
        }.getType());
        if (updatePrefixRequestPayload == null || updatePrefixRequestPayload.params == null) {
            iConnectRrpcHandle.onRrpcResponse(null, new ResponsePayload(updatePrefixRequestPayload.id, 300));
            return;
        }
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11180b.get();
        if (aVar != null) {
            aVar.d(updatePrefixRequestPayload.params.prefix);
        }
        iConnectRrpcHandle.onRrpcResponse(null, new ResponsePayload(updatePrefixRequestPayload.id, 200));
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener
    public void onResponseFailed(ARequest aRequest, AError aError) {
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener
    public void onResponseSuccess(ARequest aRequest) {
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener
    public void onSubscribeFailed(ARequest aRequest, AError aError) {
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener
    public void onSubscribeSuccess(ARequest aRequest) {
    }
}
