package com.meizu.cloud.pushsdk.handler.e;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class f extends a<MessageV3> {
    public f(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 8;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    public void d(MessageV3 messageV3) throws JSONException {
        if (messageV3 == null || TextUtils.isEmpty(messageV3.getDeviceId()) || TextUtils.isEmpty(messageV3.getTaskId())) {
            return;
        }
        String strA = a(messageV3.getThroughMessage());
        if (TextUtils.isEmpty(strA)) {
            com.meizu.cloud.pushsdk.util.d.h(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
        } else {
            com.meizu.cloud.pushsdk.util.d.h(c(), strA, messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public MessageV3 f(Intent intent) {
        MessageV3 messageV3 = new MessageV3();
        if (PushConstants.C2DM_INTENT.equals(intent.getAction())) {
            b().a(c(), intent);
            return null;
        }
        messageV3.setThroughMessage(intent.getStringExtra("message"));
        messageV3.setTaskId(j(intent));
        messageV3.setDeviceId(d(intent));
        messageV3.setSeqId(i(intent));
        messageV3.setPushTimestamp(h(intent));
        messageV3.setUploadDataPackageName(g(intent));
        return messageV3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    public void a(MessageV3 messageV3, com.meizu.cloud.pushsdk.notification.c cVar) {
        if (b() == null || messageV3 == null || TextUtils.isEmpty(messageV3.getThroughMessage())) {
            return;
        }
        b().a(c(), messageV3.getThroughMessage());
        b().a(c(), messageV3.getThroughMessage(), com.meizu.cloud.pushsdk.handler.e.j.d.a().d(messageV3.getTaskId()).c(messageV3.getSeqId()).b(messageV3.getPushTimestamp()).a(messageV3.getDeviceId()).a().f());
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start ThroughMessageHandler match");
        if (!a(1, g(intent))) {
            return false;
        }
        if (PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction())) {
            if ("message".equals(e(intent))) {
                return true;
            }
            if (TextUtils.isEmpty(e(intent))) {
                String stringExtra = intent.getStringExtra("message");
                if (!TextUtils.isEmpty(stringExtra) && !b(stringExtra)) {
                    return true;
                }
            }
        }
        return PushConstants.C2DM_INTENT.equals(intent.getAction());
    }
}
