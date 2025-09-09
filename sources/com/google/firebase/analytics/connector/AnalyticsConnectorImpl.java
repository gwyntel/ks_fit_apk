package com.google.firebase.analytics.connector;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.annotation.Size;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzdf;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.analytics.connector.internal.zze;
import com.google.firebase.analytics.connector.internal.zzg;
import com.google.firebase.events.Event;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Subscriber;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class AnalyticsConnectorImpl implements AnalyticsConnector {
    private static volatile AnalyticsConnector zzb;

    /* renamed from: a, reason: collision with root package name */
    final Map f14990a;

    @VisibleForTesting
    private final AppMeasurementSdk zzc;

    private AnalyticsConnectorImpl(AppMeasurementSdk appMeasurementSdk) {
        Preconditions.checkNotNull(appMeasurementSdk);
        this.zzc = appMeasurementSdk;
        this.f14990a = new ConcurrentHashMap();
    }

    static /* synthetic */ void a(Event event) {
        boolean z2 = ((DataCollectionDefaultChange) event.getPayload()).enabled;
        synchronized (AnalyticsConnectorImpl.class) {
            ((AnalyticsConnectorImpl) Preconditions.checkNotNull(zzb)).zzc.zza(z2);
        }
    }

    @NonNull
    @KeepForSdk
    public static AnalyticsConnector getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zza(@NonNull String str) {
        return (str.isEmpty() || !this.f14990a.containsKey(str) || this.f14990a.get(str) == null) ? false : true;
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @KeepForSdk
    public void clearConditionalUserProperty(@NonNull @Size(max = 24, min = 1) String str, @NonNull String str2, @NonNull Bundle bundle) {
        if (str2 == null || com.google.firebase.analytics.connector.internal.zzb.zza(str2, bundle)) {
            this.zzc.clearConditionalUserProperty(str, str2, bundle);
        }
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @NonNull
    @KeepForSdk
    @WorkerThread
    public List<AnalyticsConnector.ConditionalUserProperty> getConditionalUserProperties(@NonNull String str, @NonNull @Size(max = 23, min = 1) String str2) {
        ArrayList arrayList = new ArrayList();
        Iterator<Bundle> it = this.zzc.getConditionalUserProperties(str, str2).iterator();
        while (it.hasNext()) {
            arrayList.add(com.google.firebase.analytics.connector.internal.zzb.zza(it.next()));
        }
        return arrayList;
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @KeepForSdk
    @WorkerThread
    public int getMaxUserProperties(@NonNull @Size(min = 1) String str) {
        return this.zzc.getMaxUserProperties(str);
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @NonNull
    @KeepForSdk
    @WorkerThread
    public Map<String, Object> getUserProperties(boolean z2) {
        return this.zzc.getUserProperties(null, null, z2);
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @KeepForSdk
    public void logEvent(@NonNull String str, @NonNull String str2, @NonNull Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (com.google.firebase.analytics.connector.internal.zzb.zzf(str) && com.google.firebase.analytics.connector.internal.zzb.zza(str2, bundle) && com.google.firebase.analytics.connector.internal.zzb.zzb(str, str2, bundle)) {
            com.google.firebase.analytics.connector.internal.zzb.zza(str, str2, bundle);
            this.zzc.logEvent(str, str2, bundle);
        }
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @NonNull
    @KeepForSdk
    @WorkerThread
    public AnalyticsConnector.AnalyticsConnectorHandle registerAnalyticsConnectorListener(@NonNull final String str, @NonNull AnalyticsConnector.AnalyticsConnectorListener analyticsConnectorListener) {
        Preconditions.checkNotNull(analyticsConnectorListener);
        if (!com.google.firebase.analytics.connector.internal.zzb.zzf(str) || zza(str)) {
            return null;
        }
        AppMeasurementSdk appMeasurementSdk = this.zzc;
        com.google.firebase.analytics.connector.internal.zza zzeVar = AppMeasurement.FIAM_ORIGIN.equals(str) ? new zze(appMeasurementSdk, analyticsConnectorListener) : "clx".equals(str) ? new zzg(appMeasurementSdk, analyticsConnectorListener) : null;
        if (zzeVar == null) {
            return null;
        }
        this.f14990a.put(str, zzeVar);
        return new AnalyticsConnector.AnalyticsConnectorHandle() { // from class: com.google.firebase.analytics.connector.AnalyticsConnectorImpl.1
            @Override // com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorHandle
            @KeepForSdk
            public void registerEventNames(Set<String> set) {
                if (!AnalyticsConnectorImpl.this.zza(str) || !str.equals(AppMeasurement.FIAM_ORIGIN) || set == null || set.isEmpty()) {
                    return;
                }
                ((com.google.firebase.analytics.connector.internal.zza) AnalyticsConnectorImpl.this.f14990a.get(str)).zza(set);
            }

            @Override // com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorHandle
            public void unregister() {
                if (AnalyticsConnectorImpl.this.zza(str)) {
                    AnalyticsConnector.AnalyticsConnectorListener analyticsConnectorListenerZza = ((com.google.firebase.analytics.connector.internal.zza) AnalyticsConnectorImpl.this.f14990a.get(str)).zza();
                    if (analyticsConnectorListenerZza != null) {
                        analyticsConnectorListenerZza.onMessageTriggered(0, null);
                    }
                    AnalyticsConnectorImpl.this.f14990a.remove(str);
                }
            }

            @Override // com.google.firebase.analytics.connector.AnalyticsConnector.AnalyticsConnectorHandle
            @KeepForSdk
            public void unregisterEventNames() {
                if (AnalyticsConnectorImpl.this.zza(str) && str.equals(AppMeasurement.FIAM_ORIGIN)) {
                    ((com.google.firebase.analytics.connector.internal.zza) AnalyticsConnectorImpl.this.f14990a.get(str)).zzb();
                }
            }
        };
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @KeepForSdk
    public void setConditionalUserProperty(@NonNull AnalyticsConnector.ConditionalUserProperty conditionalUserProperty) {
        if (com.google.firebase.analytics.connector.internal.zzb.zzb(conditionalUserProperty)) {
            this.zzc.setConditionalUserProperty(com.google.firebase.analytics.connector.internal.zzb.zza(conditionalUserProperty));
        }
    }

    @Override // com.google.firebase.analytics.connector.AnalyticsConnector
    @KeepForSdk
    public void setUserProperty(@NonNull String str, @NonNull String str2, @NonNull Object obj) {
        if (com.google.firebase.analytics.connector.internal.zzb.zzf(str) && com.google.firebase.analytics.connector.internal.zzb.zza(str, str2)) {
            this.zzc.setUserProperty(str, str2, obj);
        }
    }

    @NonNull
    @KeepForSdk
    public static AnalyticsConnector getInstance(@NonNull FirebaseApp firebaseApp) {
        return (AnalyticsConnector) firebaseApp.get(AnalyticsConnector.class);
    }

    @NonNull
    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.WAKE_LOCK"})
    @KeepForSdk
    public static AnalyticsConnector getInstance(@NonNull FirebaseApp firebaseApp, @NonNull Context context, @NonNull Subscriber subscriber) {
        Preconditions.checkNotNull(firebaseApp);
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzb == null) {
            synchronized (AnalyticsConnectorImpl.class) {
                try {
                    if (zzb == null) {
                        Bundle bundle = new Bundle(1);
                        if (firebaseApp.isDefaultApp()) {
                            subscriber.subscribe(DataCollectionDefaultChange.class, new Executor() { // from class: com.google.firebase.analytics.connector.zza
                                @Override // java.util.concurrent.Executor
                                public final void execute(Runnable runnable) {
                                    runnable.run();
                                }
                            }, new EventHandler() { // from class: com.google.firebase.analytics.connector.zzb
                                @Override // com.google.firebase.events.EventHandler
                                public final void handle(Event event) {
                                    AnalyticsConnectorImpl.a(event);
                                }
                            });
                            bundle.putBoolean("dataCollectionDefaultEnabled", firebaseApp.isDataCollectionDefaultEnabled());
                        }
                        zzb = new AnalyticsConnectorImpl(zzdf.zza(context, (String) null, (String) null, (String) null, bundle).zzb());
                    }
                } finally {
                }
            }
        }
        return zzb;
    }
}
