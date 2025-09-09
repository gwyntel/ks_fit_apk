package com.alibaba.sdk.android.openaccount.ui.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ali.user.mobile.app.dataprovider.DataProviderFactory;
import com.ali.user.mobile.common.api.AliUserLogin;
import com.ali.user.mobile.filter.OnActivityResultHandler;
import com.ali.user.mobile.filter.PreHandlerCallback;
import com.ali.user.mobile.filter.PreLoginFilter;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.security.SecurityGuardService;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.task.AbsRunnable;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountTaobaoUIService;
import com.alibaba.sdk.android.openaccount.ui.RequestCode;
import com.alibaba.sdk.android.openaccount.ui.task.LoginByIVTokenTask;
import com.alibaba.sdk.android.openaccount.ui.task.OpenAccountLoginTask;
import com.alibaba.sdk.android.openaccount.ui.ui.ResetOATaoPasswordActivity;
import com.alibaba.sdk.android.openaccount.ui.ut.UTConstants;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.openaccount.util.OpenAccountUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.taobao.login4android.Login;
import com.taobao.login4android.login.LoginController;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes2.dex */
public class OpenAccountTaobaoUIServiceImpl implements OpenAccountTaobaoUIService {
    public static WeakReference<PreHandlerCallback> _preHandlerCallback;
    public static LoginCallback _resetOATaoPasswordCallback;

    @Autowired
    private ConfigService configService;

    @Autowired
    private SecurityGuardService securityGuardService;

    @Autowired
    private SessionManagerService sessionManagerService;

    private void addInterceptor(final Context context, final LoginCallback loginCallback) {
        if (this.configService.openTaobaoUILogin()) {
            AliUserLogin.mPreLoginFiler = new PreLoginFilter() { // from class: com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountTaobaoUIServiceImpl.1
                public void preHandle(Activity activity, String str, String str2, PreHandlerCallback preHandlerCallback) {
                    OpenAccountTaobaoUIServiceImpl._preHandlerCallback = new WeakReference<>(preHandlerCallback);
                    if (!TextUtils.isEmpty(str) && str.length() == 11 && OpenAccountUtils.isNumeric(str)) {
                        new OpenAccountLoginTask(activity, str, str2, null, null, null, loginCallback, preHandlerCallback).execute(new Void[0]);
                    } else {
                        preHandlerCallback.onFail(0, (Map) null);
                    }
                }
            };
            AliUserLogin.mOnActivityResultHandler = new OnActivityResultHandler() { // from class: com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountTaobaoUIServiceImpl.2
                public void onActivityResult(int i2, int i3, Intent intent, Activity activity, String str, String str2, PreHandlerCallback preHandlerCallback) {
                    OpenAccountTaobaoUIServiceImpl._preHandlerCallback = new WeakReference<>(preHandlerCallback);
                    if (i2 == RequestCode.NO_CAPTCHA_REQUEST_CODE && i3 == -1) {
                        if (intent == null || !"nocaptcha".equals(intent.getStringExtra("action"))) {
                            preHandlerCallback.onFail(0, (Map) null);
                            return;
                        }
                        String stringExtra = intent.getStringExtra("cSessionId");
                        new OpenAccountLoginTask(activity, str, str2, intent.getStringExtra("sig"), intent.getStringExtra("nocToken"), stringExtra, loginCallback, preHandlerCallback).execute(new Void[0]);
                        return;
                    }
                    if (i2 != RequestCode.RISK_IV_REQUEST_CODE || i3 != -1) {
                        preHandlerCallback.onFail(0, (Map) null);
                        return;
                    }
                    if (intent == null) {
                        preHandlerCallback.onFail(0, (Map) null);
                        return;
                    }
                    String stringExtra2 = intent.getStringExtra("havana_iv_token");
                    String stringExtra3 = intent.getStringExtra("actionType");
                    if (str == null || str.length() <= 0) {
                        return;
                    }
                    new LoginByIVTokenTask(activity, stringExtra2, str, stringExtra3, false, loginCallback).execute(new Void[0]);
                }
            };
            AliUserLogin.mFindPwdFilter = new PreLoginFilter() { // from class: com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountTaobaoUIServiceImpl.3
                public void preHandle(final Activity activity, final String str, String str2, PreHandlerCallback preHandlerCallback) {
                    OpenAccountTaobaoUIServiceImpl._preHandlerCallback = new WeakReference<>(preHandlerCallback);
                    OpenAccountTaobaoUIServiceImpl._resetOATaoPasswordCallback = new LoginCallback() { // from class: com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountTaobaoUIServiceImpl.3.1
                        @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
                        public void onFailure(int i2, String str3) {
                        }

                        @Override // com.alibaba.sdk.android.openaccount.callback.LoginCallback
                        public void onSuccess(OpenAccountSession openAccountSession) {
                            Activity activity2 = activity;
                            if (activity2 != null) {
                                activity2.finish();
                            }
                            loginCallback.onSuccess(openAccountSession);
                        }
                    };
                    CommonUtils.startInitWaitTask(context, loginCallback, new AbsRunnable() { // from class: com.alibaba.sdk.android.openaccount.ui.impl.OpenAccountTaobaoUIServiceImpl.3.2
                        @Override // com.alibaba.sdk.android.openaccount.task.AbsRunnable
                        public void runWithoutException() {
                            Intent intent = new Intent(context, (Class<?>) ResetOATaoPasswordActivity.class);
                            if (!TextUtils.isEmpty(str)) {
                                intent.putExtra("username", str);
                            }
                            if (!(context instanceof Activity)) {
                                intent.addFlags(268435456);
                            }
                            context.startActivity(intent);
                        }
                    }, UTConstants.E_SHOW_RESET_OA_TAO_PASSWPRD);
                }
            };
        }
    }

    public void init() {
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.OpenAccountTaobaoUIService
    public void openLogin(Context context, boolean z2, LoginCallback loginCallback) {
        addInterceptor(context, loginCallback);
        LoginController.getInstance().openLoginPage(DataProviderFactory.getApplicationContext());
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.OpenAccountTaobaoUIService
    public void showLogin(Context context, boolean z2, LoginCallback loginCallback) {
        addInterceptor(context, loginCallback);
        Login.login(z2);
    }
}
