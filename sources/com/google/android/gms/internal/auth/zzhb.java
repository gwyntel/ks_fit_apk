package com.google.android.gms.internal.auth;

/* loaded from: classes3.dex */
final class zzhb extends zzgz {
    zzhb() {
    }

    @Override // com.google.android.gms.internal.auth.zzgz
    final /* bridge */ /* synthetic */ Object a(Object obj) {
        zzev zzevVar = (zzev) obj;
        zzha zzhaVar = zzevVar.zzc;
        if (zzhaVar != zzha.zza()) {
            return zzhaVar;
        }
        zzha zzhaVarC = zzha.c();
        zzevVar.zzc = zzhaVarC;
        return zzhaVarC;
    }

    @Override // com.google.android.gms.internal.auth.zzgz
    final /* synthetic */ Object b(Object obj) {
        return ((zzev) obj).zzc;
    }

    @Override // com.google.android.gms.internal.auth.zzgz
    final /* bridge */ /* synthetic */ Object c(Object obj, Object obj2) {
        if (zzha.zza().equals(obj2)) {
            return obj;
        }
        if (zzha.zza().equals(obj)) {
            return zzha.b((zzha) obj, (zzha) obj2);
        }
        ((zzha) obj).a((zzha) obj2);
        return obj;
    }

    @Override // com.google.android.gms.internal.auth.zzgz
    final /* bridge */ /* synthetic */ void d(Object obj, int i2, long j2) {
        ((zzha) obj).f(i2 << 3, Long.valueOf(j2));
    }

    @Override // com.google.android.gms.internal.auth.zzgz
    final void e(Object obj) {
        ((zzev) obj).zzc.zzf();
    }

    @Override // com.google.android.gms.internal.auth.zzgz
    final /* synthetic */ void f(Object obj, Object obj2) {
        ((zzev) obj).zzc = (zzha) obj2;
    }
}
