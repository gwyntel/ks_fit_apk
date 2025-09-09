package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzqw;
import com.google.android.gms.measurement.internal.zzie;

/* loaded from: classes3.dex */
final class zzge extends zzid {

    /* renamed from: b, reason: collision with root package name */
    static final Pair f13277b = new Pair("", 0L);
    public zzgi zzb;
    public final zzgf zzc;
    public final zzgf zzd;
    public final zzgk zze;
    public final zzgf zzf;
    public final zzgd zzg;
    public final zzgk zzh;
    public final zzgg zzi;
    public final zzgd zzj;
    public final zzgf zzk;
    public final zzgf zzl;
    public boolean zzm;
    public zzgd zzn;
    public zzgd zzo;
    public zzgf zzp;
    public final zzgk zzq;
    public final zzgk zzr;
    public final zzgf zzs;
    public final zzgg zzt;
    private SharedPreferences zzv;
    private String zzw;
    private boolean zzx;
    private long zzy;

    zzge(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzf = new zzgf(this, "session_timeout", 1800000L);
        this.zzg = new zzgd(this, "start_new_session", true);
        this.zzk = new zzgf(this, "last_pause_time", 0L);
        this.zzl = new zzgf(this, "session_id", 0L);
        this.zzh = new zzgk(this, "non_personalized_ads", null);
        this.zzi = new zzgg(this, "last_received_uri_timestamps_by_source", null);
        this.zzj = new zzgd(this, "allow_remote_dynamite", false);
        this.zzc = new zzgf(this, "first_open_time", 0L);
        this.zzd = new zzgf(this, "app_install_time", 0L);
        this.zze = new zzgk(this, "app_instance_id", null);
        this.zzn = new zzgd(this, "app_backgrounded", false);
        this.zzo = new zzgd(this, "deep_link_retrieval_complete", false);
        this.zzp = new zzgf(this, "deep_link_retrieval_attempts", 0L);
        this.zzq = new zzgk(this, "firebase_feature_rollouts", null);
        this.zzr = new zzgk(this, "deferred_attribution_cache", null);
        this.zzs = new zzgf(this, "deferred_attribution_cache_timestamp", 0L);
        this.zzt = new zzgg(this, "default_event_parameters", null);
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final boolean a() {
        return true;
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final void b() {
        SharedPreferences sharedPreferences = zza().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzv = sharedPreferences;
        boolean z2 = sharedPreferences.getBoolean("has_been_opened", false);
        this.zzm = z2;
        if (!z2) {
            SharedPreferences.Editor editorEdit = this.zzv.edit();
            editorEdit.putBoolean("has_been_opened", true);
            editorEdit.apply();
        }
        this.zzb = new zzgi(this, "health_monitor", Math.max(0L, zzbi.zzc.zza(null).longValue()));
    }

    final void c(boolean z2) {
        zzt();
        SharedPreferences.Editor editorEdit = l().edit();
        editorEdit.putBoolean("use_service", z2);
        editorEdit.apply();
    }

    final boolean d(int i2) {
        return zzie.zza(i2, l().getInt("consent_source", 100));
    }

    final boolean e(long j2) {
        return j2 - this.zzf.zza() > this.zzk.zza();
    }

    final boolean f(zzay zzayVar) {
        zzt();
        if (!zzie.zza(zzayVar.zza(), o().zza())) {
            return false;
        }
        SharedPreferences.Editor editorEdit = l().edit();
        editorEdit.putString("dma_consent_settings", zzayVar.zzf());
        editorEdit.apply();
        return true;
    }

    final boolean g(zzie zzieVar) {
        zzt();
        int iZza = zzieVar.zza();
        if (!d(iZza)) {
            return false;
        }
        SharedPreferences.Editor editorEdit = l().edit();
        editorEdit.putString("consent_settings", zzieVar.zze());
        editorEdit.putInt("consent_source", iZza);
        editorEdit.apply();
        return true;
    }

    final boolean h() {
        SharedPreferences sharedPreferences = this.zzv;
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.contains("deferred_analytics_collection");
    }

    final void i(Boolean bool) {
        zzt();
        SharedPreferences.Editor editorEdit = l().edit();
        if (bool != null) {
            editorEdit.putBoolean("measurement_enabled_from_api", bool.booleanValue());
        } else {
            editorEdit.remove("measurement_enabled_from_api");
        }
        editorEdit.apply();
    }

    final void j(String str) {
        zzt();
        SharedPreferences.Editor editorEdit = l().edit();
        editorEdit.putString("admob_app_id", str);
        editorEdit.apply();
    }

    final void k(boolean z2) {
        zzt();
        zzj().zzp().zza("App measurement setting deferred collection", Boolean.valueOf(z2));
        SharedPreferences.Editor editorEdit = l().edit();
        editorEdit.putBoolean("deferred_analytics_collection", z2);
        editorEdit.apply();
    }

    protected final SharedPreferences l() {
        zzt();
        zzab();
        Preconditions.checkNotNull(this.zzv);
        return this.zzv;
    }

    final void m(String str) {
        zzt();
        SharedPreferences.Editor editorEdit = l().edit();
        editorEdit.putString("gmp_app_id", str);
        editorEdit.apply();
    }

    final SparseArray n() {
        Bundle bundleZza = this.zzi.zza();
        if (bundleZza == null) {
            return new SparseArray();
        }
        int[] intArray = bundleZza.getIntArray("uriSources");
        long[] longArray = bundleZza.getLongArray("uriTimestamps");
        if (intArray == null || longArray == null) {
            return new SparseArray();
        }
        if (intArray.length != longArray.length) {
            zzj().zzg().zza("Trigger URI source and timestamp array lengths do not match");
            return new SparseArray();
        }
        SparseArray sparseArray = new SparseArray();
        for (int i2 = 0; i2 < intArray.length; i2++) {
            sparseArray.put(intArray[i2], Long.valueOf(longArray[i2]));
        }
        return sparseArray;
    }

    final zzay o() {
        zzt();
        return zzay.zza(l().getString("dma_consent_settings", null));
    }

    final zzie p() {
        zzt();
        return zzie.zza(l().getString("consent_settings", "G1"), l().getInt("consent_source", 100));
    }

    final Boolean q() {
        zzt();
        if (l().contains("use_service")) {
            return Boolean.valueOf(l().getBoolean("use_service", false));
        }
        return null;
    }

    final Boolean r() {
        zzt();
        if (l().contains("measurement_enabled_from_api")) {
            return Boolean.valueOf(l().getBoolean("measurement_enabled_from_api", true));
        }
        return null;
    }

    final Boolean s() {
        zzt();
        if (l().contains("measurement_enabled")) {
            return Boolean.valueOf(l().getBoolean("measurement_enabled", true));
        }
        return null;
    }

    protected final String t() {
        zzt();
        String string = l().getString("previous_os_version", null);
        zzf().zzab();
        String str = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty(str) && !str.equals(string)) {
            SharedPreferences.Editor editorEdit = l().edit();
            editorEdit.putString("previous_os_version", str);
            editorEdit.apply();
        }
        return string;
    }

    final String u() {
        zzt();
        return l().getString("admob_app_id", null);
    }

    final Pair zza(String str) {
        zzt();
        if (zzqw.zzb() && zze().zza(zzbi.zzck) && !p().zza(zzie.zza.AD_STORAGE)) {
            return new Pair("", Boolean.FALSE);
        }
        long jElapsedRealtime = zzb().elapsedRealtime();
        if (this.zzw != null && jElapsedRealtime < this.zzy) {
            return new Pair(this.zzw, Boolean.valueOf(this.zzx));
        }
        this.zzy = jElapsedRealtime + zze().f(str);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(zza());
            this.zzw = "";
            String id = advertisingIdInfo.getId();
            if (id != null) {
                this.zzw = id;
            }
            this.zzx = advertisingIdInfo.isLimitAdTrackingEnabled();
        } catch (Exception e2) {
            zzj().zzc().zza("Unable to get advertising id", e2);
            this.zzw = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(this.zzw, Boolean.valueOf(this.zzx));
    }

    final String zzx() {
        zzt();
        return l().getString("gmp_app_id", null);
    }

    final void zzy() {
        zzt();
        Boolean boolS = s();
        SharedPreferences.Editor editorEdit = l().edit();
        editorEdit.clear();
        editorEdit.apply();
        if (boolS != null) {
            zza(boolS);
        }
    }

    final void zza(Boolean bool) {
        zzt();
        SharedPreferences.Editor editorEdit = l().edit();
        if (bool != null) {
            editorEdit.putBoolean("measurement_enabled", bool.booleanValue());
        } else {
            editorEdit.remove("measurement_enabled");
        }
        editorEdit.apply();
    }
}
