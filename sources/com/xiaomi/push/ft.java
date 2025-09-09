package com.xiaomi.push;

import android.util.Pair;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class ft {

    /* renamed from: a, reason: collision with root package name */
    private static Vector<Pair<String, Long>> f23767a = new Vector<>();

    /* renamed from: a, reason: collision with other field name */
    private static ConcurrentHashMap<String, Long> f436a = new ConcurrentHashMap<>();

    public static String a() {
        StringBuilder sb = new StringBuilder();
        synchronized (f23767a) {
            for (int i2 = 0; i2 < f23767a.size(); i2++) {
                try {
                    Pair<String, Long> pairElementAt = f23767a.elementAt(i2);
                    sb.append((String) pairElementAt.first);
                    sb.append(":");
                    sb.append(pairElementAt.second);
                    if (i2 < f23767a.size() - 1) {
                        sb.append(com.alipay.sdk.m.u.i.f9802b);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            f23767a.clear();
        }
        return sb.toString();
    }
}
