package com.google.android.gms.common;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzv extends zzx {
    private final Callable zze;

    /* synthetic */ zzv(Callable callable, zzu zzuVar) {
        super();
        this.zze = callable;
    }

    @Override // com.google.android.gms.common.zzx
    final String a() {
        try {
            return (String) this.zze.call();
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
