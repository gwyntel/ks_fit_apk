package com.meizu.cloud.pushsdk.c;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.f.g.e;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.umeng.analytics.pro.f;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19171a = "b";

    /* renamed from: b, reason: collision with root package name */
    private final HashMap<String, String> f19172b;

    /* renamed from: c, reason: collision with root package name */
    private final HashMap<String, Object> f19173c;

    /* renamed from: d, reason: collision with root package name */
    private final HashMap<String, Object> f19174d;

    /* renamed from: com.meizu.cloud.pushsdk.c.b$b, reason: collision with other inner class name */
    public static class C0147b {

        /* renamed from: a, reason: collision with root package name */
        private Context f19175a = null;

        public C0147b a(Context context) {
            this.f19175a = context;
            return this;
        }

        public b a() {
            return new b(this);
        }
    }

    private b(C0147b c0147b) throws NoSuchMethodException, SecurityException {
        this.f19172b = new HashMap<>();
        this.f19173c = new HashMap<>();
        this.f19174d = new HashMap<>();
        d();
        if (c0147b.f19175a != null) {
            d(c0147b.f19175a);
            c(c0147b.f19175a);
            b(c0147b.f19175a);
            a(c0147b.f19175a);
        }
        DebugLogger.i(f19171a, "Subject created successfully.");
    }

    private void d() {
        a("br", Build.BRAND);
        a("dc", Build.MODEL);
        a(CmcdConfiguration.KEY_OBJECT_TYPE, Build.VERSION.RELEASE);
        a("ov", Build.DISPLAY);
        a("ll", MzSystemUtils.getCurrentLanguage());
    }

    public Map<String, Object> a() {
        return this.f19173c;
    }

    public Map<String, String> b() {
        return this.f19172b;
    }

    public Map<String, Object> c() {
        return this.f19174d;
    }

    private void a(Context context) {
        a("pn", (Object) context.getPackageName());
        a(f.T, (Object) MzSystemUtils.getAppVersionName(context));
        a("pvc", Integer.valueOf(MzSystemUtils.getAppVersionCode(context)));
        a("st", Integer.valueOf(!TextUtils.isEmpty(MzSystemUtils.findReceiver(context, "com.meizu.ups.push.intent.MESSAGE", context.getPackageName())) ? 1 : 0));
    }

    private void b(Context context) {
        b("nt", MzSystemUtils.getNetWorkType(context));
    }

    private void d(Context context) {
        a("op", e.c(context));
    }

    public void c(Context context) throws NoSuchMethodException, SecurityException {
        Point pointB = e.b(context);
        if (pointB == null) {
            DebugLogger.e(f19171a, "screen information not available.");
        } else {
            a("ss", pointB.x, pointB.y);
        }
    }

    private void a(String str, int i2, int i3) {
        this.f19172b.put(str, i2 + "." + i3);
    }

    private void b(String str, Object obj) {
        if (TextUtils.isEmpty(str) || obj == null) {
            return;
        }
        if ((obj instanceof String) && ((String) obj).isEmpty()) {
            return;
        }
        this.f19174d.put(str, obj);
    }

    private void a(String str, Object obj) {
        if (TextUtils.isEmpty(str) || obj == null) {
            return;
        }
        if ((obj instanceof String) && ((String) obj).isEmpty()) {
            return;
        }
        this.f19173c.put(str, obj);
    }

    private void a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        this.f19172b.put(str, str2);
    }
}
