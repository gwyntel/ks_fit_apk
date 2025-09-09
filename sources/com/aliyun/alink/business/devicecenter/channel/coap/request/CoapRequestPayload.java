package com.aliyun.alink.business.devicecenter.channel.coap.request;

import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.utils.IdIncrementUtil;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class CoapRequestPayload<T> implements Serializable {
    public String id = "-1000";
    public String version = null;
    public String method = null;
    public T params = null;

    public class Builder {
        public String version = null;
        public String method = null;
        public T params = null;

        public Builder() {
        }

        public CoapRequestPayload<T> build() {
            CoapRequestPayload<T> coapRequestPayload = new CoapRequestPayload<>();
            coapRequestPayload.id = String.valueOf(IdIncrementUtil.getId());
            coapRequestPayload.version = this.version;
            coapRequestPayload.method = this.method;
            coapRequestPayload.params = this.params;
            return coapRequestPayload;
        }

        public CoapRequestPayload<T>.Builder method(String str) {
            this.method = str;
            return this;
        }

        public CoapRequestPayload<T>.Builder params(T t2) {
            this.params = t2;
            return this;
        }

        public CoapRequestPayload<T>.Builder version(String str) {
            this.version = str;
            return this;
        }
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
