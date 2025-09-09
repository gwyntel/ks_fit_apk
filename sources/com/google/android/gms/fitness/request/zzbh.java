package com.google.android.gms.fitness.request;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.fitness.zzcp;

@ShowFirstParty
@SafeParcelable.Class(creator = "UnsubscribeRequestCreator")
@SafeParcelable.Reserved({4, 1000})
/* loaded from: classes3.dex */
public final class zzbh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbh> CREATOR = new zzbi();

    @Nullable
    @SafeParcelable.Field(getter = "getDataType", id = 1)
    private final DataType zza;

    @Nullable
    @SafeParcelable.Field(getter = "getDataSource", id = 2)
    private final DataSource zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 3, type = "android.os.IBinder")
    private final zzcp zzc;

    public zzbh(@Nullable DataType dataType, @Nullable DataSource dataSource, @Nullable zzcp zzcpVar) {
        Preconditions.checkArgument((dataType == null) != (dataSource == null), "Must specify exactly one of dataType and dataSource.");
        this.zza = dataType;
        this.zzb = dataSource;
        this.zzc = zzcpVar;
    }

    public final boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbh)) {
            return false;
        }
        zzbh zzbhVar = (zzbh) obj;
        return Objects.equal(this.zzb, zzbhVar.zzb) && Objects.equal(this.zza, zzbhVar.zza);
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzb, this.zza);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i2, false);
        zzcp zzcpVar = this.zzc;
        SafeParcelWriter.writeIBinder(parcel, 3, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
