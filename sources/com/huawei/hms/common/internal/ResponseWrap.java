package com.huawei.hms.common.internal;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ResponseWrap {

    /* renamed from: a, reason: collision with root package name */
    private String f15990a;

    /* renamed from: b, reason: collision with root package name */
    private ResponseHeader f15991b;

    public ResponseWrap(ResponseHeader responseHeader) {
        this.f15991b = responseHeader;
    }

    public boolean fromJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f15991b.setStatusCode(JsonUtil.getIntValue(jSONObject, "status_code"));
            this.f15991b.setErrorCode(JsonUtil.getIntValue(jSONObject, NativeProtocol.BRIDGE_ARG_ERROR_CODE));
            this.f15991b.setErrorReason(JsonUtil.getStringValue(jSONObject, "error_reason"));
            this.f15991b.setSrvName(JsonUtil.getStringValue(jSONObject, "srv_name"));
            this.f15991b.setApiName(JsonUtil.getStringValue(jSONObject, "api_name"));
            this.f15991b.setAppID(JsonUtil.getStringValue(jSONObject, "app_id"));
            this.f15991b.setPkgName(JsonUtil.getStringValue(jSONObject, "pkg_name"));
            this.f15991b.setSessionId(JsonUtil.getStringValue(jSONObject, "session_id"));
            this.f15991b.setTransactionId(JsonUtil.getStringValue(jSONObject, "transaction_id"));
            this.f15991b.setResolution(JsonUtil.getStringValue(jSONObject, "resolution"));
            this.f15990a = JsonUtil.getStringValue(jSONObject, "body");
            return true;
        } catch (JSONException e2) {
            HMSLog.e("ResponseWrap", "fromJson failed: " + e2.getMessage());
            return false;
        }
    }

    public String getBody() {
        if (TextUtils.isEmpty(this.f15990a)) {
            this.f15990a = new JSONObject().toString();
        }
        return this.f15990a;
    }

    public ResponseHeader getResponseHeader() {
        return this.f15991b;
    }

    public void setBody(String str) {
        this.f15990a = str;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.f15991b = responseHeader;
    }

    public String toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status_code", this.f15991b.getStatusCode());
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, this.f15991b.getErrorCode());
            jSONObject.put("error_reason", this.f15991b.getErrorReason());
            jSONObject.put("srv_name", this.f15991b.getSrvName());
            jSONObject.put("api_name", this.f15991b.getApiName());
            jSONObject.put("app_id", this.f15991b.getAppID());
            jSONObject.put("pkg_name", this.f15991b.getPkgName());
            jSONObject.put("transaction_id", this.f15991b.getTransactionId());
            jSONObject.put("resolution", this.f15991b.getResolution());
            String sessionId = this.f15991b.getSessionId();
            if (!TextUtils.isEmpty(sessionId)) {
                jSONObject.put("session_id", sessionId);
            }
            if (!TextUtils.isEmpty(this.f15990a)) {
                jSONObject.put("body", this.f15990a);
            }
        } catch (JSONException e2) {
            HMSLog.e("ResponseWrap", "toJson failed: " + e2.getMessage());
        }
        return jSONObject.toString();
    }

    public String toString() {
        return "ResponseWrap{body='" + this.f15990a + "', responseHeader=" + this.f15991b + '}';
    }
}
