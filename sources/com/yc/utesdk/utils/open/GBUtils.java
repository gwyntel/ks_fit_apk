package com.yc.utesdk.utils.open;

import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.bc;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/* loaded from: classes4.dex */
public class GBUtils {
    private static GBUtils Instance;

    public static String BToH(String str) {
        return Integer.toHexString(Integer.valueOf(toD(str, 2)).intValue());
    }

    public static String HToB(String str) {
        return Integer.toBinaryString(Integer.valueOf(toD(str.toLowerCase(), 16)).intValue());
    }

    public static String StringToAsciiString(String str) {
        int length = str.length();
        String str2 = "";
        for (int i2 = 0; i2 < length; i2++) {
            str2 = str2 + Integer.toHexString(str.charAt(i2));
        }
        return str2;
    }

    public static int formatting(String str) {
        int i2 = 0;
        for (int i3 = 0; i3 < 10; i3++) {
            if (str.equals(String.valueOf(i3))) {
                i2 = i3;
            }
        }
        if (str.equals("a")) {
            i2 = 10;
        }
        if (str.equals("b")) {
            i2 = 11;
        }
        if (str.equals(bc.aL)) {
            i2 = 12;
        }
        if (str.equals("d")) {
            i2 = 13;
        }
        if (str.equals("e")) {
            i2 = 14;
        }
        if (str.equals("f")) {
            return 15;
        }
        return i2;
    }

    public static GBUtils getInstance() {
        if (Instance == null) {
            Instance = new GBUtils();
        }
        return Instance;
    }

