package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class d<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    Executor f15661a;

    /* renamed from: b, reason: collision with root package name */
    private OnCompleteListener<TResult> f15662b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f15663c = new Object();

    d(Executor executor, OnCompleteListener<TResult> onCompleteListener) {
        this.f15662b = onCompleteListener;
        this.f15661a = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f15663c) {
            this.f15662b = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(final Task<TResult> task) {
        this.f15661a.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.d.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (d.this.f15663c) {
                    try {
                        if (d.this.f15662b != null) {
                            d.this.f15662b.onComplete(task);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        });
    }
}
