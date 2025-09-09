package com.huawei.hms.scankit.p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class i3 {

    /* renamed from: h, reason: collision with root package name */
    private static boolean f17382h = false;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f17383i = false;

    /* renamed from: a, reason: collision with root package name */
    private final s f17385a;

    /* renamed from: e, reason: collision with root package name */
    private final v6 f17389e;

    /* renamed from: f, reason: collision with root package name */
    private static final int[] f17380f = {1, 3, 1, 1};

    /* renamed from: g, reason: collision with root package name */
    private static final int[] f17381g = {1, 1, 3, 1};

    /* renamed from: j, reason: collision with root package name */
    private static final d f17384j = new d();

    /* renamed from: b, reason: collision with root package name */
    private final List<e3> f17386b = new ArrayList();

    /* renamed from: d, reason: collision with root package name */
    private final int[] f17388d = new int[5];

    /* renamed from: c, reason: collision with root package name */
    private final List<e3> f17387c = new ArrayList();

    private static final class b implements Comparator<e3>, Serializable {

        /* renamed from: a, reason: collision with root package name */
        private final float f17390a;

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(e3 e3Var, e3 e3Var2) {
            int iCompare = Integer.compare(e3Var2.a(), e3Var.a());
            return iCompare == 0 ? Float.compare(Math.abs(e3Var.e() - this.f17390a), Math.abs(e3Var2.e() - this.f17390a)) : iCompare;
        }

        private b(float f2) {
            this.f17390a = f2;
        }
    }

    private static final class c implements Comparator<e3>, Serializable {
        private c() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(e3 e3Var, e3 e3Var2) {
            return Integer.compare(e3Var2.a(), e3Var.a());
        }
    }

    private static final class d implements Comparator<e3>, Serializable {
        private d() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(e3 e3Var, e3 e3Var2) {
            return Float.compare(e3Var.e(), e3Var2.e());
        }
    }

    private static final class e implements Comparator<e3>, Serializable {

        /* renamed from: a, reason: collision with root package name */
        private final float f17391a;

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(e3 e3Var, e3 e3Var2) {
            return Float.compare(Math.abs(e3Var2.e() - this.f17391a), Math.abs(e3Var.e() - this.f17391a));
        }

        private e(float f2) {
            this.f17391a = f2;
        }
    }

    private static final class f implements Comparator<e3>, Serializable {
        private f() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(e3 e3Var, e3 e3Var2) {
            return Float.compare(e3Var.b(), e3Var2.b());
        }
    }

    private static final class g implements Comparator<e3>, Serializable {
        private g() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(e3 e3Var, e3 e3Var2) {
            return Float.compare(e3Var.c(), e3Var2.c());
        }
    }

    public i3(s sVar, v6 v6Var) {
        this.f17385a = sVar;
        this.f17389e = v6Var;
    }

    protected static float a(int[] iArr, int[] iArr2, float f2) {
        int length = iArr.length;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            i2 += iArr[i4];
            i3 += iArr2[i4];
        }
        if (i2 < i3) {
            return Float.POSITIVE_INFINITY;
        }
        float f3 = i2;
        float f4 = f3 / i3;
        float f5 = f2 * f4;
        float f6 = 0.0f;
        for (int i5 = 0; i5 < length; i5++) {
            float f7 = iArr2[i5] * f4;
            float f8 = iArr[i5];
            float f9 = f8 > f7 ? f8 - f7 : f7 - f8;
            if (f9 > f5) {
                return Float.POSITIVE_INFINITY;
            }
            f6 += f9;
        }
        return f6 / f3;
    }

    private e3[] c() throws com.huawei.hms.scankit.p.a {
        if (this.f17386b.size() > 2) {
            try {
                return f();
            } catch (com.huawei.hms.scankit.p.a unused) {
                if (this.f17387c.size() <= 0) {
                    throw com.huawei.hms.scankit.p.a.a();
                }
                Collections.sort(this.f17387c, new c());
                int iMin = Math.min(3, this.f17387c.size());
                for (int i2 = 0; i2 < iMin; i2++) {
                    e3 e3Var = this.f17387c.get(i2);
                    float fE = e3Var.e();
                    float fC = e3Var.c();
                    float fB = e3Var.b();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= this.f17386b.size()) {
                            this.f17386b.add(e3Var);
                            break;
                        }
                        e3 e3Var2 = this.f17386b.get(i3);
                        if (e3Var2.b(fE, fC, fB)) {
                            this.f17386b.set(i3, e3Var2.a(fC, fB, fE, false));
                            break;
                        }
                        i3++;
                    }
                }
                return f();
            }
        }
        if (this.f17386b.size() == 2) {
            int i4 = this.f17386b.get(0).e() > this.f17386b.get(1).e() ? 0 : 1;
            if (Math.max(this.f17386b.get(0).e(), this.f17386b.get(1).e()) / Math.min(this.f17386b.get(0).e(), this.f17386b.get(1).e()) > 1.5d) {
                e3 e3Var3 = this.f17386b.get(i4);
                this.f17386b.clear();
                this.f17386b.add(e3Var3);
            }
        }
        if (this.f17386b.size() <= 1 && this.f17387c.size() >= 1) {
            for (int i5 = 0; i5 < this.f17386b.size(); i5++) {
                e3 e3Var4 = this.f17386b.get(i5);
                float fE2 = e3Var4.e();
                float fC2 = e3Var4.c();
                float fB2 = e3Var4.b();
                int i6 = 0;
                while (true) {
                    if (i6 >= this.f17387c.size()) {
                        this.f17387c.add(e3Var4);
                        break;
                    }
                    e3 e3Var5 = this.f17387c.get(i6);
                    if (e3Var5.b(fE2, fC2, fB2)) {
                        this.f17387c.set(i6, e3Var4.a(e3Var5.c(), e3Var5.b(), e3Var5.e(), false));
                        break;
                    }
                    i6++;
                }
            }
            this.f17386b.clear();
            this.f17386b.addAll(this.f17387c);
            this.f17387c.clear();
        }
        if (this.f17386b.size() == 2) {
            try {
                return g();
            } catch (com.huawei.hms.scankit.p.a unused2) {
                return a();
            }
        }
        if (this.f17386b.size() > 1) {
            return f();
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private int[] d() {
        a(this.f17388d);
        return this.f17388d;
    }

    private static void e() {
        f17382h = false;
        f17383i = false;
    }

    private e3[] f() throws com.huawei.hms.scankit.p.a {
        int size = this.f17386b.size();
        if (size < 3) {
            throw com.huawei.hms.scankit.p.a.a();
        }
        e3[] e3VarArr = new e3[3];
        if (size == 3) {
            e3VarArr[0] = this.f17386b.get(0);
            e3VarArr[1] = this.f17386b.get(1);
            e3 e3Var = this.f17386b.get(2);
            e3VarArr[2] = e3Var;
            if (b(e3VarArr[0], e3VarArr[1], e3Var, new float[3])) {
                return e3VarArr;
            }
            throw com.huawei.hms.scankit.p.a.a();
        }
        Collections.sort(this.f17386b, new c());
        if (this.f17386b.get(2).a() - this.f17386b.get(3).a() > 1 && this.f17386b.get(2).a() > 1) {
            e3VarArr[0] = this.f17386b.get(0);
            e3VarArr[1] = this.f17386b.get(1);
            e3VarArr[2] = this.f17386b.get(2);
            return e3VarArr;
        }
        float fE = 0.0f;
        if (this.f17386b.get(3).a() > 1) {
            float fE2 = 0.0f;
            for (int i2 = 0; i2 < 4; i2++) {
                fE2 += this.f17386b.get(i2).e();
            }
            float f2 = fE2 / 4.0f;
            int i3 = 0;
            for (int i4 = 0; i4 < 4; i4++) {
                float fAbs = Math.abs(this.f17386b.get(i4).e() - f2);
                if (fAbs > fE) {
                    i3 = i4;
                    fE = fAbs;
                }
            }
            if (i3 == 0) {
                e3VarArr[0] = this.f17386b.get(1);
                e3VarArr[1] = this.f17386b.get(2);
                e3VarArr[2] = this.f17386b.get(3);
            } else if (i3 == 1) {
                e3VarArr[0] = this.f17386b.get(0);
                e3VarArr[1] = this.f17386b.get(2);
                e3VarArr[2] = this.f17386b.get(3);
            } else if (i3 != 2) {
                e3VarArr[0] = this.f17386b.get(0);
                e3VarArr[1] = this.f17386b.get(1);
                e3VarArr[2] = this.f17386b.get(2);
            } else {
                e3VarArr[0] = this.f17386b.get(0);
                e3VarArr[1] = this.f17386b.get(1);
                e3VarArr[2] = this.f17386b.get(3);
            }
            if (b(e3VarArr[0], e3VarArr[1], e3VarArr[2], new float[3])) {
                return e3VarArr;
            }
            throw com.huawei.hms.scankit.p.a.a();
        }
        if (this.f17386b.get(1).a() > 1 && this.f17386b.get(2).a() == 1) {
            ArrayList arrayList = new ArrayList();
            float fE3 = (this.f17386b.get(0).e() + this.f17386b.get(1).e()) / 2.0f;
            for (int i5 = 2; i5 < size; i5++) {
                if (Math.abs(this.f17386b.get(i5).e() - fE3) < fE3 * 0.5d) {
                    arrayList.add(this.f17386b.get(i5));
                }
            }
            int i6 = 0;
            for (int i7 = 0; i7 < arrayList.size(); i7++) {
                float[] fArr = new float[3];
                if (b(this.f17386b.get(0), this.f17386b.get(1), (e3) arrayList.get(i7), fArr)) {
                    float f3 = fArr[0];
                    if (f3 >= fE) {
                        i6 = i7;
                        fE = f3;
                    }
                }
            }
            e3VarArr[0] = this.f17386b.get(0);
            e3VarArr[1] = this.f17386b.get(1);
            if (i6 >= arrayList.size()) {
                throw com.huawei.hms.scankit.p.a.a();
            }
            e3VarArr[2] = (e3) arrayList.get(i6);
            return e3VarArr;
        }
        if (size > 3) {
            float f4 = 0.0f;
            float f5 = 0.0f;
            for (int i8 = 0; i8 < size; i8++) {
                float fE4 = this.f17386b.get(i8).e();
                f4 += fE4;
                f5 += fE4 * fE4;
            }
            float f6 = f4 / size;
            float fSqrt = (float) Math.sqrt((f5 / r1) - (f6 * f6));
            Collections.sort(this.f17386b, new e(f6));
            float fMax = Math.max(0.5f * f6, fSqrt);
            int i9 = 0;
            while (i9 < this.f17386b.size() && this.f17386b.size() > 3) {
                if (Math.abs(this.f17386b.get(i9).e() - f6) > fMax) {
                    this.f17386b.remove(i9);
                    i9--;
                }
                i9++;
            }
        }
        if (this.f17386b.size() > 15) {
            Collections.sort(this.f17386b, new c());
            List<e3> list = this.f17386b;
            list.subList(15, list.size()).clear();
        } else if (this.f17386b.size() > 12) {
            Collections.sort(this.f17386b, new c());
            List<e3> list2 = this.f17386b;
            list2.subList(12, list2.size()).clear();
        }
        if (this.f17386b.size() >= 6) {
            Collections.sort(this.f17386b, new f());
            List<e3> list3 = this.f17386b;
            list3.subList(4, list3.size() - 2).clear();
            Collections.sort(this.f17386b, new g());
            this.f17386b.subList(1, 3).clear();
            Collections.sort(this.f17386b, new g());
            List<e3> list4 = this.f17386b;
            list4.subList(list4.size() - 1, this.f17386b.size()).clear();
        } else if (this.f17386b.size() > 3) {
            for (int i10 = 0; i10 < this.f17386b.size(); i10++) {
                fE += this.f17386b.get(i10).e();
            }
            Collections.sort(this.f17386b, new b(fE / this.f17386b.size()));
            List<e3> list5 = this.f17386b;
            list5.subList(3, list5.size()).clear();
        }
        e3VarArr[0] = this.f17386b.get(0);
        e3VarArr[1] = this.f17386b.get(1);
        e3 e3Var2 = this.f17386b.get(2);
        e3VarArr[2] = e3Var2;
        if (b(e3VarArr[0], e3VarArr[1], e3Var2, new float[3])) {
            return e3VarArr;
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    private e3[] g() throws com.huawei.hms.scankit.p.a {
        e3 e3Var;
        e3 e3Var2;
        int i2 = 3;
        char c2 = 2;
        int i3 = 0;
        e3 e3Var3 = this.f17386b.get(0);
        int i4 = 1;
        e3 e3Var4 = this.f17386b.get(1);
        e3 e3Var5 = this.f17386b.get(1);
        double dA = a(e3Var3, e3Var4);
        float fMin = Math.min(e3Var3.e(), e3Var4.e());
        Collections.sort(this.f17386b, new e(fMin));
        int i5 = 0;
        double d2 = Double.MAX_VALUE;
        while (i5 < this.f17387c.size()) {
            e3 e3Var6 = this.f17387c.get(i5);
            float fE = e3Var6.e();
            if (fE > 1.25f * fMin || fE < 0.75f * fMin) {
                e3Var = e3Var3;
                e3Var2 = e3Var4;
            } else {
                double dA2 = a(e3Var4, e3Var6);
                double dA3 = a(e3Var3, e3Var6);
                double[] dArr = new double[i2];
                dArr[i3] = dA;
                dArr[i4] = dA2;
                dArr[c2] = dA3;
                Arrays.sort(dArr);
                double dAbs = Math.abs(dArr[c2] - (dArr[i4] * 2.0d)) + Math.abs(dArr[c2] - (dArr[i3] * 2.0d));
                double d3 = dArr[c2];
                double d4 = dAbs / d3;
                double d5 = dArr[i4];
                double d6 = dArr[i3];
                double dSqrt = ((d5 + d6) - d3) / ((Math.sqrt(d6) * 2.0d) * Math.sqrt(dArr[i4]));
                if (!r3.f17721h || Math.abs(dSqrt) >= 0.25d) {
                    e3Var = e3Var3;
                    e3Var2 = e3Var4;
                } else {
                    int i6 = (e3Var3.b() >= ((float) this.f17385a.e()) * 0.3f || e3Var4.b() <= ((float) this.f17385a.e()) * 0.7f) ? i3 : i4;
                    int i7 = (e3Var4.b() >= ((float) this.f17385a.e()) * 0.3f || e3Var3.b() <= ((float) this.f17385a.e()) * 0.7f) ? 0 : i4;
                    boolean z2 = e3Var3.c() < ((float) this.f17385a.c()) * 0.3f && e3Var4.c() > ((float) this.f17385a.c()) * 0.7f;
                    boolean z3 = e3Var4.c() < ((float) this.f17385a.c()) * 0.3f && e3Var3.c() > ((float) this.f17385a.c()) * 0.7f;
                    e3Var = e3Var3;
                    boolean z4 = e3Var6.b() < ((float) this.f17385a.e()) * 0.3f || e3Var6.b() > ((float) this.f17385a.e()) * 0.7f;
                    e3Var2 = e3Var4;
                    boolean z5 = e3Var6.c() < ((float) this.f17385a.c()) * 0.3f || e3Var6.c() > ((float) this.f17385a.c()) * 0.7f;
                    if (((i6 != 0 || i7 != 0) && !z4) || ((z2 || z3) && !z5)) {
                    }
                }
                if (d4 < d2 && Math.abs(dSqrt) < 0.25d) {
                    e3Var5 = e3Var6;
                    d2 = d4;
                }
            }
            i5++;
            i4 = 1;
            e3Var3 = e3Var;
            e3Var4 = e3Var2;
            i2 = 3;
            c2 = 2;
            i3 = 0;
        }
        int i8 = i4;
        if (d2 == Double.MAX_VALUE) {
            throw com.huawei.hms.scankit.p.a.a();
        }
        this.f17386b.add(e3Var5);
        e3 e3Var7 = this.f17386b.get(0);
        e3 e3Var8 = this.f17386b.get(i8);
        e3 e3Var9 = this.f17386b.get(2);
        e3[] e3VarArr = new e3[3];
        e3VarArr[0] = e3Var7;
        e3VarArr[i8] = e3Var8;
        e3VarArr[2] = e3Var9;
        return e3VarArr;
    }

    final k3 b() throws com.huawei.hms.scankit.p.a {
        int iC = this.f17385a.c();
        int iE = this.f17385a.e();
        int i2 = (iC * 3) / 388;
        if (i2 < 3) {
            i2 = 3;
        }
        if (r3.f17727n) {
            i2 = 2;
        }
        a(i2, iC, iE);
        e3[] e3VarArrC = c();
        if (e3VarArrC == null) {
            throw com.huawei.hms.scankit.p.a.a();
        }
        u6.a(e3VarArrC);
        if ((this.f17385a.c() * this.f17385a.e()) / (Math.sqrt(a(e3VarArrC[0], e3VarArrC[1])) * Math.sqrt(a(e3VarArrC[1], e3VarArrC[2]))) <= 900.0d) {
            return new k3(e3VarArrC);
        }
        throw com.huawei.hms.scankit.p.a.a();
    }

    protected final void d(int[] iArr) {
        iArr[0] = iArr[2];
        iArr[1] = iArr[3];
        iArr[2] = iArr[4];
        iArr[3] = 1;
        iArr[4] = 0;
    }

    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    private void a(int r11, int r12, int r13) {
        /*
            r10 = this;
            r0 = 5
            int[] r1 = new int[r0]
            int r2 = r11 + (-1)
        L5:
            if (r2 >= r12) goto L5e
            r10.a(r1)
            int[] r3 = new int[r0]
            r4 = 0
            r3[r4] = r4
            r5 = 1
            r3[r5] = r2
            r6 = 2
            r3[r6] = r4
            r7 = 3
            r3[r7] = r13
            r7 = 4
            r3[r7] = r11
            r8 = r4
        L1c:
            if (r8 >= r13) goto L39
            r3[r6] = r8
            com.huawei.hms.scankit.p.s r9 = r10.f17385a
            boolean r9 = r9.b(r8, r2)
            if (r9 == 0) goto L2c
            r10.a(r1, r3)
            goto L33
        L2c:
            boolean r9 = r10.b(r1, r3)
            if (r9 == 0) goto L33
            goto L37
        L33:
            r8 = r3[r6]
            r11 = r3[r7]
        L37:
            int r8 = r8 + r5
            goto L1c
        L39:
            boolean r5 = b(r1)
            if (r5 == 0) goto L4e
            r3 = r3[r4]
            r5 = r13
        L42:
            if (r3 <= r6) goto L4a
            r7 = r1[r3]
            int r5 = r5 - r7
            int r3 = r3 + (-1)
            goto L42
        L4a:
            r10.b(r1, r2, r5)
            goto L4f
        L4e:
            r5 = r13
        L4f:
            boolean r3 = a(r1, r4)
            if (r3 == 0) goto L5c
            boolean r3 = r10.a(r1, r2, r5, r4)
            if (r3 == 0) goto L5c
            r11 = r6
        L5c:
            int r2 = r2 + r11
            goto L5
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.i3.a(int, int, int):void");
    }

    private boolean d(int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        int i7;
        s sVar = this.f17385a;
        while (i2 >= 0 && !sVar.b(i3, i2) && (i7 = iArr[3]) <= i4) {
            iArr[3] = i7 + 1;
            i2--;
        }
        if (i2 >= 0 && iArr[3] <= i4) {
            while (i2 >= 0 && sVar.b(i3, i2) && (i6 = iArr[2]) <= i4) {
                iArr[2] = i6 + 1;
                i2--;
            }
            if (i2 >= 0 && iArr[2] <= i4) {
                while (i2 >= 0 && !sVar.b(i3, i2)) {
                    int i8 = iArr[1];
                    if (i8 > i4) {
                        break;
                    }
                    iArr[1] = i8 + 1;
                    i2--;
                }
                if (i2 >= 0 && iArr[1] <= i4) {
                    while (i2 >= 0 && sVar.b(i3, i2) && (i5 = iArr[0]) <= i4) {
                        iArr[0] = i5 + 1;
                        i2--;
                    }
                    return iArr[0] > i4;
                }
            }
        }
        return true;
    }

    private boolean b(int[] iArr, int[] iArr2) {
        int i2 = iArr2[0];
        if ((i2 & 1) != 0) {
            iArr[i2] = iArr[i2] + 1;
        } else if (i2 == 4) {
            if (a(iArr, false)) {
                boolean zA = a(iArr, iArr2[1], iArr2[2], false);
                if (zA) {
                    iArr2[4] = 2;
                }
                if (!zA) {
                    zA = a(iArr, iArr2[1], iArr2[2]);
                }
                if (zA) {
                    iArr2[0] = 0;
                    a(iArr);
                    return true;
                }
            }
            if (b(iArr)) {
                int i3 = iArr2[2];
                for (int i4 = iArr2[0]; i4 > 2; i4--) {
                    i3 -= iArr[i4];
                }
                if (b(iArr, iArr2[1], i3)) {
                    d(iArr);
                    iArr2[0] = 3;
                    return true;
                }
            }
            if (r3.f17721h && a(iArr, true) && a(iArr, iArr2[1], iArr2[2], true)) {
                iArr2[0] = 0;
                a(iArr);
                return true;
            }
            d(iArr);
            iArr2[0] = 3;
        } else {
            int i5 = i2 + 1;
            iArr2[0] = i5;
            iArr[i5] = iArr[i5] + 1;
        }
        return false;
    }

    private void a(int[] iArr, int[] iArr2) {
        int i2 = iArr2[0];
        if ((i2 & 1) == 1) {
            iArr2[0] = i2 + 1;
        }
        int i3 = iArr2[0];
        if (i3 >= 0 && i3 < iArr.length) {
            iArr[i3] = iArr[i3] + 1;
        }
        if (iArr2[2] == iArr2[3] - 1 && iArr2[0] == 4) {
            if (a(iArr, false)) {
                boolean zA = a(iArr, iArr2[1], iArr2[2], false);
                if (zA) {
                    iArr2[4] = 2;
                }
                if (!zA) {
                    zA = a(iArr, iArr2[1], iArr2[2]);
                }
                if (zA) {
                    iArr2[0] = 0;
                    a(iArr);
                    while (iArr2[2] < this.f17385a.e() && !this.f17385a.b(iArr2[2], iArr2[1])) {
                        iArr2[2] = iArr2[2] + 1;
                    }
                }
            }
            if (r3.f17721h && a(iArr, true) && a(iArr, iArr2[1], iArr2[2], true)) {
                iArr2[0] = 0;
                a(iArr);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0088, code lost:
    
        if ((r11 * 3) > r13) goto L48;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float b(int r10, int r11, int r12, int r13, boolean r14) {
        /*
            r9 = this;
            com.huawei.hms.scankit.p.s r0 = r9.f17385a
            int r1 = r0.c()
            int[] r2 = r9.d()
            boolean r3 = r9.b(r10, r11, r12, r2)
            r4 = 2143289344(0x7fc00000, float:NaN)
            if (r3 == 0) goto L13
            return r4
        L13:
            r3 = 1
            int r10 = r10 + r3
        L15:
            r5 = 2
            if (r10 >= r1) goto L26
            boolean r6 = r0.b(r11, r10)
            if (r6 == 0) goto L26
            r6 = r2[r5]
            int r6 = r6 + r3
            r2[r5] = r6
            int r10 = r10 + 1
            goto L15
        L26:
            if (r10 != r1) goto L29
            return r4
        L29:
            r6 = 3
            if (r10 >= r1) goto L3d
            boolean r7 = r0.b(r11, r10)
            if (r7 != 0) goto L3d
            r7 = r2[r6]
            if (r7 >= r12) goto L3d
            int r7 = r7 + 1
            r2[r6] = r7
            int r10 = r10 + 1
            goto L29
        L3d:
            if (r10 == r1) goto L90
            r7 = r2[r6]
            if (r7 < r12) goto L44
            goto L90
        L44:
            r7 = 4
            if (r10 >= r1) goto L58
            boolean r8 = r0.b(r11, r10)
            if (r8 == 0) goto L58
            r8 = r2[r7]
            if (r8 >= r12) goto L58
            int r8 = r8 + 1
            r2[r7] = r8
            int r10 = r10 + 1
            goto L44
        L58:
            boolean r11 = a(r2, r14)
            if (r11 != 0) goto L5f
            return r4
        L5f:
            r11 = 0
            r11 = r2[r11]
            r12 = r2[r3]
            int r11 = r11 + r12
            r12 = r2[r5]
            int r11 = r11 + r12
            r12 = r2[r6]
            int r11 = r11 + r12
            r12 = r2[r7]
            int r11 = r11 + r12
            if (r14 == 0) goto L83
            int r11 = r11 - r13
            int r11 = java.lang.Math.abs(r11)
            int r11 = r11 * 5
            int r13 = r13 * r6
            if (r11 < r13) goto L8b
            boolean r11 = com.huawei.hms.scankit.p.i3.f17383i
            if (r11 != 0) goto L8b
            boolean r11 = com.huawei.hms.scankit.p.i3.f17382h
            if (r11 != 0) goto L8b
            return r4
        L83:
            int r12 = r13 * 3
            if (r11 >= r12) goto L90
            int r11 = r11 * r6
            if (r11 > r13) goto L8b
            goto L90
        L8b:
            float r10 = a(r2, r10)
            return r10
        L90:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.i3.b(int, int, int, int, boolean):float");
    }

    private static float a(int[] iArr, int i2) {
        return ((i2 - iArr[4]) - iArr[3]) - (iArr[2] / 2.0f);
    }

    protected static boolean a(int[] iArr, boolean z2) {
        float f2;
        float f3;
        e();
        int i2 = 0;
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = iArr[i3];
            if (i4 == 0) {
                return false;
            }
            i2 += i4;
        }
        if (i2 < 7) {
            return false;
        }
        if (z2 && r3.f17721h) {
            f3 = 0.75f;
            f2 = 1.0f;
        } else {
            f2 = 3.0f;
            f3 = 0.5f;
        }
        float f4 = i2 / 7.0f;
        float f5 = f3 * f4;
        if (Math.abs(f4 - iArr[0]) < f5 && Math.abs(f4 - iArr[1]) < f5 && Math.abs((3.0f * f4) - iArr[2]) < f2 * f5 && Math.abs(f4 - iArr[3]) < f5 && Math.abs(f4 - iArr[4]) < f5) {
            return true;
        }
        if (!z2) {
            return false;
        }
        int[] iArr2 = new int[iArr.length - 1];
        int i5 = 0;
        while (i5 < iArr.length - 1) {
            int i6 = i5 + 1;
            iArr2[i5] = iArr[i6];
            i5 = i6;
        }
        int[] iArr3 = new int[iArr.length - 1];
        for (int i7 = 0; i7 < iArr.length - 1; i7++) {
            iArr3[i7] = iArr[i7];
        }
        float fA = a(iArr2, f17380f, 0.5f);
        float fA2 = a(iArr3, f17381g, 0.5f);
        boolean z3 = fA < 0.3f;
        f17382h = z3;
        boolean z4 = fA2 < 0.3f;
        f17383i = z4;
        return z3 || z4;
    }

    protected static boolean c(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = iArr[i3];
            if (i4 == 0) {
                return false;
            }
            i2 += i4;
        }
        if (i2 < 7) {
            return false;
        }
        float f2 = i2 / 7.0f;
        float f3 = 0.75f * f2;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs(f2 - ((float) iArr[1])) < f3 && Math.abs((f2 * 3.0f) - ((float) iArr[2])) < 3.0f * f3 && Math.abs(f2 - ((float) iArr[3])) < f3 && Math.abs(f2 - ((float) iArr[4])) < f3;
    }

    private boolean c(int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        s sVar = this.f17385a;
        while (i2 >= 0 && !sVar.b(i3, i2) && (i6 = iArr[1]) <= i4) {
            iArr[1] = i6 + 1;
            i2--;
        }
        if (i2 < 0 || iArr[1] > i4) {
            return true;
        }
        while (i2 >= 0 && sVar.b(i3, i2) && (i5 = iArr[0]) <= i4) {
            iArr[0] = i5 + 1;
            i2--;
        }
        return iArr[0] > i4;
    }

    private boolean b(int i2, int i3, int i4, int[] iArr) {
        boolean z2;
        int i5;
        s sVar = this.f17385a;
        while (true) {
            z2 = true;
            if (i2 < 0 || !sVar.b(i3, i2)) {
                break;
            }
            iArr[2] = iArr[2] + 1;
            i2--;
        }
        if (i2 < 0) {
            return true;
        }
        while (i2 >= 0 && !sVar.b(i3, i2)) {
            int i6 = iArr[1];
            if (i6 > i4) {
                break;
            }
            iArr[1] = i6 + 1;
            i2--;
        }
        if (i2 >= 0 && iArr[1] <= i4) {
            while (true) {
                z2 = false;
                if (i2 < 0 || !sVar.b(i3, i2) || (i5 = iArr[0]) > i4) {
                    break;
                }
                iArr[0] = i5 + 1;
                i2--;
            }
        }
        return z2;
    }

    protected final void a(int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = 0;
        }
    }

    private boolean a(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int[] iArrD = d();
        int i7 = 0;
        while (i2 >= i7 && i3 >= i7 && this.f17385a.b(i3 - i7, i2 - i7)) {
            iArrD[2] = iArrD[2] + 1;
            i7++;
        }
        if (iArrD[2] == 0) {
            return false;
        }
        while (i2 >= i7 && i3 >= i7 && !this.f17385a.b(i3 - i7, i2 - i7)) {
            iArrD[1] = iArrD[1] + 1;
            i7++;
        }
        if (iArrD[1] == 0) {
            return false;
        }
        while (i2 >= i7 && i3 >= i7 && this.f17385a.b(i3 - i7, i2 - i7)) {
            iArrD[0] = iArrD[0] + 1;
            i7++;
        }
        if (iArrD[0] == 0) {
            return false;
        }
        int iC = this.f17385a.c();
        int iE = this.f17385a.e();
        int i8 = 1;
        while (true) {
            int i9 = i2 + i8;
            if (i9 >= iC || (i6 = i3 + i8) >= iE || !this.f17385a.b(i6, i9)) {
                break;
            }
            iArrD[2] = iArrD[2] + 1;
            i8++;
        }
        while (true) {
            int i10 = i2 + i8;
            if (i10 >= iC || (i5 = i3 + i8) >= iE || this.f17385a.b(i5, i10)) {
                break;
            }
            iArrD[3] = iArrD[3] + 1;
            i8++;
        }
        if (iArrD[3] == 0) {
            return false;
        }
        while (true) {
            int i11 = i2 + i8;
            if (i11 >= iC || (i4 = i3 + i8) >= iE || !this.f17385a.b(i4, i11)) {
                break;
            }
            iArrD[4] = iArrD[4] + 1;
            i8++;
        }
        if (iArrD[4] == 0) {
            return false;
        }
        return c(iArrD);
    }

    private static boolean b(e3 e3Var, e3 e3Var2, e3 e3Var3, float[] fArr) {
        a(e3Var, e3Var2, e3Var3, fArr);
        float fSqrt = (float) Math.sqrt(fArr[1]);
        float fSqrt2 = (float) Math.sqrt(fArr[2]);
        float fSqrt3 = (float) Math.sqrt(fArr[0]);
        if (Math.min(Math.min(fSqrt, fSqrt2), fSqrt3) <= Math.max(Math.max(e3Var.e(), e3Var2.e()), e3Var3.e()) * 7.0f) {
            return false;
        }
        float f2 = fArr[1];
        float f3 = fArr[2];
        float f4 = fArr[0];
        float f5 = ((f2 + f3) - f4) / ((fSqrt * 2.0f) * fSqrt2);
        float f6 = fSqrt3 * 2.0f;
        float f7 = ((f4 + f2) - f3) / (fSqrt * f6);
        float f8 = ((f4 + f3) - f2) / (f6 * fSqrt2);
        return Math.abs(f5) <= 0.45f && f7 >= 0.2588f && f7 <= 0.94f && f8 >= 0.2588f && f8 <= 0.94f;
    }

    protected static boolean b(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 3; i3++) {
            int i4 = iArr[i3];
            if (i4 == 0) {
                return false;
            }
            i2 += i4;
        }
        if (i2 < 7) {
            return false;
        }
        float f2 = i2 / 7.0f;
        float f3 = 0.5f * f2;
        return Math.abs(f2 - ((float) iArr[0])) < f3 && Math.abs((5.0f * f2) - ((float) iArr[1])) < f3 && Math.abs(f2 - ((float) iArr[2])) < f3;
    }

    private float b(int i2, int i3, int i4, int i5) {
        int i6;
        s sVar = this.f17385a;
        int iC = sVar.c();
        int[] iArrD = d();
        if (d(i2, i3, i4, iArrD)) {
            return Float.NaN;
        }
        int i7 = i2 + 1;
        while (i7 < iC && !sVar.b(i3, i7) && (i6 = iArrD[3]) <= i4) {
            iArrD[3] = i6 + 1;
            i7++;
        }
        if (i7 < 0 || iArrD[3] > i4) {
            return Float.NaN;
        }
        while (i7 < iC && sVar.b(i3, i7)) {
            iArrD[4] = iArrD[4] + 1;
            i7++;
        }
        int i8 = iArrD[4];
        if (i8 <= i4 && Math.abs(((((iArrD[0] + iArrD[1]) + iArrD[2]) + iArrD[3]) + i8) - i5) * 5 < i5 * 2 && a(iArrD, true)) {
            return a(iArrD, i7);
        }
        return Float.NaN;
    }

    private float a(int i2, int i3, int i4, int i5, boolean z2) {
        int i6;
        int i7;
        s sVar = this.f17385a;
        int iE = sVar.e();
        int[] iArrD = d();
        if (a(i2, i3, i4, iArrD)) {
            return Float.NaN;
        }
        int i8 = i2 + 1;
        while (i8 < iE && sVar.b(i8, i3)) {
            iArrD[2] = iArrD[2] + 1;
            i8++;
        }
        if (i8 == iE) {
            return Float.NaN;
        }
        while (i8 < iE && !sVar.b(i8, i3) && (i7 = iArrD[3]) < i4) {
            iArrD[3] = i7 + 1;
            i8++;
        }
        if (i8 == iE || iArrD[3] >= i4) {
            return Float.NaN;
        }
        while (i8 < iE && sVar.b(i8, i3) && (i6 = iArrD[4]) < i4) {
            iArrD[4] = i6 + 1;
            i8++;
        }
        if (!a(iArrD, z2)) {
            return Float.NaN;
        }
        if (Math.abs(((((iArrD[0] + iArrD[1]) + iArrD[2]) + iArrD[3]) + iArrD[4]) - i5) * 5 < i5 || f17383i || f17382h) {
            return a(iArrD, i8);
        }
        return Float.NaN;
    }

    protected final boolean b(int[] iArr, int i2, int i3) {
        int i4 = iArr[0];
        int i5 = iArr[1];
        int i6 = i4 + i5 + iArr[2];
        float f2 = i3 - (i6 / 2);
        int i7 = (int) f2;
        float fA = a(i2, i7, i5, i6);
        if (Float.isNaN(fA)) {
            fA = a(i2, (int) (f2 - ((r9 * 2) / 5)), (int) (((r9 * 2) / 5) + f2), iArr[1], i6);
            if (Float.isNaN(fA) && r3.f17721h) {
                fA = b(i2, i7, iArr[1], i6);
            }
        }
        if (Float.isNaN(fA)) {
            return false;
        }
        return a(false, fA, f2, i6 / 7.0f);
    }

    private boolean a(int i2, int i3, int i4, int[] iArr) {
        boolean z2;
        int i5;
        s sVar = this.f17385a;
        while (true) {
            z2 = true;
            if (i2 < 0 || !sVar.b(i2, i3)) {
                break;
            }
            iArr[2] = iArr[2] + 1;
            i2--;
        }
        if (i2 < 0) {
            return true;
        }
        while (i2 >= 0 && !sVar.b(i2, i3)) {
            int i6 = iArr[1];
            if (i6 > i4) {
                break;
            }
            iArr[1] = i6 + 1;
            i2--;
        }
        if (i2 >= 0 && iArr[1] <= i4) {
            while (true) {
                z2 = false;
                if (i2 < 0 || !sVar.b(i2, i3) || (i5 = iArr[0]) > i4) {
                    break;
                }
                iArr[0] = i5 + 1;
                i2--;
            }
        }
        return z2;
    }

    protected final boolean a(int[] iArr, int i2, int i3, boolean z2) {
        int i4;
        int i5;
        int i6 = 0;
        int i7 = iArr[0];
        int i8 = iArr[1];
        int i9 = iArr[2];
        int i10 = iArr[3];
        int i11 = iArr[4];
        int i12 = i7 + i8 + i9 + i10 + i11;
        boolean z3 = f17382h;
        boolean z4 = f17383i;
        if (z3) {
            i4 = i3;
            i5 = i8 + i8 + i9 + i10 + i11;
        } else if (z4) {
            i5 = i7 + i8 + i9 + i10 + i10;
            i4 = i3;
        } else {
            i4 = i3;
            i5 = i12;
        }
        int iA = (int) a(iArr, i4);
        float fB = b(i2, iA, iArr[2], i5, z2);
        boolean z5 = f17382h;
        boolean z6 = f17383i;
        if (!Float.isNaN(fB)) {
            float f2 = i5 / 7.0f;
            int i13 = (int) fB;
            float fA = a(iA, i13, iArr[2], i5, z2);
            if (!Float.isNaN(fA) && (a(i13, (int) fA) || (z2 && (z3 || z4 || z5 || z6)))) {
                if (z2) {
                    return a(false, fB, fA, f2);
                }
                while (true) {
                    if (i6 < this.f17386b.size()) {
                        e3 e3Var = this.f17386b.get(i6);
                        if (e3Var.b(f2, fB, fA)) {
                            this.f17386b.set(i6, e3Var.a(fB, fA, f2, true));
                            break;
                        }
                        i6++;
                    } else {
                        e3 e3Var2 = new e3(fA, fB, f2, true);
                        this.f17386b.add(e3Var2);
                        v6 v6Var = this.f17389e;
                        if (v6Var != null) {
                            v6Var.a(e3Var2);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static double a(e3 e3Var, e3 e3Var2) {
        double dB = e3Var.b() - e3Var2.b();
        double dC = e3Var.c() - e3Var2.c();
        return (dB * dB) + (dC * dC);
    }

    private static void a(e3 e3Var, e3 e3Var2, e3 e3Var3, float[] fArr) {
        float fB = e3Var.b() - e3Var2.b();
        float fC = e3Var.c() - e3Var2.c();
        float f2 = (fB * fB) + (fC * fC);
        float fB2 = e3Var.b() - e3Var3.b();
        float fC2 = e3Var.c() - e3Var3.c();
        float f3 = (fB2 * fB2) + (fC2 * fC2);
        float fB3 = e3Var2.b() - e3Var3.b();
        float fC3 = e3Var2.c() - e3Var3.c();
        float f4 = (fB3 * fB3) + (fC3 * fC3);
        if (f2 > f4 && f2 > f3) {
            fArr[0] = f2;
            fArr[1] = f3;
            fArr[2] = f4;
        } else if (f4 > f2 && f4 > f3) {
            fArr[0] = f4;
            fArr[1] = f2;
            fArr[2] = f3;
        } else {
            fArr[0] = f3;
            fArr[1] = f2;
            fArr[2] = f4;
        }
    }

    private float a(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        s sVar = this.f17385a;
        int iC = sVar.c();
        int[] iArrD = d();
        if (c(i2, i3, i4, iArrD)) {
            return Float.NaN;
        }
        int i8 = i2 + 1;
        while (i8 < iC && !sVar.b(i3, i8)) {
            int i9 = iArrD[1];
            if (i9 > i4) {
                break;
            }
            iArrD[1] = i9 + 1;
            i8++;
        }
        if (i8 < 0 || iArrD[1] > i4) {
            return Float.NaN;
        }
        while (i8 < iC && sVar.b(i3, i8)) {
            iArrD[2] = iArrD[2] + 1;
            i8++;
        }
        if (i8 == iC) {
            return Float.NaN;
        }
        while (i8 < iC && !sVar.b(i3, i8) && (i7 = iArrD[3]) < i4) {
            iArrD[3] = i7 + 1;
            i8++;
        }
        if (i8 == iC || iArrD[3] >= i4) {
            return Float.NaN;
        }
        while (i8 < iC && sVar.b(i3, i8) && (i6 = iArrD[4]) < i4) {
            iArrD[4] = i6 + 1;
            i8++;
        }
        int i10 = iArrD[4];
        if (i10 >= i4 || Math.abs(((((iArrD[0] + iArrD[1]) + iArrD[2]) + iArrD[3]) + i10) - i5) * 5 >= i5 * 2) {
            return Float.NaN;
        }
        if (r3.f17721h) {
            if (a(iArrD, true)) {
                return a(iArrD, i8);
            }
            return Float.NaN;
        }
        if (a(iArrD, false)) {
            return a(iArrD, i8);
        }
        return Float.NaN;
    }

    private float a(int i2, int i3, int i4, int i5, int i6) {
        int i7;
        int i8;
        s sVar = this.f17385a;
        int iC = sVar.c();
        int i9 = i3;
        while (i9 <= i4) {
            int[] iArrD = d();
            int i10 = i2;
            while (i10 >= 0 && !sVar.b(i9, i10) && (i8 = iArrD[1]) <= i5) {
                iArrD[1] = i8 + 1;
                i10--;
            }
            if (i10 >= 0) {
                double d2 = i5 * 1.5d;
                if (iArrD[1] > d2) {
                    continue;
                } else {
                    while (i10 >= 0 && sVar.b(i9, i10) && (i7 = iArrD[0]) <= i5) {
                        iArrD[0] = i7 + 1;
                        i10--;
                    }
                    if (iArrD[0] > i5 / 2) {
                        continue;
                    } else {
                        int i11 = i2 + 1;
                        while (i11 < iC && !sVar.b(i9, i11)) {
                            int i12 = iArrD[1];
                            if (i12 > i5) {
                                break;
                            }
                            iArrD[1] = i12 + 1;
                            i11++;
                        }
                        if (i11 >= 0 && iArrD[1] <= d2) {
                            while (i11 < iC && sVar.b(i9, i11)) {
                                iArrD[2] = iArrD[2] + 1;
                                i11++;
                            }
                            if (Math.abs(((iArrD[0] + iArrD[1]) + iArrD[2]) - i6) * 5 < i6 * 2 && b(iArrD)) {
                                return (i11 - (iArrD[1] / 2)) - iArrD[2];
                            }
                        }
                    }
                }
            }
            i9 += i4 - i3;
        }
        return Float.NaN;
    }

    protected final boolean a(int[] iArr, int i2, int i3) {
        int i4 = iArr[0] + iArr[1] + iArr[2] + iArr[3] + iArr[4];
        float fA = a(iArr, i3);
        float fB = b(i2, (int) fA, iArr[2], i4, false);
        if (Float.isNaN(fB)) {
            int i5 = iArr[2];
            int i6 = iArr[1];
            fB = a(i2, (int) ((fA - (i5 / 2)) - (i6 / 2)), (int) ((i5 / 2) + fA + (r13 / 2)), i6 + i5 + iArr[3], i4);
        }
        if (Float.isNaN(fB)) {
            return false;
        }
        return a(false, fB, fA, i4 / 7.0f);
    }

    private boolean a(boolean z2, float f2, float f3, float f4) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.f17387c.size()) {
                break;
            }
            e3 e3Var = this.f17387c.get(i2);
            if (e3Var.b(f4, f2, f3)) {
                this.f17387c.set(i2, e3Var.a(f2, f3, f4, false));
                z2 = true;
                break;
            }
            i2++;
        }
        if (!z2) {
            e3 e3Var2 = new e3(f3, f2, f4, false);
            this.f17387c.add(e3Var2);
            v6 v6Var = this.f17389e;
            if (v6Var != null) {
                v6Var.a(e3Var2);
            }
        }
        return true;
    }

    private e3[] a() throws com.huawei.hms.scankit.p.a {
        e3 e3Var = this.f17386b.get(0);
        e3 e3Var2 = this.f17386b.get(1);
        float[] fArr = e3Var.b() < e3Var2.b() ? new float[]{e3Var.b(), e3Var2.b()} : new float[]{e3Var2.b(), e3Var.b()};
        float[] fArr2 = e3Var.b() < e3Var2.b() ? new float[]{e3Var.c(), e3Var2.c()} : new float[]{e3Var2.c(), e3Var.c()};
        float fE = (e3Var.e() + e3Var2.e()) / 2.0f;
        float fE2 = ((e3Var.e() + e3Var2.e()) * 7.0f) / 1.5f;
        if (Math.abs(fArr[0] - fArr[1]) > fE2 && Math.abs(fArr2[0] - fArr2[1]) <= fE2) {
            float f2 = fArr[0];
            float f3 = fArr2[0];
            this.f17386b.add(new e3((f2 + f3) - fArr2[1], (f3 + fArr[1]) - f2, fE, false, 0));
        } else if (Math.abs(fArr[0] - fArr[1]) <= fE2 && Math.abs(fArr2[0] - fArr2[1]) > fE2) {
            float f4 = fArr[0];
            float f5 = fArr[1];
            if (f4 < f5) {
                float f6 = fArr2[0] + f5;
                float f7 = fArr2[1];
                this.f17386b.add(new e3(f6 - f7, (f7 + f5) - f4, fE, false, 0));
            } else {
                float f8 = fArr2[1] + f4;
                float f9 = fArr2[0];
                this.f17386b.add(new e3(f8 - f9, (f9 + f4) - f5, fE, false, 0));
            }
        } else if (Math.abs(fArr[0] - fArr[1]) > fE2 && Math.abs(fArr2[0] - fArr2[1]) > fE2) {
            float f10 = fArr[0];
            float f11 = fArr[1];
            float f12 = fArr2[1];
            float f13 = fArr2[0];
            this.f17386b.add(new e3((((f10 + f11) + f12) - f13) / 2.0f, (((f13 + f12) + f10) - f11) / 2.0f, fE, false, 0));
        }
        if (this.f17386b.size() == 3) {
            return new e3[]{this.f17386b.get(0), this.f17386b.get(1), this.f17386b.get(2)};
        }
        throw com.huawei.hms.scankit.p.a.a();
    }
}
