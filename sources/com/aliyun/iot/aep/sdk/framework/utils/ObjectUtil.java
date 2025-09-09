package com.aliyun.iot.aep.sdk.framework.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.google.gson.Gson;
import java.util.Map;

/* loaded from: classes3.dex */
public class ObjectUtil {
    public static Map<String, Object> objectToMap(Object obj) {
        try {
            return (Map) JSON.parseObject(new Gson().toJson(obj), Map.class);
        } catch (Exception e2) {
            ALog.d("ObjectUtil", e2.toString());
            return null;
        }
    }
}
