package com.google.android.gms.internal.identity;

import androidx.annotation.GuardedBy;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class zzeo {
    private static final SimpleDateFormat zza;
    private static final SimpleDateFormat zzb;

    @GuardedBy("sharedStringBuilder")
    private static final StringBuilder zzc;

    static {
        Locale locale = Locale.ROOT;
        zza = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", locale);
        zzb = new SimpleDateFormat("MM-dd HH:mm:ss", locale);
        zzc = new StringBuilder(33);
    }

    public static String zza(long j2) {
        return j2 >= 0 ? zza.format(new Date(j2)) : Long.toString(j2);
    }

    public static String zzb(long j2) {
        String string;
        StringBuilder sb = zzc;
        synchronized (sb) {
            sb.setLength(0);
            zzc(j2, sb);
            string = sb.toString();
        }
        return string;
    }

    public static StringBuilder zzc(long j2, StringBuilder sb) {
        if (j2 == 0) {
            sb.append("0s");
            return sb;
        }
        sb.ensureCapacity(sb.length() + 27);
        boolean z2 = false;
        if (j2 < 0) {
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            if (j2 != Long.MIN_VALUE) {
                j2 = -j2;
            } else {
                j2 = Long.MAX_VALUE;
                z2 = true;
            }
        }
        if (j2 >= 86400000) {
            sb.append(j2 / 86400000);
            sb.append("d");
            j2 %= 86400000;
        }
        if (true == z2) {
            j2 = 25975808;
        }
        if (j2 >= 3600000) {
            sb.append(j2 / 3600000);
            sb.append("h");
            j2 %= 3600000;
        }
        if (j2 >= 60000) {
            sb.append(j2 / 60000);
            sb.append("m");
            j2 %= 60000;
        }
        if (j2 >= 1000) {
            sb.append(j2 / 1000);
            sb.append("s");
            j2 %= 1000;
        }
        if (j2 > 0) {
            sb.append(j2);
            sb.append("ms");
        }
        return sb;
    }
}
