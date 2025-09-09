package com.yc.utesdk.utils.open;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Locale;

/* loaded from: classes4.dex */
public class ByteUtil {
    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    public static String byteArrToHexString(byte[] bArr) {
        String str = "";
        for (byte b2 : bArr) {
            str = str + Integer.toString((b2 & 255) + 256, 16).substring(1);
        }
        return str;
    }

    public static short byteArrToShort(byte[] bArr) {
        return byteArrToShort(bArr, 0);
    }

    public static int byteArrayToInt(byte[] bArr) {
        return ((bArr[0] & 255) << 24) | (bArr[3] & 255) | ((bArr[2] & 255) << 8) | ((bArr[1] & 255) << 16);
    }

    public static int byteToInt(byte b2) {
        return b2 & 255;
    }

    public static long bytesToLong(byte[] bArr) {
        buffer.put(bArr, 0, bArr.length);
        buffer.flip();
        return buffer.getLong();
    }

    public static byte[] getByteArr(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3 - i2];
        int i4 = 0;
        while (true) {
            int i5 = i2 + i4;
            if (i5 >= i3) {
                return bArr2;
            }
            bArr2[i4] = bArr[i5];
            i4++;
        }
    }

    public static String getString(byte[] bArr, String str) {
        return getString(bArr, str, null);
    }

    public static int hexStringToInt(String str) {
        return Integer.parseInt(str, 16);
    }

    public static String intToBinary(int i2) {
        return Integer.toBinaryString(i2);
    }

    public static byte intToByte(int i2) {
        return (byte) i2;
    }

    public static byte[] intToByteArray(int i2) {
        return new byte[]{(byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public static boolean isEq(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (length != bArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] longToBytes(long j2) {
        buffer.putLong(0, j2);
        return buffer.array();
    }

    public static InputStream readByteArr(byte[] bArr) {
        return new ByteArrayInputStream(bArr);
    }

    public static byte[] readInputStream(InputStream inputStream) throws Throwable {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
        } catch (IOException unused) {
            byteArrayOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        byteArrayOutputStream.close();
                        inputStream.close();
                        return byteArray;
                    } catch (IOException unused2) {
                        return null;
                    }
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (IOException unused3) {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused4) {
                    return null;
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException unused5) {
                    return null;
                }
            }
            if (inputStream == null) {
                throw th;
            }
            inputStream.close();
            throw th;
        }
    }

    public static byte[] shortToByteArr(short s2) {
        byte[] bArr = new byte[2];
        for (int i2 = 0; i2 < 2; i2++) {
            bArr[i2] = (byte) ((s2 >>> ((1 - i2) * 8)) & 255);
        }
        return bArr;
    }

    public static String watchFaceIdToByteStr(String str) throws NumberFormatException {
        return String.format(Locale.US, "%08X", Integer.valueOf(Integer.parseInt(str)));
    }

    public static String watchFaceIdToString(int i2) {
        return String.valueOf(i2);
    }

    public static short byteArrToShort(byte[] bArr, int i2) {
        return (short) ((bArr[i2 + 1] & 255) | (bArr[i2] << 8));
    }

    public static int byteArrayToInt(byte[] bArr, int i2) {
        return ((bArr[i2] & 255) << 24) | (bArr[i2 + 3] & 255) | ((bArr[i2 + 2] & 255) << 8) | ((bArr[i2 + 1] & 255) << 16);
    }

    public static String getString(byte[] bArr, String str, String str2) {
        try {
            return new String(bArr, str);
        } catch (UnsupportedEncodingException unused) {
            if (str2 == null) {
                return null;
            }
            return str2;
        }
    }

    public static void byteArrToShort(byte[] bArr, short s2, int i2) {
        bArr[i2 + 1] = (byte) (s2 >> 8);
        bArr[i2] = (byte) s2;
    }

    public static int byteArrayToInt(byte[] bArr, int i2, int i3) {
        if (i3 == 1) {
            return bArr[i2] & 255;
        }
        if (i3 == 2) {
            return ((bArr[i2] & 255) << 8) | (bArr[i2 + 1] & 255);
        }
        if (i3 == 3) {
            return ((bArr[i2] & 255) << 16) | (bArr[i2 + 2] & 255) | ((bArr[i2 + 1] & 255) << 8);
        }
        if (i3 != 4) {
            return 0;
        }
        return ((bArr[i2] & 255) << 24) | (bArr[i2 + 3] & 255) | ((bArr[i2 + 2] & 255) << 8) | ((bArr[i2 + 1] & 255) << 16);
    }
}
