package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.huawei.secure.android.common.encrypt.hash.SHA;
import com.xiaomi.mipush.sdk.Constants;
import java.util.UUID;

/* loaded from: classes4.dex */
public class j {

    /* renamed from: b, reason: collision with root package name */
    private static j f16394b;

    /* renamed from: a, reason: collision with root package name */
    private Context f16395a;

    private static class a extends e0 {

        /* renamed from: a, reason: collision with root package name */
        String f16396a;

        /* renamed from: b, reason: collision with root package name */
        String f16397b;

        public a(String str, String str2) {
            this.f16396a = str;
            this.f16397b = str2;
        }

        @Override // com.huawei.hms.hatool.e0
        public String a() {
            return z.d(this.f16396a, this.f16397b);
        }

        @Override // com.huawei.hms.hatool.e0
        public String b() {
            return z.g(this.f16396a, this.f16397b);
        }

        @Override // com.huawei.hms.hatool.e0
        public String c() {
            return z.j(this.f16396a, this.f16397b);
        }

        @Override // com.huawei.hms.hatool.e0
        public int d() {
            return (z.k(this.f16396a, this.f16397b) ? 4 : 0) | (z.e(this.f16396a, this.f16397b) ? 2 : 0) | (z.h(this.f16396a, this.f16397b) ? 1 : 0);
        }

        @Override // com.huawei.hms.hatool.e0
        public String a(String str) {
            return SHA.sha256Encrypt(str);
        }
    }

    public static j a() {
        j jVar;
        synchronized (j.class) {
            try {
                if (f16394b == null) {
                    f16394b = new j();
                }
                jVar = f16394b;
            } catch (Throwable th) {
                throw th;
            }
        }
        return jVar;
    }

    public String b(String str, String str2) {
        return i0.b(this.f16395a, str, str2);
    }

    public i c(String str, String str2) {
        return new a(str, str2).a(this.f16395a);
    }

    public String d(String str, String str2) {
        return f1.b(str, str2);
    }

    public Pair<String, String> e(String str, String str2) {
        if (!z.f(str, str2)) {
            return new Pair<>("", "");
        }
        String strP = s.c().b().p();
        String strQ = s.c().b().q();
        if (!TextUtils.isEmpty(strP) && !TextUtils.isEmpty(strQ)) {
            return new Pair<>(strP, strQ);
        }
        Pair<String, String> pairE = x0.e(this.f16395a);
        s.c().b().k((String) pairE.first);
        s.c().b().l((String) pairE.second);
        return pairE;
    }

    public String f(String str, String str2) {
        return f1.a(str, str2);
    }

    public String a(String str, String str2) {
        return i0.a(this.f16395a, str, str2);
    }

    public String a(boolean z2) {
        if (!z2) {
            return "";
        }
        String strE = q0.e();
        if (TextUtils.isEmpty(strE)) {
            strE = d.a(this.f16395a, "global_v2", DeviceCommonConstants.KEY_DEVICE_ID, "");
            if (TextUtils.isEmpty(strE)) {
                strE = UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
                d.b(this.f16395a, "global_v2", DeviceCommonConstants.KEY_DEVICE_ID, strE);
            }
            q0.h(strE);
        }
        return strE;
    }

    public void a(Context context) {
        if (this.f16395a == null) {
            this.f16395a = context;
        }
    }
}
