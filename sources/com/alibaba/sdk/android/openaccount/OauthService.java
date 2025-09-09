package com.alibaba.sdk.android.openaccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.alibaba.sdk.android.oauth.callback.OABindCallback;
import com.alibaba.sdk.android.oauth.callback.OauthQueryCallback;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;

/* loaded from: classes2.dex */
public interface OauthService {
    @Deprecated
    void addAppCredential(int i2, String str, String str2);

    void authorizeCallback(int i2, int i3, Intent intent);

    void cleanUp();

    void logout(Context context, LogoutCallback logoutCallback);

    void logoutAll(Context context, LogoutCallback logoutCallback);

    void oaBind(Activity activity, int i2, OABindCallback oABindCallback, boolean z2);

    void oauth(Activity activity, int i2, LoginCallback loginCallback);

    void queryOauthList(Context context, int i2, OauthQueryCallback oauthQueryCallback);
}
