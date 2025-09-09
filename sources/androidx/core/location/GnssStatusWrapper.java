package androidx.core.location;

import android.location.GnssStatus;
import android.os.Build;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;

@RequiresApi(24)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
class GnssStatusWrapper extends GnssStatusCompat {
    private final GnssStatus mWrapped;

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        static float a(GnssStatus gnssStatus, int i2) {
            return gnssStatus.getCarrierFrequencyHz(i2);
        }

        @DoNotInline
        static boolean b(GnssStatus gnssStatus, int i2) {
            return gnssStatus.hasCarrierFrequencyHz(i2);
        }
    }

    @RequiresApi(30)
    static class Api30Impl {
        private Api30Impl() {
        }

        @DoNotInline
        static float a(GnssStatus gnssStatus, int i2) {
            return gnssStatus.getBasebandCn0DbHz(i2);
        }

        @DoNotInline
        static boolean b(GnssStatus gnssStatus, int i2) {
            return gnssStatus.hasBasebandCn0DbHz(i2);
        }
    }

    GnssStatusWrapper(Object obj) {
        this.mWrapped = d.a(Preconditions.checkNotNull(d.a(obj)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GnssStatusWrapper) {
            return this.mWrapped.equals(((GnssStatusWrapper) obj).mWrapped);
        }
        return false;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getAzimuthDegrees(int i2) {
        return this.mWrapped.getAzimuthDegrees(i2);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getBasebandCn0DbHz(int i2) {
        if (Build.VERSION.SDK_INT >= 30) {
            return Api30Impl.a(this.mWrapped, i2);
        }
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getCarrierFrequencyHz(int i2) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.a(this.mWrapped, i2);
        }
        throw new UnsupportedOperationException();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getCn0DbHz(int i2) {
        return this.mWrapped.getCn0DbHz(i2);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getConstellationType(int i2) {
        return this.mWrapped.getConstellationType(i2);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public float getElevationDegrees(int i2) {
        return this.mWrapped.getElevationDegrees(i2);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getSatelliteCount() {
        return this.mWrapped.getSatelliteCount();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public int getSvid(int i2) {
        return this.mWrapped.getSvid(i2);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasAlmanacData(int i2) {
        return this.mWrapped.hasAlmanacData(i2);
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasBasebandCn0DbHz(int i2) {
        if (Build.VERSION.SDK_INT >= 30) {
            return Api30Impl.b(this.mWrapped, i2);
        }
        return false;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasCarrierFrequencyHz(int i2) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Api26Impl.b(this.mWrapped, i2);
        }
        return false;
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean hasEphemerisData(int i2) {
        return this.mWrapped.hasEphemerisData(i2);
    }

    public int hashCode() {
        return this.mWrapped.hashCode();
    }

    @Override // androidx.core.location.GnssStatusCompat
    public boolean usedInFix(int i2) {
        return this.mWrapped.usedInFix(i2);
    }
}
