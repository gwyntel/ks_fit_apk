package com.alibaba.sdk.android.oauth.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.alibaba.sdk.android.oauth.AppCredential;
import com.alibaba.sdk.android.oauth.BindByOauthTask;
import com.alibaba.sdk.android.oauth.LoginByOauthRequest;
import com.alibaba.sdk.android.oauth.LoginByOauthTask;
import com.alibaba.sdk.android.oauth.OauthServiceProvider;
import com.alibaba.sdk.android.oauth.callback.OABindCallback;
import com.alibaba.sdk.android.openaccount.ConfigManager;
import com.alibaba.sdk.android.openaccount.Environment;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.callback.LogoutCallback;
import com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import java.util.Arrays;
import java.util.HashMap;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class FacebookOauthServiceProviderImpl implements OauthServiceProvider, EnvironmentChangeListener, FacebookCallback<LoginResult> {
    public static final String TAG = "oa_facebook";
    public static OABindCallback bindCallback;
    public static LoginCallback loginCallback;
    private Activity mActivity;
    private CallbackManager mCallbackManager;
    private boolean mLoginAfterOauth = true;
    private int oauthPlatform = -1;
    private OauthServiceProvider oauthServiceProvider;

    public static LoginCallback getLoginCallback() {
        return loginCallback;
    }

    public static void setLoginCallback(LoginCallback loginCallback2) {
        loginCallback = loginCallback2;
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void authorizeCallback(int i2, int i3, Intent intent) {
        this.mCallbackManager.onActivityResult(i2, i3, intent);
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void bind(Activity activity, int i2, AppCredential appCredential, OABindCallback oABindCallback) throws JSONException, FacebookException {
        this.mActivity = activity;
        bindCallback = oABindCallback;
        this.oauthPlatform = i2;
        this.mLoginAfterOauth = false;
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void cleanUp() {
        loginCallback = null;
        bindCallback = null;
    }

    public void init(Context context) {
        this.oauthServiceProvider = this;
        try {
            FacebookSdk.setApplicationId(ConfigManager.getInstance().getFacebookId());
            FacebookSdk.sdkInitialize(OpenAccountSDK.getAndroidContext());
            this.mCallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(this.mCallbackManager, this);
        } catch (Exception e2) {
            e2.printStackTrace();
            AliSDKLogger.e(TAG, "facebook oath init failed");
        }
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void logout(Context context, LogoutCallback logoutCallback) {
        LoginManager.getInstance().logOut();
    }

    @Override // com.alibaba.sdk.android.oauth.OauthServiceProvider
    public void oauth(Activity activity, int i2, AppCredential appCredential, LoginCallback loginCallback2) throws JSONException, FacebookException {
        this.mActivity = activity;
        loginCallback = loginCallback2;
        this.oauthPlatform = i2;
        this.mLoginAfterOauth = true;
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
    }

    @Override // com.facebook.FacebookCallback
    public void onCancel() {
        AliSDKLogger.e(TAG, "user cancel");
    }

    @Override // com.alibaba.sdk.android.openaccount.config.EnvironmentChangeListener
    public void onEnvironmentChange(Environment environment, Environment environment2) {
    }

    @Override // com.facebook.FacebookCallback
    public void onError(FacebookException facebookException) {
        AliSDKLogger.e(TAG, "error" + facebookException.getMessage());
    }

    @Override // com.facebook.FacebookCallback
    public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        if (accessToken == null) {
            onError(new FacebookException("AccessToken is null!"));
            return;
        }
        LoginByOauthRequest loginByOauthRequest = new LoginByOauthRequest();
        loginByOauthRequest.accessToken = accessToken.getToken();
        loginByOauthRequest.openId = accessToken.getUserId();
        loginByOauthRequest.oauthPlateform = 13;
        HashMap map = new HashMap();
        if (this.mLoginAfterOauth) {
            new LoginByOauthTask(this.mActivity, loginCallback, map, loginByOauthRequest, this.oauthServiceProvider).execute(new Void[0]);
        } else {
            new BindByOauthTask(this.mActivity, bindCallback, map, loginByOauthRequest, this.oauthServiceProvider).execute(new Void[0]);
        }
    }
}
