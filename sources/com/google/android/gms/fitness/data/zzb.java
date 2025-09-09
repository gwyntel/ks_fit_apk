package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@ShowFirstParty
@SafeParcelable.Class(creator = "ApplicationCreator")
@SafeParcelable.Reserved({2, 3, 1000})
/* loaded from: classes3.dex */
public final class zzb extends AbstractSafeParcelable {

    @SafeParcelable.Field(getter = "getPackageName", id = 1)
    private final String zzb;
    public static final zzb zza = new zzb("com.google.android.gms");
    public static final Parcelable.Creator<zzb> CREATOR = new zzc();

    @SafeParcelable.Constructor
    public zzb(@SafeParcelable.Param(id = 1) String str) {
        this.zzb = (String) Preconditions.checkNotNull(str);
    }

    public static zzb zza(String str) {
        return "com.google.android.gms".equals(str) ? zza : new zzb(str);
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzb) {
            return this.zzb.equals(((zzb) obj).zzb);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzb.hashCode();
    }

    public final String toString() {
        return String.format("Application{%s}", this.zzb);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzb, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzb() {
        return this.zzb;
    }
}
