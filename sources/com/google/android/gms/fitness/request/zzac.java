package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fitness.zzev;
import com.google.android.gms.internal.fitness.zzew;

@SafeParcelable.Class(creator = "ListClaimedBleDevicesRequestCreator")
@SafeParcelable.Reserved({2, 1000})
@Deprecated
@ShowFirstParty
/* loaded from: classes3.dex */
public final class zzac extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzac> CREATOR = new zzad();

    @SafeParcelable.Field(getter = "getCallbackBinder", id = 1, type = "android.os.IBinder")
    private final zzew zza;

    zzac(IBinder iBinder) {
        this.zza = zzev.zzc(iBinder);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIBinder(parcel, 1, this.zza.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzac(zzew zzewVar) {
        this.zza = zzewVar;
    }
}
