package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Map;
import javax.annotation.CheckForNull;
import kotlin.UShort;

/* loaded from: classes3.dex */
final class zzjh<K, V> extends zziv<K, V> {
    static final zziv<Object, Object> zza = new zzjh(null, new Object[0], 0);

    @CheckForNull
    private final transient Object zzb;
    private final transient Object[] zzc;
    private final transient int zzd;

    private zzjh(@CheckForNull Object obj, Object[] objArr, int i2) {
        this.zzb = obj;
        this.zzc = objArr;
        this.zzd = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0009 A[EDGE_INSN: B:43:0x0009->B:4:0x0009 BREAK  A[LOOP:0: B:15:0x0037->B:21:0x004d], EDGE_INSN: B:45:0x0009->B:4:0x0009 BREAK  A[LOOP:1: B:25:0x0062->B:31:0x0079], EDGE_INSN: B:47:0x0009->B:4:0x0009 BREAK  A[LOOP:2: B:33:0x0088->B:42:0x00a0]] */
    @Override // com.google.android.gms.internal.measurement.zziv, java.util.Map
    @javax.annotation.CheckForNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final V get(@javax.annotation.CheckForNull java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = r8.zzb
            java.lang.Object[] r1 = r8.zzc
            int r2 = r8.zzd
            r3 = 0
            if (r9 != 0) goto Lc
        L9:
            r9 = r3
            goto L9c
        Lc:
            r4 = 1
            if (r2 != r4) goto L22
            r0 = 0
            r0 = r1[r0]
            r0.getClass()
            boolean r9 = r0.equals(r9)
            if (r9 == 0) goto L9
            r9 = r1[r4]
            r9.getClass()
            goto L9c
        L22:
            if (r0 != 0) goto L25
            goto L9
        L25:
            boolean r2 = r0 instanceof byte[]
            if (r2 == 0) goto L50
            r2 = r0
            byte[] r2 = (byte[]) r2
            int r0 = r2.length
            int r5 = r0 + (-1)
            int r0 = r9.hashCode()
            int r0 = com.google.android.gms.internal.measurement.zzin.a(r0)
        L37:
            r0 = r0 & r5
            r6 = r2[r0]
            r7 = 255(0xff, float:3.57E-43)
            r6 = r6 & r7
            if (r6 != r7) goto L40
            goto L9
        L40:
            r7 = r1[r6]
            boolean r7 = r9.equals(r7)
            if (r7 == 0) goto L4d
            r9 = r6 ^ 1
            r9 = r1[r9]
            goto L9c
        L4d:
            int r0 = r0 + 1
            goto L37
        L50:
            boolean r2 = r0 instanceof short[]
            if (r2 == 0) goto L7c
            r2 = r0
            short[] r2 = (short[]) r2
            int r0 = r2.length
            int r5 = r0 + (-1)
            int r0 = r9.hashCode()
            int r0 = com.google.android.gms.internal.measurement.zzin.a(r0)
        L62:
            r0 = r0 & r5
            short r6 = r2[r0]
            r7 = 65535(0xffff, float:9.1834E-41)
            r6 = r6 & r7
            if (r6 != r7) goto L6c
            goto L9
        L6c:
            r7 = r1[r6]
            boolean r7 = r9.equals(r7)
            if (r7 == 0) goto L79
            r9 = r6 ^ 1
            r9 = r1[r9]
            goto L9c
        L79:
            int r0 = r0 + 1
            goto L62
        L7c:
            int[] r0 = (int[]) r0
            int r2 = r0.length
            int r2 = r2 - r4
            int r5 = r9.hashCode()
            int r5 = com.google.android.gms.internal.measurement.zzin.a(r5)
        L88:
            r5 = r5 & r2
            r6 = r0[r5]
            r7 = -1
            if (r6 != r7) goto L90
            goto L9
        L90:
            r7 = r1[r6]
            boolean r7 = r9.equals(r7)
            if (r7 == 0) goto La0
            r9 = r6 ^ 1
            r9 = r1[r9]
        L9c:
            if (r9 != 0) goto L9f
            return r3
        L9f:
            return r9
        La0:
            int r5 = r5 + 1
            goto L88
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzjh.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.measurement.zziv
    final zziq<V> zza() {
        return new zzjl(this.zzc, 1, this.zzd);
    }

    @Override // com.google.android.gms.internal.measurement.zziv
    final zzjc<Map.Entry<K, V>> zzb() {
        return new zzjk(this, this.zzc, 0, this.zzd);
    }

    @Override // com.google.android.gms.internal.measurement.zziv
    final zzjc<K> zzc() {
        return new zzjm(this, new zzjl(this.zzc, 0, this.zzd));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r5v2, types: [int[]] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.Object[]] */
    static <K, V> zzjh<K, V> zza(int i2, Object[] objArr, zziy<K, V> zziyVar) {
        short[] sArr;
        char c2;
        char c3;
        int i3 = i2;
        Object[] objArrCopyOf = objArr;
        if (i3 == 0) {
            return (zzjh) zza;
        }
        Object zzixVar = null;
        int i4 = 1;
        if (i3 == 1) {
            Object obj = objArrCopyOf[0];
            obj.getClass();
            Object obj2 = objArrCopyOf[1];
            obj2.getClass();
            zzic.b(obj, obj2);
            return new zzjh<>(null, objArrCopyOf, 1);
        }
        zzhn.zzb(i3, objArrCopyOf.length >> 1);
        int iZza = zzjc.zza(i2);
        if (i3 == 1) {
            Object obj3 = objArrCopyOf[0];
            obj3.getClass();
            Object obj4 = objArrCopyOf[1];
            obj4.getClass();
            zzic.b(obj3, obj4);
            c2 = 1;
            c3 = 2;
        } else {
            int i5 = iZza - 1;
            char c4 = 65535;
            if (iZza <= 128) {
                byte[] bArr = new byte[iZza];
                Arrays.fill(bArr, (byte) -1);
                int i6 = 0;
                int i7 = 0;
                while (i6 < i3) {
                    int i8 = i6 * 2;
                    int i9 = i7 * 2;
                    Object obj5 = objArrCopyOf[i8];
                    obj5.getClass();
                    Object obj6 = objArrCopyOf[i8 ^ i4];
                    obj6.getClass();
                    zzic.b(obj5, obj6);
                    int iA = zzin.a(obj5.hashCode());
                    while (true) {
                        int i10 = iA & i5;
                        int i11 = bArr[i10] & 255;
                        if (i11 == 255) {
                            bArr[i10] = (byte) i9;
                            if (i7 < i6) {
                                objArrCopyOf[i9] = obj5;
                                objArrCopyOf[i9 ^ 1] = obj6;
                            }
                            i7++;
                        } else {
                            if (obj5.equals(objArrCopyOf[i11])) {
                                int i12 = i11 ^ 1;
                                Object obj7 = objArrCopyOf[i12];
                                obj7.getClass();
                                zzixVar = new zzix(obj5, obj6, obj7);
                                objArrCopyOf[i12] = obj6;
                                break;
                            }
                            iA = i10 + 1;
                        }
                    }
                    i6++;
                    i4 = 1;
                }
                if (i7 == i3) {
                    zzixVar = bArr;
                    c3 = 2;
                    c2 = 1;
                } else {
                    zzixVar = new Object[]{bArr, Integer.valueOf(i7), zzixVar};
                    c3 = 2;
                    c2 = 1;
                }
            } else {
                if (iZza <= 32768) {
                    sArr = new short[iZza];
                    Arrays.fill(sArr, (short) -1);
                    int i13 = 0;
                    for (int i14 = 0; i14 < i3; i14++) {
                        int i15 = i14 * 2;
                        int i16 = i13 * 2;
                        Object obj8 = objArrCopyOf[i15];
                        obj8.getClass();
                        Object obj9 = objArrCopyOf[i15 ^ 1];
                        obj9.getClass();
                        zzic.b(obj8, obj9);
                        int iA2 = zzin.a(obj8.hashCode());
                        while (true) {
                            int i17 = iA2 & i5;
                            int i18 = sArr[i17] & UShort.MAX_VALUE;
                            if (i18 == 65535) {
                                sArr[i17] = (short) i16;
                                if (i13 < i14) {
                                    objArrCopyOf[i16] = obj8;
                                    objArrCopyOf[i16 ^ 1] = obj9;
                                }
                                i13++;
                            } else {
                                if (obj8.equals(objArrCopyOf[i18])) {
                                    int i19 = i18 ^ 1;
                                    Object obj10 = objArrCopyOf[i19];
                                    obj10.getClass();
                                    zzixVar = new zzix(obj8, obj9, obj10);
                                    objArrCopyOf[i19] = obj9;
                                    break;
                                }
                                iA2 = i17 + 1;
                            }
                        }
                    }
                    if (i13 != i3) {
                        c3 = 2;
                        zzixVar = new Object[]{sArr, Integer.valueOf(i13), zzixVar};
                        c2 = 1;
                    }
                } else {
                    sArr = new int[iZza];
                    Arrays.fill((int[]) sArr, -1);
                    int i20 = 0;
                    int i21 = 0;
                    while (i20 < i3) {
                        int i22 = i20 * 2;
                        int i23 = i21 * 2;
                        Object obj11 = objArrCopyOf[i22];
                        obj11.getClass();
                        Object obj12 = objArrCopyOf[i22 ^ 1];
                        obj12.getClass();
                        zzic.b(obj11, obj12);
                        int iA3 = zzin.a(obj11.hashCode());
                        while (true) {
                            int i24 = iA3 & i5;
                            ?? r15 = sArr[i24];
                            if (r15 == c4) {
                                sArr[i24] = i23;
                                if (i21 < i20) {
                                    objArrCopyOf[i23] = obj11;
                                    objArrCopyOf[i23 ^ 1] = obj12;
                                }
                                i21++;
                            } else {
                                if (obj11.equals(objArrCopyOf[r15])) {
                                    int i25 = r15 ^ 1;
                                    Object obj13 = objArrCopyOf[i25];
                                    obj13.getClass();
                                    zzixVar = new zzix(obj11, obj12, obj13);
                                    objArrCopyOf[i25] = obj12;
                                    break;
                                }
                                iA3 = i24 + 1;
                                c4 = 65535;
                            }
                        }
                        i20++;
                        c4 = 65535;
                    }
                    if (i21 != i3) {
                        Integer numValueOf = Integer.valueOf(i21);
                        c2 = 1;
                        c3 = 2;
                        zzixVar = new Object[]{sArr, numValueOf, zzixVar};
                    }
                }
                zzixVar = sArr;
                c3 = 2;
                c2 = 1;
            }
        }
        boolean z2 = zzixVar instanceof Object[];
        Object obj14 = zzixVar;
        if (z2) {
            Object[] objArr2 = (Object[]) zzixVar;
            zzix zzixVar2 = (zzix) objArr2[c3];
            if (zziyVar == null) {
                throw zzixVar2.a();
            }
            zziyVar.f13218a = zzixVar2;
            Object obj15 = objArr2[0];
            int iIntValue = ((Integer) objArr2[c2]).intValue();
            objArrCopyOf = Arrays.copyOf(objArrCopyOf, iIntValue << 1);
            obj14 = obj15;
            i3 = iIntValue;
        }
        return new zzjh<>(obj14, objArrCopyOf, i3);
    }
}
