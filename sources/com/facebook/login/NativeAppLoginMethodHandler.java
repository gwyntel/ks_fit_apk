package com.facebook.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.login.LoginClient;

/* loaded from: classes3.dex */
abstract class NativeAppLoginMethodHandler extends LoginMethodHandler {
    NativeAppLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    private String getError(Bundle bundle) {
        String string = bundle.getString("error");
        return string == null ? bundle.getString("error_type") : string;
    }

    private String getErrorMessage(Bundle bundle) {
        String string = bundle.getString("error_message");
        return string == null ? bundle.getString("error_description") : string;
    }

    private LoginClient.Result handleResultCancel(LoginClient.Request request, Intent intent) {
        Bundle extras = intent.getExtras();
        String error = getError(extras);
        String string = extras.get(NativeProtocol.BRIDGE_ARG_ERROR_CODE) != null ? extras.get(NativeProtocol.BRIDGE_ARG_ERROR_CODE).toString() : null;
        return ServerProtocol.errorConnectionFailure.equals(string) ? LoginClient.Result.createErrorResult(request, error, getErrorMessage(extras), string) : LoginClient.Result.createCancelResult(request, error);
    }

    private LoginClient.Result handleResultOk(LoginClient.Request request, Intent intent) {
        Bundle extras = intent.getExtras();
        String error = getError(extras);
        String string = extras.get(NativeProtocol.BRIDGE_ARG_ERROR_CODE) != null ? extras.get(NativeProtocol.BRIDGE_ARG_ERROR_CODE).toString() : null;
        String errorMessage = getErrorMessage(extras);
        String string2 = extras.getString("e2e");
        if (!Utility.isNullOrEmpty(string2)) {
            logWebLoginCompleted(string2);
        }
        if (error == null && string == null && errorMessage == null) {
            try {
                return LoginClient.Result.createTokenResult(request, LoginMethodHandler.createAccessTokenFromWebBundle(request.getPermissions(), extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB, request.getApplicationId()));
            } catch (FacebookException e2) {
                return LoginClient.Result.createErrorResult(request, null, e2.getMessage());
            }
        }
        if (ServerProtocol.errorsProxyAuthDisabled.contains(error)) {
            return null;
        }
        return ServerProtocol.errorsUserCanceled.contains(error) ? LoginClient.Result.createCancelResult(request, null) : LoginClient.Result.createErrorResult(request, error, errorMessage, string);
    }

    @Override // com.facebook.login.LoginMethodHandler
    boolean onActivityResult(int i2, int i3, Intent intent) {
        LoginClient.Request pendingRequest = this.loginClient.getPendingRequest();
        LoginClient.Result resultCreateCancelResult = intent == null ? LoginClient.Result.createCancelResult(pendingRequest, "Operation canceled") : i3 == 0 ? handleResultCancel(pendingRequest, intent) : i3 != -1 ? LoginClient.Result.createErrorResult(pendingRequest, "Unexpected resultCode from authorization.", null) : handleResultOk(pendingRequest, intent);
        if (resultCreateCancelResult != null) {
            this.loginClient.completeAndValidate(resultCreateCancelResult);
            return true;
        }
        this.loginClient.tryNextHandler();
        return true;
    }

    @Override // com.facebook.login.LoginMethodHandler
    abstract boolean tryAuthorize(LoginClient.Request request);

    protected boolean tryIntent(Intent intent, int i2) {
        if (intent == null) {
            return false;
        }
        try {
            this.loginClient.getFragment().startActivityForResult(intent, i2);
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    NativeAppLoginMethodHandler(Parcel parcel) {
        super(parcel);
    }
}
