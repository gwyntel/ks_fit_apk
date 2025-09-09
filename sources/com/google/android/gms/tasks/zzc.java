package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzc implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Task f13342a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzd f13343b;

    zzc(zzd zzdVar, Task task) {
        this.f13343b = zzdVar;
        this.f13342a = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.f13342a.isCanceled()) {
            this.f13343b.zzc.zzc();
            return;
        }
        try {
            this.f13343b.zzc.zzb(this.f13343b.zzb.then(this.f13342a));
        } catch (RuntimeExecutionException e2) {
            if (e2.getCause() instanceof Exception) {
                this.f13343b.zzc.zza((Exception) e2.getCause());
            } else {
                this.f13343b.zzc.zza(e2);
            }
        } catch (Exception e3) {
            this.f13343b.zzc.zza(e3);
        }
    }
}
