package okhttp3.internal.ws;

import okio.Buffer;
import okio.ByteString;

/* loaded from: classes5.dex */
public final class WebSocketProtocol {
    private WebSocketProtocol() {
        throw new AssertionError("No instances.");
    }

    static String a(int i2) {
        if (i2 < 1000 || i2 >= 5000) {
            return "Code must be in range [1000,5000): " + i2;
        }
        if ((i2 < 1004 || i2 > 1006) && (i2 < 1012 || i2 > 2999)) {
            return null;
        }
        return "Code " + i2 + " is reserved and may not be used.";
    }

    public static String acceptHeader(String str) {
        return ByteString.encodeUtf8(str + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").sha1().base64();
    }

    static void b(Buffer.UnsafeCursor unsafeCursor, byte[] bArr) {
        int length = bArr.length;
        int i2 = 0;
        do {
            byte[] bArr2 = unsafeCursor.data;
            int i3 = unsafeCursor.start;
            int i4 = unsafeCursor.end;
            while (i3 < i4) {
                int i5 = i2 % length;
                bArr2[i3] = (byte) (bArr2[i3] ^ bArr[i5]);
                i3++;
                i2 = i5 + 1;
            }
        } while (unsafeCursor.next() != -1);
    }

    static void c(int i2) {
        String strA = a(i2);
        if (strA != null) {
            throw new IllegalArgumentException(strA);
        }
    }
}
