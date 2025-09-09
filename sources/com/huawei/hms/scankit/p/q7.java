package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class q7 extends g5 {

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f17701c = {1, 1, 1};

    /* renamed from: d, reason: collision with root package name */
    public static final int[] f17702d = {1, 1, 1, 1, 1};

    /* renamed from: e, reason: collision with root package name */
    public static final int[] f17703e = {1, 1, 1, 1, 1, 1};

    /* renamed from: f, reason: collision with root package name */
    public static final int[][] f17704f;

    /* renamed from: g, reason: collision with root package name */
    public static final int[][] f17705g;

    /* renamed from: a, reason: collision with root package name */
    private final StringBuilder f17706a = new StringBuilder(20);

    /* renamed from: b, reason: collision with root package name */
    private final p7 f17707b = new p7();

    static {
        int[][] iArr = {new int[]{3, 2, 1, 1}, new int[]{2, 2, 2, 1}, new int[]{2, 1, 2, 2}, new int[]{1, 4, 1, 1}, new int[]{1, 1, 3, 2}, new int[]{1, 2, 3, 1}, new int[]{1, 1, 1, 4}, new int[]{1, 3, 1, 2}, new int[]{1, 2, 1, 3}, new int[]{3, 1, 1, 2}};
        f17704f = iArr;
        int[][] iArr2 = new int[20][];
        f17705g = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, 10);
        for (int i2 = 10; i2 < 20; i2++) {
            int[] iArr3 = f17704f[i2 - 10];
            int[] iArr4 = new int[iArr3.length];
            for (int i3 = 0; i3 < iArr3.length; i3++) {
                iArr4[i3] = iArr3[(iArr3.length - i3) - 1];
            }
            f17705g[i2] = iArr4;
        }
    }

    protected q7() {
    }

    static int[] a(r rVar) throws a {
        return b(rVar, 0);
    }

    static ArrayList<int[]> b(r rVar) throws a {
        int iE = rVar.e() / 2;
        ArrayList<int[]> arrayList = new ArrayList<>();
        int i2 = 0;
        while (i2 < iE) {
            try {
                int[] iArrB = b(rVar, i2);
                arrayList.add(iArrB);
                i2 = iArrB[0] + 1;
            } catch (a unused) {
            }
        }
        if (arrayList.size() != 0) {
            return arrayList;
        }
        throw a.a();
    }

    protected abstract int a(r rVar, int[] iArr, StringBuilder sb) throws a;

    abstract BarcodeFormat a();

    abstract boolean a(int i2, int i3, r rVar);

    abstract boolean a(int[] iArr, int[] iArr2) throws a;

    @Override // com.huawei.hms.scankit.p.g5
    public s6 a(int i2, r rVar, Map<l1, ?> map) throws a {
        return a(i2, rVar, a(rVar), map);
    }

    public s6 a(int i2, r rVar, int[] iArr, Map<l1, ?> map) throws a {
        v6 v6Var = map == null ? null : (v6) map.get(l1.f17496j);
        if (v6Var != null) {
            v6Var.a(new u6((iArr[0] + iArr[1]) / 2.0f, i2));
        }
        StringBuilder sb = this.f17706a;
        sb.setLength(0);
        int iA = a(rVar, iArr, sb);
        if (v6Var != null) {
            v6Var.a(new u6(iA, i2));
        }
        int[] iArrA = a(rVar, iA);
        if (iArrA[0] - iA <= 1) {
            if (v6Var != null) {
                v6Var.a(new u6((iArrA[0] + iArrA[1]) / 2.0f, i2));
            }
            if (a(iArr, iArrA)) {
                int i3 = iArrA[1];
                if ((i3 - iArrA[0]) + i3 < rVar.e() && a(iArrA[0], i3, rVar)) {
                    String string = sb.toString();
                    if (string.length() >= 8) {
                        if (a(string)) {
                            float f2 = i2;
                            s6 s6Var = new s6(string, null, new u6[]{new u6(iArr[0], f2), new u6(iArrA[1], f2)}, a());
                            a(s6Var, iArrA, i2, rVar, map);
                            return s6Var;
                        }
                        throw a.a();
                    }
                    throw a.a();
                }
                throw a.a();
            }
            throw a.a();
        }
        throw a.a();
    }

    static int[] b(r rVar, int i2) throws a {
        int[] iArr = new int[f17701c.length];
        int[] iArrA = null;
        boolean zA = false;
        while (!zA) {
            int[] iArr2 = f17701c;
            Arrays.fill(iArr, 0, iArr2.length, 0);
            iArrA = a(rVar, i2, false, iArr2, iArr);
            int i3 = iArrA[0];
            int i4 = iArrA[1];
            int i5 = i3 - (i4 - i3);
            int i6 = i5 + 3;
            while (i5 <= i6 && (i5 < 0 || !(zA = rVar.a(i5, i3, false, true)))) {
                i5++;
            }
            i2 = i4;
        }
        return iArrA;
    }

    public static int b(CharSequence charSequence) throws a {
        int length = charSequence.length();
        int i2 = 0;
        for (int i3 = length - 1; i3 >= 0; i3 -= 2) {
            int iCharAt = charSequence.charAt(i3) - '0';
            if (iCharAt < 0 || iCharAt > 9) {
                throw a.a();
            }
            i2 += iCharAt;
        }
        int i4 = i2 * 3;
        for (int i5 = length - 2; i5 >= 0; i5 -= 2) {
            int iCharAt2 = charSequence.charAt(i5) - '0';
            if (iCharAt2 < 0 || iCharAt2 > 9) {
                throw a.a();
            }
            i4 += iCharAt2;
        }
        return (1000 - i4) % 10;
    }

    private void a(s6 s6Var, int[] iArr, int i2, r rVar, Map<l1, ?> map) throws a {
        int length;
        try {
            s6 s6VarA = this.f17707b.a(i2, rVar, iArr[1]);
            s6Var.a(s6VarA.j());
            length = s6VarA.k().length();
        } catch (a unused) {
            length = 0;
        }
        int[] iArr2 = map == null ? null : (int[]) map.get(l1.f17497k);
        if (iArr2 != null) {
            for (int i3 : iArr2) {
                if (length == i3) {
                    return;
                }
            }
            throw a.a();
        }
    }

    boolean a(String str) throws a {
        return a((CharSequence) str);
    }

    public static boolean a(CharSequence charSequence) throws a {
        int length = charSequence.length();
        if (length == 0) {
            return false;
        }
        int i2 = length - 1;
        return b(charSequence.subSequence(0, i2)) == Character.digit(charSequence.charAt(i2), 10);
    }

    int[] a(r rVar, int i2) throws a {
        return a(rVar, i2, false, f17701c);
    }

    static int[] a(r rVar, int i2, boolean z2, int[] iArr) throws a {
        return a(rVar, i2, z2, iArr, new int[iArr.length]);
    }

    private static int[] a(r rVar, int i2, boolean z2, int[] iArr, int[] iArr2) throws a {
        int iE = rVar.e();
        int iD = z2 ? rVar.d(i2) : rVar.c(i2);
        int length = iArr.length;
        boolean z3 = z2;
        int i3 = 0;
        int i4 = iD;
        while (iD < iE) {
            if (rVar.b(iD) != z3) {
                if (i3 >= 0 && i3 < iArr2.length) {
                    iArr2[i3] = iArr2[i3] + 1;
                } else {
                    throw a.a();
                }
            } else {
                if (i3 != length - 1) {
                    i3++;
                } else {
                    if (g5.a(iArr2, iArr, 0.8f) < 0.46f) {
                        return new int[]{i4, iD};
                    }
                    i4 += iArr2[0] + iArr2[1];
                    int i5 = i3 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i5);
                    iArr2[i5] = 0;
                    iArr2[i3] = 0;
                    i3--;
                }
                iArr2[i3] = 1;
                z3 = !z3;
            }
            iD++;
        }
        throw a.a();
    }

    static int a(r rVar, int[] iArr, int i2, int[][] iArr2) throws a {
        g5.a(rVar, i2, iArr);
        int length = iArr2.length;
        float f2 = 0.46f;
        int i3 = -1;
        for (int i4 = 0; i4 < length; i4++) {
            float fA = g5.a(iArr, iArr2[i4], 0.8f);
            if (fA < f2) {
                i3 = i4;
                f2 = fA;
            }
        }
        if (i3 >= 0) {
            return i3;
        }
        throw a.a();
    }
}
