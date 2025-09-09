package com.aliyun.iot.aep.sdk.bridge.core.service;

import org.json.JSONObject;

/* loaded from: classes3.dex */
public interface BoneCallback {
    void failed(String str, String str2, String str3);

    void failed(String str, String str2, String str3, JSONObject jSONObject);

    void success();

    void success(JSONObject jSONObject);
}
