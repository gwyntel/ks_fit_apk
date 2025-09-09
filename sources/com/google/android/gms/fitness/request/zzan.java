package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;

@ShowFirstParty
@SafeParcelable.Class(creator = "SensorUnregistrationRequestCreator")
@SafeParcelable.Reserved({4, 1000})
/* loaded from: classes3.dex */
public final class zzan extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzan> CREATOR = new zzao();

    @Nullable
    @SafeParcelable.Field(getter = "getListenerBinder", id = 1, type = "android.os.IBinder")
    private final com.google.android.gms.fitness.data.zzv zza;

    @Nullable
    @SafeParcelable.Field(getter = "getIntent", id = 2)
    private final PendingIntent zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 3, type = "android.os.IBinder")
    private final zzcp zzc;

    zzan(IBinder iBinder, PendingIntent pendingIntent, IBinder iBinder2) {
        this.zza = iBinder == null ? null : com.google.android.gms.fitness.data.zzu.zzb(iBinder);
        this.zzb = pendingIntent;
        this.zzc = iBinder2 != null ? zzco.zzb(iBinder2) : null;
    }

    public final String toString() {
        return String.format("SensorUnregistrationRequest{%s}", this.zza);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        com.google.android.gms.fitness.data.zzv zzvVar = this.zza;
        SafeParcelWriter.writeIBinder(parcel, 1, zzvVar == null ? null : zzvVar.asBinder(), false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i2, false);
        zzcp zzcpVar = this.zzc;
        SafeParcelWriter.writeIBinder(parcel, 3, zzcpVar != null ? zzcpVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzan(@Nullable com.google.android.gms.fitness.data.zzv zzvVar, @Nullable PendingIntent pendingIntent, @Nullable zzcp zzcpVar) {
        this.zza = zzvVar;
        this.zzb = pendingIntent;
        this.zzc = zzcpVar;
    }
}
