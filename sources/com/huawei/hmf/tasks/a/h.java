package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.ExecuteResult;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class h<TResult> implements ExecuteResult<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private OnSuccessListener<TResult> f15679a;

    /* renamed from: b, reason: collision with root package name */
    private Executor f15680b;

    /* renamed from: c, reason: collision with root package name */
    private final Object f15681c = new Object();

    h(Executor executor, OnSuccessListener<TResult> onSuccessListener) {
        this.f15679a = onSuccessListener;
        this.f15680b = executor;
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void cancel() {
        synchronized (this.f15681c) {
            this.f15679a = null;
        }
    }

    @Override // com.huawei.hmf.tasks.ExecuteResult
    public final void onComplete(final Task<TResult> task) {
        if (!task.isSuccessful() || task.isCanceled()) {
            return;
        }
        this.f15680b.execute(new Runnable() { // from class: com.huawei.hmf.tasks.a.h.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (h.this.f15681c) {
                    try {
                        if (h.this.f15679a != null) {
                            h.this.f15679a.onSuccess(task.getResult());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        });
    }
}
