package com.google.android.gms.internal.auth;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alipay.sdk.m.u.i;
import com.kingsmith.miot.KsProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
final class zzfz {
    private static final char[] zza;

    static {
        char[] cArr = new char[80];
        zza = cArr;
        Arrays.fill(cArr, ' ');
    }

    static String a(zzfx zzfxVar, String str) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zzd(zzfxVar, sb, 0);
        return sb.toString();
    }

    static void b(StringBuilder sb, int i2, String str, Object obj) {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                b(sb, i2, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                b(sb, i2, str, (Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        zzc(i2, sb);
        if (!str.isEmpty()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Character.toLowerCase(str.charAt(0)));
            for (int i3 = 1; i3 < str.length(); i3++) {
                char cCharAt = str.charAt(i3);
                if (Character.isUpperCase(cCharAt)) {
                    sb2.append(OpenAccountUIConstants.UNDER_LINE);
                }
                sb2.append(Character.toLowerCase(cCharAt));
            }
            str = sb2.toString();
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"");
            sb.append(zzgx.a(new zzec(((String) obj).getBytes(zzfa.f13017b))));
            sb.append(Typography.quote);
            return;
        }
        if (obj instanceof zzef) {
            sb.append(": \"");
            sb.append(zzgx.a((zzef) obj));
            sb.append(Typography.quote);
            return;
        }
        if (obj instanceof zzev) {
            sb.append(" {");
            zzd((zzev) obj, sb, i2 + 2);
            sb.append("\n");
            zzc(i2, sb);
            sb.append(i.f9804d);
            return;
        }
        if (!(obj instanceof Map.Entry)) {
            sb.append(": ");
            sb.append(obj);
            return;
        }
        sb.append(" {");
        Map.Entry entry = (Map.Entry) obj;
        int i4 = i2 + 2;
        b(sb, i4, KsProperty.Key, entry.getKey());
        b(sb, i4, "value", entry.getValue());
        sb.append("\n");
        zzc(i2, sb);
        sb.append(i.f9804d);
    }

    private static void zzc(int i2, StringBuilder sb) {
        while (i2 > 0) {
            int i3 = 80;
            if (i2 <= 80) {
                i3 = i2;
            }
            sb.append(zza, 0, i3);
            i2 -= i3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01fa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zzd(com.google.android.gms.internal.auth.zzfx r18, java.lang.StringBuilder r19, int r20) throws java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 529
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzfz.zzd(com.google.android.gms.internal.auth.zzfx, java.lang.StringBuilder, int):void");
    }
}
