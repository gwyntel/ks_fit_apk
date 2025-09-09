package com.google.android.gms.internal.auth;

import java.util.Arrays;

/* loaded from: classes3.dex */
public final class zzha {
    private static final zzha zza = new zzha(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private boolean zze;

    private zzha(int i2, int[] iArr, Object[] objArr, boolean z2) {
        this.zzb = i2;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = z2;
    }

    static zzha b(zzha zzhaVar, zzha zzhaVar2) {
        int i2 = zzhaVar.zzb + zzhaVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzhaVar.zzc, i2);
        System.arraycopy(zzhaVar2.zzc, 0, iArrCopyOf, zzhaVar.zzb, zzhaVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzhaVar.zzd, i2);
        System.arraycopy(zzhaVar2.zzd, 0, objArrCopyOf, zzhaVar.zzb, zzhaVar2.zzb);
        return new zzha(i2, iArrCopyOf, objArrCopyOf, true);
    }

    static zzha c() {
        return new zzha(0, new int[8], new Object[8], true);
    }

    public static zzha zza() {
        return zza;
    }

    private final void zzi(int i2) {
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

    final zzha a(zzha zzhaVar) {
        if (zzhaVar.equals(zza)) {
            return this;
        }
        d();
        int i2 = this.zzb + zzhaVar.zzb;
        zzi(i2);
        System.arraycopy(zzhaVar.zzc, 0, this.zzc, this.zzb, zzhaVar.zzb);
        System.arraycopy(zzhaVar.zzd, 0, this.zzd, this.zzb, zzhaVar.zzb);
        this.zzb = i2;
        return this;
    }

    final void d() {
        if (!this.zze) {
            throw new UnsupportedOperationException();
        }
    }

    final void e(StringBuilder sb, int i2) {
        for (int i3 = 0; i3 < this.zzb; i3++) {
            zzfz.b(sb, i2, String.valueOf(this.zzc[i3] >>> 3), this.zzd[i3]);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzha)) {
            return false;
        }
        zzha zzhaVar = (zzha) obj;
        int i2 = this.zzb;
        if (i2 == zzhaVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzhaVar.zzc;
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    Object[] objArr = this.zzd;
                    Object[] objArr2 = zzhaVar.zzd;
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

    final void f(int i2, Object obj) {
        d();
        zzi(this.zzb + 1);
        int[] iArr = this.zzc;
        int i3 = this.zzb;
        iArr[i3] = i2;
        this.zzd[i3] = obj;
        this.zzb = i3 + 1;
    }

    public final int hashCode() {
        int i2 = this.zzb;
        int i3 = i2 + 527;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i2; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i3 * 31) + i4;
        Object[] objArr = this.zzd;
        int i7 = this.zzb;
        for (int i8 = 0; i8 < i7; i8++) {
            iHashCode = (iHashCode * 31) + objArr[i8].hashCode();
        }
        return (i6 * 31) + iHashCode;
    }

    public final void zzf() {
        if (this.zze) {
            this.zze = false;
        }
    }

    private zzha() {
        this(0, new int[8], new Object[8], true);
    }
}
