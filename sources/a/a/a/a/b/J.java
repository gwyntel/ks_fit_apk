package a.a.a.a.b;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.utils.Utils;

/* loaded from: classes.dex */
public class J implements IActionListener<Boolean> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService f1207a;

    public J(MeshService meshService) {
        this.f1207a = meshService;
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
        if (this.f1207a.mUnprovisionedMeshNodeData == null) {
            str2 = "";
        } else {
            str2 = this.f1207a.mUnprovisionedMeshNodeData.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str2, false, null, "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg(), String.valueOf(i2), str);
        LocalBroadcastManager.getInstance(this.f1207a).sendBroadcast(intent);
    }
}
