package aisscanner;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.C0434x;

/* loaded from: classes.dex */
public final class ScanSettings implements Parcelable {
    public static final int CALLBACK_TYPE_ALL_MATCHES = 1;
    public static final int CALLBACK_TYPE_FIRST_MATCH = 2;
    public static final int CALLBACK_TYPE_MATCH_LOST = 4;
    public static final Parcelable.Creator<ScanSettings> CREATOR = new C0434x();
    public static final long MATCH_LOST_DEVICE_TIMEOUT_DEFAULT = 10000;
    public static final long MATCH_LOST_TASK_INTERVAL_DEFAULT = 10000;
    public static final int MATCH_MODE_AGGRESSIVE = 1;
    public static final int MATCH_MODE_STICKY = 2;
    public static final int MATCH_NUM_FEW_ADVERTISEMENT = 2;
    public static final int MATCH_NUM_MAX_ADVERTISEMENT = 3;
    public static final int MATCH_NUM_ONE_ADVERTISEMENT = 1;
    public static final int PHY_LE_ALL_SUPPORTED = 255;
    public static final int SCAN_MODE_BALANCED = 1;
    public static final int SCAN_MODE_LOW_LATENCY = 2;
    public static final int SCAN_MODE_LOW_POWER = 0;
    public static final int SCAN_MODE_OPPORTUNISTIC = -1;

    /* renamed from: a, reason: collision with root package name */
    public final long f1682a;

    /* renamed from: b, reason: collision with root package name */
    public final long f1683b;

    /* renamed from: c, reason: collision with root package name */
    public int f1684c;

    /* renamed from: d, reason: collision with root package name */
    public int f1685d;

    /* renamed from: e, reason: collision with root package name */
    public long f1686e;

    /* renamed from: f, reason: collision with root package name */
    public int f1687f;

    /* renamed from: g, reason: collision with root package name */
    public int f1688g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f1689h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f1690i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f1691j;

    /* renamed from: k, reason: collision with root package name */
    public long f1692k;

    /* renamed from: l, reason: collision with root package name */
    public long f1693l;

    /* renamed from: m, reason: collision with root package name */
    public boolean f1694m;

    /* renamed from: n, reason: collision with root package name */
    public int f1695n;

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        public int f1696a = 0;

        /* renamed from: b, reason: collision with root package name */
        public int f1697b = 1;

        /* renamed from: c, reason: collision with root package name */
        public long f1698c = 0;

        /* renamed from: d, reason: collision with root package name */
        public int f1699d = 1;

        /* renamed from: e, reason: collision with root package name */
        public int f1700e = 3;

        /* renamed from: f, reason: collision with root package name */
        public boolean f1701f = true;

        /* renamed from: g, reason: collision with root package name */
        public int f1702g = 255;

        /* renamed from: h, reason: collision with root package name */
        public boolean f1703h = true;

        /* renamed from: i, reason: collision with root package name */
        public boolean f1704i = true;

        /* renamed from: j, reason: collision with root package name */
        public boolean f1705j = true;

        /* renamed from: k, reason: collision with root package name */
        public long f1706k = 10000;

        /* renamed from: l, reason: collision with root package name */
        public long f1707l = 10000;

        /* renamed from: m, reason: collision with root package name */
        public long f1708m = 0;

        /* renamed from: n, reason: collision with root package name */
        public long f1709n = 0;

        public final boolean a(int i2) {
            return i2 == 1 || i2 == 2 || i2 == 4 || i2 == 6;
        }

        @NonNull
        public ScanSettings build() {
            return new ScanSettings(this.f1696a, this.f1697b, this.f1698c, this.f1699d, this.f1700e, this.f1701f, this.f1702g, this.f1703h, this.f1704i, this.f1705j, this.f1706k, this.f1707l, this.f1709n, this.f1708m, null);
        }

        @NonNull
        public Builder setCallbackType(int i2) {
            if (a(i2)) {
                this.f1697b = i2;
                return this;
            }
            throw new IllegalArgumentException("invalid callback type - " + i2);
        }

        @NonNull
        public Builder setLegacy(boolean z2) {
            this.f1701f = z2;
            return this;
        }

        @NonNull
        public Builder setMatchMode(int i2) {
            if (i2 >= 1 && i2 <= 2) {
                this.f1699d = i2;
                return this;
            }
            throw new IllegalArgumentException("invalid matchMode " + i2);
        }

        @NonNull
        public Builder setMatchOptions(long j2, long j3) {
            if (j2 <= 0 || j3 <= 0) {
                throw new IllegalArgumentException("maxDeviceAgeMillis and taskIntervalMillis must be > 0");
            }
            this.f1706k = j2;
            this.f1707l = j3;
            return this;
        }

