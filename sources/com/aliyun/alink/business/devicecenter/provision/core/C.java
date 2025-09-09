package com.aliyun.alink.business.devicecenter.provision.core;

import com.alibaba.ailabs.iot.mesh.MeshStatusCallback;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.AppMeshStrategy;
import java.util.Map;

/* loaded from: classes2.dex */
public class C implements MeshStatusCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AppMeshStrategy f10525a;

    public C(AppMeshStrategy appMeshStrategy) {
        this.f10525a = appMeshStrategy;
    }

    @Override // com.alibaba.ailabs.iot.mesh.StatusCallback
    public void onStatus(int i2, String str) {
        ALog.d(AppMeshStrategy.TAG, "mesh sdk onStatus() called with: statusCode = [" + i2 + "], statusMsg = [" + str + "]");
        this.f10525a.meshProvisionStatus = i2;
        this.f10525a.meshProvisionErrorMessage = str;
        if (this.f10525a.provisionHasStopped.get()) {
            ALog.d(AppMeshStrategy.TAG, "provisionHasStopped = true, return.");
            return;
        }
        if (i2 == 1) {
            ALog.d(AppMeshStrategy.TAG, "mesh sdk init success.");
            this.f10525a.startMeshDeviceProvision();
        }
        if (i2 == -1) {
            this.f10525a.provisionErrorInfo = new DCErrorCode("SdkError", DCErrorCode.PF_PROVISION_APP_MESH_ERROR).setMsg("mesh sdk returned bind fail, state=" + i2 + ", msg=" + str).setSubcode(DCErrorCode.SUBCODE_MESH_SDK_INIT_EXCEPTION);
            this.f10525a.provisionResultCallback(null);
            return;
        }
        if (i2 != -3) {
            if (i2 == 20) {
                ProvisionStatus provisionStatus = ProvisionStatus.MESH_COMBO_WIFI_CONNECT_CLOUD_STATUS;
                provisionStatus.setExtraParams((Map) JSON.parseObject(str, Map.class));
                this.f10525a.provisionStatusCallback(provisionStatus);
                TgMeshManager.getInstance().stopAddNode();
                return;
            }
            return;
        }
        if (LinkType.ALI_APP_COMBO_MESH.equals(this.f10525a.mConfigParams.linkType) && this.f10525a.mCurrentRetryCount < 3) {
            this.f10525a.mCurrentRetryCount++;
            this.f10525a.provisionHasStarted.set(false);
            this.f10525a.provisionHasStarted.set(false);
            this.f10525a.stopProvisionTimer();
            this.f10525a.startProvision();
            return;
        }
        this.f10525a.provisionErrorInfo = new DCErrorCode("SdkError", DCErrorCode.PF_PROVISION_APP_MESH_ERROR).setMsg("mesh sdk returned provision fail, state=" + i2 + ", msg=" + str).setSubcode(DCErrorCode.SUBCODE_MESH_SDK_PROVISION_EXCEPTION);
        this.f10525a.provisionResultCallback(null);
        this.f10525a.mConfigCallback = null;
    }
}
