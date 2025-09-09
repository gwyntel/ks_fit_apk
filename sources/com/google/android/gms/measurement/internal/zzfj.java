package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzfj extends com.google.android.gms.internal.measurement.zzbu implements zzfh {
    zzfj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final zzam zza(zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        Parcel parcelE = e(21, parcelD);
        zzam zzamVar = (zzam) com.google.android.gms.internal.measurement.zzbw.zza(parcelE, zzam.CREATOR);
        parcelE.recycle();
        return zzamVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final String zzb(zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        Parcel parcelE = e(11, parcelD);
        String string = parcelE.readString();
        parcelE.recycle();
        return string;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zzc(zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(4, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zzd(zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(18, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zze(zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(20, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zzf(zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(6, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final List<zzmi> zza(zzo zzoVar, Bundle bundle) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, bundle);
        Parcel parcelE = e(24, parcelD);
        ArrayList arrayListCreateTypedArrayList = parcelE.createTypedArrayList(zzmi.CREATOR);
        parcelE.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final List<zzmz> zza(zzo zzoVar, boolean z2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, z2);
        Parcel parcelE = e(7, parcelD);
        ArrayList arrayListCreateTypedArrayList = parcelE.createTypedArrayList(zzmz.CREATOR);
        parcelE.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final List<zzad> zza(String str, String str2, zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        Parcel parcelE = e(16, parcelD);
        ArrayList arrayListCreateTypedArrayList = parcelE.createTypedArrayList(zzad.CREATOR);
        parcelE.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final List<zzad> zza(String str, String str2, String str3) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        parcelD.writeString(str3);
        Parcel parcelE = e(17, parcelD);
        ArrayList arrayListCreateTypedArrayList = parcelE.createTypedArrayList(zzad.CREATOR);
        parcelE.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final List<zzmz> zza(String str, String str2, boolean z2, zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, z2);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        Parcel parcelE = e(14, parcelD);
        ArrayList arrayListCreateTypedArrayList = parcelE.createTypedArrayList(zzmz.CREATOR);
        parcelE.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final List<zzmz> zza(String str, String str2, String str3, boolean z2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        parcelD.writeString(str2);
        parcelD.writeString(str3);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, z2);
        Parcel parcelE = e(15, parcelD);
        ArrayList arrayListCreateTypedArrayList = parcelE.createTypedArrayList(zzmz.CREATOR);
        parcelE.recycle();
        return arrayListCreateTypedArrayList;
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zza(zzbg zzbgVar, zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzbgVar);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(1, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zza(zzbg zzbgVar, String str, String str2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzbgVar);
        parcelD.writeString(str);
        parcelD.writeString(str2);
        f(5, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zza(zzad zzadVar, zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzadVar);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(12, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zza(zzad zzadVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzadVar);
        f(13, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zza(long j2, String str, String str2, String str3) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeLong(j2);
        parcelD.writeString(str);
        parcelD.writeString(str2);
        parcelD.writeString(str3);
        f(10, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zza(Bundle bundle, zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, bundle);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(19, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final void zza(zzmz zzmzVar, zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzmzVar);
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzoVar);
        f(2, parcelD);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    public final byte[] zza(zzbg zzbgVar, String str) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.measurement.zzbw.zza(parcelD, zzbgVar);
        parcelD.writeString(str);
        Parcel parcelE = e(9, parcelD);
        byte[] bArrCreateByteArray = parcelE.createByteArray();
        parcelE.recycle();
        return bArrCreateByteArray;
    }
}
