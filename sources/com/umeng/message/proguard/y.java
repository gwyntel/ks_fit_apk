package com.umeng.message.proguard;

import android.app.Application;
import android.app.Notification;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.os.Build;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.agoo.TaobaoRegister;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.UTrack;
import com.umeng.message.api.UPushAliasCallback;
import com.umeng.message.common.UPLog;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.ao;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.android.agoo.message.MessageService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class y extends UTrack {

    /* renamed from: a, reason: collision with root package name */
    public static volatile boolean f22948a;

    /* renamed from: d, reason: collision with root package name */
    private final aa f22951d = new z();

    /* renamed from: e, reason: collision with root package name */
    private long f22952e;

    /* renamed from: c, reason: collision with root package name */
    private static final y f22950c = new y();

    /* renamed from: b, reason: collision with root package name */
    public static boolean f22949b = false;

    private y() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b(String str, String str2, String str3) {
        try {
            if (MessageService.MSG_ACCS_NOTIFY_CLICK.equals(str3)) {
                TaobaoRegister.clickMessage(x.a(), str, str2);
            } else {
                TaobaoRegister.dismissMessage(x.a(), str, str2);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("sendMsgLogForAgoo msgId:");
            sb.append(str);
            if (!TextUtils.isEmpty(str2)) {
                sb.append(" taskId:");
                sb.append(str2);
            }
            sb.append(" status:");
            sb.append(str3);
            UPLog.d("Track", sb);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static boolean c() throws ClassNotFoundException {
        if (f.b()) {
            UPLog.d("Track", "track failed, silent mode!");
            return true;
        }
        Application applicationA = x.a();
        if (TextUtils.isEmpty(MessageSharedPrefs.getInstance(applicationA).k())) {
            return true;
        }
        return TextUtils.isEmpty(d.o(applicationA));
    }

    static /* synthetic */ JSONObject d() throws JSONException {
        String strK = MessageSharedPrefs.getInstance(x.a()).k();
        String strO = d.o(x.a());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("header", e.a());
        jSONObject.put("utdid", strO);
        jSONObject.put(RemoteMessageConst.DEVICE_TOKEN, strK);
        return jSONObject;
    }

    @Override // com.umeng.message.UTrack
    public final void addAlias(final String str, final String str2, final UPushAliasCallback uPushAliasCallback) {
        b.b(new Runnable() { // from class: com.umeng.message.proguard.y.4
            @Override // java.lang.Runnable
            public final void run() {
                boolean z2;
                boolean z3 = true;
                MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(x.a());
                try {
                } catch (Exception e2) {
                    UPLog.e("Track", e2);
                }
                if (!messageSharedPrefs.a("alias_add_")) {
                    uPushAliasCallback.onMessage(false, "interval limit.");
                    return;
                }
                String strB = y.b(str, str2);
                if (strB != null && strB.length() > 0) {
                    uPushAliasCallback.onMessage(false, strB);
                    return;
                }
                String strO = d.o(x.a());
                String strK = messageSharedPrefs.k();
                StringBuilder sb = new StringBuilder();
                String str3 = "";
                sb.append("");
                sb.append("utdid:");
                sb.append(strO);
                sb.append(", deviceToken:");
                sb.append(strK);
                sb.append(com.alipay.sdk.m.u.i.f9802b);
                String string = sb.toString();
                if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
                    UPLog.e("Track", "addAlias: type or alias empty.");
                    string = string + "addAlias: empty type or alias. ";
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (TextUtils.isEmpty(strO)) {
                    UPLog.e("Track", "utdid empty. ");
                    string = string + "utdid empty;";
                    z2 = false;
                }
                if (TextUtils.isEmpty(strK)) {
                    UPLog.e("Track", "deviceToken empty.");
                    string = string + "deviceToken empty;";
                    z2 = false;
                }
                if (messageSharedPrefs.a(0, str, str2)) {
                    String str4 = String.format("addAlias: <%s, %s> has been synced to the server before. Ignore this request.", str, str2);
                    UPLog.d("Track", str4);
                    str3 = "" + str4;
                    z2 = false;
                } else {
                    z3 = false;
                }
                try {
                    JSONObject jSONObjectD = y.d();
                    if (z2) {
                        jSONObjectD.put(PushConstants.SUB_ALIAS_STATUS_NAME, str);
                        jSONObjectD.put("type", str2);
                        jSONObjectD.put("last_alias", messageSharedPrefs.a(0, str2));
                        jSONObjectD.put("ts", System.currentTimeMillis());
                    } else if (z3) {
                        jSONObjectD.put("success", str3);
                    } else {
                        jSONObjectD.put(ITagManager.FAIL, string);
                    }
                    y.this.f22951d.a(str, str2, jSONObjectD, uPushAliasCallback);
                } catch (Throwable th) {
                    UPLog.e("Track", th);
                    if (th.getMessage() == null) {
                        UPushAliasCallback uPushAliasCallback2 = uPushAliasCallback;
                        if (uPushAliasCallback2 != null) {
                            uPushAliasCallback2.onMessage(false, "alias:" + str + " add failed");
                            return;
                        }
                        return;
                    }
                    UPushAliasCallback uPushAliasCallback3 = uPushAliasCallback;
                    if (uPushAliasCallback3 != null) {
                        uPushAliasCallback3.onMessage(false, "alias:" + str + " add failed:" + th.getMessage());
                    }
                }
            }
        });
    }

    @Override // com.umeng.message.UTrack
    public final void deleteAlias(final String str, final String str2, final UPushAliasCallback uPushAliasCallback) {
        b.b(new Runnable() { // from class: com.umeng.message.proguard.y.6
            @Override // java.lang.Runnable
            public final void run() {
                boolean z2;
                MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(x.a());
                try {
                } catch (Throwable th) {
                    UPLog.e("Track", th);
                }
                if (!messageSharedPrefs.a("alias_del_")) {
                    uPushAliasCallback.onMessage(false, "interval limit.");
                    return;
                }
                String strB = y.b(str, str2);
                if (strB != null && strB.length() > 0) {
                    uPushAliasCallback.onMessage(false, strB);
                    return;
                }
                String str3 = "";
                if (TextUtils.isEmpty(str2)) {
                    UPLog.e("Track", "removeAlias: type empty.");
                    str3 = "removeAlias: type empty. ";
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (TextUtils.isEmpty(d.o(x.a()))) {
                    UPLog.e("Track", "removeAlias: utdid empty.");
                    str3 = str3 + "utdid empty. ";
                    z2 = false;
                }
                if (TextUtils.isEmpty(messageSharedPrefs.k())) {
                    UPLog.e("Track", "removeAlias: deviceToken empty.");
                    str3 = str3 + "deviceToken empty.";
                    z2 = false;
                }
                try {
                    JSONObject jSONObjectD = y.d();
                    if (z2) {
                        jSONObjectD.put(PushConstants.SUB_ALIAS_STATUS_NAME, str);
                        jSONObjectD.put("type", str2);
                        jSONObjectD.put("ts", System.currentTimeMillis());
                    } else {
                        jSONObjectD.put(ITagManager.FAIL, str3);
                    }
                    y.this.f22951d.c(str, str2, jSONObjectD, uPushAliasCallback);
                } catch (Throwable th2) {
                    UPLog.e("Track", th2);
                    if (th2.getMessage() == null) {
                        uPushAliasCallback.onMessage(false, "alias:" + str + "remove failed.");
                        return;
                    }
                    uPushAliasCallback.onMessage(false, "alias:" + str + "remove failed:" + th2.getMessage());
                }
            }
        });
    }

    @Override // com.umeng.message.UTrack
    public final void setAlias(final String str, final String str2, final UPushAliasCallback uPushAliasCallback) {
        b.b(new Runnable() { // from class: com.umeng.message.proguard.y.5
            @Override // java.lang.Runnable
            public final void run() {
                boolean z2;
                String str3;
                boolean z3;
                MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(x.a());
                try {
                } catch (Throwable th) {
                    UPLog.e("Track", th);
                }
                if (!messageSharedPrefs.a("alias_set_")) {
                    uPushAliasCallback.onMessage(false, "interval limit.");
                    return;
                }
                String strB = y.b(str, str2);
                if (strB != null && strB.length() > 0) {
                    uPushAliasCallback.onMessage(false, strB);
                    return;
                }
                String str4 = "";
                if (TextUtils.isEmpty(str2)) {
                    UPLog.e("Track", "addExclusiveAlias: type empty.");
                    str3 = "addExclusiveAlias: type empty. ";
                    z2 = false;
                } else {
                    z2 = true;
                    str3 = "";
                }
                if (TextUtils.isEmpty(d.o(x.a()))) {
                    UPLog.e("Track", "addExclusiveAlias: utdid empty.");
                    str3 = str3 + "utdid empty. ";
                    z2 = false;
                }
                if (TextUtils.isEmpty(messageSharedPrefs.k())) {
                    UPLog.e("Track", "addExclusiveAlias: deviceToken empty.");
                    str3 = str3 + "deviceToken empty.";
                    z2 = false;
                }
                if (messageSharedPrefs.a(1, str, str2)) {
                    String str5 = String.format("addExclusiveAlias: <%s, %s> has been synced to the server before. Ignore this request.", str, str2);
                    UPLog.d("Track", str5);
                    str4 = "" + str5;
                    z3 = true;
                    z2 = false;
                } else {
                    z3 = false;
                }
                try {
                    JSONObject jSONObjectD = y.d();
                    if (z2) {
                        jSONObjectD.put(PushConstants.SUB_ALIAS_STATUS_NAME, str);
                        jSONObjectD.put("type", str2);
                        jSONObjectD.put("last_alias", messageSharedPrefs.a(1, str2));
                        jSONObjectD.put("ts", System.currentTimeMillis());
                    } else if (z3) {
                        jSONObjectD.put("success", str4);
                    } else {
                        jSONObjectD.put(ITagManager.FAIL, str3);
                    }
                    y.this.f22951d.b(str, str2, jSONObjectD, uPushAliasCallback);
                } catch (Throwable th2) {
                    UPLog.e("Track", th2);
                    if (th2.getMessage() == null) {
                        uPushAliasCallback.onMessage(false, "alias:" + str + "add failed.");
                        return;
                    }
                    uPushAliasCallback.onMessage(false, "alias:" + str + "add failed:" + th2.getMessage());
                }
            }
        });
    }

    @Override // com.umeng.message.UTrack
    public final void trackMfrPushMsgClick(UMessage uMessage) {
        if (uMessage == null || TextUtils.isEmpty(uMessage.getMsgId())) {
            return;
        }
        a(uMessage.getMsgId(), 21);
    }

    @Override // com.umeng.message.UTrack
    public final void trackMsgArrival(UMessage uMessage) {
        if (uMessage == null) {
            return;
        }
        a(uMessage.getMsgId(), 0);
    }

    @Override // com.umeng.message.UTrack
    public final void trackMsgClick(UMessage uMessage) {
        if (uMessage != null && !TextUtils.isEmpty(uMessage.getMsgId())) {
            a(uMessage.getMsgId(), 1);
        }
        if (uMessage == null || TextUtils.isEmpty(uMessage.getAgooMsgId())) {
            return;
        }
        a(uMessage.getAgooMsgId(), uMessage.getTaskId(), MessageService.MSG_ACCS_NOTIFY_CLICK);
    }

    @Override // com.umeng.message.UTrack
    public final void trackMsgDismissed(UMessage uMessage) {
        if (uMessage != null && !TextUtils.isEmpty(uMessage.getMsgId())) {
            a(uMessage.getMsgId(), 2);
        }
        if (uMessage == null || TextUtils.isEmpty(uMessage.getAgooMsgId())) {
            return;
        }
        a(uMessage.getAgooMsgId(), uMessage.getTaskId(), MessageService.MSG_ACCS_NOTIFY_DISMISS);
    }

    @Override // com.umeng.message.UTrack
    public final void trackMsgRepost(UMessage uMessage, Notification notification) {
        if (uMessage == null || TextUtils.isEmpty(uMessage.getMsgId())) {
            return;
        }
        ac acVarA = w.a().a(uMessage.getMsgId());
        if (acVarA == null) {
            UPLog.i("Track", "sendMsgRepost item null, msgId:", uMessage.getMsgId());
            return;
        }
        try {
            this.f22951d.a(uMessage.getMsgId(), (Build.VERSION.SDK_INT < 26 || notification == null) ? null : notification.getChannelId(), acVarA.f22712d);
        } catch (Throwable th) {
            UPLog.e("Track", th);
        }
    }

    @Override // com.umeng.message.UTrack
    public final void trackMsgShow(UMessage uMessage, Notification notification) {
        int i2;
        if (uMessage == null || TextUtils.isEmpty(uMessage.getMsgId())) {
            return;
        }
        if (TextUtils.equals(uMessage.getDisplayType(), "notification")) {
            i2 = 6;
        } else if (!TextUtils.equals(uMessage.getDisplayType(), UMessage.DISPLAY_TYPE_CUSTOM)) {
            return;
        } else {
            i2 = 7;
        }
        try {
            this.f22951d.a(uMessage.getMsgId(), i2, (Build.VERSION.SDK_INT < 26 || notification == null) ? null : notification.getChannelId());
        } catch (Throwable th) {
            UPLog.w("Track", "trackMsgShow error:", th.getMessage());
        }
    }

    public static y a() {
        return f22950c;
    }

    public final void a(String str, int i2) {
        if (c()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            UPLog.e("Track", "trackMsgLog skipped, msgId empty!");
            return;
        }
        try {
            this.f22951d.a(str, i2);
        } catch (Throwable th) {
            UPLog.e("Track", th);
        }
    }

    private void a(final String str, final String str2, final String str3) {
        b.c(new Runnable() { // from class: com.umeng.message.proguard.y.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    y.this.b(str, str2, str3);
                } catch (Throwable th) {
                    UPLog.e("Track", th);
                }
            }
        });
    }

    final synchronized void b() {
        ArrayList arrayList;
        if (c()) {
            return;
        }
        try {
            Application applicationA = x.a();
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (Math.abs(jCurrentTimeMillis - this.f22952e) >= 30000) {
                this.f22952e = jCurrentTimeMillis;
                if (f.b(applicationA)) {
                    ao aoVarA = ao.a(applicationA);
                    Cursor cursorQuery = aoVarA.f22779a.getContentResolver().query(h.d(aoVarA.f22779a), null, null, null, "Time Asc ");
                    if (cursorQuery == null) {
                        arrayList = null;
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        for (boolean zMoveToFirst = cursorQuery.moveToFirst(); zMoveToFirst; zMoveToFirst = cursorQuery.moveToNext()) {
                            arrayList2.add(new ao.a(cursorQuery));
                        }
                        cursorQuery.close();
                        arrayList = arrayList2;
                    }
                    if (arrayList != null && !arrayList.isEmpty()) {
                        JSONArray jSONArray = new JSONArray();
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            ao.a aVar = (ao.a) arrayList.get(i2);
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("ts", aVar.f22781b);
                            jSONObject.put(com.alipay.sdk.m.l.b.f9444k, "");
                            jSONObject.put(RemoteMessageConst.DEVICE_TOKEN, MessageSharedPrefs.getInstance(applicationA).k());
                            jSONObject.put("msg_id", aVar.f22780a);
                            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, aVar.f22782c);
                            jSONArray.put(jSONObject);
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject2.put("header", jSONObject3);
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put("push", jSONArray);
                        jSONObject2.put("content", jSONObject4);
                        jSONObject3.put(com.umeng.analytics.pro.bc.aR, MsgConstant.SDK_VERSION);
                        jSONObject3.put("din", d.c(applicationA));
                        jSONObject3.put("push_switch", d.p(applicationA));
                        UMWorkDispatch.sendEvent(x.a(), 16389, v.a(), jSONObject2.toString());
                    }
                }
            }
        } catch (Throwable th) {
            UPLog.e("Track", th);
        }
        if (f22948a) {
            return;
        }
        f22948a = true;
        b.a(new Runnable() { // from class: com.umeng.message.proguard.y.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(x.a());
                    if (messageSharedPrefs.f22601b.b("launch_send_policy", -1) == 1) {
                        UPLog.d("Track", "launch policy 1, skipped.");
                    } else {
                        if (messageSharedPrefs.a()) {
                            return;
                        }
                        y.this.f22951d.a();
                        if (f.b(x.a())) {
                            b.a(this, TimeUnit.DAYS.toMillis(1L), TimeUnit.MILLISECONDS);
                        }
                    }
                } catch (Exception e2) {
                    UPLog.e("Track", e2);
                    y.f22948a = false;
                }
            }
        }, 10L, TimeUnit.SECONDS);
    }

    static void a(JSONArray jSONArray) {
        if (jSONArray == null) {
            return;
        }
        try {
            Application applicationA = x.a();
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
            if (jSONArray.length() > 0) {
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                    arrayList.add(ContentProviderOperation.newDelete(h.d(applicationA)).withSelection("MsgId=? And ActionType=?", new String[]{jSONObject.optString("msg_id"), String.valueOf(jSONObject.optInt(ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE))}).build());
                }
            }
            applicationA.getContentResolver().applyBatch(h.f(applicationA), arrayList);
        } catch (Throwable th) {
            UPLog.e("Track", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str, String str2) {
        if (f.b()) {
            UPLog.d("Track", "checkAlias failed, silent mode!");
            return "checkAlias failed, silent mode!";
        }
        try {
            byte[] bytes = str.getBytes("UTF-8");
            byte[] bytes2 = str2.getBytes("UTF-8");
            boolean z2 = bytes.length <= 128;
            boolean z3 = bytes2.length <= 64;
            if (z2 && z3) {
                return null;
            }
            return "alias length must be <= 128 and aliasType length must be <= 64";
        } catch (Throwable th) {
            UPLog.e("Track", th);
            return null;
        }
    }
}
