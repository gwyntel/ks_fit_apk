package com.huawei.hms.scankit.p;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public final class c4 {

    /* renamed from: b, reason: collision with root package name */
    static final String[] f17066b = {"UPPER", "LOWER", "DIGIT", "MIXED", "PUNCT"};

    /* renamed from: c, reason: collision with root package name */
    static final int[][] f17067c = {new int[]{0, 327708, 327710, 327709, 656318}, new int[]{590318, 0, 327710, 327709, 656318}, new int[]{262158, 590300, 0, 590301, 932798}, new int[]{327709, 327708, 656318, 0, 327710}, new int[]{327711, 656380, 656382, 656381, 0}};

    /* renamed from: d, reason: collision with root package name */
    private static final int[][] f17068d;

    /* renamed from: e, reason: collision with root package name */
    static final int[][] f17069e;

    /* renamed from: a, reason: collision with root package name */
    private final byte[] f17070a;

    static class a<State> implements Comparator<b7> {
        a() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(b7 b7Var, b7 b7Var2) {
            return b7Var.b() - b7Var2.b();
        }
    }

    static {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 256);
        f17068d = iArr;
        iArr[0][32] = 1;
        for (int i2 = 65; i2 <= 90; i2++) {
            f17068d[0][i2] = i2 - 63;
        }
        f17068d[1][32] = 1;
        for (int i3 = 97; i3 <= 122; i3++) {
            f17068d[1][i3] = i3 - 95;
        }
        f17068d[2][32] = 1;
        for (int i4 = 48; i4 <= 57; i4++) {
            f17068d[2][i4] = i4 - 46;
        }
        int[] iArr2 = f17068d[2];
        iArr2[44] = 12;
        iArr2[46] = 13;
        int[] iArr3 = {0, 32, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 29, 30, 31, 64, 92, 94, 95, 96, 124, 126, 127};
        int i5 = 0;
        for (int i6 = 28; i5 < i6; i6 = 28) {
            f17068d[3][iArr3[i5]] = i5;
            i5++;
        }
        int[] iArr4 = {0, 13, 0, 0, 0, 0, 33, 39, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58, 59, 60, 61, 62, 63, 91, 93, 123, 125};
        for (int i7 = 0; i7 < 31; i7++) {
            int i8 = iArr4[i7];
            if (i8 > 0) {
                f17068d[4][i8] = i7;
            }
        }
        int[][] iArr5 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 6, 6);
        f17069e = iArr5;
        for (int[] iArr6 : iArr5) {
            Arrays.fill(iArr6, -1);
        }
        int[][] iArr7 = f17069e;
        if (w7.a(iArr7, 0) && w7.a(iArr7[0], 4)) {
            iArr7[0][4] = 0;
        }
        if (w7.a(iArr7, 1) && w7.a(iArr7[1], 4)) {
            iArr7[1][4] = 0;
        }
        if (w7.a(iArr7, 1) && w7.a(iArr7[1], 0)) {
            iArr7[1][0] = 28;
        }
        if (w7.a(iArr7, 3) && w7.a(iArr7[3], 4)) {
            iArr7[3][4] = 0;
        }
        if (w7.a(iArr7, 2) && w7.a(iArr7[2], 4)) {
            iArr7[2][4] = 0;
        }
        if (w7.a(iArr7, 2) && w7.a(iArr7[2], 0)) {
            iArr7[2][0] = 15;
        }
    }

    public c4(byte[] bArr) {
        this.f17070a = bArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.huawei.hms.scankit.p.r a() {
        /*
            r8 = this;
            com.huawei.hms.scankit.p.b7 r0 = com.huawei.hms.scankit.p.b7.f17026e
            java.util.List r0 = java.util.Collections.singletonList(r0)
            r1 = 0
            r2 = r1
        L8:
            byte[] r3 = r8.f17070a
            int r4 = r3.length
            if (r2 >= r4) goto L4d
            int r4 = r2 + 1
            int r5 = r3.length
            if (r4 >= r5) goto L15
            r5 = r3[r4]
            goto L16
        L15:
            r5 = r1
        L16:
            r3 = r3[r2]
            r6 = 13
            if (r3 == r6) goto L37
            r6 = 44
            r7 = 32
            if (r3 == r6) goto L33
            r6 = 46
            if (r3 == r6) goto L2f
            r6 = 58
            if (r3 == r6) goto L2b
            goto L3d
        L2b:
            if (r5 != r7) goto L3d
            r3 = 5
            goto L3e
        L2f:
            if (r5 != r7) goto L3d
            r3 = 3
            goto L3e
        L33:
            if (r5 != r7) goto L3d
            r3 = 4
            goto L3e
        L37:
            r3 = 10
            if (r5 != r3) goto L3d
            r3 = 2
            goto L3e
        L3d:
            r3 = r1
        L3e:
            if (r3 <= 0) goto L46
            java.util.Collection r0 = a(r0, r2, r3)
            r2 = r4
            goto L4a
        L46:
            java.util.Collection r0 = r8.a(r0, r2)
        L4a:
            int r2 = r2 + 1
            goto L8
        L4d:
            com.huawei.hms.scankit.p.c4$a r1 = new com.huawei.hms.scankit.p.c4$a
            r1.<init>()
            java.lang.Object r0 = java.util.Collections.min(r0, r1)
            com.huawei.hms.scankit.p.b7 r0 = (com.huawei.hms.scankit.p.b7) r0
            byte[] r1 = r8.f17070a
            com.huawei.hms.scankit.p.r r0 = r0.a(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.c4.a():com.huawei.hms.scankit.p.r");
    }

    private Collection<b7> a(Iterable<b7> iterable, int i2) {
        LinkedList linkedList = new LinkedList();
        Iterator<b7> it = iterable.iterator();
        while (it.hasNext()) {
            a(it.next(), i2, linkedList);
        }
        return a(linkedList);
    }

    private void a(b7 b7Var, int i2, Collection<b7> collection) {
        if (w7.a(this.f17070a, i2)) {
            char c2 = (char) (this.f17070a[i2] & 255);
            int[][] iArr = f17068d;
            boolean z2 = w7.a(iArr, b7Var.c()) && w7.a(iArr[b7Var.c()], (int) c2) && iArr[b7Var.c()][c2] > 0;
            b7 b7VarB = null;
            for (int i3 = 0; i3 <= 4; i3++) {
                int[][] iArr2 = f17068d;
                int i4 = (w7.a(iArr2, i3) && w7.a(iArr2[i3], (int) c2)) ? iArr2[i3][c2] : 0;
                if (i4 > 0) {
                    if (b7VarB == null) {
                        b7VarB = b7Var.b(i2);
                    }
                    if (!z2 || i3 == b7Var.c() || i3 == 2) {
                        collection.add(b7VarB.a(i3, i4));
                    }
                    if (!z2 && f17069e[b7Var.c()][i3] >= 0) {
                        collection.add(b7VarB.b(i3, i4));
                    }
                }
            }
            int[][] iArr3 = f17068d;
            if (w7.a(iArr3, b7Var.c()) && w7.a(iArr3[b7Var.c()], (int) c2)) {
                if (b7Var.a() > 0 || iArr3[b7Var.c()][c2] == 0) {
                    collection.add(b7Var.a(i2));
                }
            }
        }
    }

    private static Collection<b7> a(Iterable<b7> iterable, int i2, int i3) {
        LinkedList linkedList = new LinkedList();
        Iterator<b7> it = iterable.iterator();
        while (it.hasNext()) {
            a(it.next(), i2, i3, linkedList);
        }
        return a(linkedList);
    }

    private static void a(b7 b7Var, int i2, int i3, Collection<b7> collection) {
        b7 b7VarB = b7Var.b(i2);
        collection.add(b7VarB.a(4, i3));
        if (b7Var.c() != 4) {
            collection.add(b7VarB.b(4, i3));
        }
        if (i3 == 3 || i3 == 4) {
            collection.add(b7VarB.a(2, 16 - i3).a(2, 1));
        }
        if (b7Var.a() > 0) {
            collection.add(b7Var.a(i2).a(i2 + 1));
        }
    }

    private static Collection<b7> a(Iterable<b7> iterable) {
        LinkedList linkedList = new LinkedList();
        for (b7 b7Var : iterable) {
            Iterator it = linkedList.iterator();
            while (true) {
                if (it.hasNext()) {
                    b7 b7Var2 = (b7) it.next();
                    if (b7Var2.a(b7Var)) {
                        break;
                    }
                    if (b7Var.a(b7Var2)) {
                        it.remove();
                    }
                } else {
                    linkedList.add(b7Var);
                    break;
                }
            }
        }
        return linkedList;
    }
}
