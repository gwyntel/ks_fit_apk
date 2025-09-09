package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class BatchResult implements Result {
    private final Status zaa;
    private final PendingResult[] zab;

    BatchResult(Status status, PendingResult[] pendingResultArr) {
        this.zaa = status;
        this.zab = pendingResultArr;
    }

    @Override // com.google.android.gms.common.api.Result
    @NonNull
    public Status getStatus() {
        return this.zaa;
    }

    @NonNull
    @ResultIgnorabilityUnspecified
    public <R extends Result> R take(@NonNull BatchResultToken<R> batchResultToken) {
        Preconditions.checkArgument(batchResultToken.f12686a < this.zab.length, "The result token does not belong to this batch");
        return (R) this.zab[batchResultToken.f12686a].await(0L, TimeUnit.MILLISECONDS);
    }
}
