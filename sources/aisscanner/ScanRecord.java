package aisscanner;

import android.os.ParcelUuid;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.ailabs.iot.aisbase.C0424s;
import com.alibaba.ailabs.iot.aisbase.C0426t;
import com.alibaba.ailabs.iot.aisbase.env.AppEnv;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.LogUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ScanRecord {
    public static final int DATA_TYPE_FLAGS = 1;
    public static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
    public static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
    public static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
    public static final int DATA_TYPE_MESH_BEACON = 43;
    public static final int DATA_TYPE_MESH_MESSAGE = 42;
    public static final int DATA_TYPE_SERVICE_DATA_128_BIT = 33;
    public static final int DATA_TYPE_SERVICE_DATA_16_BIT = 22;
    public static final int DATA_TYPE_SERVICE_DATA_32_BIT = 32;
    public static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
    public static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
    public static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
    public static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
    public static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
    public static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
    public static final int DATA_TYPE_TX_POWER_LEVEL = 10;
    public static final int DATA_TYPE_UNPROVISIONED_DEVICE_BEACON = 0;
    public static final String MESH_PROVISIONING_UUID = "00001827-0000-1000-8000-00805F9B34FB";
    public static final String TAG = "ScanRecord";
    public final int advertiseFlags;
    public final byte[] bytes;
    public final String deviceName;

    @Nullable
    public final SparseArray<byte[]> manufacturerSpecificData;
    public final byte[] meshNetworkPUD;

    @Nullable
    public final Map<ParcelUuid, byte[]> serviceData;

    @Nullable
    public final List<ParcelUuid> serviceUuids;
    public final int txPowerLevel;

    public ScanRecord(@Nullable List<ParcelUuid> list, @Nullable SparseArray<byte[]> sparseArray, @Nullable Map<ParcelUuid, byte[]> map, int i2, int i3, String str, byte[] bArr, byte[] bArr2) {
        this.serviceUuids = list;
        this.manufacturerSpecificData = sparseArray;
        this.serviceData = map;
        this.deviceName = str;
        this.advertiseFlags = i2;
        this.txPowerLevel = i3;
        this.bytes = bArr2;
        this.meshNetworkPUD = bArr;
    }

    public static byte[] extractBytes(@NonNull byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return bArr2;
    }

    @Nullable
    public static ScanRecord parseFromBytes(@Nullable byte[] bArr) {
        int i2;
        int i3;
        int i4;
        if (bArr == null) {
            return null;
        }
        LogUtils.d(TAG, "scan record origin bytes: " + ConvertUtils.bytes2HexString(bArr));
        int i5 = 0;
        HashMap map = null;
        String str = null;
        byte[] bArrExtractBytes = null;
        int i6 = -1;
        byte b2 = -2147483648;
        ArrayList arrayList = null;
        SparseArray sparseArray = null;
        while (i5 < bArr.length) {
            try {
                int i7 = i5 + 1;
                int i8 = bArr[i5] & 255;
                if (i8 == 0) {
                    return new ScanRecord(arrayList, sparseArray, map, i6, b2, str, bArrExtractBytes, bArr);
                }
                int i9 = i8 - 1;
                int i10 = i5 + 2;
                int i11 = bArr[i7] & 255;
                int i12 = 2;
                if (i11 != 22) {
                    if (i11 == 255) {
                        int i13 = ((bArr[i5 + 3] & 255) << 8) + (bArr[i10] & 255);
                        byte[] bArrExtractBytes2 = extractBytes(bArr, i10, i9);
                        if (sparseArray == null) {
                            sparseArray = new SparseArray();
                        }
                        sparseArray.put(i13, bArrExtractBytes2);
                    } else if (i11 == 32 || i11 == 33) {
                        i2 = 4;
                        i4 = 32;
                        i3 = 16;
                    } else if (i11 == 42) {
                        Log.d(TAG, "Received mesh message");
                        bArrExtractBytes = extractBytes(bArr, i10, i9);
                    } else if (i11 != 43) {
                        switch (i11) {
                            case 1:
                                i6 = bArr[i10] & 255;
                                break;
                            case 2:
                            case 3:
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                parseServiceUuid(bArr, i10, i9, 2, arrayList);
                                break;
                            case 4:
                            case 5:
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                parseServiceUuid(bArr, i10, i9, 4, arrayList);
                                break;
                            case 6:
                            case 7:
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                parseServiceUuid(bArr, i10, i9, 16, arrayList);
                                break;
                            case 8:
                            case 9:
                                str = new String(extractBytes(bArr, i10, i9));
                                break;
                            case 10:
                                b2 = bArr[i10];
                                break;
                        }
                    } else if (!AppEnv.IS_GENIE_ENV) {
                        int i14 = i5 + 3;
                        if ((bArr[i10] & 255) == 0) {
                            byte[] bArrExtractBytes3 = extractBytes(bArr, i14, 16);
                            if (map == null) {
                                map = new HashMap();
                            }
                            map.put(ParcelUuid.fromString("00001827-0000-1000-8000-00805F9B34FB"), bArrExtractBytes3);
                        }
                        i10 = i14;
                    }
                    i5 = i10 + i9;
                } else {
                    i2 = 4;
                    i3 = 16;
                    i4 = 32;
                }
                if (i11 == i4) {
                    i12 = i2;
                } else if (i11 == 33) {
                    i12 = i3;
                }
                ParcelUuid parcelUuidA = C0426t.a(extractBytes(bArr, i10, i12));
                byte[] bArrExtractBytes4 = extractBytes(bArr, i10 + i12, i9 - i12);
                if (map == null) {
                    map = new HashMap();
                }
                map.put(parcelUuidA, bArrExtractBytes4);
                i5 = i10 + i9;
            } catch (Exception unused) {
                Log.e(TAG, "unable to parse scan record: " + Arrays.toString(bArr));
                return new ScanRecord(null, null, null, -1, Integer.MIN_VALUE, null, null, bArr);
            }
        }
        return new ScanRecord(arrayList, sparseArray, map, i6, b2, str, bArrExtractBytes, bArr);
    }

    public static int parseServiceUuid(@NonNull byte[] bArr, int i2, int i3, int i4, @NonNull List<ParcelUuid> list) {
        while (i3 > 0) {
            list.add(C0426t.a(extractBytes(bArr, i2, i4)));
            i3 -= i4;
            i2 += i4;
        }
        return i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ScanRecord.class != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.bytes, ((ScanRecord) obj).bytes);
    }

    public int getAdvertiseFlags() {
        return this.advertiseFlags;
    }

    @Nullable
    public byte[] getBytes() {
        return this.bytes;
    }

    @Nullable
    public String getDeviceName() {
        return this.deviceName;
    }

    @Nullable
    public SparseArray<byte[]> getManufacturerSpecificData() {
        return this.manufacturerSpecificData;
    }

    public byte[] getMeshNetworkPUD() {
        return this.meshNetworkPUD;
    }

    @Nullable
    public Map<ParcelUuid, byte[]> getServiceData() {
        return this.serviceData;
    }

    @Nullable
    public List<ParcelUuid> getServiceUuids() {
        return this.serviceUuids;
    }

    public int getTxPowerLevel() {
        return this.txPowerLevel;
    }

    public String toString() {
        return "ScanRecord [advertiseFlags=" + this.advertiseFlags + ", serviceUuids=" + this.serviceUuids + ", manufacturerSpecificData=" + C0424s.a(this.manufacturerSpecificData) + ", serviceData=" + C0424s.a(this.serviceData) + ", txPowerLevel=" + this.txPowerLevel + ", deviceName=" + this.deviceName + "]";
    }

    @Nullable
    public byte[] getManufacturerSpecificData(int i2) {
        SparseArray<byte[]> sparseArray = this.manufacturerSpecificData;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i2);
    }

    @Nullable
    public byte[] getServiceData(@NonNull ParcelUuid parcelUuid) {
        Map<ParcelUuid, byte[]> map;
        if (parcelUuid == null || (map = this.serviceData) == null) {
            return null;
        }
        return map.get(parcelUuid);
    }
}
