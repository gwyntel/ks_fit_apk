package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ConditionalUserPropertyParcelCreator")
/* loaded from: classes3.dex */
public final class zzad extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzad> CREATOR = new zzag();

    @Nullable
    @SafeParcelable.Field(id = 2)
    public String zza;

    @SafeParcelable.Field(id = 3)
    public String zzb;

    @SafeParcelable.Field(id = 4)
    public zzmz zzc;

    @SafeParcelable.Field(id = 5)
    public long zzd;

    @SafeParcelable.Field(id = 6)
    public boolean zze;

    @Nullable
    @SafeParcelable.Field(id = 7)
    public String zzf;

    @Nullable
    @SafeParcelable.Field(id = 8)
    public zzbg zzg;

    @SafeParcelable.Field(id = 9)
    public long zzh;

    @Nullable
    @SafeParcelable.Field(id = 10)
    public zzbg zzi;

    @SafeParcelable.Field(id = 11)
    public long zzj;

    @Nullable
    @SafeParcelable.Field(id = 12)
    public zzbg zzk;

    zzad(zzad zzadVar) {
        Preconditions.checkNotNull(zzadVar);
        this.zza = zzadVar.zza;
        this.zzb = zzadVar.zzb;
        this.zzc = zzadVar.zzc;
        this.zzd = zzadVar.zzd;
        this.zze = zzadVar.zze;
        this.zzf = zzadVar.zzf;
        this.zzg = zzadVar.zzg;
        this.zzh = zzadVar.zzh;
        this.zzi = zzadVar.zzi;
        this.zzj = zzadVar.zzj;
        this.zzk = zzadVar.zzk;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzb, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzc, i2, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzd);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zze);
        SafeParcelWriter.writeString(parcel, 7, this.zzf, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzg, i2, false);
        SafeParcelWriter.writeLong(parcel, 9, this.zzh);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzi, i2, false);
        SafeParcelWriter.writeLong(parcel, 11, this.zzj);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzk, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    zzad(String str, String str2, zzmz zzmzVar, long j2, boolean z2, String str3, zzbg zzbgVar, long j3, zzbg zzbgVar2, long j4, zzbg zzbgVar3) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzmzVar;
        this.zzd = j2;
        this.zze = z2;
        this.zzf = str3;
        this.zzg = zzbgVar;
        this.zzh = j3;
        this.zzi = zzbgVar2;
        this.zzj = j4;
        this.zzk = zzbgVar3;
    }
}
