package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public abstract class e0 {
    private i a(int i2) {
        String strF;
        if (i2 != 0) {
            strF = f();
            if (!TextUtils.isEmpty(strF)) {
                return new i(d0.UDID, strF);
            }
        } else {
            strF = "";
        }
        return new i(d0.EMPTY, strF);
    }

    private i b(int i2) {
        String strF;
        if ((i2 & 4) != 0) {
            strF = f();
            if (!TextUtils.isEmpty(strF)) {
                return new i(d0.UDID, strF);
            }
        } else {
            strF = "";
        }
        return new i(d0.EMPTY, strF);
    }

    private boolean e() {
        g1 g1VarB = s.c().b();
        if (TextUtils.isEmpty(g1VarB.l())) {
            g1VarB.h(o.a());
        }
        return !TextUtils.isEmpty(g1VarB.l());
    }

    private String f() {
        g1 g1VarB = s.c().b();
        if (TextUtils.isEmpty(g1VarB.i())) {
            g1VarB.e(x0.c());
        }
        return g1VarB.i();
    }

    public abstract String a();

    public abstract String a(String str);

    public abstract String b();

    public abstract String c();

    public abstract int d();

    public i a(Context context) {
        String strC = c();
        if (!TextUtils.isEmpty(strC)) {
            return new i(d0.UDID, strC);
        }
        String strA = a();
        if (!TextUtils.isEmpty(strA)) {
            return new i(d0.IMEI, strA);
        }
        boolean zE = e();
        String strB = b();
        return !TextUtils.isEmpty(strB) ? zE ? new i(d0.SN, strB) : new i(d0.UDID, a(strB)) : zE ? a(d()) : b(d());
    }
}
