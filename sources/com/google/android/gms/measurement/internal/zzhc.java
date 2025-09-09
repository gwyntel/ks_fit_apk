package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.internal.measurement.zzqr;
import com.google.android.gms.internal.measurement.zzri;
import com.google.android.gms.internal.measurement.zzsg;
import com.google.android.gms.measurement.internal.zzie;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.dataflow.qual.Pure;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class zzhc implements zzic {
    private static volatile zzhc zzb;

    /* renamed from: a, reason: collision with root package name */
    final long f13285a;
    private Boolean zzaa;
    private long zzab;
    private volatile Boolean zzac;

    @VisibleForTesting
    private Boolean zzad;

    @VisibleForTesting
    private Boolean zzae;
    private volatile boolean zzaf;
    private int zzag;
    private int zzah;
    private final Context zzc;
    private final String zzd;
    private final String zze;
    private final String zzf;
    private final boolean zzg;
    private final zzae zzh;
    private final zzaf zzi;
    private final zzge zzj;
    private final zzfs zzk;
    private final zzgz zzl;
    private final zzly zzm;
    private final zzne zzn;
    private final zzfn zzo;
    private final Clock zzp;
    private final zzki zzq;
    private final zzin zzr;
    private final zzb zzs;
    private final zzjz zzt;
    private final String zzu;
    private zzfl zzv;
    private zzkq zzw;
    private zzba zzx;
    private zzfm zzy;
    private boolean zzz = false;
    private AtomicInteger zzai = new AtomicInteger(0);

    private zzhc(zzil zzilVar) throws IllegalStateException {
        Bundle bundle;
        boolean z2 = false;
        Preconditions.checkNotNull(zzilVar);
        zzae zzaeVar = new zzae(zzilVar.f13287a);
        this.zzh = zzaeVar;
        zzfg.f13276a = zzaeVar;
        Context context = zzilVar.f13287a;
        this.zzc = context;
        this.zzd = zzilVar.f13288b;
        this.zze = zzilVar.f13289c;
        this.zzf = zzilVar.f13290d;
        this.zzg = zzilVar.f13294h;
        this.zzac = zzilVar.f13291e;
        this.zzu = zzilVar.f13296j;
        this.zzaf = true;
        com.google.android.gms.internal.measurement.zzdd zzddVar = zzilVar.f13293g;
        if (zzddVar != null && (bundle = zzddVar.zzg) != null) {
            Object obj = bundle.get("measurementEnabled");
            if (obj instanceof Boolean) {
                this.zzad = (Boolean) obj;
            }
            Object obj2 = zzddVar.zzg.get("measurementDeactivated");
            if (obj2 instanceof Boolean) {
                this.zzae = (Boolean) obj2;
            }
        }
        com.google.android.gms.internal.measurement.zzgl.zzb(context);
        Clock defaultClock = DefaultClock.getInstance();
        this.zzp = defaultClock;
        Long l2 = zzilVar.f13295i;
        this.f13285a = l2 != null ? l2.longValue() : defaultClock.currentTimeMillis();
        this.zzi = new zzaf(this);
        zzge zzgeVar = new zzge(this);
        zzgeVar.zzac();
        this.zzj = zzgeVar;
        zzfs zzfsVar = new zzfs(this);
        zzfsVar.zzac();
        this.zzk = zzfsVar;
        zzne zzneVar = new zzne(this);
        zzneVar.zzac();
        this.zzn = zzneVar;
        this.zzo = new zzfn(new zzio(zzilVar, this));
        this.zzs = new zzb(this);
        zzki zzkiVar = new zzki(this);
        zzkiVar.zzv();
        this.zzq = zzkiVar;
        zzin zzinVar = new zzin(this);
        zzinVar.zzv();
        this.zzr = zzinVar;
        zzly zzlyVar = new zzly(this);
        zzlyVar.zzv();
        this.zzm = zzlyVar;
        zzjz zzjzVar = new zzjz(this);
        zzjzVar.zzac();
        this.zzt = zzjzVar;
        zzgz zzgzVar = new zzgz(this);
        zzgzVar.zzac();
        this.zzl = zzgzVar;
        com.google.android.gms.internal.measurement.zzdd zzddVar2 = zzilVar.f13293g;
        if (zzddVar2 != null && zzddVar2.zzb != 0) {
            z2 = true;
        }
        boolean z3 = !z2;
        if (context.getApplicationContext() instanceof Application) {
            zzin zzinVarZzp = zzp();
            if (zzinVarZzp.zza().getApplicationContext() instanceof Application) {
                Application application = (Application) zzinVarZzp.zza().getApplicationContext();
                if (zzinVarZzp.f13297b == null) {
                    zzinVarZzp.f13297b = new zzjy(zzinVarZzp);
                }
                if (z3) {
                    application.unregisterActivityLifecycleCallbacks(zzinVarZzp.f13297b);
                    application.registerActivityLifecycleCallbacks(zzinVarZzp.f13297b);
                    zzinVarZzp.zzj().zzp().zza("Registered activity lifecycle callback");
                }
            }
        } else {
            zzj().zzu().zza("Application context is not an Application");
        }
        zzgzVar.zzb(new zzhh(this, zzilVar));
    }

    static /* synthetic */ void b(zzhc zzhcVar, zzil zzilVar) {
        zzhcVar.zzl().zzt();
        zzba zzbaVar = new zzba(zzhcVar);
        zzbaVar.zzac();
        zzhcVar.zzx = zzbaVar;
        zzfm zzfmVar = new zzfm(zzhcVar, zzilVar.f13292f);
        zzfmVar.zzv();
        zzhcVar.zzy = zzfmVar;
        zzfl zzflVar = new zzfl(zzhcVar);
        zzflVar.zzv();
        zzhcVar.zzv = zzflVar;
        zzkq zzkqVar = new zzkq(zzhcVar);
        zzkqVar.zzv();
        zzhcVar.zzw = zzkqVar;
        zzhcVar.zzn.zzad();
        zzhcVar.zzj.zzad();
        zzhcVar.zzy.zzw();
        zzhcVar.zzj().zzn().zza("App measurement initialized, version", 81010L);
        zzhcVar.zzj().zzn().zza("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String strF = zzfmVar.f();
        if (TextUtils.isEmpty(zzhcVar.zzd)) {
            if (zzhcVar.zzt().M(strF)) {
                zzhcVar.zzj().zzn().zza("Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.");
            } else {
                zzhcVar.zzj().zzn().zza("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app " + strF);
            }
        }
        zzhcVar.zzj().zzc().zza("Debug-level message logging enabled");
        if (zzhcVar.zzag != zzhcVar.zzai.get()) {
            zzhcVar.zzj().zzg().zza("Not all components initialized", Integer.valueOf(zzhcVar.zzag), Integer.valueOf(zzhcVar.zzai.get()));
        }
        zzhcVar.zzz = true;
    }

    @Pure
    private final zzjz zzai() {
        zza((zzid) this.zzt);
        return this.zzt;
    }

    protected final void a(com.google.android.gms.internal.measurement.zzdd zzddVar) throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzie zzieVar;
        Boolean boolZza;
        zzl().zzt();
        if (zzsg.zzb() && this.zzi.zza(zzbi.zzcg) && zzt().T()) {
            zzne zzneVarZzt = zzt();
            zzneVarZzt.zzt();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.google.android.gms.measurement.TRIGGERS_AVAILABLE");
            ContextCompat.registerReceiver(zzneVarZzt.zza(), new zzp(zzneVarZzt.f13286a), intentFilter, 2);
            zzneVarZzt.zzj().zzc().zza("Registered app receiver");
        }
        zzie zzieVarP = zzn().p();
        int iZza = zzieVarP.zza();
        Boolean boolG = this.zzi.g("google_analytics_default_allow_ad_storage");
        Boolean boolG2 = this.zzi.g("google_analytics_default_allow_analytics_storage");
        if (!(boolG == null && boolG2 == null) && zzn().d(-10)) {
            zzieVar = new zzie(boolG, boolG2, -10);
        } else {
            if (!TextUtils.isEmpty(zzh().zzae()) && (iZza == 0 || iZza == 30 || iZza == 10 || iZza == 30 || iZza == 30 || iZza == 40)) {
                zzp().zza(new zzie(null, null, -10), this.f13285a);
            } else if (TextUtils.isEmpty(zzh().zzae()) && zzddVar != null && zzddVar.zzg != null && zzn().d(30)) {
                zzieVar = zzie.zza(zzddVar.zzg, 30);
                if (!zzieVar.zzi()) {
                }
            }
            zzieVar = null;
        }
        if (zzieVar != null) {
            zzp().zza(zzieVar, this.f13285a);
            zzieVarP = zzieVar;
        }
        zzp().f(zzieVarP);
        if (zzql.zzb() && this.zzi.zza(zzbi.zzcl)) {
            int iZza2 = zzn().o().zza();
            Boolean boolG3 = this.zzi.g("google_analytics_default_allow_ad_user_data");
            if (boolG3 != null && zzie.zza(-10, iZza2)) {
                zzp().e(new zzay(boolG3, -10));
            } else if (TextUtils.isEmpty(zzh().zzae()) || !(iZza2 == 0 || iZza2 == 30)) {
                if (TextUtils.isEmpty(zzh().zzae()) && zzddVar != null && zzddVar.zzg != null && zzie.zza(30, iZza2)) {
                    zzay zzayVarZza = zzay.zza(zzddVar.zzg, 30);
                    if (zzayVarZza.zzh()) {
                        zzp().e(zzayVarZza);
                    }
                }
                if (TextUtils.isEmpty(zzh().zzae()) && zzddVar != null && zzddVar.zzg != null && zzn().zzh.zza() == null && (boolZza = zzay.zza(zzddVar.zzg)) != null) {
                    zzp().zza(zzddVar.zze, FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS, (Object) boolZza.toString(), false);
                }
            } else {
                zzp().e(new zzay((Boolean) null, -10));
            }
        }
        if (zzn().zzc.zza() == 0) {
            zzj().zzp().zza("Persisting first open", Long.valueOf(this.f13285a));
            zzn().zzc.zza(this.f13285a);
        }
        zzp().f13298c.c();
        if (f()) {
            if (!TextUtils.isEmpty(zzh().zzae()) || !TextUtils.isEmpty(zzh().e())) {
                zzt();
                if (zzne.x(zzh().zzae(), zzn().zzx(), zzh().e(), zzn().u())) {
                    zzj().zzn().zza("Rechecking which service to use due to a GMP App Id change");
                    zzn().zzy();
                    zzi().zzaa();
                    this.zzw.zzae();
                    this.zzw.zzad();
                    zzn().zzc.zza(this.f13285a);
                    zzn().zze.zza(null);
                }
                zzn().m(zzh().zzae());
                zzn().j(zzh().e());
            }
            if (!zzn().p().zza(zzie.zza.ANALYTICS_STORAGE)) {
                zzn().zze.zza(null);
            }
            zzp().k(zzn().zze.zza());
            if (zzqr.zzb() && this.zzi.zza(zzbi.zzbm) && !zzt().zzx() && !TextUtils.isEmpty(zzn().zzq.zza())) {
                zzj().zzu().zza("Remote config removed with active feature rollouts");
                zzn().zzq.zza(null);
            }
            if (!TextUtils.isEmpty(zzh().zzae()) || !TextUtils.isEmpty(zzh().e())) {
                boolean zZzac = zzac();
                if (!zzn().h() && !this.zzi.zzv()) {
                    zzn().k(!zZzac);
                }
                if (zZzac) {
                    zzp().zzaj();
                }
                zzs().f13305b.a();
                zzr().zza(new AtomicReference<>());
                zzr().zza(zzn().zzt.zza());
            }
        } else if (zzac()) {
            if (!zzt().L("android.permission.INTERNET")) {
                zzj().zzg().zza("App is missing INTERNET permission");
            }
            if (!zzt().L("android.permission.ACCESS_NETWORK_STATE")) {
                zzj().zzg().zza("App is missing ACCESS_NETWORK_STATE permission");
            }
            if (!Wrappers.packageManager(this.zzc).isCallerInstantApp() && !this.zzi.i()) {
                if (!zzne.o(this.zzc)) {
                    zzj().zzg().zza("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzne.p(this.zzc, false)) {
                    zzj().zzg().zza("AppMeasurementService not registered/enabled");
                }
            }
            zzj().zzg().zza("Uploading is not possible. App measurement disabled");
        }
        if (zzsg.zzb() && this.zzi.zza(zzbi.zzcg) && zzt().T()) {
            final zzin zzinVarZzp = zzp();
            zzinVarZzp.getClass();
            new Thread(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzhf
                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException {
                    zzinVarZzp.zzal();
                }
            }).start();
        }
        zzn().zzj.zza(true);
    }

    final /* synthetic */ void c(String str, int i2, Throwable th, byte[] bArr, Map map) throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((i2 != 200 && i2 != 204 && i2 != 304) || th != null) {
            zzj().zzu().zza("Network Request for Deferred Deep Link failed. response, exception", Integer.valueOf(i2), th);
            return;
        }
        zzn().zzo.zza(true);
        if (bArr == null || bArr.length == 0) {
            zzj().zzc().zza("Deferred Deep Link response empty.");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr));
            String strOptString = jSONObject.optString("deeplink", "");
            String strOptString2 = jSONObject.optString("gclid", "");
            String strOptString3 = jSONObject.optString("gbraid", "");
            double dOptDouble = jSONObject.optDouble(com.alipay.sdk.m.t.a.f9743k, 0.0d);
            if (TextUtils.isEmpty(strOptString)) {
                zzj().zzc().zza("Deferred Deep Link is empty.");
                return;
            }
            Bundle bundle = new Bundle();
            if (zzri.zzb() && this.zzi.zza(zzbi.zzcs)) {
                if (!zzt().zzi(strOptString)) {
                    zzj().zzu().zza("Deferred Deep Link validation failed. gclid, gbraid, deep link", strOptString2, strOptString3, strOptString);
                    return;
                }
                bundle.putString("gbraid", strOptString3);
            } else if (!zzt().zzi(strOptString)) {
                zzj().zzu().zza("Deferred Deep Link validation failed. gclid, deep link", strOptString2, strOptString);
                return;
            }
            bundle.putString("gclid", strOptString2);
            bundle.putString("_cis", "ddp");
            this.zzr.r("auto", Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundle);
            zzne zzneVarZzt = zzt();
            if (TextUtils.isEmpty(strOptString) || !zzneVarZzt.t(strOptString, dOptDouble)) {
                return;
            }
            zzneVarZzt.zza().sendBroadcast(new Intent("android.google.analytics.action.DEEPLINK_ACTION"));
        } catch (JSONException e2) {
            zzj().zzg().zza("Failed to parse the Deferred Deep Link response. exception", e2);
        }
    }

    final void d(boolean z2) {
        this.zzac = Boolean.valueOf(z2);
    }

    final void e() {
        this.zzag++;
    }

    protected final boolean f() {
        if (!this.zzz) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
        zzl().zzt();
        Boolean bool = this.zzaa;
        if (bool == null || this.zzab == 0 || (bool != null && !bool.booleanValue() && Math.abs(this.zzp.elapsedRealtime() - this.zzab) > 1000)) {
            this.zzab = this.zzp.elapsedRealtime();
            boolean z2 = true;
            Boolean boolValueOf = Boolean.valueOf(zzt().L("android.permission.INTERNET") && zzt().L("android.permission.ACCESS_NETWORK_STATE") && (Wrappers.packageManager(this.zzc).isCallerInstantApp() || this.zzi.i() || (zzne.o(this.zzc) && zzne.p(this.zzc, false))));
            this.zzaa = boolValueOf;
            if (boolValueOf.booleanValue()) {
                if (!zzt().v(zzh().zzae(), zzh().e()) && TextUtils.isEmpty(zzh().e())) {
                    z2 = false;
                }
                this.zzaa = Boolean.valueOf(z2);
            }
        }
        return this.zzaa.booleanValue();
    }

    final zzgz g() {
        return this.zzl;
    }

    final void h() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    final void i() {
        this.zzai.incrementAndGet();
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public final Context zza() {
        return this.zzc;
    }

    @WorkerThread
    public final boolean zzab() {
        return this.zzac != null && this.zzac.booleanValue();
    }

    @WorkerThread
    public final boolean zzac() {
        return zzc() == 0;
    }

    @WorkerThread
    public final boolean zzad() {
        zzl().zzt();
        return this.zzaf;
    }

    @Pure
    public final boolean zzae() {
        return TextUtils.isEmpty(this.zzd);
    }

    @Pure
    public final boolean zzag() {
        return this.zzg;
    }

    @WorkerThread
    public final boolean zzah() {
        zzl().zzt();
        zza((zzid) zzai());
        String strF = zzh().f();
        Pair pairZza = zzn().zza(strF);
        if (!this.zzi.zzp() || ((Boolean) pairZza.second).booleanValue() || TextUtils.isEmpty((CharSequence) pairZza.first)) {
            zzj().zzc().zza("ADID unavailable to retrieve Deferred Deep Link. Skipping");
            return false;
        }
        if (!zzai().zzc()) {
            zzj().zzu().zza("Network is not available for Deferred Deep Link request. Skipping");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        if (zzql.zzb() && this.zzi.zza(zzbi.zzcn)) {
            Boolean boolZzc = zzn().o().zzc();
            if (boolZzc != null && !boolZzc.booleanValue()) {
                zzj().zzc().zza("DMA consent not granted on client. Skipping");
                return false;
            }
            zzin zzinVarZzp = zzp();
            zzinVarZzp.zzt();
            zzam zzamVarR = zzinVarZzp.zzo().r();
            Bundle bundle = zzamVarR != null ? zzamVarR.zza : null;
            if (bundle == null) {
                int i2 = this.zzah;
                this.zzah = i2 + 1;
                boolean z2 = i2 < 10;
                zzj().zzc().zza("Failed to retrieve DMA consent from the service, " + (z2 ? "Retrying." : "Skipping.") + " retryCount", Integer.valueOf(this.zzah));
                return z2;
            }
            zzay zzayVarZza = zzay.zza(bundle, 100);
            if (!zzayVarZza.zzg()) {
                zzj().zzc().zza("DMA consent not granted on service. Skipping");
                return false;
            }
            zzie zzieVarZza = zzie.zza(bundle, 100);
            sb.append("&gcs=");
            sb.append(zzieVarZza.zzf());
            sb.append("&dma=");
            sb.append(zzayVarZza.zzd() == Boolean.FALSE ? 0 : 1);
            if (!TextUtils.isEmpty(zzayVarZza.zze())) {
                sb.append("&dma_cps=");
                sb.append(zzayVarZza.zze());
            }
            int i3 = zzay.zza(bundle) == Boolean.TRUE ? 0 : 1;
            sb.append("&npa=");
            sb.append(i3);
            zzj().zzp().zza("Consent query parameters to Bow", sb);
        }
        zzne zzneVarZzt = zzt();
        zzh();
        URL urlZza = zzneVarZzt.zza(81010L, strF, (String) pairZza.first, zzn().zzp.zza() - 1, sb.toString());
        if (urlZza != null) {
            zzjz zzjzVarZzai = zzai();
            zzkc zzkcVar = new zzkc() { // from class: com.google.android.gms.measurement.internal.zzhe
                @Override // com.google.android.gms.measurement.internal.zzkc
                public final void zza(String str, int i4, Throwable th, byte[] bArr, Map map) throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    this.zza.c(str, i4, th, bArr, map);
                }
            };
            zzjzVarZzai.zzt();
            zzjzVarZzai.zzab();
            Preconditions.checkNotNull(urlZza);
            Preconditions.checkNotNull(zzkcVar);
            zzjzVarZzai.zzl().zza(new zzkb(zzjzVarZzai, strF, urlZza, null, null, zzkcVar));
        }
        return false;
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public final Clock zzb() {
        return this.zzp;
    }

    @WorkerThread
    public final int zzc() {
        zzl().zzt();
        if (this.zzi.zzv()) {
            return 1;
        }
        Boolean bool = this.zzae;
        if (bool != null && bool.booleanValue()) {
            return 2;
        }
        if (!zzad()) {
            return 8;
        }
        Boolean boolS = zzn().s();
        if (boolS != null) {
            return boolS.booleanValue() ? 0 : 3;
        }
        Boolean boolG = this.zzi.g("firebase_analytics_collection_enabled");
        if (boolG != null) {
            return boolG.booleanValue() ? 0 : 4;
        }
        Boolean bool2 = this.zzad;
        return bool2 != null ? bool2.booleanValue() ? 0 : 5 : (this.zzac == null || this.zzac.booleanValue()) ? 0 : 7;
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public final zzae zzd() {
        return this.zzh;
    }

    @Pure
    public final zzb zze() {
        zzb zzbVar = this.zzs;
        if (zzbVar != null) {
            return zzbVar;
        }
        throw new IllegalStateException("Component not created");
    }

    @Pure
    public final zzaf zzf() {
        return this.zzi;
    }

    @Pure
    public final zzba zzg() {
        zza((zzid) this.zzx);
        return this.zzx;
    }

    @Pure
    public final zzfm zzh() {
        zza((zze) this.zzy);
        return this.zzy;
    }

    @Pure
    public final zzfl zzi() {
        zza((zze) this.zzv);
        return this.zzv;
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public final zzfs zzj() {
        zza((zzid) this.zzk);
        return this.zzk;
    }

    @Pure
    public final zzfn zzk() {
        return this.zzo;
    }

    @Override // com.google.android.gms.measurement.internal.zzic
    @Pure
    public final zzgz zzl() {
        zza((zzid) this.zzl);
        return this.zzl;
    }

    public final zzfs zzm() {
        zzfs zzfsVar = this.zzk;
        if (zzfsVar == null || !zzfsVar.zzae()) {
            return null;
        }
        return this.zzk;
    }

    @Pure
    public final zzge zzn() {
        zza((zzia) this.zzj);
        return this.zzj;
    }

    @Pure
    public final zzin zzp() {
        zza((zze) this.zzr);
        return this.zzr;
    }

    @Pure
    public final zzki zzq() {
        zza((zze) this.zzq);
        return this.zzq;
    }

    @Pure
    public final zzkq zzr() {
        zza((zze) this.zzw);
        return this.zzw;
    }

    @Pure
    public final zzly zzs() {
        zza((zze) this.zzm);
        return this.zzm;
    }

    @Pure
    public final zzne zzt() {
        zza((zzia) this.zzn);
        return this.zzn;
    }

    @Pure
    public final String zzu() {
        return this.zzd;
    }

    @Pure
    public final String zzv() {
        return this.zze;
    }

    @Pure
    public final String zzw() {
        return this.zzf;
    }

    @Pure
    public final String zzx() {
        return this.zzu;
    }

    public static zzhc zza(Context context, com.google.android.gms.internal.measurement.zzdd zzddVar, Long l2) {
        Bundle bundle;
        if (zzddVar != null && (zzddVar.zze == null || zzddVar.zzf == null)) {
            zzddVar = new com.google.android.gms.internal.measurement.zzdd(zzddVar.zza, zzddVar.zzb, zzddVar.zzc, zzddVar.zzd, null, null, zzddVar.zzg, null);
        }
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzb == null) {
            synchronized (zzhc.class) {
                try {
                    if (zzb == null) {
                        zzb = new zzhc(new zzil(context, zzddVar, l2));
                    }
                } finally {
                }
            }
        } else if (zzddVar != null && (bundle = zzddVar.zzg) != null && bundle.containsKey("dataCollectionDefaultEnabled")) {
            Preconditions.checkNotNull(zzb);
            zzb.d(zzddVar.zzg.getBoolean("dataCollectionDefaultEnabled"));
        }
        Preconditions.checkNotNull(zzb);
        return zzb;
    }

    @WorkerThread
    public final void zzb(boolean z2) {
        zzl().zzt();
        this.zzaf = z2;
    }

    private static void zza(zzia zziaVar) {
        if (zziaVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zze zzeVar) {
        if (zzeVar != null) {
            if (zzeVar.zzy()) {
                return;
            }
            throw new IllegalStateException("Component not initialized: " + String.valueOf(zzeVar.getClass()));
        }
        throw new IllegalStateException("Component not created");
    }

    private static void zza(zzid zzidVar) {
        if (zzidVar != null) {
            if (zzidVar.zzae()) {
                return;
            }
            throw new IllegalStateException("Component not initialized: " + String.valueOf(zzidVar.getClass()));
        }
        throw new IllegalStateException("Component not created");
    }
}
