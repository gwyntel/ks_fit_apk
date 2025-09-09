package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzlf implements zzpw {
    private final zzld zza;

    private zzlf(zzld zzldVar) {
        zzld zzldVar2 = (zzld) zzlz.c(zzldVar, "output");
        this.zza = zzldVar2;
        zzldVar2.f13223a = this;
    }

    public static zzlf zza(zzld zzldVar) {
        zzlf zzlfVar = zzldVar.f13223a;
        return zzlfVar != null ? zzlfVar : new zzlf(zzldVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzb(int i2, List<Double> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzb(i2, list.get(i3).doubleValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZza = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZza += zzld.zza(list.get(i4).doubleValue());
        }
        this.zza.zzk(iZza);
        while (i3 < list.size()) {
            this.zza.zzb(list.get(i3).doubleValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzc(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzh(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZza = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZza += zzld.zza(list.get(i4).intValue());
        }
        this.zza.zzk(iZza);
        while (i3 < list.size()) {
            this.zza.zzi(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzd(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzg(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZzb = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzb += zzld.zzb(list.get(i4).intValue());
        }
        this.zza.zzk(iZzb);
        while (i3 < list.size()) {
            this.zza.zzh(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zze(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzf(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZza = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZza += zzld.zza(list.get(i4).longValue());
        }
        this.zza.zzk(iZza);
        while (i3 < list.size()) {
            this.zza.zzf(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzf(int i2, List<Float> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzb(i2, list.get(i3).floatValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZza = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZza += zzld.zza(list.get(i4).floatValue());
        }
        this.zza.zzk(iZza);
        while (i3 < list.size()) {
            this.zza.zzb(list.get(i3).floatValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzg(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzh(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZzc = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzc += zzld.zzc(list.get(i4).intValue());
        }
        this.zza.zzk(iZzc);
        while (i3 < list.size()) {
            this.zza.zzi(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzh(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzh(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZzb = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzb += zzld.zzb(list.get(i4).longValue());
        }
        this.zza.zzk(iZzb);
        while (i3 < list.size()) {
            this.zza.zzh(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzi(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzg(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZzd = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzd += zzld.zzd(list.get(i4).intValue());
        }
        this.zza.zzk(iZzd);
        while (i3 < list.size()) {
            this.zza.zzh(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzj(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzf(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZzc = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzc += zzld.zzc(list.get(i4).longValue());
        }
        this.zza.zzk(iZzc);
        while (i3 < list.size()) {
            this.zza.zzf(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzk(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzi(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZze = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZze += zzld.zze(list.get(i4).intValue());
        }
        this.zza.zzk(iZze);
        while (i3 < list.size()) {
            this.zza.zzj(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzl(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzg(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZzd = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzd += zzld.zzd(list.get(i4).longValue());
        }
        this.zza.zzk(iZzd);
        while (i3 < list.size()) {
            this.zza.zzg(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzm(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzk(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZzg = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzg += zzld.zzg(list.get(i4).intValue());
        }
        this.zza.zzk(iZzg);
        while (i3 < list.size()) {
            this.zza.zzk(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzn(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zza.zzh(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zza.zzj(i2, 2);
        int iZze = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZze += zzld.zze(list.get(i4).longValue());
        }
        this.zza.zzk(iZze);
        while (i3 < list.size()) {
            this.zza.zzh(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final int zza() {
        return zzpv.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, boolean z2) throws IOException {
        this.zza.zzb(i2, z2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, List<Boolean> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zza.zzj(i2, 2);
            int iZza = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZza += zzld.zza(list.get(i4).booleanValue());
            }
            this.zza.zzk(iZza);
            while (i3 < list.size()) {
                this.zza.zzb(list.get(i3).booleanValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zza.zzb(i2, list.get(i3).booleanValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzb(int i2, int i3) throws IOException {
        this.zza.zzg(i2, i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzd(int i2, int i3) throws IOException {
        this.zza.zzg(i2, i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zze(int i2, int i3) throws IOException {
        this.zza.zzi(i2, i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzf(int i2, int i3) throws IOException {
        this.zza.zzk(i2, i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzb(int i2, long j2) throws IOException {
        this.zza.zzh(i2, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zze(int i2, long j2) throws IOException {
        this.zza.zzh(i2, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzc(int i2, int i3) throws IOException {
        this.zza.zzh(i2, i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzd(int i2, long j2) throws IOException {
        this.zza.zzg(i2, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzb(int i2, Object obj, zzob zzobVar) throws IOException {
        this.zza.e(i2, (zznj) obj, zzobVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzc(int i2, long j2) throws IOException {
        this.zza.zzf(i2, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, zzkm zzkmVar) throws IOException {
        this.zza.zzc(i2, zzkmVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzb(int i2, List<?> list, zzob zzobVar) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            zzb(i2, list.get(i3), zzobVar);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, List<zzkm> list) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            this.zza.zzc(i2, list.get(i3));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    @Deprecated
    public final void zzb(int i2) throws IOException {
        this.zza.zzj(i2, 3);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, double d2) throws IOException {
        this.zza.zzb(i2, d2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zzb(int i2, List<String> list) throws IOException {
        int i3 = 0;
        if (list instanceof zzmp) {
            zzmp zzmpVar = (zzmp) list;
            while (i3 < list.size()) {
                Object objZzb = zzmpVar.zzb(i3);
                if (objZzb instanceof String) {
                    this.zza.zzb(i2, (String) objZzb);
                } else {
                    this.zza.zzc(i2, (zzkm) objZzb);
                }
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zza.zzb(i2, list.get(i3));
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    @Deprecated
    public final void zza(int i2) throws IOException {
        this.zza.zzj(i2, 4);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, int i3) throws IOException {
        this.zza.zzh(i2, i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, long j2) throws IOException {
        this.zza.zzf(i2, j2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, float f2) throws IOException {
        this.zza.zzb(i2, f2);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, Object obj, zzob zzobVar) throws IOException {
        zzld zzldVar = this.zza;
        zzldVar.zzj(i2, 3);
        zzobVar.zza((zzob) obj, (zzpw) zzldVar.f13223a);
        zzldVar.zzj(i2, 4);
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, List<?> list, zzob zzobVar) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            zza(i2, list.get(i3), zzobVar);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final <K, V> void zza(int i2, zzna<K, V> zznaVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zza.zzj(i2, 2);
            this.zza.zzk(zznb.a(zznaVar, entry.getKey(), entry.getValue()));
            zznb.b(this.zza, zznaVar, entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, Object obj) throws IOException {
        if (obj instanceof zzkm) {
            this.zza.zzd(i2, (zzkm) obj);
        } else {
            this.zza.zzb(i2, (zznj) obj);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzpw
    public final void zza(int i2, String str) throws IOException {
        this.zza.zzb(i2, str);
    }
}
