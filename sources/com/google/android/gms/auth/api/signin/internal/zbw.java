package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.google.android.gms.common.api.GoogleApiClient;

/* loaded from: classes3.dex */
final class zbw implements LoaderManager.LoaderCallbacks {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ SignInHubActivity f12659a;

    /* synthetic */ zbw(SignInHubActivity signInHubActivity, zbv zbvVar) {
        this.f12659a = signInHubActivity;
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i2, Bundle bundle) {
        return new zbc(this.f12659a, GoogleApiClient.getAllClients());
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
        SignInHubActivity signInHubActivity = this.f12659a;
        signInHubActivity.setResult(signInHubActivity.zbe, signInHubActivity.zbf);
        this.f12659a.finish();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {
    }
}
