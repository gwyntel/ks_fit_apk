package com.umeng.analytics.pro;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ae extends ab {

    /* renamed from: a, reason: collision with root package name */
    private int f21289a;

    /* renamed from: b, reason: collision with root package name */
    private int f21290b;

    /* renamed from: c, reason: collision with root package name */
    private int f21291c;

    /* renamed from: d, reason: collision with root package name */
    private ArrayList<Pair<String, Integer>> f21292d;

    public ae(String str, ArrayList<ac> arrayList) {
        super(str, arrayList);
    }

    public void a(int i2) {
        this.f21289a = i2;
    }

    public void b(int i2) {
        this.f21290b = i2;
    }

    public void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(new String(ax.a(Base64.decode(str, 0), UMUtils.genSin())));
            int length = jSONArray.length();
            if (length > 0) {
                this.f21292d = new ArrayList<>();
            }
            for (int i2 = 0; i2 < length; i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                String strOptString = jSONObject.has("type") ? jSONObject.optString("type") : "";
                int iValueOf = jSONObject.has(com.umeng.ccg.a.B) ? Integer.valueOf(jSONObject.optInt(com.umeng.ccg.a.B)) : 1;
                if (!TextUtils.isEmpty(strOptString)) {
                    this.f21292d.add(new Pair<>(strOptString, iValueOf));
                }
            }
            this.f21291c = this.f21292d.size();
        } catch (Throwable unused) {
        }
    }

    public int d() {
        return this.f21291c;
    }

    public int e() {
        return this.f21289a;
    }

    public int f() {
        return this.f21290b;
    }

    public ArrayList<Pair<String, Integer>> g() {
        return this.f21292d;
    }

    @Override // com.umeng.analytics.pro.ab
    public String a() {
        return super.a();
    }

    @Override // com.umeng.analytics.pro.ab
    public String b() {
        return super.b();
    }

    @Override // com.umeng.analytics.pro.ab
    public void a(String str) {
        super.a(str);
    }

    @Override // com.umeng.analytics.pro.ab
    public void b(String str) {
        super.b(str);
    }

    private void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                int i2 = this.f21289a;
                int i3 = 0;
                if (i2 != 0) {
                    if (i2 == 1) {
                        int randNumber = DeviceConfig.getRandNumber(0, this.f21291c - 1);
                        String str = (String) this.f21292d.get(randNumber).first;
                        int iIntValue = ((Integer) this.f21292d.get(randNumber).second).intValue();
                        jSONObject.put("target", str);
                        jSONObject.put(com.umeng.ccg.a.B, iIntValue);
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "selPoclicy: 1, currIndex: " + randNumber);
                        return;
                    }
                    return;
                }
                SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
                if (sharedPreferencesA != null) {
                    int i4 = sharedPreferencesA.getInt(au.f21360h, 0);
                    if (i4 < this.f21291c) {
                        String str2 = (String) this.f21292d.get(i4).first;
                        int iIntValue2 = ((Integer) this.f21292d.get(i4).second).intValue();
                        jSONObject.put("target", str2);
                        jSONObject.put(com.umeng.ccg.a.B, iIntValue2);
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "selPoclicy: 0, currIndex: " + i4);
                        if (i4 < this.f21291c - 1) {
                            i3 = i4 + 1;
                        }
                    } else {
                        String str3 = (String) this.f21292d.get(0).first;
                        int iIntValue3 = ((Integer) this.f21292d.get(0).second).intValue();
                        jSONObject.put("target", str3);
                        jSONObject.put(com.umeng.ccg.a.B, iIntValue3);
                    }
                    sharedPreferencesA.edit().putInt(au.f21360h, i3).commit();
                }
            } catch (Throwable unused) {
            }
        }
    }

    @Override // com.umeng.analytics.pro.ab, com.umeng.analytics.pro.aj
    public void b(String str, JSONObject jSONObject) {
        super.b(str, jSONObject);
        if (jSONObject.has(com.umeng.ccg.a.f22023y)) {
            a(jSONObject.optInt(com.umeng.ccg.a.f22023y));
        }
        if (jSONObject.has(com.umeng.ccg.a.f22024z)) {
            b(jSONObject.optInt(com.umeng.ccg.a.f22024z));
        }
        if (jSONObject.has(com.umeng.ccg.a.f22022x)) {
            c(jSONObject.optString(com.umeng.ccg.a.f22022x));
        }
    }

    @Override // com.umeng.analytics.pro.ab
    public String c() {
        return super.c();
    }

    @Override // com.umeng.analytics.pro.ab, com.umeng.analytics.pro.aj
    public JSONObject a(String str, JSONObject jSONObject) {
        JSONObject jSONObjectA = super.a(str, jSONObject);
        if (this.f21291c == 0) {
            return null;
        }
        if (jSONObject != null) {
            try {
                jSONObjectA.put(com.umeng.ccg.a.f22024z, this.f21290b);
                jSONObjectA.put(com.umeng.ccg.a.f22023y, this.f21289a);
                int iOptInt = jSONObject.optInt(com.umeng.ccg.a.f22008j);
                if (this.f21290b == 0 && iOptInt == 202) {
                    a(jSONObjectA);
                }
                if (this.f21290b == 1 && iOptInt == 304) {
                    a(jSONObjectA);
                }
            } catch (Throwable unused) {
            }
        }
        return jSONObjectA;
    }
}
