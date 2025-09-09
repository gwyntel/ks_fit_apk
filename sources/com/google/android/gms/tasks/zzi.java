package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzi implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Task f13347a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zzj f13348b;

    zzi(zzj zzjVar, Task task) {
        this.f13348b = zzjVar;
        this.f13347a = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.f13348b.zzb) {
            try {
                zzj zzjVar = this.f13348b;
                if (zzjVar.zzc != null) {
                    zzjVar.zzc.onComplete(this.f13347a);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
