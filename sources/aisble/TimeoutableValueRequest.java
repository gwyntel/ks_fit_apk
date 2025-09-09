package aisble;

import aisble.Request;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class TimeoutableValueRequest<T> extends TimeoutableRequest {
    public T valueCallback;

    public TimeoutableValueRequest(@NonNull Request.Type type) {
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
    public TimeoutableValueRequest<T> with(@NonNull T t2) {
        this.valueCallback = t2;
        return this;
    }

    public TimeoutableValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    @Override // aisble.TimeoutableRequest
    @NonNull
    public TimeoutableValueRequest<T> timeout(long j2) {
        super.timeout(j2);
        return this;
    }

    public TimeoutableValueRequest(@NonNull Request.Type type, @Nullable BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
    }

    @NonNull
    public <E extends T> E await(@NonNull Class<E> cls) {
        Request.assertNotMainThread();
        try {
            return (E) await((TimeoutableValueRequest<T>) cls.newInstance());
        } catch (IllegalAccessException unused) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Is the default constructor accessible?");
        } catch (InstantiationException unused2) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Does it have a default constructor with no arguments?");
        }
    }

    @NonNull
    @Deprecated
    public <E extends T> E await(@NonNull Class<E> cls, @IntRange(from = 0) long j2) {
        return (E) timeout(j2).await((Class) cls);
    }

    @NonNull
    @Deprecated
    public <E extends T> E await(@NonNull E e2, @IntRange(from = 0) long j2) {
        return (E) timeout(j2).await((TimeoutableValueRequest<T>) e2);
    }
}
