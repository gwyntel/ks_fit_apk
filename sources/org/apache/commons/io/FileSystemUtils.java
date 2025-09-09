package org.apache.commons.io;

import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

@Deprecated
/* loaded from: classes5.dex */
public class FileSystemUtils {
    private static final String DF;
    private static final int INIT_PROBLEM = -1;
    private static final FileSystemUtils INSTANCE = new FileSystemUtils();
    private static final int OS;
    private static final int OTHER = 0;
    private static final int POSIX_UNIX = 3;
    private static final int UNIX = 2;
    private static final int WINDOWS = 1;

    static {
        int i2;
        String property;
        String str = "df";
        try {
            property = System.getProperty("os.name");
        } catch (Exception unused) {
            i2 = -1;
        }
        if (property == null) {
            throw new IOException("os.name not found");
        }
        String lowerCase = property.toLowerCase(Locale.ENGLISH);
        if (lowerCase.contains("windows")) {
            i2 = 1;
        } else if (lowerCase.contains("linux") || lowerCase.contains("mpe/ix") || lowerCase.contains("freebsd") || lowerCase.contains("openbsd") || lowerCase.contains("irix") || lowerCase.contains("digital unix") || lowerCase.contains("unix") || lowerCase.contains("mac os x")) {
            i2 = 2;
        } else {
            if (lowerCase.contains("sun os") || lowerCase.contains("sunos") || lowerCase.contains("solaris")) {
                str = "/usr/xpg4/bin/df";
            } else if (!lowerCase.contains("hp-ux") && !lowerCase.contains("aix")) {
                i2 = 0;
            }
            i2 = 3;
        }
        OS = i2;
        DF = str;
    }

    @Deprecated
    public static long freeSpace(String str) throws IOException {
        return INSTANCE.a(str, OS, false, -1L);
    }

    @Deprecated
    public static long freeSpaceKb(String str) throws IOException {
        return freeSpaceKb(str, -1L);
    }

    long a(String str, int i2, boolean z2, long j2) throws Throwable {
        if (str == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        if (i2 == 0) {
            throw new IllegalStateException("Unsupported operating system");
        }
        if (i2 == 1) {
            long jC = c(str, j2);
            return z2 ? jC / 1024 : jC;
        }
        if (i2 == 2) {
            return b(str, z2, false, j2);
        }
        if (i2 == 3) {
            return b(str, z2, true, j2);
        }
        throw new IllegalStateException("Exception caught when determining operating system");
    }

    long b(String str, boolean z2, boolean z3, long j2) throws Throwable {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        String str2 = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        if (z2) {
            str2 = Constants.ACCEPT_TIME_SEPARATOR_SERVER + "k";
        }
        if (z3) {
            str2 = str2 + "P";
        }
        List listG = g(str2.length() > 1 ? new String[]{DF, str2, str} : new String[]{DF, str}, 3, j2);
        if (listG.size() < 2) {
            throw new IOException("Command line '" + DF + "' did not return info as expected for path '" + str + "'- response was " + listG);
        }
        StringTokenizer stringTokenizer = new StringTokenizer((String) listG.get(1), " ");
        if (stringTokenizer.countTokens() >= 4) {
            stringTokenizer.nextToken();
        } else {
            if (stringTokenizer.countTokens() != 1 || listG.size() < 3) {
                throw new IOException("Command line '" + DF + "' did not return data as expected for path '" + str + "'- check path is valid");
            }
            stringTokenizer = new StringTokenizer((String) listG.get(2), " ");
        }
        stringTokenizer.nextToken();
        stringTokenizer.nextToken();
        return e(stringTokenizer.nextToken(), str);
    }

    long c(String str, long j2) throws Throwable {
        String strNormalize = FilenameUtils.normalize(str, false);
        if (strNormalize == null) {
            throw new IllegalArgumentException(str);
        }
        if (strNormalize.length() > 0 && strNormalize.charAt(0) != '\"') {
            strNormalize = "\"" + strNormalize + "\"";
        }
        List listG = g(new String[]{"cmd.exe", "/C", "dir /a /-c " + strNormalize}, Integer.MAX_VALUE, j2);
        for (int size = listG.size() + (-1); size >= 0; size--) {
            String str2 = (String) listG.get(size);
            if (str2.length() > 0) {
                return f(str2, strNormalize);
            }
        }
        throw new IOException("Command line 'dir /-c' did not return any info for path '" + strNormalize + "'");
    }

    Process d(String[] strArr) {
        return Runtime.getRuntime().exec(strArr);
    }

    long e(String str, String str2) throws NumberFormatException, IOException {
        try {
            long j2 = Long.parseLong(str);
            if (j2 >= 0) {
                return j2;
            }
            throw new IOException("Command line '" + DF + "' did not find free space in response for path '" + str2 + "'- check path is valid");
        } catch (NumberFormatException e2) {
            throw new IOException("Command line '" + DF + "' did not return numeric data as expected for path '" + str2 + "'- check path is valid", e2);
        }
    }

    long f(String str, String str2) throws IOException {
        int i2;
        int i3;
        int i4;
        int length = str.length();
        while (true) {
            length--;
            i2 = 0;
            if (length < 0) {
                i3 = 0;
                break;
            }
            if (Character.isDigit(str.charAt(length))) {
                i3 = length + 1;
                break;
            }
        }
        while (true) {
            if (length < 0) {
                i4 = 0;
                break;
            }
            char cCharAt = str.charAt(length);
            if (!Character.isDigit(cCharAt) && cCharAt != ',' && cCharAt != '.') {
                i4 = length + 1;
                break;
            }
            length--;
        }
        if (length < 0) {
            throw new IOException("Command line 'dir /-c' did not return valid info for path '" + str2 + "'");
        }
        StringBuilder sb = new StringBuilder(str.substring(i4, i3));
        while (i2 < sb.length()) {
            if (sb.charAt(i2) == ',' || sb.charAt(i2) == '.') {
                sb.deleteCharAt(i2);
                i2--;
            }
            i2++;
        }
        return e(sb.toString(), str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:68:0x012e  */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.io.Reader] */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.util.List g(java.lang.String[] r11, int r12, long r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.FileSystemUtils.g(java.lang.String[], int, long):java.util.List");
    }

    @Deprecated
    public static long freeSpaceKb(String str, long j2) throws IOException {
        return INSTANCE.a(str, OS, true, j2);
    }

    @Deprecated
    public static long freeSpaceKb() throws IOException {
        return freeSpaceKb(-1L);
    }

    @Deprecated
    public static long freeSpaceKb(long j2) throws IOException {
        return freeSpaceKb(new File(".").getAbsolutePath(), j2);
    }
}
