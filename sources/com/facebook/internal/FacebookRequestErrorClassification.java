package com.facebook.internal;

import com.facebook.FacebookRequestError;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class FacebookRequestErrorClassification {
    public static final int EC_APP_NOT_INSTALLED = 412;
    public static final int EC_APP_TOO_MANY_CALLS = 4;
    public static final int EC_INVALID_SESSION = 102;
    public static final int EC_INVALID_TOKEN = 190;
    public static final int EC_RATE = 9;
    public static final int EC_SERVICE_UNAVAILABLE = 2;
    public static final int EC_TOO_MANY_USER_ACTION_CALLS = 341;
    public static final int EC_USER_TOO_MANY_CALLS = 17;
    public static final int ESC_APP_INACTIVE = 493;
    public static final int ESC_APP_NOT_INSTALLED = 458;
    public static final String KEY_LOGIN_RECOVERABLE = "login_recoverable";
    public static final String KEY_NAME = "name";
    public static final String KEY_OTHER = "other";
    public static final String KEY_RECOVERY_MESSAGE = "recovery_message";
    public static final String KEY_TRANSIENT = "transient";
    private static FacebookRequestErrorClassification defaultInstance;
    private final Map<Integer, Set<Integer>> loginRecoverableErrors;
    private final String loginRecoverableRecoveryMessage;
    private final Map<Integer, Set<Integer>> otherErrors;
    private final String otherRecoveryMessage;
    private final Map<Integer, Set<Integer>> transientErrors;
    private final String transientRecoveryMessage;

    /* renamed from: com.facebook.internal.FacebookRequestErrorClassification$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$FacebookRequestError$Category;

        static {
            int[] iArr = new int[FacebookRequestError.Category.values().length];
            $SwitchMap$com$facebook$FacebookRequestError$Category = iArr;
            try {
                iArr[FacebookRequestError.Category.OTHER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$FacebookRequestError$Category[FacebookRequestError.Category.LOGIN_RECOVERABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$FacebookRequestError$Category[FacebookRequestError.Category.TRANSIENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    FacebookRequestErrorClassification(Map<Integer, Set<Integer>> map, Map<Integer, Set<Integer>> map2, Map<Integer, Set<Integer>> map3, String str, String str2, String str3) {
        this.otherErrors = map;
        this.transientErrors = map2;
        this.loginRecoverableErrors = map3;
        this.otherRecoveryMessage = str;
        this.transientRecoveryMessage = str2;
        this.loginRecoverableRecoveryMessage = str3;
    }

    public static FacebookRequestErrorClassification createFromJSON(JSONArray jSONArray) {
        String strOptString;
        if (jSONArray == null) {
            return null;
        }
        Map<Integer, Set<Integer>> jSONDefinition = null;
        Map<Integer, Set<Integer>> jSONDefinition2 = null;
        Map<Integer, Set<Integer>> jSONDefinition3 = null;
        String strOptString2 = null;
        String strOptString3 = null;
        String strOptString4 = null;
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
            if (jSONObjectOptJSONObject != null && (strOptString = jSONObjectOptJSONObject.optString("name")) != null) {
                if (strOptString.equalsIgnoreCase("other")) {
                    strOptString2 = jSONObjectOptJSONObject.optString(KEY_RECOVERY_MESSAGE, null);
                    jSONDefinition = parseJSONDefinition(jSONObjectOptJSONObject);
                } else if (strOptString.equalsIgnoreCase(KEY_TRANSIENT)) {
                    strOptString3 = jSONObjectOptJSONObject.optString(KEY_RECOVERY_MESSAGE, null);
                    jSONDefinition2 = parseJSONDefinition(jSONObjectOptJSONObject);
                } else if (strOptString.equalsIgnoreCase(KEY_LOGIN_RECOVERABLE)) {
                    strOptString4 = jSONObjectOptJSONObject.optString(KEY_RECOVERY_MESSAGE, null);
                    jSONDefinition3 = parseJSONDefinition(jSONObjectOptJSONObject);
                }
            }
        }
        return new FacebookRequestErrorClassification(jSONDefinition, jSONDefinition2, jSONDefinition3, strOptString2, strOptString3, strOptString4);
    }

    public static synchronized FacebookRequestErrorClassification getDefaultErrorClassification() {
        try {
            if (defaultInstance == null) {
                defaultInstance = getDefaultErrorClassificationImpl();
            }
        } catch (Throwable th) {
            throw th;
        }
        return defaultInstance;
    }

    private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl() {
        return new FacebookRequestErrorClassification(null, new HashMap<Integer, Set<Integer>>() { // from class: com.facebook.internal.FacebookRequestErrorClassification.1
            {
                put(2, null);
                put(4, null);
                put(9, null);
                put(17, null);
                put(Integer.valueOf(FacebookRequestErrorClassification.EC_TOO_MANY_USER_ACTION_CALLS), null);
            }
        }, new HashMap<Integer, Set<Integer>>() { // from class: com.facebook.internal.FacebookRequestErrorClassification.2
            {
                put(102, null);
                put(190, null);
                put(Integer.valueOf(FacebookRequestErrorClassification.EC_APP_NOT_INSTALLED), null);
            }
        }, null, null, null);
    }

    private static Map<Integer, Set<Integer>> parseJSONDefinition(JSONObject jSONObject) {
        int iOptInt;
        HashSet hashSet;
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("items");
        if (jSONArrayOptJSONArray.length() == 0) {
            return null;
        }
        HashMap map = new HashMap();
        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
            if (jSONObjectOptJSONObject != null && (iOptInt = jSONObjectOptJSONObject.optInt("code")) != 0) {
                JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("subcodes");
                if (jSONArrayOptJSONArray2 == null || jSONArrayOptJSONArray2.length() <= 0) {
                    hashSet = null;
                } else {
                    hashSet = new HashSet();
                    for (int i3 = 0; i3 < jSONArrayOptJSONArray2.length(); i3++) {
                        int iOptInt2 = jSONArrayOptJSONArray2.optInt(i3);
                        if (iOptInt2 != 0) {
                            hashSet.add(Integer.valueOf(iOptInt2));
                        }
                    }
                }
                map.put(Integer.valueOf(iOptInt), hashSet);
            }
        }
        return map;
    }

    public FacebookRequestError.Category classify(int i2, int i3, boolean z2) {
        Set<Integer> set;
        Set<Integer> set2;
        Set<Integer> set3;
        if (z2) {
            return FacebookRequestError.Category.TRANSIENT;
        }
        Map<Integer, Set<Integer>> map = this.otherErrors;
        if (map != null && map.containsKey(Integer.valueOf(i2)) && ((set3 = this.otherErrors.get(Integer.valueOf(i2))) == null || set3.contains(Integer.valueOf(i3)))) {
            return FacebookRequestError.Category.OTHER;
        }
        Map<Integer, Set<Integer>> map2 = this.loginRecoverableErrors;
        if (map2 != null && map2.containsKey(Integer.valueOf(i2)) && ((set2 = this.loginRecoverableErrors.get(Integer.valueOf(i2))) == null || set2.contains(Integer.valueOf(i3)))) {
            return FacebookRequestError.Category.LOGIN_RECOVERABLE;
        }
        Map<Integer, Set<Integer>> map3 = this.transientErrors;
        return (map3 != null && map3.containsKey(Integer.valueOf(i2)) && ((set = this.transientErrors.get(Integer.valueOf(i2))) == null || set.contains(Integer.valueOf(i3)))) ? FacebookRequestError.Category.TRANSIENT : FacebookRequestError.Category.OTHER;
    }

    public Map<Integer, Set<Integer>> getLoginRecoverableErrors() {
        return this.loginRecoverableErrors;
    }

    public Map<Integer, Set<Integer>> getOtherErrors() {
        return this.otherErrors;
    }

    public String getRecoveryMessage(FacebookRequestError.Category category) {
        int i2 = AnonymousClass3.$SwitchMap$com$facebook$FacebookRequestError$Category[category.ordinal()];
        if (i2 == 1) {
            return this.otherRecoveryMessage;
        }
        if (i2 == 2) {
            return this.loginRecoverableRecoveryMessage;
        }
        if (i2 != 3) {
            return null;
        }
        return this.transientRecoveryMessage;
    }

    public Map<Integer, Set<Integer>> getTransientErrors() {
        return this.transientErrors;
    }
}
