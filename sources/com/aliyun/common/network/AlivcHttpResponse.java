package com.aliyun.common.network;

import android.text.TextUtils;
import com.aliyun.aio.keep.CalledByNative;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CalledByNative
/* loaded from: classes2.dex */
public class AlivcHttpResponse {
    private String body;
    private Map<String, List<String>> responseHeaderMap;
    private int statusCode;
    private String statusMessage;
    private String[] responseHeaderKeyArray = null;
    protected final AlivcHttpMetrics metrics = new AlivcHttpMetrics();

    public AlivcHttpResponse(int i2, String str) {
        this.statusCode = i2;
        this.statusMessage = str;
    }

    private void initResponseHeaderKeyArray() {
        if (this.responseHeaderMap == null || this.responseHeaderKeyArray != null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : this.responseHeaderMap.keySet()) {
            if (!TextUtils.isEmpty(str)) {
                arrayList.add(str);
            }
        }
        this.responseHeaderKeyArray = (String[]) arrayList.toArray(new String[0]);
    }

    public String getBody() {
        return this.body;
    }

    protected AlivcHttpMetrics getMetrics() {
        return this.metrics;
    }

    public String getResponseHeaderKey(int i2) {
        initResponseHeaderKeyArray();
        return (this.responseHeaderKeyArray == null || i2 < 0 || i2 >= getResponseHeaderSize()) ? "" : this.responseHeaderKeyArray[i2];
    }

    public int getResponseHeaderSize() {
        initResponseHeaderKeyArray();
        String[] strArr = this.responseHeaderKeyArray;
        if (strArr != null) {
            return strArr.length;
        }
        return 0;
    }

    public String getResponseHeaderValue(int i2) {
        List<String> list;
        String responseHeaderKey = getResponseHeaderKey(i2);
        return (TextUtils.isEmpty(responseHeaderKey) || (list = this.responseHeaderMap.get(responseHeaderKey)) == null || list.isEmpty()) ? "" : list.get(0);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public void setResponseHeaders(Map<String, List<String>> map) {
        this.responseHeaderMap = map;
        this.responseHeaderKeyArray = null;
    }
}
