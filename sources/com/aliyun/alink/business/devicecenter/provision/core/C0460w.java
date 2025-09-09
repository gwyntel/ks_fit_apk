package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.UnprovisionedBluetoothMeshDevice;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.w, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0460w implements IActionListener<UnprovisionedBluetoothMeshDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10597a;

    public C0460w(AppMeshStrategy appMeshStrategy) {
        this.f10597a = appMeshStrategy;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(UnprovisionedBluetoothMeshDevice unprovisionedBluetoothMeshDevice) {
        this.f10597a.startSupportDeviceProvision(unprovisionedBluetoothMeshDevice);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        this.f10597a.provisionErrorInfo = new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setMsg("app mesh provision, no discovered object, please discover before provisioning.").setSubcode(DCErrorCode.SUBCODE_PE_PROVISION_PARAMS_ERROR);
        this.f10597a.provisionResultCallback(null);
    }
}
