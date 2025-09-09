package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ConnectionInfoCreator")
/* loaded from: classes3.dex */
public final class zzk extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzk> CREATOR = new zzl();

    /* renamed from: a, reason: collision with root package name */
    Bundle f12857a;

    /* renamed from: b, reason: collision with root package name */
    Feature[] f12858b;

    /* renamed from: c, reason: collision with root package name */
    int f12859c;

    /* renamed from: d, reason: collision with root package name */
    ConnectionTelemetryConfiguration f12860d;

    zzk(Bundle bundle, Feature[] featureArr, int i2, ConnectionTelemetryConfiguration connectionTelemetryConfiguration) {
        this.f12857a = bundle;
        this.f12858b = featureArr;
        this.f12859c = i2;
        this.f12860d = connectionTelemetryConfiguration;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 1, this.f12857a, false);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.f12858b, i2, false);
        SafeParcelWriter.writeInt(parcel, 3, this.f12859c);
        SafeParcelWriter.writeParcelable(parcel, 4, this.f12860d, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzk() {
    }
}
