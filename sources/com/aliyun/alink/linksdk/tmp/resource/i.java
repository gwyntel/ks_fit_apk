package com.aliyun.alink.linksdk.tmp.resource;

import com.aliyun.alink.linksdk.cmp.api.ResourceRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceResponseListener;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonResponsePayload;
import com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class i implements ITResResponseCallback {

    /* renamed from: d, reason: collision with root package name */
    private static final String f11432d = "[Tmp]TResResponseCallback";

    /* renamed from: a, reason: collision with root package name */
    protected IResourceResponseListener f11433a;

    /* renamed from: b, reason: collision with root package name */
    protected ResourceRequest f11434b;

    /* renamed from: c, reason: collision with root package name */
    protected AResource f11435c;

    /* renamed from: e, reason: collision with root package name */
    private String f11436e;

    public i(ResourceRequest resourceRequest, AResource aResource, String str, IResourceResponseListener iResourceResponseListener) {
        this.f11434b = resourceRequest;
        this.f11433a = iResourceResponseListener;
        this.f11435c = aResource;
        this.f11436e = str;
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback
    public void onComplete(String str, ErrorInfo errorInfo, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2 = errorInfo == null || errorInfo.isSuccess();
        ALog.d(f11432d, "onComplete identifer:" + str + " mRequestId:" + this.f11436e + " errorInfo:" + errorInfo + " result:" + obj);
        CommonResponsePayload commonResponsePayload = new CommonResponsePayload();
        commonResponsePayload.setId(this.f11436e);
        if (z2) {
            commonResponsePayload.setCode(200);
            commonResponsePayload.setData(obj);
        } else {
            commonResponsePayload.setCode(300);
            commonResponsePayload.setData(errorInfo.getErrorMsg());
        }
        AResponse aResponse = new AResponse();
        aResponse.data = GsonUtils.toJson(commonResponsePayload);
        this.f11433a.onResponse(this.f11435c, this.f11434b, aResponse);
    }
}
