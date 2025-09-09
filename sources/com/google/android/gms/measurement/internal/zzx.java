package com.google.android.gms.measurement.internal;

import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzfh;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
final class zzx {
    private zzfh.zze zza;
    private Long zzb;
    private long zzc;
    private final /* synthetic */ zzt zzd;

    final zzfh.zze a(String str, zzfh.zze zzeVar) {
        Object obj;
        String strZzg = zzeVar.zzg();
        List<zzfh.zzg> listZzh = zzeVar.zzh();
        this.zzd.g_();
        Long l2 = (Long) zzna.v(zzeVar, "_eid");
        boolean z2 = l2 != null;
        if (z2 && strZzg.equals("_ep")) {
            Preconditions.checkNotNull(l2);
            this.zzd.g_();
            strZzg = (String) zzna.v(zzeVar, "_en");
            if (TextUtils.isEmpty(strZzg)) {
                this.zzd.zzj().zzm().zza("Extra parameter without an event name. eventId", l2);
                return null;
            }
            if (this.zza == null || this.zzb == null || l2.longValue() != this.zzb.longValue()) {
                Pair<zzfh.zze, Long> pairZza = this.zzd.zzh().zza(str, l2);
                if (pairZza == null || (obj = pairZza.first) == null) {
                    this.zzd.zzj().zzm().zza("Extra parameter without existing main event. eventName, eventId", strZzg, l2);
                    return null;
                }
                this.zza = (zzfh.zze) obj;
                this.zzc = ((Long) pairZza.second).longValue();
                this.zzd.g_();
                this.zzb = (Long) zzna.v(this.zza, "_eid");
            }
            long j2 = this.zzc - 1;
            this.zzc = j2;
            if (j2 <= 0) {
                zzao zzaoVarZzh = this.zzd.zzh();
                zzaoVarZzh.zzt();
                zzaoVarZzh.zzj().zzp().zza("Clearing complex main event info. appId", str);
                try {
                    zzaoVarZzh.a().execSQL("delete from main_event_params where app_id=?", new String[]{str});
                } catch (SQLiteException e2) {
                    zzaoVarZzh.zzj().zzg().zza("Error clearing complex main event", e2);
                }
            } else {
                this.zzd.zzh().zza(str, l2, this.zzc, this.zza);
            }
            ArrayList arrayList = new ArrayList();
            for (zzfh.zzg zzgVar : this.zza.zzh()) {
                this.zzd.g_();
                if (zzna.e(zzeVar, zzgVar.zzg()) == null) {
                    arrayList.add(zzgVar);
                }
            }
            if (arrayList.isEmpty()) {
                this.zzd.zzj().zzm().zza("No unique parameters in main event. eventName", strZzg);
            } else {
                arrayList.addAll(listZzh);
                listZzh = arrayList;
            }
        } else if (z2) {
            this.zzb = l2;
            this.zza = zzeVar;
            this.zzd.g_();
            Object objV = zzna.v(zzeVar, "_epc");
            long jLongValue = ((Long) (objV != null ? objV : 0L)).longValue();
            this.zzc = jLongValue;
            if (jLongValue <= 0) {
                this.zzd.zzj().zzm().zza("Complex event with zero extra param count. eventName", strZzg);
            } else {
                this.zzd.zzh().zza(str, (Long) Preconditions.checkNotNull(l2), this.zzc, zzeVar);
            }
        }
        return (zzfh.zze) ((com.google.android.gms.internal.measurement.zzlw) zzeVar.zzby().zza(strZzg).zzd().zza(listZzh).zzab());
    }

    private zzx(zzt zztVar) {
        this.zzd = zztVar;
    }
}
