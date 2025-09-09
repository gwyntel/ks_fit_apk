package com.huawei.hmf.tasks.a;

import com.huawei.hmf.tasks.CancellationToken;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class c extends CancellationToken {

    /* renamed from: a, reason: collision with root package name */
    public final List<Runnable> f15658a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    public final Object f15659b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public boolean f15660c = false;

    @Override // com.huawei.hmf.tasks.CancellationToken
    public final boolean isCancellationRequested() {
        return this.f15660c;
    }

    @Override // com.huawei.hmf.tasks.CancellationToken
    public final CancellationToken register(Runnable runnable) {
        synchronized (this.f15659b) {
            try {
                if (this.f15660c) {
                    runnable.run();
                } else {
                    this.f15658a.add(runnable);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this;
    }
}
