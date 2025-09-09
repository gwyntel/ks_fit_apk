package com.google.android.gms.measurement.internal;

import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.BinderThread;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.internal.measurement.zzql;
import com.google.firebase.messaging.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes3.dex */
public final class zzhg extends zzfk {
    private final zzmq zza;
    private Boolean zzb;
    private String zzc;

    public zzhg(zzmq zzmqVar) {
        this(zzmqVar, null);
    }

    private final void zzd(zzbg zzbgVar, zzo zzoVar) {
        this.zza.z();
        this.zza.f(zzbgVar, zzoVar);
    }

    final /* synthetic */ void c(String str, Bundle bundle) {
        this.zza.zzf().f(str, bundle);
    }

    final zzbg d(zzbg zzbgVar, zzo zzoVar) {
        zzbb zzbbVar;
        if (Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(zzbgVar.zza) && (zzbbVar = zzbgVar.zzb) != null && zzbbVar.zza() != 0) {
            String strE = zzbgVar.zzb.e("_cis");
            if ("referrer broadcast".equals(strE) || "referrer API".equals(strE)) {
                this.zza.zzj().zzn().zza("Event has been filtered ", zzbgVar.toString());
                return new zzbg("_cmpx", zzbgVar.zzb, zzbgVar.zzc, zzbgVar.zzd);
            }
        }
        return zzbgVar;
    }

