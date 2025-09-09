package com.aliyun.alink.business.devicecenter.biz;

import com.alibaba.fastjson.JSONArray;

/* loaded from: classes2.dex */
public interface MeshDiscoverCallback {
    void onFailure(String str);

    void onSuccess(JSONArray jSONArray);
}
