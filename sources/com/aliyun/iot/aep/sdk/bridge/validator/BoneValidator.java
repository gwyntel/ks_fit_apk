package com.aliyun.iot.aep.sdk.bridge.validator;

import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;

/* loaded from: classes3.dex */
public interface BoneValidator {
    void validate(JSContext jSContext, BoneCall boneCall, BoneValidateListener boneValidateListener);
}
