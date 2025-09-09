package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zza implements OnSuccessListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ OnTokenCanceledListener f13339a;

    zza(zzb zzbVar, OnTokenCanceledListener onTokenCanceledListener) {
        this.f13339a = onTokenCanceledListener;
    }

    @Override // com.google.android.gms.tasks.OnSuccessListener
    public final /* bridge */ /* synthetic */ void onSuccess(Object obj) {
        this.f13339a.onCanceled();
    }
}
