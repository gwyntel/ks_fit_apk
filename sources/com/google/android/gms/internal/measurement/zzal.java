package com.google.android.gms.internal.measurement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class zzal implements zzak, zzaq {

    /* renamed from: a, reason: collision with root package name */
    protected final String f13176a;

    /* renamed from: b, reason: collision with root package name */
    protected final Map f13177b = new HashMap();

    public zzal(String str) {
        this.f13176a = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzal)) {
            return false;
        }
        zzal zzalVar = (zzal) obj;
        String str = this.f13176a;
        if (str != null) {
            return str.equals(zzalVar.f13176a);
        }
        return false;
    }

    public int hashCode() {
        String str = this.f13176a;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public abstract zzaq zza(zzh zzhVar, List<zzaq> list);

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final zzaq zza(String str, zzh zzhVar, List<zzaq> list) {
        return "toString".equals(str) ? new zzas(this.f13176a) : zzan.zza(this, new zzas(str), zzhVar, list);
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public zzaq zzc() {
        return this;
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final Boolean zzd() {
        return Boolean.TRUE;
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final Double zze() {
        return Double.valueOf(Double.NaN);
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final String zzf() {
        return this.f13176a;
    }

    @Override // com.google.android.gms.internal.measurement.zzaq
    public final Iterator<zzaq> zzh() {
        return zzan.zza(this.f13177b);
    }

    @Override // com.google.android.gms.internal.measurement.zzak
    public final boolean zzc(String str) {
        return this.f13177b.containsKey(str);
    }

    @Override // com.google.android.gms.internal.measurement.zzak
    public final zzaq zza(String str) {
        if (this.f13177b.containsKey(str)) {
            return (zzaq) this.f13177b.get(str);
        }
        return zzaq.zzc;
    }

    public final String zza() {
        return this.f13176a;
    }

    @Override // com.google.android.gms.internal.measurement.zzak
    public final void zza(String str, zzaq zzaqVar) {
        if (zzaqVar == null) {
            this.f13177b.remove(str);
        } else {
            this.f13177b.put(str, zzaqVar);
        }
    }
}
