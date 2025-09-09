package com.google.android.gms.tasks;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzz implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zzw f13356a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Callable f13357b;

    zzz(zzw zzwVar, Callable callable) {
        this.f13356a = zzwVar;
        this.f13357b = callable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.f13356a.zzb(this.f13357b.call());
        } catch (Exception e2) {
            this.f13356a.zza(e2);
        } catch (Throwable th) {
            this.f13356a.zza(new RuntimeException(th));
        }
    }
}
