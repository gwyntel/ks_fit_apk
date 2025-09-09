package com.aliyun.alink.linksdk.tmp.connect.mix;

import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a implements b<MTopAndApiGMixRequest, MTopAndApiGMixResponse> {

    /* renamed from: b, reason: collision with root package name */
    private static final String f11206b = TmpConstant.TAG + a.class.getSimpleName();

    /* renamed from: a, reason: collision with root package name */
    protected IConnectSendListener f11207a;

    public a(IConnectSendListener iConnectSendListener) {
        this.f11207a = iConnectSendListener;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.mix.b
    public void a(MTopAndApiGMixRequest mTopAndApiGMixRequest, MTopAndApiGMixResponse mTopAndApiGMixResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IConnectSendListener iConnectSendListener = this.f11207a;
        if (iConnectSendListener == null) {
            ALog.e(f11206b, "onSuccess mListener empty");
        } else {
            iConnectSendListener.onResponse(mTopAndApiGMixRequest.b(), mTopAndApiGMixResponse.buildAResponse());
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.mix.b
    public void a(MTopAndApiGMixRequest mTopAndApiGMixRequest, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f11207a == null) {
            ALog.e(f11206b, "onFailed mListener empty");
            return;
        }
        AError aError = new AError();
        aError.setCode(errorInfo.getErrorCode());
        aError.setMsg(errorInfo.getErrorMsg());
        this.f11207a.onFailure(mTopAndApiGMixRequest.b(), aError);
    }
}
