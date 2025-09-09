package com.aliyun.iot.aep.sdk.framework.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.iot.aep.sdk.log.ALog;
import com.umeng.analytics.pro.ay;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class AConfigure {

    /* renamed from: a, reason: collision with root package name */
    private String f11758a;

    /* renamed from: b, reason: collision with root package name */
    private HashMap<String, String> f11759b;

    /* renamed from: c, reason: collision with root package name */
    private Context f11760c;

    /* renamed from: d, reason: collision with root package name */
    private SharedPreferences f11761d;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        @SuppressLint({"StaticFieldLeak"})
        private static final AConfigure f11762a = new AConfigure();
    }

    public static AConfigure getInstance() {
        return a.f11762a;
    }

    void a(String str) {
        if (!"test".equals(str) && !"pre".equals(str) && !"release".equals(str)) {
            Log.e("SDKManager", "env value error: " + str);
            str = "release";
        }
        this.f11758a = str;
    }

    public String getConfig(String str) {
        return this.f11759b.get(str);
    }

    public String getEnv() {
        return this.f11758a;
    }

    public String getSpConfig(String str) {
        return this.f11761d.getString(str, "");
    }

    public void init(Context context) throws IOException {
        this.f11760c = context;
        this.f11761d = context.getSharedPreferences("globalConfig", 0);
        a();
        String str = this.f11759b.get(ay.f21366a);
        String str2 = this.f11759b.get("suffix");
        String str3 = "114d";
        if (TextUtils.isEmpty(str2)) {
            str2 = "114d";
        }
        String str4 = str + OpenAccountUIConstants.UNDER_LINE + str2;
        if ("true".equalsIgnoreCase(this.f11759b.get("publish")) || TextUtils.isEmpty(str)) {
            str = "release";
        } else {
            str3 = str4;
        }
        this.f11759b.put(ay.f21366a, str);
        this.f11759b.put("suffix", str2);
        this.f11759b.put("securityIndex", str3);
    }

    public void putConfig(String str, String str2) {
        this.f11759b.put(str, str2);
    }

    public void updateSpConfig(String str, String str2) {
        this.f11761d.edit().putString(str, str2).apply();
    }

    private AConfigure() {
        this.f11758a = "release";
        this.f11759b = new HashMap<>();
    }

    public Map getConfig() {
        return this.f11759b;
    }

    private void a() throws IOException {
        String strA = a(this.f11760c);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(strA);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String strOptString = jSONObject.optString(next);
                if (next.equals(ay.f21366a)) {
                    a(strOptString);
                }
                String string = this.f11761d.getString(next, "");
                if (!TextUtils.isEmpty(string)) {
                    strOptString = string;
                }
                this.f11759b.put(next, strOptString);
            }
        } catch (JSONException e2) {
            ALog.w("SDKManager", "failed to parse json config, " + e2.getLocalizedMessage());
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private String a(Context context) throws IOException {
        AssetManager assets = context.getAssets();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assets.open("globalConfig/config.json")));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
        } catch (FileNotFoundException unused) {
            ALog.w("SDKManager", "config file not found");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return sb.toString();
    }
}
