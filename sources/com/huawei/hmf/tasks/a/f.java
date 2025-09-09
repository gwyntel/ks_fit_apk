package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class f<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private OnFailureListener f15672a;

    /* renamed from: b, reason: collision with root package name */
    private Executor f15673b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f15674c = new Object();

    f(Executor executor, OnFailureListener onFailureListener) {
        this.f15672a = onFailureListener;
        this.f15673b = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f15674c) {
            this.f15672a = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(final Task<TResult> task) {
        if (task.isSuccessful() || task.isCanceled()) {
            return;
        }
        this.f15673b.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.f.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (f.this.f15674c) {
                    try {
                        if (f.this.f15672a != null) {
                            f.this.f15672a.onFailure(task.getException());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        });
    }
}
