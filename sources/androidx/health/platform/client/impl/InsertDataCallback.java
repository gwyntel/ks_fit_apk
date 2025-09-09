package androidx.health.platform.client.impl;

import androidx.health.platform.client.error.ErrorStatus;
import androidx.health.platform.client.impl.error.ErrorStatusConverterKt;
import androidx.health.platform.client.response.InsertDataResponse;
import androidx.health.platform.client.service.IInsertDataCallback;
import com.google.common.util.concurrent.SettableFuture;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Landroidx/health/platform/client/impl/InsertDataCallback;", "Landroidx/health/platform/client/service/IInsertDataCallback$Stub;", "resultFuture", "Lcom/google/common/util/concurrent/SettableFuture;", "", "", "(Lcom/google/common/util/concurrent/SettableFuture;)V", "onError", "", "error", "Landroidx/health/platform/client/error/ErrorStatus;", "onSuccess", "response", "Landroidx/health/platform/client/response/InsertDataResponse;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class InsertDataCallback extends IInsertDataCallback.Stub {

    @NotNull
    private final SettableFuture<List<String>> resultFuture;

    public InsertDataCallback(@NotNull SettableFuture<List<String>> resultFuture) {
        Intrinsics.checkNotNullParameter(resultFuture, "resultFuture");
        this.resultFuture = resultFuture;
    }

    @Override // androidx.health.platform.client.service.IInsertDataCallback
    public void onError(@NotNull ErrorStatus error) {
        Intrinsics.checkNotNullParameter(error, "error");
        this.resultFuture.setException(ErrorStatusConverterKt.toException(error));
    }

    @Override // androidx.health.platform.client.service.IInsertDataCallback
    public void onSuccess(@NotNull InsertDataResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        this.resultFuture.set(response.getDataPointUids());
    }
}
