package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzfa;
import com.google.android.gms.internal.measurement.zzsz;
import java.util.HashMap;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzmo extends zzmm {
    zzmo(zzmq zzmqVar) {
        super(zzmqVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzna g_() {
        return super.g_();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzt zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzao zzh() {
        return super.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzgp zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzlp zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzmo zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    private final String zzb(String str) {
        String strM = zzm().m(str);
        if (TextUtils.isEmpty(strM)) {
            return zzbi.zzq.zza(null);
        }
        Uri uri = Uri.parse(zzbi.zzq.zza(null));
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.authority(strM + "." + uri.getAuthority());
        return builderBuildUpon.build().toString();
    }

    public final zzmn zza(String str) {
        if (zzsz.zzb() && zze().zza(zzbi.zzbu)) {
            zzj().zzp().zza("sgtm feature flag enabled.");
            zzh zzhVarZzd = zzh().zzd(str);
            if (zzhVarZzd == null) {
                return new zzmn(zzb(str));
            }
            zzmn zzmnVar = null;
            if (zzhVarZzd.zzam()) {
                zzj().zzp().zza("sgtm upload enabled in manifest.");
                zzfa.zzd zzdVarH = zzm().h(zzhVarZzd.zzx());
                if (zzdVarH != null) {
                    String strZzj = zzdVarH.zzj();
                    if (!TextUtils.isEmpty(strZzj)) {
                        String strZzi = zzdVarH.zzi();
                        zzj().zzp().zza("sgtm configured with upload_url, server_info", strZzj, TextUtils.isEmpty(strZzi) ? "Y" : "N");
                        if (TextUtils.isEmpty(strZzi)) {
                            zzmnVar = new zzmn(strZzj);
                        } else {
                            HashMap map = new HashMap();
                            map.put("x-google-sgtm-server-info", strZzi);
                            zzmnVar = new zzmn(strZzj, map);
                        }
                    }
                }
            }
            if (zzmnVar != null) {
                return zzmnVar;
            }
        }
        return new zzmn(zzb(str));
    }
}
