package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "StringToIntConverterEntryCreator")
/* loaded from: classes3.dex */
public final class zac extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zac> CREATOR = new zae();

    /* renamed from: a, reason: collision with root package name */
    final int f12877a;

    /* renamed from: b, reason: collision with root package name */
    final String f12878b;

    /* renamed from: c, reason: collision with root package name */
    final int f12879c;

    zac(int i2, String str, int i3) {
        this.f12877a = i2;
        this.f12878b = str;
        this.f12879c = i3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int i3 = this.f12877a;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, i3);
        SafeParcelWriter.writeString(parcel, 2, this.f12878b, false);
        SafeParcelWriter.writeInt(parcel, 3, this.f12879c);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    zac(String str, int i2) {
        this.f12877a = 1;
        this.f12878b = str;
        this.f12879c = i2;
    }
}
