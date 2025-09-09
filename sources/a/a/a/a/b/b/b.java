package a.a.a.a.b.b;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks;

/* loaded from: classes.dex */
public class b implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1311a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1312b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ BleMeshManager f1313c;

    public b(BleMeshManager bleMeshManager, int i2, byte[] bArr) {
        this.f1313c = bleMeshManager;
        this.f1311a = i2;
        this.f1312b = bArr;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(BluetoothDevice bluetoothDevice, int i2) {
        a.a.a.a.b.m.a.b(this.f1313c.TAG, "writeProxyCharacteristic error: " + i2);
        int i3 = this.f1311a;
        if (i3 > 0) {
            this.f1313c.send(this.f1312b, i3 - 1);
        } else {
            if (this.f1313c.isProvisioningComplete) {
                return;
            }
            ((BleMeshManagerCallbacks) this.f1313c.mCallbacks).onError(bluetoothDevice, BleMeshManager.ERROR_RETRY_WRITE_CHARACTERISTIC, i2);
        }
    }
}
