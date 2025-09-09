package com.google.android.gms.internal.measurement;

import androidx.core.app.NotificationCompat;
import java.util.List;

/* loaded from: classes3.dex */
public final class zzr extends zzal {
    private final zzv zzk;

    public zzr(zzv zzvVar) {
        super("internal.logger");
        this.zzk = zzvVar;
        this.f13177b.put("log", new zzu(this, false, true));
        this.f13177b.put(NotificationCompat.GROUP_KEY_SILENT, new zzq(this, NotificationCompat.GROUP_KEY_SILENT));
        ((zzal) this.f13177b.get(NotificationCompat.GROUP_KEY_SILENT)).zza("log", new zzu(this, true, true));
        this.f13177b.put("unmonitored", new zzt(this, "unmonitored"));
        ((zzal) this.f13177b.get("unmonitored")).zza("log", new zzu(this, false, false));
    }

    @Override // com.google.android.gms.internal.measurement.zzal
    public final zzaq zza(zzh zzhVar, List<zzaq> list) {
        return zzaq.zzc;
    }
}
