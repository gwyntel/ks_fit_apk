package com.meizu.cloud.pushsdk.platform.message;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class BasicPushStatus implements Serializable {
    public static final String SUCCESS_CODE = "200";
    public static final String TAG = "BasicPushStatus";
    public String code;
    public String message;

    public BasicPushStatus() {
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    protected JSONObject parse(String str) {
        JSONObject jSONObject = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            try {
                if (!jSONObject2.isNull("code")) {
                    setCode(jSONObject2.getString("code"));
                }
                if (!jSONObject2.isNull("message")) {
                    setMessage(jSONObject2.getString("message"));
                }
                return jSONObject2;
            } catch (JSONException e2) {
                e = e2;
                jSONObject = jSONObject2;
                DebugLogger.e(TAG, "covert json error " + e.getMessage());
                return jSONObject;
            }
        } catch (JSONException e3) {
            e = e3;
        }
    }

    public abstract void parseValueData(JSONObject jSONObject) throws JSONException;

    public void setCode(String str) {
        this.code = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String toString() {
        return "BasicPushStatus{code='" + this.code + "', message='" + this.message + "'}";
    }

    public BasicPushStatus(String str) {
        JSONObject jSONObject = parse(str);
        if (jSONObject == null || !"200".equals(this.code) || jSONObject.isNull("value")) {
            return;
        }
        try {
            parseValueData(jSONObject.getJSONObject("value"));
        } catch (JSONException e2) {
            DebugLogger.e(TAG, "parse value data error " + e2.getMessage() + " json " + str);
        }
    }
}
