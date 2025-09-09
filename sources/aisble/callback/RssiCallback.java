package aisble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl;

/* loaded from: classes.dex */
public interface RssiCallback {
    void onRssiRead(@NonNull BluetoothDevice bluetoothDevice, @IntRange(from = -128, to = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED) int i2);
}
