package a.a.a.a.b;

import android.content.Intent;
import android.os.ParcelUuid;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import b.InterfaceC0326a;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import datasource.MeshConfigCallback;
import meshprovisioner.states.UnprovisionedMeshNode;

/* renamed from: a.a.a.a.b.i, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0231i implements MeshConfigCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC0326a.InterfaceC0015a f1357a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1358b;

    public C0231i(DeviceProvisioningWorker deviceProvisioningWorker, InterfaceC0326a.InterfaceC0015a interfaceC0015a) {
        this.f1358b = deviceProvisioningWorker;
        this.f1357a = interfaceC0015a;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(this.f1358b.f8693b, "provisionAuth request success " + bool);
        if (bool != null && this.f1357a != null && (this.f1358b.f8700i instanceof UnprovisionedMeshNode)) {
            this.f1357a.a((UnprovisionedMeshNode) this.f1358b.f8700i, bool.booleanValue());
            return;
        }
        String str = this.f1358b.f8693b;
        StringBuilder sb = new StringBuilder();
        sb.append("callback is null ? ");
        sb.append(this.f1357a == null);
        sb.append(", mMeshNode == null ? ");
        sb.append(this.f1358b.f8700i == null);
        sb.append(", mMeshNode instanceof UnprovisionedMeshNode ? ");
        sb.append(this.f1358b.f8700i instanceof UnprovisionedMeshNode);
        a.a.a.a.b.m.a.b(str, sb.toString());
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(this.f1358b.f8693b, "provisionAuth request failed, errorMessage: " + str2);
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.REQUEST_FAILED.getState());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.PROVISION_AUTH_REQUEST_ERROR;
        intent.putExtra(Utils.EXTRA_REQUEST_FAIL_MSG, meshUtConst$MeshErrorEnum.getErrorMsg() + " : " + str2);
        String str3 = "";
        if (this.f1358b.f8717z != null) {
            str3 = this.f1358b.f8717z.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str3, false, this.f1358b.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)), "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg(), str, str2);
        LocalBroadcastManager.getInstance(this.f1358b.f8694c).sendBroadcast(intent);
    }
}
