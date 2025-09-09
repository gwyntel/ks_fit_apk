package com.google.android.gms.internal.measurement;

import com.google.protobuf.GeneratedMessage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class zzod {
    private static final Class<?> zza = zzd();
    private static final zzow<?, ?> zzb = zzc();
    private static final zzow<?, ?> zzc = new zzoy();

    static int A(List list) {
        int iZzd;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            iZzd = 0;
            while (i2 < size) {
                iZzd += zzld.zzd(zzmuVar.zzb(i2));
                i2++;
            }
        } else {
            iZzd = 0;
            while (i2 < size) {
                iZzd += zzld.zzd(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return iZzd;
    }

    static int B(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return C(list) + (size * zzld.zzf(i2));
    }

    static int C(List list) {
        int iZzg;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            iZzg = 0;
            while (i2 < size) {
                iZzg += zzld.zzg(zzlxVar.zzb(i2));
                i2++;
            }
        } else {
            iZzg = 0;
            while (i2 < size) {
                iZzg += zzld.zzg(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iZzg;
    }

    static int D(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return E(list) + (size * zzld.zzf(i2));
    }

    static int E(List list) {
        int iZze;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            iZze = 0;
            while (i2 < size) {
                iZze += zzld.zze(zzmuVar.zzb(i2));
                i2++;
            }
        } else {
            iZze = 0;
            while (i2 < size) {
                iZze += zzld.zze(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return iZze;
    }

    static int a(int i2, Object obj, zzob zzobVar) {
        return obj instanceof zzmn ? zzld.zzb(i2, (zzmn) obj) : zzld.d(i2, (zznj) obj, zzobVar);
    }

    static int b(int i2, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzf = size * zzld.zzf(i2);
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzf += zzld.zza((zzkm) list.get(i3));
        }
        return iZzf;
    }

    static int c(int i2, List list, zzob zzobVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iA = 0;
        for (int i3 = 0; i3 < size; i3++) {
            iA += zzld.a(i2, (zznj) list.get(i3), zzobVar);
        }
        return iA;
    }

    static int d(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzld.zza(i2, true);
    }

    static int e(List list) {
        return list.size();
    }

    static Object f(Object obj, int i2, int i3, Object obj2, zzow zzowVar) {
        if (obj2 == null) {
            obj2 = zzowVar.o(obj);
        }
        zzowVar.l(obj2, i2, i3);
        return obj2;
    }

    static Object g(Object obj, int i2, List list, zzma zzmaVar, Object obj2, zzow zzowVar) {
        if (zzmaVar == null) {
            return obj2;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                Integer num = (Integer) list.get(i4);
                int iIntValue = num.intValue();
                if (zzmaVar.zza(iIntValue)) {
                    if (i4 != i3) {
                        list.set(i3, num);
                    }
                    i3++;
                } else {
                    obj2 = f(obj, i2, iIntValue, obj2, zzowVar);
                }
            }
            if (i3 != size) {
                list.subList(i3, size).clear();
            }
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Integer) it.next()).intValue();
                if (!zzmaVar.zza(iIntValue2)) {
                    obj2 = f(obj, i2, iIntValue2, obj2, zzowVar);
                    it.remove();
                }
            }
        }
        return obj2;
    }

    static void h(zzll zzllVar, Object obj, Object obj2) {
        zzlm zzlmVarB = zzllVar.b(obj2);
        if (zzlmVarB.f13226a.isEmpty()) {
            return;
        }
        zzllVar.i(obj).zza(zzlmVarB);
    }

    static void i(zznc zzncVar, Object obj, Object obj2, long j2) {
        zzpc.i(obj, j2, zzncVar.zza(zzpc.v(obj, j2), zzpc.v(obj2, j2)));
    }

    static void j(zzow zzowVar, Object obj, Object obj2) {
        zzowVar.p(obj, zzowVar.c(zzowVar.q(obj), zzowVar.q(obj2)));
    }

    static boolean k(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static int l(int i2, List list) {
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzf = zzld.zzf(i2) * size;
        if (list instanceof zzmp) {
            zzmp zzmpVar = (zzmp) list;
            while (i3 < size) {
                Object objZzb = zzmpVar.zzb(i3);
                iZzf += objZzb instanceof zzkm ? zzld.zza((zzkm) objZzb) : zzld.zza((String) objZzb);
                i3++;
            }
        } else {
            while (i3 < size) {
                Object obj = list.get(i3);
                iZzf += obj instanceof zzkm ? zzld.zza((zzkm) obj) : zzld.zza((String) obj);
                i3++;
            }
        }
        return iZzf;
    }

    static int m(int i2, List list, zzob zzobVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzf = zzld.zzf(i2) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            iZzf += obj instanceof zzmn ? zzld.zza((zzmn) obj) : zzld.b((zznj) obj, zzobVar);
        }
        return iZzf;
    }

    static int n(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return o(list) + (size * zzld.zzf(i2));
    }

    static int o(List list) {
        int iZza;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            iZza = 0;
            while (i2 < size) {
                iZza += zzld.zza(zzlxVar.zzb(i2));
                i2++;
            }
        } else {
            iZza = 0;
            while (i2 < size) {
                iZza += zzld.zza(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iZza;
    }

    static int p(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzld.zzb(i2, 0);
    }

    static int q(List list) {
        return list.size() << 2;
    }

    static int r(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzld.zza(i2, 0L);
    }

    static int s(List list) {
        return list.size() << 3;
    }

    static int t(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return u(list) + (size * zzld.zzf(i2));
    }

    static int u(List list) {
        int iZzc;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            iZzc = 0;
            while (i2 < size) {
                iZzc += zzld.zzc(zzlxVar.zzb(i2));
                i2++;
            }
        } else {
            iZzc = 0;
            while (i2 < size) {
                iZzc += zzld.zzc(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iZzc;
    }

    static int v(int i2, List list, boolean z2) {
        if (list.size() == 0) {
            return 0;
        }
        return w(list) + (list.size() * zzld.zzf(i2));
    }

    static int w(List list) {
        int iZzb;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzmu) {
            zzmu zzmuVar = (zzmu) list;
            iZzb = 0;
            while (i2 < size) {
                iZzb += zzld.zzb(zzmuVar.zzb(i2));
                i2++;
            }
        } else {
            iZzb = 0;
            while (i2 < size) {
                iZzb += zzld.zzb(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return iZzb;
    }

    static int x(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return y(list) + (size * zzld.zzf(i2));
    }

    static int y(List list) {
        int iZze;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzlx) {
            zzlx zzlxVar = (zzlx) list;
            iZze = 0;
            while (i2 < size) {
                iZze += zzld.zze(zzlxVar.zzb(i2));
                i2++;
            }
        } else {
            iZze = 0;
            while (i2 < size) {
                iZze += zzld.zze(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iZze;
    }

    static int z(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return A(list) + (size * zzld.zzf(i2));
    }

    public static zzow<?, ?> zza() {
        return zzb;
    }

    public static zzow<?, ?> zzb() {
        return zzc;
    }

    private static zzow<?, ?> zzc() {
        try {
            Class<?> clsZze = zze();
            if (clsZze == null) {
                return null;
            }
            return (zzow) clsZze.getConstructor(null).newInstance(null);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzd() {
        try {
            int i2 = GeneratedMessage.f15234a;
            return GeneratedMessage.class;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zze() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void zzf(int i2, List<Float> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzf(i2, list, z2);
    }

    public static void zzg(int i2, List<Integer> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzg(i2, list, z2);
    }

    public static void zzh(int i2, List<Long> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzh(i2, list, z2);
    }

    public static void zzi(int i2, List<Integer> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzi(i2, list, z2);
    }

    public static void zzj(int i2, List<Long> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzj(i2, list, z2);
    }

    public static void zzk(int i2, List<Integer> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzk(i2, list, z2);
    }

    public static void zzl(int i2, List<Long> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzl(i2, list, z2);
    }

    public static void zzm(int i2, List<Integer> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzm(i2, list, z2);
    }

    public static void zzn(int i2, List<Long> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzn(i2, list, z2);
    }

    public static void zza(Class<?> cls) {
        Class<?> cls2;
        if (!zzlw.class.isAssignableFrom(cls) && (cls2 = zza) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzb(int i2, List<Double> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzb(i2, list, z2);
    }

    public static void zzd(int i2, List<Integer> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzd(i2, list, z2);
    }

    public static void zze(int i2, List<Long> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zze(i2, list, z2);
    }

    public static void zzc(int i2, List<Integer> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzc(i2, list, z2);
    }

    public static void zzb(int i2, List<?> list, zzpw zzpwVar, zzob zzobVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzb(i2, list, zzobVar);
    }

    public static void zza(int i2, List<Boolean> list, zzpw zzpwVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zza(i2, list, z2);
    }

    public static void zzb(int i2, List<String> list, zzpw zzpwVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zzb(i2, list);
    }

    public static void zza(int i2, List<zzkm> list, zzpw zzpwVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zza(i2, list);
    }

    public static void zza(int i2, List<?> list, zzpw zzpwVar, zzob zzobVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzpwVar.zza(i2, list, zzobVar);
    }
}
