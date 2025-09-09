package aisble;

import aisble.Request;
import aisble.callback.FailCallback;
import aisble.callback.SuccessCallback;
import aisble.exception.BluetoothDisabledException;
import aisble.exception.DeviceDisconnectedException;
import aisble.exception.InvalidRequestException;
import aisble.exception.RequestFailedException;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class TimeoutableRequest extends Request {
    public Handler handler;
    public long timeout;
    public Runnable timeoutCallback;
    public TimeoutHandler timeoutHandler;

    public TimeoutableRequest(@NonNull Request.Type type) {
        super(type);
    }

    public final void await() {
        Request.assertNotMainThread();
        SuccessCallback successCallback = this.successCallback;
        FailCallback failCallback = this.failCallback;
        try {
            this.syncLock.close();
            Request.RequestCallback requestCallback = new Request.RequestCallback();
            done(requestCallback).fail(requestCallback).invalid(requestCallback).enqueue();
            if (!this.syncLock.block(this.timeout)) {
                throw new InterruptedException();
            }
            if (requestCallback.isSuccess()) {
                this.successCallback = successCallback;
                this.failCallback = failCallback;
                return;
            }
            int i2 = requestCallback.status;
            if (i2 == -1) {
                throw new DeviceDisconnectedException();
            }
            if (i2 == -100) {
                throw new BluetoothDisabledException();
            }
            if (i2 != -1000000) {
                throw new RequestFailedException(this, requestCallback.status);
            }
            throw new InvalidRequestException(this);
        } catch (Throwable th) {
            this.successCallback = successCallback;
            this.failCallback = failCallback;
            throw th;
        }
    }

    @Override // aisble.Request
    public final void enqueue() {
        super.enqueue();
    }

    @Override // aisble.Request
    public void notifyFail(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        if (!this.finished) {
            this.handler.removeCallbacks(this.timeoutCallback);
            this.timeoutCallback = null;
        }
        super.notifyFail(bluetoothDevice, i2);
    }

    @Override // aisble.Request
    public void notifyInvalidRequest() {
        if (!this.finished) {
            this.handler.removeCallbacks(this.timeoutCallback);
            this.timeoutCallback = null;
        }
        super.notifyInvalidRequest();
    }

    @Override // aisble.Request
    public void notifyStarted(@NonNull final BluetoothDevice bluetoothDevice) {
        if (this.timeout > 0) {
            Runnable runnable = new Runnable() { // from class: aisble.TimeoutableRequest.1
                @Override // java.lang.Runnable
                public void run() {
                    TimeoutableRequest.this.timeoutCallback = null;
                    TimeoutableRequest timeoutableRequest = TimeoutableRequest.this;
                    if (timeoutableRequest.finished) {
                        return;
                    }
                    timeoutableRequest.notifyFail(bluetoothDevice, -5);
                    TimeoutableRequest.this.timeoutHandler.onRequestTimeout(TimeoutableRequest.this);
                }
            };
            this.timeoutCallback = runnable;
            this.handler.postDelayed(runnable, this.timeout);
        }
        super.notifyStarted(bluetoothDevice);
    }

    @Override // aisble.Request
    public void notifySuccess(@NonNull BluetoothDevice bluetoothDevice) {
        if (!this.finished) {
            this.handler.removeCallbacks(this.timeoutCallback);
            this.timeoutCallback = null;
        }
        super.notifySuccess(bluetoothDevice);
    }

    @NonNull
    public TimeoutableRequest timeout(@IntRange(from = 0) long j2) {
        if (this.timeoutCallback != null) {
            throw new IllegalStateException("Request already started");
        }
        this.timeout = j2;
        return this;
    }

    public TimeoutableRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    @Deprecated
    public final void enqueue(@IntRange(from = 0) long j2) {
        timeout(j2).enqueue();
    }

    @Override // aisble.Request
    @NonNull
    public TimeoutableRequest setManager(@NonNull BleManager bleManager) {
        super.setManager(bleManager);
        this.handler = bleManager.mHandler;
        this.timeoutHandler = bleManager;
        return this;
    }

    public TimeoutableRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
    }

    @Deprecated
    public final void await(@IntRange(from = 0) long j2) {
        timeout(j2).await();
    }
}
