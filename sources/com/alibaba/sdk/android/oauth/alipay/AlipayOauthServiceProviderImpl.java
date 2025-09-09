package com.alibaba.sdk.android.oauth.alipay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.ali.auth.third.core.MemberSDK;
import com.alibaba.sdk.android.oauth.AppCredential;
import com.alibaba.sdk.android.oauth.BindByOauthTask;
import com.alibaba.sdk.android.oauth.LoginByOauthRequest;
import com.alibaba.sdk.android.oauth.LoginByOauthTask;
import com.alibaba.sdk.android.oauth.OauthServiceProvider;
import com.alibaba.sdk.android.oauth.callback.OABindCallback;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.FailureCallback;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;
import com.alibaba.sdk.android.openaccount.config.ConfigService;
import com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.m.u.l;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class AlipayOauthServiceProviderImpl implements OauthServiceProvider, EnvironmentChangeListener {
    public static final String TAG = "oa.AlipayOauthServiceProviderImpl";
    public static OABindCallback mBindCallback;
    public static LoginCallback mLoginCallback;

    @Autowired
    private ConfigService configService;
    private boolean mLoginAfterOauth = true;
    private OauthServiceProvider mOauthServiceProvider;

    /* renamed from: com.alibaba.sdk.android.oauth.alipay.AlipayOauthServiceProviderImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
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

    private void getSignTask(final Activity activity, final FailureCallback failureCallback) {
        final SignRequest signRequest = new SignRequest();
        signRequest.app_id = ConfigManager.getInstance().getAlipayAppId();
        signRequest.pid = ConfigManager.getInstance().getAlipayPid();
        signRequest.sign_type = ConfigManager.getInstance().getAlipaySignType();
        new GetAlipaySignTask(activity, new GetSignCallback() { // from class: com.alibaba.sdk.android.oauth.alipay.AlipayOauthServiceProviderImpl.1
            @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
            public void onFailure(int i2, String str) {
                CommonUtils.onFailure(failureCallback, i2, str);
            }

            @Override // com.alibaba.sdk.android.oauth.alipay.GetSignCallback
            public void onGetSignSuccessed(final String str) {
                ((ExecutorService) OpenAccountSDK.getService(ExecutorService.class)).postTask(new Runnable() { // from class: com.alibaba.sdk.android.oauth.alipay.AlipayOauthServiceProviderImpl.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Map<String, String> mapAuthV2 = new AuthTask(activity).authV2(str, true);
                        if (mapAuthV2 != null) {
                            if (ConfigManager.getInstance().isDebugEnabled()) {
                                AliSDKLogger.d(AlipayOauthServiceProviderImpl.TAG, "result=" + mapAuthV2.toString());
                            }
                            String str2 = mapAuthV2.get("result");
                            if (TextUtils.isEmpty(str2)) {
                                CommonUtils.onFailure(failureCallback, -1, mapAuthV2.get(l.f9813b));
                                return;
                            }
                            String[] strArrSplit = str2.split("&");
                            String str3 = "";
                            if (strArrSplit != null) {
                                for (String str4 : strArrSplit) {
                                    String[] strArrSplit2 = str4.split(ContainerUtils.KEY_VALUE_DELIMITER);
                                    if (strArrSplit2 != null && strArrSplit2.length == 2 && SaveAccountLinkingTokenRequest.TOKEN_TYPE_AUTH_CODE.equals(strArrSplit2[0])) {
                                        str3 = strArrSplit2[1];
                                    }
                                }
                            }
                            if (TextUtils.isEmpty(str3)) {
                                CommonUtils.onFailure(failureCallback, MessageUtils.createMessage(10010, new Object[0]));
                                return;
                            }
                            LoginByOauthRequest loginByOauthRequest = new LoginByOauthRequest();
                            loginByOauthRequest.accessToken = str3;
                            loginByOauthRequest.oauthPlateform = 5;
                            loginByOauthRequest.oauthAppKey = signRequest.app_id;
                            HashMap map = new HashMap();
                            if (AlipayOauthServiceProviderImpl.this.mLoginAfterOauth) {
                                new LoginByOauthTask(activity, AlipayOauthServiceProviderImpl.mLoginCallback, map, loginByOauthRequest, null).execute(new Void[0]);
                            } else {
                                new BindByOauthTask(activity, AlipayOauthServiceProviderImpl.mBindCallback, map, loginByOauthRequest, null).execute(new Void[0]);
                            }
                        }
                    }
                });
            }
        }, signRequest).execute(new Void[0]);
    }

    private void initEnvironment(Environment environment) {
        int i2 = AnonymousClass2.$SwitchMap$com$alibaba$sdk$android$openaccount$Environment[environment.ordinal()];
        if (i2 == 1) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.TEST);
        } else if (i2 == 2) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.ONLINE);
        } else if (i2 == 3) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.PRE);
        } else if (i2 == 4) {
            MemberSDK.setEnvironment(com.ali.auth.third.core.config.Environment.SANDBOX);
        }
        com.ali.auth.third.core.config.ConfigManager.APP_KEY_INDEX = this.configService.getAppKeyIndex();
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void authorizeCallback(int i2, int i3, Intent intent) {
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void bind(Activity activity, int i2, AppCredential appCredential, OABindCallback oABindCallback) {
        if (activity == null) {
            CommonUtils.onFailure(oABindCallback, MessageUtils.createMessage(10010, new Object[0]));
            return;
        }
        if (TextUtils.isEmpty(ConfigManager.getInstance().getAlipayAppId()) || TextUtils.isEmpty(ConfigManager.getInstance().getAlipayPid()) || TextUtils.isEmpty(ConfigManager.getInstance().getAlipaySignType())) {
            CommonUtils.onFailure(oABindCallback, MessageUtils.createMessage(MessageConstants.NOT_PROVIDE_ALIPY_SSO_PID_OR_APPID, new Object[0]));
            return;
        }
        this.mLoginAfterOauth = false;
        mBindCallback = oABindCallback;
        getSignTask(activity, oABindCallback);
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void cleanUp() {
        mLoginCallback = null;
        mBindCallback = null;
    }

    public void init(Context context) {
        this.mOauthServiceProvider = this;
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void logout(Context context, LogoutCallback logoutCallback) {
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void oauth(Activity activity, int i2, AppCredential appCredential, LoginCallback loginCallback) {
        if (activity == null) {
            CommonUtils.onFailure(loginCallback, MessageUtils.createMessage(10010, new Object[0]));
            return;
        }
        if (TextUtils.isEmpty(ConfigManager.getInstance().getAlipayAppId()) || TextUtils.isEmpty(ConfigManager.getInstance().getAlipayPid()) || TextUtils.isEmpty(ConfigManager.getInstance().getAlipaySignType())) {
            CommonUtils.onFailure(loginCallback, MessageUtils.createMessage(MessageConstants.NOT_PROVIDE_ALIPY_SSO_PID_OR_APPID, new Object[0]));
            return;
        }
        this.mLoginAfterOauth = true;
        mLoginCallback = loginCallback;
        getSignTask(activity, loginCallback);
    }

    @Override // com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener
    public void onEnvironmentChange(Environment environment, Environment environment2) {
        initEnvironment(environment2);
    }
}
