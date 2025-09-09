package com.google.android.gms.internal.auth;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import sun.misc.Unsafe;

/* loaded from: classes3.dex */
final class zzga<T> implements zzgi<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzhj.g();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzfx zzg;
    private final int[] zzh;
    private final int zzi;
    private final int zzj;
    private final zzfl zzk;
    private final zzgz zzl;
    private final zzem zzm;
    private final zzgc zzn;
    private final zzfs zzo;

    private zzga(int[] iArr, Object[] objArr, int i2, int i3, zzfx zzfxVar, int i4, boolean z2, int[] iArr2, int i5, int i6, zzgc zzgcVar, zzfl zzflVar, zzgz zzgzVar, zzem zzemVar, zzfs zzfsVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i2;
        this.zzf = i3;
        this.zzh = iArr2;
        this.zzi = i5;
        this.zzj = i6;
        this.zzn = zzgcVar;
        this.zzk = zzflVar;
        this.zzl = zzgzVar;
        this.zzm = zzemVar;
        this.zzg = zzfxVar;
        this.zzo = zzfsVar;
    }

    static zzha b(Object obj) {
        zzev zzevVar = (zzev) obj;
        zzha zzhaVar = zzevVar.zzc;
        if (zzhaVar != zzha.zza()) {
            return zzhaVar;
        }
        zzha zzhaVarC = zzha.c();
        zzevVar.zzc = zzhaVarC;
        return zzhaVarC;
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x026f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.google.android.gms.internal.auth.zzga c(java.lang.Class r31, com.google.android.gms.internal.auth.zzfu r32, com.google.android.gms.internal.auth.zzgc r33, com.google.android.gms.internal.auth.zzfl r34, com.google.android.gms.internal.auth.zzgz r35, com.google.android.gms.internal.auth.zzem r36, com.google.android.gms.internal.auth.zzfs r37) {
        /*
            Method dump skipped, instructions count: 1010
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzga.c(java.lang.Class, com.google.android.gms.internal.auth.zzfu, com.google.android.gms.internal.auth.zzgc, com.google.android.gms.internal.auth.zzfl, com.google.android.gms.internal.auth.zzgz, com.google.android.gms.internal.auth.zzem, com.google.android.gms.internal.auth.zzfs):com.google.android.gms.internal.auth.zzga");
    }

    private final void zzA(Object obj, int i2, int i3) {
        zzhj.n(obj, zzl(i3) & 1048575, i2);
    }

    private final void zzB(Object obj, int i2, Object obj2) {
        zzb.putObject(obj, zzo(i2) & 1048575, obj2);
        zzz(obj, i2);
    }

    private final void zzC(Object obj, int i2, int i3, Object obj2) {
        zzb.putObject(obj, zzo(i3) & 1048575, obj2);
        zzA(obj, i2, i3);
    }

    private final boolean zzD(Object obj, Object obj2, int i2) {
        return zzE(obj, i2) == zzE(obj2, i2);
    }

    private final boolean zzE(Object obj, int i2) {
        int iZzl = zzl(i2);
        long j2 = iZzl & 1048575;
        if (j2 != 1048575) {
            return (zzhj.c(obj, j2) & (1 << (iZzl >>> 20))) != 0;
        }
        int iZzo = zzo(i2);
        long j3 = iZzo & 1048575;
        switch (zzn(iZzo)) {
            case 0:
                return Double.doubleToRawLongBits(zzhj.a(obj, j3)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzhj.b(obj, j3)) != 0;
            case 2:
                return zzhj.d(obj, j3) != 0;
            case 3:
                return zzhj.d(obj, j3) != 0;
            case 4:
                return zzhj.c(obj, j3) != 0;
            case 5:
                return zzhj.d(obj, j3) != 0;
            case 6:
                return zzhj.c(obj, j3) != 0;
            case 7:
                return zzhj.t(obj, j3);
            case 8:
                Object objF = zzhj.f(obj, j3);
                if (objF instanceof String) {
                    return !((String) objF).isEmpty();
                }
                if (objF instanceof zzef) {
                    return !zzef.zzb.equals(objF);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzhj.f(obj, j3) != null;
            case 10:
                return !zzef.zzb.equals(zzhj.f(obj, j3));
            case 11:
                return zzhj.c(obj, j3) != 0;
            case 12:
                return zzhj.c(obj, j3) != 0;
            case 13:
                return zzhj.c(obj, j3) != 0;
            case 14:
                return zzhj.d(obj, j3) != 0;
            case 15:
                return zzhj.c(obj, j3) != 0;
            case 16:
                return zzhj.d(obj, j3) != 0;
            case 17:
                return zzhj.f(obj, j3) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzF(Object obj, int i2, int i3, int i4, int i5) {
        return i3 == 1048575 ? zzE(obj, i2) : (i4 & i5) != 0;
    }

    private static boolean zzG(Object obj, int i2, zzgi zzgiVar) {
        return zzgiVar.zzi(zzhj.f(obj, i2 & 1048575));
    }

    private static boolean zzH(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzev) {
            return ((zzev) obj).l();
        }
        return true;
    }

    private final boolean zzI(Object obj, int i2, int i3) {
        return zzhj.c(obj, (long) (zzl(i3) & 1048575)) == i2;
    }

    private static int zzk(Object obj, long j2) {
        return ((Integer) zzhj.f(obj, j2)).intValue();
    }

    private final int zzl(int i2) {
        return this.zzc[i2 + 2];
    }

    private final int zzm(int i2, int i3) {
        int length = (this.zzc.length / 3) - 1;
        while (i3 <= length) {
            int i4 = (length + i3) >>> 1;
            int i5 = i4 * 3;
            int i6 = this.zzc[i5];
            if (i2 == i6) {
                return i5;
            }
            if (i2 < i6) {
                length = i4 - 1;
            } else {
                i3 = i4 + 1;
            }
        }
        return -1;
    }

    private static int zzn(int i2) {
        return (i2 >>> 20) & 255;
    }

    private final int zzo(int i2) {
        return this.zzc[i2 + 1];
    }

    private static long zzp(Object obj, long j2) {
        return ((Long) zzhj.f(obj, j2)).longValue();
    }

    private final zzey zzq(int i2) {
        int i3 = i2 / 3;
        return (zzey) this.zzd[i3 + i3 + 1];
    }

    private final zzgi zzr(int i2) {
        int i3 = i2 / 3;
        int i4 = i3 + i3;
        zzgi zzgiVar = (zzgi) this.zzd[i4];
        if (zzgiVar != null) {
            return zzgiVar;
        }
        zzgi zzgiVarZzb = zzgf.zza().zzb((Class) this.zzd[i4 + 1]);
        this.zzd[i4] = zzgiVarZzb;
        return zzgiVarZzb;
    }

    private final Object zzs(int i2) {
        int i3 = i2 / 3;
        return this.zzd[i3 + i3];
    }

    private final Object zzt(Object obj, int i2) {
        zzgi zzgiVarZzr = zzr(i2);
        int iZzo = zzo(i2) & 1048575;
        if (!zzE(obj, i2)) {
            return zzgiVarZzr.zzd();
        }
        Object object = zzb.getObject(obj, iZzo);
        if (zzH(object)) {
            return object;
        }
        Object objZzd = zzgiVarZzr.zzd();
        if (object != null) {
            zzgiVarZzr.zzf(objZzd, object);
        }
        return objZzd;
    }

    private final Object zzu(Object obj, int i2, int i3) {
        zzgi zzgiVarZzr = zzr(i3);
        if (!zzI(obj, i2, i3)) {
            return zzgiVarZzr.zzd();
        }
        Object object = zzb.getObject(obj, zzo(i3) & 1048575);
        if (zzH(object)) {
            return object;
        }
        Object objZzd = zzgiVarZzr.zzd();
        if (object != null) {
            zzgiVarZzr.zzf(objZzd, object);
        }
        return objZzd;
    }

    private static Field zzv(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private static void zzw(Object obj) {
        if (!zzH(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(obj)));
        }
    }

    private final void zzx(Object obj, Object obj2, int i2) {
        if (zzE(obj2, i2)) {
            int iZzo = zzo(i2) & 1048575;
            Unsafe unsafe = zzb;
            long j2 = iZzo;
            Object object = unsafe.getObject(obj2, j2);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i2] + " is present but null: " + obj2.toString());
            }
            zzgi zzgiVarZzr = zzr(i2);
            if (!zzE(obj, i2)) {
                if (zzH(object)) {
                    Object objZzd = zzgiVarZzr.zzd();
                    zzgiVarZzr.zzf(objZzd, object);
                    unsafe.putObject(obj, j2, objZzd);
                } else {
                    unsafe.putObject(obj, j2, object);
                }
                zzz(obj, i2);
                return;
            }
            Object object2 = unsafe.getObject(obj, j2);
            if (!zzH(object2)) {
                Object objZzd2 = zzgiVarZzr.zzd();
                zzgiVarZzr.zzf(objZzd2, object2);
                unsafe.putObject(obj, j2, objZzd2);
                object2 = objZzd2;
            }
            zzgiVarZzr.zzf(object2, object);
        }
    }

    private final void zzy(Object obj, Object obj2, int i2) {
        int i3 = this.zzc[i2];
        if (zzI(obj2, i3, i2)) {
            int iZzo = zzo(i2) & 1048575;
            Unsafe unsafe = zzb;
            long j2 = iZzo;
            Object object = unsafe.getObject(obj2, j2);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i2] + " is present but null: " + obj2.toString());
            }
            zzgi zzgiVarZzr = zzr(i2);
            if (!zzI(obj, i3, i2)) {
                if (zzH(object)) {
                    Object objZzd = zzgiVarZzr.zzd();
                    zzgiVarZzr.zzf(objZzd, object);
                    unsafe.putObject(obj, j2, objZzd);
                } else {
                    unsafe.putObject(obj, j2, object);
                }
                zzA(obj, i3, i2);
                return;
            }
            Object object2 = unsafe.getObject(obj, j2);
            if (!zzH(object2)) {
                Object objZzd2 = zzgiVarZzr.zzd();
                zzgiVarZzr.zzf(objZzd2, object2);
                unsafe.putObject(obj, j2, objZzd2);
                object2 = objZzd2;
            }
            zzgiVarZzr.zzf(object2, object);
        }
    }

    private final void zzz(Object obj, int i2) {
        int iZzl = zzl(i2);
        long j2 = 1048575 & iZzl;
        if (j2 == 1048575) {
            return;
        }
        zzhj.n(obj, j2, (1 << (iZzl >>> 20)) | zzhj.c(obj, j2));
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0b6c A[PHI: r1 r2 r7 r8 r10 r11 r12 r13 r15 r24 r25 r43
      0x0b6c: PHI (r1v163 byte[]) = (r1v102 byte[]), (r1v103 byte[]), (r1v104 byte[]), (r1v109 byte[]), (r1v153 byte[]), (r1v170 byte[]) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r2v95 com.google.android.gms.internal.auth.zzga<T>) = 
      (r2v43 com.google.android.gms.internal.auth.zzga<T>)
      (r2v44 com.google.android.gms.internal.auth.zzga<T>)
      (r2v45 com.google.android.gms.internal.auth.zzga<T>)
      (r2v50 com.google.android.gms.internal.auth.zzga<T>)
      (r2v85 com.google.android.gms.internal.auth.zzga<T>)
      (r2v101 com.google.android.gms.internal.auth.zzga<T>)
     binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r7v32 com.google.android.gms.internal.auth.zzdt) = 
      (r7v10 com.google.android.gms.internal.auth.zzdt)
      (r7v11 com.google.android.gms.internal.auth.zzdt)
      (r7v12 com.google.android.gms.internal.auth.zzdt)
      (r7v14 com.google.android.gms.internal.auth.zzdt)
      (r7v30 com.google.android.gms.internal.auth.zzdt)
      (r7v38 com.google.android.gms.internal.auth.zzdt)
     binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r8v137 int) = (r8v71 int), (r8v72 int), (r8v73 int), (r8v76 int), (r8v132 int), (r8v146 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r10v85 int) = (r10v52 int), (r10v53 int), (r10v54 int), (r10v56 int), (r10v80 int), (r10v92 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r11v62 int) = (r11v38 int), (r11v39 int), (r11v40 int), (r11v42 int), (r11v58 int), (r11v68 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r12v86 int) = (r12v60 int), (r12v61 int), (r12v62 int), (r12v64 int), (r12v81 int), (r12v93 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r13v69 int) = (r13v46 int), (r13v47 int), (r13v48 int), (r13v50 int), (r13v67 int), (r13v75 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r15v32 int) = (r15v9 int), (r15v10 int), (r15v11 int), (r15v13 int), (r15v28 int), (r15v38 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r24v29 int) = (r24v6 int), (r24v7 int), (r24v8 int), (r24v10 int), (r24v25 int), (r24v34 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r25v35 int) = (r25v8 int), (r25v9 int), (r25v10 int), (r25v12 int), (r25v33 int), (r25v40 int) binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]
      0x0b6c: PHI (r43v34 sun.misc.Unsafe) = 
      (r43v11 sun.misc.Unsafe)
      (r43v12 sun.misc.Unsafe)
      (r43v13 sun.misc.Unsafe)
      (r43v15 sun.misc.Unsafe)
      (r43v30 sun.misc.Unsafe)
      (r43v39 sun.misc.Unsafe)
     binds: [B:455:0x0b43, B:440:0x0ae3, B:424:0x0a89, B:409:0x0a19, B:216:0x0649, B:198:0x05c8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:624:0x0b6f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:627:0x0ec1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:629:0x0055 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:674:0x0b89 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:675:0x0edc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0283  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int a(java.lang.Object r41, byte[] r42, int r43, int r44, int r45, com.google.android.gms.internal.auth.zzdt r46) throws com.google.android.gms.internal.auth.zzfb {
        /*
            Method dump skipped, instructions count: 4110
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzga.a(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.auth.zzdt):int");
    }

    @Override // com.google.android.gms.internal.auth.zzgi
    public final int zza(Object obj) {
        int i2;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int length = this.zzc.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iZzo = zzo(i4);
            int i5 = this.zzc[i4];
            long j2 = 1048575 & iZzo;
            int iHashCode = 37;
            switch (zzn(iZzo)) {
                case 0:
                    i2 = i3 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzhj.a(obj, j2));
                    byte[] bArr = zzfa.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 1:
                    i2 = i3 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzhj.b(obj, j2));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 2:
                    i2 = i3 * 53;
                    jDoubleToLongBits = zzhj.d(obj, j2);
                    byte[] bArr2 = zzfa.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 3:
                    i2 = i3 * 53;
                    jDoubleToLongBits = zzhj.d(obj, j2);
                    byte[] bArr3 = zzfa.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 4:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.c(obj, j2);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 5:
                    i2 = i3 * 53;
                    jDoubleToLongBits = zzhj.d(obj, j2);
                    byte[] bArr4 = zzfa.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 6:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.c(obj, j2);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 7:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzfa.zza(zzhj.t(obj, j2));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 8:
                    i2 = i3 * 53;
                    iFloatToIntBits = ((String) zzhj.f(obj, j2)).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 9:
                    Object objF = zzhj.f(obj, j2);
                    if (objF != null) {
                        iHashCode = objF.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.f(obj, j2).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 11:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.c(obj, j2);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 12:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.c(obj, j2);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 13:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.c(obj, j2);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 14:
                    i2 = i3 * 53;
                    jDoubleToLongBits = zzhj.d(obj, j2);
                    byte[] bArr5 = zzfa.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 15:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.c(obj, j2);
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 16:
                    i2 = i3 * 53;
                    jDoubleToLongBits = zzhj.d(obj, j2);
                    byte[] bArr6 = zzfa.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 17:
                    Object objF2 = zzhj.f(obj, j2);
                    if (objF2 != null) {
                        iHashCode = objF2.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.f(obj, j2).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 50:
                    i2 = i3 * 53;
                    iFloatToIntBits = zzhj.f(obj, j2).hashCode();
                    i3 = i2 + iFloatToIntBits;
                    break;
                case 51:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(((Double) zzhj.f(obj, j2)).doubleValue());
                        byte[] bArr7 = zzfa.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = Float.floatToIntBits(((Float) zzhj.f(obj, j2)).floatValue());
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j2);
                        byte[] bArr8 = zzfa.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j2);
                        byte[] bArr9 = zzfa.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzk(obj, j2);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j2);
                        byte[] bArr10 = zzfa.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzk(obj, j2);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzfa.zza(((Boolean) zzhj.f(obj, j2)).booleanValue());
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = ((String) zzhj.f(obj, j2)).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzhj.f(obj, j2).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzhj.f(obj, j2).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzk(obj, j2);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzk(obj, j2);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzk(obj, j2);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j2);
                        byte[] bArr11 = zzfa.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzk(obj, j2);
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        jDoubleToLongBits = zzp(obj, j2);
                        byte[] bArr12 = zzfa.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzI(obj, i5, i4)) {
                        i2 = i3 * 53;
                        iFloatToIntBits = zzhj.f(obj, j2).hashCode();
                        i3 = i2 + iFloatToIntBits;
                        break;
                    } else {
                        break;
                    }
            }
        }
        return (i3 * 53) + this.zzl.b(obj).hashCode();
    }

    @Override // com.google.android.gms.internal.auth.zzgi
    public final Object zzd() {
        return ((zzev) this.zzg).c();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006c  */
    @Override // com.google.android.gms.internal.auth.zzgi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zze(java.lang.Object r8) {
        /*
            r7 = this;
            boolean r0 = zzH(r8)
            if (r0 != 0) goto L7
            return
        L7:
            boolean r0 = r8 instanceof com.google.android.gms.internal.auth.zzev
            r1 = 0
            if (r0 == 0) goto L1a
            r0 = r8
            com.google.android.gms.internal.auth.zzev r0 = (com.google.android.gms.internal.auth.zzev) r0
            r2 = 2147483647(0x7fffffff, float:NaN)
            r0.k(r2)
            r0.zza = r1
            r0.i()
        L1a:
            int[] r0 = r7.zzc
            int r0 = r0.length
        L1d:
            if (r1 >= r0) goto L82
            int r2 = r7.zzo(r1)
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r3 = r3 & r2
            int r2 = zzn(r2)
            long r3 = (long) r3
            r5 = 9
            if (r2 == r5) goto L6c
            r5 = 60
            if (r2 == r5) goto L54
            r5 = 68
            if (r2 == r5) goto L54
            switch(r2) {
                case 17: goto L6c;
                case 18: goto L4e;
                case 19: goto L4e;
                case 20: goto L4e;
                case 21: goto L4e;
                case 22: goto L4e;
                case 23: goto L4e;
                case 24: goto L4e;
                case 25: goto L4e;
                case 26: goto L4e;
                case 27: goto L4e;
                case 28: goto L4e;
                case 29: goto L4e;
                case 30: goto L4e;
                case 31: goto L4e;
                case 32: goto L4e;
                case 33: goto L4e;
                case 34: goto L4e;
                case 35: goto L4e;
                case 36: goto L4e;
                case 37: goto L4e;
                case 38: goto L4e;
                case 39: goto L4e;
                case 40: goto L4e;
                case 41: goto L4e;
                case 42: goto L4e;
                case 43: goto L4e;
                case 44: goto L4e;
                case 45: goto L4e;
                case 46: goto L4e;
                case 47: goto L4e;
                case 48: goto L4e;
                case 49: goto L4e;
                case 50: goto L3c;
                default: goto L3b;
            }
        L3b:
            goto L7f
        L3c:
            sun.misc.Unsafe r2 = com.google.android.gms.internal.auth.zzga.zzb
            java.lang.Object r5 = r2.getObject(r8, r3)
            if (r5 == 0) goto L7f
            r6 = r5
            com.google.android.gms.internal.auth.zzfr r6 = (com.google.android.gms.internal.auth.zzfr) r6
            r6.zzc()
            r2.putObject(r8, r3, r5)
            goto L7f
        L4e:
            com.google.android.gms.internal.auth.zzfl r2 = r7.zzk
            r2.a(r8, r3)
            goto L7f
        L54:
            int[] r2 = r7.zzc
            r2 = r2[r1]
            boolean r2 = r7.zzI(r8, r2, r1)
            if (r2 == 0) goto L7f
            com.google.android.gms.internal.auth.zzgi r2 = r7.zzr(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.auth.zzga.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zze(r3)
            goto L7f
        L6c:
            boolean r2 = r7.zzE(r8, r1)
            if (r2 == 0) goto L7f
            com.google.android.gms.internal.auth.zzgi r2 = r7.zzr(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.auth.zzga.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zze(r3)
        L7f:
            int r1 = r1 + 3
            goto L1d
        L82:
            com.google.android.gms.internal.auth.zzgz r0 = r7.zzl
            r0.e(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzga.zze(java.lang.Object):void");
    }

    @Override // com.google.android.gms.internal.auth.zzgi
    public final void zzf(Object obj, Object obj2) {
        zzw(obj);
        obj2.getClass();
        for (int i2 = 0; i2 < this.zzc.length; i2 += 3) {
            int iZzo = zzo(i2);
            int i3 = this.zzc[i2];
            long j2 = 1048575 & iZzo;
            switch (zzn(iZzo)) {
                case 0:
                    if (zzE(obj2, i2)) {
                        zzhj.l(obj, j2, zzhj.a(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzE(obj2, i2)) {
                        zzhj.m(obj, j2, zzhj.b(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzE(obj2, i2)) {
                        zzhj.o(obj, j2, zzhj.d(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzE(obj2, i2)) {
                        zzhj.o(obj, j2, zzhj.d(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzE(obj2, i2)) {
                        zzhj.n(obj, j2, zzhj.c(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzE(obj2, i2)) {
                        zzhj.o(obj, j2, zzhj.d(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzE(obj2, i2)) {
                        zzhj.n(obj, j2, zzhj.c(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzE(obj2, i2)) {
                        zzhj.k(obj, j2, zzhj.t(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzE(obj2, i2)) {
                        zzhj.p(obj, j2, zzhj.f(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzx(obj, obj2, i2);
                    break;
                case 10:
                    if (zzE(obj2, i2)) {
                        zzhj.p(obj, j2, zzhj.f(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzE(obj2, i2)) {
                        zzhj.n(obj, j2, zzhj.c(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzE(obj2, i2)) {
                        zzhj.n(obj, j2, zzhj.c(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzE(obj2, i2)) {
                        zzhj.n(obj, j2, zzhj.c(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzE(obj2, i2)) {
                        zzhj.o(obj, j2, zzhj.d(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzE(obj2, i2)) {
                        zzhj.n(obj, j2, zzhj.c(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzE(obj2, i2)) {
                        zzhj.o(obj, j2, zzhj.d(obj2, j2));
                        zzz(obj, i2);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzx(obj, obj2, i2);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzk.b(obj, obj2, j2);
                    break;
                case 50:
                    int i4 = zzgk.zza;
                    zzhj.p(obj, j2, zzfs.zza(zzhj.f(obj, j2), zzhj.f(obj2, j2)));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzI(obj2, i3, i2)) {
                        zzhj.p(obj, j2, zzhj.f(obj2, j2));
                        zzA(obj, i3, i2);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzy(obj, obj2, i2);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzI(obj2, i3, i2)) {
                        zzhj.p(obj, j2, zzhj.f(obj2, j2));
                        zzA(obj, i3, i2);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzy(obj, obj2, i2);
                    break;
            }
        }
        zzgk.b(this.zzl, obj, obj2);
    }

    @Override // com.google.android.gms.internal.auth.zzgi
    public final void zzg(Object obj, byte[] bArr, int i2, int i3, zzdt zzdtVar) throws IOException {
        a(obj, bArr, i2, i3, 0, zzdtVar);
    }

    @Override // com.google.android.gms.internal.auth.zzgi
    public final boolean zzh(Object obj, Object obj2) {
        boolean zC;
        int length = this.zzc.length;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int iZzo = zzo(i2);
            long j2 = iZzo & 1048575;
            switch (zzn(iZzo)) {
                case 0:
                    if (!zzD(obj, obj2, i2) || Double.doubleToLongBits(zzhj.a(obj, j2)) != Double.doubleToLongBits(zzhj.a(obj2, j2))) {
                        return false;
                    }
                    continue;
                    break;
                case 1:
                    if (!zzD(obj, obj2, i2) || Float.floatToIntBits(zzhj.b(obj, j2)) != Float.floatToIntBits(zzhj.b(obj2, j2))) {
                        return false;
                    }
                    continue;
                    break;
                case 2:
                    if (!zzD(obj, obj2, i2) || zzhj.d(obj, j2) != zzhj.d(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 3:
                    if (!zzD(obj, obj2, i2) || zzhj.d(obj, j2) != zzhj.d(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 4:
                    if (!zzD(obj, obj2, i2) || zzhj.c(obj, j2) != zzhj.c(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 5:
                    if (!zzD(obj, obj2, i2) || zzhj.d(obj, j2) != zzhj.d(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 6:
                    if (!zzD(obj, obj2, i2) || zzhj.c(obj, j2) != zzhj.c(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 7:
                    if (!zzD(obj, obj2, i2) || zzhj.t(obj, j2) != zzhj.t(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 8:
                    if (!zzD(obj, obj2, i2) || !zzgk.c(zzhj.f(obj, j2), zzhj.f(obj2, j2))) {
                        return false;
                    }
                    continue;
                    break;
                case 9:
                    if (!zzD(obj, obj2, i2) || !zzgk.c(zzhj.f(obj, j2), zzhj.f(obj2, j2))) {
                        return false;
                    }
                    continue;
                    break;
                case 10:
                    if (!zzD(obj, obj2, i2) || !zzgk.c(zzhj.f(obj, j2), zzhj.f(obj2, j2))) {
                        return false;
                    }
                    continue;
                    break;
                case 11:
                    if (!zzD(obj, obj2, i2) || zzhj.c(obj, j2) != zzhj.c(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 12:
                    if (!zzD(obj, obj2, i2) || zzhj.c(obj, j2) != zzhj.c(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 13:
                    if (!zzD(obj, obj2, i2) || zzhj.c(obj, j2) != zzhj.c(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 14:
                    if (!zzD(obj, obj2, i2) || zzhj.d(obj, j2) != zzhj.d(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 15:
                    if (!zzD(obj, obj2, i2) || zzhj.c(obj, j2) != zzhj.c(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 16:
                    if (!zzD(obj, obj2, i2) || zzhj.d(obj, j2) != zzhj.d(obj2, j2)) {
                        return false;
                    }
                    continue;
                    break;
                case 17:
                    if (!zzD(obj, obj2, i2) || !zzgk.c(zzhj.f(obj, j2), zzhj.f(obj2, j2))) {
                        return false;
                    }
                    continue;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zC = zzgk.c(zzhj.f(obj, j2), zzhj.f(obj2, j2));
                    break;
                case 50:
                    zC = zzgk.c(zzhj.f(obj, j2), zzhj.f(obj2, j2));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzl = zzl(i2) & 1048575;
                    if (zzhj.c(obj, jZzl) != zzhj.c(obj2, jZzl) || !zzgk.c(zzhj.f(obj, j2), zzhj.f(obj2, j2))) {
                        return false;
                    }
                    continue;
                    break;
                default:
            }
            if (!zC) {
                return false;
            }
        }
        return this.zzl.b(obj).equals(this.zzl.b(obj2));
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x009b  */
    @Override // com.google.android.gms.internal.auth.zzgi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzi(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzga.zzi(java.lang.Object):boolean");
    }
}
