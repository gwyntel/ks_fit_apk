package com.aliyun.alink.business.devicecenter.channel.http.model.request;

import com.aliyun.alink.business.devicecenter.channel.http.model.DataObject;
import java.util.Map;

/* loaded from: classes2.dex */
public class BaseRequest extends DataObject {

    /* renamed from: a, reason: collision with root package name */
    public Map<String, Object> f10324a;

    public Map<String, Object> getExtraInfo() {
        return this.f10324a;
    }

    public void setExtraInfo(Map<String, Object> map) {
        this.f10324a = map;
    }
}
