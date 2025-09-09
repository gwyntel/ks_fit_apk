package com.google.android.gms.internal.fido;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fido.fido2.api.common.BrowserPublicKeyCredentialCreationOptions;
import com.google.android.gms.fido.fido2.api.common.BrowserPublicKeyCredentialRequestOptions;

/* loaded from: classes3.dex */
public final class zzn extends zza implements IInterface {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fido.fido2.internal.privileged.IFido2PrivilegedService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzc(zzg zzgVar, String str) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzgVar);
        parcelD.writeString(str);
        e(4, parcelD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzd(zzm zzmVar, BrowserPublicKeyCredentialCreationOptions browserPublicKeyCredentialCreationOptions) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzmVar);
        zzc.zzd(parcelD, browserPublicKeyCredentialCreationOptions);
        e(1, parcelD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zze(zzm zzmVar, BrowserPublicKeyCredentialRequestOptions browserPublicKeyCredentialRequestOptions) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzmVar);
        zzc.zzd(parcelD, browserPublicKeyCredentialRequestOptions);
        e(2, parcelD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzf(zze zzeVar) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzeVar);
        e(3, parcelD);
    }
}
