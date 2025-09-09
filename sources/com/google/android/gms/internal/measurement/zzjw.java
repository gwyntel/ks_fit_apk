package com.google.android.gms.internal.measurement;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class zzjw extends zzjy {
    public static <V> void zza(ListenableFuture<V> listenableFuture, zzjt<? super V> zzjtVar, Executor executor) {
        zzhn.zza(zzjtVar);
        listenableFuture.addListener(new zzjv(listenableFuture, zzjtVar), executor);
    }
}
