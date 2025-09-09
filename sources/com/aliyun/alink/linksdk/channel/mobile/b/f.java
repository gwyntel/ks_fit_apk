package com.aliyun.alink.linksdk.channel.mobile.b;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.core.base.ARequest;
import com.aliyun.alink.linksdk.channel.core.base.AResponse;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.mobile.api.IMobileRequestListener;

/* loaded from: classes2.dex */
class f implements IOnCallListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ IMobileRequestListener f11036a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ d f11037b;

    f(d dVar, IMobileRequestListener iMobileRequestListener) {
        this.f11037b = dVar;
        this.f11036a = iMobileRequestListener;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
    public boolean needUISafety() {
        return true;
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
    public void onFailed(ARequest aRequest, AError aError) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "bindAccount(),bind, get rsp fail, error = " + aError.getMsg().toString());
        this.f11036a.onFailure(aError);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
    public void onSuccess(ARequest aRequest, AResponse aResponse) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "bindAccount(),bind,get rsp");
        try {
            JSONObject object = JSON.parseObject((String) aResponse.data);
            int intValue = object.getIntValue("code");
            String string = object.getString("message");
            if (200 == intValue) {
                IMobileRequestListener iMobileRequestListener = this.f11036a;
                if (iMobileRequestListener != null) {
                    iMobileRequestListener.onSuccess(null);
                    return;
                }
                return;
            }
            AError aError = new AError();
            aError.setCode(4103);
            aError.setSubCode(intValue);
            aError.setMsg(string);
            IMobileRequestListener iMobileRequestListener2 = this.f11036a;
            if (iMobileRequestListener2 != null) {
                iMobileRequestListener2.onFailure(aError);
            }
        } catch (Exception e2) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileChannelImpl", "bindAccount(),bind,get rsp, parse error" + e2.toString());
            e2.printStackTrace();
        }
    }
}
