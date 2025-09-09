package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
interface zzpw {
    int zza();

    @Deprecated
    void zza(int i2) throws IOException;

    void zza(int i2, double d2) throws IOException;

    void zza(int i2, float f2) throws IOException;

    void zza(int i2, int i3) throws IOException;

    void zza(int i2, long j2) throws IOException;

    void zza(int i2, zzkm zzkmVar) throws IOException;

    <K, V> void zza(int i2, zzna<K, V> zznaVar, Map<K, V> map) throws IOException;

    void zza(int i2, Object obj) throws IOException;

    @Deprecated
    void zza(int i2, Object obj, zzob zzobVar) throws IOException;

    void zza(int i2, String str) throws IOException;

    void zza(int i2, List<zzkm> list) throws IOException;

    @Deprecated
    void zza(int i2, List<?> list, zzob zzobVar) throws IOException;

    void zza(int i2, List<Boolean> list, boolean z2) throws IOException;

    void zza(int i2, boolean z2) throws IOException;

    @Deprecated
    void zzb(int i2) throws IOException;

    void zzb(int i2, int i3) throws IOException;

    void zzb(int i2, long j2) throws IOException;

    void zzb(int i2, Object obj, zzob zzobVar) throws IOException;

    void zzb(int i2, List<String> list) throws IOException;

    void zzb(int i2, List<?> list, zzob zzobVar) throws IOException;

    void zzb(int i2, List<Double> list, boolean z2) throws IOException;

    void zzc(int i2, int i3) throws IOException;

    void zzc(int i2, long j2) throws IOException;

    void zzc(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzd(int i2, int i3) throws IOException;

    void zzd(int i2, long j2) throws IOException;

    void zzd(int i2, List<Integer> list, boolean z2) throws IOException;

    void zze(int i2, int i3) throws IOException;

    void zze(int i2, long j2) throws IOException;

    void zze(int i2, List<Long> list, boolean z2) throws IOException;

    void zzf(int i2, int i3) throws IOException;

    void zzf(int i2, List<Float> list, boolean z2) throws IOException;

    void zzg(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzh(int i2, List<Long> list, boolean z2) throws IOException;

    void zzi(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzj(int i2, List<Long> list, boolean z2) throws IOException;

    void zzk(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzl(int i2, List<Long> list, boolean z2) throws IOException;

    void zzm(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzn(int i2, List<Long> list, boolean z2) throws IOException;
}
