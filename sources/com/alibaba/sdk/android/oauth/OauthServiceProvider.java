package com.alibaba.sdk.android.oauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.alibaba.sdk.android.oauth.callback.OABindCallback;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;

/* loaded from: classes2.dex */
public interface OauthServiceProvider {
    void authorizeCallback(int i2, int i3, Intent intent);

    void bind(Activity activity, int i2, AppCredential appCredential, OABindCallback oABindCallback);

    void cleanUp();

    void logout(Context context, LogoutCallback logoutCallback);

    void oauth(Activity activity, int i2, AppCredential appCredential, LoginCallback loginCallback);
}
