package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.taobao.accs.common.Constants;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.bt;
import com.umeng.analytics.pro.bz;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public static final long f22388a = 86400000;

    /* renamed from: b, reason: collision with root package name */
    public static e f22389b;

    /* renamed from: c, reason: collision with root package name */
    private static final String f22390c = ay.b().b("id");

    /* renamed from: j, reason: collision with root package name */
    private static Object f22391j = new Object();

    /* renamed from: d, reason: collision with root package name */
    private File f22392d;

    /* renamed from: f, reason: collision with root package name */
    private long f22394f;

    /* renamed from: i, reason: collision with root package name */
    private a f22397i;

    /* renamed from: e, reason: collision with root package name */
    private com.umeng.commonsdk.statistics.proto.c f22393e = null;

    /* renamed from: h, reason: collision with root package name */
    private Set<com.umeng.commonsdk.statistics.idtracking.a> f22396h = new HashSet();

    /* renamed from: g, reason: collision with root package name */
    private long f22395g = 86400000;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private Context f22398a;

        /* renamed from: b, reason: collision with root package name */
        private Set<String> f22399b = new HashSet();

        public a(Context context) {
            this.f22398a = context;
        }

        public synchronized boolean a(String str) {
            return !this.f22399b.contains(str);
        }

        public synchronized void b(String str) {
            this.f22399b.add(str);
        }

        public void c(String str) {
            this.f22399b.remove(str);
        }

        public synchronized void a() {
            try {
                if (!this.f22399b.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    Iterator<String> it = this.f22399b.iterator();
                    while (it.hasNext()) {
                        sb.append(it.next());
                        sb.append(',');
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    PreferenceWrapper.getDefault(this.f22398a).edit().putString("invld_id", sb.toString()).commit();
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public synchronized void b() {
            String[] strArrSplit;
            String string = PreferenceWrapper.getDefault(this.f22398a).getString("invld_id", null);
            if (!TextUtils.isEmpty(string) && (strArrSplit = string.split(",")) != null) {
                for (String str : strArrSplit) {
                    if (!TextUtils.isEmpty(str)) {
                        this.f22399b.add(str);
                    }
                }
            }
        }
    }

    e(Context context) {
        this.f22397i = null;
        this.f22392d = new File(context.getFilesDir(), f22390c);
        a aVar = new a(context);
        this.f22397i = aVar;
        aVar.b();
    }

    public static synchronized void a() {
        e eVar = f22389b;
        if (eVar != null) {
            eVar.e();
            f22389b = null;
        }
    }

    private synchronized void h() {
        try {
            com.umeng.commonsdk.statistics.proto.c cVar = new com.umeng.commonsdk.statistics.proto.c();
            HashMap map = new HashMap();
            ArrayList arrayList = new ArrayList();
            for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f22396h) {
                if (aVar.c()) {
                    if (aVar.d() != null) {
                        map.put(aVar.b(), aVar.d());
                    }
                    if (aVar.e() != null && !aVar.e().isEmpty()) {
                        arrayList.addAll(aVar.e());
                    }
                }
            }
            cVar.a(arrayList);
            cVar.a(map);
            synchronized (this) {
                this.f22393e = cVar;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private com.umeng.commonsdk.statistics.proto.c i() {
        Throwable th;
        FileInputStream fileInputStream;
        synchronized (f22391j) {
            if (!this.f22392d.exists()) {
                return null;
            }
            try {
                fileInputStream = new FileInputStream(this.f22392d);
            } catch (Exception e2) {
                e = e2;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = null;
                HelperUtils.safeClose(fileInputStream);
                throw th;
            }
            try {
                try {
                    byte[] streamToByteArray = HelperUtils.readStreamToByteArray(fileInputStream);
                    com.umeng.commonsdk.statistics.proto.c cVar = new com.umeng.commonsdk.statistics.proto.c();
                    new bt().a(cVar, streamToByteArray);
                    HelperUtils.safeClose(fileInputStream);
                    return cVar;
                } catch (Throwable th3) {
                    th = th3;
                    HelperUtils.safeClose(fileInputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                HelperUtils.safeClose(fileInputStream);
                return null;
            }
        }
    }

    public synchronized void b() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.f22394f >= this.f22395g) {
                boolean z2 = false;
                for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f22396h) {
                    if (aVar.c() && aVar.a()) {
                        if (!aVar.c()) {
                            this.f22397i.b(aVar.b());
                        }
                        z2 = true;
                    }
                }
                if (z2) {
                    h();
                    this.f22397i.a();
                    g();
                }
                this.f22394f = jCurrentTimeMillis;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized com.umeng.commonsdk.statistics.proto.c c() {
        return this.f22393e;
    }

    public String d() {
        return null;
    }

    public synchronized void e() {
        try {
            if (f22389b == null) {
                return;
            }
            boolean z2 = false;
            for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f22396h) {
                if (aVar.c() && aVar.e() != null && !aVar.e().isEmpty()) {
                    aVar.a((List<com.umeng.commonsdk.statistics.proto.a>) null);
                    z2 = true;
                }
            }
            if (z2) {
                this.f22393e.b(false);
                g();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void f() {
        com.umeng.commonsdk.statistics.proto.c cVarI = i();
        if (cVarI == null) {
            return;
        }
        a(cVarI);
        ArrayList arrayList = new ArrayList(this.f22396h.size());
        synchronized (this) {
            try {
                this.f22393e = cVarI;
                for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f22396h) {
                    aVar.a(this.f22393e);
                    if (!aVar.c()) {
                        arrayList.add(aVar);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    this.f22396h.remove((com.umeng.commonsdk.statistics.idtracking.a) it.next());
                }
                h();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized void g() {
        com.umeng.commonsdk.statistics.proto.c cVar = this.f22393e;
        if (cVar != null) {
            b(cVar);
        }
    }

    public static synchronized e a(Context context) {
        try {
            if (f22389b == null) {
                e eVar = new e(context);
                f22389b = eVar;
                eVar.a(new f(context));
                f22389b.a(new b(context));
                f22389b.a(new j(context));
                f22389b.a(new d(context));
                f22389b.a(new g(context));
                f22389b.a(new i());
                if (FieldManager.allow(com.umeng.commonsdk.utils.d.G)) {
                    f22389b.a(new h(context));
                    if (DeviceConfig.isHonorDevice()) {
                        f22389b.a(new c(context));
                    }
                }
                f22389b.f();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f22389b;
    }

    private void b(com.umeng.commonsdk.statistics.proto.c cVar) {
        byte[] bArrA;
        synchronized (f22391j) {
            if (cVar != null) {
                try {
                    synchronized (this) {
                        a(cVar);
                        bArrA = new bz().a(cVar);
                    }
                    if (bArrA != null) {
                        HelperUtils.writeFile(this.f22392d, bArrA);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean a(com.umeng.commonsdk.statistics.idtracking.a aVar) {
        if (this.f22397i.a(aVar.b())) {
            return this.f22396h.add(aVar);
        }
        if (!AnalyticsConstants.UM_DEBUG) {
            return false;
        }
        MLog.w("invalid domain: " + aVar.b());
        return false;
    }

    public void a(long j2) {
        this.f22395g = j2;
    }

    private void a(com.umeng.commonsdk.statistics.proto.c cVar) {
        Map<String, com.umeng.commonsdk.statistics.proto.b> map;
        if (cVar == null || (map = cVar.f22484a) == null) {
            return;
        }
        if (map.containsKey(AlinkConstants.KEY_MAC) && !FieldManager.allow(com.umeng.commonsdk.utils.d.f22564h)) {
            cVar.f22484a.remove(AlinkConstants.KEY_MAC);
        }
        if (cVar.f22484a.containsKey(Constants.KEY_IMEI) && !FieldManager.allow(com.umeng.commonsdk.utils.d.f22563g)) {
            cVar.f22484a.remove(Constants.KEY_IMEI);
        }
        if (cVar.f22484a.containsKey("android_id") && !FieldManager.allow(com.umeng.commonsdk.utils.d.f22565i)) {
            cVar.f22484a.remove("android_id");
        }
        if (cVar.f22484a.containsKey("serial") && !FieldManager.allow(com.umeng.commonsdk.utils.d.f22566j)) {
            cVar.f22484a.remove("serial");
        }
        if (cVar.f22484a.containsKey("idfa") && !FieldManager.allow(com.umeng.commonsdk.utils.d.f22579w)) {
            cVar.f22484a.remove("idfa");
        }
        if (!cVar.f22484a.containsKey("oaid") || FieldManager.allow(com.umeng.commonsdk.utils.d.G)) {
            return;
        }
        cVar.f22484a.remove("oaid");
    }
}
