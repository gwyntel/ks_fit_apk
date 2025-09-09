package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

/* loaded from: classes3.dex */
final class zae implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f12923a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Intent f12924b;

    zae(Context context, Intent intent) {
        this.f12923a = context;
        this.f12924b = intent;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        try {
            this.f12923a.startActivity(this.f12924b);
        } catch (ActivityNotFoundException e2) {
            Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", e2);
        }
    }
}
