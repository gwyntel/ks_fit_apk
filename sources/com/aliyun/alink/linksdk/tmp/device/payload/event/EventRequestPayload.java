package com.aliyun.alink.linksdk.tmp.device.payload.event;

import com.aliyun.alink.linksdk.tmp.device.payload.CommonRequestPayload;
import com.google.gson.JsonObject;

/* loaded from: classes2.dex */
public class EventRequestPayload extends CommonRequestPayload {
    protected transient String mDeviceName;
    protected transient String mProdKey;

    /* JADX WARN: Type inference failed for: r1v1, types: [T, com.google.gson.JsonObject] */
    public EventRequestPayload(String str, String str2) {
        super(str, str2);
        this.mProdKey = str;
        this.mDeviceName = str2;
        this.params = new JsonObject();
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getProdKey() {
        return this.mProdKey;
    }

    public void setDeviceName(String str) {
        this.mDeviceName = str;
    }

    public void setProdKey(String str) {
        this.mProdKey = str;
    }
}
