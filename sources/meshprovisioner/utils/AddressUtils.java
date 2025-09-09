package meshprovisioner.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.jmdns.impl.constants.DNSRecordClass;

/* loaded from: classes5.dex */
public class AddressUtils {
    public static final String TAG = "AddressUtils";

    public static byte[] getUnicastAddressBytes(int i2) {
        return new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public static int getUnicastAddressInt(byte[] bArr) {
        return ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    public static byte[] incrementUnicastAddress(byte[] bArr) {
        int unicastAddressInt = getUnicastAddressInt(bArr) + 1;
        return new byte[]{(byte) ((unicastAddressInt >> 8) & 255), (byte) (unicastAddressInt & 255)};
    }

    public static boolean isValidGroupAddress(byte[] bArr) {
        if (bArr == null || bArr.length != 2) {
            return false;
        }
        return 49152 == (((bArr[1] & 255) | ((bArr[0] & 255) << 8)) & 49152);
    }

    public static boolean isValidUnicastAddress(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        int i2 = (bArr[1] & 255) | ((bArr[0] & 255) << 8);
        return i2 == (i2 & DNSRecordClass.CLASS_MASK);
    }
}
