package com.ta.utdid2.device;

import android.content.Context;
import android.text.TextUtils;
import com.ta.a.b.h;
import com.ta.a.c.f;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final a f20034a = new a();

    /* renamed from: c, reason: collision with root package name */
    private static long f20035c = 3000;

    /* renamed from: d, reason: collision with root package name */
    private String f20036d = "";

    private a() {
    }

    public static a a() {
        return f20034a;
    }

    private void f() {
        f.e();
        if (TextUtils.isEmpty(this.f20036d)) {
            return;
        }
        try {
            final Context context = com.ta.a.a.a().getContext();
            boolean zM79c = c.m79c(context);
            f.m77a("", "isMainProcess", Boolean.valueOf(zM79c));
            if (zM79c) {
                new Thread(new Runnable() { // from class: com.ta.utdid2.device.a.1
                    @Override // java.lang.Runnable
                    public void run() throws InterruptedException {
                        try {
                            Thread.sleep(a.f20035c);
                        } catch (Exception unused) {
                        }
                        if (com.ta.a.b.e.m75a(context)) {
                            new h(context).run();
                        } else {
                            f.m77a("", "unable upload!");
                        }
                    }
                }).start();
            }
        } catch (Throwable th) {
            f.m77a("", th);
        }
    }

    private String l() {
        if (com.ta.a.a.a().getContext() == null) {
            return "";
        }
        String strD = com.ta.a.b.e.d();
        if (!d.m80c(strD)) {
            return null;
        }
        f.m77a("AppUtdid", "read utdid from V5AppFile");
        d.setType(7);
        return strD;
    }

    synchronized String getUtdid(Context context) {
        if (!TextUtils.isEmpty(this.f20036d)) {
            return this.f20036d;
        }
        try {
            com.ta.a.c.c.c();
            String strL = l();
            if (TextUtils.isEmpty(strL)) {
                strL = d.a(context).getValue();
            }
            if (TextUtils.isEmpty(strL)) {
                return "ffffffffffffffffffffffff";
            }
            this.f20036d = strL;
            f();
            return this.f20036d;
        } catch (Throwable th) {
            try {
                f.a("AppUtdid", th, new Object[0]);
                return "ffffffffffffffffffffffff";
            } finally {
                com.ta.a.c.c.d();
            }
        }
    }

    public synchronized String m() {
        return this.f20036d;
    }
}
