package com.facebook.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import com.facebook.share.internal.ShareConstants;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.json.JSONException;

/* loaded from: classes3.dex */
public class LoginManager {
    private static final String EXPRESS_LOGIN_ALLOWED = "express_login_allowed";
    private static final String MANAGE_PERMISSION_PREFIX = "manage";
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS = getOtherPublishPermissions();
    private static final String PREFERENCE_LOGIN_MANAGER = "com.facebook.loginManager";
    private static final String PUBLISH_PERMISSION_PREFIX = "publish";
    private static volatile LoginManager instance;
    private final SharedPreferences sharedPreferences;
    private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private String authType = ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE;

    private static class ActivityStartActivityDelegate implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity activity) {
            Validate.notNull(activity, PushConstants.INTENT_ACTIVITY_NAME);
            this.activity = activity;
        }

        @Override // com.facebook.login.StartActivityDelegate
        public Activity getActivityContext() {
            return this.activity;
        }

        @Override // com.facebook.login.StartActivityDelegate
        public void startActivityForResult(Intent intent, int i2) {
            this.activity.startActivityForResult(intent, i2);
        }
    }

    private static class FragmentStartActivityDelegate implements StartActivityDelegate {
        private final FragmentWrapper fragment;

        FragmentStartActivityDelegate(FragmentWrapper fragmentWrapper) {
            Validate.notNull(fragmentWrapper, "fragment");
            this.fragment = fragmentWrapper;
        }

        @Override // com.facebook.login.StartActivityDelegate
        public Activity getActivityContext() {
            return this.fragment.getActivity();
        }

        @Override // com.facebook.login.StartActivityDelegate
        public void startActivityForResult(Intent intent, int i2) {
            this.fragment.startActivityForResult(intent, i2);
        }
    }

    private static class LoginLoggerHolder {
        private static LoginLogger logger;

        private LoginLoggerHolder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static synchronized LoginLogger getLogger(Context context) {
            if (context == null) {
                try {
                    context = FacebookSdk.getApplicationContext();
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (context == null) {
                return null;
            }
            if (logger == null) {
                logger = new LoginLogger(context, FacebookSdk.getApplicationId());
            }
            return logger;
        }
    }

    LoginManager() {
        Validate.sdkInitialized();
        this.sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(PREFERENCE_LOGIN_MANAGER, 0);
    }

    static LoginResult computeLoginResult(LoginClient.Request request, AccessToken accessToken) {
        Set<String> permissions = request.getPermissions();
        HashSet hashSet = new HashSet(accessToken.getPermissions());
        if (request.isRerequest()) {
            hashSet.retainAll(permissions);
        }
        HashSet hashSet2 = new HashSet(permissions);
        hashSet2.removeAll(hashSet);
        return new LoginResult(accessToken, hashSet, hashSet2);
    }

    private LoginClient.Request createLoginRequestFromResponse(GraphResponse graphResponse) {
        Validate.notNull(graphResponse, "response");
        AccessToken accessToken = graphResponse.getRequest().getAccessToken();
        return createLoginRequest(accessToken != null ? accessToken.getPermissions() : null);
    }

    private void finishLogin(AccessToken accessToken, LoginClient.Request request, FacebookException facebookException, boolean z2, FacebookCallback<LoginResult> facebookCallback) {
        if (accessToken != null) {
            AccessToken.setCurrentAccessToken(accessToken);
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (facebookCallback != null) {
            LoginResult loginResultComputeLoginResult = accessToken != null ? computeLoginResult(request, accessToken) : null;
            if (z2 || (loginResultComputeLoginResult != null && loginResultComputeLoginResult.getRecentlyGrantedPermissions().size() == 0)) {
                facebookCallback.onCancel();
                return;
            }
            if (facebookException != null) {
                facebookCallback.onError(facebookException);
            } else if (accessToken != null) {
                setExpressLoginStatus(true);
                facebookCallback.onSuccess(loginResultComputeLoginResult);
            }
        }
    }

    @Nullable
    static Map<String, String> getExtraDataFromIntent(Intent intent) {
        LoginClient.Result result;
        if (intent == null || (result = (LoginClient.Result) intent.getParcelableExtra("com.facebook.LoginFragment:Result")) == null) {
            return null;
        }
        return result.extraData;
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                try {
                    if (instance == null) {
                        instance = new LoginManager();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    private static Set<String> getOtherPublishPermissions() {
        return Collections.unmodifiableSet(new HashSet<String>() { // from class: com.facebook.login.LoginManager.2
            {
                add("ads_management");
                add("create_event");
                add("rsvp_event");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public static Profile getProfileFromBundle(Bundle bundle) {
        String string = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_NAME);
        String string2 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_FIRST_NAME);
        String string3 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_MIDDLE_NAME);
        String string4 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_LAST_NAME);
        String string5 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_LINK);
        String string6 = bundle.getString(NativeProtocol.EXTRA_ARGS_PROFILE_USER_ID);
        if (string == null || string2 == null || string3 == null || string4 == null || string5 == null || string6 == null) {
            return null;
        }
        return new Profile(string6, string2, string3, string4, string, Uri.parse(string5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleLoginStatusError(String str, String str2, String str3, LoginLogger loginLogger, LoginStatusCallback loginStatusCallback) {
        FacebookException facebookException = new FacebookException(str + ": " + str2);
        loginLogger.logLoginStatusError(str3, facebookException);
        loginStatusCallback.onError(facebookException);
    }

    private boolean isExpressLoginAllowed() {
        return this.sharedPreferences.getBoolean(EXPRESS_LOGIN_ALLOWED, true);
    }

    static boolean isPublishPermission(String str) {
        return str != null && (str.startsWith(PUBLISH_PERMISSION_PREFIX) || str.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(str));
    }

    private void logCompleteLogin(Context context, LoginClient.Result.Code code, Map<String, String> map, Exception exc, boolean z2, LoginClient.Request request) throws JSONException {
        LoginLogger logger = LoginLoggerHolder.getLogger(context);
        if (logger == null) {
            return;
        }
        if (request == null) {
            logger.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
            return;
        }
        HashMap map2 = new HashMap();
        map2.put("try_login_activity", z2 ? "1" : "0");
        logger.logCompleteLogin(request.getAuthId(), map2, code, map, exc);
    }

    private void logStartLogin(Context context, LoginClient.Request request) throws JSONException {
        LoginLogger logger = LoginLoggerHolder.getLogger(context);
        if (logger == null || request == null) {
            return;
        }
        logger.logStartLogin(request);
    }

    private boolean resolveIntent(Intent intent) {
        return FacebookSdk.getApplicationContext().getPackageManager().resolveActivity(intent, 0) != null;
    }

    private void retrieveLoginStatusImpl(Context context, final LoginStatusCallback loginStatusCallback, long j2) {
        final String applicationId = FacebookSdk.getApplicationId();
        final String string = UUID.randomUUID().toString();
        final LoginLogger loginLogger = new LoginLogger(context, applicationId);
        if (!isExpressLoginAllowed()) {
            loginLogger.logLoginStatusFailure(string);
            loginStatusCallback.onFailure();
            return;
        }
        LoginStatusClient loginStatusClient = new LoginStatusClient(context, applicationId, string, FacebookSdk.getGraphApiVersion(), j2);
        loginStatusClient.setCompletedListener(new PlatformServiceClient.CompletedListener() { // from class: com.facebook.login.LoginManager.4
            @Override // com.facebook.internal.PlatformServiceClient.CompletedListener
            public void completed(Bundle bundle) throws NumberFormatException {
                if (bundle == null) {
                    loginLogger.logLoginStatusFailure(string);
                    loginStatusCallback.onFailure();
                    return;
                }
                String string2 = bundle.getString(NativeProtocol.STATUS_ERROR_TYPE);
                String string3 = bundle.getString(NativeProtocol.STATUS_ERROR_DESCRIPTION);
                if (string2 != null) {
                    LoginManager.handleLoginStatusError(string2, string3, string, loginLogger, loginStatusCallback);
                    return;
                }
                String string4 = bundle.getString(NativeProtocol.EXTRA_ACCESS_TOKEN);
                Date bundleLongAsDate = Utility.getBundleLongAsDate(bundle, NativeProtocol.EXTRA_EXPIRES_SECONDS_SINCE_EPOCH, new Date(0L));
                ArrayList<String> stringArrayList = bundle.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
                String string5 = bundle.getString(NativeProtocol.RESULT_ARGS_SIGNED_REQUEST);
                String string6 = bundle.getString(NativeProtocol.RESULT_ARGS_GRAPH_DOMAIN);
                Date bundleLongAsDate2 = Utility.getBundleLongAsDate(bundle, NativeProtocol.EXTRA_DATA_ACCESS_EXPIRATION_TIME, new Date(0L));
                String userIDFromSignedRequest = !Utility.isNullOrEmpty(string5) ? LoginMethodHandler.getUserIDFromSignedRequest(string5) : null;
                if (Utility.isNullOrEmpty(string4) || stringArrayList == null || stringArrayList.isEmpty() || Utility.isNullOrEmpty(userIDFromSignedRequest)) {
                    loginLogger.logLoginStatusFailure(string);
                    loginStatusCallback.onFailure();
                    return;
                }
                AccessToken accessToken = new AccessToken(string4, applicationId, userIDFromSignedRequest, stringArrayList, null, null, null, bundleLongAsDate, null, bundleLongAsDate2, string6);
                AccessToken.setCurrentAccessToken(accessToken);
                Profile profileFromBundle = LoginManager.getProfileFromBundle(bundle);
                if (profileFromBundle != null) {
                    Profile.setCurrentProfile(profileFromBundle);
                } else {
                    Profile.fetchProfileForCurrentAccessToken();
                }
                loginLogger.logLoginStatusSuccess(string);
                loginStatusCallback.onCompleted(accessToken);
            }
        });
        loginLogger.logLoginStatusStart(string);
        if (loginStatusClient.start()) {
            return;
        }
        loginLogger.logLoginStatusFailure(string);
        loginStatusCallback.onFailure();
    }

    private void setExpressLoginStatus(boolean z2) {
        SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
        editorEdit.putBoolean(EXPRESS_LOGIN_ALLOWED, z2);
        editorEdit.apply();
    }

    private void startLogin(StartActivityDelegate startActivityDelegate, LoginClient.Request request) throws JSONException, FacebookException {
        logStartLogin(startActivityDelegate.getActivityContext(), request);
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.login.LoginManager.3
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public boolean onActivityResult(int i2, Intent intent) {
                return LoginManager.this.onActivityResult(i2, intent);
            }
        });
        if (tryFacebookActivity(startActivityDelegate, request)) {
            return;
        }
        FacebookException facebookException = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
        logCompleteLogin(startActivityDelegate.getActivityContext(), LoginClient.Result.Code.ERROR, null, facebookException, false, request);
        throw facebookException;
    }

    private boolean tryFacebookActivity(StartActivityDelegate startActivityDelegate, LoginClient.Request request) {
        Intent facebookActivityIntent = getFacebookActivityIntent(request);
        if (!resolveIntent(facebookActivityIntent)) {
            return false;
        }
        try {
            startActivityDelegate.startActivityForResult(facebookActivityIntent, LoginClient.getLoginRequestCode());
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        }
    }

    private void validatePublishPermissions(Collection<String> collection) {
        if (collection == null) {
            return;
        }
        for (String str : collection) {
            if (!isPublishPermission(str)) {
                throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", str));
            }
        }
    }

    private void validateReadPermissions(Collection<String> collection) {
        if (collection == null) {
            return;
        }
        for (String str : collection) {
            if (isPublishPermission(str)) {
                throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", str));
            }
        }
    }

    protected LoginClient.Request createLoginRequest(Collection<String> collection) {
        LoginClient.Request request = new LoginClient.Request(this.loginBehavior, Collections.unmodifiableSet(collection != null ? new HashSet(collection) : new HashSet()), this.defaultAudience, this.authType, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        request.setRerequest(AccessToken.isCurrentAccessTokenActive());
        return request;
    }

    protected LoginClient.Request createReauthorizeRequest() {
        return new LoginClient.Request(LoginBehavior.DIALOG_ONLY, new HashSet(), this.defaultAudience, "reauthorize", FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
    }

    public String getAuthType() {
        return this.authType;
    }

    public DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    protected Intent getFacebookActivityIntent(LoginClient.Request request) {
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(request.getLoginBehavior().toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, request);
        intent.putExtra("com.facebook.LoginFragment:Request", bundle);
        return intent;
    }

    public LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    public void logIn(Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logIn(new FragmentWrapper(fragment), collection);
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithPublishPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithReadPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logOut() {
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);
        setExpressLoginStatus(false);
    }

    boolean onActivityResult(int i2, Intent intent) {
        return onActivityResult(i2, intent, null);
    }

    public void reauthorizeDataAccess(Activity activity) throws JSONException, FacebookException {
        startLogin(new ActivityStartActivityDelegate(activity), createReauthorizeRequest());
    }

    public void registerCallback(CallbackManager callbackManager, final FacebookCallback<LoginResult> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).registerCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.login.LoginManager.1
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public boolean onActivityResult(int i2, Intent intent) {
                return LoginManager.this.onActivityResult(i2, intent, facebookCallback);
            }
        });
    }

    public void resolveError(Activity activity, GraphResponse graphResponse) throws JSONException, FacebookException {
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequestFromResponse(graphResponse));
    }

    public void retrieveLoginStatus(Context context, LoginStatusCallback loginStatusCallback) {
        retrieveLoginStatus(context, 5000L, loginStatusCallback);
    }

    public LoginManager setAuthType(String str) {
        this.authType = str;
        return this;
    }

    public LoginManager setDefaultAudience(DefaultAudience defaultAudience) {
        this.defaultAudience = defaultAudience;
        return this;
    }

    public LoginManager setLoginBehavior(LoginBehavior loginBehavior) {
        this.loginBehavior = loginBehavior;
        return this;
    }

    public void unregisterCallback(CallbackManager callbackManager) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl) callbackManager).unregisterCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode());
    }

    public void logIn(android.app.Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logIn(new FragmentWrapper(fragment), collection);
    }

    public void logInWithPublishPermissions(android.app.Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithPublishPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logInWithReadPermissions(android.app.Fragment fragment, Collection<String> collection) throws JSONException, FacebookException {
        logInWithReadPermissions(new FragmentWrapper(fragment), collection);
    }

    boolean onActivityResult(int i2, Intent intent, FacebookCallback<LoginResult> facebookCallback) throws JSONException {
        LoginClient.Result.Code code;
        AccessToken accessToken;
        LoginClient.Request request;
        Map<String, String> map;
        boolean z2;
        Map<String, String> map2;
        boolean z3;
        LoginClient.Request request2;
        LoginClient.Result.Code code2 = LoginClient.Result.Code.ERROR;
        FacebookException facebookException = null;
        if (intent != null) {
            LoginClient.Result result = (LoginClient.Result) intent.getParcelableExtra("com.facebook.LoginFragment:Result");
            if (result != null) {
                LoginClient.Request request3 = result.request;
                LoginClient.Result.Code code3 = result.code;
                if (i2 != -1) {
                    z = i2 == 0;
                    accessToken = null;
                } else if (code3 == LoginClient.Result.Code.SUCCESS) {
                    accessToken = result.token;
                } else {
                    facebookException = new FacebookAuthorizationException(result.errorMessage);
                    accessToken = null;
                }
                map2 = result.loggingExtras;
                boolean z4 = z;
                request2 = request3;
                code2 = code3;
                z3 = z4;
            } else {
                accessToken = null;
                map2 = null;
                z3 = false;
                request2 = null;
            }
            map = map2;
            code = code2;
            request = request2;
            z2 = z3;
        } else if (i2 == 0) {
            code = LoginClient.Result.Code.CANCEL;
            z2 = true;
            accessToken = null;
            request = null;
            map = null;
        } else {
            code = code2;
            accessToken = null;
            request = null;
            map = null;
            z2 = false;
        }
        if (facebookException == null && accessToken == null && !z2) {
            facebookException = new FacebookException("Unexpected call to LoginManager.onActivityResult");
        }
        logCompleteLogin(null, code, map, facebookException, true, request);
        finishLogin(accessToken, request, facebookException, z2, facebookCallback);
        return true;
    }

    public void retrieveLoginStatus(Context context, long j2, LoginStatusCallback loginStatusCallback) {
        retrieveLoginStatusImpl(context, loginStatusCallback, j2);
    }

    private void logInWithPublishPermissions(FragmentWrapper fragmentWrapper, Collection<String> collection) throws JSONException, FacebookException {
        validatePublishPermissions(collection);
        logIn(fragmentWrapper, collection);
    }

    private void logInWithReadPermissions(FragmentWrapper fragmentWrapper, Collection<String> collection) throws JSONException, FacebookException {
        validateReadPermissions(collection);
        logIn(fragmentWrapper, collection);
    }

    public void logIn(FragmentWrapper fragmentWrapper, Collection<String> collection) throws JSONException, FacebookException {
        startLogin(new FragmentStartActivityDelegate(fragmentWrapper), createLoginRequest(collection));
    }

    public void reauthorizeDataAccess(Fragment fragment) throws JSONException, FacebookException {
        reauthorizeDataAccess(new FragmentWrapper(fragment));
    }

    private void reauthorizeDataAccess(FragmentWrapper fragmentWrapper) throws JSONException, FacebookException {
        startLogin(new FragmentStartActivityDelegate(fragmentWrapper), createReauthorizeRequest());
    }

    public void resolveError(Fragment fragment, GraphResponse graphResponse) throws JSONException, FacebookException {
        resolveError(new FragmentWrapper(fragment), graphResponse);
    }

    public void logIn(Activity activity, Collection<String> collection) throws JSONException, FacebookException {
        startLogin(new ActivityStartActivityDelegate(activity), createLoginRequest(collection));
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> collection) throws JSONException, FacebookException {
        validatePublishPermissions(collection);
        logIn(activity, collection);
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> collection) throws JSONException, FacebookException {
        validateReadPermissions(collection);
        logIn(activity, collection);
    }

    public void resolveError(android.app.Fragment fragment, GraphResponse graphResponse) throws JSONException, FacebookException {
        resolveError(new FragmentWrapper(fragment), graphResponse);
    }

    private void resolveError(FragmentWrapper fragmentWrapper, GraphResponse graphResponse) throws JSONException, FacebookException {
        startLogin(new FragmentStartActivityDelegate(fragmentWrapper), createLoginRequestFromResponse(graphResponse));
    }
}
