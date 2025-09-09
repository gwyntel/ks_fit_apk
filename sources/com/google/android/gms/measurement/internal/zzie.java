package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzie {
    public static final zzie zza = new zzie(null, null, 100);
    private final EnumMap<zza, Boolean> zzb;
    private final int zzc;

    public enum zza {
        AD_STORAGE("ad_storage"),
        ANALYTICS_STORAGE("analytics_storage"),
        AD_USER_DATA("ad_user_data"),
        AD_PERSONALIZATION("ad_personalization");

        public final String zze;

        zza(String str) {
            this.zze = str;
        }
    }

    private zzie(EnumMap<zza, Boolean> enumMap, int i2) {
        EnumMap<zza, Boolean> enumMap2 = new EnumMap<>(zza.class);
        this.zzb = enumMap2;
        enumMap2.putAll(enumMap);
        this.zzc = i2;
    }

    static char a(Boolean bool) {
        if (bool == null) {
            return '-';
        }
        return bool.booleanValue() ? '1' : '0';
    }

    static Boolean b(char c2) {
        if (c2 == '0') {
            return Boolean.FALSE;
        }
        if (c2 != '1') {
            return null;
        }
        return Boolean.TRUE;
    }

    static String c(boolean z2) {
        return z2 ? "granted" : "denied";
    }

    static Boolean d(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("granted")) {
            return Boolean.TRUE;
        }
        if (str.equals("denied")) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static boolean zza(int i2, int i3) {
        return i2 <= i3;
    }

    private static int zzb(Boolean bool) {
        if (bool == null) {
            return 0;
        }
        return bool.booleanValue() ? 1 : 2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzie)) {
            return false;
        }
        zzie zzieVar = (zzie) obj;
        for (zza zzaVar : zzig.STORAGE.zzd) {
            if (zzb(this.zzb.get(zzaVar)) != zzb(zzieVar.zzb.get(zzaVar))) {
                return false;
            }
        }
        return this.zzc == zzieVar.zzc;
    }

    public final int hashCode() {
        int iZzb = this.zzc * 17;
        Iterator<Boolean> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            iZzb = (iZzb * 31) + zzb(it.next());
        }
        return iZzb;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("settings: source=");
        sb.append(this.zzc);
        for (zza zzaVar : zzig.STORAGE.zzd) {
            sb.append(", ");
            sb.append(zzaVar.name());
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            Boolean bool = this.zzb.get(zzaVar);
            if (bool == null) {
                sb.append("uninitialized");
            } else {
                sb.append(bool.booleanValue() ? "granted" : "denied");
            }
        }
        return sb.toString();
    }

    public final Boolean zzc() {
        return this.zzb.get(zza.AD_STORAGE);
    }

    public final Boolean zzd() {
        return this.zzb.get(zza.ANALYTICS_STORAGE);
    }

    public final String zze() {
        StringBuilder sb = new StringBuilder("G1");
        for (zza zzaVar : zzig.STORAGE.zza()) {
            sb.append(a(this.zzb.get(zzaVar)));
        }
        return sb.toString();
    }

    public final String zzf() {
        StringBuilder sb = new StringBuilder("G2");
        for (zza zzaVar : zzig.STORAGE.zza()) {
            Boolean bool = this.zzb.get(zzaVar);
            sb.append(bool == null ? 'g' : bool.booleanValue() ? 'G' : 'D');
        }
        return sb.toString();
    }

    public final boolean zzg() {
        return zza(zza.AD_STORAGE);
    }

    public final boolean zzh() {
        return zza(zza.ANALYTICS_STORAGE);
    }

    public final boolean zzi() {
        Iterator<Boolean> it = this.zzb.values().iterator();
        while (it.hasNext()) {
            if (it.next() != null) {
                return true;
            }
        }
        return false;
    }

    public final int zza() {
        return this.zzc;
    }

    public final Bundle zzb() {
        Bundle bundle = new Bundle();
        Iterator it = this.zzb.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Boolean bool = (Boolean) entry.getValue();
            if (bool != null) {
                bundle.putString(((zza) entry.getKey()).zze, c(bool.booleanValue()));
            }
        }
        return bundle;
    }

    public final boolean zzc(zzie zzieVar) {
        return zzb(zzieVar, (zza[]) this.zzb.keySet().toArray(new zza[0]));
    }

    public static zzie zza(Bundle bundle, int i2) {
        if (bundle == null) {
            return new zzie(null, null, i2);
        }
        EnumMap enumMap = new EnumMap(zza.class);
        for (zza zzaVar : zzig.STORAGE.zzd) {
            enumMap.put((EnumMap) zzaVar, (zza) d(bundle.getString(zzaVar.zze)));
        }
        return new zzie(enumMap, i2);
    }

    public zzie(Boolean bool, Boolean bool2, int i2) {
        EnumMap<zza, Boolean> enumMap = new EnumMap<>(zza.class);
        this.zzb = enumMap;
        enumMap.put((EnumMap<zza, Boolean>) zza.AD_STORAGE, (zza) bool);
        enumMap.put((EnumMap<zza, Boolean>) zza.ANALYTICS_STORAGE, (zza) bool2);
        this.zzc = i2;
    }

    public final zzie zzb(zzie zzieVar) {
        EnumMap enumMap = new EnumMap(zza.class);
        for (zza zzaVar : zzig.STORAGE.zzd) {
            Boolean bool = this.zzb.get(zzaVar);
            if (bool == null) {
                bool = zzieVar.zzb.get(zzaVar);
            }
            enumMap.put((EnumMap) zzaVar, (zza) bool);
        }
        return new zzie(enumMap, this.zzc);
    }

    public static zzie zza(String str) {
        return zza(str, 100);
    }

    public static zzie zza(String str, int i2) {
        EnumMap enumMap = new EnumMap(zza.class);
        if (str != null) {
            zza[] zzaVarArrZza = zzig.STORAGE.zza();
            for (int i3 = 0; i3 < zzaVarArrZza.length; i3++) {
                zza zzaVar = zzaVarArrZza[i3];
                int i4 = i3 + 2;
                if (i4 < str.length()) {
                    enumMap.put((EnumMap) zzaVar, (zza) b(str.charAt(i4)));
                }
            }
        }
        return new zzie(enumMap, i2);
    }

    public final boolean zzb(zzie zzieVar, zza... zzaVarArr) {
        for (zza zzaVar : zzaVarArr) {
            Boolean bool = this.zzb.get(zzaVar);
            Boolean bool2 = zzieVar.zzb.get(zzaVar);
            Boolean bool3 = Boolean.FALSE;
            if (bool == bool3 && bool2 != bool3) {
                return true;
            }
        }
        return false;
    }

    public final zzie zza(zzie zzieVar) {
        EnumMap enumMap = new EnumMap(zza.class);
        for (zza zzaVar : zzig.STORAGE.zzd) {
            Boolean boolValueOf = this.zzb.get(zzaVar);
            Boolean bool = zzieVar.zzb.get(zzaVar);
            if (boolValueOf == null) {
                boolValueOf = bool;
            } else if (bool != null) {
                boolValueOf = Boolean.valueOf(boolValueOf.booleanValue() && bool.booleanValue());
            }
            enumMap.put((EnumMap) zzaVar, (zza) boolValueOf);
        }
        return new zzie(enumMap, 100);
    }

    public static String zza(Bundle bundle) {
        String string;
        for (zza zzaVar : zzig.STORAGE.zzd) {
            if (bundle.containsKey(zzaVar.zze) && (string = bundle.getString(zzaVar.zze)) != null && d(string) == null) {
                return string;
            }
        }
        return null;
    }

    public final boolean zza(zzie zzieVar, zza... zzaVarArr) {
        for (zza zzaVar : zzaVarArr) {
            if (!zzieVar.zza(zzaVar) && zza(zzaVar)) {
                return true;
            }
        }
        return false;
    }

    public final boolean zza(zza zzaVar) {
        Boolean bool = this.zzb.get(zzaVar);
        return bool == null || bool.booleanValue();
    }
}
