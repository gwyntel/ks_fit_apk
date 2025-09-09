package com.alipay.sdk.m.u;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9812a = "resultStatus";

    /* renamed from: b, reason: collision with root package name */
    public static final String f9813b = "memo";

    /* renamed from: c, reason: collision with root package name */
    public static final String f9814c = "result";

    public static Map<String, String> a(com.alipay.sdk.m.s.a aVar, String str) {
        Map<String, String> mapA = a();
        try {
            return a(str);
        } catch (Throwable th) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9374q, th);
            return mapA;
        }
    }

    public static String b(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf(i.f9804d));
    }

    public static Map<String, String> a() {
        com.alipay.sdk.m.j.c cVarB = com.alipay.sdk.m.j.c.b(com.alipay.sdk.m.j.c.CANCELED.b());
        HashMap map = new HashMap();
        map.put(f9812a, Integer.toString(cVarB.b()));
        map.put(f9813b, cVarB.a());
        map.put("result", "");
        return map;
    }

    public static Map<String, String> a(String str) {
        String[] strArrSplit = str.split(i.f9802b);
        HashMap map = new HashMap();
        for (String str2 : strArrSplit) {
            String strSubstring = str2.substring(0, str2.indexOf("={"));
            map.put(strSubstring, b(str2, strSubstring));
        }
        return map;
    }

    public static String a(String str, String str2) {
        try {
            Matcher matcher = Pattern.compile("(^|;)" + str2 + "=\\{([^}]*?)\\}").matcher(str);
            if (matcher.find()) {
                return matcher.group(2);
            }
        } catch (Throwable th) {
            e.a(th);
        }
        return "?";
    }
}
