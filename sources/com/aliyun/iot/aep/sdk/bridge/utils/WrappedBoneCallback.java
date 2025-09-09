package com.aliyun.iot.aep.sdk.bridge.utils;

import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class WrappedBoneCallback implements BoneCallback {

    /* renamed from: a, reason: collision with root package name */
    private BoneCallback f11697a;

    public WrappedBoneCallback(BoneCallback boneCallback) {
        this.f11697a = boneCallback;
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback
    public void failed(String str, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11697a.failed(str, str2, str3);
        ALog.e("CallService.WrappedBoneCallback", "Failed:code=" + str + ";message=" + str2 + ";localiedMsg=" + str3);
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback
    public void success() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11697a.success();
        ALog.d("CallService.WrappedBoneCallback", "Success:{}");
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback
    public void failed(String str, String str2, String str3, JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11697a.failed(str, str2, str3, jSONObject);
        StringBuilder sb = new StringBuilder();
        sb.append("Failed:code=");
        sb.append(str);
        sb.append(";message=");
        sb.append(str2);
        sb.append(";localiedMsg=");
        sb.append(str3);
        sb.append(";extra=");
        sb.append(jSONObject == null ? "{}" : jSONObject.toString());
        ALog.e("CallService.WrappedBoneCallback", sb.toString());
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback
    public void success(JSONObject jSONObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11697a.success(jSONObject);
        StringBuilder sb = new StringBuilder();
        sb.append("Success:");
        sb.append(jSONObject == null ? "{}" : jSONObject.toString());
        ALog.d("CallService.WrappedBoneCallback", sb.toString());
    }
}
