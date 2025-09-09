package com.meizu.cloud.pushsdk.platform.d;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class d extends c<SubAliasStatus> {

    /* renamed from: j, reason: collision with root package name */
    private String f19816j;

    /* renamed from: k, reason: collision with root package name */
    private int f19817k;

    /* renamed from: l, reason: collision with root package name */
    private String f19818l;

    /* renamed from: m, reason: collision with root package name */
    private final Map<String, Boolean> f19819m;

    public d(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, null, aVar, scheduledExecutorService);
    }

    private void f(String str) {
        com.meizu.cloud.pushsdk.util.b.j(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), str);
    }

    private boolean n() {
        return !this.f19812g && PushConstants.PUSH_PACKAGE_NAME.equals(this.f19810e);
    }

    private boolean o() {
        Boolean bool = this.f19819m.get(this.f19810e + OpenAccountUIConstants.UNDER_LINE + this.f19817k);
        return bool == null || bool.booleanValue();
    }

    private String p() {
        return com.meizu.cloud.pushsdk.util.b.a(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName());
    }

    public void b(int i2) {
        this.f19817k = i2;
    }

    public void d(String str) {
        this.f19818l = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected Intent h() {
        if (this.f19817k == 2) {
            return null;
        }
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f19808c);
        intent.putExtra(com.alipay.sdk.m.l.b.f9441h, this.f19809d);
        intent.putExtra("strategy_package_name", this.f19807b.getPackageName());
        intent.putExtra(PushConstants.REGISTER_STATUS_PUSH_ID, this.f19816j);
        intent.putExtra("strategy_type", j());
        intent.putExtra("strategy_child_type", this.f19817k);
        intent.putExtra("strategy_params", this.f19818l);
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected int j() {
        return 8;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: m, reason: merged with bridge method [inline-methods] */
    public SubAliasStatus a() {
        String str;
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        subAliasStatus.setCode("20001");
        if (TextUtils.isEmpty(this.f19808c)) {
            str = "appId not empty";
        } else {
            if (!TextUtils.isEmpty(this.f19809d)) {
                if (TextUtils.isEmpty(this.f19816j)) {
                    str = "pushId not empty";
                }
                return subAliasStatus;
            }
            str = "appKey not empty";
        }
        subAliasStatus.setMessage(str);
        return subAliasStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: q, reason: merged with bridge method [inline-methods] */
    public SubAliasStatus c() {
        if (this.f19817k != 2) {
            return null;
        }
        SubAliasStatus subAliasStatus = new SubAliasStatus();
        subAliasStatus.setCode("200");
        subAliasStatus.setPushId(this.f19816j);
        subAliasStatus.setAlias(p());
        subAliasStatus.setMessage("check alias success");
        return subAliasStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0091  */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: r, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.meizu.cloud.pushsdk.platform.message.SubAliasStatus e() {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.platform.d.d.e():com.meizu.cloud.pushsdk.platform.message.SubAliasStatus");
    }

    public d(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f19813h = z2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(SubAliasStatus subAliasStatus) {
        PlatformMessageSender.a(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), subAliasStatus);
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected boolean d() {
        return (TextUtils.isEmpty(this.f19808c) || TextUtils.isEmpty(this.f19809d) || TextUtils.isEmpty(this.f19816j)) ? false : true;
    }

    public void e(String str) {
        this.f19816j = str;
    }

    public d(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f19819m = new HashMap();
    }

    private void b(boolean z2) {
        this.f19819m.put(this.f19810e + OpenAccountUIConstants.UNDER_LINE + this.f19817k, Boolean.valueOf(z2));
    }

    public d(Context context, String str, String str2, String str3, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, str, str2, aVar, scheduledExecutorService);
        this.f19816j = str3;
    }
}
