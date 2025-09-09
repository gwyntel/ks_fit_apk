package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzfh;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.internal.measurement.zzss;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
final class zzkd extends zzml {
    public zzkd(zzmq zzmqVar) {
        super(zzmqVar);
    }

    private static String zza(String str, String str2) {
        throw new SecurityException("This implementation should not be used.");
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    protected final boolean zzc() {
        return false;
    }

    @WorkerThread
    public final byte[] zza(@NonNull zzbg zzbgVar, @Size(min = 1) String str) {
        zznb next;
        zzfh.zzj.zza zzaVar;
        Bundle bundle;
        zzh zzhVar;
        zzfh.zzi.zza zzaVar2;
        byte[] bArr;
        long j2;
        zzbc zzbcVarA;
        zzt();
        this.f13286a.h();
        Preconditions.checkNotNull(zzbgVar);
        Preconditions.checkNotEmpty(str);
        if (!zze().zze(str, zzbi.zzbc)) {
            zzj().zzc().zza("Generating ScionPayload disabled. packageName", str);
            return new byte[0];
        }
        if (!"_iap".equals(zzbgVar.zza) && !"_iapx".equals(zzbgVar.zza)) {
            zzj().zzc().zza("Generating a payload for this event is not available. package_name, event_name", str, zzbgVar.zza);
            return null;
        }
        zzfh.zzi.zza zzaVarZzb = zzfh.zzi.zzb();
        zzh().zzp();
        try {
            zzh zzhVarZzd = zzh().zzd(str);
            if (zzhVarZzd == null) {
                zzj().zzc().zza("Log and bundle not available. package_name", str);
                return new byte[0];
            }
            if (!zzhVarZzd.zzak()) {
                zzj().zzc().zza("Log and bundle disabled. package_name", str);
                return new byte[0];
            }
            zzfh.zzj.zza zzaVarZzp = zzfh.zzj.zzu().zzg(1).zzp("android");
            if (!TextUtils.isEmpty(zzhVarZzd.zzx())) {
                zzaVarZzp.zzb(zzhVarZzd.zzx());
            }
            if (!TextUtils.isEmpty(zzhVarZzd.zzz())) {
                zzaVarZzp.zzd((String) Preconditions.checkNotNull(zzhVarZzd.zzz()));
            }
            if (!TextUtils.isEmpty(zzhVarZzd.zzaa())) {
                zzaVarZzp.zze((String) Preconditions.checkNotNull(zzhVarZzd.zzaa()));
            }
            if (zzhVarZzd.zzc() != -2147483648L) {
                zzaVarZzp.zze((int) zzhVarZzd.zzc());
            }
            zzaVarZzp.zzf(zzhVarZzd.zzo()).zzd(zzhVarZzd.zzm());
            String strZzac = zzhVarZzd.zzac();
            String strZzv = zzhVarZzd.zzv();
            if (!TextUtils.isEmpty(strZzac)) {
                zzaVarZzp.zzm(strZzac);
            } else if (!TextUtils.isEmpty(strZzv)) {
                zzaVarZzp.zza(strZzv);
            }
            zzaVarZzp.zzj(zzhVarZzd.zzt());
            zzie zzieVarS = this.f13314b.s(str);
            zzaVarZzp.zzc(zzhVarZzd.zzl());
            if (this.f13286a.zzac() && zze().zzk(zzaVarZzp.zzr()) && zzieVarS.zzg() && !TextUtils.isEmpty(null)) {
                zzaVarZzp.zzj((String) null);
            }
            zzaVarZzp.zzg(zzieVarS.zze());
            if (zzieVarS.zzg() && zzhVarZzd.zzaj()) {
                Pair pairA = zzn().a(zzhVarZzd.zzx(), zzieVarS);
                if (zzhVarZzd.zzaj() && pairA != null && !TextUtils.isEmpty((CharSequence) pairA.first)) {
                    zzaVarZzp.zzq(zza((String) pairA.first, Long.toString(zzbgVar.zzd)));
                    Object obj = pairA.second;
                    if (obj != null) {
                        zzaVarZzp.zzc(((Boolean) obj).booleanValue());
                    }
                }
            }
            zzf().zzab();
            zzfh.zzj.zza zzaVarZzi = zzaVarZzp.zzi(Build.MODEL);
            zzf().zzab();
            zzaVarZzi.zzo(Build.VERSION.RELEASE).zzi((int) zzf().zzg()).zzs(zzf().zzh());
            if (zzieVarS.zzh() && zzhVarZzd.zzy() != null) {
                zzaVarZzp.zzc(zza((String) Preconditions.checkNotNull(zzhVarZzd.zzy()), Long.toString(zzbgVar.zzd)));
            }
            if (!TextUtils.isEmpty(zzhVarZzd.zzab())) {
                zzaVarZzp.zzl((String) Preconditions.checkNotNull(zzhVarZzd.zzab()));
            }
            String strZzx = zzhVarZzd.zzx();
            List<zznb> listZzi = zzh().zzi(strZzx);
            Iterator<zznb> it = listZzi.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if ("_lte".equals(next.f13324c)) {
                    break;
                }
            }
            if (next == null || next.f13326e == null) {
                zznb zznbVar = new zznb(strZzx, "auto", "_lte", zzb().currentTimeMillis(), 0L);
                listZzi.add(zznbVar);
                zzh().zza(zznbVar);
            }
            zzfh.zzn[] zznVarArr = new zzfh.zzn[listZzi.size()];
            for (int i2 = 0; i2 < listZzi.size(); i2++) {
                zzfh.zzn.zza zzaVarZzb2 = zzfh.zzn.zze().zza(listZzi.get(i2).f13324c).zzb(listZzi.get(i2).f13325d);
                g_().r(zzaVarZzb2, listZzi.get(i2).f13326e);
                zznVarArr[i2] = (zzfh.zzn) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzb2.zzab());
            }
            zzaVarZzp.zze(Arrays.asList(zznVarArr));
            g_().q(zzaVarZzp);
            if (zzql.zzb() && zze().zza(zzbi.zzcm)) {
                this.f13314b.h(zzhVarZzd, zzaVarZzp);
            }
            zzfw zzfwVarZza = zzfw.zza(zzbgVar);
            zzq().j(zzfwVarZza.zzb, zzh().zzc(str));
            zzq().l(zzfwVarZza, zze().zzd(str));
            Bundle bundle2 = zzfwVarZza.zzb;
            bundle2.putLong("_c", 1L);
            zzj().zzc().zza("Marking in-app purchase as real-time");
            bundle2.putLong("_r", 1L);
            bundle2.putString("_o", zzbgVar.zzc);
            if (zzq().M(zzaVarZzp.zzr())) {
                zzq().k(bundle2, "_dbg", 1L);
                zzq().k(bundle2, "_r", 1L);
            }
            zzbc zzbcVarZzd = zzh().zzd(str, zzbgVar.zza);
            if (zzbcVarZzd == null) {
                zzaVar = zzaVarZzp;
                bundle = bundle2;
                zzhVar = zzhVarZzd;
                zzaVar2 = zzaVarZzb;
                bArr = null;
                zzbcVarA = new zzbc(str, zzbgVar.zza, 0L, 0L, zzbgVar.zzd, 0L, null, null, null, null);
                j2 = 0;
            } else {
                zzaVar = zzaVarZzp;
                bundle = bundle2;
                zzhVar = zzhVarZzd;
                zzaVar2 = zzaVarZzb;
                bArr = null;
                j2 = zzbcVarZzd.f13270f;
                zzbcVarA = zzbcVarZzd.a(zzbgVar.zzd);
            }
            zzh().zza(zzbcVarA);
            zzaz zzazVar = new zzaz(this.f13286a, zzbgVar.zzc, str, zzbgVar.zza, zzbgVar.zzd, j2, bundle);
            zzfh.zze.zza zzaVarZza = zzfh.zze.zze().zzb(zzazVar.f13262c).zza(zzazVar.f13261b).zza(zzazVar.f13263d);
            Iterator<String> it2 = zzazVar.f13264e.iterator();
            while (it2.hasNext()) {
                String next2 = it2.next();
                zzfh.zzg.zza zzaVarZza2 = zzfh.zzg.zze().zza(next2);
                Object objD = zzazVar.f13264e.d(next2);
                if (objD != null) {
                    g_().p(zzaVarZza2, objD);
                    zzaVarZza.zza(zzaVarZza2);
                }
            }
            zzfh.zzj.zza zzaVar3 = zzaVar;
            zzaVar3.zza(zzaVarZza).zza(zzfh.zzk.zza().zza(zzfh.zzf.zza().zza(zzbcVarA.f13267c).zza(zzbgVar.zza)));
            zzaVar3.zza(zzg().a(zzhVar.zzx(), Collections.emptyList(), zzaVar3.zzx(), Long.valueOf(zzaVarZza.zzc()), Long.valueOf(zzaVarZza.zzc())));
            if (zzaVarZza.zzg()) {
                zzaVar3.zzi(zzaVarZza.zzc()).zze(zzaVarZza.zzc());
            }
            long jZzp = zzhVar.zzp();
            if (jZzp != 0) {
                zzaVar3.zzg(jZzp);
            }
            long jZzr = zzhVar.zzr();
            if (jZzr != 0) {
                zzaVar3.zzh(jZzr);
            } else if (jZzp != 0) {
                zzaVar3.zzh(jZzp);
            }
            String strZzaf = zzhVar.zzaf();
            if (zzss.zzb() && zze().zze(str, zzbi.zzbt) && strZzaf != null) {
                zzaVar3.zzr(strZzaf);
            }
            zzhVar.zzai();
            zzaVar3.zzf((int) zzhVar.zzq()).zzl(81010L).zzk(zzb().currentTimeMillis()).zzd(true);
            if (zze().zza(zzbi.zzbw)) {
                this.f13314b.m(zzaVar3.zzr(), zzaVar3);
            }
            zzfh.zzi.zza zzaVar4 = zzaVar2;
            zzaVar4.zza(zzaVar3);
            zzh zzhVar2 = zzhVar;
            zzhVar2.zzp(zzaVar3.zzd());
            zzhVar2.zzn(zzaVar3.zzc());
            zzh().zza(zzhVar2);
            zzh().zzw();
            try {
                return g_().x(((zzfh.zzi) ((com.google.android.gms.internal.measurement.zzlw) zzaVar4.zzab())).zzbv());
            } catch (IOException e2) {
                zzj().zzg().zza("Data loss. Failed to bundle and serialize. appId", zzfs.d(str), e2);
                return bArr;
            }
        } catch (SecurityException e3) {
            zzj().zzc().zza("Resettable device id encryption failed", e3.getMessage());
            return new byte[0];
        } catch (SecurityException e4) {
            zzj().zzc().zza("app instance id encryption failed", e4.getMessage());
            return new byte[0];
        } finally {
            zzh().zzu();
        }
    }
}
