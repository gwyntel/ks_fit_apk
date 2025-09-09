package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;

@ShowFirstParty
@SafeParcelable.Class(creator = "DataInsertRequestCreator")
@SafeParcelable.Reserved({3, 1000})
/* loaded from: classes3.dex */
public final class zzk extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzk> CREATOR = new zzl();

    @SafeParcelable.Field(getter = "getDataSet", id = 1)
    private final DataSet zza;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 2, type = "android.os.IBinder")
    private final zzcp zzb;

    @SafeParcelable.Field(getter = "shouldSkipSync", id = 4)
    private final boolean zzc;

    zzk(DataSet dataSet, IBinder iBinder, boolean z2) {
        this.zza = dataSet;
        this.zzb = iBinder == null ? null : zzco.zzb(iBinder);
        this.zzc = z2;
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj != this) {
            return (obj instanceof zzk) && Objects.equal(this.zza, ((zzk) obj).zza);
        }
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza);
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("dataSet", this.zza).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i2, false);
        zzcp zzcpVar = this.zzb;
        SafeParcelWriter.writeIBinder(parcel, 2, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzc);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzk(DataSet dataSet, zzcp zzcpVar, boolean z2) {
        this.zza = dataSet;
        this.zzb = zzcpVar;
        this.zzc = false;
    }
}
