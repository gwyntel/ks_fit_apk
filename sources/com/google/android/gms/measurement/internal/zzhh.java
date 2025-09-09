package com.google.android.gms.measurement.internal;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzhh implements Runnable {
    private final /* synthetic */ zzil zza;
    private final /* synthetic */ zzhc zzb;

    zzhh(zzhc zzhcVar, zzil zzilVar) {
        this.zzb = zzhcVar;
        this.zza = zzilVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzhc.b(this.zzb, this.zza);
        this.zzb.a(this.zza.f13293g);
    }
}
