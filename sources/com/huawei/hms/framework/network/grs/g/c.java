package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.h.d;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: l, reason: collision with root package name */
    private static final String f16239l = "c";

    /* renamed from: a, reason: collision with root package name */
    private final GrsBaseInfo f16240a;

    /* renamed from: b, reason: collision with root package name */
    private final Context f16241b;

    /* renamed from: c, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.e.a f16242c;

    /* renamed from: d, reason: collision with root package name */
    private d f16243d;

    /* renamed from: i, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.g.j.c f16248i;

    /* renamed from: e, reason: collision with root package name */
    private final Map<String, Future<d>> f16244e = new ConcurrentHashMap(16);

    /* renamed from: f, reason: collision with root package name */
    private final List<d> f16245f = new CopyOnWriteArrayList();

    /* renamed from: g, reason: collision with root package name */
    private final JSONArray f16246g = new JSONArray();

    /* renamed from: h, reason: collision with root package name */
    private final List<String> f16247h = new CopyOnWriteArrayList();

    /* renamed from: j, reason: collision with root package name */
    private String f16249j = "";

    /* renamed from: k, reason: collision with root package name */
    private long f16250k = 1;

    public c(com.huawei.hms.framework.network.grs.g.j.c cVar, com.huawei.hms.framework.network.grs.e.a aVar) {
        this.f16248i = cVar;
        this.f16240a = cVar.b();
        this.f16241b = cVar.a();
        this.f16242c = aVar;
        b();
        c();
    }

    private d b(d dVar) throws ExecutionException, InterruptedException, TimeoutException {
        String str;
        String str2;
        for (Map.Entry<String, Future<d>> entry : this.f16244e.entrySet()) {
            if (dVar != null && (dVar.o() || dVar.m())) {
                break;
            }
            try {
                dVar = entry.getValue().get(40000L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e = e2;
                str = f16239l;
                str2 = "{checkResponse} when check result, find InterruptedException, check others";
                Logger.w(str, str2, e);
            } catch (CancellationException unused) {
                Logger.i(f16239l, "{checkResponse} when check result, find CancellationException, check others");
            } catch (ExecutionException e3) {
                e = e3;
                str = f16239l;
                str2 = "{checkResponse} when check result, find ExecutionException, check others";
                Logger.w(str, str2, e);
            } catch (TimeoutException unused2) {
                Logger.w(f16239l, "{checkResponse} when check result, find TimeoutException, cancel current request task");
                if (!entry.getValue().isCancelled()) {
                    entry.getValue().cancel(true);
                }
            }
        }
        return dVar;
    }

    private void c() {
        String grsParasKey = this.f16240a.getGrsParasKey(true, true, this.f16241b);
        this.f16249j = this.f16242c.a().a(grsParasKey + "ETag", "");
    }

    private String d() {
        com.huawei.hms.framework.network.grs.f.b bVarA = com.huawei.hms.framework.network.grs.f.b.a(this.f16241b.getPackageName());
        com.huawei.hms.framework.network.grs.local.model.a aVarA = bVarA != null ? bVarA.a() : null;
        if (aVarA == null) {
            return "";
        }
        String strA = aVarA.a();
        Logger.v(f16239l, "get appName from local assets is{%s}", strA);
        return strA;
    }

    public d a(ExecutorService executorService, String str, com.huawei.hms.framework.network.grs.e.c cVar) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.f16247h.isEmpty()) {
            return null;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        d dVarA = a(executorService, this.f16247h, str, cVar);
        Logger.i(f16239l, "use 2.0 interface return http's code is：{%d}", Integer.valueOf(dVarA == null ? 0 : dVarA.b()));
        e.a(new ArrayList(this.f16245f), SystemClock.elapsedRealtime() - jElapsedRealtime, this.f16246g, this.f16241b);
        this.f16245f.clear();
        return dVarA;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x009b A[LOOP:0: B:3:0x0005->B:36:0x009b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0093 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.huawei.hms.framework.network.grs.g.d a(java.util.concurrent.ExecutorService r16, java.util.List<java.lang.String> r17, java.lang.String r18, com.huawei.hms.framework.network.grs.e.c r19) throws java.util.concurrent.ExecutionException, java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        /*
            r15 = this;
            r9 = r15
            r0 = 0
            r10 = 0
            r11 = r0
            r12 = r10
        L5:
            int r0 = r17.size()
            if (r12 >= r0) goto L9f
            r13 = r17
            java.lang.Object r0 = r13.get(r12)
            java.lang.String r0 = (java.lang.String) r0
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L8e
            com.huawei.hms.framework.network.grs.g.a r14 = new com.huawei.hms.framework.network.grs.g.a
            android.content.Context r5 = r9.f16241b
            com.huawei.hms.framework.network.grs.GrsBaseInfo r7 = r9.f16240a
            r1 = r14
            r2 = r0
            r3 = r12
            r4 = r15
            r6 = r18
            r8 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            java.util.concurrent.Callable r1 = r14.g()
            r2 = r16
            java.util.concurrent.Future r1 = r2.submit(r1)
            java.util.Map<java.lang.String, java.util.concurrent.Future<com.huawei.hms.framework.network.grs.g.d>> r3 = r9.f16244e
            r3.put(r0, r1)
            r3 = 1
            long r4 = r9.f16250k     // Catch: java.lang.InterruptedException -> L6a java.util.concurrent.ExecutionException -> L6c java.util.concurrent.TimeoutException -> L6e java.util.concurrent.CancellationException -> L86
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.lang.InterruptedException -> L6a java.util.concurrent.ExecutionException -> L6c java.util.concurrent.TimeoutException -> L6e java.util.concurrent.CancellationException -> L86
            java.lang.Object r0 = r1.get(r4, r0)     // Catch: java.lang.InterruptedException -> L6a java.util.concurrent.ExecutionException -> L6c java.util.concurrent.TimeoutException -> L6e java.util.concurrent.CancellationException -> L86
            r1 = r0
            com.huawei.hms.framework.network.grs.g.d r1 = (com.huawei.hms.framework.network.grs.g.d) r1     // Catch: java.lang.InterruptedException -> L6a java.util.concurrent.ExecutionException -> L6c java.util.concurrent.TimeoutException -> L6e java.util.concurrent.CancellationException -> L86
            if (r1 == 0) goto L67
            boolean r0 = r1.o()     // Catch: java.lang.InterruptedException -> L54 java.util.concurrent.ExecutionException -> L57 java.util.concurrent.TimeoutException -> L63 java.util.concurrent.CancellationException -> L65
            if (r0 != 0) goto L5a
            boolean r0 = r1.m()     // Catch: java.lang.InterruptedException -> L54 java.util.concurrent.ExecutionException -> L57 java.util.concurrent.TimeoutException -> L63 java.util.concurrent.CancellationException -> L65
            if (r0 == 0) goto L67
            goto L5a
        L54:
            r0 = move-exception
            r11 = r1
            goto L76
        L57:
            r0 = move-exception
            r11 = r1
            goto L7e
        L5a:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f16239l     // Catch: java.lang.InterruptedException -> L54 java.util.concurrent.ExecutionException -> L57 java.util.concurrent.TimeoutException -> L63 java.util.concurrent.CancellationException -> L65
            java.lang.String r4 = "grs request return body is not null and is OK."
            com.huawei.hms.framework.common.Logger.i(r0, r4)     // Catch: java.lang.InterruptedException -> L54 java.util.concurrent.ExecutionException -> L57 java.util.concurrent.TimeoutException -> L63 java.util.concurrent.CancellationException -> L65
            r11 = r1
            goto L91
        L63:
            r11 = r1
            goto L6e
        L65:
            r11 = r1
            goto L86
        L67:
            r11 = r1
        L68:
            r3 = r10
            goto L91
        L6a:
            r0 = move-exception
            goto L76
        L6c:
            r0 = move-exception
            goto L7e
        L6e:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f16239l
            java.lang.String r1 = "the wait timed out"
            com.huawei.hms.framework.common.Logger.w(r0, r1)
            goto L68
        L76:
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.c.f16239l
            java.lang.String r4 = "the current thread was interrupted while waiting"
            com.huawei.hms.framework.common.Logger.w(r1, r4, r0)
            goto L91
        L7e:
            java.lang.String r1 = com.huawei.hms.framework.network.grs.g.c.f16239l
            java.lang.String r3 = "the computation threw an ExecutionException"
            com.huawei.hms.framework.common.Logger.w(r1, r3, r0)
            goto L68
        L86:
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f16239l
            java.lang.String r1 = "{requestServer} the computation was cancelled"
            com.huawei.hms.framework.common.Logger.i(r0, r1)
            goto L91
        L8e:
            r2 = r16
            goto L68
        L91:
            if (r3 == 0) goto L9b
            java.lang.String r0 = com.huawei.hms.framework.network.grs.g.c.f16239l
            java.lang.String r1 = "needBreak is true so need break current circulation"
            com.huawei.hms.framework.common.Logger.v(r0, r1)
            goto L9f
        L9b:
            int r12 = r12 + 1
            goto L5
        L9f:
            com.huawei.hms.framework.network.grs.g.d r0 = r15.b(r11)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.g.c.a(java.util.concurrent.ExecutorService, java.util.List, java.lang.String, com.huawei.hms.framework.network.grs.e.c):com.huawei.hms.framework.network.grs.g.d");
    }

    private void b() {
        com.huawei.hms.framework.network.grs.g.j.d dVarA = com.huawei.hms.framework.network.grs.g.i.a.a(this.f16241b);
        if (dVarA == null) {
            Logger.w(f16239l, "g*s***_se****er_conf*** maybe has a big error");
            return;
        }
        a(dVarA);
        List<String> listA = dVarA.a();
        if (listA == null || listA.size() <= 0) {
            Logger.v(f16239l, "maybe grs_base_url config with [],please check.");
            return;
        }
        if (listA.size() > 10) {
            throw new IllegalArgumentException("grs_base_url's count is larger than MAX value 10");
        }
        String strB = dVarA.b();
        if (listA.size() > 0) {
            for (String str : listA) {
                if (str.startsWith("https://")) {
                    a(strB, str);
                } else {
                    Logger.w(f16239l, "grs server just support https scheme url,please check.");
                }
            }
        }
        Logger.v(f16239l, "request to GRS server url is {%s}", this.f16247h);
    }

    public String a() {
        return this.f16249j;
    }

    public synchronized void a(d dVar) {
        try {
            this.f16245f.add(dVar);
            d dVar2 = this.f16243d;
            if (dVar2 == null || (!dVar2.o() && !this.f16243d.m())) {
                if (dVar.n()) {
                    Logger.i(f16239l, "GRS server open 503 limiting strategy.");
                    com.huawei.hms.framework.network.grs.h.d.a(this.f16240a.getGrsParasKey(true, true, this.f16241b), new d.a(dVar.k(), SystemClock.elapsedRealtime()));
                    return;
                }
                if (dVar.m()) {
                    Logger.i(f16239l, "GRS server open 304 Not Modified.");
                }
                if (!dVar.o() && !dVar.m()) {
                    Logger.v(f16239l, "grsResponseResult has exception so need return");
                    return;
                }
                this.f16243d = dVar;
                this.f16242c.a(this.f16240a, dVar, this.f16241b, this.f16248i);
                for (Map.Entry<String, Future<d>> entry : this.f16244e.entrySet()) {
                    if (!entry.getKey().equals(dVar.l()) && !entry.getValue().isCancelled()) {
                        Logger.i(f16239l, "future cancel");
                        entry.getValue().cancel(true);
                    }
                }
                return;
            }
            Logger.v(f16239l, "grsResponseResult is ok");
        } catch (Throwable th) {
            throw th;
        }
    }

    public void a(com.huawei.hms.framework.network.grs.g.j.d dVar) {
    }

    private void a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str);
        String grsReqParamJoint = this.f16240a.getGrsReqParamJoint(false, false, d(), this.f16241b);
        if (!TextUtils.isEmpty(grsReqParamJoint)) {
            sb.append("?");
            sb.append(grsReqParamJoint);
        }
        this.f16247h.add(sb.toString());
    }
}
