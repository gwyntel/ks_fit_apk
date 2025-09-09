package com.alibaba.sdk.android.openaccount.ui.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import com.ali.user.mobile.filter.PreHandlerCallback;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountTaobaoUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.NoPasswordLoginActivity;
import com.alibaba.sdk.android.openaccount.ui.util.OpenAccountUIUtils;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class LoginByIVTokenTask extends TaskWithDialog<Void, Void, Result<LoginResult>> {
    private String actionType;

    @Autowired
    private ConfigService configService;
    private String loginId;
    LoginCallback mLoginCallback;

    @Autowired
    private SessionManagerService sessionManagerService;
    private String token;

    public LoginByIVTokenTask(Activity activity, String str, String str2, String str3) {
        super(activity);
        this.token = str;
        this.loginId = str2;
        this.actionType = str3;
    }

    private void callbackFailureTaobao() {
        WeakReference<PreHandlerCallback> weakReference;
        if (!this.configService.openTaobaoUILogin() || (weakReference = OpenAccountTaobaoUIServiceImpl._preHandlerCallback) == null) {
            return;
        }
        weakReference.get().onFail(0, (Map) null);
    }

    private void callbackSuccessTaobao() {
        WeakReference<PreHandlerCallback> weakReference;
        if (!this.configService.openTaobaoUILogin() || (weakReference = OpenAccountTaobaoUIServiceImpl._preHandlerCallback) == null || weakReference.get() == null) {
            return;
        }
        OpenAccountTaobaoUIServiceImpl._preHandlerCallback.get().onSuccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTipDialog(String str) {
        Context context = this.context;
        if (context instanceof LoginActivity) {
            LoginActivity loginActivity = (LoginActivity) context;
            if (loginActivity.isFinishing()) {
                loginActivity.finishWithoutCallback();
            } else {
                new AlertDialog.Builder(this.context).setMessage(str).setPositiveButton(ResourceUtils.getString(this.context, "ali_sdk_openaccount_dynamic_text_iknow"), new DialogInterface.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        LoginByIVTokenTask.this.loginSuccessAction();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask.3
                    @Override // android.content.DialogInterface.OnCancelListener
                    public void onCancel(DialogInterface dialogInterface) {
                        LoginByIVTokenTask.this.loginSuccessAction();
                    }
                }).show();
            }
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
        this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask.1
            @Override // java.lang.Runnable
            public void run() {
                if (((TaskWithDialog) LoginByIVTokenTask.this).showDialog) {
                    ToastUtils.toastSystemError(((TaskWithDialog) LoginByIVTokenTask.this).context);
                }
            }
        });
    }

    protected LoginCallback getLoginCallback() {
        LoginCallback loginCallback = this.mLoginCallback;
        if (loginCallback != null) {
            return loginCallback;
        }
        LoginCallback loginCallback2 = OpenAccountUIServiceImpl._loginCallback;
        if (loginCallback2 != null) {
            return loginCallback2;
        }
        return null;
    }

    protected void loginSuccess() {
        LoginCallback loginCallback = getLoginCallback();
        if (loginCallback != null) {
            loginCallback.onSuccess(this.sessionManagerService.getSession());
        }
    }

    protected void loginSuccessAction() {
        loginSuccess();
        Context context = this.context;
        if (context instanceof LoginActivity) {
            ((LoginActivity) context).finishWithoutCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Result<LoginResult> asyncExecute(Void... voidArr) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.token) && !TextUtils.isEmpty(this.loginId) && !TextUtils.isEmpty(this.actionType)) {
            map.put("ivToken", this.token);
            map.put("loginId", this.loginId);
            map.put("actionType", this.actionType);
        }
        return OpenAccountUtils.toLoginResult(RpcUtils.pureInvokeWithRiskControlInfo("loginByIvTokenRequest", map, "loginbyivtoken"));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(final Result<LoginResult> result) {
        LoginResult loginResult;
        super.onPostExecute((LoginByIVTokenTask) result);
        try {
            if (result == null) {
                if (this.showDialog) {
                    ToastUtils.toastSystemError(this.context);
                }
                callbackFailureTaobao();
                return;
            }
            if (result.isSuccess() && (loginResult = result.data) != null && loginResult.loginSuccessResult != null) {
                SessionData sessionDataCreateSessionDataFromLoginSuccessResult = OpenAccountUtils.createSessionDataFromLoginSuccessResult(loginResult.loginSuccessResult);
                if (sessionDataCreateSessionDataFromLoginSuccessResult.scenario == null) {
                    sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 1;
                }
                this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
                boolean zUpdateHistoryAccounts = OpenAccountUIUtils.updateHistoryAccounts(result.data.userInputName);
                Context context = this.context;
                if (context instanceof LoginActivity) {
                    if (!OpenAccountUIConfigs.AccountPasswordLoginFlow.showTipAlertAfterLogin) {
                        loginSuccessAction();
                        return;
                    } else if (zUpdateHistoryAccounts) {
                        loginSuccessAction();
                        return;
                    } else {
                        ((LoginActivity) context).runOnUiThread(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask.2
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.lang.Runnable
                            public void run() {
                                LoginByIVTokenTask.this.showTipDialog(String.format(ResourceUtils.getString(((TaskWithDialog) LoginByIVTokenTask.this).context.getApplicationContext(), "ali_sdk_openaccount_dynamic_text_alert_msg_after_login"), ((LoginResult) result.data).userInputName));
                            }
                        });
                        return;
                    }
                }
                if (context instanceof NoPasswordLoginActivity) {
                    loginSuccess();
                    ((NoPasswordLoginActivity) this.context).finishWithoutCallback();
                    return;
                } else {
                    if (!(context instanceof Activity)) {
                        loginSuccess();
                        return;
                    }
                    loginSuccess();
                    callbackSuccessTaobao();
                    Activity activity = (Activity) this.context;
                    if (activity != null) {
                        activity.finish();
                        return;
                    }
                    return;
                }
            }
            callbackFailureTaobao();
            String str = result.message;
            if (str == null || str.length() <= 0) {
                if (this.showDialog) {
                    ToastUtils.toastSystemError(this.context);
                }
            } else if (this.showDialog) {
                ToastUtils.toast(this.context, result.message, result.code);
            }
        } catch (Throwable th) {
            th.printStackTrace();
            if (this.showDialog) {
                ToastUtils.toastSystemError(this.context);
            }
            callbackFailureTaobao();
        }
    }

    public LoginByIVTokenTask(Activity activity, String str, String str2, String str3, boolean z2, LoginCallback loginCallback) {
        super(activity);
        this.token = str;
        this.loginId = str2;
        this.actionType = str3;
        this.showDialog = z2;
        this.mLoginCallback = loginCallback;
    }
}
