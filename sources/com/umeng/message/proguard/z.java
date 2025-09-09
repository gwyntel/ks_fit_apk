package com.umeng.message.proguard;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.api.UPushAliasCallback;
import com.umeng.message.common.UPLog;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.component.UmengMessageHandlerService;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
final class z extends aa {
    z() {
    }

    @Override // com.umeng.message.proguard.aa
    public final void a(String str, int i2, String str2) throws Exception {
        Object objValueOf;
        NotificationManager notificationManager;
        List notificationChannels;
        Application applicationA = x.a();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject.put("header", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("ts", System.currentTimeMillis());
        Object id = "";
        jSONObject3.put(com.alipay.sdk.m.l.b.f9444k, "");
        jSONObject3.put("msg_id", str);
        jSONObject3.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, i2);
        JSONArray jSONArray = new JSONArray();
        int importance = -1;
        if (Build.VERSION.SDK_INT < 26 || TextUtils.isEmpty(str2) || (notificationManager = (NotificationManager) applicationA.getSystemService("notification")) == null || (notificationChannels = notificationManager.getNotificationChannels()) == null) {
            objValueOf = "";
        } else {
            Iterator it = notificationChannels.iterator();
            objValueOf = "";
            while (it.hasNext()) {
                NotificationChannel notificationChannelA = androidx.core.app.i.a(it.next());
                Object obj = id;
                if (TextUtils.equals(str2, notificationChannelA.getId())) {
                    id = notificationChannelA.getId();
                    objValueOf = String.valueOf(notificationChannelA.getName());
                    importance = notificationChannelA.getImportance();
                } else {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("chan_id", notificationChannelA.getId());
                    jSONObject4.put("chan_name", String.valueOf(notificationChannelA.getName()));
                    jSONObject4.put("chan_imp", notificationChannelA.getImportance());
                    jSONArray.put(jSONObject4);
                    id = obj;
                }
            }
        }
        jSONObject3.put("chan_id", id);
        jSONObject3.put("chan_name", objValueOf);
        jSONObject3.put("chan_imp", importance);
        jSONObject3.put("ext_chan_stat", jSONArray);
        jSONObject3.put("fg_stat", t.c() ? 1 : 0);
        jSONObject3.put(RemoteMessageConst.DEVICE_TOKEN, MessageSharedPrefs.getInstance(applicationA).k());
        JSONArray jSONArray2 = new JSONArray();
        jSONArray2.put(jSONObject3);
        JSONObject jSONObject5 = new JSONObject();
        jSONObject5.put("push", jSONArray2);
        jSONObject.put("content", jSONObject5);
        if (f.b(applicationA)) {
            jSONObject2.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
            jSONObject2.put("din", d.c(applicationA));
            jSONObject2.put("push_switch", d.p(applicationA));
            UMWorkDispatch.sendEvent(applicationA, 16391, v.a(), jSONObject.toString());
            return;
        }
        JSONObject jSONObject6 = new JSONObject();
        jSONObject6.put("jsonHeader", jSONObject2);
        jSONObject6.put("jsonBody", jSONObject5);
        jSONObject6.put(RemoteMessageConst.MSGID, str);
        jSONObject6.put("actionType", i2);
        Intent intent = new Intent("com.umeng.message.action");
        intent.setPackage(applicationA.getPackageName());
        intent.setClass(applicationA, UmengMessageHandlerService.class);
        intent.putExtra("um_command", "send");
        intent.putExtra("um_px_path", "umpx_push_logs");
        intent.putExtra("send_message", jSONObject6.toString());
        q.enqueueWork(applicationA, UmengMessageHandlerService.class, intent);
    }

    @Override // com.umeng.message.proguard.aa
    public final void b(String str, String str2, JSONObject jSONObject, UPushAliasCallback uPushAliasCallback) throws Exception {
        Application applicationA = x.a();
        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(applicationA);
        String strOptString = jSONObject.optString(ITagManager.FAIL, "");
        String strOptString2 = jSONObject.optString("success", "");
        if (!TextUtils.isEmpty(strOptString)) {
            uPushAliasCallback.onMessage(false, "alias:" + str + " add failed.");
            return;
        }
        if (!TextUtils.isEmpty(strOptString2)) {
            uPushAliasCallback.onMessage(true, "alias:" + str + " already exist.");
            return;
        }
        JSONObject jSONObjectA = g.a(jSONObject, "https://msg.umengcloud.com/v3/alias/set", UMUtils.getAppkey(applicationA));
        if (jSONObjectA == null || !TextUtils.equals(jSONObjectA.optString("success", ""), ITagManager.SUCCESS)) {
            uPushAliasCallback.onMessage(false, "alias:" + str + " add failed.");
            return;
        }
        messageSharedPrefs.a(str, str2, 1, jSONObjectA.optLong(RemoteMessageConst.TTL, 3600L));
        messageSharedPrefs.a("alias_set_", jSONObjectA.optLong(com.umeng.analytics.pro.bc.ba));
        uPushAliasCallback.onMessage(true, "alias:" + str + " add success.");
    }

