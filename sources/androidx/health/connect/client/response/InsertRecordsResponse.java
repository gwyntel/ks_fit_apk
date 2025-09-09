package androidx.health.connect.client.response;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Landroidx/health/connect/client/response/InsertRecordsResponse;", "", "recordIdsList", "", "", "(Ljava/util/List;)V", "getRecordIdsList", "()Ljava/util/List;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class InsertRecordsResponse {

    @NotNull
    private final List<String> recordIdsList;

    public InsertRecordsResponse(@NotNull List<String> recordIdsList) {
        Intrinsics.checkNotNullParameter(recordIdsList, "recordIdsList");
        this.recordIdsList = recordIdsList;
    }

    @NotNull
    public final List<String> getRecordIdsList() {
        return this.recordIdsList;
    }
}
