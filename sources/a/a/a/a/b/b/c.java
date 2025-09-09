package a.a.a.a.b.b;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks;

/* loaded from: classes.dex */
public class c implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1314a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ byte[] f1315b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ BleMeshManager f1316c;

    public c(BleMeshManager bleMeshManager, int i2, byte[] bArr) {
        this.f1316c = bleMeshManager;
        this.f1314a = i2;
        this.f1315b = bArr;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(BluetoothDevice bluetoothDevice, int i2) {
        a.a.a.a.b.m.a.b(this.f1316c.TAG, "writeProvisionCharacteristic error: " + i2);
        int i3 = this.f1314a;
        if (i3 <= 0) {
            ((BleMeshManagerCallbacks) this.f1316c.mCallbacks).onError(bluetoothDevice, BleMeshManager.ERROR_RETRY_WRITE_CHARACTERISTIC, i2);
        } else {
            this.f1316c.send(this.f1315b, i3 - 1);
        }
    }
}
