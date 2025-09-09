package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

/* loaded from: classes3.dex */
public final class zzf extends zza {

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ BaseGmsClient f12854c;

    @Nullable
    public final IBinder zze;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @BinderThread
    public zzf(BaseGmsClient baseGmsClient, @Nullable int i2, @Nullable IBinder iBinder, Bundle bundle) {
        super(baseGmsClient, i2, bundle);
        this.f12854c = baseGmsClient;
        this.zze = iBinder;
    }

    @Override // com.google.android.gms.common.internal.zza
    protected final void c(ConnectionResult connectionResult) {
        if (this.f12854c.zzx != null) {
            this.f12854c.zzx.onConnectionFailed(connectionResult);
        }
        this.f12854c.j(connectionResult);
    }

    @Override // com.google.android.gms.common.internal.zza
    protected final boolean d() throws RemoteException {
        try {
            IBinder iBinder = this.zze;
            Preconditions.checkNotNull(iBinder);
            String interfaceDescriptor = iBinder.getInterfaceDescriptor();
            if (!this.f12854c.getServiceDescriptor().equals(interfaceDescriptor)) {
                Log.w("GmsClient", "service descriptor mismatch: " + this.f12854c.getServiceDescriptor() + " vs. " + interfaceDescriptor);
                return false;
            }
            IInterface iInterfaceCreateServiceInterface = this.f12854c.createServiceInterface(this.zze);
            if (iInterfaceCreateServiceInterface == null || !(BaseGmsClient.z(this.f12854c, 2, 4, iInterfaceCreateServiceInterface) || BaseGmsClient.z(this.f12854c, 3, 4, iInterfaceCreateServiceInterface))) {
                return false;
            }
            this.f12854c.zzB = null;
            BaseGmsClient baseGmsClient = this.f12854c;
            Bundle connectionHint = baseGmsClient.getConnectionHint();
            if (baseGmsClient.zzw == null) {
                return true;
            }
            this.f12854c.zzw.onConnected(connectionHint);
            return true;
        } catch (RemoteException unused) {
            Log.w("GmsClient", "service probably died");
            return false;
        }
    }
}
