package androidx.core.location;

import android.annotation.SuppressLint;
import android.location.LocationRequest;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import androidx.core.util.TimeUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class LocationRequestCompat {
    private static final long IMPLICIT_MIN_UPDATE_INTERVAL = -1;
    public static final long PASSIVE_INTERVAL = Long.MAX_VALUE;
    public static final int QUALITY_BALANCED_POWER_ACCURACY = 102;
    public static final int QUALITY_HIGH_ACCURACY = 100;
    public static final int QUALITY_LOW_POWER = 104;

    /* renamed from: a, reason: collision with root package name */
    final int f3601a;

    /* renamed from: b, reason: collision with root package name */
    final long f3602b;

    /* renamed from: c, reason: collision with root package name */
    final long f3603c;

    /* renamed from: d, reason: collision with root package name */
    final long f3604d;

    /* renamed from: e, reason: collision with root package name */
    final int f3605e;

    /* renamed from: f, reason: collision with root package name */
    final float f3606f;

    /* renamed from: g, reason: collision with root package name */
    final long f3607g;

    private static class Api19Impl {
        private static Method sCreateFromDeprecatedProviderMethod;
        private static Class<?> sLocationRequestClass;
        private static Method sSetExpireInMethod;
        private static Method sSetFastestIntervalMethod;
        private static Method sSetNumUpdatesMethod;
        private static Method sSetQualityMethod;

        private Api19Impl() {
        }

        @SuppressLint({"BanUncheckedReflection"})
        public static Object toLocationRequest(LocationRequestCompat locationRequestCompat, String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            try {
                if (sLocationRequestClass == null) {
                    sLocationRequestClass = Class.forName("android.location.LocationRequest");
                }
                if (sCreateFromDeprecatedProviderMethod == null) {
                    Method declaredMethod = sLocationRequestClass.getDeclaredMethod("createFromDeprecatedProvider", String.class, Long.TYPE, Float.TYPE, Boolean.TYPE);
                    sCreateFromDeprecatedProviderMethod = declaredMethod;
                    declaredMethod.setAccessible(true);
                }
                Object objInvoke = sCreateFromDeprecatedProviderMethod.invoke(null, str, Long.valueOf(locationRequestCompat.getIntervalMillis()), Float.valueOf(locationRequestCompat.getMinUpdateDistanceMeters()), Boolean.FALSE);
                if (objInvoke == null) {
                    return null;
                }
                if (sSetQualityMethod == null) {
                    Method declaredMethod2 = sLocationRequestClass.getDeclaredMethod("setQuality", Integer.TYPE);
                    sSetQualityMethod = declaredMethod2;
                    declaredMethod2.setAccessible(true);
                }
                sSetQualityMethod.invoke(objInvoke, Integer.valueOf(locationRequestCompat.getQuality()));
                if (sSetFastestIntervalMethod == null) {
                    Method declaredMethod3 = sLocationRequestClass.getDeclaredMethod("setFastestInterval", Long.TYPE);
                    sSetFastestIntervalMethod = declaredMethod3;
                    declaredMethod3.setAccessible(true);
                }
                sSetFastestIntervalMethod.invoke(objInvoke, Long.valueOf(locationRequestCompat.getMinUpdateIntervalMillis()));
                if (locationRequestCompat.getMaxUpdates() < Integer.MAX_VALUE) {
                    if (sSetNumUpdatesMethod == null) {
                        Method declaredMethod4 = sLocationRequestClass.getDeclaredMethod("setNumUpdates", Integer.TYPE);
                        sSetNumUpdatesMethod = declaredMethod4;
                        declaredMethod4.setAccessible(true);
                    }
                    sSetNumUpdatesMethod.invoke(objInvoke, Integer.valueOf(locationRequestCompat.getMaxUpdates()));
                }
                if (locationRequestCompat.getDurationMillis() < Long.MAX_VALUE) {
                    if (sSetExpireInMethod == null) {
                        Method declaredMethod5 = sLocationRequestClass.getDeclaredMethod("setExpireIn", Long.TYPE);
                        sSetExpireInMethod = declaredMethod5;
                        declaredMethod5.setAccessible(true);
                    }
                    sSetExpireInMethod.invoke(objInvoke, Long.valueOf(locationRequestCompat.getDurationMillis()));
                }
                return objInvoke;
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
                return null;
            }
        }
    }

    @RequiresApi(31)
    private static class Api31Impl {
        private Api31Impl() {
        }

        @DoNotInline
        public static LocationRequest toLocationRequest(LocationRequestCompat locationRequestCompat) {
            return new LocationRequest.Builder(locationRequestCompat.getIntervalMillis()).setQuality(locationRequestCompat.getQuality()).setMinUpdateIntervalMillis(locationRequestCompat.getMinUpdateIntervalMillis()).setDurationMillis(locationRequestCompat.getDurationMillis()).setMaxUpdates(locationRequestCompat.getMaxUpdates()).setMinUpdateDistanceMeters(locationRequestCompat.getMinUpdateDistanceMeters()).setMaxUpdateDelayMillis(locationRequestCompat.getMaxUpdateDelayMillis()).build();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Quality {
    }

    LocationRequestCompat(long j2, int i2, long j3, int i3, long j4, float f2, long j5) {
        this.f3602b = j2;
        this.f3601a = i2;
        this.f3603c = j4;
        this.f3604d = j3;
        this.f3605e = i3;
        this.f3606f = f2;
        this.f3607g = j5;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocationRequestCompat)) {
            return false;
        }
        LocationRequestCompat locationRequestCompat = (LocationRequestCompat) obj;
        return this.f3601a == locationRequestCompat.f3601a && this.f3602b == locationRequestCompat.f3602b && this.f3603c == locationRequestCompat.f3603c && this.f3604d == locationRequestCompat.f3604d && this.f3605e == locationRequestCompat.f3605e && Float.compare(locationRequestCompat.f3606f, this.f3606f) == 0 && this.f3607g == locationRequestCompat.f3607g;
    }

    @IntRange(from = 1)
    public long getDurationMillis() {
        return this.f3604d;
    }

    @IntRange(from = 0)
    public long getIntervalMillis() {
        return this.f3602b;
    }

    @IntRange(from = 0)
    public long getMaxUpdateDelayMillis() {
        return this.f3607g;
    }

    @IntRange(from = 1, to = 2147483647L)
    public int getMaxUpdates() {
        return this.f3605e;
    }

    @FloatRange(from = 0.0d, to = 3.4028234663852886E38d)
    public float getMinUpdateDistanceMeters() {
        return this.f3606f;
    }

    @IntRange(from = 0)
    public long getMinUpdateIntervalMillis() {
        long j2 = this.f3603c;
        return j2 == -1 ? this.f3602b : j2;
    }

    public int getQuality() {
        return this.f3601a;
    }

    public int hashCode() {
        int i2 = this.f3601a * 31;
        long j2 = this.f3602b;
        int i3 = (i2 + ((int) (j2 ^ (j2 >>> 32)))) * 31;
        long j3 = this.f3603c;
        return i3 + ((int) (j3 ^ (j3 >>> 32)));
    }

    @NonNull
    @RequiresApi(31)
    public LocationRequest toLocationRequest() {
        return Api31Impl.toLocationRequest(this);
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request[");
        if (this.f3602b != Long.MAX_VALUE) {
            sb.append("@");
            TimeUtils.formatDuration(this.f3602b, sb);
            int i2 = this.f3601a;
            if (i2 == 100) {
                sb.append(" HIGH_ACCURACY");
            } else if (i2 == 102) {
                sb.append(" BALANCED");
            } else if (i2 == 104) {
                sb.append(" LOW_POWER");
            }
        } else {
            sb.append("PASSIVE");
        }
        if (this.f3604d != Long.MAX_VALUE) {
            sb.append(", duration=");
            TimeUtils.formatDuration(this.f3604d, sb);
        }
        if (this.f3605e != Integer.MAX_VALUE) {
            sb.append(", maxUpdates=");
            sb.append(this.f3605e);
        }
        long j2 = this.f3603c;
        if (j2 != -1 && j2 < this.f3602b) {
            sb.append(", minUpdateInterval=");
            TimeUtils.formatDuration(this.f3603c, sb);
        }
        if (this.f3606f > 0.0d) {
            sb.append(", minUpdateDistance=");
            sb.append(this.f3606f);
        }
        if (this.f3607g / 2 > this.f3602b) {
            sb.append(", maxUpdateDelay=");
            TimeUtils.formatDuration(this.f3607g, sb);
        }
        sb.append(']');
        return sb.toString();
    }

    @Nullable
    @SuppressLint({"NewApi"})
    public LocationRequest toLocationRequest(@NonNull String str) {
        return Build.VERSION.SDK_INT >= 31 ? toLocationRequest() : n0.a(Api19Impl.toLocationRequest(this, str));
    }

    public static final class Builder {
        private long mDurationMillis;
        private long mIntervalMillis;
        private long mMaxUpdateDelayMillis;
        private int mMaxUpdates;
        private float mMinUpdateDistanceMeters;
        private long mMinUpdateIntervalMillis;
        private int mQuality;

        public Builder(long j2) {
            setIntervalMillis(j2);
            this.mQuality = 102;
            this.mDurationMillis = Long.MAX_VALUE;
            this.mMaxUpdates = Integer.MAX_VALUE;
            this.mMinUpdateIntervalMillis = -1L;
            this.mMinUpdateDistanceMeters = 0.0f;
            this.mMaxUpdateDelayMillis = 0L;
        }

        @NonNull
        public LocationRequestCompat build() {
            Preconditions.checkState((this.mIntervalMillis == Long.MAX_VALUE && this.mMinUpdateIntervalMillis == -1) ? false : true, "passive location requests must have an explicit minimum update interval");
            long j2 = this.mIntervalMillis;
            return new LocationRequestCompat(j2, this.mQuality, this.mDurationMillis, this.mMaxUpdates, Math.min(this.mMinUpdateIntervalMillis, j2), this.mMinUpdateDistanceMeters, this.mMaxUpdateDelayMillis);
        }

        @NonNull
        public Builder clearMinUpdateIntervalMillis() {
            this.mMinUpdateIntervalMillis = -1L;
            return this;
        }

        @NonNull
        public Builder setDurationMillis(@IntRange(from = 1) long j2) {
            this.mDurationMillis = Preconditions.checkArgumentInRange(j2, 1L, Long.MAX_VALUE, "durationMillis");
            return this;
        }

        @NonNull
        public Builder setIntervalMillis(@IntRange(from = 0) long j2) {
            this.mIntervalMillis = Preconditions.checkArgumentInRange(j2, 0L, Long.MAX_VALUE, "intervalMillis");
            return this;
        }

        @NonNull
        public Builder setMaxUpdateDelayMillis(@IntRange(from = 0) long j2) {
            this.mMaxUpdateDelayMillis = j2;
            this.mMaxUpdateDelayMillis = Preconditions.checkArgumentInRange(j2, 0L, Long.MAX_VALUE, "maxUpdateDelayMillis");
            return this;
        }

        @NonNull
        public Builder setMaxUpdates(@IntRange(from = 1, to = 2147483647L) int i2) {
            this.mMaxUpdates = Preconditions.checkArgumentInRange(i2, 1, Integer.MAX_VALUE, "maxUpdates");
            return this;
        }

        @NonNull
        public Builder setMinUpdateDistanceMeters(@FloatRange(from = 0.0d, to = 3.4028234663852886E38d) float f2) {
            this.mMinUpdateDistanceMeters = f2;
            this.mMinUpdateDistanceMeters = Preconditions.checkArgumentInRange(f2, 0.0f, Float.MAX_VALUE, "minUpdateDistanceMeters");
            return this;
        }

        @NonNull
        public Builder setMinUpdateIntervalMillis(@IntRange(from = 0) long j2) {
            this.mMinUpdateIntervalMillis = Preconditions.checkArgumentInRange(j2, 0L, Long.MAX_VALUE, "minUpdateIntervalMillis");
            return this;
        }

        @NonNull
        public Builder setQuality(int i2) {
            Preconditions.checkArgument(i2 == 104 || i2 == 102 || i2 == 100, "quality must be a defined QUALITY constant, not %d", Integer.valueOf(i2));
            this.mQuality = i2;
            return this;
        }

        public Builder(@NonNull LocationRequestCompat locationRequestCompat) {
            this.mIntervalMillis = locationRequestCompat.f3602b;
            this.mQuality = locationRequestCompat.f3601a;
            this.mDurationMillis = locationRequestCompat.f3604d;
            this.mMaxUpdates = locationRequestCompat.f3605e;
            this.mMinUpdateIntervalMillis = locationRequestCompat.f3603c;
            this.mMinUpdateDistanceMeters = locationRequestCompat.f3606f;
            this.mMaxUpdateDelayMillis = locationRequestCompat.f3607g;
        }
    }
}
