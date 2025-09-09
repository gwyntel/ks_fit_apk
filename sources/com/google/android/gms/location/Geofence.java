package com.google.android.gms.location;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.identity.zzek;

/* loaded from: classes3.dex */
public interface Geofence {
    public static final int GEOFENCE_TRANSITION_DWELL = 4;
    public static final int GEOFENCE_TRANSITION_ENTER = 1;
    public static final int GEOFENCE_TRANSITION_EXIT = 2;
    public static final long NEVER_EXPIRE = -1;

    public static final class Builder {
        private double zze;
        private double zzf;
        private float zzg;
        private String zza = null;

        @TransitionTypes
        private int zzb = 3;
        private long zzc = -1;
        private short zzd = -1;
        private int zzh = 0;
        private int zzi = -1;

        @NonNull
        public Geofence build() {
            if (this.zza == null) {
                throw new IllegalArgumentException("Request ID not set.");
            }
            int i2 = this.zzb;
            if (i2 == 0) {
                throw new IllegalArgumentException("Transitions types not set.");
            }
            if ((i2 & 4) != 0 && this.zzi < 0) {
                throw new IllegalArgumentException("Non-negative loitering delay needs to be set when transition types include GEOFENCE_TRANSITION_DWELL.");
            }
            if (this.zzc == Long.MIN_VALUE) {
                throw new IllegalArgumentException("Expiration not set.");
            }
            if (this.zzd == -1) {
                throw new IllegalArgumentException("Geofence region not set.");
            }
            if (this.zzh >= 0) {
                return new zzek(this.zza, this.zzb, (short) 1, this.zze, this.zzf, this.zzg, this.zzc, this.zzh, this.zzi);
            }
            throw new IllegalArgumentException("Notification responsiveness should be nonnegative.");
        }

        @NonNull
        public Builder setCircularRegion(@FloatRange(from = -90.0d, to = 90.0d) double d2, @FloatRange(from = -180.0d, to = 180.0d) double d3, @FloatRange(from = 0.0d, fromInclusive = false) float f2) {
            boolean z2 = d2 >= -90.0d && d2 <= 90.0d;
            StringBuilder sb = new StringBuilder(String.valueOf(d2).length() + 18);
            sb.append("Invalid latitude: ");
            sb.append(d2);
            Preconditions.checkArgument(z2, sb.toString());
            boolean z3 = d3 >= -180.0d && d3 <= 180.0d;
            StringBuilder sb2 = new StringBuilder(String.valueOf(d3).length() + 19);
            sb2.append("Invalid longitude: ");
            sb2.append(d3);
            Preconditions.checkArgument(z3, sb2.toString());
            boolean z4 = f2 > 0.0f;
            StringBuilder sb3 = new StringBuilder(String.valueOf(f2).length() + 16);
            sb3.append("Invalid radius: ");
            sb3.append(f2);
            Preconditions.checkArgument(z4, sb3.toString());
            this.zzd = (short) 1;
            this.zze = d2;
            this.zzf = d3;
            this.zzg = f2;
            return this;
        }

        @NonNull
        public Builder setExpirationDuration(long j2) {
            if (j2 < 0) {
                this.zzc = -1L;
            } else {
                this.zzc = DefaultClock.getInstance().elapsedRealtime() + j2;
            }
            return this;
        }

        @NonNull
        public Builder setLoiteringDelay(int i2) {
            this.zzi = i2;
            return this;
        }

        @NonNull
        public Builder setNotificationResponsiveness(@IntRange(from = 0) int i2) {
            this.zzh = i2;
            return this;
        }

        @NonNull
        public Builder setRequestId(@NonNull String str) {
            this.zza = (String) Preconditions.checkNotNull(str, "Request ID can't be set to null");
            return this;
        }

        @NonNull
        public Builder setTransitionTypes(@TransitionTypes int i2) {
            this.zzb = i2;
            return this;
        }
    }

    public @interface GeofenceTransition {
    }

    public @interface TransitionTypes {
    }

    long getExpirationTime();

    double getLatitude();

    int getLoiteringDelay();

    double getLongitude();

    int getNotificationResponsiveness();

    float getRadius();

    @NonNull
    String getRequestId();

    @TransitionTypes
    int getTransitionTypes();
}
