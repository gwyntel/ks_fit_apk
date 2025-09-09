package com.meizu.cloud.pushsdk.platform.d;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class f extends c<PushSwitchStatus> {

    /* renamed from: j, reason: collision with root package name */
    private String f19823j;

    /* renamed from: k, reason: collision with root package name */
    private int f19824k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19825l;

    /* renamed from: m, reason: collision with root package name */
    private final Map<String, Boolean> f19826m;

    public f(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, null, aVar, scheduledExecutorService);
    }

    private com.meizu.cloud.pushsdk.e.b.c<String> a(PushSwitchStatus pushSwitchStatus) {
        boolean zS;
        boolean zU;
        boolean zS2;
        int i2 = this.f19824k;
        if (i2 != 0) {
            if (i2 == 1) {
                pushSwitchStatus.setMessage("SWITCH_THROUGH_MESSAGE");
                if (u() != this.f19825l || p()) {
                    b(true);
                    f(this.f19825l);
                    return this.f19811f.a(this.f19808c, this.f19809d, this.f19823j, this.f19824k, this.f19825l);
                }
                zS2 = s();
            } else {
                if (i2 != 2) {
                    if (i2 == 3) {
                        pushSwitchStatus.setMessage("SWITCH_ALL");
                        if (s() != this.f19825l || u() != this.f19825l || p()) {
                            b(true);
                            d(this.f19825l);
                            return this.f19811f.a(this.f19808c, this.f19809d, this.f19823j, this.f19825l);
                        }
                        zS2 = this.f19825l;
                    }
                    return null;
                }
                pushSwitchStatus.setMessage("CHECK_PUSH");
                if (!n() || !o() || p()) {
                    b(true);
                    return this.f19811f.a(this.f19808c, this.f19809d, this.f19823j);
                }
                zS = s();
                pushSwitchStatus.setSwitchNotificationMessage(zS);
                zU = u();
            }
            pushSwitchStatus.setSwitchNotificationMessage(zS2);
            zU = this.f19825l;
        } else {
            pushSwitchStatus.setMessage("SWITCH_NOTIFICATION");
            if (s() != this.f19825l || p()) {
                b(true);
                e(this.f19825l);
                return this.f19811f.a(this.f19808c, this.f19809d, this.f19823j, this.f19824k, this.f19825l);
            }
            zS = this.f19825l;
            pushSwitchStatus.setSwitchNotificationMessage(zS);
            zU = u();
        }
        pushSwitchStatus.setSwitchThroughMessage(zU);
        return null;
    }

    private void f(boolean z2) {
        com.meizu.cloud.pushsdk.util.b.c(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), z2);
    }

    private boolean n() {
        return com.meizu.cloud.pushsdk.util.b.l(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName());
    }

    private boolean o() {
        return com.meizu.cloud.pushsdk.util.b.m(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName());
    }

    private boolean p() {
        Boolean bool = this.f19826m.get(this.f19810e + OpenAccountUIConstants.UNDER_LINE + this.f19824k);
        boolean z2 = bool == null || bool.booleanValue();
        DebugLogger.e("Strategy", "isSyncPushStatus " + this.f19810e + " switch type->" + this.f19824k + " flag->" + z2);
        return z2;
    }

    private boolean s() {
        return com.meizu.cloud.pushsdk.util.b.f(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName());
    }

    private void t() {
        int i2 = this.f19824k;
        if (i2 == 0 || i2 == 1) {
            PlatformMessageSender.a(this.f19807b, i2, this.f19825l, this.f19810e);
        } else {
            if (i2 != 3) {
                return;
            }
            PlatformMessageSender.a(this.f19807b, 0, this.f19825l, this.f19810e);
            PlatformMessageSender.a(this.f19807b, 1, this.f19825l, this.f19810e);
        }
    }

    private boolean u() {
        return com.meizu.cloud.pushsdk.util.b.k(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName());
    }

    public void b(int i2) {
        this.f19824k = i2;
    }

    public void d(String str) {
        this.f19823j = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected Intent h() {
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f19808c);
        intent.putExtra(com.alipay.sdk.m.l.b.f9441h, this.f19809d);
        intent.putExtra("strategy_package_name", this.f19807b.getPackageName());
        intent.putExtra(PushConstants.REGISTER_STATUS_PUSH_ID, this.f19823j);
        intent.putExtra("strategy_type", j());
        intent.putExtra("strategy_child_type", this.f19824k);
        intent.putExtra("strategy_params", this.f19825l ? "1" : "0");
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected int j() {
        return 16;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: m, reason: merged with bridge method [inline-methods] */
    public PushSwitchStatus a() {
        String str;
        PushSwitchStatus pushSwitchStatus = new PushSwitchStatus();
        pushSwitchStatus.setCode("20001");
        if (TextUtils.isEmpty(this.f19808c)) {
            str = "appId not empty";
        } else {
            if (!TextUtils.isEmpty(this.f19809d)) {
                if (TextUtils.isEmpty(this.f19823j)) {
                    str = "pushId not empty";
                }
                return pushSwitchStatus;
            }
            str = "appKey not empty";
        }
        pushSwitchStatus.setMessage(str);
        return pushSwitchStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: q, reason: merged with bridge method [inline-methods] */
    public PushSwitchStatus c() {
        int i2 = this.f19824k;
        if (i2 == 0) {
            e(this.f19825l);
            return null;
        }
        if (i2 == 1) {
            f(this.f19825l);
            return null;
        }
        if (i2 != 2 && i2 != 3) {
            return null;
        }
        d(this.f19825l);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: r, reason: merged with bridge method [inline-methods] */
    public PushSwitchStatus e() {
        PushSwitchStatus pushSwitchStatus = new PushSwitchStatus();
        pushSwitchStatus.setPushId(this.f19823j);
        pushSwitchStatus.setCode("200");
        com.meizu.cloud.pushsdk.e.b.c<String> cVarA = a(pushSwitchStatus);
        if (cVarA != null) {
            if (cVarA.c()) {
                PushSwitchStatus pushSwitchStatus2 = new PushSwitchStatus(cVarA.b());
                DebugLogger.e("Strategy", "network pushSwitchStatus " + pushSwitchStatus2);
                if ("200".equals(pushSwitchStatus.getCode())) {
                    b(false);
                    DebugLogger.e("Strategy", "update local switch preference");
                    pushSwitchStatus.setSwitchNotificationMessage(pushSwitchStatus2.isSwitchNotificationMessage());
                    pushSwitchStatus.setSwitchThroughMessage(pushSwitchStatus2.isSwitchThroughMessage());
                    e(pushSwitchStatus2.isSwitchNotificationMessage());
                    f(pushSwitchStatus2.isSwitchThroughMessage());
                }
            } else {
                com.meizu.cloud.pushsdk.e.c.a aVarA = cVarA.a();
                if (aVarA.c() != null) {
                    DebugLogger.e("Strategy", "status code=" + aVarA.b() + " data=" + aVarA.c());
                }
                pushSwitchStatus.setCode(String.valueOf(aVarA.b()));
                pushSwitchStatus.setMessage(aVarA.a());
                DebugLogger.e("Strategy", "pushSwitchStatus " + pushSwitchStatus);
            }
        }
        DebugLogger.e("Strategy", "enableRpc " + this.f19813h + " isSupportRemoteInvoke " + this.f19812g);
        if (this.f19813h && !this.f19812g) {
            t();
        }
        return pushSwitchStatus;
    }

    public f(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f19813h = z2;
    }

    private void d(boolean z2) {
        com.meizu.cloud.pushsdk.util.b.b(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), z2);
        com.meizu.cloud.pushsdk.util.b.c(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), z2);
    }

    private void e(boolean z2) {
        com.meizu.cloud.pushsdk.util.b.b(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), z2);
    }

    public void c(boolean z2) {
        this.f19825l = z2;
    }

    public f(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f19824k = 0;
        this.f19826m = new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    public void b(PushSwitchStatus pushSwitchStatus) {
        PlatformMessageSender.a(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), pushSwitchStatus);
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected boolean d() {
        return (TextUtils.isEmpty(this.f19808c) || TextUtils.isEmpty(this.f19809d) || TextUtils.isEmpty(this.f19823j)) ? false : true;
    }

    public f(Context context, String str, String str2, String str3, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, str, str2, aVar, scheduledExecutorService);
        this.f19823j = str3;
    }

    private void b(boolean z2) {
        this.f19826m.put(this.f19810e + OpenAccountUIConstants.UNDER_LINE + this.f19824k, Boolean.valueOf(z2));
    }
}
