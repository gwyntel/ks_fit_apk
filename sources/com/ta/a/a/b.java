package com.ta.a.a;

import android.text.TextUtils;
import com.ta.a.c.e;
import com.ta.a.c.f;

/* loaded from: classes4.dex */
public class b {
    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new String(com.ta.utdid2.a.a.a.encode(e.b(str.getBytes()), 2), "UTF-8");
        } catch (Exception e2) {
            f.a("", e2, new Object[0]);
            return "";
        }
    }
}
