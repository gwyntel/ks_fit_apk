package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.mesh.delegate.OnReadyToBindHandler;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentAppMeshStrategy;
import datasource.MeshConfigCallback;
import datasource.bean.IotDevice;
import java.util.List;

/* loaded from: classes2.dex */
public class H implements OnReadyToBindHandler {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ConcurrentAppMeshStrategy f10531a;

    public H(ConcurrentAppMeshStrategy concurrentAppMeshStrategy) {
        this.f10531a = concurrentAppMeshStrategy;
    }

    @Override // com.alibaba.ailabs.iot.mesh.delegate.OnReadyToBindHandler
    public void onReadyToBind(String str, MeshConfigCallback<Boolean> meshConfigCallback) {
        ALog.d(ConcurrentAppMeshStrategy.TAG, "mesh sdk onReadyToBind: " + str);
        if (this.f10531a.provisionHasStopped.get()) {
            return;
        }
        if (meshConfigCallback != null) {
            meshConfigCallback.onSuccess(Boolean.TRUE);
        }
        ALog.d(ConcurrentAppMeshStrategy.TAG, "provision success form mesh sdk.");
        List array = JSON.parseArray(str, IotDevice.class);
        if (array == null || array.size() <= 0) {
            return;
        }
        ALog.d(ConcurrentAppMeshStrategy.TAG, "mac: " + ((IotDevice) array.get(0)).getMac().toLowerCase());
        this.f10531a.provisionSuccess(((IotDevice) array.get(0)).getMac().toLowerCase());
        if (this.f10531a.useBatchProvisionStrategy.get()) {
            return;
        }
        this.f10531a.scheduleNextConfigTask();
    }
}
