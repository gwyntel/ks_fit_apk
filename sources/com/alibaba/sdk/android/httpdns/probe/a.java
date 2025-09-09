package com.alibaba.sdk.android.httpdns.probe;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private f f8887a;

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentHashMap<String, Long> f8888b = new ConcurrentHashMap<>();
    private String host;
    private String[] ips;

    /* renamed from: j, reason: collision with root package name */
    private long f8889j;
    private int port;

    public a(long j2, String str, String[] strArr, int i2, f fVar) {
        this.f8889j = j2;
        this.host = str;
        this.ips = strArr;
        this.port = i2;
        this.f8887a = fVar;
    }

    private c a(String[] strArr) {
        String[] strArr2 = this.ips;
        if (strArr2 == null || strArr2.length == 0 || strArr == null || strArr.length == 0) {
            return null;
        }
        String str = strArr2[0];
        String str2 = strArr[0];
        return new c(this.host, strArr, str, str2, this.f8888b.containsKey(str) ? this.f8888b.get(str).longValue() : 2147483647L, this.f8888b.containsKey(str2) ? this.f8888b.get(str2).longValue() : 2147483647L);
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        String[] strArrA;
        String[] strArr = this.ips;
        if (strArr == null || strArr.length == 0) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(this.ips.length);
        for (int i2 = 0; i2 < this.ips.length; i2++) {
            com.alibaba.sdk.android.httpdns.c.a().execute(new g(this.ips[i2], this.port, countDownLatch, this.f8888b));
        }
        try {
            countDownLatch.await(10000L, TimeUnit.MILLISECONDS);
            if (this.f8887a == null || (strArrA = a(this.f8888b)) == null || strArrA.length == 0) {
                return;
            }
            this.f8887a.a(this.f8889j, a(strArrA));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private String[] a(ConcurrentHashMap<String, Long> concurrentHashMap) {
        if (concurrentHashMap == null) {
            return null;
        }
        int size = concurrentHashMap.size();
        String[] strArr = new String[size];
        Iterator<String> it = concurrentHashMap.keySet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            strArr[i2] = new String(it.next());
            i2++;
        }
        for (int i3 = 0; i3 < size - 1; i3++) {
            int i4 = 0;
            while (i4 < (size - i3) - 1) {
                int i5 = i4 + 1;
                if (concurrentHashMap.get(strArr[i4]).longValue() > concurrentHashMap.get(strArr[i5]).longValue()) {
                    String str = strArr[i4];
                    strArr[i4] = strArr[i5];
                    strArr[i5] = str;
                }
                i4 = i5;
            }
        }
        return strArr;
    }
}
