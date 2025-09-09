package com.taobao.agoo.a;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.android.agoo.common.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {
    public static final String SP_AGOO_BIND_FILE_NAME = "AGOO_BIND";

    /* renamed from: a, reason: collision with root package name */
    private ConcurrentMap<String, Integer> f20420a = new ConcurrentHashMap();

    /* renamed from: b, reason: collision with root package name */
    private String f20421b;

    /* renamed from: c, reason: collision with root package name */
    private long f20422c;

    /* renamed from: d, reason: collision with root package name */
    private Context f20423d;

    public a(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null!!");
        }
        this.f20423d = context.getApplicationContext();
    }

    public void a(String str) throws JSONException {
        Integer num = this.f20420a.get(str);
        if (num == null || num.intValue() != 2) {
            this.f20420a.put(str, 2);
            com.taobao.accs.client.b.a(this.f20423d, "AGOO_BIND", this.f20422c, this.f20420a);
        }
    }

    public boolean b(String str) throws JSONException {
        if (this.f20420a.isEmpty()) {
            b();
        }
        Integer num = this.f20420a.get(str);
        ALog.i("AgooBindCache", "isAgooRegistered", "packageName", str, "appStatus", num, "agooBindStatus", this.f20420a);
        if (UtilityImpl.a(Config.PREFERENCES, this.f20423d)) {
            return false;
        }
        return num != null && num.intValue() == 2;
    }

    public void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f20421b = str;
    }

    public boolean d(String str) {
        String str2 = this.f20421b;
        return str2 != null && str2.equals(str);
    }

    public void a() {
        this.f20421b = null;
    }

    private void b() throws JSONException {
        try {
            String string = this.f20423d.getSharedPreferences("AGOO_BIND", 0).getString("bind_status", null);
            if (TextUtils.isEmpty(string)) {
                ALog.w("AgooBindCache", "restoreAgooClients packs null return", new Object[0]);
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            this.f20422c = jSONArray.getLong(0);
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j2 = this.f20422c;
            if (jCurrentTimeMillis < 86400000 + j2) {
                for (int i2 = 1; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    this.f20420a.put(jSONObject.getString("p"), Integer.valueOf(jSONObject.getInt("s")));
                }
                ALog.i("AgooBindCache", "restoreAgooClients", "mAgooBindStatus", this.f20420a);
                return;
            }
            ALog.i("AgooBindCache", "restoreAgooClients expired", "agooLastFlushTime", Long.valueOf(j2));
            this.f20422c = 0L;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
