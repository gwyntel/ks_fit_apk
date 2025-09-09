package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile b f23396a;

    /* renamed from: a, reason: collision with other field name */
    private Context f137a;

    /* renamed from: a, reason: collision with other field name */
    private a f138a;

    /* renamed from: a, reason: collision with other field name */
    String f139a;

    /* renamed from: a, reason: collision with other field name */
    private Map<String, a> f140a;

    private b(Context context) {
        this.f137a = context;
        c();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static b m140a(Context context) {
        if (f23396a == null) {
            synchronized (b.class) {
                try {
                    if (f23396a == null) {
                        f23396a = new b(context);
                    }
                } finally {
                }
            }
        }
        return f23396a;
    }

    private void c() {
        this.f138a = new a(this.f137a);
        this.f140a = new HashMap();
        SharedPreferences sharedPreferencesA = a(this.f137a);
        this.f138a.f142a = sharedPreferencesA.getString("appId", null);
        this.f138a.f23398b = sharedPreferencesA.getString("appToken", null);
        this.f138a.f23399c = sharedPreferencesA.getString("regId", null);
        this.f138a.f23400d = sharedPreferencesA.getString("regSec", null);
        this.f138a.f23402f = sharedPreferencesA.getString("devId", null);
        if (!TextUtils.isEmpty(this.f138a.f23402f) && com.xiaomi.push.i.a(this.f138a.f23402f)) {
            this.f138a.f23402f = com.xiaomi.push.i.h(this.f137a);
            sharedPreferencesA.edit().putString("devId", this.f138a.f23402f).commit();
        }
        this.f138a.f23401e = sharedPreferencesA.getString("vName", null);
        this.f138a.f143a = sharedPreferencesA.getBoolean("valid", true);
        this.f138a.f144b = sharedPreferencesA.getBoolean("paused", false);
        this.f138a.f23397a = sharedPreferencesA.getInt("envType", 1);
        this.f138a.f23403g = sharedPreferencesA.getString("regResource", null);
        this.f138a.f23404h = sharedPreferencesA.getString("appRegion", null);
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m147b() {
        if (this.f138a.m154a()) {
            return true;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("Don't send message before initialization succeeded!");
        return false;
    }

    public String d() {
        return this.f138a.f23400d;
    }

    public String e() {
        return this.f138a.f23403g;
    }

    public String f() {
        return this.f138a.f23404h;
    }

    /* renamed from: d, reason: collision with other method in class */
    public boolean m150d() {
        return (TextUtils.isEmpty(this.f138a.f142a) || TextUtils.isEmpty(this.f138a.f23398b) || TextUtils.isEmpty(this.f138a.f23399c) || TextUtils.isEmpty(this.f138a.f23400d)) ? false : true;
    }

    /* renamed from: e, reason: collision with other method in class */
    public boolean m151e() {
        return this.f138a.f144b;
    }

    /* renamed from: f, reason: collision with other method in class */
    public boolean m152f() {
        return !this.f138a.f143a;
    }

    public String b() {
        return this.f138a.f23398b;
    }

    public void b(String str, String str2, String str3) {
        this.f138a.b(str, str2, str3);
    }

    public void b(String str) {
        this.f140a.remove(str);
        a(this.f137a).edit().remove("hybrid_app_info_" + str).commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m144a() {
        Context context = this.f137a;
        return !TextUtils.equals(com.xiaomi.push.g.m422a(context, context.getPackageName()), this.f138a.f23401e);
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m146b() {
        this.f138a.b();
    }

    public static class a {

        /* renamed from: a, reason: collision with other field name */
        private Context f141a;

        /* renamed from: a, reason: collision with other field name */
        public String f142a;

        /* renamed from: b, reason: collision with root package name */
        public String f23398b;

        /* renamed from: c, reason: collision with root package name */
        public String f23399c;

        /* renamed from: d, reason: collision with root package name */
        public String f23400d;

        /* renamed from: e, reason: collision with root package name */
        public String f23401e;

        /* renamed from: f, reason: collision with root package name */
        public String f23402f;

        /* renamed from: g, reason: collision with root package name */
        public String f23403g;

        /* renamed from: h, reason: collision with root package name */
        public String f23404h;

        /* renamed from: a, reason: collision with other field name */
        public boolean f143a = true;

        /* renamed from: b, reason: collision with other field name */
        public boolean f144b = false;

        /* renamed from: a, reason: collision with root package name */
        public int f23397a = 1;

        public a(Context context) {
            this.f141a = context;
        }

        public void a(String str, String str2, String str3) {
            this.f142a = str;
            this.f23398b = str2;
            this.f23403g = str3;
            SharedPreferences.Editor editorEdit = b.a(this.f141a).edit();
            editorEdit.putString("appId", this.f142a);
            editorEdit.putString("appToken", str2);
            editorEdit.putString("regResource", str3);
            editorEdit.commit();
        }

        public void b(String str, String str2, String str3) {
            this.f23399c = str;
            this.f23400d = str2;
            this.f23402f = com.xiaomi.push.i.h(this.f141a);
            this.f23401e = a();
            this.f143a = true;
            this.f23404h = str3;
            SharedPreferences.Editor editorEdit = b.a(this.f141a).edit();
            editorEdit.putString("regId", str);
            editorEdit.putString("regSec", str2);
            editorEdit.putString("devId", this.f23402f);
            editorEdit.putString("vName", a());
            editorEdit.putBoolean("valid", true);
            editorEdit.putString("appRegion", str3);
            editorEdit.commit();
        }

        public void c(String str, String str2, String str3) {
            this.f142a = str;
            this.f23398b = str2;
            this.f23403g = str3;
        }

        public void a(String str, String str2) {
            this.f23399c = str;
            this.f23400d = str2;
            this.f23402f = com.xiaomi.push.i.h(this.f141a);
            this.f23401e = a();
            this.f143a = true;
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m155a(String str, String str2) {
            boolean zEquals = TextUtils.equals(this.f142a, str);
            boolean zEquals2 = TextUtils.equals(this.f23398b, str2);
            boolean z2 = !TextUtils.isEmpty(this.f23399c);
            boolean z3 = !TextUtils.isEmpty(this.f23400d);
            boolean z4 = TextUtils.isEmpty(com.xiaomi.push.i.b(this.f141a)) || TextUtils.equals(this.f23402f, com.xiaomi.push.i.h(this.f141a)) || TextUtils.equals(this.f23402f, com.xiaomi.push.i.g(this.f141a));
            boolean z5 = zEquals && zEquals2 && z2 && z3 && z4;
            if (!z5) {
                com.xiaomi.channel.commonutils.logger.b.e(String.format("register invalid, aid=%s;atn=%s;rid=%s;rse=%s;did=%s", Boolean.valueOf(zEquals), Boolean.valueOf(zEquals2), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4)));
            }
            return z5;
        }

        public void b() {
            this.f143a = false;
            b.a(this.f141a).edit().putBoolean("valid", this.f143a).commit();
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m154a() {
            return m155a(this.f142a, this.f23398b);
        }

        private String a() {
            Context context = this.f141a;
            return com.xiaomi.push.g.m422a(context, context.getPackageName());
        }

        /* renamed from: a, reason: collision with other method in class */
        public void m153a() {
            b.a(this.f141a).edit().clear().commit();
            this.f142a = null;
            this.f23398b = null;
            this.f23399c = null;
            this.f23400d = null;
            this.f23402f = null;
            this.f23401e = null;
            this.f143a = false;
            this.f144b = false;
            this.f23404h = null;
            this.f23397a = 1;
        }

        public void a(boolean z2) {
            this.f144b = z2;
        }

        public void a(int i2) {
            this.f23397a = i2;
        }

        public static a a(Context context, String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                a aVar = new a(context);
                aVar.f142a = jSONObject.getString("appId");
                aVar.f23398b = jSONObject.getString("appToken");
                aVar.f23399c = jSONObject.getString("regId");
                aVar.f23400d = jSONObject.getString("regSec");
                aVar.f23402f = jSONObject.getString("devId");
                aVar.f23401e = jSONObject.getString("vName");
                aVar.f143a = jSONObject.getBoolean("valid");
                aVar.f144b = jSONObject.getBoolean("paused");
                aVar.f23397a = jSONObject.getInt("envType");
                aVar.f23403g = jSONObject.getString("regResource");
                return aVar;
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a(th);
                return null;
            }
        }

        public static String a(a aVar) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("appId", aVar.f142a);
                jSONObject.put("appToken", aVar.f23398b);
                jSONObject.put("regId", aVar.f23399c);
                jSONObject.put("regSec", aVar.f23400d);
                jSONObject.put("devId", aVar.f23402f);
                jSONObject.put("vName", aVar.f23401e);
                jSONObject.put("valid", aVar.f143a);
                jSONObject.put("paused", aVar.f144b);
                jSONObject.put("envType", aVar.f23397a);
                jSONObject.put("regResource", aVar.f23403g);
                return jSONObject.toString();
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a(th);
                return null;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m143a(String str) {
        SharedPreferences.Editor editorEdit = a(this.f137a).edit();
        editorEdit.putString("vName", str);
        editorEdit.commit();
        this.f138a.f23401e = str;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m141a() {
        return this.f138a.f142a;
    }

    public boolean a(String str, String str2) {
        return this.f138a.m155a(str, str2);
    }

    public void a(String str, String str2, String str3) {
        this.f138a.a(str, str2, str3);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m142a() {
        this.f138a.m153a();
    }

    public a a(String str) {
        if (this.f140a.containsKey(str)) {
            return this.f140a.get(str);
        }
        String str2 = "hybrid_app_info_" + str;
        SharedPreferences sharedPreferencesA = a(this.f137a);
        if (!sharedPreferencesA.contains(str2)) {
            return null;
        }
        a aVarA = a.a(this.f137a, sharedPreferencesA.getString(str2, ""));
        this.f140a.put(str2, aVarA);
        return aVarA;
    }

    /* renamed from: c, reason: collision with other method in class */
    public String m148c() {
        return this.f138a.f23399c;
    }

    /* renamed from: c, reason: collision with other method in class */
    public boolean m149c() {
        return this.f138a.m154a();
    }

    public void a(String str, a aVar) {
        this.f140a.put(str, aVar);
        a(this.f137a).edit().putString("hybrid_app_info_" + str, a.a(aVar)).commit();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m145a(String str, String str2, String str3) {
        a aVarA = a(str3);
        return aVarA != null && TextUtils.equals(str, aVarA.f142a) && TextUtils.equals(str2, aVarA.f23398b);
    }

    public static SharedPreferences a(Context context) {
        return context.getSharedPreferences("mipush", 0);
    }

    public int a() {
        return this.f138a.f23397a;
    }

    public void a(boolean z2) {
        this.f138a.a(z2);
        a(this.f137a).edit().putBoolean("paused", z2).commit();
    }

    public void a(int i2) {
        this.f138a.a(i2);
        a(this.f137a).edit().putInt("envType", i2).commit();
    }
}
