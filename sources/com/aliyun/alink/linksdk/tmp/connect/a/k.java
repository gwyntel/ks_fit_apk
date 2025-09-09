package com.aliyun.alink.linksdk.tmp.connect.a;

import com.aliyun.alink.linksdk.cmp.api.CommonRequest;
import com.aliyun.alink.linksdk.tmp.device.payload.rawdata.SendRawdataRequestPayload;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class k extends j<k, SendRawdataRequestPayload> {
    /* JADX WARN: Type inference failed for: r1v1, types: [Payload, com.aliyun.alink.linksdk.tmp.device.payload.rawdata.SendRawdataRequestPayload] */
    protected k(String str, String str2) {
        k(str);
        l(str2);
        this.f11111l = new SendRawdataRequestPayload();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public k a(byte[] bArr) {
        ((SendRawdataRequestPayload) this.f11111l).setData(bArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.connect.a.j, com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder
    public com.aliyun.alink.linksdk.tmp.connect.d c() {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.ip = this.f11104e;
        commonRequest.port = this.f11105f;
        commonRequest.topic = "/" + this.f11131s + "/" + this.f11128p + "/" + this.f11129q + TmpConstant.URI_THING + TmpConstant.URI_MODEL + "/up_raw";
        commonRequest.mothod = b().toCRMethod();
        commonRequest.payload = ((SendRawdataRequestPayload) this.f11111l).getData();
        commonRequest.context = this.f11102c;
        commonRequest.type = Integer.valueOf(this.f11133u);
        commonRequest.isSecurity = this.f11107h;
        com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h hVar = new com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h(commonRequest);
        hVar.a(f());
        hVar.b(g());
        hVar.a(this.f11102c);
        hVar.a(this.f11107h);
        return hVar;
    }

    public static k a(String str, String str2) {
        return new k(str, str2);
    }
}
