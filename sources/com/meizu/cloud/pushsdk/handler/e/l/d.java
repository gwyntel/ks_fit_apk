package com.meizu.cloud.pushsdk.handler.e.l;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;

/* loaded from: classes4.dex */
public class d extends com.meizu.cloud.pushsdk.handler.e.a<SubAliasStatus> {
    public d(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    private void c(String str) {
        com.meizu.cloud.pushsdk.util.b.j(c(), c().getPackageName(), str);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 4096;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public SubAliasStatus f(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.MZ_MESSAGE_VALUE);
        SubAliasStatus subAliasStatusC = !TextUtils.isEmpty(stringExtra) ? com.meizu.cloud.pushsdk.platform.message.a.c(stringExtra) : (SubAliasStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_SUBALIAS_STATUS);
        if ("200".equals(subAliasStatusC.getCode())) {
            c(subAliasStatusC.getAlias());
        }
        return subAliasStatusC;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.e.a
    public void a(SubAliasStatus subAliasStatus, com.meizu.cloud.pushsdk.notification.c cVar) {
        if (b() == null || subAliasStatus == null) {
            return;
        }
        b().a(c(), subAliasStatus);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start SubScribeAliasStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SUBALIAS_STATUS.equals(e(intent));
    }
}
