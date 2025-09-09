package com.meizu.cloud.pushsdk.d;

import android.content.Context;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

/* loaded from: classes4.dex */
public class g extends h<f> implements f {

    /* renamed from: c, reason: collision with root package name */
    private static g f19222c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f19223d;

    private g(f fVar) {
        super(fVar);
        this.f19223d = false;
    }

    public static g c() {
        if (f19222c == null) {
            synchronized (g.class) {
                try {
                    if (f19222c == null) {
                        f19222c = new g(new b());
                    }
                } finally {
                }
            }
        }
        return f19222c;
    }

    public void a(Context context) {
        a(context, (String) null);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void b(String str, String str2) {
        b().b(str, str2);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void d(String str, String str2) {
        b().d(str, str2);
    }

    public void a(Context context, String str) {
        if (this.f19223d) {
            return;
        }
        this.f19223d = true;
        a((context.getApplicationInfo().flags & 2) != 0);
        if (str == null) {
            str = MzSystemUtils.getDocumentsPath(context) + "/pushSdk/" + context.getPackageName();
        }
        a(str);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void b(boolean z2) {
        b().b(z2);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void c(String str, String str2) {
        b().c(str, str2);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(String str) {
        b().a(str);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(String str, String str2) {
        b().a(str, str2);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(String str, String str2, Throwable th) {
        b().a(str, str2, th);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public void a(boolean z2) {
        b().a(z2);
    }

    @Override // com.meizu.cloud.pushsdk.d.f
    public boolean a() {
        return b().a();
    }
}
