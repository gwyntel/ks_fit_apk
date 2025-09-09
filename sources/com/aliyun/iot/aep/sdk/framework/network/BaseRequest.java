package com.aliyun.iot.aep.sdk.framework.network;

import com.aliyun.iot.aep.sdk.framework.utils.ObjectUtil;
import java.io.Serializable;
import java.util.Map;

/* loaded from: classes3.dex */
public class BaseRequest implements Serializable {
    public Map<String, Object> getParams() {
        return ObjectUtil.objectToMap(this);
    }
}
