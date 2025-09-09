package aisble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaDescriptionCompat;
import androidx.annotation.IntRange;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl;

/* loaded from: classes.dex */
public abstract class MainThreadBluetoothGattCallback extends BluetoothGattCallback {
    public Handler mHandler;

    private void runOnUiThread(@NonNull Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onCharacteristicChanged(@NonNull final BluetoothGatt bluetoothGatt, @NonNull final BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        final byte[] value = bluetoothGattCharacteristic.getValue();
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.5
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onCharacteristicChangedSafe(bluetoothGatt, bluetoothGattCharacteristic, value);
            }
        });
    }

    public abstract void onCharacteristicChangedSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable byte[] bArr);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onCharacteristicRead(@NonNull final BluetoothGatt bluetoothGatt, @NonNull final BluetoothGattCharacteristic bluetoothGattCharacteristic, final int i2) {
        final byte[] value = bluetoothGattCharacteristic.getValue();
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.3
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onCharacteristicReadSafe(bluetoothGatt, bluetoothGattCharacteristic, value, i2);
            }
        });
    }

    public abstract void onCharacteristicReadSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable byte[] bArr, int i2);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onCharacteristicWrite(@NonNull final BluetoothGatt bluetoothGatt, @NonNull final BluetoothGattCharacteristic bluetoothGattCharacteristic, final int i2) {
        final byte[] value = bluetoothGattCharacteristic.getValue();
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.4
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onCharacteristicWriteSafe(bluetoothGatt, bluetoothGattCharacteristic, value, i2);
            }
        });
    }

    public abstract void onCharacteristicWriteSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @Nullable byte[] bArr, int i2);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onConnectionStateChange(@NonNull final BluetoothGatt bluetoothGatt, final int i2, final int i3) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.1
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onConnectionStateChangeSafe(bluetoothGatt, i2, i3);
            }
        });
    }

    public abstract void onConnectionStateChangeSafe(@NonNull BluetoothGatt bluetoothGatt, int i2, int i3);

    @Keep
    @RequiresApi(api = 26)
    public final void onConnectionUpdated(@NonNull final BluetoothGatt bluetoothGatt, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_YEARS, to = 3200) final int i2, @IntRange(from = 0, to = 499) final int i3, @IntRange(from = 10, to = 3200) final int i4, final int i5) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.13
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onConnectionUpdatedSafe(bluetoothGatt, i2, i3, i4, i5);
            }
        });
    }

    @RequiresApi(api = 26)
    public abstract void onConnectionUpdatedSafe(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_YEARS, to = 3200) int i2, @IntRange(from = 0, to = 499) int i3, @IntRange(from = 10, to = 3200) int i4, int i5);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onDescriptorRead(@NonNull final BluetoothGatt bluetoothGatt, @NonNull final BluetoothGattDescriptor bluetoothGattDescriptor, final int i2) {
        final byte[] value = bluetoothGattDescriptor.getValue();
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.6
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onDescriptorReadSafe(bluetoothGatt, bluetoothGattDescriptor, value, i2);
            }
        });
    }

    public abstract void onDescriptorReadSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattDescriptor bluetoothGattDescriptor, @Nullable byte[] bArr, int i2);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onDescriptorWrite(@NonNull final BluetoothGatt bluetoothGatt, @NonNull final BluetoothGattDescriptor bluetoothGattDescriptor, final int i2) {
        final byte[] value = bluetoothGattDescriptor.getValue();
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.7
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onDescriptorWriteSafe(bluetoothGatt, bluetoothGattDescriptor, value, i2);
            }
        });
    }

    public abstract void onDescriptorWriteSafe(@NonNull BluetoothGatt bluetoothGatt, @NonNull BluetoothGattDescriptor bluetoothGattDescriptor, @Nullable byte[] bArr, int i2);

    @Override // android.bluetooth.BluetoothGattCallback
    @RequiresApi(api = 21)
    public final void onMtuChanged(@NonNull final BluetoothGatt bluetoothGatt, @IntRange(from = 23, to = 517) final int i2, final int i3) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.10
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onMtuChangedSafe(bluetoothGatt, i2, i3);
            }
        });
    }

    @RequiresApi(api = 21)
    public abstract void onMtuChangedSafe(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = 23, to = 517) int i2, int i3);

    @Override // android.bluetooth.BluetoothGattCallback
    @RequiresApi(api = 26)
    public final void onPhyRead(@NonNull final BluetoothGatt bluetoothGatt, final int i2, final int i3, final int i4) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.11
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onPhyReadSafe(bluetoothGatt, i2, i3, i4);
            }
        });
    }

    @RequiresApi(api = 26)
    public abstract void onPhyReadSafe(@NonNull BluetoothGatt bluetoothGatt, int i2, int i3, int i4);

    @Override // android.bluetooth.BluetoothGattCallback
    @RequiresApi(api = 26)
    public final void onPhyUpdate(@NonNull final BluetoothGatt bluetoothGatt, final int i2, final int i3, final int i4) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.12
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onPhyUpdateSafe(bluetoothGatt, i2, i3, i4);
            }
        });
    }

    @RequiresApi(api = 26)
    public abstract void onPhyUpdateSafe(@NonNull BluetoothGatt bluetoothGatt, int i2, int i3, int i4);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onReadRemoteRssi(@NonNull final BluetoothGatt bluetoothGatt, @IntRange(from = -128, to = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED) final int i2, final int i3) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.8
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onReadRemoteRssiSafe(bluetoothGatt, i2, i3);
            }
        });
    }

    public abstract void onReadRemoteRssiSafe(@NonNull BluetoothGatt bluetoothGatt, @IntRange(from = -128, to = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED) int i2, int i3);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onReliableWriteCompleted(@NonNull final BluetoothGatt bluetoothGatt, final int i2) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.9
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onReliableWriteCompletedSafe(bluetoothGatt, i2);
            }
        });
    }

    public abstract void onReliableWriteCompletedSafe(@NonNull BluetoothGatt bluetoothGatt, int i2);

    @Override // android.bluetooth.BluetoothGattCallback
    public final void onServicesDiscovered(@NonNull final BluetoothGatt bluetoothGatt, final int i2) {
        runOnUiThread(new Runnable() { // from class: aisble.MainThreadBluetoothGattCallback.2
            @Override // java.lang.Runnable
            public void run() {
                MainThreadBluetoothGattCallback.this.onServicesDiscoveredSafe(bluetoothGatt, i2);
            }
        });
    }

    public abstract void onServicesDiscoveredSafe(@NonNull BluetoothGatt bluetoothGatt, int i2);

    public void setHandler(@NonNull Handler handler) {
        this.mHandler = handler;
    }
}
