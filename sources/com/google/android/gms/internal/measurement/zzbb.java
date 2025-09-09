package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzbb {
    private Map<String, zzay> zza = new HashMap();
    private zzbq zzb = new zzbq();

    public zzbb() {
        zza(new zzaw());
        zza(new zzba());
        zza(new zzbc());
        zza(new zzbg());
        zza(new zzbi());
        zza(new zzbo());
        zza(new zzbt());
    }

    public final zzaq zza(zzh zzhVar, zzaq zzaqVar) {
        zzg.zza(zzhVar);
        if (!(zzaqVar instanceof zzat)) {
            return zzaqVar;
        }
        zzat zzatVar = (zzat) zzaqVar;
        ArrayList<zzaq> arrayListZzb = zzatVar.zzb();
        String strZza = zzatVar.zza();
        return (this.zza.containsKey(strZza) ? this.zza.get(strZza) : this.zzb).zza(strZza, zzhVar, arrayListZzb);
    }

    private final void zza(zzay zzayVar) {
        Iterator it = zzayVar.f13178a.iterator();
        while (it.hasNext()) {
            this.zza.put(((zzbv) it.next()).toString(), zzayVar);
        }
    }
}
