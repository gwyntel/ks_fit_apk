package com.huawei.hms.hatool;

import android.util.Log;

/* loaded from: classes4.dex */
public class m0 {

    /* renamed from: a, reason: collision with root package name */
    private boolean f16433a = false;

    /* renamed from: b, reason: collision with root package name */
    private int f16434b = 4;

    private static String a() {
        return "FormalHASDK_2.2.0.314" + p.a();
    }

    public void b(int i2, String str, String str2) {
        a(i2, "FormalHASDK", str + "=> " + str2);
    }

    public void a(int i2) {
        Log.i("FormalHASDK", System.lineSeparator() + "======================================= " + System.lineSeparator() + a() + "" + System.lineSeparator() + "=======================================");
        this.f16434b = i2;
        this.f16433a = true;
    }

    public boolean b(int i2) {
        return this.f16433a && i2 >= this.f16434b;
    }

    public void a(int i2, String str, String str2) {
        if (i2 == 3) {
            Log.d(str, str2);
            return;
        }
        if (i2 == 5) {
            Log.w(str, str2);
        } else if (i2 != 6) {
            Log.i(str, str2);
        } else {
            Log.e(str, str2);
        }
    }
}
