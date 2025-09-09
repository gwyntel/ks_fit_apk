package com.google.android.gms.internal.fido;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialCreationOptions;
import com.google.android.gms.fido.fido2.api.common.PublicKeyCredentialRequestOptions;

/* loaded from: classes3.dex */
public final class zzs extends zza implements IInterface {
    zzs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fido.fido2.internal.regular.IFido2AppService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzc(zzr zzrVar, PublicKeyCredentialCreationOptions publicKeyCredentialCreationOptions) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzrVar);
        zzc.zzd(parcelD, publicKeyCredentialCreationOptions);
        e(1, parcelD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzd(zzr zzrVar, PublicKeyCredentialRequestOptions publicKeyCredentialRequestOptions) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzrVar);
        zzc.zzd(parcelD, publicKeyCredentialRequestOptions);
        e(2, parcelD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zze(zze zzeVar) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzeVar);
        e(3, parcelD);
    }
}
