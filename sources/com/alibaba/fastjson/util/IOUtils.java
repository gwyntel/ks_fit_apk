package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.google.common.base.Ascii;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Properties;
import kotlin.text.Typography;

/* loaded from: classes2.dex */
public class IOUtils {
    public static final char[] ASCII_CHARS;
    public static final char[] CA;
    static final char[] DigitOnes;
    static final char[] DigitTens;
    public static final String FASTJSON_COMPATIBLEWITHFIELDNAME = "fastjson.compatibleWithFieldName";
    public static final String FASTJSON_COMPATIBLEWITHJAVABEAN = "fastjson.compatibleWithJavaBean";
    public static final String FASTJSON_PROPERTIES = "fastjson.properties";
    public static final int[] IA;
    static final char[] digits;
    public static final char[] replaceChars;
    static final int[] sizeTable;
    public static final byte[] specicalFlags_doubleQuotes;
    public static final boolean[] specicalFlags_doubleQuotesFlags;
    public static final byte[] specicalFlags_singleQuotes;
    public static final boolean[] specicalFlags_singleQuotesFlags;
    public static final Properties DEFAULT_PROPERTIES = new Properties();
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final boolean[] firstIdentifierFlags = new boolean[256];
    public static final boolean[] identifierFlags = new boolean[256];

