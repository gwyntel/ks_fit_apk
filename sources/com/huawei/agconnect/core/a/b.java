package com.huawei.agconnect.core.a;

import android.content.Context;
import android.util.Log;
import com.huawei.agconnect.AGCRoutePolicy;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.agconnect.AGConnectOptions;
import com.huawei.agconnect.AGConnectOptionsBuilder;
import com.huawei.agconnect.CustomAuthProvider;
import com.huawei.agconnect.CustomCredentialsProvider;
import com.huawei.agconnect.JsonProcessingFactory;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.config.impl.Utils;
import com.huawei.agconnect.core.Service;
import com.huawei.agconnect.core.service.auth.AuthProvider;
import com.huawei.agconnect.core.service.auth.CredentialsProvider;
import com.huawei.agconnect.core.service.auth.OnTokenListener;
import com.huawei.agconnect.core.service.auth.Token;
import com.huawei.hmf.tasks.Task;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class b extends AGConnectInstance {

    /* renamed from: a, reason: collision with root package name */
    private static List<Service> f15633a;

    /* renamed from: b, reason: collision with root package name */
    private static final Object f15634b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static final Map<String, AGConnectInstance> f15635c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private static String f15636d;

    /* renamed from: e, reason: collision with root package name */
    private final AGConnectOptions f15637e;

    /* renamed from: f, reason: collision with root package name */
    private final d f15638f;

    /* renamed from: g, reason: collision with root package name */
    private final d f15639g;

    public b(AGConnectOptions aGConnectOptions) {
        this.f15637e = aGConnectOptions;
        if (f15633a == null) {
            Log.e("AGConnectInstance", "please call `initialize()` first");
        }
        this.f15638f = new d(f15633a, aGConnectOptions.getContext());
        d dVar = new d(null, aGConnectOptions.getContext());
        this.f15639g = dVar;
        if (aGConnectOptions instanceof com.huawei.agconnect.config.impl.b) {
            dVar.a(((com.huawei.agconnect.config.impl.b) aGConnectOptions).a(), aGConnectOptions.getContext());
        }
    }

    public static AGConnectInstance a() {
        String str = f15636d;
        if (str == null) {
            str = Utils.DEFAULT_NAME;
        }
        return a(str);
    }

    private static void b() {
        JsonProcessingFactory.registerProcessor("/agcgw/url", new JsonProcessingFactory.JsonProcessor() { // from class: com.huawei.agconnect.core.a.b.1
            @Override // com.huawei.agconnect.JsonProcessingFactory.JsonProcessor
            public String processOption(AGConnectOptions aGConnectOptions) {
                String str;
                if (aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.CHINA)) {
                    str = "/agcgw_all/CN";
                } else if (aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.RUSSIA)) {
                    str = "/agcgw_all/RU";
                } else if (aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.GERMANY)) {
                    str = "/agcgw_all/DE";
                } else {
                    if (!aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.SINGAPORE)) {
                        return null;
                    }
                    str = "/agcgw_all/SG";
                }
                return aGConnectOptions.getString(str);
            }
        });
        JsonProcessingFactory.registerProcessor("/agcgw/backurl", new JsonProcessingFactory.JsonProcessor() { // from class: com.huawei.agconnect.core.a.b.2
            @Override // com.huawei.agconnect.JsonProcessingFactory.JsonProcessor
            public String processOption(AGConnectOptions aGConnectOptions) {
                String str;
                if (aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.CHINA)) {
                    str = "/agcgw_all/CN_back";
                } else if (aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.RUSSIA)) {
                    str = "/agcgw_all/RU_back";
                } else if (aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.GERMANY)) {
                    str = "/agcgw_all/DE_back";
                } else {
                    if (!aGConnectOptions.getRoutePolicy().equals(AGCRoutePolicy.SINGAPORE)) {
                        return null;
                    }
                    str = "/agcgw_all/SG_back";
                }
                return aGConnectOptions.getString(str);
            }
        });
    }

    @Override // com.huawei.agconnect.AGConnectInstance
    public Context getContext() {
        return this.f15637e.getContext();
    }

    @Override // com.huawei.agconnect.AGConnectInstance
    public String getIdentifier() {
        return this.f15637e.getIdentifier();
    }

    @Override // com.huawei.agconnect.AGConnectInstance
    public AGConnectOptions getOptions() {
        return this.f15637e;
    }

    @Override // com.huawei.agconnect.AGConnectInstance
    public <T> T getService(Class<? super T> cls) {
        T t2 = (T) this.f15639g.a(this, cls);
        return t2 != null ? t2 : (T) this.f15638f.a(this, cls);
    }

    public static AGConnectInstance a(AGConnectOptions aGConnectOptions) {
        return a(aGConnectOptions, false);
    }

    private static void b(Context context, AGConnectOptionsBuilder aGConnectOptionsBuilder) throws IOException {
        AGConnectServicesConfig aGConnectServicesConfigFromContext = AGConnectServicesConfig.fromContext(context);
        if (aGConnectOptionsBuilder.getInputStream() != null) {
            try {
                String string = Utils.toString(aGConnectOptionsBuilder.getInputStream(), "UTF-8");
                aGConnectOptionsBuilder.getInputStream().reset();
                aGConnectServicesConfigFromContext.overlayWith(new ByteArrayInputStream(string.getBytes(Charset.forName("UTF-8"))));
            } catch (IOException unused) {
                Log.e("AGC_Instance", "input stream set to AGConnectServicesConfig fail");
            }
        }
        for (Map.Entry<String, String> entry : aGConnectOptionsBuilder.getCustomConfigMap().entrySet()) {
            aGConnectServicesConfigFromContext.setParam(entry.getKey(), entry.getValue());
        }
        if (aGConnectOptionsBuilder.getRoutePolicy() != AGCRoutePolicy.UNKNOWN) {
            aGConnectServicesConfigFromContext.setRoutePolicy(aGConnectOptionsBuilder.getRoutePolicy());
        }
    }

    private static AGConnectInstance a(AGConnectOptions aGConnectOptions, boolean z2) {
        AGConnectInstance bVar;
        synchronized (f15634b) {
            Map<String, AGConnectInstance> map = f15635c;
            bVar = map.get(aGConnectOptions.getIdentifier());
            if (bVar == null || z2) {
                bVar = new b(aGConnectOptions);
                map.put(aGConnectOptions.getIdentifier(), bVar);
            }
        }
        return bVar;
    }

    public static AGConnectInstance a(String str) {
        AGConnectInstance aGConnectInstance;
        synchronized (f15634b) {
            try {
                aGConnectInstance = f15635c.get(str);
                if (aGConnectInstance == null) {
                    if (Utils.DEFAULT_NAME.equals(str)) {
                        Log.w("AGC_Instance", "please call `initialize()` first");
                    } else {
                        Log.w("AGC_Instance", "not find instance for : " + str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return aGConnectInstance;
    }

    public static synchronized void a(Context context) {
        if (f15635c.size() > 0) {
            Log.w("AGC_Instance", "Repeated invoking initialize");
        } else {
            a(context, AGConnectServicesConfig.fromContext(context));
        }
    }

    private static synchronized void a(Context context, AGConnectOptions aGConnectOptions) {
        try {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext == null) {
                Log.w("AGC_Instance", "context.getApplicationContext null");
            } else {
                context = applicationContext;
            }
            b();
            com.huawei.agconnect.config.impl.a.a(context);
            if (f15633a == null) {
                f15633a = new c(context).a();
            }
            a(aGConnectOptions, true);
            f15636d = aGConnectOptions.getIdentifier();
            Log.i("AGC_Instance", "AGC SDK initialize end, default route:" + aGConnectOptions.getRoutePolicy().getRouteName());
            a.a();
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void a(Context context, AGConnectOptionsBuilder aGConnectOptionsBuilder) {
        b(context, aGConnectOptionsBuilder);
        a(context, aGConnectOptionsBuilder.build(context));
    }

    public void a(final CustomAuthProvider customAuthProvider) {
        this.f15639g.a(Collections.singletonList(Service.builder((Class<?>) AuthProvider.class, new AuthProvider() { // from class: com.huawei.agconnect.core.a.b.4
            @Override // com.huawei.agconnect.core.service.auth.AuthProvider
            public void addTokenListener(OnTokenListener onTokenListener) {
            }

            @Override // com.huawei.agconnect.core.service.auth.AuthProvider
            public Task<Token> getTokens() {
                return customAuthProvider.getTokens(false);
            }

            @Override // com.huawei.agconnect.core.service.auth.AuthProvider
            public String getUid() {
                return "";
            }

            @Override // com.huawei.agconnect.core.service.auth.AuthProvider
            public void removeTokenListener(OnTokenListener onTokenListener) {
            }

            @Override // com.huawei.agconnect.core.service.auth.AuthProvider
            public Task<Token> getTokens(boolean z2) {
                return customAuthProvider.getTokens(z2);
            }
        }).build()), this.f15637e.getContext());
    }

    public void a(final CustomCredentialsProvider customCredentialsProvider) {
        this.f15639g.a(Collections.singletonList(Service.builder((Class<?>) CredentialsProvider.class, new CredentialsProvider() { // from class: com.huawei.agconnect.core.a.b.3
            @Override // com.huawei.agconnect.core.service.auth.CredentialsProvider
            public Task<Token> getTokens() {
                return customCredentialsProvider.getTokens(false);
            }

            @Override // com.huawei.agconnect.core.service.auth.CredentialsProvider
            public Task<Token> getTokens(boolean z2) {
                return customCredentialsProvider.getTokens(z2);
            }
        }).build()), this.f15637e.getContext());
    }
}
