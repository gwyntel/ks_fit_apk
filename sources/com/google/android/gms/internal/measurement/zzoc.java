package com.google.android.gms.internal.measurement;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes3.dex */
class zzoc<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private final int zza;
    private List<zzon> zzb;
    private Map<K, V> zzc;
    private boolean zzd;
    private volatile zzoo zze;
    private Map<K, V> zzf;
    private volatile zzog zzg;

    static zzoc a(int i2) {
        return new zzof(i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int zza(K r5) {
        /*
            r4 = this;
            java.util.List<com.google.android.gms.internal.measurement.zzon> r0 = r4.zzb
            int r0 = r0.size()
            int r1 = r0 + (-1)
            if (r1 < 0) goto L25
            java.util.List<com.google.android.gms.internal.measurement.zzon> r2 = r4.zzb
            java.lang.Object r2 = r2.get(r1)
            com.google.android.gms.internal.measurement.zzon r2 = (com.google.android.gms.internal.measurement.zzon) r2
            java.lang.Object r2 = r2.getKey()
            java.lang.Comparable r2 = (java.lang.Comparable) r2
            int r2 = r5.compareTo(r2)
            if (r2 <= 0) goto L22
            int r0 = r0 + 1
        L20:
            int r5 = -r0
            return r5
        L22:
            if (r2 != 0) goto L25
            return r1
        L25:
            r0 = 0
        L26:
            if (r0 > r1) goto L49
            int r2 = r0 + r1
            int r2 = r2 / 2
            java.util.List<com.google.android.gms.internal.measurement.zzon> r3 = r4.zzb
            java.lang.Object r3 = r3.get(r2)
            com.google.android.gms.internal.measurement.zzon r3 = (com.google.android.gms.internal.measurement.zzon) r3
            java.lang.Object r3 = r3.getKey()
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            int r3 = r5.compareTo(r3)
            if (r3 >= 0) goto L43
            int r1 = r2 + (-1)
            goto L26
        L43:
            if (r3 <= 0) goto L48
            int r0 = r2 + 1
            goto L26
        L48:
            return r2
        L49:
            int r0 = r0 + 1
            goto L20
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzoc.zza(java.lang.Comparable):int");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzc(int i2) {
        zzg();
        V v2 = (V) this.zzb.remove(i2).getValue();
        if (!this.zzc.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzf().entrySet().iterator();
            this.zzb.add(new zzon(this, it.next()));
            it.remove();
        }
        return v2;
    }

    private final SortedMap<K, V> zzf() {
        zzg();
        if (this.zzc.isEmpty() && !(this.zzc instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zzc = treeMap;
            this.zzf = treeMap.descendingMap();
        }
        return (SortedMap) this.zzc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzg() {
        if (this.zzd) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzg();
        if (!this.zzb.isEmpty()) {
            this.zzb.clear();
        }
        if (this.zzc.isEmpty()) {
            return;
        }
        this.zzc.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzc.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zze == null) {
            this.zze = new zzoo(this);
        }
        return this.zze;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzoc)) {
            return super.equals(obj);
        }
        zzoc zzocVar = (zzoc) obj;
        int size = size();
        if (size != zzocVar.size()) {
            return false;
        }
        int iZza = zza();
        if (iZza != zzocVar.zza()) {
            return entrySet().equals(zzocVar.entrySet());
        }
        for (int i2 = 0; i2 < iZza; i2++) {
            if (!zzb(i2).equals(zzocVar.zzb(i2))) {
                return false;
            }
        }
        if (iZza != size) {
            return this.zzc.equals(zzocVar.zzc);
        }
        return true;
    }

    final Set f() {
        if (this.zzg == null) {
            this.zzg = new zzog(this);
        }
        return this.zzg;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        return iZza >= 0 ? (V) this.zzb.get(iZza).getValue() : this.zzc.get(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZza = zza();
        int iHashCode = 0;
        for (int i2 = 0; i2 < iZza; i2++) {
            iHashCode += this.zzb.get(i2).hashCode();
        }
        return this.zzc.size() > 0 ? iHashCode + this.zzc.hashCode() : iHashCode;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzg();
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        if (iZza >= 0) {
            return zzc(iZza);
        }
        if (this.zzc.isEmpty()) {
            return null;
        }
        return this.zzc.remove(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzb.size() + this.zzc.size();
    }

    public final Iterable<Map.Entry<K, V>> zzb() {
        return this.zzc.isEmpty() ? zzoi.a() : this.zzc.entrySet();
    }

    public void zzd() {
        if (this.zzd) {
            return;
        }
        this.zzc = this.zzc.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzc);
        this.zzf = this.zzf.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzf);
        this.zzd = true;
    }

    public final boolean zze() {
        return this.zzd;
    }

    private zzoc(int i2) {
        this.zza = i2;
        this.zzb = Collections.emptyList();
        this.zzc = Collections.emptyMap();
        this.zzf = Collections.emptyMap();
    }

    public final Map.Entry<K, V> zzb(int i2) {
        return this.zzb.get(i2);
    }

    public final int zza() {
        return this.zzb.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final V put(K k2, V v2) {
        zzg();
        int iZza = zza(k2);
        if (iZza >= 0) {
            return (V) this.zzb.get(iZza).setValue(v2);
        }
        zzg();
        if (this.zzb.isEmpty() && !(this.zzb instanceof ArrayList)) {
            this.zzb = new ArrayList(this.zza);
        }
        int i2 = -(iZza + 1);
        if (i2 >= this.zza) {
            return zzf().put(k2, v2);
        }
        int size = this.zzb.size();
        int i3 = this.zza;
        if (size == i3) {
            zzon zzonVarRemove = this.zzb.remove(i3 - 1);
            zzf().put((Comparable) zzonVarRemove.getKey(), zzonVarRemove.getValue());
        }
        this.zzb.add(i2, new zzon(this, k2, v2));
        return null;
    }
}
