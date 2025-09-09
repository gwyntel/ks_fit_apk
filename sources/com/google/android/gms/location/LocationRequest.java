package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.WorkSource;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import anetwork.channel.util.RequestConstant;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.WorkSourceUtil;
import com.google.android.gms.internal.identity.ClientIdentity;
import com.google.android.gms.internal.identity.zzeo;
import org.checkerframework.dataflow.qual.Pure;

@SafeParcelable.Class(creator = "LocationRequestCreator")
@SafeParcelable.Reserved({4, 5, 14, 1000})
/* loaded from: classes3.dex */
public final class LocationRequest extends AbstractSafeParcelable implements ReflectedParcelable {

    @NonNull
    public static final Parcelable.Creator<LocationRequest> CREATOR = new zzaf();

    @Deprecated
    public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;

    @Deprecated
    public static final int PRIORITY_HIGH_ACCURACY = 100;

    @Deprecated
    public static final int PRIORITY_LOW_POWER = 104;

    @Deprecated
    public static final int PRIORITY_NO_POWER = 105;

    @SafeParcelable.Field(defaultValueUnchecked = "Priority.PRIORITY_BALANCED_POWER_ACCURACY", getter = "getPriority", id = 1)
    private int zza;

    @SafeParcelable.Field(defaultValue = "3600000", getter = "getIntervalMillis", id = 2)
    private long zzb;

    @SafeParcelable.Field(defaultValue = "600000", getter = "getMinUpdateIntervalMillis", id = 3)
    private long zzc;

    @SafeParcelable.Field(defaultValue = "0", getter = "getMaxUpdateDelayMillis", id = 8)
    private long zzd;

    @SafeParcelable.Field(defaultValueUnchecked = "Long.MAX_VALUE", getter = "getDurationMillis", id = 10)
    private long zze;

    @SafeParcelable.Field(defaultValueUnchecked = "Integer.MAX_VALUE", getter = "getMaxUpdates", id = 6)
    private int zzf;

    @SafeParcelable.Field(defaultValue = "0", getter = "getMinUpdateDistanceMeters", id = 7)
    private float zzg;

    @SafeParcelable.Field(defaultValue = RequestConstant.FALSE, getter = "isWaitForAccurateLocation", id = 9)
    private boolean zzh;

    @SafeParcelable.Field(defaultValueUnchecked = "-1", getter = "getMaxUpdateAgeMillis", id = 11)
    private long zzi;

    @SafeParcelable.Field(defaultValueUnchecked = "Granularity.GRANULARITY_PERMISSION_LEVEL", getter = "getGranularity", id = 12)
    private final int zzj;

    @SafeParcelable.Field(defaultValueUnchecked = "ThrottleBehavior.THROTTLE_BACKGROUND", getter = "getThrottleBehavior", id = 13)
    private final int zzk;

    @SafeParcelable.Field(defaultValue = RequestConstant.FALSE, getter = "isBypass", id = 15)
    private final boolean zzl;

    @SafeParcelable.Field(defaultValueUnchecked = "new android.os.WorkSource()", getter = "getWorkSource", id = 16)
    private final WorkSource zzm;

    @Nullable
    @SafeParcelable.Field(getter = "getImpersonation", id = 17)
    private final ClientIdentity zzn;

    @Deprecated
    public LocationRequest() {
        this(102, 3600000L, 600000L, 0L, Long.MAX_VALUE, Long.MAX_VALUE, Integer.MAX_VALUE, 0.0f, true, 3600000L, 0, 0, false, new WorkSource(), null);
    }

    @NonNull
    @Deprecated
    public static LocationRequest create() {
        return new LocationRequest(102, 3600000L, 600000L, 0L, Long.MAX_VALUE, Long.MAX_VALUE, Integer.MAX_VALUE, 0.0f, true, 3600000L, 0, 0, false, new WorkSource(), null);
    }

