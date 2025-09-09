package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.media3.common.C;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.measurement.dynamite.ModuleDescriptor;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public class zzdf {
    private static volatile zzdf zzb;

    /* renamed from: a, reason: collision with root package name */
    protected final Clock f13187a;
    private final String zzc;
    private final ExecutorService zzd;
    private final AppMeasurementSdk zze;

    @GuardedBy("listenerList")
    private final List<Pair<com.google.android.gms.measurement.internal.zzim, zzb>> zzf;
    private int zzg;
    private boolean zzh;
    private String zzi;
    private volatile zzcu zzj;

    abstract class zza implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final long f13188a;

        /* renamed from: b, reason: collision with root package name */
        final long f13189b;
        private final boolean zzc;

        zza(zzdf zzdfVar) {
            this(true);
        }

        protected void a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (zzdf.this.zzh) {
                a();
                return;
            }
            try {
                zza();
            } catch (Exception e2) {
                zzdf.this.zza(e2, false, this.zzc);
                a();
            }
        }

        abstract void zza();

        zza(boolean z2) {
            this.f13188a = zzdf.this.f13187a.currentTimeMillis();
            this.f13189b = zzdf.this.f13187a.elapsedRealtime();
            this.zzc = z2;
        }
    }

    static class zzb extends zzcz {
        private final com.google.android.gms.measurement.internal.zzim zza;

        zzb(com.google.android.gms.measurement.internal.zzim zzimVar) {
            this.zza = zzimVar;
        }

        @Override // com.google.android.gms.internal.measurement.zzda
        public final int zza() {
            return System.identityHashCode(this.zza);
        }

        @Override // com.google.android.gms.internal.measurement.zzda
        public final void zza(String str, String str2, Bundle bundle, long j2) {
            this.zza.onEvent(str, str2, bundle, j2);
        }
    }

    static class zzc extends zzcz {
        private final com.google.android.gms.measurement.internal.zzij zza;

        zzc(com.google.android.gms.measurement.internal.zzij zzijVar) {
            this.zza = zzijVar;
        }

        @Override // com.google.android.gms.internal.measurement.zzda
        public final int zza() {
            return System.identityHashCode(this.zza);
        }

        @Override // com.google.android.gms.internal.measurement.zzda
        public final void zza(String str, String str2, Bundle bundle, long j2) {
            this.zza.interceptEvent(str, str2, bundle, j2);
        }
    }

    class zzd implements Application.ActivityLifecycleCallbacks {
        zzd() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityCreated(Activity activity, Bundle bundle) {
            zzdf.this.zza(new zzeo(this, bundle, activity));
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
            zzdf.this.zza(new zzet(this, activity));
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityPaused(Activity activity) {
            zzdf.this.zza(new zzes(this, activity));
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityResumed(Activity activity) {
            zzdf.this.zza(new zzep(this, activity));
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            zzcs zzcsVar = new zzcs();
            zzdf.this.zza(new zzeu(this, activity, zzcsVar));
            Bundle bundleZza = zzcsVar.zza(50L);
            if (bundleZza != null) {
                bundle.putAll(bundleZza);
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStarted(Activity activity) {
            zzdf.this.zza(new zzeq(this, activity));
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStopped(Activity activity) {
            zzdf.this.zza(new zzer(this, activity));
        }
    }

    private zzdf(Context context, String str, String str2, String str3, Bundle bundle) {
        if (str == null || !zzc(str2, str3)) {
            this.zzc = "FA";
        } else {
            this.zzc = str;
        }
        this.f13187a = DefaultClock.getInstance();
        this.zzd = zzch.zza().zza(new zzdr(this), zzcq.zza);
        this.zze = new AppMeasurementSdk(this);
        this.zzf = new ArrayList();
        if (zzb(context) && !zzk()) {
            this.zzi = null;
            this.zzh = true;
            Log.w(this.zzc, "Disabling data collection. Found google_app_id in strings.xml but Google Analytics for Firebase is missing. Remove this value or add Google Analytics for Firebase to resume data collection.");
            return;
        }
        if (zzc(str2, str3)) {
            this.zzi = str2;
        } else {
            this.zzi = "fa";
            if (str2 == null || str3 == null) {
                if ((str2 == null) ^ (str3 == null)) {
                    Log.w(this.zzc, "Specified origin or custom app id is null. Both parameters will be ignored.");
                }
            } else {
                Log.v(this.zzc, "Deferring to Google Analytics for Firebase for event data collection. https://firebase.google.com/docs/analytics");
            }
        }
        zza(new zzdi(this, str2, str3, context, bundle));
        Application application = (Application) context.getApplicationContext();
        if (application == null) {
            Log.w(this.zzc, "Unable to register lifecycle notifications. Application null.");
        } else {
            application.registerActivityLifecycleCallbacks(new zzd());
        }
    }

    private final boolean zzk() throws ClassNotFoundException {
        try {
            Class.forName("com.google.firebase.analytics.FirebaseAnalytics", false, getClass().getClassLoader());
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    protected final zzcu a(Context context, boolean z2) {
        try {
            return zzct.asInterface(DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION, ModuleDescriptor.MODULE_ID).instantiate("com.google.android.gms.measurement.internal.AppMeasurementDynamiteService"));
        } catch (DynamiteModule.LoadingException e2) {
            zza((Exception) e2, true, false);
            return null;
        }
    }

    public final int zza(String str) {
        zzcs zzcsVar = new zzcs();
        zza(new zzed(this, str, zzcsVar));
        Integer num = (Integer) zzcs.zza(zzcsVar.zza(10000L), Integer.class);
        if (num == null) {
            return 25;
        }
        return num.intValue();
    }

    public final AppMeasurementSdk zzb() {
        return this.zze;
    }

    @WorkerThread
    public final Long zzc() {
        zzcs zzcsVar = new zzcs();
        zza(new zzef(this, zzcsVar));
        return zzcsVar.zzb(120000L);
    }

    public final String zzd() {
        return this.zzi;
    }

    @WorkerThread
    public final String zze() {
        zzcs zzcsVar = new zzcs();
        zza(new zzeg(this, zzcsVar));
        return zzcsVar.zzc(120000L);
    }

    public final String zzf() {
        zzcs zzcsVar = new zzcs();
        zza(new zzdv(this, zzcsVar));
        return zzcsVar.zzc(50L);
    }

    public final String zzg() {
        zzcs zzcsVar = new zzcs();
        zza(new zzea(this, zzcsVar));
        return zzcsVar.zzc(500L);
    }

    public final String zzh() {
        zzcs zzcsVar = new zzcs();
        zza(new zzdx(this, zzcsVar));
        return zzcsVar.zzc(500L);
    }

    public final String zzi() {
        zzcs zzcsVar = new zzcs();
        zza(new zzdw(this, zzcsVar));
        return zzcsVar.zzc(500L);
    }

    public final void zzj() {
        zza(new zzdp(this));
    }

    public final void zzb(String str) {
        zza(new zzdu(this, str));
    }

    public final void zzd(Bundle bundle) {
        zza(new zzek(this, bundle));
    }

    public final void zzb(String str, String str2, Bundle bundle) {
        zza(str, str2, bundle, true, true, null);
    }

    public final void zzd(String str) {
        zza(new zzdm(this, str));
    }

    public final void zzb(Bundle bundle) {
        zza(new zzdn(this, bundle));
    }

    public final void zzc(String str) {
        zza(new zzdt(this, str));
    }

    public final long zza() {
        zzcs zzcsVar = new zzcs();
        zza(new zzdy(this, zzcsVar));
        Long lZzb = zzcsVar.zzb(500L);
        if (lZzb == null) {
            long jNextLong = new Random(System.nanoTime() ^ this.f13187a.currentTimeMillis()).nextLong();
            int i2 = this.zzg + 1;
            this.zzg = i2;
            return jNextLong + i2;
        }
        return lZzb.longValue();
    }

    public final void zzb(String str, String str2) {
        zza((String) null, str, (Object) str2, false);
    }

    public final void zzc(Bundle bundle) {
        zza(new zzdq(this, bundle));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzc(String str, String str2) {
        return (str2 == null || str == null || zzk()) ? false : true;
    }

    public final void zzb(com.google.android.gms.measurement.internal.zzim zzimVar) {
        Pair<com.google.android.gms.measurement.internal.zzim, zzb> pair;
        Preconditions.checkNotNull(zzimVar);
        synchronized (this.zzf) {
            int i2 = 0;
            while (true) {
                try {
                    if (i2 >= this.zzf.size()) {
                        pair = null;
                        break;
                    } else {
                        if (zzimVar.equals(this.zzf.get(i2).first)) {
                            pair = this.zzf.get(i2);
                            break;
                        }
                        i2++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (pair == null) {
                Log.w(this.zzc, "OnEventListener had not been registered.");
                return;
            }
            this.zzf.remove(pair);
            zzb zzbVar = (zzb) pair.second;
            if (this.zzj != null) {
                try {
                    this.zzj.unregisterOnMeasurementEventListener(zzbVar);
                    return;
                } catch (BadParcelableException | NetworkOnMainThreadException | RemoteException | IllegalArgumentException | IllegalStateException | NullPointerException | SecurityException | UnsupportedOperationException unused) {
                    Log.w(this.zzc, "Failed to unregister event listener on calling thread. Trying again on the dynamite thread.");
                }
            }
            zza(new zzem(this, zzbVar));
        }
    }

    public final Bundle zza(Bundle bundle, boolean z2) {
        zzcs zzcsVar = new zzcs();
        zza(new zzeb(this, bundle, zzcsVar));
        if (z2) {
            return zzcsVar.zza(5000L);
        }
        return null;
    }

    public static zzdf zza(@NonNull Context context) {
        return zza(context, (String) null, (String) null, (String) null, (Bundle) null);
    }

    public static zzdf zza(Context context, String str, String str2, String str3, Bundle bundle) {
        Preconditions.checkNotNull(context);
        if (zzb == null) {
            synchronized (zzdf.class) {
                try {
                    if (zzb == null) {
                        zzb = new zzdf(context, str, str2, str3, bundle);
                    }
                } finally {
                }
            }
        }
        return zzb;
    }

    private static boolean zzb(Context context) {
        return new com.google.android.gms.measurement.internal.zzgw(context, com.google.android.gms.measurement.internal.zzgw.zza(context)).zza("google_app_id") != null;
    }

    public final Object zza(int i2) {
        zzcs zzcsVar = new zzcs();
        zza(new zzei(this, zzcsVar, i2));
        return zzcs.zza(zzcsVar.zza(C.DEFAULT_SEEK_FORWARD_INCREMENT_MS), Object.class);
    }

    public final List<Bundle> zza(String str, String str2) {
        zzcs zzcsVar = new zzcs();
        zza(new zzdj(this, str, str2, zzcsVar));
        List<Bundle> list = (List) zzcs.zza(zzcsVar.zza(5000L), List.class);
        return list == null ? Collections.emptyList() : list;
    }

    public final Map<String, Object> zza(String str, String str2, boolean z2) {
        zzcs zzcsVar = new zzcs();
        zza(new zzdz(this, str, str2, z2, zzcsVar));
        Bundle bundleZza = zzcsVar.zza(5000L);
        if (bundleZza != null && bundleZza.size() != 0) {
            HashMap map = new HashMap(bundleZza.size());
            for (String str3 : bundleZza.keySet()) {
                Object obj = bundleZza.get(str3);
                if ((obj instanceof Double) || (obj instanceof Long) || (obj instanceof String)) {
                    map.put(str3, obj);
                }
            }
            return map;
        }
        return Collections.emptyMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(Exception exc, boolean z2, boolean z3) {
        this.zzh |= z2;
        if (z2) {
            Log.w(this.zzc, "Data collection startup failed. No data will be collected.", exc);
            return;
        }
        if (z3) {
            zza(5, "Error with data collection. Data lost.", exc, (Object) null, (Object) null);
        }
        Log.w(this.zzc, "Error with data collection. Data lost.", exc);
    }

    public final void zza(String str, String str2, Bundle bundle) {
        zza(new zzdk(this, str, str2, bundle));
    }

    public final void zza(@NonNull String str, Bundle bundle) {
        zza(null, str, bundle, false, true, null);
    }

    public final void zza(String str, String str2, Bundle bundle, long j2) {
        zza(str, str2, bundle, true, false, Long.valueOf(j2));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z2, boolean z3, Long l2) {
        zza(new zzel(this, l2, str, str2, bundle, z2, z3));
    }

    public final void zza(int i2, String str, Object obj, Object obj2, Object obj3) {
        zza(new zzec(this, false, 5, str, obj, null, null));
    }

    public final void zza(com.google.android.gms.measurement.internal.zzim zzimVar) {
        Preconditions.checkNotNull(zzimVar);
        synchronized (this.zzf) {
            for (int i2 = 0; i2 < this.zzf.size(); i2++) {
                try {
                    if (zzimVar.equals(this.zzf.get(i2).first)) {
                        Log.w(this.zzc, "OnEventListener already registered.");
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            zzb zzbVar = new zzb(zzimVar);
            this.zzf.add(new Pair<>(zzimVar, zzbVar));
            if (this.zzj != null) {
                try {
                    this.zzj.registerOnMeasurementEventListener(zzbVar);
                    return;
                } catch (BadParcelableException | NetworkOnMainThreadException | RemoteException | IllegalArgumentException | IllegalStateException | NullPointerException | SecurityException | UnsupportedOperationException unused) {
                    Log.w(this.zzc, "Failed to register event listener on calling thread. Trying again on the dynamite thread.");
                }
            }
            zza(new zzej(this, zzbVar));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zza zzaVar) {
        this.zzd.execute(zzaVar);
    }

    public final void zza(Bundle bundle) {
        zza(new zzdh(this, bundle));
    }

    public final void zza(Activity activity, String str, String str2) {
        zza(new zzdl(this, activity, str, str2));
    }

    public final void zza(boolean z2) {
        zza(new zzeh(this, z2));
    }

    public final void zza(com.google.android.gms.measurement.internal.zzij zzijVar) {
        zzc zzcVar = new zzc(zzijVar);
        if (this.zzj != null) {
            try {
                this.zzj.setEventInterceptor(zzcVar);
                return;
            } catch (BadParcelableException | NetworkOnMainThreadException | RemoteException | IllegalArgumentException | IllegalStateException | NullPointerException | SecurityException | UnsupportedOperationException unused) {
                Log.w(this.zzc, "Failed to set event interceptor on calling thread. Trying again on the dynamite thread.");
            }
        }
        zza(new zzee(this, zzcVar));
    }

    public final void zza(Boolean bool) {
        zza(new zzdo(this, bool));
    }

    public final void zza(long j2) {
        zza(new zzds(this, j2));
    }

    public final void zza(String str, String str2, Object obj, boolean z2) {
        zza(new zzen(this, str, str2, obj, z2));
    }
}
