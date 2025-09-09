package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.a.a;
import com.alipay.apmobilesecuritysdk.e.d;
import com.alipay.apmobilesecuritysdk.e.g;
import com.alipay.apmobilesecuritysdk.e.h;
import com.alipay.apmobilesecuritysdk.e.i;
import com.alipay.apmobilesecuritysdk.f.b;
import com.alipay.apmobilesecuritysdk.otherid.UmidSdkWrapper;
import com.alipay.apmobilesecuritysdk.otherid.UtdidWrapper;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.message.MessageService;

/* loaded from: classes2.dex */
public class APSecuritySdk {

    /* renamed from: a, reason: collision with root package name */
    public static APSecuritySdk f9086a;

    /* renamed from: c, reason: collision with root package name */
    public static Object f9087c = new Object();

    /* renamed from: b, reason: collision with root package name */
    public Context f9088b;

    public interface InitResultListener {
        void onResult(TokenResult tokenResult);
    }

    public class TokenResult {
        public String apdid;
        public String apdidToken;
        public String clientKey;
        public String umidToken;

        public TokenResult() {
        }
    }

    public APSecuritySdk(Context context) {
        this.f9088b = context;
    }

    public static APSecuritySdk getInstance(Context context) {
        if (f9086a == null) {
            synchronized (f9087c) {
                try {
                    if (f9086a == null) {
                        f9086a = new APSecuritySdk(context);
                    }
                } finally {
                }
            }
        }
        return f9086a;
    }

    public static String getUtdid(Context context) {
        return UtdidWrapper.getUtdid(context);
    }

    public String getApdidToken() {
        String strA = a.a(this.f9088b, "");
        if (com.alipay.sdk.m.z.a.a(strA)) {
            initToken(0, new HashMap(), null);
        }
        return strA;
    }

    public String getSdkName() {
        return "APPSecuritySDK-ALIPAYSDK";
    }

    public String getSdkVersion() {
        return "3.4.0.202203211140";
    }

    public synchronized TokenResult getTokenResult() {
        TokenResult tokenResult;
        tokenResult = new TokenResult();
        try {
            tokenResult.apdidToken = a.a(this.f9088b, "");
            tokenResult.clientKey = h.f(this.f9088b);
            tokenResult.apdid = a.a(this.f9088b);
            tokenResult.umidToken = UmidSdkWrapper.getSecurityToken(this.f9088b);
            if (com.alipay.sdk.m.z.a.a(tokenResult.apdid) || com.alipay.sdk.m.z.a.a(tokenResult.apdidToken) || com.alipay.sdk.m.z.a.a(tokenResult.clientKey)) {
                initToken(0, new HashMap(), null);
            }
        } catch (Throwable unused) {
        }
        return tokenResult;
    }

    public void initToken(int i2, Map<String, String> map, final InitResultListener initResultListener) {
        com.alipay.apmobilesecuritysdk.b.a.a().a(i2);
        String strB = h.b(this.f9088b);
        String strC = com.alipay.apmobilesecuritysdk.b.a.a().c();
        if (com.alipay.sdk.m.z.a.b(strB) && !com.alipay.sdk.m.z.a.a(strB, strC)) {
            com.alipay.apmobilesecuritysdk.e.a.a(this.f9088b);
            d.a(this.f9088b);
            g.a(this.f9088b);
            i.h();
        }
        if (!com.alipay.sdk.m.z.a.a(strB, strC)) {
            h.c(this.f9088b, strC);
        }
        String strA = com.alipay.sdk.m.z.a.a(map, "utdid", "");
        String strA2 = com.alipay.sdk.m.z.a.a(map, "tid", "");
        String strA3 = com.alipay.sdk.m.z.a.a(map, "userId", "");
        if (com.alipay.sdk.m.z.a.a(strA)) {
            strA = UtdidWrapper.getUtdid(this.f9088b);
        }
        final HashMap map2 = new HashMap();
        map2.put("utdid", strA);
        map2.put("tid", strA2);
        map2.put("userId", strA3);
        map2.put("appName", "");
        map2.put("appKeyClient", "");
        map2.put("appchannel", "");
        map2.put("rpcVersion", MessageService.MSG_ACCS_NOTIFY_CLICK);
        b.a().a(new Runnable() { // from class: com.alipay.apmobilesecuritysdk.face.APSecuritySdk.1
            @Override // java.lang.Runnable
            public void run() {
                new a(APSecuritySdk.this.f9088b).a(map2);
                InitResultListener initResultListener2 = initResultListener;
                if (initResultListener2 != null) {
                    initResultListener2.onResult(APSecuritySdk.this.getTokenResult());
                }
            }
        });
    }
}
