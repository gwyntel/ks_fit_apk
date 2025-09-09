package com.google.android.gms.measurement.internal;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Preconditions;

@VisibleForTesting
/* loaded from: classes3.dex */
public final class zzmx {

    /* renamed from: a, reason: collision with root package name */
    final Context f13321a;

    @VisibleForTesting
    public zzmx(Context context) {
        Preconditions.checkNotNull(context);
        Context applicationContext = context.getApplicationContext();
        Preconditions.checkNotNull(applicationContext);
        this.f13321a = applicationContext;
    }
}
