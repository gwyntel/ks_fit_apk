package com.google.android.gms.internal.fido;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fido.u2f.api.common.RegisterRequestParams;
import com.google.android.gms.fido.u2f.api.common.SignRequestParams;

/* loaded from: classes3.dex */
public final class zzw extends zza implements IInterface {
    zzw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.fido.u2f.internal.regular.IU2fAppService");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzc(zzv zzvVar, RegisterRequestParams registerRequestParams) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzvVar);
        zzc.zzd(parcelD, registerRequestParams);
        e(1, parcelD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void zzd(zzv zzvVar, SignRequestParams signRequestParams) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeStrongBinder(zzvVar);
        zzc.zzd(parcelD, signRequestParams);
        e(2, parcelD);
    }
}
