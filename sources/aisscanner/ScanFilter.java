package aisscanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.ailabs.iot.aisbase.C0428u;
import com.alibaba.ailabs.iot.aisbase.C0430v;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/* loaded from: classes.dex */
public final class ScanFilter implements Parcelable {

    @Nullable
    public final String deviceAddress;

    @Nullable
    public final String deviceName;

    @Nullable
    public final byte[] manufacturerData;

    @Nullable
    public final byte[] manufacturerDataMask;
    public final int manufacturerId;

    @Nullable
    public final byte[] serviceData;

    @Nullable
    public final byte[] serviceDataMask;

    @Nullable
    public final ParcelUuid serviceDataUuid;

    @Nullable
    public final ParcelUuid serviceUuid;

    @Nullable
    public final ParcelUuid serviceUuidMask;
    public static final ScanFilter EMPTY = new Builder().build();
    public static final Parcelable.Creator<ScanFilter> CREATOR = new C0430v();

    public /* synthetic */ ScanFilter(String str, String str2, ParcelUuid parcelUuid, ParcelUuid parcelUuid2, ParcelUuid parcelUuid3, byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, C0430v c0430v) {
        this(str, str2, parcelUuid, parcelUuid2, parcelUuid3, bArr, bArr2, i2, bArr3, bArr4);
    }

    private boolean matchesPartialData(@Nullable byte[] bArr, @Nullable byte[] bArr2, @Nullable byte[] bArr3) {
        if (bArr == null) {
            return bArr3 != null;
        }
        if (bArr3 == null || bArr3.length < bArr.length) {
            return false;
        }
        if (bArr2 == null) {
            for (int i2 = 0; i2 < bArr.length; i2++) {
                if (bArr3[i2] != bArr[i2]) {
                    return false;
                }
            }
            return true;
        }
        for (int i3 = 0; i3 < bArr.length; i3++) {
            byte b2 = bArr2[i3];
            if ((bArr3[i3] & b2) != (b2 & bArr[i3])) {
                return false;
            }
        }
        return true;
    }

    public static boolean matchesServiceUuid(@NonNull UUID uuid, @Nullable UUID uuid2, @NonNull UUID uuid3) {
        if (uuid2 == null) {
            return uuid.equals(uuid3);
        }
        if ((uuid.getLeastSignificantBits() & uuid2.getLeastSignificantBits()) != (uuid3.getLeastSignificantBits() & uuid2.getLeastSignificantBits())) {
            return false;
        }
        return (uuid.getMostSignificantBits() & uuid2.getMostSignificantBits()) == (uuid2.getMostSignificantBits() & uuid3.getMostSignificantBits());
    }

    public static boolean matchesServiceUuids(@Nullable ParcelUuid parcelUuid, @Nullable ParcelUuid parcelUuid2, @Nullable List<ParcelUuid> list) {
        if (parcelUuid == null) {
            return true;
        }
        if (list == null) {
            return false;
        }
        Iterator<ParcelUuid> it = list.iterator();
        while (it.hasNext()) {
            if (matchesServiceUuid(parcelUuid.getUuid(), parcelUuid2 == null ? null : parcelUuid2.getUuid(), it.next().getUuid())) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ScanFilter.class != obj.getClass()) {
            return false;
        }
        ScanFilter scanFilter = (ScanFilter) obj;
        return C0428u.b(this.deviceName, scanFilter.deviceName) && C0428u.b(this.deviceAddress, scanFilter.deviceAddress) && this.manufacturerId == scanFilter.manufacturerId && C0428u.a(this.manufacturerData, scanFilter.manufacturerData) && C0428u.a(this.manufacturerDataMask, scanFilter.manufacturerDataMask) && C0428u.b(this.serviceDataUuid, scanFilter.serviceDataUuid) && C0428u.a(this.serviceData, scanFilter.serviceData) && C0428u.a(this.serviceDataMask, scanFilter.serviceDataMask) && C0428u.b(this.serviceUuid, scanFilter.serviceUuid) && C0428u.b(this.serviceUuidMask, scanFilter.serviceUuidMask);
    }

    @Nullable
    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    @Nullable
    public String getDeviceName() {
        return this.deviceName;
    }

    @Nullable
    public byte[] getManufacturerData() {
        return this.manufacturerData;
    }

    @Nullable
    public byte[] getManufacturerDataMask() {
        return this.manufacturerDataMask;
    }

    public int getManufacturerId() {
        return this.manufacturerId;
    }

    @Nullable
    public byte[] getServiceData() {
        return this.serviceData;
    }

    @Nullable
    public byte[] getServiceDataMask() {
        return this.serviceDataMask;
    }

    @Nullable
    public ParcelUuid getServiceDataUuid() {
        return this.serviceDataUuid;
    }

