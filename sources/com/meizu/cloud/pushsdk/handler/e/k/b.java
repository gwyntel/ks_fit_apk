package com.meizu.cloud.pushsdk.handler.e.k;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import java.util.Map;

/* loaded from: classes4.dex */
public class b extends com.meizu.cloud.pushsdk.handler.e.a<MessageV3> {

    /* renamed from: d, reason: collision with root package name */
    private Context f19741d;

    public b(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
        this.f19741d = context;
    }

    private Intent b(Context context, MessageV3 messageV3) {
        Intent intent;
        String uriPackageName = messageV3.getUriPackageName();
        if (TextUtils.isEmpty(uriPackageName)) {
            uriPackageName = messageV3.getUploadDataPackageName();
        }
        DebugLogger.i("AbstractMessageHandler", "openClassName is " + uriPackageName);
        if (messageV3.getClickType() == 0) {
            intent = context.getPackageManager().getLaunchIntentForPackage(uriPackageName);
            if (intent != null && messageV3.getParamsMap() != null) {
                for (Map.Entry<String, String> entry : messageV3.getParamsMap().entrySet()) {
                    DebugLogger.i("AbstractMessageHandler", " launcher activity key " + entry.getKey() + " value " + entry.getValue());
                    if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                        intent.putExtra(entry.getKey(), entry.getValue());
                    }
                }
            }
        } else if (1 == messageV3.getClickType()) {
            intent = new Intent();
            if (messageV3.getParamsMap() != null) {
                for (Map.Entry<String, String> entry2 : messageV3.getParamsMap().entrySet()) {
                    DebugLogger.i("AbstractMessageHandler", " key " + entry2.getKey() + " value " + entry2.getValue());
                    if (!TextUtils.isEmpty(entry2.getKey()) && !TextUtils.isEmpty(entry2.getValue())) {
                        intent.putExtra(entry2.getKey(), entry2.getValue());
                    }
                }
            }
            intent.setClassName(uriPackageName, messageV3.getActivity());
            DebugLogger.i("AbstractMessageHandler", intent.toUri(1));
        } else if (2 == messageV3.getClickType()) {
            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(messageV3.getWebUrl()));
            String uriPackageName2 = messageV3.getUriPackageName();
            if (!TextUtils.isEmpty(uriPackageName2)) {
                intent2.setPackage(uriPackageName2);
                DebugLogger.i("AbstractMessageHandler", "set uri package " + uriPackageName2);
            }
            intent = intent2;
        } else {
            if (3 == messageV3.getClickType()) {
                DebugLogger.i("AbstractMessageHandler", "CLICK_TYPE_SELF_DEFINE_ACTION");
            }
            intent = null;
        }
        if (intent != null) {
            intent.putExtra(PushConstants.MZ_PUSH_PLATFORM_EXTRA, com.meizu.cloud.pushsdk.handler.e.j.d.a().d(messageV3.getTaskId()).a().f());
        }
        return intent;
    }

    private MessageV3 c(String str) {
        return com.meizu.cloud.pushsdk.handler.d.a(str);
    }

    /* renamed from: d, reason: avoid collision after fix types in other method */
    private void d2(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.b.a(c(), messageV3.getPackageName(), 0);
        Intent intentB = b(c(), messageV3);
        if (intentB != null) {
            intentB.addFlags(268435456);
            try {
                c().startActivity(intentB);
            } catch (Exception e2) {
                DebugLogger.e("AbstractMessageHandler", "Click message StartActivity error " + e2.getMessage());
            }
        }
    }

    private MessageV3 m(Intent intent) {
        MPushMessage mPushMessage;
        String strG;
        String strD;
        String taskId;
        try {
            try {
                MessageV3 messageV3 = (MessageV3) intent.getParcelableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
                if (messageV3 != null) {
                    return messageV3;
                }
            } catch (Exception unused) {
                DebugLogger.e("AbstractMessageHandler", "cannot get messageV3");
            }
            return MessageV3.parse(strG, strD, taskId, mPushMessage);
        } finally {
            DebugLogger.e("AbstractMessageHandler", "parse MessageV2 to MessageV3");
            mPushMessage = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
            MessageV3.parse(g(intent), d(intent), mPushMessage.getTaskId(), mPushMessage);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 64;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public void d(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.d.c(c(), messageV3.getUploadDataPackageName(), TextUtils.isEmpty(messageV3.getDeviceId()) ? d((Intent) null) : messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public MessageV3 f(Intent intent) {
        DebugLogger.e("AbstractMessageHandler", "parse message V3");
        String stringExtra = intent.getStringExtra(PushConstants.MZ_MESSAGE_VALUE);
        return !TextUtils.isEmpty(stringExtra) ? c(stringExtra) : m(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    public void a(MessageV3 messageV3, com.meizu.cloud.pushsdk.notification.c cVar) {
        if (messageV3 == null) {
            return;
        }
        d2(messageV3);
        if (!TextUtils.isEmpty(messageV3.getTitle()) && !TextUtils.isEmpty(messageV3.getContent()) && b() != null) {
            b().a(c(), MzPushMessage.fromMessageV3(messageV3));
        }
        a(messageV3);
        a(this.f19741d, messageV3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public boolean a(MessageV3 messageV3, String str) {
        return a(messageV3, str);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start NotificationClickMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE.equals(e(intent));
    }
}
