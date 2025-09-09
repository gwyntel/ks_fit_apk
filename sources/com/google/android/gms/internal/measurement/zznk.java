package com.google.android.gms.internal.measurement;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alipay.sdk.m.u.i;
import com.kingsmith.miot.KsProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
final class zznk {
    private static final char[] zza;

    static {
        char[] cArr = new char[80];
        zza = cArr;
        Arrays.fill(cArr, ' ');
    }

    static String a(zznj zznjVar, String str) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zza(zznjVar, sb, 0);
        return sb.toString();
    }

    static void b(StringBuilder sb, int i2, String str, Object obj) throws SecurityException {
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
        zza(i2, sb);
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
            sb.append(zzos.a(zzkm.zza((String) obj)));
            sb.append(Typography.quote);
            return;
        }
        if (obj instanceof zzkm) {
            sb.append(": \"");
            sb.append(zzos.a((zzkm) obj));
            sb.append(Typography.quote);
            return;
        }
        if (obj instanceof zzlw) {
            sb.append(" {");
            zza((zzlw) obj, sb, i2 + 2);
            sb.append("\n");
            zza(i2, sb);
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
        zza(i2, sb);
        sb.append(i.f9804d);
    }

    private static void zza(int i2, StringBuilder sb) {
        while (i2 > 0) {
            char[] cArr = zza;
            int length = i2 > cArr.length ? cArr.length : i2;
            sb.append(cArr, 0, length);
            i2 -= length;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0199  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zza(com.google.android.gms.internal.measurement.zznj r20, java.lang.StringBuilder r21, int r22) throws java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 588
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznk.zza(com.google.android.gms.internal.measurement.zznj, java.lang.StringBuilder, int):void");
    }
}
