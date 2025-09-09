package com.meizu.cloud.pushsdk.platform.d;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class g extends c<UnRegisterStatus> {
    public g(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, aVar, scheduledExecutorService);
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected boolean d() {
        return (TextUtils.isEmpty(this.f19808c) || TextUtils.isEmpty(this.f19809d)) ? false : true;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected Intent h() {
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f19808c);
        intent.putExtra(com.alipay.sdk.m.l.b.f9441h, this.f19809d);
        intent.putExtra("strategy_package_name", this.f19807b.getPackageName());
        intent.putExtra("strategy_type", j());
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected int j() {
        return 32;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: m, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus a() {
        String str;
        UnRegisterStatus unRegisterStatus = new UnRegisterStatus();
        unRegisterStatus.setCode("20001");
        if (!TextUtils.isEmpty(this.f19808c)) {
            str = TextUtils.isEmpty(this.f19809d) ? "appKey not empty" : "appId not empty";
            return unRegisterStatus;
        }
        unRegisterStatus.setMessage(str);
        return unRegisterStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: n, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus c() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: o, reason: merged with bridge method [inline-methods] */
    public UnRegisterStatus e() {
        UnRegisterStatus unRegisterStatus = new UnRegisterStatus();
        if (TextUtils.isEmpty(com.meizu.cloud.pushsdk.util.b.h(this.f19807b, this.f19810e))) {
            unRegisterStatus.setCode("200");
            unRegisterStatus.setMessage("already unRegister PushId,don't unRegister frequently");
            unRegisterStatus.setIsUnRegisterSuccess(true);
            return unRegisterStatus;
        }
        String strB = com.meizu.cloud.pushsdk.d.c.b(this.f19807b);
        String strA = com.meizu.cloud.pushsdk.d.c.a(this.f19807b);
        if (TextUtils.isEmpty(strB) && TextUtils.isEmpty(strA)) {
            unRegisterStatus.setCode("20000");
            unRegisterStatus.setMessage("deviceId is empty");
            return unRegisterStatus;
        }
        com.meizu.cloud.pushsdk.e.b.c cVarD = this.f19811f.d(this.f19808c, this.f19809d, strA, strB);
        if (cVarD.c()) {
            UnRegisterStatus unRegisterStatus2 = new UnRegisterStatus((String) cVarD.b());
            DebugLogger.e("Strategy", "network unRegisterStatus " + unRegisterStatus2);
            if ("200".equals(unRegisterStatus2.getCode())) {
                com.meizu.cloud.pushsdk.util.b.g(this.f19807b, "", this.f19810e);
            }
            return unRegisterStatus2;
        }
        com.meizu.cloud.pushsdk.e.c.a aVarA = cVarD.a();
        if (aVarA.c() != null) {
            DebugLogger.e("Strategy", "status code=" + aVarA.b() + " data=" + aVarA.c());
        }
        unRegisterStatus.setCode(String.valueOf(aVarA.b()));
        unRegisterStatus.setMessage(aVarA.a());
        DebugLogger.e("Strategy", "unRegisterStatus " + unRegisterStatus);
        return unRegisterStatus;
    }

    public g(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f19813h = z2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(UnRegisterStatus unRegisterStatus) {
        PlatformMessageSender.a(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), unRegisterStatus);
    }

    public g(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
    }
}
