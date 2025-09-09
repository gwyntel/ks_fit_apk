package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.util.Clock;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzlp extends zzml {
    public final zzgf zza;
    public final zzgf zzb;
    public final zzgf zzc;
    public final zzgf zzd;
    public final zzgf zze;
    private final Map<String, zzls> zzg;

    zzlp(zzmq zzmqVar) {
        super(zzmqVar);
        this.zzg = new HashMap();
        zzge zzgeVarZzk = zzk();
        zzgeVarZzk.getClass();
        this.zza = new zzgf(zzgeVarZzk, "last_delete_stale", 0L);
        zzge zzgeVarZzk2 = zzk();
        zzgeVarZzk2.getClass();
        this.zzb = new zzgf(zzgeVarZzk2, "backoff", 0L);
        zzge zzgeVarZzk3 = zzk();
        zzgeVarZzk3.getClass();
        this.zzc = new zzgf(zzgeVarZzk3, "last_upload", 0L);
        zzge zzgeVarZzk4 = zzk();
        zzgeVarZzk4.getClass();
        this.zzd = new zzgf(zzgeVarZzk4, "last_upload_attempt", 0L);
        zzge zzgeVarZzk5 = zzk();
        zzgeVarZzk5.getClass();
        this.zze = new zzgf(zzgeVarZzk5, "midnight_offset", 0L);
    }

    final Pair a(String str, zzie zzieVar) {
        return zzieVar.zzg() ? zza(str) : new Pair("", Boolean.FALSE);
    }

    final String b(String str, boolean z2) throws NoSuchAlgorithmException {
        zzt();
        String str2 = z2 ? (String) zza(str).first : "00000000-0000-0000-0000-000000000000";
        MessageDigest messageDigestR = zzne.R();
        if (messageDigestR == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestR.digest(str2.getBytes())));
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
    @Deprecated
    private final Pair<String, Boolean> zza(String str) {
        zzls zzlsVar;
        AdvertisingIdClient.Info advertisingIdInfo;
        zzt();
        long jElapsedRealtime = zzb().elapsedRealtime();
        zzls zzlsVar2 = this.zzg.get(str);
        if (zzlsVar2 != null && jElapsedRealtime < zzlsVar2.f13304c) {
            return new Pair<>(zzlsVar2.f13302a, Boolean.valueOf(zzlsVar2.f13303b));
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        long jF = zze().f(str) + jElapsedRealtime;
        try {
            long jZzc = zze().zzc(str, zzbi.zzb);
            if (jZzc > 0) {
                try {
                    advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(zza());
                } catch (PackageManager.NameNotFoundException unused) {
                    if (zzlsVar2 != null && jElapsedRealtime < zzlsVar2.f13304c + jZzc) {
                        return new Pair<>(zzlsVar2.f13302a, Boolean.valueOf(zzlsVar2.f13303b));
                    }
                    advertisingIdInfo = null;
                }
            } else {
                advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(zza());
            }
        } catch (Exception e2) {
            zzj().zzc().zza("Unable to get advertising id", e2);
            zzlsVar = new zzls("", false, jF);
        }
        if (advertisingIdInfo == null) {
            return new Pair<>("00000000-0000-0000-0000-000000000000", Boolean.FALSE);
        }
        String id = advertisingIdInfo.getId();
        zzlsVar = id != null ? new zzls(id, advertisingIdInfo.isLimitAdTrackingEnabled(), jF) : new zzls("", advertisingIdInfo.isLimitAdTrackingEnabled(), jF);
        this.zzg.put(str, zzlsVar);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(zzlsVar.f13302a, Boolean.valueOf(zzlsVar.f13303b));
    }
}