    public static String toD(String str, int i2) {
        int iPow = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            iPow = (int) (iPow + (formatting(str.substring(i3, r3)) * Math.pow(i2, (str.length() - i3) - 1)));
        }
        return String.valueOf(iPow);
    }

    public static String toHex(int i2) {
        StringBuilder sb = new StringBuilder();
        int i3 = i2 / 16;
        if (i3 == 0) {
            return utendo(i2);
        }
        sb.append(toHex(i3));
        sb.append(utendo(i2 % 16));
        return sb.toString();
    }

    public static byte utendo(char c2) {
        return (byte) "0123456789ABCDEF".indexOf(c2);
    }

    public String AsciiStringToString(String str) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            int length = str.length() / 2;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i2 * 2;
                sb.append(String.valueOf((char) hexStringToAlgorism(str.substring(i3, i3 + 2))));
            }
        }
        return sb.toString();
    }

    public String asciiByteToString(byte[] bArr) {
        return AsciiStringToString(bytes2HexString(bArr));
    }

    public String bytes2HexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }

    public String bytes2HexStringUpperCase(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }

    public String bytes2HexStringUpperCaseMac(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < bArr.length; i2++) {
            String hexString = Integer.toHexString(bArr[i2] & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            sb.append(hexString.toUpperCase());
            if (i2 < bArr.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

    public String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public String customerIDAsciiByteToString(byte[] bArr) {
        StringBuilder sb;
        if (bArr == null || bArr.length <= 0) {
            sb = null;
        } else {
            sb = new StringBuilder(bArr.length);
            for (byte b2 : bArr) {
                sb.append(String.format("%02X", Byte.valueOf(b2)));
            }
        }
        if (sb == null) {
            return "";
        }
        String string = sb.toString();
        return (!string.equals("F1FA0000000000000000") && string.length() > 4) ? AsciiStringToString(string.substring(4, string.length())) : "";
    }

    public String formatTwoCharacters(int i2) {
        try {
            return String.format(Locale.US, "%1$02d", Integer.valueOf(i2));
        } catch (Exception unused) {
            String strValueOf = String.valueOf(i2);
            if (i2 < 10) {
                strValueOf = "0" + i2;
            }
            return "" + strValueOf;
        }
    }

    public int hexStringToAlgorism(String str) {
        String upperCase = str.toUpperCase();
        int iPow = 0;
        for (int length = upperCase.length(); length > 0; length--) {
            char cCharAt = upperCase.charAt(length - 1);
            iPow = (int) (iPow + (Math.pow(16.0d, r0 - length) * ((cCharAt < '0' || cCharAt > '9') ? cCharAt - '7' : cCharAt - '0')));
        }
        return iPow;
    }

    public byte[] hexStringToBytes(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (utendo(charArray[i3 + 1]) | (utendo(charArray[i3]) << 4));
        }
        return bArr;
    }

    public byte[] hexStringToBytesForGps(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (utendo(charArray[i3 + 1]) | (utendo(charArray[i3]) << 4));
        }
        return bArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004c A[PHI: r2
      0x004c: PHI (r2v8 int) = (r2v4 int), (r2v19 int) binds: [B:26:0x005b, B:20:0x004a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] hexStringToBytess(java.lang.String r8, int r9) {
        /*
            r7 = this;
            if (r8 == 0) goto L8e
            java.lang.String r0 = ""
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto Lc
            goto L8e
        Lc:
            java.lang.String r8 = r8.toUpperCase()
            int r0 = r8.length()
            int r0 = r0 / 2
            r1 = 4
            boolean r2 = com.yc.utesdk.ble.open.DeviceMode.isHasFunction_1(r1)
            if (r2 != 0) goto L4e
            r2 = 1024(0x400, float:1.435E-42)
            boolean r2 = com.yc.utesdk.ble.open.DeviceMode.isHasFunction_7(r2)
            if (r2 != 0) goto L4e
            r2 = 2048(0x800, float:2.87E-42)
            boolean r2 = com.yc.utesdk.ble.open.DeviceMode.isHasFunction_7(r2)
            if (r2 != 0) goto L4e
            r2 = 512(0x200, float:7.17E-43)
            boolean r2 = com.yc.utesdk.ble.open.DeviceMode.isHasFunction_7(r2)
            if (r2 != 0) goto L4e
            com.yc.utesdk.ble.open.UteBleClient r2 = com.yc.utesdk.ble.open.UteBleClient.getUteBleClient()
            int r2 = r2.getDevicePlatform()
            if (r2 == 0) goto L4e
            r2 = 65536(0x10000, float:9.1835E-41)
            boolean r2 = com.yc.utesdk.ble.open.DeviceMode.isHasFunction_3(r2)
            if (r2 == 0) goto L48
            goto L4e
        L48:
            r2 = 40
            if (r0 <= r2) goto L5e
        L4c:
            r0 = r2
            goto L5e
        L4e:
            r2 = 4194304(0x400000, float:5.877472E-39)
            boolean r2 = com.yc.utesdk.ble.open.DeviceMode.isHasFunction_6(r2)
            if (r2 == 0) goto L59
            r2 = 240(0xf0, float:3.36E-43)
            goto L5b
        L59:
            r2 = 160(0xa0, float:2.24E-43)
        L5b:
            if (r0 <= r2) goto L5e
            goto L4c
        L5e:
            char[] r8 = r8.toCharArray()
            int r2 = r0 + 2
            byte[] r2 = new byte[r2]
            r9 = r9 & 255(0xff, float:3.57E-43)
            byte r9 = (byte) r9
            r3 = 0
            r2[r3] = r9
            r9 = r0 & 255(0xff, float:3.57E-43)
            byte r9 = (byte) r9
            r4 = 1
            r2[r4] = r9
        L72:
            if (r3 >= r0) goto L8d
            int r9 = r3 * 2
            int r5 = r3 + 2
            char r6 = r8[r9]
            byte r6 = utendo(r6)
            int r6 = r6 << r1
            int r9 = r9 + r4
            char r9 = r8[r9]
            byte r9 = utendo(r9)
            r9 = r9 | r6
            byte r9 = (byte) r9
            r2[r5] = r9
            int r3 = r3 + 1
            goto L72
        L8d:
            return r2
        L8e:
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.open.GBUtils.hexStringToBytess(java.lang.String, int):byte[]");
    }

    public byte[] hexStringToBytess6(String str, int i2) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        if (length > 30) {
            length = 30;
        }
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length + 2];
        bArr[0] = (byte) (i2 & 255);
        bArr[1] = (byte) (length & 255);
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = i3 * 2;
            bArr[i3 + 2] = (byte) (utendo(charArray[i4 + 1]) | (utendo(charArray[i4]) << 4));
        }
        return bArr;
    }

    public byte[] hexStringToBytessForContact(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (utendo(charArray[i3 + 1]) | (utendo(charArray[i3]) << 4));
        }
        return bArr;
    }

    public byte[] hexStringToBytessForLanguage(String str, int i2, int i3) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length + 3];
        bArr[0] = (byte) (i2 & 255);
        bArr[1] = (byte) (i3 & 255);
        bArr[2] = (byte) (length & 255);
        for (int i4 = 0; i4 < length; i4++) {
            int i5 = i4 * 2;
            bArr[i4 + 3] = (byte) (utendo(charArray[i5 + 1]) | (utendo(charArray[i5]) << 4));
        }
        return bArr;
    }

    public byte[] hexStringToBytessForOnlineDial(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (utendo(charArray[i3 + 1]) | (utendo(charArray[i3]) << 4));
        }
        return bArr;
    }

    public String int2unicode(String str) {
        String strFilterEmoji = EmojiUtil.getInstance().filterEmoji(str);
        String str2 = "";
        for (int i2 = 0; i2 < strFilterEmoji.length(); i2++) {
            char cCharAt = strFilterEmoji.charAt(i2);
            if (cCharAt >= ' ' && cCharAt != 8197 && ((cCharAt < 127 || cCharAt > 159) && cCharAt != 9924 && cCharAt != 9748 && (i2 != strFilterEmoji.length() - 1 || cCharAt > ' '))) {
                str2 = str2 + Integer.toHexString(cCharAt).toUpperCase();
            }
        }
        return str2;
    }

    public String parseAscii(String str) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : str.getBytes()) {
            sb.append(toHex(b2));
        }
        return sb.toString();
    }

    public String removeLettersKeepNumbers(String str) {
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (!utendo(str)) {
            return str;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (!Character.isLetter(cCharAt)) {
                str2 = str2 + cCharAt;
            }
        }
        return str2;
    }

    public String string2unicode(String str) {
        StringBuilder sb;
        String str2;
        String str3 = "";
        for (int i2 = 0; i2 < str.length(); i2++) {
            int iCodePointAt = str.codePointAt(i2);
            if (iCodePointAt >= 32 && iCodePointAt != 8197 && ((iCodePointAt < 127 || iCodePointAt > 159) && iCodePointAt != 9924 && iCodePointAt != 9748 && (i2 != str.length() - 1 || iCodePointAt > 32))) {
                String upperCase = Integer.toHexString(iCodePointAt).toUpperCase();
                if (upperCase.length() == 2) {
                    sb = new StringBuilder();
                    str2 = "00";
                } else if (upperCase.length() == 3) {
                    sb = new StringBuilder();
                    str2 = "0";
                } else {
                    if (upperCase.length() == 1) {
                        sb = new StringBuilder();
                        str2 = "000";
                    }
                    str3 = str3 + upperCase;
                }
                sb.append(str2);
                sb.append(upperCase);
                upperCase = sb.toString();
                str3 = str3 + upperCase;
            }
        }
        return str3;
    }

    public String stringToASCII(String str) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            for (byte b2 : str.getBytes()) {
                sb.append(toHex(b2));
            }
        }
        return sb.toString();
    }

    public String unicode2String(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < bArr.length; i2 += 2) {
            stringBuffer.append((char) (((bArr[i2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[i2 + 1] & 255)));
        }
        return stringBuffer.toString();
    }

    public String utf8ByteToString(byte[] bArr) {
        return new String(bArr, StandardCharsets.UTF_8);
    }

    public String formatTwoCharacters(int i2, int i3) {
        try {
            return String.format(Locale.US, "%1$02d%2$02d", Integer.valueOf(i2), Integer.valueOf(i3));
        } catch (Exception unused) {
            String strValueOf = String.valueOf(i2);
            String strValueOf2 = String.valueOf(i3);
            if (i2 < 10) {
                strValueOf = "0" + i2;
            }
            if (i3 < 10) {
                strValueOf2 = "0" + i3;
            }
            return "" + strValueOf + strValueOf2;
        }
    }

    public byte[] hexStringToBytes(String str, int i2) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length + 2];
        bArr[0] = -63;
        if (i2 == 1) {
            bArr[1] = 1;
        } else if (i2 == 2) {
            bArr[1] = 2;
        } else if (i2 == 3) {
            bArr[1] = 3;
        }
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = i3 * 2;
            bArr[i3 + 2] = (byte) (utendo(charArray[i4 + 1]) | (utendo(charArray[i4]) << 4));
        }
        return bArr;
    }

    public final boolean utendo(String str) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (Character.isLetter(str.charAt(i2))) {
                return true;
            }
        }
        return false;
    }

    public static String utendo(int i2) {
        StringBuilder sb;
        String str;
        switch (i2) {
            case 10:
                sb = new StringBuilder();
                sb.append("");
                str = ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
                break;
            case 11:
                sb = new StringBuilder();
                sb.append("");
                str = "B";
                break;
            case 12:
                sb = new StringBuilder();
                sb.append("");
                str = "C";
                break;
            case 13:
                sb = new StringBuilder();
                sb.append("");
                str = "D";
                break;
            case 14:
                sb = new StringBuilder();
                sb.append("");
                str = ExifInterface.LONGITUDE_EAST;
                break;
            case 15:
                sb = new StringBuilder();
                sb.append("");
                str = "F";
                break;
            default:
                return "" + i2;
        }
        sb.append(str);
        return sb.toString();
    }

    public String formatTwoCharacters(int i2, int i3, int i4) {
        try {
            return String.format(Locale.US, "%1$02d%2$02d%3$02d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
        } catch (Exception unused) {
            String strValueOf = String.valueOf(i2);
            String strValueOf2 = String.valueOf(i3);
            String strValueOf3 = String.valueOf(i4);
            if (i2 < 10) {
                strValueOf = "0" + i2;
            }
            if (i3 < 10) {
                strValueOf2 = "0" + i3;
            }
            if (i4 < 10) {
                strValueOf3 = "0" + i4;
            }
            return "" + strValueOf + strValueOf2 + strValueOf3;
        }
    }
}
