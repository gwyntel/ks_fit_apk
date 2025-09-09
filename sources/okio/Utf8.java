package okio;

/* loaded from: classes5.dex */
public final class Utf8 {
    private Utf8() {
    }

    public static long size(String str) {
        return size(str, 0, str.length());
    }

    public static long size(String str, int i2, int i3) {
        long j2;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("beginIndex < 0: " + i2);
        }
        if (i3 < i2) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
        }
        if (i3 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i3 + " > " + str.length());
        }
        long j3 = 0;
        while (i2 < i3) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                j3++;
            } else {
                if (cCharAt < 2048) {
                    j2 = 2;
                } else if (cCharAt < 55296 || cCharAt > 57343) {
                    j2 = 3;
                } else {
                    int i4 = i2 + 1;
                    char cCharAt2 = i4 < i3 ? str.charAt(i4) : (char) 0;
                    if (cCharAt > 56319 || cCharAt2 < 56320 || cCharAt2 > 57343) {
                        j3++;
                        i2 = i4;
                    } else {
                        j3 += 4;
                        i2 += 2;
                    }
                }
                j3 += j2;
            }
            i2++;
        }
        return j3;
    }
}
