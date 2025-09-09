package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/* loaded from: classes3.dex */
public final class zbn {

    @Nullable
    private static zbn zbd;

    /* renamed from: a, reason: collision with root package name */
    final Storage f12656a;

    /* renamed from: b, reason: collision with root package name */
    GoogleSignInAccount f12657b;

    /* renamed from: c, reason: collision with root package name */
    GoogleSignInOptions f12658c;

    private zbn(Context context) {
        Storage storage = Storage.getInstance(context);
        this.f12656a = storage;
        this.f12657b = storage.getSavedDefaultGoogleSignInAccount();
        this.f12658c = storage.getSavedDefaultGoogleSignInOptions();
    }

    public static synchronized zbn zbc(@NonNull Context context) {
        return zbf(context.getApplicationContext());
    }

    private static synchronized zbn zbf(Context context) {
        zbn zbnVar = zbd;
        if (zbnVar != null) {
            return zbnVar;
        }
        zbn zbnVar2 = new zbn(context);
        zbd = zbnVar2;
        return zbnVar2;
    }

    @Nullable
    public final synchronized GoogleSignInAccount zba() {
        return this.f12657b;
    }

    @Nullable
    public final synchronized GoogleSignInOptions zbb() {
        return this.f12658c;
    }

    public final synchronized void zbd() {
        this.f12656a.clear();
        this.f12657b = null;
        this.f12658c = null;
    }

    public final synchronized void zbe(GoogleSignInOptions googleSignInOptions, GoogleSignInAccount googleSignInAccount) {
        this.f12656a.saveDefaultGoogleSignInAccount(googleSignInAccount, googleSignInOptions);
        this.f12657b = googleSignInAccount;
        this.f12658c = googleSignInOptions;
    }
}
