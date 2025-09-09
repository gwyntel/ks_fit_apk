package org.mozilla.javascript.v8dtoa;

/* loaded from: classes5.dex */
public class FastDtoa {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kFastDtoaMaximalLength = 17;
    static final int kTen4 = 10000;
    static final int kTen5 = 100000;
    static final int kTen6 = 1000000;
    static final int kTen7 = 10000000;
    static final int kTen8 = 100000000;
    static final int kTen9 = 1000000000;
    static final int maximal_target_exponent = -32;
    static final int minimal_target_exponent = -60;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0011  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static long biggestPowerTen(int r5, int r6) {
        /*
            r0 = 1
            r1 = 0
            switch(r6) {
                case 0: goto L48;
                case 1: goto L45;
                case 2: goto L45;
                case 3: goto L45;
                case 4: goto L40;
                case 5: goto L40;
                case 6: goto L40;
                case 7: goto L3a;
                case 8: goto L3a;
                case 9: goto L3a;
                case 10: goto L34;
                case 11: goto L34;
                case 12: goto L34;
                case 13: goto L34;
                case 14: goto L2e;
                case 15: goto L2e;
                case 16: goto L2e;
                case 17: goto L27;
                case 18: goto L27;
                case 19: goto L27;
                case 20: goto L20;
                case 21: goto L20;
                case 22: goto L20;
                case 23: goto L20;
                case 24: goto L19;
                case 25: goto L19;
                case 26: goto L19;
                case 27: goto L11;
                case 28: goto L11;
                case 29: goto L11;
                case 30: goto L7;
                case 31: goto L7;
                case 32: goto L7;
                default: goto L5;
            }
        L5:
            r0 = r1
            goto L4c
        L7:
            r6 = 1000000000(0x3b9aca00, float:0.0047237873)
            if (r6 > r5) goto L11
            r0 = 9
        Le:
            r1 = r0
            r0 = r6
            goto L4c
        L11:
            r6 = 100000000(0x5f5e100, float:2.3122341E-35)
            if (r6 > r5) goto L19
            r0 = 8
            goto Le
        L19:
            r6 = 10000000(0x989680, float:1.4012985E-38)
            if (r6 > r5) goto L20
            r0 = 7
            goto Le
        L20:
            r6 = 1000000(0xf4240, float:1.401298E-39)
            if (r6 > r5) goto L27
            r0 = 6
            goto Le
        L27:
            r6 = 100000(0x186a0, float:1.4013E-40)
            if (r6 > r5) goto L2e
            r0 = 5
            goto Le
        L2e:
            r6 = 10000(0x2710, float:1.4013E-41)
            if (r6 > r5) goto L34
            r0 = 4
            goto Le
        L34:
            r6 = 1000(0x3e8, float:1.401E-42)
            if (r6 > r5) goto L3a
            r0 = 3
            goto Le
        L3a:
            r6 = 100
            if (r6 > r5) goto L40
            r0 = 2
            goto Le
        L40:
            r6 = 10
            if (r6 > r5) goto L45
            goto Le
        L45:
            if (r0 > r5) goto L48
            goto L4c
        L48:
            r0 = -1
            r4 = r1
            r1 = r0
            r0 = r4
        L4c:
            long r5 = (long) r0
            r0 = 32
            long r5 = r5 << r0
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = (long) r1
            long r0 = r0 & r2
            long r5 = r5 | r0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.v8dtoa.FastDtoa.biggestPowerTen(int, int):long");
    }

    static boolean digitGen(DiyFp diyFp, DiyFp diyFp2, DiyFp diyFp3, FastDtoaBuilder fastDtoaBuilder, int i2) {
        long j2 = 1;
        DiyFp diyFp4 = new DiyFp(diyFp.f() - 1, diyFp.e());
        DiyFp diyFp5 = new DiyFp(diyFp3.f() + 1, diyFp3.e());
        DiyFp diyFpMinus = DiyFp.minus(diyFp5, diyFp4);
        DiyFp diyFp6 = new DiyFp(1 << (-diyFp2.e()), diyFp2.e());
        int iF = (int) ((diyFp5.f() >>> (-diyFp6.e())) & 4294967295L);
        long jF = diyFp5.f() & (diyFp6.f() - 1);
        long jBiggestPowerTen = biggestPowerTen(iF, 64 - (-diyFp6.e()));
        int i3 = (int) ((jBiggestPowerTen >>> 32) & 4294967295L);
        int i4 = ((int) (jBiggestPowerTen & 4294967295L)) + 1;
        while (i4 > 0) {
            fastDtoaBuilder.append((char) ((iF / i3) + 48));
            iF %= i3;
            i4--;
            long j3 = (iF << (-diyFp6.e())) + jF;
            if (j3 < diyFpMinus.f()) {
                fastDtoaBuilder.point = (fastDtoaBuilder.end - i2) + i4;
                return roundWeed(fastDtoaBuilder, DiyFp.minus(diyFp5, diyFp2).f(), diyFpMinus.f(), j3, i3 << (-diyFp6.e()), 1L);
            }
            i3 /= 10;
        }
        do {
            long j4 = jF * 5;
            j2 *= 5;
            diyFpMinus.setF(diyFpMinus.f() * 5);
            diyFpMinus.setE(diyFpMinus.e() + 1);
            diyFp6.setF(diyFp6.f() >>> 1);
            diyFp6.setE(diyFp6.e() + 1);
            fastDtoaBuilder.append((char) (((int) ((j4 >>> (-diyFp6.e())) & 4294967295L)) + 48));
            jF = j4 & (diyFp6.f() - 1);
            i4--;
        } while (jF >= diyFpMinus.f());
        fastDtoaBuilder.point = (fastDtoaBuilder.end - i2) + i4;
        return roundWeed(fastDtoaBuilder, DiyFp.minus(diyFp5, diyFp2).f() * j2, diyFpMinus.f(), jF, diyFp6.f(), j2);
    }

    public static boolean dtoa(double d2, FastDtoaBuilder fastDtoaBuilder) {
        return grisu3(d2, fastDtoaBuilder);
    }

    static boolean grisu3(double d2, FastDtoaBuilder fastDtoaBuilder) {
        long jDoubleToLongBits = Double.doubleToLongBits(d2);
        DiyFp diyFpAsNormalizedDiyFp = DoubleHelper.asNormalizedDiyFp(jDoubleToLongBits);
        DiyFp diyFp = new DiyFp();
        DiyFp diyFp2 = new DiyFp();
        DoubleHelper.normalizedBoundaries(jDoubleToLongBits, diyFp, diyFp2);
        DiyFp diyFp3 = new DiyFp();
        int cachedPower = CachedPowers.getCachedPower(diyFpAsNormalizedDiyFp.e() + 64, -60, -32, diyFp3);
        return digitGen(DiyFp.times(diyFp, diyFp3), DiyFp.times(diyFpAsNormalizedDiyFp, diyFp3), DiyFp.times(diyFp2, diyFp3), fastDtoaBuilder, cachedPower);
    }

    public static String numberToString(double d2) {
        FastDtoaBuilder fastDtoaBuilder = new FastDtoaBuilder();
        if (numberToString(d2, fastDtoaBuilder)) {
            return fastDtoaBuilder.format();
        }
        return null;
    }

    static boolean roundWeed(FastDtoaBuilder fastDtoaBuilder, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 - j6;
        long j8 = j2 + j6;
        long j9 = j4;
        while (j9 < j7 && j3 - j9 >= j5) {
            long j10 = j9 + j5;
            if (j10 >= j7 && j7 - j9 < j10 - j7) {
                break;
            }
            fastDtoaBuilder.decreaseLast();
            j9 = j10;
        }
        if (j9 < j8 && j3 - j9 >= j5) {
            long j11 = j9 + j5;
            if (j11 < j8 || j8 - j9 > j11 - j8) {
                return false;
            }
        }
        return 2 * j6 <= j9 && j9 <= j3 - (4 * j6);
    }

    private static boolean uint64_lte(long j2, long j3) {
        if (j2 == j3) {
            return true;
        }
        return (((j2 > 0L ? 1 : (j2 == 0L ? 0 : -1)) < 0) ^ ((j2 > j3 ? 1 : (j2 == j3 ? 0 : -1)) < 0)) ^ ((j3 > 0L ? 1 : (j3 == 0L ? 0 : -1)) < 0);
    }

    public static boolean numberToString(double d2, FastDtoaBuilder fastDtoaBuilder) {
        fastDtoaBuilder.reset();
        if (d2 < 0.0d) {
            fastDtoaBuilder.append('-');
            d2 = -d2;
        }
        return dtoa(d2, fastDtoaBuilder);
    }
}
