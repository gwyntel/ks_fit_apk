package com.alibaba.fastjson.util;

import androidx.media3.common.C;
import com.luck.picture.lib.config.FileSizeUnit;

/* loaded from: classes2.dex */
public final class RyuFloat {
    private static final int[][] POW5_SPLIT = {new int[]{C.BUFFER_FLAG_LAST_SAMPLE, 0}, new int[]{671088640, 0}, new int[]{838860800, 0}, new int[]{1048576000, 0}, new int[]{655360000, 0}, new int[]{819200000, 0}, new int[]{1024000000, 0}, new int[]{640000000, 0}, new int[]{800000000, 0}, new int[]{FileSizeUnit.ACCURATE_GB, 0}, new int[]{625000000, 0}, new int[]{781250000, 0}, new int[]{976562500, 0}, new int[]{610351562, 1073741824}, new int[]{762939453, 268435456}, new int[]{953674316, 872415232}, new int[]{596046447, 1619001344}, new int[]{745058059, 1486880768}, new int[]{931322574, 1321730048}, new int[]{582076609, 289210368}, new int[]{727595761, 898383872}, new int[]{909494701, 1659850752}, new int[]{568434188, 1305842176}, new int[]{710542735, 1632302720}, new int[]{888178419, 1503507488}, new int[]{555111512, 671256724}, new int[]{693889390, 839070905}, new int[]{867361737, 2122580455}, new int[]{542101086, 521306416}, new int[]{677626357, 1725374844}, new int[]{847032947, 546105819}, new int[]{1058791184, 145761362}, new int[]{661744490, 91100851}, new int[]{827180612, 1187617888}, new int[]{1033975765, 1484522360}, new int[]{646234853, 1196261931}, new int[]{807793566, 2032198326}, new int[]{1009741958, 1466506084}, new int[]{631088724, 379695390}, new int[]{788860905, 474619238}, new int[]{986076131, 1130144959}, new int[]{616297582, 437905143}, new int[]{770371977, 1621123253}, new int[]{962964972, 415791331}, new int[]{601853107, 1333611405}, new int[]{752316384, 1130143345}, new int[]{940395480, 1412679181}};
    private static final int[][] POW5_INV_SPLIT = {new int[]{268435456, 1}, new int[]{214748364, 1717986919}, new int[]{171798691, 1803886265}, new int[]{137438953, 1013612282}, new int[]{219902325, 1192282922}, new int[]{175921860, 953826338}, new int[]{140737488, 763061070}, new int[]{225179981, 791400982}, new int[]{180143985, 203624056}, new int[]{144115188, 162899245}, new int[]{230584300, 1978625710}, new int[]{184467440, 1582900568}, new int[]{147573952, 1266320455}, new int[]{236118324, 308125809}, new int[]{188894659, 675997377}, new int[]{151115727, 970294631}, new int[]{241785163, 1981968139}, new int[]{193428131, 297084323}, new int[]{154742504, 1955654377}, new int[]{247588007, 1840556814}, new int[]{198070406, 613451992}, new int[]{158456325, 61264864}, new int[]{253530120, 98023782}, new int[]{202824096, 78419026}, new int[]{162259276, 1780722139}, new int[]{259614842, 1990161963}, new int[]{207691874, 733136111}, new int[]{166153499, 1016005619}, new int[]{265845599, 337118801}, new int[]{212676479, 699191770}, new int[]{170141183, 988850146}};

    public static String toString(float f2) {
        char[] cArr = new char[15];
        return new String(cArr, 0, toString(f2, cArr, 0));
    }

