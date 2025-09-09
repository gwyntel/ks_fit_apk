package com.alibaba.sdk.android.openaccount.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.annotation.ExtensionPoint;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.rpc.RpcServerBizConstants;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.model.SmsActionType;
import com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.facebook.share.internal.ShareConstants;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.HashMap;
import org.json.JSONObject;

@ExtensionPoint
/* loaded from: classes2.dex */
public class LoginWithSmsCodeActivity extends SendSmsCodeActivity {
    private static final String TAG = "LoginWithSmsCodeActivity";
    public String mLocationCode;
    private String mMobile;
    private String smsCheckTrustToken;

    protected class LoginWithSmsCodeTask extends TaskWithDialog<Void, Void, Result<LoginResult>> {
        private String cSessionId;
        private String loginId;
        private String nocToken;
        private String password;
        private String sig;

        public LoginWithSmsCodeTask(Activity activity, String str, String str2, String str3) {
            super(activity);
            this.sig = str;
            this.nocToken = str2;
            this.cSessionId = str3;
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginWithSmsCodeActivity.LoginWithSmsCodeTask.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtils.toastSystemError(((TaskWithDialog) LoginWithSmsCodeTask.this).context);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<LoginResult> asyncExecute(Void... voidArr) {
            LoginWithSmsCodeActivity.this.hideSoftInputForHw();
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            if (!TextUtils.isEmpty(this.sig)) {
                map2.put("sig", this.sig);
            }
            if (!TextUtils.isEmpty(this.cSessionId)) {
                map2.put("csessionid", this.cSessionId);
            }
            if (!TextUtils.isEmpty(this.nocToken)) {
                map2.put("nctoken", this.nocToken);
            }
            map2.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, LoginWithSmsCodeActivity.this.mobileInputBox.getMobileLocationCode());
            map2.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, LoginWithSmsCodeActivity.this.mobileInputBox.getEditTextContent());
            map2.put("version", 0);
            map2.put("securityMobile", Boolean.FALSE);
            map2.put("smsCode", LoginWithSmsCodeActivity.this.smsCodeInputBox.getInputBoxWithClear().getEditTextContent());
            map.put("checkSmsCodeRequest", map2);
            return OpenAccountUtils.toLoginResult(RpcUtils.pureInvokeWithRiskControlInfo(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, map, "loginwithoutpassword"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Result<LoginResult> result) {
            dismissProgressDialog();
            super.onPostExecute((LoginWithSmsCodeTask) result);
            try {
                if (result == null) {
                    if (ConfigManager.getInstance().isSupportOfflineLogin()) {
                        ToastUtils.toastNetworkError(this.context);
                        return;
                    } else {
                        ToastUtils.toastSystemError(this.context);
                        return;
                    }
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
                    LoginWithSmsCodeActivity.this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
                    TextUtils.isEmpty(result.data.userInputName);
                    if (ConfigManager.getInstance().isSupportOfflineLogin()) {
                        OpenAccountSDK.getSqliteUtil().saveToSqlite(this.loginId, this.password);
                    }
                    LoginWithSmsCodeActivity.this.loginSuccess();
                    return;
                }
                if (i2 == 2) {
                    SessionData sessionDataCreateSessionDataFromLoginSuccessResult2 = OpenAccountUtils.createSessionDataFromLoginSuccessResult(result.data.loginSuccessResult);
                    if (sessionDataCreateSessionDataFromLoginSuccessResult2.scenario == null) {
                        sessionDataCreateSessionDataFromLoginSuccessResult2.scenario = 1;
                    }
                    LoginWithSmsCodeActivity.this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult2);
                    LoginWithSmsCodeActivity.this.loginSuccess();
                    return;
                }
                if (i2 == 26053) {
                    LoginResult loginResult2 = result.data;
                    if (loginResult2 == null || loginResult2.checkCodeResult == null || TextUtils.isEmpty(loginResult2.checkCodeResult.clientVerifyData)) {
                        return;
                    }
                    Uri.Builder builderBuildUpon = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                    builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                    Intent intent = new Intent(LoginWithSmsCodeActivity.this, (Class<?>) LoginDoubleCheckWebActivity.class);
                    intent.putExtra("url", builderBuildUpon.toString());
                    intent.putExtra("title", result.message);
                    intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
                    LoginWithSmsCodeActivity.this.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
                    return;
                }
                if (i2 != 26152) {
                    if (!TextUtils.equals(result.type, RpcServerBizConstants.ACTION_TYPE_CALLBACK) || LoginWithSmsCodeActivity.this.getLoginCallback() == null) {
                        LoginWithSmsCodeActivity.this.onPwdLoginFail(result.code, result.message);
                        return;
                    } else {
                        LoginWithSmsCodeActivity.this.getLoginCallback().onFailure(result.code, result.message);
                        return;
                    }
                }
                LoginResult loginResult3 = result.data;
                if (loginResult3 == null || loginResult3.checkCodeResult == null || TextUtils.isEmpty(loginResult3.checkCodeResult.clientVerifyData)) {
                    return;
                }
                Uri.Builder builderBuildUpon2 = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                builderBuildUpon2.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                Intent intent2 = new Intent(LoginWithSmsCodeActivity.this, (Class<?>) LoginIVWebActivity.class);
                intent2.putExtra("url", builderBuildUpon2.toString());
                intent2.putExtra("title", result.message);
                intent2.putExtra("callback", "https://www.alipay.com/webviewbridge");
                LoginWithSmsCodeActivity.this.startActivityForResult(intent2, RequestCode.RISK_IV_REQUEST_CODE);
            } catch (Throwable th) {
                AliSDKLogger.e(LoginWithSmsCodeActivity.TAG, "after post execute error", th);
                ToastUtils.toastSystemError(this.context);
            }
        }
    }