    static {
        char c2 = 0;
        while (true) {
            boolean[] zArr = firstIdentifierFlags;
            if (c2 >= zArr.length) {
                break;
            }
            if (c2 >= 'A' && c2 <= 'Z') {
                zArr[c2] = true;
            } else if (c2 >= 'a' && c2 <= 'z') {
                zArr[c2] = true;
            } else if (c2 == '_' || c2 == '$') {
                zArr[c2] = true;
            }
            c2 = (char) (c2 + 1);
        }
        char c3 = 0;
        while (true) {
            boolean[] zArr2 = identifierFlags;
            if (c3 < zArr2.length) {
                if (c3 >= 'A' && c3 <= 'Z') {
                    zArr2[c3] = true;
                } else if (c3 >= 'a' && c3 <= 'z') {
                    zArr2[c3] = true;
                } else if (c3 == '_') {
                    zArr2[c3] = true;
                } else if (c3 >= '0' && c3 <= '9') {
                    zArr2[c3] = true;
                }
                c3 = (char) (c3 + 1);
            } else {
                try {
                    break;
                } catch (Throwable unused) {
                }
            }
        }
        loadPropertiesFromFile();
        byte[] bArr = new byte[161];
        specicalFlags_doubleQuotes = bArr;
        byte[] bArr2 = new byte[161];
        specicalFlags_singleQuotes = bArr2;
        specicalFlags_doubleQuotesFlags = new boolean[161];
        specicalFlags_singleQuotesFlags = new boolean[161];
        replaceChars = new char[93];
        bArr[0] = 4;
        bArr[1] = 4;
        bArr[2] = 4;
        bArr[3] = 4;
        bArr[4] = 4;
        bArr[5] = 4;
        bArr[6] = 4;
        bArr[7] = 4;
        bArr[8] = 1;
        bArr[9] = 1;
        bArr[10] = 1;
        bArr[11] = 4;
        bArr[12] = 1;
        bArr[13] = 1;
        bArr[34] = 1;
        bArr[92] = 1;
        bArr2[0] = 4;
        bArr2[1] = 4;
        bArr2[2] = 4;
        bArr2[3] = 4;
        bArr2[4] = 4;
        bArr2[5] = 4;
        bArr2[6] = 4;
        bArr2[7] = 4;
        bArr2[8] = 1;
        bArr2[9] = 1;
        bArr2[10] = 1;
        bArr2[11] = 4;
        bArr2[12] = 1;
        bArr2[13] = 1;
        bArr2[92] = 1;
        bArr2[39] = 1;
        for (int i2 = 14; i2 <= 31; i2++) {
            specicalFlags_doubleQuotes[i2] = 4;
            specicalFlags_singleQuotes[i2] = 4;
        }
        for (int i3 = 127; i3 < 160; i3++) {
            specicalFlags_doubleQuotes[i3] = 4;
            specicalFlags_singleQuotes[i3] = 4;
        }
        for (int i4 = 0; i4 < 161; i4++) {
            specicalFlags_doubleQuotesFlags[i4] = specicalFlags_doubleQuotes[i4] != 0;
            specicalFlags_singleQuotesFlags[i4] = specicalFlags_singleQuotes[i4] != 0;
        }
        char[] cArr = replaceChars;
        cArr[0] = '0';
        cArr[1] = '1';
        cArr[2] = '2';
        cArr[3] = '3';
        cArr[4] = '4';
        cArr[5] = '5';
        cArr[6] = '6';
        cArr[7] = '7';
        cArr[8] = 'b';
        cArr[9] = 't';
        cArr[10] = 'n';
        cArr[11] = 'v';
        cArr[12] = 'f';
        cArr[13] = 'r';
        cArr[34] = Typography.quote;
        cArr[39] = '\'';
        cArr[47] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_UNIX;
        cArr[92] = org.apache.commons.io.IOUtils.DIR_SEPARATOR_WINDOWS;
        ASCII_CHARS = new char[]{'0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9', '0', 'A', '0', 'B', '0', 'C', '0', 'D', '0', 'E', '0', 'F', '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9', '1', 'A', '1', 'B', '1', 'C', '1', 'D', '1', 'E', '1', 'F', '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9', '2', 'A', '2', 'B', '2', 'C', '2', 'D', '2', 'E', '2', 'F'};
        digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        DigitTens = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9', '9'};
        DigitOnes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        sizeTable = new int[]{9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        CA = charArray;
        int[] iArr = new int[256];
        IA = iArr;
        Arrays.fill(iArr, -1);
        int length = charArray.length;
        for (int i5 = 0; i5 < length; i5++) {
            IA[CA[i5]] = i5;
        }
        IA[61] = 0;
    }

    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static void decode(CharsetDecoder charsetDecoder, ByteBuffer byteBuffer, CharBuffer charBuffer) throws CharacterCodingException {
        try {
            CoderResult coderResultDecode = charsetDecoder.decode(byteBuffer, charBuffer, true);
            if (!coderResultDecode.isUnderflow()) {
                coderResultDecode.throwException();
            }
            CoderResult coderResultFlush = charsetDecoder.flush(charBuffer);
            if (coderResultFlush.isUnderflow()) {
                return;
            }
            coderResultFlush.throwException();
        } catch (CharacterCodingException e2) {
            throw new JSONException("utf8 decode error, " + e2.getMessage(), e2);
        }
    }

    public static byte[] decodeBase64(char[] cArr, int i2, int i3) {
        int i4;
        int i5 = 0;
        if (i3 == 0) {
            return new byte[0];
        }
        int i6 = (i2 + i3) - 1;
        int i7 = i2;
        while (i7 < i6 && IA[cArr[i7]] < 0) {
            i7++;
        }
        while (i6 > 0 && IA[cArr[i6]] < 0) {
            i6--;
        }
        int i8 = cArr[i6] == '=' ? cArr[i6 + (-1)] == '=' ? 2 : 1 : 0;
        int i9 = (i6 - i7) + 1;
        if (i3 > 76) {
            i4 = (cArr[76] == '\r' ? i9 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i10 = (((i9 - i4) * 6) >> 3) - i8;
        byte[] bArr = new byte[i10];
        int i11 = (i10 / 3) * 3;
        int i12 = 0;
        int i13 = 0;
        while (i12 < i11) {
            int[] iArr = IA;
            int i14 = i7 + 4;
            int i15 = iArr[cArr[i7 + 3]] | (iArr[cArr[i7 + 1]] << 12) | (iArr[cArr[i7]] << 18) | (iArr[cArr[i7 + 2]] << 6);
            bArr[i12] = (byte) (i15 >> 16);
            int i16 = i12 + 2;
            bArr[i12 + 1] = (byte) (i15 >> 8);
            i12 += 3;
            bArr[i16] = (byte) i15;
            if (i4 <= 0 || (i13 = i13 + 1) != 19) {
                i7 = i14;
            } else {
                i7 += 6;
                i13 = 0;
            }
        }
        if (i12 < i10) {
            int i17 = 0;
            while (i7 <= i6 - i8) {
                i5 |= IA[cArr[i7]] << (18 - (i17 * 6));
                i17++;
                i7++;
            }
            int i18 = 16;
            while (i12 < i10) {
                bArr[i12] = (byte) (i5 >> i18);
                i18 -= 8;
                i12++;
            }
        }
        return bArr;
    }

    public static int decodeUTF8(byte[] bArr, int i2, int i3, char[] cArr) {
        int i4 = i2 + i3;
        int iMin = Math.min(i3, cArr.length);
        int i5 = 0;
        while (i5 < iMin) {
            byte b2 = bArr[i2];
            if (b2 < 0) {
                break;
            }
            i2++;
            cArr[i5] = (char) b2;
            i5++;
        }
        while (i2 < i4) {
            int i6 = i2 + 1;
            byte b3 = bArr[i2];
            if (b3 >= 0) {
                cArr[i5] = (char) b3;
                i5++;
                i2 = i6;
            } else {
                if ((b3 >> 5) != -2 || (b3 & Ascii.RS) == 0) {
                    if ((b3 >> 4) == -2) {
                        int i7 = i2 + 2;
                        if (i7 < i4) {
                            byte b4 = bArr[i6];
                            i2 += 3;
                            byte b5 = bArr[i7];
                            if ((b3 != -32 || (b4 & 224) != 128) && (b4 & 192) == 128 && (b5 & 192) == 128) {
                                char c2 = (char) (((b4 << 6) ^ (b3 << 12)) ^ ((-123008) ^ b5));
                                if (c2 >= 55296 && c2 < 57344) {
                                    return -1;
                                }
                                cArr[i5] = c2;
                                i5++;
                            }
                        }
                        return -1;
                    }
                    if ((b3 >> 3) == -2 && i2 + 3 < i4) {
                        byte b6 = bArr[i6];
                        int i8 = i2 + 3;
                        byte b7 = bArr[i2 + 2];
                        i2 += 4;
                        byte b8 = bArr[i8];
                        int i9 = (((b3 << 18) ^ (b6 << 12)) ^ (b7 << 6)) ^ (3678080 ^ b8);
                        if ((b6 & 192) == 128 && (b7 & 192) == 128 && (b8 & 192) == 128 && i9 >= 65536 && i9 < 1114112) {
                            int i10 = i5 + 1;
                            cArr[i5] = (char) ((i9 >>> 10) + 55232);
                            i5 += 2;
                            cArr[i10] = (char) ((i9 & 1023) + 56320);
                        }
                    }
                    return -1;
                }
                if (i6 >= i4) {
                    return -1;
                }
                i2 += 2;
                byte b9 = bArr[i6];
                if ((b9 & 192) != 128) {
                    return -1;
                }
                cArr[i5] = (char) ((b9 ^ (b3 << 6)) ^ 3968);
                i5++;
            }
        }
        return i5;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007e  */
    /* JADX WARN: Type inference failed for: r3v0, types: [char, int] */
    /* JADX WARN: Type inference failed for: r3v1, types: [int] */
    /* JADX WARN: Type inference failed for: r3v7, types: [int] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int encodeUTF8(char[] r9, int r10, int r11, byte[] r12) {
        /*
            Method dump skipped, instructions count: 193
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.IOUtils.encodeUTF8(char[], int, int, byte[]):int");
    }

    public static boolean firstIdentifier(char c2) {
        boolean[] zArr = firstIdentifierFlags;
        return c2 < zArr.length && zArr[c2];
    }

    public static void getChars(long j2, int i2, char[] cArr) {
        char c2;
        if (j2 < 0) {
            j2 = -j2;
            c2 = '-';
        } else {
            c2 = 0;
        }
        while (j2 > 2147483647L) {
            long j3 = j2 / 100;
            int i3 = (int) (j2 - (((j3 << 6) + (j3 << 5)) + (j3 << 2)));
            cArr[i2 - 1] = DigitOnes[i3];
            i2 -= 2;
            cArr[i2] = DigitTens[i3];
            j2 = j3;
        }
        int i4 = (int) j2;
        while (i4 >= 65536) {
            int i5 = i4 / 100;
            int i6 = i4 - (((i5 << 6) + (i5 << 5)) + (i5 << 2));
            cArr[i2 - 1] = DigitOnes[i6];
            i2 -= 2;
            cArr[i2] = DigitTens[i6];
            i4 = i5;
        }
        while (true) {
            int i7 = (52429 * i4) >>> 19;
            int i8 = i2 - 1;
            cArr[i8] = digits[i4 - ((i7 << 3) + (i7 << 1))];
            if (i7 == 0) {
                break;
            }
            i4 = i7;
            i2 = i8;
        }
        if (c2 != 0) {
            cArr[i2 - 2] = c2;
        }
    }

    public static String getStringProperty(String str) {
        String property;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            property = null;
        }
        return property == null ? DEFAULT_PROPERTIES.getProperty(str) : property;
    }

    public static boolean isIdent(char c2) {
        boolean[] zArr = identifierFlags;
        return c2 < zArr.length && zArr[c2];
    }

    public static boolean isValidJsonpQueryParam(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != '.' && !isIdent(cCharAt)) {
                return false;
            }
        }
        return true;
    }

    public static void loadPropertiesFromFile() throws IOException {
        InputStream inputStream = (InputStream) AccessController.doPrivileged(new PrivilegedAction<InputStream>() { // from class: com.alibaba.fastjson.util.IOUtils.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public InputStream run() {
                ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
                return contextClassLoader != null ? contextClassLoader.getResourceAsStream(IOUtils.FASTJSON_PROPERTIES) : ClassLoader.getSystemResourceAsStream(IOUtils.FASTJSON_PROPERTIES);
            }
        });
        if (inputStream != null) {
            try {
                DEFAULT_PROPERTIES.load(inputStream);
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static String readAll(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            char[] cArr = new char[2048];
            while (true) {
                int i2 = reader.read(cArr, 0, 2048);
                if (i2 < 0) {
                    return sb.toString();
                }
                sb.append(cArr, 0, i2);
            }
        } catch (Exception e2) {
            throw new JSONException("read string from reader error", e2);
        }
    }

    public static int stringSize(long j2) {
        long j3 = 10;
        for (int i2 = 1; i2 < 19; i2++) {
            if (j2 < j3) {
                return i2;
            }
            j3 *= 10;
        }
        return 19;
    }

    public static int stringSize(int i2) {
        int i3 = 0;
        while (i2 > sizeTable[i3]) {
            i3++;
        }
        return i3 + 1;
    }

    public static void getChars(int i2, int i3, char[] cArr) {
        char c2;
        if (i2 < 0) {
            i2 = -i2;
            c2 = '-';
        } else {
            c2 = 0;
        }
        while (i2 >= 65536) {
            int i4 = i2 / 100;
            int i5 = i2 - (((i4 << 6) + (i4 << 5)) + (i4 << 2));
            cArr[i3 - 1] = DigitOnes[i5];
            i3 -= 2;
            cArr[i3] = DigitTens[i5];
            i2 = i4;
        }
        while (true) {
            int i6 = (52429 * i2) >>> 19;
            int i7 = i3 - 1;
            cArr[i7] = digits[i2 - ((i6 << 3) + (i6 << 1))];
            if (i6 == 0) {
                break;
            }
            i2 = i6;
            i3 = i7;
        }
        if (c2 != 0) {
            cArr[i3 - 2] = c2;
        }
    }

    public static byte[] decodeBase64(String str, int i2, int i3) {
        int i4;
        if (i3 == 0) {
            return new byte[0];
        }
        int i5 = (i2 + i3) - 1;
        int i6 = i2;
        while (i6 < i5 && IA[str.charAt(i6)] < 0) {
            i6++;
        }
        while (i5 > 0 && IA[str.charAt(i5)] < 0) {
            i5--;
        }
        int i7 = str.charAt(i5) == '=' ? str.charAt(i5 + (-1)) == '=' ? 2 : 1 : 0;
        int i8 = (i5 - i6) + 1;
        if (i3 > 76) {
            i4 = (str.charAt(76) == '\r' ? i8 / 78 : 0) << 1;
        } else {
            i4 = 0;
        }
        int i9 = (((i8 - i4) * 6) >> 3) - i7;
        byte[] bArr = new byte[i9];
        int i10 = (i9 / 3) * 3;
        int i11 = 0;
        int i12 = 0;
        while (i11 < i10) {
            int[] iArr = IA;
            int i13 = i6 + 4;
            int i14 = iArr[str.charAt(i6 + 3)] | (iArr[str.charAt(i6 + 1)] << 12) | (iArr[str.charAt(i6)] << 18) | (iArr[str.charAt(i6 + 2)] << 6);
            bArr[i11] = (byte) (i14 >> 16);
            int i15 = i11 + 2;
            bArr[i11 + 1] = (byte) (i14 >> 8);
            i11 += 3;
            bArr[i15] = (byte) i14;
            if (i4 <= 0 || (i12 = i12 + 1) != 19) {
                i6 = i13;
            } else {
                i6 += 6;
                i12 = 0;
            }
        }
        if (i11 < i9) {
            int i16 = 0;
            int i17 = 0;
            while (i6 <= i5 - i7) {
                i16 |= IA[str.charAt(i6)] << (18 - (i17 * 6));
                i17++;
                i6++;
            }
            int i18 = 16;
            while (i11 < i9) {
                bArr[i11] = (byte) (i16 >> i18);
                i18 -= 8;
                i11++;
            }
        }
        return bArr;
    }

    public static void getChars(byte b2, int i2, char[] cArr) {
        char c2;
        int i3;
        if (b2 < 0) {
            c2 = '-';
            i3 = -b2;
        } else {
            c2 = 0;
            i3 = b2;
        }
        while (true) {
            int i4 = (52429 * i3) >>> 19;
            int i5 = i2 - 1;
            cArr[i5] = digits[i3 - ((i4 << 3) + (i4 << 1))];
            if (i4 == 0) {
                break;
            }
            i3 = i4;
            i2 = i5;
        }
        if (c2 != 0) {
            cArr[i2 - 2] = c2;
        }
    }

    public static byte[] decodeBase64(String str) {
        int i2;
        int length = str.length();
        if (length == 0) {
            return new byte[0];
        }
        int i3 = length - 1;
        int i4 = 0;
        while (i4 < i3 && IA[str.charAt(i4) & 255] < 0) {
            i4++;
        }
        while (i3 > 0 && IA[str.charAt(i3) & 255] < 0) {
            i3--;
        }
        int i5 = str.charAt(i3) == '=' ? str.charAt(i3 + (-1)) == '=' ? 2 : 1 : 0;
        int i6 = (i3 - i4) + 1;
        if (length > 76) {
            i2 = (str.charAt(76) == '\r' ? i6 / 78 : 0) << 1;
        } else {
            i2 = 0;
        }
        int i7 = (((i6 - i2) * 6) >> 3) - i5;
        byte[] bArr = new byte[i7];
        int i8 = (i7 / 3) * 3;
        int i9 = 0;
        int i10 = 0;
        while (i9 < i8) {
            int[] iArr = IA;
            int i11 = i4 + 4;
            int i12 = iArr[str.charAt(i4 + 3)] | (iArr[str.charAt(i4 + 1)] << 12) | (iArr[str.charAt(i4)] << 18) | (iArr[str.charAt(i4 + 2)] << 6);
            bArr[i9] = (byte) (i12 >> 16);
            int i13 = i9 + 2;
            bArr[i9 + 1] = (byte) (i12 >> 8);
            i9 += 3;
            bArr[i13] = (byte) i12;
            if (i2 <= 0 || (i10 = i10 + 1) != 19) {
                i4 = i11;
            } else {
                i4 += 6;
                i10 = 0;
            }
        }
        if (i9 < i7) {
            int i14 = 0;
            int i15 = 0;
            while (i4 <= i3 - i5) {
                i14 |= IA[str.charAt(i4)] << (18 - (i15 * 6));
                i15++;
                i4++;
            }
            int i16 = 16;
            while (i9 < i7) {
                bArr[i9] = (byte) (i14 >> i16);
                i16 -= 8;
                i9++;
            }
        }
        return bArr;
    }
}
