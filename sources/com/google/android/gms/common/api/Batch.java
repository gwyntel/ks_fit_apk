package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class Batch extends BasePendingResult<BatchResult> {
    private int zae;
    private boolean zaf;
    private boolean zag;
    private final PendingResult[] zah;
    private final Object zai;

    public static final class Builder {
        private final List zaa = new ArrayList();
        private final GoogleApiClient zab;

        public Builder(@NonNull GoogleApiClient googleApiClient) {
            this.zab = googleApiClient;
        }

        @NonNull
        @ResultIgnorabilityUnspecified
        public <R extends Result> BatchResultToken<R> add(@NonNull PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.zaa.size());
            this.zaa.add(pendingResult);
            return batchResultToken;
        }

        @NonNull
        public Batch build() {
            return new Batch(this.zaa, this.zab, null);
        }
    }

    /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zac zacVar) {
        super(googleApiClient);
        this.zai = new Object();
        int size = list.size();
        this.zae = size;
        PendingResult[] pendingResultArr = new PendingResult[size];
        this.zah = pendingResultArr;
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.RESULT_SUCCESS, pendingResultArr));
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            PendingResult pendingResult = (PendingResult) list.get(i2);
            this.zah[i2] = pendingResult;
            pendingResult.addStatusListener(new zab(this));
        }
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult, com.google.android.gms.common.api.PendingResult
    public void cancel() {
        super.cancel();
        int i2 = 0;
        while (true) {
            PendingResult[] pendingResultArr = this.zah;
            if (i2 >= pendingResultArr.length) {
                return;
            }
            pendingResultArr[i2].cancel();
            i2++;
        }
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    @NonNull
    public BatchResult createFailedResult(@NonNull Status status) {
        return new BatchResult(status, this.zah);
    }
}
