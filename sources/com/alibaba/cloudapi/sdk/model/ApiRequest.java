package com.alibaba.cloudapi.sdk.model;

import com.alibaba.cloudapi.sdk.enums.HttpConnectionModel;
import com.alibaba.cloudapi.sdk.enums.HttpMethod;
import com.alibaba.cloudapi.sdk.enums.ParamPosition;
import com.alibaba.cloudapi.sdk.enums.Scheme;
import com.alibaba.cloudapi.sdk.enums.WebSocketApiType;
import com.alibaba.cloudapi.sdk.exception.SdkException;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ApiRequest extends ApiHttpMessage {
    private Date currentDate;
    private String host;
    private HttpMethod method;
    private String path;
    private Scheme scheme;
    private String signatureMethod;
    private String url;
    private HttpConnectionModel httpConnectionMode = HttpConnectionModel.SINGER_CONNECTION;
    private WebSocketApiType webSocketApiType = WebSocketApiType.COMMON;
    private Map<String, String> pathParams = new HashMap();
    private Map<String, String> querys = new HashMap();
    private Map<String, String> formParams = new HashMap();
    private boolean isBase64BodyViaWebsocket = false;

    /* renamed from: com.alibaba.cloudapi.sdk.model.ApiRequest$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$cloudapi$sdk$enums$ParamPosition;

        static {
            int[] iArr = new int[ParamPosition.values().length];
            $SwitchMap$com$alibaba$cloudapi$sdk$enums$ParamPosition = iArr;
            try {
                iArr[ParamPosition.HEAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$cloudapi$sdk$enums$ParamPosition[ParamPosition.PATH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$cloudapi$sdk$enums$ParamPosition[ParamPosition.QUERY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$alibaba$cloudapi$sdk$enums$ParamPosition[ParamPosition.BODY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public ApiRequest(HttpMethod httpMethod, String str) {
        this.method = httpMethod;
        this.path = str;
    }

    public void addParam(String str, String str2, ParamPosition paramPosition, boolean z2) {
        Map<String, String> map;
        if (str2 == null) {
            if (z2) {
                throw new SdkException(String.format("param %s is not nullable, please check your codes", str));
            }
            return;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$alibaba$cloudapi$sdk$enums$ParamPosition[paramPosition.ordinal()];
        if (i2 == 1) {
            addHeader(str, str2);
            return;
        }
        if (i2 == 2) {
            map = this.pathParams;
        } else if (i2 == 3) {
            map = this.querys;
        } else {
            if (i2 != 4) {
                throw new SdkException("unknown param position: " + paramPosition);
            }
            map = this.formParams;
        }
        map.put(str, str2);
    }

    public ApiRequest duplicate() {
        ApiRequest apiRequest = new ApiRequest(this.method, this.path, this.body);
        apiRequest.scheme = this.scheme;
        String str = this.host;
        if (str != null) {
            apiRequest.host = new String(str);
        }
        String str2 = this.url;
        if (str2 != null) {
            apiRequest.url = new String(str2);
        }
        HashMap map = new HashMap();
        apiRequest.pathParams = map;
        map.putAll(this.pathParams);
        HashMap map2 = new HashMap();
        apiRequest.headers = map2;
        map2.putAll(this.headers);
        HashMap map3 = new HashMap();
        apiRequest.querys = map3;
        map3.putAll(this.querys);
        HashMap map4 = new HashMap();
        apiRequest.formParams = map4;
        map4.putAll(this.formParams);
        String str3 = this.signatureMethod;
        if (str3 != null) {
            apiRequest.signatureMethod = new String(str3);
        }
        apiRequest.webSocketApiType = this.webSocketApiType;
        apiRequest.httpConnectionMode = this.httpConnectionMode;
        apiRequest.isBase64BodyViaWebsocket = this.isBase64BodyViaWebsocket;
        return apiRequest;
    }

    public Date getCurrentDate() {
        return this.currentDate;
    }

    public Map<String, String> getFormParams() {
        return this.formParams;
    }

    public String getHost() {
        return this.host;
    }

    public HttpConnectionModel getHttpConnectionMode() {
        return this.httpConnectionMode;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public Map<String, String> getPathParams() {
        return this.pathParams;
    }

    public Map<String, String> getQuerys() {
        return this.querys;
    }

    public Scheme getScheme() {
        return this.scheme;
    }

    public String getSignatureMethod() {
        return this.signatureMethod;
    }

    public String getUrl() {
        return this.url;
    }

    public WebSocketApiType getWebSocketApiType() {
        return this.webSocketApiType;
    }

    public boolean isBase64BodyViaWebsocket() {
        return this.isBase64BodyViaWebsocket;
    }

    @Override // com.alibaba.cloudapi.sdk.model.ApiHttpMessage
    public void parse(JSONObject jSONObject) {
        super.parse(jSONObject);
    }

    public void setBase64BodyViaWebsocket(boolean z2) {
        this.isBase64BodyViaWebsocket = z2;
    }

    public void setCurrentDate(Date date) {
        this.currentDate = date;
    }

    public void setFormParams(Map<String, String> map) {
        this.formParams = map;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setHttpConnectionMode(HttpConnectionModel httpConnectionModel) {
        this.httpConnectionMode = httpConnectionModel;
    }

    public void setMethod(HttpMethod httpMethod) {
        this.method = httpMethod;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void setPathParams(Map<String, String> map) {
        this.pathParams = map;
    }

    public void setQuerys(Map<String, String> map) {
        this.querys = map;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public void setSignatureMethod(String str) {
        this.signatureMethod = str;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setWebSocketApiType(WebSocketApiType webSocketApiType) {
        this.webSocketApiType = webSocketApiType;
    }

    public ApiRequest(HttpMethod httpMethod, String str, byte[] bArr) {
        this.method = httpMethod;
        this.path = str;
        this.body = bArr;
    }

    public ApiRequest(JSONObject jSONObject) {
        parse(jSONObject);
    }
}
