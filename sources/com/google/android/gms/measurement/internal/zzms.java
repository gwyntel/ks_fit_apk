package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzms implements zzfy {
    private final /* synthetic */ String zza;
    private final /* synthetic */ zzmq zzb;

    zzms(zzmq zzmqVar, String str) {
        this.zzb = zzmqVar;
        this.zza = str;
    }

    @Override // com.google.android.gms.measurement.internal.zzfy
    public final void zza(String str, int i2, Throwable th, byte[] bArr, Map<String, List<String>> map) throws IllegalStateException {
        this.zzb.r(true, i2, th, bArr, this.zza);
    }
}
