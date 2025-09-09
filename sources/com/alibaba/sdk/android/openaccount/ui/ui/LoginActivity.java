package com.alibaba.sdk.android.openaccount.ui.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OauthService;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.annotation.ExtensionPoint;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.LoginResult;
import com.alibaba.sdk.android.openaccount.model.LoginSuccessResult;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.model.SessionData;
import com.alibaba.sdk.android.openaccount.model.UserContract;
import com.alibaba.sdk.android.openaccount.rpc.RpcServerBizConstants;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIService;
import com.alibaba.sdk.android.openaccount.ui.R;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.callback.EmailResetPasswordCallback;
import com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountUIServiceImpl;
import com.alibaba.sdk.android.openaccount.ui.model.SmsActionType;
import com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask;
import com.alibaba.sdk.android.openaccount.ui.util.AttributeUtils;
import com.alibaba.sdk.android.openaccount.ui.util.StringUtils;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import com.alibaba.sdk.android.openaccount.ui.widget.InputBoxWithHistory;
import com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener;
import com.alibaba.sdk.android.openaccount.ui.widget.NextStepButtonWatcher;
import com.alibaba.sdk.android.openaccount.ui.widget.NonMultiClickListener;
import com.alibaba.sdk.android.openaccount.ui.widget.OauthWidget;
import com.alibaba.sdk.android.openaccount.ui.widget.PasswordInputBox;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.Md5Utils;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.util.RpcUtils;
import com.alibaba.sdk.android.openaccount.util.safe.RSAKey;
import com.alibaba.sdk.android.openaccount.util.safe.Rsa;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.mipush.sdk.Constants;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.ES6Iterator;

@ExtensionPoint
/* loaded from: classes2.dex */
public class LoginActivity extends NextStepActivityTemplate {
    protected static final String TAG = "oa";
    protected InputBoxWithHistory loginIdEdit;
    protected TextView loginWithSmsCodeTV;
    protected OauthWidget oauthWidget;
    protected PasswordInputBox passwordEdit;
    protected TextView registerTV;
    protected TextView resetPasswordTV;

    @Autowired
    protected SessionManagerService sessionManagerService;
    protected String token;

    protected class LoginTask extends TaskWithDialog<Void, Void, Result<LoginResult>> {
        private String cSessionId;
        private String loginId;
        private String nocToken;
        private String password;
        private String sig;

        public LoginTask(Activity activity, String str, String str2, String str3, String str4, String str5) {
            super(activity);
            this.loginId = str;
            this.password = str2;
            this.sig = str3;
            this.nocToken = str4;
            this.cSessionId = str5;
        }

