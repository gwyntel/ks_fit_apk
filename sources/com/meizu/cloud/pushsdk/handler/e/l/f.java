package com.meizu.cloud.pushsdk.handler.e.l;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/* loaded from: classes4.dex */
public class f extends com.meizu.cloud.pushsdk.handler.e.a<UnRegisterStatus> {
    public f(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 1024;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus f(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.MZ_MESSAGE_VALUE);
        UnRegisterStatus unRegisterStatusE = !TextUtils.isEmpty(stringExtra) ? com.meizu.cloud.pushsdk.platform.message.a.e(stringExtra) : (UnRegisterStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_UNREGISTER_STATUS);
        if (unRegisterStatusE.isUnRegisterSuccess()) {
            com.meizu.cloud.pushsdk.util.b.g(c(), "", c().getPackageName());
        }
        return unRegisterStatusE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    public void a(UnRegisterStatus unRegisterStatus, com.meizu.cloud.pushsdk.notification.c cVar) {
        if (b() == null || unRegisterStatus == null) {
            return;
        }
        b().a(c(), unRegisterStatus);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start UnRegisterStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_UNREGISTER_STATUS.equals(e(intent));
    }
}
