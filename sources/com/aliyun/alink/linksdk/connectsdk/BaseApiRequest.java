package com.aliyun.alink.linksdk.connectsdk;

import java.io.Serializable;
import java.util.Map;

/* loaded from: classes2.dex */
public class BaseApiRequest implements Serializable {
    public Map<String, Object> objectToMap() {
        return ApiHelper.objectToMap(this);
    }
}
