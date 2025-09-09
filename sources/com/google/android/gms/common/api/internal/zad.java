package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
abstract class zad extends zac {

    /* renamed from: a, reason: collision with root package name */
    protected final TaskCompletionSource f12764a;

    public zad(int i2, TaskCompletionSource taskCompletionSource) {
        super(i2);
        this.f12764a = taskCompletionSource;
    }

    protected abstract void zac(zabq zabqVar);

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zad(@NonNull Status status) {
        this.f12764a.trySetException(new ApiException(status));
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zae(@NonNull Exception exc) {
        this.f12764a.trySetException(exc);
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public final void zaf(zabq zabqVar) throws DeadObjectException {
        try {
            zac(zabqVar);
        } catch (DeadObjectException e2) {
            zad(zai.a(e2));
            throw e2;
        } catch (RemoteException e3) {
            zad(zai.a(e3));
        } catch (RuntimeException e4) {
            this.f12764a.trySetException(e4);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zai
    public void zag(@NonNull zaad zaadVar, boolean z2) {
    }
}
