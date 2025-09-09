package com.huawei.hms.hatool;

import android.content.Context;
import com.heytap.mcssdk.constant.IntentConstant;

/* loaded from: classes4.dex */
public final class m {

    /* renamed from: b, reason: collision with root package name */
    private static m f16430b;

    /* renamed from: c, reason: collision with root package name */
    private static final Object f16431c = new Object();

    /* renamed from: a, reason: collision with root package name */
    private Context f16432a;

    private m() {
    }

    public static m a() {
        if (f16430b == null) {
            b();
        }
        return f16430b;
    }

    private static synchronized void b() {
        if (f16430b == null) {
            f16430b = new m();
        }
    }

    public void a(Context context) {
        synchronized (f16431c) {
            try {
                if (this.f16432a != null) {
                    v.f("hmsSdk", "DataManager already initialized.");
                    return;
                }
                this.f16432a = context;
                s.c().b().a(this.f16432a);
                s.c().b().j(context.getPackageName());
                j.a().a(context);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void a(String str) {
        v.c("hmsSdk", "HiAnalyticsDataManager.setAppid(String appid) is execute.");
        Context context = this.f16432a;
        if (context == null) {
            v.e("hmsSdk", "sdk is not init");
        } else {
            s.c().b().i(e1.a(IntentConstant.APP_ID, str, "[a-zA-Z0-9_][a-zA-Z0-9. _-]{0,255}", context.getPackageName()));
        }
    }
}