    private static String zze(long j2) {
        return j2 == Long.MAX_VALUE ? "∞" : zzeo.zzb(j2);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof LocationRequest) {
            LocationRequest locationRequest = (LocationRequest) obj;
            if (this.zza == locationRequest.zza && ((isPassive() || this.zzb == locationRequest.zzb) && this.zzc == locationRequest.zzc && isBatched() == locationRequest.isBatched() && ((!isBatched() || this.zzd == locationRequest.zzd) && this.zze == locationRequest.zze && this.zzf == locationRequest.zzf && this.zzg == locationRequest.zzg && this.zzh == locationRequest.zzh && this.zzj == locationRequest.zzj && this.zzk == locationRequest.zzk && this.zzl == locationRequest.zzl && this.zzm.equals(locationRequest.zzm) && Objects.equal(this.zzn, locationRequest.zzn)))) {
                return true;
            }
        }
        return false;
    }

    @Pure
    public long getDurationMillis() {
        return this.zze;
    }

    @Deprecated
    @Pure
    public long getExpirationTime() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = this.zze;
        long j3 = jElapsedRealtime + j2;
        if (((jElapsedRealtime ^ j3) & (j2 ^ j3)) < 0) {
            return Long.MAX_VALUE;
        }
        return j3;
    }

    @Deprecated
    @Pure
    public long getFastestInterval() {
        return getMinUpdateIntervalMillis();
    }

    @Pure
    public int getGranularity() {
        return this.zzj;
    }

    @Deprecated
    @Pure
    public long getInterval() {
        return getIntervalMillis();
    }

    @Pure
    public long getIntervalMillis() {
        return this.zzb;
    }

    @Pure
    public long getMaxUpdateAgeMillis() {
        return this.zzi;
    }

    @Pure
    public long getMaxUpdateDelayMillis() {
        return this.zzd;
    }

    @Pure
    public int getMaxUpdates() {
        return this.zzf;
    }

    @Deprecated
    @Pure
    public long getMaxWaitTime() {
        return Math.max(this.zzd, this.zzb);
    }

    @Pure
    public float getMinUpdateDistanceMeters() {
        return this.zzg;
    }

    @Pure
    public long getMinUpdateIntervalMillis() {
        return this.zzc;
    }

    @Deprecated
    @Pure
    public int getNumUpdates() {
        return getMaxUpdates();
    }

    @Pure
    public int getPriority() {
        return this.zza;
    }

    @Deprecated
    @Pure
    public float getSmallestDisplacement() {
        return getMinUpdateDistanceMeters();
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zza), Long.valueOf(this.zzb), Long.valueOf(this.zzc), this.zzm);
    }

    @Pure
    public boolean isBatched() {
        long j2 = this.zzd;
        return j2 > 0 && (j2 >> 1) >= this.zzb;
    }

    @Deprecated
    @Pure
    public boolean isFastestIntervalExplicitlySet() {
        return true;
    }

    @Pure
    public boolean isPassive() {
        return this.zza == 105;
    }

    public boolean isWaitForAccurateLocation() {
        return this.zzh;
    }

    @NonNull
    @Deprecated
    public LocationRequest setExpirationDuration(long j2) {
        Preconditions.checkArgument(j2 > 0, "durationMillis must be greater than 0");
        this.zze = j2;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setExpirationTime(long j2) {
        this.zze = Math.max(1L, j2 - SystemClock.elapsedRealtime());
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setFastestInterval(long j2) {
        Preconditions.checkArgument(j2 >= 0, "illegal fastest interval: %d", Long.valueOf(j2));
        this.zzc = j2;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setInterval(long j2) {
        Preconditions.checkArgument(j2 >= 0, "intervalMillis must be greater than or equal to 0");
        long j3 = this.zzc;
        long j4 = this.zzb;
        if (j3 == j4 / 6) {
            this.zzc = j2 / 6;
        }
        if (this.zzi == j4) {
            this.zzi = j2;
        }
        this.zzb = j2;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setMaxWaitTime(long j2) {
        Preconditions.checkArgument(j2 >= 0, "illegal max wait time: %d", Long.valueOf(j2));
        this.zzd = j2;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setNumUpdates(int i2) {
        if (i2 > 0) {
            this.zzf = i2;
            return this;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(i2).length() + 20);
        sb.append("invalid numUpdates: ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }

    @NonNull
    @Deprecated
    public LocationRequest setPriority(int i2) {
        zzan.zza(i2);
        this.zza = i2;
        return this;
    }

    @NonNull
    @Deprecated
    public LocationRequest setSmallestDisplacement(float f2) {
        if (f2 >= 0.0f) {
            this.zzg = f2;
            return this;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(f2).length() + 22);
        sb.append("invalid displacement: ");
        sb.append(f2);
        throw new IllegalArgumentException(sb.toString());
    }

    @NonNull
    @Deprecated
    public LocationRequest setWaitForAccurateLocation(boolean z2) {
        this.zzh = z2;
        return this;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[");
        if (isPassive()) {
            sb.append(zzan.zzb(this.zza));
            if (this.zzd > 0) {
                sb.append("/");
                zzeo.zzc(this.zzd, sb);
            }
        } else {
            sb.append("@");
            if (isBatched()) {
                zzeo.zzc(this.zzb, sb);
                sb.append("/");
                zzeo.zzc(this.zzd, sb);
            } else {
                zzeo.zzc(this.zzb, sb);
            }
            sb.append(" ");
            sb.append(zzan.zzb(this.zza));
        }
        if (isPassive() || this.zzc != this.zzb) {
            sb.append(", minUpdateInterval=");
            sb.append(zze(this.zzc));
        }
        if (this.zzg > 0.0d) {
            sb.append(", minUpdateDistance=");
            sb.append(this.zzg);
        }
        if (!isPassive() ? this.zzi != this.zzb : this.zzi != Long.MAX_VALUE) {
            sb.append(", maxUpdateAge=");
            sb.append(zze(this.zzi));
        }
        if (this.zze != Long.MAX_VALUE) {
            sb.append(", duration=");
            zzeo.zzc(this.zze, sb);
        }
        if (this.zzf != Integer.MAX_VALUE) {
            sb.append(", maxUpdates=");
            sb.append(this.zzf);
        }
        if (this.zzk != 0) {
            sb.append(", ");
            sb.append(zzar.zzb(this.zzk));
        }
        if (this.zzj != 0) {
            sb.append(", ");
            sb.append(zzq.zzb(this.zzj));
        }
        if (this.zzh) {
            sb.append(", waitForAccurateLocation");
        }
        if (this.zzl) {
            sb.append(", bypass");
        }
        if (!WorkSourceUtil.isEmpty(this.zzm)) {
            sb.append(", ");
            sb.append(this.zzm);
        }
        if (this.zzn != null) {
            sb.append(", impersonation=");
            sb.append(this.zzn);
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getPriority());
        SafeParcelWriter.writeLong(parcel, 2, getIntervalMillis());
        SafeParcelWriter.writeLong(parcel, 3, getMinUpdateIntervalMillis());
        SafeParcelWriter.writeInt(parcel, 6, getMaxUpdates());
        SafeParcelWriter.writeFloat(parcel, 7, getMinUpdateDistanceMeters());
        SafeParcelWriter.writeLong(parcel, 8, getMaxUpdateDelayMillis());
        SafeParcelWriter.writeBoolean(parcel, 9, isWaitForAccurateLocation());
        SafeParcelWriter.writeLong(parcel, 10, getDurationMillis());
        SafeParcelWriter.writeLong(parcel, 11, getMaxUpdateAgeMillis());
        SafeParcelWriter.writeInt(parcel, 12, getGranularity());
        SafeParcelWriter.writeInt(parcel, 13, this.zzk);
        SafeParcelWriter.writeBoolean(parcel, 15, this.zzl);
        SafeParcelWriter.writeParcelable(parcel, 16, this.zzm, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 17, this.zzn, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Pure
    public final int zza() {
        return this.zzk;
    }

    @Pure
    public final boolean zzb() {
        return this.zzl;
    }

    @NonNull
    @Pure
    public final WorkSource zzc() {
        return this.zzm;
    }

    @Nullable
    @Pure
    public final ClientIdentity zzd() {
        return this.zzn;
    }

    public static final class Builder {
        public static final long IMPLICIT_MAX_UPDATE_AGE = -1;
        public static final long IMPLICIT_MIN_UPDATE_INTERVAL = -1;
        private int zza;
        private long zzb;
        private long zzc;
        private long zzd;
        private long zze;
        private int zzf;
        private float zzg;
        private boolean zzh;
        private long zzi;
        private int zzj;
        private int zzk;
        private boolean zzl;

        @Nullable
        private WorkSource zzm;

        @Nullable
        private ClientIdentity zzn;

        public Builder(int i2, long j2) {
            this(j2);
            setPriority(i2);
        }

        @NonNull
        public LocationRequest build() {
            int i2 = this.zza;
            long j2 = this.zzb;
            long jMin = this.zzc;
            if (jMin == -1) {
                jMin = j2;
            } else if (i2 != 105) {
                jMin = Math.min(jMin, j2);
            }
            long jMax = Math.max(this.zzd, this.zzb);
            long j3 = this.zze;
            int i3 = this.zzf;
            float f2 = this.zzg;
            boolean z2 = this.zzh;
            long j4 = this.zzi;
            return new LocationRequest(i2, j2, jMin, jMax, Long.MAX_VALUE, j3, i3, f2, z2, j4 == -1 ? this.zzb : j4, this.zzj, this.zzk, this.zzl, new WorkSource(this.zzm), this.zzn);
        }

        @NonNull
        public Builder setDurationMillis(long j2) {
            Preconditions.checkArgument(j2 > 0, "durationMillis must be greater than 0");
            this.zze = j2;
            return this;
        }

        @NonNull
        public Builder setGranularity(int i2) {
            zzq.zza(i2);
            this.zzj = i2;
            return this;
        }

        @NonNull
        public Builder setIntervalMillis(long j2) {
            Preconditions.checkArgument(j2 >= 0, "intervalMillis must be greater than or equal to 0");
            this.zzb = j2;
            return this;
        }

        @NonNull
        public Builder setMaxUpdateAgeMillis(long j2) {
            boolean z2 = true;
            if (j2 != -1 && j2 < 0) {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "maxUpdateAgeMillis must be greater than or equal to 0, or IMPLICIT_MAX_UPDATE_AGE");
            this.zzi = j2;
            return this;
        }

        @NonNull
        public Builder setMaxUpdateDelayMillis(long j2) {
            Preconditions.checkArgument(j2 >= 0, "maxUpdateDelayMillis must be greater than or equal to 0");
            this.zzd = j2;
            return this;
        }

        @NonNull
        public Builder setMaxUpdates(int i2) {
            Preconditions.checkArgument(i2 > 0, "maxUpdates must be greater than 0");
            this.zzf = i2;
            return this;
        }

        @NonNull
        public Builder setMinUpdateDistanceMeters(float f2) {
            Preconditions.checkArgument(f2 >= 0.0f, "minUpdateDistanceMeters must be greater than or equal to 0");
            this.zzg = f2;
            return this;
        }

        @NonNull
        public Builder setMinUpdateIntervalMillis(long j2) {
            boolean z2 = true;
            if (j2 != -1 && j2 < 0) {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "minUpdateIntervalMillis must be greater than or equal to 0, or IMPLICIT_MIN_UPDATE_INTERVAL");
            this.zzc = j2;
            return this;
        }

        @NonNull
        public Builder setPriority(int i2) {
            zzan.zza(i2);
            this.zza = i2;
            return this;
        }

        @NonNull
        public Builder setWaitForAccurateLocation(boolean z2) {
            this.zzh = z2;
            return this;
        }

        @NonNull
        public final Builder zza(int i2) {
            zzar.zza(i2);
            this.zzk = i2;
            return this;
        }

        @NonNull
        @RequiresPermission(anyOf = {"android.permission.WRITE_SECURE_SETTINGS", "android.permission.LOCATION_BYPASS"})
        public final Builder zzb(boolean z2) {
            this.zzl = z2;
            return this;
        }

        @NonNull
        @RequiresPermission("android.permission.UPDATE_DEVICE_STATS")
        public final Builder zzc(@Nullable WorkSource workSource) {
            this.zzm = workSource;
            return this;
        }

        public Builder(long j2) {
            this.zza = 102;
            this.zzc = -1L;
            this.zzd = 0L;
            this.zze = Long.MAX_VALUE;
            this.zzf = Integer.MAX_VALUE;
            this.zzg = 0.0f;
            this.zzh = true;
            this.zzi = -1L;
            this.zzj = 0;
            this.zzk = 0;
            this.zzl = false;
            this.zzm = null;
            this.zzn = null;
            setIntervalMillis(j2);
        }

        public Builder(@NonNull LocationRequest locationRequest) {
            this(locationRequest.getPriority(), locationRequest.getIntervalMillis());
            setMinUpdateIntervalMillis(locationRequest.getMinUpdateIntervalMillis());
            setMaxUpdateDelayMillis(locationRequest.getMaxUpdateDelayMillis());
            setDurationMillis(locationRequest.getDurationMillis());
            setMaxUpdates(locationRequest.getMaxUpdates());
            setMinUpdateDistanceMeters(locationRequest.getMinUpdateDistanceMeters());
            setWaitForAccurateLocation(locationRequest.isWaitForAccurateLocation());
            setMaxUpdateAgeMillis(locationRequest.getMaxUpdateAgeMillis());
            setGranularity(locationRequest.getGranularity());
            int iZza = locationRequest.zza();
            zzar.zza(iZza);
            this.zzk = iZza;
            this.zzl = locationRequest.zzb();
            this.zzm = locationRequest.zzc();
            ClientIdentity clientIdentityZzd = locationRequest.zzd();
            boolean z2 = true;
            if (clientIdentityZzd != null && clientIdentityZzd.zza()) {
                z2 = false;
            }
            Preconditions.checkArgument(z2);
            this.zzn = clientIdentityZzd;
        }
    }

    LocationRequest(int i2, long j2, long j3, long j4, long j5, long j6, int i3, float f2, boolean z2, long j7, int i4, int i5, boolean z3, WorkSource workSource, ClientIdentity clientIdentity) {
        long j8;
        this.zza = i2;
        if (i2 == 105) {
            this.zzb = Long.MAX_VALUE;
            j8 = j2;
        } else {
            j8 = j2;
            this.zzb = j8;
        }
        this.zzc = j3;
        this.zzd = j4;
        this.zze = j5 == Long.MAX_VALUE ? j6 : Math.min(Math.max(1L, j5 - SystemClock.elapsedRealtime()), j6);
        this.zzf = i3;
        this.zzg = f2;
        this.zzh = z2;
        this.zzi = j7 != -1 ? j7 : j8;
        this.zzj = i4;
        this.zzk = i5;
        this.zzl = z3;
        this.zzm = workSource;
        this.zzn = clientIdentity;
    }
}
