package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.fitness.zzcf;
import com.google.android.gms.internal.fitness.zzcg;

@ShowFirstParty
@SafeParcelable.Class(creator = "ListSubscriptionsRequestCreator")
@SafeParcelable.Reserved({3, 1000})
/* loaded from: classes3.dex */
public final class zzae extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzae> CREATOR = new zzaf();

    @Nullable
    @SafeParcelable.Field(getter = "getDataType", id = 1)
    private final DataType zza;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 2, type = "android.os.IBinder")
    private final zzcg zzb;

    zzae(DataType dataType, IBinder iBinder) {
        this.zza = dataType;
        this.zzb = iBinder == null ? null : zzcf.zzb(iBinder);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i2, false);
        zzcg zzcgVar = this.zzb;
        SafeParcelWriter.writeIBinder(parcel, 2, zzcgVar == null ? null : zzcgVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzae(@Nullable DataType dataType, zzcg zzcgVar) {
        this.zza = dataType;
        this.zzb = zzcgVar;
    }
}
