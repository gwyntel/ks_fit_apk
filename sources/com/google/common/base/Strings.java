package com.google.common.base;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.common.annotations.GwtCompatible;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Strings {
    private Strings() {
    }

    static boolean a(CharSequence charSequence, int i2) {
        return i2 >= 0 && i2 <= charSequence.length() + (-2) && Character.isHighSurrogate(charSequence.charAt(i2)) && Character.isLowSurrogate(charSequence.charAt(i2 + 1));
    }

    public static String commonPrefix(CharSequence charSequence, CharSequence charSequence2) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int iMin = Math.min(charSequence.length(), charSequence2.length());
        int i2 = 0;
        while (i2 < iMin && charSequence.charAt(i2) == charSequence2.charAt(i2)) {
            i2++;
        }
        int i3 = i2 - 1;
        if (a(charSequence, i3) || a(charSequence2, i3)) {
            i2--;
        }
        return charSequence.subSequence(0, i2).toString();
    }

    public static String commonSuffix(CharSequence charSequence, CharSequence charSequence2) {
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int iMin = Math.min(charSequence.length(), charSequence2.length());
        int i2 = 0;
        while (i2 < iMin && charSequence.charAt((charSequence.length() - i2) - 1) == charSequence2.charAt((charSequence2.length() - i2) - 1)) {
            i2++;
        }
        if (a(charSequence, (charSequence.length() - i2) - 1) || a(charSequence2, (charSequence2.length() - i2) - 1)) {
            i2--;
        }
        return charSequence.subSequence(charSequence.length() - i2, charSequence.length()).toString();
    }

    @CheckForNull
    public static String emptyToNull(@CheckForNull String str) {
        return Platform.b(str);
    }

    public static boolean isNullOrEmpty(@CheckForNull String str) {
        return Platform.h(str);
    }

    public static String lenientFormat(@CheckForNull String str, @CheckForNull Object... objArr) {
        int iIndexOf;
        String strValueOf = String.valueOf(str);
        int i2 = 0;
        if (objArr == null) {
            objArr = new Object[]{"(Object[])null"};
        } else {
            for (int i3 = 0; i3 < objArr.length; i3++) {
                objArr[i3] = lenientToString(objArr[i3]);
            }
        }
        StringBuilder sb = new StringBuilder(strValueOf.length() + (objArr.length * 16));
        int i4 = 0;
        while (i2 < objArr.length && (iIndexOf = strValueOf.indexOf("%s", i4)) != -1) {
            sb.append((CharSequence) strValueOf, i4, iIndexOf);
            sb.append(objArr[i2]);
            i4 = iIndexOf + 2;
            i2++;
        }
        sb.append((CharSequence) strValueOf, i4, strValueOf.length());
        if (i2 < objArr.length) {
            sb.append(" [");
            sb.append(objArr[i2]);
            for (int i5 = i2 + 1; i5 < objArr.length; i5++) {
                sb.append(", ");
                sb.append(objArr[i5]);
            }
            sb.append(']');
        }
        return sb.toString();
    }

    private static String lenientToString(@CheckForNull Object obj) {
        if (obj == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        try {
            return obj.toString();
        } catch (Exception e2) {
            String str = obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj));
            Logger.getLogger("com.google.common.base.Strings").log(Level.WARNING, "Exception during lenientFormat for " + str, (Throwable) e2);
            return "<" + str + " threw " + e2.getClass().getName() + ">";
        }
    }

    public static String nullToEmpty(@CheckForNull String str) {
        return Platform.e(str);
    }

    public static String padEnd(String str, int i2, char c2) {
        Preconditions.checkNotNull(str);
        if (str.length() >= i2) {
            return str;
        }
        StringBuilder sb = new StringBuilder(i2);
        sb.append(str);
        for (int length = str.length(); length < i2; length++) {
            sb.append(c2);
        }
        return sb.toString();
    }

    public static String padStart(String str, int i2, char c2) {
        Preconditions.checkNotNull(str);
        if (str.length() >= i2) {
            return str;
        }
        StringBuilder sb = new StringBuilder(i2);
        for (int length = str.length(); length < i2; length++) {
            sb.append(c2);
        }
        sb.append(str);
        return sb.toString();
    }

    public static String repeat(String str, int i2) {
        Preconditions.checkNotNull(str);
        if (i2 <= 1) {
            Preconditions.checkArgument(i2 >= 0, "invalid count: %s", i2);
            return i2 == 0 ? "" : str;
        }
        int length = str.length();
        long j2 = length * i2;
        int i3 = (int) j2;
        if (i3 != j2) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + j2);
        }
        char[] cArr = new char[i3];
        str.getChars(0, length, cArr, 0);
        while (true) {
            int i4 = i3 - length;
            if (length >= i4) {
                System.arraycopy(cArr, 0, cArr, length, i4);
                return new String(cArr);
            }
            System.arraycopy(cArr, 0, cArr, length, length);
            length <<= 1;
        }
    }
}
