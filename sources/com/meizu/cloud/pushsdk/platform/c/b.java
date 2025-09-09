package com.meizu.cloud.pushsdk.platform.c;

import android.content.Context;
import com.meizu.cloud.pushsdk.e.b.c;
import com.meizu.cloud.pushsdk.platform.d.d;
import com.meizu.cloud.pushsdk.platform.d.e;
import com.meizu.cloud.pushsdk.platform.d.f;
import com.meizu.cloud.pushsdk.platform.d.g;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static b f19793a;

    /* renamed from: b, reason: collision with root package name */
    private ScheduledExecutorService f19794b;

    /* renamed from: c, reason: collision with root package name */
    private final Context f19795c;

    /* renamed from: d, reason: collision with root package name */
    private final a f19796d;

    /* renamed from: e, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.platform.d.b f19797e;

    /* renamed from: f, reason: collision with root package name */
    private final g f19798f;

    /* renamed from: g, reason: collision with root package name */
    private final f f19799g;

    /* renamed from: h, reason: collision with root package name */
    private final e f19800h;

    /* renamed from: i, reason: collision with root package name */
    private final d f19801i;

    /* renamed from: j, reason: collision with root package name */
    private final boolean f19802j;

    public b(Context context, boolean z2) {
        this(context, z2, true);
    }

    public c<String> a(String str, String str2, String str3, String str4, File file) {
        return this.f19796d.a(str, str2, str3, str4, file);
    }

    public boolean b(String str, String str2, String str3) {
        this.f19798f.a(str);
        this.f19798f.b(str2);
        this.f19798f.c(str3);
        return this.f19798f.f();
    }

    public boolean c(String str, String str2, String str3, String str4) {
        this.f19800h.a(str);
        this.f19800h.b(str2);
        this.f19800h.c(str3);
        this.f19800h.d(str4);
        this.f19800h.b(3);
        return this.f19800h.f();
    }

    public boolean d(String str, String str2, String str3, String str4) {
        this.f19800h.a(str);
        this.f19800h.b(str2);
        this.f19800h.c(str3);
        this.f19800h.d(str4);
        this.f19800h.b(2);
        return this.f19800h.f();
    }

    public b(Context context, boolean z2, boolean z3) {
        Context applicationContext = context.getApplicationContext();
        this.f19795c = applicationContext;
        a aVar = new a(applicationContext);
        this.f19796d = aVar;
        if (z2) {
            this.f19794b = (ScheduledExecutorService) com.meizu.cloud.pushsdk.f.c.h.b.a();
        }
        this.f19802j = z3;
        this.f19797e = new com.meizu.cloud.pushsdk.platform.d.b(applicationContext, aVar, this.f19794b, z3);
        this.f19798f = new g(applicationContext, aVar, this.f19794b, z3);
        this.f19799g = new f(applicationContext, aVar, this.f19794b, z3);
        this.f19800h = new e(applicationContext, aVar, this.f19794b, z3);
        this.f19801i = new d(applicationContext, aVar, this.f19794b, z3);
    }

    public static b a(Context context) {
        if (f19793a == null) {
            synchronized (b.class) {
                try {
                    if (f19793a == null) {
                        f19793a = new b(context, true);
                    }
                } finally {
                }
            }
        }
        return f19793a;
    }

    public boolean b(String str, String str2, String str3, String str4) {
        this.f19801i.a(str);
        this.f19801i.b(str2);
        this.f19801i.c(str3);
        this.f19801i.e(str4);
        this.f19801i.b(2);
        return this.f19801i.f();
    }

    public boolean c(String str, String str2, String str3, String str4, String str5) {
        this.f19801i.a(str);
        this.f19801i.b(str2);
        this.f19801i.c(str3);
        this.f19801i.e(str4);
        this.f19801i.b(1);
        this.f19801i.d(str5);
        return this.f19801i.f();
    }

    public boolean d(String str, String str2, String str3, String str4, String str5) {
        this.f19800h.a(str);
        this.f19800h.b(str2);
        this.f19800h.c(str3);
        this.f19800h.d(str4);
        this.f19800h.b(1);
        this.f19800h.e(str5);
        return this.f19800h.f();
    }

    public void a(boolean z2) {
        this.f19797e.a(z2);
        this.f19798f.a(z2);
        this.f19799g.a(z2);
        this.f19801i.a(z2);
        this.f19800h.a(z2);
    }

    public boolean b(String str, String str2, String str3, String str4, String str5) {
        this.f19800h.a(str);
        this.f19800h.b(str2);
        this.f19800h.c(str3);
        this.f19800h.d(str4);
        this.f19800h.b(0);
        this.f19800h.e(str5);
        return this.f19800h.f();
    }

    public boolean a(String str) {
        com.meizu.cloud.pushsdk.platform.d.a aVar = new com.meizu.cloud.pushsdk.platform.d.a(this.f19795c, this.f19794b, this.f19802j);
        aVar.b(0);
        aVar.c(str);
        return aVar.f();
    }

    public boolean a(String str, String str2) {
        com.meizu.cloud.pushsdk.platform.d.a aVar = new com.meizu.cloud.pushsdk.platform.d.a(this.f19795c, this.f19794b, this.f19802j);
        aVar.b(2);
        aVar.d(str2);
        aVar.c(str);
        return aVar.f();
    }

    public boolean a(String str, String str2, String str3) {
        this.f19797e.a(str);
        this.f19797e.b(str2);
        this.f19797e.c(str3);
        return this.f19797e.f();
    }

    public boolean a(String str, String str2, String str3, String str4) {
        this.f19799g.a(str);
        this.f19799g.b(str2);
        this.f19799g.c(str3);
        this.f19799g.d(str4);
        this.f19799g.b(2);
        return this.f19799g.f();
    }

    public boolean a(String str, String str2, String str3, String str4, int i2, boolean z2) {
        this.f19799g.a(str);
        this.f19799g.b(str2);
        this.f19799g.c(str3);
        this.f19799g.d(str4);
        this.f19799g.b(i2);
        this.f19799g.c(z2);
        return this.f19799g.f();
    }

    public boolean a(String str, String str2, String str3, String str4, String str5) {
        this.f19801i.a(str);
        this.f19801i.b(str2);
        this.f19801i.c(str3);
        this.f19801i.e(str4);
        this.f19801i.b(0);
        this.f19801i.d(str5);
        return this.f19801i.f();
    }

    public boolean a(String str, String str2, String str3, String str4, boolean z2) {
        this.f19799g.a(str);
        this.f19799g.b(str2);
        this.f19799g.c(str3);
        this.f19799g.d(str4);
        this.f19799g.b(3);
        this.f19799g.c(z2);
        return this.f19799g.f();
    }

    public boolean a(String str, int... iArr) {
        com.meizu.cloud.pushsdk.platform.d.a aVar = new com.meizu.cloud.pushsdk.platform.d.a(this.f19795c, this.f19794b, this.f19802j);
        aVar.a(iArr);
        aVar.c(str);
        aVar.b(1);
        return aVar.f();
    }
}
