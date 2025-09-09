package com.taobao.accs.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private Context f20088a;

    /* renamed from: d, reason: collision with root package name */
    private long f20091d;

    /* renamed from: f, reason: collision with root package name */
    private String f20093f;

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentMap<String, Integer> f20089b = new ConcurrentHashMap();

    /* renamed from: c, reason: collision with root package name */
    private ConcurrentMap<String, Set<String>> f20090c = new ConcurrentHashMap();

    /* renamed from: e, reason: collision with root package name */
    private String f20092e = "ClientManager_";

    public b(Context context, String str) throws JSONException {
        this.f20093f = "ACCS_BIND";
        if (context == null) {
            throw new RuntimeException("Context is null!!");
        }
        this.f20092e += str;
        this.f20088a = context.getApplicationContext();
        this.f20093f = "ACCS_BIND" + str;
        a();
    }

    public void a(String str) throws JSONException {
        Integer num = this.f20089b.get(str);
        if (num == null || num.intValue() != 2) {
            this.f20089b.put(str, 2);
            a(this.f20088a, this.f20093f, this.f20091d, this.f20089b);
        }
    }

    public void b(String str) throws JSONException {
        Integer num = this.f20089b.get(str);
        if (num == null || num.intValue() != 4) {
            this.f20089b.put(str, 4);
            a(this.f20088a, this.f20093f, this.f20091d, this.f20089b);
        }
    }

    public boolean c(String str) throws JSONException {
        if (this.f20089b.isEmpty()) {
            a();
        }
        Integer num = this.f20089b.get(str);
        ALog.i(this.f20092e, "isAppBinded", "appStatus", num, "mBindStatus", this.f20089b);
        return num != null && num.intValue() == 2;
    }

    public boolean d(String str) {
        Integer num = this.f20089b.get(str);
        return num != null && num.intValue() == 4;
    }

    public void e(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.f20090c.remove(str);
        } catch (Exception e2) {
            ALog.e(this.f20092e, this.f20092e + e2.toString(), new Object[0]);
            e2.printStackTrace();
        }
    }

    public void a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                Set<String> hashSet = this.f20090c.get(str);
                if (hashSet == null) {
                    hashSet = new HashSet<>();
                }
                hashSet.add(str2);
                this.f20090c.put(str, hashSet);
            }
        } catch (Exception e2) {
            ALog.e(this.f20092e, this.f20092e + e2.toString(), new Object[0]);
            e2.printStackTrace();
        }
    }

    public boolean b(String str, String str2) {
        Set<String> set;
        try {
            if (!TextUtils.isEmpty(str) && (set = this.f20090c.get(str)) != null) {
                if (set.contains(str2)) {
                    return true;
                }
            }
        } catch (Exception e2) {
            ALog.e(this.f20092e, this.f20092e + e2.toString(), new Object[0]);
            e2.printStackTrace();
        }
        return false;
    }

    private void a() throws JSONException {
        try {
            String string = this.f20088a.getSharedPreferences(this.f20093f, 0).getString("bind_status", null);
            if (TextUtils.isEmpty(string)) {
                ALog.w(this.f20092e, "restoreClients break as packages null", new Object[0]);
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            this.f20091d = jSONArray.getLong(0);
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j2 = this.f20091d;
            if (jCurrentTimeMillis < 86400000 + j2) {
                for (int i2 = 1; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    this.f20089b.put(jSONObject.getString("p"), Integer.valueOf(jSONObject.getInt("s")));
                }
                ALog.i(this.f20092e, "restoreClients success", "mBindStatus", this.f20089b);
                return;
            }
            ALog.i(this.f20092e, "restoreClients expired", "lastFlushTime", Long.valueOf(j2));
            this.f20091d = 0L;
        } catch (Exception e2) {
            ALog.w(this.f20092e, "restoreClients", e2, new Object[0]);
        }
    }

    public static void a(Context context, String str, long j2, Map<String, Integer> map) throws JSONException {
        try {
            String[] strArr = (String[]) map.keySet().toArray(new String[0]);
            JSONArray jSONArray = new JSONArray();
            if (j2 > 0 && j2 < System.currentTimeMillis()) {
                jSONArray.put(j2);
            } else {
                jSONArray.put(System.currentTimeMillis() - (Math.random() * 8.64E7d));
            }
            for (String str2 : strArr) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("p", str2);
                jSONObject.put("s", map.get(str2).intValue());
                jSONArray.put(jSONObject);
            }
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
            editorEdit.putString("bind_status", jSONArray.toString());
            editorEdit.apply();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
