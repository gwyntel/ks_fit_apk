package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import datasource.MeshConfigCallback;
import datasource.bean.ProvisionInfo;
import datasource.bean.SigmeshKey;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* loaded from: classes.dex */
public class I implements MeshConfigCallback<ProvisionInfo> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1204a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1205b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshService f1206c;

    public I(MeshService meshService, UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        this.f1206c = meshService;
        this.f1204a = unprovisionedMeshNodeData;
        this.f1205b = bArr;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(ProvisionInfo provisionInfo) {
        Integer num;
        SigmeshKey sigmeshKey;
        a.a.a.a.b.m.a.a(MeshService.TAG, "getProvisionInfo request success");
        if (provisionInfo == null) {
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.NULL_PROVISION_INFO_ERROR;
            this.f1206c.handleProvisionFailed(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg());
            return;
        }
        if (provisionInfo.getPrimaryUnicastAddress() == null || provisionInfo.getNetKeyIndexes() == null) {
            return;
        }
        this.f1206c.mProvisioningSettings.e(provisionInfo.getPrimaryUnicastAddress().intValue());
        this.f1206c.mNetKeyIndexes = provisionInfo.getNetKeyIndexes();
        if (this.f1206c.mNetKeyIndexes == null || (num = (Integer) this.f1206c.mNetKeyIndexes.get(0)) == null || (sigmeshKey = (SigmeshKey) this.f1206c.mSigmeshKeys.get(num.intValue())) == null || sigmeshKey.getProvisionNetKey() == null) {
            return;
        }
        a.a.a.a.b.m.a.a(MeshService.TAG, "Update provisioning setttings");
        this.f1206c.mProvisioningSettings.a(sigmeshKey.getProvisionNetKey().getNetKey());
        this.f1206c.mProvisioningSettings.d(sigmeshKey.getProvisionNetKey().getNetKeyIndex());
        if (this.f1204a.isFastProvisionMesh() && this.f1206c.mFastProvisionWorker != null) {
            a.a.a.a.b.i.J j2 = this.f1206c.mFastProvisionWorker;
            MeshService meshService = this.f1206c;
            b.s sVar = meshService.mProvisioningSettings;
            MeshService meshService2 = this.f1206c;
            j2.a(meshService, meshService, sVar, meshService2, meshService2, meshService2);
        }
        this.f1206c.mProvisionInfoReady = true;
        if (this.f1206c.mDeviceIsReadyInProvisioningStep) {
            a.a.a.a.b.m.a.a(MeshService.TAG, "identifyNode after provisioning info is ready");
            this.f1206c.mMeshManagerApi.a(this.f1206c.mBluetoothDevice.getAddress(), this.f1206c.mBluetoothDevice.getName(), this.f1205b, this.f1204a, this.f1206c.mFastProvisionWorker);
            this.f1206c.sendBroadcastConnectionState("identifyNode");
        }
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(MeshService.TAG, "getProvisionInfo request failed, errorMessage: " + str2);
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.GET_PROVISION_REQUEST_ERROR;
        this.f1206c.handleProvisionFailed(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg() + " : " + str2);
    }
}
