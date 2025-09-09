package com.facebook.appevents;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class UserDataStore {
    public static final String CITY = "ct";
    public static final String COUNTRY = "country";
    private static final String DATA_SEPARATOR = ",";
    public static final String DATE_OF_BIRTH = "db";
    public static final String EMAIL = "em";
    public static final String FIRST_NAME = "fn";
    public static final String GENDER = "ge";
    private static final String INTERNAL_USER_DATA_KEY = "com.facebook.appevents.UserDataStore.internalUserData";
    public static final String LAST_NAME = "ln";
    private static final int MAX_NUM = 5;
    public static final String PHONE = "ph";
    public static final String STATE = "st";
    private static final String TAG = "UserDataStore";
    private static final String USER_DATA_KEY = "com.facebook.appevents.UserDataStore.userData";
    public static final String ZIP = "zp";
    private static SharedPreferences sharedPreferences;
    private static AtomicBoolean initialized = new AtomicBoolean(false);
    private static final ConcurrentHashMap<String, String> externalHashedUserData = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> internalHashedUserData = new ConcurrentHashMap<>();

    static void clear() {
        InternalAppEventsLogger.getAnalyticsExecutor().execute(new Runnable() { // from class: com.facebook.appevents.UserDataStore.3
            @Override // java.lang.Runnable
            public void run() {
                if (!UserDataStore.initialized.get()) {
                    Log.w(UserDataStore.TAG, "initStore should have been called before calling setUserData");
                    UserDataStore.initAndWait();
                }
                UserDataStore.externalHashedUserData.clear();
                UserDataStore.sharedPreferences.edit().putString(UserDataStore.USER_DATA_KEY, null).apply();
            }
        });
    }

    public static String getAllHashedUserData() {
        if (!initialized.get()) {
            initAndWait();
        }
        HashMap map = new HashMap();
        map.putAll(externalHashedUserData);
        map.putAll(internalHashedUserData);
        return Utility.mapToJsonStr(map);
    }

    static String getHashedUserData() {
        if (!initialized.get()) {
            Log.w(TAG, "initStore should have been called before calling setUserID");
            initAndWait();
        }
        return Utility.mapToJsonStr(externalHashedUserData);
    }

    public static Map<String, String> getInternalHashedUserData() {
        if (!initialized.get()) {
            initAndWait();
        }
        return new HashMap(internalHashedUserData);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void initAndWait() {
        if (initialized.get()) {
            return;
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext());
        sharedPreferences = defaultSharedPreferences;
        String string = defaultSharedPreferences.getString(USER_DATA_KEY, "");
        String string2 = sharedPreferences.getString(INTERNAL_USER_DATA_KEY, "");
        externalHashedUserData.putAll(Utility.JsonStrToMap(string));
        internalHashedUserData.putAll(Utility.JsonStrToMap(string2));
        initialized.set(true);
    }

    static void initStore() {
        if (initialized.get()) {
            return;
        }
        initAndWait();
    }

    private static boolean maybeSHA256Hashed(String str) {
        return str.matches("[A-Fa-f0-9]{64}");
    }

    private static String normalizeData(String str, String str2) {
        String lowerCase = str2.trim().toLowerCase();
        if (EMAIL.equals(str)) {
            if (Patterns.EMAIL_ADDRESS.matcher(lowerCase).matches()) {
                return lowerCase;
            }
            Log.e(TAG, "Setting email failure: this is not a valid email address");
            return "";
        }
        if (PHONE.equals(str)) {
            return lowerCase.replaceAll("[^0-9]", "");
        }
        if (!GENDER.equals(str)) {
            return lowerCase;
        }
        String strSubstring = lowerCase.length() > 0 ? lowerCase.substring(0, 1) : "";
        if ("f".equals(strSubstring) || "m".equals(strSubstring)) {
            return strSubstring;
        }
        Log.e(TAG, "Setting gender failure: the supported value for gender is f or m");
        return "";
    }

    public static void removeRules(List<String> list) {
        if (!initialized.get()) {
            initAndWait();
        }
        for (String str : list) {
            ConcurrentHashMap<String, String> concurrentHashMap = internalHashedUserData;
            if (concurrentHashMap.containsKey(str)) {
                concurrentHashMap.remove(str);
            }
        }
        writeDataIntoCache(INTERNAL_USER_DATA_KEY, Utility.mapToJsonStr(internalHashedUserData));
    }

    static void setInternalUd(Map<String, String> map) {
        if (!initialized.get()) {
            initAndWait();
        }
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            String strSha256hash = Utility.sha256hash(normalizeData(key, map.get(key).trim()));
            ConcurrentHashMap<String, String> concurrentHashMap = internalHashedUserData;
            if (concurrentHashMap.containsKey(key)) {
                String str = concurrentHashMap.get(key);
                String[] strArrSplit = str != null ? str.split(",") : new String[0];
                HashSet hashSet = new HashSet(Arrays.asList(strArrSplit));
                if (hashSet.contains(strSha256hash)) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                if (strArrSplit.length == 0) {
                    sb.append(strSha256hash);
                } else if (strArrSplit.length < 5) {
                    sb.append(str);
                    sb.append(",");
                    sb.append(strSha256hash);
                } else {
                    for (int i2 = 1; i2 < 5; i2++) {
                        sb.append(strArrSplit[i2]);
                        sb.append(",");
                    }
                    sb.append(strSha256hash);
                    hashSet.remove(strArrSplit[0]);
                }
                internalHashedUserData.put(key, sb.toString());
            } else {
                concurrentHashMap.put(key, strSha256hash);
            }
        }
        writeDataIntoCache(INTERNAL_USER_DATA_KEY, Utility.mapToJsonStr(internalHashedUserData));
    }

    static void setUserDataAndHash(final Bundle bundle) {
        InternalAppEventsLogger.getAnalyticsExecutor().execute(new Runnable() { // from class: com.facebook.appevents.UserDataStore.2
            @Override // java.lang.Runnable
            public void run() {
                if (!UserDataStore.initialized.get()) {
                    Log.w(UserDataStore.TAG, "initStore should have been called before calling setUserData");
                    UserDataStore.initAndWait();
                }
                UserDataStore.updateHashUserData(bundle);
                UserDataStore.writeDataIntoCache(UserDataStore.USER_DATA_KEY, Utility.mapToJsonStr(UserDataStore.externalHashedUserData));
                UserDataStore.writeDataIntoCache(UserDataStore.INTERNAL_USER_DATA_KEY, Utility.mapToJsonStr(UserDataStore.internalHashedUserData));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateHashUserData(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj != null) {
                String string = obj.toString();
                if (maybeSHA256Hashed(string)) {
                    externalHashedUserData.put(str, string.toLowerCase());
                } else {
                    String strSha256hash = Utility.sha256hash(normalizeData(str, string));
                    if (strSha256hash != null) {
                        externalHashedUserData.put(str, strSha256hash);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeDataIntoCache(final String str, final String str2) {
        FacebookSdk.getExecutor().execute(new Runnable() { // from class: com.facebook.appevents.UserDataStore.1
            @Override // java.lang.Runnable
            public void run() {
                if (!UserDataStore.initialized.get()) {
                    UserDataStore.initAndWait();
                }
                UserDataStore.sharedPreferences.edit().putString(str, str2).apply();
            }
        });
    }

    static void setUserDataAndHash(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10) {
        Bundle bundle = new Bundle();
        if (str != null) {
            bundle.putString(EMAIL, str);
        }
        if (str2 != null) {
            bundle.putString(FIRST_NAME, str2);
        }
        if (str3 != null) {
            bundle.putString(LAST_NAME, str3);
        }
        if (str4 != null) {
            bundle.putString(PHONE, str4);
        }
        if (str5 != null) {
            bundle.putString(DATE_OF_BIRTH, str5);
        }
        if (str6 != null) {
            bundle.putString(GENDER, str6);
        }
        if (str7 != null) {
            bundle.putString("ct", str7);
        }
        if (str8 != null) {
            bundle.putString("st", str8);
        }
        if (str9 != null) {
            bundle.putString(ZIP, str9);
        }
        if (str10 != null) {
            bundle.putString("country", str10);
        }
        setUserDataAndHash(bundle);
    }
}
