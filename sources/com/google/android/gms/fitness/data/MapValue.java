package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@ShowFirstParty
@SafeParcelable.Class(creator = "MapValueCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public class MapValue extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<MapValue> CREATOR = new zzw();

    @SafeParcelable.Field(getter = "getFormat", id = 1)
    private final int zza;

    @SafeParcelable.Field(getter = "getValue", id = 2)
    private final float zzb;

    @SafeParcelable.Constructor
    public MapValue(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) float f2) {
        this.zza = i2;
        this.zzb = f2;
    }

    @NonNull
    public static MapValue zzb(float f2) {
        return new MapValue(2, f2);
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MapValue)) {
            return false;
        }
        MapValue mapValue = (MapValue) obj;
        int i2 = this.zza;
        if (i2 == mapValue.zza) {
            if (i2 != 2) {
                return this.zzb == mapValue.zzb;
            }
            if (zza() == mapValue.zza()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (int) this.zzb;
    }

    @NonNull
    public final String toString() {
        return this.zza != 2 ? "unknown" : Float.toString(zza());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zza);
        SafeParcelWriter.writeFloat(parcel, 2, this.zzb);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final float zza() {
        Preconditions.checkState(this.zza == 2, "Value is not in float format");
        return this.zzb;
    }
}
