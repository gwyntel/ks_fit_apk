package org.mozilla.javascript;

import java.math.BigInteger;

/* loaded from: classes5.dex */
class DToA {
    private static final int Bias = 1023;
    private static final int Bletch = 16;
    private static final int Bndry_mask = 1048575;
    static final int DTOSTR_EXPONENTIAL = 3;
    static final int DTOSTR_FIXED = 2;
    static final int DTOSTR_PRECISION = 4;
    static final int DTOSTR_STANDARD = 0;
    static final int DTOSTR_STANDARD_EXPONENTIAL = 1;
    private static final int Exp_11 = 1072693248;
    private static final int Exp_mask = 2146435072;
    private static final int Exp_mask_shifted = 2047;
    private static final int Exp_msk1 = 1048576;
    private static final long Exp_msk1L = 4503599627370496L;
    private static final int Exp_shift = 20;
    private static final int Exp_shift1 = 20;
    private static final int Exp_shiftL = 52;
    private static final int Frac_mask = 1048575;
    private static final int Frac_mask1 = 1048575;
    private static final long Frac_maskL = 4503599627370495L;
    private static final int Int_max = 14;
    private static final int Log2P = 1;
    private static final int P = 53;
    private static final int Quick_max = 14;
    private static final int Sign_bit = Integer.MIN_VALUE;
    private static final int Ten_pmax = 22;
    private static final int n_bigtens = 5;
    private static final double[] tens = {1.0d, 10.0d, 100.0d, 1000.0d, 10000.0d, 100000.0d, 1000000.0d, 1.0E7d, 1.0E8d, 1.0E9d, 1.0E10d, 1.0E11d, 1.0E12d, 1.0E13d, 1.0E14d, 1.0E15d, 1.0E16d, 1.0E17d, 1.0E18d, 1.0E19d, 1.0E20d, 1.0E21d, 1.0E22d};
    private static final double[] bigtens = {1.0E16d, 1.0E32d, 1.0E64d, 1.0E128d, 1.0E256d};
    private static final int[] dtoaModes = {0, 0, 3, 2, 2};

    DToA() {
    }

    private static char BASEDIGIT(int i2) {
        return (char) (i2 >= 10 ? i2 + 87 : i2 + 48);
    }

