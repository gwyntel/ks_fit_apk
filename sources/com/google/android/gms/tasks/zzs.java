package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzs implements OnTokenCanceledListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f13355a;

    zzs(TaskCompletionSource taskCompletionSource) {
        this.f13355a = taskCompletionSource;
    }

    @Override // com.google.android.gms.tasks.OnTokenCanceledListener
    public final void onCanceled() {
        this.f13355a.zza.zzc();
    }
}
