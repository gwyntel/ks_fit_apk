package com.meizu.cloud.pushsdk.handler.e.m;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.e.d;

/* loaded from: classes4.dex */
public class b extends d {
    public b(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.e.d, com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 524288;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.d, com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: g */
    public void c(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.d.f(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp(), messageV3.getDelayedReportMillis());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.d, com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: h */
    public void d(MessageV3 messageV3) {
        DebugLogger.e("AbstractMessageHandler", "BrightNotificationHandler don't repeat upload receiver push event");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.d, com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: j */
    public int f(MessageV3 messageV3) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.d, com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: l */
    public MessageV3 f(Intent intent) {
        return (MessageV3) intent.getParcelableExtra(PushConstants.EXTRA_APP_PUSH_BRIGHT_NOTIFICATION_MESSAGE);
    }

    @Override // com.meizu.cloud.pushsdk.handler.e.d, com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start BrightNotificationHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_BRIGHT_NOTIFICATION_MESSAGE.equals(e(intent));
    }
}
