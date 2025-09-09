package com.umeng.message.proguard;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.api.UPushMessageNotifyApi;
import com.umeng.message.common.UPLog;
import com.umeng.message.common.inter.ITagManager;
import java.lang.ref.WeakReference;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class t {

    /* renamed from: a, reason: collision with root package name */
    private static final t f22911a = new t();

    /* renamed from: b, reason: collision with root package name */
    private WeakReference<Activity> f22912b;

    /* renamed from: g, reason: collision with root package name */
    private s f22917g;

    /* renamed from: c, reason: collision with root package name */
    private final Handler f22913c = new Handler(Looper.getMainLooper());

    /* renamed from: d, reason: collision with root package name */
    private boolean f22914d = true;

    /* renamed from: e, reason: collision with root package name */
    private boolean f22915e = false;

    /* renamed from: f, reason: collision with root package name */
    private boolean f22916f = false;

    /* renamed from: h, reason: collision with root package name */
    private final Runnable f22918h = new Runnable() { // from class: com.umeng.message.proguard.t.1
        @Override // java.lang.Runnable
        public final void run() {
            t tVar = t.this;
            tVar.f22915e = !(tVar.f22915e && t.this.f22914d) && t.this.f22915e;
        }
    };

    /* renamed from: i, reason: collision with root package name */
    private final Application.ActivityLifecycleCallbacks f22919i = new Application.ActivityLifecycleCallbacks() { // from class: com.umeng.message.proguard.t.2
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
            s unused = t.this.f22917g;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityPaused(Activity activity) {
            try {
                t.this.f22914d = true;
                t.this.f22913c.removeCallbacks(t.this.f22918h);
                t.this.f22913c.postDelayed(t.this.f22918h, 1000L);
            } catch (Throwable unused) {
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityResumed(Activity activity) {
            try {
                t.this.f22912b = new WeakReference(activity);
                t.this.f22914d = false;
                t.this.f22913c.removeCallbacks(t.this.f22918h);
                t.this.f22915e = true;
            } catch (Throwable unused) {
            }
            s unused2 = t.this.f22917g;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStarted(Activity activity) {
            if (t.this.f22917g != null) {
                try {
                    aj ajVarA = aj.a();
                    if (ajVarA.f22761b) {
                        final String name = activity.getClass().getName();
                        final ak akVar = ajVarA.f22760a;
                        if (akVar.f22763b.a()) {
                            if (!akVar.f22763b.f22774a.b("e_s", true)) {
                                if (Math.abs(System.currentTimeMillis() - akVar.f22763b.b()) <= 86400000) {
                                    return;
                                }
                            }
                            al alVar = akVar.f22763b;
                            if (Math.abs(System.currentTimeMillis() - alVar.b()) > Math.max(600L, Math.min(alVar.f22774a.b("req_interval", 1800L), 86400L)) * 1000) {
                                al alVar2 = akVar.f22763b;
                                alVar2.f22774a.a("req_ts", System.currentTimeMillis());
                                b.c(new Runnable() { // from class: com.umeng.message.proguard.ak.2

                                    /* renamed from: a */
                                    final /* synthetic */ String f22766a;

                                    public AnonymousClass2(final String name2) {
                                        str = name2;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        String str;
                                        String str2;
                                        JSONObject jSONObjectA;
                                        int length;
                                        String str3;
                                        String str4;
                                        String str5;
                                        String str6;
                                        String str7;
                                        String str8 = "trace_id";
                                        String str9 = "ts";
                                        String str10 = "appkey";
                                        String str11 = "Notify";
                                        try {
                                            if (d.h(x.a())) {
                                                ak akVar2 = ak.this;
                                                String str12 = str;
                                                Application applicationA = x.a();
                                                String zid = UMUtils.getZid(applicationA);
                                                try {
                                                    if (TextUtils.isEmpty(zid)) {
                                                        UPLog.d("Notify", "zid skip.");
                                                        return;
                                                    }
                                                    String registrationId = PushAgent.getInstance(applicationA).getRegistrationId();
                                                    if (TextUtils.isEmpty(registrationId)) {
                                                        UPLog.d("Notify", "deviceToken skip.");
                                                        return;
                                                    }
                                                    String messageAppkey = PushAgent.getInstance(applicationA).getMessageAppkey();
                                                    if (TextUtils.isEmpty(messageAppkey)) {
                                                        UPLog.d("Notify", "appkey skip.");
                                                        return;
                                                    }
                                                    String packageName = applicationA.getPackageName();
                                                    if (TextUtils.isEmpty(packageName)) {
                                                        UPLog.d("Notify", "pkgName skip.");
                                                        return;
                                                    }
                                                    JSONObject jSONObject = new JSONObject();
                                                    jSONObject.put(com.umeng.analytics.pro.bc.al, zid);
                                                    jSONObject.put("appkey", messageAppkey);
                                                    jSONObject.put("package_name", packageName);
                                                    jSONObject.put(com.umeng.analytics.pro.bc.F, d.f());
                                                    jSONObject.put("device_model", d.d());
                                                    jSONObject.put(com.umeng.analytics.pro.bc.f21402a, registrationId);
                                                    jSONObject.put(com.umeng.analytics.pro.bc.f21426y, Build.VERSION.RELEASE);
                                                    jSONObject.put("sdk_version", MsgConstant.SDK_VERSION);
                                                    jSONObject.put("app_version", d.b(applicationA));
                                                    jSONObject.put("version_code", d.a(applicationA));
                                                    jSONObject.put("ts", System.currentTimeMillis());
                                                    if (d.i()) {
                                                        jSONObject.put("harmony_ver", d.j());
                                                    }
                                                    try {
                                                        jSONObjectA = g.a(jSONObject, "https://offmsg.umeng.com/v2/offmsg/req", messageAppkey, false);
                                                    } catch (Exception e2) {
                                                        UPLog.d("Notify", "request fail:", e2.getMessage());
                                                        jSONObjectA = null;
                                                    }
                                                    if (jSONObjectA == null || jSONObjectA.optInt("code") == 13043) {
                                                        return;
                                                    }
                                                    JSONObject jSONObjectOptJSONObject = jSONObjectA.optJSONObject("data");
                                                    if (jSONObjectOptJSONObject == null) {
                                                        akVar2.f22763b.a(false);
                                                        return;
                                                    }
                                                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("config");
                                                    try {
                                                        if (jSONObjectOptJSONObject2 == null) {
                                                            akVar2.f22763b.a(false);
                                                            return;
                                                        }
                                                        int iOptInt = jSONObjectOptJSONObject2.optInt("ipad");
                                                        boolean z2 = jSONObjectOptJSONObject2.optInt("aps") == 1;
                                                        akVar2.f22763b.f22774a.a("req_interval", iOptInt);
                                                        akVar2.f22763b.a(z2);
                                                        String strOptString = jSONObjectOptJSONObject.optString("trace_id");
                                                        JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("action");
                                                        if (jSONArrayOptJSONArray == null || (length = jSONArrayOptJSONArray.length()) == 0) {
                                                            return;
                                                        }
                                                        int i2 = 5;
                                                        if (length > 5) {
                                                            int i3 = 5;
                                                            while (i3 < length) {
                                                                JSONObject jSONObjectOptJSONObject3 = jSONArrayOptJSONArray.optJSONObject(i3);
                                                                if (jSONObjectOptJSONObject3 != null) {
                                                                    str7 = str11;
                                                                    jSONObjectOptJSONObject3.put(ITagManager.SUCCESS, 0);
                                                                } else {
                                                                    str7 = str11;
                                                                }
                                                                i3++;
                                                                str11 = str7;
                                                                i2 = 5;
                                                            }
                                                        }
                                                        int iMin = Math.min(i2, length);
                                                        int i4 = 0;
                                                        int i5 = 0;
                                                        while (i4 < iMin) {
                                                            ak akVar3 = akVar2;
                                                            JSONObject jSONObjectOptJSONObject4 = jSONArrayOptJSONArray.optJSONObject(i4);
                                                            if (jSONObjectOptJSONObject4 != null) {
                                                                str5 = str9;
                                                                String strOptString2 = jSONObjectOptJSONObject4.optString("pkg");
                                                                String strOptString3 = jSONObjectOptJSONObject4.optString(PushConstants.INTENT_ACTIVITY_NAME);
                                                                String strOptString4 = jSONObjectOptJSONObject4.optString(str10);
                                                                if (TextUtils.isEmpty(strOptString2)) {
                                                                    str6 = str10;
                                                                    jSONObjectOptJSONObject4.put(ITagManager.SUCCESS, 0);
                                                                } else {
                                                                    str6 = str10;
                                                                    if (TextUtils.equals(strOptString2, packageName) || TextUtils.isEmpty(strOptString3) || TextUtils.isEmpty(strOptString4)) {
                                                                        jSONObjectOptJSONObject4.put(ITagManager.SUCCESS, 0);
                                                                    } else {
                                                                        boolean zA = ak.a(applicationA, str12, strOptString, strOptString2, strOptString3);
                                                                        if (zA) {
                                                                            str3 = strOptString;
                                                                            str4 = str8;
                                                                        } else {
                                                                            str3 = strOptString;
                                                                            str4 = str8;
                                                                            jSONObjectOptJSONObject4.put("msg", "cur:" + packageName + " start failed:" + strOptString2);
                                                                        }
                                                                        int i6 = i5 | (zA ? 1 : 0);
                                                                        jSONObjectOptJSONObject4.put(ITagManager.SUCCESS, zA ? 1 : 0);
                                                                        if (i4 < iMin - 1) {
                                                                            try {
                                                                                Thread.sleep(500L);
                                                                            } catch (InterruptedException unused) {
                                                                            }
                                                                        }
                                                                        i5 = i6 == true ? 1 : 0;
                                                                    }
                                                                }
                                                                str3 = strOptString;
                                                                str4 = str8;
                                                            } else {
                                                                str3 = strOptString;
                                                                str4 = str8;
                                                                str5 = str9;
                                                                str6 = str10;
                                                            }
                                                            i4++;
                                                            akVar2 = akVar3;
                                                            str9 = str5;
                                                            strOptString = str3;
                                                            str10 = str6;
                                                            str8 = str4;
                                                        }
                                                        String str13 = strOptString;
                                                        String str14 = str8;
                                                        String str15 = str9;
                                                        ak akVar4 = akVar2;
                                                        JSONObject jSONObject2 = new JSONObject();
                                                        JSONObject jSONObject3 = new JSONObject();
                                                        jSONObject3.put("din", d.c(applicationA));
                                                        jSONObject3.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
                                                        jSONObject3.put("push_switch", d.p(applicationA));
                                                        jSONObject2.put("header", jSONObject3);
                                                        JSONObject jSONObject4 = new JSONObject();
                                                        jSONObject4.put(com.alipay.sdk.m.l.b.f9444k, "");
                                                        jSONObject4.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, 70);
                                                        jSONObject4.put(RemoteMessageConst.DEVICE_TOKEN, PushAgent.getInstance(applicationA).getRegistrationId());
                                                        jSONObject4.put("msg_id", "");
                                                        jSONObject4.put(PushConstants.INTENT_ACTIVITY_NAME, str12);
                                                        jSONObject4.put("putar", jSONArrayOptJSONArray);
                                                        jSONObject4.put(str14, str13);
                                                        jSONObject4.put(str15, System.currentTimeMillis());
                                                        JSONArray jSONArray = new JSONArray();
                                                        jSONArray.put(jSONObject4);
                                                        JSONObject jSONObject5 = new JSONObject();
                                                        jSONObject5.put("push", jSONArray);
                                                        jSONObject2.put("content", jSONObject5);
                                                        UMWorkDispatch.sendEvent(applicationA, 16385, v.a(), jSONObject2.toString());
                                                        if (i5 != 0) {
                                                            try {
                                                                UPushMessageNotifyApi.Callback callback = akVar4.f22762a;
                                                                if (callback != null) {
                                                                    callback.onNotifying();
                                                                }
                                                            } catch (Throwable unused2) {
                                                            }
                                                        }
                                                    } catch (Throwable th) {
                                                        th = th;
                                                        str2 = str;
                                                        UPLog.e(str2, th);
                                                    }
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    str2 = "Notify";
                                                    UPLog.e(str2, th);
                                                }
                                            }
                                        } catch (Throwable th3) {
                                            th = th3;
                                            str = str11;
                                        }
                                    }
                                });
                            }
                        }
                    }
                } catch (Throwable unused) {
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStopped(Activity activity) {
        }
    };

    private t() {
    }

    public static boolean c() {
        return f22911a.f22915e;
    }

    public static void b() {
        t tVar = f22911a;
        synchronized (tVar) {
            try {
                if (tVar.f22917g == null) {
                    tVar.f22917g = new s();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a() {
        t tVar = f22911a;
        if (tVar.f22916f) {
            return;
        }
        try {
            Application applicationA = x.a();
            if (applicationA != null) {
                applicationA.registerActivityLifecycleCallbacks(tVar.f22919i);
                tVar.f22916f = true;
            }
        } catch (Throwable unused) {
        }
    }
}
