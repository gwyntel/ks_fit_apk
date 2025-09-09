package com.meizu.cloud.pushsdk.handler.e.l;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

/* loaded from: classes4.dex */
public class c extends com.meizu.cloud.pushsdk.handler.e.a<RegisterStatus> {

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.f();
        }
    }

    public c(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 512;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public RegisterStatus f(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.MZ_MESSAGE_VALUE);
        RegisterStatus registerStatusB = !TextUtils.isEmpty(stringExtra) ? com.meizu.cloud.pushsdk.platform.message.a.b(stringExtra) : (RegisterStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_REGISTER_STATUS);
        if (!TextUtils.isEmpty(registerStatusB.getPushId())) {
            com.meizu.cloud.pushsdk.util.b.g(c(), registerStatusB.getPushId(), c().getPackageName());
            com.meizu.cloud.pushsdk.util.b.a(c(), (int) ((System.currentTimeMillis() / 1000) + registerStatusB.getExpireTime()), c().getPackageName());
        }
        return registerStatusB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        String mzPushServicePackageName = MzSystemUtils.getMzPushServicePackageName(c());
        if (com.meizu.cloud.pushsdk.util.b.d(c(), mzPushServicePackageName)) {
            com.meizu.cloud.pushsdk.util.b.a(c(), mzPushServicePackageName, false);
            if (TextUtils.isEmpty(com.meizu.cloud.pushsdk.util.b.g(c(), mzPushServicePackageName))) {
                String strE = e();
                if (TextUtils.isEmpty(strE)) {
                    return;
                }
                com.meizu.cloud.pushsdk.util.b.f(c(), mzPushServicePackageName, strE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void c(RegisterStatus registerStatus) {
        com.meizu.cloud.pushsdk.d.m.a.a().execute(new a());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    public void a(RegisterStatus registerStatus, com.meizu.cloud.pushsdk.notification.c cVar) {
        if (b() == null || registerStatus == null) {
            return;
        }
        b().a(c(), registerStatus);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start RegisterStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_REGISTER_STATUS.equals(e(intent));
    }
}
