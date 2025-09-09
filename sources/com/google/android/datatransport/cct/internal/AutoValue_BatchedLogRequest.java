package com.google.android.datatransport.cct.internal;

import androidx.annotation.NonNull;
import com.alipay.sdk.m.u.i;
import com.google.firebase.encoders.annotations.Encodable;
import java.util.List;

/* loaded from: classes3.dex */
final class AutoValue_BatchedLogRequest extends BatchedLogRequest {
    private final List<LogRequest> logRequests;

    AutoValue_BatchedLogRequest(List list) {
        if (list == null) {
            throw new NullPointerException("Null logRequests");
        }
        this.logRequests = list;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BatchedLogRequest) {
            return this.logRequests.equals(((BatchedLogRequest) obj).getLogRequests());
        }
        return false;
    }

    @Override // com.google.android.datatransport.cct.internal.BatchedLogRequest
    @NonNull
    @Encodable.Field(name = "logRequest")
    public List<LogRequest> getLogRequests() {
        return this.logRequests;
    }

    public int hashCode() {
        return this.logRequests.hashCode() ^ 1000003;
    }

    public String toString() {
        return "BatchedLogRequest{logRequests=" + this.logRequests + i.f9804d;
    }
}
