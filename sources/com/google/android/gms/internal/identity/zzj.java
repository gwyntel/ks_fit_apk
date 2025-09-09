package com.google.android.gms.internal.identity;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.zzs;
import com.google.android.gms.location.zzt;

@SafeParcelable.Class(creator = "DeviceOrientationRequestUpdateDataCreator")
/* loaded from: classes3.dex */
public final class zzj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();

    /* renamed from: a, reason: collision with root package name */
    final int f13172a;

    /* renamed from: b, reason: collision with root package name */
    final zzh f13173b;

    /* renamed from: c, reason: collision with root package name */
    final zzt f13174c;

    /* renamed from: d, reason: collision with root package name */
    final zzr f13175d;

    zzj(int i2, zzh zzhVar, IBinder iBinder, IBinder iBinder2) {
        this.f13172a = i2;
        this.f13173b = zzhVar;
        zzr zzpVar = null;
        this.f13174c = iBinder == null ? null : zzs.zzb(iBinder);
        if (iBinder2 != null) {
            IInterface iInterfaceQueryLocalInterface = iBinder2.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
            zzpVar = iInterfaceQueryLocalInterface instanceof zzr ? (zzr) iInterfaceQueryLocalInterface : new zzp(iBinder2);
        }
        this.f13175d = zzpVar;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f13172a);
        SafeParcelWriter.writeParcelable(parcel, 2, this.f13173b, i2, false);
        zzt zztVar = this.f13174c;
        SafeParcelWriter.writeIBinder(parcel, 3, zztVar == null ? null : zztVar.asBinder(), false);
        zzr zzrVar = this.f13175d;
        SafeParcelWriter.writeIBinder(parcel, 4, zzrVar != null ? zzrVar.asBinder() : null, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
