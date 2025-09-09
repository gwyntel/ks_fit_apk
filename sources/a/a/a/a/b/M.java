package a.a.a.a.b;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import b.InterfaceC0326a;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import datasource.MeshConfigCallback;
import meshprovisioner.states.UnprovisionedMeshNode;

/* loaded from: classes.dex */
public class M implements MeshConfigCallback<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ InterfaceC0326a.InterfaceC0015a f1213a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ MeshService f1214b;

    public M(MeshService meshService, InterfaceC0326a.InterfaceC0015a interfaceC0015a) {
        this.f1214b = meshService;
        this.f1213a = interfaceC0015a;
    }

    @Override // datasource.MeshConfigCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "provisionAuth request success " + bool);
        if (bool != null && this.f1213a != null && (this.f1214b.mCurrentProvisionMeshNode instanceof UnprovisionedMeshNode)) {
            this.f1213a.a((UnprovisionedMeshNode) this.f1214b.mCurrentProvisionMeshNode, bool.booleanValue());
            return;
        }
        String str = MeshService.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("callback is null ? ");
        sb.append(this.f1213a == null);
        sb.append(", mMeshNode == null ? ");
        sb.append(this.f1214b.mCurrentProvisionMeshNode == null);
        sb.append(", mMeshNode instanceof UnprovisionedMeshNode ? ");
        sb.append(this.f1214b.mCurrentProvisionMeshNode instanceof UnprovisionedMeshNode);
        a.a.a.a.b.m.a.b(str, sb.toString());
    }

    @Override // datasource.MeshConfigCallback
    public void onFailure(String str, String str2) {
        a.a.a.a.b.m.a.b(MeshService.TAG, "provisionAuth request failed, errorMessage: " + str2);
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.REQUEST_FAILED.getState());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.PROVISION_AUTH_REQUEST_ERROR;
        intent.putExtra(Utils.EXTRA_REQUEST_FAIL_MSG, meshUtConst$MeshErrorEnum.getErrorMsg() + " : " + str2);
        String str3 = "";
        if (this.f1214b.mUnprovisionedMeshNodeData != null) {
            str3 = this.f1214b.mUnprovisionedMeshNodeData.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str3, false, null, "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg(), str, str2);
        LocalBroadcastManager.getInstance(this.f1214b).sendBroadcast(intent);
    }
}
