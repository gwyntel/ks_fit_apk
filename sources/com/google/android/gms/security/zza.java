package com.google.android.gms.security;

import android.content.Context;
import android.os.AsyncTask;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

/* loaded from: classes3.dex */
final class zza extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f13329a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ProviderInstaller.ProviderInstallListener f13330b;

    zza(Context context, ProviderInstaller.ProviderInstallListener providerInstallListener) {
        this.f13329a = context;
        this.f13330b = providerInstallListener;
    }

    @Override // android.os.AsyncTask
    protected final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        try {
            ProviderInstaller.installIfNeeded(this.f13329a);
            return 0;
        } catch (GooglePlayServicesNotAvailableException e2) {
            return Integer.valueOf(e2.errorCode);
        } catch (GooglePlayServicesRepairableException e3) {
            return Integer.valueOf(e3.getConnectionStatusCode());
        }
    }

    @Override // android.os.AsyncTask
    protected final /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
        Integer num = (Integer) obj;
        if (num.intValue() == 0) {
            this.f13330b.onProviderInstalled();
            return;
        }
        this.f13330b.onProviderInstallFailed(num.intValue(), ProviderInstaller.zza.getErrorResolutionIntent(this.f13329a, num.intValue(), AlinkConstants.KEY_PI));
    }
}
