package com.huawei.hmf.tasks;

/* loaded from: classes3.dex */
public interface ExecuteResult<TResult> {
    void cancel();

    void onComplete(Task<TResult> task);
}
