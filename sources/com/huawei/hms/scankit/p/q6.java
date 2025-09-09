package com.huawei.hms.scankit.p;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class q6 {

    /* renamed from: a, reason: collision with root package name */
    private final o3 f17699a;

    /* renamed from: b, reason: collision with root package name */
    private final List<p3> f17700b;

    public q6(o3 o3Var) {
        this.f17699a = o3Var;
        ArrayList arrayList = new ArrayList();
        this.f17700b = arrayList;
        arrayList.add(new p3(o3Var, new int[]{1}));
    }

    private p3 a(int i2) {
        if (i2 >= this.f17700b.size()) {
            List<p3> list = this.f17700b;
            p3 p3VarC = list.get(list.size() - 1);
            for (int size = this.f17700b.size(); size <= i2; size++) {
                o3 o3Var = this.f17699a;
                p3VarC = p3VarC.c(new p3(o3Var, new int[]{1, o3Var.a((size - 1) + o3Var.a())}));
                this.f17700b.add(p3VarC);
            }
        }
        return this.f17700b.get(i2);
    }

    public void a(int[] iArr, int i2) {
        if (i2 != 0) {
            int length = iArr.length - i2;
            if (length > 0) {
                p3 p3VarA = a(i2);
                int[] iArr2 = new int[length];
                System.arraycopy(iArr, 0, iArr2, 0, length);
                int[] iArrA = new p3(this.f17699a, iArr2).a(i2, 1).b(p3VarA)[1].a();
                int length2 = i2 - iArrA.length;
                for (int i3 = 0; i3 < length2; i3++) {
                    iArr[length + i3] = 0;
                }
                System.arraycopy(iArrA, 0, iArr, length + length2, iArrA.length);
                return;
            }
            throw new IllegalArgumentException("No data bytes provided");
        }
        throw new IllegalArgumentException("No error correction bytes");
    }
}
