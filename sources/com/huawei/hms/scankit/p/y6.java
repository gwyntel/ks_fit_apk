package com.huawei.hms.scankit.p;

import com.huawei.hms.ml.scan.HmsScan;

/* loaded from: classes4.dex */
public class y6 {
    public static HmsScan a(s6 s6Var) {
        HmsScan hmsScanA;
        if (s6Var == null) {
            return null;
        }
        return (!r3.f17719f || (hmsScanA = v5.a(s6Var)) == null) ? new HmsScan(s6Var.k(), t6.a(s6Var.c()), s6Var.k(), HmsScan.PURE_TEXT_FORM, s6Var.i(), t6.a(s6Var.j()), null, null).setZoomValue(s6Var.l()) : hmsScanA;
    }

    public static HmsScan[] a(s6[] s6VarArr) {
        if (s6VarArr != null && s6VarArr.length > 0) {
            HmsScan[] hmsScanArr = new HmsScan[s6VarArr.length];
            for (int i2 = 0; i2 < s6VarArr.length; i2++) {
                hmsScanArr[i2] = a(s6VarArr[i2]);
            }
            return hmsScanArr;
        }
        return new HmsScan[0];
    }
}
