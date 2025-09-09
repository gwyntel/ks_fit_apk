package a.a.a.a.b;

import android.content.Intent;
import android.os.ParcelUuid;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* renamed from: a.a.a.a.b.f, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0228f implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1342a;

    public C0228f(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1342a = deviceProvisioningWorker;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(Boolean bool) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
    public void onFailure(int i2, String str) {
        String str2;
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.REQUEST_FAILED.getState());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.PROVISION_COMPLETE_REQUEST_ERROR;
        intent.putExtra(Utils.EXTRA_REQUEST_FAIL_MSG, meshUtConst$MeshErrorEnum.getErrorMsg() + " : " + str);
        if (this.f1342a.f8717z == null) {
            str2 = "";
        } else {
            str2 = this.f1342a.f8717z.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str2, false, this.f1342a.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)), "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg(), String.valueOf(i2), str);
        LocalBroadcastManager.getInstance(this.f1342a.f8694c).sendBroadcast(intent);
    }
}
