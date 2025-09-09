package a.a.a.a.b.i;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;

/* renamed from: a.a.a.a.b.i.o, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0246o implements IConnectCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ u f1493a;

    public C0246o(u uVar) {
        this.f1493a = uVar;
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onConnected(BluetoothDevice bluetoothDevice) {
        if (this.f1493a.f1504e != null) {
            this.f1493a.f1504e.onConnected(bluetoothDevice);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onFailure(BluetoothDevice bluetoothDevice, int i2, String str) {
        if (this.f1493a.f1504e != null) {
            this.f1493a.f1504e.onFailure(bluetoothDevice, i2, str);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.callback.IConnectCallback
    public void onReady(BluetoothDevice bluetoothDevice) {
        this.f1493a.e();
        if (this.f1493a.f1504e != null) {
            this.f1493a.f1504e.onReady(bluetoothDevice);
        }
    }
}
