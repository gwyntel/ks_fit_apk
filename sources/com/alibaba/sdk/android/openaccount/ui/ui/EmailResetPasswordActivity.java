package com.alibaba.sdk.android.openaccount.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.sdk.android.openaccount.annotation.ExtensionPoint;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.model.CheckCodeResult;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.R;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.model.EmailRegisterResult;
import com.alibaba.sdk.android.openaccount.ui.model.SendEmailResult;
import com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithClear;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.ui.widget.NextStepButtonWatcher;
import com.alibaba.sdk.android.openaccount.ui.widget.SmsCodeInputBox;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.HashMap;
import org.json.JSONObject;
import org.mozilla.javascript.ES6Iterator;

@ExtensionPoint
/* loaded from: classes2.dex */
public class EmailResetPasswordActivity extends NextStepActivityTemplate {
    protected SmsCodeInputBox checkCodeInputBox;
    protected TextView mHintTextView;
    protected InputBoxWithClear mailInputBox;
    protected Button sendEmailButton;

    @Autowired
    protected SessionManagerService sessionManagerService;

    public class CheckEmailTask extends TaskWithDialog<Void, Void, Result<EmailRegisterResult>> {
        public CheckEmailTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
        }

        protected EmailRegisterResult parseData(JSONObject jSONObject) {
            EmailRegisterResult emailRegisterResult = new EmailRegisterResult();
            if (jSONObject.has("loginSuccessResult")) {
                emailRegisterResult.loginSuccessResult = OpenAccountUtils.parseLoginSuccessResult(jSONObject);
            }
            return emailRegisterResult;
        }

        protected Result<EmailRegisterResult> parseJsonResult(Result<JSONObject> result) {
            JSONObject jSONObject = result.data;
            return jSONObject == null ? Result.result(result.code, result.message) : Result.result(result.code, result.message, parseData(jSONObject));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<EmailRegisterResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            map.put("email", EmailResetPasswordActivity.this.mailInputBox.getEditText().getText());
            map.put("actionType", "sdk_email_reset_password");
            map.put("emailToken", EmailResetPasswordActivity.this.checkCodeInputBox.getInputBoxWithClear().getEditTextContent());
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("checkEmailTokenRequest", map, "emailvalidatetoken"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Result<EmailRegisterResult> result) {
            super.onPostExecute((CheckEmailTask) result);
            if (result == null) {
                ToastUtils.toastSystemError(this.context);
                return;
            }
            if (result.code != 1) {
                if (TextUtils.isEmpty(result.message)) {
                    ToastUtils.toastSystemError(this.context);
                    return;
                } else {
                    ToastUtils.toast(this.context, result.message, result.code);
                    return;
                }
            }
            Intent intent = new Intent(EmailResetPasswordActivity.this, OpenAccountUIConfigs.EmailResetPasswordLoginFlow.resetPasswordFillActivityClazz);
            intent.putExtra("emailToken", EmailResetPasswordActivity.this.checkCodeInputBox.getInputBoxWithClear().getEditTextContent());
            intent.putExtra("email", EmailResetPasswordActivity.this.mailInputBox.getEditText().getText().toString());
            EmailResetPasswordActivity.this.startActivityForResult(intent, 1);
        }
    }

    class SendEmailTask extends TaskWithToastMessage<SendEmailResult> {
        private String cSessionId;
        private String nocToken;
        private String sig;

