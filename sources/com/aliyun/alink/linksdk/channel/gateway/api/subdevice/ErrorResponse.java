package com.aliyun.alink.linksdk.channel.gateway.api.subdevice;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class ErrorResponse<T> implements Serializable {
    public String code = null;
    public T data = null;
    public String message = null;

    public String toString() {
        return JSON.toJSONString(this);
    }
}
