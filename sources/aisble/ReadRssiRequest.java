package aisble;

import aisble.Request;
import aisble.callback.BeforeCallback;
import aisble.callback.FailCallback;
import aisble.callback.InvalidRequestCallback;
import aisble.callback.RssiCallback;
import aisble.callback.SuccessCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl;

/* loaded from: classes.dex */
public final class ReadRssiRequest extends SimpleValueRequest<RssiCallback> implements Operation {
    public ReadRssiRequest(@NonNull Request.Type type) {
        super(type);
    }

    public void notifyRssiRead(@NonNull BluetoothDevice bluetoothDevice, @IntRange(from = -128, to = DefaultLivePlaybackSpeedControl.DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED) int i2) {
        T t2 = this.valueCallback;
        if (t2 != 0) {
            ((RssiCallback) t2).onRssiRead(bluetoothDevice, i2);
        }
    }

    @Override // aisble.Request
    @NonNull
    public ReadRssiRequest before(@NonNull BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public ReadRssiRequest done(@NonNull SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public ReadRssiRequest fail(@NonNull FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public ReadRssiRequest invalid(@NonNull InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public ReadRssiRequest setManager(@NonNull BleManager bleManager) {
        super.setManager(bleManager);
        return this;
    }

    @Override // aisble.SimpleValueRequest
    @NonNull
    public ReadRssiRequest with(@NonNull RssiCallback rssiCallback) {
        super.with((ReadRssiRequest) rssiCallback);
        return this;
    }
}
