package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzlo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzlm<T extends zzlo<T>> {
    private static final zzlm zzb = new zzlm(true);

    /* renamed from: a, reason: collision with root package name */
    final zzoc f13226a;
    private boolean zzc;
    private boolean zzd;

    private zzlm() {
        this.f13226a = zzoc.a(16);
    }

    static int a(zzpj zzpjVar, int i2, Object obj) {
        int iZzf = zzld.zzf(i2);
        if (zzpjVar == zzpj.zzj) {
            zzlz.d((zznj) obj);
            iZzf <<= 1;
        }
        return iZzf + zza(zzpjVar, obj);
    }

    static void b(zzld zzldVar, zzpj zzpjVar, int i2, Object obj) {
        if (zzpjVar == zzpj.zzj) {
            zznj zznjVar = (zznj) obj;
            zzlz.d(zznjVar);
            zzldVar.zzj(i2, 3);
            zznjVar.zza(zzldVar);
            zzldVar.zzj(i2, 4);
        }
        zzldVar.zzj(i2, zzpjVar.zza());
        switch (zzlp.f13228b[zzpjVar.ordinal()]) {
            case 1:
                zzldVar.zzb(((Double) obj).doubleValue());
                break;
            case 2:
                zzldVar.zzb(((Float) obj).floatValue());
                break;
            case 3:
                zzldVar.zzh(((Long) obj).longValue());
                break;
            case 4:
                zzldVar.zzh(((Long) obj).longValue());
                break;
            case 5:
                zzldVar.zzi(((Integer) obj).intValue());
                break;
            case 6:
                zzldVar.zzf(((Long) obj).longValue());
                break;
            case 7:
                zzldVar.zzh(((Integer) obj).intValue());
                break;
            case 8:
                zzldVar.zzb(((Boolean) obj).booleanValue());
                break;
            case 9:
                ((zznj) obj).zza(zzldVar);
                break;
            case 10:
                zzldVar.zzc((zznj) obj);
                break;
            case 11:
                if (!(obj instanceof zzkm)) {
                    zzldVar.zzb((String) obj);
                    break;
                } else {
                    zzldVar.zzb((zzkm) obj);
                    break;
                }
            case 12:
                if (!(obj instanceof zzkm)) {
                    byte[] bArr = (byte[]) obj;
                    zzldVar.zzb(bArr, 0, bArr.length);
                    break;
                } else {
                    zzldVar.zzb((zzkm) obj);
                    break;
                }
            case 13:
                zzldVar.zzk(((Integer) obj).intValue());
                break;
            case 14:
                zzldVar.zzh(((Integer) obj).intValue());
                break;
            case 15:
                zzldVar.zzf(((Long) obj).longValue());
                break;
            case 16:
                zzldVar.zzj(((Integer) obj).intValue());
                break;
            case 17:
                zzldVar.zzg(((Long) obj).longValue());
                break;
            case 18:
                if (!(obj instanceof zzly)) {
                    zzldVar.zzi(((Integer) obj).intValue());
                    break;
                } else {
                    zzldVar.zzi(((zzly) obj).zza());
                    break;
                }
        }
    }

    private static int zza(zzpj zzpjVar, Object obj) {
        switch (zzlp.f13228b[zzpjVar.ordinal()]) {
            case 1:
                return zzld.zza(((Double) obj).doubleValue());
            case 2:
                return zzld.zza(((Float) obj).floatValue());
            case 3:
                return zzld.zzb(((Long) obj).longValue());
            case 4:
                return zzld.zze(((Long) obj).longValue());
            case 5:
                return zzld.zzc(((Integer) obj).intValue());
            case 6:
                return zzld.zza(((Long) obj).longValue());
            case 7:
                return zzld.zzb(((Integer) obj).intValue());
            case 8:
                return zzld.zza(((Boolean) obj).booleanValue());
            case 9:
                return zzld.zza((zznj) obj);
            case 10:
                return obj instanceof zzmj ? zzld.zza((zzmj) obj) : zzld.zzb((zznj) obj);
            case 11:
                return obj instanceof zzkm ? zzld.zza((zzkm) obj) : zzld.zza((String) obj);
            case 12:
                return obj instanceof zzkm ? zzld.zza((zzkm) obj) : zzld.zza((byte[]) obj);
            case 13:
                return zzld.zzg(((Integer) obj).intValue());
            case 14:
                return zzld.zzd(((Integer) obj).intValue());
            case 15:
                return zzld.zzc(((Long) obj).longValue());
            case 16:
                return zzld.zze(((Integer) obj).intValue());
            case 17:
                return zzld.zzd(((Long) obj).longValue());
            case 18:
                return obj instanceof zzly ? zzld.zza(((zzly) obj).zza()) : zzld.zza(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static <T extends zzlo<T>> zzlm<T> zzb() {
        return zzb;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void zzc(T r5, java.lang.Object r6) {
        /*
            com.google.android.gms.internal.measurement.zzpj r0 = r5.zzb()
            com.google.android.gms.internal.measurement.zzlz.b(r6)
            int[] r1 = com.google.android.gms.internal.measurement.zzlp.f13227a
            com.google.android.gms.internal.measurement.zzpt r0 = r0.zzb()
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 1
            r2 = 0
            switch(r0) {
                case 1: goto L45;
                case 2: goto L42;
                case 3: goto L3f;
                case 4: goto L3c;
                case 5: goto L39;
                case 6: goto L36;
                case 7: goto L2d;
                case 8: goto L24;
                case 9: goto L1a;
                default: goto L18;
            }
        L18:
            r0 = r2
            goto L47
        L1a:
            boolean r0 = r6 instanceof com.google.android.gms.internal.measurement.zznj
            if (r0 != 0) goto L22
            boolean r0 = r6 instanceof com.google.android.gms.internal.measurement.zzmj
            if (r0 == 0) goto L18
        L22:
            r0 = r1
            goto L47
        L24:
            boolean r0 = r6 instanceof java.lang.Integer
            if (r0 != 0) goto L22
            boolean r0 = r6 instanceof com.google.android.gms.internal.measurement.zzly
            if (r0 == 0) goto L18
            goto L22
        L2d:
            boolean r0 = r6 instanceof com.google.android.gms.internal.measurement.zzkm
            if (r0 != 0) goto L22
            boolean r0 = r6 instanceof byte[]
            if (r0 == 0) goto L18
            goto L22
        L36:
            boolean r0 = r6 instanceof java.lang.String
            goto L47
        L39:
            boolean r0 = r6 instanceof java.lang.Boolean
            goto L47
        L3c:
            boolean r0 = r6 instanceof java.lang.Double
            goto L47
        L3f:
            boolean r0 = r6 instanceof java.lang.Float
            goto L47
        L42:
            boolean r0 = r6 instanceof java.lang.Long
            goto L47
        L45:
            boolean r0 = r6 instanceof java.lang.Integer
        L47:
            if (r0 == 0) goto L4a
            return
        L4a:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            int r3 = r5.zza()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            com.google.android.gms.internal.measurement.zzpj r5 = r5.zzb()
            com.google.android.gms.internal.measurement.zzpt r5 = r5.zzb()
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getName()
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r2] = r3
            r4[r1] = r5
            r5 = 2
            r4[r5] = r6
            java.lang.String r5 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            java.lang.String r5 = java.lang.String.format(r5, r4)
            r0.<init>(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzlm.zzc(com.google.android.gms.internal.measurement.zzlo, java.lang.Object):void");
    }

    final Iterator c() {
        return this.zzd ? new zzmk(this.f13226a.f().iterator()) : this.f13226a.f().iterator();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzlm zzlmVar = new zzlm();
        for (int i2 = 0; i2 < this.f13226a.zza(); i2++) {
            Map.Entry entryZzb = this.f13226a.zzb(i2);
            zzlmVar.zzb((zzlo) entryZzb.getKey(), entryZzb.getValue());
        }
        for (Map.Entry entry : this.f13226a.zzb()) {
            zzlmVar.zzb((zzlo) entry.getKey(), entry.getValue());
        }
        zzlmVar.zzd = this.zzd;
        return zzlmVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzlm) {
            return this.f13226a.equals(((zzlm) obj).f13226a);
        }
        return false;
    }

    public final int hashCode() {
        return this.f13226a.hashCode();
    }

    public final Iterator<Map.Entry<T, Object>> zzd() {
        return this.zzd ? new zzmk(this.f13226a.entrySet().iterator()) : this.f13226a.entrySet().iterator();
    }

    public final void zze() {
        if (this.zzc) {
            return;
        }
        for (int i2 = 0; i2 < this.f13226a.zza(); i2++) {
            Map.Entry entryZzb = this.f13226a.zzb(i2);
            if (entryZzb.getValue() instanceof zzlw) {
                ((zzlw) entryZzb.getValue()).r();
            }
        }
        this.f13226a.zzd();
        this.zzc = true;
    }

    public final boolean zzf() {
        return this.zzc;
    }

    public final boolean zzg() {
        for (int i2 = 0; i2 < this.f13226a.zza(); i2++) {
            if (!zzc(this.f13226a.zzb(i2))) {
                return false;
            }
        }
        Iterator it = this.f13226a.zzb().iterator();
        while (it.hasNext()) {
            if (!zzc((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    private final void zzb(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (value instanceof zzmj) {
            value = zzmj.zza();
        }
        if (key.zze()) {
            Object objZza = zza((zzlm<T>) key);
            if (objZza == null) {
                objZza = new ArrayList();
            }
            Iterator it = ((List) value).iterator();
            while (it.hasNext()) {
                ((List) objZza).add(zza(it.next()));
            }
            this.f13226a.put(key, objZza);
            return;
        }
        if (key.zzc() != zzpt.MESSAGE) {
            this.f13226a.put(key, zza(value));
            return;
        }
        Object objZza2 = zza((zzlm<T>) key);
        if (objZza2 == null) {
            this.f13226a.put(key, zza(value));
        } else {
            this.f13226a.put(key, objZza2 instanceof zzno ? key.zza((zzno) objZza2, (zzno) value) : key.zza(((zznj) objZza2).zzce(), (zznj) value).zzab());
        }
    }

    private zzlm(zzoc<T, Object> zzocVar) {
        this.f13226a = zzocVar;
        zze();
    }

    private zzlm(boolean z2) {
        this(zzoc.a(0));
        zze();
    }

    private static <T extends zzlo<T>> boolean zzc(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zzc() != zzpt.MESSAGE) {
            return true;
        }
        if (key.zze()) {
            Iterator it = ((List) entry.getValue()).iterator();
            while (it.hasNext()) {
                if (!zzb(it.next())) {
                    return false;
                }
            }
            return true;
        }
        return zzb(entry.getValue());
    }

    private final void zzb(T t2, Object obj) {
        if (t2.zze()) {
            if (obj instanceof List) {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll((List) obj);
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    zzc(t2, obj2);
                }
                obj = arrayList;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        } else {
            zzc(t2, obj);
        }
        if (obj instanceof zzmj) {
            this.zzd = true;
        }
        this.f13226a.put(t2, obj);
    }

    public static int zza(zzlo<?> zzloVar, Object obj) {
        zzpj zzpjVarZzb = zzloVar.zzb();
        int iZza = zzloVar.zza();
        if (zzloVar.zze()) {
            List list = (List) obj;
            int iA = 0;
            if (zzloVar.zzd()) {
                if (list.isEmpty()) {
                    return 0;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    iA += zza(zzpjVarZzb, it.next());
                }
                return zzld.zzf(iZza) + iA + zzld.zzg(iA);
            }
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                iA += a(zzpjVarZzb, iZza, it2.next());
            }
            return iA;
        }
        return a(zzpjVarZzb, iZza, obj);
    }

    private static boolean zzb(Object obj) {
        if (obj instanceof zznl) {
            return ((zznl) obj).i_();
        }
        if (obj instanceof zzmj) {
            return true;
        }
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }

    public final int zza() {
        int iZza = 0;
        for (int i2 = 0; i2 < this.f13226a.zza(); i2++) {
            iZza += zza(this.f13226a.zzb(i2));
        }
        Iterator it = this.f13226a.zzb().iterator();
        while (it.hasNext()) {
            iZza += zza((Map.Entry) it.next());
        }
        return iZza;
    }

    private static int zza(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        Object value = entry.getValue();
        if (key.zzc() == zzpt.MESSAGE && !key.zze() && !key.zzd()) {
            if (value instanceof zzmj) {
                return zzld.zza(entry.getKey().zza(), (zzmj) value);
            }
            return zzld.zza(entry.getKey().zza(), (zznj) value);
        }
        return zza((zzlo<?>) key, value);
    }

    private static Object zza(Object obj) {
        if (obj instanceof zzno) {
            return ((zzno) obj).clone();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    private final Object zza(T t2) {
        Object obj = this.f13226a.get(t2);
        return obj instanceof zzmj ? zzmj.zza() : obj;
    }

    public final void zza(zzlm<T> zzlmVar) {
        for (int i2 = 0; i2 < zzlmVar.f13226a.zza(); i2++) {
            zzb((Map.Entry) zzlmVar.f13226a.zzb(i2));
        }
        Iterator it = zzlmVar.f13226a.zzb().iterator();
        while (it.hasNext()) {
            zzb((Map.Entry) it.next());
        }
    }
}
