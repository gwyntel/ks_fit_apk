package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.measurement.zzrc;
import com.xiaomi.mipush.sdk.Constants;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class zzfs extends zzid {
    private char zza;
    private long zzb;

    @GuardedBy("this")
    private String zzc;
    private final zzfu zzd;
    private final zzfu zze;
    private final zzfu zzf;
    private final zzfu zzg;
    private final zzfu zzh;
    private final zzfu zzi;
    private final zzfu zzj;
    private final zzfu zzk;
    private final zzfu zzl;

    zzfs(zzhc zzhcVar) {
        super(zzhcVar);
        this.zza = (char) 0;
        this.zzb = -1L;
        this.zzd = new zzfu(this, 6, false, false);
        this.zze = new zzfu(this, 6, true, false);
        this.zzf = new zzfu(this, 6, false, true);
        this.zzg = new zzfu(this, 5, false, false);
        this.zzh = new zzfu(this, 5, true, false);
        this.zzi = new zzfu(this, 5, false, true);
        this.zzj = new zzfu(this, 4, false, false);
        this.zzk = new zzfu(this, 3, false, false);
        this.zzl = new zzfu(this, 2, false, false);
    }

    protected static Object d(String str) {
        if (str == null) {
            return null;
        }
        return new zzft(str);
    }

    static String e(boolean z2, String str, Object obj, Object obj2, Object obj3) {
        String str2 = "";
        if (str == null) {
            str = "";
        }
        String strZza = zza(z2, obj);
        String strZza2 = zza(z2, obj2);
        String strZza3 = zza(z2, obj3);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        String str3 = ", ";
        if (!TextUtils.isEmpty(strZza)) {
            sb.append(str2);
            sb.append(strZza);
            str2 = ", ";
        }
        if (TextUtils.isEmpty(strZza2)) {
            str3 = str2;
        } else {
            sb.append(str2);
            sb.append(strZza2);
        }
        if (!TextUtils.isEmpty(strZza3)) {
            sb.append(str3);
            sb.append(strZza3);
        }
        return sb.toString();
    }

    @EnsuresNonNull({"logTagDoNotUseDirectly"})
    @VisibleForTesting
    private final String zzy() {
        String str;
        synchronized (this) {
            try {
                if (this.zzc == null) {
                    this.zzc = this.f13286a.zzw() != null ? this.f13286a.zzw() : "FA";
                }
                Preconditions.checkNotNull(this.zzc);
                str = this.zzc;
            } catch (Throwable th) {
                throw th;
            }
        }
        return str;
    }

    @Override // com.google.android.gms.measurement.internal.zzid
    protected final boolean a() {
        return false;
    }

    protected final void f(int i2, String str) {
        Log.println(i2, zzy(), str);
    }

    protected final void g(int i2, boolean z2, boolean z3, String str, Object obj, Object obj2, Object obj3) throws IllegalStateException {
        if (!z2 && j(i2)) {
            f(i2, e(false, str, obj, obj2, obj3));
        }
        if (z3 || i2 < 5) {
            return;
        }
        Preconditions.checkNotNull(str);
        zzgz zzgzVarG = this.f13286a.g();
        if (zzgzVarG == null) {
            f(6, "Scheduler not set. Not logging error/warn");
            return;
        }
        if (!zzgzVarG.zzae()) {
            f(6, "Scheduler not initialized. Not logging error/warn");
            return;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 >= 9) {
            i2 = 8;
        }
        zzgzVarG.zzb(new zzfr(this, i2, str, obj, obj2, obj3));
    }

    protected final boolean j(int i2) {
        return Log.isLoggable(zzy(), i2);
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Context zza() {
        return super.zza();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ Clock zzb() {
        return super.zzb();
    }

    public final zzfu zzc() {
        return this.zzk;
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzae zzd() {
        return super.zzd();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzaf zze() {
        return super.zze();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzba zzf() {
        return super.zzf();
    }

    public final zzfu zzg() {
        return this.zzd;
    }

    public final zzfu zzh() {
        return this.zzf;
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzfn zzi() {
        return super.zzi();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzfs zzj() {
        return super.zzj();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzge zzk() {
        return super.zzk();
    }

    @Override // com.google.android.gms.measurement.internal.zzia, com.google.android.gms.measurement.internal.zzic
    @Pure
    public final /* bridge */ /* synthetic */ zzgz zzl() {
        return super.zzl();
    }

    public final zzfu zzm() {
        return this.zze;
    }

    public final zzfu zzn() {
        return this.zzj;
    }

    public final zzfu zzp() {
        return this.zzl;
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    @Pure
    public final /* bridge */ /* synthetic */ zzne zzq() {
        return super.zzq();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzr() {
        super.zzr();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzs() {
        super.zzs();
    }

    @Override // com.google.android.gms.measurement.internal.zzia
    public final /* bridge */ /* synthetic */ void zzt() {
        super.zzt();
    }

    public final zzfu zzu() {
        return this.zzg;
    }

    public final zzfu zzv() {
        return this.zzi;
    }

    public final zzfu zzw() {
        return this.zzh;
    }

    public final String zzx() {
        Pair<String, Long> pairZza;
        if (zzk().zzb == null || (pairZza = zzk().zzb.zza()) == null || pairZza == zzge.f13277b) {
            return null;
        }
        return String.valueOf(pairZza.second) + ":" + ((String) pairZza.first);
    }

    @VisibleForTesting
    private static String zza(boolean z2, Object obj) {
        String className;
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            obj = Long.valueOf(((Integer) obj).intValue());
        }
        int i2 = 0;
        if (obj instanceof Long) {
            if (!z2) {
                return String.valueOf(obj);
            }
            Long l2 = (Long) obj;
            if (Math.abs(l2.longValue()) < 100) {
                return String.valueOf(obj);
            }
            String str = String.valueOf(obj).charAt(0) == '-' ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : "";
            String strValueOf = String.valueOf(Math.abs(l2.longValue()));
            return str + Math.round(Math.pow(10.0d, strValueOf.length() - 1)) + "..." + str + Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d);
        }
        if (obj instanceof Boolean) {
            return String.valueOf(obj);
        }
        if (!(obj instanceof Throwable)) {
            return obj instanceof zzft ? ((zzft) obj).zza : z2 ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : String.valueOf(obj);
        }
        Throwable th = (Throwable) obj;
        StringBuilder sb = new StringBuilder(z2 ? th.getClass().getName() : th.toString());
        String strZzb = zzb(zzhc.class.getCanonicalName());
        StackTraceElement[] stackTrace = th.getStackTrace();
        int length = stackTrace.length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            StackTraceElement stackTraceElement = stackTrace[i2];
            if (!stackTraceElement.isNativeMethod() && (className = stackTraceElement.getClassName()) != null && zzb(className).equals(strZzb)) {
                sb.append(": ");
                sb.append(stackTraceElement);
                break;
            }
            i2++;
        }
        return sb.toString();
    }

    @VisibleForTesting
    private static String zzb(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf == -1 ? (zzrc.zzb() && zzbi.zzcb.zza(null).booleanValue()) ? "" : str : str.substring(0, iLastIndexOf);
    }
}
