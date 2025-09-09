package com.xiaomi.push;

import android.util.Log;
import com.xiaomi.push.gu;

/* loaded from: classes4.dex */
class gr {

    /* renamed from: a, reason: collision with root package name */
    private static final boolean f23845a = Log.isLoggable("BCompressed", 3);

    static byte[] a(gq gqVar, byte[] bArr) {
        try {
            byte[] bArrA = gu.a.a(bArr);
            if (f23845a) {
                com.xiaomi.channel.commonutils.logger.b.m92a("BCompressed", "decompress " + bArr.length + " to " + bArrA.length + " for " + gqVar);
                if (gqVar.f484a == 1) {
                    com.xiaomi.channel.commonutils.logger.b.m92a("BCompressed", "decompress not support upStream");
                }
            }
            return bArrA;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m92a("BCompressed", "decompress error " + e2);
            return bArr;
        }
    }
}
