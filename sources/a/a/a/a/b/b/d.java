package a.a.a.a.b.b;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks;

/* loaded from: classes.dex */
public class d implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1317a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BluetoothGattCharacteristic f1318b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ BleMeshManager f1319c;

    public d(BleMeshManager bleMeshManager, int i2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.f1319c = bleMeshManager;
        this.f1317a = i2;
        this.f1318b = bluetoothGattCharacteristic;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        int i3 = this.f1317a;
        if (i3 <= 1) {
            ((BleMeshManagerCallbacks) this.f1319c.mCallbacks).onError(bluetoothDevice, BleMeshManager.ERROR_RETRY_ENABLE_NOTIFICATION, i2);
        } else {
            this.f1319c.internalEnableNotification(this.f1318b, i3 - 1);
        }
    }
}
