package a.a.a.a.b;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import b.InterfaceC0326a;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import datasource.MeshConfigCallback;
import datasource.bean.ServerConfirmation;
import meshprovisioner.states.UnprovisionedMeshNodeData;

/* loaded from: classes.dex */
public class L implements MeshConfigCallback<ServerConfirmation> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC0326a.b f1210a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ UnprovisionedMeshNodeData f1211b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ MeshService f1212c;

    public L(MeshService meshService, InterfaceC0326a.b bVar, UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1212c = meshService;
        this.f1210a = bVar;
        this.f1211b = unprovisionedMeshNodeData;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(ServerConfirmation serverConfirmation) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "provisionConfirm request success");
        if (serverConfirmation == null || this.f1210a == null) {
            return;
        }
        String serverConfirmation2 = serverConfirmation.getServerConfirmation();
        if (!this.f1211b.isFastProvisionMesh()) {
            serverConfirmation2 = "0305" + serverConfirmation.getServerConfirmation();
        }
        a.a.a.a.b.m.a.a(MeshService.TAG, "provisionConfirm value: " + serverConfirmation2);
        this.f1210a.generate(serverConfirmation2);
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(MeshService.TAG, "provisionConfirm request failed, errorMessage: " + str2);
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.REQUEST_FAILED.getState());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.PROVISION_CONFIRM_REQUEST_ERROR;
        intent.putExtra(Utils.EXTRA_REQUEST_FAIL_MSG, meshUtConst$MeshErrorEnum.getErrorMsg() + " : " + str2);
        String str3 = "";
        if (this.f1212c.mUnprovisionedMeshNodeData != null) {
            str3 = this.f1212c.mUnprovisionedMeshNodeData.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str3, false, null, "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg(), str, str2);
        LocalBroadcastManager.getInstance(this.f1212c).sendBroadcast(intent);
    }
}
