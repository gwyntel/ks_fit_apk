package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.mesh.delegate.OnReadyToBindHandler;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;
import datasource.MeshConfigCallback;

/* loaded from: classes2.dex */
public class D implements OnReadyToBindHandler {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10526a;

    public D(AppMeshStrategy appMeshStrategy) {
        this.f10526a = appMeshStrategy;
    }

    @Override // com.alibaba.ailabs.iot.mesh.delegate.OnReadyToBindHandler
    public void onReadyToBind(String str, MeshConfigCallback<Boolean> meshConfigCallback) {
        ALog.d(AppMeshStrategy.TAG, "mesh sdk onReadyToBind: " + str);
        if (this.f10526a.provisionHasStopped.get()) {
            return;
        }
        if (meshConfigCallback != null) {
            meshConfigCallback.onSuccess(Boolean.TRUE);
        }
        ALog.d(AppMeshStrategy.TAG, "provision success form mesh sdk.");
        this.f10526a.provisionSuccess();
    }
}
