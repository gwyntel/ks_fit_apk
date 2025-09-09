package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import kotlin.text.Typography;

/* loaded from: classes5.dex */
public class FormatUtils {
    private static final double LOG_10 = Math.log(10.0d);

    private FormatUtils() {
    }

    static String a(String str, int i2) {
        String strConcat = str.length() <= i2 + 35 ? str : str.substring(0, i2 + 32).concat("...");
        if (i2 <= 0) {
            return "Invalid format: \"" + strConcat + Typography.quote;
        }
        if (i2 >= str.length()) {
            return "Invalid format: \"" + strConcat + "\" is too short";
        }
        return "Invalid format: \"" + strConcat + "\" is malformed at \"" + strConcat.substring(i2) + Typography.quote;
    }

    public static void appendPaddedInteger(StringBuffer stringBuffer, int i2, int i3) {
        if (i2 < 0) {
            stringBuffer.append('-');
            if (i2 == Integer.MIN_VALUE) {
                while (i3 > 10) {
                    stringBuffer.append('0');
                    i3--;
                }
                stringBuffer.append("2147483648");
                return;
            }
            i2 = -i2;
        }
        if (i2 < 10) {
            while (i3 > 1) {
                stringBuffer.append('0');
                i3--;
            }
            stringBuffer.append((char) (i2 + 48));
            return;
        }
        if (i2 >= 100) {
            int iLog = i2 < 1000 ? 3 : i2 < 10000 ? 4 : ((int) (Math.log(i2) / LOG_10)) + 1;
            while (i3 > iLog) {
                stringBuffer.append('0');
                i3--;
            }
            stringBuffer.append(Integer.toString(i2));
            return;
        }
        while (i3 > 2) {
            stringBuffer.append('0');
            i3--;
        }
        int i4 = ((i2 + 1) * 13421772) >> 27;
        stringBuffer.append((char) (i4 + 48));
        stringBuffer.append((char) (((i2 - (i4 << 3)) - (i4 << 1)) + 48));
    }

    public static void appendUnpaddedInteger(StringBuffer stringBuffer, int i2) {
        if (i2 < 0) {
            stringBuffer.append('-');
            if (i2 == Integer.MIN_VALUE) {
                stringBuffer.append("2147483648");
                return;
            }
            i2 = -i2;
        }
        if (i2 < 10) {
            stringBuffer.append((char) (i2 + 48));
        } else {
            if (i2 >= 100) {
                stringBuffer.append(Integer.toString(i2));
                return;
            }
            int i3 = ((i2 + 1) * 13421772) >> 27;
            stringBuffer.append((char) (i3 + 48));
            stringBuffer.append((char) (((i2 - (i3 << 3)) - (i3 << 1)) + 48));
        }
    }

    static int b(String str, int i2) {
        int iCharAt = str.charAt(i2) - '0';
        return (((iCharAt << 3) + (iCharAt << 1)) + str.charAt(i2 + 1)) - 48;
    }

    public static int calculateDigitCount(long j2) {
        if (j2 < 0) {
            if (j2 != Long.MIN_VALUE) {
                return calculateDigitCount(-j2) + 1;
            }
            return 20;
        }
        if (j2 < 10) {
            return 1;
        }
        if (j2 < 100) {
            return 2;
        }
        if (j2 < 1000) {
            return 3;
        }
        if (j2 < 10000) {
            return 4;
        }
        return 1 + ((int) (Math.log(j2) / LOG_10));
    }

    public static void writePaddedInteger(Writer writer, int i2, int i3) throws IOException {
        if (i2 < 0) {
            writer.write(45);
            if (i2 == Integer.MIN_VALUE) {
                while (i3 > 10) {
                    writer.write(48);
                    i3--;
                }
                writer.write("2147483648");
                return;
            }
            i2 = -i2;
        }
        if (i2 < 10) {
            while (i3 > 1) {
                writer.write(48);
                i3--;
            }
            writer.write(i2 + 48);
            return;
        }
        if (i2 >= 100) {
            int iLog = i2 < 1000 ? 3 : i2 < 10000 ? 4 : ((int) (Math.log(i2) / LOG_10)) + 1;
            while (i3 > iLog) {
                writer.write(48);
                i3--;
            }
            writer.write(Integer.toString(i2));
            return;
        }
        while (i3 > 2) {
            writer.write(48);
            i3--;
        }
        int i4 = ((i2 + 1) * 13421772) >> 27;
        writer.write(i4 + 48);
        writer.write(((i2 - (i4 << 3)) - (i4 << 1)) + 48);
    }

    public static void writeUnpaddedInteger(Writer writer, int i2) throws IOException {
        if (i2 < 0) {
            writer.write(45);
            if (i2 == Integer.MIN_VALUE) {
                writer.write("2147483648");
                return;
            }
            i2 = -i2;
        }
        if (i2 < 10) {
            writer.write(i2 + 48);
        } else {
            if (i2 >= 100) {
                writer.write(Integer.toString(i2));
                return;
            }
            int i3 = ((i2 + 1) * 13421772) >> 27;
            writer.write(i3 + 48);
            writer.write(((i2 - (i3 << 3)) - (i3 << 1)) + 48);
        }
    }

    public static void appendUnpaddedInteger(StringBuffer stringBuffer, long j2) {
        int i2 = (int) j2;
        if (i2 == j2) {
            appendUnpaddedInteger(stringBuffer, i2);
        } else {
            stringBuffer.append(Long.toString(j2));
        }
    }

    public static void writeUnpaddedInteger(Writer writer, long j2) throws IOException {
        int i2 = (int) j2;
        if (i2 == j2) {
            writeUnpaddedInteger(writer, i2);
        } else {
            writer.write(Long.toString(j2));
        }
    }

    public static void appendPaddedInteger(StringBuffer stringBuffer, long j2, int i2) {
        int i3 = (int) j2;
        if (i3 == j2) {
            appendPaddedInteger(stringBuffer, i3, i2);
            return;
        }
        if (i2 <= 19) {
            stringBuffer.append(Long.toString(j2));
            return;
        }
        if (j2 < 0) {
            stringBuffer.append('-');
            if (j2 == Long.MIN_VALUE) {
                while (i2 > 19) {
                    stringBuffer.append('0');
                    i2--;
                }
                stringBuffer.append("9223372036854775808");
                return;
            }
            j2 = -j2;
        }
        int iLog = ((int) (Math.log(j2) / LOG_10)) + 1;
        while (i2 > iLog) {
            stringBuffer.append('0');
            i2--;
        }
        stringBuffer.append(Long.toString(j2));
    }

    public static void writePaddedInteger(Writer writer, long j2, int i2) throws IOException {
        int i3 = (int) j2;
        if (i3 == j2) {
            writePaddedInteger(writer, i3, i2);
            return;
        }
        if (i2 <= 19) {
            writer.write(Long.toString(j2));
            return;
        }
        if (j2 < 0) {
            writer.write(45);
            if (j2 == Long.MIN_VALUE) {
                while (i2 > 19) {
                    writer.write(48);
                    i2--;
                }
                writer.write("9223372036854775808");
                return;
            }
            j2 = -j2;
        }
        int iLog = ((int) (Math.log(j2) / LOG_10)) + 1;
        while (i2 > iLog) {
            writer.write(48);
            i2--;
        }
        writer.write(Long.toString(j2));
    }
}
