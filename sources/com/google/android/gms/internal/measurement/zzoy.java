package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes3.dex */
final class zzoy extends zzow<zzoz, zzoz> {
    zzoy() {
    }

    private static void zza(Object obj, zzoz zzozVar) {
        ((zzlw) obj).zzb = zzozVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ int a(Object obj) {
        return ((zzoz) obj).zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ Object b() {
        return zzoz.f();
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ Object c(Object obj, Object obj2) {
        zzoz zzozVar = (zzoz) obj;
        zzoz zzozVar2 = (zzoz) obj2;
        return zzoz.zzc().equals(zzozVar2) ? zzozVar : zzoz.zzc().equals(zzozVar) ? zzoz.b(zzozVar, zzozVar2) : zzozVar.a(zzozVar2);
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void d(Object obj, int i2, int i3) {
        ((zzoz) obj).c((i2 << 3) | 5, Integer.valueOf(i3));
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void e(Object obj, int i2, long j2) {
        ((zzoz) obj).c((i2 << 3) | 1, Long.valueOf(j2));
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void f(Object obj, int i2, zzkm zzkmVar) {
        ((zzoz) obj).c((i2 << 3) | 2, zzkmVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void g(Object obj, int i2, Object obj2) {
        ((zzoz) obj).c((i2 << 3) | 3, (zzoz) obj2);
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void h(Object obj, zzpw zzpwVar) throws IOException {
        ((zzoz) obj).d(zzpwVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final boolean i(zzny zznyVar) {
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ int k(Object obj) {
        return ((zzoz) obj).zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void l(Object obj, int i2, long j2) {
        ((zzoz) obj).c(i2 << 3, Long.valueOf(j2));
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void m(Object obj, zzpw zzpwVar) throws IOException {
        ((zzoz) obj).zzb(zzpwVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void n(Object obj, Object obj2) {
        zza(obj, (zzoz) obj2);
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ Object o(Object obj) {
        zzoz zzozVar = ((zzlw) obj).zzb;
        if (zzozVar != zzoz.zzc()) {
            return zzozVar;
        }
        zzoz zzozVarF = zzoz.f();
        zza(obj, zzozVarF);
        return zzozVarF;
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ void p(Object obj, Object obj2) {
        zza(obj, (zzoz) obj2);
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ Object q(Object obj) {
        return ((zzlw) obj).zzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final /* synthetic */ Object r(Object obj) {
        zzoz zzozVar = (zzoz) obj;
        zzozVar.zze();
        return zzozVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzow
    final void s(Object obj) {
        ((zzlw) obj).zzb.zze();
    }
}
