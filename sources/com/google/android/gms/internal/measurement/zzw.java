package com.google.android.gms.internal.measurement;

import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzw extends zzal {
    private zzaa zzk;

    public zzw(zzaa zzaaVar) {
        super("internal.registerCallback");
        this.zzk = zzaaVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        zzg.zza(this.f13176a, 3, list);
        String strZzf = zzhVar.zza(list.get(0)).zzf();
        zzaq zzaqVarZza = zzhVar.zza(list.get(1));
        if (!(zzaqVarZza instanceof zzar)) {
            throw new IllegalArgumentException("Invalid callback type");
        }
        zzaq zzaqVarZza2 = zzhVar.zza(list.get(2));
        if (!(zzaqVarZza2 instanceof zzap)) {
            throw new IllegalArgumentException("Invalid callback params");
        }
        zzap zzapVar = (zzap) zzaqVarZza2;
        if (!zzapVar.zzc("type")) {
            throw new IllegalArgumentException("Undefined rule type");
        }
        this.zzk.zza(strZzf, zzapVar.zzc(RemoteMessageConst.Notification.PRIORITY) ? zzg.zzb(zzapVar.zza(RemoteMessageConst.Notification.PRIORITY).zze().doubleValue()) : 1000, (zzar) zzaqVarZza, zzapVar.zza("type").zzf());
        return zzaq.zzc;
    }
}