    /* JADX WARN: Code restructure failed: missing block: B:351:0x05c9, code lost:
    
        if (r12 <= 0) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:352:0x05cb, code lost:
    
        r2 = r11.shiftLeft(1).compareTo(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:353:0x05d4, code lost:
    
        if (r2 > 0) goto L361;
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x05d6, code lost:
    
        if (r2 != 0) goto L360;
     */
    /* JADX WARN: Code restructure failed: missing block: B:356:0x05da, code lost:
    
        if ((r5 & 1) == 1) goto L361;
     */
    /* JADX WARN: Code restructure failed: missing block: B:357:0x05dc, code lost:
    
        if (r46 == false) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:359:0x05df, code lost:
    
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:360:0x05e1, code lost:
    
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:361:0x05e3, code lost:
    
        r0 = (char) (r5 + 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x05e8, code lost:
    
        if (r5 != '9') goto L368;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x05ea, code lost:
    
        r49.append('9');
     */
    /* JADX WARN: Code restructure failed: missing block: B:364:0x05f1, code lost:
    
        if (roundOff(r49) == false) goto L366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:365:0x05f3, code lost:
    
        r10 = r10 + 1;
        r49.append('1');
     */
    /* JADX WARN: Code restructure failed: missing block: B:367:0x05fc, code lost:
    
        return r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x05fd, code lost:
    
        r3 = 1;
        r5 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x05ff, code lost:
    
        r49.append(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x0603, code lost:
    
        return r10 + r3;
     */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x032d  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0350  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0417  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0422 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0428  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0453  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x048b  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0494  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04a5  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x04ac  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x04b2  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x04d8  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0508  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0604  */
    /* JADX WARN: Removed duplicated region for block: B:377:0x0628 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0636  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x063c  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0161 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static int JS_dtoa(double r43, int r45, boolean r46, int r47, boolean[] r48, java.lang.StringBuilder r49) {
        /*
            Method dump skipped, instructions count: 1623
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.DToA.JS_dtoa(double, int, boolean, int, boolean[], java.lang.StringBuilder):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0123, code lost:
    
        if (r8 > 0) goto L77;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static java.lang.String JS_dtobasestr(int r12, double r13) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.DToA.JS_dtobasestr(int, double):java.lang.String");
    }

    static void JS_dtostr(StringBuilder sb, int i2, int i3, double d2) {
        boolean z2;
        boolean[] zArr = new boolean[1];
        if (i2 == 2 && (d2 >= 1.0E21d || d2 <= -1.0E21d)) {
            i2 = 0;
        }
        int iJS_dtoa = JS_dtoa(d2, dtoaModes[i2], i2 >= 2, i3, zArr, sb);
        int length = sb.length();
        if (iJS_dtoa != 9999) {
            if (i2 != 0) {
                if (i2 != 1) {
                    if (i2 == 2) {
                        i3 = i3 >= 0 ? i3 + iJS_dtoa : iJS_dtoa;
                    } else if (i2 != 3) {
                        if (i2 != 4) {
                            z2 = false;
                            i3 = 0;
                        } else if (iJS_dtoa < -5 || iJS_dtoa > i3) {
                        }
                    }
                    z2 = false;
                } else {
                    i3 = 0;
                }
                z2 = true;
            } else {
                if (iJS_dtoa < -5 || iJS_dtoa > 21) {
                    z2 = true;
                    i3 = 0;
                }
                z2 = false;
            }
            if (length < i3) {
                do {
                    sb.append('0');
                } while (sb.length() != i3);
                length = i3;
            }
            if (z2) {
                if (length != 1) {
                    sb.insert(1, '.');
                }
                sb.append('e');
                int i4 = iJS_dtoa - 1;
                if (i4 >= 0) {
                    sb.append('+');
                }
                sb.append(i4);
            } else if (iJS_dtoa != length) {
                if (iJS_dtoa > 0) {
                    sb.insert(iJS_dtoa, '.');
                } else {
                    for (int i5 = 0; i5 < 1 - iJS_dtoa; i5++) {
                        sb.insert(0, '0');
                    }
                    sb.insert(1, '.');
                }
            }
        }
        if (zArr[0]) {
            if (word0(d2) == Integer.MIN_VALUE && word1(d2) == 0) {
                return;
            }
            if ((word0(d2) & Exp_mask) != Exp_mask || (word1(d2) == 0 && (word0(d2) & 1048575) == 0)) {
                sb.insert(0, '-');
            }
        }
    }

    private static BigInteger d2b(double d2, int[] iArr, int[] iArr2) {
        byte[] bArr;
        int iLo0bits;
        long jDoubleToLongBits = Double.doubleToLongBits(d2);
        int i2 = (int) (jDoubleToLongBits >>> 32);
        int i3 = (int) jDoubleToLongBits;
        int i4 = 1048575 & i2;
        int i5 = (i2 & Integer.MAX_VALUE) >>> 20;
        if (i5 != 0) {
            i4 |= 1048576;
        }
        int i6 = 1;
        if (i3 != 0) {
            bArr = new byte[8];
            iLo0bits = lo0bits(i3);
            int i7 = i3 >>> iLo0bits;
            if (iLo0bits != 0) {
                stuffBits(bArr, 4, i7 | (i4 << (32 - iLo0bits)));
                i4 >>= iLo0bits;
            } else {
                stuffBits(bArr, 4, i7);
            }
            stuffBits(bArr, 0, i4);
            if (i4 != 0) {
                i6 = 2;
            }
        } else {
            bArr = new byte[4];
            int iLo0bits2 = lo0bits(i4);
            i4 >>>= iLo0bits2;
            stuffBits(bArr, 0, i4);
            iLo0bits = iLo0bits2 + 32;
        }
        if (i5 != 0) {
            iArr[0] = (i5 - 1075) + iLo0bits;
            iArr2[0] = 53 - iLo0bits;
        } else {
            iArr[0] = (i5 - 1074) + iLo0bits;
            iArr2[0] = (i6 * 32) - hi0bits(i4);
        }
        return new BigInteger(bArr);
    }

    private static int hi0bits(int i2) {
        int i3;
        if (((-65536) & i2) == 0) {
            i2 <<= 16;
            i3 = 16;
        } else {
            i3 = 0;
        }
        if (((-16777216) & i2) == 0) {
            i3 += 8;
            i2 <<= 8;
        }
        if (((-268435456) & i2) == 0) {
            i3 += 4;
            i2 <<= 4;
        }
        if (((-1073741824) & i2) == 0) {
            i3 += 2;
            i2 <<= 2;
        }
        if ((Integer.MIN_VALUE & i2) == 0) {
            i3++;
            if ((i2 & 1073741824) == 0) {
                return 32;
            }
        }
        return i3;
    }

    private static int lo0bits(int i2) {
        int i3 = 0;
        if ((i2 & 7) != 0) {
            if ((i2 & 1) != 0) {
                return 0;
            }
            return (i2 & 2) != 0 ? 1 : 2;
        }
        if ((65535 & i2) == 0) {
            i2 >>>= 16;
            i3 = 16;
        }
        if ((i2 & 255) == 0) {
            i3 += 8;
            i2 >>>= 8;
        }
        if ((i2 & 15) == 0) {
            i3 += 4;
            i2 >>>= 4;
        }
        if ((i2 & 3) == 0) {
            i3 += 2;
            i2 >>>= 2;
        }
        if ((i2 & 1) == 0) {
            i3++;
            if (((i2 >>> 1) & 1) == 0) {
                return 32;
            }
        }
        return i3;
    }

    static BigInteger pow5mult(BigInteger bigInteger, int i2) {
        return bigInteger.multiply(BigInteger.valueOf(5L).pow(i2));
    }

    static boolean roundOff(StringBuilder sb) {
        int length = sb.length();
        while (length != 0) {
            int i2 = length - 1;
            char cCharAt = sb.charAt(i2);
            if (cCharAt != '9') {
                sb.setCharAt(i2, (char) (cCharAt + 1));
                sb.setLength(length);
                return false;
            }
            length = i2;
        }
        sb.setLength(0);
        return true;
    }

    static double setWord0(double d2, int i2) {
        return Double.longBitsToDouble((Double.doubleToLongBits(d2) & 4294967295L) | (i2 << 32));
    }

    private static void stripTrailingZeroes(StringBuilder sb) {
        int length = sb.length();
        while (true) {
            int i2 = length - 1;
            if (length <= 0 || sb.charAt(i2) != '0') {
                break;
            } else {
                length = i2;
            }
        }
        sb.setLength(length);
    }

    private static void stuffBits(byte[] bArr, int i2, int i3) {
        bArr[i2] = (byte) (i3 >> 24);
        bArr[i2 + 1] = (byte) (i3 >> 16);
        bArr[i2 + 2] = (byte) (i3 >> 8);
        bArr[i2 + 3] = (byte) i3;
    }

    static int word0(double d2) {
        return (int) (Double.doubleToLongBits(d2) >> 32);
    }

    static int word1(double d2) {
        return (int) Double.doubleToLongBits(d2);
    }
}
