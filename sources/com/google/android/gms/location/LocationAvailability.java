package com.google.android.gms.location;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

@SafeParcelable.Class(creator = "LocationAvailabilityCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class LocationAvailability extends AbstractSafeParcelable implements ReflectedParcelable {

    /* renamed from: a, reason: collision with root package name */
    final int f13243a;

    @SafeParcelable.Field(defaultValueUnchecked = "LocationAvailability.STATUS_UNKNOWN", getter = "getCellStatus", id = 1)
    private final int zzd;

    @SafeParcelable.Field(defaultValueUnchecked = "LocationAvailability.STATUS_UNKNOWN", getter = "getWifiStatus", id = 2)
    private final int zze;

    @SafeParcelable.Field(defaultValueUnchecked = "0", getter = "getElapsedRealtimeNs", id = 3)
    private final long zzf;

    @SafeParcelable.Field(getter = "getBatchedStatus", id = 5)
    private final zzal[] zzg;

    @NonNull
    public static final LocationAvailability zza = new LocationAvailability(0, 1, 1, 0, null, true);

    @NonNull
    public static final LocationAvailability zzb = new LocationAvailability(1000, 1, 1, 0, null, false);

    @NonNull
    public static final Parcelable.Creator<LocationAvailability> CREATOR = new zzab();

    LocationAvailability(int i2, int i3, int i4, long j2, zzal[] zzalVarArr, boolean z2) {
        this.f13243a = i2 < 1000 ? 0 : 1000;
        this.zzd = i3;
        this.zze = i4;
        this.zzf = j2;
        this.zzg = zzalVarArr;
    }

    @Nullable
    public static LocationAvailability extractLocationAvailability(@NonNull Intent intent) {
        if (!hasLocationAvailability(intent)) {
            return null;
        }
        try {
            return (LocationAvailability) intent.getParcelableExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
        } catch (ClassCastException unused) {
            return null;
        }
    }

    @EnsuresNonNullIf(expression = {"#1"}, result = true)
    public static boolean hasLocationAvailability(@Nullable Intent intent) {
        return intent != null && intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof LocationAvailability) {
            LocationAvailability locationAvailability = (LocationAvailability) obj;
            if (this.zzd == locationAvailability.zzd && this.zze == locationAvailability.zze && this.zzf == locationAvailability.zzf && this.f13243a == locationAvailability.f13243a && Arrays.equals(this.zzg, locationAvailability.zzg)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.f13243a));
    }

    public boolean isLocationAvailable() {
        return this.f13243a < 1000;
    }

    @NonNull
    public String toString() {
        boolean zIsLocationAvailable = isLocationAvailable();
        StringBuilder sb = new StringBuilder(String.valueOf(zIsLocationAvailable).length() + 22);
        sb.append("LocationAvailability[");
        sb.append(zIsLocationAvailable);
        sb.append("]");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int i3 = this.zzd;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, i3);
        SafeParcelWriter.writeInt(parcel, 2, this.zze);
        SafeParcelWriter.writeLong(parcel, 3, this.zzf);
        SafeParcelWriter.writeInt(parcel, 4, this.f13243a);
        SafeParcelWriter.writeTypedArray(parcel, 5, this.zzg, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 6, isLocationAvailable());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
