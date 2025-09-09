package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public final class zzhi {
    private final String zza;
    private final zzhk zzb;
    private zzhk zzc;
    private boolean zzd;
    private boolean zze;

    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.zza);
        sb.append('{');
        zzhk zzhkVar = this.zzb.f13213b;
        String str = "";
        while (zzhkVar != null) {
            Object obj = zzhkVar.f13212a;
            boolean z2 = zzhkVar instanceof zzhh;
            sb.append(str);
            if (obj == null || !obj.getClass().isArray()) {
                sb.append(obj);
            } else {
                String strDeepToString = Arrays.deepToString(new Object[]{obj});
                sb.append((CharSequence) strDeepToString, 1, strDeepToString.length() - 1);
            }
            zzhkVar = zzhkVar.f13213b;
            str = ", ";
        }
        sb.append('}');
        return sb.toString();
    }

    public final zzhi zza(@CheckForNull Object obj) {
        zzhk zzhkVar = new zzhk();
        this.zzc.f13213b = zzhkVar;
        this.zzc = zzhkVar;
        zzhkVar.f13212a = obj;
        return this;
    }

    private zzhi(String str) {
        zzhk zzhkVar = new zzhk();
        this.zzb = zzhkVar;
        this.zzc = zzhkVar;
        this.zzd = false;
        this.zze = false;
        this.zza = (String) zzhn.zza(str);
    }
}
