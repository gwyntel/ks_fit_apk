package com.xiaomi.push.service;

import android.os.SystemClock;
import android.text.TextUtils;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static final Map<String, Long> f24600a = new HashMap();

    public static boolean a(byte[] bArr, String str) throws NoSuchAlgorithmException {
        boolean z2 = false;
        if (bArr != null && bArr.length > 0 && !TextUtils.isEmpty(str)) {
            String strA = com.xiaomi.push.bp.a(bArr);
            if (!TextUtils.isEmpty(strA)) {
                Map<String, Long> map = f24600a;
                synchronized (map) {
                    if (map.get(strA + str) != null) {
                        z2 = true;
                    } else {
                        map.put(strA + str, Long.valueOf(SystemClock.elapsedRealtime()));
                    }
                    a();
                }
            }
        }
        return z2;
    }

    private static void a() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        Map<String, Long> map = f24600a;
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (jElapsedRealtime - entry.getValue().longValue() > 60000) {
                arrayList.add(entry.getKey());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            f24600a.remove((String) it.next());
        }
    }
}
