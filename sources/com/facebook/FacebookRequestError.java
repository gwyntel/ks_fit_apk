package com.facebook;

import android.os.Parcel;
import android.os.Parcelable;
import com.alipay.sdk.m.u.i;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class FacebookRequestError implements Parcelable {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    private static final String ERROR_CODE_FIELD_KEY = "code";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_FIELD_KEY = "message";
    private static final String ERROR_MSG_KEY = "error_msg";
    private static final String ERROR_REASON_KEY = "error_reason";
    private static final String ERROR_SUB_CODE_KEY = "error_subcode";
    private static final String ERROR_TYPE_FIELD_KEY = "type";
    private static final String ERROR_USER_MSG_KEY = "error_user_msg";
    private static final String ERROR_USER_TITLE_KEY = "error_user_title";
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final String errorMessage;
    private final String errorRecoveryMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final int subErrorCode;
    static final Range HTTP_RANGE_SUCCESS = new Range(200, 299);
    public static final Parcelable.Creator<FacebookRequestError> CREATOR = new Parcelable.Creator<FacebookRequestError>() { // from class: com.facebook.FacebookRequestError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FacebookRequestError createFromParcel(Parcel parcel) {
            return new FacebookRequestError(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FacebookRequestError[] newArray(int i2) {
            return new FacebookRequestError[i2];
        }
    };

    public enum Category {
        LOGIN_RECOVERABLE,
        OTHER,
        TRANSIENT
    }

    private static class Range {
        private final int end;
        private final int start;

        boolean contains(int i2) {
            return this.start <= i2 && i2 <= this.end;
        }

        private Range(int i2, int i3) {
            this.start = i2;
            this.end = i3;
        }
    }

    static FacebookRequestError checkResponseAndCreateError(JSONObject jSONObject, Object obj, HttpURLConnection httpURLConnection) throws JSONException {
        int iOptInt;
        String str;
        boolean zOptBoolean;
        String strOptString;
        String strOptString2;
        int iOptInt2;
        String str2;
        try {
            if (jSONObject.has("code")) {
                int i2 = jSONObject.getInt("code");
                Object stringPropertyAsJSON = Utility.getStringPropertyAsJSON(jSONObject, "body", GraphResponse.NON_JSON_RESPONSE_PROPERTY);
                if (stringPropertyAsJSON != null && (stringPropertyAsJSON instanceof JSONObject)) {
                    JSONObject jSONObject2 = (JSONObject) stringPropertyAsJSON;
                    boolean z2 = false;
                    if (jSONObject2.has("error")) {
                        JSONObject jSONObject3 = (JSONObject) Utility.getStringPropertyAsJSON(jSONObject2, "error", null);
                        String strOptString3 = jSONObject3.optString("type", null);
                        String strOptString4 = jSONObject3.optString("message", null);
                        iOptInt2 = jSONObject3.optInt("code", -1);
                        int iOptInt3 = jSONObject3.optInt("error_subcode", -1);
                        strOptString = jSONObject3.optString(ERROR_USER_MSG_KEY, null);
                        strOptString2 = jSONObject3.optString(ERROR_USER_TITLE_KEY, null);
                        zOptBoolean = jSONObject3.optBoolean(ERROR_IS_TRANSIENT_KEY, false);
                        z2 = true;
                        str2 = strOptString3;
                        str = strOptString4;
                        iOptInt = iOptInt3;
                    } else if (jSONObject2.has("error_code") || jSONObject2.has(ERROR_MSG_KEY) || jSONObject2.has(ERROR_REASON_KEY)) {
                        String strOptString5 = jSONObject2.optString(ERROR_REASON_KEY, null);
                        String strOptString6 = jSONObject2.optString(ERROR_MSG_KEY, null);
                        int iOptInt4 = jSONObject2.optInt("error_code", -1);
                        iOptInt = jSONObject2.optInt("error_subcode", -1);
                        str = strOptString6;
                        zOptBoolean = false;
                        strOptString = null;
                        strOptString2 = null;
                        iOptInt2 = iOptInt4;
                        z2 = true;
                        str2 = strOptString5;
                    } else {
                        zOptBoolean = false;
                        iOptInt2 = -1;
                        iOptInt = -1;
                        str2 = null;
                        str = null;
                        strOptString = null;
                        strOptString2 = null;
                    }
                    if (z2) {
                        return new FacebookRequestError(i2, iOptInt2, iOptInt, str2, str, strOptString2, strOptString, zOptBoolean, jSONObject2, jSONObject, obj, httpURLConnection, null);
                    }
                }
                if (!HTTP_RANGE_SUCCESS.contains(i2)) {
                    return new FacebookRequestError(i2, -1, -1, null, null, null, null, false, jSONObject.has("body") ? (JSONObject) Utility.getStringPropertyAsJSON(jSONObject, "body", GraphResponse.NON_JSON_RESPONSE_PROPERTY) : null, jSONObject, obj, httpURLConnection, null);
                }
            }
        } catch (JSONException unused) {
        }
        return null;
    }

    static synchronized FacebookRequestErrorClassification getErrorClassification() {
        FetchedAppSettings appSettingsWithoutQuery = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
        if (appSettingsWithoutQuery == null) {
            return FacebookRequestErrorClassification.getDefaultErrorClassification();
        }
        return appSettingsWithoutQuery.getErrorClassification();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Object getBatchRequestResult() {
        return this.batchRequestResult;
    }

    public Category getCategory() {
        return this.category;
    }

    public HttpURLConnection getConnection() {
        return this.connection;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        String str = this.errorMessage;
        return str != null ? str : this.exception.getLocalizedMessage();
    }

    public String getErrorRecoveryMessage() {
        return this.errorRecoveryMessage;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public String getErrorUserMessage() {
        return this.errorUserMessage;
    }

    public String getErrorUserTitle() {
        return this.errorUserTitle;
    }

    public FacebookException getException() {
        return this.exception;
    }

    public JSONObject getRequestResult() {
        return this.requestResult;
    }

    public JSONObject getRequestResultBody() {
        return this.requestResultBody;
    }

    public int getRequestStatusCode() {
        return this.requestStatusCode;
    }

    public int getSubErrorCode() {
        return this.subErrorCode;
    }

    public String toString() {
        return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", subErrorCode: " + this.subErrorCode + ", errorType: " + this.errorType + ", errorMessage: " + getErrorMessage() + i.f9804d;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.requestStatusCode);
        parcel.writeInt(this.errorCode);
        parcel.writeInt(this.subErrorCode);
        parcel.writeString(this.errorType);
        parcel.writeString(this.errorMessage);
        parcel.writeString(this.errorUserTitle);
        parcel.writeString(this.errorUserMessage);
    }

    private FacebookRequestError(int i2, int i3, int i4, String str, String str2, String str3, String str4, boolean z2, JSONObject jSONObject, JSONObject jSONObject2, Object obj, HttpURLConnection httpURLConnection, FacebookException facebookException) {
        boolean z3;
        this.requestStatusCode = i2;
        this.errorCode = i3;
        this.subErrorCode = i4;
        this.errorType = str;
        this.errorMessage = str2;
        this.requestResultBody = jSONObject;
        this.requestResult = jSONObject2;
        this.batchRequestResult = obj;
        this.connection = httpURLConnection;
        this.errorUserTitle = str3;
        this.errorUserMessage = str4;
        if (facebookException != null) {
            this.exception = facebookException;
            z3 = true;
        } else {
            this.exception = new FacebookServiceException(this, str2);
            z3 = false;
        }
        FacebookRequestErrorClassification errorClassification = getErrorClassification();
        Category categoryClassify = z3 ? Category.OTHER : errorClassification.classify(i3, i4, z2);
        this.category = categoryClassify;
        this.errorRecoveryMessage = errorClassification.getRecoveryMessage(categoryClassify);
    }

    FacebookRequestError(HttpURLConnection httpURLConnection, Exception exc) {
        this(-1, -1, -1, null, null, null, null, false, null, null, null, httpURLConnection, exc instanceof FacebookException ? (FacebookException) exc : new FacebookException(exc));
    }

    public FacebookRequestError(int i2, String str, String str2) {
        this(-1, i2, -1, str, str2, null, null, false, null, null, null, null, null);
    }

    private FacebookRequestError(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), false, null, null, null, null, null);
    }
}
