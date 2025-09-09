package org.apache.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes5.dex */
public class HexDump {
    public static final String EOL = System.getProperty("line.separator");
    private static final char[] _hexcodes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int[] _shifts = {28, 24, 20, 16, 12, 8, 4, 0};

    public static void dump(byte[] bArr, long j2, OutputStream outputStream, int i2) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (i2 < 0 || i2 >= bArr.length) {
            throw new ArrayIndexOutOfBoundsException("illegal index: " + i2 + " into array of length " + bArr.length);
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("cannot write to nullstream");
        }
        long j3 = j2 + i2;
        StringBuilder sb = new StringBuilder(74);
        while (i2 < bArr.length) {
            int length = bArr.length - i2;
            if (length > 16) {
                length = 16;
            }
            dump(sb, j3).append(' ');
            for (int i3 = 0; i3 < 16; i3++) {
                if (i3 < length) {
                    dump(sb, bArr[i3 + i2]);
                } else {
                    sb.append("  ");
                }
                sb.append(' ');
            }
            for (int i4 = 0; i4 < length; i4++) {
                byte b2 = bArr[i4 + i2];
                if (b2 < 32 || b2 >= Byte.MAX_VALUE) {
                    sb.append('.');
                } else {
                    sb.append((char) b2);
                }
            }
            sb.append(EOL);
            outputStream.write(sb.toString().getBytes(Charset.defaultCharset()));
            outputStream.flush();
            sb.setLength(0);
            j3 += length;
            i2 += 16;
        }
    }

    private static StringBuilder dump(StringBuilder sb, long j2) {
        for (int i2 = 0; i2 < 8; i2++) {
            sb.append(_hexcodes[((int) (j2 >> _shifts[i2])) & 15]);
        }
        return sb;
    }

    private static StringBuilder dump(StringBuilder sb, byte b2) {
        for (int i2 = 0; i2 < 2; i2++) {
            sb.append(_hexcodes[(b2 >> _shifts[i2 + 6]) & 15]);
        }
        return sb;
    }
}
