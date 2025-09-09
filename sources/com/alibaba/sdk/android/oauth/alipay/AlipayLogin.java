package com.alibaba.sdk.android.oauth.alipay;

import android.app.Activity;
import android.content.Intent;
import com.alibaba.sdk.android.oauth.BindByOauthTask;
import com.alibaba.sdk.android.oauth.LoginByOauthRequest;
import com.alibaba.sdk.android.oauth.LoginByOauthTask;
import com.alibaba.sdk.android.openaccount.OpenAccountService;
import com.alibaba.sdk.android.openaccount.message.MessageConstants;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.model.OpenAccountSession;
import com.alibaba.sdk.android.openaccount.security.SecurityGuardService;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alipay.auth.mobile.api.IAlipayAuthEventHandler;
import com.alipay.auth.mobile.exception.AlipayAuthIllegalArgumentException;
import com.taobao.android.sso.v2.launch.alipay.AlipayAuthManager;
import com.taobao.login4android.Login;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class AlipayLogin {
    public static void handleAlipaySSOIntent(Intent intent, IAlipayAuthEventHandler iAlipayAuthEventHandler) {
        if (isAlipayAuthCallBack(intent)) {
            try {
                AlipayAuthManager.getInstance().getAlipayAuth().handleIntent(intent, iAlipayAuthEventHandler);
            } catch (AlipayAuthIllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static boolean isAlipayAuthCallBack(Intent intent) {
        try {
            return AlipayAuthManager.getInstance().getAlipayAuth().isAlipayAuthCallBack(intent);
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void loginWithAuthCode(Activity activity, String str, String str2, boolean z2) {
        Pluto pluto = Pluto.DEFAULT_INSTANCE;
        OpenAccountService openAccountService = (OpenAccountService) pluto.getBean(OpenAccountService.class);
        SecurityGuardService securityGuardService = (SecurityGuardService) pluto.getBean(SecurityGuardService.class);
        if (openAccountService == null) {
            return;
        }
        OpenAccountSession session = openAccountService.getSession();
        if (z2 && session != null && session.isLogin()) {
            CommonUtils.onFailure(AlipayOauthServiceProviderImpl.mLoginCallback, MessageUtils.createMessage(MessageConstants.USER_ALREADY_LOGIN, new Object[0]));
            return;
        }
        LoginByOauthRequest loginByOauthRequest = new LoginByOauthRequest();
        loginByOauthRequest.accessToken = str;
        loginByOauthRequest.tokenType = "havana-original-sid";
        loginByOauthRequest.oauthPlateform = 5;
        loginByOauthRequest.oauthAppKey = securityGuardService.getAppKey();
        HashMap map = new HashMap();
        map.put("avatarUrl", Login.getHeadPicLink());
        map.put("nick", Login.getNick());
        map.put("openId", Login.getUserId());
        if (z2) {
            new LoginByOauthTask(activity, AlipayOauthServiceProviderImpl.mLoginCallback, map, loginByOauthRequest, null).execute(new Void[0]);
        } else {
            new BindByOauthTask(activity, AlipayOauthServiceProviderImpl.mBindCallback, map, loginByOauthRequest, null).execute(new Void[0]);
        }
    }
}
