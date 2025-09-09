package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

@KeepName
@SafeParcelable.Class(creator = "RawDataSetCreator")
@SafeParcelable.Reserved({2, 4, 1000})
/* loaded from: classes3.dex */
public final class RawDataSet extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<RawDataSet> CREATOR = new zzaa();

    @SafeParcelable.Field(id = 1)
    public final int zza;

    @NonNull
    @SafeParcelable.Field(id = 3)
    public final List zzb;

    @SafeParcelable.Constructor
    public RawDataSet(@SafeParcelable.Param(id = 1) int i2, @NonNull @SafeParcelable.Param(id = 3) List list) {
        this.zza = i2;
        this.zzb = list;
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RawDataSet)) {
            return false;
        }
        RawDataSet rawDataSet = (RawDataSet) obj;
        return this.zza == rawDataSet.zza && Objects.equal(this.zzb, rawDataSet.zzb);
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza));
    }

    @NonNull
    public final String toString() {
        return String.format("RawDataSet{%s@[%s]}", Integer.valueOf(this.zza), this.zzb);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zza);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public RawDataSet(@NonNull DataSet dataSet, @NonNull List list) {
        this.zzb = dataSet.a(list);
        this.zza = com.google.android.gms.internal.fitness.zzd.zza(dataSet.getDataSource(), list);
    }
}
