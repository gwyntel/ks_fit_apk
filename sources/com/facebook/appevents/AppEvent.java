package com.facebook.appevents;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.eventdeactivation.EventDeactivationManager;
import com.facebook.appevents.internal.AppEventUtility;
import com.facebook.appevents.internal.Constants;
import com.facebook.appevents.restrictivedatafilter.AddressFilterManager;
import com.facebook.appevents.restrictivedatafilter.RestrictiveDataManager;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class AppEvent implements Serializable {
    private static final long serialVersionUID = 1;
    private static final HashSet<String> validatedIdentifiers = new HashSet<>();
    private final String checksum;
    private final boolean inBackground;
    private final boolean isImplicit;
    private final JSONObject jsonObject;
    private final String name;

    static class SerializationProxyV1 implements Serializable {
        private static final long serialVersionUID = -2488473066578201069L;
        private final boolean inBackground;
        private final boolean isImplicit;
        private final String jsonString;

        private SerializationProxyV1(String str, boolean z2, boolean z3) {
            this.jsonString = str;
            this.isImplicit = z2;
            this.inBackground = z3;
        }

        private Object readResolve() throws JSONException {
            return new AppEvent(this.jsonString, this.isImplicit, this.inBackground, null);
        }
    }

    static class SerializationProxyV2 implements Serializable {
        private static final long serialVersionUID = 20160803001L;
        private final String checksum;
        private final boolean inBackground;
        private final boolean isImplicit;
        private final String jsonString;

        private Object readResolve() throws JSONException {
            return new AppEvent(this.jsonString, this.isImplicit, this.inBackground, this.checksum);
        }

        private SerializationProxyV2(String str, boolean z2, boolean z3, String str2) {
            this.jsonString = str;
            this.isImplicit = z2;
            this.inBackground = z3;
            this.checksum = str2;
        }
    }

    private String calculateChecksum() {
        return md5Checksum(this.jsonObject.toString());
    }

    private JSONObject getJSONObjectForAppEvent(String str, @NonNull String str2, Double d2, Bundle bundle, @Nullable UUID uuid) throws JSONException, FacebookException {
        validateIdentifier(str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(Constants.EVENT_NAME_EVENT_KEY, str2);
        jSONObject.put(Constants.EVENT_NAME_MD5_EVENT_KEY, md5Checksum(str2));
        jSONObject.put(Constants.LOG_TIME_APP_EVENT_KEY, System.currentTimeMillis() / 1000);
        jSONObject.put("_ui", str);
        if (uuid != null) {
            jSONObject.put("_session_id", uuid);
        }
        if (bundle != null) {
            Map<String, String> mapValidateParameters = validateParameters(bundle);
            for (String str3 : mapValidateParameters.keySet()) {
                jSONObject.put(str3, mapValidateParameters.get(str3));
            }
        }
        if (d2 != null) {
            jSONObject.put(AppEventsConstants.EVENT_PARAM_VALUE_TO_SUM, d2.doubleValue());
        }
        if (this.inBackground) {
            jSONObject.put("_inBackground", "1");
        }
        if (this.isImplicit) {
            jSONObject.put("_implicitlyLogged", "1");
        } else {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Created app event '%s'", jSONObject.toString());
        }
        return jSONObject;
    }

    private static String md5Checksum(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
            byte[] bytes = str.getBytes("UTF-8");
            messageDigest.update(bytes, 0, bytes.length);
            return AppEventUtility.bytesToHex(messageDigest.digest());
        } catch (UnsupportedEncodingException e2) {
            Utility.logd("Failed to generate checksum: ", e2);
            return "1";
        } catch (NoSuchAlgorithmException e3) {
            Utility.logd("Failed to generate checksum: ", e3);
            return "0";
        }
    }

    private static void validateIdentifier(String str) throws FacebookException {
        boolean zContains;
        if (str == null || str.length() == 0 || str.length() > 40) {
            if (str == null) {
                str = "<None Provided>";
            }
            throw new FacebookException(String.format(Locale.ROOT, "Identifier '%s' must be less than %d characters", str, 40));
        }
        HashSet<String> hashSet = validatedIdentifiers;
        synchronized (hashSet) {
            zContains = hashSet.contains(str);
        }
        if (zContains) {
            return;
        }
        if (!str.matches("^[0-9a-zA-Z_]+[0-9a-zA-Z _-]*$")) {
            throw new FacebookException(String.format("Skipping event named '%s' due to illegal name - must be under 40 chars and alphanumeric, _, - or space, and not start with a space or hyphen.", str));
        }
        synchronized (hashSet) {
            hashSet.add(str);
        }
    }

    private Map<String, String> validateParameters(Bundle bundle) throws JSONException, FacebookException {
        HashMap map = new HashMap();
        for (String str : bundle.keySet()) {
            validateIdentifier(str);
            Object obj = bundle.get(str);
            if (!(obj instanceof String) && !(obj instanceof Number)) {
                throw new FacebookException(String.format("Parameter value '%s' for key '%s' should be a string or a numeric type.", obj, str));
            }
            map.put(str, obj.toString());
        }
        AddressFilterManager.processParameters(map);
        RestrictiveDataManager.processParameters(map, this.name);
        EventDeactivationManager.processDeprecatedParameters(map, this.name);
        return map;
    }

    private Object writeReplace() {
        return new SerializationProxyV2(this.jsonObject.toString(), this.isImplicit, this.inBackground, this.checksum);
    }

    public boolean getIsImplicit() {
        return this.isImplicit;
    }

    public JSONObject getJSONObject() {
        return this.jsonObject;
    }

    public String getName() {
        return this.name;
    }

    public boolean isChecksumValid() {
        if (this.checksum == null) {
            return true;
        }
        return calculateChecksum().equals(this.checksum);
    }

    public String toString() {
        return String.format("\"%s\", implicit: %b, json: %s", this.jsonObject.optString(Constants.EVENT_NAME_EVENT_KEY), Boolean.valueOf(this.isImplicit), this.jsonObject.toString());
    }

    public AppEvent(String str, @NonNull String str2, Double d2, Bundle bundle, boolean z2, boolean z3, @Nullable UUID uuid) throws JSONException, FacebookException {
        this.isImplicit = z2;
        this.inBackground = z3;
        this.name = str2;
        this.jsonObject = getJSONObjectForAppEvent(str, str2, d2, bundle, uuid);
        this.checksum = calculateChecksum();
    }

    private AppEvent(String str, boolean z2, boolean z3, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        this.jsonObject = jSONObject;
        this.isImplicit = z2;
        this.name = jSONObject.optString(Constants.EVENT_NAME_EVENT_KEY);
        this.checksum = str2;
        this.inBackground = z3;
    }
}
