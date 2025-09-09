package com.google.android.gms.internal.measurement;

import androidx.media3.common.C;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* loaded from: classes3.dex */
final class zznn<T> implements zzob<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzpc.n();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zznj zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final zznu zzj;
    private final boolean zzk;
    private final int[] zzl;
    private final int zzm;
    private final int zzn;
    private final zznr zzo;
    private final zzmo zzp;
    private final zzow<?, ?> zzq;
    private final zzll<?> zzr;
    private final zznc zzs;

    private zznn(int[] iArr, Object[] objArr, int i2, int i3, zznj zznjVar, zznu zznuVar, boolean z2, int[] iArr2, int i4, int i5, zznr zznrVar, zzmo zzmoVar, zzow<?, ?> zzowVar, zzll<?> zzllVar, zznc zzncVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i2;
        this.zzf = i3;
        this.zzi = zznjVar instanceof zzlw;
        this.zzj = zznuVar;
        this.zzh = zzllVar != null && zzllVar.h(zznjVar);
        this.zzk = false;
        this.zzl = iArr2;
        this.zzm = i4;
        this.zzn = i5;
        this.zzo = zznrVar;
        this.zzp = zzmoVar;
        this.zzq = zzowVar;
        this.zzr = zzllVar;
        this.zzg = zznjVar;
        this.zzs = zzncVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0279  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.google.android.gms.internal.measurement.zznn b(java.lang.Class r33, com.google.android.gms.internal.measurement.zznh r34, com.google.android.gms.internal.measurement.zznr r35, com.google.android.gms.internal.measurement.zzmo r36, com.google.android.gms.internal.measurement.zzow r37, com.google.android.gms.internal.measurement.zzll r38, com.google.android.gms.internal.measurement.zznc r39) {
        /*
            Method dump skipped, instructions count: 1038
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznn.b(java.lang.Class, com.google.android.gms.internal.measurement.zznh, com.google.android.gms.internal.measurement.zznr, com.google.android.gms.internal.measurement.zzmo, com.google.android.gms.internal.measurement.zzow, com.google.android.gms.internal.measurement.zzll, com.google.android.gms.internal.measurement.zznc):com.google.android.gms.internal.measurement.zznn");
    }

    private static <T> double zza(T t2, long j2) {
        return ((Double) zzpc.v(t2, j2)).doubleValue();
    }

    private static <T> float zzb(T t2, long j2) {
        return ((Float) zzpc.v(t2, j2)).floatValue();
    }

    private static <T> int zzc(T t2, long j2) {
        return ((Integer) zzpc.v(t2, j2)).intValue();
    }

    private static <T> long zzd(T t2, long j2) {
        return ((Long) zzpc.v(t2, j2)).longValue();
    }

    private final zzob zze(int i2) {
        int i3 = (i2 / 3) << 1;
        zzob zzobVar = (zzob) this.zzd[i3];
        if (zzobVar != null) {
            return zzobVar;
        }
        zzob<T> zzobVarZza = zznx.zza().zza((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzobVarZza;
        return zzobVarZza;
    }

    private final Object zzf(int i2) {
        return this.zzd[(i2 / 3) << 1];
    }

    private static boolean zzg(int i2) {
        return (i2 & C.BUFFER_FLAG_LAST_SAMPLE) != 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:423:0x09f2, code lost:
    
        throw com.google.android.gms.internal.measurement.zzme.zzh();
     */
    /* JADX WARN: Code restructure failed: missing block: B:520:0x0cfe, code lost:
    
        if (r14 == r0) goto L522;
     */
    /* JADX WARN: Code restructure failed: missing block: B:521:0x0d00, code lost:
    
        r27.putInt(r7, r14, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:522:0x0d06, code lost:
    
        r10 = r9.zzm;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:524:0x0d0d, code lost:
    
        if (r10 >= r9.zzn) goto L635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:525:0x0d0f, code lost:
    
        r3 = (com.google.android.gms.internal.measurement.zzoz) zza((java.lang.Object) r31, r9.zzl[r10], (int) r3, (com.google.android.gms.internal.measurement.zzow<UT, int>) r9.zzq, (java.lang.Object) r31);
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:526:0x0d25, code lost:
    
        if (r3 == null) goto L528;
     */
    /* JADX WARN: Code restructure failed: missing block: B:527:0x0d27, code lost:
    
        r9.zzq.n(r7, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:528:0x0d2c, code lost:
    
        if (r6 != 0) goto L534;
     */
    /* JADX WARN: Code restructure failed: missing block: B:530:0x0d30, code lost:
    
        if (r8 != r34) goto L532;
     */
    /* JADX WARN: Code restructure failed: missing block: B:533:0x0d37, code lost:
    
        throw com.google.android.gms.internal.measurement.zzme.zzg();
     */
    /* JADX WARN: Code restructure failed: missing block: B:535:0x0d3a, code lost:
    
        if (r8 > r34) goto L538;
     */
    /* JADX WARN: Code restructure failed: missing block: B:536:0x0d3c, code lost:
    
        if (r11 != r6) goto L538;
     */
    /* JADX WARN: Code restructure failed: missing block: B:537:0x0d3e, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:539:0x0d43, code lost:
    
        throw com.google.android.gms.internal.measurement.zzme.zzg();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:378:0x08c5  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x08d2  */
    /* JADX WARN: Removed duplicated region for block: B:499:0x0c73 A[PHI: r4 r5 r6 r8 r13 r14 r19
      0x0c73: PHI (r4v94 int) = 
      (r4v69 int)
      (r4v70 int)
      (r4v71 int)
      (r4v72 int)
      (r4v73 int)
      (r4v75 int)
      (r4v76 int)
      (r4v77 int)
      (r4v84 int)
      (r4v90 int)
      (r4v95 int)
     binds: [B:497:0x0c5f, B:494:0x0c3e, B:491:0x0c1e, B:488:0x0bfe, B:485:0x0bdf, B:481:0x0bbd, B:474:0x0b93, B:460:0x0b55, B:440:0x0a99, B:434:0x0a61, B:428:0x0a1b] A[DONT_GENERATE, DONT_INLINE]
      0x0c73: PHI (r5v97 com.google.android.gms.internal.measurement.zznn<T>) = 
      (r5v72 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v73 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v74 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v75 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v76 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v78 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v79 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v80 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v87 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v93 com.google.android.gms.internal.measurement.zznn<T>)
      (r5v98 com.google.android.gms.internal.measurement.zznn<T>)
     binds: [B:497:0x0c5f, B:494:0x0c3e, B:491:0x0c1e, B:488:0x0bfe, B:485:0x0bdf, B:481:0x0bbd, B:474:0x0b93, B:460:0x0b55, B:440:0x0a99, B:434:0x0a61, B:428:0x0a1b] A[DONT_GENERATE, DONT_INLINE]
      0x0c73: PHI (r6v37 int) = 
      (r6v13 int)
      (r6v14 int)
      (r6v15 int)
      (r6v16 int)
      (r6v17 int)
      (r6v19 int)
      (r6v20 int)
      (r6v21 int)
      (r6v26 int)
      (r6v32 int)
      (r6v38 int)
     binds: [B:497:0x0c5f, B:494:0x0c3e, B:491:0x0c1e, B:488:0x0bfe, B:485:0x0bdf, B:481:0x0bbd, B:474:0x0b93, B:460:0x0b55, B:440:0x0a99, B:434:0x0a61, B:428:0x0a1b] A[DONT_GENERATE, DONT_INLINE]
      0x0c73: PHI (r8v109 int) = 
      (r8v85 int)
      (r8v86 int)
      (r8v87 int)
      (r8v88 int)
      (r8v89 int)
      (r8v91 int)
      (r8v92 int)
      (r8v93 int)
      (r8v101 int)
      (r8v105 int)
      (r8v110 int)
     binds: [B:497:0x0c5f, B:494:0x0c3e, B:491:0x0c1e, B:488:0x0bfe, B:485:0x0bdf, B:481:0x0bbd, B:474:0x0b93, B:460:0x0b55, B:440:0x0a99, B:434:0x0a61, B:428:0x0a1b] A[DONT_GENERATE, DONT_INLINE]
      0x0c73: PHI (r13v88 com.google.android.gms.internal.measurement.zzkh) = 
      (r13v64 com.google.android.gms.internal.measurement.zzkh)
      (r13v65 com.google.android.gms.internal.measurement.zzkh)
      (r13v66 com.google.android.gms.internal.measurement.zzkh)
      (r13v67 com.google.android.gms.internal.measurement.zzkh)
      (r13v68 com.google.android.gms.internal.measurement.zzkh)
      (r13v70 com.google.android.gms.internal.measurement.zzkh)
      (r13v71 com.google.android.gms.internal.measurement.zzkh)
      (r13v72 com.google.android.gms.internal.measurement.zzkh)
      (r13v77 com.google.android.gms.internal.measurement.zzkh)
      (r13v83 com.google.android.gms.internal.measurement.zzkh)
      (r13v89 com.google.android.gms.internal.measurement.zzkh)
     binds: [B:497:0x0c5f, B:494:0x0c3e, B:491:0x0c1e, B:488:0x0bfe, B:485:0x0bdf, B:481:0x0bbd, B:474:0x0b93, B:460:0x0b55, B:440:0x0a99, B:434:0x0a61, B:428:0x0a1b] A[DONT_GENERATE, DONT_INLINE]
      0x0c73: PHI (r14v88 byte[]) = 
      (r14v61 byte[])
      (r14v62 byte[])
      (r14v63 byte[])
      (r14v64 byte[])
      (r14v65 byte[])
      (r14v67 byte[])
      (r14v68 byte[])
      (r14v69 byte[])
      (r14v74 byte[])
      (r14v83 byte[])
      (r14v89 byte[])
     binds: [B:497:0x0c5f, B:494:0x0c3e, B:491:0x0c1e, B:488:0x0bfe, B:485:0x0bdf, B:481:0x0bbd, B:474:0x0b93, B:460:0x0b55, B:440:0x0a99, B:434:0x0a61, B:428:0x0a1b] A[DONT_GENERATE, DONT_INLINE]
      0x0c73: PHI (r19v48 int) = 
      (r19v27 int)
      (r19v28 int)
      (r19v29 int)
      (r19v30 int)
      (r19v31 int)
      (r19v33 int)
      (r19v34 int)
      (r19v35 int)
      (r19v39 int)
      (r19v45 int)
      (r19v49 int)
     binds: [B:497:0x0c5f, B:494:0x0c3e, B:491:0x0c1e, B:488:0x0bfe, B:485:0x0bdf, B:481:0x0bbd, B:474:0x0b93, B:460:0x0b55, B:440:0x0a99, B:434:0x0a61, B:428:0x0a1b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0c76  */
    /* JADX WARN: Removed duplicated region for block: B:517:0x0ccf  */
    /* JADX WARN: Removed duplicated region for block: B:518:0x0ce1  */
    /* JADX WARN: Type inference failed for: r1v135, types: [int] */
    /* JADX WARN: Type inference failed for: r31v0, types: [java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int a(java.lang.Object r31, byte[] r32, int r33, int r34, int r35, com.google.android.gms.internal.measurement.zzkh r36) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 3544
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznn.a(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.measurement.zzkh):int");
    }

    private static int zza(byte[] bArr, int i2, int i3, zzpj zzpjVar, Class<?> cls, zzkh zzkhVar) throws IOException {
        switch (zznm.f13233a[zzpjVar.ordinal()]) {
            case 1:
                int iQ = zzki.q(bArr, i2, zzkhVar);
                zzkhVar.zzc = Boolean.valueOf(zzkhVar.zzb != 0);
                return iQ;
            case 2:
                return zzki.k(bArr, i2, zzkhVar);
            case 3:
                zzkhVar.zzc = Double.valueOf(zzki.a(bArr, i2));
                return i2 + 8;
            case 4:
            case 5:
                zzkhVar.zzc = Integer.valueOf(zzki.o(bArr, i2));
                return i2 + 4;
            case 6:
            case 7:
                zzkhVar.zzc = Long.valueOf(zzki.r(bArr, i2));
                return i2 + 8;
            case 8:
                zzkhVar.zzc = Float.valueOf(zzki.m(bArr, i2));
                return i2 + 4;
            case 9:
            case 10:
            case 11:
                int iP = zzki.p(bArr, i2, zzkhVar);
                zzkhVar.zzc = Integer.valueOf(zzkhVar.zza);
                return iP;
            case 12:
            case 13:
                int iQ2 = zzki.q(bArr, i2, zzkhVar);
                zzkhVar.zzc = Long.valueOf(zzkhVar.zzb);
                return iQ2;
            case 14:
                return zzki.h(zznx.zza().zza((Class) cls), bArr, i2, i3, zzkhVar);
            case 15:
                int iP2 = zzki.p(bArr, i2, zzkhVar);
                zzkhVar.zzc = Integer.valueOf(zzkx.zza(zzkhVar.zza));
                return iP2;
            case 16:
                int iQ3 = zzki.q(bArr, i2, zzkhVar);
                zzkhVar.zzc = Long.valueOf(zzkx.zza(zzkhVar.zzb));
                return iQ3;
            case 17:
                return zzki.n(bArr, i2, zzkhVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private final int zzc(int i2) {
        return this.zzc[i2 + 1];
    }

    private final zzma zzd(int i2) {
        return (zzma) this.zzd[((i2 / 3) << 1) + 1];
    }

    private static void zzf(Object obj) {
        if (zzg(obj)) {
            return;
        }
        throw new IllegalArgumentException("Mutating immutable message: " + String.valueOf(obj));
    }

    private static boolean zzg(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzlw) {
            return ((zzlw) obj).t();
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final int zzb(T t2) {
        int i2;
        int iZza;
        int length = this.zzc.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iZzc = zzc(i4);
            int i5 = this.zzc[i4];
            long j2 = 1048575 & iZzc;
            int iHashCode = 37;
            switch ((iZzc & 267386880) >>> 20) {
                case 0:
                    i2 = i3 * 53;
                    iZza = zzlz.zza(Double.doubleToLongBits(zzpc.a(t2, j2)));
                    i3 = i2 + iZza;
                    break;
                case 1:
                    i2 = i3 * 53;
                    iZza = Float.floatToIntBits(zzpc.m(t2, j2));
                    i3 = i2 + iZza;
                    break;
                case 2:
                    i2 = i3 * 53;
                    iZza = zzlz.zza(zzpc.t(t2, j2));
                    i3 = i2 + iZza;
                    break;
                case 3:
                    i2 = i3 * 53;
                    iZza = zzlz.zza(zzpc.t(t2, j2));
                    i3 = i2 + iZza;
                    break;
                case 4:
                    i2 = i3 * 53;
                    iZza = zzpc.q(t2, j2);
                    i3 = i2 + iZza;
                    break;
                case 5:
                    i2 = i3 * 53;
                    iZza = zzlz.zza(zzpc.t(t2, j2));
                    i3 = i2 + iZza;
                    break;
                case 6:
                    i2 = i3 * 53;
                    iZza = zzpc.q(t2, j2);
                    i3 = i2 + iZza;
                    break;
                case 7:
                    i2 = i3 * 53;
                    iZza = zzlz.zza(zzpc.y(t2, j2));
                    i3 = i2 + iZza;
                    break;
                case 8:
                    i2 = i3 * 53;
                    iZza = ((String) zzpc.v(t2, j2)).hashCode();
                    i3 = i2 + iZza;
                    break;
                case 9:
                    Object objV = zzpc.v(t2, j2);
                    if (objV != null) {
                        iHashCode = objV.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i2 = i3 * 53;
                    iZza = zzpc.v(t2, j2).hashCode();
                    i3 = i2 + iZza;
                    break;
                case 11:
                    i2 = i3 * 53;
                    iZza = zzpc.q(t2, j2);
                    i3 = i2 + iZza;
                    break;
                case 12:
                    i2 = i3 * 53;
                    iZza = zzpc.q(t2, j2);
                    i3 = i2 + iZza;
                    break;
                case 13:
                    i2 = i3 * 53;
                    iZza = zzpc.q(t2, j2);
                    i3 = i2 + iZza;
                    break;
                case 14:
                    i2 = i3 * 53;
                    iZza = zzlz.zza(zzpc.t(t2, j2));
                    i3 = i2 + iZza;
                    break;
                case 15:
                    i2 = i3 * 53;
                    iZza = zzpc.q(t2, j2);
                    i3 = i2 + iZza;
                    break;
                case 16:
                    i2 = i3 * 53;
                    iZza = zzlz.zza(zzpc.t(t2, j2));
                    i3 = i2 + iZza;
                    break;
                case 17:
                    Object objV2 = zzpc.v(t2, j2);
                    if (objV2 != null) {
                        iHashCode = objV2.hashCode();
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
                    iZza = zzpc.v(t2, j2).hashCode();
                    i3 = i2 + iZza;
                    break;
                case 50:
                    i2 = i3 * 53;
                    iZza = zzpc.v(t2, j2).hashCode();
                    i3 = i2 + iZza;
                    break;
                case 51:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzlz.zza(Double.doubleToLongBits(zza(t2, j2)));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = Float.floatToIntBits(zzb(t2, j2));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzlz.zza(zzd(t2, j2));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzlz.zza(zzd(t2, j2));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzc(t2, j2);
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzlz.zza(zzd(t2, j2));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzc(t2, j2);
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzlz.zza(zze(t2, j2));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = ((String) zzpc.v(t2, j2)).hashCode();
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzpc.v(t2, j2).hashCode();
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzpc.v(t2, j2).hashCode();
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzc(t2, j2);
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzc(t2, j2);
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzc(t2, j2);
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzlz.zza(zzd(t2, j2));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzc(t2, j2);
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzlz.zza(zzd(t2, j2));
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzc((zznn<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZza = zzpc.v(t2, j2).hashCode();
                        i3 = i2 + iZza;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i3 * 53) + this.zzq.q(t2).hashCode();
        return this.zzh ? (iHashCode2 * 53) + this.zzr.b(t2).hashCode() : iHashCode2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006d  */
    @Override // com.google.android.gms.internal.measurement.zzob
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzc(T r8) {
        /*
            r7 = this;
            boolean r0 = zzg(r8)
            if (r0 != 0) goto L7
            return
        L7:
            boolean r0 = r8 instanceof com.google.android.gms.internal.measurement.zzlw
            r1 = 0
            if (r0 == 0) goto L1a
            r0 = r8
            com.google.android.gms.internal.measurement.zzlw r0 = (com.google.android.gms.internal.measurement.zzlw) r0
            r2 = 2147483647(0x7fffffff, float:NaN)
            r0.d(r2)
            r0.zza = r1
            r0.s()
        L1a:
            int[] r0 = r7.zzc
            int r0 = r0.length
        L1d:
            if (r1 >= r0) goto L83
            int r2 = r7.zzc(r1)
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r3 = r3 & r2
            long r3 = (long) r3
            r5 = 267386880(0xff00000, float:2.3665827E-29)
            r2 = r2 & r5
            int r2 = r2 >>> 20
            r5 = 9
            if (r2 == r5) goto L6d
            r5 = 60
            if (r2 == r5) goto L55
            r5 = 68
            if (r2 == r5) goto L55
            switch(r2) {
                case 17: goto L6d;
                case 18: goto L4f;
                case 19: goto L4f;
                case 20: goto L4f;
                case 21: goto L4f;
                case 22: goto L4f;
                case 23: goto L4f;
                case 24: goto L4f;
                case 25: goto L4f;
                case 26: goto L4f;
                case 27: goto L4f;
                case 28: goto L4f;
                case 29: goto L4f;
                case 30: goto L4f;
                case 31: goto L4f;
                case 32: goto L4f;
                case 33: goto L4f;
                case 34: goto L4f;
                case 35: goto L4f;
                case 36: goto L4f;
                case 37: goto L4f;
                case 38: goto L4f;
                case 39: goto L4f;
                case 40: goto L4f;
                case 41: goto L4f;
                case 42: goto L4f;
                case 43: goto L4f;
                case 44: goto L4f;
                case 45: goto L4f;
                case 46: goto L4f;
                case 47: goto L4f;
                case 48: goto L4f;
                case 49: goto L4f;
                case 50: goto L3d;
                default: goto L3c;
            }
        L3c:
            goto L80
        L3d:
            sun.misc.Unsafe r2 = com.google.android.gms.internal.measurement.zznn.zzb
            java.lang.Object r5 = r2.getObject(r8, r3)
            if (r5 == 0) goto L80
            com.google.android.gms.internal.measurement.zznc r6 = r7.zzs
            java.lang.Object r5 = r6.zzc(r5)
            r2.putObject(r8, r3, r5)
            goto L80
        L4f:
            com.google.android.gms.internal.measurement.zzmo r2 = r7.zzp
            r2.e(r8, r3)
            goto L80
        L55:
            int[] r2 = r7.zzc
            r2 = r2[r1]
            boolean r2 = r7.zzc(r8, r2, r1)
            if (r2 == 0) goto L80
            com.google.android.gms.internal.measurement.zzob r2 = r7.zze(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.measurement.zznn.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zzc(r3)
            goto L80
        L6d:
            boolean r2 = r7.zzc(r8, r1)
            if (r2 == 0) goto L80
            com.google.android.gms.internal.measurement.zzob r2 = r7.zze(r1)
            sun.misc.Unsafe r5 = com.google.android.gms.internal.measurement.zznn.zzb
            java.lang.Object r3 = r5.getObject(r8, r3)
            r2.zzc(r3)
        L80:
            int r1 = r1 + 3
            goto L1d
        L83:
            com.google.android.gms.internal.measurement.zzow<?, ?> r0 = r7.zzq
            r0.s(r8)
            boolean r0 = r7.zzh
            if (r0 == 0) goto L91
            com.google.android.gms.internal.measurement.zzll<?> r0 = r7.zzr
            r0.j(r8)
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznn.zzc(java.lang.Object):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00d2  */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.google.android.gms.internal.measurement.zzob] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.google.android.gms.internal.measurement.zzob] */
    @Override // com.google.android.gms.internal.measurement.zzob
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzd(T r18) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznn.zzd(java.lang.Object):boolean");
    }

    private static zzoz zze(Object obj) {
        zzlw zzlwVar = (zzlw) obj;
        zzoz zzozVar = zzlwVar.zzb;
        if (zzozVar != zzoz.zzc()) {
            return zzozVar;
        }
        zzoz zzozVarF = zzoz.f();
        zzlwVar.zzb = zzozVarF;
        return zzozVarF;
    }

    private static <T> boolean zze(T t2, long j2) {
        return ((Boolean) zzpc.v(t2, j2)).booleanValue();
    }

    private final boolean zzc(T t2, T t3, int i2) {
        return zzc((zznn<T>) t2, i2) == zzc((zznn<T>) t3, i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.google.android.gms.internal.measurement.zzob
    public final int zza(T t2) {
        int i2;
        int i3;
        int i4;
        int iZza;
        int iZza2;
        int iZzd;
        boolean z2;
        int iP;
        int iS;
        int iZzf;
        int iZzg;
        Unsafe unsafe = zzb;
        int i5 = 1048575;
        int i6 = 1048575;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (i8 < this.zzc.length) {
            int iZzc = zzc(i8);
            int i10 = (267386880 & iZzc) >>> 20;
            int[] iArr = this.zzc;
            int i11 = iArr[i8];
            int i12 = iArr[i8 + 2];
            int i13 = i12 & i5;
            if (i10 <= 17) {
                if (i13 != i6) {
                    i7 = i13 == i5 ? 0 : unsafe.getInt(t2, i13);
                    i6 = i13;
                }
                i2 = i6;
                i3 = i7;
                i4 = 1 << (i12 >>> 20);
            } else {
                i2 = i6;
                i3 = i7;
                i4 = 0;
            }
            long j2 = iZzc & i5;
            if (i10 >= zzlr.zza.zza()) {
                zzlr.zzb.zza();
            }
            switch (i10) {
                case 0:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza = zzld.zza(i11, 0.0d);
                        i9 += iZza;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza = zzld.zza(i11, 0.0f);
                        i9 += iZza;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza = zzld.zzb(i11, unsafe.getLong(t2, j2));
                        i9 += iZza;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza = zzld.zze(i11, unsafe.getLong(t2, j2));
                        i9 += iZza;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza = zzld.zzc(i11, unsafe.getInt(t2, j2));
                        i9 += iZza;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza = zzld.zza(i11, 0L);
                        i9 += iZza;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza = zzld.zzb(i11, 0);
                        i9 += iZza;
                        break;
                    }
                    break;
                case 7:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.zza(i11, true);
                        i9 += iZza2;
                    }
                    break;
                case 8:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        Object object = unsafe.getObject(t2, j2);
                        if (object instanceof zzkm) {
                            iZza2 = zzld.zza(i11, (zzkm) object);
                        } else {
                            iZza2 = zzld.zza(i11, (String) object);
                        }
                        i9 += iZza2;
                    }
                    break;
                case 9:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzod.a(i11, unsafe.getObject(t2, j2), zze(i8));
                        i9 += iZza2;
                    }
                    break;
                case 10:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.zza(i11, (zzkm) unsafe.getObject(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 11:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.zzf(i11, unsafe.getInt(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 12:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.zza(i11, unsafe.getInt(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 13:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZzd = zzld.zzd(i11, 0);
                        i9 += iZzd;
                    }
                    break;
                case 14:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.zzc(i11, 0L);
                        i9 += iZza2;
                    }
                    break;
                case 15:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.zze(i11, unsafe.getInt(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 16:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.zzd(i11, unsafe.getLong(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 17:
                    if (zza((zznn<T>) t2, i8, i2, i3, i4)) {
                        iZza2 = zzld.a(i11, (zznj) unsafe.getObject(t2, j2), zze(i8));
                        i9 += iZza2;
                    }
                    break;
                case 18:
                    iZza2 = zzod.r(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iZza2;
                    break;
                case 19:
                    z2 = false;
                    iP = zzod.p(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 20:
                    z2 = false;
                    iP = zzod.v(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 21:
                    z2 = false;
                    iP = zzod.D(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 22:
                    z2 = false;
                    iP = zzod.t(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 23:
                    z2 = false;
                    iP = zzod.r(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 24:
                    z2 = false;
                    iP = zzod.p(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 25:
                    z2 = false;
                    iP = zzod.d(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 26:
                    iZza2 = zzod.l(i11, (List) unsafe.getObject(t2, j2));
                    i9 += iZza2;
                    break;
                case 27:
                    iZza2 = zzod.m(i11, (List) unsafe.getObject(t2, j2), zze(i8));
                    i9 += iZza2;
                    break;
                case 28:
                    iZza2 = zzod.b(i11, (List) unsafe.getObject(t2, j2));
                    i9 += iZza2;
                    break;
                case 29:
                    iZza2 = zzod.B(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iZza2;
                    break;
                case 30:
                    z2 = false;
                    iP = zzod.n(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 31:
                    z2 = false;
                    iP = zzod.p(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 32:
                    z2 = false;
                    iP = zzod.r(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 33:
                    z2 = false;
                    iP = zzod.x(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 34:
                    z2 = false;
                    iP = zzod.z(i11, (List) unsafe.getObject(t2, j2), false);
                    i9 += iP;
                    break;
                case 35:
                    iS = zzod.s((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 36:
                    iS = zzod.q((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 37:
                    iS = zzod.w((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 38:
                    iS = zzod.E((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 39:
                    iS = zzod.u((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 40:
                    iS = zzod.s((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 41:
                    iS = zzod.q((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 42:
                    iS = zzod.e((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 43:
                    iS = zzod.C((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 44:
                    iS = zzod.o((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 45:
                    iS = zzod.q((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 46:
                    iS = zzod.s((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 47:
                    iS = zzod.y((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 48:
                    iS = zzod.A((List) unsafe.getObject(t2, j2));
                    if (iS > 0) {
                        iZzf = zzld.zzf(i11);
                        iZzg = zzld.zzg(iS);
                        iZzd = iZzf + iZzg + iS;
                        i9 += iZzd;
                    }
                    break;
                case 49:
                    iZza2 = zzod.c(i11, (List) unsafe.getObject(t2, j2), zze(i8));
                    i9 += iZza2;
                    break;
                case 50:
                    iZza2 = this.zzs.zza(i11, unsafe.getObject(t2, j2), zzf(i8));
                    i9 += iZza2;
                    break;
                case 51:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zza(i11, 0.0d);
                        i9 += iZza2;
                    }
                    break;
                case 52:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zza(i11, 0.0f);
                        i9 += iZza2;
                    }
                    break;
                case 53:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zzb(i11, zzd(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 54:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zze(i11, zzd(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 55:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zzc(i11, zzc(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 56:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zza(i11, 0L);
                        i9 += iZza2;
                    }
                    break;
                case 57:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZzd = zzld.zzb(i11, 0);
                        i9 += iZzd;
                    }
                    break;
                case 58:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zza(i11, true);
                        i9 += iZza2;
                    }
                    break;
                case 59:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        Object object2 = unsafe.getObject(t2, j2);
                        if (object2 instanceof zzkm) {
                            iZza2 = zzld.zza(i11, (zzkm) object2);
                        } else {
                            iZza2 = zzld.zza(i11, (String) object2);
                        }
                        i9 += iZza2;
                    }
                    break;
                case 60:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzod.a(i11, unsafe.getObject(t2, j2), zze(i8));
                        i9 += iZza2;
                    }
                    break;
                case 61:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zza(i11, (zzkm) unsafe.getObject(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 62:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zzf(i11, zzc(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 63:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zza(i11, zzc(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 64:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZzd = zzld.zzd(i11, 0);
                        i9 += iZzd;
                    }
                    break;
                case 65:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zzc(i11, 0L);
                        i9 += iZza2;
                    }
                    break;
                case 66:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zze(i11, zzc(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 67:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.zzd(i11, zzd(t2, j2));
                        i9 += iZza2;
                    }
                    break;
                case 68:
                    if (zzc((zznn<T>) t2, i11, i8)) {
                        iZza2 = zzld.a(i11, (zznj) unsafe.getObject(t2, j2), zze(i8));
                        i9 += iZza2;
                    }
                    break;
            }
            i8 += 3;
            i6 = i2;
            i7 = i3;
            i5 = 1048575;
        }
        int iZza3 = 0;
        zzow<?, ?> zzowVar = this.zzq;
        int iA = i9 + zzowVar.a(zzowVar.q(t2));
        if (!this.zzh) {
            return iA;
        }
        zzlm zzlmVarB = this.zzr.b(t2);
        for (int i14 = 0; i14 < zzlmVarB.f13226a.zza(); i14++) {
            Map.Entry entryZzb = zzlmVarB.f13226a.zzb(i14);
            iZza3 += zzlm.zza((zzlo<?>) entryZzb.getKey(), entryZzb.getValue());
        }
        for (Map.Entry entry : zzlmVarB.f13226a.zzb()) {
            iZza3 += zzlm.zza((zzlo<?>) entry.getKey(), entry.getValue());
        }
        return iA + iZza3;
    }

    private final boolean zzc(T t2, int i2) {
        int iZzb = zzb(i2);
        long j2 = iZzb & 1048575;
        if (j2 != 1048575) {
            return (zzpc.q(t2, j2) & (1 << (iZzb >>> 20))) != 0;
        }
        int iZzc = zzc(i2);
        long j3 = iZzc & 1048575;
        switch ((iZzc & 267386880) >>> 20) {
            case 0:
                return Double.doubleToRawLongBits(zzpc.a(t2, j3)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzpc.m(t2, j3)) != 0;
            case 2:
                return zzpc.t(t2, j3) != 0;
            case 3:
                return zzpc.t(t2, j3) != 0;
            case 4:
                return zzpc.q(t2, j3) != 0;
            case 5:
                return zzpc.t(t2, j3) != 0;
            case 6:
                return zzpc.q(t2, j3) != 0;
            case 7:
                return zzpc.y(t2, j3);
            case 8:
                Object objV = zzpc.v(t2, j3);
                if (objV instanceof String) {
                    return !((String) objV).isEmpty();
                }
                if (objV instanceof zzkm) {
                    return !zzkm.zza.equals(objV);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzpc.v(t2, j3) != null;
            case 10:
                return !zzkm.zza.equals(zzpc.v(t2, j3));
            case 11:
                return zzpc.q(t2, j3) != 0;
            case 12:
                return zzpc.q(t2, j3) != 0;
            case 13:
                return zzpc.q(t2, j3) != 0;
            case 14:
                return zzpc.t(t2, j3) != 0;
            case 15:
                return zzpc.q(t2, j3) != 0;
            case 16:
                return zzpc.t(t2, j3) != 0;
            case 17:
                return zzpc.v(t2, j3) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzc(T t2, int i2, int i3) {
        return zzpc.q(t2, (long) (zzb(i3) & 1048575)) == i2;
    }

    private final int zzb(int i2) {
        return this.zzc[i2 + 2];
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void zzb(T t2, T t3, int i2) {
        int i3 = this.zzc[i2];
        if (zzc((zznn<T>) t3, i3, i2)) {
            long jZzc = zzc(i2) & 1048575;
            Unsafe unsafe = zzb;
            Object object = unsafe.getObject(t3, jZzc);
            if (object != null) {
                zzob zzobVarZze = zze(i2);
                if (!zzc((zznn<T>) t2, i3, i2)) {
                    if (!zzg(object)) {
                        unsafe.putObject(t2, jZzc, object);
                    } else {
                        Object objZza = zzobVarZze.zza();
                        zzobVarZze.zza(objZza, object);
                        unsafe.putObject(t2, jZzc, objZza);
                    }
                    zzb((zznn<T>) t2, i3, i2);
                    return;
                }
                Object object2 = unsafe.getObject(t2, jZzc);
                if (!zzg(object2)) {
                    Object objZza2 = zzobVarZze.zza();
                    zzobVarZze.zza(objZza2, object2);
                    unsafe.putObject(t2, jZzc, objZza2);
                    object2 = objZza2;
                }
                zzobVarZze.zza(object2, object);
                return;
            }
            throw new IllegalStateException("Source subfield " + this.zzc[i2] + " is present but null: " + String.valueOf(t3));
        }
    }

    private final void zzb(T t2, int i2) {
        int iZzb = zzb(i2);
        long j2 = 1048575 & iZzb;
        if (j2 == 1048575) {
            return;
        }
        zzpc.g(t2, j2, (1 << (iZzb >>> 20)) | zzpc.q(t2, j2));
    }

    private final void zzb(T t2, int i2, int i3) {
        zzpc.g(t2, zzb(i3) & 1048575, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    @Override // com.google.android.gms.internal.measurement.zzob
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzb(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznn.zzb(java.lang.Object, java.lang.Object):boolean");
    }

    private final int zza(int i2) {
        if (i2 < this.zze || i2 > this.zzf) {
            return -1;
        }
        return zza(i2, 0);
    }

    private final int zza(int i2, int i3) {
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

    private final <UT, UB> UB zza(Object obj, int i2, UB ub, zzow<UT, UB> zzowVar, Object obj2) {
        zzma zzmaVarZzd;
        int i3 = this.zzc[i2];
        Object objV = zzpc.v(obj, zzc(i2) & 1048575);
        return (objV == null || (zzmaVarZzd = zzd(i2)) == null) ? ub : (UB) zza(i2, i3, this.zzs.zze(objV), zzmaVarZzd, ub, zzowVar, obj2);
    }

    private final <K, V, UT, UB> UB zza(int i2, int i3, Map<K, V> map, zzma zzmaVar, UB ub, zzow<UT, UB> zzowVar, Object obj) {
        zzna<?, ?> zznaVarZza = this.zzs.zza(zzf(i2));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!zzmaVar.zza(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = (UB) zzowVar.o(obj);
                }
                zzkr zzkrVarZzc = zzkm.zzc(zznb.a(zznaVarZza, next.getKey(), next.getValue()));
                try {
                    zznb.b(zzkrVarZzc.zzb(), zznaVarZza, next.getKey(), next.getValue());
                    zzowVar.f(ub, i3, zzkrVarZzc.zza());
                    it.remove();
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return ub;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Object zza(T t2, int i2) {
        zzob zzobVarZze = zze(i2);
        long jZzc = zzc(i2) & 1048575;
        if (!zzc((zznn<T>) t2, i2)) {
            return zzobVarZze.zza();
        }
        Object object = zzb.getObject(t2, jZzc);
        if (zzg(object)) {
            return object;
        }
        Object objZza = zzobVarZze.zza();
        if (object != null) {
            zzobVarZze.zza(objZza, object);
        }
        return objZza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Object zza(T t2, int i2, int i3) {
        zzob zzobVarZze = zze(i3);
        if (!zzc((zznn<T>) t2, i2, i3)) {
            return zzobVarZze.zza();
        }
        Object object = zzb.getObject(t2, zzc(i3) & 1048575);
        if (zzg(object)) {
            return object;
        }
        Object objZza = zzobVarZze.zza();
        if (object != null) {
            zzobVarZze.zza(objZza, object);
        }
        return objZza;
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final T zza() {
        return (T) this.zzo.zza(this.zzg);
    }

    private static Field zza(Class<?> cls, String str) {
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

    @Override // com.google.android.gms.internal.measurement.zzob
    public final void zza(T t2, T t3) {
        zzf(t2);
        t3.getClass();
        for (int i2 = 0; i2 < this.zzc.length; i2 += 3) {
            int iZzc = zzc(i2);
            long j2 = 1048575 & iZzc;
            int i3 = this.zzc[i2];
            switch ((iZzc & 267386880) >>> 20) {
                case 0:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.e(t2, j2, zzpc.a(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.f(t2, j2, zzpc.m(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.h(t2, j2, zzpc.t(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.h(t2, j2, zzpc.t(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.g(t2, j2, zzpc.q(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.h(t2, j2, zzpc.t(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.g(t2, j2, zzpc.q(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.r(t2, j2, zzpc.y(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.i(t2, j2, zzpc.v(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t2, t3, i2);
                    break;
                case 10:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.i(t2, j2, zzpc.v(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.g(t2, j2, zzpc.q(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.g(t2, j2, zzpc.q(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.g(t2, j2, zzpc.q(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.h(t2, j2, zzpc.t(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.g(t2, j2, zzpc.q(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzc((zznn<T>) t3, i2)) {
                        zzpc.h(t2, j2, zzpc.t(t3, j2));
                        zzb((zznn<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t2, t3, i2);
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
                    this.zzp.c(t2, t3, j2);
                    break;
                case 50:
                    zzod.i(this.zzs, t2, t3, j2);
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
                    if (zzc((zznn<T>) t3, i3, i2)) {
                        zzpc.i(t2, j2, zzpc.v(t3, j2));
                        zzb((zznn<T>) t2, i3, i2);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t2, t3, i2);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzc((zznn<T>) t3, i3, i2)) {
                        zzpc.i(t2, j2, zzpc.v(t3, j2));
                        zzb((zznn<T>) t2, i3, i2);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t2, t3, i2);
                    break;
            }
        }
        zzod.j(this.zzq, t2, t3);
        if (this.zzh) {
            zzod.h(this.zzr, t2, t3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:169:0x062e A[Catch: all -> 0x00ca, TryCatch #6 {all -> 0x00ca, blocks: (B:48:0x00c4, B:53:0x00d2, B:167:0x0629, B:169:0x062e, B:170:0x0633, B:65:0x00fe, B:67:0x0113, B:68:0x0124, B:69:0x0135, B:70:0x0146, B:71:0x0157, B:73:0x0161, B:76:0x0168, B:77:0x016d, B:78:0x017a, B:79:0x018b, B:80:0x0199, B:81:0x01ab, B:82:0x01b3, B:83:0x01c5, B:84:0x01d7, B:85:0x01e9, B:86:0x01fb, B:87:0x020d, B:88:0x021f, B:89:0x0231, B:90:0x0243, B:92:0x0253, B:96:0x0274, B:93:0x025d, B:95:0x0265, B:97:0x0285, B:98:0x0297, B:99:0x02a5, B:100:0x02b3, B:101:0x02c1), top: B:191:0x00c4 }] */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0662 A[LOOP:2: B:182:0x065e->B:184:0x0662, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0676  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0639 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r19v0, types: [com.google.android.gms.internal.measurement.zzny] */
    @Override // com.google.android.gms.internal.measurement.zzob
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r18, com.google.android.gms.internal.measurement.zzny r19, com.google.android.gms.internal.measurement.zzlj r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1800
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzny, com.google.android.gms.internal.measurement.zzlj):void");
    }

    @Override // com.google.android.gms.internal.measurement.zzob
    public final void zza(T t2, byte[] bArr, int i2, int i3, zzkh zzkhVar) throws IOException {
        a(t2, bArr, i2, i3, 0, zzkhVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void zza(T t2, T t3, int i2) {
        if (zzc((zznn<T>) t3, i2)) {
            long jZzc = zzc(i2) & 1048575;
            Unsafe unsafe = zzb;
            Object object = unsafe.getObject(t3, jZzc);
            if (object != null) {
                zzob zzobVarZze = zze(i2);
                if (!zzc((zznn<T>) t2, i2)) {
                    if (!zzg(object)) {
                        unsafe.putObject(t2, jZzc, object);
                    } else {
                        Object objZza = zzobVarZze.zza();
                        zzobVarZze.zza(objZza, object);
                        unsafe.putObject(t2, jZzc, objZza);
                    }
                    zzb((zznn<T>) t2, i2);
                    return;
                }
                Object object2 = unsafe.getObject(t2, jZzc);
                if (!zzg(object2)) {
                    Object objZza2 = zzobVarZze.zza();
                    zzobVarZze.zza(objZza2, object2);
                    unsafe.putObject(t2, jZzc, objZza2);
                    object2 = objZza2;
                }
                zzobVarZze.zza(object2, object);
                return;
            }
            throw new IllegalStateException("Source subfield " + this.zzc[i2] + " is present but null: " + String.valueOf(t3));
        }
    }

    private final void zza(Object obj, int i2, zzny zznyVar) throws IOException {
        if (zzg(i2)) {
            zzpc.i(obj, i2 & 1048575, zznyVar.zzr());
        } else if (this.zzi) {
            zzpc.i(obj, i2 & 1048575, zznyVar.zzq());
        } else {
            zzpc.i(obj, i2 & 1048575, zznyVar.zzp());
        }
    }

    private final void zza(T t2, int i2, Object obj) {
        zzb.putObject(t2, zzc(i2) & 1048575, obj);
        zzb((zznn<T>) t2, i2);
    }

    private final void zza(T t2, int i2, int i3, Object obj) {
        zzb.putObject(t2, zzc(i3) & 1048575, obj);
        zzb((zznn<T>) t2, i2, i3);
    }

    private final <K, V> void zza(zzpw zzpwVar, int i2, Object obj, int i3) throws IOException {
        if (obj != null) {
            zzpwVar.zza(i2, this.zzs.zza(zzf(i3)), this.zzs.zzd(obj));
        }
    }

    private static void zza(int i2, Object obj, zzpw zzpwVar) throws IOException {
        if (obj instanceof String) {
            zzpwVar.zza(i2, (String) obj);
        } else {
            zzpwVar.zza(i2, (zzkm) obj);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:176:0x054b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0037  */
    @Override // com.google.android.gms.internal.measurement.zzob
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r24, com.google.android.gms.internal.measurement.zzpw r25) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 3272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zznn.zza(java.lang.Object, com.google.android.gms.internal.measurement.zzpw):void");
    }

    private static <UT, UB> void zza(zzow<UT, UB> zzowVar, T t2, zzpw zzpwVar) throws IOException {
        zzowVar.m(zzowVar.q(t2), zzpwVar);
    }

    private final boolean zza(T t2, int i2, int i3, int i4, int i5) {
        if (i3 == 1048575) {
            return zzc((zznn<T>) t2, i2);
        }
        return (i4 & i5) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i2, zzob zzobVar) {
        return zzobVar.zzd(zzpc.v(obj, i2 & 1048575));
    }
}
