package com.meizu.cloud.pushsdk.c;

import android.content.Context;
import com.meizu.cloud.pushsdk.c.b;
import com.meizu.cloud.pushsdk.f.b.c;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import java.util.Map;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: com.meizu.cloud.pushsdk.c.a$a, reason: collision with other inner class name */
    class RunnableC0146a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f19170a;

        RunnableC0146a(Context context) {
            this.f19170a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.meizu.cloud.pushsdk.c.c.b.a(this.f19170a).b("POST", null, a.a(this.f19170a).toString());
        }
    }

    public static c a(Context context) {
        c cVar = new c();
        b bVarB = b(context);
        Map<String, String> mapB = bVarB.b();
        Map<String, Object> mapA = bVarB.a();
        Map<String, Object> mapC = bVarB.c();
        if (mapB.size() > 0) {
            cVar.a(AppIconSetting.DEFAULT_LARGE_ICON, mapB);
        }
        if (mapA.size() > 0) {
            cVar.a("ai", mapA);
        }
        if (mapC.size() > 0) {
            cVar.a(AppIconSetting.LARGE_ICON_URL, mapC);
        }
        return cVar;
    }

    private static b b(Context context) {
        return new b.C0147b().a(context).a();
    }

    public static void c(Context context) {
        com.meizu.cloud.pushsdk.d.m.a.a().execute(new RunnableC0146a(context));
    }
}
