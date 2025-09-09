package com.alibaba.sdk.android.httpdns;

import android.util.Log;
import java.lang.Thread;

/* loaded from: classes2.dex */
public class k implements Thread.UncaughtExceptionHandler {
    private void b(Throwable th) {
        com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
        if (bVarA != null) {
            bVarA.k(th.getMessage());
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        try {
            Log.e("HttpDnsSDK", "Catch an uncaught exception, " + thread.getName() + ", error message: " + th.getMessage());
            b(th);
            th.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
