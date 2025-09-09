package com.alibaba.sdk.android.openaccount.session.impl;

import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSessionService;
import com.alibaba.sdk.android.openaccount.initialization.InitializationHandler;
import com.alibaba.sdk.android.openaccount.initialization.InitializationServiceClient;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.model.RefreshToken;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.ut.UserTrackerService;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SessionServiceImpl implements OpenAccountSessionService, InitializationHandler<ResultCode> {

    @Autowired
    private ExecutorService executorService;
    private volatile boolean forceRefreshOnce;

    @Autowired
    private InitializationServiceClient initializationServiceClient;

    @Autowired
    private SessionManagerService sessionManagerService;

    @Autowired
    private UserTrackerService userTrackerService;

    private boolean isRefreshTokenUpgraded(SessionData sessionData) {
        String str;
        String str2;
        RefreshToken refreshToken = this.sessionManagerService.getRefreshToken();
        return (refreshToken == null || (str = refreshToken.token) == null || str.startsWith("OA") || (str2 = sessionData.refreshToken) == null || !str2.startsWith("OA")) ? false : true;
    }

    private ResultCode processRefreshSessionResult(JSONObject jSONObject) {
        int iOptInt = jSONObject.optInt("code", -1);
        String strOptString = jSONObject.optString("message");
        try {
            if (iOptInt != 1) {
                if (iOptInt == 26107) {
                    this.sessionManagerService.removeSession();
                    AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "refresh token expired, local session will be removed");
                    sendSessionUTHint(jSONObject, false, null);
                    return ResultCode.create(100, strOptString);
                }
                sendSessionUTHint(jSONObject, false, null);
                Message messageCreateMessage = MessageUtils.createMessage(10010, "refresh session error code = " + iOptInt + " message = " + strOptString);
                AliSDKLogger.log(OpenAccountConstants.LOG_TAG, messageCreateMessage);
                return ResultCode.create(messageCreateMessage);
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
            if (jSONObjectOptJSONObject == null) {
                sendSessionUTHint(jSONObject, false, null);
                AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "Null data from refresh sid response with code = 1");
                return ResultCode.create(MessageUtils.createMessage(10010, new Object[0]));
            }
            SessionData sessionDataCreateSessionDataFromRefreshSidResponse = OpenAccountUtils.createSessionDataFromRefreshSidResponse(jSONObjectOptJSONObject);
            if (sessionDataCreateSessionDataFromRefreshSidResponse != null) {
                if (sessionDataCreateSessionDataFromRefreshSidResponse.scenario == null) {
                    OpenAccountSession session = this.sessionManagerService.getSession();
                    sessionDataCreateSessionDataFromRefreshSidResponse.scenario = Integer.valueOf(session != null ? session.getScenario() : 0);
                }
                sendSessionUTHint(jSONObject, isRefreshTokenUpgraded(sessionDataCreateSessionDataFromRefreshSidResponse), null);
                return this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromRefreshSidResponse);
            }
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "null session data is created from the refreshSid response " + jSONObject);
            sendSessionUTHint(jSONObject, false, null);
            return ResultCode.create(MessageUtils.createMessage(10010, new Object[0]));
        } catch (Exception e2) {
            Message messageCreateMessage2 = MessageUtils.createMessage(10010, " code = " + iOptInt + " message = " + strOptString + " " + e2.getMessage());
            AliSDKLogger.log(OpenAccountConstants.LOG_TAG, messageCreateMessage2, e2);
            sendSessionUTHint(jSONObject, false, messageCreateMessage2.message);
            return ResultCode.create(messageCreateMessage2);
        }
    }

    private void sendSessionUTHint(JSONObject jSONObject, boolean z2, String str) {
        HashMap map = new HashMap();
        map.put("code", jSONObject.optString("code"));
        map.put("traceId", jSONObject.optString("traceId"));
        if (str == null) {
            str = jSONObject.optString("message");
        }
        map.put("msg", str);
        this.userTrackerService.sendCustomHit(UTConstants.E_SDK_CONNECT_RESULT, 19999, "session", 0L, z2 ? UTConstants.E_SDK_CONNECT_SESSION_UPGRADED : jSONObject.optInt("code", -1) == 1 ? UTConstants.E_SDK_CONNECT_SESSION_SUCCESS : UTConstants.E_SDK_CONNECT_SESSION_FAILED, map);
    }

    @Override // com.alibaba.sdk.android.openaccount.initialization.InitializationHandler
    public Object createRequestParameters() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.putOpt("refreshToken", this.sessionManagerService.getRefreshToken().token);
            jSONObject.putOpt("sid", this.sessionManagerService.getSessionId());
        } catch (JSONException e2) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to create session refresh requirement", e2);
        }
        return jSONObject;
    }

    @Override // com.alibaba.sdk.android.openaccount.initialization.InitializationHandler
    public String getRequestParameterKey() {
        return "refreshSid";
    }

    @Override // com.alibaba.sdk.android.openaccount.initialization.InitializationHandler
    public int getRequestRequirement() {
        if (this.forceRefreshOnce) {
            this.forceRefreshOnce = false;
            return 1;
        }
        boolean zIsRefreshTokenExpired = this.sessionManagerService.isRefreshTokenExpired();
        if (!zIsRefreshTokenExpired && this.sessionManagerService.isSessionExpired()) {
            return 1;
        }
        if (!AliSDKLogger.isDebugEnabled()) {
            return 2;
        }
        AliSDKLogger.d(OpenAccountConstants.LOG_TAG, "refreshTokenExpired = " + zIsRefreshTokenExpired + "isSessionExpired = " + this.sessionManagerService.isSessionExpired());
        return 2;
    }

    @Override // com.alibaba.sdk.android.openaccount.initialization.InitializationHandler
    public int getRequestServiceType() {
        return 2;
    }

    @Override // com.alibaba.sdk.android.openaccount.initialization.InitializationHandler
    public String getResponseValueKey() {
        return "refreshSid";
    }

    @Override // com.alibaba.sdk.android.openaccount.OpenAccountSessionService
    public Result<String> getSessionId() {
        try {
            String sessionId = this.sessionManagerService.getSessionId();
            if (AliSDKLogger.isDebugEnabled()) {
                AliSDKLogger.d(OpenAccountConstants.LOG_TAG, "get sid: " + sessionId);
            }
            return sessionId == null ? Result.result(MessageUtils.createMessage(10011, new Object[0])) : Result.result(sessionId);
        } catch (Exception e2) {
            AliSDKLogger.e(OpenAccountConstants.LOG_TAG, "fail to get session Id", e2);
            return Result.result(MessageUtils.createMessage(10010, new Object[0]));
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.initialization.InitializationHandler
    public void handleResponseError(int i2, String str) {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0036 A[Catch: all -> 0x0027, TRY_LEAVE, TryCatch #0 {all -> 0x0027, blocks: (B:12:0x001e, B:22:0x0050, B:24:0x0056, B:25:0x0074, B:17:0x0029, B:19:0x0036), top: B:33:0x001e, outer: #1 }] */
    @Override // com.alibaba.sdk.android.openaccount.OpenAccountSessionService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized com.alibaba.sdk.android.openaccount.model.Result<java.lang.String> refreshSession(boolean r5) {
        /*
            r4 = this;
            r0 = 1
            r1 = 0
            monitor-enter(r4)
            com.alibaba.sdk.android.openaccount.session.SessionManagerService r2 = r4.sessionManagerService     // Catch: java.lang.Throwable -> L19
            boolean r2 = r2.isRefreshTokenExpired()     // Catch: java.lang.Throwable -> L19
            if (r2 == 0) goto L1c
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L19
            r0 = 10011(0x271b, float:1.4028E-41)
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r0, r5)     // Catch: java.lang.Throwable -> L19
            com.alibaba.sdk.android.openaccount.model.Result r5 = com.alibaba.sdk.android.openaccount.model.Result.result(r5)     // Catch: java.lang.Throwable -> L19
            monitor-exit(r4)
            return r5
        L19:
            r5 = move-exception
            goto L97
        L1c:
            if (r5 != 0) goto L29
            com.alibaba.sdk.android.openaccount.session.SessionManagerService r5 = r4.sessionManagerService     // Catch: java.lang.Throwable -> L27
            boolean r5 = r5.isSessionExpired()     // Catch: java.lang.Throwable -> L27
            if (r5 == 0) goto L50
            goto L29
        L27:
            r5 = move-exception
            goto L7a
        L29:
            r4.forceRefreshOnce = r0     // Catch: java.lang.Throwable -> L27
            com.alibaba.sdk.android.openaccount.initialization.InitializationServiceClient r5 = r4.initializationServiceClient     // Catch: java.lang.Throwable -> L27
            r5.request()     // Catch: java.lang.Throwable -> L27
            boolean r5 = com.alibaba.sdk.android.openaccount.util.CommonUtils.isNetworkAvailable()     // Catch: java.lang.Throwable -> L27
            if (r5 != 0) goto L50
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L27
            r2 = 10014(0x271e, float:1.4033E-41)
            java.lang.String r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.getMessageContent(r2, r5)     // Catch: java.lang.Throwable -> L27
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L27
            r3[r1] = r5     // Catch: java.lang.Throwable -> L27
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r2, r3)     // Catch: java.lang.Throwable -> L27
            int r2 = r5.code     // Catch: java.lang.Throwable -> L27
            java.lang.String r5 = r5.message     // Catch: java.lang.Throwable -> L27
            com.alibaba.sdk.android.openaccount.model.Result r5 = com.alibaba.sdk.android.openaccount.model.Result.result(r2, r5)     // Catch: java.lang.Throwable -> L27
            monitor-exit(r4)
            return r5
        L50:
            boolean r5 = com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.isDebugEnabled()     // Catch: java.lang.Throwable -> L27
            if (r5 == 0) goto L74
            java.lang.String r5 = "oa"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L27
            r2.<init>()     // Catch: java.lang.Throwable -> L27
            java.lang.String r3 = "refresh sid: "
            r2.append(r3)     // Catch: java.lang.Throwable -> L27
            com.alibaba.sdk.android.openaccount.model.Result r3 = r4.getSessionId()     // Catch: java.lang.Throwable -> L27
            T r3 = r3.data     // Catch: java.lang.Throwable -> L27
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Throwable -> L27
            r2.append(r3)     // Catch: java.lang.Throwable -> L27
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L27
            com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.d(r5, r2)     // Catch: java.lang.Throwable -> L27
        L74:
            com.alibaba.sdk.android.openaccount.model.Result r5 = r4.getSessionId()     // Catch: java.lang.Throwable -> L27
            monitor-exit(r4)
            return r5
        L7a:
            java.lang.String r5 = r5.getMessage()     // Catch: java.lang.Throwable -> L19
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L19
            r0[r1] = r5     // Catch: java.lang.Throwable -> L19
            r5 = 10010(0x271a, float:1.4027E-41)
            com.alibaba.sdk.android.openaccount.message.Message r5 = com.alibaba.sdk.android.openaccount.message.MessageUtils.createMessage(r5, r0)     // Catch: java.lang.Throwable -> L19
            java.lang.String r0 = "oa"
            com.alibaba.sdk.android.openaccount.trace.AliSDKLogger.log(r0, r5)     // Catch: java.lang.Throwable -> L19
            int r0 = r5.code     // Catch: java.lang.Throwable -> L19
            java.lang.String r5 = r5.message     // Catch: java.lang.Throwable -> L19
            com.alibaba.sdk.android.openaccount.model.Result r5 = com.alibaba.sdk.android.openaccount.model.Result.result(r0, r5)     // Catch: java.lang.Throwable -> L19
            monitor-exit(r4)
            return r5
        L97:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L19
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.openaccount.session.impl.SessionServiceImpl.refreshSession(boolean):com.alibaba.sdk.android.openaccount.model.Result");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.alibaba.sdk.android.openaccount.initialization.InitializationHandler
    public ResultCode handleResponseValue(JSONObject jSONObject) {
        if (AliSDKLogger.isDebugEnabled()) {
            AliSDKLogger.d(OpenAccountConstants.LOG_TAG, "handleInitSession response " + jSONObject);
        }
        return processRefreshSessionResult(jSONObject);
    }
}
