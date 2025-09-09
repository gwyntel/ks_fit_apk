package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.internal.base.zau;

@SuppressLint({"HandlerLeak"})
/* loaded from: classes3.dex */
final class zad extends zau {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ GoogleApiAvailability f12901a;
    private final Context zab;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zad(GoogleApiAvailability googleApiAvailability, Context context) {
        super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
        this.f12901a = googleApiAvailability;
        this.zab = context.getApplicationContext();
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) throws Resources.NotFoundException {
        int i2 = message.what;
        if (i2 != 1) {
            Log.w("GoogleApiAvailability", "Don't know how to handle this message: " + i2);
            return;
        }
        GoogleApiAvailability googleApiAvailability = this.f12901a;
        int iIsGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this.zab);
        if (googleApiAvailability.isUserResolvableError(iIsGooglePlayServicesAvailable)) {
            this.f12901a.showErrorNotification(this.zab, iIsGooglePlayServicesAvailable);
        }
    }
}