    @Override // com.umeng.message.proguard.aa
    public final void c(String str, String str2, JSONObject jSONObject, UPushAliasCallback uPushAliasCallback) throws Exception {
        Application applicationA = x.a();
        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(applicationA);
        JSONObject jSONObjectA = g.a(jSONObject, "https://msg.umengcloud.com/v3/alias/delete", UMUtils.getAppkey(applicationA));
        if (jSONObjectA == null || !TextUtils.equals(jSONObjectA.getString("success"), ITagManager.SUCCESS)) {
            uPushAliasCallback.onMessage(true, "alias:" + str + ", type:" + str2 + " delete failed.");
            return;
        }
        try {
            messageSharedPrefs.f22600a.getContentResolver().delete(h.a(messageSharedPrefs.f22600a), "type=? and alias=?", new String[]{str2, str});
        } catch (Exception e2) {
            UPLog.e("Prefs", e2);
        }
        messageSharedPrefs.a("alias_del_", jSONObjectA.optLong(com.umeng.analytics.pro.bc.ba));
        uPushAliasCallback.onMessage(true, "alias:" + str + ", type:" + str2 + " delete success.");
    }

    @Override // com.umeng.message.proguard.aa
    final void a(String str, String str2, int i2) throws Exception {
        Object objValueOf;
        NotificationManager notificationManager;
        List notificationChannels;
        Application applicationA = x.a();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject.put("header", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("ts", System.currentTimeMillis());
        Object id = "";
        jSONObject3.put(com.alipay.sdk.m.l.b.f9444k, "");
        jSONObject3.put("msg_id", str);
        jSONObject3.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, 9);
        jSONObject3.put("repop_count", i2);
        JSONArray jSONArray = new JSONArray();
        int importance = -1;
        if (Build.VERSION.SDK_INT < 26 || TextUtils.isEmpty(str2) || (notificationManager = (NotificationManager) applicationA.getSystemService("notification")) == null || (notificationChannels = notificationManager.getNotificationChannels()) == null) {
            objValueOf = "";
        } else {
            Iterator it = notificationChannels.iterator();
            objValueOf = "";
            while (it.hasNext()) {
                NotificationChannel notificationChannelA = androidx.core.app.i.a(it.next());
                if (TextUtils.equals(str2, notificationChannelA.getId())) {
                    id = notificationChannelA.getId();
                    objValueOf = String.valueOf(notificationChannelA.getName());
                    importance = notificationChannelA.getImportance();
                } else {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("chan_id", notificationChannelA.getId());
                    jSONObject4.put("chan_name", String.valueOf(notificationChannelA.getName()));
                    jSONObject4.put("chan_imp", notificationChannelA.getImportance());
                    jSONArray.put(jSONObject4);
                    id = id;
                }
            }
        }
        jSONObject3.put("chan_id", id);
        jSONObject3.put("chan_name", objValueOf);
        jSONObject3.put("chan_imp", importance);
        jSONObject3.put("ext_chan_stat", jSONArray);
        jSONObject3.put("fg_stat", t.c() ? 1 : 0);
        jSONObject3.put(RemoteMessageConst.DEVICE_TOKEN, MessageSharedPrefs.getInstance(applicationA).k());
        JSONArray jSONArray2 = new JSONArray();
        jSONArray2.put(jSONObject3);
        JSONObject jSONObject5 = new JSONObject();
        jSONObject5.put("push", jSONArray2);
        jSONObject.put("content", jSONObject5);
        if (f.b(applicationA)) {
            jSONObject2.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
            jSONObject2.put("din", d.c(applicationA));
            jSONObject2.put("push_switch", d.p(applicationA));
            UMWorkDispatch.sendEvent(applicationA, 16392, v.a(), jSONObject.toString());
            return;
        }
        JSONObject jSONObject6 = new JSONObject();
        jSONObject6.put("jsonHeader", jSONObject2);
        jSONObject6.put("jsonBody", jSONObject5);
        jSONObject6.put(RemoteMessageConst.MSGID, str);
        jSONObject6.put("actionType", 9);
        Intent intent = new Intent("com.umeng.message.action");
        intent.setPackage(applicationA.getPackageName());
        intent.setClass(applicationA, UmengMessageHandlerService.class);
        intent.putExtra("um_command", "send");
        intent.putExtra("um_px_path", "umpx_push_logs");
        intent.putExtra("send_message", jSONObject6.toString());
        q.enqueueWork(applicationA, UmengMessageHandlerService.class, intent);
    }

