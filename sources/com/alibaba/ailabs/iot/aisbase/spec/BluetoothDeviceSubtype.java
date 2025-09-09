package com.alibaba.ailabs.iot.aisbase.spec;

/* loaded from: classes2.dex */
public enum BluetoothDeviceSubtype {
    UNKNOWN,
    BASIC,
    BEACON,
    GMA,
    BLE,
    COMBO,
    FEIYAN;

    public static BluetoothDeviceSubtype parseFromByte(byte b2) {
        if (b2 == 0 || b2 == 1 || b2 == 2 || b2 == 3 || b2 == 4) {
            return FEIYAN;
        }
        switch (b2) {
            case 8:
                return BASIC;
            case 9:
                return BEACON;
            case 10:
                return GMA;
            case 11:
                return BLE;
            case 12:
                return COMBO;
            default:
                return UNKNOWN;
        }
    }
}
