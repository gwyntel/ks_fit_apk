package com.alibaba.sdk.android.openaccount.task;

import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;

/* loaded from: classes2.dex */
public abstract class AbsRunnable implements Runnable {
    private static final String TAG = "AbsRunnable";

    @Override // java.lang.Runnable
    public void run() {
        try {
            runWithoutException();
        } catch (Throwable th) {
            AliSDKLogger.e(TAG, th.getMessage(), th);
        }
    }

    public abstract void runWithoutException();
}
