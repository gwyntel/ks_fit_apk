package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.bg;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

@DynamiteApi
/* loaded from: classes3.dex */
public class AppMeasurementDynamiteService extends com.google.android.gms.internal.measurement.zzct {

    /* renamed from: a, reason: collision with root package name */
    zzhc f13245a = null;

    @GuardedBy("listenerMap")
    private final Map<Integer, zzim> zzb = new ArrayMap();

    class zza implements zzij {
        private com.google.android.gms.internal.measurement.zzda zza;

        zza(com.google.android.gms.internal.measurement.zzda zzdaVar) {
            this.zza = zzdaVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzij
        public final void interceptEvent(String str, String str2, Bundle bundle, long j2) {
            try {
                this.zza.zza(str, str2, bundle, j2);
            } catch (RemoteException e2) {
                zzhc zzhcVar = AppMeasurementDynamiteService.this.f13245a;
                if (zzhcVar != null) {
                    zzhcVar.zzj().zzu().zza("Event interceptor threw exception", e2);
                }
            }
        }
    }

    class zzb implements zzim {
        private com.google.android.gms.internal.measurement.zzda zza;

        zzb(com.google.android.gms.internal.measurement.zzda zzdaVar) {
            this.zza = zzdaVar;
        }

        @Override // com.google.android.gms.measurement.internal.zzim
        public final void onEvent(String str, String str2, Bundle bundle, long j2) {
            try {
                this.zza.zza(str, str2, bundle, j2);
            } catch (RemoteException e2) {
                zzhc zzhcVar = AppMeasurementDynamiteService.this.f13245a;
                if (zzhcVar != null) {
                    zzhcVar.zzj().zzu().zza("Event listener threw exception", e2);
                }
            }
        }
    }

