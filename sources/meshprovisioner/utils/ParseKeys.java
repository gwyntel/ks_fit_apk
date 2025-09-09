package meshprovisioner.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes5.dex */
public class ParseKeys {
    public static int getAppKeyIndexInt(byte[] bArr) {
        return ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort();
    }

    public static int getNetkeyIndexInt(byte[] bArr) {
        return ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort();
    }
}
