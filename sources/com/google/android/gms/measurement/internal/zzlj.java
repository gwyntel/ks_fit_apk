package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;

/* loaded from: classes3.dex */
public final class zzlj implements ServiceConnection, BaseGmsClient.BaseConnectionCallbacks, BaseGmsClient.BaseOnConnectionFailedListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzkq f13301a;
    private volatile boolean zzb;
    private volatile zzfp zzc;

    protected zzlj(zzkq zzkqVar) {
        this.f13301a = zzkqVar;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    @MainThread
    public final void onConnected(Bundle bundle) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                Preconditions.checkNotNull(this.zzc);
                this.f13301a.zzl().zzb(new zzlo(this, this.zzc.getService()));
            } catch (DeadObjectException | IllegalStateException unused) {
                this.zzc = null;
                this.zzb = false;
            }
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener
    @MainThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) throws IllegalStateException {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionFailed");
        zzfs zzfsVarZzm = this.f13301a.f13286a.zzm();
        if (zzfsVarZzm != null) {
            zzfsVarZzm.zzu().zza("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzb = false;
            this.zzc = null;
        }
        this.f13301a.zzl().zzb(new zzlq(this));
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    @MainThread
    public final void onConnectionSuspended(int i2) throws IllegalStateException {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionSuspended");
        this.f13301a.zzj().zzc().zza("Service connection suspended");
        this.f13301a.zzl().zzb(new zzln(this));
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                this.zzb = false;
                this.f13301a.zzj().zzg().zza("Service connected with null binder");
                return;
            }
            zzfh zzfjVar = null;
            try {
                String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                    zzfjVar = iInterfaceQueryLocalInterface instanceof zzfh ? (zzfh) iInterfaceQueryLocalInterface : new zzfj(iBinder);
                    this.f13301a.zzj().zzp().zza("Bound to IMeasurementService interface");
                } else {
                    this.f13301a.zzj().zzg().zza("Got binder with a wrong descriptor", interfaceDescriptor);
                }
            } catch (RemoteException unused) {
                this.f13301a.zzj().zzg().zza("Service connect failed to get IMeasurementService");
            }
            if (zzfjVar == null) {
                this.zzb = false;
                try {
                    ConnectionTracker.getInstance().unbindService(this.f13301a.zza(), this.f13301a.zza);
                } catch (IllegalArgumentException unused2) {
                }
            } else {
                this.f13301a.zzl().zzb(new zzlm(this, zzfjVar));
            }
        }
    }

    @Override // android.content.ServiceConnection
    @MainThread
    public final void onServiceDisconnected(ComponentName componentName) throws IllegalStateException {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceDisconnected");
        this.f13301a.zzj().zzc().zza("Service disconnected");
        this.f13301a.zzl().zzb(new zzll(this, componentName));
    }

    @WorkerThread
    public final void zza(Intent intent) {
        this.f13301a.zzt();
        Context contextZza = this.f13301a.zza();
        ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
        synchronized (this) {
            try {
                if (this.zzb) {
                    this.f13301a.zzj().zzp().zza("Connection attempt already in progress");
                    return;
                }
                this.f13301a.zzj().zzp().zza("Using local app measurement service");
                this.zzb = true;
                connectionTracker.bindService(contextZza, intent, this.f13301a.zza, 129);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @WorkerThread
    public final void zzb() {
        if (this.zzc != null && (this.zzc.isConnected() || this.zzc.isConnecting())) {
            this.zzc.disconnect();
        }
        this.zzc = null;
    }

    @WorkerThread
    public final void zza() {
        this.f13301a.zzt();
        Context contextZza = this.f13301a.zza();
        synchronized (this) {
            try {
                if (this.zzb) {
                    this.f13301a.zzj().zzp().zza("Connection attempt already in progress");
                    return;
                }
                if (this.zzc != null && (this.zzc.isConnecting() || this.zzc.isConnected())) {
                    this.f13301a.zzj().zzp().zza("Already awaiting connection attempt");
                    return;
                }
                this.zzc = new zzfp(contextZza, Looper.getMainLooper(), this, this);
                this.f13301a.zzj().zzp().zza("Connecting to remote service");
                this.zzb = true;
                Preconditions.checkNotNull(this.zzc);
                this.zzc.checkAvailabilityAndConnect();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
