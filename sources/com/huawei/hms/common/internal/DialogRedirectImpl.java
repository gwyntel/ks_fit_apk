package com.huawei.hms.common.internal;

import android.app.Activity;
import android.content.Intent;

/* loaded from: classes4.dex */
public class DialogRedirectImpl extends DialogRedirect {

    /* renamed from: a, reason: collision with root package name */
    private final Activity f15972a;

    /* renamed from: b, reason: collision with root package name */
    private final int f15973b;

    /* renamed from: c, reason: collision with root package name */
    private final Intent f15974c;

    DialogRedirectImpl(Intent intent, Activity activity, int i2) {
        this.f15974c = intent;
        this.f15972a = activity;
        this.f15973b = i2;
    }

    @Override // com.huawei.hms.common.internal.DialogRedirect
    public final void redirect() {
        Intent intent = this.f15974c;
        if (intent != null) {
            this.f15972a.startActivityForResult(intent, this.f15973b);
        }
    }
}