    @Override // com.umeng.message.proguard.aa
    public final void a(String str, int i2) throws Exception {
        Application applicationA = x.a();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject.put("header", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("ts", System.currentTimeMillis());
        jSONObject3.put(com.alipay.sdk.m.l.b.f9444k, "");
        jSONObject3.put("msg_id", str);
        jSONObject3.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, i2);
        jSONObject3.put(RemoteMessageConst.DEVICE_TOKEN, MessageSharedPrefs.getInstance(applicationA).k());
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jSONObject3);
        JSONObject jSONObject4 = new JSONObject();
        jSONObject4.put("push", jSONArray);
        jSONObject.put("content", jSONObject4);
        if (f.b(applicationA)) {
            jSONObject2.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
            jSONObject2.put("din", d.c(applicationA));
            jSONObject2.put("push_switch", d.p(applicationA));
            UMWorkDispatch.sendEvent(applicationA, 16385, v.a(), jSONObject.toString());
            return;
        }
        JSONObject jSONObject5 = new JSONObject();
        jSONObject5.put("jsonHeader", jSONObject2);
        jSONObject5.put("jsonBody", jSONObject4);
        jSONObject5.put(RemoteMessageConst.MSGID, str);
        jSONObject5.put("actionType", i2);
        Intent intent = new Intent("com.umeng.message.action");
        intent.setPackage(applicationA.getPackageName());
        intent.setClass(applicationA, UmengMessageHandlerService.class);
        intent.putExtra("um_command", "send");
        intent.putExtra("um_px_path", "umpx_push_logs");
        intent.putExtra("send_message", jSONObject5.toString());
        q.enqueueWork(applicationA, UmengMessageHandlerService.class, intent);
    }

    @Override // com.umeng.message.proguard.aa
    public final void a() throws Exception {
        Application applicationA = x.a();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject.put("header", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put(RemoteMessageConst.DEVICE_TOKEN, MessageSharedPrefs.getInstance(applicationA).k());
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jSONObject3);
        JSONObject jSONObject4 = new JSONObject();
        jSONObject4.put("push", jSONArray);
        jSONObject.put("content", jSONObject4);
        if (f.b(applicationA)) {
            jSONObject2.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
            jSONObject2.put("din", d.c(applicationA));
            jSONObject2.put("push_switch", d.p(applicationA));
            UMWorkDispatch.sendEvent(applicationA, InputDeviceCompat.SOURCE_STYLUS, v.a(), jSONObject.toString());
            return;
        }
        JSONObject jSONObject5 = new JSONObject();
        jSONObject5.put("jsonHeader", jSONObject2);
        jSONObject5.put("jsonBody", jSONObject4);
        Intent intent = new Intent("com.umeng.message.action");
        intent.setPackage(applicationA.getPackageName());
        intent.setClass(applicationA, UmengMessageHandlerService.class);
        intent.putExtra("um_command", "send");
        intent.putExtra("um_px_path", "umpx_push_launch");
        intent.putExtra("send_message", jSONObject5.toString());
        q.enqueueWork(applicationA, UmengMessageHandlerService.class, intent);
    }

    @Override // com.umeng.message.proguard.aa
    public final void a(String str) throws Exception {
        Application applicationA = x.a();
        if (f.b(applicationA)) {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("din", d.c(applicationA));
            jSONObject2.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
            jSONObject2.put("push_switch", d.p(applicationA));
            jSONObject.put("header", jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(RemoteMessageConst.DEVICE_TOKEN, MessageSharedPrefs.getInstance(applicationA).k());
            jSONObject3.put("old_device_token", str);
            try {
                if (f.c(applicationA)) {
                    jSONObject3.put("utdid_gen", 1);
                    String strD = f.d(applicationA);
                    if (!TextUtils.isEmpty(strD)) {
                        jSONObject3.put("utdid_rst_id", strD);
                    }
                    f.e(applicationA);
                }
            } catch (Throwable unused) {
            }
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject3);
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("push", jSONArray);
            jSONObject.put("content", jSONObject4);
            UMWorkDispatch.sendEvent(applicationA, 16387, v.a(), jSONObject.toString());
        }
    }

    @Override // com.umeng.message.proguard.aa
    public final void a(String str, String str2, JSONObject jSONObject, UPushAliasCallback uPushAliasCallback) throws Exception {
        Application applicationA = x.a();
        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(applicationA);
        String strOptString = jSONObject.optString(ITagManager.FAIL, "");
        String strOptString2 = jSONObject.optString("success", "");
        if (!TextUtils.isEmpty(strOptString)) {
            uPushAliasCallback.onMessage(false, "alias:" + str + " add failed.");
            return;
        }
        if (!TextUtils.isEmpty(strOptString2)) {
            uPushAliasCallback.onMessage(true, "alias:" + str + " already exist.");
            return;
        }
        JSONObject jSONObjectA = g.a(jSONObject, "https://msg.umengcloud.com/v3/alias", UMUtils.getAppkey(applicationA));
        if (jSONObjectA != null && TextUtils.equals(jSONObjectA.optString("success", ""), ITagManager.SUCCESS)) {
            messageSharedPrefs.a(str, str2, 0, jSONObjectA.optLong(RemoteMessageConst.TTL, 3600L));
            messageSharedPrefs.a("alias_add_", jSONObjectA.optLong(com.umeng.analytics.pro.bc.ba));
            uPushAliasCallback.onMessage(true, "alias:" + str + " add success.");
            return;
        }
        uPushAliasCallback.onMessage(false, "alias:" + str + " add failed.");
    }
}
