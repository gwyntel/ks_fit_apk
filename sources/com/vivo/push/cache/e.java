package com.vivo.push.cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.vivo.push.util.f;
import com.vivo.push.util.p;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class e extends c<com.vivo.push.model.a> {
    public e(Context context) {
        super(context);
    }

    @Override // com.vivo.push.cache.c
    public final List<com.vivo.push.model.a> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.trim().split("@#")) {
            String strTrim = str2.trim();
            String[] strArrSplit = strTrim.trim().split(",");
            if (strArrSplit.length >= 2) {
                try {
                    String str3 = strArrSplit[0];
                    arrayList.add(new com.vivo.push.model.a(str3, strTrim.substring(str3.length() + 1)));
                } catch (Exception e2) {
                    p.d("PushConfigSettings", "str2Clients E: ".concat(String.valueOf(e2)));
                }
            }
        }
        return arrayList;
    }

    @Override // com.vivo.push.cache.c
    final String b(String str) throws Exception {
        return new String(f.a(f.a(e()), f.a(f()), Base64.decode(str, 2)), "utf-8");
    }

    public final String c(String str) {
        synchronized (c.f23089a) {
            try {
                for (T t2 : this.f23090b) {
                    if (!TextUtils.isEmpty(t2.a()) && t2.a().equals(str)) {
                        return t2.b();
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.vivo.push.cache.c
    protected final String a() {
        return "com.vivo.pushservice.other";
    }
}
