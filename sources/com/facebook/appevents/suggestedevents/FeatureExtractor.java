package com.facebook.appevents.suggestedevents;

import android.util.Patterns;
import androidx.annotation.Nullable;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.android.agoo.message.MessageService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.ES6Iterator;

/* loaded from: classes3.dex */
final class FeatureExtractor {
    private static final int NUM_OF_FEATURES = 30;
    private static final String REGEX_ADD_TO_CART_BUTTON_TEXT = "(?i)add to(\\s|\\Z)|update(\\s|\\Z)|cart";
    private static final String REGEX_ADD_TO_CART_PAGE_TITLE = "(?i)add to(\\s|\\Z)|update(\\s|\\Z)|cart|shop|buy";
    private static final String REGEX_CR_HAS_CONFIRM_PASSWORD_FIELD = "(?i)(confirm.*password)|(password.*(confirmation|confirm)|confirmation)";
    private static final String REGEX_CR_HAS_LOG_IN_KEYWORDS = "(?i)(sign in)|login|signIn";
    private static final String REGEX_CR_HAS_SIGN_ON_KEYWORDS = "(?i)(sign.*(up|now)|registration|register|(create|apply).*(profile|account)|open.*account|account.*(open|creation|application)|enroll|join.*now)";
    private static final String REGEX_CR_PASSWORD_FIELD = "password";
    private static Map<String, String> eventInfo = null;
    private static boolean initialized = false;
    private static Map<String, String> languageInfo;
    private static JSONObject rules;
    private static Map<String, String> textTypeInfo;

    FeatureExtractor() {
    }

    @Nullable
    static float[] getDenseFeatures(JSONObject jSONObject, String str) {
        String lowerCase;
        JSONObject jSONObject2;
        String strOptString;
        JSONArray jSONArray;
        JSONObject interactedNode;
        if (!initialized) {
            return null;
        }
        float[] fArr = new float[30];
        Arrays.fill(fArr, 0.0f);
        try {
            lowerCase = str.toLowerCase();
            jSONObject2 = new JSONObject(jSONObject.optJSONObject(ViewHierarchyConstants.VIEW_KEY).toString());
            strOptString = jSONObject.optString(ViewHierarchyConstants.SCREEN_NAME_KEY);
            jSONArray = new JSONArray();
            pruneTree(jSONObject2, jSONArray);
            sum(fArr, parseFeatures(jSONObject2));
            interactedNode = getInteractedNode(jSONObject2);
        } catch (JSONException unused) {
        }
        if (interactedNode == null) {
            return null;
        }
        sum(fArr, nonparseFeatures(interactedNode, jSONArray, strOptString, jSONObject2.toString(), lowerCase));
        return fArr;
    }

    @Nullable
    private static JSONObject getInteractedNode(JSONObject jSONObject) {
        if (jSONObject.optBoolean(ViewHierarchyConstants.IS_INTERACTED_KEY)) {
            return jSONObject;
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
        if (jSONArrayOptJSONArray == null) {
            return null;
        }
        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
            JSONObject interactedNode = getInteractedNode(jSONArrayOptJSONArray.getJSONObject(i2));
            if (interactedNode != null) {
                return interactedNode;
            }
        }
        return null;
    }

    static String getTextFeature(String str, String str2, String str3) {
        return (str3 + " | " + str2 + ", " + str).toLowerCase();
    }

    static void initialize(File file) throws IOException {
        try {
            rules = new JSONObject();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            fileInputStream.close();
            rules = new JSONObject(new String(bArr, "UTF-8"));
            HashMap map = new HashMap();
            languageInfo = map;
            map.put(ViewHierarchyConstants.ENGLISH, "1");
            languageInfo.put(ViewHierarchyConstants.GERMAN, "2");
            languageInfo.put(ViewHierarchyConstants.SPANISH, "3");
            languageInfo.put(ViewHierarchyConstants.JAPANESE, "4");
            HashMap map2 = new HashMap();
            eventInfo = map2;
            map2.put(ViewHierarchyConstants.VIEW_CONTENT, "0");
            eventInfo.put(ViewHierarchyConstants.SEARCH, "1");
            eventInfo.put(ViewHierarchyConstants.ADD_TO_CART, "2");
            eventInfo.put(ViewHierarchyConstants.ADD_TO_WISHLIST, "3");
            eventInfo.put(ViewHierarchyConstants.INITIATE_CHECKOUT, "4");
            eventInfo.put(ViewHierarchyConstants.ADD_PAYMENT_INFO, AlcsPalConst.MODEL_TYPE_TGMESH);
            eventInfo.put(ViewHierarchyConstants.PURCHASE, "6");
            eventInfo.put(ViewHierarchyConstants.LEAD, "7");
            eventInfo.put(ViewHierarchyConstants.COMPLETE_REGISTRATION, MessageService.MSG_ACCS_NOTIFY_CLICK);
            HashMap map3 = new HashMap();
            textTypeInfo = map3;
            map3.put(ViewHierarchyConstants.BUTTON_TEXT, "1");
            textTypeInfo.put(ViewHierarchyConstants.PAGE_TITLE, "2");
            textTypeInfo.put(ViewHierarchyConstants.RESOLVED_DOCUMENT_LINK, "3");
            textTypeInfo.put(ViewHierarchyConstants.BUTTON_ID, "4");
            initialized = true;
        } catch (Exception unused) {
        }
    }

