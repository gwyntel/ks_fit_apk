package a.a.a.a.b;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;

/* loaded from: classes.dex */
public class B implements IConnectCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1193a;

    public B(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1193a = deviceProvisioningWorker;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onConnected(BluetoothDevice bluetoothDevice) {
        this.f1193a.o();
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onFailure(BluetoothDevice bluetoothDevice, int i2, String str) {
        this.f1193a.o();
        a.a.a.a.b.m.a.a(this.f1193a.f8693b, "init connect onFailure:" + i2 + ";msg:" + str);
        this.f1193a.a(MeshUtConst$MeshErrorEnum.CREATE_TINYMESH_CHANNEL_ERROR, "init channel onFailure:" + i2 + ";msg:" + str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onReady(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(this.f1193a.f8693b, "init transport layer success");
        this.f1193a.f8702k = true;
        this.f1193a.i();
    }
}
