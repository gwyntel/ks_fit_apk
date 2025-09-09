package com.alibaba.sdk.android.openaccount.task;

import android.content.Context;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.Pluto;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class TokenLoginTask extends InitWaitTask {
    public TokenLoginTask(Context context, final String str, final LoginCallback loginCallback) {
        super(context, loginCallback, new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.TokenLoginTask.1
            @Override // java.lang.Runnable
            public void run() {
                HashMap map = new HashMap();
                map.put("token", str);
                RpcResponse rpcResponsePureInvokeWithRiskControlInfo = RpcUtils.pureInvokeWithRiskControlInfo("loginByTokenRequest", map, "loginbytoken");
                int i2 = rpcResponsePureInvokeWithRiskControlInfo.code;
                if (i2 != 1) {
                    CommonUtils.onFailure(loginCallback, i2, rpcResponsePureInvokeWithRiskControlInfo.message);
                    return;
                }
                Result<LoginResult> loginResult = OpenAccountUtils.toLoginResult(rpcResponsePureInvokeWithRiskControlInfo);
                LoginResult loginResult2 = loginResult.data;
                if (loginResult2 == null || loginResult2.loginSuccessResult == null) {
                    Message messageCreateMessage = MessageUtils.createMessage(10010, "Null Response " + loginResult.message + "[" + loginResult.code + "]");
                    CommonUtils.onFailure(loginCallback, messageCreateMessage.code, messageCreateMessage.message);
                    return;
                }
                SessionData sessionDataCreateSessionDataFromLoginSuccessResult = OpenAccountUtils.createSessionDataFromLoginSuccessResult(loginResult2.loginSuccessResult);
                if (sessionDataCreateSessionDataFromLoginSuccessResult.scenario == null) {
                    sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 1;
                }
                Pluto pluto = Pluto.DEFAULT_INSTANCE;
                final SessionManagerService sessionManagerService = (SessionManagerService) pluto.getBean(SessionManagerService.class);
                ResultCode resultCodeUpdateSession = sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
                if (resultCodeUpdateSession.isSuccess()) {
                    ((ExecutorService) pluto.getBean(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.TokenLoginTask.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            loginCallback.onSuccess(sessionManagerService.getSession());
                        }
                    });
                    return;
                }
                Message messageCreateMessage2 = MessageUtils.createMessage(10010, resultCodeUpdateSession.message + "[" + resultCodeUpdateSession.code + "]");
                CommonUtils.onFailure(loginCallback, messageCreateMessage2.code, messageCreateMessage2.message);
            }
        }, UTConstants.E_TOKEN_LOGIN);
    }

    @Override // com.alibaba.sdk.android.openaccount.task.InitWaitTask, com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
        Message messageCreateMessage = MessageUtils.createMessage(10010, th == null ? "" : th.getMessage());
        CommonUtils.onFailure(this.failureCallback, messageCreateMessage.code, messageCreateMessage.message);
    }
}
