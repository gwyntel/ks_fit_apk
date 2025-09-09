package com.google.android.gms.measurement.internal;

import androidx.annotation.WorkerThread;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzmd extends zzaw {
    private final /* synthetic */ zzme zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzmd(zzme zzmeVar, zzic zzicVar) {
        super(zzicVar);
        this.zza = zzmeVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaw
    @WorkerThread
    public final void zzb() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzme.c(this.zza);
    }
}
