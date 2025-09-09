package com.huawei.hms.framework.network.grs.f;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.AssetsUtil;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static final Map<String, b> f16223b = new ConcurrentHashMap(16);

    /* renamed from: c, reason: collision with root package name */
    private static final Object f16224c = new Object();

    /* renamed from: a, reason: collision with root package name */
    private a f16225a;

    public b(Context context, boolean z2) throws IOException {
        a(context, z2);
        f16223b.put(context.getPackageName(), this);
    }

    public static b a(String str) {
        return f16223b.get(str);
    }

    public Set<String> b() {
        return this.f16225a.b();
    }

    public com.huawei.hms.framework.network.grs.local.model.a a() {
        return this.f16225a.a();
    }

    public String a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, String str2, boolean z2) {
        String strA;
        synchronized (f16224c) {
            strA = this.f16225a.a(context, aVar, grsBaseInfo, str, str2, z2);
        }
        return strA;
    }

    public Map<String, String> a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, boolean z2) {
        Map<String, String> mapA;
        synchronized (f16224c) {
            mapA = this.f16225a.a(context, aVar, grsBaseInfo, str, z2);
        }
        return mapA;
    }

    private void a(Context context, boolean z2) throws IOException {
        String[] strArrSplit;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String strA = com.huawei.hms.framework.network.grs.h.c.a("grs_route_config_files_list.txt", context);
        Logger.i("LocalManagerProxy", "initLocalManager configFileListContent TimeCost:%d  Content:%s", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis), strA);
        if (TextUtils.isEmpty(strA)) {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            strArrSplit = AssetsUtil.list(context, GrsApp.getInstance().getBrand(""));
            Logger.i("LocalManagerProxy", "list by AssetsManager, timeCost:" + (System.currentTimeMillis() - jCurrentTimeMillis2));
        } else {
            strArrSplit = strA.split(MqttTopic.MULTI_LEVEL_WILDCARD);
        }
        List<String> arrayList = strArrSplit == null ? new ArrayList<>() : Arrays.asList(strArrSplit);
        String appConfigName = GrsApp.getInstance().getAppConfigName();
        Logger.i("LocalManagerProxy", "appConfigName is: " + appConfigName);
        this.f16225a = new d(false, z2);
        if (arrayList.contains("grs_app_global_route_config.json") || !TextUtils.isEmpty(appConfigName)) {
            this.f16225a = new d(context, appConfigName, z2);
        }
        if (!this.f16225a.c() && arrayList.contains("grs_sdk_global_route_config.json")) {
            this.f16225a = new c(context, z2);
        }
        this.f16225a.a(context, arrayList);
        StringBuilder sb = new StringBuilder();
        sb.append("on initLocalManager finish, check appGrs: ");
        sb.append(this.f16225a.f16218a == null);
        Logger.i("LocalManagerProxy", sb.toString());
    }
}
