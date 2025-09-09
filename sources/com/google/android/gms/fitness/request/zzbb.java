package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;

@SafeParcelable.Class(creator = "StopBleScanRequestCreator")
@SafeParcelable.Reserved({3, 1000})
@Deprecated
@ShowFirstParty
/* loaded from: classes3.dex */
public final class zzbb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbb> CREATOR = new zzbc();

    @SafeParcelable.Field(getter = "getBleScanCallbackBinder", id = 1, type = "android.os.IBinder")
    private final zzab zza;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 2, type = "android.os.IBinder")
    private final zzcp zzb;

    zzbb(IBinder iBinder, IBinder iBinder2) {
        zzab zzzVar;
        if (iBinder == null) {
            zzzVar = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.request.IBleScanCallback");
            zzzVar = iInterfaceQueryLocalInterface instanceof zzab ? (zzab) iInterfaceQueryLocalInterface : new zzz(iBinder);
        }
        this.zza = zzzVar;
        this.zzb = iBinder2 != null ? zzco.zzb(iBinder2) : null;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIBinder(parcel, 1, this.zza.asBinder(), false);
        zzcp zzcpVar = this.zzb;
        SafeParcelWriter.writeIBinder(parcel, 2, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzbb(zzab zzabVar, @Nullable zzcp zzcpVar) {
        this.zza = zzabVar;
        this.zzb = zzcpVar;
    }
}
