package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnCanceledListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class b<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private OnCanceledListener f15654a;

    /* renamed from: b, reason: collision with root package name */
    private Executor f15655b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f15656c = new Object();

    b(Executor executor, OnCanceledListener onCanceledListener) {
        this.f15654a = onCanceledListener;
        this.f15655b = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f15656c) {
            this.f15654a = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(Task<TResult> task) {
        if (task.isCanceled()) {
            this.f15655b.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.b.1
                @Override // java.lang.Runnable
                public final void run() {
                    synchronized (b.this.f15656c) {
                        try {
                            if (b.this.f15654a != null) {
                                b.this.f15654a.onCanceled();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        }
    }
}
