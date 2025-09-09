package com.aliyun.alink.business.devicecenter.channel.http;

import com.alibaba.ailabs.tg.mtop.AbstractOnResponseListener;
import com.alibaba.ailabs.tg.network.mtop.inner.MtopHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.taobao.tao.remotebusiness.MtopBusiness;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.IMTOPDataObject;

/* loaded from: classes2.dex */
public class MtopApiClientImpl implements IApiClient {

    /* renamed from: a, reason: collision with root package name */
    public AbstractOnResponseListener f10305a = null;

    /* renamed from: b, reason: collision with root package name */
    public MtopBusiness f10306b;

    @Override // com.aliyun.alink.business.devicecenter.channel.http.IApiClient
    public void send(IRequest iRequest, Class<?> cls, final IRequestCallback iRequestCallback) {
        if (!(iRequest instanceof IMTOPDataObject)) {
            ALog.e("IMtopApiClientImpl", "Invalid request not IMTOPDataObject");
            return;
        }
        this.f10305a = new AbstractOnResponseListener() { // from class: com.aliyun.alink.business.devicecenter.channel.http.MtopApiClientImpl.1
            public final void a(BaseOutDo baseOutDo, String[] strArr) {
                String[] strArr2;
                String[] ret = baseOutDo.getRet();
                if (ret == null) {
                    strArr2 = new String[]{"tid:" + strArr[0]};
                } else {
                    String[] strArr3 = new String[ret.length + 1];
                    System.arraycopy(ret, 0, strArr3, 0, ret.length);
                    strArr3[ret.length] = "tid:" + strArr[0];
                    strArr2 = strArr3;
                }
                baseOutDo.setRet(strArr2);
                ALog.d("IMtopApiClientImpl", "onResponseSuccess() newRet = " + strArr2);
            }

            public void onResponseFailed(int i2, String str, String str2, String... strArr) {
                ALog.d("IMtopApiClientImpl", "onResponseFailed() called with: code = [" + i2 + "], s = [" + str + "], msgInfo = [" + str2 + "] traceId=" + strArr[0]);
                if (iRequestCallback != null) {
                    iRequestCallback.onFail(new DCError(str, str2 + ",tid:" + strArr[0]), null);
                }
            }

            public void onResponseSuccess(BaseOutDo baseOutDo, int i2, String... strArr) {
                ALog.d("IMtopApiClientImpl", "onResponseSuccess() called with: baseOutDo = [" + baseOutDo + "], i = [" + i2 + "] traceId=" + strArr[0]);
                if (iRequestCallback != null) {
                    a(baseOutDo, strArr);
                    iRequestCallback.onSuccess(baseOutDo);
                }
            }
        };
        MtopBusiness mtopBusinessAsyncRequestApi = MtopHelper.getInstance().asyncRequestApi((IMTOPDataObject) iRequest, cls, this.f10305a, 0);
        this.f10306b = mtopBusinessAsyncRequestApi;
        if (mtopBusinessAsyncRequestApi == null) {
            ALog.d("IMtopApiClientImpl", "mtopBusiness is null");
            if (iRequestCallback != null) {
                iRequestCallback.onFail(new DCError(String.valueOf(DCErrorCode.PF_SDK_ERROR), "mtop asyncRequestApi failed, return null."), null);
            }
        }
    }
}