    @Nullable
    public ParcelUuid getServiceUuid() {
        return this.serviceUuid;
    }

    @Nullable
    public ParcelUuid getServiceUuidMask() {
        return this.serviceUuidMask;
    }

    public int hashCode() {
        return C0428u.a(this.deviceName, this.deviceAddress, Integer.valueOf(this.manufacturerId), Integer.valueOf(Arrays.hashCode(this.manufacturerData)), Integer.valueOf(Arrays.hashCode(this.manufacturerDataMask)), this.serviceDataUuid, Integer.valueOf(Arrays.hashCode(this.serviceData)), Integer.valueOf(Arrays.hashCode(this.serviceDataMask)), this.serviceUuid, this.serviceUuidMask);
    }

    public boolean isAllFieldsEmpty() {
        return EMPTY.equals(this);
    }

    public boolean matches(@Nullable ScanResult scanResult) {
        if (scanResult == null) {
            return false;
        }
        BluetoothDevice device = scanResult.getDevice();
        String str = this.deviceAddress;
        if (str != null && !str.equals(device.getAddress())) {
            return false;
        }
        ScanRecord scanRecord = scanResult.getScanRecord();
        if (scanRecord == null && (this.deviceName != null || this.serviceUuid != null || this.manufacturerData != null || this.serviceData != null)) {
            return false;
        }
        String str2 = this.deviceName;
        if (str2 != null && !str2.equals(scanRecord.getDeviceName())) {
            return false;
        }
        ParcelUuid parcelUuid = this.serviceUuid;
        if (parcelUuid != null && !matchesServiceUuids(parcelUuid, this.serviceUuidMask, scanRecord.getServiceUuids())) {
            return false;
        }
        ParcelUuid parcelUuid2 = this.serviceDataUuid;
        if (parcelUuid2 != null && scanRecord != null && !matchesPartialData(this.serviceData, this.serviceDataMask, scanRecord.getServiceData(parcelUuid2))) {
            return false;
        }
        int i2 = this.manufacturerId;
        return i2 < 0 || scanRecord == null || matchesPartialData(this.manufacturerData, this.manufacturerDataMask, scanRecord.getManufacturerSpecificData(i2));
    }

