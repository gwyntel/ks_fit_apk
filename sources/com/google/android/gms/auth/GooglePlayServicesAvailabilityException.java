package com.google.android.gms.auth;

import android.content.Intent;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class GooglePlayServicesAvailabilityException extends UserRecoverableAuthException {
    private final int zza;

    GooglePlayServicesAvailabilityException(int i2, @Nullable String str, @Nullable Intent intent) {
        super(str, intent);
        this.zza = i2;
    }

    public int getConnectionStatusCode() {
        return this.zza;
    }
}
