package a.a.a.a.b;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;

/* loaded from: classes.dex */
public class D implements IConnectCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DeviceProvisioningWorker f1195a;

    public D(DeviceProvisioningWorker deviceProvisioningWorker) {
        this.f1195a = deviceProvisioningWorker;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onConnected(BluetoothDevice bluetoothDevice) {
        this.f1195a.o();
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onFailure(BluetoothDevice bluetoothDevice, int i2, String str) {
        this.f1195a.o();
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onReady(BluetoothDevice bluetoothDevice) {
    }
}
