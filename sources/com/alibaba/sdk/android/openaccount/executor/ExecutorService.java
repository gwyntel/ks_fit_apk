package com.alibaba.sdk.android.openaccount.executor;

import android.os.Looper;

/* loaded from: classes2.dex */
public interface ExecutorService {
    Looper getDefaultLooper();

    void postHandlerTask(Runnable runnable);

    void postTask(Runnable runnable);

    void postUITask(Runnable runnable);
}
