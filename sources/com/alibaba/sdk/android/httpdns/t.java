package com.alibaba.sdk.android.httpdns;

/* loaded from: classes2.dex */
public class t {

    /* renamed from: a, reason: collision with root package name */
    private static volatile t f8914a;

    /* renamed from: h, reason: collision with root package name */
    private long f8915h = 0;

    /* renamed from: k, reason: collision with root package name */
    private boolean f8916k = true;
    private String hostName = null;

    private t() {
    }

    public static t a() {
        if (f8914a == null) {
            synchronized (t.class) {
                try {
                    if (f8914a == null) {
                        f8914a = new t();
                    }
                } finally {
                }
            }
        }
        return f8914a;
    }

    private boolean d() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j2 = this.f8915h;
        if (j2 != 0 && jCurrentTimeMillis - j2 < 30000) {
            return false;
        }
        this.f8915h = jCurrentTimeMillis;
        return true;
    }

    public synchronized void c(boolean z2) {
        this.f8916k = z2;
    }

    public synchronized void g() {
        this.f8915h = 0L;
    }

    private void a(String str, String str2) {
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                bVarA.a(str, u.a(s.SNIFF_HOST), str2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0010  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0015 A[Catch: all -> 0x0006, Exception -> 0x0008, TryCatch #1 {Exception -> 0x0008, blocks: (B:4:0x0003, B:9:0x000a, B:21:0x002d, B:22:0x004f, B:13:0x0015, B:16:0x001e), top: B:30:0x0003, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x002d A[Catch: all -> 0x0006, Exception -> 0x0008, TryCatch #1 {Exception -> 0x0008, blocks: (B:4:0x0003, B:9:0x000a, B:21:0x002d, B:22:0x004f, B:13:0x0015, B:16:0x001e), top: B:30:0x0003, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004f A[Catch: all -> 0x0006, Exception -> 0x0008, TRY_LEAVE, TryCatch #1 {Exception -> 0x0008, blocks: (B:4:0x0003, B:9:0x000a, B:21:0x002d, B:22:0x004f, B:13:0x0015, B:16:0x001e), top: B:30:0x0003, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void g(java.lang.String r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 == 0) goto La
            r5.hostName = r6     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            goto La
        L6:
            r6 = move-exception
            goto L69
        L8:
            r6 = move-exception
            goto L64
        La:
            boolean r0 = r5.f8916k     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r1 = 0
            r2 = 0
            if (r0 != 0) goto L15
            java.lang.String r0 = "sniffer is turned off"
        L12:
            r3 = r0
            r0 = r2
            goto L2b
        L15:
            boolean r0 = r5.d()     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            if (r0 != 0) goto L1e
            java.lang.String r0 = "sniff too often"
            goto L12
        L1e:
            java.lang.String r0 = r5.hostName     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            if (r0 == 0) goto L29
            java.lang.String r0 = "hostname is null"
            goto L12
        L29:
            r0 = 1
            r3 = r1
        L2b:
            if (r0 == 0) goto L4f
            java.lang.String r0 = "launch a sniff task"
            com.alibaba.sdk.android.httpdns.i.d(r0)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            com.alibaba.sdk.android.httpdns.q r0 = new com.alibaba.sdk.android.httpdns.q     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            java.lang.String r3 = r5.hostName     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            com.alibaba.sdk.android.httpdns.s r4 = com.alibaba.sdk.android.httpdns.s.SNIFF_HOST     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r0.<init>(r3, r4)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r0.a(r2)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            java.util.concurrent.ExecutorService r2 = com.alibaba.sdk.android.httpdns.c.a()     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r2.submit(r0)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            java.lang.String r0 = com.alibaba.sdk.android.httpdns.u.a(r4)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r5.a(r6, r0)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r5.hostName = r1     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            goto L67
        L4f:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r6.<init>()     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            java.lang.String r0 = "launch sniffer failed due to "
            r6.append(r0)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            r6.append(r3)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            com.alibaba.sdk.android.httpdns.i.d(r6)     // Catch: java.lang.Throwable -> L6 java.lang.Exception -> L8
            goto L67
        L64:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L6
        L67:
            monitor-exit(r5)
            return
        L69:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L6
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.t.g(java.lang.String):void");
    }
}
