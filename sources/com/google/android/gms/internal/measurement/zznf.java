package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zznf implements zznc {
    zznf() {
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final int zza(int i2, Object obj, Object obj2) {
        zznd zzndVar = (zznd) obj;
        if (zzndVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzndVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final Object zzb(Object obj) {
        return zznd.zza().zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final Object zzc(Object obj) {
        ((zznd) obj).zzc();
        return obj;
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final Map<?, ?> zzd(Object obj) {
        return (zznd) obj;
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final Map<?, ?> zze(Object obj) {
        return (zznd) obj;
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final boolean zzf(Object obj) {
        return !((zznd) obj).zzd();
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final zzna<?, ?> zza(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.measurement.zznc
    public final Object zza(Object obj, Object obj2) {
        zznd zzndVarZzb = (zznd) obj;
        zznd zzndVar = (zznd) obj2;
        if (!zzndVar.isEmpty()) {
            if (!zzndVarZzb.zzd()) {
                zzndVarZzb = zzndVarZzb.zzb();
            }
            zzndVarZzb.zza(zzndVar);
        }
        return zzndVarZzb;
    }
}
