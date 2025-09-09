package com.google.android.gms.tasks;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class zze implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Task f13344a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzf f13345b;

    zze(zzf zzfVar, Task task) {
        this.f13345b = zzfVar;
        this.f13344a = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Task task = (Task) this.f13345b.zzb.then(this.f13344a);
            if (task == null) {
                this.f13345b.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            zzf zzfVar = this.f13345b;
            Executor executor = TaskExecutors.f13338a;
            task.addOnSuccessListener(executor, zzfVar);
            task.addOnFailureListener(executor, this.f13345b);
            task.addOnCanceledListener(executor, this.f13345b);
        } catch (RuntimeExecutionException e2) {
            if (e2.getCause() instanceof Exception) {
                this.f13345b.zzc.zza((Exception) e2.getCause());
            } else {
                this.f13345b.zzc.zza(e2);
            }
        } catch (Exception e3) {
            this.f13345b.zzc.zza(e3);
        }
    }
}
