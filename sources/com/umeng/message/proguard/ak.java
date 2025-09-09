package com.umeng.message.proguard;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.Constants;
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
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class ak {

    /* renamed from: a, reason: collision with root package name */
    UPushMessageNotifyApi.Callback f22762a;

    /* renamed from: b, reason: collision with root package name */
    public final al f22763b = new al();

    /* renamed from: c, reason: collision with root package name */
    boolean f22764c;

    public final void a() {
        if (this.f22763b.e()) {
            b.b(new Runnable() { // from class: com.umeng.message.proguard.ak.1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean zA = ak.this.f22763b.a();
                    boolean zA2 = false;
                    try {
                        if (d.h(x.a())) {
                            zA2 = ak.a(zA);
                        }
                    } catch (Throwable th) {
                        UPLog.e("Notify", th);
                    }
                    ak.this.f22763b.b(!zA2);
                }
            });
        }
    }

    public final void a(Activity activity, final Intent intent) {
        this.f22764c = true;
        if (activity == null || intent == null) {
            return;
        }
        final Context applicationContext = activity.getApplicationContext();
        final String name = activity.getClass().getName();
        b.b(new Runnable() { // from class: com.umeng.message.proguard.ak.3
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ak akVar = ak.this;
                    Context context = applicationContext;
                    Intent intent2 = intent;
                    String str = name;
                    akVar.f22763b.a(System.currentTimeMillis());
                    String stringExtra = intent2.getStringExtra("data");
                    String stringExtra2 = intent2.getStringExtra("pkg");
                    intent2.removeExtra("data");
                    intent2.removeExtra("pkg");
                    if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2)) {
                        String str2 = new String(as.a(new String(new byte[]{100, 85, 120, 112, 78, 67, 57, 109, 78, 67, 116, 81, 89, 106, 77, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 76, 108, 81, 120, 79, 81, 61, 61})));
                        byte[] bArrA = as.a(new String(new byte[]{98, 109, Constants.CMD_TYPE.CMD_STATUS_REPORT, 108, 100, 87, 99, 117, 90, 106, 107, 118, 84, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 114, 84, 68, 103, 121, 77, 119, 61, 61}));
                        String strB = ax.b(stringExtra2, str2, bArrA);
                        String strB2 = ax.b(stringExtra, str2, bArrA);
                        if (!TextUtils.isEmpty(strB2) && !TextUtils.isEmpty(strB)) {
                            JSONObject jSONObject = new JSONObject(strB2);
                            jSONObject.put(PushConstants.INTENT_ACTIVITY_NAME, str);
                            al alVar = akVar.f22763b;
                            String string = jSONObject.toString();
                            String strA = null;
                            if (string != null) {
                                try {
                                    strA = as.a(string.getBytes());
                                } catch (Exception unused) {
                                }
                            }
                            alVar.f22774a.a(XiaomiOAuthConstants.EXTRA_INFO, strA);
                            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, 71);
                            jSONObject.put(RemoteMessageConst.DEVICE_TOKEN, PushAgent.getInstance(context).getRegistrationId());
                            jSONObject.put("msg_id", "");
                            jSONObject.put(com.alipay.sdk.m.l.b.f9444k, "");
                            jSONObject.put("ts", System.currentTimeMillis());
                            JSONObject jSONObject2 = new JSONObject();
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("din", d.c(context));
                            jSONObject3.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
                            jSONObject3.put("push_switch", d.p(context));
                            jSONObject2.put("header", jSONObject3);
                            JSONArray jSONArray = new JSONArray();
                            jSONArray.put(jSONObject);
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put("push", jSONArray);
                            jSONObject2.put("content", jSONObject4);
                            UMWorkDispatch.sendEvent(context, 16385, v.a(), jSONObject2.toString());
                            try {
                                UPushMessageNotifyApi.Callback callback = akVar.f22762a;
                                if (callback != null) {
                                    callback.onNotified();
                                    akVar.f22764c = false;
                                    return;
                                }
                                return;
                            } catch (Throwable unused2) {
                                return;
                            }
                        }
                        akVar.f22763b.a(0L);
                        return;
                    }
                    akVar.f22763b.a(0L);
                } catch (Throwable th) {
                    UPLog.e("Notify", th);
                }
            }
        });
    }

    static boolean a(boolean z2) throws Exception {
        JSONObject jSONObjectA;
        Application applicationA = x.a();
        String zid = UMUtils.getZid(applicationA);
        if (TextUtils.isEmpty(zid)) {
            UPLog.d("Notify", "zid skip.");
            return false;
        }
        String messageAppkey = PushAgent.getInstance(applicationA).getMessageAppkey();
        if (TextUtils.isEmpty(messageAppkey)) {
            UPLog.d("Notify", "appkey skip.");
            return false;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(com.umeng.analytics.pro.bc.al, zid);
        jSONObject.put("ts", System.currentTimeMillis());
        jSONObject.put("appkey", messageAppkey);
        jSONObject.put("dps", z2 ? 1 : 0);
        try {
            jSONObjectA = g.a(jSONObject, "https://offmsg.umeng.com/v2/offmsg/switch", messageAppkey, false);
        } catch (Exception e2) {
            UPLog.d("Notify", "uploadEnableState error:", e2.getMessage());
            jSONObjectA = null;
        }
        return jSONObjectA != null;
    }

    static boolean a(Context context, String str, String str2, String str3, String str4) {
        try {
            String messageAppkey = PushAgent.getInstance(context).getMessageAppkey();
            ComponentName componentName = new ComponentName(str3, str4);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("trace_id", str2);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("appkey", messageAppkey);
            jSONObject2.put("pkg", context.getPackageName());
            jSONObject2.put(PushConstants.INTENT_ACTIVITY_NAME, str);
            jSONObject2.put(ITagManager.SUCCESS, 1);
            jSONArray.put(jSONObject2);
            jSONObject.put("pusor", jSONArray);
            String string = jSONObject.toString();
            String str5 = new String(as.a(new String(new byte[]{100, 85, 120, 112, 78, 67, 57, 109, 78, 67, 116, 81, 89, 106, 77, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 76, 108, 81, 120, 79, 81, 61, 61})));
            byte[] bArrA = as.a(new String(new byte[]{98, 109, Constants.CMD_TYPE.CMD_STATUS_REPORT, 108, 100, 87, 99, 117, 90, 106, 107, 118, 84, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 114, 84, 68, 103, 121, 77, 119, 61, 61}));
            String strA = ax.a(context.getPackageName(), str5, bArrA);
            String strA2 = ax.a(string, str5, bArrA);
            Intent intent = new Intent();
            intent.putExtra("data", strA2);
            intent.putExtra("pkg", strA);
            intent.setPackage(str3);
            intent.setComponent(componentName);
            intent.setFlags(268468224);
            context.startActivity(intent);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public final void a(JSONObject jSONObject) {
        try {
            if (this.f22763b.c()) {
                String strD = this.f22763b.d();
                if (TextUtils.isEmpty(strD)) {
                    return;
                }
                JSONObject jSONObject2 = new JSONObject(strD);
                jSONObject.put(PushConstants.INTENT_ACTIVITY_NAME, jSONObject2.optString(PushConstants.INTENT_ACTIVITY_NAME));
                jSONObject.put("pusor", jSONObject2.optJSONArray("pusor"));
                jSONObject.put("trace_id", jSONObject2.optString("trace_id"));
            }
        } catch (Throwable th) {
            UPLog.e("Notify", "handle msg arrived error:", th);
        }
    }
}
