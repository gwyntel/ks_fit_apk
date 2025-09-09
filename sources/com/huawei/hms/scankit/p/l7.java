package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Map;

/* loaded from: classes4.dex */
public final class l7 extends q7 {

    /* renamed from: h, reason: collision with root package name */
    private final q7 f17515h = new o2();

    @Override // com.huawei.hms.scankit.p.q7
    public boolean a(int i2, int i3, r rVar) {
        return rVar.a(i3, (i3 - i2) + i3, false, false);
    }

    @Override // com.huawei.hms.scankit.p.q7
    public s6 a(int i2, r rVar, int[] iArr, Map<l1, ?> map) throws a {
        return a(this.f17515h.a(i2, rVar, iArr, map));
    }

    @Override // com.huawei.hms.scankit.p.q7, com.huawei.hms.scankit.p.g5
    public s6 a(int i2, r rVar, Map<l1, ?> map) throws a {
        return a(this.f17515h.a(i2, rVar, map));
    }

    @Override // com.huawei.hms.scankit.p.g5, com.huawei.hms.scankit.p.o6
    public s6 a(p pVar, Map<l1, ?> map) throws a {
        return a(this.f17515h.a(pVar, map));
    }

    @Override // com.huawei.hms.scankit.p.q7
    BarcodeFormat a() {
        return BarcodeFormat.UPC_A;
    }

    @Override // com.huawei.hms.scankit.p.q7
    protected int a(r rVar, int[] iArr, StringBuilder sb) throws a {
        return this.f17515h.a(rVar, iArr, sb);
    }

    private static s6 a(s6 s6Var) throws a {
        String strK = s6Var.k();
        if (strK.charAt(0) == '0') {
            return new s6(strK.substring(1), null, s6Var.j(), BarcodeFormat.UPC_A);
        }
        throw a.a();
    }

    @Override // com.huawei.hms.scankit.p.q7
    boolean a(int[] iArr, int[] iArr2) throws a {
        int i2 = iArr2[1];
        int i3 = i2 - iArr2[0];
        int i4 = iArr[1];
        int i5 = iArr[0];
        return Math.abs(((int) Math.round(((double) (i2 - i5)) / (((double) (i3 + (i4 - i5))) / 6.0d))) + (-113)) <= 5;
    }
}
