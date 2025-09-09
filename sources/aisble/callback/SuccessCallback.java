package aisble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public interface SuccessCallback {
    void onRequestCompleted(@NonNull BluetoothDevice bluetoothDevice);
}
