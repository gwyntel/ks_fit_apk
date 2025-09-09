package com.facebook.login;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.browser.customtabs.CustomTabsService;
import androidx.fragment.app.FragmentActivity;
import com.facebook.AccessTokenSource;
import com.facebook.CustomTabMainActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class CustomTabLoginMethodHandler extends WebLoginMethodHandler {
    private static final int API_EC_DIALOG_CANCEL = 4201;
    private static final int CHALLENGE_LENGTH = 20;
    private static final String[] CHROME_PACKAGES = {"com.android.chrome", "com.chrome.beta", "com.chrome.dev"};
    public static final Parcelable.Creator<CustomTabLoginMethodHandler> CREATOR = new Parcelable.Creator() { // from class: com.facebook.login.CustomTabLoginMethodHandler.1
        @Override // android.os.Parcelable.Creator
        public CustomTabLoginMethodHandler createFromParcel(Parcel parcel) {
            return new CustomTabLoginMethodHandler(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public CustomTabLoginMethodHandler[] newArray(int i2) {
            return new CustomTabLoginMethodHandler[i2];
        }
    };
    private static final int CUSTOM_TAB_REQUEST_CODE = 1;
    private String currentPackage;
    private String expectedChallenge;
    private String validRedirectURI;

    CustomTabLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
        this.validRedirectURI = "";
        this.expectedChallenge = Utility.generateRandomString(20);
        if (Validate.hasCustomTabRedirectActivity(FacebookSdk.getApplicationContext(), getDeveloperDefinedRedirectURI())) {
            this.validRedirectURI = getDeveloperDefinedRedirectURI();
        } else if (Validate.hasCustomTabRedirectActivity(FacebookSdk.getApplicationContext(), getDefaultRedirectURI())) {
            this.validRedirectURI = getDefaultRedirectURI();
        }
    }

    private String getChromePackage() {
        String str = this.currentPackage;
        if (str != null) {
            return str;
        }
        FragmentActivity activity = this.loginClient.getActivity();
        List<ResolveInfo> listQueryIntentServices = activity.getPackageManager().queryIntentServices(new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION), 0);
        if (listQueryIntentServices == null) {
            return null;
        }
        HashSet hashSet = new HashSet(Arrays.asList(CHROME_PACKAGES));
        Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().serviceInfo;
            if (serviceInfo != null && hashSet.contains(serviceInfo.packageName)) {
                String str2 = serviceInfo.packageName;
                this.currentPackage = str2;
                return str2;
            }
        }
        return null;
    }

    private String getDefaultRedirectURI() {
        return Validate.CUSTOM_TAB_REDIRECT_URI_PREFIX + FacebookSdk.getApplicationContext().getPackageName();
    }

    private String getDeveloperDefinedRedirectURI() {
        return super.getRedirectUrl();
    }

    private boolean isCustomTabsAllowed() {
        return (getChromePackage() == null || getRedirectUrl().isEmpty()) ? false : true;
    }

    private void onCustomTabComplete(String str, LoginClient.Request request) throws NumberFormatException {
        int i2;
        if (str != null) {
            if (str.startsWith(Validate.CUSTOM_TAB_REDIRECT_URI_PREFIX) || str.startsWith(super.getRedirectUrl())) {
                Uri uri = Uri.parse(str);
                Bundle urlQueryString = Utility.parseUrlQueryString(uri.getQuery());
                urlQueryString.putAll(Utility.parseUrlQueryString(uri.getFragment()));
                if (!validateChallengeParam(urlQueryString)) {
                    super.onComplete(request, null, new FacebookException("Invalid state parameter"));
                    return;
                }
                String string = urlQueryString.getString("error");
                if (string == null) {
                    string = urlQueryString.getString("error_type");
                }
                String string2 = urlQueryString.getString("error_msg");
                if (string2 == null) {
                    string2 = urlQueryString.getString("error_message");
                }
                if (string2 == null) {
                    string2 = urlQueryString.getString("error_description");
                }
                String string3 = urlQueryString.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
                if (Utility.isNullOrEmpty(string3)) {
                    i2 = -1;
                } else {
                    try {
                        i2 = Integer.parseInt(string3);
                    } catch (NumberFormatException unused) {
                    }
                }
                if (Utility.isNullOrEmpty(string) && Utility.isNullOrEmpty(string2) && i2 == -1) {
                    super.onComplete(request, urlQueryString, null);
                    return;
                }
                if (string != null && (string.equals("access_denied") || string.equals("OAuthAccessDeniedException"))) {
                    super.onComplete(request, null, new FacebookOperationCanceledException());
                } else if (i2 == 4201) {
                    super.onComplete(request, null, new FacebookOperationCanceledException());
                } else {
                    super.onComplete(request, null, new FacebookServiceException(new FacebookRequestError(i2, string, string2), string2));
                }
            }
        }
    }

    private boolean validateChallengeParam(Bundle bundle) {
        try {
            String string = bundle.getString("state");
            if (string == null) {
                return false;
            }
            return new JSONObject(string).getString("7_challenge").equals(this.expectedChallenge);
        } catch (JSONException unused) {
            return false;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.facebook.login.LoginMethodHandler
    String getNameForLogging() {
        return "custom_tab";
    }

    @Override // com.facebook.login.WebLoginMethodHandler
    protected String getRedirectUrl() {
        return this.validRedirectURI;
    }

    @Override // com.facebook.login.WebLoginMethodHandler
    protected String getSSODevice() {
        return "chrome_custom_tab";
    }

    @Override // com.facebook.login.WebLoginMethodHandler
    AccessTokenSource getTokenSource() {
        return AccessTokenSource.CHROME_CUSTOM_TAB;
    }

    @Override // com.facebook.login.LoginMethodHandler
    boolean onActivityResult(int i2, int i3, Intent intent) throws NumberFormatException {
        if (i2 != 1) {
            return super.onActivityResult(i2, i3, intent);
        }
        LoginClient.Request pendingRequest = this.loginClient.getPendingRequest();
        if (i3 == -1) {
            onCustomTabComplete(intent.getStringExtra(CustomTabMainActivity.EXTRA_URL), pendingRequest);
            return true;
        }
        super.onComplete(pendingRequest, null, new FacebookOperationCanceledException());
        return false;
    }

    @Override // com.facebook.login.LoginMethodHandler
    protected void putChallengeParam(JSONObject jSONObject) throws JSONException {
        jSONObject.put("7_challenge", this.expectedChallenge);
    }

    @Override // com.facebook.login.LoginMethodHandler
    boolean tryAuthorize(LoginClient.Request request) {
        if (!isCustomTabsAllowed()) {
            return false;
        }
        Bundle bundleAddExtraParameters = addExtraParameters(getParameters(request), request);
        Intent intent = new Intent(this.loginClient.getActivity(), (Class<?>) CustomTabMainActivity.class);
        intent.putExtra(CustomTabMainActivity.EXTRA_PARAMS, bundleAddExtraParameters);
        intent.putExtra(CustomTabMainActivity.EXTRA_CHROME_PACKAGE, getChromePackage());
        this.loginClient.getFragment().startActivityForResult(intent, 1);
        return true;
    }

    @Override // com.facebook.login.LoginMethodHandler, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        super.writeToParcel(parcel, i2);
        parcel.writeString(this.expectedChallenge);
    }

    CustomTabLoginMethodHandler(Parcel parcel) {
        super(parcel);
        this.validRedirectURI = "";
        this.expectedChallenge = parcel.readString();
    }
}
