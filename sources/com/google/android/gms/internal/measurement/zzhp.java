package com.google.android.gms.internal.measurement;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public final class zzhp {
    public static String zza(@CheckForNull String str, @CheckForNull Object... objArr) {
        int iIndexOf;
        String strValueOf = String.valueOf(str);
        int i2 = 0;
        for (int i3 = 0; i3 < objArr.length; i3++) {
            objArr[i3] = zza(objArr[i3]);
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

    private static String zza(@CheckForNull Object obj) {
        if (obj == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        try {
            return obj.toString();
        } catch (Exception e2) {
            String str = obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
            Logger.getLogger("com.google.common.base.Strings").logp(Level.WARNING, "com.google.common.base.Strings", "lenientToString", "Exception during lenientFormat for " + str, (Throwable) e2);
            return "<" + str + " threw " + e2.getClass().getName() + ">";
        }
    }
}
