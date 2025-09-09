package com.facebook.login;

import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;
import java.util.Locale;

/* loaded from: classes3.dex */
abstract class WebLoginMethodHandler extends LoginMethodHandler {
    private static final String WEB_VIEW_AUTH_HANDLER_STORE = "com.facebook.login.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY";
    private static final String WEB_VIEW_AUTH_HANDLER_TOKEN_KEY = "TOKEN";
    private String e2e;

    WebLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    private String loadCookieToken() {
        return this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).getString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, "");
    }

    private void saveCookieToken(String str) {
        this.loginClient.getActivity().getSharedPreferences(WEB_VIEW_AUTH_HANDLER_STORE, 0).edit().putString(WEB_VIEW_AUTH_HANDLER_TOKEN_KEY, str).apply();
    }

    protected Bundle addExtraParameters(Bundle bundle, LoginClient.Request request) {
        bundle.putString("redirect_uri", getRedirectUrl());
        bundle.putString("client_id", request.getApplicationId());
        bundle.putString("e2e", LoginClient.getE2E());
        bundle.putString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST);
        bundle.putString(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, "true");
        bundle.putString(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, request.getAuthType());
        bundle.putString("sdk", String.format(Locale.ROOT, "android-%s", FacebookSdk.getSdkVersion()));
        if (getSSODevice() != null) {
            bundle.putString(ServerProtocol.DIALOG_PARAM_SSO_DEVICE, getSSODevice());
        }
        return bundle;
    }

    protected Bundle getParameters(LoginClient.Request request) {
        Bundle bundle = new Bundle();
        if (!Utility.isNullOrEmpty(request.getPermissions())) {
            String strJoin = TextUtils.join(",", request.getPermissions());
            bundle.putString("scope", strJoin);
            addLoggingExtra("scope", strJoin);
        }
        bundle.putString(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, request.getDefaultAudience().getNativeProtocolAudience());
        bundle.putString("state", getClientState(request.getAuthId()));
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        String token = currentAccessToken != null ? currentAccessToken.getToken() : null;
        if (token == null || !token.equals(loadCookieToken())) {
            Utility.clearFacebookCookies(this.loginClient.getActivity());
            addLoggingExtra("access_token", "0");
        } else {
            bundle.putString("access_token", token);
            addLoggingExtra("access_token", "1");
        }
        bundle.putString(ServerProtocol.DIALOG_PARAM_CBT, String.valueOf(System.currentTimeMillis()));
        bundle.putString(ServerProtocol.DIALOG_PARAM_IES, FacebookSdk.getAutoLogAppEventsEnabled() ? "1" : "0");
        return bundle;
    }

    protected String getRedirectUrl() {
        return "fb" + FacebookSdk.getApplicationId() + "://authorize";
    }

    protected String getSSODevice() {
        return null;
    }

    abstract AccessTokenSource getTokenSource();

    protected void onComplete(LoginClient.Request request, Bundle bundle, FacebookException facebookException) {
        String str;
        LoginClient.Result resultCreateErrorResult;
        this.e2e = null;
        if (bundle != null) {
            if (bundle.containsKey("e2e")) {
                this.e2e = bundle.getString("e2e");
            }
            try {
                AccessToken accessTokenCreateAccessTokenFromWebBundle = LoginMethodHandler.createAccessTokenFromWebBundle(request.getPermissions(), bundle, getTokenSource(), request.getApplicationId());
                resultCreateErrorResult = LoginClient.Result.createTokenResult(this.loginClient.getPendingRequest(), accessTokenCreateAccessTokenFromWebBundle);
                CookieSyncManager.createInstance(this.loginClient.getActivity()).sync();
                saveCookieToken(accessTokenCreateAccessTokenFromWebBundle.getToken());
            } catch (FacebookException e2) {
                resultCreateErrorResult = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), null, e2.getMessage());
            }
        } else if (facebookException instanceof FacebookOperationCanceledException) {
            resultCreateErrorResult = LoginClient.Result.createCancelResult(this.loginClient.getPendingRequest(), "User canceled log in.");
        } else {
            this.e2e = null;
            String message = facebookException.getMessage();
            if (facebookException instanceof FacebookServiceException) {
                FacebookRequestError requestError = ((FacebookServiceException) facebookException).getRequestError();
                str = String.format(Locale.ROOT, "%d", Integer.valueOf(requestError.getErrorCode()));
                message = requestError.toString();
            } else {
                str = null;
            }
            resultCreateErrorResult = LoginClient.Result.createErrorResult(this.loginClient.getPendingRequest(), null, message, str);
        }
        if (!Utility.isNullOrEmpty(this.e2e)) {
            logWebLoginCompleted(this.e2e);
        }
        this.loginClient.completeAndValidate(resultCreateErrorResult);
    }

    WebLoginMethodHandler(Parcel parcel) {
        super(parcel);
    }
}
