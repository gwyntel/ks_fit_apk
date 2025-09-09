package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.umeng.analytics.pro.bc;
import java.util.Iterator;

@SafeParcelable.Class(creator = "EventParamsCreator")
@SafeParcelable.Reserved({1})
/* loaded from: classes3.dex */
public final class zzbb extends AbstractSafeParcelable implements Iterable<String> {
    public static final Parcelable.Creator<zzbb> CREATOR = new zzbd();

    @SafeParcelable.Field(getter = bc.aJ, id = 2)
    private final Bundle zza;

    zzbb(Bundle bundle) {
        this.zza = bundle;
    }

    final Double b(String str) {
        return Double.valueOf(this.zza.getDouble(str));
    }

    final Long c(String str) {
        return Long.valueOf(this.zza.getLong(str));
    }

    final Object d(String str) {
        return this.zza.get(str);
    }

    final String e(String str) {
        return this.zza.getString(str);
    }

    @Override // java.lang.Iterable
    public final Iterator<String> iterator() {
        return new zzbe(this);
    }

    public final String toString() {
        return this.zza.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, zzb(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final int zza() {
        return this.zza.size();
    }

    public final Bundle zzb() {
        return new Bundle(this.zza);
    }
}
