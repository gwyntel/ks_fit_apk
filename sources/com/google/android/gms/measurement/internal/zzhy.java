package com.google.android.gms.measurement.internal;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzsg;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzhy implements Callable<List<zzmi>> {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ Bundle zzb;
    private final /* synthetic */ zzhg zzc;

    zzhy(zzhg zzhgVar, zzo zzoVar, Bundle bundle) {
        this.zzc = zzhgVar;
        this.zza = zzoVar;
        this.zzb = bundle;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzmi> call() throws Exception {
        this.zzc.zza.z();
        zzmq zzmqVar = this.zzc.zza;
        zzo zzoVar = this.zza;
        Bundle bundle = this.zzb;
        zzmqVar.zzl().zzt();
        if (!zzsg.zzb() || !zzmqVar.zze().zze(zzoVar.zza, zzbi.zzcf) || zzoVar.zza == null) {
            return new ArrayList();
        }
        if (bundle != null) {
            int[] intArray = bundle.getIntArray("uriSources");
            long[] longArray = bundle.getLongArray("uriTimestamps");
            if (intArray != null) {
                if (longArray == null || longArray.length != intArray.length) {
                    zzmqVar.zzj().zzg().zza("Uri sources and timestamps do not match");
                } else {
                    for (int i2 = 0; i2 < intArray.length; i2++) {
                        zzao zzaoVarZzf = zzmqVar.zzf();
                        String str = zzoVar.zza;
                        int i3 = intArray[i2];
                        long j2 = longArray[i2];
                        Preconditions.checkNotEmpty(str);
                        zzaoVarZzf.zzt();
                        zzaoVarZzf.zzak();
                        try {
                            int iDelete = zzaoVarZzf.a().delete("trigger_uris", "app_id=? and source=? and timestamp_millis<=?", new String[]{str, String.valueOf(i3), String.valueOf(j2)});
                            zzaoVarZzf.zzj().zzp().zza("Pruned " + iDelete + " trigger URIs. appId, source, timestamp", str, Integer.valueOf(i3), Long.valueOf(j2));
                        } catch (SQLiteException e2) {
                            zzaoVarZzf.zzj().zzg().zza("Error pruning trigger URIs. appId", zzfs.d(str), e2);
                        }
                    }
                }
            }
        }
        return zzmqVar.zzf().zzh(zzoVar.zza);
    }
}
