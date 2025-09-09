package com.google.android.gms.internal.identity;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LastLocationRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SleepSegmentRequest;
import com.google.android.gms.location.zzad;
import com.google.android.gms.location.zzb;

/* loaded from: classes3.dex */
public final class zzu extends zza implements zzv {
    zzu(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzA(Location location) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, location);
        f(13, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzB(Location location, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, location);
        zzc.zzc(parcelD, iStatusCallback);
        f(85, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzC(zzr zzrVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzrVar);
        f(67, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzD(LocationSettingsRequest locationSettingsRequest, zzab zzabVar, String str) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, locationSettingsRequest);
        zzc.zzc(parcelD, zzabVar);
        parcelD.writeString(null);
        f(63, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzE(zzo zzoVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzc(parcelD, zzoVar);
        f(95, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzF(zzj zzjVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzjVar);
        f(75, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzd(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, zzt zztVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, geofencingRequest);
        zzc.zzb(parcelD, pendingIntent);
        zzc.zzc(parcelD, zztVar);
        f(57, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zze(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, geofencingRequest);
        zzc.zzb(parcelD, pendingIntent);
        zzc.zzc(parcelD, iStatusCallback);
        f(97, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzf(zzem zzemVar, zzt zztVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzemVar);
        zzc.zzc(parcelD, zztVar);
        f(74, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzg(zzem zzemVar, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzemVar);
        zzc.zzc(parcelD, iStatusCallback);
        f(98, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzh(long j2, boolean z2, PendingIntent pendingIntent) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeLong(j2);
        int i2 = zzc.zza;
        parcelD.writeInt(1);
        zzc.zzb(parcelD, pendingIntent);
        f(5, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzi(zzb zzbVar, PendingIntent pendingIntent, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzbVar);
        zzc.zzb(parcelD, pendingIntent);
        zzc.zzc(parcelD, iStatusCallback);
        f(70, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzj(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, activityTransitionRequest);
        zzc.zzb(parcelD, pendingIntent);
        zzc.zzc(parcelD, iStatusCallback);
        f(72, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzk(PendingIntent pendingIntent, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, pendingIntent);
        zzc.zzc(parcelD, iStatusCallback);
        f(73, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzl(PendingIntent pendingIntent) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, pendingIntent);
        f(6, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzm(PendingIntent pendingIntent, SleepSegmentRequest sleepSegmentRequest, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, pendingIntent);
        zzc.zzb(parcelD, sleepSegmentRequest);
        zzc.zzc(parcelD, iStatusCallback);
        f(79, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzn(PendingIntent pendingIntent, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, pendingIntent);
        zzc.zzc(parcelD, iStatusCallback);
        f(69, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzo(zzad zzadVar, zzee zzeeVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzadVar);
        zzc.zzb(parcelD, zzeeVar);
        f(91, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final LocationAvailability zzp(String str) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeString(str);
        Parcel parcelE = e(34, parcelD);
        LocationAvailability locationAvailability = (LocationAvailability) zzc.zza(parcelE, LocationAvailability.CREATOR);
        parcelE.recycle();
        return locationAvailability;
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzq(LastLocationRequest lastLocationRequest, zzee zzeeVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, lastLocationRequest);
        zzc.zzb(parcelD, zzeeVar);
        f(90, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzr(LastLocationRequest lastLocationRequest, zzz zzzVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, lastLocationRequest);
        zzc.zzc(parcelD, zzzVar);
        f(82, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final Location zzs() throws RemoteException {
        Parcel parcelE = e(7, d());
        Location location = (Location) zzc.zza(parcelE, Location.CREATOR);
        parcelE.recycle();
        return location;
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final ICancelToken zzt(CurrentLocationRequest currentLocationRequest, zzee zzeeVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, currentLocationRequest);
        zzc.zzb(parcelD, zzeeVar);
        Parcel parcelE = e(92, parcelD);
        ICancelToken iCancelTokenAsInterface = ICancelToken.Stub.asInterface(parcelE.readStrongBinder());
        parcelE.recycle();
        return iCancelTokenAsInterface;
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final ICancelToken zzu(CurrentLocationRequest currentLocationRequest, zzz zzzVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, currentLocationRequest);
        zzc.zzc(parcelD, zzzVar);
        Parcel parcelE = e(87, parcelD);
        ICancelToken iCancelTokenAsInterface = ICancelToken.Stub.asInterface(parcelE.readStrongBinder());
        parcelE.recycle();
        return iCancelTokenAsInterface;
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzv(zzei zzeiVar) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzeiVar);
        f(59, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzw(zzee zzeeVar, LocationRequest locationRequest, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzeeVar);
        zzc.zzb(parcelD, locationRequest);
        zzc.zzc(parcelD, iStatusCallback);
        f(88, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzx(zzee zzeeVar, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        zzc.zzb(parcelD, zzeeVar);
        zzc.zzc(parcelD, iStatusCallback);
        f(89, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzy(boolean z2) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeInt(z2 ? 1 : 0);
        f(12, parcelD);
    }

    @Override // com.google.android.gms.internal.identity.zzv
    public final void zzz(boolean z2, IStatusCallback iStatusCallback) throws RemoteException {
        Parcel parcelD = d();
        int i2 = zzc.zza;
        parcelD.writeInt(z2 ? 1 : 0);
        zzc.zzc(parcelD, iStatusCallback);
        f(84, parcelD);
    }
}
