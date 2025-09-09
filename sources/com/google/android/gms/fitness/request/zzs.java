package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;

@SafeParcelable.Class(creator = "DataUpdateListenerUnregistrationRequestCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class zzs extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzs> CREATOR = new zzt();

    @SafeParcelable.Field(getter = "getIntent", id = 1)
    private final PendingIntent zza;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 2, type = "android.os.IBinder")
    private final zzcp zzb;

    @SafeParcelable.Constructor
    public zzs(@SafeParcelable.Param(id = 1) PendingIntent pendingIntent, @Nullable @SafeParcelable.Param(id = 2) IBinder iBinder) {
        this.zza = pendingIntent;
        this.zzb = iBinder == null ? null : zzco.zzb(iBinder);
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj != this) {
            return (obj instanceof zzs) && Objects.equal(this.zza, ((zzs) obj).zza);
        }
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza);
    }

    public final String toString() {
        return "DataUpdateListenerUnregistrationRequest";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i2, false);
        zzcp zzcpVar = this.zzb;
        SafeParcelWriter.writeIBinder(parcel, 2, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
