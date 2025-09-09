package com.google.android.gms.internal.auth;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyRequest;

/* loaded from: classes3.dex */
public final class zzbh extends zza implements IInterface {
    zzbh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.internal.IAuthService");
    }

    public final void zzd(zzbg zzbgVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzbgVar);
        f(3, parcelD);
    }

    public final void zze(zzbg zzbgVar, ProxyRequest proxyRequest) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzd(parcelD, zzbgVar);
        zzc.zzc(parcelD, proxyRequest);
        f(1, parcelD);
    }
}
