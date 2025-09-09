package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zacy implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Result f12761a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zada f12762b;

    zacy(zada zadaVar, Result result) {
        this.f12762b = zadaVar;
        this.f12761a = result;
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        GoogleApiClient googleApiClient;
        try {
            try {
                ThreadLocal threadLocal = BasePendingResult.f12690c;
                threadLocal.set(Boolean.TRUE);
                PendingResult pendingResultOnSuccess = ((ResultTransform) Preconditions.checkNotNull(this.f12762b.zaa)).onSuccess(this.f12761a);
                zada zadaVar = this.f12762b;
                zadaVar.zah.sendMessage(zadaVar.zah.obtainMessage(0, pendingResultOnSuccess));
                threadLocal.set(Boolean.FALSE);
                zada zadaVar2 = this.f12762b;
                zada.zan(this.f12761a);
                googleApiClient = (GoogleApiClient) this.f12762b.zag.get();
                if (googleApiClient == null) {
                    return;
                }
            } catch (RuntimeException e2) {
                zada zadaVar3 = this.f12762b;
                zadaVar3.zah.sendMessage(zadaVar3.zah.obtainMessage(1, e2));
                BasePendingResult.f12690c.set(Boolean.FALSE);
                zada zadaVar4 = this.f12762b;
                zada.zan(this.f12761a);
                googleApiClient = (GoogleApiClient) this.f12762b.zag.get();
                if (googleApiClient == null) {
                    return;
                }
            }
            googleApiClient.zap(this.f12762b);
        } catch (Throwable th) {
            BasePendingResult.f12690c.set(Boolean.FALSE);
            zada zadaVar5 = this.f12762b;
            zada.zan(this.f12761a);
            GoogleApiClient googleApiClient2 = (GoogleApiClient) this.f12762b.zag.get();
            if (googleApiClient2 != null) {
                googleApiClient2.zap(this.f12762b);
            }
            throw th;
        }
    }
}
