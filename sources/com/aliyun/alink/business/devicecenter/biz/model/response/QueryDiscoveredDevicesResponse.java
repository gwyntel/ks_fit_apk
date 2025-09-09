package com.aliyun.alink.business.devicecenter.biz.model.response;

import com.alibaba.fastjson.JSONArray;
import com.aliyun.alink.linksdk.connectsdk.BaseApiResponse;

/* loaded from: classes2.dex */
public class QueryDiscoveredDevicesResponse extends BaseApiResponse {
    public JSONArray list;
    public int total;

    public JSONArray getList() {
        return this.list;
    }

    public int getTotal() {
        return this.total;
    }

    public void setList(JSONArray jSONArray) {
        this.list = jSONArray;
    }

    public void setTotal(int i2) {
        this.total = i2;
    }
}
