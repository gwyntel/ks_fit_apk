package com.huawei.hms.hatool;

import com.heytap.mcssdk.constant.Constants;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;

/* loaded from: classes4.dex */
public class m1 {

    /* renamed from: b, reason: collision with root package name */
    private static m1 f16435b = new m1();

    /* renamed from: a, reason: collision with root package name */
    private a f16436a = new a();

    class a {

        /* renamed from: a, reason: collision with root package name */
        String f16437a;

        /* renamed from: b, reason: collision with root package name */
        String f16438b;

        /* renamed from: c, reason: collision with root package name */
        long f16439c = 0;

        a() {
        }

        void a(long j2) {
            m1.this.f16436a.f16439c = j2;
        }

        void b(String str) {
            m1.this.f16436a.f16437a = str;
        }

        void a(String str) {
            m1.this.f16436a.f16438b = str;
        }
    }

    public static m1 d() {
        return f16435b;
    }

    public long b() {
        return this.f16436a.f16439c;
    }

    public String c() {
        return this.f16436a.f16437a;
    }

    public String a() {
        return this.f16436a.f16438b;
    }

    public void a(String str, String str2) {
        long jB = b();
        String strC = w0.c(str, str2);
        if (strC == null || strC.isEmpty()) {
            v.e("WorkKeyHandler", "get rsa pubkey config error");
            return;
        }
        if (jB == 0) {
            jB = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - jB <= Constants.MILLS_OF_LAUNCH_INTERVAL) {
            return;
        }
        String strGenerateSecureRandomStr = EncryptUtil.generateSecureRandomStr(16);
        String strA = h0.a(strC, strGenerateSecureRandomStr);
        this.f16436a.a(jB);
        this.f16436a.b(strGenerateSecureRandomStr);
        this.f16436a.a(strA);
    }
}
