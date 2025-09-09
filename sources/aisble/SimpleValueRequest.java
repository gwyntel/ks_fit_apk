package aisble;

import aisble.Request;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class SimpleValueRequest<T> extends SimpleRequest {
    public T valueCallback;

    public SimpleValueRequest(@NonNull Request.Type type) {
        super(type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    public <E extends T> E await(@NonNull E e2) {
        Request.assertNotMainThread();
        T t2 = this.valueCallback;
        try {
            with(e2).await();
            return e2;
        } finally {
            this.valueCallback = t2;
        }
    }

    @NonNull
    public SimpleValueRequest<T> with(@NonNull T t2) {
        this.valueCallback = t2;
        return this;
    }

    public SimpleValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    public SimpleValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
    }

    @NonNull
    public <E extends T> E await(@NonNull Class<E> cls) {
        Request.assertNotMainThread();
        try {
            return (E) await((SimpleValueRequest<T>) cls.newInstance());
        } catch (IllegalAccessException unused) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Is the default constructor accessible?");
        } catch (InstantiationException unused2) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Does it have a default constructor with no arguments?");
        }
    }
}