        @NonNull
        public Builder setNumOfMatches(int i2) {
            if (i2 >= 1 && i2 <= 3) {
                this.f1700e = i2;
                return this;
            }
            throw new IllegalArgumentException("invalid numOfMatches " + i2);
        }

        @NonNull
        public Builder setPhy(int i2) {
            this.f1702g = i2;
            return this;
        }

        @NonNull
        public Builder setPowerSave(long j2, long j3) {
            if (j2 <= 0 || j3 <= 0) {
                throw new IllegalArgumentException("scanInterval and restInterval must be > 0");
            }
            this.f1709n = j2;
            this.f1708m = j3;
            return this;
        }

        @NonNull
        public Builder setReportDelay(long j2) {
            if (j2 < 0) {
                throw new IllegalArgumentException("reportDelay must be > 0");
            }
            this.f1698c = j2;
            return this;
        }

        @NonNull
        public Builder setScanMode(int i2) {
            if (i2 >= -1 && i2 <= 2) {
                this.f1696a = i2;
                return this;
            }
            throw new IllegalArgumentException("invalid scan mode " + i2);
        }

        @NonNull
        public Builder setUseHardwareBatchingIfSupported(boolean z2) {
            this.f1704i = z2;
            return this;
        }

        @NonNull
        public Builder setUseHardwareCallbackTypesIfSupported(boolean z2) {
            this.f1705j = z2;
            return this;
        }

        @NonNull
        public Builder setUseHardwareFilteringIfSupported(boolean z2) {
            this.f1703h = z2;
            return this;
        }
    }

    public /* synthetic */ ScanSettings(int i2, int i3, long j2, int i4, int i5, boolean z2, int i6, boolean z3, boolean z4, boolean z5, long j3, long j4, long j5, long j6, C0434x c0434x) {
        this(i2, i3, j2, i4, i5, z2, i6, z3, z4, z5, j3, j4, j5, j6);
    }

    public void a() {
        this.f1691j = false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCallbackType() {
        return this.f1685d;
    }

    public boolean getLegacy() {
        return this.f1694m;
    }

    public long getMatchLostDeviceTimeout() {
        return this.f1692k;
    }

    public long getMatchLostTaskInterval() {
        return this.f1693l;
    }

    public int getMatchMode() {
        return this.f1687f;
    }

    public int getNumOfMatches() {
        return this.f1688g;
    }

    public int getPhy() {
        return this.f1695n;
    }

    public long getPowerSaveRest() {
        return this.f1683b;
    }

    public long getPowerSaveScan() {
        return this.f1682a;
    }

    public long getReportDelayMillis() {
        return this.f1686e;
    }

    public int getScanMode() {
        return this.f1684c;
    }

    public boolean getUseHardwareBatchingIfSupported() {
        return this.f1690i;
    }

    public boolean getUseHardwareCallbackTypesIfSupported() {
        return this.f1691j;
    }

    public boolean getUseHardwareFilteringIfSupported() {
        return this.f1689h;
    }

    public boolean hasPowerSaveMode() {
        return this.f1683b > 0 && this.f1682a > 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f1684c);
        parcel.writeInt(this.f1685d);
        parcel.writeLong(this.f1686e);
        parcel.writeInt(this.f1687f);
        parcel.writeInt(this.f1688g);
        parcel.writeInt(this.f1694m ? 1 : 0);
        parcel.writeInt(this.f1695n);
        parcel.writeInt(this.f1689h ? 1 : 0);
        parcel.writeInt(this.f1690i ? 1 : 0);
        parcel.writeLong(this.f1682a);
        parcel.writeLong(this.f1683b);
    }

    public /* synthetic */ ScanSettings(Parcel parcel, C0434x c0434x) {
        this(parcel);
    }

    public ScanSettings(int i2, int i3, long j2, int i4, int i5, boolean z2, int i6, boolean z3, boolean z4, boolean z5, long j3, long j4, long j5, long j6) {
        this.f1684c = i2;
        this.f1685d = i3;
        this.f1686e = j2;
        this.f1688g = i5;
        this.f1687f = i4;
        this.f1694m = z2;
        this.f1695n = i6;
        this.f1689h = z3;
        this.f1690i = z4;
        this.f1691j = z5;
        this.f1692k = 1000000 * j3;
        this.f1693l = j4;
        this.f1682a = j5;
        this.f1683b = j6;
    }

    public ScanSettings(Parcel parcel) {
        this.f1684c = parcel.readInt();
        this.f1685d = parcel.readInt();
        this.f1686e = parcel.readLong();
        this.f1687f = parcel.readInt();
        this.f1688g = parcel.readInt();
        this.f1694m = parcel.readInt() != 0;
        this.f1695n = parcel.readInt();
        this.f1689h = parcel.readInt() == 1;
        this.f1690i = parcel.readInt() == 1;
        this.f1682a = parcel.readLong();
        this.f1683b = parcel.readLong();
    }
}
