package aisble;

import aisble.Request;
import aisble.callback.BeforeCallback;
import aisble.callback.FailCallback;
import aisble.callback.InvalidRequestCallback;
import aisble.callback.MtuCallback;
import aisble.callback.SuccessCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public final class MtuRequest extends SimpleValueRequest<MtuCallback> implements Operation {
    public final int value;

    public MtuRequest(@NonNull Request.Type type, @IntRange(from = 23, to = 517) int i2) {
        super(type);
        i2 = i2 < 23 ? 23 : i2;
        this.value = i2 > 517 ? 517 : i2;
    }

    public int getRequiredMtu() {
        return this.value;
    }

    public void notifyMtuChanged(@NonNull BluetoothDevice bluetoothDevice, @IntRange(from = 23, to = 517) int i2) {
        T t2 = this.valueCallback;
        if (t2 != 0) {
            ((MtuCallback) t2).onMtuChanged(bluetoothDevice, i2);
        }
    }

    @Override // aisble.Request
    @NonNull
    public MtuRequest before(@NonNull BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public MtuRequest done(@NonNull SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public MtuRequest fail(@NonNull FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public MtuRequest invalid(@NonNull InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public MtuRequest setManager(@NonNull BleManager bleManager) {
        super.setManager(bleManager);
        return this;
    }

    @Override // aisble.SimpleValueRequest
    @NonNull
    public MtuRequest with(@NonNull MtuCallback mtuCallback) {
        super.with((MtuRequest) mtuCallback);
        return this;
    }
}
