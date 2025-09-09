package aisble;

import aisble.Request;
import aisble.callback.BeforeCallback;
import aisble.callback.DataReceivedCallback;
import aisble.callback.FailCallback;
import aisble.callback.InvalidRequestCallback;
import aisble.callback.ReadProgressCallback;
import aisble.callback.SuccessCallback;
import aisble.callback.profile.ProfileReadResponse;
import aisble.data.Data;
import aisble.data.DataFilter;
import aisble.data.DataMerger;
import aisble.data.DataStream;
import aisble.exception.InvalidDataException;
import aisble.exception.RequestFailedException;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public final class WaitForValueChangedRequest extends TimeoutableValueRequest<DataReceivedCallback> implements Operation {
    public static final int NOT_STARTED = -123456;
    public static final int STARTED = -123455;
    public boolean bluetoothDisabled;
    public DataStream buffer;
    public int count;
    public DataMerger dataMerger;
    public boolean deviceDisconnected;
    public DataFilter filter;
    public ReadProgressCallback progressCallback;
    public Request trigger;
    public int triggerStatus;

    public WaitForValueChangedRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
        this.triggerStatus = 0;
        this.count = 0;
    }

    @NonNull
    public <E extends ProfileReadResponse> E awaitValid(@NonNull E e2) throws InvalidDataException {
        E e3 = (E) await((WaitForValueChangedRequest) e2);
        if (e3 == null || e3.isValid()) {
            return e3;
        }
        throw new InvalidDataException(e3);
    }

    @NonNull
    public WaitForValueChangedRequest filter(@NonNull DataFilter dataFilter) {
        this.filter = dataFilter;
        return this;
    }

    @Nullable
    public Request getTrigger() {
        return this.trigger;
    }

    public boolean hasMore() {
        return this.count > 0;
    }

    public boolean isTriggerCompleteOrNull() {
        return this.triggerStatus != -123455;
    }

    public boolean isTriggerPending() {
        return this.triggerStatus == -123456;
    }

    public boolean matches(byte[] bArr) {
        DataFilter dataFilter = this.filter;
        return dataFilter == null || dataFilter.filter(bArr);
    }

    @NonNull
    public WaitForValueChangedRequest merge(@NonNull DataMerger dataMerger) {
        this.dataMerger = dataMerger;
        this.progressCallback = null;
        return this;
    }

    public void notifyValueChanged(BluetoothDevice bluetoothDevice, byte[] bArr) {
        DataReceivedCallback dataReceivedCallback = (DataReceivedCallback) this.valueCallback;
        if (dataReceivedCallback == null) {
            return;
        }
        if (this.dataMerger == null) {
            dataReceivedCallback.onDataReceived(bluetoothDevice, new Data(bArr));
            return;
        }
        ReadProgressCallback readProgressCallback = this.progressCallback;
        if (readProgressCallback != null) {
            readProgressCallback.onPacketReceived(bluetoothDevice, bArr, this.count);
        }
        if (this.buffer == null) {
            this.buffer = new DataStream();
        }
        DataMerger dataMerger = this.dataMerger;
        DataStream dataStream = this.buffer;
        int i2 = this.count;
        this.count = i2 + 1;
        if (dataMerger.merge(dataStream, bArr, i2)) {
            dataReceivedCallback.onDataReceived(bluetoothDevice, this.buffer.toData());
            this.buffer = null;
            this.count = 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public WaitForValueChangedRequest trigger(@NonNull Operation operation) {
        if (operation instanceof Request) {
            Request request = (Request) operation;
            this.trigger = request;
            this.triggerStatus = NOT_STARTED;
            request.internalBefore(new BeforeCallback() { // from class: aisble.WaitForValueChangedRequest.1
                @Override // aisble.callback.BeforeCallback
                public void onRequestStarted(@NonNull BluetoothDevice bluetoothDevice) {
                    WaitForValueChangedRequest.this.triggerStatus = WaitForValueChangedRequest.STARTED;
                }
            });
            this.trigger.internalSuccess(new SuccessCallback() { // from class: aisble.WaitForValueChangedRequest.2
                @Override // aisble.callback.SuccessCallback
                public void onRequestCompleted(@NonNull BluetoothDevice bluetoothDevice) {
                    WaitForValueChangedRequest.this.triggerStatus = 0;
                }
            });
            this.trigger.internalFail(new FailCallback() { // from class: aisble.WaitForValueChangedRequest.3
                @Override // aisble.callback.FailCallback
                public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
                    WaitForValueChangedRequest.this.triggerStatus = i2;
                    WaitForValueChangedRequest.this.syncLock.open();
                    WaitForValueChangedRequest.this.notifyFail(bluetoothDevice, i2);
                }
            });
        }
        return this;
    }

    @Override // aisble.TimeoutableValueRequest
    @NonNull
    public <E extends DataReceivedCallback> E await(@NonNull E e2) throws RequestFailedException {
        Request.assertNotMainThread();
        try {
            Request request = this.trigger;
            if (request != null && request.enqueued) {
                throw new IllegalStateException("Trigger request already enqueued");
            }
            super.await((WaitForValueChangedRequest) e2);
            return e2;
        } catch (RequestFailedException e3) {
            int i2 = this.triggerStatus;
            if (i2 != 0) {
                throw new RequestFailedException(this.trigger, i2);
            }
            throw e3;
        }
    }

    @Override // aisble.Request
    @NonNull
    public WaitForValueChangedRequest before(@NonNull BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public WaitForValueChangedRequest done(@NonNull SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public WaitForValueChangedRequest fail(@NonNull FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // aisble.Request
    @NonNull
    public WaitForValueChangedRequest invalid(@NonNull InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // aisble.TimeoutableValueRequest
    @NonNull
    public WaitForValueChangedRequest with(@NonNull DataReceivedCallback dataReceivedCallback) {
        super.with((WaitForValueChangedRequest) dataReceivedCallback);
        return this;
    }

    @NonNull
    public WaitForValueChangedRequest merge(@NonNull DataMerger dataMerger, @NonNull ReadProgressCallback readProgressCallback) {
        this.dataMerger = dataMerger;
        this.progressCallback = readProgressCallback;
        return this;
    }

    @Override // aisble.TimeoutableRequest, aisble.Request
    @NonNull
    public WaitForValueChangedRequest setManager(@NonNull BleManager bleManager) {
        super.setManager(bleManager);
        return this;
    }

    @Override // aisble.TimeoutableValueRequest, aisble.TimeoutableRequest
    @NonNull
    public WaitForValueChangedRequest timeout(@IntRange(from = 0) long j2) {
        super.timeout(j2);
        return this;
    }

    @NonNull
    public <E extends ProfileReadResponse> E awaitValid(@NonNull Class<E> cls) throws InvalidDataException {
        E e2 = (E) await((Class) cls);
        if (e2 == null || e2.isValid()) {
            return e2;
        }
        throw new InvalidDataException(e2);
    }

    @NonNull
    @Deprecated
    public <E extends ProfileReadResponse> E awaitValid(@NonNull Class<E> cls, @IntRange(from = 0) long j2) {
        return (E) timeout(j2).awaitValid(cls);
    }

    @NonNull
    @Deprecated
    public <E extends ProfileReadResponse> E awaitValid(@NonNull E e2, @IntRange(from = 0) long j2) {
        return (E) timeout(j2).awaitValid((WaitForValueChangedRequest) e2);
    }
}
