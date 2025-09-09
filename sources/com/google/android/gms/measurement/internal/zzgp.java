package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.collection.LruCache;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzfa;
import com.google.android.gms.internal.measurement.zzfo;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.measurement.internal.zzie;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.umeng.analytics.pro.bc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzgp extends zzml implements zzah {

    /* renamed from: c, reason: collision with root package name */
    final LruCache f13280c;

    /* renamed from: d, reason: collision with root package name */
    final com.google.android.gms.internal.measurement.zzv f13281d;
    private final Map<String, Map<String, String>> zzc;

    @VisibleForTesting
    private final Map<String, Set<String>> zzd;

    @VisibleForTesting
    private final Map<String, Map<String, Boolean>> zze;

    @VisibleForTesting
    private final Map<String, Map<String, Boolean>> zzg;
    private final Map<String, zzfa.zzd> zzh;
    private final Map<String, Map<String, Integer>> zzi;
    private final Map<String, String> zzj;
    private final Map<String, String> zzk;
    private final Map<String, String> zzl;
    private final Map<String, Map<zzie.zza, Boolean>> zzm;
    private final Map<String, Map<zzie.zza, zzie.zza>> zzn;

    zzgp(zzmq zzmqVar) {
        super(zzmqVar);
        this.zzc = new ArrayMap();
        this.zzd = new ArrayMap();
        this.zze = new ArrayMap();
        this.zzg = new ArrayMap();
        this.zzh = new ArrayMap();
        this.zzj = new ArrayMap();
        this.zzk = new ArrayMap();
        this.zzl = new ArrayMap();
        this.zzi = new ArrayMap();
        this.zzm = new ArrayMap();
        this.zzn = new ArrayMap();
        this.f13280c = new zzgs(this, 20);
        this.f13281d = new zzgv(this);
    }

    static /* synthetic */ com.google.android.gms.internal.measurement.zzb a(zzgp zzgpVar, String str) {
        zzgpVar.zzak();
        Preconditions.checkNotEmpty(str);
        if (!zzgpVar.zzl(str)) {
            return null;
        }
        if (!zzgpVar.zzh.containsKey(str) || zzgpVar.zzh.get(str) == null) {
            zzgpVar.zzv(str);
        } else {
            zzgpVar.zzc(str, zzgpVar.zzh.get(str));
        }
        return (com.google.android.gms.internal.measurement.zzb) zzgpVar.f13280c.snapshot().get(str);
    }

    @WorkerThread
    private final void zzv(String str) {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        if (this.zzh.get(str) == null) {
            zzaq zzaqVarZze = zzh().zze(str);
            if (zzaqVarZze == null) {
                this.zzc.put(str, null);
                this.zze.put(str, null);
                this.zzd.put(str, null);
                this.zzg.put(str, null);
                this.zzh.put(str, null);
                this.zzj.put(str, null);
                this.zzk.put(str, null);
                this.zzl.put(str, null);
                this.zzi.put(str, null);
                this.zzm.put(str, null);
                this.zzn.put(str, null);
                return;
            }
            zzfa.zzd.zza zzaVarZzby = zza(str, zzaqVarZze.f13257a).zzby();
            zza(str, zzaVarZzby);
            this.zzc.put(str, zza((zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab())));
            this.zzh.put(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
            zzc(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
            this.zzj.put(str, zzaVarZzby.zzc());
            this.zzk.put(str, zzaqVarZze.f13258b);
            this.zzl.put(str, zzaqVarZze.f13259c);
            if (zzql.zzb() && zze().zza(zzbi.zzcm)) {
                zza(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
                zzb(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
            }
        }
    }

    final zzie.zza b(String str, zzie.zza zzaVar) {
        zzt();
        zzv(str);
        if (this.zzn.get(str) != null && this.zzn.get(str).containsKey(zzaVar)) {
            return this.zzn.get(str).get(zzaVar);
        }
        return null;
    }

    protected final boolean d(String str, byte[] bArr, String str2, String str3) {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        zzfa.zzd.zza zzaVarZzby = zza(str, bArr).zzby();
        if (zzaVarZzby == null) {
            return false;
        }
        zza(str, zzaVarZzby);
        zzc(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
        this.zzh.put(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
        this.zzj.put(str, zzaVarZzby.zzc());
        this.zzk.put(str, str2);
        this.zzl.put(str, str3);
        this.zzc.put(str, zza((zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab())));
        if (zzql.zzb() && zze().zza(zzbi.zzcm)) {
            zza(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
            zzb(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
        }
        zzh().d(str, new ArrayList(zzaVarZzby.zzd()));
        try {
            zzaVarZzby.zzb();
            bArr = ((zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab())).zzbv();
        } catch (RuntimeException e2) {
            zzj().zzu().zza("Unable to serialize reduced-size config. Storing full config instead. appId", zzfs.d(str), e2);
        }
        zzao zzaoVarZzh = zzh();
        Preconditions.checkNotEmpty(str);
        zzaoVarZzh.zzt();
        zzaoVarZzh.zzak();
        ContentValues contentValues = new ContentValues();
        contentValues.put("remote_config", bArr);
        contentValues.put("config_last_modified_time", str2);
        contentValues.put("e_tag", str3);
        try {
            if (zzaoVarZzh.a().update("apps", contentValues, "app_id = ?", new String[]{str}) == 0) {
                zzaoVarZzh.zzj().zzg().zza("Failed to update remote config (got 0). appId", zzfs.d(str));
            }
        } catch (SQLiteException e3) {
            zzaoVarZzh.zzj().zzg().zza("Error storing remote config. appId", zzfs.d(str), e3);
        }
        this.zzh.put(str, (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) zzaVarZzby.zzab()));
        return true;
    }

    final int e(String str, String str2) {
        Integer num;
        zzt();
        zzv(str);
        Map<String, Integer> map = this.zzi.get(str);
        if (map == null || (num = map.get(str2)) == null) {
            return 1;
        }
        return num.intValue();
    }

    final zzfa.zza f(String str) {
        zzfa.zzd zzdVarH = h(str);
        if (zzdVarH == null || !zzdVarH.zzq()) {
            return null;
        }
        return zzdVarH.zzd();
    }

    final boolean g(String str, zzie.zza zzaVar) {
        zzt();
        zzv(str);
        if (this.zzm.get(str) != null && this.zzm.get(str).containsKey(zzaVar)) {
            return this.zzm.get(str).get(zzaVar).booleanValue();
        }
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzmm
    public final /* bridge */ /* synthetic */ zzna g_() {
        return super.g_();
    }

    protected final zzfa.zzd h(String str) {
        zzak();
        zzt();
        Preconditions.checkNotEmpty(str);
        zzv(str);
        return this.zzh.get(str);
    }

    final boolean i(String str, String str2) {
        Boolean bool;
        zzt();
        zzv(str);
        if ("ecommerce_purchase".equals(str2) || FirebaseAnalytics.Event.PURCHASE.equals(str2) || FirebaseAnalytics.Event.REFUND.equals(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzg.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    protected final String j(String str) {
        zzt();
        return this.zzl.get(str);
    }

    final boolean k(String str, String str2) {
        Boolean bool;
        zzt();
        zzv(str);
        if (r(str) && zzne.N(str2)) {
            return true;
        }
        if (s(str) && zzne.O(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zze.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    protected final String l(String str) {
        zzt();
        return this.zzk.get(str);
    }

    final String m(String str) {
        zzt();
        zzv(str);
        return this.zzj.get(str);
    }

    final Set n(String str) {
        zzt();
        zzv(str);
        return this.zzd.get(str);
    }

    final SortedSet o(String str) {
        TreeSet treeSet = new TreeSet();
        zzfa.zza zzaVarF = f(str);
        if (zzaVarF == null) {
            return treeSet;
        }
        Iterator<zzfa.zza.zzf> it = zzaVarF.zzc().iterator();
        while (it.hasNext()) {
            treeSet.add(it.next().zzb());
        }
        return treeSet;
    }

    protected final void p(String str) {
        zzt();
        this.zzk.put(str, null);
    }

    final void q(String str) {
        zzt();
        this.zzh.remove(str);
    }

    final boolean r(String str) {
        return "1".equals(zza(str, "measurement.upload.blacklist_internal"));
    }

    final boolean s(String str) {
        return "1".equals(zza(str, "measurement.upload.blacklist_public"));
    }

    final boolean t(String str) {
        zzt();
        zzv(str);
        return this.zzd.get(str) != null && this.zzd.get(str).contains("app_instance_id");
    }

    final boolean u(String str) {
        zzt();
        zzv(str);
        if (this.zzd.get(str) != null) {
            return this.zzd.get(str).contains("device_model") || this.zzd.get(str).contains(DeviceRequestsHelper.DEVICE_INFO_PARAM);
        }
        return false;
    }

    final boolean v(String str) {
        zzt();
        zzv(str);
        return this.zzd.get(str) != null && this.zzd.get(str).contains("enhanced_user_id");
    }

    final boolean w(String str) {
        zzt();
        zzv(str);
        return this.zzd.get(str) != null && this.zzd.get(str).contains("google_signals");
    }

    final boolean x(String str) {
        zzt();
        zzv(str);
        if (this.zzd.get(str) != null) {
            return this.zzd.get(str).contains(bc.f21426y) || this.zzd.get(str).contains(DeviceRequestsHelper.DEVICE_INFO_PARAM);
        }
        return false;
    }

    final boolean y(String str) {
        zzt();
        zzv(str);
        return this.zzd.get(str) != null && this.zzd.get(str).contains("user_id");
    }

    final long zza(String str) {
        String strZza = zza(str, "measurement.account.time_zone_offset_minutes");
        if (TextUtils.isEmpty(strZza)) {
            return 0L;
        }
        try {
            return Long.parseLong(strZza);
        } catch (NumberFormatException e2) {
            zzj().zzu().zza("Unable to parse timezone offset. appId", zzfs.d(str), e2);
            return 0L;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzml
    protected final boolean zzc() {
        return false;
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

    @WorkerThread
    private final void zzb(String str, zzfa.zzd zzdVar) {
        if (zzdVar.zzq()) {
            HashMap map = new HashMap();
            for (zzfa.zza.zzc zzcVar : zzdVar.zzd().zze()) {
                zzie.zza zzaVarZza = zza(zzcVar.zzc());
                zzie.zza zzaVarZza2 = zza(zzcVar.zzb());
                if (zzaVarZza != null && zzaVarZza2 != null) {
                    map.put(zzaVarZza, zzaVarZza2);
                }
            }
            this.zzn.put(str, map);
        }
    }

    @WorkerThread
    private final void zzc(final String str, zzfa.zzd zzdVar) {
        if (zzdVar.zza() == 0) {
            this.f13280c.remove(str);
            return;
        }
        zzj().zzp().zza("EES programs found", Integer.valueOf(zzdVar.zza()));
        zzfo.zzc zzcVar = zzdVar.zzn().get(0);
        try {
            com.google.android.gms.internal.measurement.zzb zzbVar = new com.google.android.gms.internal.measurement.zzb();
            zzbVar.zza("internal.remoteConfig", new Callable() { // from class: com.google.android.gms.measurement.internal.zzgr
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return new com.google.android.gms.internal.measurement.zzm("internal.remoteConfig", new zzgu(this.zza, str));
                }
            });
            zzbVar.zza("internal.appMetadata", new Callable() { // from class: com.google.android.gms.measurement.internal.zzgq
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    final zzgp zzgpVar = this.zza;
                    final String str2 = str;
                    return new com.google.android.gms.internal.measurement.zzx("internal.appMetadata", new Callable() { // from class: com.google.android.gms.measurement.internal.zzgo
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            zzgp zzgpVar2 = zzgpVar;
                            String str3 = str2;
                            zzh zzhVarZzd = zzgpVar2.zzh().zzd(str3);
                            HashMap map = new HashMap();
                            map.put(DispatchConstants.PLATFORM, "android");
                            map.put("package_name", str3);
                            map.put("gmp_version", 81010L);
                            if (zzhVarZzd != null) {
                                String strZzaa = zzhVarZzd.zzaa();
                                if (strZzaa != null) {
                                    map.put("app_version", strZzaa);
                                }
                                map.put("app_version_int", Long.valueOf(zzhVarZzd.zzc()));
                                map.put("dynamite_version", Long.valueOf(zzhVarZzd.zzm()));
                            }
                            return map;
                        }
                    });
                }
            });
            zzbVar.zza("internal.logger", new Callable() { // from class: com.google.android.gms.measurement.internal.zzgt
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return new com.google.android.gms.internal.measurement.zzr(this.zza.f13281d);
                }
            });
            zzbVar.zza(zzcVar);
            this.f13280c.put(str, zzbVar);
            zzj().zzp().zza("EES program loaded for appId, activities", str, Integer.valueOf(zzcVar.zza().zza()));
            Iterator<zzfo.zzb> it = zzcVar.zza().zzd().iterator();
            while (it.hasNext()) {
                zzj().zzp().zza("EES program activity", it.next().zzb());
            }
        } catch (com.google.android.gms.internal.measurement.zzc unused) {
            zzj().zzg().zza("Failed to load EES program. appId", str);
        }
    }

    final boolean zzk(String str) {
        zzt();
        zzfa.zzd zzdVarH = h(str);
        if (zzdVarH == null) {
            return false;
        }
        return zzdVarH.zzp();
    }

    public final boolean zzl(String str) {
        zzfa.zzd zzdVar;
        return (TextUtils.isEmpty(str) || (zzdVar = this.zzh.get(str)) == null || zzdVar.zza() == 0) ? false : true;
    }

    final boolean zzn(String str) {
        zzfa.zza zzaVarF = f(str);
        return zzaVarF == null || !zzaVarF.zzg() || zzaVarF.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    private static zzie.zza zza(zzfa.zza.zze zzeVar) {
        int i2 = zzgx.f13283b[zzeVar.ordinal()];
        if (i2 == 1) {
            return zzie.zza.AD_STORAGE;
        }
        if (i2 == 2) {
            return zzie.zza.ANALYTICS_STORAGE;
        }
        if (i2 == 3) {
            return zzie.zza.AD_USER_DATA;
        }
        if (i2 != 4) {
            return null;
        }
        return zzie.zza.AD_PERSONALIZATION;
    }

    @WorkerThread
    private final zzfa.zzd zza(String str, byte[] bArr) {
        if (bArr == null) {
            return zzfa.zzd.zzg();
        }
        try {
            zzfa.zzd zzdVar = (zzfa.zzd) ((com.google.android.gms.internal.measurement.zzlw) ((zzfa.zzd.zza) zzna.f(zzfa.zzd.zze(), bArr)).zzab());
            zzj().zzp().zza("Parsed config. version, gmp_app_id", zzdVar.zzs() ? Long.valueOf(zzdVar.zzc()) : null, zzdVar.zzr() ? zzdVar.zzh() : null);
            return zzdVar;
        } catch (com.google.android.gms.internal.measurement.zzme e2) {
            zzj().zzu().zza("Unable to merge remote config. appId", zzfs.d(str), e2);
            return zzfa.zzd.zzg();
        } catch (RuntimeException e3) {
            zzj().zzu().zza("Unable to merge remote config. appId", zzfs.d(str), e3);
            return zzfa.zzd.zzg();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzah
    @WorkerThread
    public final String zza(String str, String str2) {
        zzt();
        zzv(str);
        Map<String, String> map = this.zzc.get(str);
        if (map != null) {
            return map.get(str2);
        }
        return null;
    }

    private static Map<String, String> zza(zzfa.zzd zzdVar) {
        ArrayMap arrayMap = new ArrayMap();
        if (zzdVar != null) {
            for (zzfa.zzg zzgVar : zzdVar.zzo()) {
                arrayMap.put(zzgVar.zzb(), zzgVar.zzc());
            }
        }
        return arrayMap;
    }

    private final void zza(String str, zzfa.zzd.zza zzaVar) {
        HashSet hashSet = new HashSet();
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        if (zzaVar != null) {
            Iterator<zzfa.zzb> it = zzaVar.zze().iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().zzb());
            }
            for (int i2 = 0; i2 < zzaVar.zza(); i2++) {
                zzfa.zzc.zza zzaVarZzby = zzaVar.zza(i2).zzby();
                if (zzaVarZzby.zzb().isEmpty()) {
                    zzj().zzu().zza("EventConfig contained null event name");
                } else {
                    String strZzb = zzaVarZzby.zzb();
                    String strZzb2 = zzii.zzb(zzaVarZzby.zzb());
                    if (!TextUtils.isEmpty(strZzb2)) {
                        zzaVarZzby = zzaVarZzby.zza(strZzb2);
                        zzaVar.zza(i2, zzaVarZzby);
                    }
                    if (zzaVarZzby.zze() && zzaVarZzby.zzc()) {
                        arrayMap.put(strZzb, Boolean.TRUE);
                    }
                    if (zzaVarZzby.zzf() && zzaVarZzby.zzd()) {
                        arrayMap2.put(zzaVarZzby.zzb(), Boolean.TRUE);
                    }
                    if (zzaVarZzby.zzg()) {
                        if (zzaVarZzby.zza() >= 2 && zzaVarZzby.zza() <= 65535) {
                            arrayMap3.put(zzaVarZzby.zzb(), Integer.valueOf(zzaVarZzby.zza()));
                        } else {
                            zzj().zzu().zza("Invalid sampling rate. Event name, sample rate", zzaVarZzby.zzb(), Integer.valueOf(zzaVarZzby.zza()));
                        }
                    }
                }
            }
        }
        this.zzd.put(str, hashSet);
        this.zze.put(str, arrayMap);
        this.zzg.put(str, arrayMap2);
        this.zzi.put(str, arrayMap3);
    }

    @WorkerThread
    private final void zza(String str, zzfa.zzd zzdVar) {
        if (zzdVar.zzq()) {
            HashMap map = new HashMap();
            for (zzfa.zza.C0106zza c0106zza : zzdVar.zzd().zzd()) {
                boolean z2 = c0106zza.zzb() == zzfa.zza.zzd.GRANTED;
                zzie.zza zzaVarZza = zza(c0106zza.zzc());
                if (zzaVarZza != null) {
                    map.put(zzaVarZza, Boolean.valueOf(z2));
                }
            }
            this.zzm.put(str, map);
        }
    }
}