    public String toString() {
        return "BluetoothLeScanFilter [deviceName=" + this.deviceName + ", deviceAddress=" + this.deviceAddress + ", mUuid=" + this.serviceUuid + ", uuidMask=" + this.serviceUuidMask + ", serviceDataUuid=" + C0428u.a(this.serviceDataUuid) + ", serviceData=" + Arrays.toString(this.serviceData) + ", serviceDataMask=" + Arrays.toString(this.serviceDataMask) + ", manufacturerId=" + this.manufacturerId + ", manufacturerData=" + Arrays.toString(this.manufacturerData) + ", manufacturerDataMask=" + Arrays.toString(this.manufacturerDataMask) + "]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.deviceName == null ? 0 : 1);
        String str = this.deviceName;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.deviceAddress == null ? 0 : 1);
        String str2 = this.deviceAddress;
        if (str2 != null) {
            parcel.writeString(str2);
        }
        parcel.writeInt(this.serviceUuid == null ? 0 : 1);
        ParcelUuid parcelUuid = this.serviceUuid;
        if (parcelUuid != null) {
            parcel.writeParcelable(parcelUuid, i2);
            parcel.writeInt(this.serviceUuidMask == null ? 0 : 1);
            ParcelUuid parcelUuid2 = this.serviceUuidMask;
            if (parcelUuid2 != null) {
                parcel.writeParcelable(parcelUuid2, i2);
            }
        }
        parcel.writeInt(this.serviceDataUuid == null ? 0 : 1);
        ParcelUuid parcelUuid3 = this.serviceDataUuid;
        if (parcelUuid3 != null) {
            parcel.writeParcelable(parcelUuid3, i2);
            parcel.writeInt(this.serviceData == null ? 0 : 1);
            byte[] bArr = this.serviceData;
            if (bArr != null) {
                parcel.writeInt(bArr.length);
                parcel.writeByteArray(this.serviceData);
                parcel.writeInt(this.serviceDataMask == null ? 0 : 1);
                byte[] bArr2 = this.serviceDataMask;
                if (bArr2 != null) {
                    parcel.writeInt(bArr2.length);
                    parcel.writeByteArray(this.serviceDataMask);
                }
            }
        }
        parcel.writeInt(this.manufacturerId);
        parcel.writeInt(this.manufacturerData == null ? 0 : 1);
        byte[] bArr3 = this.manufacturerData;
        if (bArr3 != null) {
            parcel.writeInt(bArr3.length);
            parcel.writeByteArray(this.manufacturerData);
            parcel.writeInt(this.manufacturerDataMask == null ? 0 : 1);
            byte[] bArr4 = this.manufacturerDataMask;
            if (bArr4 != null) {
                parcel.writeInt(bArr4.length);
                parcel.writeByteArray(this.manufacturerDataMask);
            }
        }
    }

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        public String f1672a;

        /* renamed from: b, reason: collision with root package name */
        public String f1673b;

        /* renamed from: c, reason: collision with root package name */
        public ParcelUuid f1674c;

        /* renamed from: d, reason: collision with root package name */
        public ParcelUuid f1675d;

        /* renamed from: e, reason: collision with root package name */
        public ParcelUuid f1676e;

        /* renamed from: f, reason: collision with root package name */
        public byte[] f1677f;

        /* renamed from: g, reason: collision with root package name */
        public byte[] f1678g;

        /* renamed from: h, reason: collision with root package name */
        public int f1679h = -1;

        /* renamed from: i, reason: collision with root package name */
        public byte[] f1680i;

        /* renamed from: j, reason: collision with root package name */
        public byte[] f1681j;

        public ScanFilter build() {
            return new ScanFilter(this.f1672a, this.f1673b, this.f1674c, this.f1675d, this.f1676e, this.f1677f, this.f1678g, this.f1679h, this.f1680i, this.f1681j, null);
        }

        public Builder setDeviceAddress(@NonNull String str) {
            if (str == null || BluetoothAdapter.checkBluetoothAddress(str)) {
                this.f1673b = str;
                return this;
            }
            throw new IllegalArgumentException("invalid device address " + str);
        }

        public Builder setDeviceName(@NonNull String str) {
            this.f1672a = str;
            return this;
        }

        public Builder setManufacturerData(int i2, @NonNull byte[] bArr) {
            if (bArr != null && i2 < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            this.f1679h = i2;
            this.f1680i = bArr;
            this.f1681j = null;
            return this;
        }

        public Builder setServiceData(@NonNull ParcelUuid parcelUuid, @NonNull byte[] bArr) {
            if (parcelUuid == null) {
                throw new IllegalArgumentException("serviceDataUuid is null");
            }
            this.f1676e = parcelUuid;
            this.f1677f = bArr;
            this.f1678g = null;
            return this;
        }

        public Builder setServiceUuid(@NonNull ParcelUuid parcelUuid) {
            this.f1674c = parcelUuid;
            this.f1675d = null;
            return this;
        }

        public Builder setServiceUuid(@NonNull ParcelUuid parcelUuid, @Nullable ParcelUuid parcelUuid2) {
            if (parcelUuid2 != null && parcelUuid == null) {
                throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
            }
            this.f1674c = parcelUuid;
            this.f1675d = parcelUuid2;
            return this;
        }

        public Builder setManufacturerData(int i2, @NonNull byte[] bArr, @Nullable byte[] bArr2) {
            if (bArr != null && i2 < 0) {
                throw new IllegalArgumentException("invalid manufacture id");
            }
            if (bArr2 != null) {
                if (bArr != null) {
                    if (bArr.length != bArr2.length) {
                        throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
                    }
                } else {
                    throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
                }
            }
            this.f1679h = i2;
            this.f1680i = bArr;
            this.f1681j = bArr2;
            return this;
        }

        public Builder setServiceData(@NonNull ParcelUuid parcelUuid, @NonNull byte[] bArr, @Nullable byte[] bArr2) {
            if (parcelUuid != null) {
                if (bArr2 != null) {
                    if (bArr != null) {
                        if (bArr.length != bArr2.length) {
                            throw new IllegalArgumentException("size mismatch for service data and service data mask");
                        }
                    } else {
                        throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
                    }
                }
                this.f1676e = parcelUuid;
                this.f1677f = bArr;
                this.f1678g = bArr2;
                return this;
            }
            throw new IllegalArgumentException("serviceDataUuid is null");
        }
    }

    public ScanFilter(@Nullable String str, @Nullable String str2, @Nullable ParcelUuid parcelUuid, @Nullable ParcelUuid parcelUuid2, @Nullable ParcelUuid parcelUuid3, @Nullable byte[] bArr, @Nullable byte[] bArr2, int i2, @Nullable byte[] bArr3, @Nullable byte[] bArr4) {
        this.deviceName = str;
        this.serviceUuid = parcelUuid;
        this.serviceUuidMask = parcelUuid2;
        this.deviceAddress = str2;
        this.serviceDataUuid = parcelUuid3;
        this.serviceData = bArr;
        this.serviceDataMask = bArr2;
        this.manufacturerId = i2;
        this.manufacturerData = bArr3;
        this.manufacturerDataMask = bArr4;
    }
}
