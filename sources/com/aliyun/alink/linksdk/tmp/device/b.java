package com.aliyun.alink.linksdk.tmp.device;

import com.aliyun.alink.linksdk.tmp.config.GroupConfig;
import com.aliyun.alink.linksdk.tmp.data.ut.ExtraData;
import com.aliyun.alink.linksdk.tmp.device.a.c;
import com.aliyun.alink.linksdk.tmp.device.payload.KeyValuePair;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11372a = "[Tmp]GroupImpl";

    /* renamed from: b, reason: collision with root package name */
    private GroupConfig f11373b;

    /* renamed from: c, reason: collision with root package name */
    private com.aliyun.alink.linksdk.tmp.connect.b.b f11374c;

    public b(GroupConfig groupConfig) {
        this.f11373b = groupConfig;
    }

    public GroupConfig a() {
        return this.f11373b;
    }

    public com.aliyun.alink.linksdk.tmp.connect.b.b b() {
        return this.f11374c;
    }

    public void a(GroupConfig groupConfig) {
        this.f11373b = this.f11373b;
    }

    public void a(com.aliyun.alink.linksdk.tmp.connect.b.b bVar) {
        this.f11374c = bVar;
    }

    public boolean a(String str, List<KeyValuePair> list, ExtraData extraData, IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11372a, "invokeService serviceId:" + str + " args:" + list + " handler:" + iDevListener);
        com.aliyun.alink.linksdk.tmp.device.a.c.a aVar = new com.aliyun.alink.linksdk.tmp.device.a.c.a(this, iDevListener);
        return new c().b(aVar).b(new com.aliyun.alink.linksdk.tmp.device.a.c.b(this, iDevListener)).b(new com.aliyun.alink.linksdk.tmp.device.a.c.c(this, iDevListener).a(str).a(list).a(extraData)).a();
    }

    public boolean a(IDevListener iDevListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11372a, "GetLocalGroupInfo handler:" + iDevListener);
        return new c().b(new com.aliyun.alink.linksdk.tmp.device.a.c.b(this, iDevListener)).a();
    }
}
