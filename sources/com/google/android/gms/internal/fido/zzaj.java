package com.google.android.gms.internal.fido;

import com.alipay.sdk.m.n.a;
import java.util.Arrays;
import javax.annotation.CheckForNull;

/* loaded from: classes3.dex */
public final class zzaj {
    private final String zza;
    private final zzah zzb;
    private zzah zzc;

    /* synthetic */ zzaj(String str, zzai zzaiVar) {
        zzah zzahVar = new zzah(null);
        this.zzb = zzahVar;
        this.zzc = zzahVar;
        str.getClass();
        this.zza = str;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.zza);
        sb.append('{');
        zzah zzahVar = this.zzb.f13051c;
        String str = "";
        while (zzahVar != null) {
            Object obj = zzahVar.f13050b;
            boolean z2 = zzahVar instanceof zzaf;
            sb.append(str);
            String str2 = zzahVar.f13049a;
            if (str2 != null) {
                sb.append(str2);
                sb.append(a.f9565h);
            }
            if (obj == null || !obj.getClass().isArray()) {
                sb.append(obj);
            } else {
                sb.append((CharSequence) Arrays.deepToString(new Object[]{obj}), 1, r3.length() - 1);
            }
            zzahVar = zzahVar.f13051c;
            str = ", ";
        }
        sb.append('}');
        return sb.toString();
    }

    public final zzaj zza(String str, int i2) {
        String strValueOf = String.valueOf(i2);
        zzaf zzafVar = new zzaf(null);
        this.zzc.f13051c = zzafVar;
        this.zzc = zzafVar;
        zzafVar.f13050b = strValueOf;
        zzafVar.f13049a = "errorCode";
        return this;
    }

    public final zzaj zzb(String str, @CheckForNull Object obj) {
        zzah zzahVar = new zzah(null);
        this.zzc.f13051c = zzahVar;
        this.zzc = zzahVar;
        zzahVar.f13050b = obj;
        zzahVar.f13049a = str;
        return this;
    }
}
