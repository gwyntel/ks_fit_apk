package com.google.android.gms.internal.measurement;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alipay.sdk.m.u.i;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzad {
    private static final zzjc<String> zza = zzjc.zza("_syn", "_err", "_el");
    private String zzb;
    private long zzc;
    private Map<String, Object> zzd;

    public zzad(String str, long j2, Map<String, Object> map) {
        this.zzb = str;
        this.zzc = j2;
        HashMap map2 = new HashMap();
        this.zzd = map2;
        if (map != null) {
            map2.putAll(map);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return new zzad(this.zzb, this.zzc, new HashMap(this.zzd));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzad)) {
            return false;
        }
        zzad zzadVar = (zzad) obj;
        if (this.zzc == zzadVar.zzc && this.zzb.equals(zzadVar.zzb)) {
            return this.zzd.equals(zzadVar.zzd);
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.zzb.hashCode() * 31;
        long j2 = this.zzc;
        return ((iHashCode + ((int) (j2 ^ (j2 >>> 32)))) * 31) + this.zzd.hashCode();
    }

    public final String toString() {
        return "Event{name='" + this.zzb + "', timestamp=" + this.zzc + ", params=" + String.valueOf(this.zzd) + i.f9804d;
    }

    public final long zza() {
        return this.zzc;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final Map<String, Object> zzc() {
        return this.zzd;
    }

    public final Object zza(String str) {
        if (this.zzd.containsKey(str)) {
            return this.zzd.get(str);
        }
        return null;
    }

    public final void zzb(String str) {
        this.zzb = str;
    }

    public static Object zza(String str, Object obj, Object obj2) {
        if (zza.contains(str) && (obj2 instanceof Double)) {
            return Long.valueOf(Math.round(((Double) obj2).doubleValue()));
        }
        if (str.startsWith(OpenAccountUIConstants.UNDER_LINE)) {
            return ((obj instanceof String) || obj == null) ? obj2 : obj;
        }
        if (obj instanceof Double) {
            return obj2;
        }
        if (obj instanceof Long) {
            return Long.valueOf(Math.round(((Double) obj2).doubleValue()));
        }
        return obj instanceof String ? obj2.toString() : obj2;
    }

    public final void zza(String str, Object obj) {
        if (obj == null) {
            this.zzd.remove(str);
        } else {
            this.zzd.put(str, zza(str, this.zzd.get(str), obj));
        }
    }
}
