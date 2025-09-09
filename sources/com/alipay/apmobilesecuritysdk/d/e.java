package com.alipay.apmobilesecuritysdk.d;

import android.content.Context;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    public static Map<String, String> f9060a;

    /* renamed from: b, reason: collision with root package name */
    public static final String[] f9061b = {"AD1", "AD2", "AD3", "AD8", "AD9", "AD10", "AD11", "AD12", "AD14", "AD15", "AD16", "AD18", "AD20", "AD21", "AD23", "AD24", "AD26", "AD27", "AD28", "AD29", "AD30", "AD31", "AD34", "AA1", "AA2", "AA3", "AA4", "AC4", "AC10", "AE1", "AE2", "AE3", "AE4", "AE5", "AE6", "AE7", "AE8", "AE9", "AE10", "AE11", "AE12", "AE13", "AE14", "AE15"};

    public static String a(Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList(map.keySet());
        Collections.sort(arrayList);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            String str = (String) arrayList.get(i2);
            String str2 = map.get(str);
            String str3 = "";
            if (str2 == null) {
                str2 = "";
            }
            StringBuilder sb = new StringBuilder();
            if (i2 != 0) {
                str3 = "&";
            }
            sb.append(str3);
            sb.append(str);
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(str2);
            stringBuffer.append(sb.toString());
        }
        return stringBuffer.toString();
    }

    public static synchronized String b(Context context, Map<String, String> map) {
        TreeMap treeMap;
        try {
            a(context, map);
            treeMap = new TreeMap();
            for (String str : f9061b) {
                if (f9060a.containsKey(str)) {
                    treeMap.put(str, f9060a.get(str));
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return com.alipay.sdk.m.y.b.a(a(treeMap));
    }

    public static synchronized void c(Context context, Map<String, String> map) {
        TreeMap treeMap = new TreeMap();
        f9060a = treeMap;
        treeMap.putAll(b.a(context, map));
        f9060a.putAll(d.a(context));
        f9060a.putAll(c.a(context));
        f9060a.putAll(a.a(context, map));
    }

    public static synchronized Map<String, String> a(Context context, Map<String, String> map) {
        try {
            if (f9060a == null) {
                c(context, map);
            }
            f9060a.putAll(d.a());
        } catch (Throwable th) {
            throw th;
        }
        return f9060a;
    }

    public static synchronized void a() {
        f9060a = null;
    }
}
