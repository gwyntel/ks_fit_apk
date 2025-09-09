package com.google.android.gms.measurement;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.internal.zzij;
import com.google.android.gms.measurement.internal.zzim;
import com.google.android.gms.measurement.internal.zzka;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzc extends AppMeasurement.zza {
    private final zzka zza;

    public zzc(zzka zzkaVar) {
        super();
        Preconditions.checkNotNull(zzkaVar);
        this.zza = zzkaVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final int zza(String str) {
        return this.zza.zza(str);
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Boolean zzb() {
        return (Boolean) this.zza.zza(4);
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Double zzc() {
        return (Double) this.zza.zza(2);
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Integer zzd() {
        return (Integer) this.zza.zza(3);
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Long zze() {
        return (Long) this.zza.zza(1);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzf() {
        return this.zza.zzf();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzg() {
        return this.zza.zzg();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzh() {
        return this.zza.zzh();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzi() {
        return this.zza.zzi();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final String zzj() {
        return (String) this.zza.zza(0);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final long zza() {
        return this.zza.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzb(String str) {
        this.zza.zzb(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzc(String str) {
        this.zza.zzc(str);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final Object zza(int i2) {
        return this.zza.zza(i2);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzb(String str, String str2, Bundle bundle) {
        this.zza.zzb(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final List<Bundle> zza(String str, String str2) {
        return this.zza.zza(str, str2);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzb(zzim zzimVar) {
        this.zza.zzb(zzimVar);
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Map<String, Object> zza(boolean z2) {
        return this.zza.zza((String) null, (String) null, z2);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final Map<String, Object> zza(String str, String str2, boolean z2) {
        return this.zza.zza(str, str2, z2);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(String str, String str2, Bundle bundle) {
        this.zza.zza(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(String str, String str2, Bundle bundle, long j2) {
        this.zza.zza(str, str2, bundle, j2);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(zzim zzimVar) {
        this.zza.zza(zzimVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(Bundle bundle) {
        this.zza.zza(bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(zzij zzijVar) {
        this.zza.zza(zzijVar);
    }
}
