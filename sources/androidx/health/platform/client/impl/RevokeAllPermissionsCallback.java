package androidx.health.platform.client.impl;

import androidx.health.platform.client.error.ErrorStatus;
import androidx.health.platform.client.impl.error.ErrorStatusConverterKt;
import androidx.health.platform.client.service.IRevokeAllPermissionsCallback;
import com.google.common.util.concurrent.SettableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Landroidx/health/platform/client/impl/RevokeAllPermissionsCallback;", "Landroidx/health/platform/client/service/IRevokeAllPermissionsCallback$Stub;", "resultFuture", "Lcom/google/common/util/concurrent/SettableFuture;", "", "(Lcom/google/common/util/concurrent/SettableFuture;)V", "onError", "error", "Landroidx/health/platform/client/error/ErrorStatus;", "onSuccess", "connect-client_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class RevokeAllPermissionsCallback extends IRevokeAllPermissionsCallback.Stub {

    @NotNull
    private final SettableFuture<Unit> resultFuture;

    public RevokeAllPermissionsCallback(@NotNull SettableFuture<Unit> resultFuture) {
        Intrinsics.checkNotNullParameter(resultFuture, "resultFuture");
        this.resultFuture = resultFuture;
    }

    @Override // androidx.health.platform.client.service.IRevokeAllPermissionsCallback
    public void onError(@NotNull ErrorStatus error) {
        Intrinsics.checkNotNullParameter(error, "error");
        this.resultFuture.setException(ErrorStatusConverterKt.toException(error));
    }

    @Override // androidx.health.platform.client.service.IRevokeAllPermissionsCallback
    public void onSuccess() {
        this.resultFuture.set(Unit.INSTANCE);
    }
}
