package com.umeng.message.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.common.UPLog;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.message.proguard.ae;
import com.umeng.message.proguard.h;
import java.io.File;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class ai {

    /* renamed from: b, reason: collision with root package name */
    private static boolean f22740b = false;

    /* renamed from: c, reason: collision with root package name */
    private static volatile ai f22741c;

    /* renamed from: a, reason: collision with root package name */
    private final Context f22742a;

    private ai(Context context) {
        this.f22742a = context.getApplicationContext();
    }

    static /* synthetic */ boolean b() {
        f22740b = false;
        return false;
    }

    private void c() {
        if (f22740b) {
            UMLog.mutlInfo("UmengInAppMessageTracker", 2, "sendInAppCacheLog已经在队列里，忽略该请求");
            return;
        }
        f22740b = true;
        UMLog.mutlInfo("UmengInAppMessageTracker", 2, "sendInAppCacheLog开始");
        b.c(new Runnable() { // from class: com.umeng.message.proguard.ai.4
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    Iterator<ae> it = InAppMessageManager.getInstance(ai.this.f22742a).b().iterator();
                    while (it.hasNext()) {
                        ae next = it.next();
                        JSONObject jSONObjectB = ai.b(next.f22714b, next.f22715c, next.f22716d, next.f22717e, next.f22718f, next.f22719g, next.f22720h, next.f22721i, next.f22722j);
                        if (jSONObjectB != null && TextUtils.equals(jSONObjectB.getString("success"), ITagManager.SUCCESS)) {
                            InAppMessageManager inAppMessageManager = InAppMessageManager.getInstance(ai.this.f22742a);
                            inAppMessageManager.f22627b.getContentResolver().delete(h.e(inAppMessageManager.f22627b), "MsgId=?", new String[]{next.f22714b});
                        }
                    }
                } catch (Exception e2) {
                    UPLog.w("UmengInAppMessageTracker", "sendInAppCacheLog error:" + e2.getMessage());
                } finally {
                    ai.b();
                }
            }
        });
    }

    public static ai a(Context context) {
        if (f22741c == null) {
            synchronized (ai.class) {
                try {
                    if (f22741c == null) {
                        f22741c = new ai(context);
                    }
                } finally {
                }
            }
        }
        return f22741c;
    }

    static /* synthetic */ JSONObject b(String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("header", e.a());
        jSONObject.put("msg_id", str);
        jSONObject.put("msg_type", i2);
        jSONObject.put("num_display", i3);
        jSONObject.put("num_open_full", i4);
        jSONObject.put("num_open_top", i5);
        jSONObject.put("num_open_bottom", i6);
        jSONObject.put("num_close", i7);
        jSONObject.put("num_duration", i8);
        jSONObject.put("num_custom", i9);
        return g.a(jSONObject, "https://msg.umengcloud.com/admsg/v3/stats", UMUtils.getAppkey(x.a()), true);
    }

    public final void a(final ad adVar) {
        if (f.b()) {
            UPLog.d("UmengInAppMessageTracker", "getSplashMsg failed, silent mode!");
        } else {
            c();
            b.c(new Runnable() { // from class: com.umeng.message.proguard.ai.1
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    UInAppMessage uInAppMessage;
                    UMLog.mutlInfo("UmengInAppMessageTracker", 2, "get splash message begin");
                    try {
                        JSONObject jSONObjectA = g.a(ai.a(), "https://msg.umengcloud.com/admsg/v3/launch", UMUtils.getAppkey(x.a()), true);
                        if (TextUtils.equals(jSONObjectA.getString("success"), ITagManager.SUCCESS)) {
                            UMLog.mutlInfo("UmengInAppMessageTracker", 2, "get splash message success".concat(String.valueOf(jSONObjectA)));
                            JSONObject jSONObject = jSONObjectA.getJSONObject("data");
                            InAppMessageManager.f22624d = jSONObject.getInt("pduration") * 1000;
                            InAppMessageManager.f22625e = jSONObject.getInt("sduration") * 1000;
                            adVar.a(new UInAppMessage(jSONObject.getJSONObject("launch")));
                            InAppMessageManager inAppMessageManager = InAppMessageManager.getInstance(ai.this.f22742a);
                            StringBuilder sb = new StringBuilder();
                            sb.append(System.currentTimeMillis());
                            inAppMessageManager.b("KEY_SPLASH_TS", sb.toString());
                            return;
                        }
                        if (!TextUtils.equals(jSONObjectA.getString("success"), ITagManager.FAIL) || !TextUtils.equals(jSONObjectA.getString("error"), "no message")) {
                            adVar.a(null);
                            return;
                        }
                        String strA = InAppMessageManager.getInstance(ai.this.f22742a).a();
                        if (TextUtils.isEmpty(strA)) {
                            return;
                        }
                        try {
                            uInAppMessage = new UInAppMessage(new JSONObject(strA));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            uInAppMessage = null;
                        }
                        if (uInAppMessage != null) {
                            InAppMessageManager.getInstance(ai.this.f22742a).a(new File(f.a(ai.this.f22742a, uInAppMessage.msg_id)));
                            InAppMessageManager.getInstance(ai.this.f22742a).a((UInAppMessage) null);
                        }
                    } catch (Exception e3) {
                        UPLog.w("UmengInAppMessageTracker", "getSplashMsg error: ", e3.getMessage());
                        adVar.a(null);
                    }
                }
            });
        }
    }

    public final void a(final String str, final ad adVar) {
        if (f.b()) {
            UPLog.d("UmengInAppMessageTracker", "getCardMsg failed, silent mode!");
        } else {
            c();
            b.c(new Runnable() { // from class: com.umeng.message.proguard.ai.2
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    UInAppMessage uInAppMessage;
                    UMLog.mutlInfo("UmengInAppMessageTracker", 2, "get card message begin");
                    try {
                        JSONObject jSONObjectA = ai.a();
                        jSONObjectA.put(Constants.ScionAnalytics.PARAM_LABEL, str);
                        JSONObject jSONObjectA2 = g.a(jSONObjectA, "https://msg.umengcloud.com/admsg/v3/getmsg", UMUtils.getAppkey(x.a()), true);
                        if (TextUtils.equals(jSONObjectA2.getString("success"), ITagManager.SUCCESS)) {
                            UMLog.mutlInfo("UmengInAppMessageTracker", 2, "get card message success".concat(String.valueOf(jSONObjectA2)));
                            JSONObject jSONObject = jSONObjectA2.getJSONObject("data");
                            InAppMessageManager.f22624d = jSONObject.getInt("pduration") * 1000;
                            InAppMessageManager.f22625e = jSONObject.getInt("sduration") * 1000;
                            adVar.b(new UInAppMessage(jSONObject.getJSONObject("card")));
                            InAppMessageManager inAppMessageManager = InAppMessageManager.getInstance(ai.this.f22742a);
                            String strConcat = "KEY_CARD_TS_".concat(String.valueOf(jSONObjectA.optString(Constants.ScionAnalytics.PARAM_LABEL, "")));
                            StringBuilder sb = new StringBuilder();
                            sb.append(System.currentTimeMillis());
                            inAppMessageManager.b(strConcat, sb.toString());
                            return;
                        }
                        if (!TextUtils.equals(jSONObjectA2.getString("success"), ITagManager.FAIL) || !TextUtils.equals(jSONObjectA2.getString("error"), "no message")) {
                            adVar.b(null);
                            return;
                        }
                        String strA = InAppMessageManager.getInstance(ai.this.f22742a).a(str);
                        if (TextUtils.isEmpty(strA)) {
                            return;
                        }
                        try {
                            uInAppMessage = new UInAppMessage(new JSONObject(strA));
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                            uInAppMessage = null;
                        }
                        if (uInAppMessage != null) {
                            InAppMessageManager.getInstance(ai.this.f22742a).a(new File(f.a(ai.this.f22742a, uInAppMessage.msg_id)));
                            InAppMessageManager.getInstance(ai.this.f22742a).a((UInAppMessage) null, str);
                        }
                    } catch (Exception e3) {
                        UPLog.w("UmengInAppMessageTracker", "getCardMsg error: ", e3.getMessage());
                        adVar.b(null);
                    }
                }
            });
        }
    }

    public final void a(final String str, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int i8, final int i9) {
        b.c(new Runnable() { // from class: com.umeng.message.proguard.ai.3
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    UMLog.mutlInfo("UmengInAppMessageTracker", 2, "track in app msg begin");
                    JSONObject jSONObjectB = ai.b(str, i2, i3, i4, i5, i6, i7, i8, i9);
                    if (jSONObjectB == null || !TextUtils.equals(jSONObjectB.getString("success"), ITagManager.SUCCESS)) {
                        return;
                    }
                    UMLog.mutlInfo("UmengInAppMessageTracker", 2, "track in app msg success");
                } catch (Exception e2) {
                    UPLog.w("UmengInAppMessageTracker", "trackInAppMessage error:" + e2.getMessage());
                    final InAppMessageManager inAppMessageManager = InAppMessageManager.getInstance(ai.this.f22742a);
                    final String str2 = str;
                    final int i10 = i2;
                    final int i11 = i3;
                    final int i12 = i4;
                    final int i13 = i5;
                    final int i14 = i6;
                    final int i15 = i7;
                    final int i16 = i8;
                    final int i17 = i9;
                    if (TextUtils.isEmpty(str2)) {
                        return;
                    }
                    b.c(new Runnable() { // from class: com.umeng.message.inapp.InAppMessageManager.1

                        /* renamed from: a */
                        final /* synthetic */ String f22631a;

                        /* renamed from: b */
                        final /* synthetic */ int f22632b;

                        /* renamed from: c */
                        final /* synthetic */ int f22633c;

                        /* renamed from: d */
                        final /* synthetic */ int f22634d;

                        /* renamed from: e */
                        final /* synthetic */ int f22635e;

                        /* renamed from: f */
                        final /* synthetic */ int f22636f;

                        /* renamed from: g */
                        final /* synthetic */ int f22637g;

                        /* renamed from: h */
                        final /* synthetic */ int f22638h;

                        /* renamed from: i */
                        final /* synthetic */ int f22639i;

                        public AnonymousClass1(final String str22, final int i102, final int i112, final int i122, final int i132, final int i142, final int i152, final int i162, final int i172) {
                            str = str22;
                            i = i102;
                            i = i112;
                            i = i122;
                            i = i132;
                            i = i142;
                            i = i152;
                            i = i162;
                            i = i172;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                ae aeVarA = InAppMessageManager.a(InAppMessageManager.this, str);
                                if (aeVarA != null) {
                                    ae aeVar = new ae(str, i, aeVarA.f22716d + i, aeVarA.f22717e + i, aeVarA.f22718f + i, aeVarA.f22719g + i, aeVarA.f22720h + i, aeVarA.f22721i + i, aeVarA.f22722j);
                                    InAppMessageManager.this.f22627b.getContentResolver().update(h.e(InAppMessageManager.this.f22627b), aeVar.a(), "MsgId=?", new String[]{str});
                                } else {
                                    InAppMessageManager.this.f22627b.getContentResolver().insert(h.e(InAppMessageManager.this.f22627b), new ae(str, i, i, i, i, i, i, i, i).a());
                                }
                                UMLog.mutlInfo("InAppMessageManager", 2, "store in app cache log success");
                            } catch (Exception e3) {
                                UMLog.mutlInfo("InAppMessageManager", 0, "store in app cache log fail");
                                e3.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    static /* synthetic */ JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("header", e.a());
        jSONObject.put("pmode", InAppMessageManager.f22623a ? "0" : "1");
        return jSONObject;
    }
}
