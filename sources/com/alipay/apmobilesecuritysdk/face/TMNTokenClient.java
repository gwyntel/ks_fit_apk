package com.alipay.apmobilesecuritysdk.face;

import android.content.Context;
import com.alipay.apmobilesecuritysdk.f.b;
import com.alipay.apmobilesecuritysdk.otherid.UtdidWrapper;
import com.alipay.sdk.m.z.a;
import java.util.HashMap;
import org.android.agoo.message.MessageService;

/* loaded from: classes2.dex */
public class TMNTokenClient {

    /* renamed from: a, reason: collision with root package name */
    public static TMNTokenClient f9093a;

    /* renamed from: b, reason: collision with root package name */
    public Context f9094b;

    public interface InitResultListener {
        void onResult(String str, int i2);
    }

    public TMNTokenClient(Context context) {
        this.f9094b = null;
        if (context == null) {
            throw new IllegalArgumentException("TMNTokenClient initialization error: context is null.");
        }
        this.f9094b = context;
    }

    public static TMNTokenClient getInstance(Context context) {
        if (f9093a == null) {
            synchronized (TMNTokenClient.class) {
                try {
                    if (f9093a == null) {
                        f9093a = new TMNTokenClient(context);
                    }
                } finally {
                }
            }
        }
        return f9093a;
    }

    public void intiToken(final String str, String str2, String str3, final InitResultListener initResultListener) {
        if (a.a(str) && initResultListener != null) {
            initResultListener.onResult("", 2);
        }
        if (a.a(str2) && initResultListener != null) {
            initResultListener.onResult("", 3);
        }
        final HashMap map = new HashMap();
        map.put("utdid", UtdidWrapper.getUtdid(this.f9094b));
        map.put("tid", "");
        map.put("userId", "");
        map.put("appName", str);
        map.put("appKeyClient", str2);
        map.put("appchannel", "openapi");
        map.put("sessionId", str3);
        map.put("rpcVersion", MessageService.MSG_ACCS_NOTIFY_CLICK);
        b.a().a(new Runnable() { // from class: com.alipay.apmobilesecuritysdk.face.TMNTokenClient.1
            @Override // java.lang.Runnable
            public void run() {
                int iA = new com.alipay.apmobilesecuritysdk.a.a(TMNTokenClient.this.f9094b).a(map);
                InitResultListener initResultListener2 = initResultListener;
                if (initResultListener2 == null) {
                    return;
                }
                if (iA != 0) {
                    initResultListener2.onResult("", iA);
                } else {
                    initResultListener.onResult(com.alipay.apmobilesecuritysdk.a.a.a(TMNTokenClient.this.f9094b, str), 0);
                }
            }
        });
    }
}
