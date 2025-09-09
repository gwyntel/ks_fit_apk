package com.alibaba.cloudapi.sdk.model;

import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.m.u.i;
import com.huawei.hms.framework.common.ContainerUtils;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public abstract class ApiHttpMessage {
    protected byte[] body;
    protected String bodyStr;
    protected Map<String, List<String>> headers = new HashMap();

    public void addHeader(String str, String str2) {
        String lowerCase = str.trim().toLowerCase(Locale.ENGLISH);
        if (this.headers.containsKey(lowerCase)) {
            this.headers.get(lowerCase).add(str2);
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2.trim());
        this.headers.put(lowerCase, arrayList);
    }

    public byte[] getBody() {
        return this.body;
    }

    public String getBodyStr() {
        return this.bodyStr;
    }

    public String getFirstHeaderValue(String str) {
        if (!this.headers.containsKey(str) || this.headers.get(str).size() <= 0) {
            return null;
        }
        return this.headers.get(str).get(0);
    }

    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    public void parse(JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject.getJSONObject("header");
        for (Map.Entry<String, Object> entry : jSONObject2.entrySet()) {
            if (entry.getValue() instanceof String) {
                addHeader(entry.getKey(), (String) entry.getValue());
            } else if (entry.getValue() instanceof List) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    addHeader(entry.getKey(), (String) it.next());
                }
            }
        }
        String string = jSONObject2.getString("content-type");
        Charset charsetForName = SdkConstant.CLOUDAPI_ENCODING;
        if (string != null) {
            try {
                String[] strArrSplit = string.toLowerCase().split(i.f9802b);
                for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                    if (strArrSplit[i2].contains("charset")) {
                        String str = strArrSplit[i2];
                        charsetForName = Charset.forName(str.substring(str.indexOf(ContainerUtils.KEY_VALUE_DELIMITER)));
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        String string2 = jSONObject.getString("body");
        this.bodyStr = string2;
        this.body = string2.getBytes(charsetForName);
    }

    public void setBody(byte[] bArr) {
        this.body = bArr;
    }

    public void setHeaders(Map<String, List<String>> map) {
        this.headers = map;
    }
}