    final void e(zzbg zzbgVar, zzo zzoVar) {
        boolean zZza;
        if (!this.zza.zzi().zzl(zzoVar.zza)) {
            zzd(zzbgVar, zzoVar);
            return;
        }
        this.zza.zzj().zzp().zza("EES config found for", zzoVar.zza);
        zzgp zzgpVarZzi = this.zza.zzi();
        String str = zzoVar.zza;
        com.google.android.gms.internal.measurement.zzb zzbVar = TextUtils.isEmpty(str) ? null : (com.google.android.gms.internal.measurement.zzb) zzgpVarZzi.f13280c.get(str);
        if (zzbVar == null) {
            this.zza.zzj().zzp().zza("EES not loaded for", zzoVar.zza);
            zzd(zzbgVar, zzoVar);
            return;
        }
        try {
            Map mapN = this.zza.zzp().n(zzbgVar.zzb.zzb(), true);
            String strZza = zzii.zza(zzbgVar.zza);
            if (strZza == null) {
                strZza = zzbgVar.zza;
            }
            zZza = zzbVar.zza(new com.google.android.gms.internal.measurement.zzad(strZza, zzbgVar.zzd, mapN));
        } catch (com.google.android.gms.internal.measurement.zzc unused) {
            this.zza.zzj().zzg().zza("EES error. appId, eventName", zzoVar.zzb, zzbgVar.zza);
            zZza = false;
        }
        if (!zZza) {
            this.zza.zzj().zzp().zza("EES was not applied to event", zzbgVar.zza);
            zzd(zzbgVar, zzoVar);
            return;
        }
        if (zzbVar.zzd()) {
            this.zza.zzj().zzp().zza("EES edited event", zzbgVar.zza);
            zzd(this.zza.zzp().g(zzbVar.zza().zzb()), zzoVar);
        } else {
            zzd(zzbgVar, zzoVar);
        }
        if (zzbVar.zzc()) {
            for (com.google.android.gms.internal.measurement.zzad zzadVar : zzbVar.zza().zzc()) {
                this.zza.zzj().zzp().zza("EES logging created event", zzadVar.zzb());
                zzd(this.zza.zzp().g(zzadVar), zzoVar);
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final zzam zza(zzo zzoVar) throws IllegalStateException {
        zzb(zzoVar, false);
        Preconditions.checkNotEmpty(zzoVar.zza);
        if (!zzql.zzb()) {
            return new zzam(null);
        }
        try {
            return (zzam) this.zza.zzl().zzb(new zzhv(this, zzoVar)).get(10000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e2) {
            this.zza.zzj().zzg().zza("Failed to get consent. appId", zzfs.d(zzoVar.zza), e2);
            return new zzam(null);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final String zzb(zzo zzoVar) {
        zzb(zzoVar, false);
        return this.zza.t(zzoVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zzc(zzo zzoVar) throws IllegalStateException {
        zzb(zzoVar, false);
        zza(new zzhl(this, zzoVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zze(zzo zzoVar) throws IllegalStateException {
        Preconditions.checkNotEmpty(zzoVar.zza);
        Preconditions.checkNotNull(zzoVar.zzt);
        zzhs zzhsVar = new zzhs(this, zzoVar);
        Preconditions.checkNotNull(zzhsVar);
        if (this.zza.zzl().zzg()) {
            zzhsVar.run();
        } else {
            this.zza.zzl().zzc(zzhsVar);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zzf(zzo zzoVar) throws IllegalStateException {
        zzb(zzoVar, false);
        zza(new zzhi(this, zzoVar));
    }

    private zzhg(zzmq zzmqVar, String str) {
        Preconditions.checkNotNull(zzmqVar);
        this.zza = zzmqVar;
        this.zzc = null;
    }

    @BinderThread
    private final void zzb(zzo zzoVar, boolean z2) {
        Preconditions.checkNotNull(zzoVar);
        Preconditions.checkNotEmpty(zzoVar.zza);
        zza(zzoVar.zza, false);
        this.zza.zzq().v(zzoVar.zzb, zzoVar.zzp);
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zzd(zzo zzoVar) throws IllegalStateException {
        Preconditions.checkNotEmpty(zzoVar.zza);
        zza(zzoVar.zza, false);
        zza(new zzht(this, zzoVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final List<zzmi> zza(zzo zzoVar, Bundle bundle) {
        zzb(zzoVar, false);
        Preconditions.checkNotNull(zzoVar.zza);
        try {
            return (List) this.zza.zzl().zza(new zzhy(this, zzoVar, bundle)).get();
        } catch (InterruptedException | ExecutionException e2) {
            this.zza.zzj().zzg().zza("Failed to get trigger URIs. appId", zzfs.d(zzoVar.zza), e2);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final List<zzmz> zza(zzo zzoVar, boolean z2) {
        zzb(zzoVar, false);
        String str = zzoVar.zza;
        Preconditions.checkNotNull(str);
        try {
            List<zznb> list = (List) this.zza.zzl().zza(new zzib(this, str)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zznb zznbVar : list) {
                if (z2 || !zzne.N(zznbVar.f13324c)) {
                    arrayList.add(new zzmz(zznbVar));
                }
            }
            return arrayList;
        } catch (InterruptedException e2) {
            e = e2;
            this.zza.zzj().zzg().zza("Failed to get user properties. appId", zzfs.d(zzoVar.zza), e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            this.zza.zzj().zzg().zza("Failed to get user properties. appId", zzfs.d(zzoVar.zza), e);
            return null;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final List<zzad> zza(String str, String str2, zzo zzoVar) {
        zzb(zzoVar, false);
        String str3 = zzoVar.zza;
        Preconditions.checkNotNull(str3);
        try {
            return (List) this.zza.zzl().zza(new zzhr(this, str3, str, str2)).get();
        } catch (InterruptedException | ExecutionException e2) {
            this.zza.zzj().zzg().zza("Failed to get conditional user properties", e2);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final List<zzad> zza(String str, String str2, String str3) {
        zza(str, true);
        try {
            return (List) this.zza.zzl().zza(new zzhq(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e2) {
            this.zza.zzj().zzg().zza("Failed to get conditional user properties as", e2);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final List<zzmz> zza(String str, String str2, boolean z2, zzo zzoVar) {
        zzb(zzoVar, false);
        String str3 = zzoVar.zza;
        Preconditions.checkNotNull(str3);
        try {
            List<zznb> list = (List) this.zza.zzl().zza(new zzhp(this, str3, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zznb zznbVar : list) {
                if (z2 || !zzne.N(zznbVar.f13324c)) {
                    arrayList.add(new zzmz(zznbVar));
                }
            }
            return arrayList;
        } catch (InterruptedException e2) {
            e = e2;
            this.zza.zzj().zzg().zza("Failed to query user properties. appId", zzfs.d(zzoVar.zza), e);
            return Collections.emptyList();
        } catch (ExecutionException e3) {
            e = e3;
            this.zza.zzj().zzg().zza("Failed to query user properties. appId", zzfs.d(zzoVar.zza), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final List<zzmz> zza(String str, String str2, String str3, boolean z2) {
        zza(str, true);
        try {
            List<zznb> list = (List) this.zza.zzl().zza(new zzho(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zznb zznbVar : list) {
                if (z2 || !zzne.N(zznbVar.f13324c)) {
                    arrayList.add(new zzmz(zznbVar));
                }
            }
            return arrayList;
        } catch (InterruptedException e2) {
            e = e2;
            this.zza.zzj().zzg().zza("Failed to get user properties as. appId", zzfs.d(str), e);
            return Collections.emptyList();
        } catch (ExecutionException e3) {
            e = e3;
            this.zza.zzj().zzg().zza("Failed to get user properties as. appId", zzfs.d(str), e);
            return Collections.emptyList();
        }
    }

    @BinderThread
    private final void zza(String str, boolean z2) {
        if (!TextUtils.isEmpty(str)) {
            if (z2) {
                try {
                    if (this.zzb == null) {
                        this.zzb = Boolean.valueOf("com.google.android.gms".equals(this.zzc) || UidVerifier.isGooglePlayServicesUid(this.zza.zza(), Binder.getCallingUid()) || GoogleSignatureVerifier.getInstance(this.zza.zza()).isUidGoogleSigned(Binder.getCallingUid()));
                    }
                    if (this.zzb.booleanValue()) {
                        return;
                    }
                } catch (SecurityException e2) {
                    this.zza.zzj().zzg().zza("Measurement Service called with invalid calling package. appId", zzfs.d(str));
                    throw e2;
                }
            }
            if (this.zzc == null && GooglePlayServicesUtilLight.uidHasPackageName(this.zza.zza(), Binder.getCallingUid(), str)) {
                this.zzc = str;
            }
            if (str.equals(this.zzc)) {
                return;
            } else {
                throw new SecurityException(String.format("Unknown calling package name '%s'.", str));
            }
        }
        this.zza.zzj().zzg().zza("Measurement Service called without app package");
        throw new SecurityException("Measurement Service called without app package");
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zza(zzbg zzbgVar, zzo zzoVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzbgVar);
        zzb(zzoVar, false);
        zza(new zzhu(this, zzbgVar, zzoVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zza(zzbg zzbgVar, String str, String str2) throws IllegalStateException {
        Preconditions.checkNotNull(zzbgVar);
        Preconditions.checkNotEmpty(str);
        zza(str, true);
        zza(new zzhx(this, zzbgVar, str));
    }

    @VisibleForTesting
    private final void zza(Runnable runnable) throws IllegalStateException {
        Preconditions.checkNotNull(runnable);
        if (this.zza.zzl().zzg()) {
            runnable.run();
        } else {
            this.zza.zzl().zzb(runnable);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zza(zzad zzadVar, zzo zzoVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzadVar);
        Preconditions.checkNotNull(zzadVar.zzc);
        zzb(zzoVar, false);
        zzad zzadVar2 = new zzad(zzadVar);
        zzadVar2.zza = zzoVar.zza;
        zza(new zzhn(this, zzadVar2, zzoVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zza(zzad zzadVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzadVar);
        Preconditions.checkNotNull(zzadVar.zzc);
        Preconditions.checkNotEmpty(zzadVar.zza);
        zza(zzadVar.zza, true);
        zza(new zzhm(this, new zzad(zzadVar)));
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zza(long j2, String str, String str2, String str3) throws IllegalStateException {
        zza(new zzhk(this, str2, str3, str, j2));
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zza(final Bundle bundle, zzo zzoVar) throws IllegalStateException {
        zzb(zzoVar, false);
        final String str = zzoVar.zza;
        Preconditions.checkNotNull(str);
        zza(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzhj
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.c(str, bundle);
            }
        });
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final void zza(zzmz zzmzVar, zzo zzoVar) throws IllegalStateException {
        Preconditions.checkNotNull(zzmzVar);
        zzb(zzoVar, false);
        zza(new zzhz(this, zzmzVar, zzoVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzfh
    @BinderThread
    public final byte[] zza(zzbg zzbgVar, String str) throws IllegalStateException {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzbgVar);
        zza(str, true);
        this.zza.zzj().zzc().zza("Log and bundle. event", this.zza.zzg().c(zzbgVar.zza));
        long jNanoTime = this.zza.zzb().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zza.zzl().zzb(new zzhw(this, zzbgVar, str)).get();
            if (bArr == null) {
                this.zza.zzj().zzg().zza("Log and bundle returned null. appId", zzfs.d(str));
                bArr = new byte[0];
            }
            this.zza.zzj().zzc().zza("Log and bundle processed. event, size, time_ms", this.zza.zzg().c(zzbgVar.zza), Integer.valueOf(bArr.length), Long.valueOf((this.zza.zzb().nanoTime() / 1000000) - jNanoTime));
            return bArr;
        } catch (InterruptedException e2) {
            e = e2;
            this.zza.zzj().zzg().zza("Failed to log and bundle. appId, event, error", zzfs.d(str), this.zza.zzg().c(zzbgVar.zza), e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            this.zza.zzj().zzg().zza("Failed to log and bundle. appId, event, error", zzfs.d(str), this.zza.zzg().c(zzbgVar.zza), e);
            return null;
        }
    }
}
