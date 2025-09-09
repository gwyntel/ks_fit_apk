package com.google.android.gms.internal.measurement;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes3.dex */
public final class zzjx {
    public static <V> V zza(Future<V> future) throws ExecutionException {
        V v2;
        boolean z2 = false;
        while (true) {
            try {
                v2 = future.get();
                break;
            } catch (InterruptedException unused) {
                z2 = true;
            } catch (Throwable th) {
                if (z2) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return v2;
    }
}
