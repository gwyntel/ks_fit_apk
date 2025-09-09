package com.alibaba.fastjson.util;

import androidx.media3.common.C;
import androidx.media3.exoplayer.MediaPeriodQueue;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import java.lang.reflect.Array;
import java.math.BigInteger;

/* loaded from: classes2.dex */
public final class RyuDouble {
    private static final int[][] POW5_INV_SPLIT;
    private static final int[][] POW5_SPLIT;

    static {
        Class cls = Integer.TYPE;
        POW5_SPLIT = (int[][]) Array.newInstance((Class<?>) cls, 326, 4);
        POW5_INV_SPLIT = (int[][]) Array.newInstance((Class<?>) cls, 291, 4);
        BigInteger bigInteger = BigInteger.ONE;
        BigInteger bigIntegerSubtract = bigInteger.shiftLeft(31).subtract(bigInteger);
        BigInteger bigIntegerSubtract2 = bigInteger.shiftLeft(31).subtract(bigInteger);
        int i2 = 0;
        while (i2 < 326) {
            BigInteger bigIntegerPow = BigInteger.valueOf(5L).pow(i2);
            int iBitLength = bigIntegerPow.bitLength();
            int i3 = i2 == 0 ? 1 : (int) (((i2 * 23219280) + 9999999) / 10000000);
            if (i3 != iBitLength) {
                throw new IllegalStateException(iBitLength + " != " + i3);
            }
            if (i2 < POW5_SPLIT.length) {
                for (int i4 = 0; i4 < 4; i4++) {
                    POW5_SPLIT[i2][i4] = bigIntegerPow.shiftRight((iBitLength - 121) + ((3 - i4) * 31)).and(bigIntegerSubtract).intValue();
                }
            }
            if (i2 < POW5_INV_SPLIT.length) {
                BigInteger bigInteger2 = BigInteger.ONE;
                BigInteger bigIntegerAdd = bigInteger2.shiftLeft(iBitLength + 121).divide(bigIntegerPow).add(bigInteger2);
                for (int i5 = 0; i5 < 4; i5++) {
                    if (i5 == 0) {
                        POW5_INV_SPLIT[i2][i5] = bigIntegerAdd.shiftRight((3 - i5) * 31).intValue();
                    } else {
                        POW5_INV_SPLIT[i2][i5] = bigIntegerAdd.shiftRight((3 - i5) * 31).and(bigIntegerSubtract2).intValue();
                    }
                }
            }
            i2++;
        }
    }

    public static String toString(double d2) {
        char[] cArr = new char[24];
        return new String(cArr, 0, toString(d2, cArr, 0));
    }