    public static int toString(float f2, char[] cArr, int i2) {
        int i3;
        boolean z2;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        if (!Float.isNaN(f2)) {
            if (f2 == Float.POSITIVE_INFINITY) {
                cArr[i2] = 'I';
                cArr[i2 + 1] = 'n';
                cArr[i2 + 2] = 'f';
                cArr[i2 + 3] = 'i';
                cArr[i2 + 4] = 'n';
                cArr[i2 + 5] = 'i';
                cArr[i2 + 6] = 't';
                i17 = i2 + 8;
                cArr[i2 + 7] = 'y';
            } else if (f2 == Float.NEGATIVE_INFINITY) {
                cArr[i2] = '-';
                cArr[i2 + 1] = 'I';
                cArr[i2 + 2] = 'n';
                cArr[i2 + 3] = 'f';
                cArr[i2 + 4] = 'i';
                cArr[i2 + 5] = 'n';
                cArr[i2 + 6] = 'i';
                cArr[i2 + 7] = 't';
                i18 = i2 + 9;
                cArr[i2 + 8] = 'y';
            } else {
                int iFloatToIntBits = Float.floatToIntBits(f2);
                if (iFloatToIntBits == 0) {
                    cArr[i2] = '0';
                    cArr[i2 + 1] = '.';
                    i18 = i2 + 3;
                    cArr[i2 + 2] = '0';
                } else if (iFloatToIntBits == Integer.MIN_VALUE) {
                    cArr[i2] = '-';
                    cArr[i2 + 1] = '0';
                    cArr[i2 + 2] = '.';
                    i17 = i2 + 4;
                    cArr[i2 + 3] = '0';
                } else {
                    int i19 = (iFloatToIntBits >> 23) & 255;
                    int i20 = 8388607 & iFloatToIntBits;
                    if (i19 == 0) {
                        i3 = -149;
                    } else {
                        i3 = i19 - 150;
                        i20 |= 8388608;
                    }
                    boolean z3 = iFloatToIntBits < 0;
                    boolean z4 = (i20 & 1) == 0;
                    int i21 = i20 * 4;
                    int i22 = i21 + 2;
                    int i23 = i21 - ((((long) i20) != 8388608 || i19 <= 1) ? 2 : 1);
                    int i24 = i3 - 2;
                    if (i24 >= 0) {
                        i10 = (int) ((i24 * 3010299) / 10000000);
                        int i25 = i10 == 0 ? 1 : (int) (((i10 * 23219280) + 9999999) / 10000000);
                        int i26 = (-i24) + i10;
                        int[][] iArr = POW5_INV_SPLIT;
                        int[] iArr2 = iArr[i10];
                        long j2 = iArr2[0];
                        long j3 = iArr2[1];
                        long j4 = i21;
                        int i27 = ((i25 + 58) + i26) - 31;
                        i4 = (int) (((j4 * j2) + ((j4 * j3) >> 31)) >> i27);
                        long j5 = i22;
                        i13 = (int) (((j5 * j2) + ((j5 * j3) >> 31)) >> i27);
                        z2 = z4;
                        int i28 = i23;
                        long j6 = i28;
                        i5 = (int) (((j2 * j6) + ((j6 * j3) >> 31)) >> i27);
                        if (i10 == 0 || (i13 - 1) / 10 > i5 / 10) {
                            i9 = 0;
                        } else {
                            int i29 = i10 - 1;
                            int i30 = (i26 - 1) + (i29 == 0 ? 1 : (int) (((i29 * 23219280) + 9999999) / 10000000)) + 58;
                            int[] iArr3 = iArr[i29];
                            i9 = (int) ((((iArr3[0] * j4) + ((j4 * iArr3[1]) >> 31)) >> (i30 - 31)) % 10);
                        }
                        int i31 = 0;
                        while (i22 > 0 && i22 % 5 == 0) {
                            i22 /= 5;
                            i31++;
                        }
                        int i32 = i21;
                        int i33 = 0;
                        while (i32 > 0 && i32 % 5 == 0) {
                            i32 /= 5;
                            i33++;
                        }
                        int i34 = 0;
                        while (i28 > 0 && i28 % 5 == 0) {
                            i28 /= 5;
                            i34++;
                        }
                        i11 = i31 >= i10 ? 1 : 0;
                        int i35 = i33 >= i10 ? 1 : 0;
                        i12 = i34 >= i10 ? 1 : 0;
                        i8 = i35;
                        i6 = 0;
                    } else {
                        z2 = z4;
                        int i36 = -i24;
                        int i37 = (int) ((i36 * 6989700) / 10000000);
                        int i38 = i36 - i37;
                        int i39 = i38 == 0 ? 1 : (int) (((i38 * 23219280) + 9999999) / 10000000);
                        int[][] iArr4 = POW5_SPLIT;
                        int[] iArr5 = iArr4[i38];
                        long j7 = iArr5[0];
                        long j8 = iArr5[1];
                        int i40 = (i37 - (i39 - 61)) - 31;
                        long j9 = i21;
                        long j10 = i22;
                        int i41 = (int) (((j10 * j7) + ((j10 * j8) >> 31)) >> i40);
                        i4 = (int) (((j9 * j7) + ((j9 * j8) >> 31)) >> i40);
                        long j11 = i23;
                        i5 = (int) (((j7 * j11) + ((j11 * j8) >> 31)) >> i40);
                        if (i37 == 0 || (i41 - 1) / 10 > i5 / 10) {
                            i6 = 0;
                            i7 = 0;
                        } else {
                            int i42 = i38 + 1;
                            int i43 = (i37 - 1) - ((i42 == 0 ? 1 : (int) (((i42 * 23219280) + 9999999) / 10000000)) - 61);
                            int[] iArr6 = iArr4[i42];
                            i6 = 0;
                            i7 = (int) ((((iArr6[0] * j9) + ((j9 * iArr6[1]) >> 31)) >> (i43 - 31)) % 10);
                        }
                        int i44 = i24 + i37;
                        int i45 = 1 >= i37 ? 1 : i6;
                        i8 = (i37 >= 23 || (i21 & ((1 << (i37 + (-1))) - 1)) != 0) ? i6 : 1;
                        int i46 = (i23 % 2 == 1 ? i6 : 1) >= i37 ? 1 : i6;
                        i9 = i7;
                        i10 = i44;
                        i11 = i45;
                        i12 = i46;
                        i13 = i41;
                    }
                    int i47 = FileSizeUnit.ACCURATE_GB;
                    int i48 = 10;
                    while (i48 > 0 && i13 < i47) {
                        i47 /= 10;
                        i48--;
                    }
                    int i49 = i10 + i48;
                    int i50 = i49 - 1;
                    int i51 = (i50 < -3 || i50 >= 7) ? 1 : i6;
                    if (i11 != 0 && !z2) {
                        i13--;
                    }
                    int i52 = i6;
                    while (true) {
                        int i53 = i13 / 10;
                        int i54 = i5 / 10;
                        if (i53 <= i54 || (i13 < 100 && i51 != 0)) {
                            break;
                        }
                        i12 &= i5 % 10 == 0 ? 1 : i6;
                        i9 = i4 % 10;
                        i4 /= 10;
                        i52++;
                        i13 = i53;
                        i5 = i54;
                    }
                    if (i12 != 0 && z2) {
                        while (i5 % 10 == 0 && (i13 >= 100 || i51 == 0)) {
                            i13 /= 10;
                            i9 = i4 % 10;
                            i4 /= 10;
                            i5 /= 10;
                            i52++;
                        }
                    }
                    int i55 = i4;
                    if (i8 != 0 && i9 == 5 && i55 % 2 == 0) {
                        i9 = 4;
                    }
                    int i56 = (((i55 != i5 || (i12 != 0 && z2)) && i9 < 5) ? i6 : 1) + i55;
                    int i57 = i48 - i52;
                    if (z3) {
                        i14 = i2 + 1;
                        cArr[i2] = '-';
                    } else {
                        i14 = i2;
                    }
                    if (i51 != 0) {
                        while (i6 < i57 - 1) {
                            int i58 = i56 % 10;
                            i56 /= 10;
                            cArr[(i14 + i57) - i6] = (char) (i58 + 48);
                            i6++;
                        }
                        cArr[i14] = (char) ((i56 % 10) + 48);
                        cArr[i14 + 1] = '.';
                        int i59 = i14 + i57 + 1;
                        if (i57 == 1) {
                            cArr[i59] = '0';
                            i59++;
                        }
                        int i60 = i59 + 1;
                        cArr[i59] = 'E';
                        if (i50 < 0) {
                            cArr[i60] = '-';
                            i50 = -i50;
                            i60 = i59 + 2;
                        }
                        if (i50 >= 10) {
                            i16 = 48;
                            cArr[i60] = (char) ((i50 / 10) + 48);
                            i60++;
                        } else {
                            i16 = 48;
                        }
                        i15 = i60 + 1;
                        cArr[i60] = (char) ((i50 % 10) + i16);
                    } else {
                        int i61 = 48;
                        if (i50 < 0) {
                            int i62 = i14 + 1;
                            cArr[i14] = '0';
                            int i63 = i14 + 2;
                            cArr[i62] = '.';
                            int i64 = -1;
                            while (i64 > i50) {
                                cArr[i63] = '0';
                                i64--;
                                i63++;
                            }
                            int i65 = i63;
                            int i66 = i6;
                            while (i66 < i57) {
                                cArr[((i63 + i57) - i66) - 1] = (char) ((i56 % 10) + i61);
                                i56 /= 10;
                                i65++;
                                i66++;
                                i61 = 48;
                            }
                            i15 = i65;
                        } else if (i49 >= i57) {
                            for (int i67 = i6; i67 < i57; i67++) {
                                cArr[((i14 + i57) - i67) - 1] = (char) ((i56 % 10) + 48);
                                i56 /= 10;
                            }
                            int i68 = i14 + i57;
                            while (i57 < i49) {
                                cArr[i68] = '0';
                                i57++;
                                i68++;
                            }
                            int i69 = i68 + 1;
                            cArr[i68] = '.';
                            i15 = i68 + 2;
                            cArr[i69] = '0';
                        } else {
                            int i70 = i14 + 1;
                            while (i6 < i57) {
                                if ((i57 - i6) - 1 == i50) {
                                    cArr[((i70 + i57) - i6) - 1] = '.';
                                    i70--;
                                }
                                cArr[((i70 + i57) - i6) - 1] = (char) ((i56 % 10) + 48);
                                i56 /= 10;
                                i6++;
                            }
                            i15 = i14 + i57 + 1;
                        }
                    }
                    return i15 - i2;
                }
            }
            return i17 - i2;
        }
        cArr[i2] = 'N';
        cArr[i2 + 1] = 'a';
        i18 = i2 + 3;
        cArr[i2 + 2] = 'N';
        return i18 - i2;
    }
}