    private static boolean isButton(JSONObject jSONObject) {
        return (jSONObject.optInt(ViewHierarchyConstants.CLASS_TYPE_BITMASK_KEY) & 32) > 0;
    }

    static boolean isInitialized() {
        return initialized;
    }

    private static boolean matchIndicators(String[] strArr, String[] strArr2) {
        for (String str : strArr) {
            for (String str2 : strArr2) {
                if (str2.contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static float[] nonparseFeatures(JSONObject jSONObject, JSONArray jSONArray, String str, String str2, String str3) {
        float[] fArr = new float[30];
        Arrays.fill(fArr, 0.0f);
        fArr[3] = jSONArray.length() > 1 ? r2 - 1 : 0;
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                if (isButton(jSONArray.getJSONObject(i2))) {
                    fArr[9] = fArr[9] + 1.0f;
                }
            } catch (JSONException unused) {
            }
        }
        fArr[13] = -1.0f;
        fArr[14] = -1.0f;
        String str4 = str + '|' + str3;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        updateHintAndTextRecursively(jSONObject, sb, sb2);
        String string = sb.toString();
        String string2 = sb2.toString();
        fArr[15] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.COMPLETE_REGISTRATION, ViewHierarchyConstants.BUTTON_TEXT, string2) ? 1.0f : 0.0f;
        fArr[16] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.COMPLETE_REGISTRATION, ViewHierarchyConstants.PAGE_TITLE, str4) ? 1.0f : 0.0f;
        fArr[17] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.COMPLETE_REGISTRATION, ViewHierarchyConstants.BUTTON_ID, string) ? 1.0f : 0.0f;
        fArr[18] = str2.contains("password") ? 1.0f : 0.0f;
        fArr[19] = regexMatched(REGEX_CR_HAS_CONFIRM_PASSWORD_FIELD, str2) ? 1.0f : 0.0f;
        fArr[20] = regexMatched(REGEX_CR_HAS_LOG_IN_KEYWORDS, str2) ? 1.0f : 0.0f;
        fArr[21] = regexMatched(REGEX_CR_HAS_SIGN_ON_KEYWORDS, str2) ? 1.0f : 0.0f;
        fArr[22] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.PURCHASE, ViewHierarchyConstants.BUTTON_TEXT, string2) ? 1.0f : 0.0f;
        fArr[24] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.PURCHASE, ViewHierarchyConstants.PAGE_TITLE, str4) ? 1.0f : 0.0f;
        fArr[25] = regexMatched(REGEX_ADD_TO_CART_BUTTON_TEXT, string2) ? 1.0f : 0.0f;
        fArr[27] = regexMatched(REGEX_ADD_TO_CART_PAGE_TITLE, str4) ? 1.0f : 0.0f;
        fArr[28] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.LEAD, ViewHierarchyConstants.BUTTON_TEXT, string2) ? 1.0f : 0.0f;
        fArr[29] = regexMatched(ViewHierarchyConstants.ENGLISH, ViewHierarchyConstants.LEAD, ViewHierarchyConstants.PAGE_TITLE, str4) ? 1.0f : 0.0f;
        return fArr;
    }

    private static float[] parseFeatures(JSONObject jSONObject) {
        float[] fArr = new float[30];
        Arrays.fill(fArr, 0.0f);
        String lowerCase = jSONObject.optString("text").toLowerCase();
        String lowerCase2 = jSONObject.optString(ViewHierarchyConstants.HINT_KEY).toLowerCase();
        String lowerCase3 = jSONObject.optString(ViewHierarchyConstants.CLASS_NAME_KEY).toLowerCase();
        int iOptInt = jSONObject.optInt(ViewHierarchyConstants.INPUT_TYPE_KEY, -1);
        String[] strArr = {lowerCase, lowerCase2};
        if (matchIndicators(new String[]{"$", "amount", FirebaseAnalytics.Param.PRICE, AlinkConstants.KEY_TOTAL}, strArr)) {
            fArr[0] = (float) (fArr[0] + 1.0d);
        }
        if (matchIndicators(new String[]{"password", "pwd"}, strArr)) {
            fArr[1] = (float) (fArr[1] + 1.0d);
        }
        if (matchIndicators(new String[]{"tel", "phone"}, strArr)) {
            fArr[2] = (float) (fArr[2] + 1.0d);
        }
        if (matchIndicators(new String[]{FirebaseAnalytics.Event.SEARCH}, strArr)) {
            fArr[4] = (float) (fArr[4] + 1.0d);
        }
        if (iOptInt >= 0) {
            fArr[5] = (float) (fArr[5] + 1.0d);
        }
        if (iOptInt == 3 || iOptInt == 2) {
            fArr[6] = (float) (fArr[6] + 1.0d);
        }
        if (iOptInt == 32 || Patterns.EMAIL_ADDRESS.matcher(lowerCase).matches()) {
            fArr[7] = (float) (fArr[7] + 1.0d);
        }
        if (lowerCase3.contains("checkbox")) {
            fArr[8] = (float) (fArr[8] + 1.0d);
        }
        if (matchIndicators(new String[]{"complete", "confirm", ES6Iterator.DONE_PROPERTY, "submit"}, new String[]{lowerCase})) {
            fArr[10] = (float) (fArr[10] + 1.0d);
        }
        if (lowerCase3.contains("radio") && lowerCase3.contains("button")) {
            fArr[12] = (float) (fArr[12] + 1.0d);
        }
        try {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
            int length = jSONArrayOptJSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                sum(fArr, parseFeatures(jSONArrayOptJSONArray.getJSONObject(i2)));
            }
        } catch (JSONException unused) {
        }
        return fArr;
    }

    private static boolean pruneTree(JSONObject jSONObject, JSONArray jSONArray) throws JSONException {
        boolean z2;
        try {
            if (jSONObject.optBoolean(ViewHierarchyConstants.IS_INTERACTED_KEY)) {
                return true;
            }
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
            int i2 = 0;
            while (true) {
                if (i2 >= jSONArrayOptJSONArray.length()) {
                    z2 = false;
                    break;
                }
                if (jSONArrayOptJSONArray.getJSONObject(i2).optBoolean(ViewHierarchyConstants.IS_INTERACTED_KEY)) {
                    z2 = true;
                    break;
                }
                i2++;
            }
            boolean z3 = z2;
            JSONArray jSONArray2 = new JSONArray();
            if (z2) {
                for (int i3 = 0; i3 < jSONArrayOptJSONArray.length(); i3++) {
                    jSONArray.put(jSONArrayOptJSONArray.getJSONObject(i3));
                }
            } else {
                for (int i4 = 0; i4 < jSONArrayOptJSONArray.length(); i4++) {
                    JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i4);
                    if (pruneTree(jSONObject2, jSONArray)) {
                        jSONArray2.put(jSONObject2);
                        z3 = true;
                    }
                }
                jSONObject.put(ViewHierarchyConstants.CHILDREN_VIEW_KEY, jSONArray2);
            }
            return z3;
        } catch (JSONException unused) {
            return false;
        }
    }

    private static boolean regexMatched(String str, String str2, String str3, String str4) {
        return regexMatched(rules.optJSONObject("rulesForLanguage").optJSONObject(languageInfo.get(str)).optJSONObject("rulesForEvent").optJSONObject(eventInfo.get(str2)).optJSONObject("positiveRules").optString(textTypeInfo.get(str3)), str4);
    }

    private static void sum(float[] fArr, float[] fArr2) {
        for (int i2 = 0; i2 < fArr.length; i2++) {
            fArr[i2] = fArr[i2] + fArr2[i2];
        }
    }

    private static void updateHintAndTextRecursively(JSONObject jSONObject, StringBuilder sb, StringBuilder sb2) {
        String lowerCase = jSONObject.optString("text", "").toLowerCase();
        String lowerCase2 = jSONObject.optString(ViewHierarchyConstants.HINT_KEY, "").toLowerCase();
        if (!lowerCase.isEmpty()) {
            sb.append(lowerCase);
            sb.append(" ");
        }
        if (!lowerCase2.isEmpty()) {
            sb2.append(lowerCase2);
            sb2.append(" ");
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(ViewHierarchyConstants.CHILDREN_VIEW_KEY);
        if (jSONArrayOptJSONArray == null) {
            return;
        }
        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
            updateHintAndTextRecursively(jSONObject, sb, sb2);
        }
    }

    private static boolean regexMatched(String str, String str2) {
        return Pattern.compile(str).matcher(str2).find();
    }
}
