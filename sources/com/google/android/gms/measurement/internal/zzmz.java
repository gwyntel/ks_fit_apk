package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "UserAttributeParcelCreator")
/* loaded from: classes3.dex */
public final class zzmz extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzmz> CREATOR = new zznc();

    @SafeParcelable.Field(id = 2)
    public final String zza;

    @SafeParcelable.Field(id = 3)
    public final long zzb;

    @Nullable
    @SafeParcelable.Field(id = 4)
    public final Long zzc;

    @Nullable
    @SafeParcelable.Field(id = 6)
    public final String zzd;

    @SafeParcelable.Field(id = 7)
    public final String zze;

    @Nullable
    @SafeParcelable.Field(id = 8)
    public final Double zzf;

    @SafeParcelable.Field(id = 1)
    private final int zzg;

    @Nullable
    @SafeParcelable.Field(id = 5)
    private final Float zzh;

    zzmz(zznb zznbVar) {
        this(zznbVar.f13324c, zznbVar.f13325d, zznbVar.f13326e, zznbVar.f13323b);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzg);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzb);
        SafeParcelWriter.writeLongObject(parcel, 4, this.zzc, false);
        SafeParcelWriter.writeFloatObject(parcel, 5, null, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzd, false);
        SafeParcelWriter.writeString(parcel, 7, this.zze, false);
        SafeParcelWriter.writeDoubleObject(parcel, 8, this.zzf, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Nullable
    public final Object zza() {
        Long l2 = this.zzc;
        if (l2 != null) {
            return l2;
        }
        Double d2 = this.zzf;
        if (d2 != null) {
            return d2;
        }
        String str = this.zzd;
        if (str != null) {
            return str;
        }
        return null;
    }

    zzmz(String str, long j2, Object obj, String str2) {
        Preconditions.checkNotEmpty(str);
        this.zzg = 2;
        this.zza = str;
        this.zzb = j2;
        this.zze = str2;
        if (obj == null) {
            this.zzc = null;
            this.zzh = null;
            this.zzf = null;
            this.zzd = null;
            return;
        }
        if (obj instanceof Long) {
            this.zzc = (Long) obj;
            this.zzh = null;
            this.zzf = null;
            this.zzd = null;
            return;
        }
        if (obj instanceof String) {
            this.zzc = null;
            this.zzh = null;
            this.zzf = null;
            this.zzd = (String) obj;
            return;
        }
        if (!(obj instanceof Double)) {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
        this.zzc = null;
        this.zzh = null;
        this.zzf = (Double) obj;
        this.zzd = null;
    }

    zzmz(int i2, String str, long j2, Long l2, Float f2, String str2, String str3, Double d2) {
        this.zzg = i2;
        this.zza = str;
        this.zzb = j2;
        this.zzc = l2;
        this.zzh = null;
        if (i2 == 1) {
            this.zzf = f2 != null ? Double.valueOf(f2.doubleValue()) : null;
        } else {
            this.zzf = d2;
        }
        this.zzd = str2;
        this.zze = str3;
    }
}