    @EnsuresNonNull({"scion"})
    private final void zza() {
        if (this.f13245a == null) {
            throw new IllegalStateException("Attempting to perform action before initialize.");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void beginAdUnitExposure(@NonNull String str, long j2) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zze().zza(str, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void clearConditionalUserProperty(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzp().zza(str, str2, bundle);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void clearMeasurementEnabled(long j2) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzp().zza((Boolean) null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void endAdUnitExposure(@NonNull String str, long j2) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zze().zzb(str, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void generateEventId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        long jZzm = this.f13245a.zzt().zzm();
        zza();
        this.f13245a.zzt().zza(zzcvVar, jZzm);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getAppInstanceId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzl().zzb(new zzi(this, zzcvVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getCachedAppInstanceId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.f13245a.zzp().zzae());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getConditionalUserProperties(String str, String str2, com.google.android.gms.internal.measurement.zzcv zzcvVar) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzl().zzb(new zzl(this, zzcvVar, str, str2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getCurrentScreenClass(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.f13245a.zzp().zzaf());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getCurrentScreenName(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.f13245a.zzp().zzag());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getGmpAppId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        zza(zzcvVar, this.f13245a.zzp().zzah());
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getMaxUserProperties(String str, com.google.android.gms.internal.measurement.zzcv zzcvVar) throws RemoteException {
        zza();
        this.f13245a.zzp();
        Preconditions.checkNotEmpty(str);
        zza();
        this.f13245a.zzt().zza(zzcvVar, 25);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getSessionId(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws IllegalStateException, RemoteException {
        zza();
        zzin zzinVarZzp = this.f13245a.zzp();
        zzinVarZzp.zzl().zzb(new zzjn(zzinVarZzp, zzcvVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getTestFlag(com.google.android.gms.internal.measurement.zzcv zzcvVar, int i2) throws RemoteException {
        zza();
        if (i2 == 0) {
            this.f13245a.zzt().zza(zzcvVar, this.f13245a.zzp().zzai());
            return;
        }
        if (i2 == 1) {
            this.f13245a.zzt().zza(zzcvVar, this.f13245a.zzp().zzad().longValue());
            return;
        }
        if (i2 != 2) {
            if (i2 == 3) {
                this.f13245a.zzt().zza(zzcvVar, this.f13245a.zzp().zzac().intValue());
                return;
            } else {
                if (i2 != 4) {
                    return;
                }
                this.f13245a.zzt().zza(zzcvVar, this.f13245a.zzp().zzaa().booleanValue());
                return;
            }
        }
        zzne zzneVarZzt = this.f13245a.zzt();
        double dDoubleValue = this.f13245a.zzp().zzab().doubleValue();
        Bundle bundle = new Bundle();
        bundle.putDouble("r", dDoubleValue);
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            zzneVarZzt.f13286a.zzj().zzu().zza("Error returning double value to wrapper", e2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void getUserProperties(String str, String str2, boolean z2, com.google.android.gms.internal.measurement.zzcv zzcvVar) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzl().zzb(new zzj(this, zzcvVar, str, str2, z2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void initForTests(@NonNull Map map) throws RemoteException {
        zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void initialize(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzdd zzddVar, long j2) throws RemoteException {
        zzhc zzhcVar = this.f13245a;
        if (zzhcVar == null) {
            this.f13245a = zzhc.zza((Context) Preconditions.checkNotNull((Context) ObjectWrapper.unwrap(iObjectWrapper)), zzddVar, Long.valueOf(j2));
        } else {
            zzhcVar.zzj().zzu().zza("Attempting to initialize multiple times");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void isDataCollectionEnabled(com.google.android.gms.internal.measurement.zzcv zzcvVar) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzl().zzb(new zzn(this, zzcvVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void logEvent(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle, boolean z2, boolean z3, long j2) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzp().zza(str, str2, bundle, z2, z3, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void logEventAndBundle(String str, String str2, Bundle bundle, com.google.android.gms.internal.measurement.zzcv zzcvVar, long j2) throws IllegalStateException, RemoteException {
        zza();
        Preconditions.checkNotEmpty(str2);
        (bundle != null ? new Bundle(bundle) : new Bundle()).putString("_o", PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
        this.f13245a.zzl().zzb(new zzk(this, zzcvVar, new zzbg(str2, new zzbb(bundle), PushConstants.EXTRA_APPLICATION_PENDING_INTENT, j2), str));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void logHealthData(int i2, @NonNull String str, @NonNull IObjectWrapper iObjectWrapper, @NonNull IObjectWrapper iObjectWrapper2, @NonNull IObjectWrapper iObjectWrapper3) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzj().g(i2, true, false, str, iObjectWrapper == null ? null : ObjectWrapper.unwrap(iObjectWrapper), iObjectWrapper2 == null ? null : ObjectWrapper.unwrap(iObjectWrapper2), iObjectWrapper3 != null ? ObjectWrapper.unwrap(iObjectWrapper3) : null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityCreated(@NonNull IObjectWrapper iObjectWrapper, @NonNull Bundle bundle, long j2) throws RemoteException {
        zza();
        zzjy zzjyVar = this.f13245a.zzp().f13297b;
        if (zzjyVar != null) {
            this.f13245a.zzp().zzak();
            zzjyVar.onActivityCreated((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityDestroyed(@NonNull IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        zza();
        zzjy zzjyVar = this.f13245a.zzp().f13297b;
        if (zzjyVar != null) {
            this.f13245a.zzp().zzak();
            zzjyVar.onActivityDestroyed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityPaused(@NonNull IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        zza();
        zzjy zzjyVar = this.f13245a.zzp().f13297b;
        if (zzjyVar != null) {
            this.f13245a.zzp().zzak();
            zzjyVar.onActivityPaused((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityResumed(@NonNull IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        zza();
        zzjy zzjyVar = this.f13245a.zzp().f13297b;
        if (zzjyVar != null) {
            this.f13245a.zzp().zzak();
            zzjyVar.onActivityResumed((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivitySaveInstanceState(IObjectWrapper iObjectWrapper, com.google.android.gms.internal.measurement.zzcv zzcvVar, long j2) throws RemoteException {
        zza();
        zzjy zzjyVar = this.f13245a.zzp().f13297b;
        Bundle bundle = new Bundle();
        if (zzjyVar != null) {
            this.f13245a.zzp().zzak();
            zzjyVar.onActivitySaveInstanceState((Activity) ObjectWrapper.unwrap(iObjectWrapper), bundle);
        }
        try {
            zzcvVar.zza(bundle);
        } catch (RemoteException e2) {
            this.f13245a.zzj().zzu().zza("Error returning bundle value to wrapper", e2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityStarted(@NonNull IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        zza();
        zzjy zzjyVar = this.f13245a.zzp().f13297b;
        if (zzjyVar != null) {
            this.f13245a.zzp().zzak();
            zzjyVar.onActivityStarted((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void onActivityStopped(@NonNull IObjectWrapper iObjectWrapper, long j2) throws RemoteException {
        zza();
        zzjy zzjyVar = this.f13245a.zzp().f13297b;
        if (zzjyVar != null) {
            this.f13245a.zzp().zzak();
            zzjyVar.onActivityStopped((Activity) ObjectWrapper.unwrap(iObjectWrapper));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void performAction(Bundle bundle, com.google.android.gms.internal.measurement.zzcv zzcvVar, long j2) throws RemoteException {
        zza();
        zzcvVar.zza(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void registerOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzda zzdaVar) throws RemoteException {
        zzim zzbVar;
        zza();
        synchronized (this.zzb) {
            try {
                zzbVar = this.zzb.get(Integer.valueOf(zzdaVar.zza()));
                if (zzbVar == null) {
                    zzbVar = new zzb(zzdaVar);
                    this.zzb.put(Integer.valueOf(zzdaVar.zza()), zzbVar);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.f13245a.zzp().zza(zzbVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void resetAnalyticsData(long j2) throws IllegalStateException, RemoteException {
        zza();
        zzin zzinVarZzp = this.f13245a.zzp();
        zzinVarZzp.k(null);
        zzinVarZzp.zzl().zzb(new zzjh(zzinVarZzp, j2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setConditionalUserProperty(@NonNull Bundle bundle, long j2) throws IllegalStateException, RemoteException {
        zza();
        if (bundle == null) {
            this.f13245a.zzj().zzg().zza("Conditional user property must not be null");
        } else {
            this.f13245a.zzp().zza(bundle, j2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setConsent(@NonNull final Bundle bundle, final long j2) throws IllegalStateException, RemoteException {
        zza();
        final zzin zzinVarZzp = this.f13245a.zzp();
        zzinVarZzp.zzl().zzc(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzit
            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException {
                zzin zzinVar = zzinVarZzp;
                Bundle bundle2 = bundle;
                long j3 = j2;
                if (TextUtils.isEmpty(zzinVar.zzg().zzae())) {
                    zzinVar.d(bundle2, 0, j3);
                } else {
                    zzinVar.zzj().zzv().zza("Using developer consent only; google app id found");
                }
            }
        });
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setConsentThirdParty(@NonNull Bundle bundle, long j2) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzp().d(bundle, -20, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setCurrentScreen(@NonNull IObjectWrapper iObjectWrapper, @NonNull String str, @NonNull String str2, long j2) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzq().zza((Activity) ObjectWrapper.unwrap(iObjectWrapper), str, str2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setDataCollectionEnabled(boolean z2) throws IllegalStateException, RemoteException {
        zza();
        zzin zzinVarZzp = this.f13245a.zzp();
        zzinVarZzp.zzu();
        zzinVarZzp.zzl().zzb(new zzjc(zzinVarZzp, z2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setDefaultEventParameters(@NonNull Bundle bundle) throws IllegalStateException {
        zza();
        final zzin zzinVarZzp = this.f13245a.zzp();
        final Bundle bundle2 = bundle == null ? null : new Bundle(bundle);
        zzinVarZzp.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zziu
            @Override // java.lang.Runnable
            public final void run() {
                zzinVarZzp.zza(bundle2);
            }
        });
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setEventInterceptor(com.google.android.gms.internal.measurement.zzda zzdaVar) throws IllegalStateException, RemoteException {
        zza();
        zza zzaVar = new zza(zzdaVar);
        if (this.f13245a.zzl().zzg()) {
            this.f13245a.zzp().zza(zzaVar);
        } else {
            this.f13245a.zzl().zzb(new zzm(this, zzaVar));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setInstanceIdProvider(com.google.android.gms.internal.measurement.zzdb zzdbVar) throws RemoteException {
        zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setMeasurementEnabled(boolean z2, long j2) throws IllegalStateException, RemoteException {
        zza();
        this.f13245a.zzp().zza(Boolean.valueOf(z2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setMinimumSessionDuration(long j2) throws RemoteException {
        zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setSessionTimeoutDuration(long j2) throws IllegalStateException, RemoteException {
        zza();
        zzin zzinVarZzp = this.f13245a.zzp();
        zzinVarZzp.zzl().zzb(new zzje(zzinVarZzp, j2));
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setUserId(@NonNull final String str, long j2) throws IllegalStateException, RemoteException {
        zza();
        final zzin zzinVarZzp = this.f13245a.zzp();
        if (str != null && TextUtils.isEmpty(str)) {
            zzinVarZzp.f13286a.zzj().zzu().zza("User ID must be non-empty or null");
        } else {
            zzinVarZzp.zzl().zzb(new Runnable() { // from class: com.google.android.gms.measurement.internal.zziv
                @Override // java.lang.Runnable
                public final void run() {
                    zzin zzinVar = zzinVarZzp;
                    if (zzinVar.zzg().i(str)) {
                        zzinVar.zzg().h();
                    }
                }
            });
            zzinVarZzp.zza(null, bg.f21483d, str, true, j2);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void setUserProperty(@NonNull String str, @NonNull String str2, @NonNull IObjectWrapper iObjectWrapper, boolean z2, long j2) throws IllegalStateException, SecurityException, RemoteException {
        zza();
        this.f13245a.zzp().zza(str, str2, ObjectWrapper.unwrap(iObjectWrapper), z2, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzcu
    public void unregisterOnMeasurementEventListener(com.google.android.gms.internal.measurement.zzda zzdaVar) throws RemoteException {
        zzim zzimVarRemove;
        zza();
        synchronized (this.zzb) {
            zzimVarRemove = this.zzb.remove(Integer.valueOf(zzdaVar.zza()));
        }
        if (zzimVarRemove == null) {
            zzimVarRemove = new zzb(zzdaVar);
        }
        this.f13245a.zzp().zzb(zzimVarRemove);
    }

    private final void zza(com.google.android.gms.internal.measurement.zzcv zzcvVar, String str) {
        zza();
        this.f13245a.zzt().zza(zzcvVar, str);
    }
}
