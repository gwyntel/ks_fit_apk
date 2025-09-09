package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzcn implements zzcm {
    zzcn() {
    }

    @Override // com.google.android.gms.internal.measurement.zzcm
    public final Runnable zza(Runnable runnable) {
        return runnable;
    }

    @Override // com.google.android.gms.internal.measurement.zzcm
    public final <V> Callable<V> zza(Callable<V> callable) {
        return callable;
    }
}
