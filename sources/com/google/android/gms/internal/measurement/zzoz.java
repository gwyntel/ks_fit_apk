package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class zzoz {
    private static final zzoz zza = new zzoz(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzoz() {
        this(0, new int[8], new Object[8], true);
    }

    static zzoz b(zzoz zzozVar, zzoz zzozVar2) {
        int i2 = zzozVar.zzb + zzozVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzozVar.zzc, i2);
        System.arraycopy(zzozVar2.zzc, 0, iArrCopyOf, zzozVar.zzb, zzozVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzozVar.zzd, i2);
        System.arraycopy(zzozVar2.zzd, 0, objArrCopyOf, zzozVar.zzb, zzozVar2.zzb);
        return new zzoz(i2, iArrCopyOf, objArrCopyOf, true);
    }

    static zzoz f() {
        return new zzoz();
    }

    public static zzoz zzc() {
        return zza;
    }

    private final void zzf() {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
    }

    final zzoz a(zzoz zzozVar) {
        if (zzozVar.equals(zza)) {
            return this;
        }
        zzf();
        int i2 = this.zzb + zzozVar.zzb;
        zza(i2);
        System.arraycopy(zzozVar.zzc, 0, this.zzc, this.zzb, zzozVar.zzb);
        System.arraycopy(zzozVar.zzd, 0, this.zzd, this.zzb, zzozVar.zzb);
        this.zzb = i2;
        return this;
    }

    final void c(int i2, Object obj) {
        zzf();
        zza(this.zzb + 1);
        int[] iArr = this.zzc;
        int i3 = this.zzb;
        iArr[i3] = i2;
        this.zzd[i3] = obj;
        this.zzb = i3 + 1;
    }

    final void d(zzpw zzpwVar) throws IOException {
        if (zzpwVar.zza() == zzpv.zzb) {
            for (int i2 = this.zzb - 1; i2 >= 0; i2--) {
                zzpwVar.zza(this.zzc[i2] >>> 3, this.zzd[i2]);
            }
            return;
        }
        for (int i3 = 0; i3 < this.zzb; i3++) {
            zzpwVar.zza(this.zzc[i3] >>> 3, this.zzd[i3]);
        }
    }

    final void e(StringBuilder sb, int i2) {
        for (int i3 = 0; i3 < this.zzb; i3++) {
            zznk.b(sb, i2, String.valueOf(this.zzc[i3] >>> 3), this.zzd[i3]);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzoz)) {
            return false;
        }
        zzoz zzozVar = (zzoz) obj;
        int i2 = this.zzb;
        if (i2 == zzozVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzozVar.zzc;
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    Object[] objArr = this.zzd;
                    Object[] objArr2 = zzozVar.zzd;
                    int i4 = this.zzb;
                    for (int i5 = 0; i5 < i4; i5++) {
                        if (objArr[i5].equals(objArr2[i5])) {
                        }
                    }
                    return true;
                }
                if (iArr[i3] != iArr2[i3]) {
                    break;
                }
                i3++;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i2 = this.zzb;
        int i3 = (i2 + 527) * 31;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i2; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i3 + i4) * 31;
        Object[] objArr = this.zzd;
        int i7 = this.zzb;
        for (int i8 = 0; i8 < i7; i8++) {
            iHashCode = (iHashCode * 31) + objArr[i8].hashCode();
        }
        return i6 + iHashCode;
    }

    public final int zza() {
        int iZze;
        int i2 = this.zze;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.zzb; i4++) {
            int i5 = this.zzc[i4];
            int i6 = i5 >>> 3;
            int i7 = i5 & 7;
            if (i7 == 0) {
                iZze = zzld.zze(i6, ((Long) this.zzd[i4]).longValue());
            } else if (i7 == 1) {
                iZze = zzld.zza(i6, ((Long) this.zzd[i4]).longValue());
            } else if (i7 == 2) {
                iZze = zzld.zza(i6, (zzkm) this.zzd[i4]);
            } else if (i7 == 3) {
                iZze = (zzld.zzf(i6) << 1) + ((zzoz) this.zzd[i4]).zza();
            } else {
                if (i7 != 5) {
                    throw new IllegalStateException(zzme.zza());
                }
                iZze = zzld.zzb(i6, ((Integer) this.zzd[i4]).intValue());
            }
            i3 += iZze;
        }
        this.zze = i3;
        return i3;
    }

    public final int zzb() {
        int i2 = this.zze;
        if (i2 != -1) {
            return i2;
        }
        int iZzb = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            iZzb += zzld.zzb(this.zzc[i3] >>> 3, (zzkm) this.zzd[i3]);
        }
        this.zze = iZzb;
        return iZzb;
    }

    public final void zze() {
        if (this.zzf) {
            this.zzf = false;
        }
    }

    private zzoz(int i2, int[] iArr, Object[] objArr, boolean z2) {
        this.zze = -1;
        this.zzb = i2;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z2;
    }

    public final void zzb(zzpw zzpwVar) throws IOException {
        if (this.zzb == 0) {
            return;
        }
        if (zzpwVar.zza() == zzpv.zza) {
            for (int i2 = 0; i2 < this.zzb; i2++) {
                zza(this.zzc[i2], this.zzd[i2], zzpwVar);
            }
            return;
        }
        for (int i3 = this.zzb - 1; i3 >= 0; i3--) {
            zza(this.zzc[i3], this.zzd[i3], zzpwVar);
        }
    }

    private final void zza(int i2) {
        int[] iArr = this.zzc;
        if (i2 > iArr.length) {
            int i3 = this.zzb;
            int i4 = i3 + (i3 / 2);
            if (i4 >= i2) {
                i2 = i4;
            }
            if (i2 < 8) {
                i2 = 8;
            }
            this.zzc = Arrays.copyOf(iArr, i2);
            this.zzd = Arrays.copyOf(this.zzd, i2);
        }
    }

    private static void zza(int i2, Object obj, zzpw zzpwVar) throws IOException {
        int i3 = i2 >>> 3;
        int i4 = i2 & 7;
        if (i4 == 0) {
            zzpwVar.zzb(i3, ((Long) obj).longValue());
            return;
        }
        if (i4 == 1) {
            zzpwVar.zza(i3, ((Long) obj).longValue());
            return;
        }
        if (i4 == 2) {
            zzpwVar.zza(i3, (zzkm) obj);
            return;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                zzpwVar.zzb(i3, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzme.zza());
        }
        if (zzpwVar.zza() == zzpv.zza) {
            zzpwVar.zzb(i3);
            ((zzoz) obj).zzb(zzpwVar);
            zzpwVar.zza(i3);
        } else {
            zzpwVar.zza(i3);
            ((zzoz) obj).zzb(zzpwVar);
            zzpwVar.zzb(i3);
        }
    }
}
