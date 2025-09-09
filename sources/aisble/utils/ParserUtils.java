package aisble.utils;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

/* loaded from: classes.dex */
public class ParserUtils {
    public static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String parse(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return parse(bluetoothGattCharacteristic.getValue());
    }

    public static String parse(BluetoothGattDescriptor bluetoothGattDescriptor) {
        return parse(bluetoothGattDescriptor.getValue());
    }

    public static String parse(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        char[] cArr = new char[(bArr.length * 3) - 1];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2];
            int i3 = i2 * 3;
            char[] cArr2 = HEX_ARRAY;
            cArr[i3] = cArr2[(b2 & 255) >>> 4];
            cArr[i3 + 1] = cArr2[b2 & 15];
            if (i2 != bArr.length - 1) {
                cArr[i3 + 2] = '-';
            }
        }
        return "(0x) " + new String(cArr);
    }
}
