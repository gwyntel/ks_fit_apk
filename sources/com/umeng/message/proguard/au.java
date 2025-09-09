package com.umeng.message.proguard;

import android.os.SystemClock;
import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.umeng.message.common.UPLog;

/* loaded from: classes4.dex */
public final class au {
    public static String a(String str, String str2) {
        String ipByHostAsync;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        try {
            HttpDnsService service = HttpDns.getService(x.a(), str);
            service.setExpiredIPEnabled(true);
            int i2 = 0;
            ipByHostAsync = null;
            while (true) {
                try {
                    ipByHostAsync = service.getIpByHostAsync(str2);
                    if (ipByHostAsync != null) {
                        break;
                    }
                    Thread.sleep(500L);
                    int i3 = i2 + 1;
                    if (i3 >= 5) {
                        break;
                    }
                    i2 = i3;
                } catch (Throwable th) {
                    th = th;
                    try {
                        UPLog.i("HttpDns", "host parse error:", th.getMessage());
                        UPLog.i("HttpDns", "host:", str2, "ip:", ipByHostAsync, "consume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
                        return ipByHostAsync;
                    } catch (Throwable th2) {
                        UPLog.i("HttpDns", "host:", str2, "ip:", ipByHostAsync, "consume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
                        throw th2;
                    }
                }
            }
            UPLog.i("HttpDns", "host:", str2, "ip:", ipByHostAsync, "consume:", Long.valueOf(SystemClock.elapsedRealtime() - jElapsedRealtime));
        } catch (Throwable th3) {
            th = th3;
            ipByHostAsync = null;
        }
        return ipByHostAsync;
    }
}
