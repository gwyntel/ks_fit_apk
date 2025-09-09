package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult;

/* loaded from: classes3.dex */
final class zab implements PendingResult.StatusListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Batch f12785a;

    zab(Batch batch) {
        this.f12785a = batch;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        synchronized (this.f12785a.zai) {
            try {
                if (this.f12785a.isCanceled()) {
                    return;
                }
                if (status.isCanceled()) {
                    this.f12785a.zag = true;
                } else if (!status.isSuccess()) {
                    this.f12785a.zaf = true;
                }
                Batch batch = this.f12785a;
                batch.zae--;
                Batch batch2 = this.f12785a;
                if (batch2.zae == 0) {
                    if (batch2.zag) {
                        super/*com.google.android.gms.common.api.internal.BasePendingResult*/.cancel();
                    } else {
                        Status status2 = batch2.zaf ? new Status(13) : Status.RESULT_SUCCESS;
                        Batch batch3 = this.f12785a;
                        batch3.setResult(new BatchResult(status2, batch3.zah));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
