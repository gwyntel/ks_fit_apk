package com.aliyun.alink.linksdk.tmp.connect.a;

import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.GetTslRequestPayload;

/* loaded from: classes2.dex */
public class e extends j<e, GetTslRequestPayload> {

    /* renamed from: m, reason: collision with root package name */
    protected String f11124m = "/thing/tsl/get";

    /* renamed from: n, reason: collision with root package name */
    protected String f11125n = "1.0.0";

    /* JADX WARN: Type inference failed for: r0v2, types: [Payload, com.aliyun.alink.linksdk.tmp.device.payload.discovery.GetTslRequestPayload] */
    public e() {
        this.f11111l = new GetTslRequestPayload(null, null);
    }

    public static e d() {
        return new e();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.connect.a.j, com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder
    public com.aliyun.alink.linksdk.tmp.connect.d c() {
        return new com.aliyun.alink.linksdk.tmp.connect.d(ApiGatewayRequest.build(this.f11124m, this.f11125n, ((GetTslRequestPayload) this.f11111l).getParams()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public e e(String str) {
        ((GetTslRequestPayload) this.f11111l).putIotId(str);
        return this;
    }
}
