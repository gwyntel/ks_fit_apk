package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;
import javax.annotation.Nullable;

@KeepForSdk
@SafeParcelable.Class(creator = "WakeLockEventCreator")
@Deprecated
/* loaded from: classes3.dex */
public final class WakeLockEvent extends StatsEvent {

    @NonNull
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zza();

    /* renamed from: a, reason: collision with root package name */
    final int f12895a;

    @SafeParcelable.Field(getter = "getTimeMillis", id = 2)
    private final long zzb;

    @SafeParcelable.Field(getter = "getEventType", id = 11)
    private final int zzc;

    @SafeParcelable.Field(getter = "getWakeLockName", id = 4)
    private final String zzd;

    @SafeParcelable.Field(getter = "getSecondaryWakeLockName", id = 10)
    private final String zze;

    @SafeParcelable.Field(getter = "getCodePackage", id = 17)
    private final String zzf;

    @SafeParcelable.Field(getter = "getWakeLockType", id = 5)
    private final int zzg;

    @Nullable
    @SafeParcelable.Field(getter = "getCallingPackages", id = 6)
    private final List zzh;

    @SafeParcelable.Field(getter = "getEventKey", id = 12)
    private final String zzi;

    @SafeParcelable.Field(getter = "getElapsedRealtime", id = 8)
    private final long zzj;

    @SafeParcelable.Field(getter = "getDeviceState", id = 14)
    private final int zzk;

    @SafeParcelable.Field(getter = "getHostPackage", id = 13)
    private final String zzl;

    @SafeParcelable.Field(getter = "getBeginPowerPercentage", id = 15)
    private final float zzm;

    @SafeParcelable.Field(getter = "getTimeout", id = 16)
    private final long zzn;

    @SafeParcelable.Field(getter = "getAcquiredWithTimeout", id = 18)
    private final boolean zzo;

    WakeLockEvent(int i2, long j2, int i3, String str, int i4, List list, String str2, long j3, int i5, String str3, String str4, float f2, long j4, String str5, boolean z2) {
        this.f12895a = i2;
        this.zzb = j2;
        this.zzc = i3;
        this.zzd = str;
        this.zze = str3;
        this.zzf = str5;
        this.zzg = i4;
        this.zzh = list;
        this.zzi = str2;
        this.zzj = j3;
        this.zzk = i5;
        this.zzl = str4;
        this.zzm = f2;
        this.zzn = j4;
        this.zzo = z2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.f12895a);
        SafeParcelWriter.writeLong(parcel, 2, this.zzb);
        SafeParcelWriter.writeString(parcel, 4, this.zzd, false);
        SafeParcelWriter.writeInt(parcel, 5, this.zzg);
        SafeParcelWriter.writeStringList(parcel, 6, this.zzh, false);
        SafeParcelWriter.writeLong(parcel, 8, this.zzj);
        SafeParcelWriter.writeString(parcel, 10, this.zze, false);
        SafeParcelWriter.writeInt(parcel, 11, this.zzc);
        SafeParcelWriter.writeString(parcel, 12, this.zzi, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzl, false);
        SafeParcelWriter.writeInt(parcel, 14, this.zzk);
        SafeParcelWriter.writeFloat(parcel, 15, this.zzm);
        SafeParcelWriter.writeLong(parcel, 16, this.zzn);
        SafeParcelWriter.writeString(parcel, 17, this.zzf, false);
        SafeParcelWriter.writeBoolean(parcel, 18, this.zzo);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final int zza() {
        return this.zzc;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long zzb() {
        return this.zzb;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    @NonNull
    public final String zzc() {
        List list = this.zzh;
        String strJoin = list == null ? "" : TextUtils.join(",", list);
        int i2 = this.zzk;
        String str = this.zze;
        String str2 = this.zzl;
        float f2 = this.zzm;
        String str3 = this.zzf;
        int i3 = this.zzg;
        String str4 = this.zzd;
        boolean z2 = this.zzo;
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        sb.append(str4);
        sb.append("\t");
        sb.append(i3);
        sb.append("\t");
        sb.append(strJoin);
        sb.append("\t");
        sb.append(i2);
        sb.append("\t");
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append("\t");
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str2);
        sb.append("\t");
        sb.append(f2);
        sb.append("\t");
        sb.append(str3 != null ? str3 : "");
        sb.append("\t");
        sb.append(z2);
        return sb.toString();
    }
}
