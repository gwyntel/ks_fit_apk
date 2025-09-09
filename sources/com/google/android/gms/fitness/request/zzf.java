package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;

@ShowFirstParty
@SafeParcelable.Class(creator = "ClaimBleDeviceRequestCreator")
@SafeParcelable.Reserved({4, 1000})
/* loaded from: classes3.dex */
public final class zzf extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzf> CREATOR = new zzg();

    @SafeParcelable.Field(getter = "getDeviceAddress", id = 1)
    private final String zza;

    @Nullable
    @SafeParcelable.Field(getter = "getBleDevice", id = 2)
    private final BleDevice zzb;

    @SafeParcelable.Field(getter = "getCallbackBinder", id = 3, type = "android.os.IBinder")
    private final zzcp zzc;

    zzf(String str, BleDevice bleDevice, IBinder iBinder) {
        this.zza = str;
        this.zzb = bleDevice;
        this.zzc = zzco.zzb(iBinder);
    }

    public final String toString() {
        return String.format("ClaimBleDeviceRequest{%s %s}", this.zza, this.zzb);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i2, false);
        zzcp zzcpVar = this.zzc;
        SafeParcelWriter.writeIBinder(parcel, 3, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzf(String str, @Nullable BleDevice bleDevice, zzcp zzcpVar) {
        this.zza = str;
        this.zzb = bleDevice;
        this.zzc = zzcpVar;
    }
}
