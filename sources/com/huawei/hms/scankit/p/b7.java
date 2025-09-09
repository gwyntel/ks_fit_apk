package com.huawei.hms.scankit.p;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

/* loaded from: classes4.dex */
final class b7 {

    /* renamed from: e, reason: collision with root package name */
    static final b7 f17026e = new b7(i7.f17395b, 0, 0, 0);

    /* renamed from: a, reason: collision with root package name */
    private final int f17027a;

    /* renamed from: b, reason: collision with root package name */
    private final i7 f17028b;

    /* renamed from: c, reason: collision with root package name */
    private final int f17029c;

    /* renamed from: d, reason: collision with root package name */
    private final int f17030d;

    private b7(i7 i7Var, int i2, int i3, int i4) {
        this.f17028b = i7Var;
        this.f17027a = i2;
        this.f17029c = i3;
        this.f17030d = i4;
    }

    int a() {
        return this.f17029c;
    }

    int b() {
        return this.f17030d;
    }

    int c() {
        return this.f17027a;
    }

    public String toString() {
        String[] strArr = c4.f17066b;
        if (w7.a(strArr, this.f17027a)) {
            return String.format(Locale.ENGLISH, "%s bits=%d bytes=%d", strArr[this.f17027a], Integer.valueOf(this.f17030d), Integer.valueOf(this.f17029c));
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    b7 a(int i2, int i3) {
        int i4 = this.f17030d;
        i7 i7VarA = this.f17028b;
        int i5 = this.f17027a;
        if (i2 != i5) {
            int i6 = c4.f17067c[i5][i2];
            int i7 = 65535 & i6;
            int i8 = i6 >> 16;
            i7VarA = i7VarA.a(i7, i8);
            i4 += i8;
        }
        int i9 = i2 == 2 ? 4 : 5;
        return new b7(i7VarA.a(i3, i9), i2, 0, i4 + i9);
    }

    b7 b(int i2, int i3) {
        i7 i7VarA = this.f17028b;
        int i4 = this.f17027a;
        int i5 = i4 == 2 ? 4 : 5;
        if (i4 >= 0) {
            int[][] iArr = c4.f17069e;
            if (i4 < iArr.length && i2 > 0) {
                int[] iArr2 = iArr[i4];
                if (i2 < iArr2.length) {
                    i7VarA = i7VarA.a(iArr2[i2], i5);
                }
            }
        }
        return new b7(i7VarA.a(i3, 5), this.f17027a, 0, this.f17030d + i5 + 5);
    }

    b7 a(int i2) {
        i7 i7VarA = this.f17028b;
        int i3 = this.f17027a;
        int i4 = this.f17030d;
        if (i3 == 4 || i3 == 2) {
            int i5 = c4.f17067c[i3][0];
            int i6 = 65535 & i5;
            int i7 = i5 >> 16;
            i7VarA = i7VarA.a(i6, i7);
            i4 += i7;
            i3 = 0;
        }
        int i8 = this.f17029c;
        b7 b7Var = new b7(i7VarA, i3, i8 + 1, i4 + ((i8 == 0 || i8 == 31) ? 18 : i8 == 62 ? 9 : 8));
        return b7Var.f17029c == 2078 ? b7Var.b(i2 + 1) : b7Var;
    }

    b7 b(int i2) {
        int i3 = this.f17029c;
        return i3 == 0 ? this : new b7(this.f17028b.b(i2 - i3, i3), this.f17027a, 0, this.f17030d);
    }

    boolean a(b7 b7Var) {
        int i2;
        int i3 = this.f17030d + (c4.f17067c[this.f17027a][b7Var.f17027a] >> 16);
        int i4 = b7Var.f17029c;
        if (i4 > 0 && ((i2 = this.f17029c) == 0 || i2 > i4)) {
            i3 += 10;
        }
        return i3 <= b7Var.f17030d;
    }

    r a(byte[] bArr) {
        LinkedList linkedList = new LinkedList();
        for (i7 i7VarA = b(bArr.length).f17028b; i7VarA != null; i7VarA = i7VarA.a()) {
            linkedList.addFirst(i7VarA);
        }
        r rVar = new r();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            ((i7) it.next()).a(rVar, bArr);
        }
        return rVar;
    }
}
