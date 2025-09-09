package com.meizu.cloud.pushsdk.platform;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, String> f19776a = a();

    /* renamed from: b, reason: collision with root package name */
    private static final List<String> f19777b = new ArrayList(f19776a.keySet());

    public static String a(String str) {
        String strSubstring;
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            if (str.length() <= 3) {
                return str;
            }
            String strSubstring2 = str.substring(0, 3);
            if (!f19776a.containsKey(strSubstring2)) {
                return str;
            }
            String str2 = f19776a.get(strSubstring2);
            strSubstring = str.substring(3);
            try {
                char[] cArr = new char[strSubstring.length() / 2];
                int i2 = 0;
                int i3 = 0;
                while (i2 < strSubstring.length() / 2) {
                    if (i3 == str2.length()) {
                        i3 = 0;
                    }
                    int i4 = i2 * 2;
                    cArr[i2] = (char) (((char) Integer.valueOf(strSubstring.substring(i4, i4 + 2), 16).intValue()) ^ str2.charAt(i3));
                    i2++;
                    i3++;
                }
                return new String(String.valueOf(cArr).getBytes("iso-8859-1"), "UTF-8");
            } catch (Exception unused) {
                DebugLogger.e("PushIdEncryptUtils", "invalid pushId encryption " + strSubstring);
                return str;
            }
        } catch (Exception unused2) {
            strSubstring = str;
        }
    }

    private static Map<String, String> a() {
        if (a(f19776a)) {
            synchronized (a.class) {
                try {
                    if (a(f19776a)) {
                        TreeMap treeMap = new TreeMap();
                        f19776a = treeMap;
                        treeMap.put("UCI", "v9tC0Myz1MGwXRFy");
                        f19776a.put("G3G", "XAsFqhhaf4gKpmAi");
                        f19776a.put("V5R", "cOqH18NXwBtZVkvz");
                        f19776a.put("0XC", "IgSEKZ3Ea6Pm4woS");
                        f19776a.put("Z9K", "pH6J9DMPNgqQp8m8");
                        f19776a.put("EIM", "K11Rs9HAKRXeNwq8");
                        f19776a.put("SO7", "T8LquL1DvwVcogiU");
                        f19776a.put("DDI", "d02F6ttOtV05MYCQ");
                        f19776a.put("ULY", "ToZZIhAywnUfHShN");
                        f19776a.put("0EV", "r5D5RRwQhfV0AYLb");
                        f19776a.put("N6A", "QAtSBFcXnQoUgHO2");
                        f19776a.put("S5Q", "sDWLrZINnum227am");
                        f19776a.put("RA5", "4Uq3Ruxo1FTBdHQE");
                        f19776a.put("J04", "N5hViUTdLCpN59H0");
                        f19776a.put("B68", "EY3sH1KKtalg5ZaT");
                        f19776a.put("9IW", "q1u0MiuFyim4pCYY");
                        f19776a.put("UU3", "syLnkkd8AqNykVV7");
                        f19776a.put("Z49", "V00FiWu124yE91sH");
                        f19776a.put("BNA", "rPP7AK1VWpKEry3p");
                        f19776a.put("WXG", "om8w5ahkJJgpAH9v");
                    }
                } finally {
                }
            }
        }
        return f19776a;
    }

    public static <K, V> boolean a(Map<K, V> map) {
        return map == null || map.isEmpty();
    }
}
