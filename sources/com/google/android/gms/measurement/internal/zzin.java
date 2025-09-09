package com.google.android.gms.measurement.internal;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.GuardedBy;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.core.app.NotificationCompat;
import androidx.media3.common.C;
import androidx.privacysandbox.ads.adservices.java.measurement.MeasurementManagerFutures;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.internal.measurement.zzql;
import com.google.android.gms.internal.measurement.zzqr;
import com.google.android.gms.internal.measurement.zzrd;
import com.google.android.gms.internal.measurement.zzri;
import com.google.android.gms.internal.measurement.zzsg;
import com.google.android.gms.internal.measurement.zzss;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.gms.measurement.internal.zzie;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.Constants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import kotlin.Unit;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzin extends zze {

    /* renamed from: b, reason: collision with root package name */
    protected zzjy f13297b;

    /* renamed from: c, reason: collision with root package name */
    final zzu f13298c;
    private zzij zzc;
    private final Set<zzim> zzd;
    private boolean zze;
    private final AtomicReference<String> zzf;
    private final Object zzg;
    private boolean zzh;
    private PriorityQueue<zzmi> zzi;

    @GuardedBy("consentLock")
    private zzie zzj;
    private final AtomicLong zzk;
    private long zzl;

    @VisibleForTesting
    private boolean zzm;
    private zzaw zzn;
    private final zznf zzo;

    protected zzin(zzhc zzhcVar) {
        super(zzhcVar);
        this.zzd = new CopyOnWriteArraySet();
        this.zzg = new Object();
        this.zzh = false;
        this.zzm = true;
        this.zzo = new zzjq(this);
        this.zzf = new AtomicReference<>();
        this.zzj = zzie.zza;
        this.zzl = -1L;
        this.zzk = new AtomicLong(0L);
        this.f13298c = new zzu(zzhcVar);
    }

    static /* synthetic */ void g(zzin zzinVar, zzie zzieVar, long j2, boolean z2, boolean z3) {
        zzinVar.zzt();
        zzinVar.zzu();
        zzie zzieVarP = zzinVar.zzk().p();
        if (j2 <= zzinVar.zzl && zzie.zza(zzieVarP.zza(), zzieVar.zza())) {
            zzinVar.zzj().zzn().zza("Dropped out-of-date consent setting, proposed settings", zzieVar);
            return;
        }
        if (!zzinVar.zzk().g(zzieVar)) {
            zzinVar.zzj().zzn().zza("Lower precedence consent source ignored, proposed source", Integer.valueOf(zzieVar.zza()));
            return;
        }
        zzinVar.zzl = j2;
        zzinVar.zzo().q(z2);
        if (z3) {
            zzinVar.zzo().zza(new AtomicReference<>());
        }
    }

    static /* synthetic */ void h(zzin zzinVar, zzie zzieVar, zzie zzieVar2) {
        zzie.zza zzaVar = zzie.zza.ANALYTICS_STORAGE;
        zzie.zza zzaVar2 = zzie.zza.AD_STORAGE;
        boolean zZza = zzieVar.zza(zzieVar2, zzaVar, zzaVar2);
        boolean zZzb = zzieVar.zzb(zzieVar2, zzaVar, zzaVar2);
        if (zZza || zZzb) {
            zzinVar.zzg().h();
        }
    }

    @TargetApi(30)
    private final PriorityQueue<zzmi> zzao() {
        if (this.zzi == null) {
            c.a();
            this.zzi = b.a(Comparator.comparing(new Function() { // from class: com.google.android.gms.measurement.internal.zziq
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(((zzmi) obj).zzb);
                }
            }, new Comparator() { // from class: com.google.android.gms.measurement.internal.zzip
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return (((Long) obj).longValue() > ((Long) obj2).longValue() ? 1 : (((Long) obj).longValue() == ((Long) obj2).longValue() ? 0 : -1));
                }
            }));
        }
        return this.zzi;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zzap() throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzt();
        String strZza = zzk().zzh.zza();
        if (strZza != null) {
            if ("unset".equals(strZza)) {
                n(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, "_npa", null, zzb().currentTimeMillis());
            } else {
                n(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, "_npa", Long.valueOf("true".equals(strZza) ? 1L : 0L), zzb().currentTimeMillis());
            }
        }
        if (!this.f13286a.zzac() || !this.zzm) {
            zzj().zzc().zza("Updating Scion state (FE)");
            zzo().u();
            return;
        }
        zzj().zzc().zza("Recording app launch after enabling measurement for the first time (FE)");
        zzaj();
        if (zzrd.zzb() && zze().zza(zzbi.zzbn)) {
            zzp().f13305b.a();
        }
        zzl().zzb(new zzjb(this));
    }

    final void c(long j2, boolean z2) {
        zzt();
        zzu();
        zzj().zzc().zza("Resetting analytics data (FE)");
        zzly zzlyVarZzp = zzp();
        zzlyVarZzp.zzt();
        zzlyVarZzp.f13306c.b();
        if (zzss.zzb() && zze().zza(zzbi.zzbs)) {
            zzg().h();
        }
        boolean zZzac = this.f13286a.zzac();
        zzge zzgeVarZzk = zzk();
        zzgeVarZzk.zzc.zza(j2);
        if (!TextUtils.isEmpty(zzgeVarZzk.zzk().zzq.zza())) {
            zzgeVarZzk.zzq.zza(null);
        }
        if (zzrd.zzb() && zzgeVarZzk.zze().zza(zzbi.zzbn)) {
            zzgeVarZzk.zzk.zza(0L);
        }
        zzgeVarZzk.zzl.zza(0L);
        if (!zzgeVarZzk.zze().zzv()) {
            zzgeVarZzk.k(!zZzac);
        }
        zzgeVarZzk.zzr.zza(null);
        zzgeVarZzk.zzs.zza(0L);
        zzgeVarZzk.zzt.zza(null);
        if (z2) {
            zzo().t();
        }
        if (zzrd.zzb() && zze().zza(zzbi.zzbn)) {
            zzp().f13305b.a();
        }
        this.zzm = !zZzac;
    }

    final void d(Bundle bundle, int i2, long j2) throws IllegalStateException {
        zzu();
        String strZza = zzie.zza(bundle);
        if (strZza != null) {
            zzj().zzv().zza("Ignoring invalid consent setting", strZza);
            zzj().zzv().zza("Valid consent values are 'granted', 'denied'");
        }
        zzie zzieVarZza = zzie.zza(bundle, i2);
        if (!zzql.zzb() || !zze().zza(zzbi.zzcl)) {
            zza(zzieVarZza, j2);
            return;
        }
        if (zzieVarZza.zzi()) {
            zza(zzieVarZza, j2);
        }
        zzay zzayVarZza = zzay.zza(bundle, i2);
        if (zzayVarZza.zzh()) {
            e(zzayVarZza);
        }
        Boolean boolZza = zzay.zza(bundle);
        if (boolZza != null) {
            zza(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, FirebaseAnalytics.UserProperty.ALLOW_AD_PERSONALIZATION_SIGNALS, (Object) boolZza.toString(), false);
        }
    }

    final void e(zzay zzayVar) throws IllegalStateException {
        zzl().zzb(new zzjt(this, zzayVar));
    }

    final void f(zzie zzieVar) throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzt();
        boolean z2 = (zzieVar.zzh() && zzieVar.zzg()) || zzo().w();
        if (z2 != this.f13286a.zzad()) {
            this.f13286a.zzb(z2);
            Boolean boolR = zzk().r();
            if (!z2 || boolR == null || boolR.booleanValue()) {
                zza(Boolean.valueOf(z2), false);
            }
        }
    }

    final void k(String str) {
        this.zzf.set(str);
    }

    final void l(String str, String str2, long j2, Bundle bundle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzt();
        m(str, str2, j2, bundle, true, this.zzc == null || zzne.N(str2), true, null);
    }

    protected final void m(String str, String str2, long j2, Bundle bundle, boolean z2, boolean z3, boolean z4, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str4;
        int i2;
        int length;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(bundle);
        zzt();
        zzu();
        if (!this.f13286a.zzac()) {
            zzj().zzc().zza("Event not sent since app measurement is disabled");
            return;
        }
        List listG = zzg().g();
        if (listG != null && !listG.contains(str2)) {
            zzj().zzc().zza("Dropping non-safelisted event. event name, origin", str2, str);
            return;
        }
        if (!this.zze) {
            this.zze = true;
            try {
                try {
                    (!this.f13286a.zzag() ? Class.forName("com.google.android.gms.tagmanager.TagManagerService", true, zza().getClassLoader()) : Class.forName("com.google.android.gms.tagmanager.TagManagerService")).getDeclaredMethod("initialize", Context.class).invoke(null, zza());
                } catch (Exception e2) {
                    zzj().zzu().zza("Failed to invoke Tag Manager's initialize() method", e2);
                }
            } catch (ClassNotFoundException unused) {
                zzj().zzn().zza("Tag Manager is not found and thus will not be used");
            }
        }
        if (Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN.equals(str2)) {
            if (bundle.containsKey("gclid")) {
                n("auto", "_lgclid", bundle.getString("gclid"), zzb().currentTimeMillis());
            }
            if (zzri.zzb() && zze().zza(zzbi.zzcs) && bundle.containsKey("gbraid")) {
                n("auto", "_gbraid", bundle.getString("gbraid"), zzb().currentTimeMillis());
            }
        }
        if (z2 && zzne.zzj(str2)) {
            zzq().j(bundle, zzk().zzt.zza());
        }
        if (!z4 && !"_iap".equals(str2)) {
            zzne zzneVarZzt = this.f13286a.zzt();
            int i3 = 2;
            if (zzneVarZzt.J(NotificationCompat.CATEGORY_EVENT, str2)) {
                if (!zzneVarZzt.z(NotificationCompat.CATEGORY_EVENT, zzii.zza, zzii.zzb, str2)) {
                    i3 = 13;
                } else if (zzneVarZzt.u(NotificationCompat.CATEGORY_EVENT, 40, str2)) {
                    i3 = 0;
                }
            }
            if (i3 != 0) {
                zzj().zzh().zza("Invalid public event name. Event will not be logged (FE)", zzi().c(str2));
                this.f13286a.zzt();
                String strZza = zzne.zza(str2, 40, true);
                length = str2 != null ? str2.length() : 0;
                this.f13286a.zzt();
                zzne.zza(this.zzo, i3, "_ev", strZza, length);
                return;
            }
        }
        zzkf zzkfVarZza = zzn().zza(false);
        if (zzkfVarZza != null && !bundle.containsKey("_sc")) {
            zzkfVarZza.f13299a = true;
        }
        zzne.zza(zzkfVarZza, bundle, z2 && !z4);
        boolean zEquals = "am".equals(str);
        boolean zN = zzne.N(str2);
        if (z2 && this.zzc != null && !zN && !zEquals) {
            zzj().zzc().zza("Passing event to registered event handler (FE)", zzi().c(str2), zzi().a(bundle));
            Preconditions.checkNotNull(this.zzc);
            this.zzc.interceptEvent(str, str2, bundle, j2);
            return;
        }
        if (this.f13286a.f()) {
            int iC = zzq().c(str2);
            if (iC != 0) {
                zzj().zzh().zza("Invalid event name. Event will not be logged (FE)", zzi().c(str2));
                zzq();
                String strZza2 = zzne.zza(str2, 40, true);
                length = str2 != null ? str2.length() : 0;
                this.f13286a.zzt();
                zzne.m(this.zzo, str3, iC, "_ev", strZza2, length);
                return;
            }
            Bundle bundleH = zzq().h(str3, str2, bundle, CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"}), z4);
            Preconditions.checkNotNull(bundleH);
            if (zzn().zza(false) != null && "_ae".equals(str2)) {
                zzme zzmeVar = zzp().f13306c;
                long jElapsedRealtime = zzmeVar.f13312b.zzb().elapsedRealtime();
                long j3 = jElapsedRealtime - zzmeVar.f13311a;
                zzmeVar.f13311a = jElapsedRealtime;
                if (j3 > 0) {
                    zzq().zza(bundleH, j3);
                }
            }
            if (zzqr.zzb() && zze().zza(zzbi.zzbm)) {
                if (!"auto".equals(str) && "_ssr".equals(str2)) {
                    zzne zzneVarZzq = zzq();
                    String string = bundleH.getString("_ffr");
                    String strTrim = Strings.isEmptyOrWhitespace(string) ? null : string != null ? string.trim() : string;
                    if (zznd.zza(strTrim, zzneVarZzq.zzk().zzq.zza())) {
                        zzneVarZzq.zzj().zzc().zza("Not logging duplicate session_start_with_rollout event");
                        return;
                    }
                    zzneVarZzq.zzk().zzq.zza(strTrim);
                } else if ("_ae".equals(str2)) {
                    String strZza3 = zzq().zzk().zzq.zza();
                    if (!TextUtils.isEmpty(strZza3)) {
                        bundleH.putString("_ffr", strZza3);
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(bundleH);
            boolean zE = zze().zza(zzbi.zzcj) ? zzp().e() : zzk().zzn.zza();
            if (zzk().zzk.zza() > 0 && zzk().e(j2) && zE) {
                zzj().zzp().zza("Current session is expired, remove the session number, ID, and engagement time");
                str4 = "_ae";
                n("auto", "_sid", null, zzb().currentTimeMillis());
                n("auto", "_sno", null, zzb().currentTimeMillis());
                n("auto", "_se", null, zzb().currentTimeMillis());
                zzk().zzl.zza(0L);
            } else {
                str4 = "_ae";
            }
            if (bundleH.getLong(FirebaseAnalytics.Param.EXTEND_SESSION, 0L) == 1) {
                zzj().zzp().zza("EXTEND_SESSION param attached: initiate a new session or extend the current active session");
                i2 = 1;
                this.f13286a.zzs().f13305b.b(j2, true);
            } else {
                i2 = 1;
            }
            ArrayList arrayList2 = new ArrayList(bundleH.keySet());
            Collections.sort(arrayList2);
            int size = arrayList2.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj = arrayList2.get(i4);
                i4 += i2;
                String str5 = (String) obj;
                if (str5 != null) {
                    zzq();
                    Bundle[] bundleArrF = zzne.F(bundleH.get(str5));
                    if (bundleArrF != null) {
                        bundleH.putParcelableArray(str5, bundleArrF);
                    }
                }
                i2 = 1;
            }
            int i5 = 0;
            while (i5 < arrayList.size()) {
                Bundle bundleC = (Bundle) arrayList.get(i5);
                String str6 = i5 != 0 ? "_ep" : str2;
                bundleC.putString("_o", str);
                if (z3) {
                    bundleC = zzq().C(bundleC);
                }
                Bundle bundle2 = bundleC;
                zzo().f(new zzbg(str6, new zzbb(bundle2), str, j2), str3);
                if (!zEquals) {
                    Iterator<zzim> it = this.zzd.iterator();
                    while (it.hasNext()) {
                        it.next().onEvent(str, str2, new Bundle(bundle2), j2);
                    }
                }
                i5++;
            }
            if (zzn().zza(false) == null || !str4.equals(str2)) {
                return;
            }
            zzp().zza(true, true, zzb().elapsedRealtime());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void n(java.lang.String r9, java.lang.String r10, java.lang.Object r11, long r12) {
        /*
            r8 = this;
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r9)
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r10)
            r8.zzt()
            r8.zzu()
            java.lang.String r0 = "allow_personalized_ads"
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L60
            boolean r0 = r11 instanceof java.lang.String
            java.lang.String r1 = "_npa"
            if (r0 == 0) goto L51
            r0 = r11
            java.lang.String r0 = (java.lang.String) r0
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L51
            java.util.Locale r10 = java.util.Locale.ENGLISH
            java.lang.String r10 = r0.toLowerCase(r10)
            java.lang.String r11 = "false"
            boolean r10 = r11.equals(r10)
            r2 = 1
            if (r10 == 0) goto L35
            r4 = r2
            goto L37
        L35:
            r4 = 0
        L37:
            java.lang.Long r10 = java.lang.Long.valueOf(r4)
            com.google.android.gms.measurement.internal.zzge r0 = r8.zzk()
            com.google.android.gms.measurement.internal.zzgk r0 = r0.zzh
            long r4 = r10.longValue()
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 != 0) goto L4b
            java.lang.String r11 = "true"
        L4b:
            r0.zza(r11)
            r6 = r10
        L4f:
            r3 = r1
            goto L62
        L51:
            if (r11 != 0) goto L60
            com.google.android.gms.measurement.internal.zzge r10 = r8.zzk()
            com.google.android.gms.measurement.internal.zzgk r10 = r10.zzh
            java.lang.String r0 = "unset"
            r10.zza(r0)
            r6 = r11
            goto L4f
        L60:
            r3 = r10
            r6 = r11
        L62:
            com.google.android.gms.measurement.internal.zzhc r10 = r8.f13286a
            boolean r10 = r10.zzac()
            if (r10 != 0) goto L78
            com.google.android.gms.measurement.internal.zzfs r9 = r8.zzj()
            com.google.android.gms.measurement.internal.zzfu r9 = r9.zzp()
            java.lang.String r10 = "User property not set since app measurement is disabled"
            r9.zza(r10)
            return
        L78:
            com.google.android.gms.measurement.internal.zzhc r10 = r8.f13286a
            boolean r10 = r10.f()
            if (r10 != 0) goto L81
            return
        L81:
            com.google.android.gms.measurement.internal.zzmz r10 = new com.google.android.gms.measurement.internal.zzmz
            r2 = r10
            r4 = r12
            r7 = r9
            r2.<init>(r3, r4, r6, r7)
            com.google.android.gms.measurement.internal.zzkq r9 = r8.zzo()
            r9.l(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzin.n(java.lang.String, java.lang.String, java.lang.Object, long):void");
    }

    final /* synthetic */ void o(List list) {
        zzt();
        if (Build.VERSION.SDK_INT >= 30) {
            SparseArray sparseArrayN = zzk().n();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                zzmi zzmiVar = (zzmi) it.next();
                if (!sparseArrayN.contains(zzmiVar.zzc) || ((Long) sparseArrayN.get(zzmiVar.zzc)).longValue() < zzmiVar.zzb) {
                    zzao().add(zzmiVar);
                }
            }
            p();
        }
    }

    final void p() {
        zzmi zzmiVarPoll;
        MeasurementManagerFutures measurementManagerFuturesP;
        zzt();
        if (zzao().isEmpty() || this.zzh || (zzmiVarPoll = zzao().poll()) == null || (measurementManagerFuturesP = zzq().P()) == null) {
            return;
        }
        this.zzh = true;
        zzj().zzp().zza("Registering trigger URI", zzmiVarPoll.zza);
        ListenableFuture<Unit> listenableFutureRegisterTriggerAsync = measurementManagerFuturesP.registerTriggerAsync(Uri.parse(zzmiVarPoll.zza));
        if (listenableFutureRegisterTriggerAsync == null) {
            this.zzh = false;
            zzao().add(zzmiVarPoll);
            return;
        }
        SparseArray sparseArrayN = zzk().n();
        sparseArrayN.put(zzmiVarPoll.zzc, Long.valueOf(zzmiVarPoll.zzb));
        zzge zzgeVarZzk = zzk();
        int[] iArr = new int[sparseArrayN.size()];
        long[] jArr = new long[sparseArrayN.size()];
        for (int i2 = 0; i2 < sparseArrayN.size(); i2++) {
            iArr[i2] = sparseArrayN.keyAt(i2);
            jArr[i2] = ((Long) sparseArrayN.valueAt(i2)).longValue();
        }
        Bundle bundle = new Bundle();
        bundle.putIntArray("uriSources", iArr);
        bundle.putLongArray("uriTimestamps", jArr);
        zzgeVarZzk.zzi.zza(bundle);
        com.google.android.gms.internal.measurement.zzjw.zza(listenableFutureRegisterTriggerAsync, new zziz(this, zzmiVarPoll), new zzja(this));
    }

    final void r(String str, String str2, Bundle bundle) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzt();
        l(str, str2, zzb().currentTimeMillis(), bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    public final Boolean zzaa() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzl().d(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "boolean test flag value", new zzix(this, atomicReference));
    }

    public final Double zzab() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzl().d(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "double test flag value", new zzjr(this, atomicReference));
    }

    public final Integer zzac() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzl().d(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "int test flag value", new zzjs(this, atomicReference));
    }

    public final Long zzad() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzl().d(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "long test flag value", new zzjp(this, atomicReference));
    }

    public final String zzae() {
        return this.zzf.get();
    }

    public final String zzaf() {
        zzkf zzkfVarZzaa = this.f13286a.zzq().zzaa();
        if (zzkfVarZzaa != null) {
            return zzkfVarZzaa.zzb;
        }
        return null;
    }

    public final String zzag() {
        zzkf zzkfVarZzaa = this.f13286a.zzq().zzaa();
        if (zzkfVarZzaa != null) {
            return zzkfVarZzaa.zza;
        }
        return null;
    }

    public final String zzah() {
        if (this.f13286a.zzu() != null) {
            return this.f13286a.zzu();
        }
        try {
            return new zzgw(zza(), this.f13286a.zzx()).zza("google_app_id");
        } catch (IllegalStateException e2) {
            this.f13286a.zzj().zzg().zza("getGoogleAppId failed with exception", e2);
            return null;
        }
    }

    public final String zzai() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzl().d(atomicReference, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS, "String test flag value", new zzjk(this, atomicReference));
    }

    @WorkerThread
    public final void zzaj() throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Boolean boolG;
        zzt();
        zzu();
        if (this.f13286a.f()) {
            if (zze().zza(zzbi.zzbh) && (boolG = zze().g("google_analytics_deferred_deep_link_enabled")) != null && boolG.booleanValue()) {
                zzj().zzc().zza("Deferred Deep Link feature enabled.");
                zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zziw
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zza.zzam();
                    }
                });
            }
            zzo().zzac();
            this.zzm = false;
            String strT = zzk().t();
            if (TextUtils.isEmpty(strT)) {
                return;
            }
            zzf().zzab();
            if (strT.equals(Build.VERSION.RELEASE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", strT);
            r("auto", "_ou", bundle);
        }
    }

    public final void zzak() {
        if (!(zza().getApplicationContext() instanceof Application) || this.f13297b == null) {
            return;
        }
        ((Application) zza().getApplicationContext()).unregisterActivityLifecycleCallbacks(this.f13297b);
    }

    final void zzal() throws IllegalStateException {
        if (zzsg.zzb() && zze().zza(zzbi.zzcg)) {
            if (zzl().zzg()) {
                zzj().zzg().zza("Cannot get trigger URIs from analytics worker thread");
                return;
            }
            if (zzae.zza()) {
                zzj().zzg().zza("Cannot get trigger URIs from main thread");
                return;
            }
            zzu();
            zzj().zzp().zza("Getting trigger URIs (FE)");
            final AtomicReference atomicReference = new AtomicReference();
            zzl().d(atomicReference, 5000L, "get trigger URIs", new Runnable() { // from class: com.google.android.gms.measurement.internal.zzis
                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException {
                    zzin zzinVar = this.zza;
                    AtomicReference atomicReference2 = atomicReference;
                    Bundle bundleZza = zzinVar.zzk().zzi.zza();
                    zzkq zzkqVarZzo = zzinVar.zzo();
                    if (bundleZza == null) {
                        bundleZza = new Bundle();
                    }
                    zzkqVarZzo.m(atomicReference2, bundleZza);
                }
            });
            final List list = (List) atomicReference.get();
            if (list == null) {
                zzj().zzg().zza("Timed out waiting for get trigger URIs");
            } else {
                zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzir
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.zza.o(list);
                    }
                });
            }
        }
    }

    @WorkerThread
    public final void zzam() {
        zzt();
        if (zzk().zzo.zza()) {
            zzj().zzc().zza("Deferred Deep Link already retrieved. Not fetching again.");
            return;
        }
        long jZza = zzk().zzp.zza();
        zzk().zzp.zza(1 + jZza);
        if (jZza >= 5) {
            zzj().zzu().zza("Permanently failed to retrieve Deferred Deep Link. Reached maximum retries.");
            zzk().zzo.zza(true);
        } else {
            if (!zzql.zzb() || !zze().zza(zzbi.zzcn)) {
                this.f13286a.zzah();
                return;
            }
            if (this.zzn == null) {
                this.zzn = new zzji(this, this.f13286a);
            }
            this.zzn.zza(0L);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzb zzc() {
        return super.zzc();
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

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfm zzg() {
        return super.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzfl zzh() {
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

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzin zzm() {
        return super.zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzki zzn() {
        return super.zzn();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzkq zzo() {
        return super.zzo();
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    public final /* bridge */ /* synthetic */ zzly zzp() {
        return super.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzf, com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    @Override // com.google.android.gms.measurement.internal.zze
    protected final boolean zzz() {
        return false;
    }

    public final ArrayList<Bundle> zza(String str, String str2) {
        if (zzl().zzg()) {
            zzj().zzg().zza("Cannot get conditional user properties from analytics worker thread");
            return new ArrayList<>(0);
        }
        if (zzae.zza()) {
            zzj().zzg().zza("Cannot get conditional user properties from main thread");
            return new ArrayList<>(0);
        }
        AtomicReference atomicReference = new AtomicReference();
        this.f13286a.zzl().d(atomicReference, 5000L, "get conditional user properties", new zzjl(this, atomicReference, null, str, str2));
        List list = (List) atomicReference.get();
        if (list != null) {
            return zzne.zzb(list);
        }
        zzj().zzg().zza("Timed out waiting for get conditional user properties", null);
        return new ArrayList<>();
    }

    public final void zzb(String str, String str2, Bundle bundle) throws IllegalStateException {
        zza(str, str2, bundle, true, true, zzb().currentTimeMillis());
    }

    private final void zzb(String str, String str2, long j2, Bundle bundle, boolean z2, boolean z3, boolean z4, String str3) throws IllegalStateException {
        zzl().zzb(new zzjd(this, str, str2, j2, zzne.zza(bundle), z2, z3, z4, str3));
    }

    public final void zzb(Bundle bundle) throws IllegalStateException {
        zza(bundle, zzb().currentTimeMillis());
    }

    public final void zzb(zzim zzimVar) {
        zzu();
        Preconditions.checkNotNull(zzimVar);
        if (this.zzd.remove(zzimVar)) {
            return;
        }
        zzj().zzu().zza("OnEventListener had not been registered");
    }

    public final List<zzmz> zza(boolean z2) {
        zzu();
        zzj().zzp().zza("Getting user properties (FE)");
        if (zzl().zzg()) {
            zzj().zzg().zza("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        if (zzae.zza()) {
            zzj().zzg().zza("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        this.f13286a.zzl().d(atomicReference, 5000L, "get user properties", new zzjf(this, atomicReference, z2));
        List<zzmz> list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        zzj().zzg().zza("Timed out waiting for get user properties, includeInternal", Boolean.valueOf(z2));
        return Collections.emptyList();
    }

    public final Map<String, Object> zza(String str, String str2, boolean z2) {
        if (zzl().zzg()) {
            zzj().zzg().zza("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        if (zzae.zza()) {
            zzj().zzg().zza("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        this.f13286a.zzl().d(atomicReference, 5000L, "get user properties", new zzjo(this, atomicReference, null, str, str2, z2));
        List<zzmz> list = (List) atomicReference.get();
        if (list == null) {
            zzj().zzg().zza("Timed out waiting for handle get user properties, includeInternal", Boolean.valueOf(z2));
            return Collections.emptyMap();
        }
        ArrayMap arrayMap = new ArrayMap(list.size());
        for (zzmz zzmzVar : list) {
            Object objZza = zzmzVar.zza();
            if (objZza != null) {
                arrayMap.put(zzmzVar.zza, objZza);
            }
        }
        return arrayMap;
    }

    public final void zza(String str, String str2, Bundle bundle) throws IllegalStateException {
        long jCurrentTimeMillis = zzb().currentTimeMillis();
        Preconditions.checkNotEmpty(str);
        Bundle bundle2 = new Bundle();
        bundle2.putString("name", str);
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, jCurrentTimeMillis);
        if (str2 != null) {
            bundle2.putString(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, str2);
            bundle2.putBundle(AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, bundle);
        }
        zzl().zzb(new zzjm(this, bundle2));
    }

    final /* synthetic */ void zza(Bundle bundle) {
        if (bundle == null) {
            zzk().zzt.zza(new Bundle());
            return;
        }
        Bundle bundleZza = zzk().zzt.zza();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null && !(obj instanceof String) && !(obj instanceof Long) && !(obj instanceof Double)) {
                zzq();
                if (zzne.s(obj)) {
                    zzq();
                    zzne.zza(this.zzo, 27, (String) null, (String) null, 0);
                }
                zzj().zzv().zza("Invalid default event parameter type. Name, value", str, obj);
            } else if (zzne.N(str)) {
                zzj().zzv().zza("Invalid default event parameter name. Name", str);
            } else if (obj == null) {
                bundleZza.remove(str);
            } else if (zzq().w(RemoteMessageConst.MessageBody.PARAM, str, zze().c(this.f13286a.zzh().f()), obj)) {
                zzq().k(bundleZza, str, obj);
            }
        }
        zzq();
        if (zzne.r(bundleZza, zze().zzg())) {
            zzq();
            zzne.zza(this.zzo, 26, (String) null, (String) null, 0);
            zzj().zzv().zza("Too many default event parameters set. Discarding beyond event parameter limit");
        }
        zzk().zzt.zza(bundleZza);
        zzo().zza(bundleZza);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z2, boolean z3, long j2) throws IllegalStateException {
        String str3 = str == null ? PushConstants.EXTRA_APPLICATION_PENDING_INTENT : str;
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        if (str2 != FirebaseAnalytics.Event.SCREEN_VIEW && (str2 == null || !str2.equals(FirebaseAnalytics.Event.SCREEN_VIEW))) {
            zzb(str3, str2, j2, bundle2, z3, !z3 || this.zzc == null || zzne.N(str2), z2, null);
        } else {
            zzn().zza(bundle2, j2);
        }
    }

    public final void zza(String str, String str2, Bundle bundle, String str3) throws IllegalStateException {
        zzs();
        zzb(str, str2, zzb().currentTimeMillis(), bundle, false, true, true, str3);
    }

    public final void zza(zzim zzimVar) {
        zzu();
        Preconditions.checkNotNull(zzimVar);
        if (this.zzd.add(zzimVar)) {
            return;
        }
        zzj().zzu().zza("OnEventListener already registered");
    }

    private final void zza(String str, String str2, long j2, Object obj) throws IllegalStateException {
        zzl().zzb(new zzjg(this, str, str2, obj, j2));
    }

    public final void zza(Bundle bundle, long j2) throws IllegalStateException {
        Preconditions.checkNotNull(bundle);
        Bundle bundle2 = new Bundle(bundle);
        if (!TextUtils.isEmpty(bundle2.getString("app_id"))) {
            zzj().zzu().zza("Package name should be null when calling setConditionalUserProperty");
        }
        bundle2.remove("app_id");
        Preconditions.checkNotNull(bundle2);
        zzif.zza(bundle2, "app_id", String.class, null);
        zzif.zza(bundle2, "origin", String.class, null);
        zzif.zza(bundle2, "name", String.class, null);
        zzif.zza(bundle2, "value", Object.class, null);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME, String.class, null);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT, Long.class, 0L);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_NAME, String.class, null);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIMED_OUT_EVENT_PARAMS, Bundle.class, null);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_NAME, String.class, null);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TRIGGERED_EVENT_PARAMS, Bundle.class, null);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE, Long.class, 0L);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_NAME, String.class, null);
        zzif.zza(bundle2, AppMeasurementSdk.ConditionalUserProperty.EXPIRED_EVENT_PARAMS, Bundle.class, null);
        Preconditions.checkNotEmpty(bundle2.getString("name"));
        Preconditions.checkNotEmpty(bundle2.getString("origin"));
        Preconditions.checkNotNull(bundle2.get("value"));
        bundle2.putLong(AppMeasurementSdk.ConditionalUserProperty.CREATION_TIMESTAMP, j2);
        String string = bundle2.getString("name");
        Object obj = bundle2.get("value");
        if (zzq().B(string) != 0) {
            zzj().zzg().zza("Invalid conditional user property name", zzi().e(string));
            return;
        }
        if (zzq().d(string, obj) != 0) {
            zzj().zzg().zza("Invalid conditional user property value", zzi().e(string), obj);
            return;
        }
        Object objI = zzq().I(string, obj);
        if (objI == null) {
            zzj().zzg().zza("Unable to normalize conditional user property value", zzi().e(string), obj);
            return;
        }
        zzif.zza(bundle2, objI);
        long j3 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_TIMEOUT);
        if (!TextUtils.isEmpty(bundle2.getString(AppMeasurementSdk.ConditionalUserProperty.TRIGGER_EVENT_NAME)) && (j3 > 15552000000L || j3 < 1)) {
            zzj().zzg().zza("Invalid conditional user property timeout", zzi().e(string), Long.valueOf(j3));
            return;
        }
        long j4 = bundle2.getLong(AppMeasurementSdk.ConditionalUserProperty.TIME_TO_LIVE);
        if (j4 <= 15552000000L && j4 >= 1) {
            zzl().zzb(new zzjj(this, bundle2));
        } else {
            zzj().zzg().zza("Invalid conditional user property time to live", zzi().e(string), Long.valueOf(j4));
        }
    }

    public final void zza(zzie zzieVar, long j2) throws IllegalStateException {
        zzie zzieVar2;
        boolean z2;
        zzie zzieVar3;
        boolean z3;
        boolean zZzc;
        zzu();
        int iZza = zzieVar.zza();
        if (iZza != -10 && zzieVar.zzc() == null && zzieVar.zzd() == null) {
            zzj().zzv().zza("Discarding empty consent settings");
            return;
        }
        synchronized (this.zzg) {
            try {
                zzieVar2 = this.zzj;
                z2 = false;
                if (zzie.zza(iZza, zzieVar2.zza())) {
                    zZzc = zzieVar.zzc(this.zzj);
                    if (zzieVar.zzh() && !this.zzj.zzh()) {
                        z2 = true;
                    }
                    zzie zzieVarZzb = zzieVar.zzb(this.zzj);
                    this.zzj = zzieVarZzb;
                    zzieVar3 = zzieVarZzb;
                    z3 = z2;
                    z2 = true;
                } else {
                    zzieVar3 = zzieVar;
                    z3 = false;
                    zZzc = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!z2) {
            zzj().zzn().zza("Ignoring lower-priority consent settings, proposed settings", zzieVar3);
            return;
        }
        long andIncrement = this.zzk.getAndIncrement();
        if (zZzc) {
            k(null);
            zzl().zzc(new zzjw(this, zzieVar3, j2, andIncrement, z3, zzieVar2));
            return;
        }
        zzjv zzjvVar = new zzjv(this, zzieVar3, andIncrement, z3, zzieVar2);
        if (iZza != 30 && iZza != -10) {
            zzl().zzb(zzjvVar);
        } else {
            zzl().zzc(zzjvVar);
        }
    }

    @WorkerThread
    public final void zza(zzij zzijVar) {
        zzij zzijVar2;
        zzt();
        zzu();
        if (zzijVar != null && zzijVar != (zzijVar2 = this.zzc)) {
            Preconditions.checkState(zzijVar2 == null, "EventInterceptor already set.");
        }
        this.zzc = zzijVar;
    }

    public final void zza(Boolean bool) throws IllegalStateException {
        zzu();
        zzl().zzb(new zzju(this, bool));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zza(Boolean bool, boolean z2) throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzt();
        zzu();
        zzj().zzc().zza("Setting app measurement enabled (FE)", bool);
        zzk().zza(bool);
        if (z2) {
            zzk().i(bool);
        }
        if (this.f13286a.zzad() || !(bool == null || bool.booleanValue())) {
            zzap();
        }
    }

    public final void zza(String str, String str2, Object obj, boolean z2) throws IllegalStateException {
        zza(str, str2, obj, z2, zzb().currentTimeMillis());
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(java.lang.String r7, java.lang.String r8, java.lang.Object r9, boolean r10, long r11) throws java.lang.IllegalStateException {
        /*
            r6 = this;
            if (r7 != 0) goto L4
            java.lang.String r7 = "app"
        L4:
            r1 = r7
            r7 = 0
            r0 = 24
            if (r10 == 0) goto L13
            com.google.android.gms.measurement.internal.zzne r10 = r6.zzq()
            int r10 = r10.B(r8)
            goto L35
        L13:
            com.google.android.gms.measurement.internal.zzne r10 = r6.zzq()
            java.lang.String r2 = "user property"
            boolean r3 = r10.J(r2, r8)
            r4 = 6
            if (r3 != 0) goto L22
        L20:
            r10 = r4
            goto L35
        L22:
            java.lang.String[] r3 = com.google.android.gms.measurement.internal.zzik.zza
            boolean r3 = r10.y(r2, r3, r8)
            if (r3 != 0) goto L2d
            r10 = 15
            goto L35
        L2d:
            boolean r10 = r10.u(r2, r0, r8)
            if (r10 != 0) goto L34
            goto L20
        L34:
            r10 = r7
        L35:
            java.lang.String r2 = "_ev"
            r3 = 1
            if (r10 == 0) goto L52
            r6.zzq()
            java.lang.String r9 = com.google.android.gms.measurement.internal.zzne.zza(r8, r0, r3)
            if (r8 == 0) goto L47
            int r7 = r8.length()
        L47:
            com.google.android.gms.measurement.internal.zzhc r8 = r6.f13286a
            r8.zzt()
            com.google.android.gms.measurement.internal.zznf r8 = r6.zzo
            com.google.android.gms.measurement.internal.zzne.zza(r8, r10, r2, r9, r7)
            return
        L52:
            if (r9 == 0) goto L91
            com.google.android.gms.measurement.internal.zzne r10 = r6.zzq()
            int r10 = r10.d(r8, r9)
            if (r10 == 0) goto L80
            r6.zzq()
            java.lang.String r8 = com.google.android.gms.measurement.internal.zzne.zza(r8, r0, r3)
            boolean r11 = r9 instanceof java.lang.String
            if (r11 != 0) goto L6d
            boolean r11 = r9 instanceof java.lang.CharSequence
            if (r11 == 0) goto L75
        L6d:
            java.lang.String r7 = java.lang.String.valueOf(r9)
            int r7 = r7.length()
        L75:
            com.google.android.gms.measurement.internal.zzhc r9 = r6.f13286a
            r9.zzt()
            com.google.android.gms.measurement.internal.zznf r9 = r6.zzo
            com.google.android.gms.measurement.internal.zzne.zza(r9, r10, r2, r8, r7)
            return
        L80:
            com.google.android.gms.measurement.internal.zzne r7 = r6.zzq()
            java.lang.Object r5 = r7.I(r8, r9)
            if (r5 == 0) goto L90
            r0 = r6
            r2 = r8
            r3 = r11
            r0.zza(r1, r2, r3, r5)
        L90:
            return
        L91:
            r5 = 0
            r0 = r6
            r2 = r8
            r3 = r11
            r0.zza(r1, r2, r3, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzin.zza(java.lang.String, java.lang.String, java.lang.Object, boolean, long):void");
    }
}
