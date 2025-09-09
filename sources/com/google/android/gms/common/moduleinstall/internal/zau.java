package com.google.android.gms.common.moduleinstall.internal;

import androidx.annotation.Nullable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstallResponse;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zau extends zaa {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AtomicReference f12867a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ TaskCompletionSource f12868b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ InstallStatusListener f12869c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ zay f12870d;

    zau(zay zayVar, AtomicReference atomicReference, TaskCompletionSource taskCompletionSource, InstallStatusListener installStatusListener) {
        this.f12870d = zayVar;
        this.f12867a = atomicReference;
        this.f12868b = taskCompletionSource;
        this.f12869c = installStatusListener;
    }

    @Override // com.google.android.gms.common.moduleinstall.internal.zaa, com.google.android.gms.common.moduleinstall.internal.zae
    public final void zad(Status status, @Nullable ModuleInstallResponse moduleInstallResponse) {
        if (moduleInstallResponse != null) {
            this.f12867a.set(moduleInstallResponse);
        }
        TaskUtil.trySetResultOrApiException(status, null, this.f12868b);
        if (!status.isSuccess() || (moduleInstallResponse != null && moduleInstallResponse.zaa())) {
            this.f12870d.doUnregisterEventListener(ListenerHolders.createListenerKey(this.f12869c, InstallStatusListener.class.getSimpleName()), 27306);
        }
    }
}
