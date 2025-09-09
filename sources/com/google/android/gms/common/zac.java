package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;

/* loaded from: classes3.dex */
final class zac implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Activity f12897a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f12898b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ ActivityResultLauncher f12899c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ GoogleApiAvailability f12900d;

    zac(GoogleApiAvailability googleApiAvailability, Activity activity, int i2, ActivityResultLauncher activityResultLauncher) {
        this.f12900d = googleApiAvailability;
        this.f12897a = activity;
        this.f12898b = i2;
        this.f12899c = activityResultLauncher;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        dialogInterface.dismiss();
        PendingIntent errorResolutionPendingIntent = this.f12900d.getErrorResolutionPendingIntent(this.f12897a, this.f12898b, 0);
        if (errorResolutionPendingIntent == null) {
            return;
        }
        this.f12899c.launch(new IntentSenderRequest.Builder(errorResolutionPendingIntent.getIntentSender()).build());
    }
}