        @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
        protected void doWhenException(Throwable th) {
            this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.LoginTask.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtils.toastSystemError(((TaskWithDialog) LoginTask.this).context);
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
            if (this.password != null) {
                try {
                    String rsaPubkey = RSAKey.getRsaPubkey();
                    if (TextUtils.isEmpty(rsaPubkey)) {
                        return null;
                    }
                    map.put("password", Rsa.encrypt(this.password, rsaPubkey));
                } catch (Exception unused) {
                    return null;
                }
            }
            LoginActivity.this.hideSoftInputForHw();
            if (!CommonUtils.isNetworkAvailable()) {
                if (ConfigManager.getInstance().isSupportOfflineLogin()) {
                    return LoginActivity.this.tryOfflineLogin(this.loginId, this.password);
                }
                Result<LoginResult> result = new Result<>();
                result.code = MessageConstants.NETWORK_NOT_AVAILABLE;
                result.message = MessageUtils.getMessageContent(MessageConstants.NETWORK_NOT_AVAILABLE, new Object[0]);
                return result;
            }
            String str2 = this.sig;
            if (str2 != null) {
                map.put("sig", str2);
            }
            if (!TextUtils.isEmpty(this.cSessionId)) {
                map.put("csessionid", this.cSessionId);
            }
            if (!TextUtils.isEmpty(this.nocToken)) {
                map.put("nctoken", this.nocToken);
            }
            Result<LoginResult> loginResult = OpenAccountUtils.toLoginResult(RpcUtils.pureInvokeWithRiskControlInfo("loginRequest", map, FirebaseAnalytics.Event.LOGIN));
            return (ConfigManager.getInstance().isSupportOfflineLogin() && loginResult.code == 10019) ? LoginActivity.this.tryOfflineLogin(this.loginId, this.password) : loginResult;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Result<LoginResult> result) {
            dismissProgressDialog();
            super.onPostExecute((LoginTask) result);
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
                    LoginActivity.this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult);
                    String str = result.data.userInputName;
                    if (TextUtils.isEmpty(str)) {
                        str = this.loginId;
                    }
                    if (ConfigManager.getInstance().isSupportOfflineLogin()) {
                        OpenAccountSDK.getSqliteUtil().saveToSqlite(this.loginId, this.password);
                    }
                    boolean zSaveInputHistory = LoginActivity.this.loginIdEdit.saveInputHistory(str);
                    if (!OpenAccountUIConfigs.AccountPasswordLoginFlow.showTipAlertAfterLogin || zSaveInputHistory) {
                        LoginActivity.this.loginSuccess();
                        return;
                    } else {
                        LoginActivity.this.showTipDialog(String.format(ResourceUtils.getString(LoginActivity.this.getApplicationContext(), "ali_sdk_openaccount_dynamic_text_alert_msg_after_login"), this.loginId));
                        return;
                    }
                }
                if (i2 == 2) {
                    SessionData sessionDataCreateSessionDataFromLoginSuccessResult2 = OpenAccountUtils.createSessionDataFromLoginSuccessResult(result.data.loginSuccessResult);
                    if (sessionDataCreateSessionDataFromLoginSuccessResult2.scenario == null) {
                        sessionDataCreateSessionDataFromLoginSuccessResult2.scenario = 1;
                    }
                    LoginActivity.this.sessionManagerService.updateSession(sessionDataCreateSessionDataFromLoginSuccessResult2);
                    LoginActivity.this.loginSuccess();
                    return;
                }
                if (i2 == 4037) {
                    if (OpenAccountUIConfigs.AccountPasswordLoginFlow.showAlertForPwdErrorToManyTimes) {
                        String string = LoginActivity.this.getResources().getString(R.string.ali_sdk_openaccount_text_confirm);
                        String string2 = LoginActivity.this.getResources().getString(R.string.ali_sdk_openaccount_text_reset_password);
                        final ToastUtils toastUtils = new ToastUtils();
                        toastUtils.alert(LoginActivity.this, "", result.message, string, new DialogInterface.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.LoginTask.2
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i3) {
                                toastUtils.dismissAlertDialog(LoginActivity.this);
                            }
                        }, string2, new DialogInterface.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.LoginTask.3
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i3) {
                                LoginActivity.this.forgetPassword(null);
                            }
                        });
                        return;
                    }
                    ToastUtils.toast(this.context, result.message + result.code, result.code);
                    return;
                }
                if (i2 == 26053) {
                    LoginResult loginResult2 = result.data;
                    if (loginResult2 == null || loginResult2.checkCodeResult == null || TextUtils.isEmpty(loginResult2.checkCodeResult.clientVerifyData)) {
                        return;
                    }
                    Uri.Builder builderBuildUpon = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                    builderBuildUpon.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                    Intent intent = new Intent(LoginActivity.this, (Class<?>) LoginDoubleCheckWebActivity.class);
                    intent.putExtra("url", builderBuildUpon.toString());
                    intent.putExtra("title", result.message);
                    intent.putExtra("callback", "https://www.alipay.com/webviewbridge");
                    LoginActivity.this.startActivityForResult(intent, RequestCode.NO_CAPTCHA_REQUEST_CODE);
                    return;
                }
                if (i2 != 26152) {
                    if (i2 == 40399) {
                        Context context = this.context;
                        ToastUtils.toast(context, context.getString(R.string.please_check_phone_time), RpcServerBizConstants.PWD_ERROR_TO_TIME_EXPIRED);
                        return;
                    } else if (!TextUtils.equals(result.type, RpcServerBizConstants.ACTION_TYPE_CALLBACK) || LoginActivity.this.getLoginCallback() == null) {
                        LoginActivity.this.onPwdLoginFail(result.code, result.message);
                        return;
                    } else {
                        LoginActivity.this.getLoginCallback().onFailure(result.code, result.message);
                        return;
                    }
                }
                LoginResult loginResult3 = result.data;
                if (loginResult3 == null || loginResult3.checkCodeResult == null || TextUtils.isEmpty(loginResult3.checkCodeResult.clientVerifyData)) {
                    return;
                }
                Uri.Builder builderBuildUpon2 = Uri.parse(result.data.checkCodeResult.clientVerifyData).buildUpon();
                builderBuildUpon2.appendQueryParameter("callback", "https://www.alipay.com/webviewbridge");
                Intent intent2 = new Intent(LoginActivity.this, (Class<?>) LoginIVWebActivity.class);
                intent2.putExtra("url", builderBuildUpon2.toString());
                intent2.putExtra("title", result.message);
                intent2.putExtra("callback", "https://www.alipay.com/webviewbridge");
                LoginActivity.this.startActivityForResult(intent2, RequestCode.RISK_IV_REQUEST_CODE);
            } catch (Throwable th) {
                AliSDKLogger.e("oa", "after post execute error", th);
                ToastUtils.toastSystemError(this.context);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideSoftInputForHw() {
        InputMethodManager inputMethodManager;
        if (!"HUAWEI".equalsIgnoreCase(Build.MANUFACTURER) || Build.VERSION.SDK_INT < 27 || (inputMethodManager = (InputMethodManager) getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(this.passwordEdit.getEditText().getWindowToken(), 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginSuccess() {
        AliSDKLogger.i("oa", "loginSuccess");
        ((ExecutorService) OpenAccountSDK.getService(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.11
            @Override // java.lang.Runnable
            public void run() {
                LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    AliSDKLogger.i("oa", "loginCallback != null");
                    loginCallback.onSuccess(LoginActivity.this.sessionManagerService.getSession());
                }
                ((ExecutorService) OpenAccountSDK.getService(ExecutorService.class)).postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.11.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LoginActivity.this.finishWithoutCallback();
                    }
                });
            }
        });
    }

    protected LoginTask createLoginTask(String str, String str2, String str3) {
        String string;
        String loginId = getLoginId();
        if (loginId == null || loginId.length() <= 0 || (string = this.passwordEdit.getEditText().getText().toString()) == null || loginId.length() <= 0) {
            return null;
        }
        return new LoginTask(this, loginId, string, str, str2, str3);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected void doUseCustomAttrs(Context context, TypedArray typedArray) {
        super.doUseCustomAttrs(context, typedArray);
        TextView textView = this.registerTV;
        if (textView != null) {
            textView.setTextColor(AttributeUtils.getColor(context, typedArray, "ali_sdk_openaccount_attrs_login_register_text_color"));
        }
        TextView textView2 = this.resetPasswordTV;
        if (textView2 != null) {
            textView2.setTextColor(AttributeUtils.getColor(context, typedArray, "ali_sdk_openaccount_attrs_login_reset_password_text_color"));
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        LoginCallback loginCallback = getLoginCallback();
        if (loginCallback != null) {
            Message messageCreateMessage = MessageUtils.createMessage(10003, new Object[0]);
            loginCallback.onFailure(messageCreateMessage.code, messageCreateMessage.message);
        }
    }

    public void finishWithoutCallback() {
        super.finish();
    }

    public void forgetPassword(View view) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.loginIdEdit.getInputBoxWithClear().getMobileLocationCode())) {
            map.put("LocationCode", this.loginIdEdit.getInputBoxWithClear().getMobileLocationCode());
        }
        String string = this.loginIdEdit.getEditText().getText().toString();
        map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, string);
        OpenAccountUIService openAccountUIService = (OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class);
        if (StringUtils.isEmail(string)) {
            openAccountUIService.showEmailResetPassword(this, OpenAccountUIConfigs.EmailResetPasswordLoginFlow.resetPasswordActivityClazz, getResetPasswordCallback4Email());
        } else {
            openAccountUIService.showResetPassword(this, map, OpenAccountUIConfigs.UnifyLoginFlow.resetPasswordActivityClass, getResetPasswordLoginCallback());
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_login";
    }

    protected LoginCallback getLoginCallback() {
        LoginCallback loginCallback = OpenAccountUIServiceImpl._loginCallback;
        if (loginCallback != null) {
            return loginCallback;
        }
        return null;
    }

    protected String getLoginId() {
        InputBoxWithHistory inputBoxWithHistory = this.loginIdEdit;
        if (inputBoxWithHistory == null) {
            return "";
        }
        String mobileLocationCode = inputBoxWithHistory.getInputBoxWithClear().getMobileLocationCode();
        if (TextUtils.isEmpty(mobileLocationCode) || TextUtils.equals("86", mobileLocationCode)) {
            return this.loginIdEdit.getEditText().getText().toString();
        }
        return mobileLocationCode + Constants.ACCEPT_TIME_SEPARATOR_SERVER + this.loginIdEdit.getEditText().getText().toString();
    }

    protected LoginCallback getRegisterLoginCallback() {
        return new LoginCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.14
            @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
            public void onFailure(int i2, String str) {
                LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onFailure(i2, str);
                }
            }

            @Override // com.alibaba.sdk.android.openaccount.callback.LoginCallback
            public void onSuccess(OpenAccountSession openAccountSession) {
                LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onSuccess(openAccountSession);
                }
                LoginActivity.this.finishWithoutCallback();
            }
        };
    }

    protected EmailResetPasswordCallback getResetPasswordCallback4Email() {
        return new EmailResetPasswordCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.12
            @Override // com.alibaba.sdk.android.openaccount.ui.callback.EmailResetPasswordCallback
            public void onEmailSent(String str) {
                String string = ResourceUtils.getString(LoginActivity.this, "alisdk_openaccount_message_email_already_send");
                Toast.makeText(LoginActivity.this.getApplicationContext(), str + string, 1).show();
            }

            @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
            public void onFailure(int i2, String str) {
                LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onFailure(i2, str);
                }
            }

            @Override // com.alibaba.sdk.android.openaccount.callback.LoginCallback
            public void onSuccess(OpenAccountSession openAccountSession) {
                LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onSuccess(openAccountSession);
                }
                LoginActivity.this.finishWithoutCallback();
            }
        };
    }

    protected LoginCallback getResetPasswordLoginCallback() {
        return new LoginCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.13
            @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
            public void onFailure(int i2, String str) {
                LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onFailure(i2, str);
                }
            }

            @Override // com.alibaba.sdk.android.openaccount.callback.LoginCallback
            public void onSuccess(OpenAccountSession openAccountSession) {
                LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                if (loginCallback != null) {
                    loginCallback.onSuccess(openAccountSession);
                }
                LoginActivity.this.finishWithoutCallback();
            }
        };
    }

    protected boolean handleIVResult(Intent intent) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("havana_iv_token");
            String stringExtra2 = intent.getStringExtra("actionType");
            InputBoxWithHistory inputBoxWithHistory = this.loginIdEdit;
            if (inputBoxWithHistory != null && inputBoxWithHistory.getEditText() != null && this.loginIdEdit.getEditText().getText() != null) {
                String string = this.loginIdEdit.getEditText().getText().toString();
                if (string != null && string.length() > 0 && !TextUtils.isEmpty(stringExtra)) {
                    new IVTask(this, stringExtra, string, stringExtra2).execute(new Void[0]);
                }
                return true;
            }
        }
        return false;
    }

    public void login(View view) {
        login(view, null, null, null);
    }

    public void loginWithSmsCode(View view) {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.loginIdEdit.getInputBoxWithClear().getMobileLocationCode())) {
            map.put("LocationCode", this.loginIdEdit.getInputBoxWithClear().getMobileLocationCode());
        }
        map.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, this.loginIdEdit.getEditText().getText().toString());
        ((OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class)).showLoginWithSmsCode(this, map, OpenAccountUIConfigs.LoginWithSmsCodeFlow.loginWithSmsCodeActivityClass, getResetPasswordLoginCallback());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 == RequestCode.NO_CAPTCHA_REQUEST_CODE && i3 == -1) {
            if (intent != null && "nocaptcha".equals(intent.getStringExtra("action"))) {
                String stringExtra = intent.getStringExtra("cSessionId");
                login(null, intent.getStringExtra("sig"), intent.getStringExtra("nocToken"), stringExtra);
            }
        } else if (i2 != RequestCode.RISK_IV_REQUEST_CODE || i3 != -1) {
            InputBoxWithHistory inputBoxWithHistory = this.loginIdEdit;
            if (inputBoxWithHistory != null && inputBoxWithHistory.getInputBoxWithClear().onActivityResult(i2, i3, intent)) {
                return;
            }
        } else if (handleIVResult(intent)) {
            return;
        }
        OauthWidget oauthWidget = this.oauthWidget;
        if (oauthWidget == null || oauthWidget.getVisibility() != 0) {
            return;
        }
        this.oauthWidget.authorizeCallback(i2, i3, intent);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.NextStepActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        InputBoxWithHistory inputBoxWithHistory = (InputBoxWithHistory) findViewById("login_id");
        this.loginIdEdit = inputBoxWithHistory;
        inputBoxWithHistory.getInputBoxWithClear().setSupportForeignMobile(this, OpenAccountUIConfigs.AccountPasswordLoginFlow.mobileCountrySelectorActvityClazz, OpenAccountUIConfigs.AccountPasswordLoginFlow.supportForeignMobileNumbers);
        this.passwordEdit = (PasswordInputBox) findViewById("password");
        this.loginIdEdit.setHistoryView((ListView) findViewById("input_history"));
        Button button = (Button) findViewById(ES6Iterator.NEXT_METHOD);
        if (ConfigManager.getInstance().isSupportOfflineLogin()) {
            button.setOnClickListener(new NonMultiClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.1
                @Override // com.alibaba.sdk.android.openaccount.ui.widget.NonMultiClickListener
                public void onMonMultiClick(View view) {
                    LoginActivity.this.login(view);
                }
            });
        } else {
            button.setOnClickListener(new NetworkCheckOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.2
                @Override // com.alibaba.sdk.android.openaccount.ui.widget.NetworkCheckOnClickListener
                public void afterCheck(View view) {
                    LoginActivity.this.login(view);
                }
            });
        }
        NextStepButtonWatcher nextStepButtonWatcher = getNextStepButtonWatcher();
        nextStepButtonWatcher.addEditTexts(this.loginIdEdit.getEditText(), this.passwordEdit.getEditText());
        this.passwordEdit.getInputBoxWithClear().getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                if (i2 != 6) {
                    return false;
                }
                LoginActivity.this.login(null);
                return true;
            }
        });
        this.passwordEdit.getInputBoxWithClear().addTextChangedListener(nextStepButtonWatcher);
        this.loginIdEdit.getInputBoxWithClear().addTextChangedListener(nextStepButtonWatcher);
        this.loginIdEdit.getEditText().addTextChangedListener(new TextWatcher() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.4
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                LoginActivity.this.loginIdEdit.updateHistoryView(editable.toString());
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        });
        if (bundle != null) {
            String string = bundle.getString("login_id");
            if (!TextUtils.isEmpty(string)) {
                this.loginIdEdit.getInputBoxWithClear().getEditText().setText(string);
            }
        }
        TextView textView = (TextView) findViewById(ResourceUtils.getRId(this, "register"));
        this.registerTV = textView;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    LoginActivity.this.registerUser(view);
                }
            });
        }
        TextView textView2 = (TextView) findViewById(ResourceUtils.getRId(this, SmsActionType.RESET_PASSWORD));
        this.resetPasswordTV = textView2;
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    LoginActivity.this.forgetPassword(view);
                }
            });
        }
        TextView textView3 = (TextView) findViewById(ResourceUtils.getRId(this, "login_with_sms_code"));
        this.loginWithSmsCodeTV = textView3;
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    LoginActivity.this.loginWithSmsCode(view);
                }
            });
        }
        OauthWidget oauthWidget = (OauthWidget) findViewById(ResourceUtils.getRId(this, "oauth"));
        this.oauthWidget = oauthWidget;
        if (oauthWidget != null) {
            if (OpenAccountSDK.getService(OauthService.class) == null) {
                this.oauthWidget.setVisibility(8);
            } else {
                this.oauthWidget.setOauthOnClickListener(new LoginCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.8
                    @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
                    public void onFailure(int i2, String str) {
                        LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                        if (loginCallback != null) {
                            loginCallback.onFailure(i2, str);
                        }
                    }

                    @Override // com.alibaba.sdk.android.openaccount.callback.LoginCallback
                    public void onSuccess(OpenAccountSession openAccountSession) {
                        LoginCallback loginCallback = LoginActivity.this.getLoginCallback();
                        if (loginCallback != null) {
                            loginCallback.onSuccess(openAccountSession);
                        }
                        LoginActivity.this.finishWithoutCallback();
                    }
                });
            }
        }
        useCustomAttrs(this, this.attrs);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.passwordEdit.getEditText().setText("");
        OpenAccountUIServiceImpl._loginCallback = null;
        if (OpenAccountSDK.getService(OauthService.class) != null) {
            ((OauthService) OpenAccountSDK.getService(OauthService.class)).cleanUp();
        }
    }

    protected void onPwdLoginFail(int i2, String str) {
        if (TextUtils.isEmpty(str)) {
            ToastUtils.toastSystemError(getApplicationContext());
            return;
        }
        ToastUtils.toast(getApplicationContext(), str + i2, i2);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("token", this.token);
        Editable text = this.loginIdEdit.getInputBoxWithClear().getEditText().getText();
        if (text != null) {
            bundle.putString("login_id", text.toString());
        }
    }

    public void registerUser(View view) {
        ((OpenAccountUIService) OpenAccountSDK.getService(OpenAccountUIService.class)).showRegister(this, getRegisterLoginCallback());
    }

    public void showTipDialog(String str) {
        if (isFinishing()) {
            finishWithoutCallback();
        } else {
            new AlertDialog.Builder(this).setMessage(str).setPositiveButton(ResourceUtils.getString(getApplicationContext(), "ali_sdk_openaccount_dynamic_text_iknow"), new DialogInterface.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.10
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i2) {
                    LoginActivity.this.loginSuccess();
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.LoginActivity.9
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    LoginActivity.this.loginSuccess();
                }
            }).show();
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [T, com.alibaba.sdk.android.openaccount.model.LoginResult] */
    public Result<LoginResult> tryOfflineLogin(String str, String str2) throws JSONException, NoSuchAlgorithmException {
        String sha256 = Md5Utils.getSHA256(str + "&" + str2);
        UserContract account = OpenAccountSDK.getSqliteUtil().getAccount(sha256);
        if (account == null || !TextUtils.equals(account.getHash(), sha256)) {
            Result<LoginResult> result = new Result<>();
            result.code = 3;
            result.message = ResourceUtils.getString(this, "ali_sdk_openaccount_dynamic_text_offline_exception");
            return result;
        }
        Result<LoginResult> result2 = new Result<>();
        result2.code = 2;
        ?? loginResult = new LoginResult();
        LoginSuccessResult loginSuccessResult = new LoginSuccessResult();
        JSONObject jSONObject = new JSONObject();
        loginSuccessResult.openAccount = jSONObject;
        try {
            jSONObject.put("id", account.getUserid());
            loginSuccessResult.openAccount.put(XiaomiOAuthConstants.EXTRA_DISPLAY_MOBILE, account.getMobile());
            loginSuccessResult.openAccount.put("nick", account.getNick());
            loginSuccessResult.openAccount.put("loginId", account.getLoginId());
            loginSuccessResult.openAccount.put("email", account.getEmail());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        loginResult.loginSuccessResult = loginSuccessResult;
        result2.data = loginResult;
        return result2;
    }

    protected class IVTask extends LoginByIVTokenTask {
        public IVTask(Activity activity, String str, String str2, String str3) {
            super(activity, str, str2, str3);
        }

        @Override // com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask
        protected void loginSuccess() {
            String loginId = LoginActivity.this.getLoginId();
            String string = LoginActivity.this.passwordEdit.getEditText().getText().toString();
            if (ConfigManager.getInstance().isSupportOfflineLogin()) {
                OpenAccountSDK.getSqliteUtil().saveToSqlite(loginId, string);
            }
            super.loginSuccess();
        }

        public IVTask(Activity activity, String str, String str2, String str3, boolean z2, LoginCallback loginCallback) {
            super(activity, str, str2, str3, z2, loginCallback);
        }
    }

    public void login(View view, String str, String str2, String str3) {
        LoginTask loginTaskCreateLoginTask = createLoginTask(str, str2, str3);
        if (loginTaskCreateLoginTask != null) {
            loginTaskCreateLoginTask.execute(new Void[0]);
        }
    }
}
