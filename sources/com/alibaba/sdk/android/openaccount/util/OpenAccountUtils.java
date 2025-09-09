package com.alibaba.sdk.android.openaccount.util;

import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.CheckCodeResult;
import com.alibaba.sdk.android.openaccount.model.CheckOAExistResult;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.LoginSuccessResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.model.User;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class OpenAccountUtils {
    public static final String TAG = "oa.OpenAccountUtils";

    public static void copyOauthOtherInfo(JSONObject jSONObject, SessionData sessionData) {
        if (jSONObject == null) {
            return;
        }
        JSONArray jSONArrayNames = jSONObject.names();
        HashMap map = new HashMap();
        if (jSONArrayNames != null) {
            try {
                int length = jSONArrayNames.length();
                for (int i2 = 0; i2 < length; i2++) {
                    String strOptString = jSONArrayNames.optString(i2);
                    Object objOpt = jSONObject.opt(strOptString);
                    if (objOpt != null) {
                        map.put(strOptString, objOpt);
                    }
                }
            } catch (Exception e2) {
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "Fail to read oauth other info", e2);
            }
        }
        if (map.size() > 0) {
            sessionData.otherInfo.put(OpenAccountConstants.OAUTH_OTHER_INFO, map);
        }
    }

    public static void copyOpenAccountInfo(JSONObject jSONObject, SessionData sessionData) {
        Map<String, Object> map;
        if (jSONObject == null) {
            return;
        }
        sessionData.id = jSONObject.optString("id");
        sessionData.openId = jSONObject.optString("openId");
        sessionData.avatarUrl = jSONObject.optString("avatarUrl");
        sessionData.mobile = jSONObject.optString(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE);
        sessionData.nick = jSONObject.optString("nick");
        sessionData.mobileLocationCode = jSONObject.optString(AlinkConstants.KEY_MOBILE_LOCATION_CODE);
        sessionData.hasPassword = jSONObject.optBoolean("hasPassword");
        sessionData.email = jSONObject.optString("email");
        sessionData.displayName = jSONObject.optString("displayName");
        String strOptString = jSONObject.optString("loginId");
        if (!TextUtils.isEmpty(strOptString)) {
            try {
                sessionData.loginId = URLDecoder.decode(strOptString, "utf-8");
            } catch (Exception e2) {
                AliSDKLogger.e(TAG, e2.getMessage(), e2);
            }
        }
        JSONArray jSONArrayNames = jSONObject.names();
        HashMap map2 = new HashMap();
        if (jSONArrayNames != null) {
            try {
                int length = jSONArrayNames.length();
                for (int i2 = 0; i2 < length; i2++) {
                    String strOptString2 = jSONArrayNames.optString(i2);
                    Object objOpt = jSONObject.opt(strOptString2);
                    if (objOpt != null) {
                        map2.put(strOptString2, objOpt);
                    }
                }
            } catch (Exception e3) {
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "Fail to read other info from open account info", e3);
            }
        }
        if (map2.size() <= 0 || (map = sessionData.otherInfo) == null) {
            return;
        }
        map.put(OpenAccountConstants.OPEN_ACCOUNT_OTHER_INFO, map2);
    }

    public static SessionData createSessionDataFromLoginSuccessResult(LoginSuccessResult loginSuccessResult) {
        SessionData sessionData = new SessionData();
        sessionData.authCode = loginSuccessResult.token;
        sessionData.refreshToken = loginSuccessResult.refreshToken;
        sessionData.refreshTokenExpireTime = loginSuccessResult.reTokenExpireIn;
        sessionData.refreshTokenCreationTime = loginSuccessResult.reTokenCreationTime;
        sessionData.sessionId = loginSuccessResult.sid;
        sessionData.sessionExpireTime = loginSuccessResult.sidExpireIn;
        sessionData.sessionCreationTime = loginSuccessResult.sidCreationTime;
        sessionData.scenario = loginSuccessResult.scenario;
        sessionData.otherInfo = new HashMap();
        copyOpenAccountInfo(loginSuccessResult.openAccount, sessionData);
        copyOauthOtherInfo(loginSuccessResult.oauthOtherInfo, sessionData);
        return sessionData;
    }

    public static SessionData createSessionDataFromRefreshSidResponse(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        SessionData sessionData = new SessionData();
        String strOptString = JSONUtils.optString(jSONObject, "refreshToken");
        sessionData.refreshToken = strOptString;
        if (strOptString != null) {
            sessionData.refreshTokenCreationTime = Long.valueOf(System.currentTimeMillis());
            sessionData.refreshTokenExpireTime = JSONUtils.optInteger(jSONObject, "reTokenExpireIn");
        }
        sessionData.sessionId = JSONUtils.optString(jSONObject, "sid");
        sessionData.sessionCreationTime = Long.valueOf(System.currentTimeMillis());
        sessionData.sessionExpireTime = JSONUtils.optInteger(jSONObject, "sidExpireIn");
        sessionData.authCode = JSONUtils.optString(jSONObject, "authCode");
        Integer numOptInteger = JSONUtils.optInteger(jSONObject, "scenario");
        if (numOptInteger != null) {
            sessionData.scenario = numOptInteger;
        }
        sessionData.otherInfo = new HashMap();
        copyOpenAccountInfo(jSONObject.optJSONObject("newOpenAccount"), sessionData);
        copyOauthOtherInfo(jSONObject.optJSONObject("oauthOtherInfo"), sessionData);
        return sessionData;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isDigit(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [T, com.alibaba.sdk.android.openaccount.model.CheckOAExistResult] */
    public static Result<CheckOAExistResult> parseCheckOAResult(RpcResponse rpcResponse) {
        if (rpcResponse == null) {
            return null;
        }
        Result<CheckOAExistResult> result = new Result<>();
        try {
            result.code = rpcResponse.code;
            result.message = rpcResponse.message;
            result.type = rpcResponse.type;
            JSONObject jSONObject = rpcResponse.data;
            if (jSONObject != null) {
                ?? checkOAExistResult = new CheckOAExistResult();
                result.data = checkOAExistResult;
                checkOAExistResult.accountExist = JSONUtils.optBoolean(jSONObject, "accountExist").booleanValue();
                result.data.havanaExist = JSONUtils.optBoolean(jSONObject, "havanaExist").booleanValue();
                result.data.havanaId = JSONUtils.optString(jSONObject, "havanaId");
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("checkCodeResult");
                if (jSONObjectOptJSONObject != null) {
                    result.data.checkCodeResult = new CheckCodeResult();
                    result.data.checkCodeResult.clientVerifyData = jSONObjectOptJSONObject.optString("clientVerifyData");
                }
            }
            return result;
        } catch (Exception unused) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to parse response json " + rpcResponse);
            Message messageCreateMessage = MessageUtils.createMessage(18, new Object[0]);
            return Result.result(messageCreateMessage.code, messageCreateMessage.message);
        }
    }

    public static LoginResult parseLoginResult(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        LoginResult loginResult = new LoginResult();
        loginResult.loginSuccessResult = parseLoginSuccessResult(jSONObject);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("checkCodeResult");
        if (jSONObjectOptJSONObject != null) {
            CheckCodeResult checkCodeResult = new CheckCodeResult();
            loginResult.checkCodeResult = checkCodeResult;
            checkCodeResult.checkCodeId = JSONUtils.optString(jSONObjectOptJSONObject, "checkCodeId");
            checkCodeResult.checkCodeUrl = JSONUtils.optString(jSONObjectOptJSONObject, "checkCodeUrl");
            checkCodeResult.clientVerifyData = JSONUtils.optString(jSONObjectOptJSONObject, "clientVerifyData");
        }
        loginResult.doubleCheckToken = JSONUtils.optString(jSONObject, "doubleCheckToken");
        loginResult.doubleCheckUrl = JSONUtils.optString(jSONObject, "doubleCheckUrl");
        loginResult.smsCheckTrustToken = JSONUtils.optString(jSONObject, "smsCheckTrustToken");
        loginResult.ivCancelTrustToken = JSONUtils.optString(jSONObject, "ivCancelTrustToken");
        loginResult.ivRecreateAccountTrustToken = JSONUtils.optString(jSONObject, "ivRecreateAccountTrustToken");
        loginResult.mobileBindRequired = JSONUtils.optBoolean(jSONObject, "mobileBindRequired").booleanValue();
        loginResult.userInputName = JSONUtils.optString(jSONObject, "userInputName");
        return loginResult;
    }

    public static LoginSuccessResult parseLoginSuccessResult(JSONObject jSONObject) {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("loginSuccessResult");
        LoginSuccessResult loginSuccessResult = new LoginSuccessResult();
        if (jSONObjectOptJSONObject == null) {
            return loginSuccessResult;
        }
        loginSuccessResult.refreshToken = JSONUtils.optString(jSONObjectOptJSONObject, "refreshToken");
        loginSuccessResult.reTokenExpireIn = JSONUtils.optInteger(jSONObjectOptJSONObject, "reTokenExpireIn");
        Long lOptLong = JSONUtils.optLong(jSONObjectOptJSONObject, "reTokenCreationTime");
        loginSuccessResult.reTokenCreationTime = lOptLong;
        if (lOptLong == null) {
            loginSuccessResult.reTokenCreationTime = Long.valueOf(System.currentTimeMillis());
        }
        loginSuccessResult.sidExpireIn = JSONUtils.optInteger(jSONObjectOptJSONObject, "sidExpireIn");
        loginSuccessResult.sid = JSONUtils.optString(jSONObjectOptJSONObject, "sid");
        Long lOptLong2 = JSONUtils.optLong(jSONObjectOptJSONObject, "sidCreationTime");
        loginSuccessResult.sidCreationTime = lOptLong2;
        if (lOptLong2 == null) {
            loginSuccessResult.sidCreationTime = Long.valueOf(System.currentTimeMillis());
        }
        loginSuccessResult.token = JSONUtils.optString(jSONObjectOptJSONObject, "token");
        loginSuccessResult.scenario = JSONUtils.optInteger(jSONObjectOptJSONObject, "scenario");
        loginSuccessResult.openAccount = jSONObjectOptJSONObject.optJSONObject("openAccount");
        loginSuccessResult.oauthOtherInfo = jSONObjectOptJSONObject.optJSONObject("oauthOtherInfo");
        return loginSuccessResult;
    }

    public static User parseUserFromJSONObject(JSONObject jSONObject) {
        User user = new User();
        if (jSONObject == null) {
            return user;
        }
        user.id = jSONObject.optString("id");
        user.openId = jSONObject.optString("openId");
        user.avatarUrl = jSONObject.optString("avatarUrl");
        user.mobile = jSONObject.optString(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE);
        user.email = jSONObject.optString("email");
        user.displayName = jSONObject.optString("displayName");
        user.mobileLocationCode = jSONObject.optString(AlinkConstants.KEY_MOBILE_LOCATION_CODE);
        user.hasPassword = jSONObject.optBoolean("hasPassword");
        String strOptString = jSONObject.optString("loginId");
        if (!TextUtils.isEmpty(strOptString)) {
            try {
                user.nick = URLDecoder.decode(strOptString, "utf-8");
            } catch (Exception e2) {
                AliSDKLogger.e(TAG, e2.getMessage(), e2);
            }
        }
        JSONArray jSONArrayNames = jSONObject.names();
        HashMap map = new HashMap();
        if (jSONArrayNames != null) {
            try {
                int length = jSONArrayNames.length();
                for (int i2 = 0; i2 < length; i2++) {
                    String strOptString2 = jSONArrayNames.optString(i2);
                    Object objOpt = jSONObject.opt(strOptString2);
                    if (objOpt != null) {
                        map.put(strOptString2, objOpt);
                    }
                }
            } catch (Exception e3) {
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "Fail to read other info from open account info", e3);
            }
        }
        HashMap map2 = new HashMap();
        user.otherInfo = map2;
        map2.put(OpenAccountConstants.OPEN_ACCOUNT_OTHER_INFO, map);
        return user;
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [T, com.alibaba.sdk.android.openaccount.model.LoginResult] */
    public static Result<LoginResult> toLoginResult(RpcResponse rpcResponse) {
        if (rpcResponse == null) {
            return null;
        }
        Result<LoginResult> result = new Result<>();
        try {
            result.code = rpcResponse.code;
            result.message = rpcResponse.message;
            result.type = rpcResponse.type;
            result.data = parseLoginResult(rpcResponse.data);
            return result;
        } catch (Exception unused) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to parse response json " + rpcResponse);
            Message messageCreateMessage = MessageUtils.createMessage(18, new Object[0]);
            return Result.result(messageCreateMessage.code, messageCreateMessage.message);
        }
    }
}
