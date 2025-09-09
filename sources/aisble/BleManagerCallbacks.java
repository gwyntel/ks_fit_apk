package aisble;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public interface BleManagerCallbacks {
    @Deprecated
    void onBatteryValueReceived(@NonNull BluetoothDevice bluetoothDevice, @IntRange(from = 0, to = 100) int i2);

    void onBonded(@NonNull BluetoothDevice bluetoothDevice);

    void onBondingFailed(@NonNull BluetoothDevice bluetoothDevice);

    void onBondingRequired(@NonNull BluetoothDevice bluetoothDevice);

    void onDeviceConnected(@NonNull BluetoothDevice bluetoothDevice);

    void onDeviceConnecting(@NonNull BluetoothDevice bluetoothDevice);

    void onDeviceDisconnected(@NonNull BluetoothDevice bluetoothDevice);

    void onDeviceDisconnecting(@NonNull BluetoothDevice bluetoothDevice);

    void onDeviceNotSupported(@NonNull BluetoothDevice bluetoothDevice);

    void onDeviceReady(@NonNull BluetoothDevice bluetoothDevice);

    void onError(@NonNull BluetoothDevice bluetoothDevice, @NonNull String str, int i2);

    void onLinkLossOccurred(@NonNull BluetoothDevice bluetoothDevice);

    void onServicesDiscovered(@NonNull BluetoothDevice bluetoothDevice, boolean z2);

    @Deprecated
    boolean shouldEnableBatteryLevelNotifications(@NonNull BluetoothDevice bluetoothDevice);
}
