package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;

/* loaded from: classes2.dex */
public class K implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy.a f10536a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy f10537b;

    public K(ConcurrentAppMeshStrategy concurrentAppMeshStrategy, ConcurrentAppMeshStrategy.a aVar) {
        this.f10537b = concurrentAppMeshStrategy;
        this.f10536a = aVar;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        ConcurrentAppMeshStrategy.a aVar = this.f10536a;
        if (aVar != null) {
            aVar.onFail(-1, exc.getMessage());
        }
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (this.f10536a == null) {
            return;
        }
        if (ioTResponse == null || ioTResponse.getCode() != 200) {
            this.f10536a.onFail(ioTResponse == null ? 0 : ioTResponse.getCode(), ioTResponse == null ? "" : ioTResponse.getLocalizedMsg());
        } else if (ioTResponse.getData() != null) {
            this.f10536a.onSuccess(ioTResponse.getData());
        } else {
            this.f10536a.onSuccess(null);
        }
    }
}
