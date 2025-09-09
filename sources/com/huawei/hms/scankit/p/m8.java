package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class m8 implements o6 {

    /* renamed from: a, reason: collision with root package name */
    private Map<l1, Object> f17561a = new EnumMap(l1.class);

    @Override // com.huawei.hms.scankit.p.o6
    public s6 a(p pVar, Map<l1, ?> map) throws a {
        char c2 = 0;
        f3[] f3VarArrA = new h2(pVar.b()).a(map);
        int length = f3VarArrA.length;
        s6 s6Var = null;
        if (length > 10) {
            return null;
        }
        if (length != 3) {
            int i2 = 0;
            while (i2 <= length - 3) {
                int i3 = i2 + 1;
                int i4 = i3;
                while (i4 <= length - 2) {
                    int i5 = i4 + 1;
                    int i6 = i5;
                    while (i6 <= length - 1) {
                        f3 f3Var = f3VarArrA[i2];
                        f3 f3Var2 = f3VarArrA[i4];
                        f3 f3Var3 = f3VarArrA[i6];
                        f3[] f3VarArr = new f3[3];
                        f3VarArr[c2] = f3Var;
                        f3VarArr[1] = f3Var2;
                        f3VarArr[2] = f3Var3;
                        int[] iArr = {i2, i4, i6};
                        u6.a(f3VarArr);
                        if (h2.a(f3VarArr[c2], f3VarArr[1], f3VarArr[2]) && !h2.a(f3VarArr, f3VarArrA, iArr)) {
                            return a(f3VarArrA[i2], f3VarArrA[i4], f3VarArrA[i6]);
                        }
                        i6++;
                        c2 = 0;
                        s6Var = null;
                    }
                    i4 = i5;
                }
                i2 = i3;
            }
        } else if (h2.a(f3VarArrA[0], f3VarArrA[1], f3VarArrA[2])) {
            return a(f3VarArrA[0], f3VarArrA[1], f3VarArrA[2]);
        }
        return s6Var;
    }

    private s6 a(f3 f3Var, f3 f3Var2, f3 f3Var3) {
        return new s6("WXCODE_TEXT", null, new u6[]{new u6(f3Var.b(), f3Var.c()), new u6(f3Var2.b(), f3Var2.c()), new u6(f3Var3.b(), f3Var3.c())}, BarcodeFormat.WXCODE);
    }
}
