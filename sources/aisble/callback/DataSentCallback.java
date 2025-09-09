package aisble.callback;

import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public interface DataSentCallback {
    void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data);
}
