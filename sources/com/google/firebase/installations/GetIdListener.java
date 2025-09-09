package com.google.firebase.installations;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.installations.local.PersistedInstallationEntry;

/* loaded from: classes3.dex */
class GetIdListener implements StateListener {

    /* renamed from: a, reason: collision with root package name */
    final TaskCompletionSource f15082a;

    public GetIdListener(TaskCompletionSource<String> taskCompletionSource) {
        this.f15082a = taskCompletionSource;
    }

    @Override // com.google.firebase.installations.StateListener
    public boolean onException(Exception exc) {
        return false;
    }

    @Override // com.google.firebase.installations.StateListener
    public boolean onStateReached(PersistedInstallationEntry persistedInstallationEntry) {
        if (!persistedInstallationEntry.isUnregistered() && !persistedInstallationEntry.isRegistered() && !persistedInstallationEntry.isErrored()) {
            return false;
        }
        this.f15082a.trySetResult(persistedInstallationEntry.getFirebaseInstallationId());
        return true;
    }
}
