package com.meizu.cloud.pushsdk.platform.d;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public class a extends c {

    /* renamed from: j, reason: collision with root package name */
    private int[] f19803j;

    /* renamed from: k, reason: collision with root package name */
    private int f19804k;

    /* renamed from: l, reason: collision with root package name */
    private String f19805l;

    public a(Context context, String str, String str2, com.meizu.cloud.pushsdk.platform.c.a aVar, ScheduledExecutorService scheduledExecutorService) {
        super(context, str, str2, aVar, scheduledExecutorService);
        this.f19812g = MinSdkChecker.isSupportSetDrawableSmallIcon();
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected BasicPushStatus a() {
        return null;
    }

    public void b(int i2) {
        this.f19804k = i2;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected BasicPushStatus c() {
        return null;
    }

    public void d(String str) {
        this.f19805l = str;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected BasicPushStatus e() {
        int i2 = this.f19804k;
        if (i2 == 0) {
            if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
                DebugLogger.e("Strategy", "android 6.0 blow so cancel all by context");
                com.meizu.cloud.pushsdk.notification.g.b.a(this.f19807b);
            }
            com.meizu.cloud.pushsdk.notification.g.b.a(this.f19807b, this.f19810e);
            return null;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                return null;
            }
            com.meizu.cloud.pushsdk.notification.g.b.a(this.f19807b, this.f19810e, this.f19805l);
            return null;
        }
        int[] iArr = this.f19803j;
        if (iArr == null) {
            return null;
        }
        for (int i3 : iArr) {
            DebugLogger.e("Strategy", "clear notifyId " + i3);
            com.meizu.cloud.pushsdk.notification.g.b.a(this.f19807b, this.f19810e, i3);
        }
        return null;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected Intent h() {
        Intent intent = new Intent();
        intent.putExtra("strategy_package_name", this.f19807b.getPackageName());
        intent.putExtra("strategy_type", j());
        intent.putExtra("strategy_child_type", this.f19804k);
        int i2 = this.f19804k;
        if (i2 == 2) {
            intent.putExtra("strategy_params", this.f19805l);
            return intent;
        }
        if (i2 == 1) {
            return null;
        }
        return intent;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected Intent[] i() {
        int[] iArr = this.f19803j;
        if (iArr == null) {
            return null;
        }
        Intent[] intentArr = new Intent[iArr.length];
        for (int i2 = 0; i2 < this.f19803j.length; i2++) {
            DebugLogger.i("Strategy", "send notifyId " + this.f19803j[i2] + " to PushManagerService");
            Intent intent = new Intent();
            intent.putExtra("strategy_package_name", this.f19807b.getPackageName());
            intent.putExtra("strategy_type", j());
            intent.putExtra("strategy_child_type", this.f19804k);
            intent.putExtra("strategy_params", "" + this.f19803j[i2]);
            intentArr[i2] = intent;
        }
        return intentArr;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected int j() {
        return 64;
    }

    public a(Context context, ScheduledExecutorService scheduledExecutorService, boolean z2) {
        this(context, null, null, null, scheduledExecutorService);
        this.f19813h = z2;
    }

    public void a(int... iArr) {
        this.f19803j = iArr;
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected void b(BasicPushStatus basicPushStatus) {
    }

    @Override // com.meizu.cloud.pushsdk.platform.d.c
    protected boolean d() {
        int i2 = this.f19804k;
        if (i2 == 0) {
            return true;
        }
        int[] iArr = this.f19803j;
        if (iArr == null || iArr.length <= 0 || i2 != 1) {
            return i2 == 2 && !TextUtils.isEmpty(this.f19805l);
        }
        return true;
    }
}
