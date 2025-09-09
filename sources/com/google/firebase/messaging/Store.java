package com.google.firebase.messaging;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
class Store {
    private static final String SCOPE_ALL = "*";
    private static final String STORE_KEY_TOKEN = "|T|";

    /* renamed from: a, reason: collision with root package name */
    final SharedPreferences f15107a;

    static class Token {
        private static final String KEY_APP_VERSION = "appVersion";
        private static final String KEY_TIMESTAMP = "timestamp";
        private static final String KEY_TOKEN = "token";
        private static final long REFRESH_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(7);

        /* renamed from: a, reason: collision with root package name */
        final String f15108a;

        /* renamed from: b, reason: collision with root package name */
        final String f15109b;

        /* renamed from: c, reason: collision with root package name */
        final long f15110c;

        private Token(String str, String str2, long j2) {
            this.f15108a = str;
            this.f15109b = str2;
            this.f15110c = j2;
        }

        static String a(String str, String str2, long j2) throws JSONException {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("token", str);
                jSONObject.put("appVersion", str2);
                jSONObject.put("timestamp", j2);
                return jSONObject.toString();
            } catch (JSONException e2) {
                Log.w(Constants.TAG, "Failed to encode token: " + e2);
                return null;
            }
        }

        static Token c(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if (!str.startsWith("{")) {
                return new Token(str, null, 0L);
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                return new Token(jSONObject.getString("token"), jSONObject.getString("appVersion"), jSONObject.getLong("timestamp"));
            } catch (JSONException e2) {
                Log.w(Constants.TAG, "Failed to parse token: " + e2);
                return null;
            }
        }

        boolean b(String str) {
            return System.currentTimeMillis() > this.f15110c + REFRESH_PERIOD_MILLIS || !str.equals(this.f15109b);
        }
    }

    public Store(Context context) {
        this.f15107a = context.getSharedPreferences("com.google.android.gms.appid", 0);
        checkForRestore(context, "com.google.android.gms.appid-no-backup");
    }

    private void checkForRestore(Context context, String str) {
        File file = new File(ContextCompat.getNoBackupFilesDir(context), str);
        if (file.exists()) {
            return;
        }
        try {
            if (!file.createNewFile() || isEmpty()) {
                return;
            }
            Log.i(Constants.TAG, "App restored, clearing state");
            deleteAll();
        } catch (IOException e2) {
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "Error creating file in no backup dir: " + e2.getMessage());
            }
        }
    }

    private String createTokenKey(String str, String str2) {
        return str + STORE_KEY_TOKEN + str2 + "|*";
    }

    public synchronized void deleteAll() {
        this.f15107a.edit().clear().commit();
    }

    public synchronized void deleteToken(String str, String str2) {
        String strCreateTokenKey = createTokenKey(str, str2);
        SharedPreferences.Editor editorEdit = this.f15107a.edit();
        editorEdit.remove(strCreateTokenKey);
        editorEdit.commit();
    }

    public synchronized Token getToken(String str, String str2) {
        return Token.c(this.f15107a.getString(createTokenKey(str, str2), null));
    }

    public synchronized boolean isEmpty() {
        return this.f15107a.getAll().isEmpty();
    }

    public synchronized void saveToken(String str, String str2, String str3, String str4) {
        String strA = Token.a(str3, str4, System.currentTimeMillis());
        if (strA == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.f15107a.edit();
        editorEdit.putString(createTokenKey(str, str2), strA);
        editorEdit.commit();
    }
}
