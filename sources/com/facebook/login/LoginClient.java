package com.facebook.login;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.common.R;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.tekartik.sqflite.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class LoginClient implements Parcelable {
    public static final Parcelable.Creator<LoginClient> CREATOR = new Parcelable.Creator<LoginClient>() { // from class: com.facebook.login.LoginClient.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoginClient createFromParcel(Parcel parcel) {
            return new LoginClient(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LoginClient[] newArray(int i2) {
            return new LoginClient[i2];
        }
    };
    BackgroundProcessingListener backgroundProcessingListener;
    boolean checkedInternetPermission;
    int currentHandler;
    Map<String, String> extraData;
    Fragment fragment;
    LoginMethodHandler[] handlersToTry;
    Map<String, String> loggingExtras;
    private LoginLogger loginLogger;
    OnCompletedListener onCompletedListener;
    Request pendingRequest;

    interface BackgroundProcessingListener {
        void onBackgroundProcessingStarted();

        void onBackgroundProcessingStopped();
    }

    public interface OnCompletedListener {
        void onCompleted(Result result);
    }

    public static class Request implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() { // from class: com.facebook.login.LoginClient.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Request createFromParcel(Parcel parcel) {
                return new Request(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Request[] newArray(int i2) {
                return new Request[i2];
            }
        };
        private final String applicationId;
        private final String authId;
        private String authType;
        private final DefaultAudience defaultAudience;
        private String deviceAuthTargetUserId;
        private String deviceRedirectUriString;
        private boolean isRerequest;
        private final LoginBehavior loginBehavior;
        private Set<String> permissions;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        String getApplicationId() {
            return this.applicationId;
        }

        String getAuthId() {
            return this.authId;
        }

        String getAuthType() {
            return this.authType;
        }

        DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        String getDeviceAuthTargetUserId() {
            return this.deviceAuthTargetUserId;
        }

        String getDeviceRedirectUriString() {
            return this.deviceRedirectUriString;
        }

        LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        Set<String> getPermissions() {
            return this.permissions;
        }

        boolean hasPublishPermission() {
            Iterator<String> it = this.permissions.iterator();
            while (it.hasNext()) {
                if (LoginManager.isPublishPermission(it.next())) {
                    return true;
                }
            }
            return false;
        }

        boolean isRerequest() {
            return this.isRerequest;
        }

        void setAuthType(String str) {
            this.authType = str;
        }

        void setDeviceAuthTargetUserId(String str) {
            this.deviceAuthTargetUserId = str;
        }

        void setDeviceRedirectUriString(String str) {
            this.deviceRedirectUriString = str;
        }

        void setPermissions(Set<String> set) {
            Validate.notNull(set, NativeProtocol.RESULT_ARGS_PERMISSIONS);
            this.permissions = set;
        }

        void setRerequest(boolean z2) {
            this.isRerequest = z2;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            LoginBehavior loginBehavior = this.loginBehavior;
            parcel.writeString(loginBehavior != null ? loginBehavior.name() : null);
            parcel.writeStringList(new ArrayList(this.permissions));
            DefaultAudience defaultAudience = this.defaultAudience;
            parcel.writeString(defaultAudience != null ? defaultAudience.name() : null);
            parcel.writeString(this.applicationId);
            parcel.writeString(this.authId);
            parcel.writeByte(this.isRerequest ? (byte) 1 : (byte) 0);
            parcel.writeString(this.deviceRedirectUriString);
            parcel.writeString(this.authType);
            parcel.writeString(this.deviceAuthTargetUserId);
        }

        Request(LoginBehavior loginBehavior, Set<String> set, DefaultAudience defaultAudience, String str, String str2, String str3) {
            this.isRerequest = false;
            this.loginBehavior = loginBehavior;
            this.permissions = set == null ? new HashSet<>() : set;
            this.defaultAudience = defaultAudience;
            this.authType = str;
            this.applicationId = str2;
            this.authId = str3;
        }

        private Request(Parcel parcel) {
            this.isRerequest = false;
            String string = parcel.readString();
            this.loginBehavior = string != null ? LoginBehavior.valueOf(string) : null;
            ArrayList arrayList = new ArrayList();
            parcel.readStringList(arrayList);
            this.permissions = new HashSet(arrayList);
            String string2 = parcel.readString();
            this.defaultAudience = string2 != null ? DefaultAudience.valueOf(string2) : null;
            this.applicationId = parcel.readString();
            this.authId = parcel.readString();
            this.isRerequest = parcel.readByte() != 0;
            this.deviceRedirectUriString = parcel.readString();
            this.authType = parcel.readString();
            this.deviceAuthTargetUserId = parcel.readString();
        }
    }

    public static class Result implements Parcelable {
        public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() { // from class: com.facebook.login.LoginClient.Result.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Result createFromParcel(Parcel parcel) {
                return new Result(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Result[] newArray(int i2) {
                return new Result[i2];
            }
        };
        final Code code;
        final String errorCode;
        final String errorMessage;
        public Map<String, String> extraData;
        public Map<String, String> loggingExtras;
        final Request request;
        final AccessToken token;

        enum Code {
            SUCCESS("success"),
            CANCEL(Constant.PARAM_CANCEL),
            ERROR("error");

            private final String loggingValue;

            Code(String str) {
                this.loggingValue = str;
            }

            String getLoggingValue() {
                return this.loggingValue;
            }
        }

        static Result createCancelResult(Request request, String str) {
            return new Result(request, Code.CANCEL, null, str, null);
        }

        static Result createErrorResult(Request request, String str, String str2) {
            return createErrorResult(request, str, str2, null);
        }

        static Result createTokenResult(Request request, AccessToken accessToken) {
            return new Result(request, Code.SUCCESS, accessToken, null, null);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeString(this.code.name());
            parcel.writeParcelable(this.token, i2);
            parcel.writeString(this.errorMessage);
            parcel.writeString(this.errorCode);
            parcel.writeParcelable(this.request, i2);
            Utility.writeStringMapToParcel(parcel, this.loggingExtras);
            Utility.writeStringMapToParcel(parcel, this.extraData);
        }

        Result(Request request, Code code, AccessToken accessToken, String str, String str2) {
            Validate.notNull(code, "code");
            this.request = request;
            this.token = accessToken;
            this.errorMessage = str;
            this.code = code;
            this.errorCode = str2;
        }

        static Result createErrorResult(Request request, String str, String str2, String str3) {
            return new Result(request, Code.ERROR, null, TextUtils.join(": ", Utility.asListNoNulls(str, str2)), str3);
        }

        private Result(Parcel parcel) {
            this.code = Code.valueOf(parcel.readString());
            this.token = (AccessToken) parcel.readParcelable(AccessToken.class.getClassLoader());
            this.errorMessage = parcel.readString();
            this.errorCode = parcel.readString();
            this.request = (Request) parcel.readParcelable(Request.class.getClassLoader());
            this.loggingExtras = Utility.readStringMapFromParcel(parcel);
            this.extraData = Utility.readStringMapFromParcel(parcel);
        }
    }

    public LoginClient(Fragment fragment) {
        this.currentHandler = -1;
        this.fragment = fragment;
    }

    private void addLoggingExtra(String str, String str2, boolean z2) {
        if (this.loggingExtras == null) {
            this.loggingExtras = new HashMap();
        }
        if (this.loggingExtras.containsKey(str) && z2) {
            str2 = this.loggingExtras.get(str) + "," + str2;
        }
        this.loggingExtras.put(str, str2);
    }

    private void completeWithFailure() {
        complete(Result.createErrorResult(this.pendingRequest, "Login attempt failed.", null));
    }

    static String getE2E() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("init", System.currentTimeMillis());
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    private LoginLogger getLogger() {
        LoginLogger loginLogger = this.loginLogger;
        if (loginLogger == null || !loginLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
            this.loginLogger = new LoginLogger(getActivity(), this.pendingRequest.getApplicationId());
        }
        return this.loginLogger;
    }

    public static int getLoginRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    private void logAuthorizationMethodComplete(String str, Result result, Map<String, String> map) {
        logAuthorizationMethodComplete(str, result.code.getLoggingValue(), result.errorMessage, result.errorCode, map);
    }

    private void notifyOnCompleteListener(Result result) {
        OnCompletedListener onCompletedListener = this.onCompletedListener;
        if (onCompletedListener != null) {
            onCompletedListener.onCompleted(result);
        }
    }

    void addExtraData(String str, String str2, boolean z2) {
        if (this.extraData == null) {
            this.extraData = new HashMap();
        }
        if (this.extraData.containsKey(str) && z2) {
            str2 = this.extraData.get(str) + "," + str2;
        }
        this.extraData.put(str, str2);
    }

    void authorize(Request request) {
        if (request == null) {
            return;
        }
        if (this.pendingRequest != null) {
            throw new FacebookException("Attempted to authorize while a request is pending.");
        }
        if (!AccessToken.isCurrentAccessTokenActive() || checkInternetPermission()) {
            this.pendingRequest = request;
            this.handlersToTry = getHandlersToTry(request);
            tryNextHandler();
        }
    }

    void cancelCurrentHandler() {
        if (this.currentHandler >= 0) {
            getCurrentHandler().cancel();
        }
    }

    boolean checkInternetPermission() {
        if (this.checkedInternetPermission) {
            return true;
        }
        if (checkPermission("android.permission.INTERNET") == 0) {
            this.checkedInternetPermission = true;
            return true;
        }
        FragmentActivity activity = getActivity();
        complete(Result.createErrorResult(this.pendingRequest, activity.getString(R.string.com_facebook_internet_permission_error_title), activity.getString(R.string.com_facebook_internet_permission_error_message)));
        return false;
    }

    int checkPermission(String str) {
        return getActivity().checkCallingOrSelfPermission(str);
    }

    void complete(Result result) {
        LoginMethodHandler currentHandler = getCurrentHandler();
        if (currentHandler != null) {
            logAuthorizationMethodComplete(currentHandler.getNameForLogging(), result, currentHandler.methodLoggingExtras);
        }
        Map<String, String> map = this.loggingExtras;
        if (map != null) {
            result.loggingExtras = map;
        }
        Map<String, String> map2 = this.extraData;
        if (map2 != null) {
            result.extraData = map2;
        }
        this.handlersToTry = null;
        this.currentHandler = -1;
        this.pendingRequest = null;
        this.loggingExtras = null;
        notifyOnCompleteListener(result);
    }

    void completeAndValidate(Result result) {
        if (result.token == null || !AccessToken.isCurrentAccessTokenActive()) {
            complete(result);
        } else {
            validateSameFbidAndFinish(result);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    FragmentActivity getActivity() {
        return this.fragment.getActivity();
    }

    BackgroundProcessingListener getBackgroundProcessingListener() {
        return this.backgroundProcessingListener;
    }

    LoginMethodHandler getCurrentHandler() {
        int i2 = this.currentHandler;
        if (i2 >= 0) {
            return this.handlersToTry[i2];
        }
        return null;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    protected LoginMethodHandler[] getHandlersToTry(Request request) {
        ArrayList arrayList = new ArrayList();
        LoginBehavior loginBehavior = request.getLoginBehavior();
        if (loginBehavior.allowsGetTokenAuth()) {
            arrayList.add(new GetTokenLoginMethodHandler(this));
        }
        if (loginBehavior.allowsKatanaAuth()) {
            arrayList.add(new KatanaProxyLoginMethodHandler(this));
        }
        if (loginBehavior.allowsFacebookLiteAuth()) {
            arrayList.add(new FacebookLiteLoginMethodHandler(this));
        }
        if (loginBehavior.allowsCustomTabAuth()) {
            arrayList.add(new CustomTabLoginMethodHandler(this));
        }
        if (loginBehavior.allowsWebViewAuth()) {
            arrayList.add(new WebViewLoginMethodHandler(this));
        }
        if (loginBehavior.allowsDeviceAuth()) {
            arrayList.add(new DeviceAuthMethodHandler(this));
        }
        LoginMethodHandler[] loginMethodHandlerArr = new LoginMethodHandler[arrayList.size()];
        arrayList.toArray(loginMethodHandlerArr);
        return loginMethodHandlerArr;
    }

    boolean getInProgress() {
        return this.pendingRequest != null && this.currentHandler >= 0;
    }

    OnCompletedListener getOnCompletedListener() {
        return this.onCompletedListener;
    }

    public Request getPendingRequest() {
        return this.pendingRequest;
    }

    void notifyBackgroundProcessingStart() {
        BackgroundProcessingListener backgroundProcessingListener = this.backgroundProcessingListener;
        if (backgroundProcessingListener != null) {
            backgroundProcessingListener.onBackgroundProcessingStarted();
        }
    }

    void notifyBackgroundProcessingStop() {
        BackgroundProcessingListener backgroundProcessingListener = this.backgroundProcessingListener;
        if (backgroundProcessingListener != null) {
            backgroundProcessingListener.onBackgroundProcessingStopped();
        }
    }

    public boolean onActivityResult(int i2, int i3, Intent intent) {
        if (this.pendingRequest != null) {
            return getCurrentHandler().onActivityResult(i2, i3, intent);
        }
        return false;
    }

    void setBackgroundProcessingListener(BackgroundProcessingListener backgroundProcessingListener) {
        this.backgroundProcessingListener = backgroundProcessingListener;
    }

    void setFragment(Fragment fragment) {
        if (this.fragment != null) {
            throw new FacebookException("Can't set fragment once it is already set.");
        }
        this.fragment = fragment;
    }

    void setOnCompletedListener(OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    void startOrContinueAuth(Request request) {
        if (getInProgress()) {
            return;
        }
        authorize(request);
    }

    boolean tryCurrentHandler() {
        LoginMethodHandler currentHandler = getCurrentHandler();
        if (currentHandler.needsInternetPermission() && !checkInternetPermission()) {
            addLoggingExtra("no_internet_permission", "1", false);
            return false;
        }
        boolean zTryAuthorize = currentHandler.tryAuthorize(this.pendingRequest);
        if (zTryAuthorize) {
            getLogger().logAuthorizationMethodStart(this.pendingRequest.getAuthId(), currentHandler.getNameForLogging());
        } else {
            getLogger().logAuthorizationMethodNotTried(this.pendingRequest.getAuthId(), currentHandler.getNameForLogging());
            addLoggingExtra("not_tried", currentHandler.getNameForLogging(), true);
        }
        return zTryAuthorize;
    }

    void tryNextHandler() {
        int i2;
        if (this.currentHandler >= 0) {
            logAuthorizationMethodComplete(getCurrentHandler().getNameForLogging(), "skipped", null, null, getCurrentHandler().methodLoggingExtras);
        }
        do {
            if (this.handlersToTry == null || (i2 = this.currentHandler) >= r0.length - 1) {
                if (this.pendingRequest != null) {
                    completeWithFailure();
                    return;
                }
                return;
            }
            this.currentHandler = i2 + 1;
        } while (!tryCurrentHandler());
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0027 A[Catch: Exception -> 0x0025, TryCatch #0 {Exception -> 0x0025, blocks: (B:7:0x000e, B:9:0x001c, B:13:0x0030, B:12:0x0027), top: B:19:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void validateSameFbidAndFinish(com.facebook.login.LoginClient.Result r3) {
        /*
            r2 = this;
            com.facebook.AccessToken r0 = r3.token
            if (r0 == 0) goto L44
            com.facebook.AccessToken r0 = com.facebook.AccessToken.getCurrentAccessToken()
            com.facebook.AccessToken r1 = r3.token
            if (r0 == 0) goto L27
            if (r1 == 0) goto L27
            java.lang.String r0 = r0.getUserId()     // Catch: java.lang.Exception -> L25
            java.lang.String r1 = r1.getUserId()     // Catch: java.lang.Exception -> L25
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L25
            if (r0 == 0) goto L27
            com.facebook.login.LoginClient$Request r0 = r2.pendingRequest     // Catch: java.lang.Exception -> L25
            com.facebook.AccessToken r3 = r3.token     // Catch: java.lang.Exception -> L25
            com.facebook.login.LoginClient$Result r3 = com.facebook.login.LoginClient.Result.createTokenResult(r0, r3)     // Catch: java.lang.Exception -> L25
            goto L30
        L25:
            r3 = move-exception
            goto L34
        L27:
            com.facebook.login.LoginClient$Request r3 = r2.pendingRequest     // Catch: java.lang.Exception -> L25
            java.lang.String r0 = "User logged in as different Facebook user."
            r1 = 0
            com.facebook.login.LoginClient$Result r3 = com.facebook.login.LoginClient.Result.createErrorResult(r3, r0, r1)     // Catch: java.lang.Exception -> L25
        L30:
            r2.complete(r3)     // Catch: java.lang.Exception -> L25
            goto L43
        L34:
            com.facebook.login.LoginClient$Request r0 = r2.pendingRequest
            java.lang.String r1 = "Caught exception"
            java.lang.String r3 = r3.getMessage()
            com.facebook.login.LoginClient$Result r3 = com.facebook.login.LoginClient.Result.createErrorResult(r0, r1, r3)
            r2.complete(r3)
        L43:
            return
        L44:
            com.facebook.FacebookException r3 = new com.facebook.FacebookException
            java.lang.String r0 = "Can't validate without a token"
            r3.<init>(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.login.LoginClient.validateSameFbidAndFinish(com.facebook.login.LoginClient$Result):void");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelableArray(this.handlersToTry, i2);
        parcel.writeInt(this.currentHandler);
        parcel.writeParcelable(this.pendingRequest, i2);
        Utility.writeStringMapToParcel(parcel, this.loggingExtras);
        Utility.writeStringMapToParcel(parcel, this.extraData);
    }

    public LoginClient(Parcel parcel) {
        this.currentHandler = -1;
        Parcelable[] parcelableArray = parcel.readParcelableArray(LoginMethodHandler.class.getClassLoader());
        this.handlersToTry = new LoginMethodHandler[parcelableArray.length];
        for (int i2 = 0; i2 < parcelableArray.length; i2++) {
            LoginMethodHandler[] loginMethodHandlerArr = this.handlersToTry;
            LoginMethodHandler loginMethodHandler = (LoginMethodHandler) parcelableArray[i2];
            loginMethodHandlerArr[i2] = loginMethodHandler;
            loginMethodHandler.setLoginClient(this);
        }
        this.currentHandler = parcel.readInt();
        this.pendingRequest = (Request) parcel.readParcelable(Request.class.getClassLoader());
        this.loggingExtras = Utility.readStringMapFromParcel(parcel);
        this.extraData = Utility.readStringMapFromParcel(parcel);
    }

    private void logAuthorizationMethodComplete(String str, String str2, String str3, String str4, Map<String, String> map) {
        if (this.pendingRequest == null) {
            getLogger().logUnexpectedError("fb_mobile_login_method_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.", str);
        } else {
            getLogger().logAuthorizationMethodComplete(this.pendingRequest.getAuthId(), str, str2, str3, str4, map);
        }
    }
}
