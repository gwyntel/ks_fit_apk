package com.meizu.cloud.pushsdk.f.f;

import android.content.Context;
import android.os.Build;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import com.meizu.cloud.pushsdk.f.g.e;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19660a = "c";

    /* renamed from: b, reason: collision with root package name */
    private final HashMap<String, String> f19661b;

    /* renamed from: c, reason: collision with root package name */
    private final HashMap<String, Object> f19662c;

    /* renamed from: d, reason: collision with root package name */
    private final HashMap<String, String> f19663d;

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        private Context f19664a = null;

        public b a(Context context) {
            this.f19664a = context;
            return this;
        }

        public c a() {
            return new c(this);
        }
    }

    private c(b bVar) {
        this.f19661b = new HashMap<>();
        this.f19662c = new HashMap<>();
        this.f19663d = new HashMap<>();
        f();
        g();
        d();
        e();
        if (bVar.f19664a != null) {
            b(bVar.f19664a);
        }
        com.meizu.cloud.pushsdk.f.g.c.c(f19660a, "Subject created successfully.", new Object[0]);
    }

    private void d() {
        a("dm", Build.MODEL);
    }

    private void e() {
        a("df", Build.MANUFACTURER);
    }

    private void f() {
        a(CmcdConfiguration.KEY_OBJECT_TYPE, "android-" + Build.VERSION.RELEASE);
    }

    private void g() {
        a("ov", Build.DISPLAY);
    }

    public Map<String, String> a() {
        return this.f19661b;
    }

    public Map<String, Object> b() {
        return this.f19662c;
    }

    public Map<String, String> c() {
        return this.f19663d;
    }

    public void a(Context context) {
        String strA = e.a(context);
        if (strA != null) {
            a("ca", strA);
        }
    }

    public void b(Context context) {
        a(context);
    }

    private void a(String str, String str2) {
        if (str == null || str2 == null || str.isEmpty() || str2.isEmpty()) {
            return;
        }
        this.f19663d.put(str, str2);
    }
}
