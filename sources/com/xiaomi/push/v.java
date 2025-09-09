package com.xiaomi.push;

import android.content.Context;
import java.io.File;

/* loaded from: classes4.dex */
class v extends u {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Runnable f24639a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    v(Context context, File file, Runnable runnable) {
        super(context, file, null);
        this.f24639a = runnable;
    }

    @Override // com.xiaomi.push.u
    protected void a(Context context) {
        Runnable runnable = this.f24639a;
        if (runnable != null) {
            runnable.run();
        }
    }
}
