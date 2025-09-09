package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
final class SoundexUtils {
    static String a(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        int length = str.length();
        char[] cArr = new char[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (Character.isLetter(str.charAt(i3))) {
                cArr[i2] = str.charAt(i3);
                i2++;
            }
        }
        return i2 == length ? str.toUpperCase(Locale.ENGLISH) : new String(cArr, 0, i2).toUpperCase(Locale.ENGLISH);
    }

    static int b(StringEncoder stringEncoder, String str, String str2) {
        return c(stringEncoder.encode(str), stringEncoder.encode(str2));
    }

    static int c(String str, String str2) {
        if (str == null || str2 == null) {
            return 0;
        }
        int iMin = Math.min(str.length(), str2.length());
        int i2 = 0;
        for (int i3 = 0; i3 < iMin; i3++) {
            if (str.charAt(i3) == str2.charAt(i3)) {
                i2++;
            }
        }
        return i2;
    }
}
