package com.huawei.hms.support.api.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.push.o;
import com.huawei.hms.push.p;
import com.huawei.hms.push.utils.JsonUtil;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.ResourceLoaderUtil;
import com.tekartik.sqflite.Constant;
import java.util.concurrent.RejectedExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class PushReceiver extends BroadcastReceiver {

    private static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Context f18107a;

        /* renamed from: b, reason: collision with root package name */
        private Intent f18108b;

        @Override // java.lang.Runnable
        public void run() {
            Intent intent = new Intent("com.huawei.push.action.MESSAGING_EVENT");
            intent.setPackage(this.f18108b.getPackage());
            try {
                JSONObject jSONObjectB = PushReceiver.b(this.f18108b);
                String string = JsonUtil.getString(jSONObjectB, "moduleName", "");
                int i2 = JsonUtil.getInt(jSONObjectB, "msgType", 0);
                int internalCode = JsonUtil.getInt(jSONObjectB, "status", 0);
                if (ErrorEnum.SUCCESS.getInternalCode() != internalCode) {
                    internalCode = ErrorEnum.ERROR_APP_SERVER_NOT_ONLINE.getInternalCode();
                }
                Bundle bundle = new Bundle();
                if ("Push".equals(string) && i2 == 1) {
                    bundle.putString("message_type", "delivery");
                    bundle.putString(Constants.MessagePayloadKeys.MSGID_SERVER, JsonUtil.getString(jSONObjectB, RemoteMessageConst.MSGID, ""));
                    bundle.putInt("error", internalCode);
                    bundle.putString("transaction_id", JsonUtil.getString(jSONObjectB, Constant.PARAM_TRANSACTION_ID, ""));
                } else {
                    if (this.f18108b.getExtras() != null) {
                        bundle.putAll(this.f18108b.getExtras());
                    }
                    bundle.putString("message_type", "received_message");
                    bundle.putString(Constants.MessagePayloadKeys.MSGID_SERVER, this.f18108b.getStringExtra("msgIdStr"));
                    bundle.putByteArray(RemoteMessageConst.MSGBODY, this.f18108b.getByteArrayExtra("msg_data"));
                    bundle.putString(RemoteMessageConst.DEVICE_TOKEN, com.huawei.hms.push.a.a(this.f18108b.getByteArrayExtra(RemoteMessageConst.DEVICE_TOKEN)));
                    bundle.putInt(RemoteMessageConst.INPUT_TYPE, 1);
                    bundle.putString("message_proxy_type", this.f18108b.getStringExtra("message_proxy_type"));
                }
                if (new p().a(this.f18107a, bundle, intent)) {
                    HMSLog.i("PushReceiver", "receive " + this.f18108b.getAction() + " and start service success");
                    return;
                }
                HMSLog.e("PushReceiver", "receive " + this.f18108b.getAction() + " and start service failed");
            } catch (RuntimeException unused) {
                HMSLog.e("PushReceiver", "handle push message occur exception.");
            }
        }

        private b(Context context, Intent intent) {
            this.f18107a = context;
            this.f18108b = intent;
        }
    }

    private static class c implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Context f18109a;

        /* renamed from: b, reason: collision with root package name */
        private Intent f18110b;

        @Override // java.lang.Runnable
        public void run() {
            try {
                byte[] byteArrayExtra = this.f18110b.getByteArrayExtra(RemoteMessageConst.DEVICE_TOKEN);
                if (byteArrayExtra != null && byteArrayExtra.length != 0) {
                    HMSLog.i("PushReceiver", "receive a push token: " + this.f18109a.getPackageName());
                    Intent intent = new Intent("com.huawei.push.action.MESSAGING_EVENT");
                    intent.setPackage(this.f18110b.getPackage());
                    Bundle bundle = new Bundle();
                    bundle.putString("message_type", "new_token");
                    bundle.putString(RemoteMessageConst.DEVICE_TOKEN, com.huawei.hms.push.a.a(byteArrayExtra));
                    bundle.putString("transaction_id", this.f18110b.getStringExtra("transaction_id"));
                    bundle.putString("subjectId", this.f18110b.getStringExtra("subjectId"));
                    bundle.putInt("error", this.f18110b.getIntExtra("error", ErrorEnum.SUCCESS.getInternalCode()));
                    bundle.putString("belongId", this.f18110b.getStringExtra("belongId"));
                    if (new p().a(this.f18109a, bundle, intent)) {
                        return;
                    }
                    HMSLog.e("PushReceiver", "receive " + this.f18110b.getAction() + " and start service failed");
                    return;
                }
                HMSLog.i("PushReceiver", "get a deviceToken, but it is null or empty");
            } catch (RejectedExecutionException unused) {
                HMSLog.e("PushReceiver", "execute task error");
            } catch (Exception unused2) {
                HMSLog.e("PushReceiver", "handle push token error");
            }
        }

        private c(Context context, Intent intent) {
            this.f18109a = context;
            this.f18110b = intent;
        }
    }

    private void b(Context context, Intent intent) {
        try {
            if (intent.hasExtra(RemoteMessageConst.DEVICE_TOKEN)) {
                o.a().execute(new c(context, intent));
            } else {
                HMSLog.i("PushReceiver", "This message dose not sent by hwpush.");
            }
        } catch (RuntimeException unused) {
            HMSLog.e("PushReceiver", "handlePushMessageEvent execute task runtime exception.");
        } catch (Exception unused2) {
            HMSLog.e("PushReceiver", "handlePushTokenEvent execute task error");
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            return;
        }
        HMSLog.i("PushReceiver", "push receive broadcast message, Intent:" + intent.getAction() + " pkgName:" + context.getPackageName());
        try {
            intent.getStringExtra("TestIntent");
            String action = intent.getAction();
            if (ResourceLoaderUtil.getmContext() == null) {
                ResourceLoaderUtil.setmContext(context.getApplicationContext());
            }
            if ("com.huawei.android.push.intent.REGISTRATION".equals(action)) {
                b(context, intent);
            } else if ("com.huawei.android.push.intent.RECEIVE".equals(action)) {
                a(context, intent);
            } else {
                HMSLog.i("PushReceiver", "message can't be recognised.");
            }
        } catch (Exception unused) {
            HMSLog.e("PushReceiver", "intent has some error");
        }
    }

    private void a(Context context, Intent intent) {
        try {
            if (intent.hasExtra("msg_data")) {
                o.a().execute(new b(context, intent));
            } else {
                HMSLog.i("PushReceiver", "This push message dose not sent by hwpush.");
            }
        } catch (RuntimeException unused) {
            HMSLog.e("PushReceiver", "handlePushMessageEvent execute task runtime exception.");
        } catch (Exception unused2) {
            HMSLog.e("PushReceiver", "handlePushMessageEvent execute task error");
        }
    }

    private static JSONObject b(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.PS_CONTENT);
        }
        return null;
    }

    private static JSONObject a(byte[] bArr) {
        try {
            return new JSONObject(com.huawei.hms.push.a.a(bArr));
        } catch (JSONException unused) {
            HMSLog.w("PushReceiver", "JSONException:parse message body failed.");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JSONObject b(Intent intent) throws RuntimeException {
        JSONObject jSONObjectA = a(intent.getByteArrayExtra("msg_data"));
        JSONObject jSONObjectA2 = a(jSONObjectA);
        String string = JsonUtil.getString(jSONObjectA2, "data", null);
        if (com.huawei.hms.push.c.a(jSONObjectA2, b(jSONObjectA2), string)) {
            return jSONObjectA;
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return new JSONObject(string);
        } catch (JSONException unused) {
            return null;
        }
    }

    private static JSONObject a(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.MSG_CONTENT);
        }
        return null;
    }
}
