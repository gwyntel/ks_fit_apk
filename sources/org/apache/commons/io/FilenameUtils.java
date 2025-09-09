package org.apache.commons.io;

import androidx.webkit.ProxyConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

/* loaded from: classes5.dex */
public class FilenameUtils {
    public static final char EXTENSION_SEPARATOR = '.';
    private static final int NOT_FOUND = -1;
    private static final char OTHER_SEPARATOR;
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
    private static final char SYSTEM_SEPARATOR = File.separatorChar;

    static {
        if (a()) {
            OTHER_SEPARATOR = '/';
        } else {
            OTHER_SEPARATOR = '\\';
        }
    }

    static boolean a() {
        return SYSTEM_SEPARATOR == '\\';
    }

    static String[] b(String str) {
        if (str.indexOf(63) == -1 && str.indexOf(42) == -1) {
            return new String[]{str};
        }
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int length = charArray.length;
        int i2 = 0;
        char c2 = 0;
        while (i2 < length) {
            char c3 = charArray[i2];
            if (c3 == '?' || c3 == '*') {
                if (sb.length() != 0) {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                }
                if (c3 == '?') {
                    arrayList.add("?");
                } else if (c2 != '*') {
                    arrayList.add(ProxyConfig.MATCH_ALL_SCHEMES);
                }
            } else {
                sb.append(c3);
            }
            i2++;
            c2 = c3;
        }
        if (sb.length() != 0) {
            arrayList.add(sb.toString());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String concat(String str, String str2) {
        int prefixLength = getPrefixLength(str2);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength > 0) {
            return normalize(str2);
        }
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return normalize(str2);
        }
        if (isSeparator(str.charAt(length - 1))) {
            return normalize(str + str2);
        }
        return normalize(str + '/' + str2);
    }

