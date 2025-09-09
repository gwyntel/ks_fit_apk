package a.a.a.a.b;

import android.content.Context;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import datasource.MeshConfigCallback;
import datasource.bean.ProvisionInfo;
import datasource.bean.SigmeshKey;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* renamed from: a.a.a.a.b.e, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0227e implements MeshConfigCallback<ProvisionInfo> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1338a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1339b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1340c;

    public C0227e(DeviceProvisioningWorker deviceProvisioningWorker, UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        this.f1340c = deviceProvisioningWorker;
        this.f1338a = unprovisionedMeshNodeData;
        this.f1339b = bArr;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(ProvisionInfo provisionInfo) {
        Integer num;
        SigmeshKey sigmeshKey;
        a.a.a.a.b.m.a.a(this.f1340c.f8693b, "getProvisionInfo request success");
        if (provisionInfo == null) {
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.NULL_PROVISION_INFO_ERROR;
            this.f1340c.a(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg());
            return;
        }
        if (provisionInfo.getPrimaryUnicastAddress() == null || provisionInfo.getNetKeyIndexes() == null) {
            return;
        }
        this.f1340c.f8704m.e(provisionInfo.getPrimaryUnicastAddress().intValue());
        this.f1340c.f8713v = provisionInfo.getNetKeyIndexes();
        if (this.f1340c.f8713v == null || (num = (Integer) this.f1340c.f8713v.get(0)) == null || (sigmeshKey = (SigmeshKey) this.f1340c.f8711t.get(num.intValue())) == null || sigmeshKey.getProvisionNetKey() == null) {
            return;
        }
        a.a.a.a.b.m.a.a(this.f1340c.f8693b, "Update provisioning setttings");
        this.f1340c.f8704m.a(sigmeshKey.getProvisionNetKey().getNetKey());
        this.f1340c.f8704m.d(sigmeshKey.getProvisionNetKey().getNetKeyIndex());
        if (this.f1338a.isFastProvisionMesh() && this.f1340c.P != null) {
            a.a.a.a.b.i.J j2 = this.f1340c.P;
            Context context = this.f1340c.f8694c;
            DeviceProvisioningWorker deviceProvisioningWorker = this.f1340c;
            b.s sVar = deviceProvisioningWorker.f8704m;
            DeviceProvisioningWorker deviceProvisioningWorker2 = this.f1340c;
            j2.a(context, deviceProvisioningWorker, sVar, deviceProvisioningWorker2, deviceProvisioningWorker2, deviceProvisioningWorker2);
        }
        this.f1340c.f8703l = true;
        if (this.f1340c.f8702k) {
            a.a.a.a.b.m.a.a(this.f1340c.f8693b, "identifyNode after provisioning info is ready");
            this.f1340c.f8696e.a(this.f1340c.f8708q.getAddress(), this.f1340c.f8708q.getName(), this.f1339b, this.f1338a, this.f1340c.P);
            this.f1340c.b("identifyNode");
        }
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(this.f1340c.f8693b, "getProvisionInfo request failed, errorMessage: " + str2);
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.GET_PROVISION_REQUEST_ERROR;
        this.f1340c.a(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg() + " : " + str2);
    }
}
