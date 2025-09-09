package com.alibaba.sdk.android.oauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.alibaba.sdk.android.oauth.callback.OABindCallback;
import com.alibaba.sdk.android.oauth.callback.OauthQueryCallback;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.OauthService;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.message.Message;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.OpenAccountLink;
import com.alibaba.sdk.android.openaccount.session.SessionManagerService;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.alibaba.sdk.android.pluto.annotation.BeanProperty;
import com.alibaba.sdk.android.pluto.annotation.Qualifier;
import com.umeng.analytics.pro.f;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class OauthServiceImpl implements OauthService {
    private static final String TAG = "oa_Oauth";

    @Autowired(proxyDisabled = true, required = false)
    @Qualifier(filters = {@BeanProperty(key = f.M, value = "ALIPAY")})
    private OauthServiceProvider alipayOauthServiceProvider;

    @Autowired
    private ExecutorService executorService;

    @Autowired(proxyDisabled = true, required = false)
    @Qualifier(filters = {@BeanProperty(key = f.M, value = "FACEBOOK")})
    private OauthServiceProvider facebookOauthServiceProvider;

    @Autowired(proxyDisabled = true, required = false)
    @Qualifier(filters = {@BeanProperty(key = f.M, value = "GOOGLE")})
    private OauthServiceProvider googleOauthServiceProvider;

    @Autowired
    private SessionManagerService sessionManagerService;

    @Autowired(proxyDisabled = true, required = false)
    @Qualifier(filters = {@BeanProperty(key = f.M, value = "TAOBAO2nd")})
    private OauthServiceProvider taobao2ndOauthServiceProvider;

    @Autowired(proxyDisabled = true, required = false)
    @Qualifier(filters = {@BeanProperty(key = f.M, value = "TAOBAO3rd")})
    private OauthServiceProvider taobao3rdOauthServiceProvider;

    @Autowired(proxyDisabled = true, required = false)
    @Qualifier(filters = {@BeanProperty(key = f.M, value = "TWITTER")})
    private OauthServiceProvider twitterOauthServiceProvider;

    @Autowired(proxyDisabled = true, required = false)
    @Qualifier(filters = {@BeanProperty(key = f.M, value = "UMENG")})
    private OauthServiceProvider umengOauthServiceProvider;
    private Map<Integer, AppCredential> plateform2Credential = new HashMap();
    private int currentOauthPlatform = -1;

    /* JADX INFO: Access modifiers changed from: private */
    public void oauthBind(Activity activity, int i2, OABindCallback oABindCallback) {
        this.currentOauthPlatform = i2;
        if (i2 == 1) {
            OauthServiceProvider oauthServiceProvider = this.taobao2ndOauthServiceProvider;
            if (oauthServiceProvider != null) {
                oauthServiceProvider.bind(activity, i2, null, oABindCallback);
                return;
            }
            OauthServiceProvider oauthServiceProvider2 = this.taobao3rdOauthServiceProvider;
            if (oauthServiceProvider2 != null) {
                oauthServiceProvider2.bind(activity, i2, null, oABindCallback);
                return;
            }
            Message messageCreateMessage = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "taobao");
            AliSDKLogger.log(TAG, messageCreateMessage);
            CommonUtils.onFailure(oABindCallback, messageCreateMessage);
            return;
        }
        if (i2 == 5) {
            OauthServiceProvider oauthServiceProvider3 = this.alipayOauthServiceProvider;
            if (oauthServiceProvider3 != null) {
                oauthServiceProvider3.bind(activity, i2, null, oABindCallback);
                return;
            }
            Message messageCreateMessage2 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "alipay");
            AliSDKLogger.log(TAG, messageCreateMessage2);
            CommonUtils.onFailure(oABindCallback, messageCreateMessage2);
            return;
        }
        OauthServiceProvider oauthServiceProvider4 = this.umengOauthServiceProvider;
        if (oauthServiceProvider4 != null) {
            oauthServiceProvider4.bind(activity, i2, this.plateform2Credential.get(Integer.valueOf(i2)), oABindCallback);
            return;
        }
        Message messageCreateMessage3 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, Integer.valueOf(i2));
        AliSDKLogger.log(TAG, messageCreateMessage3);
        CommonUtils.onFailure(oABindCallback, messageCreateMessage3);
    }

    private void oauthWhat(Activity activity, int i2, LoginCallback loginCallback) {
        this.currentOauthPlatform = i2;
        if (i2 == 1) {
            OauthServiceProvider oauthServiceProvider = this.taobao2ndOauthServiceProvider;
            if (oauthServiceProvider != null) {
                oauthServiceProvider.oauth(activity, i2, null, loginCallback);
                return;
            }
            OauthServiceProvider oauthServiceProvider2 = this.taobao3rdOauthServiceProvider;
            if (oauthServiceProvider2 != null) {
                oauthServiceProvider2.oauth(activity, i2, null, loginCallback);
                return;
            }
            Message messageCreateMessage = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "taobao");
            AliSDKLogger.log(TAG, messageCreateMessage);
            CommonUtils.onFailure(loginCallback, messageCreateMessage);
            return;
        }
        if (i2 == 5) {
            OauthServiceProvider oauthServiceProvider3 = this.alipayOauthServiceProvider;
            if (oauthServiceProvider3 != null) {
                oauthServiceProvider3.oauth(activity, i2, null, loginCallback);
                return;
            }
            Message messageCreateMessage2 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "alipay");
            AliSDKLogger.log(TAG, messageCreateMessage2);
            CommonUtils.onFailure(loginCallback, messageCreateMessage2);
            return;
        }
        if (i2 == 13) {
            OauthServiceProvider oauthServiceProvider4 = this.facebookOauthServiceProvider;
            if (oauthServiceProvider4 != null) {
                oauthServiceProvider4.oauth(activity, i2, null, loginCallback);
                return;
            }
            Message messageCreateMessage3 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "alipay");
            AliSDKLogger.log(TAG, messageCreateMessage3);
            CommonUtils.onFailure(loginCallback, messageCreateMessage3);
            return;
        }
        if (i2 == 32) {
            OauthServiceProvider oauthServiceProvider5 = this.googleOauthServiceProvider;
            if (oauthServiceProvider5 != null) {
                oauthServiceProvider5.oauth(activity, i2, null, loginCallback);
                return;
            }
            Message messageCreateMessage4 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "google");
            AliSDKLogger.log(TAG, messageCreateMessage4);
            CommonUtils.onFailure(loginCallback, messageCreateMessage4);
            return;
        }
        if (i2 == 33) {
            OauthServiceProvider oauthServiceProvider6 = this.twitterOauthServiceProvider;
            if (oauthServiceProvider6 != null) {
                oauthServiceProvider6.oauth(activity, i2, null, loginCallback);
                return;
            }
            Message messageCreateMessage5 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "twitter");
            AliSDKLogger.log(TAG, messageCreateMessage5);
            CommonUtils.onFailure(loginCallback, messageCreateMessage5);
            return;
        }
        OauthServiceProvider oauthServiceProvider7 = this.umengOauthServiceProvider;
        if (oauthServiceProvider7 != null) {
            oauthServiceProvider7.oauth(activity, i2, this.plateform2Credential.get(Integer.valueOf(i2)), loginCallback);
            return;
        }
        Message messageCreateMessage6 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, Integer.valueOf(i2));
        AliSDKLogger.log(TAG, messageCreateMessage6);
        CommonUtils.onFailure(loginCallback, messageCreateMessage6);
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void addAppCredential(int i2, String str, String str2) {
        this.plateform2Credential.put(Integer.valueOf(i2), new AppCredential(str, str2));
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void authorizeCallback(int i2, int i3, Intent intent) {
        OauthServiceProvider oauthServiceProvider = this.taobao3rdOauthServiceProvider;
        if (oauthServiceProvider != null) {
            oauthServiceProvider.authorizeCallback(i2, i3, intent);
        }
        OauthServiceProvider oauthServiceProvider2 = this.taobao2ndOauthServiceProvider;
        if (oauthServiceProvider2 != null) {
            oauthServiceProvider2.authorizeCallback(i2, i3, intent);
        }
        OauthServiceProvider oauthServiceProvider3 = this.umengOauthServiceProvider;
        if (oauthServiceProvider3 != null) {
            oauthServiceProvider3.authorizeCallback(i2, i3, intent);
        }
        OauthServiceProvider oauthServiceProvider4 = this.alipayOauthServiceProvider;
        if (oauthServiceProvider4 != null) {
            oauthServiceProvider4.authorizeCallback(i2, i3, intent);
        }
        OauthServiceProvider oauthServiceProvider5 = this.facebookOauthServiceProvider;
        if (oauthServiceProvider5 != null) {
            oauthServiceProvider5.authorizeCallback(i2, i3, intent);
        }
        OauthServiceProvider oauthServiceProvider6 = this.googleOauthServiceProvider;
        if (oauthServiceProvider6 != null) {
            oauthServiceProvider6.authorizeCallback(i2, i3, intent);
        }
        OauthServiceProvider oauthServiceProvider7 = this.twitterOauthServiceProvider;
        if (oauthServiceProvider7 != null) {
            oauthServiceProvider7.authorizeCallback(i2, i3, intent);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void cleanUp() {
        OauthServiceProvider oauthServiceProvider = this.taobao2ndOauthServiceProvider;
        if (oauthServiceProvider != null) {
            oauthServiceProvider.cleanUp();
        }
        OauthServiceProvider oauthServiceProvider2 = this.taobao3rdOauthServiceProvider;
        if (oauthServiceProvider2 != null) {
            oauthServiceProvider2.cleanUp();
        }
        OauthServiceProvider oauthServiceProvider3 = this.alipayOauthServiceProvider;
        if (oauthServiceProvider3 != null) {
            oauthServiceProvider3.cleanUp();
        }
        OauthServiceProvider oauthServiceProvider4 = this.umengOauthServiceProvider;
        if (oauthServiceProvider4 != null) {
            oauthServiceProvider4.cleanUp();
        }
        OauthServiceProvider oauthServiceProvider5 = this.googleOauthServiceProvider;
        if (oauthServiceProvider5 != null) {
            oauthServiceProvider5.cleanUp();
        }
        OauthServiceProvider oauthServiceProvider6 = this.twitterOauthServiceProvider;
        if (oauthServiceProvider6 != null) {
            oauthServiceProvider6.cleanUp();
        }
    }

    public void init(Context context) {
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void logout(Context context, LogoutCallback logoutCallback) {
        int i2 = this.currentOauthPlatform;
        if (i2 != 1) {
            OauthServiceProvider oauthServiceProvider = this.umengOauthServiceProvider;
            if (oauthServiceProvider != null) {
                oauthServiceProvider.logout(context, logoutCallback);
                return;
            }
            Message messageCreateMessage = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, Integer.valueOf(i2));
            AliSDKLogger.log(TAG, messageCreateMessage);
            CommonUtils.onFailure(logoutCallback, messageCreateMessage);
            return;
        }
        OauthServiceProvider oauthServiceProvider2 = this.taobao2ndOauthServiceProvider;
        if (oauthServiceProvider2 != null) {
            oauthServiceProvider2.logout(context, null);
            return;
        }
        OauthServiceProvider oauthServiceProvider3 = this.taobao3rdOauthServiceProvider;
        if (oauthServiceProvider3 != null) {
            oauthServiceProvider3.logout(context, logoutCallback);
            return;
        }
        Message messageCreateMessage2 = MessageUtils.createMessage(MessageConstants.UNINSTALLED_OATUH_PLATFROM, "taobao");
        AliSDKLogger.log(TAG, messageCreateMessage2);
        CommonUtils.onFailure(logoutCallback, messageCreateMessage2);
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void logoutAll(Context context, LogoutCallback logoutCallback) {
        OauthServiceProvider oauthServiceProvider;
        if (ConfigManager.getInstance().isLogoutLoginSDKSwitch() && (oauthServiceProvider = this.taobao2ndOauthServiceProvider) != null) {
            oauthServiceProvider.logout(context, null);
        }
        OauthServiceProvider oauthServiceProvider2 = this.taobao3rdOauthServiceProvider;
        if (oauthServiceProvider2 != null) {
            oauthServiceProvider2.logout(context, null);
        }
        OauthServiceProvider oauthServiceProvider3 = this.alipayOauthServiceProvider;
        if (oauthServiceProvider3 != null) {
            oauthServiceProvider3.logout(context, null);
        }
        OauthServiceProvider oauthServiceProvider4 = this.umengOauthServiceProvider;
        if (oauthServiceProvider4 != null) {
            oauthServiceProvider4.logout(context, null);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void oaBind(final Activity activity, final int i2, final OABindCallback oABindCallback, final boolean z2) {
        if (CommonUtils.sessionFail(oABindCallback)) {
            return;
        }
        queryOauthList(activity, i2, new OauthQueryCallback() { // from class: com.alibaba.sdk.android.oauth.OauthServiceImpl.1
            @Override // com.alibaba.sdk.android.openaccount.callback.FailureCallback
            public void onFailure(int i3, String str) {
                OABindCallback oABindCallback2 = oABindCallback;
                if (oABindCallback2 != null) {
                    oABindCallback2.onFailure(i3, str);
                }
            }

            @Override // com.alibaba.sdk.android.oauth.callback.OauthQueryCallback
            public void onSuccess(List<OpenAccountLink> list) {
                if (!z2) {
                    OauthServiceImpl.this.oauthBind(activity, i2, oABindCallback);
                    return;
                }
                if (list == null || list.size() < 1) {
                    OauthServiceImpl.this.oauthBind(activity, i2, oABindCallback);
                    return;
                }
                OABindCallback oABindCallback2 = oABindCallback;
                if (oABindCallback2 != null) {
                    CommonUtils.onFailure(oABindCallback2, MessageUtils.createMessage(i2 + MessageConstants.OA_ALREADY_BIND_BASE, new Object[0]));
                }
            }
        });
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void oauth(Activity activity, int i2, LoginCallback loginCallback) {
        oauthWhat(activity, i2, loginCallback);
    }

    @Override // com.alibaba.sdk.android.openaccount.OauthService
    public void queryOauthList(Context context, int i2, OauthQueryCallback oauthQueryCallback) {
        if (CommonUtils.sessionFail(oauthQueryCallback)) {
            return;
        }
        new OauthQueryTask(context, i2, oauthQueryCallback).execute(new Void[0]);
    }
}
