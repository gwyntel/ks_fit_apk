package aisble.callback;

import android.bluetooth.BluetoothDevice;
import android.support.v4.media.MediaDescriptionCompat;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public interface ConnectionPriorityCallback {
    void onConnectionUpdated(@NonNull BluetoothDevice bluetoothDevice, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_YEARS, to = 3200) int i2, @IntRange(from = 0, to = 499) int i3, @IntRange(from = 10, to = 3200) int i4);
}
