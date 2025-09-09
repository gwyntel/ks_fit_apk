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
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.message.UIMessageConstants;
import com.alibaba.sdk.android.openaccount.ui.model.CheckSmsCodeForResetPasswordResult;
import com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.util.OpenAccountRiskControlContext;
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
public class ResetPasswordActivity extends SendSmsCodeActivity {
    public String mLocationCode;
    private String mMobile;
    private String smsCheckTrustToken;

    protected class CheckSmsCodeForResetPasswordTask extends TaskWithToastMessage<CheckSmsCodeForResetPasswordResult> {
        private String cSessionId;
        private String nocToken;
        private String sig;

        public CheckSmsCodeForResetPasswordTask(Activity activity, String str, String str2, String str3) {
            super(activity);
            this.cSessionId = str;
            this.nocToken = str2;
            this.sig = str3;
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<CheckSmsCodeForResetPasswordResult> result) {
            CheckSmsCodeForResetPasswordResult checkSmsCodeForResetPasswordResult;
            if (result.code != 26053 || (checkSmsCodeForResetPasswordResult = result.data) == null || TextUtils.isEmpty(checkSmsCodeForResetPasswordResult.clientVerifyData)) {
                return;
            }
            ResetPasswordActivity.this.smsCheckTrustToken = result.data.smsCheckTrustToken;
            Uri.Builder builderBuildUpon = Uri.parse(result.data.clientVerifyData).buildUpon();
            builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
            Intent intent = new Intent(ResetPasswordActivity.this, (Class<?>) LoginDoubleCheckWebActivity.class);
            intent.putExtra("url", builderBuildUpon.toString());
            intent.putExtra("title", result.message);
            intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
            ResetPasswordActivity.this.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<CheckSmsCodeForResetPasswordResult> result) {
            Intent intent = new Intent(ResetPasswordActivity.this, OpenAccountUIConfigs.MobileResetPasswordLoginFlow.resetPasswordPasswordActivityClazz);
            intent.putExtra("token", result.data.token);
            intent.putExtra("loginId", ResetPasswordActivity.this.mobileInputBox.getEditTextContent());
            ResetPasswordActivity.this.startActivityForResult(intent, 1);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected boolean toastMessageRequired(Result<CheckSmsCodeForResetPasswordResult> result) {
            return result.code != 26053;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<CheckSmsCodeForResetPasswordResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            map.put("checkCodeRequest", map2);
            HashMap map3 = new HashMap();
            if (!TextUtils.isEmpty(this.sig)) {
                map2.put("sig", this.sig);
            }
            if (!TextUtils.isEmpty(this.nocToken)) {
                map2.put("nctoken", this.nocToken);
                map3.put("smsToken", ResetPasswordActivity.this.smsCheckTrustToken);
            }
            ResetPasswordActivity.this.smsCheckTrustToken = "";
            if (!TextUtils.isEmpty(this.cSessionId)) {
                map2.put("csessionid", this.cSessionId);
            }
            map3.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, ResetPasswordActivity.this.mobileInputBox.getMobileLocationCode());
            map3.put("userId", ResetPasswordActivity.this.mobileInputBox.getEditTextContent());
            map3.put("version", 1);
            map3.put("smsCode", ResetPasswordActivity.this.smsCodeInputBox.getInputBoxWithClear().getEditTextContent());
            map.put("checkSmsCodeRequest", map3);
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, map, "checksmscodeforresetpassword"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public CheckSmsCodeForResetPasswordResult parseData(JSONObject jSONObject) {
            CheckSmsCodeForResetPasswordResult checkSmsCodeForResetPasswordResult = new CheckSmsCodeForResetPasswordResult();
            checkSmsCodeForResetPasswordResult.token = jSONObject.optString("token");
            checkSmsCodeForResetPasswordResult.clientVerifyData = jSONObject.optString("clientVerifyData");
            checkSmsCodeForResetPasswordResult.smsCheckTrustToken = jSONObject.optString("smsCheckTrustToken");
            return checkSmsCodeForResetPasswordResult;
        }
    }

    protected class SendSmsCodeForResetPasswordTask extends TaskWithToastMessage<Void> {
        public SendSmsCodeForResetPasswordTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<Void> result) {
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<Void> result) {
            ResetPasswordActivity resetPasswordActivity = ResetPasswordActivity.this;
            resetPasswordActivity.smsCodeInputBox.startTimer(resetPasswordActivity);
            ResetPasswordActivity.this.smsCodeInputBox.requestFocus();
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
            map.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, ResetPasswordActivity.this.mobileInputBox.getMobileLocationCode());
            if (OpenAccountUIConfigs.MobileResetPasswordLoginFlow.supportResetPasswordWithNick) {
                map.put("userId", ResetPasswordActivity.this.mobileInputBox.getEditTextContent());
                map.put("version", 1);
            } else {
                map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, ResetPasswordActivity.this.mobileInputBox.getEditTextContent());
            }
            map.put("riskControlInfo", OpenAccountRiskControlContext.buildRiskContext());
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("sendSmsCodeForResetPasswordRequest", map, "sendsmscodeforresetpassword"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSMS() {
        new SendSmsCodeForResetPasswordTask(this).execute(new Void[0]);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_reset_password";
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected LoginCallback getLoginCallback() {
        return OpenAccountUIServiceImpl._resetPasswordCallback;
    }

    protected void goCheckSMSCode(String str, String str2, String str3) {
        new CheckSmsCodeForResetPasswordTask(this, str, str2, str3).execute(new Void[0]);
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

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected void onUserCancel() {
        LoginCallback loginCallback = getLoginCallback();
        if (loginCallback != null) {
            loginCallback.onFailure(UIMessageConstants.OPEN_ACCOUNT_RESET_PASSWORD_CANCEL, MessageUtils.getMessageContent(UIMessageConstants.OPEN_ACCOUNT_RESET_PASSWORD_CANCEL, new Object[0]));
        }
    }

    public void setViewListener(Activity activity) {
        this.next.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.ResetPasswordActivity.1
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                ResetPasswordActivity.this.goCheckSMSCode(null, null, null);
            }
        });
        this.smsCodeInputBox.addSendClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.ResetPasswordActivity.2
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                ResetPasswordActivity.this.sendSMS();
            }
        });
    }
}
