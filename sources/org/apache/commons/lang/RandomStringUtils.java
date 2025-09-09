package org.apache.commons.lang;

import java.util.Random;

/* loaded from: classes5.dex */
public class RandomStringUtils {
    private static final Random RANDOM = new Random();

    public static String random(int i2) {
        return random(i2, false, false);
    }

    public static String randomAlphabetic(int i2) {
        return random(i2, true, false);
    }

    public static String randomAlphanumeric(int i2) {
        return random(i2, true, true);
    }

    public static String randomAscii(int i2) {
        return random(i2, 32, 127, false, false);
    }

    public static String randomNumeric(int i2) {
        return random(i2, false, true);
    }

    public static String random(int i2, boolean z2, boolean z3) {
        return random(i2, 0, 0, z2, z3);
    }

    public static String random(int i2, int i3, int i4, boolean z2, boolean z3) {
        return random(i2, i3, i4, z2, z3, null, RANDOM);
    }

    public static String random(int i2, int i3, int i4, boolean z2, boolean z3, char[] cArr) {
        return random(i2, i3, i4, z2, z3, cArr, RANDOM);
    }

    public static String random(int i2, int i3, int i4, boolean z2, boolean z3, char[] cArr, Random random) {
        char cNextInt;
        if (i2 == 0) {
            return "";
        }
        if (i2 >= 0) {
            if (i3 == 0 && i4 == 0) {
                if (z2 || z3) {
                    i4 = 123;
                    i3 = 32;
                } else {
                    i3 = 0;
                    i4 = Integer.MAX_VALUE;
                }
            }
            char[] cArr2 = new char[i2];
            int i5 = i4 - i3;
            while (true) {
                int i6 = i2 - 1;
                if (i2 != 0) {
                    if (cArr == null) {
                        cNextInt = (char) (random.nextInt(i5) + i3);
                    } else {
                        cNextInt = cArr[random.nextInt(i5) + i3];
                    }
                    if ((z2 && Character.isLetter(cNextInt)) || ((z3 && Character.isDigit(cNextInt)) || (!z2 && !z3))) {
                        if (cNextInt < 56320 || cNextInt > 57343) {
                            if (cNextInt < 55296 || cNextInt > 56191) {
                                if (cNextInt < 56192 || cNextInt > 56319) {
                                    cArr2[i6] = cNextInt;
                                    i2 = i6;
                                }
                            } else if (i6 != 0) {
                                cArr2[i6] = (char) (random.nextInt(128) + 56320);
                                i2 -= 2;
                                cArr2[i2] = cNextInt;
                            }
                        } else if (i6 != 0) {
                            cArr2[i6] = cNextInt;
                            i2 -= 2;
                            cArr2[i2] = (char) (random.nextInt(128) + 55296);
                        }
                    }
                } else {
                    return new String(cArr2);
                }
            }
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Requested random string length ");
            stringBuffer.append(i2);
            stringBuffer.append(" is less than 0.");
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    public static String random(int i2, String str) {
        if (str == null) {
            return random(i2, 0, 0, false, false, null, RANDOM);
        }
        return random(i2, str.toCharArray());
    }

    public static String random(int i2, char[] cArr) {
        if (cArr == null) {
            return random(i2, 0, 0, false, false, null, RANDOM);
        }
        return random(i2, 0, cArr.length, false, false, cArr, RANDOM);
    }
}
