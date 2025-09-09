package com.meizu.cloud.pushsdk.platform.d;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class b extends c<RegisterStatus> {
    public b(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, aVar, scheduledExecutorService);
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    public boolean d() {
        DebugLogger.e("Strategy", "isBrandMeizu " + MzSystemUtils.isBrandMeizu(this.f19807b));
        return (TextUtils.isEmpty(this.f19808c) || TextUtils.isEmpty(this.f19809d)) ? false : true;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    public Intent h() {
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f19808c);
        intent.putExtra(com.alipay.sdk.m.l.b.f9441h, this.f19809d);
        intent.putExtra("strategy_package_name", this.f19807b.getPackageName());
        intent.putExtra("strategy_type", j());
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected int j() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: m, reason: merged with bridge method [inline-methods] */
    public RegisterStatus a() {
        String str;
        RegisterStatus registerStatus = new RegisterStatus();
        registerStatus.setCode("20001");
        if (!TextUtils.isEmpty(this.f19808c)) {
            str = TextUtils.isEmpty(this.f19809d) ? "appKey not empty" : "appId not empty";
            return registerStatus;
        }
        registerStatus.setMessage(str);
        return registerStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: n, reason: merged with bridge method [inline-methods] */
    public RegisterStatus c() {
        return null;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: o, reason: merged with bridge method [inline-methods] */
    public RegisterStatus e() {
        RegisterStatus registerStatus = new RegisterStatus();
        String strH = com.meizu.cloud.pushsdk.util.b.h(this.f19807b, this.f19810e);
        int i2 = com.meizu.cloud.pushsdk.util.b.i(this.f19807b, this.f19810e);
        if (!a(strH, i2)) {
            registerStatus.setCode("200");
            registerStatus.setMessage("already register PushId,don't register frequently");
            registerStatus.setPushId(strH);
            registerStatus.setExpireTime((int) (i2 - (System.currentTimeMillis() / 1000)));
            return registerStatus;
        }
        com.meizu.cloud.pushsdk.util.b.g(this.f19807b, "", this.f19810e);
        String strB = com.meizu.cloud.pushsdk.d.c.b(this.f19807b);
        String strA = com.meizu.cloud.pushsdk.d.c.a(this.f19807b);
        if (TextUtils.isEmpty(strB) && TextUtils.isEmpty(strA)) {
            registerStatus.setCode("20000");
            registerStatus.setMessage("deviceId is empty");
            return registerStatus;
        }
        com.meizu.cloud.pushsdk.e.b.c cVarA = this.f19811f.a(this.f19808c, this.f19809d, strA, strB);
        if (cVarA.c()) {
            RegisterStatus registerStatus2 = new RegisterStatus((String) cVarA.b());
            DebugLogger.e("Strategy", "registerStatus " + registerStatus2);
            if (!TextUtils.isEmpty(registerStatus2.getPushId())) {
                com.meizu.cloud.pushsdk.util.b.g(this.f19807b, registerStatus2.getPushId(), this.f19810e);
                com.meizu.cloud.pushsdk.util.b.a(this.f19807b, (int) ((System.currentTimeMillis() / 1000) + registerStatus2.getExpireTime()), this.f19810e);
            }
            return registerStatus2;
        }
        com.meizu.cloud.pushsdk.e.c.a aVarA = cVarA.a();
        if (aVarA.c() != null) {
            DebugLogger.e("Strategy", "status code=" + aVarA.b() + " data=" + aVarA.c());
        }
        registerStatus.setCode(String.valueOf(aVarA.b()));
        registerStatus.setMessage(aVarA.a());
        DebugLogger.e("Strategy", "registerStatus " + registerStatus);
        return registerStatus;
    }

    public b(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f19813h = z2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(RegisterStatus registerStatus) {
        PlatformMessageSender.a(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), registerStatus);
    }

    public b(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
    }

    protected boolean a(String str, int i2) {
        String strA = com.meizu.cloud.pushsdk.d.c.a(this.f19807b);
        boolean zA = a(strA, str, i2);
        return zA ? a(strA, com.meizu.cloud.pushsdk.platform.a.a(str), i2) : zA;
    }

    private boolean a(String str, String str2, int i2) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || !str2.startsWith(str) || System.currentTimeMillis() / 1000 > ((long) i2);
    }
}
