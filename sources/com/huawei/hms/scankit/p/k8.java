package com.huawei.hms.scankit.p;

import android.text.TextUtils;
import com.huawei.hms.ml.scan.HmsScan;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class k8 extends t6 {

    /* renamed from: b, reason: collision with root package name */
    private static final Pattern f17483b = Pattern.compile("WIFI:[^:]", 2);

    /* renamed from: c, reason: collision with root package name */
    static final String[] f17484c = new String[0];

    static String[] a(String str, String str2, char c2, boolean z2) {
        int length = str2.length();
        ArrayList arrayList = null;
        int i2 = 0;
        while (i2 < length) {
            int iIndexOf = str2.indexOf(str, i2);
            if (iIndexOf < 0) {
                break;
            }
            int length2 = iIndexOf + str.length();
            boolean z3 = true;
            ArrayList arrayList2 = arrayList;
            int length3 = length2;
            while (z3) {
                int iIndexOf2 = str2.indexOf(c2, length3);
                if (iIndexOf2 < 0) {
                    length3 = str2.length();
                } else if (a(str2, iIndexOf2) % 2 != 0) {
                    length3 = iIndexOf2 + 1;
                } else {
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList(3);
                    }
                    String strB = t6.b(str2.substring(length2, iIndexOf2));
                    if (z2) {
                        strB = strB.trim();
                    }
                    arrayList2.add(strB);
                    length3 = iIndexOf2 + 1;
                }
                z3 = false;
            }
            i2 = length3;
            arrayList = arrayList2;
        }
        if (arrayList == null) {
            return null;
        }
        return (String[]) arrayList.toArray(f17484c);
    }

    private static int c(String str) {
        if (str == null) {
            return 0;
        }
        if (str.equalsIgnoreCase("WEP")) {
            return 2;
        }
        if ((str.equalsIgnoreCase("WPA") | str.equalsIgnoreCase("WPA2") | str.equalsIgnoreCase("WPA/WPA2")) || str.equalsIgnoreCase("WPA2/WPA")) {
            return 1;
        }
        return str.equalsIgnoreCase("SAE") ? 3 : 0;
    }

    @Override // com.huawei.hms.scankit.p.t6
    public HmsScan b(s6 s6Var) {
        String str;
        String strA = t6.a(s6Var);
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        Matcher matcher = f17483b.matcher(strA);
        if (matcher.find() && matcher.start() == 0) {
            String strSubstring = strA.substring(5);
            if (!strSubstring.endsWith(com.alipay.sdk.m.u.i.f9802b)) {
                strSubstring = strSubstring + com.alipay.sdk.m.u.i.f9802b;
            }
            String strB = b("S:", strSubstring, ';', false);
            if (strB != null && !strB.isEmpty()) {
                String strB2 = b("P:", strSubstring, ';', false);
                String strB3 = b("T:", strSubstring, ';', false);
                StringBuilder sb = new StringBuilder();
                sb.append(strB);
                if (strB2 == null || strB2.isEmpty()) {
                    str = "";
                } else {
                    str = " " + strB2;
                }
                sb.append(str);
                return new HmsScan(s6Var.k(), t6.a(s6Var.c()), sb.toString(), HmsScan.WIFI_CONNECT_INFO_FORM, s6Var.i(), t6.a(s6Var.j()), null, new z6(new HmsScan.WiFiConnectionInfo(strB, strB2, c(strB3))));
            }
        }
        return null;
    }

    private static int a(CharSequence charSequence, int i2) {
        int i3 = 0;
        for (int i4 = i2 - 1; i4 >= 0 && charSequence.charAt(i4) == '\\'; i4--) {
            i3++;
        }
        return i3;
    }

    private String b(String str, String str2, char c2, boolean z2) {
        String str3;
        String[] strArrA = a(str, str2, c2, z2);
        return (strArrA == null || strArrA.length == 0 || (str3 = strArrA[0]) == null) ? "" : str3;
    }
}
