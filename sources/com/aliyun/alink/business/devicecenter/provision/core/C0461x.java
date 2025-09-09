package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.mesh.TgScanManager;
import com.alibaba.ailabs.iot.mesh.UnprovisionedBluetoothMeshDevice;
import com.alibaba.ailabs.tg.utils.ListUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.x, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0461x implements AppMeshStrategy.a {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedBluetoothMeshDevice f10598a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10599b;

    public C0461x(AppMeshStrategy appMeshStrategy, UnprovisionedBluetoothMeshDevice unprovisionedBluetoothMeshDevice) {
        this.f10599b = appMeshStrategy;
        this.f10598a = unprovisionedBluetoothMeshDevice;
    }

    @Override // com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy.a
    public void onFail(int i2, String str) {
        ALog.w(AppMeshStrategy.TAG, "filter device fail code:" + i2 + ";msg:" + str);
    }

    @Override // com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy.a
    public void onSuccess(Object obj) {
        ALog.d(AppMeshStrategy.TAG, "filter device success:" + obj);
        if (obj != null) {
            if (ListUtils.isEmpty(JSON.parseArray(obj.toString())) || this.f10599b.unprovisionedDeviceFound.get()) {
                ALog.e(AppMeshStrategy.TAG, "filter device is null");
                return;
            }
            TgScanManager.getInstance().stopGetRemoteSpecifiedPIDUnprovisionedSigMeshDeviceWithScan();
            this.f10599b.unprovisionedBluetoothMeshDevice = this.f10598a;
            this.f10599b.unprovisionedDeviceFound.set(true);
            this.f10599b.startProvision();
        }
    }
}
