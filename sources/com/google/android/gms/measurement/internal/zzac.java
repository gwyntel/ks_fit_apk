package com.google.android.gms.measurement.internal;

import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzev;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

/* loaded from: classes3.dex */
abstract class zzac {

    /* renamed from: a, reason: collision with root package name */
    String f13246a;

    /* renamed from: b, reason: collision with root package name */
    int f13247b;

    /* renamed from: c, reason: collision with root package name */
    Boolean f13248c;

    /* renamed from: d, reason: collision with root package name */
    Boolean f13249d;

    /* renamed from: e, reason: collision with root package name */
    Long f13250e;

    /* renamed from: f, reason: collision with root package name */
    Long f13251f;

    zzac(String str, int i2) {
        this.f13246a = str;
        this.f13247b = i2;
    }

    static Boolean b(double d2, zzev.zzd zzdVar) {
        try {
            return zza(new BigDecimal(d2), zzdVar, Math.ulp(d2));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Boolean c(long j2, zzev.zzd zzdVar) {
        try {
            return zza(new BigDecimal(j2), zzdVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Boolean d(Boolean bool, boolean z2) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() != z2);
    }

    static Boolean e(String str, zzev.zzd zzdVar) {
        if (!zzna.w(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzdVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Boolean f(String str, zzev.zzf zzfVar, zzfs zzfsVar) {
        List<String> list;
        Preconditions.checkNotNull(zzfVar);
        if (str == null || !zzfVar.zzj() || zzfVar.zzb() == zzev.zzf.zzb.UNKNOWN_MATCH_TYPE) {
            return null;
        }
        zzev.zzf.zzb zzbVarZzb = zzfVar.zzb();
        zzev.zzf.zzb zzbVar = zzev.zzf.zzb.IN_LIST;
        if (zzbVarZzb == zzbVar) {
            if (zzfVar.zza() == 0) {
                return null;
            }
        } else if (!zzfVar.zzi()) {
            return null;
        }
        zzev.zzf.zzb zzbVarZzb2 = zzfVar.zzb();
        boolean zZzg = zzfVar.zzg();
        String strZze = (zZzg || zzbVarZzb2 == zzev.zzf.zzb.REGEXP || zzbVarZzb2 == zzbVar) ? zzfVar.zze() : zzfVar.zze().toUpperCase(Locale.ENGLISH);
        if (zzfVar.zza() == 0) {
            list = null;
        } else {
            List<String> listZzf = zzfVar.zzf();
            if (!zZzg) {
                ArrayList arrayList = new ArrayList(listZzf.size());
                Iterator<String> it = listZzf.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().toUpperCase(Locale.ENGLISH));
                }
                listZzf = Collections.unmodifiableList(arrayList);
            }
            list = listZzf;
        }
        return zza(str, zzbVarZzb2, zZzg, strZze, list, zzbVarZzb2 == zzev.zzf.zzb.REGEXP ? strZze : null, zzfsVar);
    }

    @VisibleForTesting
    private static Boolean zza(BigDecimal bigDecimal, zzev.zzd zzdVar, double d2) {
        BigDecimal bigDecimal2;
        BigDecimal bigDecimal3;
        BigDecimal bigDecimal4;
        Preconditions.checkNotNull(zzdVar);
        if (zzdVar.zzh() && zzdVar.zza() != zzev.zzd.zza.UNKNOWN_COMPARISON_TYPE) {
            zzev.zzd.zza zzaVarZza = zzdVar.zza();
            zzev.zzd.zza zzaVar = zzev.zzd.zza.BETWEEN;
            if (zzaVarZza == zzaVar) {
                if (!zzdVar.zzl() || !zzdVar.zzk()) {
                    return null;
                }
            } else if (!zzdVar.zzi()) {
                return null;
            }
            zzev.zzd.zza zzaVarZza2 = zzdVar.zza();
            if (zzdVar.zza() == zzaVar) {
                if (zzna.w(zzdVar.zzf()) && zzna.w(zzdVar.zze())) {
                    try {
                        BigDecimal bigDecimal5 = new BigDecimal(zzdVar.zzf());
                        bigDecimal4 = new BigDecimal(zzdVar.zze());
                        bigDecimal3 = bigDecimal5;
                        bigDecimal2 = null;
                    } catch (NumberFormatException unused) {
                    }
                }
                return null;
            }
            if (!zzna.w(zzdVar.zzd())) {
                return null;
            }
            try {
                bigDecimal2 = new BigDecimal(zzdVar.zzd());
                bigDecimal3 = null;
                bigDecimal4 = null;
            } catch (NumberFormatException unused2) {
            }
            if (zzaVarZza2 == zzaVar) {
                if (bigDecimal3 == null) {
                    return null;
                }
            } else if (bigDecimal2 != null) {
            }
            int i2 = zzw.f13328b[zzaVarZza2.ordinal()];
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        if (i2 != 4 || bigDecimal3 == null) {
                            return null;
                        }
                        if (bigDecimal.compareTo(bigDecimal3) >= 0 && bigDecimal.compareTo(bigDecimal4) <= 0) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    }
                    if (bigDecimal2 != null) {
                        if (d2 == 0.0d) {
                            return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 0);
                        }
                        if (bigDecimal.compareTo(bigDecimal2.subtract(new BigDecimal(d2).multiply(new BigDecimal(2)))) > 0 && bigDecimal.compareTo(bigDecimal2.add(new BigDecimal(d2).multiply(new BigDecimal(2)))) < 0) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    }
                } else if (bigDecimal2 != null) {
                    return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) > 0);
                }
            } else if (bigDecimal2 != null) {
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) < 0);
            }
        }
        return null;
    }

    abstract int a();

    abstract boolean g();

    abstract boolean h();

    private static Boolean zza(String str, zzev.zzf.zzb zzbVar, boolean z2, String str2, List<String> list, String str3, zzfs zzfsVar) {
        if (str == null) {
            return null;
        }
        if (zzbVar == zzev.zzf.zzb.IN_LIST) {
            if (list == null || list.isEmpty()) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z2 && zzbVar != zzev.zzf.zzb.REGEXP) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (zzw.f13327a[zzbVar.ordinal()]) {
            case 1:
                if (str3 != null) {
                    try {
                        break;
                    } catch (PatternSyntaxException unused) {
                        if (zzfsVar != null) {
                            zzfsVar.zzu().zza("Invalid regular expression in REGEXP audience filter. expression", str3);
                        }
                        return null;
                    }
                }
                break;
            case 6:
                if (list != null) {
                    break;
                }
                break;
        }
        return null;
    }
}
