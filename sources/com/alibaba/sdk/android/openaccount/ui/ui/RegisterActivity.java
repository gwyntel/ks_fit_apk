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
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.message.UIMessageConstants;
import com.alibaba.sdk.android.openaccount.ui.model.CheckSmsCodeForRegisterResult;
import com.alibaba.sdk.android.openaccount.ui.model.SendSmsCodeForRegisterResult;
import com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.facebook.share.internal.ShareConstants;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.HashMap;
import org.json.JSONObject;

@ExtensionPoint
/* loaded from: classes2.dex */
public class RegisterActivity extends SendSmsCodeActivity {
    protected String clientVerifyData;
    protected String smsToken;

    protected class CheckSmsCodeForRegisterTask extends TaskWithDialog<Void, Void, Result<CheckSmsCodeForRegisterResult>> {
        private String cSessionId;
        private String nocToken;
        private String sig;

        public CheckSmsCodeForRegisterTask(Activity activity, String str, String str2, String str3) {
            super(activity);
            this.sig = str;
            this.nocToken = str2;
            this.cSessionId = str3;
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.RegisterActivity.CheckSmsCodeForRegisterTask.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtils.toastSystemError(((TaskWithDialog) CheckSmsCodeForRegisterTask.this).context);
                }
            });
        }

        protected CheckSmsCodeForRegisterResult parseData(JSONObject jSONObject) {
            CheckSmsCodeForRegisterResult checkSmsCodeForRegisterResult = new CheckSmsCodeForRegisterResult();
            String strOptString = jSONObject.optString("token");
            checkSmsCodeForRegisterResult.token = strOptString;
            RegisterActivity.this.smsToken = strOptString;
            checkSmsCodeForRegisterResult.checkCodeUrl = jSONObject.optString("checkCodeUrl");
            checkSmsCodeForRegisterResult.clientVerifyData = jSONObject.optString("clientVerifyData");
            return checkSmsCodeForRegisterResult;
        }

        protected Result<CheckSmsCodeForRegisterResult> parseJsonResult(Result<JSONObject> result) {
            JSONObject jSONObject = result.data;
            return jSONObject == null ? Result.result(result.code, result.message) : Result.result(result.code, result.message, parseData(jSONObject));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<CheckSmsCodeForRegisterResult> asyncExecute(Void... voidArr) {
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            map.put("checkCodeRequest", map2);
            HashMap map3 = new HashMap();
            map3.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, RegisterActivity.this.mobileInputBox.getMobileLocationCode());
            map3.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, RegisterActivity.this.mobileInputBox.getEditTextContent());
            map3.put("smsCode", RegisterActivity.this.smsCodeInputBox.getInputBoxWithClear().getEditTextContent());
            if (!TextUtils.isEmpty(this.sig)) {
                map2.put("sig", this.sig);
            }
            if (!TextUtils.isEmpty(this.cSessionId)) {
                map2.put("csessionid", this.cSessionId);
            }
            if (!TextUtils.isEmpty(this.nocToken)) {
                map2.put("nctoken", this.nocToken);
                map3.put("smsToken", RegisterActivity.this.smsToken);
            }
            RegisterActivity.this.smsToken = "";
            map.put("checkSmsCodeRequest", map3);
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, map, "checksmscodeforregister"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Result<CheckSmsCodeForRegisterResult> result) {
            super.onPostExecute((CheckSmsCodeForRegisterTask) result);
            try {
                if (result == null) {
                    ToastUtils.toastSystemError(this.context);
                    return;
                }
                int i2 = result.code;
                if (i2 == 1) {
                    CheckSmsCodeForRegisterResult checkSmsCodeForRegisterResult = result.data;
                    if (checkSmsCodeForRegisterResult == null || TextUtils.isEmpty(checkSmsCodeForRegisterResult.token)) {
                        return;
                    }
                    Intent intent = new Intent(RegisterActivity.this, OpenAccountUIConfigs.MobileRegisterFlow.registerFillPasswordActivityClazz);
                    intent.putExtra("token", result.data.token);
                    intent.putExtra("loginId", RegisterActivity.this.mobileInputBox.getEditTextContent());
                    RegisterActivity.this.startActivityForResult(intent, 1);
                    return;
                }
                if (i2 != 26053) {
                    if (TextUtils.isEmpty(result.message)) {
                        ToastUtils.toastSystemError(this.context);
                        return;
                    } else {
                        ToastUtils.toast(this.context, result.message, result.code);
                        return;
                    }
                }
                CheckSmsCodeForRegisterResult checkSmsCodeForRegisterResult2 = result.data;
                if (checkSmsCodeForRegisterResult2 == null || TextUtils.isEmpty(checkSmsCodeForRegisterResult2.clientVerifyData)) {
                    return;
                }
                Uri.Builder builderBuildUpon = Uri.parse(result.data.clientVerifyData).buildUpon();
                builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                Intent intent2 = new Intent(RegisterActivity.this, (Class<?>) LoginDoubleCheckWebActivity.class);
                intent2.putExtra("url", builderBuildUpon.toString());
                intent2.putExtra("title", result.message);
                intent2.putExtra("callback", "https://www.alipay.com/webviewbridge");
                RegisterActivity.this.startActivityForResult(intent2, RequestCode.REGISTER_NO_CAPTCHA_REQUEST_CODE);
            } catch (Throwable th) {
                th.printStackTrace();
                ToastUtils.toastSystemError(this.context);
            }
        }
    }

    protected class SendSmsCodeForRegisterTask extends TaskWithToastMessage<SendSmsCodeForRegisterResult> {
        public SendSmsCodeForRegisterTask(Activity activity) {
            super(activity);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doFailAfterToast(Result<SendSmsCodeForRegisterResult> result) {
            if (result.code == 26053) {
                RegisterActivity registerActivity = RegisterActivity.this;
                registerActivity.clientVerifyData = result.data.clientVerifyData;
                registerActivity.smsCodeInputBox.startTimer(registerActivity);
            }
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected void doSuccessAfterToast(Result<SendSmsCodeForRegisterResult> result) {
            RegisterActivity registerActivity = RegisterActivity.this;
            registerActivity.smsCodeInputBox.startTimer(registerActivity);
            RegisterActivity.this.smsCodeInputBox.requestFocus();
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        protected boolean toastMessageRequired(Result<SendSmsCodeForRegisterResult> result) {
            return result.code != 26053;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        public Result<SendSmsCodeForRegisterResult> asyncExecute(Void... voidArr) {
            RegisterActivity.this.clientVerifyData = null;
            HashMap map = new HashMap();
            map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, RegisterActivity.this.mobileInputBox.getEditTextContent());
            map.put(AlinkConstants.KEY_MOBILE_LOCATION_CODE, RegisterActivity.this.mobileInputBox.getMobileLocationCode());
            return parseJsonResult(RpcUtils.invokeWithRiskControlInfo("registerRequest", map, "sendsmscodeforregister"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.alibaba.sdk.android.openaccount.ui.task.TaskWithToastMessage
        public SendSmsCodeForRegisterResult parseData(JSONObject jSONObject) {
            SendSmsCodeForRegisterResult sendSmsCodeForRegisterResult = new SendSmsCodeForRegisterResult();
            sendSmsCodeForRegisterResult.checkCodeId = jSONObject.optString("checkCodeId");
            sendSmsCodeForRegisterResult.checkCodeUrl = jSONObject.optString("checkCodeUrl");
            sendSmsCodeForRegisterResult.clientVerifyData = jSONObject.optString("clientVerifyData");
            return sendSmsCodeForRegisterResult;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goSetPwd(String str, String str2, String str3) {
        new CheckSmsCodeForRegisterTask(this, str, str2, str3).execute(new Void[0]);
    }

    protected void addSendListener() {
        this.smsCodeInputBox.addSendClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.RegisterActivity.2
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                RegisterActivity registerActivity = RegisterActivity.this;
                registerActivity.new SendSmsCodeForRegisterTask(registerActivity).execute(new Void[0]);
            }
        });
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_register";
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected LoginCallback getLoginCallback() {
        return OpenAccountUIServiceImpl._registerCallback;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 != RequestCode.REGISTER_NO_CAPTCHA_REQUEST_CODE) {
            if (i2 == 1 && i3 == -1) {
                finishWithoutCallback();
                LoginCallback loginCallback = getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onSuccess(this.sessionManagerService.getSession());
                    return;
                }
                return;
            }
            return;
        }
        if (i3 != -1) {
            this.smsToken = "";
        } else {
            if (intent == null || !"nocaptcha".equals(intent.getStringExtra("action"))) {
                return;
            }
            String stringExtra = intent.getStringExtra("cSessionId");
            goSetPwd(intent.getStringExtra("sig"), intent.getStringExtra("nocToken"), stringExtra);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, com.alibaba.sdk.android.openaccount.ui.ui.NextStepActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.clientVerifyData = (String) bundle.get("clientVerifyData");
        }
        this.next.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.RegisterActivity.1
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
            public void afterCheck(View view) {
                RegisterActivity.this.goSetPwd(null, null, null);
            }
        });
        addSendListener();
        this.mobileInputBox.setSupportForeignMobile(this, OpenAccountUIConfigs.MobileRegisterFlow.mobileCountrySelectorActvityClazz, OpenAccountUIConfigs.MobileRegisterFlow.supportForeignMobileNumbers);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putString("clientVerifyData", this.clientVerifyData);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.SendSmsCodeActivity
    protected void onUserCancel() {
        LoginCallback loginCallback = getLoginCallback();
        if (loginCallback != null) {
            loginCallback.onFailure(UIMessageConstants.OPEN_ACCOUNT_REGISTER_CANCEL, MessageUtils.getMessageContent(UIMessageConstants.OPEN_ACCOUNT_REGISTER_CANCEL, new Object[0]));
        }
    }
}
