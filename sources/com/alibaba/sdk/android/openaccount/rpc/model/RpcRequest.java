package com.alibaba.sdk.android.openaccount.rpc.model;

import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.util.JSONUtils;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RpcRequest {
    public String domain;
    public Map<String, Object> params;
    public String rpcReferer;
    public String target;
    public String version = "1.0";

    public static RpcRequest create(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.target = JSONUtils.optString(jSONObject, "target");
            String strOptString = JSONUtils.optString(jSONObject, "version");
            if (!TextUtils.isEmpty(strOptString)) {
                rpcRequest.version = strOptString;
            }
            rpcRequest.params = JSONUtils.toMap(jSONObject.optJSONObject("params"));
            return rpcRequest;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    public String toString() {
        return "RpcRequest{target='" + this.target + "', version='" + this.version + "', params=" + this.params + ", domain='" + this.domain + "', rpcReferer='" + this.rpcReferer + "'}";
    }
}
