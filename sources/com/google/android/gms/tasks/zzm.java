package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzm implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Task f13351a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzn f13352b;

    zzm(zzn zznVar, Task task) {
        this.f13352b = zznVar;
        this.f13351a = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.f13352b.zzb) {
            try {
                zzn zznVar = this.f13352b;
                if (zznVar.zzc != null) {
                    zznVar.zzc.onSuccess(this.f13351a.getResult());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
