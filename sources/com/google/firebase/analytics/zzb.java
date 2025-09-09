package com.google.firebase.analytics;

import androidx.annotation.Nullable;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzb implements Callable<Long> {
    private final /* synthetic */ FirebaseAnalytics zza;

    zzb(FirebaseAnalytics firebaseAnalytics) {
        this.zza = firebaseAnalytics;
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public final /* synthetic */ Long call() throws Exception {
        return this.zza.zzb.zzc();
    }
}
