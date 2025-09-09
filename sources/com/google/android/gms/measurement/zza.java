package com.google.android.gms.measurement;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.internal.zzhc;
import com.google.android.gms.measurement.internal.zzij;
import com.google.android.gms.measurement.internal.zzim;
import com.google.android.gms.measurement.internal.zzin;
import com.google.android.gms.measurement.internal.zzmz;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zza extends AppMeasurement.zza {
    private final zzhc zza;
    private final zzin zzb;

    public zza(@NonNull zzhc zzhcVar) {
        super();
        Preconditions.checkNotNull(zzhcVar);
        this.zza = zzhcVar;
        this.zzb = zzhcVar.zzp();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final int zza(String str) {
        Preconditions.checkNotEmpty(str);
        return 25;
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Boolean zzb() {
        return this.zzb.zzaa();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Double zzc() {
        return this.zzb.zzab();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Integer zzd() {
        return this.zzb.zzac();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Long zze() {
        return this.zzb.zzad();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzf() {
        return this.zzb.zzae();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzg() {
        return this.zzb.zzaf();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzh() {
        return this.zzb.zzag();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final String zzi() {
        return this.zzb.zzae();
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final String zzj() {
        return this.zzb.zzai();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final long zza() {
        return this.zza.zzt().zzm();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzb(String str) throws IllegalStateException {
        this.zza.zze().zza(str, this.zza.zzb().elapsedRealtime());
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzc(String str) throws IllegalStateException {
        this.zza.zze().zzb(str, this.zza.zzb().elapsedRealtime());
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final Object zza(int i2) {
        if (i2 == 0) {
            return zzj();
        }
        if (i2 == 1) {
            return zze();
        }
        if (i2 == 2) {
            return zzc();
        }
        if (i2 == 3) {
            return zzd();
        }
        if (i2 != 4) {
            return null;
        }
        return zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzb(String str, String str2, Bundle bundle) throws IllegalStateException {
        this.zzb.zzb(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zzb(zzim zzimVar) {
        this.zzb.zzb(zzimVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final List<Bundle> zza(String str, String str2) {
        return this.zzb.zza(str, str2);
    }

    @Override // com.google.android.gms.measurement.AppMeasurement.zza
    public final Map<String, Object> zza(boolean z2) {
        List<zzmz> listZza = this.zzb.zza(z2);
        ArrayMap arrayMap = new ArrayMap(listZza.size());
        for (zzmz zzmzVar : listZza) {
            Object objZza = zzmzVar.zza();
            if (objZza != null) {
                arrayMap.put(zzmzVar.zza, objZza);
            }
        }
        return arrayMap;
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final Map<String, Object> zza(String str, String str2, boolean z2) {
        return this.zzb.zza(str, str2, z2);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(String str, String str2, Bundle bundle) throws IllegalStateException {
        this.zza.zzp().zza(str, str2, bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(String str, String str2, Bundle bundle, long j2) throws IllegalStateException {
        this.zzb.zza(str, str2, bundle, true, false, j2);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(zzim zzimVar) {
        this.zzb.zza(zzimVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(Bundle bundle) throws IllegalStateException {
        this.zzb.zzb(bundle);
    }

    @Override // com.google.android.gms.measurement.internal.zzka
    public final void zza(zzij zzijVar) {
        this.zzb.zza(zzijVar);
    }
}
