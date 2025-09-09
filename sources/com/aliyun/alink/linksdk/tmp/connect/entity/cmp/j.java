package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public class j implements IConnectSubscribeListener {

    /* renamed from: a, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.c f11172a;

    /* renamed from: b, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.d f11173b;

    public j(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.c cVar) {
        this.f11172a = cVar;
        this.f11173b = dVar;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onFailure(AError aError) {
        this.f11172a.a(this.f11173b, new ErrorInfo(aError.getCode(), aError.getMsg()));
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onSuccess() {
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setCode(200);
        AResponse aResponse = new AResponse();
        aResponse.data = GsonUtils.toJson(commonResponsePayload);
        this.f11172a.a(this.f11173b, new i(aResponse));
    }
}
