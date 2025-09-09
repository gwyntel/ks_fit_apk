package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.measurement.internal.zzie;
import com.xiaomi.mipush.sdk.Constants;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzay {
    public static final zzay zza = new zzay((Boolean) null, 100);
    private final int zzb;
    private final String zzc;
    private final Boolean zzd;
    private final String zze;
    private final EnumMap<zzie.zza, Boolean> zzf;

    zzay(Boolean bool, int i2) {
        this(bool, i2, (Boolean) null, (String) null);
    }

    private final String zzi() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.zzb);
        for (zzie.zza zzaVar : zzig.DMA.zza()) {
            sb.append(":");
            sb.append(zzie.a(this.zzf.get(zzaVar)));
        }
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzay)) {
            return false;
        }
        zzay zzayVar = (zzay) obj;
        if (this.zzc.equalsIgnoreCase(zzayVar.zzc) && zzax.zza(this.zzd, zzayVar.zzd)) {
            return zzax.zza(this.zze, zzayVar.zze);
        }
        return false;
    }

    public final int hashCode() {
        Boolean bool = this.zzd;
        int i2 = bool == null ? 3 : bool == Boolean.TRUE ? 7 : 13;
        String str = this.zze;
        return this.zzc.hashCode() + (i2 * 29) + ((str == null ? 17 : str.hashCode()) * 137);
    }

    public final String toString() {
        return String.format("Dma Settings: %s, isDmaRegion: %s, cpsDisplayStr: %s", this.zzc, this.zzd, this.zze);
    }

    public final int zza() {
        return this.zzb;
    }

    public final Bundle zzb() {
        Bundle bundle = new Bundle();
        Iterator it = this.zzf.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Boolean bool = (Boolean) entry.getValue();
            if (bool != null) {
                bundle.putString(((zzie.zza) entry.getKey()).zze, zzie.c(bool.booleanValue()));
            }
        }
        Boolean bool2 = this.zzd;
        if (bool2 != null) {
            bundle.putString("is_dma_region", bool2.toString());
        }
        String str = this.zze;
        if (str != null) {
            bundle.putString("cps_display_str", str);
        }
        return bundle;
    }

    public final Boolean zzc() {
        return this.zzf.get(zzie.zza.AD_USER_DATA);
    }

    public final Boolean zzd() {
        return this.zzd;
    }

    public final String zze() {
        return this.zze;
    }

    public final String zzf() {
        return this.zzc;
    }

    public final boolean zzg() {
        if (zzc() != Boolean.TRUE || Constants.ACCEPT_TIME_SEPARATOR_SERVER.equals(this.zze)) {
            return false;
        }
        return !TextUtils.isEmpty(this.zze) || this.zzd == Boolean.FALSE;
    }

    public final boolean zzh() {
        Iterator<Boolean> it = this.zzf.values().iterator();
        while (it.hasNext()) {
            if (it.next() != null) {
                return true;
            }
        }
        return false;
    }

    private zzay(EnumMap<zzie.zza, Boolean> enumMap, int i2) {
        this(enumMap, i2, (Boolean) null, (String) null);
    }

    public static zzay zza(Bundle bundle, int i2) {
        if (bundle == null) {
            return new zzay((Boolean) null, i2);
        }
        EnumMap enumMap = new EnumMap(zzie.zza.class);
        for (zzie.zza zzaVar : zzig.DMA.zza()) {
            enumMap.put((EnumMap) zzaVar, (zzie.zza) zzie.d(bundle.getString(zzaVar.zze)));
        }
        return new zzay((EnumMap<zzie.zza, Boolean>) enumMap, i2, bundle.containsKey("is_dma_region") ? Boolean.valueOf(bundle.getString("is_dma_region")) : null, bundle.getString("cps_display_str"));
    }

    zzay(Boolean bool, int i2, Boolean bool2, String str) {
        EnumMap<zzie.zza, Boolean> enumMap = new EnumMap<>(zzie.zza.class);
        this.zzf = enumMap;
        enumMap.put((EnumMap<zzie.zza, Boolean>) zzie.zza.AD_USER_DATA, (zzie.zza) bool);
        this.zzb = i2;
        this.zzc = zzi();
        this.zzd = bool2;
        this.zze = str;
    }

    private zzay(EnumMap<zzie.zza, Boolean> enumMap, int i2, Boolean bool, String str) {
        EnumMap<zzie.zza, Boolean> enumMap2 = new EnumMap<>(zzie.zza.class);
        this.zzf = enumMap2;
        enumMap2.putAll(enumMap);
        this.zzb = i2;
        this.zzc = zzi();
        this.zzd = bool;
        this.zze = str;
    }

    public static zzay zza(String str) throws NumberFormatException {
        if (str != null && str.length() > 0) {
            String[] strArrSplit = str.split(":");
            int i2 = Integer.parseInt(strArrSplit[0]);
            EnumMap enumMap = new EnumMap(zzie.zza.class);
            zzie.zza[] zzaVarArrZza = zzig.DMA.zza();
            int length = zzaVarArrZza.length;
            int i3 = 1;
            int i4 = 0;
            while (i4 < length) {
                enumMap.put((EnumMap) zzaVarArrZza[i4], (zzie.zza) zzie.b(strArrSplit[i3].charAt(0)));
                i4++;
                i3++;
            }
            return new zzay((EnumMap<zzie.zza, Boolean>) enumMap, i2);
        }
        return zza;
    }

    public static Boolean zza(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return zzie.d(bundle.getString("ad_personalization"));
    }
}
