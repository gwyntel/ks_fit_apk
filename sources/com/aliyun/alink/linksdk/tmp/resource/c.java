package com.aliyun.alink.linksdk.tmp.resource;

import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.listener.ITRawDataRequestHandler;
import com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class c implements b {

    /* renamed from: b, reason: collision with root package name */
    private static final String f11415b = "[Tmp]TRawResRequestWrapperHandler";

    /* renamed from: a, reason: collision with root package name */
    protected ITRawDataRequestHandler f11416a;

    public c(ITRawDataRequestHandler iTRawDataRequestHandler) {
        this.f11416a = iTRawDataRequestHandler;
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11415b, "onFail errorInfo:" + errorInfo + " returnValue: mHandler:" + this.f11416a);
        ITRawDataRequestHandler iTRawDataRequestHandler = this.f11416a;
        if (iTRawDataRequestHandler != null) {
            iTRawDataRequestHandler.onSuccess(obj, errorInfo);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.resource.b
    public void onProcess(String str, String str2, Object obj, ITResResponseCallback iTResResponseCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11415b, "onProcess identifier:" + str2 + " topic:" + str2 + " payload:" + obj + " mHandler:" + this.f11416a);
        ITRawDataRequestHandler iTRawDataRequestHandler = this.f11416a;
        if (iTRawDataRequestHandler != null) {
            iTRawDataRequestHandler.onProcess(str, obj, iTResResponseCallback);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11415b, "onSuccess identifier:" + outputParams + " returnValue: mHandler:" + this.f11416a);
        ITRawDataRequestHandler iTRawDataRequestHandler = this.f11416a;
        if (iTRawDataRequestHandler != null) {
            iTRawDataRequestHandler.onSuccess(obj, outputParams);
        }
    }
}
