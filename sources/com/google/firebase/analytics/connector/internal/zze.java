package com.google.firebase.analytics.connector.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public final class zze implements zza {

    /* renamed from: a, reason: collision with root package name */
    Set f14991a;
    private AnalyticsConnector.AnalyticsConnectorListener zzb;
    private AppMeasurementSdk zzc;
    private zzd zzd;

    public zze(AppMeasurementSdk appMeasurementSdk, AnalyticsConnector.AnalyticsConnectorListener analyticsConnectorListener) {
        this.zzb = analyticsConnectorListener;
        this.zzc = appMeasurementSdk;
        zzd zzdVar = new zzd(this);
        this.zzd = zzdVar;
        this.zzc.registerOnMeasurementEventListener(zzdVar);
        this.f14991a = new HashSet();
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final AnalyticsConnector.AnalyticsConnectorListener zza() {
        return this.zzb;
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final void zzb() {
        this.f14991a.clear();
    }

    @Override // com.google.firebase.analytics.connector.internal.zza
    public final void zza(Set<String> set) {
        this.f14991a.clear();
        Set set2 = this.f14991a;
        HashSet hashSet = new HashSet();
        for (String str : set) {
            if (hashSet.size() >= 50) {
                break;
            }
            if (zzb.zzc(str) && zzb.zzd(str)) {
                String strZzb = zzb.zzb(str);
                Preconditions.checkNotNull(strZzb);
                hashSet.add(strZzb);
            }
        }
        set2.addAll(hashSet);
    }
}
