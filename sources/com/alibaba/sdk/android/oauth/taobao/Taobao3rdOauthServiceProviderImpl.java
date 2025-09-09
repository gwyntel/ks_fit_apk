package com.alibaba.sdk.android.oauth.taobao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.InitResultCallback;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.sdk.android.oauth.AppCredential;
import com.alibaba.sdk.android.oauth.BindByOauthTask;
import com.alibaba.sdk.android.oauth.LoginByOauthRequest;
import com.alibaba.sdk.android.oauth.LoginByOauthTask;
import com.alibaba.sdk.android.oauth.OauthServiceProvider;
import com.alibaba.sdk.android.oauth.callback.OABindCallback;
import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener;
import com.alibaba.sdk.android.openaccount.security.SecurityGuardService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class Taobao3rdOauthServiceProviderImpl implements OauthServiceProvider, EnvironmentChangeListener {
    private static final String TAG = "oa_TaobaoOauth";

    @Autowired
    private ConfigService configService;
    private OauthServiceProvider oauthServiceProvider;

    @Autowired
    private SecurityGuardService securityGuardService;

    /* renamed from: com.alibaba.sdk.android.oauth.taobao.Taobao3rdOauthServiceProviderImpl$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$sdk$android$openaccount$Environment;

        static {
            int[] iArr = new int[Environment.values().length];
            $SwitchMap$com$alibaba$sdk$android$openaccount$Environment = iArr;
            try {
                iArr[Environment.TEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$openaccount$Environment[Environment.ONLINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$openaccount$Environment[Environment.PRE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$alibaba$sdk$android$openaccount$Environment[Environment.SANDBOX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void initEnvironment(Environment environment) {
        int i2 = AnonymousClass6.$SwitchMap$com$alibaba$sdk$android$openaccount$Environment[environment.ordinal()];
        if (i2 == 1) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.TEST);
        } else if (i2 == 2) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.ONLINE);
        } else if (i2 == 3) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.PRE);
        } else if (i2 == 4) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.SANDBOX);
        }
        ConfigManager.APP_KEY_INDEX = this.configService.getAppKeyIndex();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void oauthTaobao(final Activity activity, final LoginCallback loginCallback, LoginService loginService) {
        loginService.auth(activity, new com.ali.auth.third.core.callback.LoginCallback() { // from class: com.alibaba.sdk.android.oauth.taobao.Taobao3rdOauthServiceProviderImpl.3
            public void onFailure(int i2, String str) {
                AliSDKLogger.e(Taobao3rdOauthServiceProviderImpl.TAG, "Taobao login failed with code " + i2 + " message " + str);
            }

            public void onSuccess(Session session) {
                LoginByOauthRequest loginByOauthRequest = new LoginByOauthRequest();
                loginByOauthRequest.accessToken = session.openSid;
                loginByOauthRequest.openId = session.openId;
                loginByOauthRequest.oauthAppKey = Taobao3rdOauthServiceProviderImpl.this.securityGuardService.getAppKey();
                loginByOauthRequest.oauthPlateform = 1;
                loginByOauthRequest.tokenType = "havana-sid";
                HashMap map = new HashMap();
                map.put("avatarUrl", session.avatarUrl);
                map.put("nick", session.nick);
                map.put("openId", session.openId);
                new LoginByOauthTask(activity, loginCallback, map, loginByOauthRequest, Taobao3rdOauthServiceProviderImpl.this.oauthServiceProvider).execute(new Void[0]);
            }
        });
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void authorizeCallback(int i2, int i3, Intent intent) {
        CallbackContext.onActivityResult(i2, i3, intent);
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void bind(final Activity activity, int i2, AppCredential appCredential, final OABindCallback oABindCallback) {
        ((LoginService) MemberSDK.getService(LoginService.class)).auth(activity, new com.ali.auth.third.core.callback.LoginCallback() { // from class: com.alibaba.sdk.android.oauth.taobao.Taobao3rdOauthServiceProviderImpl.4
            public void onFailure(int i3, String str) {
                AliSDKLogger.e(Taobao3rdOauthServiceProviderImpl.TAG, "Taobao login failed with code " + i3 + " message " + str);
            }

            public void onSuccess(Session session) {
                LoginByOauthRequest loginByOauthRequest = new LoginByOauthRequest();
                loginByOauthRequest.accessToken = session.openSid;
                loginByOauthRequest.openId = session.openId;
                loginByOauthRequest.oauthAppKey = Taobao3rdOauthServiceProviderImpl.this.securityGuardService.getAppKey();
                loginByOauthRequest.oauthPlateform = 1;
                loginByOauthRequest.tokenType = "havana-sid";
                HashMap map = new HashMap();
                map.put("avatarUrl", session.avatarUrl);
                map.put("nick", session.nick);
                map.put("openId", session.openId);
                new BindByOauthTask(activity, oABindCallback, map, loginByOauthRequest, Taobao3rdOauthServiceProviderImpl.this.oauthServiceProvider).execute(new Void[0]);
            }
        });
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void cleanUp() {
    }

    public void init(Context context) {
        this.oauthServiceProvider = this;
        if (this.configService.getBooleanProperty("disable3rdMemberSdkInit", false)) {
            return;
        }
        ConfigManager.POSTFIX_OF_SECURITY_JPG_USER_SET = this.configService.getSecurityImagePostfix();
        if (this.configService.isDebugEnabled()) {
            MemberSDK.turnOnDebug();
        }
        initEnvironment(this.configService.getEnvironment());
        MemberSDK.init(context, new InitResultCallback() { // from class: com.alibaba.sdk.android.oauth.taobao.Taobao3rdOauthServiceProviderImpl.1
            public void onFailure(int i2, String str) {
                AliSDKLogger.e(Taobao3rdOauthServiceProviderImpl.TAG, "MemberSDK initialized failed code = " + i2 + " message = " + str);
            }

            public void onSuccess() {
                AliSDKLogger.i(Taobao3rdOauthServiceProviderImpl.TAG, "MemberSDK initialized successfully");
            }
        });
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void logout(Context context, LogoutCallback logoutCallback) {
        ((LoginService) MemberSDK.getService(LoginService.class)).logout(new com.ali.auth.third.login.callback.LogoutCallback() { // from class: com.alibaba.sdk.android.oauth.taobao.Taobao3rdOauthServiceProviderImpl.5
            public void onFailure(int i2, String str) {
            }

            public void onSuccess() {
            }
        });
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void oauth(final Activity activity, int i2, AppCredential appCredential, final LoginCallback loginCallback) {
        final LoginService loginService = (LoginService) MemberSDK.getService(LoginService.class);
        loginService.logout(activity, new com.ali.auth.third.login.callback.LogoutCallback() { // from class: com.alibaba.sdk.android.oauth.taobao.Taobao3rdOauthServiceProviderImpl.2
            public void onFailure(int i3, String str) {
                Taobao3rdOauthServiceProviderImpl.this.oauthTaobao(activity, loginCallback, loginService);
            }

            public void onSuccess() {
                Taobao3rdOauthServiceProviderImpl.this.oauthTaobao(activity, loginCallback, loginService);
            }
        });
    }

    @Override // com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener
    public void onEnvironmentChange(Environment environment, Environment environment2) {
        initEnvironment(environment2);
    }
}
