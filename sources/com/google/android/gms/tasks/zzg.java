package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzg implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzh f13346a;

    zzg(zzh zzhVar) {
        this.f13346a = zzhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.f13346a.zzb) {
            try {
                zzh zzhVar = this.f13346a;
                if (zzhVar.zzc != null) {
                    zzhVar.zzc.onCanceled();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
