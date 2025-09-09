package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.AError;

/* loaded from: classes2.dex */
public class k implements IConnectUnscribeListener {

    /* renamed from: a, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.c f11174a;

    /* renamed from: b, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.d f11175b;

    public k(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.c cVar) {
        this.f11174a = cVar;
        this.f11175b = dVar;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onFailure(AError aError) {
        this.f11174a.a(this.f11175b, new ErrorInfo(aError.getCode(), aError.getMsg()));
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onSuccess() {
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setCode(200);
        AResponse aResponse = new AResponse();
        aResponse.data = GsonUtils.toJson(commonResponsePayload);
        this.f11174a.a(this.f11175b, new i(aResponse));
    }
}
