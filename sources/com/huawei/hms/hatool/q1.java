package com.huawei.hms.hatool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.UserManager;
import com.umeng.analytics.pro.ay;

/* loaded from: classes4.dex */
public class q1 {

    /* renamed from: c, reason: collision with root package name */
    private static q1 f16458c = new q1();

    /* renamed from: a, reason: collision with root package name */
    private boolean f16459a = false;

    /* renamed from: b, reason: collision with root package name */
    private Context f16460b = q0.i();

    private q1() {
    }

    public static q1 b() {
        return f16458c;
    }

    @TargetApi(24)
    public boolean a() {
        boolean zIsUserUnlocked;
        if (!this.f16459a) {
            Context context = this.f16460b;
            if (context == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                UserManager userManager = (UserManager) context.getSystemService(ay.f21378m);
                if (userManager != null) {
                    zIsUserUnlocked = userManager.isUserUnlocked();
                } else {
                    this.f16459a = false;
                }
            } else {
                zIsUserUnlocked = true;
            }
            this.f16459a = zIsUserUnlocked;
        }
        return this.f16459a;
    }
}
