package a.a.a.a.b;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;

/* loaded from: classes.dex */
public class H implements IConnectCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MeshService f1203a;

    public H(MeshService meshService) {
        this.f1203a = meshService;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onConnected(BluetoothDevice bluetoothDevice) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onFailure(BluetoothDevice bluetoothDevice, int i2, String str) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "init connect onFailure:" + i2 + ";msg:" + str);
        this.f1203a.handleProvisionFailed(MeshUtConst$MeshErrorEnum.CREATE_TINYMESH_CHANNEL_ERROR, "init channel onFailure:" + i2 + ";msg:" + str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onReady(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(MeshService.TAG, "init transport layer success");
        this.f1203a.mDeviceIsReadyInProvisioningStep = true;
        this.f1203a.handleDeviceReadyInProvisioningStep();
    }
}
