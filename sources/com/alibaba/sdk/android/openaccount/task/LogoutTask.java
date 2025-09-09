package com.alibaba.sdk.android.openaccount.task;

import android.content.Context;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OauthService;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.Pluto;
import java.util.HashMap;
import mtopsdk.mtop.intf.Mtop;

/* loaded from: classes2.dex */
public class LogoutTask extends InitWaitTask {
    public static final String TAG = "LogoutTask";

    public LogoutTask(Context context, final LogoutCallback logoutCallback) {
        super(context, logoutCallback, new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.LogoutTask.1
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                Pluto pluto = Pluto.DEFAULT_INSTANCE;
                SessionManagerService sessionManagerService = (SessionManagerService) pluto.getBean(SessionManagerService.class);
                if (sessionManagerService.isRefreshTokenExpired()) {
                    Message messageCreateMessage = MessageUtils.createMessage(10011, new Object[0]);
                    AliSDKLogger.log(OpenAccountConstants.LOG_TAG, messageCreateMessage);
                    CommonUtils.onFailure(logoutCallback, messageCreateMessage);
                    return;
                }
                if (!ConfigManager.getInstance().isAPIGateway()) {
                    Mtop.instance(OpenAccountSDK.getAndroidContext()).logout();
                }
                map.put("refreshToken", sessionManagerService.getRefreshToken().token);
                map.put("sessionId", sessionManagerService.getSessionId());
                RpcUtils.pureInvokeWithRiskControlInfo("logoutRequest", map, "logout");
                ResultCode resultCodeRemoveSession = ((SessionManagerService) pluto.getBean(SessionManagerService.class)).removeSession();
                if (resultCodeRemoveSession == null || !resultCodeRemoveSession.isSuccess()) {
                    CommonUtils.onFailure(logoutCallback, resultCodeRemoveSession);
                } else {
                    ((ExecutorService) pluto.getBean(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.LogoutTask.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            OauthService oauthService = (OauthService) Pluto.DEFAULT_INSTANCE.getBean(OauthService.class);
                            if (oauthService != null) {
                                oauthService.logoutAll(null, new LogoutCallback() { // from class: com.alibaba.sdk.android.openaccount.task.LogoutTask.1.1.1
                                    @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
                                    public void onFailure(int i2, String str) {
                                        AliSDKLogger.d(LogoutTask.TAG, "ignore. logout oauth fail" + i2 + ", " + str);
                                    }

                                    @Override // com.alibaba.sdk.android.openaccount.callback.LogoutCallback
                                    public void onSuccess() {
                                        AliSDKLogger.d(LogoutTask.TAG, "ignore. logout oauth success");
                                    }
                                });
                            } else {
                                AliSDKLogger.e(LogoutTask.TAG, "oauth service is null");
                            }
                            LogoutCallback logoutCallback2 = logoutCallback;
                            if (logoutCallback2 != null) {
                                logoutCallback2.onSuccess();
                            }
                        }
                    });
                }
            }
        }, UTConstants.E_LOGOUT, false);
    }

    @Override // com.alibaba.sdk.android.openaccount.task.InitWaitTask, com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
        Message messageCreateMessage = MessageUtils.createMessage(10010, th == null ? "" : th.getMessage());
        CommonUtils.onFailure(this.failureCallback, messageCreateMessage.code, messageCreateMessage.message);
    }
}
