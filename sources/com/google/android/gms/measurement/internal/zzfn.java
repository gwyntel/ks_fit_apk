package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public final class zzfn {
    private static final AtomicReference<String[]> zza = new AtomicReference<>();
    private static final AtomicReference<String[]> zzb = new AtomicReference<>();
    private static final AtomicReference<String[]> zzc = new AtomicReference<>();
    private final zzfq zzd;

    public zzfn(zzfq zzfqVar) {
        this.zzd = zzfqVar;
    }

    private final String zza(Object[] objArr) {
        if (objArr == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object obj : objArr) {
            String strA = obj instanceof Bundle ? a((Bundle) obj) : String.valueOf(obj);
            if (strA != null) {
                if (sb.length() != 1) {
                    sb.append(", ");
                }
                sb.append(strA);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    protected final String a(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        if (!this.zzd.zza()) {
            return bundle.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Bundle[{");
        for (String str : bundle.keySet()) {
            if (sb.length() != 8) {
                sb.append(", ");
            }
            sb.append(d(str));
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            Object obj = bundle.get(str);
            sb.append(obj instanceof Bundle ? zza(new Object[]{obj}) : obj instanceof Object[] ? zza((Object[]) obj) : obj instanceof ArrayList ? zza(((ArrayList) obj).toArray()) : String.valueOf(obj));
        }
        sb.append("}]");
        return sb.toString();
    }

    protected final String b(zzbg zzbgVar) {
        if (zzbgVar == null) {
            return null;
        }
        if (!this.zzd.zza()) {
            return zzbgVar.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("origin=");
        sb.append(zzbgVar.zzc);
        sb.append(",name=");
        sb.append(c(zzbgVar.zza));
        sb.append(",params=");
        zzbb zzbbVar = zzbgVar.zzb;
        sb.append(zzbbVar != null ? !this.zzd.zza() ? zzbbVar.toString() : a(zzbbVar.zzb()) : null);
        return sb.toString();
    }

    protected final String c(String str) {
        if (str == null) {
            return null;
        }
        return !this.zzd.zza() ? str : zza(str, zzii.zzc, zzii.zza, zza);
    }

    protected final String d(String str) {
        if (str == null) {
            return null;
        }
        return !this.zzd.zza() ? str : zza(str, zzih.zzb, zzih.zza, zzb);
    }

    protected final String e(String str) {
        if (str == null) {
            return null;
        }
        if (!this.zzd.zza()) {
            return str;
        }
        if (!str.startsWith("_exp_")) {
            return zza(str, zzik.zzb, zzik.zza, zzc);
        }
        return "experiment_id(" + str + ")";
    }

    private static String zza(String str, String[] strArr, String[] strArr2, AtomicReference<String[]> atomicReference) {
        String str2;
        Preconditions.checkNotNull(strArr);
        Preconditions.checkNotNull(strArr2);
        Preconditions.checkNotNull(atomicReference);
        Preconditions.checkArgument(strArr.length == strArr2.length);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            Object obj = strArr[i2];
            if (str == obj || (str != null && str.equals(obj))) {
                synchronized (atomicReference) {
                    try {
                        String[] strArr3 = atomicReference.get();
                        if (strArr3 == null) {
                            strArr3 = new String[strArr2.length];
                            atomicReference.set(strArr3);
                        }
                        if (strArr3[i2] == null) {
                            strArr3[i2] = strArr2[i2] + "(" + strArr[i2] + ")";
                        }
                        str2 = strArr3[i2];
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return str2;
            }
        }
        return str;
    }
}
