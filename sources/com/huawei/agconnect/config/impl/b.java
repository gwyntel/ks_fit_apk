package com.huawei.agconnect.config.impl;

import android.content.Context;
import com.huawei.agconnect.AGCRoutePolicy;
import com.huawei.agconnect.AGConnectOptions;
import com.huawei.agconnect.JsonProcessingFactory;
import com.huawei.agconnect.core.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class b implements AGConnectOptions {

    /* renamed from: a, reason: collision with root package name */
    private final String f15591a;

    /* renamed from: b, reason: collision with root package name */
    private final Context f15592b;

    /* renamed from: c, reason: collision with root package name */
    private final String f15593c;

    /* renamed from: d, reason: collision with root package name */
    private final AGCRoutePolicy f15594d;

    /* renamed from: e, reason: collision with root package name */
    private final d f15595e;

    /* renamed from: f, reason: collision with root package name */
    private final e f15596f;

    /* renamed from: g, reason: collision with root package name */
    private final Map<String, String> f15597g;

    /* renamed from: h, reason: collision with root package name */
    private final List<Service> f15598h;

    /* renamed from: i, reason: collision with root package name */
    private final Map<String, String> f15599i = new HashMap();

    public b(Context context, String str, AGCRoutePolicy aGCRoutePolicy, InputStream inputStream, Map<String, String> map, List<Service> list, String str2) throws IOException {
        context = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        this.f15592b = context;
        str = str == null ? context.getPackageName() : str;
        this.f15593c = str;
        if (inputStream != null) {
            this.f15595e = new h(inputStream, str);
            Utils.closeQuietly(inputStream);
        } else {
            this.f15595e = new k(context, str);
        }
        this.f15596f = new e(this.f15595e);
        AGCRoutePolicy aGCRoutePolicy2 = AGCRoutePolicy.UNKNOWN;
        if (aGCRoutePolicy != aGCRoutePolicy2 && "1.0".equals(this.f15595e.a("/configuration_version", null))) {
            throw new RuntimeException("The file version does not match,please download the latest agconnect-services.json from the AGC website.");
        }
        this.f15594d = (aGCRoutePolicy == null || aGCRoutePolicy == aGCRoutePolicy2) ? Utils.getRoutePolicyFromJson(this.f15595e.a("/region", null), this.f15595e.a("/agcgw/url", null)) : aGCRoutePolicy;
        this.f15597g = Utils.fixKeyPathMap(map);
        this.f15598h = list;
        this.f15591a = str2 == null ? b() : str2;
    }

    private String a(String str) {
        Map<String, JsonProcessingFactory.JsonProcessor> processors = JsonProcessingFactory.getProcessors();
        if (!processors.containsKey(str)) {
            return null;
        }
        if (this.f15599i.containsKey(str)) {
            return this.f15599i.get(str);
        }
        JsonProcessingFactory.JsonProcessor jsonProcessor = processors.get(str);
        if (jsonProcessor == null) {
            return null;
        }
        String strProcessOption = jsonProcessor.processOption(this);
        this.f15599i.put(str, strProcessOption);
        return strProcessOption;
    }

    private String b() {
        return String.valueOf(("{packageName='" + this.f15593c + "', routePolicy=" + this.f15594d + ", reader=" + this.f15595e.toString().hashCode() + ", customConfigMap=" + new JSONObject(this.f15597g).toString().hashCode() + '}').hashCode());
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public Context getContext() {
        return this.f15592b;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getIdentifier() {
        return this.f15591a;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public int getInt(String str) {
        return getInt(str, 0);
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getPackageName() {
        return this.f15593c;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public AGCRoutePolicy getRoutePolicy() {
        AGCRoutePolicy aGCRoutePolicy = this.f15594d;
        return aGCRoutePolicy == null ? AGCRoutePolicy.UNKNOWN : aGCRoutePolicy;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getString(String str) {
        return getString(str, null);
    }

    public List<Service> a() {
        return this.f15598h;
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public boolean getBoolean(String str, boolean z2) {
        return Boolean.parseBoolean(getString(str, String.valueOf(z2)));
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public int getInt(String str, int i2) {
        try {
            return Integer.parseInt(getString(str, String.valueOf(i2)));
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    @Override // com.huawei.agconnect.AGConnectOptions
    public String getString(String str, String str2) {
        if (str == null) {
            return str2;
        }
        String strFixPath = Utils.fixPath(str);
        String str3 = this.f15597g.get(strFixPath);
        if (str3 != null) {
            return str3;
        }
        String strA = a(strFixPath);
        if (strA != null) {
            return strA;
        }
        String strA2 = this.f15595e.a(strFixPath, str2);
        return e.a(strA2) ? this.f15596f.a(strA2, str2) : strA2;
    }
}