    public static int toString(double d2, char[] cArr, int i2) {
        int i3;
        boolean z2;
        boolean z3;
        long j2;
        long j3;
        int i4;
        long j4;
        boolean z4;
        boolean z5;
        long j5;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z6;
        int i10;
        int i11;
        int i12;
        int i13;
        if (Double.isNaN(d2)) {
            cArr[i2] = 'N';
            cArr[i2 + 1] = 'a';
            i13 = i2 + 3;
            cArr[i2 + 2] = 'N';
        } else {
            if (d2 == Double.POSITIVE_INFINITY) {
                cArr[i2] = 'I';
                cArr[i2 + 1] = 'n';
                cArr[i2 + 2] = 'f';
                cArr[i2 + 3] = 'i';
                cArr[i2 + 4] = 'n';
                cArr[i2 + 5] = 'i';
                cArr[i2 + 6] = 't';
                cArr[i2 + 7] = 'y';
                return (i2 + 8) - i2;
            }
            if (d2 == Double.NEGATIVE_INFINITY) {
                cArr[i2] = '-';
                cArr[i2 + 1] = 'I';
                cArr[i2 + 2] = 'n';
                cArr[i2 + 3] = 'f';
                cArr[i2 + 4] = 'i';
                cArr[i2 + 5] = 'n';
                cArr[i2 + 6] = 'i';
                cArr[i2 + 7] = 't';
                i13 = i2 + 9;
                cArr[i2 + 8] = 'y';
            } else {
                long jDoubleToLongBits = Double.doubleToLongBits(d2);
                if (jDoubleToLongBits != 0) {
                    if (jDoubleToLongBits == Long.MIN_VALUE) {
                        cArr[i2] = '-';
                        cArr[i2 + 1] = '0';
                        cArr[i2 + 2] = '.';
                        i9 = i2 + 4;
                        cArr[i2 + 3] = '0';
                    } else {
                        int i14 = (int) ((jDoubleToLongBits >>> 52) & 2047);
                        long j6 = jDoubleToLongBits & 4503599627370495L;
                        if (i14 == 0) {
                            i3 = -1074;
                        } else {
                            i3 = i14 - 1075;
                            j6 |= 4503599627370496L;
                        }
                        boolean z7 = jDoubleToLongBits < 0;
                        boolean z8 = (j6 & 1) == 0;
                        long j7 = 4 * j6;
                        long j8 = j7 + 2;
                        int i15 = (j6 != 4503599627370496L || i14 <= 1) ? 1 : 0;
                        long j9 = (j7 - 1) - i15;
                        int i16 = i3 - 2;
                        int i17 = 3;
                        if (i16 >= 0) {
                            int iMax = Math.max(0, ((int) ((i16 * 3010299) / 10000000)) - 1);
                            int i18 = (((-i16) + iMax) + ((iMax == 0 ? 1 : (int) (((iMax * 23219280) + 9999999) / 10000000)) + 121)) - 114;
                            if (i18 >= 0) {
                                int[] iArr = POW5_INV_SPLIT[iMax];
                                long j10 = j7 >>> 31;
                                long j11 = j7 & 2147483647L;
                                int i19 = iArr[0];
                                int i20 = iArr[1];
                                z2 = z7;
                                int i21 = iArr[2];
                                z3 = z8;
                                int i22 = iArr[3];
                                long j12 = ((((((((((((j11 * i22) >>> 31) + (i21 * j11)) + (j10 * i22)) >>> 31) + (i20 * j11)) + (i21 * j10)) >>> 31) + (i19 * j11)) + (i20 * j10)) >>> 21) + ((i19 * j10) << 10)) >>> i18;
                                long j13 = j8 >>> 31;
                                long j14 = j8 & 2147483647L;
                                long j15 = ((((((((((((j14 * i22) >>> 31) + (i21 * j14)) + (j13 * i22)) >>> 31) + (i20 * j14)) + (i21 * j13)) >>> 31) + (i19 * j14)) + (i20 * j13)) >>> 21) + ((i19 * j13) << 10)) >>> i18;
                                long j16 = j9 >>> 31;
                                long j17 = j9 & 2147483647L;
                                j4 = j15;
                                j3 = ((((((((((((j17 * i22) >>> 31) + (i21 * j17)) + (j16 * i22)) >>> 31) + (i20 * j17)) + (i21 * j16)) >>> 31) + (i19 * j17)) + (i20 * j16)) >>> 21) + ((i19 * j16) << 10)) >>> i18;
                                if (iMax <= 21) {
                                    long j18 = j7 % 5;
                                    if (j18 == 0) {
                                        if (j18 != 0) {
                                            i12 = 0;
                                        } else if (j7 % 25 != 0) {
                                            i12 = 1;
                                        } else if (j7 % 125 != 0) {
                                            i12 = 2;
                                        } else if (j7 % 625 != 0) {
                                            i12 = 3;
                                        } else {
                                            long j19 = j7 / 625;
                                            i12 = 4;
                                            for (long j20 = 0; j19 > j20 && j19 % 5 == j20; j20 = 0) {
                                                j19 /= 5;
                                                i12++;
                                            }
                                        }
                                        z5 = i12 >= iMax;
                                        z6 = false;
                                        j2 = j12;
                                        z4 = z6;
                                        i4 = iMax;
                                    } else {
                                        if (z3) {
                                            if (j9 % 5 != 0) {
                                                i11 = 0;
                                            } else if (j9 % 25 != 0) {
                                                i11 = 1;
                                            } else if (j9 % 125 != 0) {
                                                i11 = 2;
                                            } else if (j9 % 625 != 0) {
                                                i11 = 3;
                                            } else {
                                                long j21 = j9 / 625;
                                                i11 = 4;
                                                for (long j22 = 0; j21 > j22 && j21 % 5 == j22; j22 = 0) {
                                                    j21 /= 5;
                                                    i11++;
                                                }
                                            }
                                            z6 = i11 >= iMax;
                                            z5 = false;
                                            j2 = j12;
                                            z4 = z6;
                                            i4 = iMax;
                                        } else {
                                            if (j8 % 5 != 0) {
                                                i10 = 0;
                                            } else if (j8 % 25 != 0) {
                                                i10 = 1;
                                            } else if (j8 % 125 != 0) {
                                                i10 = 2;
                                            } else if (j8 % 625 != 0) {
                                                i10 = 3;
                                            } else {
                                                long j23 = j8 / 625;
                                                i10 = 4;
                                                for (long j24 = 0; j23 > j24 && j23 % 5 == j24; j24 = 0) {
                                                    j23 /= 5;
                                                    i10++;
                                                }
                                            }
                                            if (i10 >= iMax) {
                                                j4--;
                                            }
                                        }
                                        z5 = false;
                                        j2 = j12;
                                        z4 = z6;
                                        i4 = iMax;
                                    }
                                } else {
                                    z5 = false;
                                    j2 = j12;
                                    z4 = z6;
                                    i4 = iMax;
                                }
                            } else {
                                throw new IllegalArgumentException("" + i18);
                            }
                        } else {
                            z2 = z7;
                            z3 = z8;
                            int i23 = -i16;
                            int iMax2 = Math.max(0, ((int) ((i23 * 6989700) / 10000000)) - 1);
                            int i24 = i23 - iMax2;
                            int i25 = (iMax2 - ((i24 == 0 ? 1 : (int) (((i24 * 23219280) + 9999999) / 10000000)) - 121)) - 114;
                            if (i25 >= 0) {
                                int[] iArr2 = POW5_SPLIT[i24];
                                long j25 = j7 >>> 31;
                                long j26 = j7 & 2147483647L;
                                int i26 = iArr2[0];
                                int i27 = iArr2[1];
                                int i28 = i15;
                                int i29 = iArr2[2];
                                int i30 = iArr2[3];
                                long j27 = ((((((((((((j26 * i30) >>> 31) + (i29 * j26)) + (j25 * i30)) >>> 31) + (i27 * j26)) + (i29 * j25)) >>> 31) + (i26 * j26)) + (i27 * j25)) >>> 21) + ((i26 * j25) << 10)) >>> i25;
                                long j28 = j8 >>> 31;
                                long j29 = j8 & 2147483647L;
                                j2 = j27;
                                long j30 = ((((((((((((i30 * j29) >>> 31) + (i29 * j29)) + (j28 * i30)) >>> 31) + (i27 * j29)) + (i29 * j28)) >>> 31) + (i26 * j29)) + (i27 * j28)) >>> 21) + ((i26 * j28) << 10)) >>> i25;
                                long j31 = j9 >>> 31;
                                long j32 = j9 & 2147483647L;
                                j3 = ((((((((((((i30 * j32) >>> 31) + (i29 * j32)) + (j31 * i30)) >>> 31) + (i27 * j32)) + (i29 * j31)) >>> 31) + (i26 * j32)) + (i27 * j31)) >>> 21) + ((i26 * j31) << 10)) >>> i25;
                                i4 = iMax2 + i16;
                                if (iMax2 <= 1) {
                                    if (z3) {
                                        z5 = true;
                                        j4 = j30;
                                        z4 = i28 == 1;
                                    } else {
                                        j4 = j30 - 1;
                                        z5 = true;
                                        z4 = false;
                                    }
                                } else if (iMax2 < 63) {
                                    z5 = (j7 & ((1 << (iMax2 - 1)) - 1)) == 0;
                                    j4 = j30;
                                    z4 = false;
                                } else {
                                    j4 = j30;
                                    z4 = false;
                                    z5 = false;
                                }
                            } else {
                                throw new IllegalArgumentException("" + i25);
                            }
                        }
                        if (j4 >= 1000000000000000000L) {
                            i17 = 19;
                        } else if (j4 >= 100000000000000000L) {
                            i17 = 18;
                        } else if (j4 >= 10000000000000000L) {
                            i17 = 17;
                        } else if (j4 >= 1000000000000000L) {
                            i17 = 16;
                        } else if (j4 >= 100000000000000L) {
                            i17 = 15;
                        } else if (j4 >= 10000000000000L) {
                            i17 = 14;
                        } else if (j4 >= MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US) {
                            i17 = 13;
                        } else if (j4 >= 100000000000L) {
                            i17 = 12;
                        } else if (j4 >= 10000000000L) {
                            i17 = 11;
                        } else if (j4 >= C.NANOS_PER_SECOND) {
                            i17 = 10;
                        } else if (j4 >= 100000000) {
                            i17 = 9;
                        } else if (j4 >= 10000000) {
                            i17 = 8;
                        } else if (j4 >= 1000000) {
                            i17 = 7;
                        } else if (j4 >= SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US) {
                            i17 = 6;
                        } else if (j4 >= 10000) {
                            i17 = 5;
                        } else if (j4 >= 1000) {
                            i17 = 4;
                        } else if (j4 < 100) {
                            i17 = j4 >= 10 ? 2 : 1;
                        }
                        int i31 = i4 + i17;
                        int i32 = i31 - 1;
                        boolean z9 = i32 < -3 || i32 >= 7;
                        if (z4 || z5) {
                            int i33 = 0;
                            int i34 = 0;
                            while (true) {
                                long j33 = j4 / 10;
                                long j34 = j3 / 10;
                                if (j33 <= j34 || (j4 < 100 && z9)) {
                                    break;
                                }
                                z4 &= j3 % 10 == 0;
                                z5 &= i33 == 0;
                                i33 = (int) (j2 % 10);
                                j2 /= 10;
                                i34++;
                                j4 = j33;
                                j3 = j34;
                            }
                            if (z4 && z3) {
                                while (j3 % 10 == 0 && (j4 >= 100 || !z9)) {
                                    z5 &= i33 == 0;
                                    i33 = (int) (j2 % 10);
                                    j4 /= 10;
                                    j2 /= 10;
                                    j3 /= 10;
                                    i34++;
                                }
                            }
                            if (z5 && i33 == 5 && j2 % 2 == 0) {
                                i33 = 4;
                            }
                            j5 = j2 + (((j2 != j3 || (z4 && z3)) && i33 < 5) ? 0 : 1);
                            i5 = i34;
                        } else {
                            int i35 = 0;
                            i5 = 0;
                            while (true) {
                                long j35 = j4 / 10;
                                long j36 = j3 / 10;
                                if (j35 <= j36 || (j4 < 100 && z9)) {
                                    break;
                                }
                                i35 = (int) (j2 % 10);
                                j2 /= 10;
                                i5++;
                                j4 = j35;
                                j3 = j36;
                            }
                            j5 = j2 + ((j2 == j3 || i35 >= 5) ? 1 : 0);
                        }
                        int i36 = i17 - i5;
                        if (z2) {
                            i6 = i2 + 1;
                            cArr[i2] = '-';
                        } else {
                            i6 = i2;
                        }
                        if (!z9) {
                            char c2 = '0';
                            if (i32 < 0) {
                                int i37 = i6 + 1;
                                cArr[i6] = '0';
                                int i38 = i6 + 2;
                                cArr[i37] = '.';
                                int i39 = -1;
                                while (i39 > i32) {
                                    cArr[i38] = c2;
                                    i39--;
                                    i38++;
                                    c2 = '0';
                                }
                                i7 = i38;
                                for (int i40 = 0; i40 < i36; i40++) {
                                    cArr[((i38 + i36) - i40) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                    i7++;
                                }
                            } else if (i31 >= i36) {
                                for (int i41 = 0; i41 < i36; i41++) {
                                    cArr[((i6 + i36) - i41) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                }
                                int i42 = i6 + i36;
                                while (i36 < i31) {
                                    cArr[i42] = '0';
                                    i36++;
                                    i42++;
                                }
                                cArr[i42] = '.';
                                i7 = i42 + 2;
                                cArr[i42 + 1] = '0';
                            } else {
                                int i43 = i6 + 1;
                                for (int i44 = 0; i44 < i36; i44++) {
                                    if ((i36 - i44) - 1 == i32) {
                                        cArr[((i43 + i36) - i44) - 1] = '.';
                                        i43--;
                                    }
                                    cArr[((i43 + i36) - i44) - 1] = (char) ((j5 % 10) + 48);
                                    j5 /= 10;
                                }
                                i7 = i6 + i36 + 1;
                            }
                            return i7 - i2;
                        }
                        for (int i45 = 0; i45 < i36 - 1; i45++) {
                            int i46 = (int) (j5 % 10);
                            j5 /= 10;
                            cArr[(i6 + i36) - i45] = (char) (i46 + 48);
                        }
                        cArr[i6] = (char) ((j5 % 10) + 48);
                        cArr[i6 + 1] = '.';
                        int i47 = i6 + i36 + 1;
                        if (i36 == 1) {
                            cArr[i47] = '0';
                            i47++;
                        }
                        int i48 = i47 + 1;
                        cArr[i47] = 'E';
                        if (i32 < 0) {
                            cArr[i48] = '-';
                            i32 = -i32;
                            i48 = i47 + 2;
                        }
                        if (i32 >= 100) {
                            int i49 = i48 + 1;
                            i8 = 48;
                            cArr[i48] = (char) ((i32 / 100) + 48);
                            i32 %= 100;
                            i48 += 2;
                            cArr[i49] = (char) ((i32 / 10) + 48);
                        } else {
                            i8 = 48;
                            if (i32 >= 10) {
                                cArr[i48] = (char) ((i32 / 10) + 48);
                                i48++;
                            }
                        }
                        i9 = i48 + 1;
                        cArr[i48] = (char) ((i32 % 10) + i8);
                    }
                    return i9 - i2;
                }
                cArr[i2] = '0';
                cArr[i2 + 1] = '.';
                i13 = i2 + 3;
                cArr[i2 + 2] = '0';
            }
        }
        return i13 - i2;
    }
}
