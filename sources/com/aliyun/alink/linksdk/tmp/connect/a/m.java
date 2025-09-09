package com.aliyun.alink.linksdk.tmp.connect.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.api.CommonRequest;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.data.device.Option;
import com.aliyun.alink.linksdk.tmp.device.payload.property.SetPropertyRequestPayload;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;

/* loaded from: classes2.dex */
public class m extends j<m, SetPropertyRequestPayload> {

    /* renamed from: m, reason: collision with root package name */
    protected String f11137m;

    /* renamed from: n, reason: collision with root package name */
    protected Option f11138n;

    public static m d() {
        return new m();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public m a(Option option) {
        this.f11138n = option;
        return (m) this.f11110k;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.connect.a.j, com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder
    public com.aliyun.alink.linksdk.tmp.connect.d c() {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.ip = this.f11104e;
        commonRequest.port = this.f11105f;
        commonRequest.topic = CommonRequestBuilder.a(f(), g(), h(), this.f11131s);
        commonRequest.mothod = b().toCRMethod();
        commonRequest.payload = TextUtils.isEmpty(this.f11106g) ? GsonUtils.toJson(this.f11111l) : this.f11106g;
        commonRequest.context = this.f11102c;
        commonRequest.type = Integer.valueOf(this.f11133u);
        commonRequest.isSecurity = this.f11107h;
        commonRequest.traceId = String.valueOf(this.f11137m);
        Payload payload = this.f11111l;
        commonRequest.alinkIdForTracker = String.valueOf(payload == 0 ? "" : ((SetPropertyRequestPayload) payload).getId());
        commonRequest.iotId = this.f11134v;
        commonRequest.tgMeshType = this.f11135w;
        com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h hVar = new com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h(commonRequest);
        hVar.a(f());
        hVar.b(g());
        hVar.a(this.f11102c);
        hVar.a(this.f11107h);
        hVar.a(this.f11138n);
        return hVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public m e(String str) {
        this.f11137m = str;
        return (m) this.f11110k;
    }
}
