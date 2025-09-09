package com.vivo.push.util;

import android.content.Context;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class y implements d {

    /* renamed from: a, reason: collision with root package name */
    private static final HashMap<String, Integer> f23267a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private static final HashMap<String, Long> f23268b = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private static final HashMap<String, String> f23269c = new HashMap<>();

    /* renamed from: d, reason: collision with root package name */
    private static y f23270d;

    /* renamed from: e, reason: collision with root package name */
    private Context f23271e;

    /* renamed from: f, reason: collision with root package name */
    private d f23272f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f23273g;

    private y(Context context) {
        this.f23273g = false;
        this.f23271e = context;
        this.f23273g = a(context);
        p.d("SystemCache", "init status is " + this.f23273g + ";  curCache is " + this.f23272f);
    }

    public static synchronized y b(Context context) {
        try {
            if (f23270d == null) {
                f23270d = new y(context.getApplicationContext());
            }
        } catch (Throwable th) {
            throw th;
        }
        return f23270d;
    }

    public final void a() {
        x xVar = new x();
        if (xVar.a(this.f23271e)) {
            xVar.a();
            p.d("SystemCache", "sp cache is cleared");
        }
    }

    @Override // com.vivo.push.util.d
    public final void b(String str, String str2) {
        d dVar;
        f23269c.put(str, str2);
        if (!this.f23273g || (dVar = this.f23272f) == null) {
            return;
        }
        dVar.b(str, str2);
    }

    @Override // com.vivo.push.util.d
    public final boolean a(Context context) {
        v vVar = new v();
        this.f23272f = vVar;
        boolean zA = vVar.a(context);
        if (!zA) {
            x xVar = new x();
            this.f23272f = xVar;
            zA = xVar.a(context);
        }
        if (!zA) {
            this.f23272f = null;
        }
        return zA;
    }

    @Override // com.vivo.push.util.d
    public final String a(String str, String str2) {
        d dVar;
        String str3 = f23269c.get(str);
        return (str3 != null || (dVar = this.f23272f) == null) ? str3 : dVar.a(str, str2);
    }
}
