package com.google.android.gms.fitness.request;

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
@SafeParcelable.Class(creator = "UnclaimBleDeviceRequestCreator")
@SafeParcelable.Reserved({1, 4, 1000})
/* loaded from: classes3.dex */
public final class zzbf extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbf> CREATOR = new zzbg();

    @SafeParcelable.Field(getter = "getDeviceAddress", id = 2)
    private final String zza;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 3, type = "android.os.IBinder")
    private final zzcp zzb;

    zzbf(String str, IBinder iBinder) {
        this.zza = str;
        this.zzb = iBinder == null ? null : zzco.zzb(iBinder);
    }

    public final String toString() {
        return String.format("UnclaimBleDeviceRequest{%s}", this.zza);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        zzcp zzcpVar = this.zzb;
        SafeParcelWriter.writeIBinder(parcel, 3, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzbf(String str, @Nullable zzcp zzcpVar) {
        this.zza = str;
        this.zzb = zzcpVar;
    }
}
