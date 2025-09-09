package com.aliyun.alink.linksdk.tmp.connect.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.api.CommonRequest;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.device.payload.discovery.DiscoveryRequestPayload;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class c extends j<c, DiscoveryRequestPayload> {
    protected c() {
        this.f11107h = false;
        this.f11104e = "224.0.1.187";
        this.f11103d = TmpConstant.PATH_DISCOVERY;
        this.f11109j = CommonRequestBuilder.RequestType.MULTIPLE_RESPONSE;
        c(true);
    }

    public static c d() {
        return new c();
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.a.j, com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder
    public com.aliyun.alink.linksdk.tmp.connect.d c() {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.ip = this.f11104e;
        commonRequest.port = this.f11105f;
        commonRequest.topic = this.f11103d;
        commonRequest.mothod = b().toCRMethod();
        commonRequest.payload = TextUtils.isEmpty(this.f11106g) ? GsonUtils.toJson(this.f11111l) : this.f11106g;
        commonRequest.context = this.f11102c;
        commonRequest.type = Integer.valueOf(this.f11133u);
        commonRequest.isSecurity = false;
        return new com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h(commonRequest);
    }
}
