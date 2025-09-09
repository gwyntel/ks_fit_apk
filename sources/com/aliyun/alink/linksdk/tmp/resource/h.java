package com.aliyun.alink.linksdk.tmp.resource;

import com.aliyun.alink.linksdk.tmp.api.InputParams;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.device.payload.CommonRequestPayload;
import com.aliyun.alink.linksdk.tmp.listener.ITResRequestHandler;
import com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class h implements b {

    /* renamed from: b, reason: collision with root package name */
    private static final String f11429b = "[Tmp]TResRequestWrapperHandler";

    /* renamed from: a, reason: collision with root package name */
    protected ITResRequestHandler f11430a;

    public h(ITResRequestHandler iTResRequestHandler) {
        this.f11430a = iTResRequestHandler;
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11429b, "onFail errorInfo:" + errorInfo + " returnValue: mHandler:" + this.f11430a);
        ITResRequestHandler iTResRequestHandler = this.f11430a;
        if (iTResRequestHandler != null) {
            iTResRequestHandler.onFail(obj, errorInfo);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.resource.b
    public void onProcess(String str, String str2, Object obj, ITResResponseCallback iTResResponseCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11429b, "onProcess identifier:" + str2 + " topic:" + str2 + " payload:" + obj + " mHandler:" + this.f11430a);
        if (this.f11430a != null) {
            CommonRequestPayload commonRequestPayload = (CommonRequestPayload) GsonUtils.fromJson(String.valueOf(obj), new TypeToken<CommonRequestPayload>() { // from class: com.aliyun.alink.linksdk.tmp.resource.h.1
            }.getType());
            this.f11430a.onProcess(str, (obj == null || commonRequestPayload == null) ? null : new InputParams(commonRequestPayload.getParams()), iTResResponseCallback);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11429b, "onSuccess identifier:" + outputParams + " returnValue: mHandler:" + this.f11430a);
        ITResRequestHandler iTResRequestHandler = this.f11430a;
        if (iTResRequestHandler != null) {
            iTResRequestHandler.onSuccess(obj, outputParams);
        }
    }
}
