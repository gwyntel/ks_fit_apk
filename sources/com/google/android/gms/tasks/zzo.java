package com.google.android.gms.tasks;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class zzo implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Task f13353a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzp f13354b;

    zzo(zzp zzpVar, Task task) {
        this.f13354b = zzpVar;
        this.f13353a = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Task taskThen = this.f13354b.zzb.then(this.f13353a.getResult());
            if (taskThen == null) {
                this.f13354b.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            zzp zzpVar = this.f13354b;
            Executor executor = TaskExecutors.f13338a;
            taskThen.addOnSuccessListener(executor, zzpVar);
            taskThen.addOnFailureListener(executor, this.f13354b);
            taskThen.addOnCanceledListener(executor, this.f13354b);
        } catch (RuntimeExecutionException e2) {
            if (e2.getCause() instanceof Exception) {
                this.f13354b.onFailure((Exception) e2.getCause());
            } else {
                this.f13354b.onFailure(e2);
            }
        } catch (CancellationException unused) {
            this.f13354b.onCanceled();
        } catch (Exception e3) {
            this.f13354b.onFailure(e3);
        }
    }
}
