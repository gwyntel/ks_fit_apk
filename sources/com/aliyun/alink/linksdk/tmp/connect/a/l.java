package com.aliyun.alink.linksdk.tmp.connect.a;

import com.aliyun.alink.linksdk.tmp.data.device.Option;
import com.aliyun.alink.linksdk.tmp.device.payload.service.ServiceRequestPayload;

/* loaded from: classes2.dex */
public class l extends j<l, ServiceRequestPayload> {

    /* renamed from: m, reason: collision with root package name */
    protected Option f11136m;

    public static l d() {
        return new l();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public l a(Option option) {
        this.f11136m = option;
        return (l) this.f11110k;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.a.j, com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder
    public com.aliyun.alink.linksdk.tmp.connect.d c() {
        com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h hVar = (com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h) super.c();
        hVar.a(this.f11136m);
        return hVar;
    }
}
