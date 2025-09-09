package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.fitness.zzco;
import com.google.android.gms.internal.fitness.zzcp;
import java.util.concurrent.TimeUnit;

@ShowFirstParty
@SafeParcelable.Class(creator = "SessionStartRequestCreator")
@SafeParcelable.Reserved({3, 1000})
/* loaded from: classes3.dex */
public final class zzat extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzat> CREATOR = new zzau();

    @SafeParcelable.Field(getter = "getSession", id = 1)
    private final Session zza;

    @Nullable
    @SafeParcelable.Field(getter = "getCallbackBinder", id = 2, type = "android.os.IBinder")
    private final zzcp zzb;

    zzat(Session session, IBinder iBinder) {
        this.zza = session;
        this.zzb = iBinder == null ? null : zzco.zzb(iBinder);
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj != this) {
            return (obj instanceof zzat) && Objects.equal(this.zza, ((zzat) obj).zza);
        }
        return true;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zza);
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("session", this.zza).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i2, false);
        zzcp zzcpVar = this.zzb;
        SafeParcelWriter.writeIBinder(parcel, 2, zzcpVar == null ? null : zzcpVar.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzat(Session session, @Nullable zzcp zzcpVar) {
        Preconditions.checkArgument(session.getStartTime(TimeUnit.MILLISECONDS) <= System.currentTimeMillis(), "Cannot start a session in the future");
        Preconditions.checkArgument(session.isOngoing(), "Cannot start a session which has already ended");
        this.zza = session;
        this.zzb = zzcpVar;
    }
}
