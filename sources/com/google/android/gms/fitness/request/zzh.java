package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.fitness.zzbj;
import com.google.android.gms.internal.fitness.zzbk;

@ShowFirstParty
@SafeParcelable.Class(creator = "DailyTotalRequestCreator")
@SafeParcelable.Reserved({3, 1000})
/* loaded from: classes3.dex */
public final class zzh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();

    @SafeParcelable.Field(getter = "getCallbackBinder", id = 1, type = "android.os.IBinder")
    private final zzbk zza;

    @Nullable
    @SafeParcelable.Field(getter = "getDataType", id = 2)
    private final DataType zzb;

    @SafeParcelable.Field(getter = "getLocalDataOnly", id = 4)
    private final boolean zzc;

    zzh(IBinder iBinder, DataType dataType, boolean z2) {
        this.zza = zzbj.zzb(iBinder);
        this.zzb = dataType;
        this.zzc = z2;
    }

    public final String toString() {
        DataType dataType = this.zzb;
        return String.format("DailyTotalRequest{%s}", dataType == null ? TmpConstant.GROUP_ROLE_UNKNOWN : dataType.zzc());
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIBinder(parcel, 1, this.zza.asBinder(), false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzc);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzh(zzbk zzbkVar, @Nullable DataType dataType, boolean z2) {
        this.zza = zzbkVar;
        this.zzb = dataType;
        this.zzc = z2;
    }
}
