package com.google.android.gms.measurement.internal;

import java.util.Map;

/* loaded from: classes3.dex */
final class zzgu implements com.google.android.gms.internal.measurement.zzo {
    private final /* synthetic */ String zza;
    private final /* synthetic */ zzgp zzb;

    zzgu(zzgp zzgpVar, String str) {
        this.zzb = zzgpVar;
        this.zza = str;
    }

    @Override // com.google.android.gms.internal.measurement.zzo
    public final String zza(String str) {
        Map map = (Map) this.zzb.zzc.get(this.zza);
        if (map == null || !map.containsKey(str)) {
            return null;
        }
        return (String) map.get(str);
    }
}