    public static boolean directoryContains(String str, String str2) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        if (str2 == null) {
            return false;
        }
        IOCase iOCase = IOCase.SYSTEM;
        if (iOCase.checkEquals(str, str2)) {
            return false;
        }
        return iOCase.checkStartsWith(str2, str);
    }

    private static String doGetFullPath(String str, boolean z2) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        if (prefixLength >= str.length()) {
            return z2 ? getPrefix(str) : str;
        }
        int iIndexOfLastSeparator = indexOfLastSeparator(str);
        if (iIndexOfLastSeparator < 0) {
            return str.substring(0, prefixLength);
        }
        int i2 = iIndexOfLastSeparator + (z2 ? 1 : 0);
        if (i2 == 0) {
            i2++;
        }
        return str.substring(0, i2);
    }

    private static String doGetPath(String str, int i2) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        int iIndexOfLastSeparator = indexOfLastSeparator(str);
        int i3 = i2 + iIndexOfLastSeparator;
        if (prefixLength >= str.length() || iIndexOfLastSeparator < 0 || prefixLength >= i3) {
            return "";
        }
        String strSubstring = str.substring(prefixLength, i3);
        failIfNullBytePresent(strSubstring);
        return strSubstring;
    }

    private static String doNormalize(String str, char c2, boolean z2) {
        boolean z3;
        if (str == null) {
            return null;
        }
        failIfNullBytePresent(str);
        int length = str.length();
        if (length == 0) {
            return str;
        }
        int prefixLength = getPrefixLength(str);
        if (prefixLength < 0) {
            return null;
        }
        int i2 = length + 2;
        char[] cArr = new char[i2];
        str.getChars(0, str.length(), cArr, 0);
        char c3 = SYSTEM_SEPARATOR;
        if (c2 == c3) {
            c3 = OTHER_SEPARATOR;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (cArr[i3] == c3) {
                cArr[i3] = c2;
            }
        }
        if (cArr[length - 1] != c2) {
            cArr[length] = c2;
            length++;
            z3 = false;
        } else {
            z3 = true;
        }
        int i4 = prefixLength + 1;
        int i5 = i4;
        while (i5 < length) {
            if (cArr[i5] == c2) {
                int i6 = i5 - 1;
                if (cArr[i6] == c2) {
                    System.arraycopy(cArr, i5, cArr, i6, length - i5);
                    length--;
                    i5--;
                }
            }
            i5++;
        }
        int i7 = i4;
        while (i7 < length) {
            if (cArr[i7] == c2) {
                int i8 = i7 - 1;
                if (cArr[i8] == '.' && (i7 == i4 || cArr[i7 - 2] == c2)) {
                    if (i7 == length - 1) {
                        z3 = true;
                    }
                    System.arraycopy(cArr, i7 + 1, cArr, i8, length - i7);
                    length -= 2;
                    i7--;
                }
            }
            i7++;
        }
        int i9 = prefixLength + 2;
        int i10 = i9;
        while (i10 < length) {
            if (cArr[i10] == c2 && cArr[i10 - 1] == '.' && cArr[i10 - 2] == '.' && (i10 == i9 || cArr[i10 - 3] == c2)) {
                if (i10 == i9) {
                    return null;
                }
                if (i10 == length - 1) {
                    z3 = true;
                }
                int i11 = i10 - 4;
                while (true) {
                    if (i11 < prefixLength) {
                        int i12 = i10 + 1;
                        System.arraycopy(cArr, i12, cArr, prefixLength, length - i10);
                        length -= i12 - prefixLength;
                        i10 = i4;
                        break;
                    }
                    if (cArr[i11] == c2) {
                        int i13 = i11 + 1;
                        System.arraycopy(cArr, i10 + 1, cArr, i13, length - i10);
                        length -= i10 - i11;
                        i10 = i13;
                        break;
                    }
                    i11--;
                }
            }
            i10++;
        }
        return length <= 0 ? "" : length <= prefixLength ? new String(cArr, 0, length) : (z3 && z2) ? new String(cArr, 0, length) : new String(cArr, 0, length - 1);
    }

    public static boolean equals(String str, String str2) {
        return equals(str, str2, false, IOCase.SENSITIVE);
    }

    public static boolean equalsNormalized(String str, String str2) {
        return equals(str, str2, true, IOCase.SENSITIVE);
    }

    public static boolean equalsNormalizedOnSystem(String str, String str2) {
        return equals(str, str2, true, IOCase.SYSTEM);
    }

    public static boolean equalsOnSystem(String str, String str2) {
        return equals(str, str2, false, IOCase.SYSTEM);
    }

    private static void failIfNullBytePresent(String str) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (str.charAt(i2) == 0) {
                throw new IllegalArgumentException("Null byte present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it");
            }
        }
    }

    public static String getBaseName(String str) {
        return removeExtension(getName(str));
    }

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        }
        int iIndexOfExtension = indexOfExtension(str);
        return iIndexOfExtension == -1 ? "" : str.substring(iIndexOfExtension + 1);
    }

    public static String getFullPath(String str) {
        return doGetFullPath(str, true);
    }

    public static String getFullPathNoEndSeparator(String str) {
        return doGetFullPath(str, false);
    }

    public static String getName(String str) {
        if (str == null) {
            return null;
        }
        failIfNullBytePresent(str);
        return str.substring(indexOfLastSeparator(str) + 1);
    }

    public static String getPath(String str) {
        return doGetPath(str, 1);
    }

    public static String getPathNoEndSeparator(String str) {
        return doGetPath(str, 0);
    }

    public static String getPrefix(String str) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        if (prefixLength <= str.length()) {
            String strSubstring = str.substring(0, prefixLength);
            failIfNullBytePresent(strSubstring);
            return strSubstring;
        }
        failIfNullBytePresent(str + '/');
        return str + '/';
    }

    public static int getPrefixLength(String str) {
        int iMin;
        if (str == null) {
            return -1;
        }
        int length = str.length();
        if (length == 0) {
            return 0;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt == ':') {
            return -1;
        }
        if (length == 1) {
            if (cCharAt == '~') {
                return 2;
            }
            return isSeparator(cCharAt) ? 1 : 0;
        }
        if (cCharAt == '~') {
            int iIndexOf = str.indexOf(47, 1);
            int iIndexOf2 = str.indexOf(92, 1);
            if (iIndexOf == -1 && iIndexOf2 == -1) {
                return length + 1;
            }
            if (iIndexOf == -1) {
                iIndexOf = iIndexOf2;
            }
            if (iIndexOf2 == -1) {
                iIndexOf2 = iIndexOf;
            }
            iMin = Math.min(iIndexOf, iIndexOf2);
        } else {
            char cCharAt2 = str.charAt(1);
            if (cCharAt2 == ':') {
                char upperCase = Character.toUpperCase(cCharAt);
                return (upperCase < 'A' || upperCase > 'Z') ? upperCase == '/' ? 1 : -1 : (length == 2 || !isSeparator(str.charAt(2))) ? 2 : 3;
            }
            if (!isSeparator(cCharAt) || !isSeparator(cCharAt2)) {
                return isSeparator(cCharAt) ? 1 : 0;
            }
            int iIndexOf3 = str.indexOf(47, 2);
            int iIndexOf4 = str.indexOf(92, 2);
            if ((iIndexOf3 == -1 && iIndexOf4 == -1) || iIndexOf3 == 2 || iIndexOf4 == 2) {
                return -1;
            }
            if (iIndexOf3 == -1) {
                iIndexOf3 = iIndexOf4;
            }
            if (iIndexOf4 == -1) {
                iIndexOf4 = iIndexOf3;
            }
            iMin = Math.min(iIndexOf3, iIndexOf4);
        }
        return iMin + 1;
    }

    public static int indexOfExtension(String str) {
        int iLastIndexOf;
        if (str != null && indexOfLastSeparator(str) <= (iLastIndexOf = str.lastIndexOf(46))) {
            return iLastIndexOf;
        }
        return -1;
    }

    public static int indexOfLastSeparator(String str) {
        if (str == null) {
            return -1;
        }
        return Math.max(str.lastIndexOf(47), str.lastIndexOf(92));
    }

    public static boolean isExtension(String str, String str2) {
        if (str == null) {
            return false;
        }
        failIfNullBytePresent(str);
        return (str2 == null || str2.isEmpty()) ? indexOfExtension(str) == -1 : getExtension(str).equals(str2);
    }

    private static boolean isSeparator(char c2) {
        return c2 == '/' || c2 == '\\';
    }

    public static String normalize(String str) {
        return doNormalize(str, SYSTEM_SEPARATOR, true);
    }

    public static String normalizeNoEndSeparator(String str) {
        return doNormalize(str, SYSTEM_SEPARATOR, false);
    }

    public static String removeExtension(String str) {
        if (str == null) {
            return null;
        }
        failIfNullBytePresent(str);
        int iIndexOfExtension = indexOfExtension(str);
        return iIndexOfExtension == -1 ? str : str.substring(0, iIndexOfExtension);
    }

    public static String separatorsToSystem(String str) {
        if (str == null) {
            return null;
        }
        return a() ? separatorsToWindows(str) : separatorsToUnix(str);
    }

    public static String separatorsToUnix(String str) {
        return (str == null || str.indexOf(92) == -1) ? str : str.replace('\\', '/');
    }

    public static String separatorsToWindows(String str) {
        return (str == null || str.indexOf(47) == -1) ? str : str.replace('/', '\\');
    }

    public static boolean wildcardMatch(String str, String str2) {
        return wildcardMatch(str, str2, IOCase.SENSITIVE);
    }

    public static boolean wildcardMatchOnSystem(String str, String str2) {
        return wildcardMatch(str, str2, IOCase.SYSTEM);
    }

    public static boolean equals(String str, String str2, boolean z2, IOCase iOCase) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        }
        if (z2) {
            str = normalize(str);
            str2 = normalize(str2);
            if (str == null || str2 == null) {
                throw new NullPointerException("Error normalizing one or both of the file names");
            }
        }
        if (iOCase == null) {
            iOCase = IOCase.SENSITIVE;
        }
        return iOCase.checkEquals(str, str2);
    }

    public static String normalize(String str, boolean z2) {
        return doNormalize(str, z2 ? '/' : '\\', true);
    }

    public static String normalizeNoEndSeparator(String str, boolean z2) {
        return doNormalize(str, z2 ? '/' : '\\', false);
    }

    public static boolean wildcardMatch(String str, String str2, IOCase iOCase) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str != null && str2 != null) {
            if (iOCase == null) {
                iOCase = IOCase.SENSITIVE;
            }
            String[] strArrB = b(str2);
            Stack stack = new Stack();
            boolean z2 = false;
            int length = 0;
            int i2 = 0;
            do {
                if (stack.size() > 0) {
                    int[] iArr = (int[]) stack.pop();
                    i2 = iArr[0];
                    length = iArr[1];
                    z2 = true;
                }
                while (i2 < strArrB.length) {
                    if (strArrB[i2].equals("?")) {
                        length++;
                        if (length > str.length()) {
                            break;
                        }
                        z2 = false;
                        i2++;
                    } else if (strArrB[i2].equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
                        if (i2 == strArrB.length - 1) {
                            length = str.length();
                        }
                        z2 = true;
                        i2++;
                    } else {
                        if (z2) {
                            length = iOCase.checkIndexOf(str, length, strArrB[i2]);
                            if (length == -1) {
                                break;
                            }
                            int iCheckIndexOf = iOCase.checkIndexOf(str, length + 1, strArrB[i2]);
                            if (iCheckIndexOf >= 0) {
                                stack.push(new int[]{i2, iCheckIndexOf});
                            }
                            length += strArrB[i2].length();
                            z2 = false;
                        } else {
                            if (!iOCase.checkRegionMatches(str, length, strArrB[i2])) {
                                break;
                            }
                            length += strArrB[i2].length();
                            z2 = false;
                        }
                        i2++;
                    }
                }
                if (i2 == strArrB.length && length == str.length()) {
                    return true;
                }
            } while (stack.size() > 0);
        }
        return false;
    }

    public static boolean isExtension(String str, String[] strArr) {
        if (str == null) {
            return false;
        }
        failIfNullBytePresent(str);
        if (strArr == null || strArr.length == 0) {
            return indexOfExtension(str) == -1;
        }
        String extension = getExtension(str);
        for (String str2 : strArr) {
            if (extension.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExtension(String str, Collection<String> collection) {
        if (str == null) {
            return false;
        }
        failIfNullBytePresent(str);
        if (collection == null || collection.isEmpty()) {
            return indexOfExtension(str) == -1;
        }
        String extension = getExtension(str);
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            if (extension.equals(it.next())) {
                return true;
            }
        }
        return false;
    }
}
