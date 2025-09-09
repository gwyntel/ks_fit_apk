package com.aliyun.alink.sdk.jsbridge;

import android.app.Activity;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface IJSBridge {
    void emit(String str, JSONObject jSONObject);

    Activity getCurrentActivity();

    void reload();
}
