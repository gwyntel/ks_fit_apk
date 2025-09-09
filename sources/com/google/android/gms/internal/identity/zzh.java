package com.google.android.gms.internal.identity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.DeviceOrientationRequest;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(creator = "DeviceOrientationRequestInternalCreator")
/* loaded from: classes3.dex */
public final class zzh extends AbstractSafeParcelable {

    /* renamed from: a, reason: collision with root package name */
    final DeviceOrientationRequest f13169a;

    /* renamed from: b, reason: collision with root package name */
    final List f13170b;

    /* renamed from: c, reason: collision with root package name */
    final String f13171c;

    /* renamed from: d, reason: collision with root package name */
    static final List f13167d = Collections.emptyList();

    /* renamed from: e, reason: collision with root package name */
    static final DeviceOrientationRequest f13168e = new DeviceOrientationRequest.Builder(20000).build();
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();

    zzh(DeviceOrientationRequest deviceOrientationRequest, List list, String str) {
        this.f13169a = deviceOrientationRequest;
        this.f13170b = list;
        this.f13171c = str;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzh)) {
            return false;
        }
        zzh zzhVar = (zzh) obj;
        return Objects.equal(this.f13169a, zzhVar.f13169a) && Objects.equal(this.f13170b, zzhVar.f13170b) && Objects.equal(this.f13171c, zzhVar.f13171c);
    }

    public final int hashCode() {
        return this.f13169a.hashCode();
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.f13169a);
        String strValueOf2 = String.valueOf(this.f13170b);
        int length = strValueOf.length();
        int length2 = strValueOf2.length();
        String str = this.f13171c;
        StringBuilder sb = new StringBuilder(length + 68 + length2 + 7 + String.valueOf(str).length() + 2);
        sb.append("DeviceOrientationRequestInternal[deviceOrientationRequest=");
        sb.append(strValueOf);
        sb.append(", clients=");
        sb.append(strValueOf2);
        sb.append(", tag='");
        sb.append(str);
        sb.append("']");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.f13169a, i2, false);
        SafeParcelWriter.writeTypedList(parcel, 2, this.f13170b, false);
        SafeParcelWriter.writeString(parcel, 3, this.f13171c, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
