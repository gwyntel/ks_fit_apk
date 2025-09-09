package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class r0 extends g5 {

    /* renamed from: a, reason: collision with root package name */
    public static final int[][] f17711a = {new int[]{2, 1, 2, 2, 2, 2}, new int[]{2, 2, 2, 1, 2, 2}, new int[]{2, 2, 2, 2, 2, 1}, new int[]{1, 2, 1, 2, 2, 3}, new int[]{1, 2, 1, 3, 2, 2}, new int[]{1, 3, 1, 2, 2, 2}, new int[]{1, 2, 2, 2, 1, 3}, new int[]{1, 2, 2, 3, 1, 2}, new int[]{1, 3, 2, 2, 1, 2}, new int[]{2, 2, 1, 2, 1, 3}, new int[]{2, 2, 1, 3, 1, 2}, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, new int[]{1, 2, 2, 1, 3, 2}, new int[]{1, 2, 2, 2, 3, 1}, new int[]{1, 1, 3, 2, 2, 2}, new int[]{1, 2, 3, 1, 2, 2}, new int[]{1, 2, 3, 2, 2, 1}, new int[]{2, 2, 3, 2, 1, 1}, new int[]{2, 2, 1, 1, 3, 2}, new int[]{2, 2, 1, 2, 3, 1}, new int[]{2, 1, 3, 2, 1, 2}, new int[]{2, 2, 3, 1, 1, 2}, new int[]{3, 1, 2, 1, 3, 1}, new int[]{3, 1, 1, 2, 2, 2}, new int[]{3, 2, 1, 1, 2, 2}, new int[]{3, 2, 1, 2, 2, 1}, new int[]{3, 1, 2, 2, 1, 2}, new int[]{3, 2, 2, 1, 1, 2}, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, new int[]{2, 1, 2, 3, 2, 1}, new int[]{2, 3, 2, 1, 2, 1}, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, new int[]{1, 1, 2, 3, 1, 3}, new int[]{1, 3, 2, 1, 1, 3}, new int[]{1, 3, 2, 3, 1, 1}, new int[]{2, 1, 1, 3, 1, 3}, new int[]{2, 3, 1, 1, 1, 3}, new int[]{2, 3, 1, 3, 1, 1}, new int[]{1, 1, 2, 1, 3, 3}, new int[]{1, 1, 2, 3, 3, 1}, new int[]{1, 3, 2, 1, 3, 1}, new int[]{1, 1, 3, 1, 2, 3}, new int[]{1, 1, 3, 3, 2, 1}, new int[]{1, 3, 3, 1, 2, 1}, new int[]{3, 1, 3, 1, 2, 1}, new int[]{2, 1, 1, 3, 3, 1}, new int[]{2, 3, 1, 1, 3, 1}, new int[]{2, 1, 3, 1, 1, 3}, new int[]{2, 1, 3, 3, 1, 1}, new int[]{2, 1, 3, 1, 3, 1}, new int[]{3, 1, 1, 1, 2, 3}, new int[]{3, 1, 1, 3, 2, 1}, new int[]{3, 3, 1, 1, 2, 1}, new int[]{3, 1, 2, 1, 1, 3}, new int[]{3, 1, 2, 3, 1, 1}, new int[]{3, 3, 2, 1, 1, 1}, new int[]{3, 1, 4, 1, 1, 1}, new int[]{2, 2, 1, 4, 1, 1}, new int[]{4, 3, 1, 1, 1, 1}, new int[]{1, 1, 1, 2, 2, 4}, new int[]{1, 1, 1, 4, 2, 2}, new int[]{1, 2, 1, 1, 2, 4}, new int[]{1, 2, 1, 4, 2, 1}, new int[]{1, 4, 1, 1, 2, 2}, new int[]{1, 4, 1, 2, 2, 1}, new int[]{1, 1, 2, 2, 1, 4}, new int[]{1, 1, 2, 4, 1, 2}, new int[]{1, 2, 2, 1, 1, 4}, new int[]{1, 2, 2, 4, 1, 1}, new int[]{1, 4, 2, 1, 1, 2}, new int[]{1, 4, 2, 2, 1, 1}, new int[]{2, 4, 1, 2, 1, 1}, new int[]{2, 2, 1, 1, 1, 4}, new int[]{4, 1, 3, 1, 1, 1}, new int[]{2, 4, 1, 1, 1, 2}, new int[]{1, 3, 4, 1, 1, 1}, new int[]{1, 1, 1, 2, 4, 2}, new int[]{1, 2, 1, 1, 4, 2}, new int[]{1, 2, 1, 2, 4, 1}, new int[]{1, 1, 4, 2, 1, 2}, new int[]{1, 2, 4, 1, 1, 2}, new int[]{1, 2, 4, 2, 1, 1}, new int[]{4, 1, 1, 2, 1, 2}, new int[]{4, 2, 1, 1, 1, 2}, new int[]{4, 2, 1, 2, 1, 1}, new int[]{2, 1, 2, 1, 4, 1}, new int[]{2, 1, 4, 1, 2, 1}, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, new int[]{1, 1, 1, 3, 4, 1}, new int[]{1, 3, 1, 1, 4, 1}, new int[]{1, 1, 4, 1, 1, 3}, new int[]{1, 1, 4, 3, 1, 1}, new int[]{4, 1, 1, 1, 1, 3}, new int[]{4, 1, 1, 3, 1, 1}, new int[]{1, 1, 3, 1, 4, 1}, new int[]{1, 1, 4, 1, 3, 1}, new int[]{3, 1, 1, 1, 4, 1}, new int[]{4, 1, 1, 1, 3, 1}, new int[]{2, 1, 1, 4, 1, 2}, new int[]{2, 1, 1, 2, 1, 4}, new int[]{2, 1, 1, 2, 3, 2}, new int[]{2, 3, 3, 1, 1, 1, 2}};

    private static boolean a(r rVar, int i2, int i3) {
        return rVar.a(i2, i3, false, false);
    }

    private static float b(r rVar, int[] iArr, int i2) {
        int[] iArr2 = new int[7];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        for (int i3 : iArr) {
            i2 += i3;
        }
        boolean z2 = true;
        int i4 = 0;
        while (z2 && i2 < rVar.e()) {
            if (rVar.b(i2)) {
                i4++;
                i2++;
            } else {
                iArr2[6] = i4;
                z2 = false;
            }
        }
        int[][] iArr3 = f17711a;
        return g5.a(iArr2, iArr3[iArr3.length - 1], 0.7f);
    }

    private int[] c(StringBuilder sb, int[] iArr) throws a {
        int i2;
        int i3;
        int i4 = iArr[0];
        int i5 = iArr[1] == 1 ? 1 : 0;
        int i6 = iArr[2] == 1 ? 1 : 0;
        int i7 = iArr[3] == 1 ? 1 : 0;
        int i8 = iArr[4];
        int i9 = iArr[5] == 1 ? 1 : 0;
        int i10 = iArr[6] == 1 ? 1 : 0;
        if (i4 < 100) {
            if (i4 < 10) {
                sb.append('0');
            }
            sb.append(i4);
            i2 = i7;
        } else {
            i2 = i4 == 106 ? i7 : 0;
            if (i4 != 106) {
                switch (i4) {
                    case 100:
                        i3 = 100;
                        break;
                    case 101:
                        i3 = 101;
                        break;
                    case 102:
                        break;
                    default:
                        throw a.a();
                }
                return new int[]{i4, i5, i6, i2, i3, i9, i10};
            }
            i10 = 1;
        }
        i3 = i8;
        return new int[]{i4, i5, i6, i2, i3, i9, i10};
    }

    private static int[] a(r rVar) throws a {
        int iE = rVar.e();
        int iC = rVar.c(0);
        int[] iArr = new int[6];
        boolean z2 = false;
        int i2 = 0;
        int i3 = iC;
        while (iC < iE) {
            if (rVar.b(iC) != z2) {
                iArr[i2] = iArr[i2] + 1;
            } else {
                if (i2 == 5) {
                    int i4 = -1;
                    float f2 = 0.25f;
                    for (int i5 = 103; i5 <= 105; i5++) {
                        float fA = g5.a(iArr, f17711a[i5], 0.7f);
                        if (fA < f2) {
                            i4 = i5;
                            f2 = fA;
                        }
                    }
                    if (i4 >= 0) {
                        return new int[]{i3, iC, i4};
                    }
                    i3 += iArr[0] + iArr[1];
                    int i6 = i2 - 1;
                    System.arraycopy(iArr, 2, iArr, 0, i6);
                    iArr[i6] = 0;
                    iArr[i2] = 0;
                    i2--;
                } else {
                    i2++;
                }
                iArr[i2] = 1;
                z2 = !z2;
            }
            iC++;
        }
        throw a.a();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int[] b(StringBuilder sb, int[] iArr) throws a {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = iArr[0];
        int i8 = 1;
        int i9 = iArr[1] == 1 ? 1 : 0;
        int i10 = iArr[2] == 1 ? 1 : 0;
        int i11 = iArr[3] == 1 ? 1 : 0;
        int i12 = iArr[4];
        int i13 = iArr[5] == 1 ? 1 : 0;
        int i14 = iArr[6] == 1 ? 1 : 0;
        if (i7 >= 96) {
            if (i7 != 106) {
                i11 = 0;
            }
            if (i7 != 106) {
                int i15 = 101;
                switch (i7) {
                    case 96:
                    case 97:
                    case 102:
                        i8 = i9;
                        break;
                    case 98:
                        i2 = i14;
                        i6 = 1;
                        i8 = i9;
                        i3 = i10;
                        i4 = i11;
                        i5 = 101;
                        break;
                    case 99:
                        i15 = 99;
                        i8 = i9;
                        i3 = i10;
                        i4 = i11;
                        i6 = i13;
                        i5 = i15;
                        i2 = i14;
                        break;
                    case 100:
                        if (i10 == 0 && i9 != 0) {
                            i3 = 1;
                            i4 = i11;
                            i5 = i12;
                            i6 = i13;
                            i2 = i14;
                            i8 = 0;
                            break;
                        } else {
                            if (i10 != 0 && i9 != 0) {
                                i8 = 0;
                                i3 = 0;
                            }
                            i4 = i11;
                            i5 = i12;
                            i6 = i13;
                            i2 = i14;
                            break;
                        }
                        break;
                    case 101:
                        i8 = i9;
                        i3 = i10;
                        i4 = i11;
                        i6 = i13;
                        i5 = i15;
                        i2 = i14;
                        break;
                    default:
                        throw a.a();
                }
            } else {
                int i16 = i13;
                i2 = 1;
                i8 = i9;
                i3 = i10;
                i4 = i11;
                i5 = i12;
                i6 = i16;
            }
            return new int[]{i7, i8, i3, i4, i5, i6, i2};
        }
        if (i9 == i10) {
            sb.append((char) (i7 + 32));
        } else {
            sb.append((char) (i7 + 160));
        }
        i8 = 0;
        i3 = i10;
        i4 = i11;
        i5 = i12;
        i6 = i13;
        i2 = i14;
        return new int[]{i7, i8, i3, i4, i5, i6, i2};
    }

    private static int a(r rVar, int[] iArr, int i2) throws a {
        float fA;
        g5.a(rVar, i2, iArr);
        float f2 = 0.25f;
        int i3 = -1;
        int i4 = 0;
        while (true) {
            int[][] iArr2 = f17711a;
            if (i4 >= iArr2.length) {
                break;
            }
            int[] iArr3 = iArr2[i4];
            if (i4 == iArr2.length - 1) {
                fA = b(rVar, iArr, i2);
            } else {
                fA = g5.a(iArr, iArr3, 0.7f);
            }
            if (fA < f2) {
                i3 = i4;
                f2 = fA;
            }
            i4++;
        }
        if (i3 >= 0) {
            return i3;
        }
        throw a.a();
    }

    @Override // com.huawei.hms.scankit.p.g5
    public s6 a(int i2, r rVar, Map<l1, ?> map) throws a {
        int[] iArrA = a(rVar);
        int i3 = iArrA[0];
        int i4 = i3 - (((iArrA[1] - i3) / 11) * 10);
        if (i4 > 0 && i4 < i3) {
            if (!a(rVar, i4, i3)) {
                throw a.a();
            }
        }
        int i5 = iArrA[2];
        ArrayList arrayList = new ArrayList(20);
        arrayList.add(Byte.valueOf((byte) i5));
        int i6 = i5 == 103 ? 101 : i5 == 104 ? 100 : i5 == 105 ? 99 : 0;
        if (i6 != 0) {
            StringBuilder sb = new StringBuilder(20);
            int[] iArr = new int[7];
            iArr[6] = i6;
            a(sb, iArrA, iArr, i5, rVar, arrayList);
            int i7 = iArr[0];
            int i8 = iArr[1];
            int i9 = iArr[2];
            int i10 = iArr[3];
            int i11 = iArr[4];
            boolean z2 = iArr[5] == 1;
            int i12 = iArr[6];
            int i13 = i8 - i7;
            if ((i10 - (i11 * i9)) % 103 == i9) {
                int length = sb.length();
                if (length != 0) {
                    if (length > 0 && z2) {
                        if (i12 == 99) {
                            sb.delete(length - 2, length);
                        } else {
                            sb.delete(length - 1, length);
                        }
                    }
                    float f2 = iArrA[0];
                    float f3 = i7 + ((i13 * 13) / 11);
                    int size = arrayList.size();
                    byte[] bArr = new byte[size];
                    for (int i14 = 0; i14 < size; i14++) {
                        bArr[i14] = arrayList.get(i14).byteValue();
                    }
                    float f4 = i2;
                    return new s6(sb.toString(), bArr, new u6[]{new u6(f2, f4), new u6(f3, f4)}, BarcodeFormat.CODE_128);
                }
                throw a.a();
            }
            throw a.a();
        }
        throw a.a();
    }

    private void a(StringBuilder sb, int[] iArr, int[] iArr2, int i2, r rVar, List<Byte> list) throws a {
        r0 r0Var = this;
        boolean z2 = false;
        int i3 = iArr[0];
        int i4 = iArr[1];
        int[] iArr3 = new int[6];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        boolean z3 = false;
        int i10 = 0;
        int i11 = 1;
        int i12 = iArr2[6];
        int i13 = i4;
        int i14 = i3;
        int i15 = i2;
        while (i5 == 0) {
            int iA = a(rVar, iArr3, i13);
            list.add(Byte.valueOf((byte) iA));
            if (iA != 106) {
                i7++;
                i15 += i7 * iA;
                i11 = 1;
            }
            int i16 = i13;
            for (int i17 = 0; i17 < 6; i17++) {
                i16 += iArr3[i17];
            }
            if (iA != 105) {
                int[] iArrC = {iA, i8, i9, i11, i12, 0, i5};
                if (i12 == 101) {
                    iArrC = r0Var.a(sb, iArrC);
                } else if (i12 == 100) {
                    iArrC = r0Var.b(sb, iArrC);
                } else if (i12 == 99) {
                    iArrC = r0Var.c(sb, iArrC);
                }
                int i18 = iArrC[0];
                i8 = iArrC[1] == 1 ? 1 : 0;
                i9 = iArrC[2] == 1 ? 1 : 0;
                int i19 = iArrC[3] == 1 ? 1 : 0;
                boolean z4 = iArrC[5] == 1;
                int i20 = iArrC[6] == 1 ? 1 : 0;
                if (z3) {
                    i12 = iArrC[4] == 101 ? 100 : 101;
                } else {
                    i12 = iArrC[4];
                }
                z3 = z4;
                i6 = i10;
                z2 = false;
                i5 = i20;
                i10 = i18;
                r0Var = this;
                i11 = i19;
                i14 = i13;
                i13 = i16;
            } else {
                throw a.a();
            }
        }
        iArr2[z2 ? 1 : 0] = i14;
        iArr2[1] = i13;
        iArr2[2] = i6;
        iArr2[3] = i15;
        iArr2[4] = i7;
        iArr2[5] = i11;
        iArr2[6] = i12;
    }

    private int[] a(StringBuilder sb, int[] iArr) throws a {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = iArr[0];
        int i8 = 1;
        int i9 = iArr[1] == 1 ? 1 : 0;
        int i10 = iArr[2] == 1 ? 1 : 0;
        int i11 = iArr[3] == 1 ? 1 : 0;
        int i12 = iArr[4];
        int i13 = iArr[5] == 1 ? 1 : 0;
        int i14 = iArr[6] == 1 ? 1 : 0;
        if (i7 < 64) {
            if (i9 == i10) {
                sb.append((char) (i7 + 32));
            } else {
                sb.append((char) (i7 + 160));
            }
        } else {
            if (i7 >= 96) {
                if (i7 != 106) {
                    i11 = 0;
                }
                if (i7 != 106) {
                    int i15 = 100;
                    switch (i7) {
                        case 96:
                        case 97:
                        case 102:
                            i8 = i9;
                            i3 = i10;
                            i4 = i11;
                            i5 = i12;
                            i6 = i13;
                            i2 = i14;
                            break;
                        case 98:
                            i2 = i14;
                            i6 = 1;
                            i8 = i9;
                            i3 = i10;
                            i4 = i11;
                            i5 = 100;
                            break;
                        case 99:
                            i15 = 99;
                        case 100:
                            i8 = i9;
                            i3 = i10;
                            i4 = i11;
                            i6 = i13;
                            i5 = i15;
                            i2 = i14;
                            break;
                        case 101:
                            if (i10 == 0 && i9 != 0) {
                                i3 = 1;
                                i4 = i11;
                                i5 = i12;
                                i6 = i13;
                                i2 = i14;
                                i8 = 0;
                                break;
                            } else {
                                if (i10 == 0 || i9 == 0) {
                                    i3 = i10;
                                } else {
                                    i8 = 0;
                                    i3 = 0;
                                }
                                i4 = i11;
                                i5 = i12;
                                i6 = i13;
                                i2 = i14;
                                break;
                            }
                        default:
                            throw a.a();
                    }
                } else {
                    int i16 = i13;
                    i2 = 1;
                    i8 = i9;
                    i3 = i10;
                    i4 = i11;
                    i5 = i12;
                    i6 = i16;
                }
                return new int[]{i7, i8, i3, i4, i5, i6, i2};
            }
            if (i9 == i10) {
                sb.append((char) (i7 - 64));
            } else {
                sb.append((char) (i7 + 64));
            }
        }
        i8 = 0;
        i3 = i10;
        i4 = i11;
        i5 = i12;
        i6 = i13;
        i2 = i14;
        return new int[]{i7, i8, i3, i4, i5, i6, i2};
    }
}
