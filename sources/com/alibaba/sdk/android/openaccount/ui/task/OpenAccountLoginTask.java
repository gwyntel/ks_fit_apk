package com.alibaba.sdk.android.openaccount.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.ali.user.mobile.filter.PreHandlerCallback;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginDoubleCheckWebActivity;
import com.alibaba.sdk.android.openaccount.ui.ui.LoginIVWebActivity;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class OpenAccountLoginTask extends TaskWithDialog<Void, Void, Result<LoginResult>> {
    protected String cSessionId;
    protected String loginId;
    protected Activity mActivity;
    protected LoginCallback mLoginCallback;
    protected PreHandlerCallback mPreCallback;
    protected String nocToken;
    protected String password;

    @Autowired
    protected SessionManagerService sessionManagerService;
    protected String sig;

    public OpenAccountLoginTask(Activity activity, String str, String str2, String str3, String str4, String str5, LoginCallback loginCallback, PreHandlerCallback preHandlerCallback) {
        super(activity);
        this.loginId = str;
        this.password = str2;
        this.sig = str3;
        this.nocToken = str4;
        this.cSessionId = str5;
        this.mActivity = activity;
        this.mLoginCallback = loginCallback;
        this.mPreCallback = preHandlerCallback;
        this.showDialog = false;
        this.showToast = false;
    }

    private void handleFailCallback(Result<LoginResult> result) {
        LoginCallback loginCallback = this.mLoginCallback;
        if (loginCallback != null) {
            loginCallback.onFailure(result.code, result.message);
        }
        PreHandlerCallback preHandlerCallback = this.mPreCallback;
        if (preHandlerCallback != null) {
            preHandlerCallback.onFail(0, (Map) null);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
        this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.task.OpenAccountLoginTask.1
            @Override // java.lang.Runnable
            public void run() {
                if (((TaskWithDialog) OpenAccountLoginTask.this).showToast) {
                    ToastUtils.toastSystemError(((TaskWithDialog) OpenAccountLoginTask.this).context);
                }
                PreHandlerCallback preHandlerCallback = OpenAccountLoginTask.this.mPreCallback;
                if (preHandlerCallback != null) {
                    preHandlerCallback.onFail(0, (Map) null);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Result<LoginResult> asyncExecute(Void... voidArr) {
        HashMap map = new HashMap();
        String str = this.loginId;
        if (str != null) {
            map.put("loginId", str);
        }
        String str2 = this.password;
        if (str2 != null) {
            map.put("password", str2);
        }
        String str3 = this.sig;
        if (str3 != null) {
            map.put("sig", str3);
        }
        if (!TextUtils.isEmpty(this.cSessionId)) {
            map.put("csessionid", this.cSessionId);
        }
        if (!TextUtils.isEmpty(this.nocToken)) {
            map.put("nctoken", this.nocToken);
        }
        return OpenAccountUtils.toLoginResult(RpcUtils.pureInvokeWithRiskControlInfo("loginRequest", map, FirebaseAnalytics.Event.LOGIN));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Result<LoginResult> result) {
        super.onPostExecute((OpenAccountLoginTask) result);
        try {
            if (result == null) {
                if (this.showToast) {
                    ToastUtils.toastSystemError(this.context);
                }
                PreHandlerCallback preHandlerCallback = this.mPreCallback;
                if (preHandlerCallback != null) {
                    preHandlerCallback.onFail(0, (Map) null);
                    return;
                }
                return;
            }
            int i2 = result.code;
            if (i2 == 1) {
                LoginResult loginResult = result.data;
                if (loginResult == null || loginResult.loginSuccessResult == null) {
                    return;
                }
                SessionData sessionDataCreateSessionDataFromLoginSuccessResult = OpenAccountUtils.createSessionDataFromLoginSuccessResult(loginResult.loginSuccessResult);
                if (sessionDataCreateSessionDataFromLoginSuccessResult.scenario == null) {
                    sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 1;
                }
                this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
                PreHandlerCallback preHandlerCallback2 = this.mPreCallback;
                if (preHandlerCallback2 != null) {
                    preHandlerCallback2.onSuccess();
                }
                LoginCallback loginCallback = this.mLoginCallback;
                if (loginCallback != null) {
                    loginCallback.onSuccess(this.sessionManagerService.getSession());
                    return;
                }
                return;
            }
            if (i2 == 4016) {
                LoginResult loginResult2 = result.data;
                if (loginResult2 == null || loginResult2.doubleCheckUrl == null) {
                    return;
                }
                Uri.Builder builderBuildUpon = Uri.parse(loginResult2.doubleCheckUrl).buildUpon();
                builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                Intent intent = new Intent(this.mActivity, (Class<?>) LoginDoubleCheckWebActivity.class);
                intent.putExtra("url", builderBuildUpon.toString());
                intent.putExtra("title", result.message);
                intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
                intent.putExtra("token", result.data.doubleCheckToken);
                this.mActivity.startActivityForResult(intent, RequestCode.DOUBLE_CHECK_REQUEST_CODE);
                return;
            }
            if (i2 == 26053) {
                LoginResult loginResult3 = result.data;
                if (loginResult3 == null || loginResult3.checkCodeResult == null || TextUtils.isEmpty(loginResult3.checkCodeResult.clientVerifyData)) {
                    return;
                }
                Uri.Builder builderBuildUpon2 = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                builderBuildUpon2.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                Intent intent2 = new Intent(this.mActivity, (Class<?>) LoginDoubleCheckWebActivity.class);
                intent2.putExtra("url", builderBuildUpon2.toString());
                intent2.putExtra("title", result.message);
                intent2.putExtra("callback", "https://www.alipay.com/webviewbridge");
                this.mActivity.startActivityForResult(intent2, RequestCode.NO_CAPTCHA_REQUEST_CODE);
                return;
            }
            if (i2 != 26152) {
                handleFailCallback(result);
                if (this.showToast) {
                    if (TextUtils.isEmpty(result.message)) {
                        ToastUtils.toastSystemError(this.context);
                        return;
                    } else {
                        ToastUtils.toast(this.context, result.message, result.code);
                        return;
                    }
                }
                return;
            }
            LoginResult loginResult4 = result.data;
            if (loginResult4 == null || loginResult4.checkCodeResult == null || TextUtils.isEmpty(loginResult4.checkCodeResult.clientVerifyData)) {
                return;
            }
            Uri.Builder builderBuildUpon3 = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
            builderBuildUpon3.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
            Intent intent3 = new Intent(this.mActivity, (Class<?>) LoginIVWebActivity.class);
            intent3.putExtra("url", builderBuildUpon3.toString());
            intent3.putExtra("title", result.message);
            intent3.putExtra("callback", "https://www.alipay.com/webviewbridge");
            this.mActivity.startActivityForResult(intent3, RequestCode.RISK_IV_REQUEST_CODE);
        } catch (Throwable unused) {
            if (this.showToast) {
                ToastUtils.toastSystemError(this.context);
            }
            PreHandlerCallback preHandlerCallback3 = this.mPreCallback;
            if (preHandlerCallback3 != null) {
                preHandlerCallback3.onFail(0, (Map) null);
            }
        }
    }
}
