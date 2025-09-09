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
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;

@ShowFirstParty
@SafeParcelable.Class(creator = "SubscribeRequestCreator")
@SafeParcelable.Reserved({4, 1000})
/* loaded from: classes3.dex */
public final class zzbd extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbd> CREATOR = new zzbe();

    @Nullable
    @SafeParcelable.Field(getter = "getSubscription", id = 1)
    private final Subscription zza;

    @SafeParcelable.Field(getter = "isServerOnly", id = 2)
    private final boolean zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 3, type = "android.os.IBinder")
    private final zzcp zzc;

    zzbd(Subscription subscription, boolean z2, IBinder iBinder) {
        this.zza = subscription;
        this.zzb = z2;
        this.zzc = iBinder == null ? null : zzco.zzb(iBinder);
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("subscription", this.zza).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzb);
        zzcp zzcpVar = this.zzc;
        SafeParcelWriter.writeIBinder(parcel, 3, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzbd(@Nullable Subscription subscription, boolean z2, @Nullable zzcp zzcpVar) {
        this.zza = subscription;
        this.zzb = false;
        this.zzc = zzcpVar;
    }
}
