package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;

@ShowFirstParty
@SafeParcelable.Class(creator = "FieldMapPairCreator")
/* loaded from: classes3.dex */
public final class zam extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zam> CREATOR = new zak();

    /* renamed from: a, reason: collision with root package name */
    final int f12891a;

    /* renamed from: b, reason: collision with root package name */
    final String f12892b;

    /* renamed from: c, reason: collision with root package name */
    final FastJsonResponse.Field f12893c;

    zam(int i2, String str, FastJsonResponse.Field field) {
        this.f12891a = i2;
        this.f12892b = str;
        this.f12893c = field;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int i3 = this.f12891a;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, i3);
        SafeParcelWriter.writeString(parcel, 2, this.f12892b, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.f12893c, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    zam(String str, FastJsonResponse.Field field) {
        this.f12891a = 1;
        this.f12892b = str;
        this.f12893c = field;
    }
}
