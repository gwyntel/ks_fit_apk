package com.aliyun.iot.aep.sdk.bridge.base;

import android.content.Context;
import com.aliyun.iot.aep.sdk.bridge.a;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneService;

/* loaded from: classes3.dex */
public class BaseBoneService implements BoneService {

    /* renamed from: a, reason: collision with root package name */
    a f11670a = new a();
    private boolean isBoneInit = true;

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
    public final boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) {
        try {
            if (this.isBoneInit) {
                return this.f11670a.a(this, jSContext, boneCall, boneCallback);
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            boneCallback.failed("608", "method invoke failed", boneCall.methodName + " execute failed");
            return false;
        }
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
    public void onDestroy() {
        if (this.isBoneInit) {
            this.f11670a.a();
        }
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
    public void onInitialize(Context context) throws SecurityException {
        if (this.isBoneInit) {
            this.f11670a.a(this);
        }
    }
}
