package com.google.android.gms.internal.fitness;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.fitness.service.FitnessSensorServiceRequest;

/* loaded from: classes3.dex */
public abstract class zzfb extends zzb implements zzfc {
    public zzfb() {
        super("com.google.android.gms.fitness.internal.service.IFitnessSensorService");
    }

    @Override // com.google.android.gms.internal.fitness.zzb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 == 1) {
            zzex zzexVar = (zzex) zzc.zza(parcel, zzex.CREATOR);
            zzbq zzbqVarZzc = zzbp.zzc(parcel.readStrongBinder());
            zzc.zzb(parcel);
            zzb(zzexVar, zzbqVarZzc);
        } else if (i2 == 2) {
            FitnessSensorServiceRequest fitnessSensorServiceRequest = (FitnessSensorServiceRequest) zzc.zza(parcel, FitnessSensorServiceRequest.CREATOR);
            zzcp zzcpVarZzb = zzco.zzb(parcel.readStrongBinder());
            zzc.zzb(parcel);
            zzc(fitnessSensorServiceRequest, zzcpVarZzb);
        } else {
            if (i2 != 3) {
                return false;
            }
            zzez zzezVar = (zzez) zzc.zza(parcel, zzez.CREATOR);
            zzcp zzcpVarZzb2 = zzco.zzb(parcel.readStrongBinder());
            zzc.zzb(parcel);
            zzd(zzezVar, zzcpVarZzb2);
        }
        return true;
    }
}
