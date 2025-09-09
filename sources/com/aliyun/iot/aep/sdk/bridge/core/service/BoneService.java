package com.aliyun.iot.aep.sdk.bridge.core.service;

import android.content.Context;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;

/* loaded from: classes3.dex */
public interface BoneService {
    boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback);

    void onDestroy();

    void onInitialize(Context context);
}
