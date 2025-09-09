package com.google.android.gms.internal.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@ShowFirstParty
@SafeParcelable.Class(creator = "ClearTokenRequestCreator")
/* loaded from: classes3.dex */
public final class zzbw extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbw> CREATOR = new zzbx();

    /* renamed from: a, reason: collision with root package name */
    final int f12993a;

    /* renamed from: b, reason: collision with root package name */
    String f12994b;

    public zzbw() {
        this.f12993a = 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f12993a);
        SafeParcelWriter.writeString(parcel, 2, this.f12994b, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final zzbw zza(String str) {
        this.f12994b = str;
        return this;
    }

    zzbw(int i2, String str) {
        this.f12993a = i2;
        this.f12994b = str;
    }
}
