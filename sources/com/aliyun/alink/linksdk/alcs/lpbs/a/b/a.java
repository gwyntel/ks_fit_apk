package com.aliyun.alink.linksdk.alcs.lpbs.a.b;

import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10693a = "[AlcsLPBS]ConnectMgr";

    /* renamed from: b, reason: collision with root package name */
    private Map<String, IPalConnect> f10694b = new ConcurrentHashMap();

    public Map<String, IPalConnect> a() {
        return this.f10694b;
    }

    public IPalConnect b(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10693a, "getConnect id: mConnectList:" + this.f10694b);
        return this.f10694b.get(str);
    }

    public void a(String str, IPalConnect iPalConnect) {
        this.f10694b.put(str, iPalConnect);
    }

    public void a(String str) {
        this.f10694b.remove(str);
    }
}
