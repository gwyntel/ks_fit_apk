package androidx.health.platform.client.impl;

import androidx.health.platform.client.error.ErrorStatus;
import androidx.health.platform.client.impl.error.ErrorStatusConverterKt;
import androidx.health.platform.client.proto.ResponseProto;
import androidx.health.platform.client.response.GetChangesResponse;
import androidx.health.platform.client.service.IGetChangesCallback;
import com.google.common.util.concurrent.SettableFuture;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/health/platform/client/impl/GetChangesCallback;", "Landroidx/health/platform/client/service/IGetChangesCallback$Stub;", "resultFuture", "Lcom/google/common/util/concurrent/SettableFuture;", "Landroidx/health/platform/client/proto/ResponseProto$GetChangesResponse;", "(Lcom/google/common/util/concurrent/SettableFuture;)V", "onError", "", "error", "Landroidx/health/platform/client/error/ErrorStatus;", "onSuccess", "response", "Landroidx/health/platform/client/response/GetChangesResponse;", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class GetChangesCallback extends IGetChangesCallback.Stub {

    @NotNull
    private final SettableFuture<ResponseProto.GetChangesResponse> resultFuture;

    public GetChangesCallback(@NotNull SettableFuture<ResponseProto.GetChangesResponse> resultFuture) {
        Intrinsics.checkNotNullParameter(resultFuture, "resultFuture");
        this.resultFuture = resultFuture;
    }

    @Override // androidx.health.platform.client.service.IGetChangesCallback
    public void onError(@NotNull ErrorStatus error) {
        Intrinsics.checkNotNullParameter(error, "error");
        this.resultFuture.setException(ErrorStatusConverterKt.toException(error));
    }

    @Override // androidx.health.platform.client.service.IGetChangesCallback
    public void onSuccess(@NotNull GetChangesResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        this.resultFuture.set(response.getProto());
    }
}
