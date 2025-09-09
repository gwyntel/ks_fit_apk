package com.umeng.message;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import com.taobao.agoo.BaseNotifyClick;
import com.umeng.message.common.UPLog;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.aw;
import com.umeng.message.proguard.x;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class UmengNotifyClick extends BaseNotifyClick {
    private static final String TAG = "NotifyClickActivity";

    protected void onChangeBadgeNumber(UMessage uMessage) throws ClassNotFoundException {
        Application applicationA = x.a();
        MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(applicationA);
        if (TextUtils.equals(uMessage.getMsgId(), messageSharedPrefs.f())) {
            return;
        }
        messageSharedPrefs.c(uMessage.getMsgId());
        aw.b(applicationA, -1);
    }

    @Override // com.taobao.agoo.BaseNotifyClick
    public final void onMessage(Intent intent) {
        Throwable th;
        UMessage uMessage;
        try {
            String stringExtra = intent.getStringExtra("body");
            UPLog.i(TAG, "msg:", stringExtra, "source:", intent.getStringExtra(AgooConstants.MESSAGE_SOURCE));
            onMessageReceived(intent);
            if (stringExtra != null && stringExtra.length() != 0) {
                String stringExtra2 = intent.getStringExtra("id");
                String stringExtra3 = intent.getStringExtra("task_id");
                JSONObject jSONObject = new JSONObject(stringExtra);
                jSONObject.put("agoo_msg_id", stringExtra2);
                jSONObject.put("agoo_task_id", stringExtra3);
                uMessage = new UMessage(jSONObject);
                try {
                    UTrack.getInstance().trackMfrPushMsgClick(uMessage);
                    onChangeBadgeNumber(uMessage);
                    try {
                        onMessage(uMessage);
                        return;
                    } catch (Throwable th2) {
                        UPLog.e(TAG, th2);
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        UPLog.e(TAG, th);
                        if (uMessage == null) {
                            try {
                            } catch (Throwable th4) {
                                return;
                            }
                        }
                        return;
                    } finally {
                        if (uMessage == null) {
                            try {
                                uMessage = new UMessage(new JSONObject());
                            } catch (Throwable th42) {
                                UPLog.e(TAG, th42);
                            }
                        }
                        onMessage(uMessage);
                    }
                }
            }
            try {
                onMessage(new UMessage(new JSONObject()));
            } catch (Throwable th5) {
                UPLog.e(TAG, th5);
            }
        } catch (Throwable th6) {
            th = th6;
            uMessage = null;
        }
    }

    protected abstract void onMessage(UMessage uMessage);

    protected void onMessageReceived(Intent intent) {
    }
}
