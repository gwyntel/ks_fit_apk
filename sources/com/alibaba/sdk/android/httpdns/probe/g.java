package com.alibaba.sdk.android.httpdns.probe;

import com.alibaba.sdk.android.httpdns.i;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes2.dex */
class g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private CountDownLatch f8899a;

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentHashMap<String, Long> f8900b;

    /* renamed from: o, reason: collision with root package name */
    private String f8901o;
    private int port;

    public g(String str, int i2, CountDownLatch countDownLatch, ConcurrentHashMap<String, Long> concurrentHashMap) {
        this.f8901o = str;
        this.port = i2;
        this.f8899a = countDownLatch;
        this.f8900b = concurrentHashMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x007c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long a(java.lang.String r8, int r9) throws java.lang.Throwable {
        /*
            r7 = this;
            java.lang.String r0 = "socket close failed:"
            long r1 = java.lang.System.currentTimeMillis()
            r3 = 0
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            java.net.Socket r6 = new java.net.Socket     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41
            r6.<init>()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L41
            java.net.InetSocketAddress r3 = new java.net.InetSocketAddress     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r3.<init>(r8, r9)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r8 = 5000(0x1388, float:7.006E-42)
            r6.connect(r3, r8)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            long r8 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r6.close()     // Catch: java.io.IOException -> L21
            goto L78
        L21:
            r3 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r0)
            java.lang.String r0 = r3.toString()
            r6.append(r0)
            java.lang.String r0 = r6.toString()
            com.alibaba.sdk.android.httpdns.i.f(r0)
            goto L78
        L39:
            r8 = move-exception
            r3 = r6
            goto L7f
        L3c:
            r8 = move-exception
            r3 = r6
            goto L42
        L3f:
            r8 = move-exception
            goto L7f
        L41:
            r8 = move-exception
        L42:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3f
            r9.<init>()     // Catch: java.lang.Throwable -> L3f
            java.lang.String r6 = "connect failed:"
            r9.append(r6)     // Catch: java.lang.Throwable -> L3f
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L3f
            r9.append(r8)     // Catch: java.lang.Throwable -> L3f
            java.lang.String r8 = r9.toString()     // Catch: java.lang.Throwable -> L3f
            com.alibaba.sdk.android.httpdns.i.f(r8)     // Catch: java.lang.Throwable -> L3f
            if (r3 == 0) goto L77
            r3.close()     // Catch: java.io.IOException -> L60
            goto L77
        L60:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r0)
            java.lang.String r8 = r8.toString()
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            com.alibaba.sdk.android.httpdns.i.f(r8)
        L77:
            r8 = r4
        L78:
            int r0 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r0 != 0) goto L7d
            return r4
        L7d:
            long r8 = r8 - r1
            return r8
        L7f:
            if (r3 == 0) goto L9c
            r3.close()     // Catch: java.io.IOException -> L85
            goto L9c
        L85:
            r9 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r9 = r9.toString()
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            com.alibaba.sdk.android.httpdns.i.f(r9)
        L9c:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.probe.g.a(java.lang.String, int):long");
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            if (this.f8901o == null || !a(this.port)) {
                i.f("invalid params, give up");
            } else {
                long jA = a(this.f8901o, this.port);
                i.d("connect cost for ip:" + this.f8901o + " is " + jA);
                ConcurrentHashMap<String, Long> concurrentHashMap = this.f8900b;
                if (concurrentHashMap != null) {
                    concurrentHashMap.put(this.f8901o, Long.valueOf(jA));
                }
            }
            CountDownLatch countDownLatch = this.f8899a;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private boolean a(int i2) {
        return i2 >= 1 && i2 <= 65535;
    }
}
