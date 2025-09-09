package com.alibaba.sdk.android.openaccount.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.sdk.android.openaccount.annotation.ExtensionPoint;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.message.UIMessageConstants;
import com.alibaba.sdk.android.openaccount.ui.model.SendSmsCodeForNoPasswordLoginResult;
import com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask;
import com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.ui.widget.MobileInputBoxWithClear;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.facebook.share.internal.ShareConstants;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashMap;
import org.json.JSONObject;

@ExtensionPoint
/* loaded from: classes2.dex */
public class NoPasswordLoginActivity extends SendSmsCodeActivity {
    private String cSessionId;
    private String nocToken;
    private String sig;
    private String smsCheckTrustToken;

    protected class LoginWithoutPasswordTask extends TaskWithDialog<Void, Void, Result<LoginResult>> {
        private String cSessionId;
        private String nocToken;
        private String sig;

        public LoginWithoutPasswordTask(Activity activity, String str, String str2, String str3) {
            super(activity);
            this.sig = str;
            this.nocToken = str2;
            this.cSessionId = str3;
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.NoPasswordLoginActivity.LoginWithoutPasswordTask.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtils.toastSystemError(((TaskWithDialog) LoginWithoutPasswordTask.this).context);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<LoginResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            if (!TextUtils.isEmpty(this.sig)) {
                map2.put("sig", this.sig);
            }
            HashMap map3 = new HashMap();
            if (!TextUtils.isEmpty(this.nocToken)) {
                map2.put("nctoken", this.nocToken);
                map3.put("smsToken", NoPasswordLoginActivity.this.smsCheckTrustToken);
            }
            NoPasswordLoginActivity.this.smsCheckTrustToken = "";
            if (!TextUtils.isEmpty(this.cSessionId)) {
                map2.put("csessionid", this.cSessionId);
            }
            map.put("checkCodeRequest", map2);
            map3.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, NoPasswordLoginActivity.this.mobileInputBox.getMobileLocationCode());
            map3.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, NoPasswordLoginActivity.this.mobileInputBox.getEditTextContent());
            map3.put("smsCode", NoPasswordLoginActivity.this.smsCodeInputBox.getInputBoxWithClear().getEditTextContent());
            map.put("checkSmsCodeRequest", map3);
            return OpenAccountUtils.toLoginResult(RpcUtils.pureInvokeWithRiskControlInfo(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, map, "loginwithoutpassword"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Result<LoginResult> result) {
            super.onPostExecute((LoginWithoutPasswordTask) result);
            try {
                if (result == null) {
                    ToastUtils.toastSystemError(this.context);
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
                        sessionDataCreateSessionDataFromLoginSuccessResult.scenario = 4;
                    }
                    NoPasswordLoginActivity.this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
                    NoPasswordLoginActivity.this.finishWithoutCallback();
                    LoginCallback loginCallback = NoPasswordLoginActivity.this.getLoginCallback();
                    if (loginCallback != null) {
                        loginCallback.onSuccess(NoPasswordLoginActivity.this.sessionManagerService.getSession());
                        return;
                    }
                    return;
                }
                if (i2 == 26053) {
                    if (NoPasswordLoginActivity.this.isUrlNotEmpty(result)) {
                        NoPasswordLoginActivity.this.smsCheckTrustToken = result.data.smsCheckTrustToken;
                        Uri.Builder builderBuildUpon = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                        builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                        Intent intent = new Intent(NoPasswordLoginActivity.this, (Class<?>) LoginDoubleCheckWebActivity.class);
                        intent.putExtra("url", builderBuildUpon.toString());
                        intent.putExtra("title", result.message);
                        intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
                        NoPasswordLoginActivity.this.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
                        return;
                    }
                    return;
                }
                if (i2 != 26152) {
                    if (TextUtils.isEmpty(result.message)) {
                        ToastUtils.toastSystemError(this.context);
                        return;
                    } else {
                        ToastUtils.toast(this.context, result.message, result.code);
                        return;
                    }
                }
                if (NoPasswordLoginActivity.this.isUrlNotEmpty(result)) {
                    Uri.Builder builderBuildUpon2 = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                    builderBuildUpon2.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                    Intent intent2 = new Intent(NoPasswordLoginActivity.this, (Class<?>) LoginIVWebActivity.class);
                    intent2.putExtra("url", builderBuildUpon2.toString());
                    intent2.putExtra("title", result.message);
                    intent2.putExtra("callback", "https://www.alipay.com/webviewbridge");
                    NoPasswordLoginActivity.this.startActivityForResult(intent2, RequestCode.RISK_IV_REQUEST_CODE);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        public LoginWithoutPasswordTask(Activity activity) {
            super(activity);
        }
    }

    protected class SendSmsCodeForNoPasswordLoginTask extends TaskWithToastMessage<SendSmsCodeForNoPasswordLoginResult> {
        public SendSmsCodeForNoPasswordLoginTask(Activity activity, String str, String str2, String str3) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<SendSmsCodeForNoPasswordLoginResult> result) {
            if (result.code != 26053) {
                AliSDKLogger.e(OpenAccountUIConstants.LOG_TAG, "sendSmsCodeForNoPassword failed with result " + result);
                return;
            }
            NoPasswordLoginActivity noPasswordLoginActivity = NoPasswordLoginActivity.this;
            noPasswordLoginActivity.smsCodeInputBox.startTimer(noPasswordLoginActivity);
            if (TextUtils.isEmpty(result.data.clientVerifyData)) {
                return;
            }
            Uri.Builder builderBuildUpon = Uri.parse(result.data.clientVerifyData).buildUpon();
            builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
            Intent intent = new Intent(NoPasswordLoginActivity.this, (Class<?>) LoginDoubleCheckWebActivity.class);
            intent.putExtra("url", builderBuildUpon.toString());
            intent.putExtra("title", result.message);
            intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
            NoPasswordLoginActivity.this.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<SendSmsCodeForNoPasswordLoginResult> result) {
            NoPasswordLoginActivity noPasswordLoginActivity = NoPasswordLoginActivity.this;
            noPasswordLoginActivity.smsCodeInputBox.startTimer(noPasswordLoginActivity);
            NoPasswordLoginActivity.this.smsCodeInputBox.requestFocus();
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected boolean toastMessageRequired(Result<SendSmsCodeForNoPasswordLoginResult> result) {
            return result.code != 26053;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<SendSmsCodeForNoPasswordLoginResult> asyncExecute(Void... voidArr) {
            NoPasswordLoginActivity.this.cSessionId = null;
            NoPasswordLoginActivity.this.nocToken = null;
            NoPasswordLoginActivity.this.sig = null;
            HashMap map = new HashMap();
            map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, NoPasswordLoginActivity.this.mobileInputBox.getEditTextContent());
            map.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, NoPasswordLoginActivity.this.mobileInputBox.getMobileLocationCode());
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, map, "sendsmscode"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public SendSmsCodeForNoPasswordLoginResult parseData(JSONObject jSONObject) {
            SendSmsCodeForNoPasswordLoginResult sendSmsCodeForNoPasswordLoginResult = new SendSmsCodeForNoPasswordLoginResult();
            sendSmsCodeForNoPasswordLoginResult.checkCodeId = jSONObject.optString("checkCodeId");
            sendSmsCodeForNoPasswordLoginResult.checkCodeUrl = jSONObject.optString("checkCodeUrl");
            sendSmsCodeForNoPasswordLoginResult.clientVerifyData = jSONObject.optString("clientVerifyData");
            return sendSmsCodeForNoPasswordLoginResult;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUrlNotEmpty(Result<LoginResult> result) {
        LoginResult loginResult = result.data;
        return (loginResult == null || loginResult.checkCodeResult == null || TextUtils.isEmpty(loginResult.checkCodeResult.clientVerifyData)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSms(String str, String str2, String str3) {
        new SendSmsCodeForNoPasswordLoginTask(this, null, null, null).execute(new Void[0]);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_no_password_login";
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected LoginCallback getLoginCallback() {
        return OpenAccountUIServiceImpl._noPasswordLoginCallback;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        String editTextContent;
        super.onActivityResult(i2, i3, intent);
        if (i2 == RequestCode.NO_CAPTCHA_REQUEST_CODE && i3 == -1) {
            if (intent == null || !"nocaptcha".equals(intent.getStringExtra("action"))) {
                return;
            }
            this.cSessionId = intent.getStringExtra("cSessionId");
            this.nocToken = intent.getStringExtra("nocToken");
            this.sig = intent.getStringExtra("sig");
            new LoginWithoutPasswordTask(this, this.sig, this.nocToken, this.cSessionId).execute(new Void[0]);
            return;
        }
        if (i2 == RequestCode.RISK_IV_REQUEST_CODE) {
            if (i3 != -1) {
                this.smsCheckTrustToken = "";
                return;
            }
            if (intent != null) {
                String stringExtra = intent.getStringExtra("havana_iv_token");
                String stringExtra2 = intent.getStringExtra("actionType");
                MobileInputBoxWithClear mobileInputBoxWithClear = this.mobileInputBox;
                if (mobileInputBoxWithClear != null) {
                    if (TextUtils.isEmpty(mobileInputBoxWithClear.getMobileLocationCode())) {
                        editTextContent = this.mobileInputBox.getEditTextContent();
                    } else {
                        editTextContent = this.mobileInputBox.getMobileLocationCode() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.mobileInputBox.getEditTextContent();
                    }
                    if (TextUtils.isEmpty(stringExtra) || TextUtils.isEmpty(stringExtra2) || TextUtils.isEmpty(editTextContent)) {
                        return;
                    }
                    new LoginByIVTokenTask(this, stringExtra, editTextContent, stringExtra2).execute(new Void[0]);
                }
            }
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, com.alibaba.sdk.android.openaccount.ui.ui.NextStepActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.next.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.NoPasswordLoginActivity.1
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                NoPasswordLoginActivity noPasswordLoginActivity = NoPasswordLoginActivity.this;
                noPasswordLoginActivity.new LoginWithoutPasswordTask(noPasswordLoginActivity).execute(new Void[0]);
            }
        });
        this.smsCodeInputBox.addSendClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.NoPasswordLoginActivity.2
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                NoPasswordLoginActivity.this.sendSms(null, null, null);
            }
        });
        this.mobileInputBox.setSupportForeignMobile(this, OpenAccountUIConfigs.MobileNoPasswordLoginFlow.mobileCountrySelectorActvityClazz, OpenAccountUIConfigs.MobileNoPasswordLoginFlow.supportForeignMobileNumbers);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected void onUserCancel() {
        LoginCallback loginCallback = getLoginCallback();
        if (loginCallback != null) {
            loginCallback.onFailure(UIMessageConstants.OPEN_ACCOUNT_NO_PASSWORD_LOGIN_CANCEL, MessageUtils.getMessageContent(UIMessageConstants.OPEN_ACCOUNT_NO_PASSWORD_LOGIN_CANCEL, new Object[0]));
        }
    }
}
