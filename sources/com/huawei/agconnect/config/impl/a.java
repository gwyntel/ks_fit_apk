package com.huawei.agconnect.config.impl;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.AGConnectApp;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.CustomAuthProvider;
import com.huawei.agconnect.CustomCredentialsProvider;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class a extends AGConnectApp {

    /* renamed from: a, reason: collision with root package name */
    private static final Map<String, AGConnectApp> f15587a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private static final Object f15588b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static String f15589c;

    /* renamed from: d, reason: collision with root package name */
    private AGConnectServicesConfig f15590d;

    private a(Context context, String str) {
        this.f15590d = AGConnectServicesConfig.fromContext(context, str);
    }

    public static AGConnectApp a() {
        return a(f15589c);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setApiKey(String str) {
        this.f15590d.setParam("/client/api_key", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setAppId(String str) {
        this.f15590d.setParam("/client/app_id", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setClientId(String str) {
        this.f15590d.setParam("/client/client_id", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setClientSecret(String str) {
        this.f15590d.setParam("/client/client_secret", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setCpId(String str) {
        this.f15590d.setParam("/client/cp_id", str);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setCustomAuthProvider(CustomAuthProvider customAuthProvider) {
        ((com.huawei.agconnect.core.a.b) AGConnectInstance.getInstance()).a(customAuthProvider);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setCustomCredentialsProvider(CustomCredentialsProvider customCredentialsProvider) {
        ((com.huawei.agconnect.core.a.b) AGConnectInstance.getInstance()).a(customCredentialsProvider);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setParam(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("path can not be empty");
        }
        this.f15590d.setParam(str, str2);
    }

    @Override // com.huawei.agconnect.AGConnectApp
    public void setProductId(String str) {
        this.f15590d.setParam("/client/product_id", str);
    }

    public static AGConnectApp a(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        String packageName = context.getPackageName();
        f15589c = packageName;
        return a(context, packageName);
    }

    public static AGConnectApp a(Context context, String str) {
        AGConnectApp aGConnectApp;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("packageName can not be empty");
        }
        synchronized (f15588b) {
            try {
                Map<String, AGConnectApp> map = f15587a;
                aGConnectApp = map.get(str);
                if (aGConnectApp == null) {
                    map.put(str, new a(context, str));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return aGConnectApp;
    }

    public static AGConnectApp a(String str) {
        AGConnectApp aGConnectApp;
        synchronized (f15588b) {
            try {
                aGConnectApp = f15587a.get(str);
                if (aGConnectApp == null) {
                    throw new IllegalStateException("you should call AGConnectApp.initialize first");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return aGConnectApp;
    }
}
