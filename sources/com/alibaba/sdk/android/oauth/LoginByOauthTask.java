package com.alibaba.sdk.android.oauth;

import android.content.Context;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class LoginByOauthTask extends TaskWithDialog<Void, Void, Void> {

    @Autowired
    private ExecutorService executorService;
    private LoginByOauthRequest loginByOauthRequest;
    private LoginCallback loginCallback;
    private OauthServiceProvider oauthServiceProvider;
    private Map<String, Object> otherInfo;

    @Autowired
    private SessionManagerService sessionManagerService;

    public LoginByOauthTask(Context context, LoginCallback loginCallback, Map map, LoginByOauthRequest loginByOauthRequest, OauthServiceProvider oauthServiceProvider) {
        super(context);
        this.loginCallback = loginCallback;
        this.loginByOauthRequest = loginByOauthRequest;
        this.otherInfo = map;
        this.oauthServiceProvider = oauthServiceProvider;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logoutOauthAccount() {
        OauthServiceProvider oauthServiceProvider = this.oauthServiceProvider;
        if (oauthServiceProvider != null) {
            oauthServiceProvider.logout(this.context, new LogoutCallback() { // from class: com.alibaba.sdk.android.oauth.LoginByOauthTask.3
                @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
                public void onFailure(int i2, String str) {
                }

                @Override // com.alibaba.sdk.android.openaccount.callback.LogoutCallback
                public void onSuccess() {
                }
            });
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
        this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.oauth.LoginByOauthTask.4
            @Override // java.lang.Runnable
            public void run() {
                if (LoginByOauthTask.this.loginCallback != null) {
                    LoginByOauthTask.this.loginCallback.onFailure(10010, MessageUtils.getMessageContent(10010, new Object[0]));
                }
                LoginByOauthTask.this.logoutOauthAccount();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Void asyncExecute(Void... voidArr) {
        LoginResult loginResult;
        HashMap map = new HashMap();
        map.put("oauthPlateform", Integer.valueOf(this.loginByOauthRequest.oauthPlateform));
        map.put("accessToken", this.loginByOauthRequest.accessToken);
        map.put("openId", this.loginByOauthRequest.openId);
        map.put("oauthAppKey", this.loginByOauthRequest.oauthAppKey);
        map.put(AlinkConstants.KEY_TOKEN_TYPE, this.loginByOauthRequest.tokenType);
        map.put("authCode", this.loginByOauthRequest.authCode);
        map.put("userData", this.loginByOauthRequest.userData);
        final Result<LoginResult> loginResult2 = OpenAccountUtils.toLoginResult(RpcUtils.pureInvokeWithRiskControlInfo("loginByOauthRequest", map, "loginbyoauth"));
        final int i2 = loginResult2.code;
        if (!loginResult2.isSuccess() || (loginResult = loginResult2.data) == null || loginResult.loginSuccessResult == null) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.oauth.LoginByOauthTask.1
                @Override // java.lang.Runnable
                public void run() {
                    if (LoginByOauthTask.this.loginCallback != null) {
                        LoginByOauthTask.this.loginCallback.onFailure(i2, loginResult2.message);
                    }
                    LoginByOauthTask.this.logoutOauthAccount();
                }
            });
            return null;
        }
        SessionData sessionDataCreateSessionDataFromLoginSuccessResult = OpenAccountUtils.createSessionDataFromLoginSuccessResult(loginResult.loginSuccessResult);
        if (sessionDataCreateSessionDataFromLoginSuccessResult.scenario == null) {
            sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 1;
        }
        if (this.otherInfo != null && sessionDataCreateSessionDataFromLoginSuccessResult.otherInfo == null) {
            sessionDataCreateSessionDataFromLoginSuccessResult.otherInfo = new HashMap();
        }
        this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
        this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.oauth.LoginByOauthTask.2
            @Override // java.lang.Runnable
            public void run() {
                if (LoginByOauthTask.this.loginCallback != null) {
                    LoginByOauthTask.this.loginCallback.onSuccess(LoginByOauthTask.this.sessionManagerService.getSession());
                }
            }
        });
        return null;
    }
}