        public SendEmailTask(Activity activity, String str, String str2, String str3) {
            super(activity);
            this.cSessionId = str;
            this.nocToken = str2;
            this.sig = str3;
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<SendEmailResult> result) {
            SendEmailResult sendEmailResult;
            if (result.code != 26053 || (sendEmailResult = result.data) == null || sendEmailResult.checkCodeResult == null) {
                if (TextUtils.isEmpty(result.message)) {
                    ToastUtils.toastSystemError(EmailResetPasswordActivity.this);
                    return;
                } else {
                    ToastUtils.toast(EmailResetPasswordActivity.this, result.message, result.code);
                    return;
                }
            }
            Uri.Builder builderBuildUpon = Uri.parse(sendEmailResult.checkCodeResult.clientVerifyData).buildUpon();
            builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
            Intent intent = new Intent(EmailResetPasswordActivity.this, (Class<?>) LoginDoubleCheckWebActivity.class);
            intent.putExtra("url", builderBuildUpon.toString());
            intent.putExtra("title", result.message);
            intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
            EmailResetPasswordActivity.this.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<SendEmailResult> result) {
            EmailResetPasswordActivity.this.checkCodeInputBox.startTimer(this.context);
            EmailResetPasswordActivity.this.checkCodeInputBox.requestFocus();
            EmailResetPasswordActivity.this.mHintTextView.setVisibility(0);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected boolean toastMessageRequired(Result<SendEmailResult> result) {
            return result.code != 26053;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<SendEmailResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            map.put("email", EmailResetPasswordActivity.this.mailInputBox.getEditText().getText());
            map.put("actionType", "sdk_email_reset_password");
            if (!TextUtils.isEmpty(this.sig)) {
                map.put("sig", this.sig);
            }
            if (!TextUtils.isEmpty(this.nocToken)) {
                map.put("nctoken", this.nocToken);
            }
            if (!TextUtils.isEmpty(this.cSessionId)) {
                map.put("csessionid", this.cSessionId);
            }
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("sendEmailTokenRequest", map, "emailsendtoken"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public SendEmailResult parseData(JSONObject jSONObject) {
            SendEmailResult sendEmailResult = new SendEmailResult();
            sendEmailResult.email = jSONObject.optString("email");
            sendEmailResult.emailToken = jSONObject.optString("emailToken");
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("checkCodeResult");
            if (jSONObjectOptJSONObject != null) {
                CheckCodeResult checkCodeResult = new CheckCodeResult();
                sendEmailResult.checkCodeResult = checkCodeResult;
                checkCodeResult.clientVerifyData = jSONObjectOptJSONObject.optString("clientVerifyData");
                sendEmailResult.checkCodeResult.checkCodeId = jSONObjectOptJSONObject.optString("checkCodeId");
                sendEmailResult.checkCodeResult.checkCodeUrl = jSONObjectOptJSONObject.optString("checkCodeUrl");
            }
            return sendEmailResult;
        }
    }

    public void finishWithoutCallback() {
        super.finish();
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_mail_reset_password";
    }

    protected LoginCallback getLoginCallback() {
        return OpenAccountUIServiceImpl._emailResetPasswordCallback;
    }

    protected void goCheckEmail(String str, String str2, String str3) {
        new SendEmailTask(this, str, str2, str3).execute(new Void[0]);
    }

    protected void goVerifyEmailCode() {
        new CheckEmailTask(this).execute(new Void[0]);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 == RequestCode.NO_CAPTCHA_REQUEST_CODE) {
            if (i3 == -1 && intent != null && "nocaptcha".equals(intent.getStringExtra("action"))) {
                goCheckEmail(intent.getStringExtra("cSessionId"), intent.getStringExtra("nocToken"), intent.getStringExtra("sig"));
                return;
            }
            return;
        }
        if (i2 == 1 && i3 == -1) {
            LoginCallback loginCallback = getLoginCallback();
            if (loginCallback != null) {
                loginCallback.onSuccess(this.sessionManagerService.getSession());
            }
            finishWithoutCallback();
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.NextStepActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mHintTextView = (TextView) findViewById(R.id.ali_oa_send_email_success_tip);
        this.mailInputBox = (InputBoxWithClear) findViewById("mail_input_box");
        this.sendEmailButton = (Button) findViewById(ES6Iterator.NEXT_METHOD);
        SmsCodeInputBox smsCodeInputBox = (SmsCodeInputBox) findViewById("check_code_input_box");
        this.checkCodeInputBox = smsCodeInputBox;
        smsCodeInputBox.getSend().setText(ResourceUtils.getString("ali_sdk_openaccount_text_sendmail"));
        this.checkCodeInputBox.setSendText("ali_sdk_openaccount_text_sendmail");
        NextStepButtonWatcher nextStepButtonWatcher = getNextStepButtonWatcher();
        nextStepButtonWatcher.addEditTexts(this.mailInputBox.getEditText(), this.checkCodeInputBox.getEditText());
        this.mailInputBox.addTextChangedListener(nextStepButtonWatcher);
        this.checkCodeInputBox.getInputBoxWithClear().addTextChangedListener(nextStepButtonWatcher);
        this.checkCodeInputBox.addSendClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.EmailResetPasswordActivity.1
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                EmailResetPasswordActivity.this.mHintTextView.setVisibility(4);
                EmailResetPasswordActivity.this.goCheckEmail(null, null, null);
            }
        });
        this.sendEmailButton.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.EmailResetPasswordActivity.2
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                EmailResetPasswordActivity.this.goVerifyEmailCode();
            }
        });
        useCustomAttrs(this, this.attrs);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        OpenAccountUIServiceImpl._emailResetPasswordCallback = null;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
}