    protected class SendSmsCodeTask extends TaskWithToastMessage<Void> {
        public SendSmsCodeTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<Void> result) {
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<Void> result) {
            LoginWithSmsCodeActivity loginWithSmsCodeActivity = LoginWithSmsCodeActivity.this;
            loginWithSmsCodeActivity.smsCodeInputBox.startTimer(loginWithSmsCodeActivity);
            LoginWithSmsCodeActivity.this.smsCodeInputBox.requestFocus();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public Void parseData(JSONObject jSONObject) {
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<Void> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, LoginWithSmsCodeActivity.this.mobileInputBox.getEditTextContent());
            map.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, LoginWithSmsCodeActivity.this.mobileInputBox.getMobileLocationCode());
            map.put("smsActionType", SmsActionType.SDK_NO_PASSWORD_LOGIN);
            map.put("securityMobile", Boolean.FALSE);
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("sendSmsCodeRequest", map, "sendsmscode"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideSoftInputForHw() {
        InputMethodManager inputMethodManager;
        if (!"HUAWEI".equalsIgnoreCase(Build.MANUFACTURER) || Build.VERSION.SDK_INT < 27 || (inputMethodManager = (InputMethodManager) getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(this.mobileInputBox.getEditText().getWindowToken(), 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginSuccess() {
        AliSDKLogger.i(TAG, "loginSuccess");
        ((ExecutorService) OpenAccountSDK.getService(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginWithSmsCodeActivity.3
            @Override // java.lang.Runnable
            public void run() {
                LoginCallback loginCallback = LoginWithSmsCodeActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    AliSDKLogger.i(LoginWithSmsCodeActivity.TAG, "loginCallback != null");
                    loginCallback.onSuccess(LoginWithSmsCodeActivity.this.sessionManagerService.getSession());
                }
                ((ExecutorService) OpenAccountSDK.getService(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginWithSmsCodeActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LoginWithSmsCodeActivity.this.finishWithoutCallback();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSMS() {
        new SendSmsCodeTask(this).execute(new Void[0]);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_login_with_sms_code_password";
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected LoginCallback getLoginCallback() {
        return OpenAccountUIServiceImpl._loginWithSmsCodeCallback;
    }

    protected void goCheckSMSCode(String str, String str2, String str3) {
        new LoginWithSmsCodeTask(this, str, str2, str3).execute(new Void[0]);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 == RequestCode.NO_CAPTCHA_REQUEST_CODE) {
            if (i3 != -1) {
                this.smsCheckTrustToken = "";
                return;
            } else {
                if (intent == null || !"nocaptcha".equals(intent.getStringExtra("action"))) {
                    return;
                }
                goCheckSMSCode(intent.getStringExtra("cSessionId"), intent.getStringExtra("nocToken"), intent.getStringExtra("sig"));
                return;
            }
        }
        if (i2 == 1 && i3 == -1) {
            finishWithoutCallback();
            LoginCallback loginCallback = getLoginCallback();
            if (loginCallback != null) {
                loginCallback.onSuccess(this.sessionManagerService.getSession());
            }
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, com.alibaba.sdk.android.openaccount.ui.ui.NextStepActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setViewListener(this);
        this.mobileInputBox.setSupportForeignMobile(this, OpenAccountUIConfigs.MobileResetPasswordLoginFlow.mobileCountrySelectorActvityClazz, OpenAccountUIConfigs.MobileResetPasswordLoginFlow.supportForeignMobileNumbers);
        if (OpenAccountUIConfigs.MobileResetPasswordLoginFlow.supportResetPasswordWithNick) {
            if (this.mobileInputBox.getInputHint() == null) {
                this.mobileInputBox.setInputHint(ResourceUtils.getString(this, "ali_sdk_openaccount_dynamic_text_mobile_or_login_id"));
            }
            this.mobileInputBox.setInputType(1);
        } else {
            if (this.mobileInputBox.getInputHint() == null) {
                this.mobileInputBox.setInputHint(ResourceUtils.getString(this, "ali_sdk_openaccount_dynamic_text_mobile"));
            }
            this.mobileInputBox.setInputType(2);
        }
        try {
            this.mMobile = getIntent().getStringExtra(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE);
            this.mLocationCode = getIntent().getStringExtra("LocationCode");
        } catch (Exception unused) {
        }
        if (OpenAccountUIConfigs.MobileResetPasswordLoginFlow.supportForeignMobileNumbers) {
            if (TextUtils.isEmpty(this.mLocationCode)) {
                return;
            }
            this.mobileInputBox.setMobileLocationCode(this.mLocationCode);
        } else {
            if (TextUtils.isEmpty(this.mMobile) || !OpenAccountUtils.isNumeric(this.mMobile)) {
                return;
            }
            this.mobileInputBox.getEditText().setText(this.mMobile);
            this.mobileInputBox.getEditText().setEnabled(false);
            this.mobileInputBox.getEditText().setFocusable(false);
            this.mobileInputBox.getClearTextView().setVisibility(8);
            sendSMS();
        }
    }

    protected void onPwdLoginFail(int i2, String str) {
        if (TextUtils.isEmpty(str)) {
            ToastUtils.toastSystemError(getApplicationContext());
        } else {
            ToastUtils.toast(getApplicationContext(), str, i2);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected void onUserCancel() {
        getLoginCallback();
    }

    public void setViewListener(Activity activity) {
        this.next.setText(ResourceUtils.getString(this, "ali_sdk_openaccount_text_login"));
        this.next.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginWithSmsCodeActivity.1
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                LoginWithSmsCodeActivity.this.goCheckSMSCode(null, null, null);
            }
        });
        this.smsCodeInputBox.addSendClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginWithSmsCodeActivity.2
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                LoginWithSmsCodeActivity.this.sendSMS();
            }
        });
    }
}
