package com.umeng.ccg;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.ab;
import com.umeng.analytics.pro.ac;
import com.umeng.analytics.pro.ad;
import com.umeng.analytics.pro.ae;
import com.umeng.analytics.pro.af;
import com.umeng.analytics.pro.ag;
import com.umeng.analytics.pro.ah;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.ak;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.ao;
import com.umeng.analytics.pro.aq;
import com.umeng.analytics.pro.as;
import com.umeng.analytics.pro.au;
import com.umeng.analytics.pro.av;
import com.umeng.analytics.pro.aw;
import com.umeng.analytics.pro.ax;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.bc;
import com.umeng.ccg.c;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback;
import com.umeng.commonsdk.utils.UMUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.android.agoo.common.AgooConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class d implements c.a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22054a = "iucc";

    /* renamed from: b, reason: collision with root package name */
    private static final String f22055b = ay.b().b(ay.C);

    /* renamed from: c, reason: collision with root package name */
    private static JSONObject f22056c = null;

    /* renamed from: d, reason: collision with root package name */
    private static final String[] f22057d = {com.umeng.ccg.a.f22004f, com.umeng.ccg.a.f22005g, com.umeng.ccg.a.f22006h};

    /* renamed from: e, reason: collision with root package name */
    private static final String[] f22058e = {com.umeng.ccg.a.f22004f, com.umeng.ccg.a.f22005g, com.umeng.ccg.a.f22006h, com.umeng.ccg.a.f22007i};

    /* renamed from: f, reason: collision with root package name */
    private static ArrayList<ac> f22059f = null;

    /* renamed from: g, reason: collision with root package name */
    private static ArrayList<ac> f22060g = null;

    /* renamed from: h, reason: collision with root package name */
    private static ArrayList<ac> f22061h = null;

    /* renamed from: i, reason: collision with root package name */
    private static ArrayList<ac> f22062i = null;

    /* renamed from: j, reason: collision with root package name */
    private static ab f22063j = null;

    /* renamed from: m, reason: collision with root package name */
    private static e f22064m = new e();

    /* renamed from: k, reason: collision with root package name */
    private volatile String f22065k = "";

    /* renamed from: l, reason: collision with root package name */
    private Map<String, b> f22066l = new HashMap();

    public static class a extends BroadcastReceiver {
        public long a(ArrayList<ac> arrayList) {
            if (arrayList != null && arrayList.size() > 0) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    ac acVar = arrayList.get(i2);
                    if (acVar instanceof af) {
                        return ((af) acVar).c();
                    }
                }
            }
            return 0L;
        }

        public boolean b(ArrayList<ac> arrayList) {
            if (arrayList == null || arrayList.size() <= 0) {
                return false;
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (arrayList.get(i2).b()) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (TextUtils.isEmpty(action)) {
                    return;
                }
                if (action.equals("android.intent.action.SCREEN_ON") && d.f22063j != null && (d.f22063j instanceof ae)) {
                    if (b(d.f22062i)) {
                        com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 304, d.a(), null, a(d.f22062i) * 1000);
                    } else {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "don't send INVOKE_APPACT_WHEN_SCREEN_ON msg.");
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    public class b {

        /* renamed from: b, reason: collision with root package name */
        private JSONArray f22072b;

        /* renamed from: c, reason: collision with root package name */
        private String f22073c;

        public b(JSONArray jSONArray, String str) {
            this.f22072b = jSONArray;
            this.f22073c = str;
        }

        public JSONArray a() {
            return this.f22072b;
        }

        public String b() {
            return this.f22073c;
        }
    }

    private class c {

        /* renamed from: a, reason: collision with root package name */
        public String f22074a;

        /* renamed from: b, reason: collision with root package name */
        public int f22075b;

        /* renamed from: c, reason: collision with root package name */
        public int f22076c;

        public c(String str, int i2, int i3) {
            this.f22074a = str;
            this.f22075b = i2;
            this.f22076c = i3;
        }
    }

    /* renamed from: com.umeng.ccg.d$d, reason: collision with other inner class name */
    private static class C0176d {

        /* renamed from: a, reason: collision with root package name */
        private static final d f22078a = new d();

        private C0176d() {
        }
    }

    public static class e extends BroadcastReceiver {
        public long a(ArrayList<ac> arrayList) {
            if (arrayList != null && arrayList.size() > 0) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    ac acVar = arrayList.get(i2);
                    if (acVar instanceof af) {
                        return ((af) acVar).c();
                    }
                }
            }
            return 0L;
        }

        public boolean b(ArrayList<ac> arrayList) {
            if (arrayList == null || arrayList.size() <= 0) {
                return false;
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (arrayList.get(i2).b()) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (TextUtils.isEmpty(action)) {
                    return;
                }
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "recv intent : ACTION_SCREEN_ON");
                    if (b(d.f22059f)) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "report screen_on event.");
                        com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 301, d.a(), null, a(d.f22059f) * 1000);
                    } else {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "don't report screen_on event.");
                    }
                }
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "recv intent : ACTION_SCREEN_OFF");
                    if (b(d.f22060g)) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "report screen_off event.");
                        com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 302, d.a(), null, a(d.f22060g) * 1000);
                    } else {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "don't report screen_off event.");
                    }
                }
                if (action.equals("android.intent.action.USER_PRESENT")) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "recv intent : ACTION_USER_PRESENT");
                    if (!b(d.f22061h)) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "don't report screen_unlock event.");
                        return;
                    }
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "report screen_unlock event.");
                    com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 303, d.a(), null, a(d.f22061h) * 1000);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public static void a(Context context, String str, BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(str);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private boolean g() {
        SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
        if (sharedPreferencesA != null) {
            String string = sharedPreferencesA.getString(au.f21358f, "");
            if (TextUtils.isEmpty(string)) {
                h();
                return false;
            }
            try {
                if (!as.a().keySet().equals(as.a(new JSONObject(string)).keySet())) {
                    return true;
                }
            } catch (Throwable unused) {
            }
        }
        return false;
    }

    private void h() {
        try {
            SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
            if (sharedPreferencesA != null) {
                sharedPreferencesA.edit().putString(au.f21358f, new JSONObject(as.a()).toString()).commit();
            }
        } catch (Throwable unused) {
        }
    }

    private boolean i() {
        try {
            SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
            if (sharedPreferencesA != null) {
                return !TextUtils.isEmpty(sharedPreferencesA.getString(au.f21359g, ""));
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    private JSONObject j() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ax.b(UMUtils.genUmc(), byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            ax.a(byteArray, UMUtils.genSin());
            String str = new String(byteArray);
            byteArrayOutputStream.reset();
            return new JSONObject(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    private long b(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.has("ts")) {
            try {
                return jSONObject.optLong("ts");
            } catch (Throwable unused) {
            }
        }
        return 0L;
    }

    private void c(Context context) {
        ImprintHandler.getImprintService(context).registImprintCallback(f22054a, new UMImprintChangeCallback() { // from class: com.umeng.ccg.d.1
            @Override // com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback
            public void onImprintValueChanged(String str, String str2) {
                com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 107, d.a(), str2);
            }
        });
    }

    private Long d(Context context) {
        long j2 = 0L;
        try {
            SharedPreferences sharedPreferencesA = au.a(context);
            return sharedPreferencesA != null ? Long.valueOf(sharedPreferencesA.getLong(au.f21356d, 0L)) : j2;
        } catch (Throwable unused) {
            return j2;
        }
    }

    private String e(Context context) {
        try {
            SharedPreferences sharedPreferencesA = au.a(context);
            return sharedPreferencesA != null ? sharedPreferencesA.getString(au.f21357e, "") : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private void c(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has(com.umeng.ccg.a.f21999a)) {
            return;
        }
        try {
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(com.umeng.ccg.a.f21999a);
            ab abVarA = null;
            ab abVarA2 = jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22000b) ? a(com.umeng.ccg.a.f22000b, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22000b)) : null;
            ab abVarA3 = jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22001c) ? a(com.umeng.ccg.a.f22001c, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22001c)) : null;
            ab abVarA4 = jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22002d) ? a(com.umeng.ccg.a.f22002d, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22002d)) : null;
            ab abVarA5 = jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22003e) ? a(com.umeng.ccg.a.f22003e, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22003e)) : null;
            ab abVarA6 = jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22004f) ? a(com.umeng.ccg.a.f22004f, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22004f)) : null;
            ab abVarA7 = jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22005g) ? a(com.umeng.ccg.a.f22005g, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22005g)) : null;
            ab abVarA8 = jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22006h) ? a(com.umeng.ccg.a.f22006h, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22006h)) : null;
            if (jSONObjectOptJSONObject.has(com.umeng.ccg.a.f22007i)) {
                abVarA = a(com.umeng.ccg.a.f22007i, jSONObjectOptJSONObject.optJSONObject(com.umeng.ccg.a.f22007i));
                f22063j = abVarA;
            }
            ArrayList arrayList = new ArrayList();
            if (abVarA2 != null) {
                arrayList.add(abVarA2);
            }
            if (abVarA3 != null) {
                arrayList.add(abVarA3);
            }
            if (abVarA4 != null) {
                arrayList.add(abVarA4);
            }
            if (abVarA5 != null) {
                arrayList.add(abVarA5);
            }
            if (abVarA6 != null) {
                arrayList.add(abVarA6);
            }
            if (abVarA7 != null) {
                arrayList.add(abVarA7);
            }
            if (abVarA8 != null) {
                arrayList.add(abVarA8);
            }
            if (abVarA != null) {
                arrayList.add(abVarA);
            }
            com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 202, a(), arrayList);
        } catch (Throwable unused) {
        }
    }

    public static d a() {
        return C0176d.f22078a;
    }

    synchronized JSONObject b(Context context) {
        File filesDir;
        String str;
        FileInputStream fileInputStreamOpenFileInput;
        JSONObject jSONObject = null;
        try {
            filesDir = context.getFilesDir();
            str = f22055b;
        } catch (Throwable unused) {
        }
        if (!new File(filesDir, str).exists()) {
            return null;
        }
        try {
            fileInputStreamOpenFileInput = context.openFileInput(str);
            try {
                JSONObject jSONObject2 = new JSONObject(new String(ax.a(HelperUtils.readStreamToByteArray(fileInputStreamOpenFileInput), UMConfigure.sAppkey.getBytes())));
                try {
                    as.a(fileInputStreamOpenFileInput);
                } catch (Throwable unused2) {
                }
                jSONObject = jSONObject2;
            } catch (Throwable unused3) {
                as.a(fileInputStreamOpenFileInput);
                return jSONObject;
            }
        } catch (Throwable unused4) {
            fileInputStreamOpenFileInput = null;
        }
        return jSONObject;
    }

    public void a(Context context) {
        com.umeng.ccg.c.a(context, 105, a(), null);
    }

    private boolean a(JSONObject jSONObject) {
        if (jSONObject == null || !jSONObject.has("code")) {
            return false;
        }
        try {
            if (200 == jSONObject.optInt("code") && jSONObject.has(com.umeng.ccg.a.f21999a)) {
                return jSONObject.has("ts");
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    private synchronized void a(Context context, JSONObject jSONObject, String str) {
        long jB;
        byte[] bArrA;
        try {
            jB = b(jSONObject);
            bArrA = ax.a(jSONObject.toString().getBytes(), UMConfigure.sAppkey.getBytes());
        } catch (Throwable unused) {
        }
        if (bArrA != null && bArrA.length > 1) {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(context.getFilesDir(), f22055b));
            try {
                fileOutputStream.write(bArrA);
                fileOutputStream.flush();
                as.a(fileOutputStream);
                a(context, str, jB);
                UMRTLog.i(UMRTLog.RTLOG_TAG, "saveConfigFile success.");
            } catch (Throwable th) {
                as.a(fileOutputStream);
                throw th;
            }
        }
    }

    private void b(String str) {
        String str2 = au.f21354b + str;
        SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
        if (sharedPreferencesA != null) {
            sharedPreferencesA.edit().putLong(str2, System.currentTimeMillis()).commit();
        }
    }

    private void a(String str, ac acVar) {
        if (com.umeng.ccg.a.f22004f.equalsIgnoreCase(str)) {
            if (f22059f == null) {
                f22059f = new ArrayList<>();
            }
            f22059f.add(acVar);
        }
        if (com.umeng.ccg.a.f22005g.equalsIgnoreCase(str)) {
            if (f22060g == null) {
                f22060g = new ArrayList<>();
            }
            f22060g.add(acVar);
        }
        if (com.umeng.ccg.a.f22006h.equalsIgnoreCase(str)) {
            if (f22061h == null) {
                f22061h = new ArrayList<>();
            }
            f22061h.add(acVar);
        }
        if (com.umeng.ccg.a.f22007i.equalsIgnoreCase(str)) {
            if (f22062i == null) {
                f22062i = new ArrayList<>();
            }
            f22062i.add(acVar);
        }
    }

    private ab a(String str, JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray;
        String str2;
        ab abVar;
        JSONArray jSONArrayOptJSONArray2;
        if (jSONObject != null) {
            try {
                if (jSONObject.has(com.umeng.ccg.a.f22010l) && (jSONArrayOptJSONArray = jSONObject.optJSONArray(com.umeng.ccg.a.f22010l)) != null && jSONArrayOptJSONArray.length() > 0) {
                    JSONObject jSONObject2 = (JSONObject) jSONArrayOptJSONArray.get(0);
                    boolean zHas = jSONObject2.has(com.umeng.ccg.a.f22011m);
                    boolean zHas2 = jSONObject2.has(com.umeng.ccg.a.f22014p);
                    boolean zHas3 = jSONObject2.has(com.umeng.ccg.a.f22015q);
                    if (!zHas || !zHas2 || !zHas3) {
                        return null;
                    }
                    try {
                        int iOptInt = jSONObject2.optInt(com.umeng.ccg.a.f22011m);
                        long jOptLong = jSONObject2.optLong(com.umeng.ccg.a.f22014p);
                        long jOptLong2 = jSONObject2.optLong(com.umeng.ccg.a.f22015q);
                        String strOptString = jSONObject2.optString(com.umeng.ccg.a.f22016r);
                        ArrayList arrayList = new ArrayList();
                        if (!jSONObject2.has(com.umeng.ccg.a.f22012n)) {
                            str2 = "sdk";
                        } else {
                            JSONArray jSONArrayOptJSONArray3 = jSONObject2.optJSONArray(com.umeng.ccg.a.f22012n);
                            HashSet hashSet = new HashSet();
                            if (jSONArrayOptJSONArray3 == null) {
                                str2 = "sdk";
                            } else {
                                str2 = "sdk";
                                int i2 = 0;
                                for (int length = jSONArrayOptJSONArray3.length(); i2 < length; length = length) {
                                    hashSet.add(Integer.valueOf(jSONArrayOptJSONArray3.getInt(i2)));
                                    i2++;
                                }
                            }
                            if (hashSet.size() > 0) {
                                am amVar = new am(hashSet);
                                if (Arrays.asList(f22057d).contains(str)) {
                                    a(str, amVar);
                                } else {
                                    arrayList.add(amVar);
                                    if (com.umeng.ccg.a.f22007i.equalsIgnoreCase(str)) {
                                        a(str, amVar);
                                    }
                                }
                            }
                        }
                        if (jSONObject2.has(com.umeng.ccg.a.f22013o)) {
                            String strOptString2 = jSONObject2.optString(com.umeng.ccg.a.f22013o);
                            if (!TextUtils.isEmpty(strOptString2)) {
                                ak akVar = new ak(strOptString2);
                                HashSet hashSet2 = new HashSet();
                                for (int i3 = 1; i3 <= 24; i3++) {
                                    if (akVar.a(i3)) {
                                        hashSet2.add(Integer.valueOf(i3));
                                    }
                                }
                                if (hashSet2.size() > 0) {
                                    ag agVar = new ag(hashSet2);
                                    if (Arrays.asList(f22057d).contains(str)) {
                                        a(str, agVar);
                                    } else {
                                        arrayList.add(agVar);
                                    }
                                    if (com.umeng.ccg.a.f22007i.equalsIgnoreCase(str)) {
                                        a(str, agVar);
                                    }
                                }
                            }
                        }
                        arrayList.add(new ai(iOptInt));
                        ah ahVar = new ah(str, jOptLong);
                        String[] strArr = f22057d;
                        if (Arrays.asList(strArr).contains(str)) {
                            a(str, ahVar);
                        } else {
                            arrayList.add(ahVar);
                        }
                        if (com.umeng.ccg.a.f22007i.equalsIgnoreCase(str)) {
                            a(str, ahVar);
                        }
                        af afVar = new af(jOptLong2);
                        if (Arrays.asList(strArr).contains(str)) {
                            a(str, afVar);
                            arrayList.add(afVar);
                        } else {
                            arrayList.add(afVar);
                        }
                        if (com.umeng.ccg.a.f22007i.equalsIgnoreCase(str)) {
                            a(str, afVar);
                        }
                        if (com.umeng.ccg.a.f22003e.equals(str)) {
                            abVar = new ad(str, arrayList);
                        } else if (com.umeng.ccg.a.f22007i.equals(str)) {
                            abVar = new ae(str, arrayList);
                        } else {
                            abVar = new ab(str, arrayList);
                        }
                        ab abVar2 = abVar;
                        try {
                            abVar2.b(str, jSONObject2);
                            abVar2.a(strOptString);
                            String str3 = "";
                            String str4 = str2;
                            if (jSONObject.has(str4) && (jSONArrayOptJSONArray2 = jSONObject.optJSONArray(str4)) != null) {
                                Map<String, b> map = this.f22066l;
                                if (map != null && !map.containsKey(str)) {
                                    this.f22066l.put(str, new b(new JSONArray(jSONArrayOptJSONArray2.toString()), strOptString));
                                }
                                int length2 = jSONArrayOptJSONArray2.length();
                                for (int i4 = 0; i4 < jSONArrayOptJSONArray2.length(); i4++) {
                                    str3 = str3 + jSONArrayOptJSONArray2.getString(i4);
                                    if (i4 < length2 - 1) {
                                        str3 = str3 + ",";
                                    }
                                }
                            }
                            abVar2.b(str3);
                            return abVar2;
                        } catch (Throwable unused) {
                            return abVar2;
                        }
                    } catch (Throwable unused2) {
                        return null;
                    }
                }
            } catch (Throwable unused3) {
            }
        }
        return null;
    }

    private void a(Context context, String str, long j2) {
        SharedPreferences sharedPreferencesA;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            String[] strArrSplit = str.split("@");
            if (strArrSplit.length != 4 || (sharedPreferencesA = au.a(context)) == null) {
                return;
            }
            long j3 = Long.parseLong(strArrSplit[0]);
            String str2 = strArrSplit[1];
            SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
            editorEdit.putLong(au.f21355c, j2);
            editorEdit.putLong(au.f21356d, j3);
            editorEdit.putString(au.f21357e, str2).commit();
            UMRTLog.i(UMRTLog.RTLOG_TAG, "updateTsS1S2 : ts = " + j2 + "; s1 = " + j3 + "; s2 = " + str2);
        } catch (Throwable unused) {
        }
    }

    private void a(String str) {
        try {
            String[] strArrSplit = str.split("@");
            if (strArrSplit.length != 4) {
                return;
            }
            long j2 = Long.parseLong(strArrSplit[0]);
            String str2 = strArrSplit[1];
            if (!TextUtils.isEmpty(this.f22065k)) {
                String[] strArrSplit2 = this.f22065k.split("@");
                if (strArrSplit2.length == 2) {
                    long j3 = Long.parseLong(strArrSplit2[0]);
                    String str3 = strArrSplit2[1];
                    if (j3 == j2 && str3.equalsIgnoreCase(str2)) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "重复的iucc S1 and S2, 忽略本次更新，不发起fetch。");
                        return;
                    }
                }
            }
            SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
            if (sharedPreferencesA != null) {
                if (sharedPreferencesA.getLong(au.f21355c, 0L) != j2) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "local config ts != iuccS1, send FETCH_NEW_CONFIG msg.");
                    this.f22065k = String.valueOf(j2) + "@" + str2;
                    com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 101, a(), str);
                    return;
                }
                d(UMGlobalContext.getAppContext());
                if (e(UMGlobalContext.getAppContext()).equalsIgnoreCase(str2)) {
                    return;
                }
                UMRTLog.i(UMRTLog.RTLOG_TAG, "local S2 != iuccS2, send FETCH_NEW_CONFIG msg.");
                this.f22065k = String.valueOf(j2) + "@" + str2;
                com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 101, a(), str);
            }
        } catch (Throwable unused) {
        }
    }

    private void a(boolean z2) {
        try {
            SharedPreferences sharedPreferencesA = au.a(UMGlobalContext.getAppContext());
            if (sharedPreferencesA != null) {
                SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
                if (z2) {
                    editorEdit.putString(au.f21359g, "1").commit();
                } else {
                    editorEdit.putString(au.f21359g, "").commit();
                }
            }
        } catch (Throwable unused) {
        }
    }

    private JSONObject a(String str, int i2, int i3) {
        b bVar;
        JSONObject jSONObject = new JSONObject();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            jSONObject.put("id", "$$_umc_ev1");
            jSONObject.put("ts", jCurrentTimeMillis);
            jSONObject.put("tt", str);
            jSONObject.put(com.umeng.ccg.a.B, i2);
            jSONObject.put("result", i3);
            if (!this.f22066l.containsKey(com.umeng.ccg.a.f22007i) || (bVar = this.f22066l.get(com.umeng.ccg.a.f22007i)) == null) {
                return null;
            }
            JSONObject jSONObjectA = ao.a(UMGlobalContext.getAppContext(), bVar.a(), bVar.b());
            JSONObject jSONObjectA2 = ao.a(UMGlobalContext.getAppContext(), jSONObject);
            if (jSONObjectA == null || jSONObjectA2 == null) {
                return null;
            }
            return ao.a(jSONObjectA, jSONObjectA2);
        } catch (Throwable unused) {
            return null;
        }
    }

    JSONObject a(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            jSONObject.put("id", "$$_umc_ev2");
            jSONObject.put("ts", jCurrentTimeMillis);
            jSONObject.put("tt", str);
            jSONObject.put(AgooConstants.MESSAGE_FLAG, str2);
            jSONObject.put("ss", str3);
            JSONObject jSONObjectA = ao.a(UMGlobalContext.getAppContext(), new JSONArray(), "");
            JSONObject jSONObjectA2 = ao.a(UMGlobalContext.getAppContext(), jSONObject);
            if (jSONObjectA == null || jSONObjectA2 == null) {
                return null;
            }
            return ao.a(jSONObjectA, jSONObjectA2);
        } catch (Throwable unused) {
            return null;
        }
    }

    @SuppressLint({"MissingPermission"})
    private void a(Context context, String str, JSONObject jSONObject) {
        Object objA;
        try {
            b(com.umeng.ccg.a.f22007i);
            final String strOptString = "";
            if (jSONObject.has("target")) {
                strOptString = jSONObject.optString("target");
            }
            if (TextUtils.isEmpty(strOptString)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> target is empty, ignore umc_cfg process");
                return;
            }
            final int iOptInt = jSONObject.has(com.umeng.ccg.a.B) ? jSONObject.optInt(com.umeng.ccg.a.B) : 0;
            if (iOptInt == 0) {
                JSONObject jSONObjectJ = j();
                if (jSONObjectJ == null || (objA = aw.a(jSONObjectJ.optString(bc.aL), jSONObjectJ.optString("s"), new Class[]{String.class}, context, new Object[]{jSONObjectJ.optString("a")})) == null) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("ss", Base64.encodeToString(ax.a(DeviceConfig.getPackageName(context).getBytes(), UMUtils.genSin()), 0).trim());
                aw.a(jSONObjectJ.optString("m"), jSONObjectJ.optString("x"), new Class[]{String.class, String.class, String[].class, Bundle.class, Activity.class, aw.a(jSONObjectJ.optString(bc.aJ)), Handler.class}, objA, new Object[]{strOptString, com.umeng.ccg.a.f22009k, null, bundle, null, new AccountManagerCallback<Bundle>() { // from class: com.umeng.ccg.d.2
                    @Override // android.accounts.AccountManagerCallback
                    public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                        int i2 = 1;
                        try {
                            accountManagerFuture.getResult();
                            i2 = 0;
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> umc_cfg p s!");
                        } catch (Throwable unused) {
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> umc_cfg p f!");
                        }
                        com.umeng.ccg.c.a(UMGlobalContext.getAppContext(), 305, d.a(), d.this.new c(strOptString, iOptInt, i2));
                    }
                }, null});
                return;
            }
            JSONObject jSONObjectA = a(strOptString, iOptInt, 0);
            if (jSONObjectA != null) {
                av.a(new aq(aq.f21335b, jSONObjectA), 0L, TimeUnit.SECONDS);
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03b5 A[Catch: all -> 0x0578, TryCatch #2 {all -> 0x0578, blocks: (B:9:0x002e, B:11:0x003c, B:17:0x005b, B:19:0x0071, B:20:0x007a, B:23:0x0086, B:25:0x0091, B:27:0x009b, B:30:0x00d5, B:32:0x00e0, B:34:0x00ea, B:37:0x0124, B:39:0x012f, B:41:0x0139, B:43:0x016f, B:45:0x0173, B:47:0x0195, B:49:0x01af, B:51:0x01bf, B:54:0x01c7, B:55:0x01d5, B:57:0x01db, B:58:0x01eb, B:60:0x01f1, B:61:0x0201, B:63:0x0207, B:65:0x020d, B:67:0x0213, B:69:0x021e, B:70:0x022c, B:72:0x0235, B:75:0x023e, B:77:0x024a, B:79:0x0250, B:82:0x0294, B:80:0x0279, B:83:0x029d, B:84:0x02a6, B:86:0x02ad, B:88:0x02b1, B:91:0x02bb, B:93:0x02cb, B:94:0x02d5, B:96:0x02db, B:99:0x02ef, B:101:0x02f8, B:103:0x0302, B:105:0x0308, B:106:0x030e, B:107:0x033a, B:108:0x0340, B:110:0x0347, B:112:0x034b, B:124:0x0394, B:126:0x039c, B:128:0x03a3, B:130:0x03a9, B:132:0x03b1, B:133:0x03b5, B:134:0x03b8, B:136:0x03be, B:137:0x03dd, B:164:0x0498, B:166:0x04a6, B:168:0x04ac, B:169:0x04b7, B:175:0x04ce, B:177:0x04da, B:178:0x04e9, B:186:0x0528, B:187:0x054e, B:189:0x0554), top: B:196:0x0015 }] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11, types: [int] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v14, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r3v15, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r3v17, types: [org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r3v42 */
    /* JADX WARN: Type inference failed for: r3v43 */
    /* JADX WARN: Type inference failed for: r3v44 */
    @Override // com.umeng.ccg.c.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(java.lang.Object r21, int r22) {
        /*
            Method dump skipped, instructions count: 1444
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.ccg.d.a(java.lang.Object, int):void");
    }
}
