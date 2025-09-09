package com.aliyun.iot.aep.sdk.login;

import android.app.Activity;
import android.content.Context;
import com.aliyun.iot.aep.sdk.login.data.UserInfo;

/* loaded from: classes3.dex */
public interface ILoginAdapter {
    void authCodeLogin(String str, ILoginCallback iLoginCallback);

    Object getSessionData();

    String getSessionId();

    UserInfo getUserData();

    @Deprecated
    void init(String str);

    boolean isLogin();

    void login(ILoginCallback iLoginCallback);

    void login(String str, String str2, ILoginCallback iLoginCallback);

    void logout(ILogoutCallback iLogoutCallback);

    void oauthLogin(Activity activity, int i2, ILoginCallback iLoginCallback);

    void oauthLogin(Activity activity, ILoginCallback iLoginCallback);

    void refreshSession(boolean z2, IRefreshSessionCallback iRefreshSessionCallback);

    void registerLoginListener(ILoginStatusChangeListener iLoginStatusChangeListener);

    void setIsDebuggable(boolean z2);

    void showEmailRegister(Context context, Class<?> cls, ILoginCallback iLoginCallback);

    void showRegister(Context context, Class<?> cls, ILoginCallback iLoginCallback);

    void unRegisterLoginListener(ILoginStatusChangeListener iLoginStatusChangeListener);
}
