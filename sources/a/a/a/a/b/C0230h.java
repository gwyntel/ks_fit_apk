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
import datasource.bean.ServerConfirmation;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* renamed from: a.a.a.a.b.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0230h implements MeshConfigCallback<ServerConfirmation> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC0326a.b f1349a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1350b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1351c;

    public C0230h(DeviceProvisioningWorker deviceProvisioningWorker, InterfaceC0326a.b bVar, UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1351c = deviceProvisioningWorker;
        this.f1349a = bVar;
        this.f1350b = unprovisionedMeshNodeData;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(ServerConfirmation serverConfirmation) {
        a.a.a.a.b.m.a.a(this.f1351c.f8693b, "provisionConfirm request success");
        if (serverConfirmation == null || this.f1349a == null) {
            return;
        }
        String serverConfirmation2 = serverConfirmation.getServerConfirmation();
        if (!this.f1350b.isFastProvisionMesh()) {
            serverConfirmation2 = "0305" + serverConfirmation.getServerConfirmation();
        }
        a.a.a.a.b.m.a.a(this.f1351c.f8693b, "provisionConfirm value: " + serverConfirmation2);
        this.f1349a.generate(serverConfirmation2);
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(this.f1351c.f8693b, "provisionConfirm request failed, errorMessage: " + str2);
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.REQUEST_FAILED.getState());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.PROVISION_CONFIRM_REQUEST_ERROR;
        intent.putExtra(Utils.EXTRA_REQUEST_FAIL_MSG, meshUtConst$MeshErrorEnum.getErrorMsg() + " : " + str2);
        String str3 = "";
        if (this.f1351c.f8717z != null) {
            str3 = this.f1351c.f8717z.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str3, false, this.f1351c.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)), "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg(), str, str2);
        LocalBroadcastManager.getInstance(this.f1351c.f8694c).sendBroadcast(intent);
    }
}
