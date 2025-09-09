package com.aliyun.iot.aep.sdk.bridge.invoker;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallMode;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public class DefaultBoneInvoker implements BoneInvoker {

    /* renamed from: a, reason: collision with root package name */
    private BoneInvoker f11693a;

    /* renamed from: b, reason: collision with root package name */
    private BoneInvoker f11694b;

    public DefaultBoneInvoker(BoneInvoker boneInvoker, BoneInvoker boneInvoker2) {
        this.f11693a = boneInvoker;
        this.f11694b = boneInvoker2;
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.invoker.BoneInvoker
    public void invoke(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback, BoneCallback boneCallback2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (jSContext == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        if (jSContext.getCurrentActivity() == null) {
            ALog.d("DefaultBoneInvoker", "ignore call after destroy");
            return;
        }
        if (TextUtils.isEmpty(jSContext.getCurrentUrl())) {
            throw new IllegalArgumentException("jsContext.getCurrentUrl can not be empty");
        }
        if (boneCall == null) {
            throw new IllegalArgumentException("call can not be null");
        }
        if (TextUtils.isEmpty(boneCall.serviceId)) {
            throw new IllegalArgumentException("call.serviceId can not be empty");
        }
        if (TextUtils.isEmpty(boneCall.methodName)) {
            throw new IllegalArgumentException("call.methodName can not be empty");
        }
        if (boneCallback == null) {
            throw new IllegalArgumentException("syncCallback can not be null");
        }
        BoneCallMode boneCallMode = BoneCallMode.ASYNC;
        BoneCallMode boneCallMode2 = boneCall.mode;
        if (boneCallMode == boneCallMode2 && boneCallback2 == null) {
            throw new IllegalArgumentException("asyncCallback can not be null when call mode is async");
        }
        if (boneCallMode == boneCallMode2) {
            this.f11693a.invoke(jSContext, boneCall, boneCallback, boneCallback2);
        } else {
            this.f11694b.invoke(jSContext, boneCall, boneCallback, null);
        }
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.invoker.BoneInvoker
    public void onDestroy() {
        BoneInvoker boneInvoker = this.f11693a;
        if (boneInvoker != null) {
            boneInvoker.onDestroy();
            this.f11693a = null;
        }
        BoneInvoker boneInvoker2 = this.f11694b;
        if (boneInvoker2 != null) {
            boneInvoker2.onDestroy();
            this.f11694b = null;
        }
    }
}
