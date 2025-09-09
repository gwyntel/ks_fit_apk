package com.aliyun.alink.linksdk.tmp.connect.mix;

import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;
import com.aliyun.alink.linksdk.connectsdk.ApiHelper;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11208a = TmpConstant.TAG + c.class.getSimpleName();

    public static void a(final MTopAndApiGMixRequest mTopAndApiGMixRequest, final b<MTopAndApiGMixRequest, MTopAndApiGMixResponse> bVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11208a, "send request:" + mTopAndApiGMixRequest + " callback:" + bVar);
        ApiHelper.getInstance().send(mTopAndApiGMixRequest.a(), new ApiCallBack() { // from class: com.aliyun.alink.linksdk.tmp.connect.mix.c.1
            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onFail(int i2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(c.f11208a, "send onFail code:" + i2 + " msg:" + str + " callback:" + bVar);
                b bVar2 = bVar;
                if (bVar2 == null) {
                    ALog.e(c.f11208a, "send onFail callback empty");
                } else {
                    bVar2.a((b) mTopAndApiGMixRequest, new ErrorInfo(i2, str));
                }
            }

            @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
            public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(c.f11208a, "send onSuccess data:" + obj);
                b bVar2 = bVar;
                if (bVar2 == null) {
                    ALog.e(c.f11208a, "send onSuccess callback empty");
                } else {
                    bVar2.a((b) mTopAndApiGMixRequest, (MTopAndApiGMixRequest) new MTopAndApiGMixResponse(obj));
                }
            }
        });
    }
}
