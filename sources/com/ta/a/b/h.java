package com.ta.a.b;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f20014a = false;
    private Context mContext;

    public h(Context context) {
        this.mContext = context;
    }

    private void a() {
        com.ta.a.c.f.e();
        if (com.ta.a.c.d.b(this.mContext) && !f20014a) {
            f20014a = true;
            try {
                b();
            } catch (Throwable unused) {
            }
            f20014a = false;
        }
    }

    private void b() {
        com.ta.a.c.f.e();
        String strE = e();
        if (TextUtils.isEmpty(strE)) {
            com.ta.a.c.f.m77a("postData is empty", new Object[0]);
        } else if (a(strE)) {
            com.ta.a.c.f.m77a("", "upload success");
        } else {
            com.ta.a.c.f.m77a("", "upload fail");
        }
    }

    private String e() {
        String strM = com.ta.utdid2.device.a.a().m();
        if (TextUtils.isEmpty(strM)) {
            return null;
        }
        String strA = com.ta.a.a.a.a(strM);
        if (com.ta.a.c.f.m78a()) {
            com.ta.a.c.f.b("", strA);
        }
        return com.ta.a.a.b.b(strA);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a();
        } catch (Throwable th) {
            com.ta.a.c.f.a("", th, new Object[0]);
        }
    }

    private boolean a(String str) {
        a aVarA = b.a("https://mpush-api.aliyun.com/v2.0/a/audid/req/", str, true);
        if (aVarA == null) {
            return false;
        }
        return com.ta.utdid2.device.e.a(aVarA);
    }
}
