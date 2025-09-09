package com.meizu.cloud.pushsdk.platform.d;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.PlatformMessageSender;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class e extends c<SubTagsStatus> {

    /* renamed from: j, reason: collision with root package name */
    private String f19820j;

    /* renamed from: k, reason: collision with root package name */
    private int f19821k;

    /* renamed from: l, reason: collision with root package name */
    private String f19822l;

    public e(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, null, null, null, aVar, scheduledExecutorService);
    }

    public void b(int i2) {
        this.f19821k = i2;
    }

    public void d(String str) {
        this.f19820j = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected Intent h() {
        Intent intent = new Intent();
        intent.putExtra("app_id", this.f19808c);
        intent.putExtra(com.alipay.sdk.m.l.b.f9441h, this.f19809d);
        intent.putExtra("strategy_package_name", this.f19807b.getPackageName());
        intent.putExtra(PushConstants.REGISTER_STATUS_PUSH_ID, this.f19820j);
        intent.putExtra("strategy_type", j());
        intent.putExtra("strategy_child_type", this.f19821k);
        intent.putExtra("strategy_params", this.f19822l);
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected int j() {
        return 4;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: m, reason: merged with bridge method [inline-methods] */
    public SubTagsStatus a() {
        String str;
        SubTagsStatus subTagsStatus = new SubTagsStatus();
        subTagsStatus.setCode("20001");
        if (TextUtils.isEmpty(this.f19808c)) {
            str = "appId not empty";
        } else {
            if (!TextUtils.isEmpty(this.f19809d)) {
                if (TextUtils.isEmpty(this.f19820j)) {
                    str = "pushId not empty";
                }
                return subTagsStatus;
            }
            str = "appKey not empty";
        }
        subTagsStatus.setMessage(str);
        return subTagsStatus;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: n, reason: merged with bridge method [inline-methods] */
    public SubTagsStatus c() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: o, reason: merged with bridge method [inline-methods] */
    public SubTagsStatus e() {
        StringBuilder sb;
        String str;
        SubTagsStatus subTagsStatus = new SubTagsStatus();
        int i2 = this.f19821k;
        com.meizu.cloud.pushsdk.e.b.c cVarB = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? null : this.f19811f.b(this.f19808c, this.f19809d, this.f19820j) : this.f19811f.c(this.f19808c, this.f19809d, this.f19820j) : this.f19811f.f(this.f19808c, this.f19809d, this.f19820j, this.f19822l) : this.f19811f.c(this.f19808c, this.f19809d, this.f19820j, this.f19822l);
        if (cVarB == null) {
            DebugLogger.e("Strategy", "network anResponse is null");
            return null;
        }
        if (cVarB.c()) {
            subTagsStatus = new SubTagsStatus((String) cVarB.b());
            sb = new StringBuilder();
            str = "network subTagsStatus ";
        } else {
            com.meizu.cloud.pushsdk.e.c.a aVarA = cVarB.a();
            if (aVarA.c() != null) {
                DebugLogger.e("Strategy", "status code=" + aVarA.b() + " data=" + aVarA.c());
            }
            subTagsStatus.setCode(String.valueOf(aVarA.b()));
            subTagsStatus.setMessage(aVarA.a());
            sb = new StringBuilder();
            str = "subTagsStatus ";
        }
        sb.append(str);
        sb.append(subTagsStatus);
        DebugLogger.e("Strategy", sb.toString());
        return subTagsStatus;
    }

    public e(Context context, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, aVar, scheduledExecutorService);
        this.f19813h = z2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.platform.d.c
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(SubTagsStatus subTagsStatus) {
        PlatformMessageSender.a(this.f19807b, !TextUtils.isEmpty(this.f19810e) ? this.f19810e : this.f19807b.getPackageName(), subTagsStatus);
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected boolean d() {
        return (TextUtils.isEmpty(this.f19808c) || TextUtils.isEmpty(this.f19809d) || TextUtils.isEmpty(this.f19820j)) ? false : true;
    }

    public void e(String str) {
        this.f19822l = str;
    }

    public e(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f19821k = 3;
    }

    public e(Context context, String str, String str2, String str3, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        this(context, str, str2, aVar, scheduledExecutorService);
        this.f19820j = str3;
    }
}
