package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
abstract class zzow<T, B> {
    zzow() {
    }

    abstract int a(Object obj);

    abstract Object b();

    abstract Object c(Object obj, Object obj2);

    abstract void d(Object obj, int i2, int i3);

    abstract void e(Object obj, int i2, long j2);

    abstract void f(Object obj, int i2, zzkm zzkmVar);

    abstract void g(Object obj, int i2, Object obj2);

    abstract void h(Object obj, zzpw zzpwVar);

    abstract boolean i(zzny zznyVar);

    final boolean j(Object obj, zzny zznyVar) throws zzme {
        int iZzd = zznyVar.zzd();
        int i2 = iZzd >>> 3;
        int i3 = iZzd & 7;
        if (i3 == 0) {
            l(obj, i2, zznyVar.zzl());
            return true;
        }
        if (i3 == 1) {
            e(obj, i2, zznyVar.zzk());
            return true;
        }
        if (i3 == 2) {
            f(obj, i2, zznyVar.zzp());
            return true;
        }
        if (i3 != 3) {
            if (i3 == 4) {
                return false;
            }
            if (i3 != 5) {
                throw zzme.zza();
            }
            d(obj, i2, zznyVar.zzf());
            return true;
        }
        Object objB = b();
        int i4 = 4 | (i2 << 3);
        while (zznyVar.zzc() != Integer.MAX_VALUE && j(objB, zznyVar)) {
        }
        if (i4 != zznyVar.zzd()) {
            throw zzme.zzb();
        }
        g(obj, i2, r(objB));
        return true;
    }

    abstract int k(Object obj);

    abstract void l(Object obj, int i2, long j2);

    abstract void m(Object obj, zzpw zzpwVar);

    abstract void n(Object obj, Object obj2);

    abstract Object o(Object obj);

    abstract void p(Object obj, Object obj2);

    abstract Object q(Object obj);

    abstract Object r(Object obj);

    abstract void s(Object obj);
}
