package com.google.android.gms.fido.u2f;

import android.app.Activity;
import android.content.IntentSender;
import androidx.annotation.NonNull;

@Deprecated
/* loaded from: classes3.dex */
public interface U2fPendingIntent {
    boolean hasPendingIntent();

    void launchPendingIntent(@NonNull Activity activity, int i2) throws IntentSender.SendIntentException;
}
