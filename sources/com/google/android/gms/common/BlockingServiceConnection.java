package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@KeepForSdk
/* loaded from: classes3.dex */
public class BlockingServiceConnection implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    boolean f12681a = false;
    private final BlockingQueue zzb = new LinkedBlockingQueue();

    @NonNull
    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public IBinder getService() throws InterruptedException {
        Preconditions.checkNotMainThread("BlockingServiceConnection.getService() called on main thread");
        if (this.f12681a) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.f12681a = true;
        return (IBinder) this.zzb.take();
    }

    @NonNull
    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public IBinder getServiceWithTimeout(long j2, @NonNull TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        Preconditions.checkNotMainThread("BlockingServiceConnection.getServiceWithTimeout() called on main thread");
        if (this.f12681a) {
            throw new IllegalStateException("Cannot call get on this connection more than once");
        }
        this.f12681a = true;
        IBinder iBinder = (IBinder) this.zzb.poll(j2, timeUnit);
        if (iBinder != null) {
            return iBinder;
        }
        throw new TimeoutException("Timed out waiting for the service connection");
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(@NonNull ComponentName componentName, @NonNull IBinder iBinder) {
        this.zzb.add(iBinder);
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(@NonNull ComponentName componentName) {
    }
}
