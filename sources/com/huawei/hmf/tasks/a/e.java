package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.OnCanceledListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
final class e<TResult> implements OnCanceledListener, OnFailureListener, OnSuccessListener<TResult> {

    /* renamed from: a, reason: collision with root package name */
    private final Object f15666a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private final int f15667b;

    /* renamed from: c, reason: collision with root package name */
    private final i<Void> f15668c;

    /* renamed from: d, reason: collision with root package name */
    private int f15669d;

    /* renamed from: e, reason: collision with root package name */
    private Exception f15670e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f15671f;

    e(int i2, i<Void> iVar) {
        this.f15667b = i2;
        this.f15668c = iVar;
    }

    private void a() {
        if (this.f15669d >= this.f15667b) {
            if (this.f15670e != null) {
                this.f15668c.a(new ExecutionException("a task failed", this.f15670e));
            } else if (this.f15671f) {
                this.f15668c.a();
            } else {
                this.f15668c.a((i<Void>) null);
            }
        }
    }

    @Override // com.huawei.hmf.tasks.OnCanceledListener
    public final void onCanceled() {
        synchronized (this.f15666a) {
            this.f15669d++;
            this.f15671f = true;
            a();
        }
    }

    @Override // com.huawei.hmf.tasks.OnFailureListener
    public final void onFailure(Exception exc) {
        synchronized (this.f15666a) {
            this.f15669d++;
            this.f15670e = exc;
            a();
        }
    }

    @Override // com.huawei.hmf.tasks.OnSuccessListener
    public final void onSuccess(TResult tresult) {
        synchronized (this.f15666a) {
            this.f15669d++;
            a();
        }
    }
}
