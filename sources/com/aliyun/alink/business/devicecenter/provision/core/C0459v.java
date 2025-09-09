package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.v, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0459v implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy.a f10595a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10596b;

    public C0459v(AppMeshStrategy appMeshStrategy, AppMeshStrategy.a aVar) {
        this.f10596b = appMeshStrategy;
        this.f10595a = aVar;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        AppMeshStrategy.a aVar = this.f10595a;
        if (aVar != null) {
            aVar.onFail(-1, exc.getMessage());
        }
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (this.f10595a == null) {
            return;
        }
        if (ioTResponse == null || ioTResponse.getCode() != 200) {
            this.f10595a.onFail(ioTResponse == null ? 0 : ioTResponse.getCode(), ioTResponse == null ? "" : ioTResponse.getLocalizedMsg());
        } else if (ioTResponse.getData() != null) {
            this.f10595a.onSuccess(ioTResponse.getData());
        } else {
            this.f10595a.onSuccess(null);
        }
    }
}
