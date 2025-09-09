package com.huawei.hms.framework.network.grs.e;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.g.d;
import com.huawei.hms.framework.network.grs.g.g;
import com.huawei.hms.framework.network.grs.h.e;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: e, reason: collision with root package name */
    private static final String f16208e = "a";

    /* renamed from: f, reason: collision with root package name */
    private static final Map<String, Map<String, Map<String, String>>> f16209f = new ConcurrentHashMap(16);

    /* renamed from: a, reason: collision with root package name */
    private final Map<String, Long> f16210a = new ConcurrentHashMap(16);

    /* renamed from: b, reason: collision with root package name */
    private final c f16211b;

    /* renamed from: c, reason: collision with root package name */
    private final c f16212c;

    /* renamed from: d, reason: collision with root package name */
    private final g f16213d;

    public a(c cVar, c cVar2, g gVar) {
        this.f16212c = cVar2;
        this.f16211b = cVar;
        this.f16213d = gVar;
        gVar.a(this);
    }

    public c a() {
        return this.f16211b;
    }

    public g b() {
        return this.f16213d;
    }

    public c c() {
        return this.f16212c;
    }

    public Map<String, String> a(GrsBaseInfo grsBaseInfo, String str, b bVar, Context context) {
        String grsParasKey = grsBaseInfo.getGrsParasKey(true, true, context);
        Map<String, Map<String, Map<String, String>>> map = f16209f;
        Map<String, Map<String, String>> map2 = map.get(grsParasKey);
        if (map2 != null && !map2.isEmpty()) {
            a(grsBaseInfo, bVar, context, str);
            return map2.get(str);
        }
        Logger.d(f16208e, "Cache size is: " + map.size());
        return new HashMap();
    }

    public void b(GrsBaseInfo grsBaseInfo, Context context) {
        String grsParasKey = grsBaseInfo.getGrsParasKey(true, true, context);
        String strA = this.f16211b.a(grsParasKey, "");
        String strA2 = this.f16211b.a(grsParasKey + "time", "0");
        long j2 = 0;
        if (!TextUtils.isEmpty(strA2) && strA2.matches("\\d+")) {
            try {
                j2 = Long.parseLong(strA2);
            } catch (NumberFormatException e2) {
                Logger.w(f16208e, "convert urlParamKey from String to Long catch NumberFormatException.", e2);
            }
        }
        Map<String, Map<String, Map<String, String>>> map = f16209f;
        map.put(grsParasKey, com.huawei.hms.framework.network.grs.a.a(strA));
        Logger.d(f16208e, "Cache size is: " + map.size());
        this.f16210a.put(grsParasKey, Long.valueOf(j2));
        a(grsBaseInfo, grsParasKey, context);
    }

    public void a(GrsBaseInfo grsBaseInfo, Context context) {
        String grsParasKey = grsBaseInfo.getGrsParasKey(true, true, context);
        this.f16211b.b(grsParasKey + "time", "0");
        this.f16210a.remove(grsParasKey + "time");
        Map<String, Map<String, Map<String, String>>> map = f16209f;
        map.remove(grsParasKey);
        Logger.d(f16208e, "Cache size is: " + map.size());
        this.f16213d.a(grsParasKey);
    }

    private void a(GrsBaseInfo grsBaseInfo, b bVar, Context context, String str) {
        Long l2 = this.f16210a.get(grsBaseInfo.getGrsParasKey(true, true, context));
        if (e.a(l2)) {
            bVar.a(2);
            return;
        }
        if (e.a(l2, 300000L)) {
            this.f16213d.a(new com.huawei.hms.framework.network.grs.g.j.c(grsBaseInfo, context), null, str, this.f16212c, -1);
        }
        bVar.a(1);
    }

    public void a(GrsBaseInfo grsBaseInfo, d dVar, Context context, com.huawei.hms.framework.network.grs.g.j.c cVar) {
        if (dVar.f() == 2) {
            Logger.w(f16208e, "update cache from server failed");
            return;
        }
        if (cVar.d().size() == 0) {
            String grsParasKey = grsBaseInfo.getGrsParasKey(true, true, context);
            if (dVar.m()) {
                f16209f.put(grsParasKey, com.huawei.hms.framework.network.grs.a.a(this.f16211b.a(grsParasKey, "")));
            } else {
                this.f16211b.b(grsParasKey, dVar.j());
                f16209f.put(grsParasKey, com.huawei.hms.framework.network.grs.a.a(dVar.j()));
            }
            if (!TextUtils.isEmpty(dVar.e())) {
                this.f16211b.b(grsParasKey + "ETag", dVar.e());
            }
            this.f16211b.b(grsParasKey + "time", dVar.a());
            this.f16210a.put(grsParasKey, Long.valueOf(Long.parseLong(dVar.a())));
        } else {
            this.f16211b.b("geoipCountryCode", dVar.j());
            this.f16211b.b("geoipCountryCodetime", dVar.a());
        }
        Logger.d(f16208e, "Cache size is: " + f16209f.size());
    }

    private void a(GrsBaseInfo grsBaseInfo, String str, Context context) {
        if (e.a(this.f16210a.get(str), 300000L)) {
            this.f16213d.a(new com.huawei.hms.framework.network.grs.g.j.c(grsBaseInfo, context), null, null, this.f16212c, -1);
        }
    }
}
