package androidx.health.platform.client.impl;

import androidx.health.platform.client.error.ErrorStatus;
import androidx.health.platform.client.impl.error.ErrorStatusConverterKt;
import androidx.health.platform.client.service.IUnregisterFromDataNotificationsCallback;
import com.google.common.util.concurrent.SettableFuture;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0007H\u0016R\u0016\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Landroidx/health/platform/client/impl/UnregisterFromDataNotificationsCallback;", "Landroidx/health/platform/client/service/IUnregisterFromDataNotificationsCallback$Stub;", "resultFuture", "Lcom/google/common/util/concurrent/SettableFuture;", "Ljava/lang/Void;", "(Lcom/google/common/util/concurrent/SettableFuture;)V", "onError", "", "status", "Landroidx/health/platform/client/error/ErrorStatus;", "onSuccess", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class UnregisterFromDataNotificationsCallback extends IUnregisterFromDataNotificationsCallback.Stub {

    @NotNull
    private final SettableFuture<Void> resultFuture;

    public UnregisterFromDataNotificationsCallback(@NotNull SettableFuture<Void> resultFuture) {
        Intrinsics.checkNotNullParameter(resultFuture, "resultFuture");
        this.resultFuture = resultFuture;
    }

    @Override // androidx.health.platform.client.service.IUnregisterFromDataNotificationsCallback
    public void onError(@NotNull ErrorStatus status) {
        Intrinsics.checkNotNullParameter(status, "status");
        this.resultFuture.setException(ErrorStatusConverterKt.toException(status));
    }

    @Override // androidx.health.platform.client.service.IUnregisterFromDataNotificationsCallback
    public void onSuccess() {
        this.resultFuture.set(null);
    }
}
